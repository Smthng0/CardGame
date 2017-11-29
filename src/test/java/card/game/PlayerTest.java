package card.game;

import card.game.cards.MinionCard;
import card.game.cards.WeaponCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void cratePlayer_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck, true);

        assertTrue(player.getNumberOfCards() == 3);
        assertTrue(player.getNumberOfMinions() == 0);
    }

    @Test
    public void takeDamage() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck, true);
        player.takeDamage(17);

        assertTrue(player.getHealth() == 13);
    }

    @Test
    public void attack_sequence_getWeapon_goToGraveyard_OK() {
        Deck deck = new DeckTest().createDeck();
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

        player.setRemainingAttacks(player.getMaxAttacks());
        player.attack(minion);

        assertTrue(player.getHealth() == 16);
        assertTrue(minion.getHealth() == -4);
        assertFalse(player.hasWeapon());
    }
    @Test
    public void twoBoard_test_OK() {
        Player attacker = new Player("fuckyou", null, false);
        Player defer = new Player("fuckyou2", null, false);

        MinionCard minionCard = new MinionCard("frane", 0, 4, 4);
        attacker.summonMinion(minionCard);

        minionCard = new MinionCard("frane", 0, 3, 4);
        attacker.summonMinion(minionCard);

        minionCard = new MinionCard("frane", 0, 3, 4);
        defer.summonMinion(minionCard);

        MinionCard attackDog = attacker.getMinion(0);

        MinionCard shouldBeDead = defer.getMinion(0);
        attackDog.resetAttacks();

        attackDog.attack(shouldBeDead);

        Assert.assertEquals(1, attackDog.getHealth());
        Assert.assertTrue(shouldBeDead.isDead());
        Assert.assertFalse(attacker.getMinion(1).isDead());
        Assert.assertTrue(defer.getMinion(0).isDead());

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