package model.orders;

import java.util.*;

import model.Country;

public class Order {
    private String orderDetails;
    private int numberOfArmies;
    private Country targetCountry;
    private OrderDetails d_OrderInfo;
public Order(){
    
} 

    public Order(String orderDetails, int numberOfArmies, Country targetCountry) {
        this.orderDetails = orderDetails;
        this.numberOfArmies = numberOfArmies;
        this.targetCountry = targetCountry;
    }

    public OrderInfo getOrderDetails() {

        return d_OrderInfo;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public int getNumberOfArmies() {
        return numberOfArmies;
    }

    public Country getTargetCountry() {
        return targetCountry;
    }

    // public boolean execute() {
    //     if (getOrderDetails().getPlayer() == null || getOrderDetails().getDestination() == null) {
    //         System.out.println("Cannot be Executed: Invalid order information.");
    //         return false;
    //     }
    //     Player l_Player = getOrderDetails().getPlayer();
    //     String l_Destination = getOrderDetails().getDestination();
    //     int l_ArmiesToDeploy = getOrderDetails().getNumberOfArmy();
    //     for(Country l_Country : l_Player.getCapturedCountries()){
    //         if(l_Country.getName().equals(l_Destination)){
    //             l_Country.deployArmies(l_ArmiesToDeploy);
    //             System.out.println(l_Country.getArmies() + " armies have been deployed in " + l_Country.getName());
    //         }
    //     }
    //     System.out.println("\nExecution has been completed: " + l_ArmiesToDeploy + " armies deployed to " + l_Destination + ".");
    //     System.out.println("=========================================================================================");
    //     return true;
    // }
}
