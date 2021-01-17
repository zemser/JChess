package engine.tests.pieces;

import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.pieces.Pawn;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Collection;

public class PawnTest extends TestCase {
    @Test
    public void testPawnMoves(){
        Board b = Board.createInitialBoard();
        Pawn p = new Pawn(51, Color.WHITE);
        Collection<Move> ans= p.calculateMoves(b);
        assertTrue(ans.size()==2);
        System.out.println(ans);

    }

}