package engine.players;

import com.google.common.collect.ImmutableList;
import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.board.Move.KingSizeCastleMove;
import engine.board.Move.QueenSizeCastleMove;
import engine.board.Tile;
import engine.pieces.Piece;
import engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board, final Collection<Move> whitePlayerMoves, final Collection<Move> blackPlayerMoves) {
        super(board, whitePlayerMoves, blackPlayerMoves);
    }

    /**
     * @return collection of all the white pieces on the board
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    /**
     * @return the color of the player - white
     */
    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    /**
     * @return the opponent player object - the black player
     */
    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    /**
     * The king can make a castle move if he and the rook not moved yet, king is not in check,
     * the tiles between the king and rook are free and are not threatened
     * @param playerLegals  collection of the legal moves the player can make
     * @param opponentLegals collection of the opponents legal moves
     * @return Collection of the legal castling moves
     */
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playersKings.isFirstMove() && !this.isInCheck()) {
            // King side castle
            if(!board.getTile(61).isTileOccupied() && !board.getTile(62).isTileOccupied()){ //lane between king and rook is clear
                final Tile rookTile = this.board.getTile(63);  //get tile that the rook should reside on
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    //check that free lane between king and rook is threatened
                    if(calculateAttackOnTile(61, opponentLegals).isEmpty() && calculateAttackOnTile(62, opponentLegals).isEmpty()){
                        kingCastles.add(new KingSizeCastleMove(this.board, this.playersKings, 62, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }

                }
            }
            // Queen side castle
            if(!board.getTile(59).isTileOccupied() && !board.getTile(58).isTileOccupied() && !board.getTile(57).isTileOccupied()){ //lane between king and rook is clear
                final Tile rookTile = this.board.getTile(56);  //get tile that the rook should reside on
                if(rookTile.isTileOccupied() && rookTile.getPiece().getPieceType().isRook() && rookTile.getPiece().isFirstMove()){
                    //check that free lane between king and rook is threatened
                    if(calculateAttackOnTile(59, opponentLegals).isEmpty() && calculateAttackOnTile(58, opponentLegals).isEmpty()
                        && calculateAttackOnTile(57, opponentLegals).isEmpty()){
                        kingCastles.add(new QueenSizeCastleMove(this.board, this.playersKings, 58, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }

                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public String toString() {
        return "White";
    }
}
