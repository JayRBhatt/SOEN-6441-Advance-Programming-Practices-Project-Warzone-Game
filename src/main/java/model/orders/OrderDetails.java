package model.orders;

import model.Country;
import model.Player;

/**
 * A class with the information of Order details
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class OrderDetails {

    private Player d_Player;
    private Country d_Departure;
    private int d_NumberOfArmy;
    private Country d_TargetCountry;
    private Player d_NeutralPlayer;
    private Country d_CountryWhereDeployed;

    

    /**
     * function to get the Neutral player
     *
     * @return the Neutral player
     */
    public Player getNeutralPlayer() {
        return d_NeutralPlayer;
    }

    /**
     * function to set the Neutral Player
     *
     * @param d_NeutralPlayer the Neutral player
     */
    public void setNeutralPlayer(Player d_NeutralPlayer) {
        this.d_NeutralPlayer = d_NeutralPlayer;
    }

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
    
    public Country getDeparture() {

        return d_Departure;
    }

    /**
     * A function to set the departure of the armies from the order
     * 
     * @param d_Departure departure country object
     */
    
    public void setDeparture(Country d_Departure) {

        this.d_Departure = d_Departure;
    }

    /**
     * A function to get where the army is going to.
     * 
     * @return the destination of armies
     */
    
    public Country getCountryWhereDeployed() {

        return d_CountryWhereDeployed;
    }

    /**
     * A function to set the destination of the armies
     * 
     * @param d_Destination the destination of armies
     */
    
    public void setCountryWhereDeployed(Country d_CountryWhereDeployed) {

        this.d_CountryWhereDeployed = d_CountryWhereDeployed;
    }

    /**
     * A function to get the number of armies in the order
     * 
     * @return the number of armies
     */
    
    public int getNumberOfArmy() {

        return d_NumberOfArmy;
    }

    /**
     * A function to set the number of armies in the order
     * 
     * @param d_NumberOfArmy the number of armies
     */
    
    public void setNumberOfArmy(int d_NumberOfArmy) {

        this.d_NumberOfArmy = d_NumberOfArmy;
    }

    /**
     * A function to get the target country of the order
     *
     * @return the target country
     */
    public Country getTargetCountry() {
        return this.d_TargetCountry;
    }

    /**
     * A function to set the target country of the order
     *
     * @param p_TargetCountry the target country
     */
    public void setTargetCountry(Country p_TargetCountry) {
        this.d_TargetCountry = p_TargetCountry;
    }

}
