package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.GameStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/commands")
@Produces(MediaType.APPLICATION_JSON)
public class ServerCommands {
    private GameController gameController;

    public ServerCommands(GameController gameController) {
        this.gameController = gameController;
    }

    @GET
    @Path("/status")
    public GameStatus gameStatus(@QueryParam("player_name") String playerName) {
        return gameController.gameState;
    }

    //TODO: ovo trebam jos puno tu prckat... promjenit iz GET u POST, isto na clintu... i wrappat stvari u response
    @POST
    @Path(("/store_actions"))
    public boolean getAction(@QueryParam("player_name") String playerName, String command) {
        return gameController.getActions(command);
    }

    @GET
    @Path("/return_actions")
    public boolean sendAction(@QueryParam("player_name") String playerName) {
        return gameController.sendActions();
    }

    @GET
    @Path("/turn")
    public GameStatus endTurn(@QueryParam("player_name") String playerName) {
        gameController.endTurn(playerName);
        return gameController.gameState;
    }

}
