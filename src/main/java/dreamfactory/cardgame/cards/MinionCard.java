package dreamfactory.cardgame.cards;

import dreamfactory.cardgame.player.Attackable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinionCard extends Attackable implements HearthstoneCard {
    private String title;
    private int manaCost;
    private List<Ability> abilities;

    public MinionCard(String title, int manaCost, int attack, int health){
        this.title = title;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.abilities = new ArrayList<>();
    }

    public MinionCard(String title, int manaCost, int attack, int health, List<Ability> abilities){
        this (title, manaCost, attack, health);
        if (abilities != null) {
            this.abilities.addAll(abilities);

            if (abilities.contains(Ability.HASTE)) {
                resetAttacks();
            }
        }
    }

    @Override
    public void takeDamage(int damage){
        if (hasAbility(Ability.DIVINE_SHIELD)){
            abilities.remove(Ability.DIVINE_SHIELD);
        } else {
            health -= damage;
        }
    }

    @Override
    public boolean hasWindfury() {
        return hasAbility(Ability.WINDFURY);
    }

    @Override
    public String getName() {
        return getTitle();
    }

    @Override
    public void addAbility(Ability ability) {
        abilities.add(ability);

        if (ability == Ability.HASTE){
            resetAttacks();
        }
    }

    @Override
    public String asString() {
        return basicInfoAsString()
                + abilitiesAsString()
                + "\n";
    }

    public String boardString() {
        return basicInfoAsString()
                + attacksAsString()
                + abilitiesAsString()
                + "\n";
    }

    private String basicInfoAsString() {
        return String.format("%15s, Mana Cost: %2s, Attack: %2s, Health: %2s",
                title, manaCost, attack, health);
    }

    private String attacksAsString() {
        if (canAttack()) {
            return ", Remaining Attacks: " + remainingAttacks;
        }
        return "";
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

    @Override
    public boolean hasAbilities() {
        return !abilities.isEmpty();
    }

    @Override
    public boolean hasAbility(Ability ability) {
        return abilities.contains(ability);
    }

}
