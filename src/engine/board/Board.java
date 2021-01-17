package engine.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import engine.Color;
import engine.pieces.*;
import engine.players.BlackPlayer;
import engine.players.Player;
import engine.players.WhitePlayer;

import java.util.*;

public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;


    private Board(final Builder builder){
        this.gameBoard = createBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Color.WHITE);  // get collections of all the white piece on the board
        this.blackPieces = calculateActivePieces(this.gameBoard, Color.BLACK);  // get collections of all the black piece on the board
        //need to add legal moves
        final Collection<Move> whitePlayerMoves = calculatePlayerMoves(this.whitePieces); // collection of the moves the combined white pieces can make
        final Collection<Move> blackPlayerMoves = calculatePlayerMoves(this.blackPieces); // collection of the moves the combined black pieces can make


        this.whitePlayer = new WhitePlayer(this, whitePlayerMoves, blackPlayerMoves);  // create white player object
        this.blackPlayer = new BlackPlayer(this, blackPlayerMoves, whitePlayerMoves);  // create black player object
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    /**
     * gett for the current player on the board
     * @return Player object - the next player to make a move
     */
    public Player currentPlayer(){
        return this.currentPlayer;
    }

    /**
     * @param pieces collection of pieces
     * @return list of all possible moves for all the pieces
     */
    private Collection<Move> calculatePlayerMoves(Collection<Piece> pieces) {
        Collection<Move> possbileMoves = new ArrayList<>();
        for(Piece piece : pieces){
            possbileMoves.addAll(piece.calculateMovess(this));

        }
        return possbileMoves;
    }

    /**
     * Go through all tiles of the board and add the pieces that are of given color
     * @param gameBoard
     * @param color
     * @return all active pieces of given color on the board
     */
    private Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Color color) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile : gameBoard){
            if(tile.isTileOccupied()){
                if(tile.getPiece().getPieceColor() == color)
                    activePieces.add(tile.getPiece());
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    private static List<Tile> createBoard(Builder builder) {
        final Tile [] board = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            board[i] = Tile.createTile(i, builder.boardConfig.get(i));  // if no piece is on the i tile then board[i] will be an empty tile
        }
        return ImmutableList.copyOf(board);
    }

    /**
     * Create an instance of our board Builder, then sets all the necessary fields:
     * pieces in their initial position
     * color of starting player -white
     * @return a new instance of Board with all the initial settings
     */
    public static Board createInitialBoard(){
        final Builder builder = new Builder();
        //set up black pieces
        builder.setPiece(new Rook(0, Color.BLACK));
        builder.setPiece(new Knight(1, Color.BLACK));
        builder.setPiece(new Bishop(2, Color.BLACK));
        builder.setPiece(new Queen(3, Color.BLACK));
        builder.setPiece(new King(4, Color.BLACK));
        builder.setPiece(new Bishop(5, Color.BLACK));
        builder.setPiece(new Knight(6, Color.BLACK));
        builder.setPiece(new Rook(7, Color.BLACK));
        builder.setPiece(new Pawn(8, Color.BLACK));
        builder.setPiece(new Pawn(9, Color.BLACK));
        builder.setPiece(new Pawn(10, Color.BLACK));
        builder.setPiece(new Pawn(11, Color.BLACK));
        builder.setPiece(new Pawn(12, Color.BLACK));
        builder.setPiece(new Pawn(13, Color.BLACK));
        builder.setPiece(new Pawn(14, Color.BLACK));
        builder.setPiece(new Pawn(15, Color.BLACK));
        // Set up white pieces
        builder.setPiece(new Pawn(48, Color.WHITE));
        builder.setPiece(new Pawn(49, Color.WHITE));
        builder.setPiece(new Pawn(50, Color.WHITE));
        builder.setPiece(new Pawn(51, Color.WHITE));
        builder.setPiece(new Pawn(52, Color.WHITE));
        builder.setPiece(new Pawn(53, Color.WHITE));
        builder.setPiece(new Pawn(54, Color.WHITE));
        builder.setPiece(new Pawn(55, Color.WHITE));
        builder.setPiece(new Rook(56, Color.WHITE));
        builder.setPiece(new Knight(57, Color.WHITE));
        builder.setPiece(new Bishop(58, Color.WHITE));
        builder.setPiece(new Queen(59, Color.WHITE));
        builder.setPiece(new King(60, Color.WHITE));
        builder.setPiece(new Bishop(61, Color.WHITE));
        builder.setPiece(new Knight(62, Color.WHITE));
        builder.setPiece(new Rook(63, Color.WHITE));
        //white to move first
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }

    public Tile getTile(int coordinate){
        return gameBoard.get(coordinate);
    }

    public Player getBlackPlayer(){
        return this.blackPlayer;
    }

    public Player getWhitePlayer(){
        return this.whitePlayer;
    }

    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    }

    @Override
    public String toString() {
        final StringBuilder boardString = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            final String tileString = this.gameBoard.get(i).toString();
            boardString.append(String.format("%3s", tileString));
            if((i % BoardUtils.NUM_TILES_PER_ROW == 7)){
                boardString.append("\n");
            }
        }
        return boardString.toString();
    }

    /**
     * @return collection of all the moves the white and the black player can make
     */
    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }


    /***
     * Builder static class - we create the booard with the builder. it sets all the fields and configuration and then
     * creates and new board that is initialized with the help of the fields of the builder
     * We simply call static method createInitialBoard to create a new board.
     */
    public static class Builder{
        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;
        Pawn enPassantPawn;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        /**
         * set the piece with its position on our builder board
         * @param piece
         * @return instance of the builder - this is a design pattern
         */
        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        /**
         * set the color of the next players move on our builder
         * @param color
         * @return instance of the builder - this is a design pattern
         */
        public Builder setMoveMaker(Color color){
            this.nextMoveMaker = color;
            return this;
        }

        /**
         * Create a new board by passing the builder instance to the board constructor, it will use the builder field to create a new board
         * @return return a new instance of board
         */
        public Board build(){
            return new Board(this);
        }

        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }
}
