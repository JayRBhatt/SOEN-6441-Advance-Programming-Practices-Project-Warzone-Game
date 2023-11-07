package model.order;

import services.OrderIssue;
import model.Cards;
import model.CardsType;
import model.Country;
import model.GameMap;
import model.Player;
import model.orders.OrderCreator;
import model.orders.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the Blockade Order
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */

public class BlockadeOrderTest {

    GameMap d_GameMap;
    List<Country> d_CountryList1 = new ArrayList<Country>();
    List<Country> d_CountryList2 = new ArrayList<Country>();

    /**
     * Setup for the test case
     *
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
        d_GameMap.addContinent("Asia", "5");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("Pakistan", "Asia");
        d_GameMap.addCountry("SriLanka", "Asia");
        d_GameMap.addCountry("Afganisthan", "Asia");
        d_GameMap.addCountry("Bangladesh", "Asia");
        d_GameMap.addCountry("Myanmar", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.assignCountries();
        d_CountryList1= d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        d_CountryList2= d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
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
     * Test to test that the blockade command works successfully
     *
     */
    @Test
    public void execute() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.BLOCKADE));
        OrderIssue.Commands = "blockade " + d_CountryList1.get(0).getCountryName() ;
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test the validation of Blockade command for when the target country belongs to same player
     *
     */
    @Test
    public void testIfCommandIsTrue() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.BLOCKADE));
        OrderIssue.Commands = "blockade " + d_CountryList1.get(0).getCountryName() ;
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }


    /**
     * Test the validation of Blockade command when the target country does not belong to player
     *
     */
    @Test
    public void testIfCommandIsfalse() {
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        l_Player1.addPlayerCard(new Cards(CardsType.BLOCKADE));
        OrderIssue.Commands = "blockade " + d_CountryList2.get(0).getCountryName() ;
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().validateCommand());
    }
}