package services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import utils.maputils.MapViewer;
import utils.maputils.ValidateMap;
import controller.GameEngineController;
import model.*;
import utils.InvalidCommandException;

/**
 * Class that has the main logic behind the functioning of GameplayBegins phase in
 * the game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */

public class GamePlayBegins implements GameEngineController{
    private final Scanner sc = new Scanner(System.in);
    private final List<String> l_PreDefinedCommands = Arrays.asList("showmap", "loadmap", "gameplayer",
            "assigncountries");
    GameMap d_GameMap;
    GamePhase d_NextState = GamePhase.Reinforcement;

    /**
     * Constructor to initialize d_GameMap
     * 
     */
    public GamePlayBegins() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This function runs the phase and execute the tasks depending on the commands
     * given
     * 
     * @param p_GamePhase current Game Phase
     * @throws InvalidCommandException when any invalid command is encountered
     */
    public  GamePhase start(GamePhase p_GamePhase) throws InvalidCommandException {
        System.out
                .println("================================ End of Map Editor Phase ==================================");
        System.out.println(
                "Congo!! It looks like you have made it to the second phase of the game the GamePlayBegins phase");
        while (true) {
            System.out.println(
                    "1. If you need help DON'T WORRY We have got your back just type help to view the list of commands "
                            + "\n" + "2. If you want to exit the game just type exit");
            String l_InputString = sc.nextLine();
            List<String> l_InputList = splitInput(l_InputString);

            if (!isValidInput(l_InputList)) {
                if (l_InputString.startsWith("exit")) {
                    l_InputList.add(0, "exit");
                } else {
                    l_InputList.clear();
                    l_InputList.add("help");
                    l_InputList.add("unknown");
                }
            }

            String l_PrimaryCommand = l_InputList.get(0);
            l_InputList.remove(l_PrimaryCommand);
            for (String l_Command : l_InputList) {
                String[] l_ArrayOfCommands = l_Command.split(" ");
                switch (l_PrimaryCommand.toLowerCase()) {
                    case "loadmap": {
                        if (l_ArrayOfCommands.length == 1) {
                            loadMapFromFile(l_ArrayOfCommands[0]);
                        }
                        break;
                    }

                    case "gameplayer": {
                        if (l_ArrayOfCommands.length > 0) {
                            switch (l_ArrayOfCommands[0]) {
                                case "add": {
                                    if (l_ArrayOfCommands.length == 2) {
                                        d_GameMap.addGamePlayer(l_ArrayOfCommands[1]);
                                    } else {
                                        throw new InvalidCommandException();
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (l_ArrayOfCommands.length == 2) {
                                        d_GameMap.removeExistingPlayer(l_ArrayOfCommands[1]);
                                    } else {
                                        throw new InvalidCommandException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    case "assigncountries": {
                        if (d_GameMap.getGamePlayers().size() > 1) {
                            d_GameMap.assignCountries();

                            return p_GamePhase.nextState(d_NextState);
                        } else {
                            throw new InvalidCommandException("Create atleast two players before assigning countries");
                        }
                    }

                    case "showmap": {
                        d_GameMap.showMap();
                        break;
                    }
                    case "exit": {
                        return p_GamePhase.nextState(d_NextState);
                    }
                    default: {
                        System.out.println("The list of user commands for these phase are mentioned below:");
                        System.out.println(
                                "-----------------------------------------------------------------------------------------");
                        System.out.println("If you wish to load the map type : loadmap filename");
                        System.out.println("If you wish to see the existing map type : showmap");
                        System.out
                                .println("If you wish to add the player to the game type : gameplayer -add playername");
                        System.out.println(
                                "If you have added any player by mistake or if you want to get rid of any player type: gameplayer -remove player");
                        System.out.println("If you wish to assign countries to the players type : assigncountries");
                        System.out.println(
                                "-----------------------------------------------------------------------------------------");

                    }
                }
            }
        }
    }

    /**
     * This method loads the game map from the map file
     *
     * @param p_Filename the map file name
     * @throws ValidationException when validation fails
     */

    private void loadMapFromFile(String p_Filename) throws InvalidCommandException {
        MapViewer.readMap(d_GameMap, p_Filename);
        if (!ValidateMap.validateMap(d_GameMap, 0)) {
            throw new InvalidCommandException("OOPS! I fear that this is not the right map");
        }
    }

    /**
     * This method checks whether the current command is executable in this phase or
     * not
     *
     * @param p_InputList the command list from console
     * @return true if command is executable or else it returns false
     */
    public boolean isValidInput(List<String> p_InputList) {
        if (p_InputList.size() == 1) {
            p_InputList.add("unknown");
        }
        String l_primaryCommand = p_InputList.get(0).toLowerCase();
        return l_PreDefinedCommands.contains(l_primaryCommand);
    }

    /**
     * Method to Split the inputstring based on hyphens(-) and spaces(" ")
     * 
     * @param p_Input user input
     * @return list of String obtained by splitting the input string.
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
}