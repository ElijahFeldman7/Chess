public class ChessPanel {
    private ChessBoard chessBoard;
    private Piece selectedPiece;
    private Square[][] squares;

    public ChessPanel() {
        chessBoard = new ChessBoard();
        squares = chessBoard.board;
        selectedPiece = null;
    }

    public void selectPiece(int x, int y) {
        if (selectedPiece == null) {
            selectedPiece = squares[x][y].getPiece();
        } else {
            selectedPiece.move(squares, squares[x][y].getPosition());
            selectedPiece = null;
        }
    }

    public void draw() {
      
    }
}
