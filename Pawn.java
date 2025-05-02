public class Pawn extends Piece {
    private String position;
    private String type = "Pawn";

    public Pawn(String position) {
        this.position = position;
        this.type = "Pawn";
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public void move(Square[][] board, String newPosition) {
        this.position = newPosition;
    }
    
}
