package services;

import controller.GameEngineController;
import model.GameMap;
import model.Player;
import utils.InvalidCommandException;

/**
 * Class that has the main logic behind the functioning of Reinforcements phase
 * in
 * the game
 *
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class Reinforcements {

	private GameMap d_GameMap;

	/**
	 * Constructor to initialize the GameMap object
	 * 
	 */
	public Reinforcements() {
		d_GameMap = GameMap.getInstance();
	}

	/**
	 * Method that executes the logic of the Reinforcements phase
	 * 
	 * @param p_GamePhaseID ID of the GamePhase
	 * @throws InvalidCommandException if command is invalid
	 */
	public void start(int p_GamePhaseID) throws InvalidCommandException {
		calculateReinforcements();
		new GameEngineController().controller(4);
	}

	/**
	 * Method that calculates the reinforcements
	 * 
	 */
	public void calculateReinforcements() {
		for (Player l_Player : d_GameMap.getGamePlayers().values()) {
			assignReinforcementTroops(l_Player);
		}
	}

	/**
	 * Method that assigns the calculated number of reinforcements for a player
	 * 
	 * @param p_Player Current Player
	 */
	public void assignReinforcementTroops(Player p_Player) {
		p_Player.calculateTotalReinforcementArmies();
	}
}