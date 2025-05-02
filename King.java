import javafx.scene.paint.Color;

public class King {
    private String position;
    private String type = "King";
    private String color;
    private boolean hasMoved = false;
    private boolean isInCheck = false;
    private boolean isInCheckMate = false;
    private boolean isInStaleMate = false;
    private boolean isInDraw = false;
    private Square[][] board;

    public King(String position, Color color) {
        this.color = color.toString();
        this.position = position;
        this.type = "King";
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
