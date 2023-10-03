package services;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import controller.*;
import model.GameMap;
import utils.InvalidCommandException;
import utils.maputils.MapViewer;
import utils.maputils.ValidateMap;

public class MapEditor {
    private Scanner sc = new Scanner(System.in);
    GameMap d_GameMap;
    private final List<String> l_PreDefinedCommands = Arrays.asList("editcontinent", "editcountry", "editneighbor",
            "showmap",
            "savemap", "editmap", "validatemap");

    public MapEditor() {
        this.d_GameMap = GameMap.getInstance();
    }

    public void mapEdit(int p_GamePhaseID) throws InvalidCommandException {
        while (true) {
            System.out.println("Wow,It looks like Now you have entered in the very first phase of the game,The Map editor phase where players create the game maps");
            System.out.println("We are here to guide you throughout the game:" + "\n" + "1.If you don't know what to do just type help it will show you the user commands we developed for you to create a gamemap" + "\n"
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
                        System.out.println("after object");

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
                        new GameEngineController().controller(2);
                    }

                    default: {
                        displayHelpCommands();
                    }
                }

            }
        }
    }

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

    private void handleInvalidInput(List<String> p_inputList) {
        if (p_inputList.get(0).startsWith("exit")) {
            p_inputList.add(0, "exit");
        } else {
            p_inputList.clear();
            p_inputList.add("help");
            p_inputList.add("unknown");
        }
    }

    public boolean isValidInput(List<String> p_InputList) {
        if (p_InputList.size() == 1) {
            p_InputList.add("unknown");
        }
        String l_primaryCommand = p_InputList.get(0).toLowerCase();
        return l_PreDefinedCommands.contains(l_primaryCommand);
    }

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

    private void addContinent(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }

        d_GameMap.addContinent(p_CommandList.get(1), p_CommandList.get(2));
    }

    private void removeContinent(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 2) {
            throw new InvalidCommandException();
        }

        d_GameMap.removeContinent(p_CommandList.get(1));
    }

    private void addCountry(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }

        d_GameMap.addCountry(p_CommandList.get(1), p_CommandList.get(2));
    }

    private void removeCountry(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 2) {
            throw new InvalidCommandException();
        }

        d_GameMap.removeCountry(p_CommandList.get(1));
    }

    private void addNeighbor(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }

        d_GameMap.addNeighbor(p_CommandList.get(1), p_CommandList.get(2));
    }

    private void removeNeighbor(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() != 3) {
            throw new InvalidCommandException();
        }
        System.out.println(p_CommandList.get(2));
        d_GameMap.removeNeighbor(p_CommandList.get(1), p_CommandList.get(2));
    }

    private void handleValidateMap() {

        String validationMessage = ValidateMap.validateMap(d_GameMap, 0) ? "Validation successful"
                : "Validation failed";
        System.out.println(validationMessage);

    }

    private void handleSaveMap(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() == 1) {
            d_GameMap.setName(p_CommandList.get(0));
            d_GameMap.saveMap();
        }
    }

    private void handleEditMap(List<String> p_CommandList) throws InvalidCommandException {
        if (p_CommandList.size() == 1) {
            MapViewer.readMap(d_GameMap, p_CommandList.get(0));
        }

    }

    private void exitMapCreationPhase() {
        d_GameMap.ClearMap();
    }

    private void displayHelpCommands() {
        System.out.println("The mentioned below are the list of map creation console commands developed by us for the players:");
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