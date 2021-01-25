package engine.tests.other;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.players.MoveMaker;
import engine.players.MoveStatus;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCastling {
    @Test
    public void testWhiteKingSideCastle() {
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
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition(
                        "e1"), BoardUtils.getCoordinateAtPosition("g1"));
        assertTrue(t6.getBoard().currentPlayer().getLegalMoves().contains(wm1) && wm1.isCastlingMove());
        final MoveMaker t7 = t6.getBoard().currentPlayer().makeMove(wm1);
        assertTrue(t7.getMoveStatus().isDone());
       // assertTrue(t7.getBoard().getWhitePlayer().isCastled());
//        assertFalse(t7.getBoard().getWhitePlayer().isKingSideCastleCapable());
//        assertFalse(t7.getBoard().getWhitePlayer().isQueenSideCastleCapable());
    }
    @Test
    public void testWhiteQueenSideCastle() {
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
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("d2")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveMaker t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("h7"),
                        BoardUtils.getCoordinateAtPosition("h6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveMaker t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveMaker t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("h6"),
                        BoardUtils.getCoordinateAtPosition("h5")));
        assertTrue(t10.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition(
                        "e1"), BoardUtils.getCoordinateAtPosition("c1"));
        assertTrue(t10.getBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveMaker t11 = t10.getBoard().currentPlayer().makeMove(wm1);
        assertTrue(t11.getMoveStatus().isDone());
//        assertTrue(t11.getToBoard().whitePlayer().isCastled());
//        assertFalse(t11.getToBoard().whitePlayer().isKingSideCastleCapable());
//        assertFalse(t11.getToBoard().whitePlayer().isQueenSideCastleCapable());
    }
    @Test
    public void testBlackKingSideCastle() {
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
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("d3"),
                        BoardUtils.getCoordinateAtPosition("d4")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("f8"),
                        BoardUtils.getCoordinateAtPosition("e7")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t7.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition(
                        "e8"), BoardUtils.getCoordinateAtPosition("g8"));
        assertTrue(t7.getBoard().currentPlayer().getLegalMoves().contains(wm1) && wm1.isCastlingMove());
        final MoveMaker t8 = t7.getBoard().currentPlayer().makeMove(wm1);
        System.out.println(t8.getBoard());
        assertTrue(t8.getMoveStatus().isDone());
//        assertTrue(t8.getBoard().getBlackPlayer().isCastled());
//        assertFalse(t8.getBoard().blackPlayer().isKingSideCastleCapable());
//        assertFalse(t8.getBoard().blackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackQueenSideCastle() {
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
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("e7")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("d2")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveMaker t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveMaker t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveMaker t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("d7")));
        assertTrue(t10.getMoveStatus().isDone());
        final MoveMaker t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                                BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(t11.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition(
                        "e8"), BoardUtils.getCoordinateAtPosition("c8"));
        assertTrue(t11.getBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveMaker t12 = t11.getBoard().currentPlayer().makeMove(wm1);
        assertTrue(t12.getMoveStatus().isDone());
//        assertTrue(t12.getBoard().getBlackPlayer().isCastled());
//        assertFalse(t12.getBoard().blackPlayer().isKingSideCastleCapable());
//        assertFalse(t12.getBoard().blackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testCastleBugOne() {
        final Board board = Board.createInitialBoard();
        final MoveMaker t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveMaker t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveMaker t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("f5")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("f5"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("c2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveMaker t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveMaker t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("a4")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveMaker t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("d7")));
        assertTrue(t10.getMoveStatus().isDone());
        final MoveMaker t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                                BoardUtils.getCoordinateAtPosition("c3")));
        assertTrue(t11.getMoveStatus().isDone());


    }

    @Test
    public void testNoCastlingOutOfCheckOnLane() {
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
                        BoardUtils.getCoordinateAtPosition("b5")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveMaker t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("f8"),
                        BoardUtils.getCoordinateAtPosition("c5")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveMaker t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f4")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveMaker t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("h6")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveMaker t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("h3")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveMaker t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("g7"),
                        BoardUtils.getCoordinateAtPosition("g6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveMaker t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("e1"),
                        BoardUtils.getCoordinateAtPosition("g1")));
        assertTrue(t9.getMoveStatus() == MoveStatus.ILLEGAL_MOVE);
    }


}

