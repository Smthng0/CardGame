package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<MinionCard> backingBoard = new ArrayList<>();

    public void summonMinion(MinionCard card) {
        if (isFull()){
            System.out.println("Board is full");
        } else {
            backingBoard.add(card);
        }
    }

    public MinionCard getMinion(int index) {
        if (validIndex(index)) {
           return backingBoard.get(index);
        }

        return null;
    }

    public void removeMinion(int index) {
        if (validIndex(index)) {
            backingBoard.remove(index);
        }
    }

    public void printBoard() {
        if  (isEmpty()) {
            System.out.println("Board empty!");
        } else {
            for (int index = 0; index < backingBoard.size(); index++) {
                MinionCard minion = backingBoard.get(index);
                System.out.print(index + ". "
                        + minion.getTitle()
                        + ", Attack: " + minion.getAttack()
                        + ", Health: " + minion.getHealth());
                if (minion.hasAbility()) {
                    for (Ability ability : minion.getAbilities()) {
                        System.out.print(", " + ability);
                    }
                }

                System.out.println(", Remaining attacks: "
                        + minion.getRemainingAttacks());
            }
            System.out.println();
        }
    }

    public boolean isFull() {
        return getNumberOfMinions() == 7;
    }

    public boolean isEmpty() {
        return backingBoard.isEmpty();
    }

    public int getNumberOfMinions() {
        return backingBoard.size();
    }

    private boolean validIndex(int index) {
        return ((index < getNumberOfMinions()
                && (index >= 0)));
    }

    public void resetAttacks() {
        for (MinionCard minion : backingBoard) {
            minion.resetAttacks();
        }
    }
}
