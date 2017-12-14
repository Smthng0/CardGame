package dreamfactory.cardgame.cards.spells;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.player.Player;

public class ManaCake extends SpellCard {

    public ManaCake() {
        super("Mana Cake (5)", 2);
        this.addAbility(Ability.ADD_MANA);
    }

    @Override
    public boolean effect(Player player) {
        for (int i = 0; i < 5; i++) {
            player.incrementRemainingMana();
        }
        return true;
    }
}
