package services;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import controller.*;
import model.GameMap;
import model.GamePhase;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;
import utils.maputils.MapViewer;
import utils.maputils.ValidateMap;

/**
 * Class that has the main logic behind the functioning of Map Editor phase in
 * the game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class MapEditor implements GameEngineController {
    private Scanner sc = new Scanner(System.in);
    GameMap d_GameMap;
    GamePhase d_NextState = GamePhase.StartUp;
    LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();
    private final List<String> l_PreDefinedCommands = Arrays.asList("editcontinent", "editcountry", "editneighbor",
            "showmap",
            "savemap", "editmap", "validatemap");

    /**
     * A Constructor to initialize d_GameMap
     * 
     */
    public MapEditor() {
        this.d_GameMap = GameMap.getInstance();
    }

    /**
     * Method where the command gets checked of its type(editcontinent or
     * editcountry
     * etc)
     * 
     * @param p_GamePhase the game phase
     * @throws InvalidCommandException when something failes
     */
    public GamePhase start(GamePhase p_GamePhase) throws InvalidCommandException {
        d_LogEntryBuffer.clear();
        d_LogEntryBuffer.logAction("Entered Map Editor Phase!");
        while (true) {
            System.out.println(
                    "Wow,It looks like Now you have entered in the very first phase of the game,The Map editor phase where players create the game maps");
            System.out.println("We are here to guide you throughout the game:" + "\n"
                    + "1.If you don't know what to do just type help it will show you the user commands we developed for you to create a gamemap"
                    + "\n"
                    + "2. If you are done with the map creation and want to save the phase type exit");
            String l_InputString = sc.nextLine();
            List<String> l_InputList = splitInput(l_InputString);
            if (!isValidInput(l_InputList)) {
                handleInvalidInput(l_InputList);
            }

            String l_PrimaryCommand = l_InputList.get(0);

            l_InputList.remove(l_PrimaryCommand);

            for (String l_Command : l_InputList) {
                String[] l_CommandArray = l_Command.split(" ");
                List<String> l_input = Arrays.asList(l_CommandArray);
                switch (l_PrimaryCommand.toLowerCase()) {
                    case "editcontinent": {
                        handleEditContinentCommands(l_input);
                        break;
                    }

                    case "editcountry":

                    {
                        handleEditCountryCommands(l_input);
                        break;
                    }

                    case "editneighbor": {
                        handleEditNeighborCommands(l_input);
                        break;
                    }

                    case "showmap": {
                        System.out.println("working");
                        d_GameMap.showMap();
                        break;
                    }

                    case "validatemap": {
                        handleValidateMap();
                        break;
                    }

                    case "savemap": {
                        handleSaveMap(l_input);
                        break;
                    }

                    case "editmap": {
                        handleEditMap(l_input);
                        break;
                    }

                    case "exit": {
                        exitMapCreationPhase();
                        return p_GamePhase.nextState(d_NextState);
                    }

                    default: {
                        displayHelpCommands();
                    }
                }

            }
        }
    }

    /**
     * Method that splits the Input and save it into a List of Strings
     * 
     * @param p_Input
     * @return the result as a list of string
     */
    private List<String> splitInput(String p_Input) {
        if (p_Input.contains("-")) {
            return Arrays.stream(p_Input.split("-"))
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList());
        } else {
            return Arrays.stream(p_Input.split(" ")).collect(Collectors.toList());
        }
    }

    /**
     * Method that Handles validation of the input
     * 
     * @param p_inputList
     */
    private void handleInvalidInput(List<String> p_inputList) {
        if (p_inputList.get(0).startsWith("exit")) {
            p_inputList.add(0, "exit");
        } else {
            p_inputList.clear();
            p_inputList.add("help");
            p_inputList.add("unknown");
        }
    }

    /**
     * Method that checks whether the input is valid
     * 
     * @param p_InputList list of inputs
     * @return true if the input provided by the player is present in set of
     *         PredefinedCommands or else it returns false
     */
    public boolean isValidInput(List<String> p_InputList) {
        if (p_InputList.size() == 1) {
            p_InputList.add("unknown");
        }
        String l_primaryCommand = p_InputList.get(0).toLowerCase();
        return l_PreDefinedCommands.contains(l_primaryCommand);
    }

    /**
     * Method specific to handle the editcontinent command
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void handleEditContinentCommands(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.isEmpty()) {
            return;
        }

        String l_SubCommand = p_CommandList.get(0);

        switch (l_SubCommand) {
            case "add": {
                addContinent(p_CommandList);
                break;
            }
            case "remove": {
                removeContinent(p_CommandList);
                break;
            }
            default: {
                throw new InvalidCommandException();
            }
        }
    }

    /**
     * Method specific to handle the editcountry commands
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void handleEditCountryCommands(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.isEmpty()) {
            return;
        }

        String l_SubCommand = p_CommandList.get(0);

        switch (l_SubCommand) {
            case "add": {
                addCountry(p_CommandList);
                break;
            }
            case "remove": {
                removeCountry(p_CommandList);
                break;
            }
            default: {
                throw new InvalidCommandException();
            }
        }
    }

    /**
     * Method specific to handle editneighbor command
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void handleEditNeighborCommands(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.isEmpty()) {
            return;
        }

        String l_SubCommand = p_CommandList.get(0);

        switch (l_SubCommand) {

            case "add": {
                addNeighbor(p_CommandList);
                break;
            }
            case "remove": {
                removeNeighbor(p_CommandList);
                break;
            }
            default:
                throw new InvalidCommandException();
        }
    }

    /**
     * Method that adds continent on the map
     * 
     * @param p_CommandList command list
     * @throws InvalidCommandException when something failes
     */
    public void addContinent(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }
        // Retrieve the continent name from the command list.
        String l_ContinentName = p_CommandList.get(2);
        if (d_GameMap.getContinents().containsKey(l_ContinentName)) {
            throw new InvalidCommandException("The continent " + l_ContinentName + " already exists.");
        }
        d_GameMap.addContinent(p_CommandList.get(1), p_CommandList.get(2));
    }

    /**
     * Method that removes a continent off the map
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void removeContinent(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 2) {
            throw new InvalidCommandException();
        }
        // Retrieve the continent name from the command list.
        String l_ContinentName = p_CommandList.get(1);

        // Check if the continent exists in the GameMap's continents hashmap.
        if (!d_GameMap.getContinents().containsKey(l_ContinentName)) {
            throw new InvalidCommandException("The continent " + l_ContinentName + " does not exist.");
        }
        d_GameMap.removeContinent(p_CommandList.get(1));
    }

    /**
     * Method that adds a country to the map
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void addCountry(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException("The command does not have the correct number of arguments.");
        }

        // Retrieve the continent name from the command list.
        String l_ContinentName = p_CommandList.get(2);

        // Check if the continent exists in the GameMap's continents hashmap.
        if (!d_GameMap.getContinents().containsKey(l_ContinentName)) {
            throw new InvalidCommandException("The continent " + l_ContinentName + " does not exist.");
        }

        // Since the continent exists, proceed to add the country.
        d_GameMap.addCountry(p_CommandList.get(1), l_ContinentName);
    }

    /**
     * Method that removes a country off the map
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void removeCountry(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 2) {
            throw new InvalidCommandException();
        }
        // Retrieve the country name from the command list.
        String l_CountryName = p_CommandList.get(1);

        // Check if the country exists in the GameMap's conuntries hashmap.
        if (!d_GameMap.getCountries().containsKey(l_CountryName)) {
            throw new InvalidCommandException("The Country - " + l_CountryName + " - does not exist.");
        }
        d_GameMap.removeCountry(p_CommandList.get(1));
    }

    /**
     * Method that Adds a neighbor to a country
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void addNeighbor(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }
        // Retrieve the country name from the command list.
        String l_CountryName = p_CommandList.get(2);
        String l_NeighborCountryMName = p_CommandList.get(1);
        // Check if the country exists in the GameMap's conuntries hashmap.
        if (!d_GameMap.getCountries().containsKey(l_CountryName)) {
            throw new InvalidCommandException("The Country - " + l_CountryName + " - does not exist.");
        } else if (!d_GameMap.getCountries().containsKey(l_NeighborCountryMName)) {
            throw new InvalidCommandException("The Country - " + l_NeighborCountryMName + " - does not exist.");
        }
        d_GameMap.addNeighbor(p_CommandList.get(1), p_CommandList.get(2));
    }

    /**
     * Method that removes a neighbor of a country
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void removeNeighbor(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }
        // Retrieve the country name from the command list.
        String l_CountryName = p_CommandList.get(2);
        String l_NeighborCountryMName = p_CommandList.get(1);
        // Check if the country exists in the GameMap's conuntries hashmap.
        if (!d_GameMap.getCountries().containsKey(l_CountryName)) {
            throw new InvalidCommandException("The Country - " + l_CountryName + " - does not exist.");
        } else if (!d_GameMap.getCountries().containsKey(l_NeighborCountryMName)) {
            throw new InvalidCommandException("The Country - " + l_NeighborCountryMName + " - does not exist.");
        }
        d_GameMap.removeNeighbor(p_CommandList.get(1), p_CommandList.get(2));
    }

    /**
     * Method that handles validatemap command
     * 
     */
    private void handleValidateMap() {

        String validationMessage = ValidateMap.validateMap(d_GameMap, 0) ? "Validation successful"
                : "Validation failed";
        System.out.println(validationMessage);

    }

    /**
     * Method that handles savemap command
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void handleSaveMap(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() == 1) {
            d_GameMap.setName(p_CommandList.get(0));
            d_GameMap.saveMap();
        }
    }

    /**
     * Method that handles editmap command
     * 
     * @param p_CommandList
     * @throws InvalidCommandException
     */
    private void handleEditMap(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() == 1) {
            MapViewer.readMap(d_GameMap, p_CommandList.get(0));
        }

    }

    /**
     * Method that initializes the map once the game enters the next phase
     * 
     */
    private void exitMapCreationPhase() {
        d_GameMap.ClearMap();
    }

    /**
     * Help commands to be displayed to the user for their reference
     * 
     */
    private void displayHelpCommands() {
        System.out.println(
                "The mentioned below are the list of map creation console commands developed by us for the players:");
        System.out.println("If you wish to add a continent in a map:");
        System.out.println("editcontinent -add continentID continentvalue");
        System.out.println("If you wish to remove a continent from the map");
        System.out.println("editcontinent -remove continentID");
        System.out.println("If you wish to add a country inside a continent in the map:");
        System.out.println("editcountry -add countryID continentID");
        System.out.println("If you wish to remove a country from a continent");
        System.out.println("editcountry -remove countryID");
        System.out.println("If you wish to add neighbours to the country");
        System.out.println("editneighbor -add countryID neighborcountryID");
        System.out.println("If you wish to remove a neighbour country");
        System.out.println("editneighbor -remove countryID neighborcountryID");
        System.out.println("---------------------------------------------------------");
        System.out.println("To Read/Update existing map commands:");
        System.out.println("editmap filename");
        System.out.println("---------------------------------------------------------");
        System.out.println("Some more map commands:");
        System.out.println("If you want to see the game map you developed: showmap");
        System.out.println("If you want to validate the game map: validatemap");
        System.out.println("---------------------------------------------------------");
        System.out.println("If you are done with creation of game map and want to save the gamemap, use the command:");
        System.out.println("savemap filename where filename is your choice");
        System.out.println("---------------------------------------------------------");
    }
}