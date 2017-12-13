package dreamfactory.cardgame;

import dreamfactory.cardgame.api.*;
import dreamfactory.cardgame.api.actions.Action;

import java.util.List;

public class Client {
    private ClientCreateGame clientCreateGame;
    private ClientCommands clientCommands;
    private String playerName;

    private Client (ClientCreateGame clientCreateGame,
                   ClientCommands clientCommands) {
        this.clientCreateGame = clientCreateGame;
        this.clientCommands = clientCommands;
    }

    public Client (String playerName,
                   ClientCreateGame clientCreateGame,
                   ClientCommands clientCommands) {
        this(clientCreateGame, clientCommands);
        this.playerName = playerName;
    }

    public Players initializeClient() {
        create();
        Players players = null;

        if ((getStatus().equals(GameStatus.PREPARING))
                || (getStatus().equals(GameStatus.READY_TO_START))
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

        startGame();
        return players;
    }

    public void create() {
        clientCreateGame.createPlayer(playerName);
    }

    public Players gameReady() {
        return clientCreateGame.gameReady(playerName);
    }

    public void startGame() {
        clientCreateGame.startGame(playerName);
    }

    public GameStatus getStatus() {
        return clientCommands.getStatus(playerName);
    }

    public void endTurn() {
        clientCommands.endTurn(playerName);
    }

    public boolean sendAction(Action action) {
        return clientCommands.sendAction(action);
    }

    public List<Action> getActions() {
        return clientCommands.getActions(playerName);
    }

}
