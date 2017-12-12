package dreamfactory.cardgame.api.actions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayCard implements Action {
    @JsonProperty
    private int index;

    public PlayCard() {
    }

    public PlayCard(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
