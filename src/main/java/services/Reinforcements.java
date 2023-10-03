package services;

import model.GameMap;
import model.Player;
import utils.InvalidCommandException;

/**
 * A class that assigns the number of armies to each player 
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */

public class Reinforcements {

	private GameMap d_GameMap;
	
	/**
     * Constructor to get the current state of the game
     */
	public Reinforcements() {
		d_GameMap = GameMap.getInstance();
	}
	
	/**
	 * Starts the execution of orders in the Reinforcement phase and calls calculateReinforcements()
	 * @param p_GamePhaseID indicating  the current phase
	 * @throws InvalidCommandException if game phase transition is invalid.
	 */
	public void start(int p_GamePhaseID) throws InvalidCommandException {
		calculateReinforcements();
	}

	/**
     * Calls the assignReinforcementTroops() method for each player in the current match
     */
	private void calculateReinforcements() {
		for (Player l_Player : d_GameMap.getGamePlayers().values()) {
			assignReinforcementTroops(l_Player);

		}
	}
	/**
	 * For the particular player, calls the associated calculateTotalReinforcementArmies()
	 * @param p_Player for each player
	 */
	public void assignReinforcementTroops(Player p_Player) {
		p_Player.calculateTotalReinforcementArmies();

	}
}