package card.game.io;

import card.game.abilities.keywords.Charge;
import card.game.abilities.keywords.DivineShield;
import card.game.abilities.keywords.Windfury;
import card.game.cards.MinionCard;

import java.util.ArrayList;
import java.util.List;

public class MinionsWithAbilities {
    public List<MinionCard> createMinions() {
        List<MinionCard> minionList = new ArrayList<>();
        MinionCard minion1 = new MinionCard("Puddle Jumper",1,2,1);
        minion1.addAbility(new Charge());
        minionList.add(minion1);
        MinionCard minion2 = new MinionCard("Mini Mantis",3,2,4);
        minion2.addAbility(new Windfury());
        minionList.add(minion2);
        MinionCard minion3 = new MinionCard("Porcupine",4,6,2);
        minion3.addAbility(new DivineShield());
        minionList.add(minion3);
        MinionCard minion4 = new MinionCard("Mantis",5,3,4);
        minion4.addAbility(new Charge());
        minion4.addAbility(new Windfury());
        minionList.add(minion4);
        MinionCard minion5 = new MinionCard("Puddle Jumpitron",6,7,3);
        minion5.addAbility(new Charge());
        minionList.add(minion5);
        return minionList;
    }

}
