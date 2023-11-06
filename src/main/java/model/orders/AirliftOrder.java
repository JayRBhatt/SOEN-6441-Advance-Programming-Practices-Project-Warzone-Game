package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import services.ILoggingService;

/**
 * This class gives the order to execute an airlift from one country to another.
 */
public class AirliftOrder extends Order {

    private final ILoggingService logger;
    private final GameMap gameMap;

    /**
     * Constructor for the Airlift Order.
     * 
     * @param loggingService The logging service to be used for log entries
     */
    public AirliftOrder(ILoggingService loggingService) {
        super("airlift");
        this.logger = loggingService;
        this.gameMap = GameMap.getInstance();
    }

    @Override
    public boolean execute() {
        Player player = getOrderInfo().getPlayer();
        Country fromCountry = getOrderInfo().getDeparture();
        Country toCountry = getOrderInfo().getDestination();
        int armyCountToAirlift = getOrderInfo().getNumberOfArmy();

        if (!validateCommand()) {
            return false;
        }

        fromCountry.setArmies(fromCountry.getArmies() - armyCountToAirlift);
        toCountry.setArmies(toCountry.getArmies() + armyCountToAirlift);

        logger.logInfo(String.format("Executed airlift: %d armies from %s to %s.",
                armyCountToAirlift, fromCountry.getName(), toCountry.getName()));

        player.removeCard(CardType.AIRLIFT);
        return true;
    }

    @Override
    public boolean validateCommand() {
        Player player = getOrderInfo().getPlayer();
        Country fromCountry = getOrderInfo().getDeparture();
        Country toCountry = getOrderInfo().getDestination();
        int armyCountToAirlift = getOrderInfo().getNumberOfArmy();

        if (player == null) {
            logger.logError("The Player is not valid.");
            return false;
        }

        if (!player.hasCard(CardType.AIRLIFT)) {
            logger.logError("Player doesn't have Airlift Card.");
            return false;
        }

        if (!player.ownsCountry(fromCountry) || !player.ownsCountry(toCountry)) {
            logger.logError("One or both countries do not belong to the player.");
            return false;
        }

        if (armyCountToAirlift <= 0) {
            logger.logError("The number of armies to airlift must be greater than 0.");
            return false;
        }

        if (fromCountry.getArmies() < armyCountToAirlift) {
            logger.logError("Not enough armies in the departure country to airlift.");
            return false;
        }

        return true;
    }

    @Override
    public void printOrderCommand() {
        String message = String.format("Airlifted %d armies from %s to %s.",
                getOrderInfo().getNumberOfArmy(),
                getOrderInfo().getDeparture().getName(),
                getOrderInfo().getDestination().getName());
        System.out.println(message);
        logger.logInfo(message);
    }
}
