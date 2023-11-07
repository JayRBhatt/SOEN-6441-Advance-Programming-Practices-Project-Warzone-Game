package model.orders;

import model.CardsType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * Represents an order to execute a blockade, which triples the number of armies
 * in a country and makes it neutral.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class BlockadeOrder extends Order {
    private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
    /**
     * Reference to the singleton instance of the game map.
     */
    private final GameMap d_gameMap;

    /**
     * Initializes a new instance of the Blockade Order.
     */
    public BlockadeOrder() {
        super();
        setOrderType("blockade");
        d_gameMap = GameMap.getInstance();
    }

    /**
     * Executes the blockade order, tripling the number of armies in the targeted
     * country and relinquishing control.
     *
     * @return True if the execution is successful, false otherwise.
     */
    @Override
    public boolean execute() {
        Player l_player = getOrderDetails().getPlayer();
        Country l_country = getOrderDetails().getTargetCountry();
        if (validateCommand()) {
            l_country.setArmies(l_country.getArmies() * 3);
            l_player.getOccupiedCountries().remove(l_country);
            l_country.setPlayer(null);
            System.out.println("Executing order: " + getOrderType() + " on " + l_country.getCountryName());
            l_player.removeCard(CardsType.BLOCKADE);
            return true;
        }
        return false;
    }

    /**
     * Validates the conditions required to execute the blockade order, ensuring
     * that the country belongs to the player and the card is available.
     *
     * @return True if the order can be executed, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_player = getOrderDetails().getPlayer();
        Country l_country = getOrderDetails().getTargetCountry();

        if (l_player == null) {
            System.err.println("Invalid player for the blockade order.");
            d_logEntryBuffer.logAction("Invalid player for the blockade order.");
            return false;
        }

        if (l_country.getPlayer() != l_player) {
            System.err.println("The country is not under the player's control.");
            d_logEntryBuffer.logAction("The country is not under the player's control.");
            return false;
        }
        if (!l_player.checkIfCardAvailable(CardsType.BLOCKADE)) {
            System.err.println("The blockade card is not in the player's possession.");
            d_logEntryBuffer.logAction("The blockade card is not in the player's possession.");
            return false;
        }
        return true;
    }

    /**
     * Outputs the details of the blockade order for record-keeping and player
     * information.
     */
    @Override
    public void printOrderCommand() {
        String l_message = "Blockade applied to " + getOrderDetails().getTargetCountry().getCountryName() +
                " by " + getOrderDetails().getPlayer().getPlayerName();
        System.out.println(l_message);
        System.out.println("------------------------------------------------------------------------------------");
        d_logEntryBuffer.logAction(l_message);
    }
}
