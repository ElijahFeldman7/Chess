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

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target = getCoordinates(newPosition);

        int dx = Math.abs(current[0] - target[0]);
        int dy = Math.abs(current[1] - target[1]);

        // normal king move
        if (dx <= 1 && dy <= 1) {
            Piece destPiece = board[target[0]][target[1]].getPiece();
            return destPiece == null || isOpponentPiece(board[target[0]][target[1]]);
        }

        // castling
        if (!hasMoved && dy == 0 && dx == 2) {
            int rookX = (target[0] == 6) ? 7 : 0;
            Piece rook = board[rookX][current[1]].getPiece();
            if (rook instanceof Rook && !rook.hasMoved()) {
                int step = (target[0] - current[0]) / 2;
                // Check path clear
                if (isPathClear(board, current[0], current[1], rookX, current[1])) {
                    // todo: check if the piece is in check also checkmate logic
                    return true;
                }
            }
        }
        return false;
    }
}
