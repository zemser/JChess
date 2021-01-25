package engine.tests.other;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.players.MoveMaker;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestPawnEnPassant {
    @Test
    public void testPawnEnPassant () {
        final Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("f7"),
                        BoardUtils.getCoordinateAtPosition("f6")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveMaker t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("f4"),
                        BoardUtils.getCoordinateAtPosition("f5")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("g7"),
                        BoardUtils.getCoordinateAtPosition("g5")));
        assertTrue(t4.getMoveStatus().isDone());
        assertNotNull(t4.getBoard().getEnPassantPawn());
        final Move enPassantMove = Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f5"),
                BoardUtils.getCoordinateAtPosition("g6"));
        assertTrue(t4.getBoard().currentPlayer().getLegalMoves().contains(enPassantMove) && (enPassantMove instanceof Move.PawnEnPassantAttackMove));
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(enPassantMove);
        assertTrue(t5.getMoveStatus().isDone());
    }
}
