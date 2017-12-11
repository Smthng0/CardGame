package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

public class GameController {
    private MultiplayerEngine engine;
    private Players players = new Players();
    public GameStatus gameState = GameStatus.NO_GAME;

    enum GameStatus {
        NO_GAME,
        PREPARING,
        PLAYER1_TURN,
        PLAYER2_TURN
    }

    private void startGame() {
        this.engine = new MultiplayerEngine();
        engine.initializeGame(players.getPlayer1(),players.getPlayer2());
        gameState = GameStatus.PLAYER1_TURN;
    }

    public void endTurn(Player player) {
        if (player.equals(players.getPlayer1())) {
            gameState = GameStatus.PLAYER2_TURN;
        } else if (player.equals(players.getPlayer2())) {
            gameState = GameStatus.PLAYER1_TURN;
        }
        //TODO: tu trebam poporavit logiku... (da su sve isti objekti, samo uvalit dodatno ako igra prvi)
    }

    public boolean doAction(String command) {
        return !command.equalsIgnoreCase("tu trebam vidit ako je prosla komanda");
        //TODO: mislio sam slati komande u engine, tj u system in dok se vrti igra... pa vracati oba playera system.out..gi
    }

    public Player createPlayer(String playerName) {
        if(playersExist()){
            return null;
        }

        Player player = new Player(playerName, Deck.getConstructedDeck());

        if (!player1Exists()) {
            players.setPlayer1(player);
            return player;
    }

        if (playerName.equals(players.getPlayer1().getPlayerName())) {
            return null;
        }

        players.setPlayer2(player);
        return player;
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

    public Players gameReady() {
        if(playersExist()) {
            startGame();
            return players;
        }
        return null;
    }

}
