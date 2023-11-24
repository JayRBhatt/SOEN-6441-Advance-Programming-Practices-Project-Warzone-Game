
import controller.GameEngineController;
import model.GamePhase;
import model.Calculation.gameCalculation.DefaultAttackLogic;
import model.Calculation.gameCalculation.GameCalculation;
import utils.exceptions.InvalidCommandException;
import utils.exceptions.InvalidInputException;
import utils.loggers.LogEntryBuffer;

/**
 * A class that starts the game with the first phase
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */

public class GameEngine implements Engine {
    /**
     * gamephase instance for the state
     */
    GamePhase d_GamePhase = GamePhase.MapEditor;
    /**
     * Creating Logger Observable
     * Single Instance needs to be maintained (Singleton)
     */
    private static LogEntryBuffer d_LogEntryBuffer;

    /**
     * constructor for game engine
     */

    public GameEngine() {
        GameCalculation l_gameCalculation = GameCalculation.getInstance();
        l_gameCalculation.setStrategy(new DefaultAttackLogic());
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
        d_LogEntryBuffer.clear();

    }

    /**
     * The function which takes the game to different phases through gameController
     * 
     * @param p_GameCalculation
     * @throws InvalidCommandException when something failes
     */

    public void start() throws InvalidCommandException {
        while (!d_GamePhase.equals(GamePhase.ExitGame)) {
            try {
                GameEngineController l_GameController = d_GamePhase.getController();
                if (l_GameController == null) {
                    System.err.println("No Controller found for the current phase.");
                    return;
                }
                d_GamePhase = l_GameController.start(d_GamePhase);
                System.out.println("You have entered the " + d_GamePhase + " Phase.");
                System.out.println(
                        "-----------------------------------------------------------------------------------------");

            } catch (InvalidInputException | InvalidCommandException p_Exception) {
                System.err.println(p_Exception.getMessage());

            } catch (Exception p_Exception) {
                p_Exception.printStackTrace();

                break;
            }
        }
    }

    /**
     * method to set game phase
     * 
     * @param p_GamePhase the game phase
     */
    public void setGamePhase(GamePhase p_GamePhase) {
        d_GamePhase = p_GamePhase;
    }
}