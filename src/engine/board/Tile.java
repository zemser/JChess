package engine.board;

import com.google.common.collect.ImmutableMap;
import engine.Color;
import engine.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

import static engine.board.BoardUtils.NUM_TILES;

public abstract class Tile {
    protected final int tileCoordinate; // the coordinate can only be set once, at construction time
    private static final Map<Integer, EmptyTile> empty_tiles = createAllEmptyTiles();


    /**
     * create a map and for each of the 64 coordianted create a new tile and put in the map.
     * @return - the ImmutableMap (using the guava library)
     */
    private  static Map<Integer, EmptyTile> createAllEmptyTiles(){
        Map<Integer, EmptyTile> map = new HashMap<>();
        for(int i = 0; i <NUM_TILES; i++){
            map.put(i, new EmptyTile(i));
        }
        return  ImmutableMap.copyOf(map);
    }

    /**
     * @param titleCoordiante - the coordiante of the piece on the engine.board
     * @param piece - the piece on the tile (if there is none - piece is null)
     * @return the if piece is null return an empty tile - from the existing empty_tiles map, else create a new tile with the piece
     */
    public static Tile createTile(final int titleCoordiante, final  Piece piece){
        if(piece == null)
            return empty_tiles.get(titleCoordiante);
        return new OccupiedTile(titleCoordiante, piece);
    }

    /**
    * Constructor for the Tile Class
    * @return - tile that is associated with the given coordiante
    * */
    public Tile(final int coordinate) {
        this.tileCoordinate = coordinate;
    }

    // abstract function - return boolean which indicated if the tile is occupied
    public abstract boolean isTileOccupied();
    // abstract function - return Piece if there is a piece on the class, else return null
    public abstract Piece getPiece();

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    public static final class EmptyTile extends Tile{
        private EmptyTile(int cor){
            super(cor);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }


        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() {
           return "-";
        }
    }
    public static final class OccupiedTile extends Tile{
        private final Piece piece;
        private OccupiedTile(int cor, Piece piece) {
            super(cor);
            this.piece = piece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.piece;
        }

        @Override
        public String toString() {
            return this.getPiece().getPieceColor() == Color.BLACK ? this.getPiece().toString().toLowerCase() :
                    this.getPiece().toString();
        }
    }


}
