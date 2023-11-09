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
import utils.exceptions.InvalidCommandException;

/**
 * A class which provides the logic for reading the pre-existing maps in the file
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */


public class MapViewer 
{
   /** 
    * A method which reads and processes the gamemap which was earlier created and saved by the player 
    @param p_GameMap a map which is to be checked
    @param p_FileName a file in which that map is stored
    @throws InvalidCommandException when validation fails
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
    * A method which reads the continents from the file and add them to gamemap
      @param p_GameMap map where continent is to be added
      @param p_ContinentArray a value list of continents
      @throws InvalidCommandException when validation fails
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
   * A method which reads the country from the file and add them to gamemap
     @param p_GameMap where country is to be added
     @param p_CountryArray a value list of countries
     @throws InvalidCommandException when validation fails
     @return neighbour countries
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
  * A method which add neighboring countries into the gamemap
    @param p_GameMap where neighboring countries are to be added
    @param p_NeighborList a value list for neighboring countries
    @throws InvalidCommandException when validation fails
  */

    public static void addNeighborsFromFile(GameMap p_GameMap, Map<String, List<String>> p_NeighborList) throws InvalidCommandException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}