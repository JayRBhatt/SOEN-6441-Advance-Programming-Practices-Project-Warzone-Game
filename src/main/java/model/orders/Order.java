package model.orders;
import java.util.*;

/**
 * It is a class to manage the orders of the players
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class Order {
    private static Order d_Order;
    private OrderDetails d_OrderInfo;
    private List<Order> d_OrderList = new ArrayList<Order>();
    private String d_Type;
  
	/**
	 * A method to get the instance of the order
	 * 
	 * @return d_Order
	*/
     public static Order getInstance() {
        if (Objects.isNull(d_Order)) {
            d_Order = new Order();
        }
        return d_Order;
    }


    /**
     * A method to get the order list
     *
     * @return the list of type Order class
     */
  
     public List<Order> getOrders() {
    	 return d_OrderList;
    }

    /**
     * A method to set the order list
     *
     * @param p_OrderList Order List of type Order class
     */
    
     public void setOrders(List<Order> p_OrderList) {
        this.d_OrderList = p_OrderList;
    }


    /**
     * A method to get order information
     *
     * @return the order information in an object
     */

    public OrderDetails getOrderDetails() {
        return d_OrderInfo;
    }


    /**
     * A method to the set Order information based on the order
     *
     * @param p_OrderInfo Order Information contained in an object of type OrderInfo
     */

    public void setOrderDetails(OrderDetails p_OrderInfo) {
        this.d_OrderInfo = p_OrderInfo;
    }


    /**
     * A method to add the order to the order list
     *
     * @param p_Order The order to be added to the list
     */
    
    public void AddToOrders(Order p_Order) {
        d_OrderList.add(p_Order);
    }


    /**
     * A method to return the type of order
     *
     * @return string which indicates the type of order
     */
    public String getOrderType() {

       return d_Type;
    }


    /**
     * A method to set the type of order
     *
     * @param p_Type string which indicates the type of order
     */

    public void setOrderType(String p_Type) {
    this.d_Type = p_Type;
    }


    /**
     * Execute method that is to be overriden by child method
     *
     * @return false no order to be executed
     */
    
     public boolean execute() {
        System.out.println("cannot execute a null or void ");
        return false;
    }
}



