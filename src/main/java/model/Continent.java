package model;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that defines the structure of a continent in the game 
  *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */

public class Continent {
	
    private String d_ContinentID;
    private String d_ContinentName;
    private int d_ContinentValue;
    private Set<Country> d_Countries;
    
    /**
     * Constructor to set the values of the attributes
     * @param d_ContinentID id of the continent
     * @param d_ContinentName name of the continent
     * @param d_ContinentValue value of the continent
     * @param d_Countries countries in that particular continent
     */
    public Continent(String d_ContinentID, String d_ContinentName, int d_ContinentValue, Set<Country> d_Countries) {
        this.d_ContinentID = d_ContinentID;
        this.d_ContinentName = d_ContinentName;
        this.d_ContinentValue = d_ContinentValue;
        this.d_Countries = d_Countries;
    }

    /**
     * Method to get the continent id
     * @return the continent id
     */
    public String getContinentId() {
        return d_ContinentID;
    }

    /**
     * Method to set the continent id
     * @param p_ContinentID indicates the continent id
     */
    public void setContinentId(String p_ContinentID) {
        this.d_ContinentID = p_ContinentID;
    }

    /**
     * Method to get the continent name
     * @return the continent name
     */
    public String getContinentName() {
        return d_ContinentName;
    }

    /**
     * Method to set the continent name
     * @param p_ContinentName indicates the continent name
     */
    public void setContinentName(String p_ContinentName) {
        System.out.println("methid");
        this.d_ContinentName = p_ContinentName;
        System.out.println("para" + p_ContinentName + "::::  this vali value ::->" + this.d_ContinentName);
    }

    /**
     * Method to get the continent value
     * @return the value of the continent
     */
    public int getContinentValue() {
        return d_ContinentValue;
    }

    /**
     * Method to set the continent value
     * @param p_ContinentValue indicates the continent value
     */
    public void setContinentValue(int p_ContinentValue) {
        this.d_ContinentValue = p_ContinentValue;
    }


    /**
     * Method to get the set of all countries in that continent
     * @return the set of countries
     */
    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
