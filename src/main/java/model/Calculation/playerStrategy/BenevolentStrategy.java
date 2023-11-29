package model.Calculation.playerStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import model.Cards;
import model.CardsType;
import model.Country;
import model.GameMap;
import model.Player;
import model.orders.AdvancingOrder;
import model.orders.DeployOrder;
import model.orders.NegotiatingOrder;
import model.orders.Order;
import model.orders.OrderCreator;
import services.OrderIssue;
import utils.loggers.LogEntryBuffer;

/**
 * Class that implements the Benevolent Player Strategy
 *
 * @author Madhav Anadkat
 */

public class BenevolentStrategy extends PlayerStrategy implements Serializable {
    private static final Random d_Random = new Random();
    /**
     * Logger for game actions
     */
    private LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    private static GameMap d_GameMap;

    /**
     * Retrieves the weakest country conquered by a player.
     *
     * @param p_Player The player whose occupied countries are considered.
     * @return The weakest conquered country or null
     */
    public Country getWeakestConqueredCountry(Player p_Player) {
        List<Country> l_CountryList = p_Player.getOccupiedCountries();
        if (!l_CountryList.isEmpty()) {
            Country l_WeakestCountry = l_CountryList.get(0);
            for (Country l_Country : l_CountryList) {
                if (l_Country.getArmies() < l_WeakestCountry.getArmies()) {
                    l_WeakestCountry = l_Country;
                }
            }
            return l_WeakestCountry;
        }
        return null;
    }

    public String createCommand() {
        d_GameMap = GameMap.getInstance();
        d_Player = d_GameMap.getCurrentPlayer();
        d_LogEntryBuffer.logAction("Issuing Orders for the Benevolent Player - " + d_Player.getPlayerName());

        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        Country l_WeakestCountry = getWeakestConqueredCountry(d_Player);

        if (Objects.isNull(l_WeakestCountry)) {
            return "pass";
        }

        int l_ArmiesReinforce = d_Player.getAdditionalArmies();

        // Deploy armies to weakest Country
        l_Commands.add(0, "deploy");
        l_Commands.add(1, l_WeakestCountry.getCountryName());
        l_Commands.add(2, String.valueOf(l_ArmiesReinforce));
        l_CommandsArr = l_Commands.toArray(new String[0]);
        l_Order = new DeployOrder();
        l_Order.setOrderDetails(OrderCreator.GenerateDeployOrderInfo(l_CommandsArr, d_Player));
        OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
        d_LogEntryBuffer
                .logAction(String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
        d_Player.deployOrder();

        // If Player has a diplomacy card, then use it
        if (d_Player.getPlayersCards().size() > 0) {
            for (Cards l_Card : d_Player.getPlayersCards()) {
                if (l_Card.getCardsType() == CardsType.DIPLOMACY) {
                    Player l_RandomPlayer = getRandomPlayer(d_Player);
                    if (Objects.nonNull(l_RandomPlayer)) {
                        l_Commands = new ArrayList<>();
                        l_Commands.add(0, "negotiate");
                        l_Commands.add(1, l_RandomPlayer.getPlayerName());
                        l_CommandsArr = l_Commands.toArray(new String[0]);
                        l_Order = new NegotiatingOrder();
                        l_Order.setOrderDetails(OrderCreator.GenerateNegotiateOrderInfo(l_CommandsArr, d_Player));
                        OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                        d_LogEntryBuffer.logAction(
                                String.format("%s issuing new command: %s", d_Player.getPlayerName(),
                                        OrderIssue.Commands));
                        d_Player.deployOrder();
                        return "pass";
                    }
                }
            }
        }

        // Move armies to the weakest country from the other neighboring countries of
        // the same player
        for (Country l_Country : l_WeakestCountry.getNeighbors()) {
            if (l_Country.getPlayer().getPlayerName().equals(d_Player.getPlayerName())) {
                l_Commands = new ArrayList<>();
                l_Commands.add(0, "advance");
                l_Commands.add(1, l_Country.getCountryName());
                l_Commands.add(2, l_WeakestCountry.getCountryName());
                l_Commands.add(3, String.valueOf(l_Country.getArmies()));
                l_CommandsArr = l_Commands.toArray(new String[0]);
                l_Order = new AdvancingOrder();
                l_Order.setOrderDetails(OrderCreator.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                OrderIssue.Commands = l_Order.getOrderDetails().getCommand();
                d_Player.deployOrder();
                d_LogEntryBuffer.logAction(
                        String.format("%s issuing new command: %s", d_Player.getPlayerName(), OrderIssue.Commands));
                l_WeakestCountry = l_Country;
            }
        }

        return "pass";
    }

    protected Player getRandomPlayer(Player p_Player) {
        List<Country> l_Enemies = d_Player.getOccupiedCountries().stream()
                .flatMap(country -> country.getNeighbors().stream())
                .filter(country -> !country.getPlayer().getPlayerName().equals(d_Player.getPlayerName()))
                .collect(Collectors.toList());

        if (!l_Enemies.isEmpty()) {
            int l_Random = d_Random.nextInt(l_Enemies.size());
            return l_Enemies.get(l_Random).getPlayer();
        }

        return null;
    }
}
