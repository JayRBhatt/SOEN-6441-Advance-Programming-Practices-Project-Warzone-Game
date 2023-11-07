package model.GameCalculations;

import model.Country;
import model.Player;

/**
 * An interface with the Attack logic
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public interface AttackLogic {
 
    /**
     * Method holding the default attack logic
     *
     * @param p_Player The player who initiated attack
     * @param p_From   The country from which the attack is initiated
     * @param p_To     The country on which the attack is going to happen
     * @param p_Armies The number of armies to be moved
     * @return true on successful execution else false
     */
    boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies);

    /**
     * Method to swap the ownership of territories once conquered
     *
     * @param p_Player The player to whom the ownership should go
     * @param p_Country The country which is conquered
     */
    default void winner(Player p_Player, Country p_Country) {
        p_Country.getPlayer().getOccupiedCountries().remove(p_Country);
        p_Country.setPlayer(p_Player);
        p_Player.getOccupiedCountries().add(p_Country);
    }   
}
