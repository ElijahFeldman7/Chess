public abstract class Piece{

public abstract String getType();
public abstract String getPosition();
public abstract void move(Square[][] board, String newPosition);
}