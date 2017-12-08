package dreamfactory.cardgame;

import dreamfactory.cardgame.resources.CreatePlayers;
import dreamfactory.cardgame.resources.GameController;
import dreamfactory.cardgame.resources.ServerCommands;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ServerApplication extends Application<ServerConfiguration>{
    public static void main(String[] args) throws Exception {
        new ServerApplication().run(args);
    }

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
