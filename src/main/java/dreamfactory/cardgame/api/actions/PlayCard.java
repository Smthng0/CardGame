package dreamfactory.cardgame.api.actions;

import com.fasterxml.jackson.annotation.JsonProperty;
import dreamfactory.cardgame.engine.Engine;

public class PlayCard implements Action {
    private int index;
    private Engine engine;

    public PlayCard() {
    }

    public PlayCard(int index, Engine engine) {
        this.index = index;
        this.engine = engine;
    }

    @JsonProperty
    public int getIndex() {
        return index;
    }

    @JsonProperty
    public void setIndex(int index) {
        this.index = index;
    }

    @JsonProperty
    public Engine getEngine() {
        return engine;
    }

    @JsonProperty
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
