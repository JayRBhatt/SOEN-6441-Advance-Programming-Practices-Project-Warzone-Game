
package services;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Country;
import model.GameMap;
import model.Player;
import model.orders.Order;
import model.orders.OrderDetails;

public class ExecuteOrderTest {

    private ExecuteOrder executeOrder;
    private GameMap gameMap;
    private Player player1;
    private Player player2;
    private Country country1;
    private Country country2;

    @Before
    public void initializeTestData() {
        executeOrder = new ExecuteOrder();
        gameMap = GameMap.getInstance();

        player1 = new Player();
        player1.setPlayerName("Jay");
        player2 = new Player();
        player2.setPlayerName("Bhargav");

        country1 = new Country();
        country1.setCountryName("India");
        country1.setPlayer(player1);

        country2 = new Country();
        country2.setCountryName("China");
        country2.setPlayer(player2);

        player1.getOccupiedCountries().add(country1);
        player2.getOccupiedCountries().add(country2);

        Order order1 = new Order();
        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setPlayer(player1);
        orderDetails1.setDestination(country1.getCountryName());
        orderDetails1.setNumberOfArmy(0);
        order1.setOrderDetails(orderDetails1);

        Order order2 = new Order();
        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setPlayer(player2);
        orderDetails2.setDestination(country2.getCountryName());
        orderDetails2.setNumberOfArmy(0);
        order2.setOrderDetails(orderDetails2);

        player1.getOrders().add(order1);
        player2.getOrders().add(order2);

        gameMap.getGamePlayers().put(player1.getPlayerName(), player1);
        gameMap.getGamePlayers().put(player2.getPlayerName(), player2);
    }

    @Test
    public void testExecuteValidOrder() {
        // Create a valid order to test execution
        Order validOrder = new Order();
        OrderDetails validOrderDetails = new OrderDetails();
        validOrderDetails.setPlayer(player1);
        validOrderDetails.setDestination("India");
        validOrderDetails.setNumberOfArmy(2);

        validOrder.setOrderDetails(validOrderDetails);

        // Execute the valid order
        boolean result = executeOrder.execute(validOrder);

        // Assert that the execution was successful
        assertTrue(result);
        assertEquals("Jay", player1.getPlayerName());
        assertEquals(2, country1.getArmies()); // Verify that armies were deployed
    }

    @Test
    public void testExecuteInvalidOrder() {
        // Create an invalid order with a null destination
        Order invalidOrder = new Order();
        OrderDetails invalidOrderDetails = new OrderDetails();
        invalidOrderDetails.setPlayer(player1);
        invalidOrderDetails.setDestination(null);
        invalidOrderDetails.setNumberOfArmy(2);

        invalidOrder.setOrderDetails(invalidOrderDetails);

        // Execute the invalid order
        boolean result = executeOrder.execute(invalidOrder);

        // Assert that the execution failed
        assertFalse(result);
        
        assertEquals(0, country1.getArmies()); // Verify that armies were not deployed
    }
}
