import engine.board.Board;
import gui.Table;

public class ChessRunner {
    public static void main(String[] args) {
        Board board = Board.createInitialBoard();
        System.out.println(board);
        Table table = new Table();
    }
}
