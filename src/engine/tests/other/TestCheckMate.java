package engine.tests.other;

import engine.Color;
import engine.board.Board;
import engine.board.Board.Builder;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.pieces.Bishop;
import engine.pieces.King;
import engine.pieces.Rook;
import engine.players.MoveMaker;
import engine.players.Player;
import engine.players.WhitePlayer;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class TestCheckMate {
    @Test
    public void testDiscoveredCheck() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(4, Color.BLACK, false));
        builder.setPiece(new Rook(24,Color.BLACK));
        // White Layout
        builder.setPiece(new Bishop(44,Color.WHITE));
        builder.setPiece(new Rook(52,Color.WHITE));
        builder.setPiece(new King(58,Color.WHITE, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);
        final Board board = builder.build();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e3"),
                        BoardUtils.getCoordinateAtPosition("b6")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isInCheck());
        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("b5")));
        assertFalse(t2.getMoveStatus().isDone());
        final MoveMaker t3 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t3.getMoveStatus().isDone());
    }
    @Test
    public void testFoolsMate() {

        final Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveMaker t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        assertTrue(t4.getBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testScholarsMate() {

        final Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("a7"),
                        BoardUtils.getCoordinateAtPosition("a6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveMaker t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("a4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertTrue(t7.getMoveStatus().isDone());
        assertTrue(t7.getBoard().currentPlayer().isInCheckMate());
    }
    @Test
    public void testLegalsMate() {

        final Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveMaker t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveMaker t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("g7"),
                        BoardUtils.getCoordinateAtPosition("g6")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveMaker t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveMaker t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("g4"),
                        BoardUtils.getCoordinateAtPosition("d1")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveMaker t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveMaker t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("e8"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveMaker t13 = t12.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t12.getBoard(), BoardUtils.getCoordinateAtPosition("c3"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getBoard().currentPlayer().isInCheckMate());

    }


}