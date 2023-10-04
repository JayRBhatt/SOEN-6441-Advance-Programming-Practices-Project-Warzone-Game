package services;

import controller.GameEngineController;
import model.*;
import model.orders.*;
import services.ExecuteOrder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExecuteOrderTest {

    private ExecuteOrder executeOrder;
    private GameMap d_GameMap;
    private Player d_Player1;
    private Player d_Player2;
    private Country d_Country1;
    private Country d_Country2;
    private Order d_Order1;
    private Order d_Order2;

    @Before
    public void setUp() {
        executeOrder = new ExecuteOrder();
        d_GameMap = GameMap.getInstance();

        d_Player1 = new Player();
        d_Player1.setPlayerName("Player1");
        d_Player2 = new Player();
        d_Player2.setPlayerName("Player2");

        d_Country1 = new Country();
        d_Country1.setCountryName("India");
        d_Country1.setPlayer(d_Player1);

        d_Country2 = new Country();
        d_Country2.setCountryName("China");
        d_Country2.setPlayer(d_Player2);

        d_Player1.getOccupiedCountries().add(d_Country1);
        d_Player2.getOccupiedCountries().add(d_Country2);

        // Create mock orders
        d_Order1 = mock(Order.class);
        when(d_Order1.execute()).thenReturn(true);
        OrderDetails orderDetails1 = new OrderDetails(d_Player1, "India", 3);
        when(d_Order1.getOrderDetails()).thenReturn(orderDetails1);

        d_Order2 = mock(Order.class);
        when(d_Order2.execute()).thenReturn(true);
        OrderDetails orderDetails2 = new OrderDetails(d_Player2, "China", 2);
        when(d_Order2.getOrderDetails()).thenReturn(orderDetails2);

        d_Player1.getOrders().add(d_Order1);
        d_Player2.getOrders().add(d_Order2);
        d_GameMap.getGamePlayers().put("Player1", d_Player1);
        d_GameMap.getGamePlayers().put("Player2", d_Player2);

        resetConsoleOutput();
    }

    // Test case to verify the execution of multiple orders
    @Test
    public void testExecuteOrders() {
        executeOrder.executeOrders();

        verify(d_Order1, times(1)).execute();
        verify(d_Order2, times(1)).execute();

        String expectedOutput = "Order executed: " + d_Order1.getOrderDetails() + "\n" +
                "3 Armies have been deployed in " + d_Order1.getOrderDetails().getDestination() + "\n" +
                "Order executed: " + d_Order2.getOrderDetails() + "\n" +
                "2 Armies have been deployed in " + d_Order2.getOrderDetails().getDestination() + "\n";
        assertEquals(expectedOutput, getConsoleOutput());
    }

    // Test case to verify the execution of a single order
    @Test
    public void testExecuteOrder() {
        boolean result = executeOrder.execute(d_Order1);
        assertTrue(result);
        String expectedOutput = "3 Armies have been deployed in " + d_Order1.getOrderDetails().getDestination() + "\n" +
                "\nHoorayyy, The Execution has been completed successfully: 3 armies deployed to " + d_Order1.getOrderDetails().getDestination() + ".\n" +
                "=========================================================================================\n";
        assertEquals(expectedOutput, getConsoleOutput());
        assertEquals(3, d_Country1.getArmies());
    }

    // Test case to verify the execution of an order with a null player
    @Test
    public void testExecuteOrderWithNullPlayer() {
        OrderDetails orderDetails = new OrderDetails(null, "India", 3);
        Order nullPlayerOrder = mock(Order.class);
        when(nullPlayerOrder.getOrderDetails()).thenReturn(orderDetails);
        
        boolean result = executeOrder.execute(nullPlayerOrder);
        assertFalse(result);
        
        String expectedOutput = "Sorry,this order can't be done you have made a mistake we guess,try checking the above commands and execute the order again\n";
        assertEquals(expectedOutput, getConsoleOutput());
    }

    // Test case to verify the execution of an order with a null destination
    @Test
    public void testExecuteOrderWithNullDestination() {
        OrderDetails orderDetails = new OrderDetails(d_Player1, null, 3);
        Order nullDestinationOrder = mock(Order.class);
        when(nullDestinationOrder.getOrderDetails()).thenReturn(orderDetails);
        
        boolean result = executeOrder.execute(nullDestinationOrder);
        assertFalse(result);
        
        String expectedOutput = "Sorry,this order can't be done you have made a mistake we guess,try checking the above commands and execute the order again\n";
        assertEquals(expectedOutput, getConsoleOutput());
    }

    // Test case to verify the execution of an order with an invalid country
    @Test
    public void testExecuteOrderWithInvalidCountry() {
        OrderDetails orderDetails = new OrderDetails(d_Player1, "InvalidCountry", 3);
        Order invalidCountryOrder = mock(Order.class);
        when(invalidCountryOrder.getOrderDetails()).thenReturn(orderDetails);
        
        boolean result = executeOrder.execute(invalidCountryOrder);
        assertFalse(result);
        
        String expectedOutput = "Sorry,this order can't be done you have made a mistake we guess,try checking the above commands and execute the order again\n";
        assertEquals(expectedOutput, getConsoleOutput());
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
    }

    // Helper method to capture console output (you may need to implement this)
    private String getConsoleOutput() {
        return consoleOutput.toString().replaceAll("\r", "");
    }

    // Function to reset or capture console output during the test
    private void resetConsoleOutput() {
        consoleOutput.reset();
    }
}
