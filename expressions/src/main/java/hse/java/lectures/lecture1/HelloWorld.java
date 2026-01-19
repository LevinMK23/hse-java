package hse.java.lectures.lecture1;

import javax.swing.*;
import java.awt.*;

public class HelloWorld {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Oh my God!!!");
        frame.setSize(300, 300);

        JPanel panel = new JPanel(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {
            panel.add(new JButton());
        }
        frame.add(panel);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
