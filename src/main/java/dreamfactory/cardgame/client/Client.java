package dreamfactory.cardgame.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.CreateGame;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import dreamfactory.cardgame.player.Player;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

public class Client {
    static Gson myGson = new GsonBuilder().registerTypeAdapter(Card.class, new CardTypeAdapter()).create();

    public static void main(String[] args) {
        Player player = create();
        Players players = gameReady();
        System.out.println(player.getPlayerName());
        System.out.println(players.getPlayer2().getPlayerName());
    }

    public static Player create() {

        CreateGame target = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(CreateGame.class, "http://localhost:8080/app");

        return target.createPlayer("frane");
    }

    public static Players gameReady() {

        CreateGame target = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(CreateGame.class, "http://localhost:8080/app");

        return target.gameReady("frane");
    }
}
