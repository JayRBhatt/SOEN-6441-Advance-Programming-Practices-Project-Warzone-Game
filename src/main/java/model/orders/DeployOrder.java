package model.orders;

import model.Country;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * Class DeployOrder which is a child of Order, used to execute the orders
 *
 * /**
 * A class with the information of Order details
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class DeployOrder extends Order {
    LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

    /**
     * Constructor for class DeployOrder
     */
    public DeployOrder() {
        super();
        setOrderType("deploy");
    }

    /**
     * Overriding the execute function for the order type deploy
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        Country l_Destination = getOrderDetails().getCountryWhereDeployed();
        int l_ArmiesToDeploy = getOrderDetails().getAmountOfArmy();
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        System.out.println(
                "The order: " + getOrderType() + " " + getOrderDetails().getCountryWhereDeployed().getCountryName()
                        + " " + getOrderDetails().getAmountOfArmy());
        if (validateCommand()) {
            l_Destination.deployArmies(l_ArmiesToDeploy);
            return true;
        }
        return false;
    }

    /**
     * A function to validate the commands
     *
     * @return true if command can be executed else false
     */
    public boolean validateCommand() {
        Player l_Player = getOrderDetails().getPlayer();
        Country l_Destination = getOrderDetails().getCountryWhereDeployed();
        int l_Reinforcements = getOrderDetails().getAmountOfArmy();
        if (l_Player == null || l_Destination == null) {
            System.out.println("Invalid order information.");
            return false;
        }
        if (!l_Player.isCaptured(l_Destination)) {
            System.out.println("The country does not belong to you");
            return false;
        }
        if (!l_Player.stationAdditionalArmiesFromPlayer(l_Reinforcements)) {
            System.out.println("You do not have enough Reinforcement Armies to deploy.");
            return false;
        }
        return true;
    }

    /**
     * A function to print the order on completion
     */
    public void printOrderCommand() {
        System.out.println("Deployed " + getOrderDetails().getAmountOfArmy() + " armies to "
                + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        d_LogEntryBuffer.logAction("Deployed " + getOrderDetails().getAmountOfArmy() + " armies to "
                + getOrderDetails().getCountryWhereDeployed().getCountryName() + ".");

    }

}
