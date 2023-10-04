package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player p = new Player();
    String d_CountryValid, d_CountryInvalid;
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country c1 = new Country();
    Country c2 = new Country();
    Country c3 = new Country();
    GameMap d_GameMap = GameMap.getInstance();

      @Before
    public void setUp() throws Exception {
        c1.setCountryName("India");
        c2.setCountryName("China");
        c3.setCountryName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(c1);           
        d_CapturedCountries.add(c2);
        d_CapturedCountries.add(c3);
        p.setOccupiedCountries(d_CapturedCountries);
    }
      @After
    public void tearDown() throws Exception {
        d_GameMap.getContinents().clear();
        d_GameMap.getCountries().clear();
        d_GameMap.getGamePlayers().clear();
    }
      @Test
    public void testValidCheckIfCountryExists() {
        assertTrue(p.confirmIfCountryisOccupied(d_CountryValid,p));
    }
    /**
     * This is the test method to check if Country does not exist
     *
     */
    @Test
    public void testInvalidCheckIfCountryExists() {
        assertFalse(p.confirmIfCountryisOccupied(d_CountryInvalid,p));
    }
}
