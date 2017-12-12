package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.WeaponCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void cratePlayer_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);

        assertEquals(3, player.getNumberOfCards());
        assertEquals(0, player.getNumberOfMinions());
    }

    @Test
    public void takeDamage() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);
        int playerHealth = player.getHealth();
        player.takeDamage(17);

        assertEquals(playerHealth - 17, player.getHealth());
    }

    @Test
    public void equipWeapon_attack_sequence_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);
        MinionCard minion = new MinionCard("Vice", 0, 7, 14);
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 2);

        player.equipWeapon(weapon);
        int playerHealth = player.getHealth();

        player.attack(minion);

        assertEquals(playerHealth - 7, player.getHealth());
        assertEquals(5, minion.getHealth());
        assertTrue(player.hasWeapon());

        player.resetAttacks();
        player.attack(minion);

        assertEquals(playerHealth - 14, player.getHealth());
        assertEquals(-4, minion.getHealth());
        assertFalse(player.hasWeapon());
    }

    @Test
    public void drawCard_playCard_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);
        player.drawCard();
        player.setRemainingMana(10);
        player.playCard(0);

        assertTrue(player.hasMinions());
    }

    @Test
    public void asString_OK() {
        Deck deck = new DeckTest().createDeck();
        Player player = new Player("Frane", deck);
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 4);
        weapon.addAbility(Ability.BLOCK);
        weapon.addAbility(Ability.EXTRA_ATTACK);
        player.equipWeapon(weapon);

        assertTrue(player.asString().equals("Frane, Health: 20, Remaining Attacks: 2, Weapon Equipped: \n" +
                "Sledgehammer, Mana Cost: 6, Attack: 9, Durability: 4, Abilities: BLOCK, EXTRA_ATTACK\n"));

        MinionCard minion = new MinionCard("Dummy", 0, 0, 20);
        player.attack(minion);
        player.attack(minion);
        player.resetAttacks();
        player.attack(minion);
        player.attack(minion);

        assertEquals("Frane, Health: 20\n", player.asString());
    }

    @Test
    public void twoBoard_test_OK() {
        Deck deck = new DeckTest().createDeck();
        Player attacker = new Player("fuckyou", deck);
        Player defer = new Player("fuckyou2", deck);

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

}