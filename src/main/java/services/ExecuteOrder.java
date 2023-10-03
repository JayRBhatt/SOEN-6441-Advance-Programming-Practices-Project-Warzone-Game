package services;

import controller.GameController;
import model.GameMap;
import model.Player;
import model.order.Order;

/**
 * This class represents the service responsible for executing orders in the game.
 * It implements the GameController interface.
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class ExecuteOrderService implements GameController {

    private final GameMap d_GameMap;

    /**
     * Constructs an ExecuteOrderService with a reference to the game map.
     *
     * @param gameMap The game map instance.
     */
    public ExecuteOrderService(GameMap d_GameMap) {
        this.d_GameMap = d_GameMap;
    }

    /**
     * Starts the execution of orders in the Execute Order phase.
     *
     * @return The next game phase.
     * @throws Exception if  game phase transition is invalid.
     */
    @Override
    public void startExecuteOrder(int p_GamePhaseID) throws Exception {
        if (p_GamePhaseID != 5) {
            throw new Exception("Invalid game phase transition.");
        }
        executeOrders();
        System.out.println("All the orders have been executed successfully");
        GameEngineController.controller(5);
    }

    /**
     * Executes orders for each player in the game.
     */
    private void executeOrders() {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            for (Order order : l_Player.getOrders()) {
                execute(order);
                if (execute(order)) {
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
     * @return {@code true} if the order was successfully executed, {@code false} otherwise.
     */
    public boolean execute(Order order) {
        // Get the order details
        OrderDetails orderDetails = order.getOrderDetails();

        // Check if the player or destination is null
        if (orderDetails.getPlayer() == null || orderDetails.getDestination() == null) {
            System.out.println("Cannot be Executed: Invalid order information.");
            return false;
        }
        // Get player, destination, and the number of armies from orderDetails
        Player l_Player = orderDetails.getPlayer();
        String l_Destination = orderDetails.getDestination();
        int l_ArmiesToDeploy = orderDetails.getNumberOfArmy();

        // Iterate through the player's occupied countries to find the destination country
        for(Country l_Country : l_Player.getOccupiedCountries()){
            if(l_Country.getName().equals(l_Destination)){
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println(l_Country.getArmies() + " armies have been deployed in " + l_Country.getName());
            }
        }

        // Print execution details
        System.out.println("\nExecution has been completed: " + l_ArmiesToDeploy + " armies deployed to " + l_Destination + ".");
        System.out.println("=========================================================================================");
        return true;
    }
}
