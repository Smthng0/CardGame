package dreamfactory.cardgame.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.ActionTypeAdapter;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/commands")
@Produces(MediaType.APPLICATION_JSON)
public class ServerCommands {
    private GameController gameController;
    private final Gson actionGson;

    public ServerCommands(GameController gameController) {
        actionGson = new GsonBuilder()
                .registerTypeAdapter(Action.class, new ActionTypeAdapter())
                .registerTypeAdapter(Card.class, new CardTypeAdapter())
                .create();
        this.gameController = gameController;
    }

    @GET
    @Path("/status")
    public Response gameStatus(@QueryParam("player_name") String playerName) {
        GameStatus gameState = gameController.gameState;

        return Response.ok(actionGson.toJson(gameState))
                .build();
    }

    @POST
    @Path("/actions")
    public Response sendAction(@QueryParam("player_name") String playerName, Action action) {
        boolean isAction = gameController.sendAction(playerName, action);

        return Response.ok(actionGson.toJson(isAction))
                .build();
    }

    @GET
    @Path("/actions")
    public Response getAction(@QueryParam("player_name") String playerName) {
        List<Action> actions = gameController.getActions(playerName);

        return Response.ok(actionGson.toJson(actions))
                .build();
    }

    @GET
    @Path("/turn")
    public Response endTurn(@QueryParam("player_name") String playerName) {
        gameController.endTurn(playerName);
        GameStatus gameState = gameController.gameState;

        return Response.ok(actionGson.toJson(gameState))
                .build();
    }

}
