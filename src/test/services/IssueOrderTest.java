import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderIssueTest {

    private OrderIssue orderIssue;
    private GameMap gameMap;
    private Player player1;
    private Player player2;
    private Country country1;
    private Country country2;
    private Order order1;
    private Order order2;

    // Helper method to provide input for simulating player input
    private void provideInput(String data) {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Before
    public void setUp() {
        orderIssue = new OrderIssue();
        gameMap = GameMap.getInstance();

        player1 = new Player();
        player1.setPlayerName("Mariya");
        player2 = new Player();
        player2.setPlayerName("Reema");

        country1 = new Country();
        country1.setCountryName("India");
        country1.setPlayer(player1);

        country2 = new Country();
        country2.setCountryName("China");
        country2.setPlayer(player2);

        player1.getOccupiedCountries().add(country1);
        player2.getOccupiedCountries().add(country2);

        // Create mock orders
        order1 = mock(Order.class);
        when(order1.execute()).thenReturn(true);
        OrderDetails orderDetails1 = new OrderDetails(player1, "India", 3);
        when(order1.getOrderDetails()).thenReturn(orderDetails1);

        order2 = mock(Order.class);
        when(order2.execute()).thenReturn(true);
        OrderDetails orderDetails2 = new OrderDetails(player2, "China", 2);
        when(order2.getOrderDetails()).thenReturn(orderDetails2);

        player1.getOrders().add(order1);
        player2.getOrders().add(order2);
        gameMap.getGamePlayers().put("Player1", player1);
        gameMap.getGamePlayers().put("Player2", player2);
    }

    @Test
    public void testBegin() {
        // Simulate player input
        provideInput("deploy India 3\n");

        // Capture the output for validation
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method
        try {
            orderIssue.begin(1);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }

        // Validate the output here based on your expectations
        String expectedOutput = "The Armies assigned to Player Player1 are : 3\n" +
                "Please assign your armies only to the below listed countries:\n" +
                "India\n" +
                "****************************************************************************************\n" +
                "Lets Begin Issuing Orders ! : \n" +
                "1.For any guidance type help we are happy to help you.\n" +
                "You have assigned all your armies to the countries.Lets Move to the next phase!!\n" +
                "**************************************************************************************\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testVerifyCommandDeployValid() {
        assertTrue(orderIssue.VerifyCommandDeploy("DEPLOY India 3"));
    }

    @Test
    public void testVerifyCommandDeployInvalid() {
        assertFalse(orderIssue.VerifyCommandDeploy("INVALID India 3"));
    }

    @After
    public void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }
}