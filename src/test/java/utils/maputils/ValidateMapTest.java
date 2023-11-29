package utils.maputils;

import model.GameMap;
import utils.exceptions.InvalidCommandException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test all the functionalities in Map Validation
 *
 */
public class ValidateMapTest {
    /**
     * d_gameMap instance
     */
    private GameMap d_gameMap;

    /**
     * Initial Setup to be used before all test cases
     *
     * @throws Exception if execution fails
     */
    @Before
    public void setUp() throws Exception {
        d_gameMap = d_gameMap.getInstance();
        d_gameMap.addContinent("Asia", "5");
        d_gameMap.addContinent("US", "5");
        d_gameMap.addContinent("Africa", "5");
        d_gameMap.addContinent("Antartica", "5");
        d_gameMap.addContinent("Aus", "5");

        d_gameMap.addCountry("Pak", "Africa");
        d_gameMap.addCountry("India", "Asia");
        d_gameMap.addCountry("Newyork", "US");
        d_gameMap.addCountry("Penguin", "Antartica");
        d_gameMap.addCountry("Melbourne", "Aus");

        d_gameMap.addNeighbor("Pak", "India");
        d_gameMap.addNeighbor("India", "Pak");
        d_gameMap.addNeighbor("Pak", "Newyork");
        d_gameMap.addNeighbor("Newyork", "Pak");
        d_gameMap.addNeighbor("Penguin", "India");
        d_gameMap.addNeighbor("India", "Penguin");
        d_gameMap.addNeighbor("Penguin", "Melbourne");
        d_gameMap.addNeighbor("Melbourne", "Penguin");
    }

    /**
     * This method will be executed at the end of the test
     *
     * @throws Exception when execution fails
     */
    @After
    public void tearDown() throws Exception {
        d_gameMap.getContinents().clear();
        d_gameMap.getCountries().clear();
        d_gameMap.getGamePlayers().clear();
    }

    /**
     * This method tests if Continent is Empty
     *
     * @throws InvalidCommandException if validation fails
     */
    @Test
    public void testCheckIfContinentIsEmpty() throws InvalidCommandException {
        d_gameMap.addContinent("Europe", "5");
        assertTrue(ValidateMap.checkContinentIsEmpty(d_gameMap));
    }

    /**
     * This method tests if duplicate Neighbors exist
     *
     * @throws InvalidCommandException if validation fails
     */

    /**
     * This method tests if continent subgraph is connected
     *
     */
    @Test
    public void testCheckIfContinentIsConnected() {
        assertTrue(ValidateMap.checkIfContinentIsConnected(d_gameMap));
    }

    /**
     * This method tests if the whole graph is connected
     */
    @Test
    public void testCheckIfMapIsConnected() {
        assertTrue(ValidateMap.checkIfMapIsConnected(d_gameMap));
    }

    /**
     * Checks that the map is invalid
     * 
     * @throws InvalidCommandException if exception occurs
     */
    @Test
    public void testCheckIfMapIsInvalid() throws InvalidCommandException {
        d_gameMap.removeNeighbor("Newyork", "Pak");
        assertTrue(ValidateMap.checkIfMapIsConnected(d_gameMap));
    }
}
