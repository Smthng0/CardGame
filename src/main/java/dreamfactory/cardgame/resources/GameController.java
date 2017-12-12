package dreamfactory.cardgame.resources;

import dreamfactory.cardgame.api.GameStatus;
import dreamfactory.cardgame.api.Players;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import dreamfactory.cardgame.engine.Commands;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Commands commands;
    private List<Action> actionList = new ArrayList<>();
    private Players players = new Players();
    public GameStatus gameState = GameStatus.NO_GAME;

    private void startGame() {
        gameState = GameStatus.PLAYER1_TURN;
    }

    public void endTurn(String playerName) {
        if (isPlayer1(playerName)) {
            gameState = GameStatus.PLAYER2_TURN;
        } else if (isPlayer2(playerName)) {
            gameState = GameStatus.PLAYER1_TURN;
        }
    }

    public boolean sendAction(String playerName, Action action) {
        if (isPlayer1(playerName)) {
            if (doAction(players.getPlayer1(), players.getPlayer2(), action)){
                actionList.add(action);
                return true;
            }
        } else if (isPlayer2(playerName)) {
            if (doAction(players.getPlayer2(), players.getPlayer1(), action)){
                actionList.add(action);
                return true;
            }
        }

        return false;
    }

    private boolean doAction(Player activePlayer, Player passivePlayer, Action action) {
        if (action instanceof Attack) {
            return commands.attackTarget(activePlayer, passivePlayer,
                    ((Attack) action).getAttackingIndex(),
                    ((Attack) action).getDefendingIndex());
        } else if (action instanceof PlayCard) {
            return  ((activePlayer.playCard(
                    ((PlayCard) action).getIndex(),
                    ((PlayCard) action).getEngine())) != null);
        }
        return false;
    }

    public List<Action> getActions(String playerName) {
        List<Action> tempList = actionList;

        if ((isPlayer1(playerName))
                && (gameState.equals(GameStatus.PLAYER1_TURN))) {
            actionList.clear();
            return tempList;
        } else if ((isPlayer2(playerName))
            && (gameState.equals(GameStatus.PLAYER2_TURN))) {
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
            startGame();
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
