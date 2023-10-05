package model.orders;

import model.Country;
import model.Player;

/**
 * A class with the information of Order details
 * 
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class OrderDetails {

    private Player d_Player;
    private Country d_DepartureCountry;
    private String d_DestinationCountry;
    private int d_NumberOfArmies;

    /**
     * A function to get the player information
     * 
     * @return the object of player
     */
    public Player getPlayer() {

        return d_Player;
    }

    /**
     * A function to set the player information
     * 
     * @param d_Player the object of player
     */
    public void setPlayer(Player d_Player) {

        this.d_Player = d_Player;
    }

    /**
     * A function to get the departure of the armies from the order
     * 
     * @return the departure country object
     */
    public Country getDepartureCountry() {

        return d_DepartureCountry;
    }

    /**
     * A function to set the departure of the armies from the order
     * 
     * @param d_DepartureCountry departure country object
     */
    public void setDepartureCountry(Country d_DepartureCountry) {

        this.d_DepartureCountry = d_DepartureCountry;
    }

    /**
     * A function to get where the army is going to.
     * 
     * @return the destination of armies
     */
    public String getCountryDestination() {

        return d_DestinationCountry;
    }

    /**
     * A function to set the destination of the armies
     * 
     * @param d_DestinationCountry the destination of armies
     */
    public void setCountryDestination(String d_DestinationCountry) {

        this.d_DestinationCountry = d_DestinationCountry;
    }

    /**
     * A function to get the number of armies in the order
     * 
     * @return the number of armies
     */
    public int getNumberOfArmies() {

        return d_NumberOfArmies;
    }

    /**
     * A function to set the number of armies in the order
     * 
     * @param d_NumberOfArmies the number of armies
     */
    public void setNumberOfArmies(int d_NumberOfArmies) {

        this.d_NumberOfArmies = d_NumberOfArmies;
    }

}
