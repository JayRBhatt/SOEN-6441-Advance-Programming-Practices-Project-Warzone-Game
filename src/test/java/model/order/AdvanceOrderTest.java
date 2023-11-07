package model.order;

import services.OrderIssue;
import model.Country;
import model.DefaultAttackLogic;
import model.GameMap;
import model.GameCalculation;
import model.Player;
import model.orders.Order;
import model.orders.OrderCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases for Advance Order
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class AdvanceOrderTest {
    /**
     * Game map object
     */
    GameMap d_GameMap;

    /**
     * Basic setup before each test case runs
     *
     * @throws Exception if an exception occurs
     */
    @Before
    public void setUp() throws Exception {
        /**
         * Singleton game map instance
         */
        d_GameMap = GameMap.getInstance();

        /**
         * Singleton game settings instance
         */
        GameCalculation l_GameSettings = GameCalculation.getInstance();
        l_GameSettings.setStrategy(new DefaultAttackLogic());

        //Add Continent
        d_GameMap.addContinent("Asia", "4");
        //Add Countries
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addCountry("Vietnam", "Asia");
        //Add Neighbors
        Country l_Country1 = d_GameMap.getCountry("India");
        Country l_Country2 = d_GameMap.getCountry("China");
        Country l_Country3 = d_GameMap.getCountry("Vietnam");
        l_Country1.setNeighbors(l_Country2);
        l_Country2.setNeighbors(l_Country3);
        l_Country3.setNeighbors(l_Country1);
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
        //Add Countries to players
        d_GameMap.getGamePlayer("Player1").getOccupiedCountries().add(d_GameMap.getCountry("India"));
        d_GameMap.getGamePlayer("Player1").getOccupiedCountries().add(d_GameMap.getCountry("Vietnam"));
        d_GameMap.getGamePlayer("Player2").getOccupiedCountries().add(d_GameMap.getCountry("China"));
        //Add Reinforcements to players
        d_GameMap.getGamePlayer("Player1").setAdditionalArmies(10);
        d_GameMap.getGamePlayer("Player2").setAdditionalArmies(10);
    }

    /**
     * Tear down the setup after each test runs
     *
     * @throws Exception if an exception occurs
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.ClearMap();
    }

    /**
     * Test to check if advance execution fails on no troops deployed
     */
    @Test
    public void testExecutionFailOnNoTroopsDeployed() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();

        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(0).getCountryName() + " " + l_CountriesPlayer2.get(0).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution fails, if destination is not neighbor
     */
    @Test
    public void testExecutionFailOnNotANeighbor() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        l_CountriesPlayer1.get(0).setArmies(6);
        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(0).getCountryName() + " " + l_CountriesPlayer1.get(1).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed.
     */
    @Test
    public void testExecutionSuccessOnNeighborAndTroopsDeployedInOwnCountry() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        l_CountriesPlayer1.get(1).setArmies(6);
        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(1).getCountryName() + " " + l_CountriesPlayer1.get(0).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed and no king exists. Also proper armies distribution
     * after successful attack.
     */
    @Test
    public void testAdvanceSuccessOnNeighborIfNoKingExists() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);

        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(0).getCountryName() + " " + l_CountriesPlayer2.get(0).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
        assertEquals("Armies Depleted from source and deployed to target",5,d_GameMap.getCountry("China").getArmies());
        assertTrue(l_CountriesPlayer1.get(0).getArmies() > 0);
    }

    /**
     * Test to check if advance execution succeeds, if destination is neighbor
     * and troops deployed and king exists.
     */
    @Test
    public void testAttackSuccessOnNeighborWithKing() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        Player l_Player2 = d_GameMap.getGamePlayer("Player2");
        // Set Players to countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);
        l_CountriesPlayer2.get(0).setArmies(6);

        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(0).getCountryName() + " " + l_CountriesPlayer2.get(0).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if attack execution succeeds and ownership changed, if destination is neighbor
     * and troops deployed and king exists.
     */
    @Test
    public void testOwnershipChangeOnAdvanceSuccess() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        Player l_Player2 = d_GameMap.getGamePlayer("Player2");

        //Set Players to Countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);

        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(0).getCountryName() + " " + l_CountriesPlayer2.get(0).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
        assertEquals("Ownership changed",l_Player1,d_GameMap.getCountry("China").getPlayer());
    }

    /**
     * Test to check if advance command gets skipped if on neutral player
     */
    @Test
    public void testExecutionSkipsOnNeutralPlayer() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        Player l_Player2 = d_GameMap.getGamePlayer("Player2");
        // Set Players to countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);
        l_CountriesPlayer2.get(0).setArmies(6);
        //Set Neutral Players
        l_Player1.getNeutralPlayers().add(l_Player2);
        l_Player2.getNeutralPlayers().add(l_Player1);

        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(0).getCountryName() + " " + l_CountriesPlayer2.get(0).getCountryName() + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    /**
     * Test to check if advance execution fails and gets skipped if invalid command
     */
    @Test
    public void testExecutionFailOnInvalidCommand() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        Player l_Player1 = d_GameMap.getGamePlayer("Player1");
        l_CountriesPlayer1.get(1).setArmies(6);
        OrderIssue.Commands = "advance " + l_CountriesPlayer1.get(1).getCountryName() + " " + "Thailand" + " " + (l_Player1.getAdditionalArmies() - 5);
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), l_Player1);
        l_Player1.receiveOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

}