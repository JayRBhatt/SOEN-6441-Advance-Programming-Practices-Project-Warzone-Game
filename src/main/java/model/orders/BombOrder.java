package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * Manages the logic for the bomb order in the game.
 * It allows a player to bomb a country, reducing its army count by half.
 * The action is not permitted on the player's own countries or on neutral
 * countries with a truce.
 */
public class BombOrder extends Order {

    /** Reference to the game map to access the global state. */
    private GameMap d_gameMap;
    /** Log entry buffer to record events and states in the game. */
    private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Constructs a new bomb order and initializes the game map instance.
     */
    public BombOrder() {
        super();
        setType("bomb");
        d_gameMap = GameMap.getInstance();
    }

    /**
     * Executes the bomb order on a target country.
     * It halves the number of armies in the target country if the order is valid.
     *
     * @return True if execution is successful, false otherwise.
     */
    @Override
    public boolean execute() {
        Player l_player = getOrderInfo().getPlayer();
        Country l_targetCountry = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            System.out.println("Executing order: " + getType() + " on " + l_targetCountry.getName());
            int l_armies = l_targetCountry.getArmies();
            int l_newArmies = Math.max(l_armies / 2, 0);
            l_targetCountry.setArmies(l_newArmies);
            l_player.removeCard(CardType.BOMB);
            return true;
        }
        return false;
    }

    /**
     * Validates the bomb order based on game rules.
     * A player cannot bomb their own countries, countries with a truce,
     * or non-adjacent countries.
     *
     * @return True if the order is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_player = getOrderInfo().getPlayer();
        Country l_targetCountry = getOrderInfo().getTargetCountry();

        if (l_player == null) {
            System.err.println("Invalid player for the bomb order.");
            d_logEntryBuffer.logInfo("Invalid player for the bomb order.");
            return false;
        }

        if (!l_player.checkIfCardAvailable(CardType.BOMB)) {
            System.err.println("The bomb card is not available for the player.");
            d_logEntryBuffer.logInfo("The bomb card is not available for the player.");
            return false;
        }

        if (l_player.getCapturedCountries().contains(l_targetCountry)) {
            System.err.println("A player cannot bomb their own territory.");
            d_logEntryBuffer.logInfo("A player cannot bomb their own territory.");
            return false;
        }

        boolean l_isAdjacent = false;
        for (Country l_country : l_player.getCapturedCountries()) {
            if (l_country.isNeighbor(l_targetCountry)) {
                l_isAdjacent = true;
                break;
            }
        }

        if (!l_isAdjacent) {
            System.err.println("The target country is not adjacent to the player's territories.");
            d_logEntryBuffer.logInfo("The target country is not adjacent to the player's territories.");
            return false;
        }

        if (l_player.getNeutralPlayers().contains(l_targetCountry.getPlayer())) {
            System.err.println("Cannot bomb due to truce with " + l_targetCountry.getPlayer().getName());
            d_logEntryBuffer.logInfo("Cannot bomb due to truce with " + l_targetCountry.getPlayer().getName());
            return false;
        }

        return true;
    }

    /**
     * Outputs the details of the bomb order.
     */
    @Override
    public void printOrderCommand() {
        String l_message = "Bomb order by " + getOrderInfo().getPlayer().getName() +
                " targeting " + getOrderInfo().getTargetCountry().getName();
        System.out.println(l_message);
        System.out.println("-------------------------------------------------------------------");
        d_logEntryBuffer.logInfo(l_message);
    }
}
