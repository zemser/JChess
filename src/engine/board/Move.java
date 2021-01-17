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

    /**
     * Returns a new board that represents the board after we execute the move
     */
    public Board execute(){
        final Builder builder = new Builder();
        //put the player's pieces on the builder board, except the piece we want to move
        for(final Piece piece : this.board.currentPlayer().getActivePiece()){
            if(!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        //put the opponents pieces on the builder board
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePiece()){
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
        public Board execute() {
            return null;
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
    public static final class PawnMove extends Move {
        public PawnMove(final Board board, final Piece movedPiece, final int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }
    }
    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destCoordinate, final Piece attackPiece){
            super(board, movedPiece, destCoordinate, attackPiece);
        }
    }
    public static final class PawnEnPassantMove extends PawnAttackMove {
        public PawnEnPassantMove(final Board board, final Piece movedPiece, final int destCoordinate, final Piece attackPiece){
            super(board, movedPiece, destCoordinate, attackPiece);
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
            for(final Piece piece : this.board.currentPlayer().getActivePiece()){
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            //put the opponents pieces on the builder board
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePiece()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn); //creates a new piece with new coordinate and puts on the builder board
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor()); //switch turn to play to other player
            return builder.build();  //create and return a new board
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
             for(final Piece piece : this.board.currentPlayer().getActivePiece()){
                 if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                     builder.setPiece(piece);
                 }
             }
             //put the opponents pieces on the builder board
             for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePiece()){
                 builder.setPiece(piece);
             }
             builder.setPiece(this.movedPiece.movePiece(this)); //creates a new piece with new coordinate and puts on the builder board - should be the king piece
             builder.setPiece(new Rook(this.destRookCoordinate, this.castleRook.getPieceColor())); //creates a new rook with the positon after the castling
             builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor()); //switch turn to play to other player
             return builder.build();  //create and return a new board
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
    }
    public static final class NullMove extends Move{
        public NullMove() {
            super(null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute the null move!");
        }
    }

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


