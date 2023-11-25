package utils.maputils;

import model.GameMap;
import model.GamePhase;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A class to save and load game progress
 * @author Jay Bhatt
 * @version 1.0.0
 *
 */
public class GameProgress {
    /**
     * Constant path
     */
    static final String PATH = "savedFiles/";

    /**
     * LogEntry Buffer Instance
     */
    private static LogEntryBuffer d_LogManager = LogEntryBuffer.getInstance();

    /**
     * A function to save the game progress
     *
     * @param p_MapState instance of the game
     * @param p_FileLabel file name
     * @return true if successful else false
     */
    public static boolean storeGameState(GameMap p_MapState, String p_FileLabel) {
        try {
            FileOutputStream l_fileStream = new FileOutputStream(PATH + p_FileLabel + ".bin");
            ObjectOutputStream l_objectStream = new ObjectOutputStream(l_fileStream);
            l_objectStream.writeObject(p_MapState);
            d_LogManager.logAction("The game has been saved successfully to file ./savedFiles/" + p_FileLabel + ".bin");
            l_objectStream.flush();
            l_fileStream.close();
            p_MapState.ClearMap();
            return true;
        } catch (Exception p_Error) {
            d_LogManager.logAction(p_Error.toString());
            return false;
        }
    }

    /**
     * A function to load the game progress
     *
     * @param p_SaveFileName the file name string
     * @return GamePhase instance
     */
    public static GamePhase retrieveGameState(String p_SaveFileName) {
        FileInputStream l_fileStream;
        GameMap l_retrievedMap;
        try {
            l_fileStream = new FileInputStream(PATH + p_SaveFileName);
            ObjectInputStream l_objectStream = new ObjectInputStream(l_fileStream);
            l_retrievedMap = (GameMap) l_objectStream.readObject();
            d_LogManager.logAction("The game is loaded successfully will continue from where it last stopped.");
            l_objectStream.close();
            return GameMap.getInstance().gamePlayBuilder(l_retrievedMap);
        } catch (IOException | ClassNotFoundException | InvalidCommandException p_Error) {
            d_LogManager.logAction("The file could not be loaded.");
            return GamePhase.StartUp;
        }
    }

    /**
     * A function to show the files
     *
     * @throws IOException File exception
     */
    public static void displaySavedGames() throws IOException {
        d_LogManager.logAction("==================================");
        d_LogManager.logAction("\t\t\t Warzone");
        d_LogManager.logAction("==================================");
        d_LogManager.logAction("\t\t\t Load Game");
        d_LogManager.logAction("\t=======================\n");
        if (new File(PATH).exists()) {
            Files.walk(Path.of(PATH))
                    .filter(path -> path.toFile().isFile())
                    .forEach(path -> {
                        d_LogManager.logAction("\t\t " + path.getFileName());
                    });
        } else {
            d_LogManager.logAction("\t\t " + "no load files found");
        }
        d_LogManager.logAction("");
        d_LogManager.logAction("\t=======================");
        d_LogManager.logAction("\t use file name to load");
        d_LogManager.logAction("==================================");
        d_LogManager.logAction("example command: loadgame");
    }
}
