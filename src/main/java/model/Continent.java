package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that provides every method that is required for any properties of
 * Continent
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */

public class Continent {
    private String d_ContinentName;
    private int d_ContinentValue;
    private Set<Country> d_Countries;

    /**
     * Parameterised Constructor that initializes the variables
     * 
     * @param d_ContinentName
     * @param d_ContinentValue
     * @param d_Countries
     */
    public Continent(String d_ContinentName, int d_ContinentValue, Set<Country> d_Countries) {

        this.d_ContinentName = d_ContinentName;
        this.d_ContinentValue = d_ContinentValue;
        this.d_Countries = d_Countries;
    }

    /**
     * Returns the name of the continent
     * 
     * @return d_ContinentName
     */

    public String getContinentName() {
        return d_ContinentName;
    }

    /**
     * sets the name of the continent
     * 
     * @param p_ContinentName
     */
    public void setContinentName(String p_ContinentName) {

        this.d_ContinentName = p_ContinentName;
    }

    /**
     * Returns the value of the continent given by the user(Number of Awarded armies
     * the User wants to be associated with the continent)
     * 
     * @return
     */

    public int getContinentValue() {
        return d_ContinentValue;
    }

    /**
     * Sets the value of the Continent
     * 
     * @param p_ContinentValue
     */
    public void setContinentValue(int p_ContinentValue) {
        this.d_ContinentValue = p_ContinentValue;
    }

    /**
     * Gets the Set of Countries it possess
     * 
     * @return d_Countries if it already has some, or else creates a new HashSet
     */
    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
