package dreamfactory.cardgame.engine;

public class Checker {

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

    public boolean checkIfServer(String command) {
        return command.equals("StarT ServeR");
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
        return ((command.equalsIgnoreCase("View boards"))
                || (command.equalsIgnoreCase("View"))
                || (command.equalsIgnoreCase("V"))
                || (command.equalsIgnoreCase("Boards")));
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
