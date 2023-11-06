package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * Represents an order to execute a blockade, which triples the number of armies
 * in a country and makes it neutral.
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
        setType("blockade");
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
        Player l_player = getOrderInfo().getPlayer();
        Country l_country = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            l_country.setArmies(l_country.getArmies() * 3);
            l_player.getCapturedCountries().remove(l_country);
            l_country.setPlayer(null);
            System.out.println("Executing order: " + getType() + " on " + l_country.getName());
            l_player.removeCard(CardType.BLOCKADE);
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
        Player l_player = getOrderInfo().getPlayer();
        Country l_country = getOrderInfo().getTargetCountry();

        if (l_player == null) {
            System.err.println("Invalid player for the blockade order.");
            d_logEntryBuffer.logInfo("Invalid player for the blockade order.");
            return false;
        }

        if (!l_country.isOwnedBy(l_player)) {
            System.err.println("The country is not under the player's control.");
            d_logEntryBuffer.logInfo("The country is not under the player's control.");
            return false;
        }
        if (!l_player.checkIfCardAvailable(CardType.BLOCKADE)) {
            System.err.println("The blockade card is not in the player's possession.");
            d_logEntryBuffer.logInfo("The blockade card is not in the player's possession.");
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
        String l_message = "Blockade applied to " + getOrderInfo().getTargetCountry().getName() +
                " by " + getOrderInfo().getPlayer().getName();
        System.out.println(l_message);
        System.out.println("------------------------------------------------------------------------------------");
        d_logEntryBuffer.logInfo(l_message);
    }
}
