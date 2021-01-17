package engine.tests.board;

import engine.board.Tile;
import engine.pieces.Knight;
import org.junit.Test;
///import static org.junit.Assert.*;
import static engine.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    Tile emptyTile = Tile.createTile(1, null);
    Tile occupiedTile = Tile.createTile(1, new Knight(1 , WHITE));
    String message = "bye";
    @Test
    public void TestSomething(){
        String testMessage = "hello";
        assertEquals(testMessage,message, "compare strings");

        //assertFalse(emptyTile.isTileOccupied(), "should be false");
        //assertTrue(occupiedTile.isTileOccupied(), "should be true");
    }
}