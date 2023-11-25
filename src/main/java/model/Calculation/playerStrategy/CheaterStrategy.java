

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
 */

public class CheaterStrategy extends PlayerStrategy implements Serializable {

    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Implementation of create command
     *
     * @return null
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        d_Logger.logAction("Issuing Orders for the Cheater Player - " + d_Player.getPlayerName());
        Player l_NeighborOwner = null;
        List<Country> l_Enemies = new ArrayList<>();
        //find and conquer neighbor countries
        for (Country l_Country : d_Player.getOccupiedCountries()) {
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                if (l_Neighbor.getPlayer() != d_Player) {
                    l_NeighborOwner = l_Neighbor.getPlayer();
                    l_NeighborOwner.getOccupiedCountries().remove(l_Neighbor);
                    l_Enemies.add(l_Neighbor);
                    l_Neighbor.setPlayer(d_Player);
                    d_Logger.logAction("Conquered the neighbor country of enemy - " + l_Neighbor.getCountryName());
                }
            }
        }
        d_Player.getOccupiedCountries().addAll(l_Enemies);

        //double the army of a country if it has an enemy
        for (Country l_Country : d_Player.getOccupiedCountries()) {
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                if (l_Neighbor.getPlayer() != d_Player) {
                    l_Country.setArmies(l_Country.getArmies() * 2);
                    d_Logger.logAction("Armies doubled in Cheater Player's country" + l_Country.getCountryName());
                }
            }
        }
        return "pass";
    }
}

