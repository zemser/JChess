package engine.board;

import java.util.*;

public class BoardUtils {
    public BoardUtils() {
        throw new RuntimeException("Cant call BoardUtils constructor - only use methods of this calls");
    }
    public static final int START_TILE_INDEX = 0;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int NUM_TILES = 64;

    public static final List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();

    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionCoordinate();


    /**
     * @param coordinate
     * @return boolean - true if the coordiante is within the bounds of the board
     */
    public static boolean isValidCoordinate(int coordinate){
        return coordinate >= 0 && coordinate < 64;
    }

    /**
     * creates a list of all the notations by the order of the tiles
     * @return List with all the algebraic notations
     */
    private static List<String> initializeAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        ));
    }

    /**
     * @return Map that maps between algebraic notations to its corresponding tile number
     */
    private static Map<String, Integer> initializePositionCoordinate() {
        final Map<String, Integer> positionCoordinates = new HashMap<>();
        for(int i = START_TILE_INDEX; i < NUM_TILES; i++){
            positionCoordinates.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionCoordinates);
    }

    public static boolean isBetween(int val, int left, int right){
        if(val >= left && val <=right){
            return true;
        }
        return false;
    }

    /**
     * @param coordinate the coordinate number
     * @return the corresponding algebraic notation
     */
    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }

    /**
     * @param position algebraic notation
     * @return  the corresponding coordinate number
     */
    public static int getCoordinateAtPosition(final String position){
        return POSITION_TO_COORDINATE.get(position);
    }
}
