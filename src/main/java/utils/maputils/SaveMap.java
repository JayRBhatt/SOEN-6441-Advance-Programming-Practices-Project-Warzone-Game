package utils.maputils;

import java.io.PrintWriter;
import java.util.Set;

import model.Continent;
import model.Country;
import model.GameMap;

public class SaveMap {

    private GameMap gameMap;

    public String createANeighborList(Set<Country> p_Neighbors) {
        String l_result = "";
        for (Country l_Neighbor : p_Neighbors) {
            l_result += l_Neighbor.getCountryName() + " ";
        }
        return l_result.length() > 0 ? l_result.substring(0, l_result.length() - 1) : "";
    }

    public boolean saveMapIntoFile(GameMap p_GameMap, String name) {
        String mapData = "[Map]\n\n[Continents]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            mapData += continent.getContinentName() + " " + continent.getContinentValue();
            mapData += "\n";
        }

        mapData += "[Territories]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            for (Country country : p_GameMap.getCountries().values()) {
                mapData += country.getCountryName() + " " + country.getContinent() + " " + createANeighborList(country.getNeighbors()) + "\n";
            }

            PrintWriter writeData = null;
            try {
                writeData = new PrintWriter("src/main/maps/" + name + ".map");
                writeData.println(mapData);
                return true;
            } catch (Exception ex) {
                return false;
            } finally {
                if (writeData != null) {
                    writeData.close();
                }
            }
        }
        return true;
    }
}
