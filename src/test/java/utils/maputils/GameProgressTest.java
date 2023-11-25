package utils.maputils;

import model.GameMap;
import model.GamePhase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.maputils.GameProgress.storeGameState;
import static utils.maputils.GameProgress.retrieveGameState;;

/**
 * A class to test all the functionalities in GameProgress saving and loading game
 * @author Jay Bhatt 
 */
public class GameProgressTest {
    /**
     * GameMap instance
     */
    private GameMap d_GameMap;

    /**
     * Initial Setup to be used before all test cases
     *
     * @throws Exception if execution fails
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        // ... [rest of the setup code remains unchanged]
    }

    /**
     * This method will be executed at the end of the test
     *
     * @throws Exception when execution fails
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.getContinents().clear();
        d_GameMap.getCountries().clear();
        d_GameMap.getGamePlayers().clear();
    }

    /**
     * Test case for successful save game progress
     */
    @Test
    public void testSaveGameProgressSuccessful() {
        assertTrue(storeGameState(d_GameMap, "output"));
    }

    /**
     * Test case for failed save game progress
     */
    @Test
    public void testSaveGameProgressFailure() {
        assertFalse(storeGameState(null, "output"));
    }

    /**
     * Test case for incorrect file name while loading
     */
    @Test
    public void testLoadGameWithIncorrectFileName() {
        assertEquals(GamePhase.StartUp, retrieveGameState("random.bin"));
    }

    /**
     * Test case for corrupt file while loading
     */
    @Test
    public void testLoadGameWithCorruptFile() {
        assertEquals(GamePhase.StartUp, retrieveGameState("corrupt.bin"));
    }

    /**
     * Test case for successful load game
     */
    @Test
    public void testLoadGameSuccessful() {
        assertEquals(GamePhase.StartUp, retrieveGameState("test.bin"));
    }
}
