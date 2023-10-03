package model;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that defines the structure of a continent in the game 
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
     *  To get the continent id
     * @return the continent id
     */
    public String getContinentId() {
        return d_ContinentID;
    }

    /**
     * To set the continent id
     * @param p_ContinentID indicates the continent id
     */
    public void setContinentId(String p_ContinentID) {
        this.d_ContinentID = p_ContinentID;
    }

    /**
     * To get the continent name
     * @return the continent name
     */
    public String getContinentName() {
        return d_ContinentName;
    }

    /**
     * To set the continent name
     * @param p_ContinentName indicates the continent name
     */
    public void setContinentName(String p_ContinentName) {
        System.out.println("methid");
        this.d_ContinentName = p_ContinentName;
        System.out.println("para" + p_ContinentName + "::::  this vali value ::->" + this.d_ContinentName);
    }

    /**
     * To get the continent value
     * @return the value of the continent
     */
    public int getContinentValue() {
        return d_ContinentValue;
    }

    /**
     * To set the continent value
     * @param p_ContinentValue indicates the continent value
     */
    public void setContinentValue(int p_ContinentValue) {
        this.d_ContinentValue = p_ContinentValue;
    }


    /**
     * To get the set of all countries in that continent
     * @return the set of countries
     */
    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
