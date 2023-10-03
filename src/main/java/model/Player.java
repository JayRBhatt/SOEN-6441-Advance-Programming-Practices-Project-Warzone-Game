package model;

import java.util.*;
import model.orders.Order;
import model.orders.OrderCreator;

import model.orders.Order;

/**
 * A class that defines the structure of a player in the game 
 */
public class Player {
    private int d_PlayerId;
    private String d_PlayerName;
    private List<Country> d_OccupiedCountries = new ArrayList<>();
    private Deque<Order> d_Orders = new ArrayDeque<>();
    private int d_AdditionalArmies;
    private int d_AssignedTroops;
    /**
     * List containing all the orders
     */
    public static List<Order> OrderList = new ArrayList<>();

    /**
     * Method to get the player id
     * @return the player id
     */
    public int getPlayerId() {
        return d_PlayerId;
    }

    /**
     * Method to set the player id
     * @param p_PlayerId player id
     */
    public void setPlayerId(int p_PlayerId) {
        this.d_PlayerId = p_PlayerId;
    }

    /**
     * Method to get the player name
     * @return the player name
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * Method to set the player name
     * @param p_PlayerName player name
     */
    public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }

    /**
     * Method to get the list of occupied countries
     * @return the list of occupied countries
     */
    public List<Country> getOccupiedCountries() {
        return d_OccupiedCountries;
    }

    /**
     * Method to set the list of occupied countries
     * @param p_OccupiedCountries list of occupied countries
     */
    public void setOccupiedCountries(List<Country> p_OccupiedCountries) {
        this.d_OccupiedCountries = p_OccupiedCountries;
    }

    /**
     * Method to manage the orders
     * @return the orders
     */
    public Deque<Order> getOrders() {
        return d_Orders;
    }
    /**
     * Method to set the orders
     * @param p_Orders the orders
     */
    private void setOrders(Deque<Order> p_Orders) {
        this.d_Orders = p_Orders;
    }
	/**
	 * Method to receive orders
	 * @param p_Order the order object
	 */
    private void receiveOrder(Order p_Order) {
        d_Orders.add(p_Order);
    }

    /**
     * Method to get additional armies
     * @return the number of additional armies
     */
    public int getAdditionalArmies() {
        return d_AdditionalArmies;
    }

    /**
     * Method to set the additional armies
     * @param p_AdditionalArmies number of additional armies
     */
    public void setAdditionalArmies(int p_AdditionalArmies) {
        this.d_AdditionalArmies = p_AdditionalArmies;
    }

    /**
     * Method to get the number of assigned troops
     * @return the number of assigned troops
     */
    public int get_AssignedTroops() {
        return d_AssignedTroops;
    }

    /**
     * Method to assign the number of troops
     * @param p_AssignedTroops assigned troops to be set
     */
    public void set_AssignedTroops(int p_AssignedTroops) {
        this.d_AssignedTroops = p_AssignedTroops;
    }

    /**
     * Method to publish an order
     * @param p_Command the order/command to be published
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
                    + l_Order.getOrderDetails().getDestination() + " with "
                    + l_Order.getOrderDetails().getNumberOfArmy() + " armies");
            System.out.println(
                    "=========================================================================================");
        }
    }

    /**
     * Method to check whether a country is occupied or not
     * @param p_Country name of the country
     * @param p_Player name of the player
     * @return true if country is occupied else returns false
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
     * Method to check whether or not to station additional armies
     * @param p_ArmyNumber number of armies 
     * @return true if we need to station else false
     */
    public boolean stationAdditionalArmiesFromPlayer(int p_ArmyNumber) {
        if (p_ArmyNumber > d_AdditionalArmies || p_ArmyNumber < 0) {
            return false;
        }
        d_AdditionalArmies -= p_ArmyNumber;
        return true;
    }

    /**
     * Method to create an occupy list
     * @param p_Occupy 
     * @return
     */
    public String createOccupyList(List<Country> p_Occupy) {
        String l_Conclusion = "";
        for (Country l_Occupy : p_Occupy) {
            l_Conclusion += l_Occupy.getPlayer() + "-";
        }
        return l_Conclusion.length() > 0 ? l_Conclusion.substring(0, l_Conclusion.length() - 1) : "";
    }

    /**
     * Sets assigned troops of each player to 10 and prints it
     */
    public void calculateTotalReinforcementArmies() {
        set_AssignedTroops(10);
        System.out.println(getPlayerName() + "has been assigned with" + get_AssignedTroops() + " troops");

    }

    /**
     * Method to print 
     */
    @Override
    public String toString() {
        return "helloworld [d_PlayerId=" + d_PlayerId + ", d_PlayerName=" + d_PlayerName + ", d_AdditionalArmies="
                + d_AdditionalArmies + ", d_AssignedTroops=" + d_AssignedTroops + "]";
    }

}
