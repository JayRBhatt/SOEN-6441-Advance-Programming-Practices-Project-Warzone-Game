package services;

import controller.GameEngineController;
import model.GameMap;
import model.GamePhase;
import model.Player;
import utils.exceptions.InvalidCommandException;
import utils.exceptions.InvalidInputException;

/**
 * Class that has the main logic behind the functioning of Reinforcements phase
 * in
 * the game
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class Reinforcements implements GameEngineController{

    GameMap d_GameMap;
	GamePhase d_GamePhase;
	GamePhase d_NextGamePhase = GamePhase.IssueOrder;
	Player d_CurrentPlayer;

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
	public GamePhase start(GamePhase p_GamePhase) throws InvalidCommandException, InvalidInputException {
		d_GamePhase = p_GamePhase;
		calculateReinforcements();
		return d_NextGamePhase;

	}

	/**
	 * Method that calculates the reinforcements
	 * 
	 */
	private void calculateReinforcements() throws InvalidInputException{
		for (Player l_Player : d_GameMap.getGamePlayers().values()) {
			d_CurrentPlayer = l_Player;
			assignReinforcementTroops();

		}
	}

	/**
	 * Method that assigns the calculated number of reinforcements for a player
	 * @throws InvalidInputException if the validation fails
	 */
	public void assignReinforcementTroops() throws InvalidInputException{
	        if (d_GamePhase.equals(GamePhase.Reinforcement)) {
				
            d_CurrentPlayer.calculateTotalReinforcementArmies(d_GameMap);
        } else throw new InvalidInputException();

	}
}