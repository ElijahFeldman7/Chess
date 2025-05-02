import java.awt.Color;

public class Pawn extends Piece {
    private String position;
    private String type = "Pawn";
    private Color color;
    private boolean hasMoved = false;
    private Square[][] board;

    public Pawn(String position, Color color) {
        super(position, color);
    }

    @Override
    public String getType() {
        return "Pawn";
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target = getCoordinates(newPosition);

        final int direction = (color == Color.WHITE) ? -1 : 1;
        int startRow = (color == Color.WHITE) ? 6 : 1;

        // Check if moving forward
        if (current[0] == target[0]) {
            // Normal move (1 square forward)
            if (target[1] - current[1] == direction) {
                return board[target[0]][target[1]].getPiece() == null;
            }
            // First move (2 squares forward)
            if (!hasMoved && current[1] == startRow && target[1] - current[1] == 2 * direction) {
                return board[target[0]][target[1]].getPiece() == null &&
                       board[target[0]][current[1] + direction].getPiece() == null;
            }
            return false;
        }

        // Check if capturing diagonally
        if (Math.abs(target[0] - current[0]) == 1 && target[1] - current[1] == direction) {
            Square targetSquare = board[target[0]][target[1]];
            return targetSquare.getPiece() != null && isOpponentPiece(targetSquare);
        }

        return false;
    }

    @Override
    public void move(Square[][] board, String newPosition) {
        this.position = newPosition;
    }

    public void validMoves(Square[][] board) {
        if(King.isInCheck == true){
            return;
        }
        if(color.equals("white)")){
            if(!hasMoved) {

            }
        }

    }
}

