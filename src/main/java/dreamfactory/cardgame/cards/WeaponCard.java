package dreamfactory.cardgame.cards;

import java.util.ArrayList;
import java.util.List;

public class WeaponCard implements HearthstoneCard {
    private String title;
    private int manaCost;
    private int attack;
    private int durability;
    private List<Ability> abilities;

    public WeaponCard(String title, int manaCost, int attack, int durability){
        this.title = title;
        this.manaCost = manaCost;
        this.attack = attack;
        this.durability = durability;
        this.abilities = new ArrayList<>();
    }

    public WeaponCard(String title, int manaCost, int attack, int durability, List<Ability> abilities){
        this(title, manaCost, attack, durability);
        if (abilities != null){
            this.abilities = abilities;
        }
    }

    @Override
    public boolean hasAbility() {
        return abilities.size() > 0;
    }

    @Override
    public void addAbility(Ability ability){
        this.abilities.add(ability);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }

}
