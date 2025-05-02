public class Queen {
    private String position;
    private String type = "Queen";
    private String color;
    private Square[][] board;
    
    public Queen(String position) {
        this.position = position;
        this.type = "Queen";
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
