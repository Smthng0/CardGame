package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.Players;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/update")
@Produces(MediaType.APPLICATION_JSON)
public class ServerUpdate {
    private GameController gameController;

    public ServerUpdate(GameController gameController) {
        this.gameController = gameController;
    }

    @GET
    @Path(("/recieve"))
    public boolean recievePlayers(@QueryParam("player_name") String playerName, Players players) {
        return gameController.recievePlayers(playerName, players);
    }

    @GET
    @Path(("/send"))
    public Players sendPlayers(@QueryParam("player_name") String playerName) {
        return gameController.sendPlayers(playerName);
    }

}
