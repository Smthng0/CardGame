package dreamfactory.cardgame;

import dreamfactory.cardgame.engine.Engine;

public class GameStarter {
    public static void main(String[] args) throws Exception{
        new Engine().initializeGame();
    }
}
