package dreamfactory.cardgame.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

public class Players {
    private Player player1;
    private Player player2;
    private boolean playsFirst;

    public Players(String player1Name, String player2Name){
        this.player1 = new Player(player1Name, Deck.getConstructedDeck());
        this.player2 = new Player(player2Name, Deck.getConstructedDeck());
        this.playsFirst = playsFirst;
    }

    public Players(String player1Name, String player2Name, boolean playsFirst){
        this.player1 = new Player(player1Name, Deck.getConstructedDeck());
        this.player2 = new Player(player2Name, Deck.getConstructedDeck());
        this.playsFirst = playsFirst;
    }

    @JsonProperty
    public Player getPlayer1() {
        return player1;
    }

    @JsonProperty
    public Player getPlayer2() {
        return player2;
    }

    @JsonProperty
    public boolean isPlaysFirst() {
        return playsFirst;
    }

}
