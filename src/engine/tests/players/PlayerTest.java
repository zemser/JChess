package engine.tests.players;

import engine.board.Board;
import engine.board.Move;
import engine.players.Player;
import engine.players.WhitePlayer;
import junit.framework.TestCase;



public class PlayerTest extends TestCase {
    public void testEnumPlayerType(){
        //create player
        Board board = Board.createInitialBoard();
        Player p = new WhitePlayer(board, null, null);
        assertTrue(p.isInCheck());
    }
}