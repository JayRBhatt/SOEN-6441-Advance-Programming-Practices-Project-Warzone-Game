package model.orders;

import model.Country;
import model.GameMap;
import model.Player;
import utils.loggers.LogEntryBuffer;

/**
 * A class to create Orders in the game.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */
public class OrderCreator {
    /**
     * Static object of Game Map to hold instance of game map
     */
    public static GameMap d_GameMap = GameMap.getInstance();
    /**
     * Static object of LogEntryBuffer to hold instance
     */
    static LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

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
                l_Order = new AdvanceOrder();
                l_Order.setOrderDetails(GenerateAdvanceOrderInfo(p_commands, player));
                break;
            case "negotiate":
                l_Order = new NegotiateOrder();
                l_Order.setOrderDetails(GenerateNegotiateOrderInfo(p_commands, player));
                break;
            case "blockade":
                l_Order = new BlockadeOrder();
                l_Order.setOrderDetails(GenerateBlockadeOrderInfo(p_commands, player));
                break;
            case "airlift":
                l_Order = new AirliftOrder();
                l_Order.setOrderDetails(GenerateAirliftOrderInfo(p_commands, player));
                break;
            case "bomb":
                l_Order = new BombOrder();
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
    private static OrderDetails GenerateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        Country l_Country = d_GameMap.getCountry(l_CountryID);
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setCountryWhereDeployed(l_Country);
        l_OrderDetails.setNumberOfArmy(l_NumberOfArmy);
        return l_OrderDetails;
    }

    /**
     * A function to generate the information for advance order
     *
     * @param p_Command the command entered
     * @param p_Player  the player who issued the order
     * @return the order information of advance/attack
     */
    private static OrderDetails GenerateAdvanceOrderInfo(String[] p_Command, Player p_Player) {
        String l_FromCountryID = p_Command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_Command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_Command[3]);
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setDeparture(l_FromCountry);
        l_OrderDetails.setCountryWhereDeployed(l_ToCountry);
        l_OrderDetails.setNumberOfArmy(l_NumberOfArmies);
        return l_OrderDetails;
    }


    /**
     * A function to generate the information of Negotiate order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    private static OrderDetails GenerateNegotiateOrderInfo(String[] p_Command, Player p_Player) {
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
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
    private static OrderDetails GenerateBlockadeOrderInfo(String[] p_command, Player p_player) {
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_player);
        String l_CountryID = p_command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderDetails.setTargetCountry(l_TargetCountry);
        return l_OrderDetails;
    }

    /**
     * function to generate information about Airlift Order
     *
     * @param p_command the command entered
     * @param p_player  object parameter of type Player
     * @return the order information of deploy
     */
    private static OrderDetails GenerateAirliftOrderInfo(String[] p_command, Player p_player) {
        String l_FromCountryID = p_command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_command[3]);
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_player);
        l_OrderDetails.setDeparture(l_FromCountry);
        l_OrderDetails.setCountryWhereDeployed(l_ToCountry);
        l_OrderDetails.setNumberOfArmy(l_NumberOfArmies);
        return l_OrderDetails;
    }

    private static OrderDetails GenerateBombOrderInfo(String[] p_command, Player p_player){
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_player);
        String l_CountryID = p_command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderDetails.setTargetCountry(l_TargetCountry);
        return l_OrderDetails;
    }

}
