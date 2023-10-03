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
 * A class that starts the game by giving the player instructions
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */

public class GamePlayBegins
{
    private final Scanner sc = new Scanner(System.in);
    private final List<String> l_PreDefinedCommands = Arrays.asList("showmap", "loadmap", "gameplayer", "assigncountries");
    GameMap d_GameMap;
    
    /**
     * GamePlayBegins constructor
     */
    public GamePlayBegins() {
        d_GameMap = GameMap.getInstance();
    }
    
    /**
     * Method that runs the current phase
     * @param p_GamePhaseID the current phase Id
     * @throws InvalidCommandException in case of any invalid commands
     */
    public void runPhase(int p_GamePhaseID) throws InvalidCommandException
    {
        System.out.println("I'm in \"GamePlayBegins\" phase");
        while (true) {
            System.out.println("1. If you need help DON'T WORRY We have got your back just type help to view the list of commands " + "\n" + "2. If you want to exit the game just type exit");
            String l_InputString = sc.nextLine();
            List<String> l_InputList =  splitInput(l_InputString);
          
            if (!isValidInput(l_InputList)) 
            {
                if (l_InputString.startsWith("exit")) 
                {
                    l_InputList.add(0, "exit");
                } 
                else 
                {
                    l_InputList.clear();
                    // if not available in command list forcing to call help
                    l_InputList.add("help");
                    l_InputList.add("unknown");
                }
            }
            //Handle loadmap command from console

            String l_PrimaryCommand = l_InputList.get(0);
            l_InputList.remove(l_PrimaryCommand);
            for (String l_Command : l_InputList) 
            {
                String[] l_ArrayOfCommands = l_Command.split(" ");
                switch (l_PrimaryCommand.toLowerCase()) 
                {
                    case "loadmap": 
                    {
                        if (l_ArrayOfCommands.length == 1) 
                        {
                            loadMapFromFile(l_ArrayOfCommands[0]);
                        }
                        break;
                    }
                    //Handle gameplayer command from console

                    case "gameplayer": 
                    {
                        if (l_ArrayOfCommands.length > 0) 
                        {
                            switch (l_ArrayOfCommands[0]) 
                            {
                                case "add": 
                                {
                                    if (l_ArrayOfCommands.length == 2) 
                                    {
                                        d_GameMap.addGamePlayer(l_ArrayOfCommands[1]);
                                    } 
                                    else 
                                    {
                                        throw new InvalidCommandException();
                                    }
                                    break;
                                }
                                case "remove": 
                                {
                                    if (l_ArrayOfCommands.length == 2) 
                                    {
                                        d_GameMap.removeExistingPlayer(l_ArrayOfCommands[1]);
                                    } 
                                    else 
                                    {
                                        throw new InvalidCommandException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    //Handle assigncountries command from console

                    case "assigncountries": 
                    {
                        if (d_GameMap.getGamePlayers().size() > 1) 
                        {
                            d_GameMap.assignCountries();
                            System.out.println("================================End of GamePlay begins Phase==================================");
                                new GameEngineController().controller(3);
                            } 
                        else 
                        {
                            throw new InvalidCommandException("Create atleast two players before assigning countries");
                        }
                    }
                    //Handle showmap command from console

                    case "showmap": 
                    {
                        d_GameMap.showMap();
                        break;
                    }
                    case "exit": 
                    {
                        new GameEngineController().controller(3);
                    }
                    //Print the commands for help
                    default: {
                        System.out.println("Order of game play commands:");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("If you wish to load the map type : loadmap filename");
                        System.out.println("If you wish to see the existing map type : showmap");
                        System.out.println("If you wish to add the player to the game type : gameplayer -add playername");
                        System.out.println("If you have added any player by mistake or if you want to get rid of any player type: gameplayer -remove player");
                        System.out.println("If you wish to assign countries to the players type : assigncountries");
                        System.out.println("-----------------------------------------------------------------------------------------");

                    }
                }
            }
        }
        // new GameEngineController().controller(3);
        // System.out.println("I'm in \"GamePlayBegins\" phase");

    }
    /**
     * Method that loads the map from file
     * @param p_Filename the file name
     * @throws InvalidCommandException in case of any invalid commands
     */
    private void loadMapFromFile(String p_Filename) throws InvalidCommandException 
    {
        MapViewer.readMap(d_GameMap, p_Filename);
        if (!ValidateMap.validateMap(d_GameMap, 0)) 
        {
            throw new InvalidCommandException("OOPS! I fear that this is not the right map");
        }
    }

    /**
     * This method validates to check if the current cli command is executable
     * in the current phase
     * @param p_InputList the command list from console
     * @return true if command is executable else false
     */
    public boolean isValidInput(List<String> p_InputList) 
    {
        if (p_InputList.size()==1) 
        {
            p_InputList.add("unknown");
        }   
            String l_primaryCommand = p_InputList.get(0).toLowerCase();
             return l_PreDefinedCommands.contains(l_primaryCommand);
    }  
    
    /**
     * Method to split the input string
     * @param p_Input the input
     * @return the 
     */
    private List<String> splitInput(String p_Input){
          if (p_Input.contains("-")) 
            {
                return Arrays.stream(p_Input.split("-"))
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } 
            else 
            {
                return Arrays.stream(p_Input.split(" ")).collect(Collectors.toList());
            }

    }
}