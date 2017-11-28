package card.game.cards;

import card.game.Attackable;
import card.game.Ability;

import java.util.ArrayList;
import java.util.List;

public class MinionCard implements HearthstoneCard, Attackable {
    private String title;
    private int manaCost;
    private int attack;
    private int health;
    private int maxAttacks;
    private int remainingAttacks;
    private List<Ability> abilities;

    public MinionCard(String title, int manaCost, int attack, int health){
        this.title = title;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.abilities = new ArrayList<>();
        this.maxAttacks = 1;
        this.remainingAttacks = 0;
    }

    public MinionCard(String title, int manaCost, int attack, int health, List<Ability> abilities){
        this (title, manaCost, attack, health);

        if (abilities != null){
            this.abilities = abilities;

            if (abilities.contains(Ability.WINDFURY)) {
                this.maxAttacks = 2;
            }

            if (abilities.contains(Ability.CHARGE)) {
                this.remainingAttacks = this.maxAttacks;
            }
        }
    }

    @Override
    public void attack(Attackable target) {
        target.takeDamage(this.attack);

        if (target instanceof MinionCard) {
            this.takeDamage(target.getAttack());
        }

        this.remainingAttacks--;
    }

    @Override
    public void takeDamage(int damage){
        int tempHealth = this.health;
        this.health -= damage;

        if (abilities.contains(Ability.DIVINE_SHIELD)) {
            this.health = tempHealth;
            abilities.remove(Ability.DIVINE_SHIELD);
        }
    }

    public boolean checkForAbility(Ability ability) {
        return abilities.contains(ability);
    }

    public List<Ability> getAbilities() {
        return abilities;
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
    public boolean hasAbility() {
        return abilities.size() > 0;
    }

    @Override
    public boolean isDead(){
        return this.health <= 0;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    public int getHealth() {
        return health;
    }

    public int getRemainingAttacks() {
        return remainingAttacks;
    }

    public void resetAttacks() {
        remainingAttacks = maxAttacks;
    }


}
