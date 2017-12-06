package dreamfactory.cardgame.cards;

import dreamfactory.cardgame.engine.Engine;

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
        if (abilities != null) this.abilities.addAll(abilities);
    }

    public void effect(Engine engine) {
    }

    @Override
    public boolean hasAbilities() {
        return !abilities.isEmpty();
    }

    @Override
    public boolean hasAbility(Ability ability) {
        return abilities.contains(ability);
    }

    @Override
    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    @Override
    public String asString() {
        String result = (title +
                ", Mana Cost: " + manaCost);
        if (hasAbilities()) {
            result += ", Abilities: " +
                    abilities.toString()
                            .replace("[","")
                            .replace("]","");
        }
        result += "\n";
        return result;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

}

