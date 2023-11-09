package model.order;

import services.OrderIssue;
import model.Cards;
import model.CardsType;
import model.GameMap;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.orders.Order;
import model.orders.OrderCreator;

import static org.junit.Assert.*;

/**
 * Test class for Negotiating Order
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class NegotiatingOrderTest {
    GameMap d_GameMap;

    /**
     * Setup for the test case
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
    }

    /**
     * Clear the instance
     *
     * @throws Exception in case of any exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.ClearMap();
    }

    /**
     * Test to test if the command executes correctly
     *
     */
    @Test
    public void execute() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.DIPLOMACY));
        OrderIssue.Commands = "negotiate Player2";
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test the validation of Negotiate command
     */
    @Test
    public void testIfCommandIsTrue() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.DIPLOMACY));
        OrderIssue.Commands = "negotiate Player2";
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Test the validation of Negotiate command
     */
    @Test
    public void testIfCommandIsfalse() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.DIPLOMACY));
        OrderIssue.Commands = "negotiate Player3";
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}
