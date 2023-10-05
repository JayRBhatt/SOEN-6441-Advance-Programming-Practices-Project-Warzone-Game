import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.orders.*;

public class DeployOrderTest {

    private DeployOrder deployOrder;
    private Player testPlayer;
    private Country testCountry;

    @BeforeEach
    public void setUp() {
        deployOrder = new DeployOrder();
        testPlayer = new Player("Madhav");
        testCountry = new Country("China");
        testPlayer.addOccupiedCountry(testCountry);
    }

    @Test
    public void testExecuteValidOrder() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setPlayer(testPlayer);
        orderDetails.setCountryDestination("China");
        orderDetails.setNumberOfArmies(5);
        deployOrder.setOrderDetails(orderDetails);

        assertTrue(deployOrder.execute());
        assertEquals(5, testCountry.getArmies());
    }

    @Test
    public void testExecuteInvalidOrderWithNullPlayer() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountryDestination("China");
        orderDetails.setNumberOfArmies(5);
        deployOrder.setOrderDetails(orderDetails);

        assertFalse(deployOrder.execute());
        assertEquals(0, testCountry.getArmies());
    }

    @Test
    public void testExecuteInvalidOrderWithNullDestination() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setPlayer(testPlayer);
        orderDetails.setNumberOfArmies(5);
        deployOrder.setOrderDetails(orderDetails);

        assertFalse(deployOrder.execute());
        assertEquals(0, testCountry.getArmies());
    }
}





