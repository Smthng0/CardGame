package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

public class GameController {
    private MultiplayerEngine engine;
    private Players players = new Players();
    public GameStatus gameState = GameStatus.NO_GAME;

    private void startGame() {
        this.engine = new MultiplayerEngine();
        gameState = GameStatus.PLAYER1_TURN;
    }

    public void endTurn(String playerName) {
        if (playerName.equals(players.getPlayer1().getPlayerName())) {
            gameState = GameStatus.PLAYER2_TURN;
        } else if (playerName.equals(players.getPlayer2().getPlayerName())) {
            gameState = GameStatus.PLAYER1_TURN;
        }
    }

    public boolean doAction(String command) {
        //TODO: uvalit da provjeri ako je uspila komanda...
        return true;
    }

    public boolean recievePlayers(String playerName, Players players) {
        if ((playerName.equals(players.getPlayer1().getPlayerName()))
                && gameState.equals(GameStatus.PLAYER1_TURN)) {
            this.players = players;
            return true;
        }

        if ((playerName.equals(players.getPlayer2().getPlayerName()))
                && gameState.equals(GameStatus.PLAYER2_TURN)) {
            this.players = players;
            return true;
        }

        return false;
    }

    public Players sendPlayers(String playerName) {
        if ((playerName.equals(players.getPlayer1().getPlayerName()))
                && gameState.equals(GameStatus.PLAYER1_TURN)) {
            return players;
        }

        if ((playerName.equals(players.getPlayer2().getPlayerName()))
                && gameState.equals(GameStatus.PLAYER2_TURN)) {
            return players;
        }

        return null;
    }

    public Player createPlayer(String playerName) {
        if(playersExist()){
            return null;
        }

        Player player = new Player(playerName, Deck.getConstructedDeck());

        if (!player1Exists()) {
            players.setPlayer1(player);
            gameState = GameStatus.PREPARING;
            return player;
        }

        if (playerName.equals(players.getPlayer1().getPlayerName())) {
            return null;
        }

        players.setPlayer2(player);
        gameState = GameStatus.PREPARING;
        return player;
    }

    public Players gameReady() {
        if(playersExist()) {
            startGame();
            return players;
        }
        return null;
    }

    private boolean playersExist(){
        return (player1Exists() && player2Exists());
    }

    private boolean player1Exists() {
        return (players.getPlayer1() != null);
    }

    private boolean player2Exists() {
        return (players.getPlayer2() != null);
    }

}
