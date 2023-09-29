package controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import services.*;

public class GameEngineController 
{
    public void controller(int d_GamePhaseID)
    {
        switch(d_GamePhaseID)
        {
            case 1:
                new MapEditor().start(d_GamePhaseID);
                break;
            case 2:
                new GamePlayBegins().runPhase(d_GamePhaseID);
                break;
                
            case 3:
                System.out.println("In 3rd Phaseee");
                break;

                default:
                    System.out.println("khotu");
        }
    }

}
