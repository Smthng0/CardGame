package dreamfactory.cardgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dreamfactory.cardgame.api.*;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.ActionTypeAdapter;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.CardTypeAdapter;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;

import java.util.List;

public class Client {
    private Gson myGson = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .registerTypeAdapter(Action.class, new ActionTypeAdapter())
            .create();
    private String url = "http://localhost:8080/app";
    private ClientCommands clientCommands = clientCommandsBuilder();
    private CreateGameClient createGameClient = createGameBuilder();
    private String playerName = "frane";

    public static void main(String[] args) {
        Client client = new Client();
        client.create();
        Players players = null;

        if ((client.getStatus().equals(GameStatus.PREPARING))
                || (client.getStatus().equals(GameStatus.READY_TO_START))
                || (client.getStatus().equals(GameStatus.PLAYER1_TURN))) {
            do {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                players = client.gameReady();
            } while (players == null);
        }

        client.startGame();
        new MultiplayerEngine().initializeGame(players, client.playerName);
    }

    public void create() {
        createGameClient.createPlayer(playerName);
    }

    public Players gameReady() {
        return createGameClient.gameReady(playerName);
    }

    public void startGame() {
        createGameClient.startGame(playerName);
    }

    public GameStatus getStatus() {
        return clientCommands.getStatus(playerName);
    }

    public void endTurn() {
        clientCommands.endTurn(playerName);
    }

    public boolean playCard(PlayCard action) {
        return clientCommands.playCard(action);
    }


    public boolean sendAction(Action action) {
        return clientCommands.sendAction(action);
    }

    public Action getAction() {
        return clientCommands.getAction(playerName);
    }

    private CreateGameClient createGameBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(CreateGameClient.class, url);
    }

    private ClientCommands clientCommandsBuilder() {
        return Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder(myGson))
                .decoder(new GsonDecoder(myGson))
                .target(ClientCommands.class, url);
    }

}
