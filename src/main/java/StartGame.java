import java.util.InputMismatchException;
import java.util.Scanner;

import model.GamePhase;
import utils.loggers.ConsoleEntryWriter;
import utils.loggers.LogEntryBuffer;
import utils.loggers.LogEntryWriter;

public class StartGame {

    private Engine d_Engine;
    private final LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    private GamePhase d_GamePhase;

    public StartGame() {
        d_LogEntryBuffer.addNewObserver(new LogEntryWriter());
        d_LogEntryBuffer.addNewObserver(new ConsoleEntryWriter());
    }

    public static void main(String[] args) throws Exception {
        new StartGame().start();
    }

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

    enum MenuOption {
        NEW_GAME(1), LOAD_GAME(2), SINGLE_GAME_MODE(3), SIMULATION_MODE(4), EXIT(5);

        private final int value;

        MenuOption(int value) {
            this.value = value;
        }

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
