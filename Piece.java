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

    // converts "e4" -> 4, 3 (x, y array index)
    protected int[] getCoordinates(String position) {
        if (position == null || position.length() != 2) {
             System.err.println("Invalid position format: " + position);
             return new int[]{-1, -1}; //error case
        }
        int x = position.charAt(0) - 'a';
        int y = Character.getNumericValue(position.charAt(1)) - 1; // rank 1 is y=0, rank 8 is y=7
        return new int[]{x, y}; // returns coords
    }

    protected boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    protected boolean isOpponentPiece(Square square) {
        return square.getPiece() != null && square.getPiece().getColor() != this.color;
    }

    // checks if path is clear aside from target square
    protected boolean isPathClear(Square[][] board, int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX);
        int dy = Integer.compare(endY, startY);
        int x = startX + dx;
        int y = startY + dy;

        while (x != endX || y != endY) {
             if (!isInBounds(x, y)) return false; // path goes out of bounds
            if (board[x][y].getPiece() != null) {
                return false; // blocked
            }
            x += dx;
            y += dy;
        }
        return true; // path is clear
    }
}