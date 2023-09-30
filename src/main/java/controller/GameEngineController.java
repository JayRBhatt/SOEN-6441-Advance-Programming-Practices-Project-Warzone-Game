package controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import services.*;

/**
 * A class that manages the flow of diffrent phases of the warzone game
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */


public class GameEngineController 
{
     /**
     * @param p_GamePhaseID holding the ID of the current game phase
     */

    public void controller(int p_GamePhaseID)
    {
        switch(p_GamePhaseID)
        {
            case 1:
                new MapEditor().start(p_GamePhaseID);
                break;
            case 2:
                new GamePlayBegins().runPhase(p_GamePhaseID);
                break;
                
            case 3:
                System.out.println("In 3rd Phaseee");
                break;

                default:
                    System.out.println("khotu");
        }
    }

}
