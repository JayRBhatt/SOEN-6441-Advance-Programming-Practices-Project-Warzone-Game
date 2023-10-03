package services;

import java.util.Scanner;
import controller.*;


/**
 * A class that manages the flow of diffrent phases of the warzone game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */


public class OrderIssue
{
    GameMap d_GameMap;
    private Scanner sc=new Scanner(System.in);
    /**
     * Constructor to get the current state of GameMap instance
     */
        public void OrderIssue(){
            d_GameMap = GameMap.getInstance();
        }
            /**
     *
     *A function that allows the player to start issuing orders
     *This function checks the number of armies left for the players and allows him to deploy to countries accordingly.
     *This function also shows the list of countries making it user-friendly.
     * @return the next phase to be executed
     * @throws Exception  when execution fails
     */
    public void begin(int p_GamePhaseID) throws Exception {
            int l_PlayerNumber = 0;
            while (l_PlayerNumber < d_GameMap.getPlayers().size()) {
                for (Player l_Player : d_GameMap.getPlayers().values()) {
                    if (l_Player.getReinforcementArmies() <= 0) {
                        l_PlayerNumber++;
                        continue;
                    } 
                    String l_PlayerName = l_Player.getName();
                    int l_ReinforcementArmies = l_Player.getReinforcementArmies();
                    System.out.println("Armies assigned to Player " + l_PlayerName + "are : " +l_ReinforcementArmies);
                    System.out.println("Please assign your armies to the below listed countries :");
                    printAssignedCountries(l_Player);
                    String l_CommandInputString = readFromPlayer();
                    l_Player.issueOrder(l_CommandInputString);
                }\
            }
            System.out.println("You have assigned all your armies to the countries.Lets Move to the next phase!!");
            System.out.println("**************************************************************************************");
            return p_GamePhase.nextState(d_NextGamePhase);
        }

    /**
     * A function that shows the list of countires in which the player can deploy the armies.
     * It makes the application user-friendly.
     * @return prints the countries the player holds.
     */
private void printAssignedCountries(Player player) {
    for (Country country : player.getCapturedCountries()) {
        System.out.println(country.getName());
    }
    System.out.println("****************************************************************************************");
}
    /**
     * A function to help the Player issue orders.
     * This function help the player with the syntax of command 
     * Also allows the player to enter the command and let the palyer know the command entered is right
     * @return command entered by the player
     */
    private String readFromPlayer() {
            String l_CommandInput;
            System.out.println("Lets Begin Issuing Oders ! : ");
            System.out.println("1. Type 'help' and Enter to see the commands.");
            while(true){
                l_CommandInput = SCANNER.nextLine();
                    if (VerifyCommandDeploy(l_CommandInput.toUpperCase())) 
                        return l_CommandInput;
                 else {
                    System.out.println("Here are the list of Game Loop Commands..");
                    System.out.println("You can use this command to deploy the Armies");
                    System.out.println("deploy<space>countryName<space>no.of.armies");
                    System.out.println("Please enter the command Now:");
                }
            }
        }

    /**
     * A function to check if the command entered by the player is correct before proceeding
     *
     * @param p_CommandString The command entered by player
     * @return true if the format is valid else false
     */
    private boolean VerifyCommandDeploy(String p_CommandString){
            String[] l_CommandInputsList = p_CommandString.split(" ");
            if(l_CommandInputsList.length ==  3){
                return l_CommandInputsList[0].equals("DEPLOY");
            }
            else
                return false;
        }



}