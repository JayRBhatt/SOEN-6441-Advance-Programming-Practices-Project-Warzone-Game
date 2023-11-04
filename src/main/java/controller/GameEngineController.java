package controller;

import model.GamePhase;

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

public interface GameEngineController {
    /**
     * The start method of Game Controller
     *
     * @param p_GamePhase holding the current game phase
     * @return each phase return the next game phase after it
     * @throws Exception If an issue occurred
     */
    GamePhase start(GamePhase p_GamePhase) throws Exception;
}
