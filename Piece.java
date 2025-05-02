import java.awt.Color;

public abstract class Piece {
    protected String position;
    protected Color color;
    protected boolean hasMoved;

    public Piece(String position, Color color) {
        this.position = position;
        this.color = color;
        this.hasMoved = false;
    }

    public String getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public abstract String getType();
    public abstract boolean isValidMove(Square[][] board, String newPosition);

    public void move(Square[][] board, String newPosition) {
        if (isValidMove(board, newPosition)) {
            this.position = newPosition;
            this.hasMoved = true;
        }
    }

    protected int[] getCoordinates(String position) {
        int x = position.charAt(0) - 'a';
        int y = 8 - Character.getNumericValue(position.charAt(1));
        return new int[]{x, y};
    }

    protected boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    protected boolean isOpponentPiece(Square square) {
        return square.getPiece() != null && square.getPiece().getColor() != this.color;
    }

    protected boolean isPathClear(Square[][] board, int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX);
        int dy = Integer.compare(endY, startY);
        int x = startX + dx;
        int y = startY + dy;

        while (x != endX || y != endY) {
            if (board[x][y].getPiece() != null) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }
}