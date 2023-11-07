import services.ExecuteOrderTest;
import services.IssueOrderTest;
import services.ReinforcementTest;
import model.ContinentTest;
import model.CountryTest;
import model.GameMapTest;
import model.PlayerTest;
import model.order.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utils.maputils.ValidateMapTest;

@RunWith(Suite.class)

/**
 * Run all test cases
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
@Suite.SuiteClasses({
        IssueOrderTest.class, ReinforcementTest.class, ExecuteOrderTest.class,
        AirliftOrderTest.class, BlockadeOrderTest.class, DeployOrderTest.class, NegotiateOrderTest.class, AdvanceOrderTest.class, BombOrderTest.class,
        ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class,
        ValidateMapTest.class})

/**
 * class for test suite
 */
public class AllTestSuite {

}