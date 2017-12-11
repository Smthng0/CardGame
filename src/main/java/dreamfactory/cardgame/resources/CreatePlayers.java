package dreamfactory.cardgame.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.Players;
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
public class CreatePlayers {
    private GameController gameController;
    private final Gson myGson;

    public CreatePlayers(GameController gameController) {
        myGson = new GsonBuilder().registerTypeAdapter(Card.class, new CardTypeAdapter()).create();
        this.gameController = gameController;
    }

    @GET
    @Timed
    @Path("/create")
    public Response createPlayer(@QueryParam("player_name") String playerName) {
        Player player = gameController.createPlayer(playerName);

        return Response.ok(myGson.toJson(player))
                .build();
    }

    @GET
    @Timed
    @Path("/start")
    public Response gameReadyToStart(@QueryParam("player_name") String playerName) {
        Players players = gameController.gameReady();

        return Response.ok(myGson.toJson(players))
                .build();
    }

}
