package services;


import controller.*;
import model.GameMap;
import model.GamePhase;
import model.Player;
import model.orders.Order;
import utils.exceptions.InvalidCommandException;
import utils.loggers.LogEntryBuffer;

/**
 * Class that has the main logic behind the functioning of Execute order phase
 * in
 * the game
 *
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 */

public class ExecuteOrder implements GameEngineController {

    GamePhase d_NextState = GamePhase.ExitGame;
    GamePhase d_GamePhase;
    GameMap d_GameMap;
    LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();
    GamePhase d_ReinforcementGamePhase = GamePhase.Reinforcement;

    /**
     * Constructs an ExecuteOrderService with a reference to the game map.
     *
     */

    public ExecuteOrder() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Starts the execution of orders in the Execute Order phase and then takes the
     * player to the next game phase
     * after this phase gets completed
     * 
     * @throws InvalidCommandException if game phase transition is invalid.
     */

    public GamePhase start(GamePhase p_GamePhase) throws InvalidCommandException {
        d_GamePhase = p_GamePhase;
        d_LogEntryBuffer.logAction("\n EXECUTE ORDER PHASE \n");
        executeOrders();
        clearAllNeutralPlayers();

        return checkPlayerWon(p_GamePhase);
    }

    /**
     * Executes orders for each player in the game.
     */

    private void executeOrders() {
        int l_Counter = 0;
        while (l_Counter < d_GameMap.getGamePlayers().size()) {
            l_Counter = 0;
            for (Player player : d_GameMap.getGamePlayers().values()) {
                Order l_Order = player.nextOrder();
                if (l_Order == null) {
                    l_Counter++;
                } else {
                    if (l_Order.execute()) {
                        l_Order.printOrderCommand();
                    }
                }
            }
        }
    }

    private void clearAllNeutralPlayers() {
        for (Player l_Player : d_GameMap.getGamePlayers().values()) {
            l_Player.removeNeutralPlayer();
        }
    }

    /**
     * Executes a game order by deploying armies to a destination country.
     *
     * @param order The order to execute.
     * @return true if the order was successfully executed, or false
     *         otherwise.
     */
    public GamePhase checkPlayerWon(GamePhase p_GamePhase) {

        for (Player l_Player : d_GameMap.getGamePlayers().values()) {
            if (l_Player.getOccupiedCountries().size() == d_GameMap.getCountries().size()) {
                System.out.println("The Player " + l_Player.getPlayerName() + " won the game.");
                System.out.println("Exiting the game...");
                return p_GamePhase.nextState(d_NextState);
            }
        }
        return p_GamePhase.nextState(d_ReinforcementGamePhase);
    }

}
