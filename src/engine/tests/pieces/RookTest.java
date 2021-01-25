package engine.tests.pieces;

import engine.board.Board;
import engine.board.Move;
import engine.pieces.Bishop;
import engine.pieces.Rook;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static engine.Color.WHITE;
import static engine.board.Board.createInitialBoard;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest extends TestCase {
    @Test
    public void testRookIsValidDestinationTile(){
        System.out.println("test name: " + this.getName());
        Rook rook = new Rook(0, WHITE);

        assertFalse(rook.isValidDestinationTile(0,-1) || rook.isValidDestinationTile(32,-1));
        assertFalse(rook.isValidDestinationTile(7,1) || rook.isValidDestinationTile(23,1));
        assertTrue(rook.isValidDestinationTile(37,-4) && rook.isValidDestinationTile(38,3));
        assertTrue(rook.isValidDestinationTile(61,-5) && rook.isValidDestinationTile(0,7));

    }

    @Test
    public void testRookMoves(){
        System.out.println("test name: " + this.getName());
        Board b = createInitialBoard();
        ArrayList<Move> moves= (ArrayList<Move>) b.calculatePieceMove(new Rook(56, WHITE), WHITE);
        assertTrue(moves.isEmpty());



    }

}