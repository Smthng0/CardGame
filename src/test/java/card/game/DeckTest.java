package card.game;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeckTest {

    @Test
    public void create_deck_OK() {
        Deck deck = createDeck();

        assertTrue(deck.getRemainingCards() == 30);
    }

    @Test
    public void drawCard_OK() {
        Deck deck = createDeck();

        deck.drawCard();
        assertTrue(deck.getRemainingCards() == 29);
        deck.drawCard();
        assertTrue(deck.getRemainingCards() == 28);

        for (int i = 0; i < 30; i++) {
            deck.drawCard();
        }

        assertTrue(deck.getRemainingCards() == 0);
        assertTrue(deck.getDmgCounter() == 2);
    }

    @Test
    public void searchCard_byTitle_OK() {
        Deck deck = createDeck();

        assertTrue(deck.searchCard("Minion29"));
        assertFalse(deck.searchCard("Minion31"));
        assertTrue(deck.searchCard("Minion14"));

    }

    @Test
    public void addCard_searchCard_byCard_OK() {
        Deck deck = createDeck();
        MinionCard minion = new MinionCard("Vice", 2, 9, 4);
        deck.addCard(minion);

        assertTrue(deck.getRemainingCards() == 31);
        assertTrue(deck.searchCard(minion));
    }

    @Test
    public void removeCard_OK () {
        Deck deck = createDeck();
        MinionCard minion = new MinionCard("Vice", 2, 9, 4);
        deck.addCard(minion);

        assertTrue(deck.searchCard(minion));
        assertTrue(deck.getRemainingCards() == 31);
        assertTrue(deck.removeTargetCard(minion));
        assertFalse(deck.searchCard(minion));
        assertTrue(deck.removeFirstCard());
        assertTrue(deck.getRemainingCards() == 29);

        for (int i = 0; i < 29; i++) {
            deck.removeFirstCard();
        }

        assertFalse(deck.removeTargetCard(minion));
        assertFalse(deck.removeFirstCard());
    }

    public static Deck createDeck(){
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

        Deck deck = new Deck (arrayDeck);
        return deck;
    }

}