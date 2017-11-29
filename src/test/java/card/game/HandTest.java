package card.game;

import card.game.cards.SpellCard;
import card.game.cards.WeaponCard;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HandTest {

    @Test
    public void crate_hand_OK() {
        Hand hand = createHand();

        assertTrue(hand.getNumberOfCards() == 1);
        assertTrue(hand.hasCards());
    }

    @Test
    public void addCard_OK() {
        Hand hand = createHand();

        hand.addCard(new SpellCard("kifla", 2, null));
        hand.addCard(new WeaponCard("kifla2", 2, 2, 2));

        assertTrue(hand.getNumberOfCards() == 3);
    }

    @Test
    public void viewHand_OK() {
        Hand hand = createHand();

        hand.viewHand();
    }

    public Hand createHand() {
        return new Hand(false);
    }

}