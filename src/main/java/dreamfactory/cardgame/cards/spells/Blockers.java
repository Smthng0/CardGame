package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Blockers extends SpellCard {

    public Blockers() {
        super("Blockers", 1, new ArrayList<>());
        this.addAbility(Ability.SUMMON);
    }

    @Override
    public boolean effect(Player player) {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(Ability.TAUNT);
        abilities.add(Ability.BLOCK);
        if (player.getNumberOfMinions() > 5) {
            return false;
        }

        player.summonMinion(
                (new MinionCard("Blocker", 0, 0, 2, abilities)));

        player.summonMinion(
                (new MinionCard("Blocker", 0, 0, 2, abilities)));

        return true;
    }

}
