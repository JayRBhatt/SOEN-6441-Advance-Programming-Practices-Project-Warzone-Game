package utils.maputils;

import model.Continent;
import model.Country;
import model.GameMap;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;
import java.io.*;
import java.util.*;

/**
 * Domination class
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class DominationMap {
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * current line
     */
    private String d_CurrentLine;
    /**
     * buffer for reading
     */
    private BufferedReader d_Buffer;
    /**
     * continent list
     */
    private List<String> Continents = new ArrayList<>();
    /**
     * country hashmap
     */
    private HashMap<String, String> Country = new HashMap<>();

    /**
     * This function reads the file and places the contents of the file
     * in a Hash Map
     *
     * @param p_FileName the map file name
     * @param p_GameMap  the game map
     * @throws InvalidCommandException when validation fails
     */
    public void readMap(GameMap p_GameMap, String p_FileName) throws InvalidCommandException {
        d_Logger.clear();
        d_Logger.logAction("Domination map is loaded \n");
        try {
            p_GameMap.ClearMap();
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();

            d_Buffer = new BufferedReader(l_FileReader);
            while ((d_CurrentLine = d_Buffer.readLine()) != null) {
                if (d_CurrentLine.contains("[continents]")) {
                    readContinentsFromFile(p_GameMap);
                }
                if (d_CurrentLine.contains("[countries]")) {
                    readCountriesFromFile(p_GameMap);
                }
                if (d_CurrentLine.contains("[borders]")) {
                    addNeighborsFromFile(p_GameMap);
                }

            }
        } catch (InvalidCommandException | IOException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    /**
     * This function reads the Continents from the file
     *
     * @param p_GameMap        the game map
     * @throws InvalidCommandException when validation fails
     * @throws IOException file IO exception
     */
    public void readContinentsFromFile(GameMap p_GameMap) throws InvalidCommandException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_ContinentDetails = d_CurrentLine.split(" ");
            p_GameMap.addContinent(l_ContinentDetails[0], l_ContinentDetails[1]);
            Continents.add(l_ContinentDetails[0]);
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_GameMap      the game map
     * @throws InvalidCommandException when validation fails
     * @throws IOException file IO exception
     */

    public void readCountriesFromFile(GameMap p_GameMap) throws InvalidCommandException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_CountryDetails = d_CurrentLine.split(" ");
            p_GameMap.addCountry(l_CountryDetails[1], Continents.get((Integer.parseInt(l_CountryDetails[2]) - 1)));
            Country.put(l_CountryDetails[0], l_CountryDetails[1]);
        }
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_GameMap      the game map
     * @throws InvalidCommandException when validation fails
     * @throws IOException file IO exception
     */

    public void addNeighborsFromFile(GameMap p_GameMap) throws InvalidCommandException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_NeighbourDetails = d_CurrentLine.split(" ");
            for (int i = 1; i < l_NeighbourDetails.length; i++) {
                p_GameMap.addNeighbor(Country.get(l_NeighbourDetails[0]), Country.get(l_NeighbourDetails[i]));
            }
        }
    }

    /**
     * function to save the map
     *
     * @param p_GameMap Gamemap instance
     * @param p_FileName file name to be save
     * @return the saved map
     * @throws IOException exception for file save
     */
    public boolean saveMap(GameMap p_GameMap, String p_FileName) throws IOException {
        String mapTitle = "yura.net Risk 1.0.9.2";
        String currentPath = System.getProperty("user.dir") + "\\src/main/maps/";
        String mapPath = currentPath + p_FileName + ".map";
        
        BufferedWriter bwFile = new BufferedWriter(new FileWriter(mapPath));
        StringBuilder mapContent = new StringBuilder();
    
        mapContent.append(";Map ").append(p_FileName).append(".map").append("\r")
                  .append("name ").append(p_FileName).append(".map").append(" Map").append("\r")
                  .append(mapTitle).append("\r")
                  .append("\r\n[continents]\r\n");
    
        HashMap<Integer, String> continentMap = createContinentList(p_GameMap);
    
        for (Continent continent : p_GameMap.getContinents().values()) {
            mapContent.append(continent.getContinentName()).append(" ").append(continent.getBonusArmies()).append(" 00000\r\n");
        }
    
        mapContent.append("\r\n[countries]\r\n");
        StringBuilder borders = new StringBuilder();
    
        HashMap<Integer, String> countryMap = createCountryList(p_GameMap);
    
        for (Map.Entry<Integer, String> countryEntry : countryMap.entrySet()) {
            for (Map.Entry<Integer, String> continentEntry : continentMap.entrySet()) {
                if (continentEntry.getValue().equals(p_GameMap.getCountry(countryEntry.getValue()).getContinent())) {
                    mapContent.append(countryEntry.getKey()).append(" ").append(countryEntry.getValue())
                              .append(" ").append(continentEntry.getKey()).append("\r\n");
                    break;
                }
            }
    
            borders.append(countryEntry.getKey());
            for (Country neighbor : p_GameMap.getCountry(countryEntry.getValue()).getNeighbors()) {
                for (Map.Entry<Integer, String> countryListEntry : countryMap.entrySet()) {
                    if (neighbor.getCountryName().equals(countryListEntry.getValue())) {
                        borders.append(" ").append(countryListEntry.getKey());
                    }
                }
            }
            borders.append("\r\n");
        }
    
        mapContent.append("\r\n[borders]\r\n").append(borders);
    
        bwFile.write(mapContent.toString());
        bwFile.close();
        
        System.out.println("Map file saved as: " + p_FileName + ".map");
        
        return true;
    }
    
    /**
     * Create hashmap of country
     *
     * @param p_GameMap instance of gamemap
     * @return hashmap of country with index
     */
    public HashMap<Integer, String> createCountryList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int counter = 1;
        for(Country l_Country : p_GameMap.getCountries().values()){
            l_CountryMap.put(counter++, l_Country.getCountryName());
        }
        return l_CountryMap;
    }

    /**
     * create a continent hashmap
     *
     * @param p_GameMap gamemap instance
     * @return hashmap of continent and index
     */
    public HashMap<Integer, String> createContinentList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int counter = 1;
        for(Continent l_Continent : p_GameMap.getContinents().values()){
            l_CountryMap.put(counter++, l_Continent.getContinentName());
        }
        return l_CountryMap;
    }


}

