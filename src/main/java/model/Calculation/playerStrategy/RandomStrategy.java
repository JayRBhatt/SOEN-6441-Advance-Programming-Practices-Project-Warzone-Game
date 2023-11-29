package model.Calculation.playerStrategy;

import services.OrderIssue;
import model.Cards;
import model.Country;
import model.GameMap;
import model.Player;
import model.orders.*;
import utils.loggers.LogEntryBuffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * Random Strategy class, taking random commands for tournament mode.
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class RandomStrategy extends PlayerStrategy implements Serializable {
    /**
     * Random variable
     */
    private static final Random d_Random = new Random();
    /**
     * GameMap instance
     */
    private static GameMap d_GameMap;
    /**
     * Logentry buffer instance
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Get a random player other than itself
     *
     * @param p_Player current player
     * @return Random Player
     */
    protected Player getRandomPlayer(Player p_Player) {
        List<Country> l_Enemies = d_Player.getOccupiedCountries().stream()
                .flatMap(country -> country.getNeighbors().stream())
                .filter(country -> !country.getPlayer().getPlayerName().equals(d_Player.getPlayerName()))
                .collect(Collectors.toList());
        if (l_Enemies.isEmpty()) {
            int l_Random = d_Random.nextInt(l_Enemies.size());
            return l_Enemies.get(l_Random).getPlayer();
        }
        return null;
    }

    /**
     * Get Random Country not belonging to the player
     *
     * @param p_Player current player
     * @return Random country
     */
    protected Country getRandomUnconqueredCountry(Player p_Player) {
        Country l_RandomCountry = null;
        int l_totalCountries = d_GameMap.getCountries().size();
        int l_occupiedCountries = p_Player.getOccupiedCountries().size();
    
        if (l_totalCountries > 0 && l_occupiedCountries <l_totalCountries) {
            do {
                int l_randomIndex = d_Random.nextInt(l_totalCountries);
                l_RandomCountry = (Country) d_GameMap.getCountries().values().toArray()[l_randomIndex];
            } while (l_RandomCountry.getPlayer().equals(p_Player));
        }
        return l_RandomCountry;
    }

    /**
     * Random country belonging to the player
     *
     * @param p_Player current player
     * @return random country
     */

    protected Country getRandomConqueredCountry(Player p_Player) {
        List<Country> l_occupiedCountries = p_Player.getOccupiedCountries();
    
        if (!l_occupiedCountries.isEmpty()) {
            int l_randomIndex = new Random().nextInt(l_occupiedCountries.size());
            return l_occupiedCountries.get(l_randomIndex);
        }
    
        return null;
    }

    /**
     * Get the random neighbor of the country
     *
     * @param p_CurrentCountry current country
     * @return random neighbor
     */
    protected Country getRandomNeighbor(Country p_CurrentCountry) {
        if (Objects.isNull(p_CurrentCountry) || p_CurrentCountry.getNeighbors().size() == 0) {
            return null;
        }
        int l_Index = d_Random.nextInt(p_CurrentCountry.getNeighbors().size());
        return (Country) p_CurrentCountry.getNeighbors().toArray()[l_Index];
    }

    /**
     * Create the orders in random fashion
     *
     * @return command, the orders on creation
     */
    public String createCommand() {
        d_GameMap = GameMap.getInstance();
        d_Player = d_GameMap.getCurrentPlayer();
        int l_Random = d_Random.nextInt(11);
        Country l_RandomCountry = getRandomConqueredCountry(d_Player);
    
        switch (l_Random) {
            case 0:
            case 1:
            case 2:
            case 3:
                return handleDeployCommand(l_RandomCountry);
            case 4:
            case 5:
            case 6:
                return handleAdvanceCommand(l_RandomCountry);
            case 7:
            case 8:
                return handleCardUsageCommand();
            default:
                return "pass";
        }
    }
    /**
     * Handles the deployment of additional armies to a random country during a player's turn.
     * @param l_RandomCountry The random country to which additional armies will be deployed.
     * @return An empty string if deployment is successful, or "pass" if the player chooses to skip deployment.
     */
    private String handleDeployCommand(Country l_RandomCountry) {
        if (Objects.nonNull(l_RandomCountry) && d_Player.getAdditionalArmies() > 0) {
            List<String> l_Commands = prepareDeployCommands(l_RandomCountry);
            executeDeployOrder(l_Commands);
            return "";
        }
        return "pass";
    }
    /**
     * Prepares a list of deployment commands for the given random country.
     * @param l_RandomCountry The random country to which additional armies will be deployed.
     * @return A list of strings representing deployment commands.
     */
    private List<String> prepareDeployCommands(Country l_RandomCountry) {
        List<String> l_Commands = new ArrayList<>();
        l_Commands.add("deploy");
        l_Commands.add(l_RandomCountry.getCountryName());
        l_Commands.add(String.valueOf(d_Random.nextInt(d_Player.getAdditionalArmies()) + 1));
        return l_Commands;
    }
    /**
     * Executes a deployment order based on the provided list of commands.
     * @param l_Commands A list of strings containing deployment commands
     */
    private void executeDeployOrder(List<String> l_Commands) {
        String[] l_CommandsArr = l_Commands.toArray(new String[0]);
        Order l_Order = new DeployOrder();
        l_Order.setOrderDetails(OrderCreator.GenerateDeployOrderInfo(l_CommandsArr, d_Player));
        finalizeOrderExecution(l_Order);
    }
    /**
     * Handles the advancement of armies from a random country to a random neighboring country during a player's turn.
     * @param l_RandomCountry The random country from which armies will be advanced.
     * @return "pass" if advancement is successful, or an empty string if the player cannot advance armies.
     */
    private String handleAdvanceCommand(Country l_RandomCountry) {
        Country l_RandomNeighbor = getRandomNeighbor(l_RandomCountry);
        if (Objects.nonNull(l_RandomCountry) && Objects.nonNull(l_RandomNeighbor) && l_RandomCountry.getArmies() > 0) {
            List<String> l_Commands = prepareAdvanceCommands(l_RandomCountry, l_RandomNeighbor);
            executeAdvanceOrder(l_Commands);
            return "pass";
        }
        return "";
    }
    /**
     * Prepares a list of advance commands to move armies from one country to another.
      * @param l_RandomCountry The source country from which armies will be advanced.
      * @param l_RandomNeighbor The target country to which armies will be advanced.
      * @return A list of strings representing advance commands.    
     */
    private List<String> prepareAdvanceCommands(Country l_RandomCountry, Country l_RandomNeighbor) {
        List<String> l_Commands = new ArrayList<>();
        l_Commands.add("advance");
        l_Commands.add(l_RandomCountry.getCountryName());
        l_Commands.add(l_RandomNeighbor.getCountryName());
        l_Commands.add(String.valueOf(d_Random.nextInt(l_RandomCountry.getArmies())));
        return l_Commands;
    }
    /**
     * Executes an advancement order based on the provided list of commands.
     * @param l_Commands A list of strings containing advancement commands
     */
    private void executeAdvanceOrder(List<String> l_Commands) {
        String[] l_CommandsArr = l_Commands.toArray(new String[0]);
        Order l_Order = new AdvancingOrder();
        l_Order.setOrderDetails(OrderCreator.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
        finalizeOrderExecution(l_Order);
    }
    /**
     * Handles the usage of a player's cards during their turn.
     * @return "pass" if a card is used for an attack, or "pass" if there are no cards to use or the card cannot be used.
     */
    private String handleCardUsageCommand() {
        if (!d_Player.getPlayersCards().isEmpty()) {
            int l_RandomCardIdx = d_Random.nextInt(d_Player.getPlayersCards().size());
            Cards l_Card = d_Player.getPlayersCards().get(l_RandomCardIdx);
            if (cardAttack(l_Card, getRandomConqueredCountry(d_Player))) {
                return "pass";
            }
        }
        return "pass";
    }
    /**
     * Finalizes the execution of a player's order and logs the issued command.
     * @param l_Order The Order object representing the player's order to be executed.
     */
    private void finalizeOrderExecution(Order l_Order) {
        OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
        d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
        d_Player.deployOrder();
    }
  /**
   * Executes a card-based attack action based on the type of card provided.
   * @param p_Card The Cards object representing the type of card to be used.
   * @param p_RandomCountry The target Country for the card action, as applicable to the card type.
   * @return true if the card action is successful, false otherwise.
   */
    private boolean cardAttack(Cards p_Card, Country p_RandomCountry) {
        switch (p_Card.getCardsType()) {
            case BLOCKADE:
                if (Objects.nonNull(p_RandomCountry)) {
                    List<String> l_Commands = new ArrayList<>();
                    l_Commands.add(0, "blockade");
                    l_Commands.add(1, p_RandomCountry.getCountryName());
                    String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    Order l_Order = new OrderForBlockade();
                    l_Order.setOrderDetails(OrderCreator.GenerateBlockadeOrderInfo(l_CommandsArr, d_Player));
                    OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                    d_Logger.logAction(String.format("%s Issuing the new Command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                    d_Player.deployOrder();
                    return true;
                }
            case BOMB:
                Country l_RandomUnconqueredCountry = getRandomUnconqueredCountry(d_Player);
                if (Objects.nonNull(l_RandomUnconqueredCountry)) {
                    List<String> l_Commands = new ArrayList<>();
                    l_Commands.add(0, "bomb");
                    l_Commands.add(1, l_RandomUnconqueredCountry.getCountryName());
                    String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    Order l_Order = new BombingOrder();
                    l_Order.setOrderDetails(OrderCreator.GenerateBombOrderInfo(l_CommandsArr, d_Player));
                    OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                    d_Logger.logAction(String.format("%s Issuing the new Command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                    d_Player.deployOrder();
                    return true;
                }
            case AIRLIFT: {
                List<String> l_Commands = new ArrayList<>();
                Country l_FromCountry = getRandomConqueredCountry(d_Player);
                Country l_ToCountry = getRandomConqueredCountry(d_Player);
                if (Objects.nonNull(l_FromCountry) && Objects.nonNull(l_ToCountry)) {
                    l_Commands.add(0, "airlift");
                    l_Commands.add(1, l_FromCountry.getCountryName());
                    l_Commands.add(2, l_ToCountry.getCountryName());
                    l_Commands.add(3, String.valueOf(d_Random.nextInt(10)));
                    String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    Order l_Order = new AirliftingOrder();
                    l_Order.setOrderDetails(OrderCreator.GenerateAirliftOrderInfo(l_CommandsArr, d_Player));
                    OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                    d_Logger.logAction(String.format("%s Issuing the new Command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                    d_Player.deployOrder();
                    return true;
                }
            }
            case DIPLOMACY: {
                Player l_RandomPlayer = getRandomPlayer(d_Player);
                List<String> l_Commands = new ArrayList<>();
                if (Objects.nonNull(l_RandomPlayer)) {
                    l_Commands.add(0, "negotiate");
                    l_Commands.add(1, l_RandomPlayer.getPlayerName());
                    String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    Order l_Order = new NegotiatingOrder();
                    l_Order.setOrderDetails(OrderCreator.GenerateNegotiateOrderInfo(l_CommandsArr, d_Player));
                    OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                    d_Logger.logAction(String.format("%s Issuing the new Command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                    d_Player.deployOrder();
                    return true;
                }
            }
        }
        return false;
    }
}

