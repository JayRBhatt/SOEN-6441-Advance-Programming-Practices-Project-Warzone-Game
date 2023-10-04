package model.orders;

import model.Country;
import model.Player;

/**
* Class DeployOrder which is a child of Order, used to execute the orders
 * The child class DeployOrder of class Order,is used to execute the orders from the players
/**
 * This class includes the order details
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
     * This functions execute deployment of orders.
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        if (getOrderDetails().getPlayer() == null || getOrderDetails().getDestination() == null) {
            System.out.println("You have entered Invalid order information.Deploy Order command execution failed ");
            return false;
        }
        Player l_Player = getOrderDetails().getPlayer();
        String l_Destination = getOrderDetails().getDestination();
        int l_ArmiesToDeploy = getOrderDetails().getNumberOfArmy();
        for(Country l_Country : l_Player.getOccupiedCountries()){
            if(l_Country.getCountryName().equals(l_Destination)){
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println("Armies Deployed Successfully!");
                System.out.println("No Of Armies Deployed : "+ l_Country.getArmies()+ " & Country Name : "+ l_Country.getCountryName());
            }
        }
        System.out.println("\nExecution is Completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination + ".");
        System.out.println("\n Execution Completed !!");
        System.out.println("\n We have deployed "+ l_ArmiesToDeploy + "armies to the country " + l_Destination + ".");
        System.out.println("*********************************************************************************************");
        return true;
    }

}