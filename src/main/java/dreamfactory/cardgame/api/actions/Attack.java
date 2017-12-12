package dreamfactory.cardgame.api.actions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attack implements Action {
    @JsonProperty
    private int attackingIndex;
    @JsonProperty
    private int defendingIndex;

    public Attack() {
    }

    public Attack(int attackingIndex, int defendingIndex) {
        this.attackingIndex = attackingIndex;
        this.defendingIndex = defendingIndex;
    }

    public int getAttackingIndex() {
        return attackingIndex;
    }

    public void setAttackingIndex(int attackingIndex) {
        this.attackingIndex = attackingIndex;
    }

    public int getDefendingIndex() {
        return defendingIndex;
    }

    public void setDefendingIndex(int defendingIndex) {
        this.defendingIndex = defendingIndex;
    }
}
