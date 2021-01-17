package engine.tests.pieces;

import engine.board.Board;
import engine.board.Move;
import engine.pieces.Bishop;
import engine.pieces.Knight;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;

import static engine.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest extends TestCase {
    @Test
    public void testBishopIsValidDestinationTile(){
        System.out.println("test name: " + this.getName());
        Bishop bishop = new Bishop(0, WHITE);

        assertFalse(bishop.isValidDestinationTile(0,-9) || bishop.isValidDestinationTile(0,7));
        assertFalse(bishop.isValidDestinationTile(7,9) || bishop.isValidDestinationTile(0,-7));
        assertFalse(bishop.isValidDestinationTile(3,-9) || bishop.isValidDestinationTile(0,-7));
        assertFalse(bishop.isValidDestinationTile(59,9) || bishop.isValidDestinationTile(0,-7));

        assertTrue(bishop.isValidDestinationTile(27,-14) && bishop.isValidDestinationTile(27,36));
        assertTrue(bishop.isValidDestinationTile(35,-7) && bishop.isValidDestinationTile(35,-14)
                && bishop.isValidDestinationTile(35,-21));
        assertTrue(bishop.isValidDestinationTile(35,7) && bishop.isValidDestinationTile(35,14)
                && bishop.isValidDestinationTile(35,21));
        assertTrue(bishop.isValidDestinationTile(35,9) && bishop.isValidDestinationTile(35,18)
                && bishop.isValidDestinationTile(35,27));
        assertTrue(bishop.isValidDestinationTile(35,-9) && bishop.isValidDestinationTile(35,-18)
                && bishop.isValidDestinationTile(35,-27));

    }

//    @Test
//    public void testBishopCalculateMovess() {
//        System.out.println("test name: " + this.getName());
//        Board board = new Board();
//        Bishop bishop1 = new Bishop(35, WHITE);
//        Bishop bishop2 = new Bishop(8, WHITE);
//        Bishop bishop3 = new Bishop(8, WHITE);
//        Bishop bishop4 = new Bishop(8, WHITE);
//
//        final Collection<Move> legalMoves = bishop1.calculateMovess(board);
//        assertFalse(legalMoves.contains(new Move.NeutralMove(board, bishop1, bishop1.getPosition()-18)));
//        assertFalse(legalMoves.contains(new Move.NeutralMove(board, bishop1, bishop1.getPosition()+14)));
//        assertTrue(legalMoves.contains(new Move.NeutralMove(board, bishop1, bishop1.getPosition()+7)));
//        assertTrue(legalMoves.contains(new Move.NeutralMove(board, bishop1, bishop1.getPosition()-9)));

        //assertFalse(bishop.isValidDestinationTile(33,-18) || bishop.isValidDestinationTile(33,14));
    //}
}