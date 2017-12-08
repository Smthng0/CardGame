package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Player;

import java.util.Scanner;

public class Commands {
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private CommandStrings commandStrings = new CommandStrings();

    public void chooseGameType() {
        printer(commandStrings.intro("1.0 alpha")
                + commandStrings.chooseGameType());
    }

    public void introPrint(Player activePlayer, Player passivePlayer){
        printer(commandStrings.gameStart(activePlayer, passivePlayer));
    }

    public void startOfTurnPrint(Player player, int turnCounter, Card card){
        printer(commandStrings.startOfTurn(player, turnCounter));
        if (card == null) {
            printer(commandStrings.noMoreCards(player));
            return;
        }
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

    public void incrementManaPool(Player player) {
        player.setManaPool(player.getManaPool()+1);
        player.setRemainingMana(player.getManaPool());
    }

    public void playCard(Player player, Engine engine) {
        printer(commandStrings.availableCards(player));
        Card card = chooseCard(player, engine);
        //TODO: igrat se malo s optional...
        if (new CommandChecker().checkIfReturn(command)) {
            return;
        }
        printer(commandStrings.cardPlayedCheck(card, player.getRemainingMana()));
    }

    private Card chooseCard(Player player, Engine engine){
        scanNextCommand();
        int index;

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return null;
        }

        return player.playCard(index, engine);
    }

    public void attack(Player activePlayer, Player passivePlayer) {
        if (!activePlayer.hasMinions()) {
            printer("No minions!" + commandStrings.getSeparator());
            command = "b";
            return;
        }

        int attackingIndex = chooseAttacker(activePlayer);
        if (notValidAttackableIndex(attackingIndex)) return;

        Attackable attacker = activePlayer.getMinion(attackingIndex);
        //TODO: uvalit da moze i player, ne smao minion...

        int defendingIndex = chooseTarget(attacker, passivePlayer);
        if (notValidAttackableIndex(defendingIndex)) return;
        if (!tauntTarget(passivePlayer, defendingIndex)) {
            printer(commandStrings.notTauntTarget());
            return;
        }

        attackTarget(activePlayer, passivePlayer, attackingIndex, defendingIndex);
    }

    private int chooseAttacker(Player player) {
        int index;
        printer(commandStrings.chooseAttackable(player));
        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return -1;
        }

        if (!validAttacker(player.getMinion(index))){
            return -1;
        }

        return index;
    }

    private int chooseTarget(Attackable attacker, Player defendingPlayer) {
        int index;
        printer(commandStrings.availableTargetsFor(attacker));
        printer(commandStrings.listTargetsOf(defendingPlayer));
        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return -1;
        }

        if (!((defendingPlayer.getMinion(index)!= null)
                || (index == getPlayerIndex(defendingPlayer))) ) {
            return -1;
        }

        return index;
    }

    private boolean tauntTarget(Player defendingPlayer, int index) {
        if (!defendingPlayer.hasTauntMinion()) {
            return true;
        } else if (index == getPlayerIndex(defendingPlayer)){
            return false;
        } else if (!defendingPlayer.getMinion(index).hasAbilities()) {
            return false;
        } else if (!defendingPlayer.getMinion(index).hasAbility(Ability.TAUNT)) {
            return false;
        }
        return true;
    }

    private boolean notValidAttackableIndex(int index) {
        if (index == -1) {
            printer(commandStrings.invalidIndex());
            return false;
        }
        return true;
    }

    private boolean validAttacker (Attackable attacker) {
        return (attacker != null && attacker.canAttack());
    }

    private void attackTarget(Player attackingPlayer, Player defendingPlayer,
                              int attackingIndex, int defendingIndex) {

        if (defendingIndex == getPlayerIndex(defendingPlayer)){
            attackPlayerTarget(attackingPlayer.getMinion(attackingIndex), defendingPlayer);
        } else if (defendingPlayer.getMinion(defendingIndex) != null){
            attackMinionTarget(attackingPlayer, defendingPlayer, attackingIndex, defendingIndex);
        }
    }

    private void attackPlayerTarget(Attackable attacker, Player defendingPlayer) {
        attacker.attack(defendingPlayer);
        printer(commandStrings.didDamageTo(attacker, defendingPlayer));

        if (defendingPlayer.isDead()) {
            printer(commandStrings.attackableDead(defendingPlayer));
            scanner.nextLine();
            command = "exit";
        }
    }

    private void attackMinionTarget(Player activePlayer, Player passivePlayer,
                                    int attackingIndex, int defendingIndex) {

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

    public String printer(String input) {
        System.out.println(input);
        return input;
    }

    private int getPlayerIndex(Player player) {
        return player.getNumberOfMinions();
    }

    public String getCommand() {
        return command;
    }
}
