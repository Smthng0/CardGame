package dreamfactory.cardgame.api.actions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attack implements Action {
    private int attackingIndex;
    private int defendingIndex;

    public Attack() {

    }

    public Attack(int attackingIndex, int defendingIndex) {
        this.attackingIndex = attackingIndex;
        this.defendingIndex = defendingIndex;
    }

    @JsonProperty
    public int getAttackingIndex() {
        return attackingIndex;
    }

    @JsonProperty
    public void setAttackingIndex(int attackingIndex) {
        this.attackingIndex = attackingIndex;
    }

    @JsonProperty
    public int getDefendingIndex() {
        return defendingIndex;
    }

    @JsonProperty
    public void setDefendingIndex(int defendingIndex) {
        this.defendingIndex = defendingIndex;
    }
}
