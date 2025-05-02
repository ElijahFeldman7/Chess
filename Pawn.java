
public class Pawn extends Piece {
    private String position;
    private String type = "Pawn";
    private String color;
    private boolean hasMoved = false;
    private Square[][] board;

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
    public void validMoves(Square[][] board) {
        if(King.isInCheck == true){
            return;
        }
        if(color.equals("white)"){
            if(!hasMoved) {
                
            }
        }
        
}

