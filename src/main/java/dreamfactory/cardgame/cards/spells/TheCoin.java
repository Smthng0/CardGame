package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.engine.Engine;

public class TheCoin extends SpellCard {

    public TheCoin() {
        super("The Coin", 0);
        this.addAbility(Ability.ADD_MANA);
    }

    @Override
    public void effect(Engine engine) {
        engine.getFriendlyPlayer().incrementRemainingMana();
    }
}
