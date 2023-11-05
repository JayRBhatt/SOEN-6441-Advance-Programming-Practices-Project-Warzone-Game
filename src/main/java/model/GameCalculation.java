package model;

public class GameCalculation {
    /**
     * Game settings object
     */
    private static GameCalculation calculation;
    /**
     * Game strategy object
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
     * @return game settings object
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
     * @return the game strategy chosen
     */
    public AttackLogic getStrategy() {
        return d_AttackLogic;
    }

    /**
     * Setter for game strategy
     *
     * @param p_Strategy the game strategy chosen
     */
    public void setStrategy(AttackLogic p_Strategy) {
        d_AttackLogic = p_Strategy;
    }    
}
