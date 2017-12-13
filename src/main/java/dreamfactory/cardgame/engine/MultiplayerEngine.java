package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.Client;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerEngine extends Engine {
    private GameStatus myTurn;
    private Client client;
    private Commands oldCommands = new Commands();
    public Commands serverCommands = new ServerCommands();

    @Override
    public void initializeGame(Players players, String host) {
        client = new Client();
        commands = new MultiplayerCommands();
        commands.printer("Starting MultiPlayer Session...");
        activePlayer = players.getPlayer1();
        passivePlayer = players.getPlayer2();

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

    public void initializeServer(Players players) {
        activePlayer = players.getPlayer1();
        passivePlayer = players.getPlayer2();
        turnCounter = 2;
        commands = new ServerCommands();
        startTurnSequence();
    }

    @Override
    protected void startTurn() {
        if (myTurn.equals(client.getStatus())) {
            commands.printer("\nIt's your turn!\n");
            super.startTurn();
        } else {
            endTurn();
        }
    }

    @Override
    public void endTurn() {
        if (myTurn.equals(client.getStatus())) {
            commands.printer("It's your opponents turn!");
            client.endTurn();
            endTurnSequence();
            startTurnSequence();
        }

        commands.printer("Waiting for your opponent to end turn...");
        waitForTurn();
    }

    private void waitForTurn() {
        List<Action> actionList = new ArrayList<>();
        while (client.getStatus() != myTurn) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
                actionList.addAll(client.getActions());

                for (Action action : actionList) {
                    doActionsOfOpponent(activePlayer, passivePlayer, action);
                }

                actionList.clear();

                if (activePlayer.isDead() || passivePlayer.isDead()) {
                    commands.scanNextCommand();
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTurnSequence();
        startTurn();
    }

    private void doActionsOfOpponent(Player activePlayer,
                                     Player passivePlayer, Action action) {
        if (action instanceof Attack) {
            commands.printer("\nOpponent just attacked: \n");
            oldCommands.attackTarget(activePlayer, passivePlayer,
                    ((Attack) action).getAttackingIndex(),
                    ((Attack) action).getDefendingIndex());
        } else if (action instanceof PlayCard) {
            commands.printer("\nOpponent just played a card: \n" +
                    new CommandStrings()
                    .cardPlayedCheck(activePlayer.playCard(
                            ((PlayCard) action).getIndex()),
                            activePlayer.getRemainingMana()));
        }
    }

}
