package dreamfactory.cardgame.cards;

import java.util.List;

public interface HearthstoneCard {

    String getTitle();

    int getManaCost();

    boolean hasAbility();

    List<Ability> getAbilities();

    void addAbility(Ability ability);

}
