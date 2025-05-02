import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessPanel extends JPanel {
    private ChessBoard chessBoard;
    private Piece selectedPiece;
    private int mouseStartX, mouseStartY, mouseEndX, mouseEndY = 0;
    private boolean dragging = false;
    private Square[][] squares;

    public ChessPanel() {
        chessBoard = new ChessBoard();
        squares = chessBoard.board;       
        selectedPiece = null;
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void selectPiece(int x, int y) {
        if (selectedPiece == null) {
            selectedPiece = squares[x][y].getPiece();
        } else {
            selectedPiece.move(squares, squares[x][y].getPosition());
            selectedPiece = null;
        }
    }
    //teoman why is this protected?
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
    MouseAdapter mouseHandler = new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
        mouseStartX = e.getX() / 60;
        mouseStartY = e.getY() / 60;
        if (mouseStartX >= 0 && mouseStartX < 8 && mouseStartY >= 0 && mouseStartY < 8) {
            selectPiece(mouseStartX, mouseStartY);
            dragging = true;
        }
    }
    public void mouseReleased(MouseEvent e) {
        mouseEndX = e.getX() / 60;
        mouseEndY = e.getY() / 60;
        if (dragging) {
            dragging = false;
            if (mouseEndX >= 0 && mouseEndX < 8 && mouseEndY >= 0 && mouseEndY < 8) {
                selectPiece(mouseEndX, mouseEndY);
            }
        }
        selectedPiece = null;
        dragging = false;
        repaint();

    }
   };
}
