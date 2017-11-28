package card.game.cards.spells;

import card.game.Engine;
import card.game.abilities.Ability;
import card.game.abilities.keywords.Taunt;
import card.game.cards.MinionCard;
import card.game.cards.SpellCard;

import java.util.ArrayList;
import java.util.List;

public class MirrorImageSpellCard extends SpellCard {

    public MirrorImageSpellCard() {
        super("Mirror Image", 1, new ArrayList<>());

        this.addAbility(new Ability() {
            @Override
            public void effect() {
                List<Ability> taunt = new ArrayList<>();
                taunt.add(new Taunt());

                Engine.getFriendlyPlayer().getBoard().summonMinion
                        (new MinionCard("Mirror Image", 0, 0, 2, taunt));
                Engine.getFriendlyPlayer().getBoard().summonMinion
                        (new MinionCard("Mirror Image", 0, 0, 2, taunt));
            }

            @Override
            public String getAbilityType() {
                return "MirrorImageSpellCard";
            }
        });
    }

}
