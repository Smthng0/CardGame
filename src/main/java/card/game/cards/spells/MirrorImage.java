package card.game.cards.spells;

import card.game.Engine;
import card.game.Ability;
import card.game.cards.MinionCard;
import card.game.cards.SpellCard;

import java.util.ArrayList;
import java.util.List;

public class MirrorImage extends SpellCard {

    public MirrorImage() {
        super("Mirror Image", 1, new ArrayList<>());
        this.addAbility(Ability.SUMMON);
    }

    @Override
    public void effect(Engine engine) {
        List<Ability> taunt = new ArrayList<>();
        taunt.add(Ability.TAUNT);

        engine.getFriendlyPlayer().playCard(
                (new MinionCard("Mirror Image", 0, 0, 2, taunt)));

        engine.getFriendlyPlayer().playCard(
                (new MinionCard("Mirror Image", 0, 0, 2, taunt)));

    }

}
