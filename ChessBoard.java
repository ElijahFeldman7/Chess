import java.awt.Color;

public class ChessBoard {
    public Square[][] board;
    public ChessBoard() {
        board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j, (i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
            }
        }
        // Initialize pieces
        // Kings
        board[4][0].setPiece(new King("e1", Color.WHITE));
        board[4][7].setPiece(new King("e8", Color.BLACK));
        // Queens
        board[3][0].setPiece(new Queen("d1", Color.WHITE));
        board[3][7].setPiece(new Queen("d8", Color.BLACK));
        // Rooks
        board[0][0].setPiece(new Rook("a1", Color.WHITE));
        board[7][0].setPiece(new Rook("h1", Color.WHITE));
        board[0][7].setPiece(new Rook("a8", Color.BLACK));
        board[7][7].setPiece(new Rook("h8", Color.BLACK));
        // Knights
        board[1][0].setPiece(new Knight("b1", Color.WHITE));
        board[6][0].setPiece(new Knight("g1", Color.WHITE));
        board[1][7].setPiece(new Knight("b8", Color.BLACK));
        board[6][7].setPiece(new Knight("g8", Color.BLACK));
        // Bishops
        board[2][0].setPiece(new Bishop("c1", Color.WHITE));
        board[5][0].setPiece(new Bishop("f1", Color.WHITE));
        board[2][7].setPiece(new Bishop("c8", Color.BLACK));
        board[5][7].setPiece(new Bishop("f8", Color.BLACK));
        // Pawns
        for (int i = 0; i < 8; i++) {
            board[i][1].setPiece(new Pawn("" + (char)('a' + i) + "2", Color.WHITE));
            board[i][6].setPiece(new Pawn("" + (char)('a' + i) + "7", Color.BLACK));
        }
    }
}
