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


    public static String Commands = null;
    
    /**
     * Constructor for class DeployOrder
     */
    public DeployOrder() {
        super();
        setOrderType("deploy");
    }
    /**
     * the execute function for the order type deploy
     *
     * @return true if the execution was successful else return false
     */

    public boolean execute() {
        if (getOrderDetails().getPlayer() == null || getOrderDetails().getCountryWhereDeployed() == null) {
            System.out.println("Failed to execute Deploy order: Invalid order information.");
            return false;
        }
    
        Player l_Player = getOrderDetails().getPlayer();
        String l_CountryWhereDeployed = getOrderDetails().getCountryWhereDeployed();
        int l_ArmiesToDeploy = getOrderDetails().getNumberOfArmy();
    
        for (Country l_Country : l_Player.getOccupiedCountries()) {
            if (l_Country.getCountryName().equals(l_CountryWhereDeployed)) {
                l_Country.deployArmies(l_ArmiesToDeploy);
                int l_DeployedArmies = l_Country.getArmies();
                System.out.println("Successfully deployed " + l_ArmiesToDeploy + " armies to " + l_CountryWhereDeployed + ".");
                System.out.println("The country " + l_Country.getCountryName() + " now has " + l_DeployedArmies + " armies.");
            }
        }
    
        System.out.println("\nExecution completed.");
        System.out.println("=========================================================================================");
        return true;
    }
    

}