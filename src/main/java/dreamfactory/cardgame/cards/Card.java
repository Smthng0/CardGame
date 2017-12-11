package dreamfactory.cardgame.cards;

public interface Card {
    String getTitle();

    int getManaCost();

    boolean hasAbilities();

    boolean hasAbility(Ability ability);

    void addAbility(Ability ability);

    String asString();

}
