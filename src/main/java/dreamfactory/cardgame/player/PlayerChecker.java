package dreamfactory.cardgame.player;

public class PlayerChecker {
    public boolean playersExist(Players players){
        return (player1Exists(players) && player2Exists(players));
    }

    public boolean player1Exists(Players players) {
        return (players.getPlayer1() != null);
    }

    public boolean player2Exists(Players players) {
        return (players.getPlayer2() != null);
    }

    public boolean isPlayer1(String playerName, Players players) {
        return playerName.equals(players.getPlayer1().getPlayerName());
    }

    public boolean isPlayer2(String playerName, Players players) {
        return playerName.equals(players.getPlayer2().getPlayerName());
    }
}
