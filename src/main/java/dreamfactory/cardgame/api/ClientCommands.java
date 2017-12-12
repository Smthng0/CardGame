package dreamfactory.cardgame.api;

import dreamfactory.cardgame.api.actions.Action;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/commands")
public interface ClientCommands {
    @GET
    @Path("/status")
    GameStatus getStatus(@QueryParam("player_name") String playerName);

    @POST
    @Path("/actions")
    boolean sendAction(@QueryParam("player_name") String playerName, Action action);

    @GET
    @Path("/actions")
    List<Action> getActions(@QueryParam("player_name") String playerName);

    @GET
    @Path("/turn")
    GameStatus endTurn(@QueryParam("player_name") String playerName);
}
