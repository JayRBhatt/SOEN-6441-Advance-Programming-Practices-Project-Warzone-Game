import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.orders.*;

public class OrderCreatorTest {

    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        // Create a sample player for testing
        testPlayer = new Player("Maria");
    }

    @Test
    public void testGenerateDeployOrder() {
        String[] commands = {"deploy", "Pakistan", "5"};
        Order deployOrder = OrderCreator.generateOrder(commands, testPlayer);

        assertNotNull(deployOrder);
        assertTrue(deployOrder instanceof DeployOrder);
        assertEquals(testPlayer, deployOrder.getOrderDetails().getPlayer());
        assertEquals("Pakistan", deployOrder.getOrderDetails().getDestination());
        assertEquals(5, deployOrder.getOrderDetails().getNumberOfArmy());
    }

    @Test
    public void testGenerateInvalidOrder() {
        String[] commands = {"invalid", "China", "3"};
        Order invalidOrder = OrderCreator.generateOrder(commands, testPlayer);

        assertNotNull(invalidOrder);
        assertFalse(invalidOrder instanceof DeployOrder);
    }

    @Test
    public void testGenerateOrderWithInvalidArguments() {
        String[] commands = {"deploy", "Japan"};
        Order deployOrder = OrderCreator.generateOrder(commands, testPlayer);

        assertNotNull(deployOrder);
        assertFalse(deployOrder instanceof DeployOrder);
    }
}