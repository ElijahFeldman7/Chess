import java.io.IOException;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);
        ChessPanel chessPanel = new ChessPanel();
        frame.add(chessPanel);
        frame.setVisible(true);
    }
}