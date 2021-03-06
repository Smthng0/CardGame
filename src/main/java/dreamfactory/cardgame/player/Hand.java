package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private List<Card> backingHand;
    private static final int CARD_LIMIT = 10;

    public Hand (){
        backingHand = new ArrayList<>();
    }

    public void addCard(Card card) {
        backingHand.add(card);
    }

    public void removeCard(int index) {
        if (validIndex(index)){
            backingHand.remove(index);
        } else {
            System.out.println("No more cards or card does not exist!");
        }
    }

    public Card getCard(int index) {
        if (validIndex(index)) {
            return backingHand.get(index);
        }

        return null;
    }

    public String asString() {
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (Card card : backingHand) {
            result.append(index)
                    .append(". ")
                    .append(card.asString());
            index++;
        }

        return result.toString();
    }

    public String asStringPlayable(int availableMana) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (Card card : backingHand) {
            if (card.getManaCost() <= availableMana) {
                result.append(index)
                        .append(". ")
                        .append(card.asString());
            }
            index++;
        }

        return result.toString();
    }

    public boolean hasPlayableCards(int availableMana) {
        for (Card card : backingHand) {
            if (card.getManaCost() <= availableMana) {
                return true;
            }
        }
        return false;
    }

    private void sortByManaCost() {
        backingHand.sort(Comparator.comparing(Card::getManaCost));
    }

    public boolean isEmpty(){
        return backingHand.isEmpty();
    }

    public int getNumberOfCards() {
        return backingHand.size();
    }

    public boolean isFull() {
        return getNumberOfCards() > CARD_LIMIT;
    }

    private boolean validIndex(int index) {
        return ((index >= 0) && (index < getNumberOfCards()));
    }

    public List<Card> getBackingHand() {
        return backingHand;
    }

    public static int getCardLimit() {
        return CARD_LIMIT;
    }
}
