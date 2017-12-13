package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

public class ServerCommands extends Commands {

    @Override
    protected void attackPlayerTarget(Attackable attacker, Player defendingPlayer) {
        attacker.attack(defendingPlayer);

        if (defendingPlayer.isDead()) {
            printer("Game over....");
        }
    }
}
