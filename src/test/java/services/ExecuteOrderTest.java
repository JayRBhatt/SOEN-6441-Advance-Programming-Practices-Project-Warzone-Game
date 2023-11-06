
package services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.Country;
import model.GameMap;
import model.GamePhase;
import model.Player;
import model.orders.Order;
import model.orders.OrderDetails;

/**
 * Test class that tests Execute order phase implementations
 * 
 * @author Jay Bhatt
 */
public class ExecuteOrderTest {

    /**
     * gameMap instance
     */
    GameMap d_GameMap;
    /**
     * Execute Order instance
     */
    private ExecuteOrder d_ExecuteOrder;
    /**
     * List if countries for player
     */
    List<Country> d_CountriesPlayer1;
    /**
     * Player variable
     */
    Player d_Player;

    /**
     * Setup for test case
     *
     * @throws Exception exception
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
        d_ExecuteOrder = new ExecuteOrder();
        d_ExecuteOrder.d_GamePhase = GamePhase.ExecuteOrder;
    }

    /**
     * Clear the setup after the test case
     *
     * @throws Exception exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.ClearMap();
    }

    /**
     * Check if the player won
     */
    @Test
    public void checkIfPlayerWon() {
        HashMap<String, Country> l_ListOfAllCountries = d_GameMap.getCountries();
        d_Player = d_GameMap.getGamePlayer("Player1");
        for (Country l_Country : l_ListOfAllCountries.values()) {
            if (!d_Player.isCaptured(l_Country)) {
                d_Player.getOccupiedCountries().add((l_Country));
                l_Country.setPlayer(d_Player);
            }
        }
        GamePhase l_Output = d_ExecuteOrder.checkPlayerWon(GamePhase.ExecuteOrder);
        assertEquals(GamePhase.ExitGame, l_Output);
    }
}
