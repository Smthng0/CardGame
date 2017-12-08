package dreamfactory.cardgame;

import dreamfactory.cardgame.engine.CommandChecker;
import dreamfactory.cardgame.engine.Commands;
import dreamfactory.cardgame.engine.Engine;


public class GameStarter {
    public static void main(String[] args) {
        Commands commands = new Commands();
        CommandChecker commandChecker = new CommandChecker();

        do {
            commands.chooseGameType();
            commands.scanNextCommand();

            if (commandChecker.checkIfHotSeat(commands.getCommand())) {
                new Engine().initializeGame();
            }

        } while (!commandChecker.checkIfExitGame(commands.getCommand()));
        new Engine().initializeGame();
    }
}
