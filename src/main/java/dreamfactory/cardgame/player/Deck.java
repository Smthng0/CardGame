package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.Card;
import dreamfactory.cardgame.cards.spells.Blockers;
import dreamfactory.cardgame.cards.spells.TheCoin;
import dreamfactory.cardgame.io.AbilityMinionGenerator;
import dreamfactory.cardgame.io.PlainMinionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private int dmgCounter = 0;
    private List<Card> backingDeck = new ArrayList<>();

    public Deck (List<Card> deck) {
        if (!acceptableSize(deck.size())){
            System.out.println("Deck must have 15 - 30 cards!");
        } else {
            backingDeck.addAll(deck);
            shuffleDeck();
        }
    }

    public Card drawCard() {
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

    public List<Card> getBackingDeck() {
        return backingDeck;
    }

    public static Deck getConstructedDeck() {
        List<Card> minionSpellList = new ArrayList<>();
        minionSpellList.addAll(PlainMinionLoader.loadMinionsFromCSV());
        minionSpellList.addAll(AbilityMinionGenerator.createMinions());
        minionSpellList.add(new TheCoin());
        minionSpellList.add(new Blockers());
        minionSpellList.add(new TheCoin());
        minionSpellList.add(new Blockers());

        return new Deck(minionSpellList);
    }

    public static Deck getConstructedDeck2() {
        List<Card> minionList = new ArrayList<>();
        minionList.addAll(PlainMinionLoader.loadMinionsFromCSV());
        minionList.addAll(PlainMinionLoader.loadMinionsFromCSV());
        minionList.addAll(AbilityMinionGenerator.createMinions());
        minionList.addAll(AbilityMinionGenerator.createMinions());

        return new Deck(minionList);
    }


}
