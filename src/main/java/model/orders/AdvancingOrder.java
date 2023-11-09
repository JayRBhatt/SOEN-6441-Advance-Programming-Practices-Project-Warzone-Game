package model.orders;

import model.Country;
import model.Player;
import model.GameCalculations.AttackLogic;
import model.GameCalculations.GameCalculation;
import utils.loggers.LogEntryBuffer;

import java.util.Objects;

/**
 * Represents the advancing order and its operational logic within the game.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class AdvancingOrder extends Order {

    /** Buffer to hold log entries for events within the game. */
    private LogEntryBuffer d_leb = new LogEntryBuffer();

    /** Singleton instance to manage game settings. */
    private GameCalculation d_settings = GameCalculation.getInstance();

    /** Strategy used for game operations. */
    private AttackLogic d_attackLogic;

    /**
     * Initializes an advance order with default settings and type.
     */
    public AdvancingOrder() {
        super();
        setOrderType("advance");
        d_attackLogic = d_settings.getStrategy();
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
            Player l_Player = getOrderDetails().getPlayer();
            Country l_from = getOrderDetails().getDeparture();
            Country l_to = getOrderDetails().getCountryWhereDeployed();
            int l_armies = getOrderDetails().getAmountOfArmy();
            if (l_Player.getNeutralPlayers().contains(l_to.getPlayer())) {
                System.out.printf("Truce between %s and %s\n", l_Player.getPlayerName(),
                        l_to.getPlayer().getPlayerName());
                l_Player.getNeutralPlayers().remove(l_to.getPlayer());
                l_to.getPlayer().getNeutralPlayers().remove(l_Player);
                return true;
            }
            if (l_Player.isCaptured(l_to) || Objects.isNull(l_to.getPlayer())) {
                l_from.depleteArmies(l_armies);
                l_to.deployArmies(l_armies);
                if (!l_Player.getOccupiedCountries().contains(l_to)) {
                    l_Player.getOccupiedCountries().add(l_to);
                }
                l_to.setPlayer(l_Player);
                System.out
                        .println("Advanced/Moved " + l_armies + " from " + l_from.getCountryName() + " to "
                                + l_to.getCountryName());
                d_leb.logAction("Advanced/Moved " + l_armies + " from " + l_from.getCountryName() + " to "
                        + l_to.getCountryName());
                return true;
            } else if (d_attackLogic.attack(l_Player, l_from, l_to, l_armies)) {
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
        Player l_Player = getOrderDetails().getPlayer();
        Country l_from = getOrderDetails().getDeparture();
        Country l_to = getOrderDetails().getCountryWhereDeployed();
        int l_armies = getOrderDetails().getAmountOfArmy();
        boolean l_success = true;
        String l_log = "Failed due to some errors\n";
        if (l_Player == null || l_to == null || l_from == null) {
            System.err.println("The information of the order is incorrect.");
            return false;
        }
        if (!l_Player.isCaptured(l_from)) {
            l_log += String.format("\t-> Country %s does not belong to player %s\n", l_from.getCountryName(),
                    l_Player.getPlayerName());
            l_success = false;
        }
        if (!l_from.isNeighbor(l_to)) {
            l_log += String.format("\t-> Destination country %s is not a neighbor of %s\n", l_to.getCountryName(),
                    l_from.getCountryName());
            l_success = false;
        }
        if (l_armies > l_from.getArmies()) {
            l_log += String.format("\t-> Attacking troop count %d is greater than available troops %d in %s", l_armies,
                    l_from.getArmies(), l_from.getCountryName());
            l_success = false;
        }
        if (!l_success) {
            System.err.println(l_log);
            d_leb.logAction(l_log);
        }
        return l_success;
    }

    /**
     * Outputs the details of the advance command.
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Advanced " + getOrderDetails().getAmountOfArmy() + " armies from "
                + getOrderDetails().getDeparture().getCountryName() + " to "
                + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        d_leb.logAction("Advanced " + getOrderDetails().getAmountOfArmy() + " armies from "
                + getOrderDetails().getDeparture().getCountryName() + " to "
                + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
    }
}