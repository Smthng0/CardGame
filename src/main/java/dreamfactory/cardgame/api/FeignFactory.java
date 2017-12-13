package dreamfactory.cardgame.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.ActionTypeAdapter;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

public class FeignFactory {
    private String url;
    private final Gson myGson = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .registerTypeAdapter(Action.class, new ActionTypeAdapter())
            .create();

    public FeignFactory(String ipAddress) {
        this.url = "http://" + ipAddress + ":8080/app";
    }

    public ClientCreateGame clientCreateGameBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(ClientCreateGame.class, url);
    }

    public ClientCommands clientCommandsBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(ClientCommands.class, url);
    }
}
