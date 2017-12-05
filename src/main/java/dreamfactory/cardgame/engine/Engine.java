package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.player.Player;

public class Engine {
    Command command = new Command();



    public Player getFriendlyPlayer() {
        return command.getActivePlayer();
    }

    public Player getEnemyPlayer() {
        return command.getPassivePlayer();
    }


}
