package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class PlainMinionLoaderTest {
    @Test
    public void createMinionListFromCSV_OK() {
        List<MinionCard> cardList = new PlainMinionLoader().loadMinionsFromCSV();
        for (MinionCard minion: cardList) {
            System.out.println(minion.asString());
        }

        assertTrue(cardList.size() == 10);
        assertTrue(cardList.get(0).getTitle().equals("Bug"));
        assertTrue(cardList.get(2).getTitle().equals("Wolf"));
        assertTrue(cardList.get(4).getTitle().equals("Bigger Wolf"));
        assertTrue(cardList.get(7).getTitle().equals("Monkey"));
        assertTrue(cardList.get(9).getTitle().equals("Scary Stuff"));
    }

}