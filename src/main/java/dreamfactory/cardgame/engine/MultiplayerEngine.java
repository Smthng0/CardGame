package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.client.Client;
import dreamfactory.cardgame.player.Player;

public class MultiplayerEngine extends Engine {
    private GameStatus myTurn;

    @Override
    public void initializeGame(Players players, String host) {
        commands.printer("Starting MultiPlayer Session...");
        this.activePlayer = players.getPlayer1();
        this.passivePlayer = players.getPlayer2();
        passivePlayer.startsSecond();

        if (host.equals(players.getPlayer1().getPlayerName())) {
            this.myTurn = GameStatus.PLAYER1_TURN;
        }

        if (host.equals(players.getPlayer2().getPlayerName())) {
            this.myTurn = GameStatus.PLAYER2_TURN;
        }

        turnCounter = 2;
        commands.introPrint(activePlayer, passivePlayer);
        startTurn();
    }

    @Override
    protected void startTurn() {
        if (myTurn.equals(Client.getStatus())) {
            commands.printer("radi startTurn");
            recievePlayers(Client.recievePlayers());
            super.startTurn();
        }

        commands.printer("radi wait for turn u start turn");
        waitForTurn();
    }

    @Override
    public void endTurn() {
        if (myTurn.equals(Client.getStatus())) {
            commands.printer("radi end turn");
            Client.sendPlayers(sendPlayers());
            Client.endTurn();
            super.endTurn();
        }

        commands.printer("radi wait for turn end turn");
        waitForTurn();
    }

    private void waitForTurn() {
        do {
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (Client.getStatus() != myTurn);
        startTurn();
    }

    public void setActivePlayer(Player player) {
        activePlayer = player;
    }

    public void setPassivePlayer(Player player) {
        passivePlayer = player;
    }

    private Players sendPlayers() {
        Players players = new Players();
        if (myTurn.equals(GameStatus.PLAYER1_TURN)) {
            players.setPlayer1(activePlayer);
            players.setPlayer2(passivePlayer);
            return players;
        } else if (myTurn.equals(GameStatus.PLAYER2_TURN)) {
            players.setPlayer1(passivePlayer);
            players.setPlayer2(activePlayer);
            return players;
        }
        return null;
    }

    private void recievePlayers(Players players) {
        turnCounter ++;
        if (myTurn.equals(GameStatus.PLAYER1_TURN)) {
            activePlayer = players.getPlayer1();
            passivePlayer = players.getPlayer2();
        } else if (myTurn.equals(GameStatus.PLAYER2_TURN)) {
            activePlayer = players.getPlayer2();
            passivePlayer = players.getPlayer1();
        }
    }
}
