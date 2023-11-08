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

// @RunWith(Suite.class)

/**
 * Run all test cases
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
@Suite.SuiteClasses({
        IssueOrderTest.class, ReinforcementTest.class, ExecuteOrderTest.class,
        AirliftingOrderTest.class, OrderOfBlockadeTest.class, DeployOrderTest.class, NegotiatingOrderTest.class, AdvancingOrderTest.class, BombingOrderTest.class,
        ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class,
        ValidateMapTest.class})

/**
 * class for test suite
 */
public class AllTestSuite {

}