import java.util.InputMismatchException;
import java.util.Scanner;

import model.GamePhase;
import utils.loggers.ConsoleEntryWriter;
import utils.loggers.LogEntryBuffer;
import utils.loggers.LogEntryWriter;

/**
 * Entry point for the Warzone game.
 */
public class StartGame {

    private Engine d_Engine;
    private final LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    private GamePhase d_GamePhase;

    

    /**
     * Constructs a StartGame instance and initializes loggers.
     */
    public StartGame() {
        d_LogEntryBuffer.addNewObserver(new LogEntryWriter());
        d_LogEntryBuffer.addNewObserver(new ConsoleEntryWriter());
    }

    /**
     * Main method to start the Warzone game.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception If an exception occurs during game execution.
     */
    public static void main(String[] args) throws Exception {
        new StartGame().start();
    }

    /**
     * Starts the Warzone game, displaying the main menu and processing user input.
     * @throws Exception If an exception occurs during game execution.
     */
    public void start() throws Exception {
        Scanner l_Scanner = new Scanner(System.in);
        boolean continueRunning = true;
        d_Engine = new GameEngine();

        while (continueRunning) {
            displayMainMenu();

            try {
                int option = l_Scanner.nextInt();
                processMenuOption(MenuOption.fromInt(option));
                continueRunning = d_GamePhase != GamePhase.ExitGame;
                if (continueRunning) {
                    d_Engine.setGamePhase(d_GamePhase);
                    d_Engine.start();
                }
            } catch (InputMismatchException e) {
                d_LogEntryBuffer.logAction("\nInvalid input. Please enter a number.");
                l_Scanner.nextLine(); // Clear the invalid input
            } catch (InvalidOptionException e) {
                d_LogEntryBuffer.logAction("\nPlease choose a correct option number");
            }
        }
    }

    /**
     * Displays the main menu for the Warzone game.
     */
    private void displayMainMenu() {
        d_LogEntryBuffer.logAction("");
        d_LogEntryBuffer.logAction("==================================");
        d_LogEntryBuffer.logAction("\t\t\t Warzone");
        d_LogEntryBuffer.logAction("==================================");
        d_LogEntryBuffer.logAction("\t\t\t Main Menu");
        d_LogEntryBuffer.logAction("\t=======================");
        d_LogEntryBuffer.logAction("\t\t 1. New Game");
        d_LogEntryBuffer.logAction("\t\t 2. Load Game");
        d_LogEntryBuffer.logAction("\t\t 3. Single Game Mode");
        d_LogEntryBuffer.logAction("\t\t 4. Tournament Mode");
        d_LogEntryBuffer.logAction("\t\t 5. Exit");
        d_LogEntryBuffer.logAction("\t=======================");
        d_LogEntryBuffer.logAction("\t\tSelect the option");
        d_LogEntryBuffer.logAction("==================================");

    }
    
    /**
     * Method that processes the user-selected menu option
     *
     * @param option The selected menu option.
     * @throws InvalidOptionException If an invalid menu option is selected.
     */

    private void processMenuOption(MenuOption option) throws InvalidOptionException {
        switch (option) {
            case NEW_GAME:
                d_GamePhase = GamePhase.MapEditor;
                break;
            case LOAD_GAME:
                d_GamePhase = GamePhase.LoadGame;
                break;
            case SINGLE_GAME_MODE:
                d_Engine = new SingleGameMode();
                break;
            case SIMULATION_MODE:
                d_Engine = new TournamentGameMode();
                break;
            case EXIT:
                d_GamePhase = GamePhase.ExitGame;
                break;
            default:
                throw new InvalidOptionException();
        }
    }
    
    /**
     * Enumeration representing the menu options available in the Warzone game.
     */

    enum MenuOption {
        NEW_GAME(1), LOAD_GAME(2), SINGLE_GAME_MODE(3), SIMULATION_MODE(4), EXIT(5);

        private final int value;

        MenuOption(int value) {
            this.value = value;
        }
        
        /**
         * Method that converts an integer value to the corresponding MenuOption.
         *
         * @param option The integer representing the menu option.
         * @return The MenuOption corresponding to the provided integer.
         * @throws InvalidOptionException If an invalid integer is provided.
         */

        public static MenuOption fromInt(int option) throws InvalidOptionException {
            for (MenuOption mo : MenuOption.values()) {
                if (mo.value == option) {
                    return mo;
                }
            }
            throw new InvalidOptionException();
        }
    }

    /**
     * Custom exception class for handling invalid menu options.
     */
    static class InvalidOptionException extends Exception {
        InvalidOptionException() {
            super("Invalid menu option selected.");
        }
    }
}
