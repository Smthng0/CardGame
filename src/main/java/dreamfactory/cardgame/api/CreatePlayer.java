package dreamfactory.cardgame.api;

import com.codahale.metrics.annotation.Timed;
import dreamfactory.cardgame.player.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/players")
public interface CreatePlayer {
    @GET
    @Path("/create")
    Player createPlayer(@QueryParam("player_name") String playerName);
}
