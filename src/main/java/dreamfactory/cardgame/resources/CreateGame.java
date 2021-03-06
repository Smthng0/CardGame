package dreamfactory.cardgame.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.player.Players;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import dreamfactory.cardgame.player.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
public class CreateGame {
    private GameController gameController;
    private final Gson cardGson;

    public CreateGame(GameController gameController) {
        cardGson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter())
                .create();
        this.gameController = gameController;
    }

    @GET
    @Timed
    @Path("/create")
    public Response createPlayer(@QueryParam("player_name") String playerName) {
        Player player = gameController.createPlayer(playerName);

        return Response.ok(cardGson.toJson(player))
                .build();
    }

    @GET
    @Timed
    @Path("/ready")
    public Response gameReady(@QueryParam("player_name") String playerName) {
        Players players = gameController.gameReady();

        return Response.ok(cardGson.toJson(players))
                .build();
    }

    @GET
    @Timed
    @Path("/start")
    public Response startGame(@QueryParam("player_name") String playerName) {
        GameStatus game = gameController.startGame();

        return Response.ok(cardGson.toJson(game))
                .build();
    }

}
