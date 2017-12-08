package dreamfactory.cardgame.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import dreamfactory.cardgame.engine.Engine;
import dreamfactory.cardgame.player.Player;

public class Players {
    private Player player1;
    private Player player2;
    private boolean playsFirst;

    public Players(String player1Name, String player2Name){
        this.player1 = new Player(player1Name, ;
        this.player2Name = player2Name;
        this.playsFirst = playsFirst;
    }

    public Players(String player1Name, String player2Name, boolean playsFirst){
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.playsFirst = playsFirst;
    }

    @JsonProperty
    public String getPlayer1Name() {
        return player1Name;
    }

    @JsonProperty
    public String getPlayer2Name() {
        return player2Name;
    }

    @JsonProperty
    public boolean isPlaysFirst() {
        return playsFirst;
    }

}
