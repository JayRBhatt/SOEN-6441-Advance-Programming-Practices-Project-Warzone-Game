package model.orders;

import model.Player;

/**
 * A class 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class OrderCreator {

	/**
	 * Method to 
	 * @param p_commands
	 * @param p_player
	 * @return
	 */
    public static Order generateOrder(String[] p_commands, Player p_player) {
        String l_Type = p_commands[0].toLowerCase();
        Order l_deployOrder;

        switch (l_Type) {
            case "deploy":
                l_deployOrder = new DeployOrder();
                l_deployOrder.setOrderDetails(generateDeployOrderInfo(p_commands, p_player));
                break;
            default:
                System.out.println("\nFail to create an order due to invalid arguments");
                l_deployOrder = new Order();
        }

        return l_deployOrder;
    }

    /**
     * Method to
     * @param p_Command
     * @param p_Player
     * @return
     */
    private static OrderDetails generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setDestination(l_CountryID);
        l_OrderDetails.setNumberOfArmy(l_NumberOfArmy);

        return l_OrderDetails;
    }
}
