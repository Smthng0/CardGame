package dreamfactory.cardgame;

import dreamfactory.cardgame.engine.CommandChecker;
import dreamfactory.cardgame.engine.Commands;
import dreamfactory.cardgame.engine.Engine;
import dreamfactory.cardgame.engine.MultiplayerEngine;


public class GameStarter {
    public static void main(String[] args) {
        Commands commands = new Commands();
        CommandChecker commandChecker = new CommandChecker();

        do {
            commands.chooseGameType();
            commands.scanNextCommand();

            if (commandChecker.checkIfHotSeat(commands.getCommand())) {
                new Engine().initializeGame(null, null);
            }

            if (commandChecker.checkIfMultiplayer(commands.getCommand())) {
                new MultiplayerEngine().createClient();
            }

        } while (!commandChecker.checkIfExitGame(commands.getCommand()));
    }
}
