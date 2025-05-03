import java.awt.Color;

public class Rook extends Piece {
    public Rook(String position, Color color) {
        super(position, color);
    }

    @Override
    public String getType() {
        return "Rook";
    }

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target = getCoordinates(newPosition);

        // Must move in a straight line
        if (current[0] != target[0] && current[1] != target[1]) return false;

        // Check path is clear
        if (!isPathClear(board, current[0], current[1], target[0], target[1])) return false;

        // Can't capture own piece
        Piece destPiece = board[target[0]][target[1]].getPiece();
        return destPiece == null || isOpponentPiece(board[target[0]][target[1]]);
    }
}
