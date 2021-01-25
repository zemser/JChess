package engine.board;

import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.Rook;

import static engine.board.Board.*;

public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destCoordinate;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    /**
     * Constructor for Move object
     * @param board reference to the board
     * @param movedPiece piece we want to move
     * @param destCoordinate tile coordinate we want to move the piece to
     */
    private Move(final Board board, final Piece movedPiece, final int destCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordinate = destCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    /**
     * Constructor for Move object - used in the NullMove constructor
     * @param board reference to the board
     * @param destCoordinate  tile coordinate we want to move the piece to
     */
    private Move(final Board board, final int destCoordinate){
        this.board = board;
        this.destCoordinate = destCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    protected  int getCurrentCoordinate(){
        return this.movedPiece.getPosition();
    }

    public int getDestinationCoordinate() {
        return this.destCoordinate;
    }

    public boolean isAttackMove(){
        return false;
    }
    public boolean isCastlingMove(){
        return false;
    }
    public Piece getAttackPiece(){
        return null;
    }

    public Board getBoard() {
        return this.board;
    }


    /**
     * Returns a new board that represents the board after we execute the move
     */
    public Board execute(){
        final Builder builder = new Builder();
        //put the player's pieces on the builder board, except the piece we want to move
        for(final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        //put the opponents pieces on the builder board
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this)); //creates a new piece with new coordinate and puts on the builder board
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor()); //switch turn to play to other player
        return builder.build();  //create and return a new board
    }

    public static final class NeutralMove extends Move {
        public NeutralMove(final Board board, final Piece movedPiece, final int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }

        @Override
        public String toString() {
            return this.movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destCoordinate);
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof NeutralMove &&  super.equals(obj);
        }
    }
    public static class AttackMove extends Move{
        Piece attackedPiece;
        public AttackMove(final Board board, final Piece movedPiece, final int destCoordinate, final Piece attackPiece){
            super(board, movedPiece, destCoordinate);
            this.attackedPiece = attackPiece;
        }
        @Override
        /**
         * returns true - this is an attack move
         */
        public boolean isAttackMove() {
            return true;
        }
        @Override
        /**
         * return the piece the move attacks
         */
        public Piece getAttackPiece() {
            return this.attackedPiece;
        }
        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }
        /**
         * override the Move equals by adding a check if obj is instance of AttackMove and their attackedPiece field is equal
         * @param obj obj we want to check if is equal to this attack move
         * @return true if the obj is equal to this attackMove
         */
        @Override
        public boolean equals(final Object obj) {
            if(this == obj){
                return true;
            }
            if(!(obj instanceof AttackMove)){
                return false;
            }
            final AttackMove otherMove = (AttackMove) obj;
            return super.equals(otherMove) && this.attackedPiece.equals(otherMove.getAttackPiece());
        }

        @Override
        public String toString() {
            return this.movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destCoordinate);
        }
    }
    public static class MajorAttackMove extends AttackMove {

        public MajorAttackMove(final Board board, final Piece pieceMoved, final int destinationCoordinate, final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object obj) {
            return this == obj || obj instanceof MajorAttackMove && super.equals(obj);

        }

    }

    public static final class PawnMove extends Move {
        public PawnMove(final Board board, final Piece movedPiece, final int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof  PawnMove && super.equals(obj);
        }

        @Override
        public String toString() {
            return this.movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destCoordinate);
        }
    }
    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destCoordinate, final Piece attackPiece){
            super(board, movedPiece, destCoordinate, attackPiece);
        }

        @Override
        public boolean equals(Object obj) {
            return  this == obj || obj instanceof PawnAttackMove && super.equals(obj);
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPosition()).substring(0, 1) + "x" +
                    BoardUtils.getPositionAtCoordinate(this.destCoordinate);
        }

    }
    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destCoordinate, final Piece attackPiece){
            super(board, movedPiece, destCoordinate, attackPiece);
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof PawnEnPassantAttackMove && super.equals(obj);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            //put the player's pieces on the builder board, except the piece we want to move
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            //put the opponents pieces on the builder board
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                if(!piece.equals(this.getAttackPiece())){
                    builder.setPiece(piece);
                }

            }
            builder.setPiece(this.movedPiece.movePiece(this)); //creates a new piece with new coordinate and puts on the builder board
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor()); //switch turn to play to other player
            return builder.build();  //create and return a new board
        }
    }
    public static final class PawnPromotionMove extends Move{
        final Move decoratedMove;
        final Pawn promotedPawn;
        public PawnPromotionMove(final Move decoratedMove){
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
        }

        /**
         * exectue the move of the pawn (maybe attack or not) and then make the pawn promotion
         * @return a new board after the execution of the move
         */
        @Override
        public Board execute() {
            final Board pawnMovedBoard = this.decoratedMove.execute();
            final Builder builder = new Builder();
            for(final Piece piece : pawnMovedBoard.currentPlayer().getActivePieces()){
                if(!this.promotedPawn.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getColor());  //we already did execute in the this function so keep the color of the next move maker the same
            return builder.build();
        }

        @Override
        public boolean isAttackMove() {
            return this.decoratedMove.isAttackMove();
        }

        @Override
        public Piece getAttackPiece() {
            return this.decoratedMove.getAttackPiece();
        }

        @Override
        public int hashCode() {
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || (obj instanceof PawnPromotionMove) && this.decoratedMove.equals(obj);
        }
    }

    public static final class PawnJumpMove extends Move {
        public PawnJumpMove(final Board board, final Piece movedPiece, final int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            //put the player's pieces on the builder board, except the piece we want to move
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            //put the opponents pieces on the builder board
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn); //creates a new piece with new coordinate and puts on the builder board
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor()); //switch turn to play to other player
            return builder.build();  //create and return a new board
        }

        @Override
        public String toString() {
            return this.movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destCoordinate);
        }
    }

    /**
     * The CastleMove extends the Move class, it had 3 addional field:
     * castleRook - the rook piece that we would castle with the king
     * originalRookCoordinate Rook position before the castle
     * destRookCoordinate - Rook position after the castle
     */
     static abstract class CastleMove extends Move {
        protected final Rook castleRook;
        protected final int originalRookCoordinate;
        protected final int destRookCoordinate;

        public CastleMove(final Board board, final Piece movedPiece, final int destCoordinate,
                          final Rook castleRook, final int originalRookCoordinate, final int destRookCoordinate) {
            super(board, movedPiece, destCoordinate);
            this.castleRook = castleRook;
            this.originalRookCoordinate = originalRookCoordinate;
            this.destRookCoordinate = destRookCoordinate;
        }

         public Rook getCastleRook() {
             return castleRook;
         }

         @Override
         public boolean isCastlingMove() {
             return true;
         }
         @Override
         public Board execute() {
             final Builder builder = new Builder();
             //put the player's pieces on the builder board, except the king and rook that we castle
             for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                 if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                     builder.setPiece(piece);
                 }
             }
             //put the opponents pieces on the builder board
             for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                 builder.setPiece(piece);
             }
             builder.setPiece(this.movedPiece.movePiece(this)); //creates a new piece with new coordinate and puts on the builder board - should be the king piece
             builder.setPiece(new Rook(this.destRookCoordinate, this.castleRook.getPieceColor(), false)); //creates a new rook with the positon after the castling
             builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor()); //switch turn to play to other player
             return builder.build();  //create and return a new board
         }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CastleMove)) return false;
            CastleMove that = (CastleMove) o;
            return super.equals(that) && this.castleRook.equals(that.getCastleRook());
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            return  result * 31 + this.destRookCoordinate;
        }
    }
    public static final class KingSizeCastleMove extends CastleMove{
        public KingSizeCastleMove(final Board board, final Piece movedPiece, final int destCoordinate,
                                    final Rook castleRook, final int originalRookCoordinate, final int destRookCoordinate) {
            super(board, movedPiece, destCoordinate, castleRook, originalRookCoordinate, destRookCoordinate);
        }
        @Override
        public String toString(){
            return "O-O";
        }
        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof KingSizeCastleMove && super.equals(obj);
        }
    }
    public static final class QueenSizeCastleMove extends CastleMove{
        public QueenSizeCastleMove(final Board board, final Piece movedPiece, final int destCoordinate,
                                   final Rook castleRook, final int originalRookCoordinate, final int destRookCoordinate) {
            super(board, movedPiece, destCoordinate,castleRook, originalRookCoordinate, destRookCoordinate);
        }
        @Override
        public String toString(){
            return "O-O-O";
        }
        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof QueenSizeCastleMove && super.equals(obj);
        }
    }
    public static final class NullMove extends Move{
        public NullMove() {
            super(null, 65);
        }

        @Override
        protected int getCurrentCoordinate() {
            return -1;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute the null move!");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NullMove)) return false;
          return true;
        }

    }

    /**
     * Factory class for the Move object
     * given a ref to the board and current coordinate and destination coordinate, go through all the legal moves of the board
     * and return the move that matches the given coordinates, if no such move exits on the board return NULL_MOVE
     */
    public static class MoveFactory{
        private MoveFactory(){
            throw new RuntimeException("Cant create instance of factory");
        }
        public static Move createMove(final Board board, final int curCoordinate, final int destCoordinate){
            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == curCoordinate && move.getDestinationCoordinate() == destCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }

    @Override
    public int hashCode() {
        int result = 31 + this.movedPiece.hashCode();
        result = 31 * result + this.destCoordinate;
        result = 31 * result + this.movedPiece.getPosition();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Move)){
            return false;
        }
        final Move otherMove = (Move) obj;
        return this.getMovedPiece().equals(otherMove.getMovedPiece()) &&
                this.getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                this.getCurrentCoordinate() == otherMove.getCurrentCoordinate();
    }
}


