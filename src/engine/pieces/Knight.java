package engine.pieces;

import com.google.common.collect.ImmutableList;
import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.board.Move.NeutralMove;
import engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static engine.board.BoardUtils.isValidCoordinate;

public class Knight extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDIANTES = {-17, -15, -10, -6, 6, 10, 15, 17}; // list of all optaional moves the piece can make

    /**
     * @param pos position of the Knight on the board
     * @param color color of the Knight piece
     * @return Knight object subclass of Piece
     * */
    public Knight(int pos, Color color) {
        super(pos, color, PieceType.KNIGHT, true);
    }
    public Knight(int pos, Color color, boolean isFirstMove) {
        super(pos, color, PieceType.KNIGHT, isFirstMove);
    }

    /**
     * Checks of all the possbile moves the knight can make on the board and if the tile is empty or has an enemy piece add the move to the list
     * @param board the game board
     * @return return the list of all the possible moves
     */
    public Collection<Move> calculateMoves(final Board board){
        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestCordinate;
        for(int coordinate : CANDIDATE_MOVE_COORDIANTES){
            candidateDestCordinate = this.position + coordinate;
            if(isValidDestinationTile(this.position, coordinate)){ //check if valid destination coordinate
                final Tile candidateDest = board.getTile(candidateDestCordinate);
                if(!candidateDest.isTileOccupied()){ //check if tile is not occupied
                    legalMoves.add(new NeutralMove(board, this, candidateDestCordinate));
                }else{ //tile is occupied, so check if there is an enemy piece on that tile
                    final Piece candiatePiece = candidateDest.getPiece();
                    if(candiatePiece.getPieceColor() != this.getPieceColor()){
                        legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestCordinate,candiatePiece));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /**
     *
     * @param curPos current postion of the piece on the board
     * @param offset offset we want to move
     * @return boolean - true if the offset move from our current postion is legal
     */
    public boolean isValidDestinationTile(final int curPos, final int offset){
        int destination = curPos + offset;
        if(!isValidCoordinate(destination)) return false;
        int column = curPos % 8;
        //if we are in the first column
        if(column == 0 && (offset == 15 || offset == 6 || offset == -10 || offset == -17))
            return false;
        //if our current position is in the second column
        if(column == 1 && (offset == 6 || offset == -10 ))
            return false;
        //if our current positiion is in the seventh column
        if(column == 6 && (offset == -6 || offset == 10 ))
            return false;
        //if our current position is in the eighth column
        if(column == 7 && (offset == -15 || offset == -6 || offset == 10 || offset == 17))
            return false;


        return true;
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    /**
     * Move the piece - we create a new instacne of the piece with an updated position
     * @param move represents the move the piece makes
     * @return new piece with the updated destination
     */
    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }
}
