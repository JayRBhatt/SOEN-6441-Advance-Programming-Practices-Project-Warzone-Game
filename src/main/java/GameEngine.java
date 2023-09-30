import controller.GameEngineController;

/**
 * A class that starts the game with the first phase Edit Map
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */

public class GameEngine
{

    /**
	 * d_GamePhaseID is initialized to showcase the start of the game and its flow to the mapEditor Phase ~ the first phase  
	 */

    int  d_GamePhaseID = 1;

    /**
     * The main method for accepting command from users to run the warzone game
     * 
     * @param args are passed to main if used in command line
     */
    
    public static void main(String args[])
    {
        new GameEngine().start();
    }

    /**
     * The function which takes the game to different phases through gameController
     */

    public void start()
    {
        System.out.println("Welcome to the Fiercest Game of Warzone Risk!! Get ready to conquer the world!! ");
        System.out.println("General Instructions:\n->Type help if you want assistance with the commmands\n-------------------------------------\n->Type Exit to end the game at any stage you like(Hope you stay till the end ;) )");
        new GameEngineController().controller(d_GamePhaseID);
    }
}