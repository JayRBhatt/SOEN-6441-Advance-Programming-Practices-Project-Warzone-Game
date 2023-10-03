package utils.maputils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.GameMap;
import utils.InvalidCommandException;

/**
 * Method to view the map
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class MapViewer 
{
	/**
	 * Method to read the map
	 * @param p_GameMap the GameMap object
	 * @param p_FileName the name of the file
	 * @throws InvalidCommandException in case of any invalid commands
	 */
    public static void readMap(GameMap p_GameMap, String p_FileName) throws InvalidCommandException
    {
        try
        {
            p_GameMap.ClearMap();
            File l_FileLoaded = new File("src/main/maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_FileLoaded);
            Map<String, List<String>> l_FileContent = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while(l_Buffer.ready())
            {
                String l_Read = l_Buffer.readLine();
                if (!l_Read.isEmpty()) 
                {
                    if (l_Read.contains("[") && l_Read.contains("]")) 
                    {
                        l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        l_FileContent.put(l_CurrentKey, new ArrayList<>());
                    } 
                    else 
                    {
                        l_FileContent.get(l_CurrentKey).add(l_Read);
                    }
                }
            }

            readContinentsFromFile(p_GameMap, l_FileContent.get("Continents"));
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMap, l_FileContent.get("Territories"));
            addNeighborsFromFile(p_GameMap, l_CountryNeighbors); 
        }
        catch (InvalidCommandException | IOException e) 
        {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    /**
     * Method to read the continents from file
     * @param p_GameMap the GameMap object
     * @param p_ContinentArray the list of continents
     * @throws InvalidCommandException in case of any invalid commands
     */
    public static void readContinentsFromFile(GameMap p_GameMap, List<String> p_ContinentArray) throws InvalidCommandException 
    {
        for (String l_InputString : p_ContinentArray) 
        {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                p_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * Method to create the map
     * @param p_GameMap the GameMap object
     * @param p_CountryArray the list of country array
     * @return the neighbor countries
     * @throws InvalidCommandException in case of any invalid commands
     */
    public static Map<String, List<String>> readCountriesFromFile(GameMap p_GameMap, List<String> p_CountryArray) throws InvalidCommandException
    {
        Map<String,List<String>> l_NeighborCountries = new HashMap<>();
        for(String l_CountryString : p_CountryArray)
        {
            List<String> l_CountryStringArray = Arrays.stream(l_CountryString.split(" ")).collect(Collectors.toList());
            if (l_CountryStringArray.size() >= 2) {
                p_GameMap.addCountry(l_CountryStringArray.get(0), l_CountryStringArray.get(1));
                l_NeighborCountries.put(l_CountryStringArray.get(0), l_CountryStringArray.subList(2, l_CountryStringArray.size()));
            }
        }
        return l_NeighborCountries;
    }

    /**
     * Method to add neighbor from files
     * @param p_GameMap the GameMap object
     * @param p_NeighborList list of neighbors 
     * @throws InvalidCommandException in case of any invalid commands
     */
    public static void addNeighborsFromFile(GameMap p_GameMap, Map<String, List<String>> p_NeighborList) throws InvalidCommandException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}