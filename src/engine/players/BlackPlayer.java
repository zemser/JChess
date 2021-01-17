package engine.players;

import com.google.common.collect.ImmutableList;
import engine.Color;
import engine.board.Board;
import engine.board.Move;
import engine.board.Tile;
import engine.pieces.Piece;
import engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {

    public BlackPlayer(final Board board, final Collection<Move> blackPlayerMoves, final Collection<Move> whitePlayerMoves) {
        super(board, blackPlayerMoves, whitePlayerMoves);
    }
    /**
     * @return collection of all the black pieces on the board
     */
    @Override
    public Collection<Piece> getActivePiece() {
        return this.board.getBlackPieces();
    }
    /**
     * @return the color of the player - black
     */
    @Override
    public Color getColor() {
        return Color.BLACK;
    }
    /**
     * @return the opponent player object - the white player
     */
    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
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
            if(!board.getTile(5).isTileOccupied() && !board.getTile(6).isTileOccupied()){ //lane between king and rook is clear
                final Tile rookTile = this.board.getTile(63);  //get tile that the rook should reside on
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    //check that free lane between king and rook is threatened
                    if(calculateAttackOnTile(5, opponentLegals).isEmpty() && calculateAttackOnTile(6, opponentLegals).isEmpty()){
                        kingCastles.add(new Move.KingSizeCastleMove(this.board, this.playersKings, 6, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }

                }
            }
            // Queen side castle
            if(!board.getTile(1).isTileOccupied() && !board.getTile(2).isTileOccupied() && !board.getTile(3).isTileOccupied()){ //lane between king and rook is clear
                final Tile rookTile = this.board.getTile(0);  //get tile that the rook should reside on
                if(rookTile.isTileOccupied() && rookTile.getPiece().getPieceType().isRook() && rookTile.getPiece().isFirstMove()){
                    //check that free lane between king and rook is threatened
                    if(calculateAttackOnTile(1, opponentLegals).isEmpty() && calculateAttackOnTile(2, opponentLegals).isEmpty()
                            && calculateAttackOnTile(3, opponentLegals).isEmpty()){
                        kingCastles.add(new Move.QueenSizeCastleMove(this.board, this.playersKings, 2, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }

                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
