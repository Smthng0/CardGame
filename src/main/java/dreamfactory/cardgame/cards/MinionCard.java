package dreamfactory.cardgame.cards;

import dreamfactory.cardgame.engine.Attackable;

import java.util.ArrayList;
import java.util.List;

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
            takeDamage(damage);
        }
    }

    @Override
    public boolean hasWindfury() {
        return hasAbility(Ability.WINDFURY);
    }

    @Override
    public void addAbility(Ability ability) {
        abilities.add(ability);

        if (ability == Ability.HASTE){
            resetAttacks();
        }
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
