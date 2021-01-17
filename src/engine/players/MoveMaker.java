package engine.players;

import engine.board.Board;
import engine.board.Move;

public class MoveMaker {
    private final Board moveBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveMaker(final Board moveBoard, final Move move, final MoveStatus moveStatus){
        this.moveBoard = moveBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    /**
     * @return moveStatus of the move
     */
    public MoveStatus getMoveStatus(){
        return this.moveStatus;
    }

    public Board getBoard() {
        return this.moveBoard;
    }
}
