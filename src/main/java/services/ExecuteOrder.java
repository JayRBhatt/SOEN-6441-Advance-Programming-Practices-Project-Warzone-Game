package services;

import controller.*;
import model.Country;
import model.GameMap;
import model.orders.OrderDetails;
import model.Player;
import model.orders.Order;
import utils.InvalidCommandException;

/**
 * Class that has the main logic behind the functioning of Execute order phase
 * in
 * the game
 *
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */

 public class ExecuteOrder {

    GameMap d_GameMap;

    /**
     * Constructs an ExecuteOrderService with a reference to the game map.
     *
     */

    public ExecuteOrder() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Starts the execution of orders in the Execute Order phase and then takes the player to the next game phase 
     * after this phase gets completed 
     * @throws InvalidCommandException if game phase transition is invalid.
     */
    public void startExecuteOrder(int p_GamePhaseID) throws InvalidCommandException {
        System.out.println("**************************************************************************************");
        System.out.println(
                "Heyyy Smartie,You have came too far now,its time to execute your orders to conquer this world");
        executeOrders();
        System.out.println("All the orders have been executed successfully");
        new GameEngineController().controller(6);
    }

    /**
     * Executes orders for each player in the game.
     */
    public void executeOrders() {
        for (Player l_Player : d_GameMap.getGamePlayers().values()) {
            for (Order order : l_Player.getOrders()) {
                boolean isOrderExecuted = execute(order);
                if (isOrderExecuted) {
                    System.out.println("Order executed: " + order.getOrderDetails());
                } else {
                    System.out.println("Failed to execute order: " + order.getOrderDetails());
                }
            }
        }
    }

    /**
     * Executes a game order by deploying armies to a destination country.
     *
     * @param order The order to execute.
     * @return true if the order was successfully executed, or false
     *         otherwise.
     */
    public boolean execute(Order order) {
        // Get the order details
        OrderDetails orderDetails = order.getOrderDetails();

        // Check if the player or destination is null
        if (orderDetails.getPlayer() == null || orderDetails.getDestination() == null) {
            System.out.println(
                    "Sorry,this order can't be done you have made a mistake we guess,try checking the above commands and execute the order again");
            return false;
        }
        // Get player, destination, and the number of armies from orderDetails
        Player l_Player = orderDetails.getPlayer();
        String l_Destination = orderDetails.getDestination();
        int l_ArmiesToDeploy = orderDetails.getNumberOfArmy();

        for (Country l_Country : l_Player.getOccupiedCountries()) {
            if (l_Country.getCountryName().equals(l_Destination)) {
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out
                        .println(l_Country.getArmies() + " Armies have been deployed in " + l_Country.getCountryName());
            }
        }

        System.out.println(
                "\nHoorayyy, The Execution has been completed successfully: " + l_ArmiesToDeploy
                        + " armies deployed to " + l_Destination + ".");
        System.out.println("=========================================================================================");
        return true;
    }
}
