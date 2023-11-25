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
     * Logger Observable
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
        if (l_Enemies.size() > 0) {
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
        if (d_GameMap.getCountries().size() > 0 && p_Player.getOccupiedCountries().size() < d_GameMap.getCountries().size()) {
            int l_Index = d_Random.nextInt(d_GameMap.getCountries().size());
            l_RandomCountry = (Country) d_GameMap.getCountries().values().toArray()[l_Index];
            while (l_RandomCountry.getPlayer().equals(p_Player)) {
                l_Index = d_Random.nextInt(d_GameMap.getCountries().size());
                l_RandomCountry = (Country) d_GameMap.getCountries().values().toArray()[l_Index];
            }
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
        if (p_Player.getOccupiedCountries().size() > 0) {
            int l_Index = d_Random.nextInt(p_Player.getOccupiedCountries().size());
            return p_Player.getOccupiedCountries().get(l_Index);
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
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        //check if player can still play
        int l_Random = d_Random.nextInt(11);
        Country l_RandomCountry = getRandomConqueredCountry(d_Player);
        switch (l_Random) {
            case 0:
            case 1:
            case 2:
            case 3:
                if (Objects.nonNull(l_RandomCountry) && d_Player.getAdditionalArmies() > 0) {
                    l_Commands.add(0, "deploy");
                    l_Commands.add(1, l_RandomCountry.getCountryName());
                    l_Commands.add(2, String.valueOf(d_Random.nextInt(d_Player.getAdditionalArmies()) + 1));
                    l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    l_Order = new DeployOrder();
                    l_Order.setOrderDetails(OrderCreator.GenerateDeployOrderInfo(l_CommandsArr, d_Player));
                    OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                    d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                    d_Player.deployOrder();
                }
                break;
            case 4:
            case 5:
            case 6:
                Country l_RandomNeighbor = getRandomNeighbor(l_RandomCountry);
                if (Objects.nonNull(l_RandomCountry) && Objects.nonNull(l_RandomNeighbor)) {
                    if (l_RandomCountry.getArmies() > 0) {
                        l_Commands.add(0, "advance");
                        l_Commands.add(1, l_RandomCountry.getCountryName());
                        l_Commands.add(2, l_RandomNeighbor.getCountryName());
                        l_Commands.add(3, String.valueOf(d_Random.nextInt(l_RandomCountry.getArmies())));
                        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                        l_Order = new AdvancingOrder();
                        l_Order.setOrderDetails(OrderCreator.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                        OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                        d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                        d_Player.deployOrder();
                        return "pass";
                    }
                    break;
                }
            case 7:
            case 8:
                if (d_Player.getPlayersCards().size() <= 0) {
                    break;
                }
                int l_RandomCardIdx = d_Random.nextInt(d_Player.getPlayersCards().size());
                Cards l_Card = d_Player.getPlayersCards().get(l_RandomCardIdx);
                if (cardAttack(l_Card, l_RandomCountry)) {
                    return "pass";
                }
            default:
                return "pass";
        }
        return "";
    }

    private boolean cardAttack(Cards l_Card, Country l_RandomCountry) {
        switch (l_Card.getCardsType()) {
            case BLOCKADE:
                if (Objects.nonNull(l_RandomCountry)) {
                    List<String> l_Commands = new ArrayList<>();
                    l_Commands.add(0, "blockade");
                    l_Commands.add(1, l_RandomCountry.getCountryName());
                    String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    Order l_Order = new OrderForBlockade();
                    l_Order.setOrderDetails(OrderCreator.GenerateBlockadeOrderInfo(l_CommandsArr, d_Player));
                    OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                    d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
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
                    d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
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
                    d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
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
                    d_Logger.logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                    d_Player.deployOrder();
                    return true;
                }
            }
        }
        return false;
    }
}

