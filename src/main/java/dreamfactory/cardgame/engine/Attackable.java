package dreamfactory.cardgame.engine;

public interface Attackable {
    int getAttack();

    int getHealth();

    void attack(Attackable target);

    void takeDamage(int damage);

    boolean isDead();
}
