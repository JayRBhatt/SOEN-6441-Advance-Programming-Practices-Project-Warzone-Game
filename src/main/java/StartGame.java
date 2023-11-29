import java.util.InputMismatchException;
import java.util.Scanner;

import model.GamePhase;
import utils.loggers.ConsoleEntryWriter;
import utils.loggers.LogEntryBuffer;
import utils.loggers.LogEntryWriter;
/**
 * Class to implement the game
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class StartGame { 
    /**
     * game engine
     */
    private Engine d_Engine;
    /**
     * logEntryBuffer observable
     */
    private final LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    /**
     * game phase
     */
    private GamePhase d_GamePhase;
    /**
     * Default Constructor
     */
    public StartGame() {
        d_LogEntryBuffer.addNewObserver(new LogEntryWriter());
        d_LogEntryBuffer.addNewObserver(new ConsoleEntryWriter());
    }
      /**
     * method to implement main class to start game
     *
     * @param args the arguments
     * @throws Exception if it occurs
     */
    public static void main(String[] args) throws Exception {
        new StartGame().start();
    }
    
    /**
     * method which starts each phase in the game
     *
     * @throws Exception when it occurs
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
    * displays the mode selection when game is started
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
     * Processes the selected menu option and updates the game phase accordingly.
     * @param option The selected menu option to process.
     * @throws InvalidOptionException If the selected menu option is not recognized or is invalid.
     */
    private void processMenuOption(MenuOption option) throws InvalidOptionException {
        switch (option) {
            case NEW_GAME:
                d_GamePhase = GamePhase.MapEditor;
                break;
            case LOAD_GAME:
                d_GamePhase = GamePhase.StartUp;
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
     * Enumeration representing the available menu options in the Risk game.
     
     */
    enum MenuOption {
        NEW_GAME(1), LOAD_GAME(2), SINGLE_GAME_MODE(3), SIMULATION_MODE(4), EXIT(5);

        private final int value;
     /**
     * Constructs a MenuOption with the specified integer value.
     *
     * @param value The integer value associated with the menu option.
     */
        MenuOption(int value) {
            this.value = value;
        }
      /**
     * Converts an integer into the corresponding MenuOption.
     *
     * @param option The integer value to convert into a MenuOption.
     * @return The MenuOption corresponding to the provided integer.
     * @throws InvalidOptionException If the provided integer does not match any valid menu option.
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
     
    static class InvalidOptionException extends Exception {
        InvalidOptionException() {
            super("Invalid menu option selected.");
        }
    }
}
