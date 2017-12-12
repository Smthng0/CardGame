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
    public Commands servercommands;

    @Override
    public void initializeGame(Players players, String host) {
        commands = new MultiplayerCommands();
        client = new Client();
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
        servercommands = new Commands();
        activePlayer = players.getPlayer1();
        passivePlayer = players.getPlayer2();
        turnCounter = 2;
        startTurnSequence();
    }

    @Override
    protected void startTurn() {
        if (myTurn.equals(client.getStatus())) {
            commands.printer("radi startTurn");
            startTurnSequence();
            chooseAction();
        } else {
            startTurnSequence();
            endTurn();
        }
    }

    @Override
    public void endTurn() {
        if (myTurn.equals(client.getStatus())) {
            commands.printer("radi end turn");
            client.endTurn();
            endTurnSequence();
        }

        commands.printer("radi wait for turn end turn");
        waitForTurn();
    }

    private void waitForTurn() {
        List<Action> actionList = new ArrayList<>();
        do {
            try {
                Thread.sleep(1000);
                System.out.print(".");
                actionList.addAll(client.getActions());
                for (Action action : actionList) {
                    doActionsOfOpponent(activePlayer, passivePlayer, action);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (client.getStatus() != myTurn);
        endTurnSequence();
        startTurn();
    }

    private void doActionsOfOpponent(Player activePlayer,
                                     Player passivePlayer, Action action) {
        if (action instanceof Attack) {
            super.commands.attackTarget(activePlayer, passivePlayer,
                    ((Attack) action).getAttackingIndex(),
                    ((Attack) action).getDefendingIndex());
        } else if (action instanceof PlayCard) {
            commands.printer(new CommandStrings()
                    .cardPlayedCheck(activePlayer.playCard(
                            ((PlayCard) action).getIndex()),
                            activePlayer.getRemainingMana()));
        }
    }

}
