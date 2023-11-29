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
            if (p_Armies <= 0) {
                d_LogEntryBuffer.logAction("You cannot move/attack with 0 armies");
                return false;
            }
            p_From.depleteArmies(p_Armies);
            if (p_To.getArmies() == 0) {
                p_To.setArmies(p_Armies);
                winner(p_Player, p_To);
                d_LogEntryBuffer.logAction(
                        String.format("Attacker : %s (%s) won", p_Player.getPlayerName(), p_From.getCountryName()));
                // Assign power card to king
                Cards l_AssignedCard = new Cards();
                p_Player.addPlayerCard(l_AssignedCard);
                d_LogEntryBuffer.logAction("Attacker: " + p_Player.getPlayerName() + " received a card: "
                        + l_AssignedCard.getCardsType().toString());
                d_LogEntryBuffer.logAction(String.format("Since won, left out %s (Attacker) armies %s moved to %s.",
                        p_From.getCountryName(), p_Armies, p_To.getCountryName()));
                return true;
            }
            int l_attackerKills = (int) Math.round(p_Armies * calculation.ATTACKER_PROBABILITY);
            int l_defenderKills = (int) Math.round(p_To.getArmies() * calculation.DEFENDER_PROBABILITY);

            int l_armiesLeftAttacker = p_Armies > l_defenderKills ? (p_Armies - l_defenderKills) : 0;
            int l_armiesLeftDefender = p_To.getArmies() > l_attackerKills ? p_To.getArmies() - l_attackerKills : 0;
            if (l_armiesLeftAttacker > 0 && l_armiesLeftDefender <= 0) {
                p_To.setArmies(l_armiesLeftAttacker);
                winner(p_Player, p_To);
                d_LogEntryBuffer.logAction(
                        String.format("Attacker : %s (%s) won", p_Player.getPlayerName(), p_From.getCountryName()));
                // Assign power card to king
                Cards l_AssignedCard = new Cards();
                p_Player.addPlayerCard(l_AssignedCard);
                d_LogEntryBuffer.logAction("Attacker: " + p_Player.getPlayerName() + " received a card: "
                        + l_AssignedCard.getCardsType().toString());
                d_LogEntryBuffer.logAction(String.format("Since won, left out %s (Attacker) armies %s moved to %s.",
                        p_From.getCountryName(), l_armiesLeftAttacker, p_To.getCountryName()));
            } else {
                p_From.deployArmies(l_armiesLeftAttacker);
                p_To.setArmies(l_armiesLeftDefender);
                d_LogEntryBuffer.logAction(
                        String.format("Attacker : %s (%s) lost", p_Player.getPlayerName(), p_From.getCountryName()));
                d_LogEntryBuffer.logAction(String.format("Remaining armies of %s (Attacker) in attack: %s ",
                        p_From.getCountryName(), l_armiesLeftAttacker));
                d_LogEntryBuffer.logAction(String.format("Remaining armies of %s (Defender) in attack: %s ",
                        p_To.getCountryName(), l_armiesLeftDefender));
            }
            return true;
        } catch (Exception p_Exception) {
            return false;
        }
    }
}
