package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.client.Client;
import dreamfactory.cardgame.player.Player;

public class MultiplayerCommands extends Commands {

    @Override
    protected Card chooseCard(Player player, Engine engine) {
        scanNextCommand();
        int index;

        try {
            index = Integer.parseInt(super.getCommand());
        } catch (Exception ex) {
            return null;
        }

        if (Client.sendAction(new PlayCard(index, engine))) {
            return player.playCard(index, engine);
        }

        printer("Play Card failed!");
        return null;
    }

    @Override
    public boolean attackTarget(Player attackingPlayer, Player defendingPlayer,
                              int attackingIndex, int defendingIndex) {

        if (Client.sendAction(new Attack(attackingIndex, defendingIndex))) {
            return super.attackTarget(attackingPlayer, defendingPlayer, attackingIndex, defendingIndex);
        }

        printer("Attack failed");
        return false;
    }

}
