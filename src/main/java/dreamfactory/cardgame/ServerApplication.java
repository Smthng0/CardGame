package dreamfactory.cardgame;

import dreamfactory.cardgame.resources.CreateGame;
import dreamfactory.cardgame.resources.GameController;
import dreamfactory.cardgame.resources.ServerCommands;
import dreamfactory.cardgame.resources.ServerUpdate;
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
        final CreateGame createGame = new CreateGame(gameController);
        final ServerCommands serverCommands = new ServerCommands(gameController);
        final ServerUpdate serverUpdate = new ServerUpdate(gameController);
        environment.jersey().register(createGame);
        environment.jersey().register(serverCommands);
        environment.jersey().register(serverUpdate);
    }


}
