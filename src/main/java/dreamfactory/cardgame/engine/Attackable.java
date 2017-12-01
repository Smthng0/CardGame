package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.MinionCard;

public abstract class Attackable {
    protected int attack = 0;
    protected int health = 20;
    protected int maxAttacks = 1;
    protected int remainingAttacks = 0;

    public void attack(Attackable target){
        target.takeDamage(attack);

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

    public int getMaxAttacks() {
        return maxAttacks;
    }

    public int getRemainingAttacks() {
        return remainingAttacks;
    }

    public void resetAttacks() {
        remainingAttacks = maxAttacks;
    }
}
