package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;
import org.junit.Test;

import java.util.List;


public class PlainMinionLoaderTest {
    @Test
    public void createMinionListFromCSV_OK() {
        PlainMinionLoader reader = new PlainMinionLoader();
        List<MinionCard> cardList = reader.createMinionListFromCSV();

        for (MinionCard card: cardList) {
            System.out.println(card.getTitle()
                    + ", " + card.getManaCost()
                    + ", " + card.getAttack()
                    + ", " + card.getHealth());
        }

        System.out.println("Number of minions: " + cardList.size());
    }

}