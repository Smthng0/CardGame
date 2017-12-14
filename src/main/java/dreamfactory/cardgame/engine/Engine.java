package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.player.Players;
import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

public class Engine {
    protected Player activePlayer;
    protected Player passivePlayer;
    protected int turnCounter;
    protected Commands commands = new Commands();
    protected CommandChecker commandChecker = new CommandChecker();

    public void initializeGame(Players players, String host) {
        createPlayers();
        commands.introPrint(activePlayer, passivePlayer);
        startTurn();
    }

    private void createPlayers() {
        Commands.printer("Enter first player name (to 15 chars):");
        commands.scanNextCommand();
        activePlayer = new Player(commands.getCommand(), Deck.getConstructedDeck());
        Commands.printer("Enter second player name (to 15 chars):");
        commands.scanNextCommand();
        passivePlayer = new Player(commands.getCommand(), Deck.getConstructedDeck());
        turnCounter = 2;
    }

    protected void startTurn() {
        commands.startOfTurnPrint(activePlayer, turnCounter);
        Card card = startTurnSequence();
        commands.drawCardPrint(activePlayer, card);
        chooseAction();
    }

    public Card startTurnSequence() {
        commands.incrementManaPool(activePlayer);
        Card card = activePlayer.drawCard();

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
        return card;
    }

    private void chooseAction() {
        do {
            commands.availableActionsPrint();
            commands.scanNextCommand();


            if (commandChecker.checkIfPlay(commands.getCommand())) {
                do {
                    commands.playCard(activePlayer);
                } while (!commandChecker.checkIfReturn(commands.getCommand()));
            }

            if (commandChecker.checkIfAttack(commands.getCommand())) {
                do {
                    commands.attack(activePlayer, passivePlayer);
                } while (!commandChecker.checkIfReturn(commands.getCommand()));
            }

            if (commandChecker.checkIfCheckStatus(commands.getCommand())) {
                commands.checkStatusPrint(activePlayer, passivePlayer);
            }

            if (commandChecker.checkIfViewBoards(commands.getCommand())) {
                commands.viewGamePrint(activePlayer, passivePlayer);
            }

            if (commandChecker.checkIfEndTurn(commands.getCommand())) {
                endTurn();
            }

        } while (!commandChecker.checkIfExitGame(commands.getCommand()));
    }

    public void endTurn() {
        endTurnSequence();
        startTurn();
    }

    public void endTurnSequence() {
        while (activePlayer.isHandFull()) {
            activePlayer.removeCard(0);
        }

        Player tempPlayer = activePlayer;
        activePlayer = passivePlayer;
        passivePlayer = tempPlayer;
        turnCounter++;
    }

}
