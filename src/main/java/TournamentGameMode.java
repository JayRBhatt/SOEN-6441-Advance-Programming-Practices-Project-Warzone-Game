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
 * 
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
        String instruction = "-----------------------------------------------------------------------------------------\n"
                + "You are in Tournament Mode\n"
                + "Enter the tournament command:\n"
                + "Sample Command: tournament -M Map1.map,Map2.map -P strategy1,strategy2 -G noOfGames -D noOfTurns\n"
                + "-----------------------------------------------------------------------------------------";
        d_Logger.logAction(instruction);
        String l_TournamentCommand = l_Scanner.nextLine();
        d_Options = parseCommand(l_TournamentCommand);
        if (Objects.isNull(d_Options)) {
            return getTournamentOptions();
        }
        return d_Options;
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
            if (!p_TournamentCommand.isEmpty() &&
                    p_TournamentCommand.contains("-M") && p_TournamentCommand.contains("-P")
                    && p_TournamentCommand.contains("-G") && p_TournamentCommand.contains("-D")) {
                List<String> l_CommandList = Arrays.asList(p_TournamentCommand.split(" "));
                String l_MapValue = l_CommandList.get(l_CommandList.indexOf("-M") + 1);
                String l_PlayerTypes = l_CommandList.get(l_CommandList.indexOf("-P") + 1);
                String l_GameCount = l_CommandList.get(l_CommandList.indexOf("-G") + 1);
                String l_maxTries = l_CommandList.get(l_CommandList.indexOf("-D") + 1);
                d_Options.getMap().addAll(Arrays.asList(l_MapValue.split(",")));
                if (l_PlayerTypes.contains("human")) {
                    d_Logger.logAction("Tournament mode does not support human player: Switch to Single Game Mode");
                    return null;
                }
                for (String l_Strategy : l_PlayerTypes.split(",")) {
                    d_Options.getPlayerStrategies().add(PlayerStrategy.getStrategy(l_Strategy));
                }
                if (d_Options.getPlayerStrategies().size() < 2) {
                    return null;
                }
                int l_NumOfGames = Integer.parseInt(l_GameCount);
                int l_NumofTurns = Integer.parseInt(l_maxTries);
                if (l_NumOfGames > 0 && l_NumOfGames <= 5 && l_NumofTurns > 0 && l_NumofTurns <= 50) {
                    d_Options.setGames(l_NumOfGames);
                    d_Options.setMaxTries(l_NumofTurns);
                } else {
                    d_Logger.logAction("Give correct number of games and turns");
                    return null;
                }
            } else {
                return null;
            }
            return d_Options;
        } catch (Exception e) {
            d_Logger.logAction("Check your command");
            d_Logger.logAction(
                    "command should be in this format: tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
            return null;
        }
    }

    /**
     * Check for valid tournament command
     * 
     * @param command The string command to validate for a tournament.
     * @return true if the command is valid for a tournament; false otherwise.
     */

    /**
     * Starts the tournament by setting up and running multiple games based on the
     * tournament configuration.
     * 
     * @throws InvalidCommandException If an invalid command or configuration is
     *                                 encountered during the tournament setup.
     */
    public void start() throws InvalidCommandException {
        for (String l_File : d_Options.getMap()) {
            for (int l_game = 1; l_game <= d_Options.getGames(); l_game++) {
                GameCalculation.getInstance().MAX_TRIES = d_Options.getMaxTries();
                d_CurrentMap = GameMap.newInstance();
                d_CurrentMap.ClearMap();
                GameTournamentResult l_Result = new GameTournamentResult();
                d_Results.add(l_Result);
                l_Result.setGame(l_game);
                l_Result.setMap(l_File);
                MapViewer.readMap(d_CurrentMap, l_File);
                if (!ValidateMap.validateMap(d_CurrentMap, 0)) {
                    throw new InvalidCommandException("Invalid Map");
                }
                for (PlayerStrategy l_PlayerStrategy : d_Options.getPlayerStrategies()) {
                    Player l_Player = new Player(l_PlayerStrategy);
                    l_Player.setPlayerName(l_PlayerStrategy.getClass().getSimpleName());
                    d_CurrentMap.getGamePlayers().put(l_PlayerStrategy.getClass().getSimpleName(), l_Player);
                }
                d_CurrentMap.assignCountries();
                GameEngine l_GameEngine = new GameEngine();
                l_GameEngine.setGamePhase(GamePhase.Reinforcement);
                l_GameEngine.start();
                Player l_Winner = d_CurrentMap.getWinner();
                if (Objects.nonNull(l_Winner)) {
                    l_Result.setWinner(l_Winner.getPlayerName());
                } else {
                    l_Result.setWinner("Draw");
                }
            }
        }

        String l_Table = "|%-15s|%-28s|%-19s|%n";
        System.out.format("+--------------+-----------------------+-------------------------+%n");
        System.out.format("|     Map      | Winner                     |   Game Number      |%n");
        System.out.format("+--------------+-----------------------+-------------------------+%n");

        for (GameTournamentResult l_Result : d_Results) {

            System.out.format(l_Table, l_Result.getMap(), l_Result.getWinner(), l_Result.getGame());

        }
        System.out.format("+--------------+-----------------------+-------------------------+%n");
    }

    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }
}
