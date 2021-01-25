package engine;

import engine.board.BoardUtils;
import engine.players.BlackPlayer;
import engine.players.Player;
import engine.players.WhitePlayer;

public enum Color {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public int getOpponentDirection() {
            return 1;
        }

        /**
         *
         * @param position tile position
         * @return true if the position is in the eight e row of the board
         */
        @Override
        public boolean isPawnPromotionTile(int position) {
            return BoardUtils.isBetween(position, 56, 63);
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public String toString() {
            return "WHITE";
        }
    },
    BLACK{
        @Override
        public int getDirection() {
            return 1;
        }
        @Override
        public int getOpponentDirection() {
            return -1;
        }

        /**
         * @param position tile position
         * @return true if the position is in the first row of the board
         */
        @Override
        public boolean isPawnPromotionTile(int position) {
            return BoardUtils.isBetween(position, 0, 7);
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public String toString() {
            return "BLACK";
        }
    };

    public abstract int getDirection();
    public abstract int getOpponentDirection();
    public abstract boolean isPawnPromotionTile(int position);

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
