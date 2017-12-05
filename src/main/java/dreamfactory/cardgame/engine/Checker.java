package dreamfactory.cardgame.engine;

public class Checker {

    public boolean checkIfPlay(String command) {
        return ((command.equalsIgnoreCase("Play card"))
                || (command.equalsIgnoreCase("Play"))
                || (command.equalsIgnoreCase("P"))
                || (command.equalsIgnoreCase("Card")));
    }

    public boolean checkIfAttack(String command) {
        return ((command.equalsIgnoreCase("Attack"))
                || (command.equalsIgnoreCase("A")));
    }

    public boolean checkIfCheckStatus(String command) {
        return ((command.equalsIgnoreCase("Check Status"))
                || (command.equalsIgnoreCase("Status"))
                || (command.equalsIgnoreCase("C"))
                || (command.equalsIgnoreCase("Check")));
    }

    public boolean checkIfViewBoard (String command) {
        return ((command.equalsIgnoreCase("View board"))
                || (command.equalsIgnoreCase("View"))
                || (command.equalsIgnoreCase("V"))
                || (command.equalsIgnoreCase("Board")));
    }

    public boolean checkIfEndTurn (String command) {
        return ((command.equalsIgnoreCase("End Turn"))
                || (command.equalsIgnoreCase("End"))
                || (command.equalsIgnoreCase("Turn"))
                || (command.equalsIgnoreCase("E")));
    }

    public boolean checkIfExitGame (String command) {
        return ((command.equalsIgnoreCase("Exit Game"))
                || (command.equalsIgnoreCase("Exit"))
                || (command.equalsIgnoreCase("Game"))
                || (command.equalsIgnoreCase("X")));
    }

}
