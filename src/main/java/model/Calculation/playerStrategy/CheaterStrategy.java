

package model.Calculation.playerStrategy;

import model.Country;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to implement the Cheater strategy for a player
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */

public class CheaterStrategy extends PlayerStrategy implements Serializable {

     /**
      * Log entry buffer instance 
      */
     private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

   /**
     * Creates and executes the command for the Cheater strategy.
     * It conquers neighboring countries and doubles the armies in border countries.
     *
     * @return String indicating the status of command execution, always returns "pass".
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        logCheaterStrategyAction("Issuing the Orders for the Cheater strategy Player - ");

        List<Country> l_conqueredCountries = conquerNeighborCountries();
        d_Player.getOccupiedCountries().addAll(l_conqueredCountries);

        doubleArmiesInBorderCountries();

        return "pass";
    }
     /**
     * Logs actions related to the Cheater strategy with the player's name.
     *
     * @param p_message The message to be logged.
     */
    private void logCheaterStrategyAction(String p_message) {
        d_Logger.logAction(p_message + d_Player.getPlayerName());
    }
    /**
     * Conquers neighboring countries of all countries occupied by the player.
     *
     * @return List of conquered countries.
     */
    private List<Country> conquerNeighborCountries() {
        List<Country> l_conqueredCountries = new ArrayList<>();
        for (Country country : d_Player.getOccupiedCountries()) {
            l_conqueredCountries.addAll(conquerNeighbors(country));
        }
        return l_conqueredCountries;
    }
    /**
     * Conquers neighboring countries of a specific country.
     *
     * @param p_country The country whose neighbors are to be conquered.
     * @return List of newly conquered countries.
     */
    private List<Country> conquerNeighbors(Country p_country) {
        List<Country> l_conquered = new ArrayList<>();
        for (Country neighbor :p_country.getNeighbors()) {
            if (neighbor.getPlayer() != d_Player) {
                conquerCountry(neighbor);
                l_conquered.add(neighbor);
            }
        }
        return l_conquered;
    }
    /**
     * Conquers a specific country, transferring it from its previous owner to the player.
     *
     * @param p_country The country to be conquered.
     */
    private void conquerCountry(Country p_country) {
        Player l_previousOwner = p_country.getPlayer();
        l_previousOwner.getOccupiedCountries().remove(p_country);
        p_country.setPlayer(d_Player);
        logCheaterStrategyAction("Hooray, you have Conquered the neighbor country of enemy - ");
    }
   /**
     * Doubles the armies in countries that have enemy neighbors.
     */
    private void doubleArmiesInBorderCountries() {
        for (Country country : d_Player.getOccupiedCountries()) {
            if (hasEnemyNeighbors(country)) {
                doubleArmies(country);
            }
        }
    }
     /**
     * Checks if a country has neighboring countries controlled by an enemy player.
     *
     * @param p_country The country to check for enemy neighbors.
     * @return true if there are enemy neighbors, false otherwise.
     */
    private boolean hasEnemyNeighbors(Country p_country) {
        return p_country.getNeighbors().stream()
                .anyMatch(neighbor -> neighbor.getPlayer() != d_Player);
    }
      /**
     * Doubles the number of armies in a given country.
     *
     * @param p_country The country whose armies are to be doubled.
     */
    private void doubleArmies(Country p_country) {
        p_country.setArmies(p_country.getArmies() * 2);
        logCheaterStrategyAction("Armies doubled in Cheater Player's country" + p_country.getCountryName());
    }
}

