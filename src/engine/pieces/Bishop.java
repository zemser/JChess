package engine.pieces;

import com.google.common.collect.ImmutableList;
import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static engine.board.BoardUtils.isValidCoordinate;

public class Bishop extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDIANTES_VECTOR = {-9, -7, 7, 9}; // list of all optaional moves the piece can make

    /**
     * constructor for the Bishop
     * @param pos - position on the board
     * @param color - color of alliance
     */
    public Bishop(final int pos, final Color color) {
        super(pos, color, PieceType.BISHOP, true);
    }
    public Bishop(final int pos, final Color color, boolean isFirstMove) {
        super(pos, color, PieceType.BISHOP, isFirstMove);
    }

    /**
     * Checks of all the possbile moves the bishop can make on the board and if the tile is empty or has an enemy piece
     * add the move to the list.
     * @param board the game board
     * @return return the list of all the possible moves
     */
    @Override
    public Collection<Move> calculateMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(int coordinateOffset : CANDIDATE_MOVE_COORDIANTES_VECTOR) {
            int candidateDestCordinate = this.position;
            while (isValidCoordinate(candidateDestCordinate)) {
                if (!isValidDestinationTile(candidateDestCordinate, coordinateOffset))
                    break;
                candidateDestCordinate += coordinateOffset;
                if (isValidCoordinate(candidateDestCordinate)) {
                    final Tile candidateDestTile = board.getTile(candidateDestCordinate);
                    if (!candidateDestTile.isTileOccupied()) {
                        legalMoves.add(new Move.NeutralMove(board, this, candidateDestCordinate));
                    } else {
                        final Piece candiDatePiece = candidateDestTile.getPiece();
                        if (candiDatePiece.getPieceColor() != this.getPieceColor()) {
                            legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestCordinate, candiDatePiece));
                        }
                        break; //there a piece of ours in that lane
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }


    /**
     * This method checks validation of one tile move. checks if 0<=dest<64 and that we dont go off the board from the right or left side
     * @param curPos current postion of the piece on the board
     * @param offset offset we want to move
     * @return boolean - true if the offset move from our current postion is legal
     */
    public boolean isValidDestinationTile(final int curPos, final int offset){
        int destination = curPos + offset;
        if(!isValidCoordinate(destination)) return false;
        int column = curPos % 8;
        //if we are in the first column
        if(column == 0 && (offset == -9 || offset == 7))
            return false;

        //if our current position is in the eighth column
        if(column == 7 && (offset == -7 || offset == 9))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    /**
     * Move the piece - we create a new instacne of the piece with an updated position
     * @param move represents the move the piece makes
     * @return new piece with the updated destination
     */
    @Override
    public Piece movePiece(Move move) {
        return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }
}
