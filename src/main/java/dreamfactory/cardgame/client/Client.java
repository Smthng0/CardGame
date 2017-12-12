package dreamfactory.cardgame.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.*;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.ActionTypeAdapter;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

import java.util.List;

public class Client {
    private static Gson cardGson = new GsonBuilder().registerTypeAdapter(
            Card.class, new CardTypeAdapter()).create();
    private static Gson actionGson = new GsonBuilder().registerTypeAdapter(
            Action.class, new ActionTypeAdapter()).create();
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
        ClientCommands target = clientStateBuilder();
        return target.getStatus(playerName);
    }

    public static void endTurn() {
        ClientCommands target = clientStateBuilder();
        target.endTurn(playerName);
    }


    public static boolean sendAction(Action action) {
        ClientCommands target = clientActionBuilder();
        return target.sendAction(playerName, action);
    }

    public static List<Action> getActions() {
        ClientCommands target = clientActionBuilder();
        return target.getActions(playerName);
    }

    private static CreateGameClient createGameBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(cardGson))
                .decoder(new GsonDecoder(cardGson))
                .target(CreateGameClient.class, "http://localhost:8080/app");
    }

    private static ClientCommands clientStateBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(cardGson))
                .decoder(new GsonDecoder(cardGson))
                .target(ClientCommands.class, "http://localhost:8080/app");
    }

    private static ClientCommands clientActionBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(actionGson))
                .decoder(new GsonDecoder(actionGson))
                .target(ClientCommands.class, "http://localhost:8080/app");
    }

}
