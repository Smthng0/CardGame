package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.engine.Engine;
import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.SpellCard;

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

        engine.getFriendlyPlayer().summonMinion(
                (new MinionCard("Mirror Image", 0, 0, 2, taunt)));

        engine.getFriendlyPlayer().summonMinion(
                (new MinionCard("Mirror Image", 0, 0, 2, taunt)));

    }

}
