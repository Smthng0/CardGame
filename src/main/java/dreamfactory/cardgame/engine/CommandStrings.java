package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.SpellCard;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

public class CommandStrings {
    private static final String SPLITTER = "  |  ";
    private static final String SEPARATOR = "\n---------------------------------------------\n";
    private static final String RETURN = "(B)ack" + SPLITTER +
            "(E)nd Turn" + SPLITTER + "E(x)it Game" + SEPARATOR;

    public String intro() {
        return "\n'Unnamed CardGame'™ by Frane\n"
                + "Version: 1.02 alpha"
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
                + "  vs  " + player2.getPlayerName() + "  ..**//")
                + SEPARATOR;
    }

    public String startOfTurn(Player player, int turnCounter){
        return ("It's " + player.getPlayerName() + "'s turn!\n"
                + "Turn number: " + (int)Math.ceil(turnCounter/2)
                + SEPARATOR);
    }

    public String playerDraws(Player player, Card card) {
        String className;
        if (card instanceof SpellCard) {
            className = "SpellCard";
        } else {
            className = card.getClass().getSimpleName();
        }

        return  (player.getPlayerName())
                + (" drew a ")
                + className
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
                "(V)iew Hand & Board" +
                SPLITTER +
                "(E)nd Turn" +
                SPLITTER +
                "E(x)it Game" +
                SEPARATOR;
    }

    public String viewPlayableCards(Player player) {
        return "\nPlayable cards:\n" +
                player.viewPlayableCards() +
                "\nAvailable Mana: " +
                player.viewRemainingMana() +
                SEPARATOR + RETURN;

    }

    public String viewHand(Player player) {
        return "\nCards in Hand: \n" +
                player.viewHand() +
                SEPARATOR;
    }

    public String cardPlayedCheck(Card card, Player player) {
        if (card == null) {
            return "Card not played! (no such card, not enough mana or board full!)\n";
        }

        return card.asString() + "--> Played successfully!" + SPLITTER +
                "Remaining mana: " + player.viewRemainingMana() + SEPARATOR;
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
        return String.format("Player %-15s: Health: %2s" + SPLITTER +
                        "Hand Size: %2s" + SPLITTER +
                        "Summoned Minions: %1s" + SPLITTER +
                        "Mana Pool: %8s" + SPLITTER +
                        "Remaining Mana: %s" + SEPARATOR +
                        "Player %-15s: Health: %2s" + SPLITTER +
                        "Hand Size: %2s" + SPLITTER +
                        "Summoned Minions: %1s" + SPLITTER +
                        "Mana Pool: %8s\n",
                player1.getPlayerName(), player1.getHealth(),
                player1.getNumberOfCards(), player1.getNumberOfMinions(),
                player1.viewManaPool(), player1.viewRemainingMana(),
                player2.getPlayerName(), player2.getHealth(),
                player2.getNumberOfCards(), player2.getNumberOfMinions(),
                player2.viewManaPool());
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
