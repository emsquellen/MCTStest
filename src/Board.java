import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[][] gameBoard;

    public Board(int[][] board) {
        this.gameBoard = board;
    }

    public Board(Board board) {
        this.gameBoard = new int[8][8];
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board.getGameBoard()[row], 0, this.gameBoard[row], 0, 8);
        }
    }

    public Board() {
        this.gameBoard = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 0 }, { 0, 0, 0, 1, 2, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, };
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Board && Arrays.deepEquals(this.gameBoard, ((Board) obj).gameBoard);
    }

    // public boolean makeMove(int x, int y, int player) {
    // if (!(0 <= x && x < 8 && 0 <= y && y < 8) && gameBoard[x][y] == 0) {
    // return false;
    // }
    // gameBoard[x][y] = player;
    // int opponent = player == 1 ? 2 : 1;
    // for (int i = -1; i <= 1; ++i) {
    // for (int j = -1; j <= 1; j++) {
    // if (i == 0 && j == 0) {
    // continue;
    // }
    // int xpos = x + i;
    // int ypos = y + j;
    // if (0 <= xpos && xpos < 8 && 0 <= ypos && ypos < 8 && gameBoard[xpos][ypos]
    // == opponent) {
    // while (0 <= xpos && xpos < 8 && 0 <= ypos && ypos < 8 &&
    // gameBoard[xpos][ypos] == opponent) {
    // xpos += i;
    // ypos += j;
    // }
    // if (0 <= xpos && xpos < 8 && 0 <= ypos && ypos < 8 && gameBoard[xpos][ypos]
    // == player) {
    // while (!(xpos == x && ypos == y)) {
    // gameBoard[xpos][ypos] = player;
    // xpos -= i;
    // ypos -= j;
    // }
    // }
    // }
    // }
    // }
    // return true;
    // }

    public void makeMove(int x, int y, int intacter) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;

                // Absolute positions on the board
                int x_pos = x + i;
                int y_pos = y + j;

                // Check if surrounding position is out of bounds
                if (x_pos == -1 || x_pos == this.gameBoard[0].length || y_pos == -1 || y_pos == this.gameBoard.length) {
                    continue;
                }

                int positionValue = this.gameBoard[y_pos][x_pos];

                if (positionValue == 0 || positionValue == intacter)
                    continue;

                updateDirection(i, j, x_pos, y_pos, intacter);
            }
        }
        this.gameBoard[y][x] = intacter;
    }

    public boolean updateDirection(int x_incremental, int y_incremental, int x_pos, int y_pos, int intacter) {
        int new_x_pos = x_pos + x_incremental;
        int new_y_pos = y_pos + y_incremental;

        // Out of bounds check
        if ((new_x_pos < 0 || new_x_pos > this.gameBoard.length - 1)
                || (new_y_pos < 0 || new_y_pos > this.gameBoard[0].length - 1)) {
            return false;
        }

        int positionValue = this.gameBoard[new_y_pos][new_x_pos];

        if (positionValue == intacter) {
            this.gameBoard[y_pos][x_pos] = intacter;
            return true;
        }

        if (positionValue == 0) {
            return false;
        }

        if (updateDirection(x_incremental, y_incremental, new_x_pos, new_y_pos, intacter)) {
            this.gameBoard[y_pos][x_pos] = intacter;

            return true;
        }

        return false;

    }

    public int getScore(int player) {
        int score = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (gameBoard[i][j] == player) {
                    score++;
                }
            }
        }
        return score;
    }

    public int[] getScores() {
        int score1 = 0;
        int score2 = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (gameBoard[i][j] == 1) {
                    score1++;
                }
                if (gameBoard[i][j] == 2) {
                    score2++;
                }
            }
        }
        return new int[] { score1, score2 };
    }

    public boolean isFull() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (gameBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<int[]> getAllMoves(int player) {
        List<int[]> moves = new ArrayList<int[]>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (gameBoard[i][j] == 0) {
                    if (checkMove(i, j, player)) {
                        moves.add(new int[] { i, j });
                    }
                }
            }
        }
        return moves;
    }

    public boolean checkMove(int x, int y, int player) {
        if (!(0 <= x && x < 8 && 0 <= y && y < 8) && gameBoard[x][y] == 0) {
            return false;
        }
        int opponent = player == 1 ? 2 : 1;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int xpos = x + i;
                int ypos = y + j;
                if (0 <= xpos && xpos < 8 && 0 <= ypos && ypos < 8 && gameBoard[xpos][ypos] == opponent) {
                    while (0 <= xpos && xpos < 8 && 0 <= ypos && ypos < 8 && gameBoard[xpos][ypos] == opponent) {
                        xpos += i;
                        ypos += j;
                    }
                    if (0 <= xpos && xpos < 8 && 0 <= ypos && ypos < 8 && gameBoard[xpos][ypos] == player) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(gameBoard).replace("], ", "]\n") + "\n";
    }

    public boolean noMoves() {
        return getAllMoves(1).size() == 0 && getAllMoves(2).size() == 0;
    }

    public boolean gameOver() {
        return isFull() || noMoves();
    }

    public void printBoard() {
        System.out.println(this.toString());
    }
}
