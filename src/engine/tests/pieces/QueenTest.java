package engine.tests.pieces;

import engine.pieces.Queen;
import engine.pieces.Rook;
import junit.framework.TestCase;
import org.junit.Test;

import static engine.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest extends TestCase {
    @Test
    public void testQueenIsValidDestinationTile(){
        System.out.println("test name: " + this.getName());
        Queen queenBee = new Queen(0, WHITE);

        assertFalse(queenBee.isValidDestinationTile(0,-9) || queenBee.isValidDestinationTile(0,7));
        assertFalse(queenBee.isValidDestinationTile(7,9) || queenBee.isValidDestinationTile(0,-7));
        assertFalse(queenBee.isValidDestinationTile(3,-9) || queenBee.isValidDestinationTile(0,-7));
        assertFalse(queenBee.isValidDestinationTile(59,9) || queenBee.isValidDestinationTile(0,-7));

        assertTrue(queenBee.isValidDestinationTile(27,-14) && queenBee.isValidDestinationTile(27,36));
        assertTrue(queenBee.isValidDestinationTile(35,-7) && queenBee.isValidDestinationTile(35,-14)
                && queenBee.isValidDestinationTile(35,-21));
        assertTrue(queenBee.isValidDestinationTile(35,7) && queenBee.isValidDestinationTile(35,14)
                && queenBee.isValidDestinationTile(35,21));
        assertTrue(queenBee.isValidDestinationTile(35,9) && queenBee.isValidDestinationTile(35,18)
                && queenBee.isValidDestinationTile(35,27));
        assertTrue(queenBee.isValidDestinationTile(35,-9) && queenBee.isValidDestinationTile(35,-18)
                && queenBee.isValidDestinationTile(35,-27));

        
        assertTrue(queenBee.isValidDestinationTile(37,-4) && queenBee.isValidDestinationTile(36,3));
       //****bug - also on the bishop it wont work: assertTrue(queenBee.isValidDestinationTile(61,-5) && queenBee.isValidDestinationTile(0,7));

    }
}