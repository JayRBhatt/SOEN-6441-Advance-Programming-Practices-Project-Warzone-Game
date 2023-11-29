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
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0 
 */
public class AggressiveStrategy extends PlayerStrategy implements Serializable {
     /**
     * LogEntryBuffer instance
     */
    private LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    /**
     * Ordered list based on number of armies
     */
    private List<Country> orderedList;
    
    
    /**
     * A function to create the commands for deploying, advancing and bombing for an Aggressive strategy player
     *
     * @return null if empty
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
    /**
     * Create a list of sorted countries with respect to their army strength
     */
    private void createAndOrderCountryList() {
        orderedList = d_Player.getOccupiedCountries()
                .stream()
                .sorted(Comparator.comparingInt(Country::getArmies).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Generates the deploy command
     */
    private void deployCommand() {
        Country l_strongCountry = orderedList.get(0);
        int l_armiesReinforce = d_Player.getAdditionalArmies();

        List<String>l_commands = Arrays.asList("deploy",l_strongCountry.getCountryName(),
                String.valueOf(l_armiesReinforce));
        String[]l_commandsArr =l_commands.toArray(new String[0]);
        Order l_deployOrder = new DeployOrder();
        l_deployOrder.setOrderDetails(OrderCreator.GenerateDeployOrderInfo(l_commandsArr, d_Player));
        OrderIssue.Commands = l_deployOrder.getOrderDetails().getCommand();
        d_LogEntryBuffer
                .logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
        d_Player.deployOrder();
    }
     /**
     * Bomb an enemy if bomb card exists else attack the enemy with strongest country
     *
     * @return true if bombing order was successful or else it attacks with strongest army
     */
    private boolean bombOrAttack() {
        boolean hasBombCard = d_Player.getPlayersCards().stream()
                .anyMatch(card -> card.getCardsType().equals(CardsType.BOMB));

        if (hasBombCard) {
            Country enemyWithHighTroops = getEnemyWithHighTroops();
            if (enemyWithHighTroops != null) {
                List<String> commands = Arrays.asList("bomb", enemyWithHighTroops.getCountryName());
                String[]l_commandsArr = commands.toArray(new String[0]);
                Order l_bombOrder = new BombingOrder();
                l_bombOrder.setOrderDetails(OrderCreator.GenerateBombOrderInfo(l_commandsArr, d_Player));
                OrderIssue.Commands = l_bombOrder.getOrderDetails().getCommand();
                d_LogEntryBuffer.logAction(
                        String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                d_Player.deployOrder();
                return true;
            }
        }

        return attackWithStrongestCountry();
    }
   /**
 * Retrieves the neighboring country with the highest number of troops that is occupied by an enemy player.
 * This method identifies countries that are neighbors to the countries currently occupied by the player,
 * then filters out those that are occupied by the player themselves. Among these enemy-occupied countries,
 * the one with the largest army is selected and returned.
 * 
 * @return Country object representing the enemy country with the highest troop count among the neighboring countries.
 *         Returns null if there are no enemy-occupied neighboring countries or if the player occupies no countries.
 */
    private Country getEnemyWithHighTroops() {
        return d_Player.getOccupiedCountries().stream()
                .flatMap(country -> country.getNeighbors().stream())
                .filter(country -> !d_Player.getPlayerName().equals(country.getPlayer().getPlayerName()))
                .max(Comparator.comparingInt(Country::getArmies))
                .orElse(null);
    }
/**
 * Attempts to initiate an attack from the strongest country occupied by the player.
 * This method iterates through a list of countries,
 * and for each country, it identifies the first neighboring country occupied by an enemy player.
 * If such a neighboring enemy country is found, and the player's country has armies, an attack order is created
 * and issued. The method tracks if at least one attack order has been successfully issued during the iteration.
 * 
 * @return true if at least one attack order is successfully issued; false otherwise
 */
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
     /**
     * If enemy doesnt exist to the strongest country
     * Move armies to the next strongest country that has an enemy
     *
     * @return true if a valid move and advance order are issued, false otherwise.
     */
    private boolean moveToSelf() {
        Country l_fromCountry = orderedList.get(0);

        if (l_fromCountry.getArmies() <= 0) {
            return false;
        }

        List<Country> neighborsWithEnemies = getNeighborsWithEnemies(l_fromCountry);
        Country toCountry = neighborsWithEnemies.stream()
                .max(Comparator.comparingInt(Country::getArmies))
                .orElse(null);

        if (toCountry != null) {
            List<String> commands = Arrays.asList("advance", l_fromCountry.getCountryName(), toCountry.getCountryName(),
                    String.valueOf(l_fromCountry.getArmies()));
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
 /**
 * Retrieves a list of neighboring countries of the specified fromCountry that have
 * at least one enemy player's army adjacent to them.
 *
 * @param p_fromCountry The country for which neighboring countries with enemies are to be found.
 * @return A list of neighboring countries with enemies, or an empty list if none are found.
 */
    private List<Country> getNeighborsWithEnemies(Country p_fromCountry) {
        return p_fromCountry.getNeighbors().stream()
                .filter(country -> country.getNeighbors().stream()
                        .anyMatch(neighbor -> !country.getPlayer().getPlayerName()
                                .equals(neighbor.getPlayer().getPlayerName())))
                .collect(Collectors.toList());
    }
}
