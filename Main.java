import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        ChessPanel chessPanel = new ChessPanel();
        frame.add(chessPanel);
        frame.setVisible(true);
    }
}