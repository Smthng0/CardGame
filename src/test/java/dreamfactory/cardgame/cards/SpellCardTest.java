package dreamfactory.cardgame.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpellCardTest {

    @Test
    public void create_spell_OK() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(Ability.WINDFURY);
        SpellCard spellCard1 = new SpellCard("The Coin", 0, null);
        SpellCard spellCard2 = new SpellCard("The Coin", 1, abilities);

        assertTrue((spellCard1.getTitle().equals("The Coin"))
                &&(spellCard1.getManaCost() == 0)
                &&(!spellCard1.hasAbilities()));
        assertTrue((spellCard2.getManaCost() == 1)
                &&(spellCard2.hasAbilities()));
    }

    @Test
    public void addAbility_OK() {
        SpellCard spellCard = new SpellCard("The Coin", 0, null);

        assertFalse(spellCard.hasAbilities());

        spellCard.addAbility(Ability.WINDFURY);

        assertTrue(spellCard.hasAbilities());
        assertTrue(spellCard.hasAbility(Ability.WINDFURY));

    }

}