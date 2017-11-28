package card.game.io;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GenericMinionReaderTest {
    @Test
    public void createMinionListFromCSV_OK() {
        GenericMinionReader reader = new GenericMinionReader();
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