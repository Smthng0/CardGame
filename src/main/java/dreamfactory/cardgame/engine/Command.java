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

    public void chooseAction() {
        do {
            printer(commandStrings.availableActions());
            scanNextCommand();

            if (checker.checkIfPlay(command)) {
                playCard();
            }

            if (checker.checkIfAttack(command)) {
                attack();
            }

            if (checker.checkIfCheckStatus(command)) {
                printer(commandStrings.checkStatus(
                        activePlayer,passivePlayer));
            }

            if (checker.checkIfViewBoard(command)) {
                printer(commandStrings.viewBoard(
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

        if (activePlayer.playCard(index)) {
            printer("Card played successfully!\n");
            printer("Remaining mana: " + activePlayer.getRemainingMana());
        } else {
            printer("Card not played! (no such card or not enough mana) ");
        }

        printer(commandStrings.getSeparator());
    }

    private void attack() {
        if (!activePlayer.hasMinions()) {
            printer("No minions!" + commandStrings.getSeparator());
            return;
        }

        printer("Choose who will attack: " + commandStrings.getSeparator());
        activePlayer.viewBoard(); //TODO: stavit da se vidu samo sa remaining attacks
        int index;
        scanNextCommand();

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return;
        }

        if ((activePlayer.getNumberOfMinions() > index)
                && (index >= 0)) {
            if (activePlayer.getMinion(index).getRemainingAttacks() > 0) {
                Attackable attacker = activePlayer.getMinion(index); //TODO: uvalit da moze i player, ne smao minion...
                int attackingIndex = index;

                printer(commandStrings.availableTargetsFor(attacker));

                if (passivePlayer.hasMinions()){
                    System.out.println("Minions: ");
                    passivePlayer.viewBoard();
                }

                System.out.println("Player: ");
                System.out.println((passivePlayer.getNumberOfMinions())
                        + ".  " + passivePlayer.getPlayerName());

                command = scanner.nextLine();
                System.out.println();

                try {
                    index = Integer.parseInt(command);
                } catch (Exception ex) {
                    return;
                }

                if (index == (passivePlayer.getNumberOfMinions())) {
                    System.out.println(attacker.getName() + " did "
                            + attacker.getAttack() + " damage to "
                            + passivePlayer.getPlayerName() + "!");

                    attacker.attack(passivePlayer);

                    if (passivePlayer.isDead()) {
                        System.out.println("");
                        System.out.println("Press Enter to exit");
                        scanner.nextLine();
                        command = "exit";
                    } else {
                        System.out.println(passivePlayer.getPlayerName()
                                + "'s remaining health: " + passivePlayer.getHealth());
                    }
                }

                if (passivePlayer.getMinion(index) != null) {
                    MinionCard defendingMinion = passivePlayer.getMinion(index);
                    attacker.attack(defendingMinion);

                    System.out.println(activePlayer.getPlayerName() + "'s "
                            + attacker.getName() + " did "
                            + attacker.getAttack() + " damage to "
                            + passivePlayer.getPlayerName() + "'s "
                            + defendingMinion.getTitle() + "!  |  "
                            + defendingMinion.getTitle()
                            + "'s remaining health: "
                            + defendingMinion.getHealth());

                    System.out.println(passivePlayer.getPlayerName() + "'s "
                            + defendingMinion.getTitle() + " did "
                            + defendingMinion.getAttack() + " damage to "
                            + activePlayer.getPlayerName() + "'s "
                            + attacker.getName() + "!  |  "
                            + attacker.getName()
                            + "'s remaining health: "
                            + attacker.getHealth());

                    if (defendingMinion.isDead()){
                        passivePlayer.killMinion(index);
                    }

                    if (attacker.isDead()){
                        activePlayer.killMinion(attackingIndex);
                    }
                }

            } else {
                System.out.println("Minion did not attack (no remaining attacks)...");
            }
            System.out.println();
        } else {
            System.out.println();
            System.out.println("No such minion!");
            printer(commandStrings.getSeparator());
        }
        System.out.println();
        printer(commandStrings.getSeparator());
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

    private Deck getConstructedDeck() {
        List<HearthstoneCard> minionList = new ArrayList<>();
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        minionList.addAll(new AbilityMinionGenerator().createMinions());
        minionList.addAll(new AbilityMinionGenerator().createMinions());

        return new Deck(minionList);
    }


}
