import java.util.List;

/**
 * This class represents a state in the game.
 *
 * @author emsquellen
 */
public class State {
    private final Board board;
    private final int player;
    private final int opponent;
    private final int x;
    private final int y;

    /**
     * Constructor for a state.
     *
     * @param board    the board
     * @param player   the player
     * @param opponent the opponent
     * @param x        the x coordinate
     * @param y        the y coordinate
     */
    public State(Board board, int player, int x, int y) {
        this.board = board;
        this.player = player;
        this.opponent = player == 1 ? 2 : 1;
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter for the player.
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Getter for the opponent.
     */
    public int getOpponent() {
        return opponent;
    }

    /**
     * Getter for the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for the move (x, y).
     */
    public int[] getMove() {
        return new int[] { x, y };
    }

    /**
     * Equals method.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof State
                && ((State) obj).board.equals(this.board)
                && ((State) obj).player == this.player
                && ((State) obj).x == this.x
                && ((State) obj).y == this.y;
    }

    /**
     * String representation of the state.
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append('\n')
                // .append(board.toString())
                .append("\nPlayer: ")
                .append(player)
                .append("\nX: ")
                .append(x).append("\nY: ")
                .append(y)
                .toString();
    }

    /**
     * Getter for the list of possible moves.
     */
    public List<int[]> getMoves() {
        return this.board.getAllMoves(this.player);
    }
}
