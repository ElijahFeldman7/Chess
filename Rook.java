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

        // Check if the move is straight (either horizontal or vertical)
        if (current[0] != target[0] && current[1] != target[1]) {
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
