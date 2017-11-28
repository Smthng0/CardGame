package card.game;

import card.game.abilities.Ability;
import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;
import card.game.cards.SpellCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private int numberOfCards = 0;
    private int limit = 10;
    private List<HearthstoneCard> backingHand;
    private Deck deck = null;
    private Board board = null;

    public Hand (Deck deck, boolean playsFirst, Board board){
        backingHand = new ArrayList<>();
        this.deck = deck;
        this.board = board;

        for (int i = 0; i < 3; i++) {
            this.drawCard();
        }

        if (!playsFirst){
            this.addCard(new SpellCard("The Coin", 0, null));
        }

    }

    public HearthstoneCard drawCard() {
        numberOfCards++;
        HearthstoneCard card = deck.drawCard();
        backingHand.add(card);
        return card;
    }

    public void addCard(HearthstoneCard card) {
        backingHand.add(card);
        numberOfCards++;
    }

    public void discardCard(HearthstoneCard card) {
        if (hasCards()
                &&backingHand.remove(card)){
            numberOfCards--;
            board.addToGraveyard(card);
        } else {
            System.out.println("Doesn't have that card!");
        }
    }

    public void discardCard(int index) {
        if (hasCards()){
            numberOfCards--;
            board.addToGraveyard(backingHand.remove(index));
        } else {
            System.out.println("No more cards!");
        }
    }

    public void playCard(HearthstoneCard card) {
        if (card instanceof MinionCard){
            board.summonMinion((MinionCard)card);
        }

        card.play();
        backingHand.remove(card);
        numberOfCards--;
    }

    public int playCard(String title) {
        for (HearthstoneCard card : backingHand) {
            if (card.getTitle().equalsIgnoreCase(title)) {
                playCard(card);
                return card.getManaCost();
            }
        }
        return -1;
    }

    public int playCard(int index) {
        if (backingHand.size() > index){
            HearthstoneCard card = backingHand.get(index);
            card.play();
            backingHand.remove(index);
            numberOfCards--;

            if (card instanceof MinionCard){
                board.summonMinion((MinionCard)card);
            }

            return card.getManaCost();
        }

        return -1;
    }

    public HearthstoneCard getCard(int index) {
        if (backingHand.size() > index) {
            return backingHand.get(index);
        }

        return null;
    }

    public boolean checkMana(String title, int availableMana) {
        for (HearthstoneCard card : backingHand) {
            if ((card.getTitle().equalsIgnoreCase(title))
                    && (card.getManaCost() <= availableMana)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMana(int index, int availableMana) {
        if ((backingHand.size() > index)
            && (backingHand.get(index).getManaCost() <= availableMana)) {
            return true;
        }

        return false;
    }

    public void viewHand() {
        for (int index = 0; index < backingHand.size(); index++) {
            sortByManaCost();
            HearthstoneCard card = backingHand.get(index);
            System.out.print(index + ". " + card.getTitle()
                    + ", Mana cost: " + card.getManaCost());

            if (card instanceof MinionCard) {
                System.out.print(", Attack: " + ((MinionCard) card).getAttack()
                        + ", Health: " + ((MinionCard) card).getHealth());
                if (card.hasAbility()) {
                    printAbilities(((MinionCard)card).getAbilities());
                }
            }

            if (card.getTitle().equalsIgnoreCase("The Coin")){
                System.out.print(", Ability: Add 1 temporary mana");
            }

            System.out.println();
        }

        System.out.println();
    }

    private void printAbilities(List<Ability> list) {
        for (Ability ability : list) {
            System.out.print(", " + ability.getAbilityType());
        }
    }

    private void sortByManaCost() {
        backingHand.sort(Comparator.comparing(HearthstoneCard::getManaCost));
    }

    public boolean hasCards(){
        return this.numberOfCards > 0;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public int getLimit() {
        return limit;
    }
}
