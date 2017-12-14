package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.engine.Commands;
import dreamfactory.cardgame.player.Player;

public class ManaCookie extends SpellCard {

    public ManaCookie() {
        super("Mana Cookie", 0);
        this.addAbility(Ability.ADD_MANA);
    }

    @Override
    public boolean effect(Player player) {
        player.incrementRemainingMana();
        return true;
    }
}
