package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.Client;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

public class MultiplayerCommands extends Commands {
    private Client client = new Client();

    @Override
    protected Card chooseCard(Player player) {
        scanNextCommand();
        int index;

        try {
            index = Integer.parseInt(super.getCommand());
        } catch (Exception ex) {
            return null;
        }

        if (client.sendAction(new PlayCard(index))) {
            return player.playCard(index);
        }

        printer("Play Card failed!");
        return null;
    }

    @Override
    public boolean attackTarget(Player attackingPlayer, Player defendingPlayer,
                              int attackingIndex, int defendingIndex) {

        if (client.sendAction(new Attack(attackingIndex, defendingIndex))) {
            return super.attackTarget(attackingPlayer, defendingPlayer, attackingIndex, defendingIndex);
        }

        printer("Attack failed");
        return false;
    }

}
