package engine.pieces;

import com.google.common.collect.ImmutableList;
import engine.Color;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Move;
import engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static engine.board.BoardUtils.isValidCoordinate;

public class King extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDIANTES = {-1, 1, 7, -7, 7, 8, -8, 9, -9}; // list of all optaional moves the piece can make
    /**
     * @param pos position of the King on the board
     * @param color color of the King piece
     * @return King object subclass of Piece
     * */
    public King(final int pos, final Color color) {
        super(pos, color, PieceType.KING, true);
    }
    public King(final int pos, final Color color, boolean isFirstMove) {
        super(pos, color, PieceType.KING, isFirstMove);
    }


    /**
     * Checks of all the possbile moves the King can make on the board, verifies if the tile is empty or has an enemy piece
     * add the move to the list.
     * @param board the game board
     * @return return the list of all the possible moves
     */
    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(int coordinateOffset : CANDIDATE_MOVE_COORDIANTES) {
            if(isEdgeCaseForMove(this.position, coordinateOffset)) continue;
            int candidateDestCoordinate = this.position + coordinateOffset;
            if(BoardUtils.isValidCoordinate(candidateDestCoordinate)){
                final Piece pieceAtDest = board.getTile(candidateDestCoordinate).getPiece();
                if(pieceAtDest == null){
                    legalMoves.add(new Move.NeutralMove(board, this, candidateDestCoordinate));
                } else if(this.pieceColor == pieceAtDest.getPieceColor()){
                    legalMoves.add(new Move.AttackMove(board, this, candidateDestCoordinate, pieceAtDest));
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /**
     * check if the postion is in edge case for the desired offset move, if true it cant make that offset move.
     * @param piecePos - current position of the piece
     * @param coordianteOffset - offset the piece wants to move
     * @return return true if the piece is in an edge case for given offset coordinate
     */
    public boolean isEdgeCaseForMove(final int piecePos, final int coordianteOffset){
        if(piecePos % 8 == 0 && (coordianteOffset == -9 || coordianteOffset == -1 || coordianteOffset == 7)){
            return true;
        }
        if(piecePos % 8 == 7 && (coordianteOffset == 9 || coordianteOffset == 1 || coordianteOffset == -7)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    /**
     * Move the piece - we create a new instacne of the piece with an updated position
     * @param move represents the move the piece makes
     * @return new piece with the updated destination
     */
    @Override
    public King movePiece(Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }
}
