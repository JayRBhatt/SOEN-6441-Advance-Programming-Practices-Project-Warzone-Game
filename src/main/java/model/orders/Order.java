package model.orders;

import java.util.*;

import model.Country;

/**
 * A class to manage the orders
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class Order {
    private static Order d_Order;
    private OrderDetails d_OrderInfo;
    private List<Order> d_OrderList = new ArrayList<Order>();
    private String d_Type;
    // public Order(String orderDetails, int numberOfArmies, Country targetCountry)
    // {
    // this.orderDetails = orderDetails;
    // this.numberOfArmies = numberOfArmies;
    // this.targetCountry = targetCountry;
    // }

    /**
     * Method to get the current instance
     * @return the order object
     */
    public static Order getInstance() {
        if (Objects.isNull(d_Order)) {
            d_Order = new Order();
        }
        return d_Order;
    }

    /**
     * A function to get the order list
     *
     * @return the list of type Order class
     */
    public List<Order> getOrderList() {

        return d_OrderList;
    }

    /**
     * A function to set the order list
     *
     * @param p_OrderList Order List of type Order class
     */
    public void setOrderList(List<Order> p_OrderList) {

        this.d_OrderList = p_OrderList;
    }

    /**
     * A function to get order information
     *
     * @return the order information in an object
     */
    public OrderDetails getOrderDetails() {

        return d_OrderInfo;
    }

    /**
     * A function to the set Order information based on the order
     *
     * @param p_OrderInfo Order Information contained in an object of type OrderInfo
     */

    public void setOrderDetails(OrderDetails p_OrderInfo) {
        this.d_OrderInfo = p_OrderInfo;
    }

    /**
     * A function to add the order to the order list
     *
     * @param p_Order The order to be added to the list
     */
    public void AddToOrderList(Order p_Order) {

        d_OrderList.add(p_Order);
    }

    /**
     * A function to return the type of order
     *
     * @return String which indicates the type of order
     */
    public String getType() {

        return d_Type;
    }

    /**
     * A function to set the type of order
     *
     * @param p_Type String which indicates the type of order
     */
    public void setType(String p_Type) {

        this.d_Type = p_Type;
    }

    /**
     * A function to be overridden by the Child class
     *
     * @return false as there is not order to be executed
     */
    public boolean execute() {
        System.out.println("Void order is not able to execute");
        return false;
    }

}
