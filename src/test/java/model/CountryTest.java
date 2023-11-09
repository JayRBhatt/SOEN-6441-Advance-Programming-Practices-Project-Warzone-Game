package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the functionalities for Country class
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class CountryTest extends Country{
    String d_Name;
    Country d_Country = new Country();

    /**
     * This method initializes each value before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {
        d_Name = "Canada";
        d_Country.setCountryName(d_Name);

    }

    /**
     * This is the test method to test the Country Name
     *
     */

    @Test
    public void testCountryName(){
        String l_Name = d_Country.getCountryName();
        assertEquals(d_Name,l_Name);
    }

}

