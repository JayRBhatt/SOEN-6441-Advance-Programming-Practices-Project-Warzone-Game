package model;

import java.util.*;

public class Player {
    private int d_PlayerId;
    private String d_PlayerName;    
    private List<Country> d_OccupiedCountries = new ArrayList<>();
    private Deque<Order> d_Orders = new ArrayDeque<>(); 
    private int d_AdditionalArmies;
    private int d_AssignedTroops;

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

public int get_AssignedTroops() {
	return d_AssignedTroops;
}

public void set_AssignedTroops(int p_AssignedTroops) {
	this.d_AssignedTroops = p_AssignedTroops;
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
        l_Conclusion += l_Occupy.getPlayer() + "-";
    }
    return l_Conclusion.length() > 0 ? l_Conclusion.substring(0,l_Conclusion.length() - 1): "";
}

public void calculateTotalReinforcementArmies() {
	// TODO Auto-generated method stub
	
	//	Player l_Player = p_GameMap.getGamePlayer(p_Id);
	if(getOccupiedCountries().size() > 0) {
		
		d_AssignedTroops = 3 * getOccupiedCountries().size();
		set_AssignedTroops(d_AssignedTroops);
	}
	else {
		set_AssignedTroops(3);
	}
	System.out.println(getPlayerName() + "has been assigned with" + get_AssignedTroops() + " troops");
}
}