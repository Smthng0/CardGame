package dreamfactory.cardgame.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import dreamfactory.cardgame.player.Player;

public class Players {
    private Player player1;
    private Player player2;

    public Players(){
    }
    
    @JsonProperty
    public Player getPlayer1() {
        return player1;
    }

    @JsonProperty
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    @JsonProperty
    public Player getPlayer2() {
        return player2;
    }

    @JsonProperty
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

}
