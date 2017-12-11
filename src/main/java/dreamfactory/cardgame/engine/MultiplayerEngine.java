package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.player.Player;

public class MultiplayerEngine {
    private Player thisPlayer;
    private Player opponent;
    private int turnCounter;
    private Commands commands = new Commands();
    private CommandChecker commandChecker = new CommandChecker();
    private GameStatus myTurn;

    public void initializeGame(Players players, String host) {
        commands.printer("Starting MultiPlayer Session...");

        if (host.equals(players.getPlayer1().getPlayerName())) {
            this.thisPlayer = players.getPlayer1();
            this.opponent = players.getPlayer2();
            this.myTurn = GameStatus.PLAYER1_TURN;
        }

        if (host.equals(players.getPlayer2().getPlayerName())) {
            this.thisPlayer = players.getPlayer2();
            this.opponent = players.getPlayer1();
            this.myTurn = GameStatus.PLAYER2_TURN;
        }

        if (host.equals("server")) {
            this.thisPlayer = players.getPlayer1();
            this.opponent = players.getPlayer2();
            this.myTurn = null;
        }

    }
}
