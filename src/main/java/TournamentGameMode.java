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
 * Class to implement the tournament mode game
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class TournamentGameMode implements Engine {
  /**
   * logger observable
   */
    private LogEntryBuffer d_Logger;
 /**
  * Tournament options variable
  */
    public GameTournamentSettings d_Options;
     /**
     * List to hold the tournament results
     */
    public List<GameTournamentResult> d_Results = new ArrayList<>();
     /**
     * game map instance
     */
    private GameMap d_CurrentMap;
    /**
     * default constructor
     */
    public TournamentGameMode() {
        d_Logger = LogEntryBuffer.getInstance();
        init();
    }
    /**
     * method to check if object is null
     */
    public void init() {
        d_Options = getTournamentOptions();
        if (Objects.isNull(d_Options)) {
            d_Logger.logAction("Re-enter command");
            init();
        }
    }
    /**
     * Method to read the tournament command
     *
     * @return parsed command
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
     * Displays the tournament command helper
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
     * method to parse the tournament command
     *
     * @param p_TournamentCommand the tournament command
     * @return tournament options
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
     * Check for valid tournament command
     * @param command The string command to validate for a tournament.
     * @return true if the command is valid for a tournament; false otherwise.
     */
    private boolean isValidTournamentCommand(String command) {
        return !command.isEmpty() &&
                command.contains("-M") && command.contains("-P") &&
                command.contains("-G") && command.contains("-D");
    }
   /**
    * Processes a tournament command by extracting and setting its options.
    * @param command The string command representing a tournament configuration to be processed.
    */
    private void processTournamentCommand(String command) {
        List<String> commandList = Arrays.asList(command.split(" "));
        extractAndSetCommandOptions(commandList);
    }
  /**
   * Extracts and sets the tournament command options.
   * @param commandList The list of command options representing a tournament configuration.
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
   * Extracts and sets player strategies from a comma-separated string.
   * @param playerTypes A string containing comma-separated player strategy identifiers.
   * @throws IllegalArgumentException If less than two player strategies are provided.
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
     * Starts the tournament by setting up and running multiple games based on the tournament configuration.
     * @throws InvalidCommandException If an invalid command or configuration is encountered during the tournament setup.
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
     * Sets up and starts a single game within the tournament based on the specified map and game number.
     * @param file The map file used for the game setup.
     * @param game The number representing the current game within the tournament.
     * @throws InvalidCommandException If an invalid command or configuration is encountered during game setup.
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
    * Sets up players and starts a game based on the specified map and records the result.
    * @param file   The map file used for setting up the game.
    * @param result The tournament result object to record the game's outcome.
    * @throws InvalidCommandException If an invalid command or configuration is encountered during game setup.
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
     * Determines and records the winner (or draw) of a game and updates the tournament result.
     * @param result The tournament result object in which the game's outcome will be recorded.
     */
    private void determineWinner(GameTournamentResult result) {
        Player winner = d_CurrentMap.getWinner();
        result.setWinner(Objects.nonNull(winner) ? winner.getPlayerName() : "Draw");
    }
    /**
     * Displays the results of the tournament in a formatted table.
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

    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }
}
