package model.GameCalculations;
/**
 * An class that contains the Game calculations
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class GameCalculation {
    /**
     * Game calculation object
     */
    private static GameCalculation calculation;
    /**
     * Game Attacklogic object
     */
    private AttackLogic d_AttackLogic;
    /**
     * The Attacker probability
     */
    public final double ATTACKER_PROBABILITY = 0.6;
    /**
     * The Defender probability
     */
    public final double DEFENDER_PROBABILITY = 0.7;

    /**
     * Constructor for Game Calculation
     */
    private GameCalculation() {
    }

    /**
     * Method to get the instance of GameCalculation
     *
     * @return game calculation object
     */
    public static GameCalculation getInstance() {
        if (calculation==null) {
            calculation = new GameCalculation();
        }
        return calculation;
    }

    /**
     * Getter for strategy
     *
     * @return the Attack logic chosen
     */
    public AttackLogic getStrategy() {
        return d_AttackLogic;
    }

    /**
     * Setter for Attack logic
     *
     * @param p_Strategy the game strategy chosen
     */
    public void setStrategy(AttackLogic p_Strategy) {
        d_AttackLogic = p_Strategy;
    }    
}
