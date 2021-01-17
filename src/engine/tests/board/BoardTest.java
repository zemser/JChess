package engine.tests.board;

import engine.board.Board;
import engine.pieces.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

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
}