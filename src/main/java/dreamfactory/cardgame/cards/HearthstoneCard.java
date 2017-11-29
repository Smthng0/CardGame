package dreamfactory.cardgame.cards;

public interface HearthstoneCard {
    String getTitle();

    int getManaCost();

    boolean hasAbilities();

    boolean hasAbility(Ability ability);

    void addAbility(Ability ability);

}
