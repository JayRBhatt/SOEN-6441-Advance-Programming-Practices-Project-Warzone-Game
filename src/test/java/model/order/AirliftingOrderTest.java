package model.order;

import services.OrderIssue;
import model.Cards;
import model.CardsType;
import model.Country;
import model.GameMap;
import model.Player;
import model.orders.Order;
import model.orders.OrderCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the Airlift Order
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class AirliftingOrderTest {
    GameMap d_GameMap;
    List<Country> l_CountryList1 = new ArrayList<Country>();
    List<Country> l_CountryList2 = new ArrayList<Country>();

    /**
     * Setup for the test case
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
        l_CountryList1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        l_CountryList2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
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
        l_Player.addPlayerCard(new Cards(CardsType.AIRLIFT));
        l_CountryList1.get(0).setArmies(100);
        OrderIssue.Commands = "airlift " + l_CountryList1.get(0).getCountryName() + " " + l_CountryList1.get(1).getCountryName()+ " "+ 10;
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test the validation of Airlift command for when the target country belongs to same player
     */
    @Test
    public void testIfCommandIsTrue() {
        Player l_Player = d_GameMap.getGamePlayer("Player1");
        l_Player.addPlayerCard(new Cards(CardsType.AIRLIFT));
        l_CountryList1.get(0).setArmies(100);
        System.out.println("Source: "+ l_CountryList1.get(0).getArmies());
        OrderIssue.Commands = "airlift " + l_CountryList1.get(0).getCountryName() + " " + l_CountryList1.get(1).getCountryName()+ " "+ 10;
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player);
        l_Player.receiveOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Test the validation of Airlift command when the target country does not belong to player
     *
     */
    @Test
    public void testIfCommandIsfalse() {
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        l_Player1.addPlayerCard(new Cards(CardsType.AIRLIFT));
        OrderIssue.Commands = "airlift " + l_CountryList1.get(0).getCountryName() + " " + l_CountryList2.get(1).getCountryName()+" "+2;
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().validateCommand());
    }

}

