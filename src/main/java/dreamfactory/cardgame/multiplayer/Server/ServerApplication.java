package dreamfactory.cardgame.multiplayer.Server;

import dreamfactory.cardgame.multiplayer.Server.resources.CreatePlayers;
import dreamfactory.cardgame.multiplayer.Server.resources.GameController;
import dreamfactory.cardgame.multiplayer.Server.resources.ServerCommands;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ServerApplication extends Application<ServerConfiguration>{

    @Override
    public String getName() {
        return "cardGame";
    }

    @Override
    public void run(ServerConfiguration serverConfiguration,
                    Environment environment) {

        final GameController gameController = new GameController();
        final CreatePlayers createPlayers = new CreatePlayers(gameController);
        final ServerCommands serverCommands = new ServerCommands(gameController);
        environment.jersey().register(createPlayers);
        environment.jersey().register(serverCommands);
    }


}
