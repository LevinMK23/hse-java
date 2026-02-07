package hse.java.lectures.lecture1;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class Game
{

    private static boolean checkWin(char[][] board, char sym)
    {
        for (int row = 0; row < 3; ++row)
        {
            if (board[row][0] == sym && board[row][1] == sym && board[row][2] == sym)
            {
                return true;
            }
        }

        for (int col = 0; col < 3; ++col)
        {
            if (board[0][col] == sym && board[1][col] == sym && board[2][col] == sym)
            {
                return true;
            }
        }

        if (board[0][0] == sym && board[1][1] == sym && board[2][2] == sym) return true;
        if (board[0][2] == sym && board[1][1] == sym && board[2][0] == sym) return true;

        return false;

    }

    private static boolean isBoardFull(char[][] board)
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col] == 0) return false;
            }
        }
        return true;
    }

    private static void botMove(JButton[][] buttons, char[][] board, Random random)
    {
        while (true)
        {
            int i = random.nextInt(3);
            int j = random.nextInt(3);
            if (board[i][j] == 0)
            {
                buttons[i][j].setText("O");
                board[i][j] = 'O';
                break;
            }
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("XO");
        JLabel statusLabel = new JLabel("Your turn:", SwingConstants.CENTER);
        frame.add(statusLabel, BorderLayout.NORTH);

        frame.setSize(300, 300);
        frame.setLocation(200, 200);


        JButton[][] buttons = new JButton[3][3];

        char[][] board = new char[3][3];

        Random random = new Random();


        JPanel panel = new JPanel(new GridLayout(3, 3));


        AtomicBoolean gameActive = new AtomicBoolean(true);
        AtomicBoolean playerTurn = new AtomicBoolean(true);


        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                buttons[i][j] = new JButton();

                int row = i;
                int col = j;

                buttons[i][j].addActionListener(a ->
                {
                    if (!gameActive.get() || !playerTurn.get() || !buttons[row][col].getText()
                            .isEmpty())
                    {
                        return;
                    }

                    buttons[row][col].setText("X");
                    board[row][col] = 'X';

                    if (checkWin(board, 'X'))
                    {
                        gameActive.set(false);
                        statusLabel.setText("You win!");
                        return;

                    } else if (isBoardFull(board))
                    {
                        gameActive.set(false);
                        statusLabel.setText("Draw");
                        return;
                    }

                    playerTurn.set(false);

                    statusLabel.setText("Bot turn:");

                    Timer timer = new Timer(500, e ->
                    {
                        botMove(buttons, board, random);

                        playerTurn.set(true);

                        if (checkWin(board, 'O'))
                        {
                            gameActive.set(false);
                            statusLabel.setText("Bot win!");
                        } else if (isBoardFull(board))
                        {
                            gameActive.set(false);
                            statusLabel.setText("Draw");
                        } else
                        {
                            statusLabel.setText("Your turn");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                });

                panel.add(buttons[i][j]);


            }
        }

        frame.add(panel);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

