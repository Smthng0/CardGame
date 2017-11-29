package dreamfactory.cardgame.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class WeaponCardTest {

    @Test
    public void create_weapon_OK() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(Ability.WINDFURY);
        WeaponCard weapon1 = new WeaponCard("Sledgehammer", 6, 9, 4);
        WeaponCard weapon2 = new WeaponCard("Needle", 6, 2, 8, abilities);

        assertTrue((weapon1.getTitle().equals("Sledgehammer"))
                &&(weapon1.getManaCost() == 6)
                &&(weapon1.getAttack() == 9)
                &&(weapon1.getDurability() == 4)
                &&(!weapon1.hasAbilities()));
        assertTrue((weapon2.getDurability() == 8)
                &&(weapon2.hasAbilities()));
    }

    @Test
    public void addAbility_OK() {
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 4);
        weapon.addAbility(Ability.DIVINE_SHIELD);

        assertTrue(weapon.hasAbilities());
        assertTrue(weapon.hasAbility(Ability.DIVINE_SHIELD));
    }


}