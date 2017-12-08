package dreamfactory.cardgame.cards;

import java.util.ArrayList;
import java.util.List;

public class WeaponCard implements Card {
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
        if (abilities != null) this.abilities.addAll(abilities);
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
    public void addAbility(Ability ability){
        this.abilities.add(ability);
    }

    @Override
    public String asString() {
        String result = (title +
                ", Mana Cost: " + manaCost +
                ", Attack: " + attack +
                ", Durability: " + durability);
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

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }


}
