package model.Calculation.playerStrategy;

import java.io.Serializable;
import java.util.Scanner;

public class HumanStrategy extends PlayerStrategy implements Serializable {

    /**
     * scanner to read from user
     */
    private final static Scanner SCANNER = new Scanner(System.in);

    /**
     * Default constructor
     */
    public HumanStrategy() {

    }

    /**
     * Function to read and return the next input of user
     *
     * @return next input of the user
     */
    @Override
    public String createCommand() {
        return SCANNER.nextLine();
    }

}
