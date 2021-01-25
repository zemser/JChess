package engine.tests.other;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestCastling.class,
        TestCheckMate.class,
        TestStaleMate.class,
        TestPawnEnPassant.class,
        TestPawnPromotion.class})

public class TestSuit {
}
