package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CardListPrinterTest {
    @Test
    public void printMinionList_OK() throws Exception {
        List<MinionCard> minionList = new AbilityMinionGenerator().createMinions();
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        new CardListPrinter().printMinionList(minionList);

        assertTrue(minionList.size() == 15);
    }

}