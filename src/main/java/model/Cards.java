package model;

import java.util.Objects;


/**
 * A class that creates card and assigns them randomly
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */

public class Cards {
      /**
     * Data member to hold cards type
     */
    private CardsType d_CardsType;

    /**
     * This is a Constructor for Class Cards
     */
    public Cards() {
        d_CardsType = CardsType.getRandomCard();
    }

    /**
     * Constructor to create card object
     *
     * @param p_cardType The cards type
     */
    public Cards(CardsType p_cardType) {
        d_CardsType = p_cardType;
    }


    /**
     * This method is used to get the Cards Type
     *
     * @return the Card Type
     */
    public CardsType getCardsType() {
        return d_CardsType;
    }

    /**
     * Setter for Card type
     *
     * @param p_cardType The card type
     */
    public void setCardsType(CardsType p_cardType) {
        d_CardsType = p_cardType;
    }

    /**
     * Method to check the object equality
     *
     * @param p_obj The object which should be compared
     * @return true if objects are equal else false
     */
    @Override
    public boolean equals(Object p_obj) {
        if (this == p_obj) return true;
        if (!(p_obj instanceof Cards)) return false;
        Cards l_Cards = (Cards) p_obj;
        return d_CardsType == l_Cards.d_CardsType;
    }

    /**
     * Method to return the hashcode of object.
     *
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_CardsType);
    }
}
