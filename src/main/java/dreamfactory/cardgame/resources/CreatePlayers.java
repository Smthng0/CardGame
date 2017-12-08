package dreamfactory.cardgame.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import dreamfactory.cardgame.api.Players;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
public class CreatePlayers {
    private String player1Name;
    private String player2Name;
    private GameController gameController;

    public CreatePlayers(GameController gameController) {
        this.gameController = gameController;
    }

    @GET
    @Timed
    public boolean createPlayer1(@QueryParam("playerName") String playerName) {
        if (player1Name == null) {
            this.player1Name = playerName;
            gameController.gameState = GameController.GameStatus.PREPARING;
            return true;
        } else if (player2Name == null) {
            this.player2Name = playerName;
            gameController.gameState = GameController.GameStatus.PREPARING;
            return true;
        }
        return false;
    }

    @GET
    @Timed
    public Players gameReadyToStart(@QueryParam("isGameReady") String playerName) {
        if (gameController.isGameReady(player1Name, player2Name)) {
            if (playerName.equals(player1Name)) {
                return new Players(player1Name, player2Name, true);
            }
            if (playerName.equals(player2Name)) {
                return new Players(player1Name, player2Name, false);
            }
        }
        return null;
    }

    @JsonProperty
    public String getPlayer1Name() {
        return player1Name;
    }

    @JsonProperty
    public String getPlayer2Name() {
        return player2Name;
    }

}
