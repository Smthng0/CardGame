package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.Ability;
import dreamfactory.cardgame.cards.MinionCard;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<MinionCard> backingBoard = new ArrayList<>();
    private static final int MINION_LIMIT = 7;

    public boolean summonMinion(MinionCard card) {
        if (isFull()){
            return false;
        }
        backingBoard.add(card);
        return true;
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

    public void resetAttacks() {
        for (MinionCard minion : backingBoard) {
            minion.resetAttacks();
        }
    }

    public String asString() {
        if (isEmpty()) {
            return "No Minions!\n";
        }

        StringBuilder result = new StringBuilder();
        int index = 0;
        for (MinionCard minion : backingBoard) {
            result.append(index)
                    .append(". ")
                    .append(minion.boardString());
            index++;
        }
        return result.toString();
    }

    public String asStringCanAttack() {
        if (isEmpty()) {
            return "No Minions!\n";
        }

        StringBuilder result = new StringBuilder();
        int index = 0;
        for (MinionCard minion : backingBoard) {
            if (minion.canAttack()) {
                result.append(index)
                        .append(". ")
                        .append(minion.boardString());
            }
            index++;
        }

        if (result.length() < 10) {
            return "No Minions that can attack!\n";
        }

        return result.toString();
    }

    public boolean hasAttackableMinion() {
        for (MinionCard minion : backingBoard) {
            if (minion.canAttack()) {
                return true;
            }
        }
        return false;
    }

    public String asStringWithTaunt() {
        if (!hasTauntMinion()) {
            return "No taunt minion!";
        }

        StringBuilder result = new StringBuilder();
        int index = 0;
        for (MinionCard minion : backingBoard) {
            if (minion.hasAbility(Ability.TAUNT)) {
                result.append(index)
                        .append(". ")
                        .append(minion.boardString());
            }
            index++;
        }
        return result.toString();
    }

    private boolean validIndex(int index) {
        return ((index < getNumberOfMinions()
                && (index >= 0)));
    }

    public boolean hasTauntMinion() {
        for (MinionCard minion : backingBoard) {
            if (minion.hasAbility(Ability.TAUNT)) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfMinions() {
        return backingBoard.size();
    }

    public boolean isFull() {
        return getNumberOfMinions() == MINION_LIMIT;
    }

    public boolean isEmpty() {
        return backingBoard.isEmpty();
    }

    public List<MinionCard> getBackingBoard() {
        return backingBoard;
    }

    public static int getMinionLimit() {
        return MINION_LIMIT;
    }
}
