package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private List<HearthstoneCard> backingHand;
    private static final int CARD_LIMIT = 10;

    public Hand (){
        backingHand = new ArrayList<>();
    }

    public void addCard(HearthstoneCard card) {
        backingHand.add(card);
    }

    public HearthstoneCard playCard(int index, int availableMana) {
        if (checkMana(index, availableMana)){
            HearthstoneCard card = getCard(index);
            removeCard(index);
            return card;
        }

        return null;
    }

    public void removeCard(int index) {
        if (validIndex(index)){
            backingHand.remove(index);
        } else {
            System.out.println("No more cards or card does not exist!");
        }
    }

    public HearthstoneCard getCard(int index) {
        if (validIndex(index)) {
            return backingHand.get(index);
        }

        return null;
    }

    public void viewAllCards() {
        sortByManaCost();
        //print card
        //minioncard printer -> minion card ga treba imat
        //ability printer -> isto minion/spell card ga treba imat...
        //vidit da to sve se vraca kao string
    }

    public void viewPlayableCards(int availableMana) {
        sortByManaCost();
    }

    public boolean checkMana(int index, int availableMana) {
        return (validIndex(index)
                && (backingHand.get(index).getManaCost() <= availableMana));
    }

    private void sortByManaCost() {
        backingHand.sort(Comparator.comparing(HearthstoneCard::getManaCost));
    }

    public boolean hasCards(){
        return !backingHand.isEmpty();
    }

    public int getNumberOfCards() {
        return backingHand.size();
    }

    public int getLimit() {
        return CARD_LIMIT;
    }

    private boolean validIndex(int index) {
        return ((index >= 0) && (index < getNumberOfCards()));
    }
}
