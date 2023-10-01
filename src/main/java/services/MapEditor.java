package services;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
            l_commandList = Arrays.stream(l_command.split(" ")).collect(Colectors.toList());

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
            String l_mapCreation = sc.nextLine();


        
        }
        
        
    }
}