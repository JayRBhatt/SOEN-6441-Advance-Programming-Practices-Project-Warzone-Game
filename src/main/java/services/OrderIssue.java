package services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import controller.*;
import model.Cards;
import model.Country;
import model.GameMap;
import model.GamePhase;
import model.Player;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;

/**
 * Class which is the controller for the Issue Order phase
 *
 * 
 * @version 1.0.0
 */

public class OrderIssue implements GameEngineController {
    GameMap d_GameMap;
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase;
    private final static Scanner sc = new Scanner(System.in);
    private static Set<Player> SkippedPlayers = new HashSet<>();
    public static String Commands = null;
    LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

    /**
     * Constructor that initializes the GameMAp Instance with the current State
     * 
     */
    public OrderIssue() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Method that runs the main logic of Order Issue and takes the player to the
     * next phase
     * 
     * @param p_GamePhase the current ID of the Phase
     * @throws InvalidCommandException if it encounters any wrong command
     */
    public GamePhase start(GamePhase p_GamePhase) throws Exception {

        d_GamePhase = p_GamePhase;
        d_LogEntryBuffer.logAction("\n ENTERED THE ISSUE ORDER PHASE \n");
        while (!(SkippedPlayers.size() == d_GameMap.getGamePlayers().size())) {
            for (Player l_Player : d_GameMap.getGamePlayers().values()) {
                if (!SkippedPlayers.isEmpty() && SkippedPlayers.contains(l_Player)) {
                    continue;
                }
                System.out.println("Player:" + l_Player.getPlayerName() + "; Armies assigned are: "
                        + l_Player.getAdditionalArmies());
                System.out.println("The countries to be assigned to the player are: ");
                for (Country l_Country : l_Player.getOccupiedCountries()) {
                    System.out.println(l_Country.getCountryName() + " ");
                }
                if (!l_Player.getPlayersCards().isEmpty()) {
                    System.out.println("The Cards assigned to the Players are:");
                    for (Cards l_Card : l_Player.getPlayersCards()) {
                        System.out.println(l_Card.getCardsType());
                    }
                }
                System.out.println("=================================================================================");
                boolean l_IssueCommand = false;
                while (!l_IssueCommand) {
                    System.out.println("List of available commands for game progression:");
                    System.out.println("- To deploy your armies, type: 'deploy countryID numArmies'");
                    System.out.println(
                            "- To move or attack with your armies, type: 'advance countryNameFrom countryNameTo numArmies'");
                    System.out.println(
                            "- To airlift your armies, type: 'airlift sourceCountryID targetCountryID numArmies'");
                    System.out.println("- To initiate a blockade, type: 'blockade countryID'");
                    System.out.println("- To start negotiations with another player, type: 'negotiate playerID'");
                    System.out.println("- To bomb a country, type: 'bomb countryID'");
                    System.out.println("- If you wish to end your turn, type: 'pass'");
                    System.out.println("Please enter a valid command to continue:");
                    System.out.println(
                            "=================================================================================");
                    Commands = ReadFromPlayer();
                    l_IssueCommand = CommandValidation(Commands, l_Player);
                    d_LogEntryBuffer.logAction(l_Player.getPlayerName() + " has issued this order :- " + Commands);
                    if (Commands.equals("pass")) {
                        break;
                    }
                }
                if (!Commands.equals("pass")) {
                    l_Player.deployOrder();
                    System.out.println("The order has been had to the list of orders.");
                    System.out.println("=============================================================================");
                }
            }
        }
        SkippedPlayers.clear();
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    /**
     * A function to read all the commands from player
     *
     * @return command entered by the player
     */
    public static String ReadFromPlayer() {
        return sc.nextLine();
    }

    /**
     * A static function to validate the deploy command
     *
     * @param p_CommandArr The string entered by the user
     * @param p_Player     the player object
     * @return true if the command is correct else false
     */
    public static boolean CommandValidation(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");
        String[] l_CommandArr = p_CommandArr.split(" ");
        if (p_CommandArr.toLowerCase().contains("pass")) {
            AddToSetOfPlayers(p_Player);
            return false;
        }
        if (!l_Commands.contains(l_CommandArr[0].toLowerCase())) {
            System.out.println("The command syntax is invalid.");
            return false;
        }
        if (!CheckLengthOfCommand(l_CommandArr[0], l_CommandArr.length)) {
            System.out.println("The command syntax is invalid.");
            return false;
        }
        switch (l_CommandArr[0].toLowerCase()) {
            case "deploy":
                try {
                    Integer.parseInt(l_CommandArr[2]);
                } catch (NumberFormatException l_Exception) {
                    System.out.println("The number format is invalid");
                    return false;
                }
                break;
            case "advance":
                if (l_CommandArr.length < 4) {
                    System.out.println("The command syntax is invalid.");
                    return false;
                }
                try {
                    Integer.parseInt(l_CommandArr[3]);
                } catch (NumberFormatException l_Exception) {
                    System.out.println("The number format is invalid");
                    return false;
                }

            default:
                break;

        }
        return true;
    }

    /**
     * A function to map the players and their status for the issuing of the order
     *
     * @param p_Player The player who has skipped his iteration for the issuing
     */
    private static void AddToSetOfPlayers(Player p_Player) {
        SkippedPlayers.add(p_Player);
    }

    /**
     * A function to check the length of each command
     *
     * @param p_Command the command to be validated
     * @return true if length is correct else false
     */
    private static boolean CheckLengthOfCommand(String p_Command, int p_Length) {
        if (p_Command.contains("deploy")) {
            return p_Length == 3;
        } else if (p_Command.contains("bomb") || p_Command.contains("blockade") || p_Command.contains("negotiate")) {
            return (p_Length == 2);
        } else if (p_Command.contains("airlift") || p_Command.contains("advance")) {
            return (p_Length == 4);
        }
        return false;
    }

}