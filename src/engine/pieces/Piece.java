package engine.pieces;

import engine.board.Board;
import engine.board.Move;
import engine.Color;

import java.util.Collection;
import java.util.Objects;


public abstract class Piece {
        protected final Color pieceColor;
        protected final int position;
        protected final boolean isFirstMove;
        protected final PieceType pieceType;
        private  final int cachedHashCode;
        //boolean dead;

        /**
         * construct an object of type Piece
         * @param pos postion of th piece on the board
         * @param color enum color of the piece - white or black
         * @param pieceType enum of the piece type
         */
        public Piece(final int pos, final Color color, PieceType pieceType, boolean isFirstMove){
                this.position = pos;
                this.pieceColor = color;
                this.isFirstMove = isFirstMove;
                this.pieceType = pieceType;
                this.cachedHashCode = computeHashCode();

        }

        public abstract Collection<Move> calculateMoves(final Board board);

        public Color getPieceColor() {
                return pieceColor;
        }

        public int getPosition() {
                return position;
        }

        public boolean isFirstMove(){
                return this.isFirstMove;
        }

        public abstract Piece movePiece(Move move);

        public PieceType getPieceType(){
                return this.pieceType;
        }

        protected int computeHashCode(){
                int result = this.pieceType.hashCode();
                result = 31 * result + this.pieceColor.hashCode();
                result = 31 * result + this.position;
                result = 31 * result + (this.isFirstMove ? 1 : 0);
                return result;
        }


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Piece piece = (Piece) o;
                return position == piece.getPosition() &&
                        isFirstMove == piece.isFirstMove() &&
                        pieceColor == piece.getPieceColor() &&
                        pieceType == piece.getPieceType();
        }

        @Override
        public int hashCode() {
                return this.cachedHashCode;
        }

        public int getPieceValue() {
                return this.pieceType.getPieceValue();
        }

        /**
         * Enum that indicates the type of the piece, each enums has a string field for print purpose
          */
        public enum PieceType {
                PAWN("P",100),
                KNIGHT("N", 300),
                ROOK("R", 500),
                BISHOP("B",300),
                QUEEN("Q", 500),
                KING("K", 10000);

                private String pieceName;
                private int pieceValue;   //for the comparator - sorting the pieces

                /**
                 * construct a PieceType Enums with the given string
                 * @param PieceString value for the pieceString field of the piece type enum
                 */
                PieceType(final String PieceString, final int pieceValue){
                        this.pieceName = PieceString;
                        this.pieceValue = pieceValue;
                }


                @Override
                public String toString() {
                        return this.pieceName;
                }

                /**
                 * @return value of the String field of the piece name
                 */
                public String getPieceName(){
                        return  this.pieceName;
                }

                /**
                 * @return true if the String of the field pieceName is K meaning it represents a king piece type
                 */
                public boolean isKing(){
                        return this.pieceName == "K" ? true : false;
                }
                public boolean isRook(){
                        return this.pieceName == "R" ? true : false;
                }

                public int getPieceValue() {
                        return this.pieceValue;
                }
        }
}

