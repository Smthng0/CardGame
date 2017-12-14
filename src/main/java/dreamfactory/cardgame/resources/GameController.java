package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.player.Players;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import dreamfactory.cardgame.player.PlayerChecker;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private List<Action> actionList = new ArrayList<>();
    private Players players;
    public GameStatus gameState = GameStatus.NO_GAME;
    private MultiplayerEngine engine = new MultiplayerEngine();
    private PlayerChecker checker = new PlayerChecker();

    public Player createPlayer(String playerName) {
        if (gameState.equals(GameStatus.NO_GAME)){
            players = new Players();
        }

        if(checker.playersExist(players)){
            return null;
        }

        Player player = new Player(playerName, Deck.getConstructedDeck());

        if (!checker.player1Exists(players)) {
            players.setPlayer1(player);
            gameState = GameStatus.PREPARING;
            return player;
        }

        if (playerName.equals(players.getPlayer1().getPlayerName())) {
            return null;
        }

        players.setPlayer2(player);
        gameState = GameStatus.PREPARING;
        players.getPlayer2().startsSecond();
        return player;
    }

    public Players gameReady() {
        if(checker.playersExist(players)) {
            if (gameState.equals(GameStatus.PREPARING)){
                gameState = GameStatus.READY_TO_START;
            }
            return players;
        }
        return null;
    }

    public GameStatus startGame() {
        if (gameState.equals(GameStatus.READY_TO_START)) {
            gameState = GameStatus.PLAYER1_TURN;
            engine.initializeServer(players);
        }
        return gameState;
    }

    public void endTurn(String playerName) {
        if (checker.isPlayer1(playerName, players)) {
            gameState = GameStatus.PLAYER2_TURN;
            changeTurn();
        } else if (checker.isPlayer2(playerName, players)) {
            gameState = GameStatus.PLAYER1_TURN;
            changeTurn();
        }
    }

    private void changeTurn() {
        engine.endTurnSequence();
        engine.startTurnSequence();
    }

    public boolean sendAction(Action action) {
        if (gameState.equals(GameStatus.PLAYER1_TURN)) {
            if (doAction(players.getPlayer1(), players.getPlayer2(), action)){
                actionList.add(action);
                return true;
            }
        } else if (gameState.equals(GameStatus.PLAYER2_TURN)) {
            if (doAction(players.getPlayer2(), players.getPlayer1(), action)){
                actionList.add(action);
                return true;
            }
        }
        return false;
    }

    private boolean doAction(Player activePlayer, Player passivePlayer, Action action) {
        if (action instanceof Attack) {
            return engine.serverCommands.attackTarget(activePlayer, passivePlayer,
                    ((Attack) action).getAttackingIndex(),
                    ((Attack) action).getDefendingIndex());
        } else if (action instanceof PlayCard) {
            return  (activePlayer.playCard(
                    ((PlayCard) action).getIndex()) != null);
        }
        return false;
    }

    public List<Action> getActions(String playerName) {
        if ((checker.isPlayer1(playerName, players))
                && (gameState.equals(GameStatus.PLAYER2_TURN))) {
            return clearAndReturn(actionList);
        } else if ((checker.isPlayer2(playerName, players))
            && (gameState.equals(GameStatus.PLAYER1_TURN))) {
            return clearAndReturn(actionList);
        }


        return actionList;
    }

    private List<Action> clearAndReturn(List<Action> actionList) {
        List<Action> tempList = new ArrayList<>(actionList);
        actionList.clear();
        checkForPlayerDeath();
        return tempList;
    }

    private void checkForPlayerDeath() {
        if (players.getPlayer1().isDead()
                || players.getPlayer2().isDead()) {
            gameState = GameStatus.NO_GAME;
        }
    }

}
