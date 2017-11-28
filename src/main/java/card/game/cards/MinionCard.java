package card.game.cards;

import card.game.Attackable;
import card.game.abilities.Ability;
import card.game.abilities.keywords.DivineShield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    }

    public MinionCard(String title, int manaCost, int attack, int health, List<Ability> abilities){
        this (title, manaCost, attack, health);
        this.abilities = new ArrayList<>();
        if (abilities != null){
            this.abilities.addAll(abilities);
        }
    }

    @Override
    public void play() {
        this.maxAttacks = 1;
        this.remainingAttacks = 0;

        if (getAbility("Windfury") != null){
            this.maxAttacks = 2;
        }

        if (getAbility("Charge") != null){
            this.remainingAttacks = this.maxAttacks;
        }
    }

    @Override
    public void attack(Attackable target) {
        if (target == null) {
            System.out.println("No target!");
        } else {
            if (remainingAttacks > 0) {
                target.defend(this);

                if (target instanceof MinionCard) {
                    this.defend(target);
                }

                this.remainingAttacks--;
            }
        }
    }

    public void defend(Attackable target){
        this.takeDamage(target.getAttack());
    }


    @Override
    public void takeDamage(int damage){
        int tempHealth = this.health;
        this.health -= damage;

        if ((getAbility("DivineShield") != null)
            && !((DivineShield)(getAbility("DivineShield")))
                .isUsedUp()) {
            getAbility("DivineShield").effect();
            this.health = tempHealth;
        }

        if (this.isDead()){
            System.out.println(this.getTitle() + " died a horrible and gruesome death!  --------{---(@");
            System.out.println();
        }
    }

    public Ability getAbility(Ability ability) {
        for (Ability abilityIterator : abilities) {
            if (abilityIterator.getAbilityType()
                    .equals(ability.getAbilityType())) {
                return abilityIterator;
            }
        }

        return null;
    }

    public Ability getAbility(String ability) {
        for (Ability abilityIterator : abilities) {
            if (abilityIterator.getAbilityType()
                    .equals(ability)) {
                return abilityIterator;
            }
        }

        return null;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    public void sortAbilitiesReverse() {
        abilities.sort(Comparator.comparing(Ability::getAbilityType));
        Collections.reverse(abilities);
    }

    public void suppressAbility(){
        this.abilities.clear();
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
    public void goToGraveyard() {
        if (this.getAbility("Deathrattle") != null) {
            this.getAbility("Deathrattle").effect();
        }

        System.out.println("Go to this players graveyard");
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

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxAttacks() {
        return maxAttacks;
    }

    public void setMaxAttacks(int maxAttacks) {
        this.maxAttacks = maxAttacks;
    }

    public int getRemainingAttacks() {
        return remainingAttacks;
    }

    public void setRemainingAttacks(int remainingAttacks) {
        this.remainingAttacks = remainingAttacks;
    }


}
