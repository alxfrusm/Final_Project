import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMain extends JPanel {
    private Board board;
    private State currentState;
    private Seed currentPlayer;

    public GameMain() {
        board = new Board();
        setPreferredSize(new Dimension(3 * Cell.SIZE, 3 * Cell.SIZE));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / Cell.SIZE;
                int col = e.getX() / Cell.SIZE;

                if (currentState == State.PLAYING && board.cells[row][col].content == Seed.NO_SEED) {
                    board.cells[row][col].content = currentPlayer;
                    updateGame(currentPlayer, row, col);
                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                    // Main point: play sound
                    if (currentState == State.PLAYING) {
                        SoundEffect.EAT_FOOD.play();
                    } else {
                        SoundEffect.WIN.play();
                    }
                    repaint();
                }
            }
        });

        initGame();
    }

    public void initGame() {
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        board.newGame();
        SoundEffect.initGame();
    }

    public void updateGame(Seed seed, int row, int col) {
        if (board.hasWon(seed, row, col)) {
            currentState = (seed == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (board.isDraw()) {
            currentState = State.DRAW;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.paint(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tic Tac Toe with Sound and Images");
            frame.setContentPane(new GameMain());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
