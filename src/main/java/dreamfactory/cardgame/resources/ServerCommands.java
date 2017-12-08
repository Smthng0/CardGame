package dreamfactory.cardgame.resources;

import com.codahale.metrics.annotation.Timed;

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
    @Timed
    public GameController.GameStatus gameStatus(@QueryParam("status") String playerName) {
        return gameController.gameState;
    }

    @GET
    @Timed
    public GameController.GameStatus endTurn(@QueryParam("endTurn") String playerName) {
        //gameController.endTurn(playerName);
        return gameController.gameState;
    }

    @GET
    @Timed
    public boolean action(@QueryParam("action") String playerName, String command) {
        return gameController.doAction(command);
    }

}
