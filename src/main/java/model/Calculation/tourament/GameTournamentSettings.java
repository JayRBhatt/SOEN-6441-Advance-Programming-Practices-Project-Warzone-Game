package model.Calculation.tourament;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Calculation.playerStrategy.PlayerStrategy;

public class GameTournamentSettings {
    private List<String> d_Map = new ArrayList<>();
    private Set<PlayerStrategy> d_PlayerStrategies = new HashSet<>();
    private int d_Games;
    private int d_MaxTries;

    public List<String> getMap() {
        return new ArrayList<>(d_Map); // Returns a copy to maintain encapsulation
    }

    public Set<PlayerStrategy> getPlayerStrategies() {
        return new HashSet<>(d_PlayerStrategies); // Returns a copy to maintain encapsulation
    }

    public int getGames() {
        return d_Games;
    }

    public void setGames(int p_Games) {
        if (p_Games > 0) {
            d_Games = p_Games;
        }
    }

    public int getMaxTries() {
        return d_MaxTries;
    }

    public void setMaxTries(int p_MaxTries) {
        if (p_MaxTries > 0) {
            d_MaxTries = p_MaxTries;
        }
    }
}
