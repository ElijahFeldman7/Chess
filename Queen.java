import java.awt.Color;

public class Queen extends Piece {
    public Queen(String position, Color color) {
        super(position, color);
    }

    @Override
    public String getType() {
        return "Queen";
    }

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target = getCoordinates(newPosition);

        boolean straight = (current[0] == target[0] || current[1] == target[1]);
        boolean diagonal = (Math.abs(current[0] - target[0]) == Math.abs(current[1] - target[1]));

        if (!straight && !diagonal) return false;

        if (!isPathClear(board, current[0], current[1], target[0], target[1])) return false;

        Piece destPiece = board[target[0]][target[1]].getPiece();
        return destPiece == null || isOpponentPiece(board[target[0]][target[1]]);
    }
}
