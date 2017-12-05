package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.MinionCard;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<MinionCard> backingBoard = new ArrayList<>();
    private static final int MINION_LIMIT = 7;

    public void summonMinion(MinionCard card) {
        if (isFull()){
            System.out.println("Board is full");
        } else {
            backingBoard.add(card);
        }
    }

    public MinionCard getMinion(int index) {
        if (validIndex(index)) {
           return backingBoard.get(index);
        }

        return null;
    }

    public void removeMinion(int index) {
        if (validIndex(index)) {
            backingBoard.remove(index);
        }
    }

    public void resetAttacks() {
        for (MinionCard minion : backingBoard) {
            minion.resetAttacks();
        }
    }

    public void printBoard() {
        for (MinionCard minion : backingBoard) {
            //return string....
            //call the "print minion" method from minionCard
        }
    }

    private boolean validIndex(int index) {
        return ((index < getNumberOfMinions()
                && (index >= 0)));
    }

    public int getNumberOfMinions() {
        return backingBoard.size();
    }

    public boolean isFull() {
        return getNumberOfMinions() == MINION_LIMIT;
    }

    public boolean isEmpty() {
        return backingBoard.isEmpty();
    }



}