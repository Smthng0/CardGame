package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.engine.MultiplayerEngine;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private List<Action> actionList = new ArrayList<>();
    private Players players = new Players();
    public GameStatus gameState = GameStatus.NO_GAME;
    private MultiplayerEngine engine = new MultiplayerEngine();

    public GameStatus startGame() {
        if (gameState.equals(GameStatus.READY_TO_START)) {
            gameState = GameStatus.PLAYER1_TURN;
            engine.initializeServer(players);
        }
        return gameState;
    }

    public void endTurn(String playerName) {
        if (isPlayer1(playerName)) {
            gameState = GameStatus.PLAYER2_TURN;
            engine.endTurnSequence();
            engine.startTurnSequence();
        } else if (isPlayer2(playerName)) {
            gameState = GameStatus.PLAYER1_TURN;
            engine.endTurnSequence();
            engine.startTurnSequence();
        }
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
        List<Action> tempList = new ArrayList<>(actionList);

        if ((isPlayer1(playerName))
                && (gameState.equals(GameStatus.PLAYER2_TURN))) {
            actionList.clear();
            return tempList;
        } else if ((isPlayer2(playerName))
            && (gameState.equals(GameStatus.PLAYER1_TURN))) {
            actionList.clear();
            return tempList;
        }

        return actionList;
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
            if (gameState.equals(GameStatus.PREPARING)){
                gameState = GameStatus.READY_TO_START;
            }
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

    private boolean isPlayer1(String playerName) {
        return playerName.equals(players.getPlayer1().getPlayerName());
    }

    private boolean isPlayer2(String playerName) {
        return playerName.equals(players.getPlayer2().getPlayerName());
    }

}
