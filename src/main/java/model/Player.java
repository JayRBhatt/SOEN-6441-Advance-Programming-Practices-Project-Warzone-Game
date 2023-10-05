package model;

import java.util.*;
import model.orders.Order;
import model.orders.OrderCreator;

/**
 * Class that provides every method that is required for any properties of
 * a Player in the game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class Player {
    private int d_PlayerId;
    private String d_PlayerName;
    private List<Country> d_OccupiedCountries = new ArrayList<>();
    private Deque<Order> d_Orders = new ArrayDeque<>();
    private int d_AdditionalArmies;
    private int d_AssignedTroops;

    public static List<Order> OrderList = new ArrayList<>();

    /**
     * Returns the Name of the Player
     * 
     * @return String
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * Sets the Name of a Player
     * 
     * @param p_PlayerName
     */
    public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }

    /**
     * Returns a List of Occupied Countries by a Player
     * 
     * @return d_OccupiedCountries
     */
    public List<Country> getOccupiedCountries() {
        return d_OccupiedCountries;
    }

    /**
     * Sets the List of Occupied Countries by a player
     * 
     * @param p_OccupiedCountries
     */
    public void setOccupiedCountries(List<Country> p_OccupiedCountries) {
        this.d_OccupiedCountries = p_OccupiedCountries;
    }

    /**
     * Returns a Deque of Orders for a player
     * 
     * @return d_Orders
     */
    public Deque<Order> getOrders() {
        return d_Orders;
    }

    /**
     * Adds an order to the Deque of Orders
     * 
     * @param p_Order
     */
   
     private void receiveOrder(Order p_Order) {
        d_Orders.add(p_Order);
    }

    /**
     * Returns the number of Additional Armies of a player
     * 
     * @return d_AdditionalArmies
     */

    public int getAdditionalArmies() {
        return d_AdditionalArmies;
    }

    /**
     * Sets the number of Additional Armies for a player
     * 
     * @param p_AdditionalArmies
     */

    public void setAdditionalArmies(int p_AdditionalArmies) {
        this.d_AdditionalArmies = p_AdditionalArmies;
    }

    /**
     * Publishes the order given by a player
     * 
     * @param p_Command
     */
    public void publishOrder(String p_Command) {
        boolean l_PublishCommand = true;
        String[] l_ArrOfCommands = p_Command.split(" ");
        int l_AdditionalArmies = Integer.parseInt(l_ArrOfCommands[2]);
        if (!confirmIfCountryisOccupied(l_ArrOfCommands[1], this)) {
            System.out.println("OOPS! This Country is not yours");
            l_PublishCommand = false;
        }
        if (!stationAdditionalArmiesFromPlayer(l_AdditionalArmies)) {
            System.out.println("Sorry, You don't have any more Available troops to deploy");
            l_PublishCommand = false;
        }

        if (l_PublishCommand) {
            Order l_Order = OrderCreator.generateOrder(l_ArrOfCommands, this);
            OrderList.add(l_Order);
            receiveOrder(l_Order);
            System.out.println("Your Order has been successfully added in the list: Deploy "
                    + l_Order.getOrderDetails().getCountryDestination() + " with "
                    + l_Order.getOrderDetails().getNumberOfArmies() + " armies");
            System.out.println(
                    "=========================================================================================");
        }
    }

    /**
     * Confirms whether a country is occupied or not
     * 
     * @param p_Country
     * @param p_Player
     * @return true if country is occupied by player or else returns false
     */
    public boolean confirmIfCountryisOccupied(String p_Country, Player p_Player) {
        List<Country> l_ListOfOccupiedCountries = p_Player.getOccupiedCountries();
        for (Country l_Country : l_ListOfOccupiedCountries) {
            if (l_Country.getCountryName().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether to deploy the armies or not
     * 
     * @param p_ArmyNumber
     * @return true if the operation was successful and false if number of army provided by user is greater than remaining armies of the player or 
     * army provided by user is negative
     */
    public boolean stationAdditionalArmiesFromPlayer(int p_ArmyNumber) {

        if (p_ArmyNumber > d_AdditionalArmies || p_ArmyNumber < 0) {
            return false;
        }
        d_AdditionalArmies -= p_ArmyNumber;
        return true;
    }

    /**
     * Creates a list of occupancy of countries by a player and returns 
     * 
     * @param p_Occupy
     * @return the list
     */
    public String createOccupyList(List<Country> p_Occupy) {
        String l_Conclusion = "";
        for (Country l_Occupy : p_Occupy) {
            l_Conclusion += l_Occupy.getPlayer() + "-";
        }
        return l_Conclusion.length() > 0 ? l_Conclusion.substring(0, l_Conclusion.length() - 1) : "";
    }

    /**
     * Calculates the total of alloted reinforcements for a player
     * 
     */
    public void calculateTotalReinforcementArmies() {

        setAdditionalArmies(10);

        System.out.println(getPlayerName() + " has been assigned with " + getAdditionalArmies() + " troops");

    }

    /**
     * An overriden method of toString for debugging
     * 
     */
    @Override
    public String toString() {
        return "helloworld [d_PlayerId=" + d_PlayerId + ", d_PlayerName=" + d_PlayerName + ", d_AdditionalArmies="
                + d_AdditionalArmies + ", d_AssignedTroops=" + d_AssignedTroops + "]";
    }

}