import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChessPanel extends JPanel {
    private ChessBoard chessBoard;
    private Piece selectedPiece;
    private int mouseStartX, mouseStartY, mouseEndX, mouseEndY = 0;
    private boolean dragging = false;
    private Square[][] squares;
    private BufferedImage pieceSheet;
    private static final int SQUARE_SIZE = 60;
    private static final int PIECE_SIZE = 333;

    public ChessPanel() throws IOException {
        chessBoard = new ChessBoard();
        squares = chessBoard.board;
        selectedPiece = null;
        pieceSheet = ImageIO.read(new File("Chess_Pieces_Sprite.png"));
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private int getPieceSheetX(String type) {
        switch (type) {
            case "King":   return 0;
            case "Queen":  return 1;
            case "Bishop": return 2;
            case "Knight": return 3;
            case "Rook":   return 4;
            case "Pawn":   return 5;
            default:       return -1;
        }
    }
    private int getPieceSheetY(Color color) {
        return color == Color.WHITE ? 0 : 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int drawY = 7-j;
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(181, 136, 99));
                } else {
                    g.setColor(new Color(240, 217, 181));
                }
                g.fillRect(i * SQUARE_SIZE, drawY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                Square square = squares[i][j];
                if (square.piece != null && pieceSheet != null) {
                    int sx = getPieceSheetX(square.piece.getType());
                    int sy = getPieceSheetY(square.piece.getColor());
                    if (sx >= 0 && sy >= 0) {
                        BufferedImage pieceImg = pieceSheet.getSubimage(
                            sx * PIECE_SIZE, sy * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE
                        );
                        g.drawImage(pieceImg, i * SQUARE_SIZE, drawY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
                    }
                }
            }
        }
    }

    public void selectPiece(int x, int y) {
        if (selectedPiece == null) {
            if (squares[x][y].getPiece() != null) {
                selectedPiece = squares[x][y].getPiece();
                mouseStartX = x;
                mouseStartY = y;
            }
        } else {
            String newPosition = squares[x][y].getPosition();
            if (selectedPiece.isValidMove(squares, newPosition)) {
                squares[mouseStartX][mouseStartY].setPiece(null);
                squares[x][y].setPiece(selectedPiece);
                selectedPiece.move(squares, newPosition);
            }
            selectedPiece = null;
            repaint();
        }
    }

    MouseAdapter mouseHandler = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            mouseStartX = e.getX() / SQUARE_SIZE;
            mouseStartY = 7 - (e.getY() / SQUARE_SIZE);
            if (mouseStartX >= 0 && mouseStartX < 8 && mouseStartY >= 0 && mouseStartY < 8) {
                selectPiece(mouseStartX, mouseStartY);
                dragging = true;
            }
        }
        public void mouseReleased(MouseEvent e) {
            mouseEndX = e.getX() / SQUARE_SIZE;
            mouseEndY = 7 - (e.getY() / SQUARE_SIZE);
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
