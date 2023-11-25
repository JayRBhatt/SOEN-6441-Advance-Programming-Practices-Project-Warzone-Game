package model;

import java.util.*;
import java.util.stream.Collectors;

import model.Calculation.playerStrategy.PlayerStrategy;
import model.orders.Order;
import model.orders.OrderCreator;
import services.OrderIssue;

/**
 * Class that provides every method that is required for any properties of
 * a Player in the game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class Player {

    /**
     * An integer to store the ID of player
     */
    private int d_PlayerId;
    /**
     * An integer to store the name of the player
     */
    private String d_PlayerName;
    /**
     * A list of occupied countries
     */
    private List<Country> d_OccupiedCountries = new ArrayList<>();
    /**
     * A deque to manage the list of orders
     */
    private Deque<Order> d_Orders = new ArrayDeque<>();
    /**
     * An integer to store the number of additional armies
     */
    private int d_AdditionalArmies;
    /**
     * An integer to store the number of assigned armies
     */
    private int d_AssignedTroops;
    /**
     * A list of cards for the player
     */
    private List<Cards> d_PlayersCards = new ArrayList<>();
    /**
     * A list of neutral players
     */
    private List<Player> d_NeutralPlayers = new ArrayList<>();
    /**
     * A list of orders
     */
    public static List<Order> OrderList = new ArrayList<>();

    private final PlayerStrategy d_PlayerStrategy;

    /**
     * method to get armies issued
     *
     * @return issues armies
     */
    public int getIssuedArmies() {
        return d_ArmiesToIssue;
    }

    /**
     * method to set the armies issued
     * 
     * @param p_ArmiesToIssue armies to issue to player
     */
    public void setIssuedArmies(int p_ArmiesToIssue) {
        d_ArmiesToIssue = p_ArmiesToIssue;
    }

    /**
     * number of armies to issue
     */
    private int d_ArmiesToIssue = 0;

    /**
     * A list of cards for the player
     */
    public Player(PlayerStrategy p_PlayerStrategy) {
        this.d_PlayerStrategy = p_PlayerStrategy;
    }

    public String readFromPlayer() {
        return this.d_PlayerStrategy.createCommand();
    }

    /**
     * A function to get the player ID
     *
     * @return the ID of player
     */
    public int getPlayerId() {
        return d_PlayerId;
    }

    /**
     * A function to set the player ID
     *
     * @param p_Id Player ID value
     */
    public void setIPlayerId(int p_Id) {
        this.d_PlayerId = p_Id;
    }

    /**
     * Returns the Name of the Player
     * 
     * @return PlayerName
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * Sets the Name of a Player
     * 
     * @param p_PlayerName name of a player which is to be set
     */
    public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }

    /**
     * Method to check if particular card is available in the player's card list
     *
     * @param p_cardType The type of card
     * @return true if card is available else false
     */
    public boolean checkIfCardAvailable(CardsType p_cardType) {
        return d_PlayersCards.stream().anyMatch(p_card -> p_card.getCardsType().equals(p_cardType));
    }

    /**
     * Returns a List of Occupied Countries by a Player
     * 
     * @return d_OccupiedCountries
     */
    public List<Country> getOccupiedCountries() {
        return d_OccupiedCountries;
    }

    /**
     * Sets the List of Occupied Countries by a player
     * 
     * @param p_OccupiedCountries the list of occupied countries
     */
    public void setOccupiedCountries(List<Country> p_OccupiedCountries) {
        this.d_OccupiedCountries = p_OccupiedCountries;
    }

    /**
     * Returns a Deque of Orders for a player
     * 
     * @return d_Orders
     */
    public Deque<Order> getOrders() {
        return d_Orders;
    }

    /**
     * Adds an order to the Deque of Orders
     * 
     * @param p_Order the order which is to be recieved
     */

    public void receiveOrder(Order p_Order) {
        d_Orders.add(p_Order);
    }

    /**
     * Returns the number of Additional Armies of a player
     * 
     * @return d_AdditionalArmies
     */

    public int getAdditionalArmies() {
        return d_AdditionalArmies;
    }

    /**
     * Sets the number of Additional Armies for a player
     * 
     * @param p_AdditionalArmies the armies which is to be added
     */

    public void setAdditionalArmies(int p_AdditionalArmies) {
        this.d_AdditionalArmies = p_AdditionalArmies;
    }

    /**
     * A function to get list of all cards for the player
     *
     * @return list of all cards
     */
    public List<Cards> getPlayersCards() {
        return d_PlayersCards;
    }

    /**
     * Method to check if particular card is available in the player's card list
     *
     * @param p_cardsType The type of card
     * @return true if card is available else false
     */
    public boolean checkCardAvailablity(CardsType p_cardsType) {
        return d_PlayersCards.stream().anyMatch(p_cards -> p_cards.getCardsType().equals(p_cardsType));
    }

    /**
     * Remove the card for the player
     *
     * @param p_CardsType card to be removed
     * @return the player cards
     */
    public boolean removeCard(CardsType p_CardsType) {
        return d_PlayersCards.remove(new Cards(p_CardsType));
    }

    /**
     * A function to remove the all cards from the player
     */
    public void removeCards() {
        d_PlayersCards.clear();
    }

    /**
     * Add the card to the player on conquering the territory
     *
     * @param p_Card card to be added to player
     */
    public void addPlayerCard(Cards p_Card) {
        d_PlayersCards.add(p_Card);
    }

    /**
     * Get the list of all players you cannot attack
     *
     * @return list of players
     */
    public List<Player> getNeutralPlayers() {
        return d_NeutralPlayers;
    }

    /**
     * Add the neutral player to the list
     *
     * @param p_NeutralPlayer The player you cannot attack
     */
    public void addNeutralPlayers(Player p_NeutralPlayer) {
        if (!d_NeutralPlayers.contains(p_NeutralPlayer)) {
            d_NeutralPlayers.add(p_NeutralPlayer);
        }
    }

    /**
     * Remove all the neutral players from list
     */
    public void removeNeutralPlayer() {
        if (!d_NeutralPlayers.isEmpty()) {
            d_NeutralPlayers.clear();
        }
    }

    /**
     * A function to get the issue order from player and add to the order list
     */
    public void deployOrder() {
        Order l_Order = OrderCreator.CreateOrder(OrderIssue.Commands.split(" "), this);
        receiveOrder(l_Order);
    }

    /**
     * A function to return the next order for execution
     *
     * @return order for executing for each player
     */
    public Order nextOrder() {
        return d_Orders.poll();
    }

    /**
     * Publishes the order given by a player
     * 
     * @param p_Command
     */

    /**
     * Confirms whether a country is occupied or not
     * 
     * @param p_Country the country which is to be checked
     * @param p_Player  the player who occupied the country
     * @return true if country is occupied by player or else returns false
     */
    public boolean confirmIfCountryisOccupied(String p_Country, Player p_Player) {
        List<Country> l_ListOfOccupiedCountries = p_Player.getOccupiedCountries();
        for (Country l_Country : l_ListOfOccupiedCountries) {
            if (l_Country.getCountryName().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether to deploy the armies or not
     * 
     * @param p_ArmyNumber army number
     * @return true if the operation was successful and false if number of army
     *         provided by user is greater than remaining armies of the player or
     *         army provided by user is negative
     */
    public boolean stationAdditionalArmiesFromPlayer(int p_ArmyNumber) {

        if (p_ArmyNumber > d_AdditionalArmies || p_ArmyNumber < 0) {
            return false;
        }
        d_AdditionalArmies -= p_ArmyNumber;
        return true;
    }

    /**
     * Creates a list of occupancy of countries by a player and returns
     * 
     * @param p_Occupy country occupancy list
     * @return the list
     */
    public String createOccupyList(List<Country> p_Occupy) {
        String l_Conclusion = "";
        for (Country l_Occupy : p_Occupy) {
            l_Conclusion += l_Occupy.getPlayer() + "-";
        }
        return l_Conclusion.length() > 0 ? l_Conclusion.substring(0, l_Conclusion.length() - 1) : "";
    }

    /**
     * Calculates the total of alloted reinforcements for a player
     * 
     * @param p_gameMap the game map
     */
    public void calculateTotalReinforcementArmies(GameMap p_gameMap) {

        if (getOccupiedCountries().size() > 0) {
            int l_reinforcements = (int) Math.floor(getOccupiedCountries().size() / 3f);
            l_reinforcements += getExtraArmiesIfPlayerWins(p_gameMap);
            setAdditionalArmies(l_reinforcements > 2 ? l_reinforcements : 20);
        } else {
            setAdditionalArmies(5);
        }
        System.out.println("The Player:" + getPlayerName() + " is assigned with " + getAdditionalArmies() + " armies.");

    }

    /**
     * Calculates the extra army which players received after winning
     * 
     */
    private int getExtraArmiesIfPlayerWins(GameMap p_gameMap) {
        int l_reinforcements = 0;

        Map<String, List<Country>> l_CountryMap = getOccupiedCountries()
                .stream()
                .collect(Collectors.groupingBy(Country::getContinent));

        for (String continent : l_CountryMap.keySet()) {
            if (p_gameMap.getContinent(continent).getCountries().size() == l_CountryMap.get(continent).size()) {

                l_reinforcements += p_gameMap.getContinent(continent).getContinentValue();
            }
        }
        return l_reinforcements;
    }

    /**
     * A function to check if the country exists in the list of player captured
     * countries
     *
     * @param p_Country The country to be checked if present
     * @return true if country exists in the assigned country list else false
     */
    public boolean isCaptured(Country p_Country) {
        return d_OccupiedCountries.contains(p_Country);
    }

    /**
     * An overriden method of toString for debugging
     * 
     */
    @Override
    public String toString() {
        return "helloworld [d_PlayerId=" + d_PlayerId + ", d_PlayerName=" + d_PlayerName + ", d_AdditionalArmies="
                + d_AdditionalArmies + ", d_AssignedTroops=" + d_AssignedTroops + "]";
    }

}