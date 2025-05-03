import java.awt.Color;

public class Pawn extends Piece {
    public Pawn(String position, Color color) {
        super(position, color);
    }

    @Override
    public String getType() {
        return "Pawn";
    }

    @Override
    public boolean isValidMove(Square[][] board, String newPosition) {
        int[] current = getCoordinates(position);
        int[] target  = getCoordinates(newPosition);

        // check if target coordinates are valid indices
        if (!isInBounds(target[0], target[1])) {
            return false;
        }

        // white moves up so +1 black moves down so -1
        int direction = (color == Color.WHITE) ? +1 : -1;
        // starting ranks: white pawns start on rank 2, black on rank 7. remember index vs rank is different so 1 and 6
        int startRow  = (color == Color.WHITE) ? 1 : 6;

        int dx = target[0] - current[0];
        int dy = target[1] - current[1]; // difference in array index

        //single square forward
        if (dx == 0 && dy == direction) {
            return board[target[0]][target[1]].getPiece() == null;
        }

        // double square forward from start row
        if (dx == 0 && dy == 2*direction && current[1] == startRow) {
            int midY = current[1] + direction;
             // Check bounds for intermediate square
            if (!isInBounds(target[0], midY)) return false;
            return board[target[0]][target[1]].getPiece() == null
                && board[target[0]][midY].getPiece()      == null;
        }

        // diagonal capture with en pessant
        if (Math.abs(dx) == 1 && dy == direction) {
            Square dest = board[target[0]][target[1]];
            // normal capture
            if (dest.getPiece() != null && isOpponentPiece(dest)) {
                return true;
            }
            String potentialEPCoord = ChessPanel.positionToString(target[0], target[1]);
             if (ChessPanel.enPassantTarget != null
                 && ChessPanel.enPassantTarget.equals(potentialEPCoord)
                 && dest.getPiece() == null) { // must land on empty square for en passant
                 // check if the piece being captured en passant is actually there
                 int capturedPawnY = current[1]; // same rank as the moving pawn started on
                 int capturedPawnX = target[0]; // same file as the target square
                 if (isInBounds(capturedPawnX, capturedPawnY)) {
                     Piece capturedPawn = board[capturedPawnX][capturedPawnY].getPiece();
                     return capturedPawn != null && capturedPawn.getColor() != this.color && capturedPawn.getType().equals("Pawn");
                 }
            }
        }

        return false;
    }
}