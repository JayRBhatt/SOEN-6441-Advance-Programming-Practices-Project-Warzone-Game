package services;

import java.util.Scanner;
import controller.*;
import model.Country;
import model.GameMap;
import model.Player;
import utils.InvalidCommandException;

/**
 * Class that has the main logic behind the functioning of OrderIssue phase
 * in the game
 *
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */

public class OrderIssue {
    GameMap d_GameMap;
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructor that initializes the GameMAp Instance with the current State
     * 
     */
    public OrderIssue() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Method that runs the main logic of Order Issue and takes the player to the next phase
     * 
     * @param p_GamePhaseID the current ID of the Phase
     * @throws InvalidCommandException if it encounters any wrong command
     */
    public void begin(int p_GamePhaseID) throws InvalidCommandException {
        int l_PlayerNumber = 0;
        while (l_PlayerNumber < d_GameMap.getGamePlayers().size()) {
            for (Player l_Player : d_GameMap.getGamePlayers().values()) {
                if (l_Player.getAdditionalArmies() <= 0) {
                    l_PlayerNumber++;
                    continue;
                }
                String l_PlayerName = l_Player.getPlayerName();
                int l_ReinforcementArmies = l_Player.getAdditionalArmies();
                System.out.println("The Armies assigned to Player " + l_PlayerName + " are : " + l_ReinforcementArmies);
                System.out.println("Please assign your armies only to the below listed countries:");
                printAssignedCountries(l_Player);
                String l_CommandInputString = readFromPlayer();
                l_Player.publishOrder(l_CommandInputString);
            }
        }
        System.out.println("You have assigned all your armies to the countries.Lets Move to the next phase!!");
        System.out.println("**************************************************************************************");
        new GameEngineController().controller(5);
    }

    /**
     * A function that shows the list of countries in which the player can deploy
     * the armies.
     * 
     * @param p_Player
     */
    private void printAssignedCountries(Player p_Player) {
        for (Country country : p_Player.getOccupiedCountries()) {
            System.out.println(country.getCountryName());
        }
        System.out.println("****************************************************************************************");
    }

    /**
     *It reads the command input from the player and validates it
     * 
     * @return command entered by the Player
     */
    private String readFromPlayer() {
        String l_CommandInput;
        System.out.println("Lets Begin Issuing Orders ! : ");
        System.out.println("1.For any guidance type help we are happy to help you.");
        while (true) {
            l_CommandInput = sc.nextLine();
            if (VerifyCommandDeploy(l_CommandInput.toUpperCase()))
                return l_CommandInput;
            else {
                System.out.println("There you go, Here is the list of Commands you can use for this phase");
                System.out.println("The command to deploy the Armies is");
                System.out.println("deploy countryName no.of.armies");
                System.out.println("Please enter the command Now:");
            }
        }
    }

    /**
     * A function to check if the command entered by the player is correct before
     * proceeding
     *
     * @param p_CommandString The command entered by player
     * @return true if the format is valid else false
     */
    private boolean VerifyCommandDeploy(String p_CommandString) {
        String[] l_CommandInputsList = p_CommandString.split(" ");
        if (l_CommandInputsList.length == 3) {
            return l_CommandInputsList[0].equals("DEPLOY");
        } else
            return false;
    }

}