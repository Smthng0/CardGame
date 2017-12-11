package dreamfactory.cardgame.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.*;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

public class Client {
    private static Gson myGson = new GsonBuilder().registerTypeAdapter(Card.class, new CardTypeAdapter()).create();
    private static MultiplayerEngine engine = new MultiplayerEngine();
    private static String playerName = "frane";

    public static void main(String[] args) {
        create();
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

    private static void create() {
        CreateGameClient target = createGameBuilder();
        target.createPlayer(playerName);
    }

    private static Players gameReady() {
        CreateGameClient target = createGameBuilder();
        return target.gameReady(playerName);
    }

    public static GameStatus getStatus() {
        ClientCommands target = clientCommandsBuilder();
        return target.getStatus(playerName);
    }

    public static void endTurn() {
        ClientCommands target = clientCommandsBuilder();
        target.endTurn(playerName);
    }

    public static boolean sendPlayers(Players players) {
        ClientUpdate target = clientUpdateBuilder();
        return target.sendPlayers(playerName, players);
    }

    public static Players recievePlayers() {
        ClientUpdate target = clientUpdateBuilder();
        return target.recievePlayers(playerName);
    }

    public static void doAction() {
        ClientCommands target = clientCommandsBuilder();
        String command = "";
        target.action(playerName, command);
        //TODO: sredit da ide action po action... trebam engine/commands razjebat
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

    private static ClientUpdate clientUpdateBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(ClientUpdate.class, "http://localhost:8080/app");
    }



}
