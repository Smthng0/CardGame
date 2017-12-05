package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.player.Board;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BoardTest {
    @Test
    public void summonMinion_OK() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.summonMinion(card);

        assertTrue(!board.isEmpty());
    }

    @Test
    public void printBoard_OK() {
        Board board = new Board();
        MinionCard card = new MinionCard("Vice", 3, 4, 2);
        board.summonMinion(card);
        board.summonMinion(card);

        board.printBoard();
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
        assertTrue(shouldBeDead.isDead());
        Assert.assertFalse(defendingBoard.getMinion(1).isDead());
        assertTrue(defendingBoard.getMinion(0).isDead());

    }

}