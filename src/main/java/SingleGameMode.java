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
 * Represents the Single Game Mode in the Warzone game
 */
public class SingleGameMode implements Engine {

    private LogEntryBuffer d_LogEntryBuffer;
    private GameTournamentSettings d_Options;
    private List<GameTournamentResult> d_Results = new ArrayList<>();
    private GameMap d_CurrentMap;

    
    /**
     * Constructs a SingleGameMode instance and initializes loggers and settings.
     */
    public SingleGameMode() {
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
        d_Options = new GameTournamentSettings();
        init();
    }


    /**
     * Initializes the game settings by prompting the user for input.
     */
    public void init() {
        d_Options = getGameTournamentSettings();
        if (Objects.isNull(d_Options)) {
            init();
        }
    }
    
    /**
     * Prompts the user to enter settings for the single game and returns the obtained settings.
     *
     * @return The settings for the single game.
     */

    private GameTournamentSettings getGameTournamentSettings() {
        Scanner l_Scanner = new Scanner(System.in);
        logTournamentModeInstructions();
        String l_TournamentCommand = l_Scanner.nextLine();
        d_Options = parseCommand(l_TournamentCommand);
        if (Objects.isNull(d_Options)) {
            d_LogEntryBuffer.logAction("Wrong command please type again");
            getGameTournamentSettings();
        }
        return d_Options;
    }

    /**
     * Logs instructions for entering the command for single game mode.
     */
    private void logTournamentModeInstructions() {
        d_LogEntryBuffer
                .logAction("-----------------------------------------------------------------------------------------");
        d_LogEntryBuffer.logAction("Single Game Mode Active");
        d_LogEntryBuffer.logAction("Please enter the command to start a single game.");
        d_LogEntryBuffer.logAction("Example Command: tournament -M Map1.map -P strategy1,strategy2");
        d_LogEntryBuffer
                .logAction("-----------------------------------------------------------------------------------------");
    }
    
    /**
     * Method to parse the entered command for single game mode
     *
     * @param p_TournamentCommand The entered command for single game mode.
     * @return The parsed game settings.
     */

    private GameTournamentSettings parseCommand(String p_TournamentCommand) {
        try {
            d_Options = new GameTournamentSettings();
            if (!p_TournamentCommand.isEmpty() &&
                    p_TournamentCommand.contains("-M") && p_TournamentCommand.contains("-P")) {
                List<String> l_CommandList = Arrays.asList(p_TournamentCommand.split(" "));
                String l_MapValue = l_CommandList.get(l_CommandList.indexOf("-M") + 1);
                String l_PlayerTypes = l_CommandList.get(l_CommandList.indexOf("-P") + 1);
                String[] l_Maps = l_MapValue.split(",");
                if (l_Maps.length == 1) {
                    d_Options.getMap().add(l_Maps[0]);
                    for (String l_Strategy : l_PlayerTypes.split(",")) {
                        d_Options.getPlayerStrategies().add(PlayerStrategy.getStrategy(l_Strategy));
                    }
                } else {
                    d_LogEntryBuffer.logAction("Multiple maps not allowed in single game mode");
                    throw new Exception();
                }
            } else {
                return null;
            }
            return d_Options;
        } catch (Exception e) {
            d_LogEntryBuffer.logAction("Check your command");
            d_LogEntryBuffer
                    .logAction("command should be in this format: tournament -M mapfile -P listofplayerstrategies");
            return null;
        }

    }

    /**
     * Starts the execution of the single game 
     * @throws InvalidCommandException If an invalid command or map is encountered.
     */
    public void start() throws InvalidCommandException {
        String l_File = d_Options.getMap().get(0);
        GameCalculation.getInstance().MAX_TRIES = 30;
        d_CurrentMap = GameMap.newInstance();
        d_CurrentMap.ClearMap();
        GameTournamentResult l_Result = new GameTournamentResult();
        d_Results.add(l_Result);
        l_Result.setGame(1);
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
        System.out.printf("%15s %15s\n", l_Result.getMap(), l_Result.getWinner());
    }

    /**
     * method to set game phase
     *
     * @param p_GamePhase the game phase
     */
    // tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3
    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }

}
