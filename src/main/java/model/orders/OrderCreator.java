package model.orders;

import model.Player;

/**
 * Class that contains the logic about creation of an order for issue order and
 * execute order phases
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class OrderCreator {

    /**
     * Method to generate the order
     * 
     * @param p_commands commands inputted by user
     * @param p_player player
     * @return an order object with all the details of order
     */
    public static Order generateOrder(String[] p_commands, Player p_player) {
        String l_OrderType = p_commands[0].toLowerCase();
        Order l_Orderdeployment;

        switch (l_OrderType) {
            case "deploy":
                l_Orderdeployment = new DeployOrder();
                l_Orderdeployment.setOrderDetails(generateDeployOrderInfo(p_commands, p_player));
                break;
            default:
                System.out.println("\nInvalid order arguments; Unable to create order");
                l_Orderdeployment = new Order();
        }

        return l_Orderdeployment;
    }

    /**
     * Method that generates and returns the orderdetails
     * 
     * @param p_Command Command inputted
     * @param p_Player  player who inputted
     * @return OrderDetails object with information of player, country of deployment
     *         and number of army to be deployed
     */
    private static OrderDetails generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_IDCountry = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setCountryDestination(l_CountryID);
        l_OrderDetails.setNumberOfArmies(l_NumberOfArmy);

        return l_OrderDetails;
    }
}