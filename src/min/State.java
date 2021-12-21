package min;
import java.util.List;

public class State {
    private final Board board;
    private final int player;
    private final int opponent;
    private final int x;
    private final int y;

    public State(Board board, int player, int x, int y) {
        this.board = board;
        this.player = player;
        this.opponent = player == 1 ? 2 : 1;
        this.x = x;
        this.y = y;
    }

    public Board getBoard() {return board;}

    public int getPlayer() {return player;}

    public int getOpponent() {return opponent;}

    public int getX() {return x;}

    public int getY() {return y;}

    public int[] getMove() {return new int[] { x, y };}

    @Override
    public boolean equals(Object obj) {return obj instanceof State && ((State) obj).board.equals(this.board) && ((State) obj).player == this.player && ((State) obj).x == this.x && ((State) obj).y == this.y;}

    @Override
    public String toString() {return new StringBuilder().append('\n').append("\nPlayer: ").append(player).append("\nX: ").append(x).append("\nY: ").append(y).toString();}
    
    public List<int[]> getMoves() {return this.board.getAllMoves(this.player);}
}
