package services;

/**
 * This class represents the service responsible for exiting the program.
 * It provides a method to gracefully exit the game.
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class ExitService {

    /**
     * Exits the program.
     *
     * @param exitCode The exit code to use when exiting the program.
     */
    public static void exitGame(int exitCode) {
        System.out.println("Exiting the game...");

        // Exit the program with the specified exit code
        System.exit(exitCode);
    }
}
