import model.GamePhase;

/**
 * interface of Engine classes
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public interface Engine {

    /**
     * start method declaration
     *
     * @throws Exception if it occurs
     */
    void start() throws Exception;

    /**
     * method declaration to set game phase
     * 
     * @param p_GamePhase the game phase
     */
    void setGamePhase(GamePhase p_GamePhase);

}
