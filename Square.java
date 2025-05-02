import java.awt.Color;

public class Square {
    public int x;
    public int y;
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
    public String getPosition() {
        char file = (char) ('a' + x);
        int rank = 8 - y;
        return "" + file + rank;
    }

}
