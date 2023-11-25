package utils.maputils;

import java.io.PrintWriter;
import java.util.Set;

import model.Continent;
import model.Country;
import model.GameMap;

/**
 * A class which provides the logic for saving the map
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class SaveMap {

  /**
   * A method which creates a string of neighboring countries
   * 
   * @param p_Neighbors a set of neigbour countries
   * @return a string of names of neighboring countries
   */  
    
    public String createANeighborList(Set<Country> p_Neighbors) {
        String l_result = "";
        for (Country l_Neighbor : p_Neighbors) {
            l_result += l_Neighbor.getCountryName() + " ";
        }
        return l_result.length() > 0 ? l_result.substring(0, l_result.length() - 1) : "";
    }
   
    /**
     * A method which saves a game map into a file with .map extension
     * @param p_GameMap the game map
     * @param name file name
     * @return true if the map is saved or else returns false
     */
    public boolean saveMapIntoFile(GameMap p_GameMap, String name) {
        String l_MapData = "[Map]\nauthor=Anonymous\n[Continents]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            l_MapData += continent.getContinentName() + " " + continent.getContinentValue();
            l_MapData += "\n";
        }

        l_MapData += "[Territories]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            for (Country country : p_GameMap.getCountries().values()) {
                l_MapData += country.getCountryName() + " " + country.getContinent() + " "
                        + createANeighborList(country.getNeighbors()) + "\n";
            }

            PrintWriter l_writeData = null;
            try {
                l_writeData = new PrintWriter("src/main/maps/" + name + ".map");
                l_writeData.println(l_MapData);
                return true;
            } catch (Exception ex) {
                return false;
            } finally {
                if (l_writeData != null) {
                    l_writeData.close();
                }
            }
        }
        return true;
    }
}
