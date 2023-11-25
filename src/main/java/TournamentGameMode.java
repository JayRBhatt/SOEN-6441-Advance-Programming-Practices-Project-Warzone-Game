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

public class TournamentGameMode implements Engine {
    private LogEntryBuffer d_Logger;
    private GameTournamentSettings d_Options;
    private List<GameTournamentResult> d_Results = new ArrayList<>();
    private GameMap d_CurrentMap;

    public TournamentGameMode() {
        d_Logger = LogEntryBuffer.getInstance();
        init();
    }

    public void init() {
        d_Options = getTournamentOptions();
        if (Objects.isNull(d_Options)) {
            d_Logger.logAction("Re-enter command");
            init();
        }
    }

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

    private void logTournamentModeInstructions() {
        String instruction = "-----------------------------------------------------------------------------------------\n"
                + "You are in Tournament Mode\n"
                + "Enter the tournament command:\n"
                + "Sample Command: tournament -M Map1.map,Map2.map -P strategy1,strategy2 -G noOfGames -D noOfTurns\n"
                + "-----------------------------------------------------------------------------------------";
        d_Logger.logAction(instruction);
    }

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

    private boolean isValidTournamentCommand(String command) {
        return !command.isEmpty() &&
                command.contains("-M") && command.contains("-P") &&
                command.contains("-G") && command.contains("-D");
    }

    private void processTournamentCommand(String command) {
        List<String> commandList = Arrays.asList(command.split(" "));
        extractAndSetCommandOptions(commandList);
    }

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

    public void start() throws InvalidCommandException {
        for (String file : d_Options.getMap()) {
            for (int game = 1; game <= d_Options.getGames(); game++) {
                setupGame(file, game);
            }
        }
        displayResults();
    }

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

    private void determineWinner(GameTournamentResult result) {
        Player winner = d_CurrentMap.getWinner();
        result.setWinner(Objects.nonNull(winner) ? winner.getPlayerName() : "Draw");
    }

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
