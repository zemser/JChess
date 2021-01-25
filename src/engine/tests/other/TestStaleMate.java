package engine.tests.other;

import engine.Color;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.pieces.Bishop;
import engine.pieces.King;
import engine.pieces.Pawn;
import engine.players.MoveMaker;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TestStaleMate {
    @Test
    public void testAnandKramnikStaleMate() {

        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new Pawn(14, Color.BLACK));
        builder.setPiece(new Pawn(21, Color.BLACK));
        builder.setPiece(new King(36,Color.BLACK, false));
        // White Layout
        builder.setPiece(new Pawn(29, Color.WHITE));
        builder.setPiece(new King(31,Color.WHITE, false));
        builder.setPiece(new Pawn(39, Color.WHITE));
        // Set the current player
        builder.setMoveMaker(Color.BLACK);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("f5")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getBoard().currentPlayer().isInCheck());
        assertFalse(t1.getBoard().currentPlayer().isInCheckMate());
    }
    @Test
    public void testAnonymousStaleMate() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(2, Color.BLACK, false));
        // White Layout
        builder.setPiece(new Pawn(10,Color.WHITE));
        builder.setPiece(new King(26, Color.WHITE, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c5"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getBoard().currentPlayer().isInCheck());
        assertFalse(t1.getBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(0, Color.BLACK, false));
        // White Layout
        builder.setPiece(new Pawn(16, Color.WHITE));
        builder.setPiece(new King(17, Color.WHITE, false));
        builder.setPiece(new Bishop(19, Color.WHITE));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a7")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getBoard().currentPlayer().isInCheck());
        assertFalse(t1.getBoard().currentPlayer().isInCheckMate());
    }
}
