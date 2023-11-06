package main.java.model.orders;

import model.Country;
import model.GameSettings;
import model.Player;
import model.strategy.GameStrategy;
import utils.logger.LogEntryBuffer;

import java.util.Objects;

/**
 * Represents the advance order and its operational logic within the game.
 */
public class AdvanceOrder extends Order {

    /** Buffer to hold log entries for events within the game. */
    private LogEntryBuffer d_leb = new LogEntryBuffer();

    /** Singleton instance to manage game settings. */
    private GameSettings d_settings = GameSettings.getInstance();

    /** Strategy used for game operations. */
    private GameStrategy d_gameStrategy;

    /**
     * Initializes an advance order with default settings and type.
     */
    public AdvanceOrder() {
        super();
        setType("advance");
        d_gameStrategy = d_settings.getStrategy();
    }

    /**
     * Executes the advance command by moving or attacking with armies.
     * The operation differs based on the ownership and status of the involved
     * countries.
     * It respects truces and performs actions in the next turn if a negotiation is
     * in place.
     *
     * @return True if the advance command is executed or skipped, False otherwise.
     */
    @Override
    public boolean execute() {
        if (validateCommand()) {
            Player l_player = getOrderInfo().getPlayer();
            Country l_from = getOrderInfo().getDeparture();
            Country l_to = getOrderInfo().getDestination();
            int l_armies = getOrderInfo().getNumberOfArmy();
            if (l_player.getNeutralPlayers().contains(l_to.getPlayer())) {
                System.out.printf("Truce between %s and %s\n", l_player.getName(), l_to.getPlayer().getName());
                l_player.getNeutralPlayers().remove(l_to.getPlayer());
                l_to.getPlayer().getNeutralPlayers().remove(l_player);
                return true;
            }
            if (l_player.isCaptured(l_to) || Objects.isNull(l_to.getPlayer())) {
                l_from.depleteArmies(l_armies);
                l_to.deployArmies(l_armies);
                if (!l_player.getCapturedCountries().contains(l_to)) {
                    l_player.getCapturedCountries().add(l_to);
                }
                l_to.setPlayer(l_player);
                System.out
                        .println("Advanced/Moved " + l_armies + " from " + l_from.getName() + " to " + l_to.getName());
                d_leb.logInfo("Advanced/Moved " + l_armies + " from " + l_from.getName() + " to " + l_to.getName());
                return true;
            } else if (d_gameStrategy.attack(l_player, l_from, l_to, l_armies)) {
                return true;
            }
        }
        System.out.println("---------------------------------------------------");
        return false;
    }

    /**
     * Checks if the advance command is applicable based on current game state.
     *
     * @return True if the command is valid, False otherwise.
     */
    @Override
    public boolean validateCommand() {
        Player l_player = getOrderInfo().getPlayer();
        Country l_from = getOrderInfo().getDeparture();
        Country l_to = getOrderInfo().getDestination();
        int l_armies = getOrderInfo().getNumberOfArmy();
        boolean l_success = true;
        String l_log = "Failed due to some errors\n";
        if (l_player == null || l_to == null || l_from == null) {
            System.err.println("Invalid order information.");
            return false;
        }
        if (!l_player.isCaptured(l_from)) {
            l_log += String.format("\t-> Country %s does not belong to player %s\n", l_from.getName(),
                    l_player.getName());
            l_success = false;
        }
        if (!l_from.isNeighbor(l_to)) {
            l_log += String.format("\t-> Destination country %s is not a neighbor of %s\n", l_to.getName(),
                    l_from.getName());
            l_success = false;
        }
        if (l_armies > l_from.getArmies()) {
            l_log += String.format("\t-> Attacking troop count %d is greater than available troops %d in %s", l_armies,
                    l_from.getArmies(), l_from.getName());
            l_success = false;
        }
        if (!l_success) {
            System.err.println(l_log);
            d_leb.logInfo(l_log);
        }
        return l_success;
    }

    /**
     * Outputs the details of the advance command.
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Advanced " + getOrderInfo().getNumberOfArmy() + " armies from "
                + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        d_leb.logInfo("Advanced " + getOrderInfo().getNumberOfArmy() + " armies from "
                + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
    }
}
