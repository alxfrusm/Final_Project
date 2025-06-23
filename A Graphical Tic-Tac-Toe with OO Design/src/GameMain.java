import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMain extends JPanel {
    private Board board;
    private State currentState;
    private Seed currentPlayer;

    private int crossScore = 0;
    private int noughtScore = 0;

    private JButton retryButton;
    private JButton resetScoreButton;
    private JLabel scoreLabel;
    private JLabel statusLabel;

    public GameMain() {
        board = new Board();
        setPreferredSize(new Dimension(3 * Cell.SIZE, 3 * Cell.SIZE));
        setLayout(new BorderLayout());

        // Panel permainan
        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                board.paint(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(3 * Cell.SIZE, 3 * Cell.SIZE));
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / Cell.SIZE;
                int col = e.getX() / Cell.SIZE;

                if (currentState == State.PLAYING && board.cells[row][col].content == Seed.NO_SEED) {
                    board.cells[row][col].content = currentPlayer;

                    updateGame(currentPlayer, row, col);

                    if (currentState == State.PLAYING) {
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }

                    // Play sound
                    if (currentState == State.PLAYING) {
                        SoundEffect.EAT_FOOD.play();
                    } else if (currentState == State.DRAW) {
                        SoundEffect.DIE.play();
                    } else {
                        SoundEffect.WIN.play();
                    }
                    gamePanel.repaint();
                }
            }
        });

        // Status label di atas
        statusLabel = new JLabel("");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        // Panel bawah
        JPanel bottomPanel = new JPanel();

        retryButton = new JButton("Retry");
        retryButton.addActionListener(e -> {
            SoundEffect.stopAll(); // Hentikan suara saat retry
            initGame();
            gamePanel.repaint();
        });
        retryButton.setVisible(false);

        resetScoreButton = new JButton("Reset Skor");
        resetScoreButton.addActionListener(e -> {
            crossScore = 0;
            noughtScore = 0;
            updateScoreLabel();
        });

        scoreLabel = new JLabel();
        updateScoreLabel();

        bottomPanel.add(retryButton);
        bottomPanel.add(resetScoreButton);
        bottomPanel.add(scoreLabel);

        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        initGame();
    }

    public void initGame() {
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        board.newGame();
        SoundEffect.initGame();
        retryButton.setVisible(false);
        statusLabel.setText("");
        repaint();
    }

    public void updateGame(Seed seed, int row, int col) {
        if (board.hasWon(seed, row, col)) {
            currentState = (seed == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
            if (seed == Seed.CROSS) {
                crossScore++;
                statusLabel.setText("X Win!");
            } else {
                noughtScore++;
                statusLabel.setText("O Win!");
            }
            updateScoreLabel();
            retryButton.setVisible(true);
        } else if (board.isDraw()) {
            currentState = State.DRAW;
            statusLabel.setText("Draw!");
            retryButton.setVisible(true);
        } else {
            statusLabel.setText("");
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Skor - X: " + crossScore + " | O: " + noughtScore);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
