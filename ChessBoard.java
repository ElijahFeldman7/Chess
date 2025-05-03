import java.awt.Color;

public class ChessBoard {
    public Square[][] board;

    public ChessBoard() {
        //a board is just an 8x8 array of squares
        board = new Square[8][8];
        for (int i = 0; i < 8; i++) { // x (file index)
            for (int j = 0; j < 8; j++) { // y (rank index)
                // the color of the squares is light if the sum of the indices is even, dark otherwise.
                Color squareColor = ((i + j) % 2 == 0) ? new Color(181, 136, 99) : new Color(240, 217, 181);
                board[i][j] = new Square(i, j, squareColor);
            }
        }
        // manually initialize the pieces. columns are indexed from 0 to 7 (a-h), rows from 0 to 7 (1-8).
        // kings
        board[4][0].setPiece(new King("e1", Color.WHITE));   // e1 -> x=4, y=0
        board[4][7].setPiece(new King("e8", Color.BLACK));   // e8 -> x=4, y=7
        // queens
        board[3][0].setPiece(new Queen("d1", Color.WHITE));  // d1 -> x=3, y=0
        board[3][7].setPiece(new Queen("d8", Color.BLACK));  // d8 -> x=3, y=7
        // the ROOKS
        board[0][0].setPiece(new Rook("a1", Color.WHITE));   // a1 -> x=0, y=0
        board[7][0].setPiece(new Rook("h1", Color.WHITE));   // h1 -> x=7, y=0
        board[0][7].setPiece(new Rook("a8", Color.BLACK));   // a8 -> x=0, y=7
        board[7][7].setPiece(new Rook("h8", Color.BLACK));   // h8 -> x=7, y=7
        // knights
        board[1][0].setPiece(new Knight("b1", Color.WHITE)); // b1 -> x=1, y=0
        board[6][0].setPiece(new Knight("g1", Color.WHITE)); // g1 -> x=6, y=0
        board[1][7].setPiece(new Knight("b8", Color.BLACK)); // b8 -> x=1, y=7
        board[6][7].setPiece(new Knight("g8", Color.BLACK)); // g8 -> x=6, y=7
        // bishops
        board[2][0].setPiece(new Bishop("c1", Color.WHITE)); // c1 -> x=2, y=0
        board[5][0].setPiece(new Bishop("f1", Color.WHITE)); // f1 -> x=5, y=0
        board[2][7].setPiece(new Bishop("c8", Color.BLACK)); // c8 -> x=2, y=7
        board[5][7].setPiece(new Bishop("f8", Color.BLACK)); // f8 -> x=5, y=7
        // pawns
        for (int i = 0; i < 8; i++) {
            // white pawns are on Rank 2 (y=1)
            board[i][1].setPiece(new Pawn(ChessPanel.positionToString(i, 1), Color.WHITE));
            // black pawns are on Rank 7 (y=6)
            board[i][6].setPiece(new Pawn(ChessPanel.positionToString(i, 6), Color.BLACK));
        }
    }
}