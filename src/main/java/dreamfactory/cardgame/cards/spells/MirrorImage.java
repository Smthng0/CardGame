package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MirrorImage extends SpellCard {

    public MirrorImage() {
        super("Mirror Image", 1, new ArrayList<>());
        this.addAbility(Ability.SUMMON);
    }

    @Override
    public void effect(Player player) {
        List<Ability> taunt = new ArrayList<>();
        taunt.add(Ability.TAUNT);

        player.summonMinion(
                (new MinionCard("Mirror Image", 0, 0, 2, taunt)));

        player.summonMinion(
                (new MinionCard("Mirror Image", 0, 0, 2, taunt)));

    }

}
