package engine.tests.players;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;

import engine.players.MoveMaker;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class PlayerTest {
    @Test
    public void tesSimpleEval(){
        //create player
        Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                       BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
    }
    @Test
    public void testIllegalMove() {
        final Board board = Board.createInitialBoard();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                BoardUtils.getCoordinateAtPosition("e6"));
        final MoveMaker t1 = board.currentPlayer().makeMove(m1);
        assertFalse(t1.getMoveStatus().isDone());
    }


}