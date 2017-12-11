package dreamfactory.cardgame.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.ClientCommands;
import dreamfactory.cardgame.api.CreateGameClient;
import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import dreamfactory.cardgame.player.Player;
import dreamfactory.cardgame.resources.CreateGame;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

public class Client {
    static Gson myGson = new GsonBuilder().registerTypeAdapter(Card.class, new CardTypeAdapter()).create();
    private static String playerName = "frane";
    static MultiplayerEngine engine = new MultiplayerEngine();

    public static void main(String[] args) {
        Player player = create();
        Players players = null;

        if ((getStatus().equals(GameStatus.PREPARING))
                || (getStatus().equals(GameStatus.PLAYER1_TURN))) {
            do {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                players = gameReady();
            } while (players == null);
        }

        engine.initializeGame(players, playerName);
    }

    private static Player create() {
        CreateGameClient target = createGameBuilder();

        return target.createPlayer(playerName);
    }

    private static Players gameReady() {
        CreateGameClient target = createGameBuilder();

        return target.gameReady(playerName);
    }

    private static GameStatus getStatus() {
        ClientCommands target = clientCommandsBuilder();

        return target.getStatus(playerName);
    }

    private static CreateGameClient createGameBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(CreateGameClient.class, "http://localhost:8080/app");
    }

    private static ClientCommands clientCommandsBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(ClientCommands.class, "http://localhost:8080/app");
    }

}
