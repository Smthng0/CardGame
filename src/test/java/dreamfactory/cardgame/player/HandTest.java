package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.cards.WeaponCard;
import dreamfactory.cardgame.player.Hand;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HandTest {

    @Test
    public void crate_hand_OK() {
        Hand hand = new Hand();

        assertTrue(hand.getNumberOfCards() == 0);
        assertTrue(hand.isEmpty());
    }

    @Test
    public void addCard_OK() {
        Hand hand = new Hand();

        hand.addCard(new SpellCard("kifla", 2, null));
        hand.addCard(new WeaponCard("kifla2", 2, 2, 2));

        assertTrue(hand.getNumberOfCards() == 2);
    }

}