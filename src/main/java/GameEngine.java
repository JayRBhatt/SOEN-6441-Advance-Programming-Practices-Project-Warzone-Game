import controller.GameEngineController;

public class GameEngine
{
    int  d_GamePhaseID = 1;
    public static void main(String args[])
    {
        new GameEngine().start();
    }

    public void start()
    {
        System.out.println("Welcome to the Fiercest Game of Warzone Risk!! Get ready to conquer the world!! ");
        System.out.println("General Instructions:\n->Type help if you want assistance with the commmands\n-------------------------------------\n->Type Exit to end the game at any stage you like(Hope you stay till the end ;) )");
        new GameEngineController().controller(d_GamePhaseID);
    }
}