package dreamfactory.cardgame.cards;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(contentAs=MinionCard.class)
public interface Card {
    String getTitle();

    int getManaCost();

    boolean hasAbilities();

    boolean hasAbility(Ability ability);

    void addAbility(Ability ability);

    String asString();

}
