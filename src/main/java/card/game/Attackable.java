package card.game;

public interface Attackable {

    void takeDamage(int damage);

    int getAttack();

    int getHealth();

    boolean isDead();

    void attack(Attackable target);
}
