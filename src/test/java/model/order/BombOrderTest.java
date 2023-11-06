package model.order;

import services.OrderIssue;
import model.*;
import model.orders.Order;
import model.orders.OrderCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Bomb Order
 */
public class BombOrderTest {
    GameMap d_GameMap;
    List<Country> d_Player1Countries;
    List<Country> d_Player2Countries;

    /**
     * Setup for the test case
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();

        d_GameMap.addContinent("Asia","10");
        d_GameMap.addContinent("Africa","20");
        d_GameMap.addCountry("India","Asia");
        d_GameMap.addCountry("Zambia","Africa");
        d_GameMap.addNeighbor("India","Zambia");
        d_GameMap.addNeighbor("Zambia","India");
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
        d_GameMap.assignCountries();
        for (Player l_Player : d_GameMap.getGamePlayers().values()) {
            l_Player.calculateTotalReinforcementArmies(d_GameMap);
        }
        d_Player1Countries = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        d_Player2Countries = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
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
     * Test case to execute the command
     *
     */
    @Test
    public void executeTest() {
        Player l_Player = d_GameMap.getGamePlayer("Player2");
        l_Player.addPlayerCard(new Cards(CardsType.BOMB));
        OrderIssue.Commands = "bomb " + d_Player1Countries.get(0).getCountryName();
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "),l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test the validation of Execute command
     */
    @Test
    public void checkIfCommandIsTrue() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.BOMB));
        OrderIssue.Commands = "bomb " + d_Player2Countries.get(0).getCountryName();
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Test to check if player has bomb card
     */
    @Test
    public void noBombCardTest() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.BLOCKADE));
        OrderIssue.Commands = "bomb " + d_Player2Countries.get(0).getCountryName();
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
    /**
     * Test to check if Target Country belongs to the player
     */
    @Test
    public void invalidTargetCountry() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.BOMB));
        OrderIssue.Commands = "bomb " + d_Player1Countries.get(0).getCountryName();
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}

