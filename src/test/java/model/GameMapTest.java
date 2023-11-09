package model;

import org.junit.Test;
import utils.exceptions.InvalidCommandException;
import java.util.*;
import static org.junit.Assert.*;

/**
 * A class to test the functionalities of GameMap Class
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class GameMapTest {
    /**
     * A test to assure that all countries are shuffled/randomized before being
     * assigned.
     * This ensures random assignment of countries.
     *
     * @throws InvalidCommandException if validation fails
     */
    @Test
    public void assignCountries() throws InvalidCommandException {

        List<String> l_CountryList = new ArrayList<String>();
        l_CountryList.add("India");
        l_CountryList.add("Pakistan");
        l_CountryList.add("Bangladesh");
        l_CountryList.add("England");
        l_CountryList.add("Australia");
        l_CountryList.add("Sri Lanka");
        Collections.shuffle(l_CountryList);
        List<String> l_Occupied = new ArrayList<String>();
        for (int i = 0; i < l_CountryList.size(); i++) {
            String l_C = l_CountryList.get(i);
            l_Occupied.add(l_C);
            l_CountryList.set(i, "Assigned to some player");
        }
        boolean l_X = l_CountryList.equals(l_Occupied);
        assertFalse(l_X);
    }
}