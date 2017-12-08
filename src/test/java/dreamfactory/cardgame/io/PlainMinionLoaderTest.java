package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class PlainMinionLoaderTest {
    @Test
    public void createMinionListFromCSV_OK() {
        List<MinionCard> cardList = new PlainMinionLoader().loadMinionsFromCSV();

        assertEquals(10, cardList.size());
        assertEquals("Bug",cardList.get(0).getTitle());
        assertEquals("Wolf",cardList.get(2).getTitle());
        assertEquals("Bigger Wolf",cardList.get(4).getTitle());
        assertEquals("Monkey",cardList.get(7).getTitle());
        assertEquals("Scary Stuff",cardList.get(9).getTitle());
    }

}