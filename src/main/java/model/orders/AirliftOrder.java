package model.orders;

import model.CardsType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * This class gives the order to execute AirliftOrder, from one country to another.
 * @author Surya Manian
 */
public class AirliftOrder extends Order {

    LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();
    /**
     * A data member to store the instance of the gamemap.
     */
    private final GameMap d_GameMap;

    /**
     * Constructor class for Airlift Order
     */
    public AirliftOrder() {
        super();
        setOrderType("airlift");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Airlift Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderDetails().getPlayer();
        Country l_fromCountry = getOrderDetails().getDeparture();
        Country l_toCountry = getOrderDetails().getCountryWhereDeployed();
        int p_armyNumberToAirLift = getOrderDetails().getNumberOfArmy();

        if (validateCommand()) {
            l_fromCountry.setArmies(l_fromCountry.getArmies() - p_armyNumberToAirLift);
            l_toCountry.setArmies(l_toCountry.getArmies() + p_armyNumberToAirLift);
            System.out.println("The order: " + getOrderType() + " " + p_armyNumberToAirLift + " armies from "+l_fromCountry.getCountryName()+" to "+l_toCountry.getCountryName());
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
        int p_armyNumberToAirLift = getOrderDetails().getNumberOfArmy();

        //check if the player is valid
        if (l_Player == null) {
            System.out.println("The Player is not valid.");
            return false;
        }
        //check if the player has an airlift card
        if (!l_Player.checkIfCardAvailable(CardsType.AIRLIFT)) {
            System.out.println("Player doesn't have Airlift Card.");
            return false;
        }
        //check if countries belong to the player
        if (!l_Player.getOccupiedCountries().contains(l_fromCountry) || !l_Player.getOccupiedCountries().contains(l_toCountry)) {
            System.out.println("Source or target country do not belong to the player.");
            return false;

        }
        //check if army number is more than 0
        if (p_armyNumberToAirLift <= 0) {
            System.out.println("The number of airlift army should be greater than 0");
            return false;
        }
        //check if army number is more that they own
        if (l_fromCountry.getArmies() < p_armyNumberToAirLift) {
            System.out.println("Player has less no. of army in country " + getOrderDetails().getDeparture().getCountryName());
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Airlifted " + getOrderDetails().getNumberOfArmy() + " armies from " + getOrderDetails().getDeparture().getCountryName() + " to " + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_LogEntryBuffer.logAction("Airlifted " + getOrderDetails().getNumberOfArmy() + " armies from " + getOrderDetails().getDeparture().getCountryName() + " to " + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
    }
}

