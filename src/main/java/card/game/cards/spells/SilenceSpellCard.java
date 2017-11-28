package card.game.cards.spells;

import card.game.abilities.effects.Silence;
import card.game.cards.SpellCard;

import java.util.ArrayList;

public class SilenceSpellCard extends SpellCard {

    public SilenceSpellCard() {
        super("Silence", 0, new ArrayList<>());
        this.addAbility(new Silence());
    }

}
