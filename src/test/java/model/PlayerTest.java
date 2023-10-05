package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Class that showcases the testcases for Model Class Player
 *
 * @author Madhav Anadkat
 *  
 */
public class PlayerTest {
    Player p = new Player();
    String d_CountryValid, d_CountryInvalid;
    int d_ArmyCountInvalid,d_ArmyCountValid;
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country c1 = new Country();
    Country c2 = new Country();
    Country c3 = new Country();
    GameMap d_GameMap = GameMap.getInstance();

    /**
     * initializes the test data
     * 
     * @throws Exception
     */
      @Before
    public void initializeTestData() throws Exception {
        c1.setCountryName("India");
        c2.setCountryName("China");
        c3.setCountryName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(c1);           
        d_CapturedCountries.add(c2);
        d_CapturedCountries.add(c3);
        p.setOccupiedCountries(d_CapturedCountries);
        p.calculateTotalReinforcementArmies(); 
        d_ArmyCountValid = 5; 
        d_ArmyCountInvalid = 13;
    }

    /**
     * Cleans up all the data from the GameMap object after execution of the test cases
     * 
     * @throws Exception
     */
      @After
    public void cleanUpTestdata() throws Exception {
        d_GameMap.getContinents().clear();
        d_GameMap.getCountries().clear();
        d_GameMap.getGamePlayers().clear();
    }

    /**
     * Test that validates whether a country IS assigned to the player 
     * 
     */
      @Test
    public void testCheckIfTheCountryIsOccupiedByThePlayer() {
        assertTrue(p.confirmIfCountryisOccupied(d_CountryValid,p));
    }
    /**
     * Test that validates whether a country IS NOT assigned to the player
     *
     */
    @Test
    public void testCheckIfCountryIsNotAssignedToPlayer() {
        assertFalse(p.confirmIfCountryisOccupied(d_CountryInvalid,p));
    }

    /**
     * Test that validates the player gets assigned 10 armies only
     * 
     */
    @Test
    public void testValidateIfTheNumberOfArmiesAllotedIsCorrect(){
        assertEquals(10,p.getAdditionalArmies());
    }


    /**
     * Test that validates Right number of troops being deployed 
     * 
     */
    @Test
    public void testValidateRighfulDeploymentOfTroops(){
      assertTrue(p.stationAdditionalArmiesFromPlayer(d_ArmyCountValid));
    } 

    /**
     * Test that validates wrongful deployment of troops by a player
     * 
     */
    @Test
    public void testValidateWrongfulDeploymentOfTroops() {
        assertFalse(p.stationAdditionalArmiesFromPlayer(d_ArmyCountInvalid));
    }

}