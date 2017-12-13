package dreamfactory.cardgame.api;

import dreamfactory.cardgame.player.Player;
import dreamfactory.cardgame.player.Players;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/players")
public interface ClientCreateGame {
    @GET
    @Path("/create")
    Player createPlayer(@QueryParam("player_name") String playerName);

    @GET
    @Path("/ready")
    Players gameReady(@QueryParam("player_name") String playerName);

    @GET
    @Path("/start")
    GameStatus startGame(@QueryParam("player_name") String playerName);
}
