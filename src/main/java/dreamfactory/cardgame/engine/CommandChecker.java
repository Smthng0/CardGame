package dreamfactory.cardgame.engine;

public class CommandChecker {

    public boolean checkIfHotSeat(String command) {
        return ((command.equalsIgnoreCase("HotSeat"))
                || (command.equalsIgnoreCase("Hot"))
                || (command.equalsIgnoreCase("Seat"))
                || (command.equalsIgnoreCase("H")));
    }

    public boolean checkIfMultiplayer(String command) {
        return ((command.equalsIgnoreCase("MultiPlayer"))
                || (command.equalsIgnoreCase("Multi"))
                || (command.equalsIgnoreCase("Player"))
                || (command.equalsIgnoreCase("M")));
    }

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

    public boolean checkIfViewBoards(String command) {
        return ((command.equalsIgnoreCase("View Hand"))
                || (command.equalsIgnoreCase("View"))
                || (command.equalsIgnoreCase("Board"))
                || (command.equalsIgnoreCase("View Board"))
                || (command.equalsIgnoreCase("V")));
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
                || (command.equalsIgnoreCase("X")));
    }

    private boolean checkIfBack (String command) {
        return ((command.equalsIgnoreCase("Back"))
                || (command.equalsIgnoreCase("B")));
    }

    public boolean checkIfReturn (String command) {
        return (checkIfBack(command)
                || checkIfEndTurn(command)
                || checkIfExitGame(command));
    }

}
