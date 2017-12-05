package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

public class CommandStrings {
    private static final String SPLITTER = "  |  ";
    private static final String SEPARATOR = "\n----------------------------------------\n";

    public String intro(Player player1, Player player2) {
        return ("\n" + "****************************************\n"
                + "\\\\**..  " + player1.getPlayerName()
                + "  vs  " + player2.getPlayerName() + "  ..**//\n")
                + SEPARATOR;
    }

    public String startOfTurn(Player player, int turnCounter){
        return ("It's " + player.getPlayerName() + "'s turn!\n"
                + "Turn number: " + (int)Math.ceil(turnCounter/2)
                + SEPARATOR);
    }

    public String playerDraws(Player player, HearthstoneCard card) {
        return  (player.getPlayerName())
                + (" drew a")
                + (card.getClass().getSimpleName())
                + (":     ")
                + cardToString(card);
    }

    public String availableActions(){
        return "Available actions: " +
                SEPARATOR +
                "(P)lay card" +
                SPLITTER +
                "(A)ttack" +
                SPLITTER +
                "(C)heck status" +
                SPLITTER +
                "(V)iew board" +
                SPLITTER +
                "(E)nd turn" +
                SPLITTER +
                "E(x)it game" +
                SEPARATOR;
    }

    public String availableCards(Player player) {
        StringBuilder builder = new StringBuilder();
        builder.append("\nAvailable cards: \n")
                .append(player.viewHand())
                .append("\nAvailable mana: ")
                .append(player.getRemainingMana())
                .append(SEPARATOR);

        return builder.toString();
    }

    public String cardToString(HearthstoneCard card) {
        StringBuilder builder = new StringBuilder();
        builder.append(card.getTitle())
                .append(", Mana cost: ")
                .append(card.getManaCost());

        if (card instanceof MinionCard) {
            builder.append(", Attack: ")
                    .append(((MinionCard)card).getAttack())
                    .append(", Health: ")
                    .append(((MinionCard) card).getHealth());
        }
        builder.append("\n");
        return builder.toString();

        //TODO: prebacit cardToString na same karte... (weapon ce imat svoj string)
    }

    public String viewBoard(Player friendlyPlayer, Player enemyPlayer) {
        StringBuilder builder = new StringBuilder();
        builder.append("My board: \n\n")
                .append(friendlyPlayer.viewBoard())
                .append(SEPARATOR)
                .append("\nEnemy board: \n\n")
                .append(enemyPlayer.viewBoard());

        return builder.toString();
    }

    public String checkStatus(Player player1, Player player2) {
        StringBuilder builder = new StringBuilder();
        builder.append("\nPlayer ")
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
                .append("\nEnemy player health: ")
                .append(player2.getHealth())
                .append(SPLITTER).append("Enemy mana pool: ")
                .append(player2.getManaPool())
                .append(SPLITTER).append("Enemy hand size: ")
                .append(player2.getNumberOfCards())
                .append(SPLITTER).append("Number of enemy summoned minions: ")
                .append(SPLITTER).append(player2.getNumberOfMinions())
                .append(SEPARATOR);

        return builder.toString();
    }

    public String getSeparator(){
        return SEPARATOR;
    }

    public String availableTargetsFor (Attackable attacker) {
        return "\nAvailable targets for " +
                attacker.getName() +
                " : \n" +
                "( attack: " +
                attacker.getAttack() +
                " , health: " +
                attacker.getHealth() +
                " , remaining attacks: " +
                attacker.getRemainingAttacks() +
                " )" + SEPARATOR;
    }

    public String playerDead() {
        return "\n\nI won!!! <3\n" +
                "\nWooohooo\n" +
                "\n     /(|" +
                "\n    (  :" +
                "\n   __\\  \\  _____" +
                "\n (____)  `|" +
                "\n(____)|   |" +
                "\n (____).__|" +
                "\n  (___)__.|_____\n";
    }

}
