public class Knight {
    private String position;
    private String type = "Knight";
    private String color;
    private Square[][] board;
    public Knight(String position, Color color) {
        this.color = color;
        this.position = position;
        this.type = "Knight";
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
    public boolean isValidMove(Square[][] board, String newPosition) {
        // Implement the logic to check if the move is valid for a Knight
        // Knights move in an "L" shape: two squares in one direction and then one square perpendicular
        return true; // Placeholder, implement actual logic
    }
}
