package dreamfactory.cardgame.multiplayer.Server.resources;

import dreamfactory.cardgame.engine.Engine;
import dreamfactory.cardgame.multiplayer.Server.api.Players;

public class GameController {
    private Engine engine;
    private Players players;
    public GameStatus gameState = GameStatus.NO_GAME;

    enum GameStatus {
        NO_GAME,
        PREPARING,
        PLAYER1_TURN,
        PLAYER2_TURN
    }

    private void startGame(String player1, String player2) {
        this.engine = new Engine();
        engine.createMultiPlayers(player1,player2);
        gameState = GameStatus.PLAYER1_TURN;
    }

    public void endTurn(String playerName) {
        if (playerName.equals(players.getPlayer1Name())) {
            gameState = GameStatus.PLAYER2_TURN;
        } else if (playerName.equals(players.getPlayer2Name())) {
            gameState = GameStatus.PLAYER1_TURN;
        }
    }

    public boolean doAction(String command) {
        return !command.equalsIgnoreCase("tu trebam vidit ako je prosla komanda");
        //mislio sam slati komande u engine, tj u system in dok se vrti igra... pa vracati oba playera system.out..gi
    }

    private boolean playerExists(String player){
        return player != null;
    }

    public boolean isGameReady(String player1, String player2) {
        if((playerExists(player1) && playerExists(player2))) {
            startGame(player1, player2);
            players = new Players(player1, player2);
            return true;
        }
        return false;
    }

}
