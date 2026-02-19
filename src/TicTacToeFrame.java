import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class TicTacToeFrame extends JFrame {

    JPanel mainPnl, titlePnl, ticTacToePnl, quitPnl;
    JLabel titleLbl;
    JButton quitBtn;
    private static final int ROW = 3;
    private static final int COL = 3;
    TicTacToeTile[][] board = new TicTacToeTile[3][3];

    private String player = "X";
    private int moveCnt = 0;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;
    boolean finished = false;
    boolean playing = true;

    public TicTacToeFrame() {
        setSize(600, 600);
        setTitle("Tic Tac Toe GUI");

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        setVisible(true);
        createTitlePnl();
        createTicTacToePnl();
        createQuitPnl();

    }



    public void createTitlePnl() {
        titlePnl = new JPanel();
        titleLbl = new JLabel();
        titleLbl.setText("Tic Tac Toe! Select Your Move!");
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);
        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setFont(new Font("Serif", Font.BOLD, 40));

        titlePnl.add(titleLbl);
        mainPnl.add(titlePnl, BorderLayout.NORTH);

    }

    public void createTicTacToePnl() {
        ticTacToePnl = new JPanel();
        ticTacToePnl.setLayout(new GridLayout(3, 3));
        Border ticTacToePnlBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        ticTacToePnl.setBorder(ticTacToePnlBorder);

        // Fill the grid with TicTacToeButtons
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = new TicTacToeTile(r, c);

                ticTacToePnl.add(board[r][c]);


            }
        }

        mainPnl.add(ticTacToePnl, BorderLayout.CENTER);


    }

    public void createQuitPnl() {
        quitPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitPnl.setLayout(new GridLayout(1, 1));
        quitBtn.setHorizontalTextPosition(JLabel.CENTER);
        quitBtn.setVerticalTextPosition(JLabel.BOTTOM);
        quitBtn.setFont(new Font("Serif", Font.BOLD, 20));

        quitPnl.add(quitBtn);


        quitBtn.addActionListener((ActionEvent ae) -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        mainPnl.add(quitPnl, BorderLayout.SOUTH);

    }

    public class TicTacToeTile extends JButton {
        private int row;
        private int col;

        public TicTacToeTile(int row, int col) {
            super();
            this.row = row;
            this.col = col;


            setFont(new Font("Serif", Font.BOLD, 60));

            addActionListener(e -> handleMove(this));
        }

    }

    public void handleMove(TicTacToeTile board){
        if (finished) return;

        if (!board.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Move! Try Again.");
            return;
        }

        board.setText(player);
        moveCnt++;

        if (moveCnt >= 5 && isWin(player)) {
            JOptionPane.showMessageDialog(this,
                    "Player " + player + " Wins!");

            int response = JOptionPane.showConfirmDialog(this,
                    "Play Again?");

            if (response == JOptionPane.YES_OPTION) {
                clearBoard();
            } else {
                finished = true;
                System.exit(0);
            }

            return;
        }

        if(moveCnt >= MOVES_FOR_TIE)
        {
            if(isTie()) {
                JOptionPane.showMessageDialog(this,
                        "It's a Tie!");

                int response = JOptionPane.showConfirmDialog(this,
                        "Play Again?");

                if (response == JOptionPane.YES_OPTION) {
                    clearBoard();
                } else {
                    finished = true;
                    System.exit(0);
                }
                return;
            }
        }

        player = player.equals("X") ? "O" : "X";

    }

    public void clearBoard(){
        for(int row=0; row < ROW; row++)
        {
            for(int col=0; col < COL; col++)
            {
                board[row][col].setText("");
            }
        }
        moveCnt = 0;
        player = "X";
        finished = false;
    }

    public boolean isValidMove(int row, int col)
    {
        boolean retVal = false;
        if(board[row][col].getText().equals(player))
            retVal = true;

        return retVal;

    }

    public boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagonalWin(player))
        {
            return true;
        }

        return false;
    }
    public boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }

    public boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }

    public boolean isDiagonalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player) )
        {
            return true;
        }
        if(board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player) )
        {
            return true;
        }
        return false;
    }

    public boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals("X") ||
                    board[row][1].getText().equals("X") ||
                    board[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].getText().equals("O") ||
                    board[row][1].getText().equals("O") ||
                    board[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals("X") ||
                    board[1][col].getText().equals("X") ||
                    board[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].getText().equals("O") ||
                    board[1][col].getText().equals("O") ||
                    board[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

}


