import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.exceptions.InvalidCommandException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test case class to check the correct starting phase of game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class TournamentGameModeTest extends TournamentGameMode {
    /**
     * The game engine
     */
    private static Engine d_gameEngine;

    /**
     * To start the tournament engine
     */
    public TournamentGameModeTest() {
        init();
    }

    /**
     * Method to set up the basics for each test case
     *
     * @throws Exception if any exception occurs
     */
    @Before
    public void setUp() throws Exception {
        d_gameEngine = new TournamentGameModeTest();
    }

    /**
     * Override of init method
     */
    @Override
    public void init() {

    }

    /**
     * Method to tear down the basics after each test case ran
     *
     * @throws Exception if any exception occurs
     */
    @After
    public void tearDown() throws Exception {
        d_gameEngine = null;
    }

    /**
     * Check if tournament command options are valid
     */
    @Test
    public void testShouldParseValidTournamentCommand() {
        String tournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D 3";
        this.parseCommand(tournamentCommand);
        assertEquals(2, d_Options.getGames());
        assertEquals(3, d_Options.getMaxTries());
        assertEquals(1, d_Options.getMap().size());
        assertEquals("Australia.map", d_Options.getMap().get(0));
    }

    /**
     * Check possibilities of invalid options in a tournament command
     */
    @Test
    public void testShouldFailParsingInvalidTournamentCommand() {
        String tournamentCommand = "tournament -M Australia.map -P aggressive,random -G 6 -D 3";
        d_Options = this.parseCommand(tournamentCommand);
        assertNull(d_Options);
        tournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D -9";
        d_Options = this.parseCommand(tournamentCommand);
        assertNull(d_Options);
        tournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D 60";
        d_Options = this.parseCommand(tournamentCommand);
        assertNull(d_Options);
        tournamentCommand = "tournament -M Australia.map -P aggressive -G 2 -D 60";
        d_Options = this.parseCommand(tournamentCommand);
        assertNull(d_Options);
    }

    /**
     * Check if Result of tournament is not null
     *
     * @throws ValidationException when it happens
     */
    @Test
    public void shouldReturnNonNullTournamentResult() throws InvalidCommandException {
        String tournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D 3";
        this.parseCommand(tournamentCommand);
        this.start();
        assertEquals(2, this.d_Results.size());
    }
}
