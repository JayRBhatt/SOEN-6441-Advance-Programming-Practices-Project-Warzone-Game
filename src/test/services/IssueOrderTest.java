import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class IssueOrderTest {
    private IssueOrder issueOrderTest;
    private String playerName;
    private int noOfReinforcementArmies;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    public IssueOrderTest(String playerName)
    {
        this.playerName = playerName;
    }
    public IssueOrderTest(int noOfReinforcementArmies)
    {
        this.noOfReinforcementArmies = noOfReinforcementArmies;
    }
    
    @Before
    public void setUp() {
        issueOrderTest = new issueOrderTest(); // Create an instance of your class
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testBegin() {
        issueOrder issueOrderObj = new issueOrder();
        issueOrderObj.playerName =this.playerName;
        issueOrderObj.noOfReinforcementArmies=this.noOfReinforcementArmies;
        // Prepare your test data and dependencies, including the game map and players
        Map<Integer, Player> gamePlayers = new HashMap<>();
        // Populate gamePlayers with test player objects
        
        // Set the game map's gamePlayers using reflection or other appropriate means
        // issueOrderTest.setGamePlayers(gamePlayers);

        // Mock user input for readFromPlayer method
        String mockInput = "deploy India 10 \n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(mockInput.getBytes()));

        try {
            issueOrderTest.begin(1); // Call the method being tested
        } catch (InvalidCommandException e) {
            fail("An exception occurred: " + e.getMessage());
        }

        // Perform assertions on the output using outContent
        String expectedOutput = "The Armies assigned to "+playerName +" are :"+ noOfReinforcementArmies
                                + "Please assign your armies only to the below listed countries:\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        // Restore standard input and output
        System.setIn(stdin);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}