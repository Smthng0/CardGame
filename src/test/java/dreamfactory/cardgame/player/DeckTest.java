package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.io.PlainMinionLoader;
import dreamfactory.cardgame.io.AbilityMinionGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void create_deck_OK() {
        Deck deck = createDeck();

        assertEquals(0, deck.getDmgCounter());
    }

    @Test
    public void createDeck_fromCSV_OK() {
        List<MinionCard> readerList = (new PlainMinionLoader().loadMinionsFromCSV());
        List<HearthstoneCard> minionList = new ArrayList<>();
        minionList.addAll(readerList);
        minionList.addAll(readerList);

        List<MinionCard> abilityList = new AbilityMinionGenerator().createMinions();
        minionList.addAll(abilityList);
        minionList.addAll(abilityList);

        assertEquals(30, minionList.size());
    }

    @Test
    public void drawCard_OK() {
        Deck deck = createDeck();

        for (int i = 0; i < 30; i++) {
            deck.drawCard();
        }

        assertEquals(0, deck.getDmgCounter());

        deck.drawCard();
        deck.drawCard();

        assertEquals(2, deck.getDmgCounter());
    }

    public Deck createDeck(){
        List<HearthstoneCard> arrayDeck = new ArrayList<>();
        Random random = new Random ();

        for (int i = 0; i < 30; i++) {
            arrayDeck.add(
                    new MinionCard(("Minion"+(i+1)),
                            random.nextInt(9),
                            random.nextInt(10),
                            random.nextInt(10)
                    )
            );
        }

        return new Deck (arrayDeck);
    }

}