package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Class that provides every method that is required for any properties of
 * Country
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */

public class Country implements Serializable {

    String d_CountryId;
    String d_CountryName;
    String d_Continent;
    Player d_Player;
    int d_Armies;
    Set<Country> d_Neighbors;
    Set<String> d_NeighborsCountryName;

    /**
     * Returns Name of the Country
     * 
     * @return d_CountryName
     */

    public String getCountryName() {
        return d_CountryName;
    }

    /**
     * Sets the name of the Country
     * 
     * @param p_CountryName country whose name is to be set
     */
    public void setCountryName(String p_CountryName) {
        this.d_CountryName = p_CountryName;
    }

    /**
     * Gets the Continent name the country resides in
     * 
     * @return d_Continent
     */
    public String getContinent() {
        return d_Continent;
    }

    /**
     * Method that checks if the country is a neighbour
     *
     * @param p_Neighbor the neighboring country
     * @return the neighbour list
     */
    public boolean isNeighbor(Country p_Neighbor) {
        return this.getNeighbors().contains(p_Neighbor);
    }

    /**
     * Sets the Continent Name into which the country is to be residing
     * 
     * @param p_Continent continent whose name is to be set
     */
    public void setContinent(String p_Continent) {
        this.d_Continent = p_Continent;
    }

    /**
     * Gets the Player's Object associated with the country
     * 
     * @return d_Player
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * Sets the Player to associate with the country
     * 
     * @param p_Player player which is to be set
     */
    public void setPlayer(Player p_Player) {
        this.d_Player = p_Player;
    }

    /**
     * Gets the Number of Armies Country possess
     * 
     * @return d_Armies
     */
    public int getArmies() {
        return d_Armies;
    }

    /**
     * Sets the Number of Army the Country will possess
     * 
     * @param p_Armies the army which is to be set to country
     */
    public void setArmies(int p_Armies) {
        this.d_Armies = p_Armies;
    }

    /**
     * Just adds the armies to the number already deployed
     * 
     * @param p_Armies the number of army which to be deployed
     */
    public void deployArmies(int p_Armies) {
        d_Armies += p_Armies;
    }

    /**
     * This method depletes the number of armies
     *
     * @param p_armies the number of armies
     */
    public void depleteArmies(int p_armies) {
        d_Armies -= p_armies;
    }

    /**
     * Returns the Set of neighbors of the country
     * 
     * @return d_Neighbors
     */
    public Set<Country> getNeighbors() {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        return d_Neighbors;
    }

    /**
     * Method that checks if the country is a neighbour
     *
     * @param p_Neighbor the neighboring country
     * @return the neighbour list
     */

    /**
     * Sets the Neighbors to the countries
     * 
     * @param p_Neighbor the country which is to be added as neighbour
     */
    public void setNeighbors(Country p_Neighbor) {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        d_Neighbors.add(p_Neighbor);
    }

    /**
     * Sets the Name of the Neighbors to the Country
     * 
     * @param p_NeighborCountryName the neighbour country whose name is to be set
     */
    public void setNeighborsCountryName(String p_NeighborCountryName) {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        d_NeighborsCountryName.add(p_NeighborCountryName);
    }

    /**
     * Returns the set of name of the neighbors of the Country
     * 
     * @return d_NeighborsCountryName
     */
    public Set<String> getNeighborsCountryName() {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        return d_NeighborsCountryName;
    }

    /**
     * Removes the name of the neighbor from the set
     * 
     * @param p_NeighborCountryName the country which is to be removed as a
     *                              neighbour
     */
    public void removeNeighborsName(String p_NeighborCountryName) {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        d_NeighborsCountryName.remove(p_NeighborCountryName);
    }

    /**
     * It creates a concatenated string of country names from a set of neighbors
     * which is
     * separated by hyphens ("-").
     * 
     * @param p_Neighbors the set of neighbour countries from which list is to be
     *                    created
     * @return string containing the names of neighboring countries which are
     *         separated by hyphens.
     */
    public String createNeighborList(Set<Country> p_Neighbors) {
        StringJoiner result = new StringJoiner("-");
        for (Country l_Neighbor : p_Neighbors) {
            result.add(l_Neighbor.getCountryName());
        }
        return result.toString();
    }
}
