package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.io.PlainMinionLoader;
import dreamfactory.cardgame.io.AbilityMinionGenerator;
import dreamfactory.cardgame.player.Deck;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class DeckTest {

    @Test
    public void create_deck_OK() {
        Deck deck = createDeck();

        assertTrue(deck.getDmgCounter() == 0);
    }

    @Test
    public void createDeck_fromCSV_OK() {
        List<MinionCard> readerList = (new PlainMinionLoader().loadMinionsFromCSV());
        List<HearthstoneCard> minionList = new ArrayList<>();
        minionList.addAll(readerList);
        minionList.addAll(readerList);

        List<MinionCard> abilityList = new AbilityMinionGenerator().createMinions();
        minionList.addAll(abilityList);
        minionList.addAll(abilityList);

        printList(minionList);
        System.out.println("Number of minions: " + minionList.size());
    }

    @Test
    public void drawCard_OK() {
        Deck deck = createDeck();

        for (int i = 0; i < 30; i++) {
            deck.drawCard();
        }

        assertTrue(deck.getDmgCounter() == 0);

        deck.drawCard();
        deck.drawCard();

        assertTrue(deck.getDmgCounter() == 2);
    }

    public Deck createDeck(){
        List<HearthstoneCard> arrayDeck = new ArrayList<>();
        Random random = new Random ();

        for (int i = 0; i < 30; i++) {
            arrayDeck.add(
                    new MinionCard(("Minion"+(i+1)),
                            random.nextInt(9),
                            random.nextInt(10),
                            random.nextInt(10)
                    )
            );
        }

        Deck deck = new Deck (arrayDeck);
        return deck;
    }

    public void printList(List<HearthstoneCard> list) {
        for (HearthstoneCard card : list) {
            System.out.print(card.getTitle()
                    + ", " + card.getManaCost());
            if (card instanceof MinionCard) {
                System.out.print(", " + ((MinionCard)card).getAttack()
                        + ", " + ((MinionCard)card).getHealth());
            }
            System.out.println();
        }
    }



}