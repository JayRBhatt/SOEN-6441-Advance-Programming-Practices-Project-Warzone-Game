package main.java.model.orders;

import model.CardType;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * This class extends Order to provide functionality for executing a negotiate
 * order.
 */
public class NegotiateOrder extends Order {
    private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
    /**
     * Singleton instance of GameMap.
     */
    private final GameMap d_gameMap;

    /**
     * Constructs a new negotiate order and sets the order type to "negotiate".
     */
    public NegotiateOrder() {
        super();
        setType("negotiate");
        d_gameMap = GameMap.getInstance();
    }

    /**
     * Executes the negotiate order by setting the involved players as neutral
     * towards each other.
     *
     * @return True if the execution is successful, otherwise false.
     */
    @Override
    public boolean execute() {
        Player l_neutralPlayer = getOrderInfo().getNeutralPlayer();
        if (validateCommand()) {
            System.out.println("Executing order: " + getType() + " with " + l_neutralPlayer.getName());
            Player l_player = getOrderInfo().getPlayer();
            l_player.addNeutralPlayers(l_neutralPlayer);
            l_neutralPlayer.addNeutralPlayers(l_player);
            l_player.removeCard(CardType.DIPLOMACY);
            return true;
        }
        return false;
    }

    /**
     * Validates whether the player has a diplomacy card and the specified neutral
     * player is valid and exists within the game.
     *
     * @return True if the order is valid, otherwise false.
     */
    @Override
    public boolean validateCommand() {
        Player l_player = getOrderInfo().getPlayer();
        Player l_neutralPlayer = getOrderInfo().getNeutralPlayer();

        if (!l_player.checkIfCardAvailable(CardType.DIPLOMACY)) {
            System.err.println("The player lacks the required diplomacy card.");
            d_logEntryBuffer.logInfo("The player lacks the required diplomacy card.");
            return false;
        }

        if (l_neutralPlayer == null) {
            System.err.println("Invalid neutral player specified.");
            d_logEntryBuffer.logInfo("Invalid neutral player specified.");
            return false;
        }

        if (!d_gameMap.getPlayers().containsKey(l_neutralPlayer.getName())) {
            System.err.println("Non-existent neutral player specified.");
            d_logEntryBuffer.logInfo("Non-existent neutral player specified.");
            return false;
        }
        return true;
    }

    /**
     * Displays the negotiation order command for user awareness.
     */
    @Override
    public void printOrderCommand() {
        String l_message = "Negotiation established with " + getOrderInfo().getNeutralPlayer().getName() + ".";
        System.out.println(l_message);
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        d_logEntryBuffer.logInfo(l_message);
    }
}
