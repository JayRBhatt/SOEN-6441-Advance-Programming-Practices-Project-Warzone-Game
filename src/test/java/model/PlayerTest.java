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
    int d_ArmyNumberInvalid,d_ArmyNumberValid;
    int d_AdditionalArmies;
    List<Country> d_OccupiedCountries = new ArrayList<>();
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
        d_OccupiedCountries.add(c1);           
        d_OccupiedCountries.add(c2);
        d_OccupiedCountries.add(c3);
        p.setOccupiedCountries(d_OccupiedCountries);
        d_AdditionalArmies = 10;
        p.setAdditionalArmies(d_AdditionalArmies);
        d_ArmyNumberValid = 5; 
        d_ArmyNumberInvalid = 13;
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
      assertTrue(p.stationAdditionalArmiesFromPlayer(d_ArmyNumberValid));
    } 

    /**
     * Test that validates wrongful deployment of troops by a player
     * 
     */
    @Test
    public void testValidateWrongfulDeploymentOfTroops() {
        assertFalse(p.stationAdditionalArmiesFromPlayer(d_ArmyNumberInvalid));
    }

}