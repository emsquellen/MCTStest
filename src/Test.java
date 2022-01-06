import java.util.Scanner;

public class Test {
    private final TestPlayer[] players;
    private Board b;
    private TestPlayer player;
    private TestPlayer opponent;
    private int[] move;

    public Test() {
        this.players = new TestPlayer[] { new TestPlayer(1), new TestPlayer(2) };
        this.player = players[0];
        this.opponent = players[1];
        this.b = new Board();
    }

    public void updatePlayer(TestPlayer player) {
        updateMove();
        this.player = player;
        this.opponent = player.getPlayer() == 1 ? players[1] : players[0];
    }

    public void updateMove() {
        b.makeMove(move[0], move[1], player.getPlayer());
        b.printBoard();
        opponent.addOpponentMove(move);
    }

    public void play() {
        do {
            move = player.getMove();
            b.printBoard();
            updatePlayer(opponent);
            
        } while (!b.gameOver());
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
