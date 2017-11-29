package card.game;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;
import card.game.cards.SpellCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private List<HearthstoneCard> backingHand;

    public Hand (boolean playsFirst){
        backingHand = new ArrayList<>();

        if (!playsFirst){
            List<Ability> list = new ArrayList<>();
            list.add(Ability.ADD_MANA);
            this.addCard(new SpellCard("The Coin", 0, list));
        }
    }

    public void addCard(HearthstoneCard card) {
        backingHand.add(card);
    }

    public void removeCard(int index) {
        if (hasCards()){
            backingHand.remove(index);
        } else {
            System.out.println("No more cards!");
        }
    }

    public HearthstoneCard getCard(int index) {
        if (backingHand.size() > index) {
            return backingHand.get(index);
        }

        return null;
    }

    public boolean checkMana(int index, int availableMana) {
        if ((backingHand.size() > index)
            && (backingHand.get(index).getManaCost() <= availableMana)) {
            return true;
        }

        return false;
    }

    public void viewHand() {
        sortByManaCost();
        for (int index = 0; index < backingHand.size(); index++) {
            HearthstoneCard card = backingHand.get(index);
            System.out.print(index + ". " + card.getTitle()
                    + ", Mana cost: " + card.getManaCost());

            if (card instanceof MinionCard) {
                System.out.print(", Attack: " + ((MinionCard) card).getAttack()
                        + ", Health: " + ((MinionCard) card).getHealth());
            }

            if (card.hasAbility()) {
                System.out.print(", Ability: ");
                printAbilities(card.getAbilities());
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
            System.out.print(", " + ability);
        }
    }

    private void sortByManaCost() {
        backingHand.sort(Comparator.comparing(HearthstoneCard::getManaCost));
    }

    public boolean hasCards(){
        return getNumberOfCards() > 0;
    }

    public int getNumberOfCards() {
        return backingHand.size();
    }

    public int getLimit() {
        return 10;
    }
}
