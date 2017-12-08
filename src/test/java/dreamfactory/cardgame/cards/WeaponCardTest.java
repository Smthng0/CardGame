package dreamfactory.cardgame.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeaponCardTest {

    @Test
    public void create_weapon_OK() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(Ability.EXTRA_ATTACK);
        WeaponCard weapon1 = new WeaponCard("Sledgehammer", 6, 9, 4);
        WeaponCard weapon2 = new WeaponCard("Needle", 6, 2, 8, abilities);

        assertEquals("Sledgehammer", weapon1.getTitle());
        assertEquals(6, weapon1.getManaCost());
        assertEquals(9, weapon1.getAttack());
        assertEquals(4, weapon1.getDurability());
        assertFalse(weapon1.hasAbilities());
        assertEquals(8, weapon2.getDurability());
        assertTrue(weapon2.hasAbilities());
    }

    @Test
    public void addAbility_OK() {
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 4);
        weapon.addAbility(Ability.BLOCK);

        assertTrue(weapon.hasAbilities());
        assertTrue(weapon.hasAbility(Ability.BLOCK));
    }

    @Test
    public void asString_OK() {
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 4);
        weapon.addAbility(Ability.BLOCK);
        weapon.addAbility(Ability.EXTRA_ATTACK);

        assertEquals("Sledgehammer, Mana Cost: 6, Attack: 9, Durability: 4, " +
                "Abilities: BLOCK, EXTRA_ATTACK\n", weapon.asString());
    }

}