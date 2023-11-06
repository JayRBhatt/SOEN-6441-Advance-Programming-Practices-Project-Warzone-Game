package services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import controller.*;
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
    private Scanner sc = new Scanner(System.in);
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
        d_LogEntryBuffer.logAction("\n ISSUE ORDER PHASE \n");

        Set<String> playerNamesToSkip = SkippedPlayers.stream()
                .map(Player::getPlayerName)
                .collect(Collectors.toSet());

        for (Player l_Player : d_GameMap.getGamePlayers().values()) {
            if (playerNamesToSkip.contains(l_Player.getPlayerName())) {
                continue;
            }

            displayPlayerInfo(l_Player);

            boolean l_IssueCommand;
            do {
                displayCommandList();
                Commands = readFromPlayer();
                l_IssueCommand = CommandValidation(Commands, l_Player);
                d_LogEntryBuffer.logAction(l_Player.getPlayerName() + " has issued this order :- " + Commands);

                if (Commands.equals("pass")) {
                    SkippedPlayers.add(l_Player);
                    break;
                }

                if (l_IssueCommand) {
                    l_Player.deployOrder();
                    System.out.println("The order has been added to the list of orders.");
                    System.out.println("=============================================================================");
                }

            } while (!l_IssueCommand);

            if (SkippedPlayers.size() == d_GameMap.getGamePlayers().size()) {
                break;
            }
        }

        SkippedPlayers.clear();
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    private void displayPlayerInfo(Player l_Player) {
        System.out.println(
                "Player:" + l_Player.getPlayerName() + " Armies assigned are: " + l_Player.getAdditionalArmies());
        System.out.println("The countries to be assigned to the player are: ");
        l_Player.getOccupiedCountries().forEach(l_Country -> System.out.println(l_Country.getCountryName() + " "));

        if (!l_Player.getPlayersCards().isEmpty()) {
            System.out.println("The Cards assigned to the Players are:");
            l_Player.getPlayersCards().forEach(l_Card -> System.out.println(l_Card.getCardsType()));
        }
        System.out.println("=================================================================================");
    }

    private void displayCommandList() {
        System.out.println("List of available commands for game progression:");
        System.out.println("- To deploy your armies, type: 'deploy countryID numArmies'");
        System.out.println(
                "- To move or attack with your armies, type: 'advance countryNameFrom countryNameTo numArmies'");
        System.out.println("- To airlift your armies, type: 'airlift sourceCountryID targetCountryID numArmies'");
        System.out.println("- To initiate a blockade, type: 'blockade countryID'");
        System.out.println("- To start negotiations with another player, type: 'negotiate playerID'");
        System.out.println("- To bomb a country, type: 'bomb countryID'");
        System.out.println("- If you wish to end your turn, type: 'pass'");
        System.out.println("Please enter a valid command to continue:");
        System.out.println("=================================================================================");
    }

    private String readFromPlayer() {

        return sc.nextLine();

    }

    public static boolean CommandValidation(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");
        String[] l_CommandArr = p_CommandArr.trim().split("\\s+");

        String l_Command = l_CommandArr[0].toLowerCase();
        if ("pass".equals(l_Command)) {
            AddToSetOfPlayers(p_Player);
            return false;
        }

        if (!l_Commands.contains(l_Command) || !CheckLengthOfCommand(l_Command, l_CommandArr.length)) {
            System.out.println("The command syntax is invalid.");
            return false;
        }

        if ("deploy".equals(l_Command) || "advance".equals(l_Command)) {
            return validateNumericArgument(l_CommandArr, l_Command.equals("deploy") ? 2 : 3);
        }

        // No specific validation needed for other commands at this stage
        return true;
    }

    private static boolean validateNumericArgument(String[] l_CommandArr, int index) {
        try {
            Integer.parseInt(l_CommandArr[index]);
        } catch (NumberFormatException l_Exception) {
            System.out.println("The number format is invalid");
            return false;
        }
        return true;
    }

    private static void AddToSetOfPlayers(Player p_Player) {
        SkippedPlayers.add(p_Player);
    }

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