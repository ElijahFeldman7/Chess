public class Bishop {
    private String position;
    private String type = "Bishop";
    private String color;
    private Square[][] board;
    public Bishop(String position) {
        this.position = position;
        this.type = "Bishop";
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
