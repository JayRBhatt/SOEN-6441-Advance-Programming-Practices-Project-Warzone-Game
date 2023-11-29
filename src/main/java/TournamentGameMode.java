import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.GameMap;
import model.GamePhase;
import model.Player;
import model.Calculation.gameCalculation.GameCalculation;
import model.Calculation.playerStrategy.PlayerStrategy;
import model.Calculation.tourament.GameTournamentResult;
import model.Calculation.tourament.GameTournamentSettings;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;
import utils.maputils.MapViewer;
import utils.maputils.ValidateMap;


/**
 * Represents the game mode for a tournament.
 * Implements the Engine interface to manage game-related functionality.
 */
public class TournamentGameMode implements Engine {
	/**
	 * Logger for recording game actions
	 */
    private LogEntryBuffer d_Logger;
    /**
     * Settings for the tournament game
     */
    public GameTournamentSettings d_Options;
    /**
     * List to store results of tournament games
     */
    public List<GameTournamentResult> d_Results = new ArrayList<>();
    /**
     * Current map used in the tournament
     */
    private GameMap d_CurrentMap;
    
    

    /**
     * Constructs a TournamentGameMode instance.
     * Initializes the logger and invokes the init method.
     */
    public TournamentGameMode() {
        d_Logger = LogEntryBuffer.getInstance();
        init();
    }

    /**
     * Initializes the tournament game mode
     * If no options are available, prompts the user to re-enter the command.
     */
    public void init() {
        d_Options = getTournamentOptions();
        if (Objects.isNull(d_Options)) {
            d_Logger.logAction("Re-enter command");
            init();
        }
    }
    
    /**
     * Method to retrieve tournament options from user input
     *
     * @return d_Options The parsed GameTournamentSettings representing the tournament options.
     */

    public GameTournamentSettings getTournamentOptions() {
        Scanner l_Scanner = new Scanner(System.in);
        logTournamentModeInstructions();
        String l_TournamentCommand = l_Scanner.nextLine();
        d_Options = parseCommand(l_TournamentCommand);
        if (Objects.isNull(d_Options)) {
            return getTournamentOptions();
        }
        return d_Options;
    }

    /**
     * Method that logs instructions for the tournament mode, displaying a sample command and usage guidelines.
     */
    private void logTournamentModeInstructions() {
        String instruction = "-----------------------------------------------------------------------------------------\n"
                + "You are in Tournament Mode\n"
                + "Enter the tournament command:\n"
                + "Sample Command: tournament -M Map1.map,Map2.map -P strategy1,strategy2 -G noOfGames -D noOfTurns\n"
                + "-----------------------------------------------------------------------------------------";
        d_Logger.logAction(instruction);
    }

    /**
     * Method that parses the given tournament command to extract and set the GameTournamentSettings.
     * @param p_TournamentCommand The tournament command to be parsed.
     * @return The parsed GameTournamentSettings or null if parsing fails.
     */
    public GameTournamentSettings parseCommand(String p_TournamentCommand) {
        try {
            d_Options = new GameTournamentSettings();
            if (isValidTournamentCommand(p_TournamentCommand)) {
                processTournamentCommand(p_TournamentCommand);
            } else {
                return null;
            }
            return d_Options;
        } catch (NumberFormatException e) {
            d_Logger.logAction("Invalid format for number of games or turns.");
            return null;
        } catch (Exception e) {
            d_Logger.logAction("Error parsing command. Check the command format.");
            return null;
        }
    }

    /**
     * Method that validates the tournament command.
     *
     * @param command The tournament command to be validated.
     * @return True if the command is valid, false otherwise.
     */
    private boolean isValidTournamentCommand(String command) {
        return !command.isEmpty() &&
                command.contains("-M") && command.contains("-P") &&
                command.contains("-G") && command.contains("-D");
    }

    /**
     *Method that processes the given tournament command
     * @param command The tournament command to be processed.
     */
    private void processTournamentCommand(String command) {
        List<String> commandList = Arrays.asList(command.split(" "));
        extractAndSetCommandOptions(commandList);
    }

    /**
     *Method that extracts and sets various tournament command options such as map, player strategies etc
     * @param commandList The list containing the parsed elements of the tournament command.
     */
    private void extractAndSetCommandOptions(List<String> commandList) {
        String mapValue = commandList.get(commandList.indexOf("-M") + 1);
        String playerTypes = commandList.get(commandList.indexOf("-P") + 1);
        String gameCount = commandList.get(commandList.indexOf("-G") + 1);
        String maxTries = commandList.get(commandList.indexOf("-D") + 1);

        d_Options.getMap().addAll(Arrays.asList(mapValue.split(",")));
        extractPlayerStrategies(playerTypes);
        d_Options.setGames(Integer.parseInt(gameCount));
        d_Options.setMaxTries(Integer.parseInt(maxTries));
    }

    /**
     *Method to extracts player strategies from the given string and sets them in the tournament options.     *
     * @param playerTypes The string containing player strategies.
     */
    private void extractPlayerStrategies(String playerTypes) {
        if (playerTypes.contains("human")) {
            d_Logger.logAction("Tournament mode does not support human players. Switch to Single Game Mode.");
            return;
        }
        Arrays.stream(playerTypes.split(","))
                .map(PlayerStrategy::getStrategy)
                .forEach(d_Options.getPlayerStrategies()::add);
        if (d_Options.getPlayerStrategies().size() < 2) {
            throw new IllegalArgumentException("At least two player strategies are required.");
        }
    }

    /**
     * Method that initiates the tournament by setting up and running games for each map
     * @throws InvalidCommandException If an invalid command is encountered during setup.
     */
    public void start() throws InvalidCommandException {
        for (String file : d_Options.getMap()) {
            for (int game = 1; game <= d_Options.getGames(); game++) {
                setupGame(file, game);
            }
        }
        displayResults();
    }
    
    /**
     * Method to set up a game using the provided map file and game number.
     *
     * @param file The map file for the game.
     * @param game The number representing the current game.
     * @throws InvalidCommandException If an invalid command is encountered during setup.
     */

    private void setupGame(String file, int game) throws InvalidCommandException {
        GameCalculation.getInstance().MAX_TRIES = d_Options.getMaxTries();
        d_CurrentMap = GameMap.newInstance();
        d_CurrentMap.ClearMap();
        GameTournamentResult result = new GameTournamentResult();
        d_Results.add(result);
        result.setGame(game);
        result.setMap(file);
        setupPlayersAndStartGame(file, result);
    }

    /**
     * Method that sets up players and starts a game using the provided map file and tournament result.
     * @param file   The map file for the game.
     * @param result The tournament result to update with game outcome.
     * @throws InvalidCommandException If an invalid command is encountered during setup.
     */
    private void setupPlayersAndStartGame(String file, GameTournamentResult result) throws InvalidCommandException {
        MapViewer.readMap(d_CurrentMap, file);
        if (!ValidateMap.validateMap(d_CurrentMap, 0)) {
            throw new InvalidCommandException("Invalid Map");
        }
        d_Options.getPlayerStrategies().forEach(strategy -> {
            Player player = new Player(strategy);
            player.setPlayerName(strategy.getClass().getSimpleName());
            d_CurrentMap.getGamePlayers().put(strategy.getClass().getSimpleName(), player);
        });
        d_CurrentMap.assignCountries();
        GameEngine gameEngine = new GameEngine();
        gameEngine.setGamePhase(GamePhase.Reinforcement);
        gameEngine.start();
        determineWinner(result);
    }


	/**
	 *Method to determines the winner of the current game
	 */
    private void determineWinner(GameTournamentResult result) {
        Player winner = d_CurrentMap.getWinner();
        result.setWinner(Objects.nonNull(winner) ? winner.getPlayerName() : "Draw");
    }
    
	/**
	 * Displays the results of the tournament in a tabular format
	 */
    private void displayResults() {
        String tableHeader = "|%-15s|%-28s|%-19s|%n";
        System.out.format("+--------------+-----------------------+-------------------------+%n");
        System.out.format("|     Map      | Winner                     |   Game Number      |%n");
        System.out.format("+--------------+-----------------------+-------------------------+%n");

        d_Results.forEach(
                result -> System.out.format(tableHeader, result.getMap(), result.getWinner(), result.getGame()));

        System.out.format("+--------------+-----------------------+-------------------------+%n");
    }
    
    /**
     * Sets the game phase for the tournament game mode.
     * @param p_GamePhase The game phase to set.
     */
    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }
}
