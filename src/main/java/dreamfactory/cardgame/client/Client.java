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


    public static void sendAction() {
        ClientCommands target = clientCommandsBuilder();
        String command = "";
        target.sendAction(playerName, command);
        //TODO: sredit da ide action po action... trebam engine/commands razjebat... napravit MultiplayerCommands :)
        // dovoljno mi je override attack target zasad... tamo prodje sve checkove....
        // i playCard (ako cardPlayedCheck vrati not null)
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
