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
                &&(weapon1.getAbilities().size() == 0)
                &&(!weapon1.hasAbility()));
        assertTrue((weapon2.getDurability() == 8)
                &&(weapon2.hasAbility()));
    }

    @Test
    public void addAbility_OK() {
        WeaponCard weapon = new WeaponCard("Sledgehammer", 6, 9, 4);
        weapon.addAbility(Ability.DIVINE_SHIELD);

        assertTrue(weapon.hasAbility());
        assertTrue(weapon.getAbilities().contains(Ability.DIVINE_SHIELD));
    }


}