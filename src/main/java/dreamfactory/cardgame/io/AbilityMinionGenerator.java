package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;

import java.util.ArrayList;
import java.util.List;

public static class AbilityMinionGenerator {

    public List<MinionCard> createMinions() {
        List<MinionCard> minionList = new ArrayList<>();

        MinionCard minion = new MinionCard("Puddle Jumper",1,2,1);
        minion.addAbility(Ability.HASTE);
        minionList.add(minion);

        minion = new MinionCard("Mini Mantis",3,2,4);
        minion.addAbility(Ability.EXTRA_ATTACK);
        minionList.add(minion);

        minion = new MinionCard("Porcupine",4,6,2);
        minion.addAbility(Ability.BLOCK);
        minionList.add(minion);

        minion = new MinionCard("Mantis",5,3,4);
        minion.addAbility(Ability.EXTRA_ATTACK);
        minion.addAbility(Ability.HASTE);
        minionList.add(minion);

        minion = new MinionCard("Puddle Jumpitor",6,7,3);
        minion.addAbility(Ability.HASTE);
        minionList.add(minion);

        return minionList;
    }

}
