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
    private AttackSequence attackSequence = new AttackSequence();
    private static final int MANA_LIMIT = 8;

    public void chooseGameType() {
        printer(commandStrings.intro()
                + commandStrings.chooseGameType());
    }

    public void introPrint(Player activePlayer, Player passivePlayer){
        printer(commandStrings.gameStart(activePlayer, passivePlayer));
    }

    public void startOfTurnPrint(Player player, int turnCounter){
        printer("\n" + commandStrings.startOfTurn(player, turnCounter));
    }

    public void drawCardPrint(Player player, Card card) {
        if (card == null) {
            printer(commandStrings.noMoreCards(player));
        }
        printer(commandStrings.playerDraws(player, card));
    }

    public void checkStatusPrint(Player activePlayer, Player passivePlayer) {
        printer(commandStrings.checkStatus(activePlayer,passivePlayer));
    }

    public void availableActionsPrint() {
        printer(commandStrings.availableActions());
    }

    public void viewGamePrint(Player activePlayer, Player passivePlayer) {
        printer(commandStrings.viewHand(activePlayer));
        printer(commandStrings.viewBoards(activePlayer, passivePlayer));
    }

    public void incrementManaPool(Player player) {
        if (player.getManaPool() == MANA_LIMIT) {
            return;
        }
        player.setManaPool(player.getManaPool()+1);
        player.setRemainingMana(player.getManaPool());
    }

    public void playCard(Player player) {
        if (!player.hasPlayableCards()) {
            printer("No playable cards!" + commandStrings.getSeparator());
            command = "b";
            return;
        }

        printer(commandStrings.viewPlayableCards(player));
        Card card = chooseCard(player);

        if (new CommandChecker().checkIfReturn(command)) {
            return;
        }

        printer(commandStrings.cardPlayedCheck(card, player));
    }

    protected Card chooseCard(Player player){
        scanNextCommand();
        int index;

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return null;
        }

        return player.playCard(index);
    }

    public void attack(Player activePlayer, Player passivePlayer) {
        if (!activePlayer.hasAttackableMinion()) {
            printer("No Minions that can attack!\n");
            command = "b";
            return;
        }

        attackSequence.startAttack(activePlayer, passivePlayer);
    }

    public boolean attackTarget(Player attackingPlayer, Player defendingPlayer,
                                int attackingIndex, int defendingIndex) {

        if (defendingIndex == getPlayerIndex(defendingPlayer)){
            attackPlayerTarget(attackingPlayer.getMinion(attackingIndex), defendingPlayer);
            return true;
        } else if (defendingPlayer.getMinion(defendingIndex) != null){
            attackSequence.attackMinionTarget(attackingPlayer, defendingPlayer, attackingIndex, defendingIndex);
            return true;
        }
        return false;
    }

    protected void attackPlayerTarget(Attackable attacker, Player defendingPlayer) {
        attacker.attack(defendingPlayer);
        printer(commandStrings.didDamageTo(attacker, defendingPlayer));

        if (defendingPlayer.isDead()) {
            printer(commandStrings.attackableDead(defendingPlayer));
            scanner.nextLine();
            command = "exit";
        }//TODO: attacksequenceclass, ali i da bude drukcije za server...
    }

    public void scanNextCommand() {
        command = scanner.nextLine();
    }

    public static void printer(String input) {
        System.out.println(input);
    }

    private int getPlayerIndex(Player player) {
        return player.getNumberOfMinions();
    }

    public String getCommand() {
        return command;
    }

    private class AttackSequence {
        private void startAttack(Player activePlayer, Player passivePlayer) {
            int attackingIndex = chooseAttacker(activePlayer);
            if (notValidAttackableIndex(attackingIndex)) return;

            Attackable attacker = activePlayer.getMinion(attackingIndex);
            //TODO: uvalit da moze i player, ne smao minion...
            //TODO: refactorat u AttackSequence class negdi vani mozda

            int defendingIndex = chooseTarget(attacker, passivePlayer);
            if (notValidAttackableIndex(defendingIndex)) return;

            if (!tauntTarget(passivePlayer, defendingIndex)) {
                printer(commandStrings.notTauntTarget());
                return;
            }

            attackTarget(activePlayer, passivePlayer, attackingIndex, defendingIndex);
        }

        private boolean notValidAttackableIndex(int index) {
            if (index == -1) {
                printer(commandStrings.invalidIndex());
                return true;
            }
            return false;
        }

        private boolean tauntTarget(Player defendingPlayer, int index) {
            return !defendingPlayer.hasTauntMinion()
                    || index != defendingPlayer.getNumberOfMinions()
                    && defendingPlayer.getMinion(index).hasAbilities()
                    && defendingPlayer.getMinion(index).hasAbility(Ability.TAUNT);
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

        private boolean validAttacker (Attackable attacker) {
            return (attacker != null && attacker.canAttack());
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
    }

}
