package engine.tests.board;

import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.board.Move.NeutralMove;
import engine.pieces.Pawn;
import junit.framework.TestCase;

import static engine.Color.BLACK;
import static engine.Color.WHITE;
import static engine.board.Board.createInitialBoard;

public class MoveTest extends TestCase {
    public void testStrings(){
        Board b = createInitialBoard();
        Move m1 = new NeutralMove(b, new Pawn(48, WHITE), 40);
        assertEquals("Pa3", m1.toString());
        Move m2 = new Move.AttackMove(b, new Pawn(48, WHITE), 40,new Pawn(40, BLACK ));
        assertEquals("Pa3", m2.toString());
    }
}
