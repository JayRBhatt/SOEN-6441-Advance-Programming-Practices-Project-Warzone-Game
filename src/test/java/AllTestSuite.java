import services.ExecuteOrderTest;
import services.IssueOrderTest;
import services.ReinforcementTest;
import utils.maputils.ValidateMapTest;
import model.ContinentTest;
import model.CountryTest;
import model.GameMapTest;
import model.GamePhaseTest;
import model.PlayerTest;
import model.order.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

 @RunWith(Suite.class)

/**
 * Run all test cases
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
@Suite.SuiteClasses({
        IssueOrderTest.class, ReinforcementTest.class, ExecuteOrderTest.class, TournamentGameModeTest.class, GamePhaseTest.class, GameEngineTest.class,
        AirliftingOrderTest.class, OrderOfBlockadeTest.class, DeployOrderTest.class, NegotiatingOrderTest.class, AdvancingOrderTest.class, BombingOrderTest.class,
        ValidateMapTest.class,ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class,
        })

/**
 * class for test suite
 */
public class AllTestSuite {

}