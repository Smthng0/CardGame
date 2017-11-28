package card.game;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Board {
    private int minionLimit = 7;
    private List<MinionCard> backingBoard = new ArrayList<>();
    private int numberOfMinions = 0;
    private List<HearthstoneCard> graveyard = new ArrayList<>();

    public void summonMinion(MinionCard card) {
        if (isFull()){
            System.out.println("Board is full");
        } else {
            backingBoard.add(card);
            numberOfMinions = backingBoard.size();
        }
    }

    public void addToGraveyard(HearthstoneCard card){
        graveyard.add(card);
    }

    public MinionCard getMinion(HearthstoneCard card){
        for (MinionCard minion : backingBoard) {
            if (minion.getTitle().equalsIgnoreCase(card.getTitle())) {
                return minion;
            }
        }
        return null;
    }

    public MinionCard getMinion(String title) {
        for (MinionCard minion : backingBoard) {
            if (minion.getTitle().equalsIgnoreCase(title)) {
                return minion;
            }
        }
        return null;
    }

    public MinionCard getMinion(int index) {
        if (backingBoard.size() > index) {
           return backingBoard.get(index);
        }

        return null;
    }


    public void removeMinion(String title) {
        if (getMinion(title) != null) {
            backingBoard.remove(getMinion(title));
            numberOfMinions = backingBoard.size();
        }
    }

    public void removeMinion(int index) {
        if (backingBoard.get(index) != null) {
            backingBoard.remove(index);
            numberOfMinions = backingBoard.size();
        }
    }

    public List<MinionCard> getAllMinions() {
        if (isEmpty()){
            return null;
        }

        return backingBoard;
    }

    public void viewBoard() {
        if  (isEmpty()) {
            System.out.println("Board empty!");
        } else {
            sortByRemainingAttack();
            for (int index = 0; index < getAllMinions().size(); index++) {
                MinionCard minion = backingBoard.get(index);
                System.out.print(index + ". "
                        + minion.getTitle()
                        + ", Attack: " + minion.getAttack()
                        + ", Health: " + minion.getHealth());

                System.out.println(", Remaining attacks: "
                        + minion.getRemainingAttacks());
            }
            System.out.println();
        }
    }

    private void sortByRemainingAttack() {
        backingBoard.sort(Comparator.comparing(MinionCard::getRemainingAttacks));
        Collections.reverse(backingBoard);
    }

    private boolean isFull() {
        return numberOfMinions == minionLimit;
    }

    private boolean isEmpty() {
        return numberOfMinions == 0;
    }

    public int getNumberOfMinions() {
        return numberOfMinions;
    }

    public List<HearthstoneCard> getGraveyard() {
        return graveyard;
    }
}
