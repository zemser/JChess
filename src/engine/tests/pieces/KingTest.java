package engine.tests.pieces;

import engine.pieces.King;
import engine.pieces.Queen;
import junit.framework.TestCase;
import org.junit.Test;

import static engine.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest extends TestCase {
    @Test
    public void testIsEdgeCaseForMove() {
        System.out.println("test name: " + this.getName());
        King arthur = new King(0, WHITE);
        assertTrue(arthur.isEdgeCaseForMove(8, -9) && arthur.isEdgeCaseForMove(8, -1)
                    && arthur.isEdgeCaseForMove(8, 7));
        assertTrue(arthur.isEdgeCaseForMove(15, 9) && arthur.isEdgeCaseForMove(15, 1)
                && arthur.isEdgeCaseForMove(15, -7));
        assertTrue(arthur.isEdgeCaseForMove(40, -9) && arthur.isEdgeCaseForMove(40, -1)
                && arthur.isEdgeCaseForMove(40, 7));
        assertTrue(arthur.isEdgeCaseForMove(47, 9) && arthur.isEdgeCaseForMove(47, 1)
                && arthur.isEdgeCaseForMove(47, -7));
        assertFalse(arthur.isEdgeCaseForMove(48, 9) && arthur.isEdgeCaseForMove(18, 1)
                && arthur.isEdgeCaseForMove(46, -7));

    }

}