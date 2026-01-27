package hse.java.lectures.lecture1;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("XO");

        frame.setSize(300, 300);
        frame.setLocation(200, 200);

        JButton[][] buttons = new JButton[3][3];

        JPanel panel = new JPanel(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.addActionListener(a -> {
                button.setText("X");
            });
            panel.add(button);
        }
        frame.add(panel);
        JDialog dialog;
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
