package card.game;

import card.game.cards.MinionCard;
import card.game.cards.WeaponCard;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void cratePlayer_OK() {
        Deck deck = DeckTest.createDeck();
        Player player = new Player("Frane", deck, true);

        assertTrue(player.getNumberOfCards() == 3);
        assertTrue(player.getBoard().getNumberOfMinions() == 0);
        assertTrue(player.getDeck().getRemainingCards() == 27);
    }

    @Test
    public void takeDamage() {
        Deck deck = DeckTest.createDeck();
        Player player = new Player("Frane", deck, true);
        player.takeDamage(17);

        assertTrue(player.getHealth() == 13);
    }

    @Test
    public void attack_sequence_getWeapon_goToGraveyard_OK() {
        Deck deck = DeckTest.createDeck();
        Player player = new Player("Frane", deck, true);
        MinionCard minion = new MinionCard("Vice", 0, 7, 14);
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 2);

        player.setWeapon(weapon);
        player.setAttack(weapon.getAttack());
        player.setMaxAttacks(1);
        player.setRemainingAttacks(player.getMaxAttacks());

        player.attack(minion);

        assertTrue(player.getHealth() == 23);
        assertTrue(minion.getHealth() == 5);
        assertTrue(player.hasWeapon());
        assertTrue(player.getBoard().getGraveyard().size() == 0);

        player.setRemainingAttacks(player.getMaxAttacks());
        player.attack(minion);

        assertTrue(player.getHealth() == 16);
        assertTrue(minion.getHealth() == -4);
        assertFalse(player.hasWeapon());
        assertTrue(player.getBoard().getGraveyard().size() == 1);
    }

    @Test
    public void getRemainingMana() {
    }

    @Test
    public void setRemainingMana() {
    }

    @Test
    public void fullHand() {
    }

    @Test
    public void addCard() {
    }

    @Test
    public void discardCard() {
    }

    @Test
    public void drawCard() {
    }

    @Test
    public void playCard() {
    }

}