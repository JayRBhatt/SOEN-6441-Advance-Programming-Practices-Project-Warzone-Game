package model.orders;

import model.Player;

public class OrderCreator {
    public static Order generateOrder(String[] p_commands, Player p_player) {
        String l_Type = p_commands[0].toLowerCase();
        Order l_Order;
    
        switch (l_Type) {
            case "deploy":
                DeployOrder deployOrder = new DeployOrder();
                deployOrder.setOrderInfo(generateDeployOrderInfo(p_commands, p_player));
                l_Order = deployOrder;
                break;
            default:
                System.out.println("\nFail to create an order due to invalid arguments");
                l_Order = new Order();
        }
        
        return l_Order;
    }
    private static OrderDetails generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);
    
        OrderDetails l_OrderDetails = new OrderDetails();
        l_OrderDetails.setPlayer(p_Player);
        l_OrderDetails.setDestination(l_CountryID);
        l_OrderDetails.setNumberOfArmy(l_NumberOfArmy);
    
        return l_OrderDetails;
    }
}
