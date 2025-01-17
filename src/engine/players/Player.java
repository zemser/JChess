package engine.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.pieces.King;
import engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final Collection<Move> legalMoves;
    protected final King playersKings;
    protected final boolean isInCheck;
    int turn;

    /**
     * Constructor for object Player
     * @param board reference to the board
     * @param legalMoves Collection of moves the player can make
     * @param opponentMoves Collection of move the opponent player can make
     */
    public Player(final Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.playersKings = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttackOnTile(this.playersKings.getPosition(), opponentMoves).isEmpty();
    }

    /**
     *
     * @param position position of a piece
     * @param moves  a list of all opponent's moves
     * @return List of moves that attack the given piece position
     */
    protected static Collection<Move> calculateAttackOnTile(int position, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(position == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    /**
     * go through the pieces of the player and find the king, if there is no king then throw runtime exception
     * @return King piece of the player
     */
    private King establishKing(){
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King)piece;
            }
        }
        throw new RuntimeException("No King on the board! Not a valid board");
    }

    /**
     * check if the move is in the legal moves collection
     * @param move
     * @return true if the move is legal
     */
    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public Move getLegalMove(final int index){
        if(index >= this.legalMoves.size() || index < 0){
            throw new RuntimeException("from getLegalMove in PLAYER - got invalid index");
        }
        int count = 0;
        for(Move m : this.legalMoves){
            if(count++ == index){
                return m;
            }
        }
        throw new RuntimeException("from getLegalMove - shouldn't get here");
    }

    /**
     * illustrates a move on the board: if move illegal status will be ILLEGAL_MOVE | if move is legal and poses a threat on the our king status
     * will be LEAVES_PLAYER_IN_CHECK | if move is legal and not poses threat on the our king status will be DONE
     * @param move Move object - the move the piece wants to take
     * @return MoveMaker object (represents the move on the board)
     */
    public MoveMaker makeMove(final Move move){
        //checks if the move is in the collection of legal moves or moved piece is not of current player color
        if(!isMoveLegal(move) || this.board.currentPlayer().getColor() != move.getMovedPiece().getPieceColor()){
            return new MoveMaker(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttackOnTile(transitBoard.currentPlayer().getOpponent().getPlayerKing().getPosition(),
                transitBoard.currentPlayer().getLegalMoves());   //calculate list of enemy attacking moves on the king
        if(!kingAttacks.isEmpty()){
            return new MoveMaker(this.board,transitBoard, move, MoveStatus.LEAVES_PLAYER_IN_CHECK); //TODO WAS ORIGNALY THIS.BOARD INSTEAD OF TRANSIT
        }
        return  new MoveMaker(this.board, transitBoard, move, MoveStatus.DONE);
    }

    public MoveMaker unMakeMove(final Move move){
        return new MoveMaker(this.board, move.undo(), move, MoveStatus.DONE);
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    protected King getPlayerKing(){
        return this.playersKings;
    }

    /**
     * @return true if the kings is being threat - an opponent piece has an attack move on the king
     */
    public boolean isInCheck(){
        return this.isInCheck;
    }

    /**
     * @return true if the king is threatened and has no escape moves
     */
    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMove();
    }

    public boolean isCastled() {
        return this.playersKings.isCastled();
    }

    /**
     * iterate over all the legal moves
     * @return true if the king has no escape moves
     */
    protected boolean hasEscapeMove(){
        for(final Move move : this.legalMoves){
            final MoveMaker transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }
    /**
     * @return true if the king is not threatened and has no escape moves
     */
    public boolean isInStaleMate(){
        return !this.isInCheck && !hasEscapeMove();
    }

    /**
     * @return collection of all player's active pieces on the board
     */
    public abstract Collection<Piece> getActivePieces();

    public abstract Color getColor();

    public abstract Player getOpponent();

    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,
                                                             Collection<Move> opponentLegals);

    public void move() {

    }

}
