package controller;

import services.*;
import utils.InvalidCommandException;

/**
 * A class that manages the flow of different phases of the warzone game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */

public class GameEngineController {
    /**
     * @param p_GamePhaseID holding the ID of the current game phase
     * @throws InvalidCommandException
     */

    public void controller(int p_GamePhaseID) throws InvalidCommandException {

        switch (p_GamePhaseID) {
            case 1:
                new MapEditor().mapEdit(p_GamePhaseID);
                break;
            case 2:
                new GamePlayBegins().runPhase(p_GamePhaseID);
                break;

            case 3:
                new Reinforcements().start(p_GamePhaseID);
                break;

            case 4:
                new OrderIssue().begin(p_GamePhaseID);
                break;

            case 5:
                new ExecuteOrder().startExecuteOrder(p_GamePhaseID);
                break;

            case 6:
                new ExitService().exitGame(p_GamePhaseID);
                break;

            default:
                System.exit(0);
        }
    }

}
