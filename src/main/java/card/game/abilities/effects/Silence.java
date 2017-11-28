package card.game.abilities.effects;


import card.game.Engine;
import card.game.abilities.Ability;
import card.game.cards.MinionCard;

public class Silence implements Ability {

    @Override
    public void effect() {
        if (Engine.getEnemyMinion("target") instanceof MinionCard){
            ((MinionCard)(Engine.getEnemyMinion(""))).suppressAbility();
        } else {
            System.out.println("Invalid target!");
        }
    }

    @Override
    public String getAbilityType() {
        return "Silence";
    }

}
