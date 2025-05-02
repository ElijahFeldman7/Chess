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

        // Check if the move is in L-shape (2 squares in one direction, 1 square perpendicular)
        int dx = Math.abs(target[0] - current[0]);
        int dy = Math.abs(target[1] - current[1]);

        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
            return false;
        }

        // Check if the target square is empty or contains an opponent's piece
        Square targetSquare = board[target[0]][target[1]];
        return targetSquare.getPiece() == null || isOpponentPiece(targetSquare);
    }
}
