package card.game;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;
import card.game.io.GenericMinionReader;
import card.game.io.MinionsWithAbilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Engine {
    private Player activePlayer;
    private Player passivePlayer;
    private Scanner scanner = new Scanner(System.in);
    private String command;
    private int turnCounter;

    private void createPlayers() {
        System.out.println("Enter first player name: ");
        command = scanner.nextLine();
        Deck deck1 = createConstructedDeck();
        activePlayer = new Player(command, deck1, true);
        System.out.println("Enter second player name: ");
        command = scanner.nextLine();
        Deck deck2 = createConstructedDeck();
        passivePlayer = new Player(command, deck2, false);

        System.out.println();
        System.out.println("****************************************");
        System.out.println("\\\\**..  " + activePlayer.getPlayerName()
                + "  vs  " + passivePlayer.getPlayerName() + "  ..**//");
        separator();
        turnCounter = 2;
    }

    private void startOfTurn() {
        System.out.println("It's " + activePlayer.getPlayerName() + "'s turn!");
        System.out.println("Turn number: " + (int)Math.ceil(turnCounter/2));
        startTurn();
    }

    private void availableActions(){
        System.out.println("Available actions: ");
        separator();
        System.out.print("(P)lay card");
        System.out.print("  |  ");
        System.out.print("(A)ttack");
        System.out.print("  |  ");
        System.out.print("(C)heck status");
        System.out.print("  |  ");
        System.out.print("(V)iew board");
        System.out.print("  |  ");
        System.out.print("(E)nd turn");
        System.out.print("  |  ");
        System.out.println("E(x)it game");
        separator();
    }

    private void chooseAction() {
        do {
            availableActions();

            command = scanner.nextLine();
            System.out.println();
            if ((command.equalsIgnoreCase("Play card"))
                    || (command.equalsIgnoreCase("Play"))
                    || (command.equalsIgnoreCase("P"))
                    || (command.equalsIgnoreCase("card"))) {
                playCard();
            }

            if ((command.equalsIgnoreCase("Attack"))
                    || (command.equalsIgnoreCase("A"))) {
                attack();
            }

            if ((command.equalsIgnoreCase("Check status"))
                    || (command.equalsIgnoreCase("status"))
                    || (command.equalsIgnoreCase("c"))
                    || (command.equalsIgnoreCase("check"))) {
                checkStatus();
            }

            if ((command.equalsIgnoreCase("View board"))
                    || (command.equalsIgnoreCase("view"))
                    || (command.equalsIgnoreCase("v"))
                    || (command.equalsIgnoreCase("board"))) {
                viewBoard();
            }

        } while ((!command.equalsIgnoreCase("End turn"))
                && (!command.equalsIgnoreCase("End"))
                && (!command.equalsIgnoreCase("e"))
                && (!command.equalsIgnoreCase("Exit"))
                && (!command.equalsIgnoreCase("x"))
                && (!command.equalsIgnoreCase("Exit game")));

        endTurn();
    }

    private void playCard() {
        System.out.println();
        System.out.println("Available cards:  ");
        activePlayer.viewHand();
        System.out.println("Available mana:  " + activePlayer.getRemainingMana());
        separator();
        command = scanner.nextLine();
        int index;

        try {
            index = Integer.parseInt(command);
        } catch (Exception ex) {
            return;
        }

        if (index < activePlayer.getNumberOfCards()
                && (index >= 0)) {
            command = activePlayer.getCard(index).getTitle();
            int mana = activePlayer.playCard(index);

            if (mana == -1) {
                System.out.println("Card not played! (no such card or not enough mana) ");
            } else {
                if (command.equalsIgnoreCase("The Coin")) {
                    activePlayer.setRemainingMana(activePlayer.getRemainingMana() + 1);
                }

                activePlayer.setRemainingMana(activePlayer.getRemainingMana() - mana);
                System.out.println("Card " + command + " played successfully!");
                System.out.println(command + " costs " + mana + " mana.");
                System.out.println("Remaining mana: " + activePlayer.getRemainingMana());
            }

        } else {
            System.out.println("Card not played! (no such card or not enough mana) ");
        }

        System.out.println();
        separator();
    }

    private void attack() {
        if (activePlayer.getBoard().getAllMinions() == null) {
            System.out.println();
            System.out.println("No minions! ");
            separator();
        } else {
            System.out.println("Choose who will attack: ");
            separator();
            activePlayer.viewBoard();
            int index;
            command = scanner.nextLine();
            System.out.println();

            try {
                index = Integer.parseInt(command);
            } catch (Exception ex) {
                return;
            }

            if ((activePlayer.getBoard().getNumberOfMinions() > index)
                    && (index >= 0)) {
                if (activePlayer.getMinion(index).getRemainingAttacks() > 0) {
                    MinionCard attackingMinion = activePlayer.getMinion(index);
                    int attackingIndex = index;

                    System.out.println("Available targets for "
                            + activePlayer.getPlayerName() + "'s "
                            + attackingMinion.getTitle() + " : ");

                    System.out.println("( attack : " + attackingMinion.getAttack()
                            + " , health: " + attackingMinion.getHealth()
                            + " , remaining attacks: "
                            + attackingMinion.getRemainingAttacks() + " ) ");

                    separator();

                    if (passivePlayer.getBoard().getNumberOfMinions() > 0){
                        System.out.println("Minions: ");
                        passivePlayer.viewBoard();
                    }

                    System.out.println("Player: ");
                    System.out.println((passivePlayer.getBoard().getNumberOfMinions())
                            + ".  " + passivePlayer.getPlayerName());

                    command = scanner.nextLine();
                    System.out.println();

                    try {
                        index = Integer.parseInt(command);
                    } catch (Exception ex) {
                        return;
                    }

                    if (index == (passivePlayer.getBoard().getNumberOfMinions())) {
                        System.out.println(attackingMinion.getTitle() + " did "
                                + attackingMinion.getAttack() + " damage to "
                                + passivePlayer.getPlayerName() + "!");

                        attackingMinion.attack(passivePlayer);

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
                        attackingMinion.attack(defendingMinion);

                        System.out.println(activePlayer.getPlayerName() + "'s "
                                + attackingMinion.getTitle() + " did "
                                + attackingMinion.getAttack() + " damage to "
                                + passivePlayer.getPlayerName() + "'s "
                                + defendingMinion.getTitle() + "!  |  "
                                + defendingMinion.getTitle()
                                + "'s remaining health: "
                                + defendingMinion.getHealth());

                        System.out.println(passivePlayer.getPlayerName() + "'s "
                                + defendingMinion.getTitle() + " did "
                                + defendingMinion.getAttack() + " damage to "
                                + activePlayer.getPlayerName() + "'s "
                                + attackingMinion.getTitle() + "!  |  "
                                + attackingMinion.getTitle()
                                + "'s remaining health: "
                                + attackingMinion.getHealth());

                        if (defendingMinion.isDead()){
                            passivePlayer.goToGraveyard(index);
                        }

                        if (attackingMinion.isDead()){
                            activePlayer.goToGraveyard(attackingIndex);
                        }
                    }

                } else {
                    System.out.println("Minion did not attack (no remaining attacks)...");
                }
                System.out.println();
            } else {
                System.out.println();
                System.out.println("No such minion!");
                separator();
            }

            System.out.println();
            separator();
        }
    }

    private void checkStatus() {
        System.out.println("Player " + activePlayer.getPlayerName() + "'s status: ");
        separator();
        System.out.print("Your health: ");
        System.out.print(activePlayer.getHealth());
        System.out.print("  |  Your mana pool: ");
        System.out.print(activePlayer.getManaPool());
        System.out.print("  |  Your remaining mana: ");
        System.out.print(activePlayer.getRemainingMana());
        System.out.print("  |  Your hand size: ");
        System.out.print(activePlayer.getNumberOfCards());
        System.out.print("  |  Number of active minions: ");
        System.out.println(activePlayer.getBoard().getNumberOfMinions());
        System.out.print("Enemy health: ");
        System.out.print(passivePlayer.getHealth());
        System.out.print("  |  Enemy mana pool: ");
        System.out.print(passivePlayer.getManaPool());
        System.out.print("  |  Enemy hand size: ");
        System.out.print(passivePlayer.getNumberOfCards());
        System.out.print("  |  Number of active enemy minions: ");
        System.out.println(passivePlayer.getBoard().getNumberOfMinions());
        separator();
    }

    private void viewBoard() {
        System.out.println("My board: ");
        System.out.println();
        activePlayer.viewBoard();
        separator();
        System.out.println("Enemy board: ");
        System.out.println();
        passivePlayer.viewBoard();
        System.out.println();
    }

    private void startTurn() {
        activePlayer.setManaPool(activePlayer.getManaPool()+1);
        activePlayer.setRemainingMana(activePlayer.getManaPool());
        activePlayer.setRemainingAttacks(activePlayer.getMaxAttacks());
        HearthstoneCard card = activePlayer.drawCard();
        separator();

        System.out.print(activePlayer.getPlayerName() + " drew a "
                + card.getClass().getSimpleName() + ":     "
                + card.getTitle() + ", Mana cost: "
                + card.getManaCost());

        if (card instanceof MinionCard) {
            System.out.print(", Attack: " + ((MinionCard) card).getAttack()
                    + ", Health: " + ((MinionCard) card).getHealth());
        }
        System.out.println();

        if (activePlayer.getBoard().getAllMinions() != null) {
            for (MinionCard minion : activePlayer.getBoard().getAllMinions()) {
                minion.resetAttacks();
            }
        }
    }

    private void endTurn() {
        if (activePlayer.fullHand()) {
            while (activePlayer.getNumberOfCards()
                    > activePlayer.getCardLimit()) {
                activePlayer.discardCard(0);
            }
        }

        Player tempPlayer = activePlayer;
        activePlayer = passivePlayer;
        passivePlayer = tempPlayer;
        turnCounter++;
    }

    public Attackable getEnemyMinion(String title) {
        for (MinionCard minion : passivePlayer.getBoard().getAllMinions()) {
            if (minion.checkForAbility("Taunt")) {
                return minion;
            }
        }

        return passivePlayer.getBoard().getMinion(title);
    }

    public Attackable getFriendlyMinion(String title) {
        return activePlayer.getBoard().getMinion(title);
    }

    public Player getFriendlyPlayer() {
        return activePlayer;
    }

    public Player getEnemyPlayer() {
        return passivePlayer;
    }

    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    private void separator() {
        System.out.println("----------------------------------------");
        System.out.println();
    }

    public Deck generateDeck() {
        List<HearthstoneCard> arrayDeck = new ArrayList<>();
        Random random = new Random ();
        System.out.println("Enter first players minion names: ");
        String minionName = scanner.nextLine();

        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(5);
            int y = random.nextInt(4);
            arrayDeck.add(
                    new MinionCard((minionName+(i+1)),
                            x+y,
                            x+x+y,
                            x+y+y+1
                    )
            );
        }

        return new Deck (arrayDeck);
    }

    private Deck createConstructedDeck() {
        List<HearthstoneCard> minionList = new ArrayList<>();
        minionList.addAll(new GenericMinionReader().createMinionListFromCSV());
        minionList.addAll(new GenericMinionReader().createMinionListFromCSV());
        minionList.addAll(new MinionsWithAbilities().createMinions());
        minionList.addAll(new MinionsWithAbilities().createMinions());

        return new Deck(minionList);
    }

    private void printAbilities(List<Ability> list) {
        for (Ability ability : list) {
            System.out.print(", " + ability.toString());
        }
    }


}
