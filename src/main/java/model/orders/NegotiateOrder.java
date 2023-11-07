package model.orders;

import model.CardsType;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * This class extends Order to provide functionality for executing a negotiate
 * order.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
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
        setOrderType("negotiate");
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
        Player l_neutralPlayer = getOrderDetails().getNeutralPlayer();
        if (validateCommand()) {
            System.out.println("Executing order: " + getOrderType() + " with " + l_neutralPlayer.getPlayerName());
            Player l_player = getOrderDetails().getPlayer();
            l_player.addNeutralPlayers(l_neutralPlayer);
            l_neutralPlayer.addNeutralPlayers(l_player);
            l_player.removeCard(CardsType.DIPLOMACY);
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
        Player l_player = getOrderDetails().getPlayer();
        Player l_neutralPlayer = getOrderDetails().getNeutralPlayer();

        if (!l_player.checkIfCardAvailable(CardsType.DIPLOMACY)) {
            System.err.println("The player lacks the required diplomacy card.");
            d_logEntryBuffer.logAction("The player lacks the required diplomacy card.");
            return false;
        }

        if (l_neutralPlayer == null) {
            System.err.println("Invalid neutral player specified.");
            d_logEntryBuffer.logAction("Invalid neutral player specified.");
            return false;
        }

        if (!d_gameMap.getGamePlayers().containsKey(l_neutralPlayer.getPlayerName())) {
            System.err.println("Non-existent neutral player specified.");
            d_logEntryBuffer.logAction("Non-existent neutral player specified.");
            return false;
        }
        return true;
    }

    /**
     * Displays the negotiation order command for user awareness.
     */
    
     @Override
    public void printOrderCommand() {
        String l_message = "Negotiation established with " + getOrderDetails().getNeutralPlayer().getPlayerName() + ".";
        System.out.println(l_message);
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        d_logEntryBuffer.logAction(l_message);
    }
}
