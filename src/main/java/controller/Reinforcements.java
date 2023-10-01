package controller;

public class Reinforcements {
private GameMap d_GameMap;
	
	private Player d_CurrentPlayer;
	
	public void start(int p_GamePhaseID) {
		calculateReinforcements();
		
	}

	private void calculateReinforcements() {
		//for each player assign troops depending on the country assigned to them
		for(Player l_Player : d_GameMap.getPlayers().values()) {
			setReinforcementTroops();
            		
		}
	}
	
    public void setReinforcementTroops() {
            d_CurrentPlayer.calculateTotalReinforcementArmies(d_GameMap);
        }
}