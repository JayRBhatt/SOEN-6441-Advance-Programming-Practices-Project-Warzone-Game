package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet; // Import this if you need to initialize a Set
/**
 * This class tests the functionalities for Continent class
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class ContinentTest {

    private Continent d_Continent;
    private final String d_ContinentName = "Asia";
    private final int d_ContinentValue = 10;
    private final Set<Country> d_Countries = new HashSet<>(); 

    public ContinentTest() {
    
    }

    @Before
    public void setUp() throws Exception {
        d_Continent = new Continent(d_ContinentName, d_ContinentValue, d_Countries);
    }
     @Test
    public void testContinentName(){
        String l_Name = d_Continent.getContinentName();
        assertEquals(d_ContinentName, l_Name);
    }
}