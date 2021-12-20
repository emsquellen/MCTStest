import java.util.List;

public class State {
    private final Board board;
    private int player;
    private int opponent;
    private int x;
    private int y;

    public State(Board board, int player, int x, int y) {
        this.board = board;
        this.player = player;
        this.opponent = player == 1 ? 2 : 1;
        this.x = x;
        this.y = y;
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
        this.opponent = player == 1 ? 2 : 1;
    }

    public int getOpponent() {
        return opponent;
    }

    public void setOpponent(int opponent) {
        this.opponent = opponent;
        this.player = opponent == 1 ? 2 : 1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[] getMove() {
        return new int[] { x, y };
    }

    public void setMove(int[] move) {
        this.x = move[0];
        this.y = move[1];
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof State
                && ((State) obj).board.equals(this.board)
                && ((State) obj).player == this.player
                && ((State) obj).x == this.x
                && ((State) obj).y == this.y;
    }

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

    public List<int[]> getMoves() {
        return this.board.getAllMoves(this.player);
    }
}
