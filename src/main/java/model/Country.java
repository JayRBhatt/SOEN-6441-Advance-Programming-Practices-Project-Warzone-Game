package model;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * A class that defines the structure of a country in the game 
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class Country {
    
     String d_CountryId;
     String d_CountryName;
     String d_Continent;
     Player d_Player;
     int d_Armies;
     Set<Country> d_Neighbors;
     Set<String> d_NeighborsCountryName;

    /**
     * Method to get the country id
     * @return country id
     */
     public String getCountryId() 
    {
        return d_CountryId;
    }

     /**
      * Method to set the country id
      * @param p_CountryId country id
      */
    public void setCountryId(String p_CountryId) 
    {
        this.d_CountryId = p_CountryId;
    }

    /**
     * Method to get the country name
     * @return the country name
     */
    public String getCountryName()
    {
        return d_CountryName;
    }

    /**
     * Method to set the country name
     * @param p_CountryName country name
     */
    public void setCountryName(String p_CountryName)
    {
        this.d_CountryName=p_CountryName;
    }

    /**
     * Method to get the continent
     * @return the continent to which the country belongs
     */
    public String getContinent() 
    {
        return d_Continent;
    }

    /**
     * Method to set the continent name
     * @param p_Continent continent name
     */
    public void setContinent(String p_Continent) 
    {
        this.d_Continent = p_Continent;
    }

    /**
     * Method to get the player instance
     * @return player instance
     */
    public Player getPlayer() 
    {
        return d_Player;
    }

    /**
     * Method to set the player instance
     * @param p_Player the Player instance
     */
    public void setPlayer(Player p_Player) 
    {
        this.d_Player = p_Player;
    }

    /**
     * Method to get the armies for the country
     * @return the number of armies in the country
     */
    public int getArmies() 
    {
        return d_Armies;
    }

    /**
     * Method to set the armies in the country
     * @param p_Armies Number of armies
     */
    public void setArmies(int p_Armies) 
    {
        this.d_Armies = p_Armies;
    }

    /**
     * Method to deploy armies for each player
     * @param p_Armies Number of armies to be deployed
     */
    public void deployArmies(int p_Armies) 
    {
        d_Armies += p_Armies;
    }

    /**
     * Method to get the neighbors of the country
     * @return the set of neighbor countries
     */
    public Set<Country> getNeighbors() 
    {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        return d_Neighbors;
    }

    /**
     * Method to set the neighbors of the country
     * @param p_Neighbor Neighbors of the country
     */
    public void setNeighbors(Country p_Neighbor) 
    {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        d_Neighbors.add(p_Neighbor);
    }

    /**
     *  Method to set the names of neighboring countries
     * @param p_NeighborCountryName Names of neighboring countries
     */
    public void setNeighborsCountryName(String p_NeighborCountryName) 
    {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        d_NeighborsCountryName.add(p_NeighborCountryName);
    }

    /**
     * Method to get the names of neighboring countries
     * @return set of neighboring country names
     */
    public Set<String> getNeighborsCountryName() 
    {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        return d_NeighborsCountryName;
    }

    /**
     * Methods to remove name of a neighboring country
     * @param p_NeighborCountryName Name of a neighboring country to be removed
     */
    public void removeNeighborsName(String p_NeighborCountryName) 
    {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        d_NeighborsCountryName.remove(p_NeighborCountryName);
    }

    /**
     * Method to store the names of neighboring countries
     * @param p_Neighbors Set of neighboring countries
     * @return the names of the countries as a string
     */
    public String createNeighborList(Set<Country> p_Neighbors)
    {
    StringJoiner result = new StringJoiner("-");
    for (Country l_Neighbor : p_Neighbors) 
    {
        result.add(l_Neighbor.getCountryName());
    }
    return result.toString();
    }
    // String result = "";
    // for (Country l_Neighbor : p_Neighbors ){
    //     result += l_Neighbor.getName() + "-";
    // }
    // return result.length() > 0 ? result.substring(0, result.length() - 1): "";

}
