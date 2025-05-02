import javafx.scene.paint.Color;

public class ChessBoard {
    public Square[][] board;
    public ChessBoard() {
        board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j, (i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
            }
        }
    }

}
