package engine.tests.board;

import engine.board.Board;
import engine.board.Move;
import engine.board.Move.AttackMove;
import engine.board.Move.NeutralMove;
import engine.board.Move.PawnJumpMove;
import engine.board.Move.PawnMove;
import engine.pieces.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static engine.Color.WHITE;
import static engine.board.Board.createInitialBoard;

public class BoardTest extends TestCase {
    public void testTestBoard(){
        System.out.println("test name: " + this.getName());
        Board board = createInitialBoard();
        ArrayList<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(new Pawn(48, WHITE));
        whitePieces.add(new Pawn(49, WHITE));
        whitePieces.add(new Pawn(50, WHITE));
        whitePieces.add(new Pawn(51, WHITE));
        whitePieces.add(new Pawn(52, WHITE));
        whitePieces.add(new Pawn(53, WHITE));
        whitePieces.add(new Pawn(54, WHITE));
        whitePieces.add(new Pawn(55, WHITE));
        whitePieces.add(new Rook(56, WHITE));
        whitePieces.add(new Knight(57, WHITE));
        whitePieces.add(new Bishop(58, WHITE));
        whitePieces.add(new Queen(59, WHITE));
        whitePieces.add(new King(60, WHITE));
        whitePieces.add(new Bishop(61, WHITE));
        whitePieces.add(new Knight(62, WHITE));
        whitePieces.add(new Rook(63, WHITE));

        assertEquals(whitePieces.size(), board.getWhitePieces().size());

    }

    /**
     * Test the collection of legal moves calculated by the board
     */
    public void testBoardLegalMoves(){
        System.out.println("test name: " + this.getName());
        Board board = createInitialBoard();
        Collection<Move> moves = board.getWhitePlayer().getLegalMoves();
        Collection<Move> enemyMove = board.getBlackPlayer().getLegalMoves();
        //check white player has all legal starting moves
        assertTrue(moves.contains(new PawnMove(board, new Pawn(48, WHITE),40)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(49, WHITE),41)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(50, WHITE),42)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(51, WHITE),43)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(52, WHITE),44)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(53, WHITE),45)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(54, WHITE),46)));
        assertTrue(moves.contains(new PawnMove(board, new Pawn(55, WHITE),47)));

        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(48, WHITE),32)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(49, WHITE),33)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(50, WHITE),34)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(51, WHITE),35)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(52, WHITE),36)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(53, WHITE),37)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(54, WHITE),38)));
        assertTrue(moves.contains(new PawnJumpMove(board, new Pawn(55, WHITE),39)));

        assertTrue(moves.contains(new NeutralMove(board, new Knight(57, WHITE),40)));
        assertTrue(moves.contains(new NeutralMove(board, new Knight(57, WHITE),42)));
        assertTrue(moves.contains(new NeutralMove(board, new Knight(62, WHITE),45)));
        assertTrue(moves.contains(new NeutralMove(board, new Knight(62, WHITE),47)));
        assertEquals(20, moves.size());
        assertEquals(20, enemyMove.size());


        //check that all initial legal moves are not attack moves
        for(Move move : moves){
            assertTrue(!(move instanceof AttackMove));
        }
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertTrue(board.getWhitePlayer().toString().equals("White"));
        assertFalse(board.currentPlayer().isCastled());

        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertTrue(board.getBlackPlayer().toString().equals("Black"));
        assertFalse(board.currentPlayer().getOpponent().isCastled());
    }


}