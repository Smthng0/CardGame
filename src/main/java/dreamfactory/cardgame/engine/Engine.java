package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

public class Engine {
    private Player activePlayer;
    private Player passivePlayer;
    private int turnCounter;
    private Commands commands = new Commands();
    private CommandChecker commandChecker = new CommandChecker();

    public void initializeGame() {
        createPlayers();
        commands.introPrint(activePlayer, passivePlayer);
        startTurn();
    }

    private void createPlayers() {
        commands.printer("Enter first player name: ");
        commands.scanNextCommand();
        activePlayer = new Player(commands.getCommand(), Deck.getConstructedDeck());
        commands.printer("Enter second player name: ");
        commands.scanNextCommand();
        passivePlayer = new Player(commands.getCommand(), Deck.getConstructedDeck());
        passivePlayer.startsSecond();
        turnCounter = 2;
    }

    private void startTurn() {
        commands.incrementManaPool(activePlayer);
        Card card = activePlayer.drawCard();
        commands.startOfTurnPrint(activePlayer, turnCounter, card);

        if (card == null) {
            activePlayer.takeDamage(activePlayer.getDeckDmgCounter());
        }
        if (activePlayer.hasMinions()) {
            activePlayer.resetMinionAttacks();
        }
        if (activePlayer.hasWeapon()) {
            activePlayer.resetAttacks();
        }

        commands.checkStatusPrint(activePlayer, passivePlayer);
        chooseAction();
    }

    private void chooseAction() {
        do {
            commands.availableActionsPrint();
            commands.scanNextCommand();


            if (commandChecker.checkIfPlay(commands.getCommand())) {
                do {
                    commands.playCard(activePlayer, this);
                } while (!commandChecker.checkIfReturn(commands.getCommand()));
            }

            if (commandChecker.checkIfAttack(commands.getCommand())) {
                do {
                    commands.attack(activePlayer, passivePlayer);
                } while (!commandChecker.checkIfReturn(commands.getCommand()));
            }

            if (commandChecker.checkIfCheckStatus(commands.getCommand())) {
                commands.checkStatusPrint(activePlayer,passivePlayer);
            }

            if (commandChecker.checkIfViewBoards(commands.getCommand())) {
                commands.viewBoardsPrint(activePlayer, passivePlayer);
            }

            if (commandChecker.checkIfEndTurn(commands.getCommand())) {
                endTurn();
            }

        } while (!commandChecker.checkIfExitGame(commands.getCommand()));
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

    public Player getFriendlyPlayer() {
        return activePlayer;
    }

    public Player getEnemyPlayer() {
        return passivePlayer;
    }


}
