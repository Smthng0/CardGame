package card.game;

import card.game.cards.HearthstoneCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private int remainingCards = 30;
    private int dmgCounter = 0;
    private List<HearthstoneCard> backingDeck = new ArrayList<>();


    public Deck (List<HearthstoneCard> list) {
        if (list.size() != 30){
            System.out.println("Must have 30 cards!");
        }

        backingDeck.addAll(list);
        Collections.shuffle(backingDeck);
    }

    public HearthstoneCard drawCard() {
        if (this.isEmpty()){
            dmgCounter += 1;
            return null;
        }

        remainingCards--;

        return backingDeck.remove(0);
    }

    public void addCard(HearthstoneCard card) {
        backingDeck.add(card);
        remainingCards++;
        shuffleDeck();
    }

    public boolean removeTargetCard(HearthstoneCard card) {
        if ((this.isEmpty())
                || (!searchCard(card))) {
            return false;
        } else {
            backingDeck.remove(backingDeck.indexOf(card));
            remainingCards--;
            shuffleDeck();
            return true;
        }
    }

    public boolean removeFirstCard() {
        if (this.isEmpty()) {
            return false;
        }

        backingDeck.remove(0);
        remainingCards--;
        shuffleDeck();
        return true;
    }

    public boolean searchCard(String title) {
        for (HearthstoneCard card : backingDeck) {
            if (card.getTitle().equals(title)) {
                shuffleDeck();
                return true;
            }
        }

        return false;
    }

    public boolean searchCard(HearthstoneCard targetCard) {
        for (HearthstoneCard card : backingDeck) {
            if (card.getTitle().equals(targetCard.getTitle())) {
                shuffleDeck();
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty() {
        return remainingCards == 0;
    }

    public void shuffleDeck() {
        Collections.shuffle(backingDeck);
    }

    public int getRemainingCards() {
        return remainingCards;
    }

    public int getDmgCounter() {
        return dmgCounter;
    }

}
