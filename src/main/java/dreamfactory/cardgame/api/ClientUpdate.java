package dreamfactory.cardgame.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/update")
public interface ClientUpdate {
    @GET
    @Path("/recieve")
    boolean sendPlayers(@QueryParam("player_name") String playerName, Players players);

    @GET
    @Path("/send")
    Players recievePlayers(@QueryParam("player_name") String playerName);

}