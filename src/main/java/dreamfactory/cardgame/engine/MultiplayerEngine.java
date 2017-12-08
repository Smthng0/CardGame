package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.player.Player;

public class MultiplayerEngine {
    private Player thisPlayer;
    private Player opponent;
    private int turnCounter;
    private Commands commands = new Commands();
    private CommandChecker commandChecker = new CommandChecker();

    public void initializeGame(Player player1, Player player2) {
        commands.printer("Starting MultiPlayer Session...");
    }
}
