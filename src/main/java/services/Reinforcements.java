package services;

import controller.GameEngineController;
import model.GameMap;
import model.GamePhase;
import model.Player;
import utils.InvalidCommandException;

/**
 * Class that has the main logic behind the functioning of Reinforcements phase
 * in
 * the game
 *
 *
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class Reinforcements implements GameEngineController{

	private GameMap d_GameMap;
	GamePhase d_GamePhase;
	GamePhase d_NextGamePhase = GamePhase.IssueOrder;

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
	 * @param p_GamePhase ID of the GamePhase
	 * @throws InvalidCommandException if command is invlaid
	 */
	public GamePhase start(GamePhase p_GamePhase) throws InvalidCommandException {
		d_GamePhase = p_GamePhase;
		calculateReinforcements();
		return d_NextGamePhase;

	}

	/**
	 * Method that calculates the reinforcements
	 * 
	 */
	private void calculateReinforcements() {
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