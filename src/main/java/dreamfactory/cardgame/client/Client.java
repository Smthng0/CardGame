package dreamfactory.cardgame.client;

import dreamfactory.cardgame.api.CreatePlayer;
import dreamfactory.cardgame.player.Player;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;

public class Client {
    public static void main(String[] args) {
        Player player = create();
        System.out.println(player.getPlayerName());
    }

    public static Player create() {
        CreatePlayer target = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CreatePlayer.class, "http://localhost:8080/app");

        return target.createPlayer("kifla");
    }
}
