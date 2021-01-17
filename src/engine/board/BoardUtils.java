package engine.board;

public class BoardUtils {
    public BoardUtils() {
        throw new RuntimeException("Cant call BoardUtils constructor - only use methods of this calls");
    }
    public static final int START_TILE_INDEX = 0;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int NUM_TILES = 64;

    /**
     * @param coordinate
     * @return boolean - true if the coordiante is within the bounds of the board
     */
    public static boolean isValidCoordinate(int coordinate){
        return coordinate >= 0 && coordinate < 64;
    }

    public static boolean isBetween(int val, int left, int right){
        if(val >= left && val <=right){
            return true;
        }
        return false;
    }
}
