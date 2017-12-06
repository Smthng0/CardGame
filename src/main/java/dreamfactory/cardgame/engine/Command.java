package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.io.AbilityMinionGenerator;
import dreamfactory.cardgame.io.PlainMinionLoader;
import dreamfactory.cardgame.player.Attackable;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Command {
    private Player activePlayer;
    private Player passivePlayer;
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private int turnCounter;
    private CommandStrings commandStrings = new CommandStrings();
    private Checker checker = new Checker();

    public void createPlayers() {
        Deck deck = getConstructedDeck();
        printer("Enter first player name: ");
        scanNextCommand();
        activePlayer = new Player(command, deck);
        printer("Enter second player name: ");
        scanNextCommand();
        passivePlayer = new Player(command, deck);

        printer(commandStrings.intro(activePlayer, passivePlayer));
        turnCounter = 2;

        startTurn();
    }

    private void chooseAction() {
        do {
            printer(commandStrings.availableActions());
            scanNextCommand();

            if (checker.checkIfPlay(command)) {
                do {
                    playCard();
                } while (!checker.checkIfReturn(command));
            }

            if (checker.checkIfAttack(command)) {
                do {
                    attack();
                } while (!checker.checkIfReturn(command));
            }

            if (checker.checkIfCheckStatus(command)) {
                printer(commandStrings.checkStatus(
                        activePlayer,passivePlayer));
            }

            if (checker.checkIfViewBoards(command)) {
                printer(commandStrings.viewBoards(
                        activePlayer, passivePlayer));
            }

            if (checker.checkIfEndTurn(command)) {
                endTurn();
            }

        } while (!checker.checkIfExitGame(command));
    }

    private void playCard() {
        printer(commandStrings.availableCards(activePlayer));
        scanNextCommand();
        int index;

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return;
        }

        HearthstoneCard card = activePlayer.playCard(index);
        //TODO: igrat se malo s optional...
        printer(commandStrings.cardPlayed(card, activePlayer.getRemainingMana()));
    }

    private void attack() {
        if (!activePlayer.hasMinions()) {
            printer("No minions!" + commandStrings.getSeparator());
            command = "b";
            return;
        }

        int attackingIndex = chooseAttacker();
        if (!validAttackableIndex(attackingIndex)) return;

        Attackable attacker = activePlayer.getMinion(attackingIndex); //TODO: uvalit da moze i player, ne smao minion...

        int defendingIndex = chooseTargetFor(attacker);
        if (!validAttackableIndex(defendingIndex)) return;
        if (attackingPlayerTarget(attacker, defendingIndex)) return;
        attackingMinionTarget(attackingIndex, defendingIndex);
    }

    private int chooseAttacker() {
        int index;
        printer(commandStrings.chooseAttackable(activePlayer));
        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return -1;
        }

        if (!checker.validAttacker(activePlayer.getMinion(index))){
            return -1;
        }

        return index;
    }

    private int chooseTargetFor(Attackable attacker) {
        int index;
        printer(commandStrings.availableTargetsFor(attacker));
        printer(commandStrings.listTargetsOf(passivePlayer));

        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return -1;
        }

        if ((passivePlayer.validIndex(index)) || (index == getPlayerIndex(passivePlayer))) {
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

    private boolean attackingPlayerTarget(Attackable attacker, int defendingIndex) {
        if (defendingIndex == getPlayerIndex(passivePlayer)) {
            return false;
        }

        printer(commandStrings.didDamageTo(attacker, passivePlayer));
        attacker.attack(passivePlayer);

        if (passivePlayer.isDead()) {
            printer(commandStrings.attackableDead(passivePlayer));
            command = "exit";
        }

        return true;
    }

    private void attackingMinionTarget(int attackingIndex, int defendingIndex) {
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

    private void startTurn() {
        activePlayer.setManaPool(activePlayer.getManaPool()+1);
        activePlayer.setRemainingMana(activePlayer.getManaPool());
        HearthstoneCard card = activePlayer.drawCard();
        printer(commandStrings.startOfTurn(activePlayer, turnCounter));
        printer(commandStrings.playerDraws(activePlayer,card));

        if (activePlayer.hasMinions()) {
            activePlayer.resetMinionAttacks();
        }
        if (activePlayer.hasWeapon()) {
            activePlayer.resetAttacks();
        }

        printer(commandStrings.checkStatus(activePlayer, passivePlayer));
        chooseAction();
    }

    private void endTurn() {
        while (activePlayer.isHandFull()) {
            activePlayer.removeCard(0);
        }

        Player tempPlayer = activePlayer;
        activePlayer = passivePlayer;
        passivePlayer = tempPlayer;
        turnCounter++;
        startTurn();
    }

    private void scanNextCommand() {
        command = scanner.nextLine();
    }

    private void printer(String input) {
        System.out.println(input);
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getPassivePlayer() {
        return passivePlayer;
    }

    private int getPlayerIndex(Player player) {
        return player.getNumberOfMinions();
    }

    private Deck getConstructedDeck() {
        List<HearthstoneCard> minionList = new ArrayList<>();
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        minionList.addAll(new AbilityMinionGenerator().createMinions());
        minionList.addAll(new AbilityMinionGenerator().createMinions());

        return new Deck(minionList);
    }

}
