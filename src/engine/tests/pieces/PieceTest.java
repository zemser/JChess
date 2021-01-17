package engine.tests.pieces;

import engine.Color;

import static engine.Color.BLACK;
import static engine.Color.WHITE;

import engine.board.Board;
import engine.board.Move;
import engine.pieces.King;
import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.Rook;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTest extends TestCase {
    Color white_color;
    Color black_color;
    Piece whiteOne;
    Piece blackOne;
    @BeforeClass
    public void setUp() {
        white_color = WHITE;
        black_color = BLACK;
        whiteOne = new Rook(2, WHITE) {
            @Override
            public List<Move> calculateMovess(Board board) {
                return null;
            }
        };
        blackOne = new King(2, BLACK) {
            @Override
            public List<Move> calculateMovess(Board board) {
                return null;
            }
        };
    }
    @Test
    public void testPieceColor(){
        //System.out.println("test name: " + this.getName());
        String testMessage = "hello";
        assertEquals("compare enums", white_color, whiteOne.getPieceColor());
        assertEquals("compare enums", black_color, blackOne.getPieceColor());

        //assertFalse(emptyTile.isTileOccupied(), "should be false");
        //assertTrue(occupiedTile.isTileOccupied(), "should be true");
    }
    @Test
    public void testPieceTile(){
        //System.out.println("test name: " + this.getName());
        assertEquals("Check Tile number", 2, blackOne.getPosition());
    }

    @Test
    /**
     * Check the field PieceType When creating a player object
     * Test the isKing() method - if and only if PieceType is KING return true
     */
    public void testPieceTypeEnum(){
        //System.out.println("test name: " + this.getName());
        assertEquals(Piece.PieceType.KING, blackOne.getPieceType());
        assertEquals(Piece.PieceType.ROOK, whiteOne.getPieceType());

        assertFalse(whiteOne.getPieceType().isKing());
        assertTrue(blackOne.getPieceType().isKing());
    }
}