package engine.tests.pieces;

import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.pieces.Pawn;
import engine.pieces.Rook;
import engine.players.MoveMaker;
import engine.players.MoveStatus;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static engine.Color.BLACK;
import static engine.Color.WHITE;
import static engine.board.Board.createInitialBoard;

public class PawnTest extends TestCase {
    @Test
    public void testPawnMoves(){
        System.out.println("test name: " + this.getName());
        Board b = createInitialBoard();

        //first start borad - pawns should have 2 moves - pawn jump and pawn move
        ArrayList<Move> moves= (ArrayList<Move>) b.calculatePieceMove(new Pawn(48, WHITE), WHITE);
        assertTrue(moves.size() == 2);
        assertTrue(moves.contains(new Move.PawnJumpMove(b, new Pawn(48, WHITE), 32)));
        assertTrue(moves.contains(new Move.PawnMove(b, new Pawn(48, WHITE), 40)));

        //check if after first move pawn has only regular move
        MoveMaker moveMake  = b.currentPlayer().makeMove(moves.get(1));
        assertTrue(moveMake.getMoveStatus() == MoveStatus.DONE);
        b = moveMake.getBoard();
        moves = (ArrayList<Move>) b.calculatePieceMove(new Pawn(moveMake.getMove().getDestinationCoordinate(), WHITE), WHITE);
        assertTrue(moves.size() == 1);
        assertTrue(moves.get(0) instanceof Move.PawnMove);
    }

}