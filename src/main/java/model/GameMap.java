package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import model.Calculation.playerStrategy.PlayerStrategy;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;
import utils.maputils.Adaptor;
import utils.maputils.ConquestMap;
import utils.maputils.DominationMap;
import utils.maputils.SaveMap;
import utils.maputils.ValidateMap;

/**
 * Class that provides every method that is required for any properties of
 * the Game Map
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */

public class GameMap implements Serializable {

    private static final long serialVersionUID = 45443434343L;

    /**
     * Constructor that initializes the various values
     * 
     */
    public GameMap() {
        this.d_Continents = new HashMap<>();
        this.d_Countries = new HashMap<>();
        this.d_GamePlayers = new HashMap<>();
    }
   /**
    *  string of continents
    */
    private HashMap<String, Continent> d_Continents;
    /**
     * string of countries
     */
    private HashMap<String, Country> d_Countries;
    /**
     * string of players of the game
     */
    private HashMap<String, Player> d_GamePlayers;
    /**
    * the game phase
    */
    private GamePhase d_GamePhase;
    /**
     * string of invalid message
     */
    private String d_InvalidMessage;
    /**
     * object of game map
     */
    private static GameMap d_GameMap = new GameMap();
    /**
     * name
     */
    private String d_Name;
    /**
     * logger instance
     */
    LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    /**
     * winner player
     */
    private Player d_Winner;
    /**
     * integer tries
     */
    private int d_Tries;
    /**
     * current player
     */
    private Player d_CurrentPlayer;
    /**
     * boolean to check game is loaded or not
     */
    private Boolean d_GameLoaded = false;

    /**
     * A static method that returns the instance of the GameMap
     * 
     * @return d_GameMap
     */
    public static GameMap getInstance() {
        if (Objects.isNull(d_GameMap)) {
            d_GameMap = new GameMap();
        }
        return d_GameMap;
    }

    /**
     * To get the current phase
     *
     * @return gamephase instance
     */
    public GamePhase getGamePhase() {
        return d_GamePhase;
    }

    /**
     * Set the current phase
     *
     * @param d_GamePhase gamephase instance
     */
    public void setGamePhase(GamePhase d_GamePhase) {
        this.d_GamePhase = d_GamePhase;
    }

    /**
     * Get the list of all the continents
     *
     * @return d_Continents List of the continents
     */

    /**
     * Returns the Hashmap of Continents
     * 
     * @return d_continents
     */
    public HashMap<String, Continent> getContinents() {

        return this.d_Continents;
    }

    /**
     * Returns a particular continent out of the HashMap
     * 
     * @param p_ID the id of the continent
     * @return d_continents
     */
    public Continent getContinent(String p_ID) {
        return d_Continents.get(p_ID);
    }

    /**
     * Returns a HashMap of Countries
     * 
     * @return d_Countries
     */
    public HashMap<String, Country> getCountries() {
        return d_Countries;
    }

    /**
     * Returns a single country value out of the HashMap of d_Countries
     * 
     * @param p_ID the id of the country
     * @return d_Countries.get(p_ID)
     */
    public Country getCountry(String p_ID) {
        return d_Countries.get(p_ID);
    }

    /**
     * Returns the HashMap of all the Players
     * 
     * @return d_GamePlayers
     */
    public HashMap<String, Player> getGamePlayers() {
        return d_GamePlayers;
    }

    /**
     * Returns a Particular Player Entity from the HashMap
     * 
     * @param p_ID the id of the game player
     * @return d_GamePlayer.get(P_ID)
     */
    public Player getGamePlayer(String p_ID) {
        return d_GamePlayers.get(p_ID);
    }

    /**
     * Returns the String of Invalid Message
     * 
     * @return d_InvalidMessage
     */
    public String getInvalidMessage() {
        return d_InvalidMessage;
    }

    /**
     * Sets the value of the Invalid Message
     * 
     * @param p_InvalidMessage
     */
    public void setInvalidMessage(String p_InvalidMessage) {
        this.d_InvalidMessage = p_InvalidMessage;
    }

    /**
     * Get the Name of the Map
     * 
     * @return d_Name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * sets the Name of the Map
     * 
     * @param p_Name the name of the map which is to be st
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Get the current Player
     *
     * @return player
     */
    public Player getCurrentPlayer() {
        return d_CurrentPlayer;
    }

    /**
     * Set the current Player
     *
     * @param d_CurrentPlayer player
     */
    public void setCurrentPlayer(Player d_CurrentPlayer) {
        this.d_CurrentPlayer = d_CurrentPlayer;
    }

    /**
     * Get the game loaded status
     *
     * @return true if game is loaded
     */
    public Boolean getGameLoaded() {
        return d_GameLoaded;
    }

    /**
     * set the game loaded status
     *
     * @param d_GameLoaded loaded status
     */
    public void setGameLoaded(Boolean d_GameLoaded) {
        this.d_GameLoaded = d_GameLoaded;
    }

    /**
     * method to get tries
     *
     * @return number of tries
     */
    public int getTries() {
        return d_Tries;
    }

    /**
     * method to set number of tries
     *
     * @param p_Tries number of tries
     */
    public void setTries(int p_Tries) {
        d_Tries = p_Tries;
    }

    /**
     * method to to go to next turn
     */
    public void nextTry() {
        d_Tries++;
    }

    /**
     * method to get winner
     *
     * @return the winner
     */
    public Player getWinner() {
        return d_Winner;
    }

    /**
     * method to set winner
     *
     * @param p_Winner the winner
     */
    public void setWinner(Player p_Winner) {
        d_Winner = p_Winner;
    }

    /**
     * Clears the Object off the values that were set by the user
     * 
     */
    public void ClearMap() {
        GameMap.getInstance().getContinents().clear();
        GameMap.getInstance().getCountries().clear();
        GameMap.getInstance().getGamePlayers().clear();
    }

    /**
     * Adds the Continent to The Map
     * 
     * @param p_ContinentName the continent which to be added
     * @param p_TroopsValue   the army value
     */
    public void addContinent(String p_ContinentName, String p_TroopsValue) {

        Continent l_Continent = new Continent(p_TroopsValue, 0, null);
        l_Continent.setContinentName(p_ContinentName);
        l_Continent.setContinentValue(Integer.parseInt(p_TroopsValue));
        this.getContinents().put(p_ContinentName, l_Continent);
        System.out.println("Woohooo! You have added " + p_ContinentName + " to your World Map!!");
        d_LogEntryBuffer.logAction("Added Continent: " + p_ContinentName);
    }

    /**
     * Removes the Continent from The Map
     * 
     * @param p_ContinentName continent which is to be removed
     */

    public void removeContinent(String p_ContinentName) {
        Set<String> l_CountrySet = this.getContinents().remove(p_ContinentName).getCountries().stream()
                .map(Country::getCountryName).collect(Collectors.toSet());
        for (String l_Country : l_CountrySet) {
            this.getCountries().remove(l_Country);
        }
        System.out.println("WOW!!" + p_ContinentName + " is off the Map!!!");
        d_LogEntryBuffer.logAction("Removed Continent: " + p_ContinentName);
    }

    /**
     * Adds a Country to the Map
     * 
     * @param p_CountryName   country which is to be added
     * @param p_ContinentName continent which is to be added
     */
    public void addCountry(String p_CountryName, String p_ContinentName) {
        Country l_Country = new Country();
        l_Country.setCountryName(p_CountryName);
        l_Country.setContinent(p_ContinentName);
        this.getCountries().put(p_CountryName, l_Country);
        this.getContinent(p_ContinentName).getCountries().add(l_Country);
        System.out.println("There you have it! " + p_CountryName + " a part of " + p_ContinentName);
        d_LogEntryBuffer.logAction("Added Country: " + p_CountryName + " to " + p_ContinentName + " continent");
    }

    /**
     * Removes a Country from the Game Map
     * 
     * @param p_CountryName the country which is to be removed
     */
    public void removeCountry(String p_CountryName) {
        Country l_Country = this.getCountry(p_CountryName);
        this.getContinent(l_Country.getContinent()).getCountries().remove(l_Country);
        this.getCountries().remove(l_Country.getCountryName());
        System.out.println("...And " + p_CountryName + " is erased off the map!! ");
        d_LogEntryBuffer.logAction("Removed Continent: " + p_CountryName);
    }

    /**
     * Adds a Neighbor to the Country
     * 
     * @param p_CountryName         the country name
     * @param p_NeighborCountryName the neighbour country name
     */
    public void addNeighbor(String p_CountryName, String p_NeighborCountryName) {
        Country l_Country = this.getCountry(p_CountryName);
        Country l_NeighborCountry = this.getCountry(p_NeighborCountryName);

        l_Country.getNeighbors().add(l_NeighborCountry);
        System.out.println("Ohhh Look at you! We have neighbors around you, Are they friendly? We'll find out soon");
        d_LogEntryBuffer.logAction("Added Neighbor: " + p_NeighborCountryName + " to " + p_CountryName);

    }

    /**
     * Removes a Neighbor of a country provided from the map
     * 
     * @param p_CountryName         country name
     * @param p_NeighborCountryName neighbour country name
     * @throws InvalidCommandException when something failes
     */
    public void removeNeighbor(String p_CountryName, String p_NeighborCountryName) throws InvalidCommandException {
        Country l_Country = this.getCountry(p_CountryName);

        Country l_NeighborCountry = this.getCountry(p_NeighborCountryName);
        if (l_Country == null) {
            throw new InvalidCommandException("Ayoo, You messed up!");
        } else if (!l_Country.getNeighbors().contains(l_NeighborCountry)
                || !l_NeighborCountry.getNeighbors().contains(l_Country)) {

            throw new InvalidCommandException("Oh c'mon! No way they are neighbors");
        } else {
            this.getCountry(p_CountryName).getNeighbors().remove(l_NeighborCountry);
            System.out.println("Good Riddance!! Annoying Neighbors makes one's life a living hell. Removed: "
                    + p_CountryName + " and " + p_NeighborCountryName + " as neighbors");
            d_LogEntryBuffer.logAction("Removed Neighbor: " + p_NeighborCountryName + " of Country: " + p_CountryName);
        }
    }

    /**
     * Adds a Player to the game
     * 
     * @param p_PlayerName player which is to be added
     * @throws InvalidCommandException when something failes
     */
    public void addGamePlayer(String p_PlayerName) throws InvalidCommandException {
        if (this.getGamePlayers().containsKey(p_PlayerName)) {
            throw new InvalidCommandException("Duplicates?! No you don't belong here");
        }
        Player l_GamePlayer = new Player(PlayerStrategy.getStrategy("human"));
        l_GamePlayer.setPlayerName(p_PlayerName);
        getGamePlayers().put(p_PlayerName, l_GamePlayer);

        System.out.println("Hello " + p_PlayerName + ", Welcome to the world of wars!!");
        d_LogEntryBuffer.logAction("Added GamePlayer: " + p_PlayerName);

    }

    /**
     * Adds a Player to the game
     * 
     * @param p_PlayerName player which is to be added
     * @param p_Strategy the chosen strategy
     * @throws InvalidCommandException when something failes
     */
    public void addGamePlayer(String p_PlayerName, String p_Strategy) throws InvalidCommandException {
        if (this.getGamePlayers().containsKey(p_PlayerName)) {
            throw new InvalidCommandException("Duplicates?! No you don't belong here");
        }
        Player l_GamePlayer = new Player(PlayerStrategy.getStrategy(p_Strategy));
        l_GamePlayer.setPlayerName(p_PlayerName);
        getGamePlayers().put(p_PlayerName, l_GamePlayer);

        System.out.println("Hello " + p_PlayerName + ", Welcome to the world of wars!!");
        d_LogEntryBuffer.logAction("Added GamePlayer: " + p_PlayerName);

    }

    /**
     * Removes a player already present in the game
     * 
     * @param p_PlayerName player which is to be removed
     * @throws InvalidCommandException when something failes
     */
    public void removeExistingPlayer(String p_PlayerName) throws InvalidCommandException {
        Player l_ExistingPlayer = this.getGamePlayer(p_PlayerName);
        if (l_ExistingPlayer == null) {
            throw new InvalidCommandException("Player does not exist: " + p_PlayerName);
        }
        this.getGamePlayers().remove(l_ExistingPlayer.getPlayerName());
        System.out.println("You didn't like the name or What? Deleted: " + p_PlayerName);

        d_LogEntryBuffer.logAction("Removed GamePlayer: " + p_PlayerName);
    }

    /**
     * Saves the Map with all the changes done in it
     * @param p_saveAsConquest whether to save map in conquest or not
     * @throws InvalidCommandException when something failes
     * @throws IOException when it happens
     */
    public void saveMap(boolean p_saveAsConquest) throws InvalidCommandException, IOException {

        if (ValidateMap.validateMap(d_GameMap, 0)) {
            DominationMap l_SaveMap = p_saveAsConquest ? new Adaptor(new ConquestMap()) : new DominationMap();
            boolean flag = true;
            while (flag) {
                d_GameMap.getName();
                if (Objects.isNull(d_GameMap.getName()) || d_GameMap.getName().isEmpty()) {
                    throw new InvalidCommandException("Nope! Not the Correct name I suppose.");
                } else {
                    if (l_SaveMap.saveMap(d_GameMap, d_GameMap.getName())) {
                        System.out.println("Wow! The Map is in Correct Format ");
                        d_LogEntryBuffer.logAction("The map is Validated and saved");
                    } else {
                        throw new InvalidCommandException(
                                "This isn't a Multiverse, give a different name for the country");
                    }
                    flag = false;
                }
            }
        } else {
            throw new InvalidCommandException(
                    "C'mon that isn't a Valid Map. Its a children's drawing at best! *Scoffs*");
        }
    }

    /**
     * Assigns all the countries present in the map to each and every player present
     * 
     */
    public void assignCountries() {
        int d_player_index = 0;
        List<Player> l_players = d_GameMap.getGamePlayers().values().stream().collect(Collectors.toList());

        List<Country> d_CountryList = d_GameMap.getCountries().values().stream().collect(Collectors.toList());
        Collections.shuffle(d_CountryList);

        for (int i = 0; i < d_CountryList.size(); i++) {
            Country d_c = d_CountryList.get(i);
            Player d_p = l_players.get(d_player_index);
            d_p.getOccupiedCountries().add(d_c);
            d_c.setPlayer(d_p);
            System.out.println(d_c.getCountryName() + " Assigned to " + d_p.getPlayerName());

            d_LogEntryBuffer.logAction(d_c.getCountryName() + " Assigned to " + d_p.getPlayerName());

            if (d_player_index < d_GameMap.getGamePlayers().size() - 1) {
                d_player_index++;
            } else {
                d_player_index = 0;
            }
        }
    }

    /**
     * Shows the Continents, Countries and the player stat are present in the
     * map(added by user) in a readable and easier format
     * 
     */
    public void showMap() {
        System.out.println("\nShowing the Map Details:\n");

        System.out.println("\nThe Continents in this Map:\n");

        printTableHeader("+-----------------+");
        System.out.println("| Continent's name |");
        printTableHeader("+-----------------+");

        for (Map.Entry<String, Continent> continentEntry : d_GameMap.getContinents().entrySet()) {
            Continent l_Continent = continentEntry.getValue();
            System.out.format("|%-16s|%n", l_Continent.getContinentName());
        }

        printTableFooter("+-----------------+");

        System.out.println("\nThe countries in this Map and their details:\n");

        printTableHeader(
                "+-----------------------+----------------+------------------------------------------------+---------------+");
        System.out.format(
                "|%-23s|%-16s|%-48s|%-15s|%n",
                "Country's name",
                "Continent's Name",
                "Neighbour Countries",
                "No. of armies");
        printTableHeader(
                "+-----------------------+----------------+------------------------------------------------+---------------+");

        for (Map.Entry<String, Continent> continentEntry : d_GameMap.getContinents().entrySet()) {
            Continent continent = continentEntry.getValue();

            for (Country country : continent.getCountries()) {
                System.out.format(
                        "|%-23s|%-16s|%-48s|%-15s|%n",
                        country.getCountryName(),
                        continent.getContinentName(),
                        country.createNeighborList(country.getNeighbors()),
                        country.getArmies());
            }
        }

        printTableFooter(
                "+-----------------------+----------------+------------------------------------------------+---------------+");

        HashMap<String, Player> players = d_GameMap.getGamePlayers();
        System.out.println("\nPlayers in this game if the game has started are:\n");

        if (players != null) {
            players.forEach((key, value) -> System.out.println(key));
            System.out.println();
        }

        System.out.println("\nThe Map ownership of the players are:\n");

        printTableHeader("+-----------------+------------------------+---------------------+");
        System.out.format("| Player's name   | Continent's Controlled | No. of Armies Owned |%n");
        printTableHeader("+-----------------+------------------------+---------------------+");

        List<Player> playerList = players.values().stream().collect(Collectors.toList());

        for (Player player : playerList) {
            System.out.format(
                    "|%-17s|%-24s|%-21d|%n",
                    player.getPlayerName(),
                    player.createOccupyList(player.getOccupiedCountries()),
                    player.getAdditionalArmies());
        }

        printTableFooter("+-----------------+------------------------+---------------------+");
    }

    /**
     * Format related method for showmap to print a particular pattern on CLI
     * 
     * @param format
     */
    private void printTableHeader(String format) {
        System.out.println(format);
    }

    /**
     * Format related method for showmap to print a particular pattern on CLI
     * 
     * @param format
     */
    private void printTableFooter(String format) {
        System.out.println(format);
    }
   /**
    * Builds and initializes the game state using a provided game map.
    * @param p_GameMap The game map containing the initial state information.
    * @return The game phase after initialization
    * @throws InvalidCommandException when it happens
    */
    public GamePhase gamePlayBuilder(GameMap p_GameMap) throws InvalidCommandException {

        this.ClearMap();
        d_GameMap.setGameLoaded(true);
        for (Map.Entry<String, Continent> l_Continent : p_GameMap.getContinents().entrySet()) {
            this.addContinent(l_Continent.getKey(), String.valueOf(l_Continent.getValue().getBonusArmies()));
        }
        for (Map.Entry<String, Country> l_Country : p_GameMap.getCountries().entrySet()) {
            this.addCountry(l_Country.getKey(), l_Country.getValue().getContinent());
        }
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                for (Country l_Neighbour : l_Country.getNeighbors()) {
                    p_GameMap.addNeighbor(l_Country.getCountryName(), l_Neighbour.getCountryName());
                }
            }
        }
        for (Map.Entry<String, Player> l_Player : p_GameMap.getGamePlayers().entrySet()) {
            this.addGamePlayer(l_Player.getKey());
            this.getGamePlayer(l_Player.getKey()).setOccupiedCountries(l_Player.getValue().getOccupiedCountries());
            this.getGamePlayer(l_Player.getKey()).setAdditionalArmies(l_Player.getValue().getAdditionalArmies());
        }
        this.setGamePhase(p_GameMap.getGamePhase());
        this.setCurrentPlayer(p_GameMap.getCurrentPlayer());
        for (Map.Entry<String, Player> l_Player : p_GameMap.getGamePlayers().entrySet()) {
            this.getGamePlayer(l_Player.getKey()).setOrders(l_Player.getValue().getOrders());
            this.getGamePlayer(l_Player.getKey()).setPlayersCards(l_Player.getValue().getPlayersCards());
        }

        return p_GameMap.getGamePhase();
    }

    /**
     * Returns new instance of gamemap
     *
     * @return instance of gamemap
     */
    public static GameMap newInstance() {
        GameMap l_GameMap = d_GameMap;
        if (Objects.nonNull(l_GameMap)) {
            l_GameMap.d_GamePhase = null;
            l_GameMap.d_Name = "";
            l_GameMap.d_InvalidMessage = "";
            l_GameMap.d_Winner = null;
            l_GameMap.d_Tries = 0;
            l_GameMap.d_CurrentPlayer = null;
            l_GameMap.d_GameLoaded = false;
            l_GameMap.ClearMap();
            return d_GameMap;
        } else {
            return getInstance();
        }
    }

}