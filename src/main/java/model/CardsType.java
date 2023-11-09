package model;
import java.util.Random;
/**
 * An enum containing all the card types
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public enum CardsType {
    /**
     * Bomb Card Type
     */
    BOMB,
    /**
     * Blockade Card Type
     */
    BLOCKADE,
    /**
     * Airlift Card Type
     */
    AIRLIFT,
    /**
     * Diplomacy Card Type
     */
    DIPLOMACY;

    /**
     * This method assigns a Random Card from the Enum of Card types
     *
     * @return The random Card Name
     */
    public static CardsType getRandomCard() {
        Random d_Random = new Random();
        CardsType d_Type = values()[d_Random.nextInt(values().length)];
        return d_Type;
    }
}
