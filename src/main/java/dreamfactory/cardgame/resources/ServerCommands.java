package dreamfactory.cardgame.resources;

import com.codahale.metrics.annotation.Timed;
import dreamfactory.cardgame.api.GameStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

    @GET
    @Path(("/action"))
    public boolean action(@QueryParam("player_name") String playerName, String command) {
        return gameController.doAction(command);
    }

    @GET
    @Path("/turn")
    public GameStatus endTurn(@QueryParam("player_name") String playerName) {
        gameController.endTurn(playerName);
        return gameController.gameState;
    }

}
