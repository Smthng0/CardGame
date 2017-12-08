package dreamfactory.cardgame.resources;

import com.codahale.metrics.annotation.Timed;

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

    public CreatePlayers(GameController gameController) {
        this.gameController = gameController;
    }

    @GET
    @Timed
    @Path("/create")
    public Response createPlayer(@QueryParam("player_name") String playerName) {
        return Response.ok()
                .entity(gameController.createPlayer(playerName))
                .build();
    }

    @GET
    @Timed
    @Path("/start")
    public Response gameReadyToStart(@QueryParam("player_name") String playerName) {
        return Response.ok()
                .entity(gameController.gameReady())
                .build();
    }

}
