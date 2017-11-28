package card.game.cards;

import card.game.Engine;
import card.game.abilities.Ability;

import java.util.List;

public class HeroCard implements HearthstoneCard {
    private String title;
    private int manaCost;
    private int armor;
    private List<Ability> abilities;


    /**
     * for now this is not needed... will make something differet here
     * when the time comes to make something my own...
     * and not a hearthstone import :D
     */

    @Override
    public void play() {
        //do ability
    }

    @Override
    public void goToGraveyard() {
        Engine.getFriendlyPlayer().goToGraveyard(this);
    }

    @Override
    public boolean hasAbility() {
        return abilities.size() > 0;
    }

    @Override
    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

}
