package model.order;

import services.OrderIssue;
import model.Country;
import model.GameMap;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.orders.Order;
import model.orders.OrderCreator;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Deploy Order
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class DeployOrderTest {
    GameMap d_GameMap;
    List<Country> d_CountriesPlayer1;
    List<Country> d_CountriesPlayer2;
    Player d_Player;

    /**
     * Setup for the test cases
     * @throws Exception exception during setup
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
        d_GameMap.assignCountries();
        for (Player l_Player : d_GameMap.getGamePlayers().values()) {
            l_Player.calculateTotalReinforcementArmies(d_GameMap);
        }
        d_CountriesPlayer1 = d_GameMap.getGamePlayer("Player1").getOccupiedCountries();
        d_Player = d_GameMap.getGamePlayer("Player1");
    }

    /**
     * Teardown after the test cases
     *
     * @throws Exception exception during teardown
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.ClearMap();
    }

    /**
     * test case to execute the command
     */
    @Test
    public void execute() {
        OrderIssue.Commands = "deploy " + d_CountriesPlayer1.get(0).getCountryName() + " " + d_Player.getAdditionalArmies();
        Order l_Order1 = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), d_Player);
        d_Player.receiveOrder(l_Order1);
        assertTrue(d_Player.nextOrder().execute());
    }

    /**
     * Check if the command is valid
     */
    @Test
    public void checkIfTheCommandIsValid() {
        OrderIssue.Commands = "deploy " + d_CountriesPlayer1.get(0).getCountryName() + " " + d_Player.getAdditionalArmies();
        Order l_Order = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), d_Player);
        d_Player.receiveOrder(l_Order);
        assertTrue(d_Player.nextOrder().validateCommand());
    }

    /**
     * Check if countries in command are valid
     */
    @Test
    public void checkIfTheCountriesAreValid() {
        d_CountriesPlayer2 = d_GameMap.getGamePlayer("Player2").getOccupiedCountries();
        OrderIssue.Commands = "deploy " + d_CountriesPlayer2.get(0).getCountryName() + " " + d_Player.getAdditionalArmies();
        Order l_Order = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), d_Player);
        d_Player.receiveOrder(l_Order);
        assertFalse(d_Player.nextOrder().validateCommand());
    }

    /**
     * Check if armies are valid in command
     */
    @Test
    public void checkIfTheArmiesAreValid() {
        OrderIssue.Commands = "deploy " + d_CountriesPlayer1.get(0).getCountryName() + " 10";
        Order l_Order = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), d_Player);
        d_Player.receiveOrder(l_Order);
        assertFalse(d_Player.nextOrder().validateCommand());
    }
}
