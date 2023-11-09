package services;

import model.GameMap;
import model.GamePhase;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.exceptions.InvalidInputException;
import utils.exceptions.InvalidCommandException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionalities for Reinforcement phase
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class ReinforcementTest extends Player {
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;
    Reinforcements l_Reinforcement;

    /**
     * This method initializes the values for continents, countries and players
     * before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addContinent("Europe", "3");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addCountry("France", "Europe");
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
        d_GameMap.assignCountries();
        l_Reinforcement = new Reinforcements();
        l_Reinforcement.d_GamePhase = GamePhase.Reinforcement;
    }

    /**
     * This method will be executed at the end of the test
     *
     * @throws Exception when execution fails
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.ClearMap();
    }

    /**
     * This method tests the validation for next Game phase
     *
     * @throws InvalidCommandException       if validation fails
     * @throws InvalidInputException if execution is invalid
     */
    @Test
    public void teststartShouldReturnNextPhase() throws InvalidCommandException, InvalidInputException {
        GamePhase l_NextGamePhase = l_Reinforcement.start(GamePhase.Reinforcement);
        assertEquals(d_NextGamePhase, l_NextGamePhase);
    }

    /**
     * This method tests if valid reinforcements is set
     *
     * @throws InvalidCommandException       if validation fails
     * @throws InvalidInputException if execution is invalid
     */
    @Test
    public void testReinforcementsSetOrNot() throws InvalidCommandException, InvalidInputException {
        l_Reinforcement.d_CurrentPlayer = d_GameMap.getGamePlayer("Player2");
        l_Reinforcement.assignReinforcementTroops();
        assertTrue(l_Reinforcement.d_CurrentPlayer.getAdditionalArmies() >= 3);
    }
}
