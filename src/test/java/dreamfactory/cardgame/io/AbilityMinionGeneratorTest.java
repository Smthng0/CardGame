package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class AbilityMinionGeneratorTest {
    @Test
    public void createMinions_OK() throws Exception {
        List<MinionCard> cardList = new AbilityMinionGenerator().createMinions();

        assertTrue(cardList.size() == 5);
        assertTrue(cardList.get(0).getTitle().equals("Puddle Jumper"));
        assertTrue(cardList.get(1).getTitle().equals("Mini Mantis"));
        assertTrue(cardList.get(2).getTitle().equals("Porcupine"));
        assertTrue(cardList.get(3).getTitle().equals("Mantis"));
        assertTrue(cardList.get(4).getTitle().equals("Puddle Jumpitor"));
    }

}