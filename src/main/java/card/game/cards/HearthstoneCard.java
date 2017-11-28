package card.game.cards;

import card.game.Ability;

public interface HearthstoneCard {

    String getTitle();

    int getManaCost();

    boolean hasAbility();

    void addAbility(Ability ability);

}
