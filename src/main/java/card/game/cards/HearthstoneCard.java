package card.game.cards;

import card.game.abilities.Ability;

public interface HearthstoneCard {

    void play();

    void goToGraveyard();

    boolean hasAbility();

    int getManaCost();

    void addAbility(Ability ability);

    default void removeFromPlay() {
        this.goToGraveyard();
    };

    String getTitle();

}
