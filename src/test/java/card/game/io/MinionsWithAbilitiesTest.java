package card.game.io;

import card.game.abilities.Ability;
import card.game.cards.MinionCard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MinionsWithAbilitiesTest {
    @Test
    public void createMinions_OK() throws Exception {
        MinionsWithAbilities minions = new MinionsWithAbilities();
        List<MinionCard> cardList = minions.createMinions();

        for (MinionCard card: cardList) {
            System.out.print(card.getTitle()
                    + ", " + card.getManaCost()
                    + ", " + card.getAttack()
                    + ", " + card.getHealth()
                    + ", ");
            printAbilities(card.getAbilities());
            System.out.println();
        }

        System.out.println("Number of minions: " + cardList.size());
    }

    public void printAbilities(List<Ability> list) {
        for (Ability ability : list) {
            System.out.print(ability.getAbilityType() + " ");
        }
    }

}