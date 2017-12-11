package dreamfactory.cardgame;

import dreamfactory.cardgame.resources.CreateGame;
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
        final CreateGame createGame = new CreateGame(gameController);
        final ServerCommands serverCommands = new ServerCommands(gameController);
        environment.jersey().register(createGame);
        environment.jersey().register(serverCommands);
    }


}
