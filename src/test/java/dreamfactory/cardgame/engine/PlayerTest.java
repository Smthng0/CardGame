package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.WeaponCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void cratePlayer_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);

        assertTrue(player.getNumberOfCards() == 0);
        assertTrue(player.getNumberOfMinions() == 0);
    }

    @Test
    public void takeDamage() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);
        int playerHealth = player.getHealth();
        player.takeDamage(17);

        assertTrue(player.getHealth() == (playerHealth - 17));
    }

    @Test
    public void equipWeapon_attack_sequence_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);
        MinionCard minion = new MinionCard("Vice", 0, 7, 14);
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 2);

        player.equipWeapon(weapon);

        player.attack(minion);

        assertTrue(player.getHealth() == 23);
        assertTrue(minion.getHealth() == 5);
        assertTrue(player.hasWeapon());

        player.resetAttacks();
        player.attack(minion);

        assertTrue(player.getHealth() == 16);
        assertTrue(minion.getHealth() == -4);
        assertFalse(player.hasWeapon());
    }
    @Test
    public void twoBoard_test_OK() {
        Player attacker = new Player("fuckyou", null);
        Player defer = new Player("fuckyou2", null);

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