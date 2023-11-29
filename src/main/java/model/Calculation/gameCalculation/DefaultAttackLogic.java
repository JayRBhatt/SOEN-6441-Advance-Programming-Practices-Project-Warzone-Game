package model.Calculation.gameCalculation;

import model.Cards;
import model.Country;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * An class which implements the AttackLogic class
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class DefaultAttackLogic implements AttackLogic {
	
    GameCalculation calculation = GameCalculation.getInstance();

    /**
     * Logger for game actions
     */
    LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();

    /**
     * Method holding the default attack logic
     *
     * @param p_Player The player who initiated attack
     * @param p_From   The country from which the attack is initiated
     * @param p_To     The country on which the attack is going to happen
     * @param p_Armies The number of armies to be moved
     * @return true on successful execution else false
     */
    @Override
    public boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies) {
        try {
            p_From.depleteArmies(p_Armies);
            int l_attackerKills = (int) Math.round(p_Armies * calculation.ATTACKER_PROBABILITY);
            int l_defenderKills = (int) Math.round(p_To.getArmies() * calculation.DEFENDER_PROBABILITY);

            int l_armiesLeftAttacker = p_Armies - l_defenderKills;
            int l_armiesLeftDefender = p_To.getArmies() - l_attackerKills;
            if (l_armiesLeftAttacker > 0 && l_armiesLeftDefender <= 0) {
                p_To.setArmies(l_armiesLeftAttacker);
                winner(p_Player, p_To);

                Cards l_AssignedCard = new Cards();
                p_Player.addPlayerCard(l_AssignedCard);
                System.out.println("Attacker: " + p_Player.getPlayerName() + " received a card: "
                        + l_AssignedCard.getCardsType().name());
                d_LogEntryBuffer
                        .logAction("Attacker: " + p_Player.getPlayerName() + " received a card: "
                                + l_AssignedCard.getCardsType().name());
                System.out.println("Attacker : " + p_Player.getPlayerName() + " won.");
                System.out.println("Remaining attacker's armies " + p_To.getArmies() + " moved from "
                        + p_From.getCountryName() + " to " + p_To.getCountryName() + ".");
            } else {
                p_From.deployArmies(l_armiesLeftAttacker);
                p_To.setArmies(l_armiesLeftDefender);
                System.out.println("Attacker : " + p_Player.getPlayerName() + " lost.");
                System.out.println("Remaining attacker's armies: " + p_From.getArmies());
                System.out.println("Remaining defender's armies: " + p_To.getArmies());
            }
            return true;
        } catch (Exception p_Exception) {
            return false;
        }
    }
}
