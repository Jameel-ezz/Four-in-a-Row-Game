import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FourInARow extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;

    private JButton[] buttons;
    private JLabel label;
    private boolean playerOneTurn;
    private boolean gameOver;
    private int[][] board;

    public FourInARow() {
        setSize(WIDTH, HEIGHT);
        setTitle("Four in a Row");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the board to be empty
        board = new int[6][7];

        // Initialize the buttons for each column
        buttons = new JButton[7];
        for (int i = 0; i < 7; i++) {
            final int col = i;
            buttons[i] = new JButton("Drop in column " + (i+1));
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gameOver) {
                        int row = dropInColumn(col);
                        if (row >= 0) {
                            buttons[col].setText("Drop in column " + (col+1));
                            if (playerOneTurn) {
                                label.setText("Player Two's Turn");
                            } else {
                                label.setText("Player One's Turn");
                            }
                            playerOneTurn = !playerOneTurn;
                            checkForWin(row, col);
                        }
                    }
                }
            });
        }

        // Add the buttons to the GUI
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 7));
        for (JButton button : buttons) {
            panel.add(button);
        }
        add(panel, BorderLayout.SOUTH);

        // Add the label for displaying the current player's turn
        label = new JLabel("Player One's Turn", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Add the panel for displaying the game board
        GamePanel gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        playerOneTurn = true;
        gameOver = false;
    }

    // Drops a token into the specified column and returns the row it was placed in
    // Returns -1 if the column is full
    private int dropInColumn(int col) {
        for (int row = 5; row >= 0; row--) {
            if (board[row][col] == 0) {
                if (playerOneTurn) {
                    board[row][col] = 1;
                } else {
                    board[row][col] = 2;
                }
                return row;
            }
        }
        return -1;
    }
    // Check if the game has been won after placing a token at the specified position
    private void checkForWin(int row, int col) {
        // Check for a horizontal win
        int count = 1;
        for (int i = col - 1; i >= 0; i--) {
            if (board[row][i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = col + 1; i < 7; i++) {
            if (board[row][i] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            gameOver = true;
            if (playerOneTurn) {
                label.setText("Player One Wins!");
            } else {
                label.setText("Player Two Wins!");
            }
            return;
        }

        // Check for a vertical win
        count = 1;
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            gameOver = true;
            if (playerOneTurn) {
                label.setText("Player One Wins!");
            } else {
                label.setText("Player Two Wins!");
            }
            return;
        }

        // Check for a diagonal win (top-left to bottom-right)
        count = 1;
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = row + 1, j = col + 1; i < 6 && j < 7; i++, j++) {
            if (board[i][j] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            gameOver = true;
            if (playerOneTurn) {
                label.setText("Player One Wins!");
            } else {
                label.setText("Player Two Wins!");
            }
            return;
        }

        // Check for a diagonal win (top-right to bottom-left)
        count = 1;
        for (int i = row - 1, j = col + 1; i >= 0 && j < 7; i--, j++) {
            if (board[i][j] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = row + 1, j = col - 1; i < 6 && j >= 0; i++, j--) {
            if (board[i][j] == board[row][col]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            gameOver = true;
            if (playerOneTurn) {
                label.setText("Player One Wins!");
            } else {
                label.setText("Player Two Wins!");
            }
            return;
        }

        // Check for a draw
        boolean draw = true;
        for (int i = 0; i < 7; i++) {
            if (buttons[i].getText().equals("Drop in column " + (i+1))) {
                draw = false;
                break;
            }
        }
        if (draw) {
            gameOver = true;
            label.setText("Draw!");
        }
    }

    public static void main(String[] args) {
        FourInARow game = new FourInARow();
        game.setVisible(true);
    }

    // Inner class for rendering the game board
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (board[i][j] == 1) {
                        g.setColor(Color.RED);
                    } else if (board[i][j] == 2) {
                        g.setColor(Color.YELLOW);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillOval(j * 100, i * 100, 100, 100);
                }
            }
        }
    }
}

