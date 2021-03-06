package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.MinionCard;

public abstract class Attackable {
    protected int attack = 0;
    protected int health = 20;  //default player health, minion gets his on creation
    protected int remainingAttacks = 0;

    public void attack(Attackable target){
        if (!canAttack()) {
            return;
        }

        target.takeDamage(attack);
        remainingAttacks--;

        if (target instanceof MinionCard) {
            takeDamage(target.getAttack());
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth(){
        return health;
    }

    public int getRemainingAttacks() {
        return remainingAttacks;
    }

    abstract public boolean hasWindfury();

    abstract public String getName();

    abstract public String asString();

    public boolean canAttack() {
        return remainingAttacks > 0;
    }

    public void resetAttacks() {
        remainingAttacks = 1;

        if (hasWindfury()){
            remainingAttacks = 2;
        }
    }
}
