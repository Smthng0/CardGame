package dreamfactory.cardgame.multiplayer.Server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Players {
    private String player1Name;
    private String player2Name;
    private boolean playsFirst;

    public Players(String player1Name, String player2Name){
        this.player1Name = player1Name;
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
