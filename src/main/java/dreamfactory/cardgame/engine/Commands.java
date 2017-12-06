package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

import java.util.Scanner;

public class Commands {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private CommandStrings commandStrings = new CommandStrings();

    public void introPrint(Player activePlayer, Player passivePlayer){
        printer(commandStrings.intro(activePlayer, passivePlayer));
    }

    public void startOfTurnPrint(Player player, int turnCounter, HearthstoneCard card){
        printer(commandStrings.startOfTurn(player, turnCounter));
        printer(commandStrings.playerDraws(player, card));
    }

    public void checkStatusPrint(Player activePlayer, Player passivePlayer) {
        printer(commandStrings.checkStatus(activePlayer,passivePlayer));
    }

    public void availableActionsPrint() {
        printer(commandStrings.availableActions());
    }

    public void viewBoardsPrint(Player activePlayer, Player passivePlayer) {
        printer(commandStrings.viewBoards(activePlayer, passivePlayer));
    }

    public void playCard(Player player) {
        printer(commandStrings.availableCards(player));
        scanNextCommand();
        int index;

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return;
        }

        HearthstoneCard card = player.playCard(index);
        //TODO: igrat se malo s optional...
        printer(commandStrings.cardPlayed(card, player.getRemainingMana()));
    }

    public void attack(Player activePlayer, Player passivePlayer) {
        if (!activePlayer.hasMinions()) {
            printer("No minions!" + commandStrings.getSeparator());
            command = "b";
            return;
        }

        int attackingIndex = chooseAttacker(activePlayer);
        if (!validAttackableIndex(attackingIndex)) return;

        Attackable attacker = activePlayer.getMinion(attackingIndex);
        //TODO: uvalit da moze i player, ne smao minion...

        int defendingIndex = chooseTargetFor(attacker, passivePlayer);
        if (!validAttackableIndex(defendingIndex)) return;
        if (attackingPlayerTarget(attacker, defendingIndex, passivePlayer)) return;
        attackingMinionTarget(activePlayer, passivePlayer, attackingIndex, defendingIndex);
    }

    private int chooseAttacker(Player activePlayer) {
        int index;
        printer(commandStrings.chooseAttackable(activePlayer));
        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return -1;
        }

        if (validAttacker(activePlayer.getMinion(index))){
            return -1;
        }

        return index;
    }

    private int chooseTargetFor(Attackable attacker, Player defendingPlayer) {
        int index;
        printer(commandStrings.availableTargetsFor(attacker));
        printer(commandStrings.listTargetsOf(defendingPlayer));

        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return -1;
        }

        if ((defendingPlayer.validIndex(index)) || (index == getPlayerIndex(defendingPlayer))) {
            return index;
        }

        return -1;
    }

    private boolean validAttackableIndex (int index) {
        if (index != -1) {
            printer(commandStrings.invalidIndex());
            return false;
        }
        return true;
    }

    private boolean validAttacker (Attackable attacker) {
        return (attacker != null && attacker.canAttack());
    }

    private boolean attackingPlayerTarget(Attackable attacker, int defendingIndex, Player defendingPlayer) {
        if (defendingIndex == getPlayerIndex(defendingPlayer)) {
            return false;
        }

        printer(commandStrings.didDamageTo(attacker, defendingPlayer));
        attacker.attack(defendingPlayer);

        if (defendingPlayer.isDead()) {
            printer(commandStrings.attackableDead(defendingPlayer));
            command = "exit";
        }

        return true;
    }

    private void attackingMinionTarget(Player activePlayer, Player passivePlayer,
                                       int attackingIndex, int defendingIndex) {
        if (passivePlayer.getMinion(defendingIndex) == null) {
            return;
        }

        Attackable attacker = activePlayer.getMinion(attackingIndex);
        MinionCard defendingMinion = passivePlayer.getMinion(defendingIndex);
        attacker.attack(defendingMinion);

        printer(commandStrings.didDamageTo(attacker, defendingMinion));
        printer(commandStrings.didDamageTo(defendingMinion, attacker));

        if (attacker.isDead()) {
            activePlayer.killMinion(attackingIndex);
            printer(commandStrings.attackableDead(attacker));
        }

        if (defendingMinion.isDead()) {
            passivePlayer.killMinion(defendingIndex);
            printer(commandStrings.attackableDead(defendingMinion));
        }
    }

    public void scanNextCommand() {
        command = scanner.nextLine();
    }

    public void printer(String input) {
        System.out.println(input);
    }

    private int getPlayerIndex(Player player) {
        return player.getNumberOfMinions();
    }

    public String getCommand() {
        return command;
    }
}
