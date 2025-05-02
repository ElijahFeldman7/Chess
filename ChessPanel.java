import javax.swing.*;
import java.awt.*;

public class ChessPanel extends JPanel {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = 60;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = squares[i][j];
                if (square.color == Color.WHITE) {
                    g.setColor(java.awt.Color.WHITE);
                } else {
                    g.setColor(java.awt.Color.BLACK);
                }
                g.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
                if (square.piece != null) {
                    g.setColor(java.awt.Color.RED);
                    g.drawString(square.piece.getType(), i * squareSize + 20, j * squareSize + 30);
                }
            }
        }
    }
}
