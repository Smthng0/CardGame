package dreamfactory.cardgame.api;

import dreamfactory.cardgame.api.actions.Action;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/commands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ClientCommands {
    @GET
    @Path("/status")
    GameStatus getStatus(@QueryParam("player_name") String playerName);

    @POST
    @Path("/actions")
    boolean sendAction(Action action);

    @GET
    @Path("/actions")
    List<Action> getActions(@QueryParam("player_name") String playerName);

    @GET
    @Path("/turn")
    GameStatus endTurn(@QueryParam("player_name") String playerName);
}
