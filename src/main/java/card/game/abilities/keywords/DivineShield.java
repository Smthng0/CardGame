package card.game.abilities.keywords;

import card.game.abilities.Ability;

public class DivineShield implements Ability {
    public boolean usedUp = false;

    @Override
    public void effect(){
        usedUp = true;
    }

    public boolean isUsedUp() {
        return usedUp;
    }

    @Override
    public String getAbilityType() {
        return "DivineShield";
    }
}
