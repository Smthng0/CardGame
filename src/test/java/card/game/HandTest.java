package card.game;

import card.game.cards.MinionCard;
import card.game.cards.SpellCard;
import card.game.cards.WeaponCard;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandTest {

    @Test
    public void crate_hand_OK() {
        Hand hand = createHand();

        assertTrue(hand.getNumberOfCards() == 4);
        assertTrue(hand.hasCards());
    }

    @Test
    public void drawCard_OK() {
        Hand hand = createHand();

        hand.drawCard();
        hand.drawCard();
        hand.drawCard();

        assertTrue(hand.getNumberOfCards() == 7);
    }

    @Test
    public void addCard_OK() {
        Hand hand = createHand();

        hand.addCard(new SpellCard("kifla", 2, null));
        hand.addCard(new WeaponCard("kifla2", 2, 2, 2));

        assertTrue(hand.getNumberOfCards() == 6);
    }

    @Test
    public void discardCard_index_OK() {
        Hand hand = createHand();

        hand.discardCard(0);

        assertTrue(hand.getNumberOfCards() == 3);

        hand.discardCard(0);
        hand.discardCard(0);
        hand.discardCard(0);
        hand.discardCard(0);
        hand.discardCard(0);

        assertFalse(hand.hasCards());
        assertTrue(hand.getNumberOfCards() == 0);
    }

    @Test
    public void discardCard_card_OK() {
        Hand hand = createHand();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        hand.addCard(card);
        hand.addCard(card);

        assertTrue(hand.getNumberOfCards() == 6);

        hand.discardCard(card);

        assertTrue(hand.getNumberOfCards() == 5);

        hand.discardCard(card);

        assertTrue(hand.getNumberOfCards() == 4);
    }

    @Test
    public void playCard_OK() {
        Hand hand = createHand();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        hand.addCard(card);
        hand.addCard(card);

        assertTrue(hand.getNumberOfCards() == 6);

        hand.playCard(card);
        hand.playCard(card);

        assertTrue(hand.getNumberOfCards() == 4);
    }

    @Test
    public void viewHand_OK() {
        Hand hand = createHand();

        hand.viewHand();
    }

    public static Hand createHand() {
        Deck deck = DeckTest.createDeck();

        return new Hand(deck, false, new Board());
    }

}