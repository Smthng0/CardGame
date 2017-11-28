package card.game.cards;

import card.game.Engine;
import card.game.Ability;

import java.util.ArrayList;
import java.util.List;

public class SpellCard implements HearthstoneCard {
    private String title;
    private int manaCost;
    private List<Ability> abilities;

    public SpellCard(String title, int manaCost) {
        this.title = title;
        this.manaCost = manaCost;
        this.abilities = new ArrayList<>();
    }

    public SpellCard(String title, int manaCost, List<Ability> abilities) {
        this(title, manaCost);

        if (abilities != null){
            this.abilities = abilities;
        }
    }

    public void effect(Engine engine) {
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

}

