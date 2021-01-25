package engine.pieces;

import com.google.common.collect.ImmutableList;
import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.board.Move.PawnJumpMove;
import engine.board.Move.PawnMove;
import engine.board.Move.PawnPromotionMove;
import engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static engine.board.BoardUtils.isValidCoordinate;

public class Pawn extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDIANTES = {8, 16, 7, 9}; // list of all optaional moves the piece can make
    /**
     * @param pos position of the Pawn on the board
     * @param color color of the Pawn piece
     * @return Pawn object subclass of Piece
     * */
    public Pawn(final int pos, final Color color) {
        super(pos, color, PieceType.PAWN, true);
    }
    public Pawn(final int pos, final Color color, boolean isFirstMove) {
        super(pos, color, PieceType.PAWN, isFirstMove);
    }

    /**
     * Checks of all the possbile moves the pawn can make on the board and if the tile is empty or has an enemy piece add the move to the list
     * @param board the game board
     * @return return the list of all the possible moves
     */
    @Override
    public Collection<Move> calculateMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(int coordinateOffset : CANDIDATE_MOVE_COORDIANTES) {
            final int candidateDestCordinate = this.position + this.pieceColor.getDirection() * coordinateOffset;
            if(!isValidCoordinate(candidateDestCordinate)) continue;
            if(coordinateOffset == 8 && !(board.getTile(candidateDestCordinate).isTileOccupied())){
                if(this.pieceColor.isPawnPromotionTile(candidateDestCordinate)){
                    legalMoves.add(new PawnPromotionMove(new PawnMove(board, this, candidateDestCordinate), this.getPromotionPiece()));
                }else{
                    legalMoves.add(new PawnMove(board, this, candidateDestCordinate));
                }
            }else if(coordinateOffset == 16 && this.isFirstMove() && isValidPostionForJump(this.position, this.getPieceColor())){
                //check if there is no piece between our position and the destination jump position
                final int middleTileCoordinates = this.position + this.getPieceColor().getDirection() * 8;
                if(!board.getTile(middleTileCoordinates).isTileOccupied() &&
                        !board.getTile(candidateDestCordinate).isTileOccupied() ){
                    legalMoves.add(new PawnJumpMove(board, this, candidateDestCordinate));
                }
            }else if(coordinateOffset == 7){
                if(!isEdgeCaseForAttack(7,this.position,this.pieceColor)){
                    if(board.getTile(candidateDestCordinate).isTileOccupied()){
                        final Piece pieceOnDestCoordinate = board.getTile(candidateDestCordinate).getPiece();
                        if(this.pieceColor != pieceOnDestCoordinate.getPieceColor()){
                            if(this.pieceColor.isPawnPromotionTile(candidateDestCordinate)){
                                legalMoves.add(new PawnPromotionMove(new Move.PawnAttackMove(board, this, candidateDestCordinate, pieceOnDestCoordinate), this.getPromotionPiece()));
                            }else{
                                legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestCordinate, pieceOnDestCoordinate));
                            }
                        }
                    }else if(board.getEnPassantPawn() != null){
                        if(board.getEnPassantPawn().getPosition() == (this.position + this.pieceColor.getOpponentDirection())){
                            final Piece pieceOnCandidate = board.getEnPassantPawn();
                            if(this.pieceColor != pieceOnCandidate.getPieceColor()){
                                legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestCordinate, pieceOnCandidate));
                            }
                        }
                    }
                }
            }else if(coordinateOffset == 9){
                if(!isEdgeCaseForAttack(9,this.position,this.pieceColor)){
                    if(board.getTile(candidateDestCordinate).isTileOccupied()){
                        final Piece pieceOnDestCoordinate = board.getTile(candidateDestCordinate).getPiece();
                        if(this.pieceColor != pieceOnDestCoordinate.getPieceColor()){
                            if(this.pieceColor.isPawnPromotionTile(candidateDestCordinate)){
                                legalMoves.add(new PawnPromotionMove(new Move.PawnAttackMove(board, this, candidateDestCordinate, pieceOnDestCoordinate), this.getPromotionPiece()));
                            }else{
                                legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestCordinate, pieceOnDestCoordinate));
                            }

                        }
                    }else if(board.getEnPassantPawn() != null){  //check for enpassant attack move
                        if(board.getEnPassantPawn().getPosition() == (this.position - this.pieceColor.getOpponentDirection())){
                            final Piece pieceOnCandidate = board.getEnPassantPawn();
                            if(this.pieceColor != pieceOnCandidate.getPieceColor()){
                                legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestCordinate, pieceOnCandidate));
                            }
                        }
                    }
                }
            }


        }
        return ImmutableList.copyOf(legalMoves);

    }

    /***
     * in order to make a jump the piece should be on the second raw from its point of view
     * @param piecePos - coordiantes of the piece, alliance - color of the piece
     * @return true if the piece can make a jump from where it stand
     */
    public boolean isValidPostionForJump(final int piecePos, Color alliance){
        if(alliance == Color.BLACK && (piecePos >= 8 && piecePos <= 15)){
            return true;
        }
        if(alliance == Color.WHITE && (piecePos >= 48 && piecePos <= 55)){
            return true;
        }
        return false;
    }

    /**
     * if the piece is white it cant make attack move offset 7 from last column and attack move offset 9 from first column
     * if the piece is black it cant make attack move offset 7 from first column and attack move offset 9 from last column
     * @param attackMove
     * @param piecePos
     * @param alliance
     * @return return true if the piece is in an edge case for attack moves 7 and 9
     */
    public boolean isEdgeCaseForAttack(final int attackMove, final int piecePos, Color alliance){
        if(attackMove == 7){
            if(alliance == Color.BLACK && (piecePos % 8 == 0))
                return true;
            if(alliance == Color.WHITE && (piecePos % 8 == 7))
                return true;
        }
        if(attackMove == 9){
            if(alliance == Color.BLACK && (piecePos % 8 == 7))
                return true;
            if(alliance == Color.WHITE && (piecePos % 8 == 0))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    /**
     * Move the piece - we create a new instacne of the piece with an updated position
     * @param move represents the move the piece makes
     * @return new piece with the updated destination
     */
    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    public Piece getPromotionPiece() {
        return new Queen(this.position, this.pieceColor, false);
    }
}
