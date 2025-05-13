import java.awt.Color;

public class King extends Piece {
    public static boolean isInCheck;

    public King(String position, Color color) {
        super(position, color);
    }

    @Override
    public String getType() {
        return "King";
    }

    private boolean isSquareUnderAttack(Square[][] board, int x, int y) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j].getPiece();
                if (piece != null && piece.getColor() != this.color) {
                    Piece tempPiece = board[x][y].getPiece();
                    board[x][y].setPiece(null);
                    String targetPos = ChessPanel.positionToString(x, y);
                    boolean canAttack = piece.isValidMove(board, targetPos);

                    board[x][y].setPiece(tempPiece);

                    if (canAttack) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheck(Square[][] board) {
        int[] coords = getCoordinates(position);
        return isSquareUnderAttack(board, coords[0], coords[1]);
    }

    public boolean isInCheckmate(Square[][] board) {
        if (!isInCheck(board)) {
            return false;
        }

        int[] coords = getCoordinates(position);
        int x = coords[0];
        int y = coords[1];

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; 
                int newX = x + dx;
                int newY = y + dy;

                if (isInBounds(newX, newY)) {
                    String newPos = ChessPanel.positionToString(newX, newY);
                    if (isValidMove(board, newPos)) {
                        Piece originalPiece = board[newX][newY].getPiece();
                        board[newX][newY].setPiece(this);
                        board[x][y].setPiece(null);

                        boolean stillInCheck = isInCheck(board);

                        board[x][y].setPiece(this);
                        board[newX][newY].setPiece(originalPiece);

                        if (!stillInCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j].getPiece();
                if (piece != null && piece.getColor() == this.color && !(piece instanceof King)) {
                    for (int targetX = 0; targetX < 8; targetX++) {
                        for (int targetY = 0; targetY < 8; targetY++) {
                            String newPos = ChessPanel.positionToString(targetX, targetY);
                            if (piece.isValidMove(board, newPos)) {
                                Piece capturedPiece = board[targetX][targetY].getPiece();
                                board[targetX][targetY].setPiece(piece);
                                board[i][j].setPiece(null);

                                boolean stillInCheck = isInCheck(board);

                                board[i][j].setPiece(piece);
                                board[targetX][targetY].setPiece(capturedPiece);

                                if (!stillInCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target = getCoordinates(newPosition);

        int dx = Math.abs(current[0] - target[0]);
        int dy = Math.abs(current[1] - target[1]);

        // normal king move
        if (dx <= 1 && dy <= 1) {
            Piece destPiece = board[target[0]][target[1]].getPiece();
            // Can only move to empty square or capture opponent's piece
            if (destPiece == null || isOpponentPiece(board[target[0]][target[1]])) {
                // Temporarily move the king to check if it would be in check
                Piece originalPiece = board[target[0]][target[1]].getPiece();
                board[target[0]][target[1]].setPiece(this);
                board[current[0]][current[1]].setPiece(null);

                boolean wouldBeInCheck = isInCheck(board);

                // Move the king back
                board[current[0]][current[1]].setPiece(this);
                board[target[0]][target[1]].setPiece(originalPiece);

                return !wouldBeInCheck;
            }
            return false;
        }

        // castling
        if (!hasMoved && dy == 0 && dx == 2) {
            int rookX = (target[0] == 6) ? 7 : 0;
            Piece rook = board[rookX][current[1]].getPiece();
            if (rook instanceof Rook && !rook.hasMoved()) {
                int step = (target[0] - current[0]) / 2;
                // Check path clear
                if (isPathClear(board, current[0], current[1], rookX, current[1])) {
                    // Check if king is in check or would pass through check
                    if (!isInCheck(board)) {
                        int intermediateX = current[0] + step;
                        String intermediatePos = ChessPanel.positionToString(intermediateX, current[1]);
                        Piece originalPiece = board[intermediateX][current[1]].getPiece();
                        board[intermediateX][current[1]].setPiece(this);
                        board[current[0]][current[1]].setPiece(null);

                        boolean wouldBeInCheck = isInCheck(board);

                        // Move the king back
                        board[current[0]][current[1]].setPiece(this);
                        board[intermediateX][current[1]].setPiece(originalPiece);

                        return !wouldBeInCheck;
                    }
                }
            }
        }
        return false;
    }
}
