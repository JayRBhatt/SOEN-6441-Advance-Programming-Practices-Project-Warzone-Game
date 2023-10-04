package services;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import utils.InvalidCommandException;
import model.GameMap;
import model.Player;

public class ReinforcementsTest {
    private GameMap d_GameMap;
    private Reinforcements d_Reinforcements;

    @Before
    public void setUp() {
    	
        d_GameMap = GameMap.getInstance();
        d_Reinforcements = new Reinforcements();
    }

    @Test
    public void testCalculateReinforcements() {
    	d_Reinforcements.calculateReinforcements();;
    }

    @Test
    public void testAssignReinforcementTroops() {
        Player l_Player = new Player();
        d_Reinforcements.assignReinforcementTroops(l_Player);
    }

    @Test
    public void testStart() {
        try {
            d_Reinforcements.start(4);

        } catch (InvalidCommandException e) {

            fail("InvalidCommandException: " + e.getMessage());
        }
    }
}