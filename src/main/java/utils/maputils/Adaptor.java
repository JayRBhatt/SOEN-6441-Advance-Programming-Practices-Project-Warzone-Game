package utils.maputils;

import java.io.IOException;

import model.GameMap;
import utils.exceptions.InvalidCommandException;
/**
 * This class is used to implement Adapter Pattern
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class Adaptor extends DominationMap {
    /**
     *  target map type
     */
    public static final String mapType="Domination";

    ConquestMap d_adaptor = new ConquestMap();

    /**
     * Constructor to initialize adaptee object
     * @param adp Object of Conquest map class
     */
    public Adaptor(ConquestMap adp) {
        this.d_adaptor = adp;
    }

    /**
     * This method loads the map file
     * @param p_GameMap  the game map
     * @param p_FileName the map file name
     * @throws InvalidCommandException when it happens
     */

    public void readMap(GameMap p_GameMap, String p_FileName) throws InvalidCommandException {
        d_adaptor.readMap(p_GameMap, p_FileName);
    }

    /**
     *Saves the input map to a given file name. Overwrites any existing map with the same name.
     * The map will only save if it is valid.
     * @param map The map to save.
     * @param fileName The name of the file to save to, including the extension.
     * @return Whether the file was successfully saved.
     * @throws IOException files exception
     */
    public boolean saveMap(GameMap map, String fileName)  throws IOException {
        return d_adaptor.saveMap(map, fileName);
    }

}
