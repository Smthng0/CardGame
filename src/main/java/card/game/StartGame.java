package card.game;

public class StartGame {
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.createPlayers();
        engine.startOfTurn();
    }
}
