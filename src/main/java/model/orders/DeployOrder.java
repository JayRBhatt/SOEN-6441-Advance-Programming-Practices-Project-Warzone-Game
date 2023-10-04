package model.orders;

import model.Country;
import model.Player;

/**
* Class DeployOrder which is a child of Order, used to execute the orders
 *
/**
 * A class with the information of Order details
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class DeployOrder extends Order {
    /**
     * Constructor for class DeployOrder
     */
    public DeployOrder() {
        super();
        setType("deploy");
    }
    /**
     * the execute function for the order type deploy
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        if (getOrderDetails().getPlayer() == null || getOrderDetails().getDestination() == null) {
            System.out.println("Fail to execute Deploy order: Invalid order information.");
            return false;
        }
        Player l_Player = getOrderDetails().getPlayer();
        String l_Destination = getOrderDetails().getDestination();
        int l_ArmiesToDeploy = getOrderDetails().getNumberOfArmy();
        for(Country l_Country : l_Player.getOccupiedCountries()){
            if(l_Country.getCountryName().equals(l_Destination)){
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println("The country " + l_Country.getCountryName() + " has been deployed with " + l_Country.getArmies() + " armies.");
            }
        }
        System.out.println("\nExecution is completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination + ".");
        System.out.println("=========================================================================================");
        return true;
    }

}