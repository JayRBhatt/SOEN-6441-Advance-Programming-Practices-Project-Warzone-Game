package services;

/**
 * Class that has the main logic behind the Exiting the game
 *
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class ExitService {

    /**
     * Exits the program.
     *
     * @param exitCode The exit code to use when exiting the program.
     */
    public void exitGame(int exitCode) {
        System.out.println("Exiting the game...");

        // Exit the program with the specified exit code
        System.exit(exitCode);
    }
}
