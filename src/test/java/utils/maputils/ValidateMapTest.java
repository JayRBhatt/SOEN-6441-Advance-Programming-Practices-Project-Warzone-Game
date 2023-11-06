package utils.maputils;

import model.GameMap;
import utils.exceptions.InvalidCommandException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  A class to test all the functionalities in Map Validation
 *  
 *  @author Jay Bhatt
 */
public class ValidateMapTest {
    GameMap d_GameMap;

    /**
     * Initial Setup to be used before all test cases
     *
     * @throws Exception if execution fails
     */
    @Before
    public void initializeTestData() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "5");
        d_GameMap.addContinent("Africa", "5");
        d_GameMap.addContinent("Australia", "5");
        d_GameMap.addContinent("NorthAmerica", "5");
        d_GameMap.addContinent("SouthAmerica", "5");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("Ghana", "Africa");
        d_GameMap.addCountry("AustraliaCountry", "Australia");
        d_GameMap.addCountry("USA", "NorthAmerica");
        d_GameMap.addCountry("Brazil", "SouthAmerica");
        d_GameMap.addNeighbor("India", "Ghana");
        d_GameMap.addNeighbor("Ghana", "USA");
        d_GameMap.addNeighbor("USA", "AustraliaCountry");
        d_GameMap.addNeighbor("AustraliaCountry", "Brazil");
        d_GameMap.addNeighbor("Brazil", "India");
    }

    /**
     * This method will be executed after the test
     *
     * @throws Exception when execution fails
     */
    @After
    public void cleanUpTestdata() throws Exception {
        d_GameMap.getContinents().clear();
        d_GameMap.getCountries().clear();
        d_GameMap.getGamePlayers().clear();
    }

    /**
     * TestCase that checks whether the country is empty or not
     *
     * @throws InvalidCommandException if validation fails
     */
    @Test
    public void testIfContinentIsNotEmpty() throws InvalidCommandException {
        d_GameMap.addContinent("Europe", "5");
        assertEquals(true, ValidateMap.checkContinentIsEmpty(d_GameMap));
    }

    
    /**
     * Test case that validates whether the continnet is a connected subgraph
     *
     * @throws InvalidCommandException if validation fails
     */
    @Test
    public void testIfContinentIsConnectedSubgraph() throws InvalidCommandException {
        assertEquals(true, ValidateMap.checkIfContinentIsConnected(d_GameMap));
    }

    /**
     * test case that validates the Game map is a connected graph
     */
    @Test
    public void testIfGameMapIsConnected() {
        assertEquals(true, ValidateMap.checkIfMapIsConnected(d_GameMap));
    }
    @Test
    public void CheckIfMapIsInvalid() throws InvalidCommandException {
        d_GameMap.removeNeighbor("Newyork", "Pak");
        assertFalse(ValidateMap.checkIfMapIsConnected(d_GameMap));
    }
}
