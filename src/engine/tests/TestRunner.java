package engine.tests;
import engine.pieces.Knight;
import engine.pieces.Piece;
import engine.tests.board.BoardTest;
import engine.tests.board.MoveTest;
import engine.tests.board.TileTest;
import engine.tests.pieces.*;
import engine.tests.players.PlayerTest;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.awt.*;

public class TestRunner {
    public static void main(String[] args) {
//        Result tileResult = JUnitCore.runClasses(TileTest.class);
//        Result pieceResult = JUnitCore.runClasses(PieceTest.class);
       Result pawnResult = JUnitCore.runClasses(PawnTest.class);
//        Result knightResult = JUnitCore.runClasses(KnightTest.class);
//        Result bishopResult = JUnitCore.runClasses(BishopTest.class);
        Result rookResult = JUnitCore.runClasses(RookTest.class);
//        Result queenResult = JUnitCore.runClasses(QueenTest.class);
//        Result kingResult = JUnitCore.runClasses(KingTest.class);
       Result boardResult = JUnitCore.runClasses(BoardTest.class);
        Result playerTest = JUnitCore.runClasses(PlayerTest.class);
        Result moveResult = JUnitCore.runClasses(MoveTest.class);

//        checkResult(tileResult, "Tile");
//        checkResult(pieceResult, "Piece");
         checkResult(pawnResult, "Pawn");
//        checkResult(knightResult, "Knight");
//        checkResult(bishopResult, "Bishop");
        checkResult(rookResult, "Rook");
//        checkResult(queenResult, "Queen");
//        checkResult(kingResult, "King");
         checkResult(boardResult, "Board");
        checkResult(moveResult, "Move");
        checkResult(playerTest, "Player");

    }
    public static void checkResult(Result res, String name){
        if(res.getFailures().size() != 0){
            for (Failure failure : res.getFailures()) {
                System.out.println(String.format("%s Test Failed in %s", name, failure.toString()));
            }
        }else{
            System.out.println(String.format("%s Test was successfull: " + res.wasSuccessful(), name));
        }
    }
}




//    TestSuite suite = new TestSuite(TileTest.class, PieceTest.class);
//    TestResult res = new TestResult();
//          suite.run(res);
//                  System.out.println("Number of test cases = " + res.runCount());
