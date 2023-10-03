package model;

import java.util.*;

public class Player {
    private int d_PlayerId;
    private String d_PlayerName;    
    private List<Country> d_OccupiedCountries = new ArrayList<>();
    private Deque<Order> d_Orders = new ArrayDeque<>(); 
    private d_AdditionalArmies;

public static List<Order> OrderList = new ArrayList<>();
    /** 
 * @return int
 */
public int getPlayerId() {
        return d_PlayerId;
    }

/** 
 * @param p_PlayerId
 */
public void setPlayerId(int p_PlayerId) {
        this.d_PlayerId = p_PlayerId;
    }    

/** 
 * @return String
 */
public String getPlayerName() {
        return d_PlayerName;
    }

/** 
 * @param p_PlayerName
 */
public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }
    
public List<Country> getOccupiedCountries(){
    return d_OccupiedCountries;
}

public void setOccupiedCountries(List<Country> p_OccupiedCountries){
   this.d_OccupiedCountries = p_OccupiedCountries;    
}

public Deque<Order> getOrders(){
    return d_Orders;
}
private void setOrders(Deque<Order>p_Orders){
    this.d_Orders = p_Orders;
}
private void receiveOrder(Order p_Order){
    d_Orders.add(p_Order);
}
public int getAdditionalArmies(){
    return d_AdditionalArmies;
}
public void setAdditionalArmies(int p_AdditionalArmies){
    this.d_AdditionalArmies=p_AdditionalArmies;
}

    /**
     * A function to get the reinforcement armies for every player
     *
     * @return armies assigned to playerof int type
     */
    public int getReinforcementArmies() {
        return d_ReinforcementArmies;
    }

    public void issueOrder(String p_CommandsString) {
        boolean l_BlnIssueCommand = true;
        String[] l_CommandStrArr = p_CommandsString.split(" ");
        int l_ReinforcementArmiesNumber = Integer.parseInt(l_CommandStrArr[2]);
        if (!checkIfCountryExists(l_CommandStrArr[1], this)) {
            System.out.println("This country doesnt exist in your countries list.");
            l_BlnIssueCommand = false;
        }
        if (!deployReinforcementArmiesFromPlayer(l_ReinforcementArmiesNumber)) {
            System.out.println("You do have enough Reinforcement Armies to deploy.");
            l_BlnIssueCommand = false;
        }

        if (l_BlnIssueCommand) {
            Order l_Order = OrderCreater.createOrder(l_CommandStrArr, this);
            OrderList.add(l_Order);
            addOrder(l_Order);
            System.out.println("The list has been updated with your order: deploy " + l_Order.getOrderInfo().getNumberOfArmy() + " armies to " + l_Order.getOrderInfo().getDestination());
            System.out.println("************************************************************************************************");
        }
    }

        /**
     *A function to check if the country exists in the list of countries assigned to a player
     *
     * @param p_Country The country to be checked if present
     * @param p_Player The Player for whom the function is checked for
     * @return true if country exists in the assigned country list else false
     */
    public boolean checkIfCountryExists(String p_Country, Player p_Player) {
        List<Country> l_ListOfCountriesAssigned = p_Player.getCapturedCountries();
        for (Country l_Country : l_ListOfCountriesAssigned) {
            if (l_Country.getName().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * A function to check if the army to be deployed is valid
     *
     * @param p_NoOfArmies The armies to be deployed to the country
     * @return true if the armies are valid and deducted from the assigned army pool else false
     */
    public boolean deployReinforcementArmiesFromPlayer(int p_NoOfArmies) {
        if (p_NoOfArmies > d_ReinforcementArmies || p_NoOfArmies < 0) {
            return false;
        }
        d_ReinforcementArmies -= p_NoOfArmies;
        return true;
    }



public void publishOrder(String p_Command){
    boolean l_PublishCommand = true;
    String[] l_ArrOfCommands=p_Command.split(" ");
    int l_AdditionalArmies = Integer.parseInt(l_ArrOfCommands[2]);
    if(!confirmIfCountryisOccupied(l_ArrOfCommands[1], this)){
        System.out.println("OOPS! This Country is not yours");
        l_PublishCommand = false;
    }
    if(!stationAdditionalArmiesFromPlayer(l_AdditionalArmies)){
       System.out.println("Sorry, You don't have any more Available Army to deploy");
       l_PublishCommand = false;
    }
   
  if(l_PublishCommand){
    Order l_Order= orderCreator.publishOrder(l_ArrOfCommands,this);
    OrderList.add(l_Order);
    addOrder(l_Order);
    System.out.println("Your Order has been successfully added in the list: Deploy " + l_Order.getOrderDetails().getLocation() + " with " + l_Order.getOrderDetails().getAmountOfArmies() + " armies");
    System.out.println("=========================================================================================");  
}  
}
public boolean confirmIfCountryisOccupied(String p_Country, Player p_Player){
    List<Country> l_ListOfOccupiedCountries = p_Player.getOccupiedCountries();
    for (Country l_Country : l_ListOfOccupiedCountries) {
        if(l_Country.getPlayerName().equals(p_Country)) {
            return true;
        }
    }
    return false;
}
public boolean stationAdditionalArmiesFromPlayer(int p_ArmyNumber){
    if(p_ArmyNumber > d_AdditionalArmies || p_ArmyNumber < 0) {
        return false;
    }
    d_AdditionalArmies -= p_ArmyNumber;
    return true;
}
public String createOccupyList(List<Country> p_Occupy) {
    String l_Conclusion = "";
    for(Country l_Occupy : p_Occupy){
        l_Conclusion += l_Occupy.getPlayerName() + "-";
    }
    return l_Conclusion.length() > 0 ? l_Conclusion.substring(0,l_Conclusion.length() - 1): "";
}
}