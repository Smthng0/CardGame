package dreamfactory.cardgame.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/commands")
public interface ClientCommands {
    @GET
    @Path("/status")
    GameStatus getStatus(@QueryParam("player_name") String playerName);

    @POST
    @Path("/store_action")
    boolean sendAction(@QueryParam("player_name") String playerName, String command);

    @GET
    @Path("/get_action")
    boolean getAction(@QueryParam("player_name") String playerName);

    @GET
    @Path("/turn")
    GameStatus endTurn(@QueryParam("player_name") String playerName);
}
