package dreamfactory.cardgame.cards;

import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpellCard implements Card {
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

    public void effect(Player player) {
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
        return basicInfoAsString()
                + abilitiesAsString()
                + "\n";
    }

    private String basicInfoAsString() {
        return String.format("%15s, Mana Cost: %2s",
                title, manaCost);
    }

    private String abilitiesAsString() {
        if (!hasAbilities()) {
            return "";
        }

        String abilitiesString = abilities.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return  ", Abilities: " + abilitiesString;
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

