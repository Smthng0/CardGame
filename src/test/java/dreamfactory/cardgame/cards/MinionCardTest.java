package dreamfactory.cardgame.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinionCardTest {

    @Test
    public void create_minion_OK() {
        List<Ability> ability = new ArrayList<>();
        ability.add(Ability.HASTE);
        MinionCard minion = new MinionCard("Minion1", 8, 17, 4);
        MinionCard minionWithAbility = new MinionCard("Minion1", 8, 5, 8, ability);

        assertEquals("Minion1", minion.getTitle());
        assertEquals(8, minion.getManaCost());
        assertEquals(17, minion.getAttack());
        assertEquals(4, minion.getHealth());
        assertFalse(minion.hasAbilities());
        assertEquals(8, minionWithAbility.getHealth());
        assertTrue(minionWithAbility.hasAbilities());
    }

    @Test
    public void attack_sequence_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 2, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 4, 3);
        minion1.resetAttacks();
        minion1.attack(minion2);

        assertEquals(2, minion1.getHealth());
        assertEquals(1, minion2.getHealth());
    }

    @Test
    public void Charge_addAbility_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.HASTE);
        minion1.attack(minion2);

        assertEquals(3, minion1.getHealth());
        assertEquals(-1, minion2.getHealth());
    }

    @Test
    public void hasAbilities_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.HASTE);
        minion2.addAbility(Ability.EXTRA_ATTACK);

        assertTrue(minion1.hasAbilities());
        assertTrue(minion2.hasAbilities());
    }

    @Test
    public void hasAbility_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.HASTE);
        minion2.addAbility(Ability.EXTRA_ATTACK);

        assertTrue(minion1.hasAbility(Ability.HASTE));
        assertFalse(minion2.hasAbility(Ability.HASTE));
    }

    @Test
    public void Haste_DivineShield_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.HASTE);
        minion2.addAbility(Ability.BLOCK);
        minion1.attack(minion2);

        assertEquals(3, minion1.getHealth());
        assertEquals(3, minion2.getHealth());
    }

    @Test
    public void Haste_Windfury_DivineShield_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 2, 4);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.EXTRA_ATTACK);
        minion1.addAbility(Ability.HASTE);
        minion2.addAbility(Ability.BLOCK);
        minion1.attack(minion2);
        minion1.attack(minion2);

        assertEquals(-2, minion1.getHealth());
        assertEquals(1, minion2.getHealth());
    }

    @Test
    public void asString_boardString_OK() {
        MinionCard minion = new MinionCard("Minion", 2, 2, 4);
        minion.addAbility(Ability.HASTE);
        minion.addAbility(Ability.TAUNT);

        assertEquals("         Minion, Mana Cost:  2, " +
                "Attack:  2, Health:  4, Abilities: HASTE, TAUNT\n", minion.asString());
        assertEquals("         Minion, Mana Cost:  2, Attack:  2, " +
                "Health:  4, Remaining Attacks: 1, Abilities: HASTE, TAUNT\n", minion.boardString());
    }


}