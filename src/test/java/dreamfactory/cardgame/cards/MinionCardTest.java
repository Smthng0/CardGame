package dreamfactory.cardgame.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinionCardTest {

    @Test
    public void create_minion_OK() {
        List<Ability> ability = new ArrayList<>();
        ability.add(Ability.CHARGE);
        MinionCard minion = new MinionCard("Minion1", 8, 17, 4);
        MinionCard minionWithAbility = new MinionCard("Minion1", 8, 5, 8, ability);

        assertTrue((minion.getTitle().equals("Minion1"))
                &&(minion.getManaCost() == 8)
                &&(minion.getAttack() == 17)
                &&(minion.getHealth() == 4)
                &&(!minion.hasAbility()));
        assertTrue((minionWithAbility.getHealth() == 8)
                &&(minionWithAbility.hasAbility()));
    }

    @Test
    public void attack_sequence_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 2, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 4, 3);
        minion1.resetAttacks();
        minion1.attack(minion2);

        assertTrue(minion1.getHealth() == 2);
        assertTrue(minion2.getHealth() == 1);
    }

    @Test
    public void Charge_addAbility_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.CHARGE);
        minion1.attack(minion2);

        assertTrue(minion1.getHealth() == 3);
        assertTrue(minion2.getHealth() == -1);
    }

    @Test
    public void checkForAbility_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.CHARGE);
        minion2.addAbility(Ability.WINDFURY);

        assertFalse(minion2.checkForAbility(Ability.DIVINE_SHIELD));
        assertTrue(minion2.checkForAbility(Ability.WINDFURY));
    }

    @Test
    public void Windfury_Charge_sortAbility_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 2, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 2, 3);
        minion1.addAbility(Ability.WINDFURY);
        minion1.addAbility(Ability.CHARGE);
        minion1.attack(minion2);
        minion1.attack(minion2);

        assertTrue(minion1.getHealth() == 2);
        assertTrue(minion2.getHealth() == -1);

    }

    @Test
    public void Charge_DivineShield_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 4, 6);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.CHARGE);
        minion2.addAbility(Ability.DIVINE_SHIELD);
        minion1.attack(minion2);

        assertTrue(minion1.getHealth() == 3);
        assertTrue(minion2.getHealth() == 3);
    }

    @Test
    public void Charge_Windfury_DivineShield_OK() {
        MinionCard minion1 = new MinionCard("Minion1", 2, 2, 4);
        MinionCard minion2 = new MinionCard("Minion2", 3, 3, 3);
        minion1.addAbility(Ability.CHARGE);
        minion1.addAbility(Ability.WINDFURY);
        minion2.addAbility(Ability.DIVINE_SHIELD);
        minion1.attack(minion2);
        minion1.attack(minion2);

        assertTrue(minion1.getHealth() == -2);
        assertTrue(minion2.getHealth() == 1);
    }


}