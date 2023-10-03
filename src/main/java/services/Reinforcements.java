package services;

import controller.GameEngineController;
import model.GameMap;
import model.Player;
import utils.InvalidCommandException;

public class Reinforcements {

	private GameMap d_GameMap;

	public Reinforcements() {
		d_GameMap = GameMap.getInstance();
	}

	public void start(int p_GamePhaseID) throws InvalidCommandException {
		calculateReinforcements();
		new GameEngineController().controller(4);

	}

	private void calculateReinforcements() {
		for (Player l_Player : d_GameMap.getGamePlayers().values()) {
			assignReinforcementTroops(l_Player);

		}
	}

	public void assignReinforcementTroops(Player p_Player) {
		// p_Player.calculateTotalReinforcementArmies(d_GameMap,
		// p_Player.getPlayerId());
		p_Player.calculateTotalReinforcementArmies();

	}
}