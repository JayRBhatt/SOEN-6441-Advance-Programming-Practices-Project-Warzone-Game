package model.Calculation.playerStrategy;

import model.Player;

/**
 * Abstract class for strategies of player.
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */

public abstract class PlayerStrategy {
    /**
     * player
     */
    static Player d_Player;

    /**
     * Default constructor
     */
    PlayerStrategy() {

    }

   /**
   * An abstract class representing a player's gameplay strategy in a game.
   * Subclasses of this class implement specific strategies, such as "human,"
   * "random," "benevolent," "aggressive," and "cheater."
 */
    public abstract String createCommand();
  
    /**
     * A factory method that returns an instance of the respective player strategy class
     * based on the provided strategy name.
     *
     * @param p_Strategy The name of the desired player strategy.
     * @return An instance of the corresponding player strategy class.
     * @throws IllegalStateException If the provided strategy name is not valid.
     */
    public static PlayerStrategy getStrategy(String p_Strategy) {
        switch (p_Strategy) {
            case "human": {
                return new HumanStrategy();
            }
            case "random": {
                return new RandomStrategy();
            }
            case "benevolent": {
                return new BenevolentStrategy();
            }
            case "aggressive": {
                return new AggressiveStrategy();
            }
            case "cheater": {
                return new CheaterStrategy();
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
