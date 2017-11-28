package card.game;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;

import java.util.ArrayList;
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
            for (MinionCard minion : getAllMinions()) {
                System.out.println(backingBoard.indexOf(minion) + ". "
                        + minion.getTitle()
                        + ", Attack: " + minion.getAttack()
                        + ", Health: " + minion.getHealth()
                        + ", Remaining attacks: " + minion.getRemainingAttacks());

            }
            System.out.println();
        }
    }

    public void returnToHand(HearthstoneCard card){
        Engine.getFriendlyPlayer().addCard(card);
        card = null;
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
