package engine.tests.pieces;

import engine.board.Board;
import engine.board.Move;
import engine.pieces.Knight;
import engine.pieces.Piece;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static engine.Color.BLACK;
import static engine.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest extends TestCase {
    Piece knight;
    @BeforeClass
    public void setUp() {
       knight = new Knight(1, WHITE);
    }
    @Test
    public void testKnightIsValidDestinationTile(){
        System.out.println("test name: " + this.getName());
        Knight knighColumn1 = new Knight(8, WHITE);
        Knight knighColumn2 = new Knight(1, WHITE);
        Knight knighColumn7 = new Knight(46, WHITE);
        Knight knighColumn8 = new Knight(55, WHITE);
        assertFalse(knighColumn1.isValidDestinationTile(8,-17) || knighColumn1.isValidDestinationTile(8,-10)
                    || knighColumn1.isValidDestinationTile(8,6) || knighColumn1.isValidDestinationTile(8,15));
        assertFalse(knighColumn2.isValidDestinationTile(55,17) || knighColumn1.isValidDestinationTile(55,10)
                || knighColumn1.isValidDestinationTile(55,-6) || knighColumn1.isValidDestinationTile(55,-15));
        assertFalse(knighColumn7.isValidDestinationTile(1,-10) || knighColumn1.isValidDestinationTile(1,6));
        assertFalse(knighColumn8.isValidDestinationTile(46,10) || knighColumn1.isValidDestinationTile(46,-6));
        assertFalse(knighColumn1.isValidDestinationTile(3,-10) || knighColumn1.isValidDestinationTile(1,6));
        assertTrue(knighColumn1.isValidDestinationTile(27,-10) && knighColumn1.isValidDestinationTile(46,6));
    }

    @Ignore
    @Test
    public void testPieceTile(){
        System.out.println("test name: " + this.getName());

    }
}