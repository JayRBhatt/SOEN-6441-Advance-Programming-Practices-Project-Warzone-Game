package model.orders;
/**
 * An abstract class to manage the orders of the players
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public abstract class Order {
    /**
     * A data member to strore the order type
     */
    private String d_Type;
    /**
     * An object of OrderDetails
     */
    private OrderDetails d_OrderDetails;

    /**
     * A function to get order information
     *
     * @return the order information in an object
     */
    public OrderDetails getOrderDetails() {
        return d_OrderDetails;
    }

    /**
     * A function to the set Order information based on the order
     *
     * @param p_OrderDetails Order Information contained in an object of type OrderDetails
     */
    public void setOrderDetails(OrderDetails p_OrderDetails) {
        this.d_OrderDetails = p_OrderDetails;
    }

    /**
     * A function to return the type of order
     *
     * @return String which indicates the type of order
     */
    public String getOrderType() {
        return d_Type;
    }

    /**
     * A function to set the type of order
     *
     * @param p_Type String which indicates the type of order
     */
    public void setOrderType(String p_Type) {
        this.d_Type = p_Type;
    }

    /**
     * A function to be overridden by the Child class
     *
     * @return false as there is not order to be executed
     */
    public abstract boolean execute();

    /**
     * A function to validate each command.
     *
     * @return true if command is valid else false
     */
    public abstract boolean validateCommand();

    /**
     * Print the command that is executed successfully
     */
    public abstract void printOrderCommand();

}
