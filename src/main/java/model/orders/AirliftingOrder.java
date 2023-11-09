package model.orders;

import model.CardsType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * This class gives the order to execute Airlifting Order, from one country to another.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class AirliftingOrder extends Order {

    LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();
    /**
     * A data member to store the instance of the gamemap.
     */
    private final GameMap d_GameMap;

    /**
     * Constructor class for Airlifting Order
     */
    public AirliftingOrder() {
        super();
        setOrderType("airlift");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Airlifting Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderDetails().getPlayer();
        Country l_fromCountry = getOrderDetails().getDeparture();
        Country l_toCountry = getOrderDetails().getCountryWhereDeployed();
        int p_amountOfArmyToAirLift = getOrderDetails().getAmountOfArmy();

        if (validateCommand()) {
            l_fromCountry.setArmies(l_fromCountry.getArmies() - p_amountOfArmyToAirLift);
            l_toCountry.setArmies(l_toCountry.getArmies() + p_amountOfArmyToAirLift);
            System.out.println("The order: " + getOrderType() + " " + p_amountOfArmyToAirLift + " armies from "+l_fromCountry.getCountryName()+" to "+l_toCountry.getCountryName());
            l_Player.removeCard(CardsType.AIRLIFT);
            return true;
        }
        return false;
    }

    /**
     * Validate the command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderDetails().getPlayer();
        Country l_fromCountry = getOrderDetails().getDeparture();
        Country l_toCountry = getOrderDetails().getCountryWhereDeployed();
        int p_amountOfArmyToAirLift = getOrderDetails().getAmountOfArmy();

        //check if the player is valid
        if (l_Player == null) {
            System.out.println("This isn't a valid Player.");
            return false;
        }
           //check if the player has an airlift card
        if (!l_Player.checkIfCardAvailable(CardsType.AIRLIFT)) {
            System.out.println("C'mon now he doesn't even have a Airlift Card.");
            return false;
        }
          //check if countries belong to the player
        if (!l_Player.getOccupiedCountries().contains(l_fromCountry) || !l_Player.getOccupiedCountries().contains(l_toCountry)) {
            System.out.println("Source or target country is not yet occupied by the player.");
            return false;
        }
         //check if army number is more than 0
        if (p_amountOfArmyToAirLift <= 0) {
            System.out.println("You need an Airlifting army to do that");
            return false;
        }
         //check if army number is more that they own
        if (l_fromCountry.getArmies() < p_amountOfArmyToAirLift) {
            System.out.println("Player has minimal army in that country " + getOrderDetails().getDeparture().getCountryName());
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Oh you just Airlifted " + getOrderDetails().getAmountOfArmy() + " armies from " + getOrderDetails().getDeparture().getCountryName() + " to " + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_LogEntryBuffer.logAction("Just Airlifted " + getOrderDetails().getAmountOfArmy() + " armies from " + getOrderDetails().getDeparture().getCountryName() + " to " + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
    }
}

