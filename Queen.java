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

        // Check if the move is diagonal or straight
        int dx = Math.abs(target[0] - current[0]);
        int dy = Math.abs(target[1] - current[1]);

        if (dx != dy && dx != 0 && dy != 0) {
            return false;
        }

        // Check if the path is clear
        if (!isPathClear(board, current[0], current[1], target[0], target[1])) {
            return false;
        }

        // Check if the target square is empty or contains an opponent's piece
        Square targetSquare = board[target[0]][target[1]];
        return targetSquare.getPiece() == null || isOpponentPiece(targetSquare);
    }
}
