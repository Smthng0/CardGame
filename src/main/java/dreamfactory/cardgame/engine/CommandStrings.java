package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

public class CommandStrings {
    private static final String SPLITTER = "  |  ";
    private static final String SEPARATOR = "\n----------------------------------------\n";
    private static final String RETURN = "(B)ack" + SPLITTER +
            "(E)nd Turn" + SPLITTER + "E(x)it Game" + SEPARATOR;

    public String intro(String version) {
        return "\n'Unnamed CardGame'™ by Frane\n"
                + "Version: " + version
                + "\nUse at your own risk! :)" + SEPARATOR;
        //TODO:version info
    }

    public String chooseGameType(){
        return "\nChoose game type:" + SEPARATOR
                + "(H)otSeat" + SPLITTER
                + "(M)ultiPlayer" + SPLITTER
                + "E(x)it Game"
                + SEPARATOR;
    }

    public String gameStart(Player player1, Player player2) {
        return ("\n" + "****************************************\n"
                + "\\\\**..  " + player1.getPlayerName()
                + "  vs  " + player2.getPlayerName() + "  ..**//\n")
                + SEPARATOR;
    }

    public String startOfTurn(Player player, int turnCounter){
        return ("\nIt's " + player.getPlayerName() + "'s turn!\n"
                + "Turn number: " + (int)Math.ceil(turnCounter/2)
                + SEPARATOR);
    }

    public String playerDraws(Player player, HearthstoneCard card) {
        return  (player.getPlayerName())
                + (" drew a ")
                + (card.getClass().getSimpleName())
                + (" :    ")
                + card.asString();
    }

    public String noMoreCards(Player player) {
        return (player.getPlayerName()
                + "'s deck is empty!\n"
                + player.getPlayerName() + " took "
                + player.getDeckDmgCounter()
                + " damage as a result..." + SPLITTER
                + player.getPlayerName() + "'s remaining health: "
                + player.getHealth()) + "\n";
    }

    public String availableActions(){
        return "Available actions: " +
                SEPARATOR +
                "(P)lay Card" +
                SPLITTER +
                "(A)ttack" +
                SPLITTER +
                "(C)heck Status" +
                SPLITTER +
                "(V)iew Boards" +
                SPLITTER +
                "(E)nd Turn" +
                SPLITTER +
                "E(x)it Game" +
                SEPARATOR;
    }

    public String availableCards(Player player) {
        StringBuilder builder = new StringBuilder();
        builder.append("\nAvailable cards: \n")
                .append(player.viewHand())
                .append("\nAvailable mana: ")
                .append(player.getRemainingMana())
                .append(SEPARATOR)
                .append(RETURN);

        return builder.toString();
    }

    public String cardPlayedCheck(HearthstoneCard card, int mana) {
        if (card == null) {
            return "Card not played! (no such card, not enough mana or board full!)\n";
        }

        return card.getTitle() + " played successfully!\n" +
                "Remaining mana: " + mana + SEPARATOR;
    }

    public String chooseAttackable(Player player) {
        return "\nChoose who will attack: " + SEPARATOR +
                player.viewBoardCanAttack() + SEPARATOR + RETURN;
    }

    public String viewBoards(Player friendlyPlayer, Player enemyPlayer) {
        return "My board: \n\n" +
                friendlyPlayer.viewBoard() +
                SEPARATOR +
                "\nEnemy board: \n\n" +
                enemyPlayer.viewBoard();
    }

    public String checkStatus(Player player1, Player player2) {
        StringBuilder builder = new StringBuilder();
        builder.append("Player ")
                .append(player1.getPlayerName())
                .append("'s status: ")
                .append(SEPARATOR).append("Your health: ")
                .append(player1.getHealth())
                .append(SPLITTER).append("Your mana pool: ")
                .append(player1.getManaPool())
                .append(SPLITTER).append("Your remaining mana: ")
                .append(player1.getRemainingMana())
                .append(SPLITTER).append("Your hand size: ")
                .append(player1.getNumberOfCards())
                .append(SPLITTER).append("Number of summoned minions: ")
                .append(player1.getNumberOfMinions())
                .append(SEPARATOR)
                .append("Enemy player health: ")
                .append(player2.getHealth())
                .append(SPLITTER).append("Enemy mana pool: ")
                .append(player2.getManaPool())
                .append(SPLITTER).append("Enemy hand size: ")
                .append(player2.getNumberOfCards())
                .append(SPLITTER).append("Number of enemy summoned minions: ")
                .append(player2.getNumberOfMinions())
                .append(SEPARATOR);

        return builder.toString();
    }

    public String getSeparator(){
        return SEPARATOR;
    }

    public String availableTargetsFor (Attackable attacker) {
        return "\nAvailable targets for: \n--> " +
                attacker.asString() + SEPARATOR;
    }

    public String listTargetsOf(Player defender) {
        String result = "";

        if (defender.hasTauntMinion()){
            result += "Warning, target has minion with Taunt!!!\n\n" +
                    "Minions with taunt: \n" + defender.viewBoardWithTaunt();
            return result;
        }

        if (defender.hasMinions()){
            result += "Minions: \n" + defender.viewBoard() +"\n";
        }

        result += "Player: \n" + defender.getNumberOfMinions() +
                ". " + defender.getPlayerName() + SEPARATOR + RETURN;

        return result;
    }

    public String invalidIndex() {
        return "Not a valid index for attackable!" +
                SEPARATOR;
    }

    public String notTauntTarget(){
        return "Must target minion with taunt!\n";
    }

    public String didDamageTo(Attackable attacker, Attackable defender){
        return attacker.getName() + " did " +
                attacker.getAttack() + " damage to " +
                defender.getName() + "!" + SPLITTER +
                defender.getName() + "'s remaining health: " +
                defender.getHealth() + "\n";
    }

    public String attackableDead(Attackable attackable) {
        if (attackable instanceof Player){
            return playerDead();
        }

        return  "{}oo((X))ΞΞΞΞΞΞΞΞΞΞΞΞΞ>  " + attackable.getName() +"  @}~}~~~";
    }

    private String playerDead() {
        return "\n\n    Game over!\n" +
                "\n    gg, wp\n" +
                "\n     /(|" +
                "\n    (  :" +
                "\n   __\\  \\  _____" +
                "\n (____)  `|" +
                "\n(____)|   |" +
                "\n (____).__|" +
                "\n  (___)__.|_____\n" +
                "\nPress Enter to exit";
    }


}
