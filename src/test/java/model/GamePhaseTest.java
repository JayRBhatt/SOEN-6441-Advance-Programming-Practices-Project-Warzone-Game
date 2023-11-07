package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test case class to test the right possible next phase from each phase
   @author Jay Bhatt
   @author Madhav Anadkat
   @author Bhargav Fofandi
 */
public class GamePhaseTest {
    /**
     * Game phase object
     */
    GamePhase d_GamePhase;

    /**
     * Method to set up the basics for each test case
     *
     * @throws Exception if any exception occurs
     */
    @Before
    public void setUp() throws Exception {

    }

    /**
     * Method to tear down the basics after each test case ran
     *
     * @throws Exception if any exception occurs
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test to test next right state after MapEditor state
     */
    @Test
    public void testNextPhaseAfterMapEditor() {
        d_GamePhase = GamePhase.MapEditor;
        assertEquals(GamePhase.StartUp,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to test next right state after StartUp state
     */
    @Test
    public void testNextPhaseAfterStartUp() {
        d_GamePhase = GamePhase.StartUp;
        assertEquals(GamePhase.Reinforcement,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to test next right state after Reinforcement state
     */
    @Test
    public void testNextPhaseAfterReinforcement() {
        d_GamePhase = GamePhase.Reinforcement;
        assertEquals(GamePhase.IssueOrder,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to test next right state after IssueOrder state
     */
    @Test
    public void testNextPhaseAfterIssueOrder() {
        d_GamePhase = GamePhase.IssueOrder;
        assertEquals(GamePhase.ExecuteOrder,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to test next right states after ExecuteOrder state
     */
    @Test
    public void testNextPhaseAfterExecuteOrder() {
        d_GamePhase = GamePhase.ExecuteOrder;
        assertTrue(d_GamePhase.possibleStates().contains(GamePhase.Reinforcement));
        assertTrue(d_GamePhase.possibleStates().contains(GamePhase.ExitGame));
    }

}
