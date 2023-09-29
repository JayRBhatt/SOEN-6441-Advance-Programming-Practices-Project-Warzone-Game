package model;

public class Player {
    private int d_PlayerId;
    private String d_PlayerName;    



/** 
 * @return int
 */
public int getPlayerId() {
        return d_PlayerId;
    }

/** 
 * @param p_PlayerId
 */
public void setPlayerId(int p_PlayerId) {
        this.d_PlayerId = p_PlayerId;
    }    

/** 
 * @return String
 */
public String getPlayerName() {
        return d_PlayerName;
    }

/** 
 * @param p_PlayerName
 */
public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }        
}