package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class PlainMinionLoaderTest {
    @Test
    public void createMinionListFromCSV_OK() {
        List<MinionCard> cardList = PlainMinionLoader.loadMinionsFromCSV();

        assertEquals(10, cardList.size());
        assertEquals("Bug",cardList.get(0).getTitle());
        assertEquals("Frog",cardList.get(2).getTitle());
        assertEquals("Turtle",cardList.get(4).getTitle());
        assertEquals("Bigger Turtle",cardList.get(7).getTitle());
        assertEquals("Froginator",cardList.get(9).getTitle());
    }

}