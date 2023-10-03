package model;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * A class that defines the structure of a country in the game 
 */
public class Country {
    
     String d_CountryId;
     String d_CountryName;
     String d_Continent;
     Player d_Player;
     int d_Armies;
     Set<Country> d_Neighbors;
     Set<String> d_NeighborsCountryName;

     public String getCountryId() 
    {
        return d_CountryId;
    }

    public void setCountryId(String p_CountryId) 
    {
        this.d_CountryId = p_CountryId;
    }

    public String getCountryName()
    {
        return d_CountryName;
    }

    public void setCountryName(String p_CountryName)
    {
        this.d_CountryName=p_CountryName;
    }

    public String getContinent() 
    {
        return d_Continent;
    }

    public void setContinent(String p_Continent) 
    {
        this.d_Continent = p_Continent;
    }

    public Player getPlayer() 
    {
        return d_Player;
    }

    public void setPlayer(Player p_Player) 
    {
        this.d_Player = p_Player;
    }

    public int getArmies() 
    {
        return d_Armies;
    }

    public void setArmies(int p_Armies) 
    {
        this.d_Armies = p_Armies;
    }

    public void deployArmies(int p_Armies) 
    {
        d_Armies += p_Armies;
    }

    public Set<Country> getNeighbors() 
    {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        return d_Neighbors;
    }

    public void setNeighbors(Country p_Neighbor) 
    {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        d_Neighbors.add(p_Neighbor);
    }

    public void setNeighborsCountryName(String p_NeighborCountryName) 
    {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        d_NeighborsCountryName.add(p_NeighborCountryName);
    }

    public Set<String> getNeighborsCountryName() 
    {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        return d_NeighborsCountryName;
    }

    public void removeNeighborsName(String p_NeighborCountryName) 
    {
        if (d_NeighborsCountryName == null) {
            d_NeighborsCountryName = new HashSet<>();
        }
        d_NeighborsCountryName.remove(p_NeighborCountryName);
    }

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
