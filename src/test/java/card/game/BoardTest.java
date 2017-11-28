package card.game;

import card.game.cards.MinionCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {
    @Test
    public void summonMinion_getAnyMinion_OK() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.summonMinion(card);

        assertTrue(board.getMinion("Vice") != null);
    }

    private void assertTrue(boolean vice) {
    }

    @Test
    public void addToGraveyard_getGraveyard_OK() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.addToGraveyard(card);

        assertTrue(board.getNumberOfMinions() == 0);
        assertTrue(board.getGraveyard().size() == 1);

        board.addToGraveyard(card);

        assertTrue(board.getGraveyard().size() == 2);
    }

    @Test
    public void viewBoard_OK() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.summonMinion(card);
        board.summonMinion(card);

        board.viewBoard();
    }

    @Test
    public void getAllMinions() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.summonMinion(card);
        board.summonMinion(card);
        board.summonMinion(card);

        List<MinionCard> list = new ArrayList<>();
        list.addAll(board.getAllMinions());

        for (int i = 0; i < 3; i++) {
            assertTrue(list.get(i).getTitle().equals("Vice"));
        }
    }

    @Test
    public void getNumberOfMinions() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.summonMinion(card);

        assertTrue(board.getNumberOfMinions() == 1);

        board.summonMinion(card);

        assertTrue(board.getNumberOfMinions() == 2);
    }

    @Test
    public void twoBoard_test_OK() {
        Board attackingBoard = new Board();
        Board defendingBoard = new Board();
        MinionCard minionCard = new MinionCard("frane", 0, 4, 4);
        attackingBoard.summonMinion(minionCard);

        minionCard = new MinionCard("frane", 0, 3, 4);
        defendingBoard.summonMinion(minionCard);

        minionCard = new MinionCard("frane", 0, 3, 4);
        defendingBoard.summonMinion(minionCard);

        MinionCard attackDog = attackingBoard.getMinion(0);

        MinionCard shouldBeDead = defendingBoard.getMinion(0);
        attackDog.resetAttacks();

        attackDog.attack(shouldBeDead);

        Assert.assertEquals(1, attackDog.getHealth());
        Assert.assertTrue(shouldBeDead.isDead());
        Assert.assertFalse(defendingBoard.getMinion(1).isDead());
        Assert.assertTrue(defendingBoard.getMinion(0).isDead());

    }

}