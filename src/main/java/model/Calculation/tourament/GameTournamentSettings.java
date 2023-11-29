package model.Calculation.tourament;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Calculation.playerStrategy.PlayerStrategy;

/**
 * Class to implement the tournament game tournament settings
 *
 * @author Madhav Anadkat
 */
public class GameTournamentSettings {
    private List<String> d_Map = new ArrayList<>();
    private Set<PlayerStrategy> d_PlayerStrategies = new HashSet<>();
    private int d_Games;
    private int d_MaxTries;
    
    /**
     * Gets the map data as a list of strings.
     *
     * @return The map data.
     */

    public List<String> getMap() {
        return d_Map;
    }

    /**
     * method to get player strategies
     *
     * @return player strategies
     */
    public Set<PlayerStrategy> getPlayerStrategies() {
        return d_PlayerStrategies;
    }

    /**
     * method to get games
     * 
     * @return number of games
     */
    public int getGames() {
        return d_Games;
    }

    /**
     * method to set game
     * 
     * @param p_Games number of games
     */
    public void setGames(int p_Games) {
        d_Games = p_Games;
    }

    /**
     * method to get maximum tried per game
     *
     * @return the maximum tries
     */
    public int getMaxTries() {
        return d_MaxTries;
    }

    /**
     * method to set maximum tries
     * 
     * @param p_MaxTries maximum tries
     */
    public void setMaxTries(int p_MaxTries) {
        d_MaxTries = p_MaxTries;
    }
}
