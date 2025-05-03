import java.awt.Color;

public class Knight extends Piece {
    public Knight(String position, Color color) {
        super(position, color);
    }

    @Override
    public String getType() {
        return "Knight";
    }

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target = getCoordinates(newPosition);

        int dx = Math.abs(current[0] - target[0]);
        int dy = Math.abs(current[1] - target[1]);

        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) return false;

        Piece destPiece = board[target[0]][target[1]].getPiece();
        return destPiece == null || isOpponentPiece(board[target[0]][target[1]]);
    }
}
