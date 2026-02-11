package hse.java.lectures.lecture1;

import java.awt.*;
import javax.swing.*;
import java.util.*;


public class Game {
    static final int[][][] lines = {
            {{0,0}, {0,1}, {0,2}},
            {{1,0}, {1,1}, {1,2}},
            {{2,0}, {2,1}, {2,2}},
            {{0,0}, {1,0}, {2,0}},
            {{0,1}, {1,1}, {2,1}},
            {{0,2}, {1,2}, {2,2}},
            {{0,0}, {1,1}, {2,2}},
            {{0,2}, {1,1}, {2,0}}
    };


    public static void main(String[] args) {
        JFrame frame = new JFrame("XO");
        frame.setSize(300, 300);
        frame.setLocation(200, 200);

        JButton[][] buttons = new JButton[3][3];
        JPanel panel = new JPanel(new GridLayout(3, 3));

        Random random = new Random();
        boolean[] over = {false};

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            JButton button = new JButton();
            buttons[row][col] = button;

            button.addActionListener(a -> {
                if (over[0] || !button.getText().isEmpty()) return;
                button.setText("X");
                if (end(frame, buttons, over)) return;
                move_computer(buttons, random);
                end(frame, buttons, over);
            });

            panel.add(button);
        }

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    static void move_computer(JButton[][] button, Random rnd) {
        int empty = 0;
        for (var row : button) {
            for (var x : row) {
                if (x.getText().isEmpty()) ++empty;
            }
        }

        if (empty == 0) return;
        int pick = rnd.nextInt(empty);

        for (var row : button) {
            for (var x : row) {
                if (x.getText().isEmpty() && pick-- == 0) {
                    x.setText("O");
                    return;
                }
            }
        }
    }


    static boolean end(JFrame f, JButton[][] button, boolean[] over) {
        for (int[][] line : lines) {
            String s = button[line[0][0]][line[0][1]].getText();
            if (!s.isEmpty()
                    && s.equals(button[line[1][0]][line[1][1]].getText())
                    && s.equals(button[line[2][0]][line[2][1]].getText())) {
                JOptionPane.showMessageDialog(f, s + " wins!");
                over[0] = true;
                f.setTitle("XO - " + (s.equals("X") ? "You win!" : "You lose!"));
                return true;
            }
        }

        for (var row : button) {
            for (var x : row) {
                if (x.getText().isEmpty()) return false;
            }
        }

        over[0] = true;
        f.setTitle("XO - Draw!");
        return true;
    }
}
