package dreamfactory.cardgame.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.ActionTypeAdapter;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;

@Path("/commands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServerCommands {
    private GameController gameController;
    private final Gson myGson = new GsonBuilder()
            .registerTypeAdapter(Action.class, new ActionTypeAdapter())
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .create();

    public ServerCommands(GameController gameController) {
        this.gameController = gameController;
    }

    @GET
    @Path("/status")
    public Response gameStatus(@QueryParam("player_name") String playerName) {
        GameStatus gameState = gameController.gameState;

        return Response.ok(myGson.toJson(gameState))
                .build();
    }

    @POST
    @Path("/actions")
    public Response sendAction(String action) {
        boolean isAction = gameController.sendAction(myGson.fromJson(action, Action.class));

        return Response.ok(myGson.toJson(isAction))
                .build();
    }

    @GET
    @Path("/actions")
    public Response getActions(@QueryParam("player_name") String playerName) {
        List<Action> actionList = gameController.getActions(playerName);
        Type collectionType = new TypeToken<List<Action>>(){}.getType();

        return Response.ok(myGson.toJson(actionList, collectionType))
                .build();
    }

    @GET
    @Path("/turn")
    public Response endTurn(@QueryParam("player_name") String playerName) {
        gameController.endTurn(playerName);
        GameStatus gameState = gameController.gameState;

        return Response.ok(myGson.toJson(gameState))
                .build();
    }

}
