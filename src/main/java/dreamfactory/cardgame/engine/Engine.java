package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.io.AbilityMinionGenerator;
import dreamfactory.cardgame.io.PlainMinionLoader;
import dreamfactory.cardgame.multiplayer.Server.ServerApplication;
import dreamfactory.cardgame.player.Deck;
import dreamfactory.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private Player activePlayer;
    private Player passivePlayer;
    private int turnCounter;
    private Commands commands = new Commands();
    private Checker checker = new Checker();

    public void initializeGame() throws Exception {
        do {
            commands.chooseGameType();
            commands.scanNextCommand();
            if (checker.checkIfHotSeat(commands.getCommand())) {
                hotSeat();
            }
            if (checker.checkIfMultiplayer(commands.getCommand())) {
                multiPlayer();
            }
            if (checker.checkIfServer(commands.getCommand())) {
                startServer();
            }
        } while (!checker.checkIfExitGame(commands.getCommand()));
    }

    private void hotSeat() {
        createPlayers();
        commands.introPrint(activePlayer, passivePlayer);
        startTurn();
    }

    private void multiPlayer() {
        commands.printer("Starting MultiPlayer Session...");
        System.exit(0);
    }

    private void startServer() throws Exception{
        commands.printer("Starting Server...");
        new ServerApplication().run();
    }

    private void createPlayers() {
        commands.printer("Enter first player name: ");
        commands.scanNextCommand();
        activePlayer = new Player(commands.getCommand(), getConstructedDeck());
        commands.printer("Enter second player name: ");
        commands.scanNextCommand();
        passivePlayer = new Player(commands.getCommand(), getConstructedDeck());
        passivePlayer.startsSecond();
        turnCounter = 2;
    }

    public void createMultiPlayers(String player1, String player2) {
        activePlayer = new Player(player1, getConstructedDeck());
        passivePlayer = new Player(player2, getConstructedDeck());
        passivePlayer.startsSecond();
        turnCounter = 2;
    }

    private void startTurn() {
        incrementManaPool();
        HearthstoneCard card = activePlayer.drawCard();
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

    private void incrementManaPool() {
        activePlayer.setManaPool(activePlayer.getManaPool()+1);
        activePlayer.setRemainingMana(activePlayer.getManaPool());
    }

    private void chooseAction() {
        do {
            commands.availableActionsPrint();
            commands.scanNextCommand();


            if (checker.checkIfPlay(commands.getCommand())) {
                do {
                    commands.playCard(activePlayer, this);
                } while (!checker.checkIfReturn(commands.getCommand()));
            }

            if (checker.checkIfAttack(commands.getCommand())) {
                do {
                    commands.attack(activePlayer, passivePlayer);
                } while (!checker.checkIfReturn(commands.getCommand()));
            }

            if (checker.checkIfCheckStatus(commands.getCommand())) {
                commands.checkStatusPrint(activePlayer,passivePlayer);
            }

            if (checker.checkIfViewBoards(commands.getCommand())) {
                commands.viewBoardsPrint(activePlayer, passivePlayer);
            }

            if (checker.checkIfEndTurn(commands.getCommand())) {
                endTurn();
            }

        } while (!checker.checkIfExitGame(commands.getCommand()));
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

    private static Deck getConstructedDeck() {
        List<HearthstoneCard> minionList = new ArrayList<>();
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        minionList.addAll(new PlainMinionLoader().loadMinionsFromCSV());
        minionList.addAll(new AbilityMinionGenerator().createMinions());
        minionList.addAll(new AbilityMinionGenerator().createMinions());

        return new Deck(minionList);
    }

}
