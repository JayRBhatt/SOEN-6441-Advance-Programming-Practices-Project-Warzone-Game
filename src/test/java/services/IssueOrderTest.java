package services;

import model.GameMap;
import model.GamePhase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class that tests Issue order phase implementations
 * 
 * @author Jay Bhatt
 */

public class IssueOrderTest {
    /**
     * gameMap instance
     */
    GameMap d_GameMap;
    /**
     * Issue Order instance
     */
    private OrderIssue d_IssueOrder;

    /**
     * Setup for test case
     *
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addGamePlayer("Player1");
        d_GameMap.addGamePlayer("Player2");
        d_GameMap.assignCountries();
        d_IssueOrder = new OrderIssue();
        d_IssueOrder.d_GamePhase = GamePhase.IssueOrder;
    }

    /**
     * Clear the setup after the test case
     *
     * @throws Exception exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.ClearMap();
    }

    /**
     * Validate the command syntax
     */
    @Test
    public void validateCommand() {
        assert d_IssueOrder.CommandValidation("deploy india 10", d_GameMap.getGamePlayer("Player1"));
        assert !d_IssueOrder.CommandValidation("deploye india 10", d_GameMap.getGamePlayer("Player2"));
    }
}
