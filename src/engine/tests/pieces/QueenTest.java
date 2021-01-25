package engine.tests.pieces;

import engine.board.Board;
import engine.board.Move;
import engine.pieces.Pawn;
import engine.pieces.Queen;
import engine.pieces.Rook;
import engine.players.MoveMaker;
import engine.players.MoveStatus;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static engine.Color.WHITE;
import static engine.board.Board.createInitialBoard;
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
    @Test
    public void testQueenMoves(){
        System.out.println("test name: " + this.getName());
        Board board = createInitialBoard();
        Collection<Move> d= board.getWhitePlayer().getLegalMoves();
        Queen queenBee = new Queen(59, WHITE);
        Collection<Move> queenMoves = queenBee.calculateMoves(board);
        assertTrue(queenMoves.size() == 0);
        // move pawn in front of the queen
        MoveMaker transit = board.currentPlayer().makeMove(new Move.PawnMove(board, new Pawn(51, WHITE), 43));
        assertTrue(transit.getMoveStatus() == MoveStatus.DONE);
        board = transit.getBoard();
        //ArrayList<Move> d= (ArrayList<Move>) queenBee.calculateMoves(board);
        assertTrue(board.calculatePieceMove(queenBee, WHITE).size() == 1); //queen should have 1 move
       // assertTrue(d.get(0).getDestinationCoordinate() == 51);
        transit = board.currentPlayer().makeMove(new Move.PawnMove(board, new Pawn(52, WHITE), 44));
        board = transit.getBoard();
        //System.out.println(board);
        board.calculatePieceMove(queenBee, WHITE);
        assertFalse(board.calculatePieceMove(queenBee, WHITE).size() == 0);

    }
}