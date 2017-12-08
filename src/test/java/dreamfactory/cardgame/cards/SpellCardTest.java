package dreamfactory.cardgame.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpellCardTest {

    @Test
    public void create_spell_OK() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(Ability.EXTRA_ATTACK);
        SpellCard spellCard1 = new SpellCard("The Coin", 0, null);
        SpellCard spellCard2 = new SpellCard("The Coin", 1, abilities);

        assertEquals("The Coin", spellCard1.getTitle());
        assertEquals(0, spellCard1.getManaCost());
        assertFalse(spellCard1.hasAbilities());
        assertEquals(1, spellCard2.getManaCost());
        assertTrue(spellCard2.hasAbilities());
    }

    @Test
    public void addAbility_OK() {
        SpellCard spellCard = new SpellCard("The Coin", 0, null);

        assertFalse(spellCard.hasAbilities());

        spellCard.addAbility(Ability.EXTRA_ATTACK);

        assertTrue(spellCard.hasAbilities());
        assertTrue(spellCard.hasAbility(Ability.EXTRA_ATTACK));

    }

    @Test
    public void asString_OK() {
        SpellCard spellCard = new SpellCard("The Coin", 0, null);
        spellCard.addAbility(Ability.ADD_MANA);

        assertEquals("       The Coin, Mana Cost:  0, Abilities: ADD_MANA\n", spellCard.asString());
    }

}