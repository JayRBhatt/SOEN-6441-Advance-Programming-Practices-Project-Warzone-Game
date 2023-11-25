package model.orders;

import java.io.Serializable;
import java.util.StringJoiner;

import model.Country;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * A class to create Orders in the game.
 *
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public class OrderCreator implements Serializable {
    /**
     * Static object of Game Map to hold instance of game map
     */
    public static GameMap d_GameMap = GameMap.getInstance();
    /**
     * Static object of LogEntryBuffer to hold instance
     */
    static LogEntryBuffer d_LogEntryBuffer = LogEntryBuffer.getInstance();

    /**
     * A function to create an order
     *
     * @param p_commands the command entered
     * @param player     object parameter of type Player
     * @return the order
     */
    public static Order CreateOrder(String[] p_commands, Player player) {
        String l_Type = p_commands[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case "deploy":
                l_Order = new DeployOrder();
                l_Order.setOrderDetails(GenerateDeployOrderInfo(p_commands, player));
                break;
            case "advance":
                l_Order = new AdvancingOrder();
                l_Order.setOrderDetails(GenerateAdvanceOrderInfo(p_commands, player));
                break;
            case "negotiate":
                l_Order = new NegotiatingOrder();
                l_Order.setOrderDetails(GenerateNegotiateOrderInfo(p_commands, player));
                break;
            case "blockade":
                l_Order = new OrderForBlockade();
                l_Order.setOrderDetails(GenerateBlockadeOrderInfo(p_commands, player));
                break;
            case "airlift":
                l_Order = new AirliftingOrder();
                l_Order.setOrderDetails(GenerateAirliftOrderInfo(p_commands, player));
                break;
            case "bomb":
                l_Order = new BombingOrder();
                l_Order.setOrderDetails(GenerateBombOrderInfo(p_commands, player));
                break;
            default:
                System.out.println("\nFailed to create an order due to invalid arguments");
                l_Order = null;

        }
        return l_Order;
    }

    /**
     * A function to generate the information of Deploying the order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderDetails GenerateDeployOrderInfo(String[] p_Command, Player p_Player) {
        Country l_Country = d_GameMap.getCountry(p_Command[1]);
        int l_NumberOfArmies = Integer.parseInt(p_Command[2]);
        OrderDetails l_OrderInfo = new OrderDetails();
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setCountryWhereDeployed(l_Country);
        l_OrderInfo.setAmountOfArmy(l_NumberOfArmies);
        if (p_Player.getAdditionalArmies() > 0 && l_NumberOfArmies <= p_Player.getIssuedArmies()
                && l_NumberOfArmies > 0) {
            p_Player.setIssuedArmies(p_Player.getIssuedArmies() - l_NumberOfArmies);
        }
        return l_OrderInfo;
    }

    /**
     * A function to generate the information for advance order
     *
     * @param p_Command the command entered
     * @param p_Player  the player who issued the order
     * @return the order information of advance/attack
     */
    public static OrderDetails GenerateAdvanceOrderInfo(String[] p_Command, Player p_Player) {
        String l_FromCountryID = p_Command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_Command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_Command[3]);
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setCommand(ConvertToString(p_Command));
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setDeparture(l_FromCountry);
        l_OrderDetails.setCountryWhereDeployed(l_ToCountry);
        l_OrderDetails.setAmountOfArmy(l_NumberOfArmies);
        return l_OrderDetails;
    }

    /**
     * A function to generate the information of Negotiating order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderDetails GenerateNegotiateOrderInfo(String[] p_Command, Player p_Player) {
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setCommand(ConvertToString(p_Command));
        l_OrderDetails.setNeutralPlayer(d_GameMap.getGamePlayer(p_Command[1]));
        return l_OrderDetails;
    }

    /**
     * A function to generate information about Blockade Order
     *
     * @param p_command the command entered
     * @param p_player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderDetails GenerateBlockadeOrderInfo(String[] p_command, Player p_player) {
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setCommand(ConvertToString(p_command));
        l_OrderDetails.setPlayer(p_player);
        String l_CountryID = p_command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderDetails.setTargetCountry(l_TargetCountry);
        return l_OrderDetails;
    }

    /**
     * function to generate information about Airlifting Order
     *
     * @param p_command the command entered
     * @param p_player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderDetails GenerateAirliftOrderInfo(String[] p_command, Player p_player) {
        String l_FromCountryID = p_command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_command[3]);
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setCommand(ConvertToString(p_command));
        l_OrderDetails.setPlayer(p_player);
        l_OrderDetails.setDeparture(l_FromCountry);
        l_OrderDetails.setCountryWhereDeployed(l_ToCountry);
        l_OrderDetails.setAmountOfArmy(l_NumberOfArmies);
        return l_OrderDetails;
    }

    /**
     * function to generate information about Bombing Order
     *
     * @param p_command the command entered
     * @param p_player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderDetails GenerateBombOrderInfo(String[] p_command, Player p_player) {
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setCommand(ConvertToString(p_command));
        l_OrderDetails.setPlayer(p_player);
        String l_CountryID = p_command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderDetails.setTargetCountry(l_TargetCountry);
        return l_OrderDetails;
    }

    /**
     * The method to convert command to string
     *
     * @param p_Commands the command entered
     * @return the string
     */
    private static String ConvertToString(String[] p_Commands) {
        StringJoiner l_Joiner = new StringJoiner(" ");
        for (int l_Index = 0; l_Index < p_Commands.length; l_Index++) {
            l_Joiner.add(p_Commands[l_Index]);
        }
        return l_Joiner.toString();
    }
}
