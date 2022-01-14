import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Test {
    private final TestPlayer[] players;
    private Board b;
    private TestPlayer player;
    private TestPlayer opponent;
    private int[] move;
    private List<int[]> moves;

    public Test() {
        this.players = new TestPlayer[] { new TestPlayer(1), new TestPlayer(2) };
        this.player = players[0];
        this.opponent = players[1];
        this.b = new Board();
        this.moves = new ArrayList<int[]>();
    }

    public void updatePlayer() {
        updateMove();
        this.player = player.getPlayer() == 1 ? players[1] : players[0];
        this.opponent = player.getPlayer() == 1 ? players[1] : players[0];
    }

    public void updateMove() {
        int[] mp = new int[] {move[0], move[1], this.player.getPlayer()};
        moves.add(mp);
        b.makeMove(move[0], move[1], player.getPlayer());
        b.printBoard();
        opponent.addOpponentMove(move);
    }

    public void play() {
        do {
            if (player.getTree().getRoot().getState().getBoard().getAllMoves(player.getPlayer()).size() == 0) {
                System.out.println("Player " + player.getPlayer() + " has no moves");
                this.player = player.getPlayer() == 1 ? players[1] : players[0];
                this.opponent = player.getPlayer() == 1 ? players[1] : players[0];
            }
            move = player.getMove();

            updatePlayer();
            
        } while (!b.gameOver());
        System.out.println(Arrays.deepToString(moves.toArray()));
        gameStats();

    }

    public void gameStats() {
        if (b.getScores()[0] > b.getScores()[1]) {
            System.out.println("Player 1 wins!");
        } else if (b.getScores()[0] < b.getScores()[1]) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.play();
    }
}
