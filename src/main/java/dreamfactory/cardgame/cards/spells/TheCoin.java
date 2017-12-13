package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.player.Player;

public class TheCoin extends SpellCard {

    public TheCoin() {
        super("The Coin", 0);
        this.addAbility(Ability.ADD_MANA);
    }

    @Override
    public void effect(Player player) {
        player.incrementRemainingMana();
    }
}
