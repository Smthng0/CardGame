package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;

import java.util.ArrayList;
import java.util.List;

public class AbilityMinionGenerator {

    public static List<MinionCard> createMinions() {
        List<MinionCard> minionList = new ArrayList<>();

        MinionCard minion = new MinionCard("Puddle Jumper",1,2,1);
        minion.addAbility(Ability.HASTE);
        minionList.add(minion);

        minion = new MinionCard("Mantis",2,2,2);
        minion.addAbility(Ability.EXTRA_ATTACK);
        minionList.add(minion);

        minion = new MinionCard("Porcupine",3,4,2);
        minion.addAbility(Ability.BLOCK);
        minionList.add(minion);

        minion = new MinionCard("Puddle Jumpitor",3,4,2);
        minion.addAbility(Ability.HASTE);
        minionList.add(minion);

        minion = new MinionCard("MantiPineJumper",6,4,3);
        minion.addAbility(Ability.EXTRA_ATTACK);
        minion.addAbility(Ability.HASTE);
        minion.addAbility(Ability.BLOCK);
        minionList.add(minion);

        return minionList;
    }

}
