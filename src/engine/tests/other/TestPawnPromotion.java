package engine.tests.other;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.players.MoveMaker;
import engine.players.MoveStatus;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPawnPromotion {
    @Test
    public void testPawnPromotion(){
        final Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c2"),
                        BoardUtils.getCoordinateAtPosition("c4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("b7"),
                        BoardUtils.getCoordinateAtPosition("b5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveMaker t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("b5")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("b5"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("b7")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("b7")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveMaker t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("a7"),
                        BoardUtils.getCoordinateAtPosition("a5")));
        assertTrue(t8.getMoveStatus().isDone());
        final Move promotionMove = Move.MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("b7"),
                BoardUtils.getCoordinateAtPosition("b8"));
        assertTrue(t8.getBoard().currentPlayer().getLegalMoves().contains(promotionMove) && (promotionMove instanceof Move.PawnPromotionMove));
        final MoveMaker t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(promotionMove);
        assertTrue(t9.getMoveStatus().isDone());
    }

}
