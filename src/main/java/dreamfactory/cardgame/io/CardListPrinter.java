package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;

import java.util.List;

public class CardListPrinter {
    public void printMinionList(List<MinionCard> cardList) {
        for (MinionCard card: cardList) {
            System.out.print(card.getTitle()
                    + ", " + card.getManaCost()
                    + ", " + card.getAttack()
                    + ", " + card.getHealth());

            if (card.hasAbility()){
                printAbilities(card.getAbilities());
            }

            System.out.println();
        }

        System.out.println("Number of minions: " + cardList.size());
    }

    private void printAbilities(List<Ability> list) {
        for (Ability ability : list) {
            System.out.print(", " + ability);
        }
    }
}
