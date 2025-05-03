import java.awt.Color;

public class Square {
    public int x; // File index 
    public int y; // Rank index 
    public Color color; 
    public Piece piece;

    public Square(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.piece = null;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public Piece getPiece() {
        return piece;
    }

    // Converts array coordinates [x, y] to "e4"
    public String getPosition() {
        if (x < 0 || x > 7 || y < 0 || y > 7) return "Invalid"; // Should not happen
        char file = (char) ('a' + x);
        int rank = y + 1; // y=0 to rank 1
        return "" + file + rank;
    }

    @Override
    public String toString() {
        return getPosition() + (piece == null ? "" : " (" + piece.getType() + ")");
    }
}