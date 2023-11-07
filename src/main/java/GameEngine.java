
import controller.GameEngineController;
import model.GamePhase;
import model.GameCalculations.DefaultAttackLogic;
import model.GameCalculations.GameCalculation;
import utils.exceptions.InvalidCommandException;
import utils.exceptions.InvalidInputException;

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

public class GameEngine {
    /**
     * gamephase instance for the state
     */
    GamePhase d_GamePhase = GamePhase.MapEditor;

    /**
     * The main method for accepting command from users to run the warzone game
     * 
     * @param args are passed to main if used in command line
     * @throws InvalidCommandException
     */

    public static void main(String args[]) throws InvalidCommandException {
        GameCalculation l_gameCalculation = GameCalculation.getInstance();
        l_gameCalculation.setStrategy(new DefaultAttackLogic());
        new GameEngine().start(l_gameCalculation);
    }

    /**
     * The function which takes the game to different phases through gameController
     * 
     * @throws InvalidCommandException
     */

    public void start(GameCalculation p_GameCalculation) throws InvalidCommandException {
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
}