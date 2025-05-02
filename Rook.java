public class Rook {
    private String position;
    private String type = "Rook";
    private String color;
    private boolean hasMoved = false;
    private Square[][] board;
    public Rook(String position) {
        this.position = position;
        this.type = "Rook";
    }

    public String getType() {
        return type;
    }

    public String getPosition() {
        return position;
    }

    public void move(Square[][] board, String newPosition) {
        this.position = newPosition;
    }
}
