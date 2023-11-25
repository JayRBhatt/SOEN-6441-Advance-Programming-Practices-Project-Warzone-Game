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
 * @version 1.0.0
 */

public class Continent {
    private String d_ContinentName;
    private int d_ContinentValue;
    private int d_BonusArmies;
    private Set<Country> d_Countries;

    /**
     * Parameterised Constructor that initializes the variables
     * 
     * @param d_ContinentName The Name of a continent
     * @param d_ContinentValue the value of the continent
     * @param d_Countries The set of the countries which continent possess
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
     * @param p_ContinentName the continent whose name is to set
     */
    public void setContinentName(String p_ContinentName) {

        this.d_ContinentName = p_ContinentName;
    }

    /**
     * Returns the value of the continent given by the user(Number of Awarded armies
     * the User wants to be associated with the continent)
     * 
     * @return d_ContinentValue
     */

    public int getContinentValue() {
        return d_ContinentValue;
    }

    /**
     * Sets the value of the Continent
     * 
     * @param p_ContinentValue the continent whose value is to be set
     */
    public void setContinentValue(int p_ContinentValue) {
        this.d_ContinentValue = p_ContinentValue;
    }
     /**
     * Get the Awarded armies
     *
     * @return d_AwardArmies  The Awarded armies assigned to the continent
     */
    public int getBonusArmies() {
        return d_BonusArmies;
    }

    /**
     * Set the Awarded armies for the continent
     *
     * @param p_AwardArmies Awarded armies
     */
    public void setBonusArmies(int p_AwardArmies) {
        this.d_BonusArmies = p_AwardArmies;
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
