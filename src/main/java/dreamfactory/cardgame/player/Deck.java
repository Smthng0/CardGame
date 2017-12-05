package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.HearthstoneCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private int dmgCounter = 0;
    private List<HearthstoneCard> backingDeck = new ArrayList<>();

    public Deck (List<HearthstoneCard> deck) {
        if (!acceptableSize(deck.size())){
            System.out.println("Deck must have 15 - 30 cards!");
        } else {
            backingDeck.addAll(deck);
            shuffleDeck();
        }
    }

    public HearthstoneCard drawCard() {
        if (backingDeck.isEmpty()){
            dmgCounter++;
            return null;
        }

        return backingDeck.remove(0);
    }

    private boolean acceptableSize(int size) {
        return ((size < 31) && (size > 14));
    }

    private void shuffleDeck() {
        Collections.shuffle(backingDeck);
    }

    public int getDmgCounter() {
        return dmgCounter;
    }

}
