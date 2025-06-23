import java.awt.*;

public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;

    Cell[][] cells;

    public Board() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();
            }
        }
    }

    public void paint(Graphics g) {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }

        // Draw grid lines
        g.setColor(Color.BLACK);
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, i * Cell.SIZE, COLS * Cell.SIZE, i * Cell.SIZE);
            g.drawLine(i * Cell.SIZE, 0, i * Cell.SIZE, ROWS * Cell.SIZE);
        }
    }

    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) return false;
            }
        }
        return true;
    }

    public boolean hasWon(Seed seed, int row, int col) {
        return (cells[row][0].content == seed && cells[row][1].content == seed && cells[row][2].content == seed)
                || (cells[0][col].content == seed && cells[1][col].content == seed && cells[2][col].content == seed)
                || (row == col && cells[0][0].content == seed && cells[1][1].content == seed && cells[2][2].content == seed)
                || (row + col == 2 && cells[0][2].content == seed && cells[1][1].content == seed && cells[2][0].content == seed);
    }
}
