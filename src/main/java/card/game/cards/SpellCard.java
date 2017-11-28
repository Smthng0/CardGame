package card.game.cards;

import card.game.Engine;
import card.game.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

public class SpellCard implements HearthstoneCard {
    private String title;
    private int manaCost;
    private List<Ability> abilities;

    public SpellCard(String title, int manaCost, List<Ability> abilities){
        super();
        this.title = title;
        this.manaCost = manaCost;
        this.abilities = new ArrayList<>();
        if (abilities != null){
            this.abilities.addAll(abilities);
        }
    }

    @Override
    public void play() {
        for (Ability ability : abilities) {
            ability.effect();
        }

        this.goToGraveyard();
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

    @Override
    public String getTitle() {
        return title;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
