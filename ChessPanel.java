import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ChessPanel extends JPanel {
    private ChessBoard chessBoard;
    private Piece selectedPiece;
    private int mouseStartX, mouseStartY = 0; // board array coords
    private int dragOffsetX, dragOffsetY;    // offset within the square for drawing drag
    private int dragMouseX, dragMouseY;       // current mouse position while dragging = true
    private boolean dragging = false;
    private Square[][] squares;
    private BufferedImage pieceSheet;

    private Color currentPlayer = Color.WHITE; // white starts
    private List<Piece> capturedWhitePieces = new ArrayList<>(); //we use an arraylist datastructure here because we are adding to the arraylist
    private List<Piece> capturedBlackPieces = new ArrayList<>(); //out of the scope of our class


    private static final int SQUARE_SIZE = 60;
    private static final int BOARD_SIZE = 8 * SQUARE_SIZE;
    private static final int CAPTURED_AREA_HEIGHT = SQUARE_SIZE; // height for captured pieces area
    private static final int CAPTURED_PIECE_SIZE = SQUARE_SIZE / 2; // smaller size for captured pieces
    private static final int PIECE_SHEET_PIECE_SIZE = 333; // approx size of one piece on the sprite sheet
    private static final int PREFERRED_WIDTH = BOARD_SIZE;
    private static final int PREFERRED_HEIGHT = BOARD_SIZE + 2 * CAPTURED_AREA_HEIGHT; // board + 2 captured areas
    private static final int BOARD_Y_OFFSET = CAPTURED_AREA_HEIGHT; // draw board below top captured area

    public ChessPanel() throws IOException {
        chessBoard = new ChessBoard();
        squares = chessBoard.board;
        selectedPiece = null;
        pieceSheet = ImageIO.read(new File("Chess_Pieces_Sprite.png"));
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        JButton button = new JButton("Convert txt File to Position");
        button.addActionListener(new ConvertListener());
        add(button);
    }
    // converts board array coordinates to like h8 or a1
    public static String positionToString(int x, int y) {
         if (x < 0 || x > 7 || y < 0 || y > 7) return null;
        char file = (char) ('a' + x);
        int rank = y + 1;
        return "" + file + rank;
    }


    private int getPieceSheetX(String type) { // returns the x coordinate of the piece on the sprite sheet
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
    private int getPieceSheetY(Color color) { // returns the y coordinate of the piece on the sprite sheet white/black
        return color == Color.WHITE ? 0 : 1;
    }

    private void drawPiece(Graphics g, Piece piece, int xPx, int yPx, int size) {
        if (piece == null || pieceSheet == null) return;
        int sx = getPieceSheetX(piece.getType()); //sprite x and y position
        int sy = getPieceSheetY(piece.getColor());
        if (sx >= 0 && sy >= 0) {
            BufferedImage pieceImg = pieceSheet.getSubimage( //creates a subimage of the sprite sheet
                sx * PIECE_SHEET_PIECE_SIZE, sy * PIECE_SHEET_PIECE_SIZE, PIECE_SHEET_PIECE_SIZE, PIECE_SHEET_PIECE_SIZE
            );
            g.drawImage(pieceImg, xPx, yPx, size, size, null); //draws the image of the piece at given position
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw captured Black pieces at the top
        int capturedBlackX = 5;
        for (Piece p : capturedBlackPieces) {  //for each piece p in arraylist captured black pieces
            drawPiece(g, p, capturedBlackX, 5, CAPTURED_PIECE_SIZE); //scaled smaller and draws it
            capturedBlackX += CAPTURED_PIECE_SIZE + 2; //increment by 2 + size
        }

        // draw captured White pieces at the bottom
        int capturedWhiteX = 5;
        int capturedWhiteY = BOARD_Y_OFFSET + BOARD_SIZE + 5;
         for (Piece p : capturedWhitePieces) {
            drawPiece(g, p, capturedWhiteX, capturedWhiteY, CAPTURED_PIECE_SIZE);
            capturedWhiteX += CAPTURED_PIECE_SIZE + 2;
        }

        // draw the board
        for (int i = 0; i < 8; i++) { // file (x)
            for (int j = 0; j < 8; j++) { // rank (y)
                // we invert j or rank for drawing because origin is top-left
                // and add offset for captured pieces area
                int drawX = i * SQUARE_SIZE;
                int drawY = (7 - j) * SQUARE_SIZE + BOARD_Y_OFFSET;

                // draw square background
                if ((i + j) % 2 == 0) { // checkered pattern based on array indices
                   g.setColor(new Color(181, 136, 99));
                } else {
                   g.setColor(new Color(240, 217, 181));
                }
                g.fillRect(drawX, drawY, SQUARE_SIZE, SQUARE_SIZE);

                // draw piece on square (if not dragging it)
                Square square = squares[i][j];
                if (square.piece != null && !(dragging && selectedPiece == square.piece)) {
                   drawPiece(g, square.piece, drawX, drawY, SQUARE_SIZE);
                }
            }
        }

        // draw the dragging piece last on top
        if (dragging && selectedPiece != null) {
            drawPiece(g, selectedPiece, dragMouseX - dragOffsetX, dragMouseY - dragOffsetY, SQUARE_SIZE);
        }

        // indicate current player turn or winner
        g.setColor(Color.BLACK);
        if (winnerMessage != null) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(winnerMessage, 10, BOARD_Y_OFFSET - 10);
        } else {
            g.drawString(currentPlayer == Color.WHITE ? "White's Turn" : "Black's Turn", 10, BOARD_Y_OFFSET - 10);
        }
    }

    // converts mouse pixel position to the respective square on the board
    private int[] getBoardCoords(int mouseX, int mouseY) {
        // adjust for the board's Y offset
        int boardRelativeY = mouseY - BOARD_Y_OFFSET;
        int x = mouseX / SQUARE_SIZE;
        // calculate y coordinate, making sure it's within board bounds
        int y = 7 - (boardRelativeY / SQUARE_SIZE);
        // ensures coordinates are within the 0-7 range for the array
        if (x < 0 || x > 7 || y < 0 || y > 7 || mouseY < BOARD_Y_OFFSET || mouseY >= BOARD_Y_OFFSET + BOARD_SIZE) {
            return new int[]{-1, -1}; // outside board
        }
        return new int[]{x, y};
    }


    MouseAdapter mouseHandler = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (winnerMessage != null) return; // Don't allow moves after game is over
            int[] coords = getBoardCoords(e.getX(), e.getY());
            int x = coords[0], y = coords[1];

            if (x != -1) { // check if click was on the board
                Square clickedSquare = squares[x][y];
                if (clickedSquare.getPiece() != null && clickedSquare.getPiece().getColor() == currentPlayer) {
                    selectedPiece = clickedSquare.getPiece();
                    mouseStartX = x; // store starting board coordinates
                    mouseStartY = y;
                    dragMouseX = e.getX(); // store starting mouse coordinates
                    dragMouseY = e.getY();
                    // calculate offset from top-left of square to mouse click point
                    int squareDrawX = x * SQUARE_SIZE;
                    int squareDrawY = (7 - y) * SQUARE_SIZE + BOARD_Y_OFFSET;
                    dragOffsetX = e.getX() - squareDrawX;
                    dragOffsetY = e.getY() - squareDrawY;
                    dragging = true;
                    repaint();
                } else {
                    selectedPiece = null; // clicked empty square or opponent's piece
                    dragging = false;
                }
            } else {
                selectedPiece = null; // clicked outside board
                dragging = false;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragging && selectedPiece != null) {
                dragMouseX = e.getX();
                dragMouseY = e.getY();
                repaint(); // update position while dragging
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (dragging && selectedPiece != null) {
                int[] coords = getBoardCoords(e.getX(), e.getY());
                int targetX = coords[0];
                int targetY = coords[1];
                if (targetX != -1) { // dropped on the board
                    String newPosition = positionToString(targetX, targetY);
                    Square targetSquare = squares[targetX][targetY];

                    if (selectedPiece.isValidMove(squares, newPosition) && isMoveLegal(squares, selectedPiece, newPosition)) {
                        Piece pieceOnTargetSquare = targetSquare.getPiece();
                        Piece capturedPiece = null; // track potential capture

                        // check for capture
                        if (pieceOnTargetSquare != null) {
                            if (pieceOnTargetSquare.getColor() != selectedPiece.getColor()) {
                                capturedPiece = pieceOnTargetSquare;
                            } else {
                                dragging = false;
                                selectedPiece = null;
                                repaint();
                                return;
                            }
                        }

                        //check for En Passant capture
                        boolean isEnPassantCapture = false;
                        if (selectedPiece instanceof Pawn &&
                            Math.abs(targetX - mouseStartX) == 1 && // diagonal move
                            targetSquare.getPiece() == null &&       // target square is empty
                            currentEnPassantTarget != null &&
                            currentEnPassantTarget.equals(newPosition)) // moved to the EP target square
                        {
                            int capturedPawnY = mouseStartY;
                            int capturedPawnX = targetX;
                            if(selectedPiece.isInBounds(capturedPawnX, capturedPawnY)) { 
                                capturedPiece = squares[capturedPawnX][capturedPawnY].getPiece();
                                squares[capturedPawnX][capturedPawnY].setPiece(null); // remove pawn captured EP
                                isEnPassantCapture = true;
                                System.out.println("En Passant Capture Executed!");
                            } else {
                                System.err.println("Error: en Passant capture logic failed boundary check.");
                                capturedPiece = null;
                            }
                        }

                        // add captured piece to the array list
                        if (capturedPiece != null) {
                            if (capturedPiece.getColor() == Color.WHITE) {
                                capturedWhitePieces.add(capturedPiece);
                            } else {
                                capturedBlackPieces.add(capturedPiece);
                            }
                        }

                        squares[mouseStartX][mouseStartY].setPiece(null); //remove from past square
                        targetSquare.setPiece(selectedPiece);             // place piece on new square
                        selectedPiece.position = newPosition;             // update piece's position
                        selectedPiece.hasMoved = true;                    // mark piece as moved
                        // pawn promotion
                        if (selectedPiece instanceof Pawn) {
                            if ((selectedPiece.getColor() == Color.WHITE && targetY == 7) || // white reaches rank 8
                                (selectedPiece.getColor() == Color.BLACK && targetY == 0)) { // black reaches rank 1
                                // default promotion to queen for simplicity
                                Queen promotedQueen = new Queen(newPosition, selectedPiece.getColor());
                                promotedQueen.hasMoved = true; // promoted piece counts as having moved
                                targetSquare.setPiece(promotedQueen);
                            }
                        }
                        // castling
                        if (selectedPiece instanceof King && Math.abs(targetX - mouseStartX) == 2) {
                            int rookStartY = mouseStartY; // same rank as king
                            Piece rookToMove = null;
                            int rookStartX, rookEndX;

                            if (targetX == 6) { // kingside Castle (e1g1 or e8g8)
                                rookStartX = 7; // h file
                                rookEndX = 5;   // f file
                            } else { // Queenside Castle (e1c1 or e8c8)
                                rookStartX = 0; // a file
                                rookEndX = 3;   // d file
                            }

                            rookToMove = squares[rookStartX][rookStartY].getPiece();
                            if (rookToMove instanceof Rook) {
                                squares[rookStartX][rookStartY].setPiece(null);
                                squares[rookEndX][rookStartY].setPiece(rookToMove);
                                rookToMove.position = positionToString(rookEndX, rookStartY);
                                rookToMove.hasMoved = true;
                                System.out.println("Castling Rook Moved!"); //debugging
                            } else {
                                 System.err.println("Error: Castling logic failed");
                            }
                        }
                        //alternate turn
                        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

                        // Check for checkmate after the move
                        checkForCheckmate();

                    } //end if valid move
                } // end if target on board
            } // end if dragging

            // reset dragging state regardless of move validity
            dragging = false;
            selectedPiece = null;
            repaint(); // redraw the board in its final state for the turn
        }
    };
    private class ConvertListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Button clicked!");
            try {
                Scanner scanner = new Scanner(new File("chess.txt"));
                if (scanner.hasNextLine()) {
                    String fen = scanner.nextLine();
                    loadFEN(fen);
                    scanner.close();
                }

            } catch (FileNotFoundException e1) {
                System.out.println("file not found ");
            }

        }
    }
}