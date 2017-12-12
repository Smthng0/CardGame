package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.client.Client;
import dreamfactory.cardgame.player.Player;

import java.util.List;

public class MultiplayerEngine extends Engine {
    private GameStatus myTurn;

    @Override
    public void initializeGame(Players players, String host) {
        commands = new MultiplayerCommands();
        commands.printer("Starting MultiPlayer Session...");
        activePlayer = players.getPlayer1();
        passivePlayer = players.getPlayer2();
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
            super.startTurn();
        } else {
            endTurn();
        }
    }

    @Override
    public void endTurn() {
        if (myTurn.equals(Client.getStatus())) {
            commands.printer("radi end turn");
            Client.endTurn();
            endTurnSequence();
        }

        commands.printer("radi wait for turn end turn");
        waitForTurn();
    }

    private void waitForTurn() {
        List<Action> actionList;
        do {
            try {
                Thread.sleep(1000);
                System.out.print(".");
                actionList = Client.getActions();
                for (Action action : actionList) {
                    doActionsOfOpponent(activePlayer, passivePlayer, action);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (Client.getStatus() != myTurn);
        endTurnSequence();
        startTurn(); //TODO: uvalit za exit game...
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
                            ((PlayCard) action).getIndex(),
                            ((PlayCard) action).getEngine()),
                            activePlayer.getRemainingMana()));
        }
    }

}
