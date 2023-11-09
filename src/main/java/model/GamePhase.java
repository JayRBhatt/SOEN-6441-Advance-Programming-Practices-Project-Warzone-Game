package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import controller.GameEngineController;
import services.ExecuteOrder;
import services.GamePlayBegins;
import services.MapEditor;
import services.OrderIssue;
import services.Reinforcements;
/**
 * Enum containing the  different phases of the Game.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @author Mariya Bosy Kondody
 * @author Reema Ann Reny
 * @author Meera Muraleedharan Nair
 * @version 1.0.0
 */
public enum GamePhase {
    /**
     * MapEditor state handling map creation and validation operations
     */

    MapEditor {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from MapEditor state
         * @return List of allowed states from {@code MapEditor phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(StartUp);
        }

        /**
         * Overrides getController() method which returns the controller
         * for map editor game phase.
         *
         * @return MapEditor Object
         */
        @Override
        public GameEngineController getController() {
            return new MapEditor();
        }
    },

    /**
     * StartUp state handling load map, player creation and countries
     * allocation operations
     */
    StartUp {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from StartUp state
         *
         * @return List of allowed states from {@code StartUp phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(Reinforcement);
        }

        /**
         * Overrides getController() method which returns the controller
         * for game play or load game phase.
         *
         * @return GamePlay Object
         */
        @Override
        public GameEngineController getController() {
            return new GamePlayBegins();
        }
    },

    /**
     * Reinforcement state handling allocation of reinforcement
     * armies to each player after completing execute orders phase
     */
    Reinforcement {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from Reinforcement state
         *
         * @return List of allowed states from {@code Reinforcement phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(IssueOrder);
        }

        /**
         * Overrides getController() method which returns the controller
         * for reinforcement phase.
         *
         * @return Reinforcement Object
         */
        @Override
        public GameEngineController getController() {
            return new Reinforcements();
        }
    },

    /**
     * IssueOrder state allowing players to provide list of orders
     */
    IssueOrder {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from IssueOrder state
         *
         * @return List of allowed states from {@code IssueOrder phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Collections.singletonList(ExecuteOrder);
        }

        /**
         * Overrides getController() method which returns the controller
         * for issue order phase.
         *
         * @return IssueOrder Object
         */
        @Override
        public GameEngineController getController() {
            return new OrderIssue();
        }
    },

    /**
     * ExecuteOrder state allowing game engine to execute provided orders
     */
    ExecuteOrder {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from ExecuteOrder state
         *
         * @return List of allowed states from {@code ExecuteOrder phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return Arrays.asList(Reinforcement, ExitGame);
        }

        /**
         * Overrides getController() method which returns the controller
         * for execute order phase.
         *
         * @return ExecuteOrder Object
         */
        @Override
        public GameEngineController getController() {
            return new ExecuteOrder();
        }
    },

    /**
     * ExitGame state ends the game once all countries are conquered
     */
    ExitGame {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from ExitGame state
         *
         * @return List of allowed states from {@code ExitGame phase}
         */
        @Override
        public List<GamePhase> possibleStates() {
            return null;
        }

        /**
         * Overrides getController() method which returns the controller
         * for exit game phase.
         *
         * @return null
         */
        @Override
        public GameEngineController getController() {
            return null;
        }
    };

    /**
     * Checks if the next game phase returned from current
     * controller is present in its possible states list
     *
     * @param p_GamePhase Next phase of game
     * @return Next game phase if valid else Current game phase
     */
    public GamePhase nextState(GamePhase p_GamePhase) {
        if (this.possibleStates().contains(p_GamePhase)) {
            return p_GamePhase;
        } else return this;
    }

    /**
     * Abstract method to get list of next phases from current phase
     *
     * @return list of next possible states
     */
    public abstract List<GamePhase> possibleStates();

    /**
     * Returns controller for each phase
     *
     * @return Respective Controller object
     */
    public abstract GameEngineController getController();
}
