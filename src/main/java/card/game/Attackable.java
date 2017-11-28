package card.game;

public interface Attackable {

    void takeDamage(int damage);

    int getAttack();

    boolean isDead();

    void attack(Attackable target);
}
