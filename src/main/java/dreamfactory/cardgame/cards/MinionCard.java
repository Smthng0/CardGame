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

            if (abilities.contains(Ability.WINDFURY)) {
                this.maxAttacks = 2;
            }

            if (abilities.contains(Ability.CHARGE)) {
                this.remainingAttacks = this.maxAttacks;
            }
        }
    }

    @Override
    public void takeDamage(int damage){
        if (abilities.contains(Ability.DIVINE_SHIELD)){
            abilities.remove(Ability.DIVINE_SHIELD);
        } else {
            takeDamage(damage);
        }
    }

    @Override
    public void addAbility(Ability ability) {
        abilities.add(ability);

        switch (ability) {
            case WINDFURY: maxAttacks = 2;
            break;
            case CHARGE: remainingAttacks = maxAttacks;
            break;
        }
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
    public String getTitle() {
        return title;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }


}
