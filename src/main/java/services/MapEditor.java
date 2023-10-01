package services;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import controller.*;
public class MapEditor
{
    private Scanner sc=new Scanner(System.in);
    
    public void start(int d_GamePhaseID)
    {
        System.out.println("Now you have entered the initial stage of the game, that is, Creation or editing of map");
        System.out.println("Do you have a map or would you like to create a new one?\n1) I have a map that I'd like to edit\n2) I do not have a map and I'd like to create one");
        int l_createOrEdit = Integer.parseInt(sc.nextLine());
        
        if(l_createOrEdit == 1)
        {
            System.out.println("Oh great You already have a map to load! You Smartie!");
            System.out.println("Here are the commands you'll require to edit the map you already have:-\neditmap filename");
            // new GameEngineController().controller(2);
            
            String l_command = sc.nextLine();
            List<String> l_commandList=null;
            l_commandList = Arrays.stream(l_command.split(" ")).collect(Collectors.toList());

            if(l_commandList.size() ==  2)
            {
                if(l_commandList.get(0).equalsIgnoreCase("editmap"))
                {
                    
                }
            }
       }                   

        else if(l_createOrEdit == 2)
        {
            System.out.println("Oh you don't have a map? Dont worry about it, we'll help you make one. Just follow the commands mentioned below:-\neditcontinent -add CONTINENTID AWARDARMIES or -remove CONTINENTID\neditcountry -add COUNTRYID CONTINENTNAME or -remove COUNTRYID\neditneighbor -add COUNTRYID NEIGHBORCOUNTRYID or -remove COUNTRYID NEIGHBORCOUNTRYID");
             while (true) {
            System.out.println("Enter your map operation:" + "\n" + "1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end map creation and save phase");
            String l_Input = sc.nextLine();
            List<String> l_InputList = null;
            if (l_Input.contains("-")) {
                l_InputList = Arrays.stream(l_Input.split("-"))
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } else {
                l_InputList = Arrays.stream(l_Input.split(" ")).collect(Collectors.toList());
            }

            if (!inputValidator(l_InputList)) {
                if (l_Input.startsWith("exit")) {
                    l_InputList.add(0, "exit");
                } else {
                    l_InputList.clear();
                    // if not available in command list forcing to call help
                    l_InputList.add("help");
                    l_InputList.add("dummy");
                }
            }

            /**
             * Handle editcontinent command from console
             */

            String l_PrimaryCommand = l_InputList.get(0);
            l_InputList.remove(l_PrimaryCommand);
            for (String l_Command : l_InputList) {
                String[] l_ArrayOfCommands = l_Command.split(" ");
                switch (l_PrimaryCommand.toLowerCase()) {
                    case "editcontinent": {
                        if (l_ArrayOfCommands.length > 0) {
                            switch (l_ArrayOfCommands[0]) {
                                case "add": {
                                    if (l_ArrayOfCommands.length == 3) {
                                        d_GameMap.addContinent(l_ArrayOfCommands[1], l_ArrayOfCommands[2]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (l_ArrayOfCommands.length == 2) {
                                        d_GameMap.removeContinent(l_ArrayOfCommands[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    /**
                     * Handle editcountry command from console
                     */

                    case "editcountry": {
                        switch (l_ArrayOfCommands[0]) {
                            case "add": {
                                if (l_ArrayOfCommands.length == 3) {
                                    d_GameMap.addCountry(l_ArrayOfCommands[1], l_ArrayOfCommands[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                            case "remove": {
                                if (l_ArrayOfCommands.length == 2) {
                                    d_GameMap.removeCountry(l_ArrayOfCommands[1]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                        }
                        break;
                    }

                    /**
                     * Handle editneighbor command from console
                     */

                    case "editneighbor": {
                        switch (l_ArrayOfCommands[0]) {
                            case "add": {
                                if (l_ArrayOfCommands.length == 3) {
                                    d_GameMap.addNeighbor(l_ArrayOfCommands[1], l_ArrayOfCommands[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                            case "remove": {
                                if (l_ArrayOfCommands.length == 3) {
                                    d_GameMap.removeNeighbor(l_ArrayOfCommands[1], l_ArrayOfCommands[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                        }
                        break;
                    }

                    /**
                     * Handle showmap command from console
                     */

                    case "showmap": {
                        d_GameMap.showMap();
                        break;
                    }
                    //Handle validatemap command from console
                    case "validatemap": {
                        if (MapValidation.validateMap(d_GameMap, 0)) {
                            System.out.println("Validation successful");
                        } else {
                            System.out.println("Validation failed");
                        }
                        break;
                    }

                    /**
                     * Handle savemap command from console
                     */

                    case "savemap": {
                        if (l_ArrayOfCommands.length == 1) {
                            d_GameMap.setName(l_ArrayOfCommands[0]);
                            d_GameMap.saveMap();
                        }
                        break;
                    }

                    /**
                     * Handle editmap command from console
                     */

                    case "editmap": {
                        if (l_ArrayOfCommands.length == 1) {
                            MapReader.readMap(d_GameMap, l_ArrayOfCommands[0]);
                        }
                        break;
                    }

                    /**
                     * To exit the map creation phase type "exit"
                     */

                    case "exit": {
                        d_GameMap.flushGameMap();
                        //return p_GamePhase.nextState(d_NextState);
                        return GameEngineController().controller(2);
                    }
                    //Print the commands for help

                    default: {
                        System.out.println("List of user map creation commands from console:");
                        System.out.println("To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID");
                        System.out.println("To add or remove a country : editcountry -add countryID continentID -remove countryID");
                        System.out.println("To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Read/Update existing map commands:");
                        System.out.println("To edit map: editmap filename");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Additional map commands:");
                        System.out.println("To show the map: showmap");
                        System.out.println("To validate map: validatemap");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Note: To save the created map use the command:");
                        System.out.println("To save map: savemap filename");
                        System.out.println("================================End of Map Editor Phase==================================");
                    }
                }
            }
        }
    }
}

    /**
     * This method validates to check if the current cli command is executable
     * in the current phase
     *
     * @param p_InputList the command list from console
     * @return true if command is executable else false
     */
    // public boolean inputValidator(List<String> p_InputList) {
    //     if (p_InputList.size() > 0) {
    //         String l_MainCommand = p_InputList.get(0);
    //         if (p_InputList.size() == 1) {
    //             p_InputList.add("dummy");
    //         }
    //         return CLI_COMMANDS.contains(l_MainCommand.toLowerCase());
    //     }
    //     return false;
    // }
    public boolean isValidInput(List<String> p_InputList) 
    {
        if (!p_InputList.isEmpty()) 
        {
            String l_primaryCommand = p_InputList.get(0).toLowerCase();
             return CLI_COMMANDS.contains(l_primaryCommand);
        }
        return false;
    }   

}