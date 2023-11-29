package model.Calculation.playerStrategy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import model.CardsType;
import model.Country;
import model.GameMap;
import model.orders.AdvancingOrder;
import model.orders.BombingOrder;
import model.orders.DeployOrder;
import model.orders.Order;
import model.orders.OrderCreator;
import services.OrderIssue;
import utils.loggers.LogEntryBuffer;

/**
 * A class to implement the Aggressive strategy for a player
 *
 * @author Madhav Anadkat
 */
public class AggressiveStrategy extends PlayerStrategy implements Serializable {
	
	/**
	 * The log entry buffer for recording game actions.
	 */
    private LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    /**
     * The ordered list of countries for processing game commands.
     */
    private List<Country> orderedList;
    
    /**
     * Initiates aggressive player's turn
     * Issues orders, including deploying armies,
     * bombing or attacking, and reinforcing own territories
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        d_LogEntryBuffer.logAction("Issuing Orders for the Aggressive Player - " + d_Player.getPlayerName());
        if (hasCapturedCountries()) {
            createAndOrderCountryList();
            deployCommand();
            if (bombOrAttack()) {
                return "pass";
            }
            moveToSelf();
        }
        return "pass";
    }

    private boolean hasCapturedCountries() {
        return !d_Player.getOccupiedCountries().isEmpty();
    }

    private void createAndOrderCountryList() {
        orderedList = d_Player.getOccupiedCountries()
                .stream()
                .sorted(Comparator.comparingInt(Country::getArmies).reversed())
                .collect(Collectors.toList());
    }

    private void deployCommand() {
        Country strongCountry = orderedList.get(0);
        int armiesReinforce = d_Player.getAdditionalArmies();

        List<String> commands = Arrays.asList("deploy", strongCountry.getCountryName(),
                String.valueOf(armiesReinforce));
        String[] commandsArr = commands.toArray(new String[0]);
        Order l_deployOrder = new DeployOrder();
        l_deployOrder.setOrderDetails(OrderCreator.GenerateDeployOrderInfo(commandsArr, d_Player));
        OrderIssue.Commands = l_deployOrder.getOrderDetails().getCommand();
        d_LogEntryBuffer
                .logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
        d_Player.deployOrder();
    }

    private boolean bombOrAttack() {
        boolean hasBombCard = d_Player.getPlayersCards().stream()
                .anyMatch(card -> card.getCardsType().equals(CardsType.BOMB));

        if (hasBombCard) {
            Country enemyWithHighTroops = getEnemyWithHighTroops();
            if (enemyWithHighTroops != null) {
                List<String> commands = Arrays.asList("bomb", enemyWithHighTroops.getCountryName());
                String[] commandsArr = commands.toArray(new String[0]);
                Order l_bombOrder = new BombingOrder();
                l_bombOrder.setOrderDetails(OrderCreator.GenerateBombOrderInfo(commandsArr, d_Player));
                OrderIssue.Commands = l_bombOrder.getOrderDetails().getCommand();
                d_LogEntryBuffer.logAction(
                        String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                d_Player.deployOrder();
                return true;
            }
        }

        return attackWithStrongestCountry();
    }

    private Country getEnemyWithHighTroops() {
        return d_Player.getOccupiedCountries().stream()
                .flatMap(country -> country.getNeighbors().stream())
                .filter(country -> !d_Player.getPlayerName().equals(country.getPlayer().getPlayerName()))
                .max(Comparator.comparingInt(Country::getArmies))
                .orElse(null);
    }

    private boolean attackWithStrongestCountry() {
        boolean flag = false;
        Country fromCountry = null;
        Country toCountry = null;

        for (Country country : orderedList) {
            fromCountry = country;
            Country enemyCountry = country.getNeighbors()
                    .stream()
                    .filter(neighbor -> !d_Player.getPlayerName().equals(neighbor.getPlayer().getPlayerName()))
                    .findFirst()
                    .orElse(null);
            toCountry = enemyCountry;

            if (enemyCountry != null && fromCountry.getArmies() > 0) {
                List<String> commands = Arrays.asList("advance", fromCountry.getCountryName(),
                        toCountry.getCountryName(),
                        String.valueOf(fromCountry.getArmies()));
                String[] commandsArr = commands.toArray(new String[0]);
                Order l_advanceOrder = new AdvancingOrder();
                l_advanceOrder.setOrderDetails(OrderCreator.GenerateAdvanceOrderInfo(commandsArr, d_Player));
                OrderIssue.Commands = l_advanceOrder.getOrderDetails().getCommand();
                d_LogEntryBuffer.logAction(
                        String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                d_Player.deployOrder();
                flag = true;
            }
        }

        return flag;
    }

    private boolean moveToSelf() {
        Country fromCountry = orderedList.get(0);

        if (fromCountry.getArmies() <= 0) {
            return false;
        }

        List<Country> neighborsWithEnemies = getNeighborsWithEnemies(fromCountry);
        Country toCountry = neighborsWithEnemies.stream()
                .max(Comparator.comparingInt(Country::getArmies))
                .orElse(null);

        if (toCountry != null) {
            List<String> commands = Arrays.asList("advance", fromCountry.getCountryName(), toCountry.getCountryName(),
                    String.valueOf(fromCountry.getArmies()));
            String[] commandsArr = commands.toArray(new String[0]);
            Order l_advanceOrder = new AdvancingOrder();
            l_advanceOrder.setOrderDetails(OrderCreator.GenerateAdvanceOrderInfo(commandsArr, d_Player));
            OrderIssue.Commands = l_advanceOrder.getOrderDetails().getCommand();
            d_LogEntryBuffer.logAction(
                    String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
            d_Player.deployOrder();
            return true;
        }

        return false;
    }

    private List<Country> getNeighborsWithEnemies(Country fromCountry) {
        return fromCountry.getNeighbors().stream()
                .filter(country -> country.getNeighbors().stream()
                        .anyMatch(neighbor -> !country.getPlayer().getPlayerName()
                                .equals(neighbor.getPlayer().getPlayerName())))
                .collect(Collectors.toList());
    }
}
