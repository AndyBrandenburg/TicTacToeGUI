import javax.swing.*;

public class TicTacToeRunner extends TicTacToeFrame{

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new TicTacToeFrame();
        });
    }
}
