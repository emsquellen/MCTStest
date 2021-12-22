import java.util.Arrays;

public class Test {
    public void play(Board b) {
        TestPlayer[] players = new TestPlayer[] { new TestPlayer(1), new TestPlayer(2) };
        while (true) {
            for (TestPlayer player : players) {
                TestPlayer opponentPlayer = players[0] == player ? players[1] : players[0];
                int[] move = new int[]{3, 3};
                while (!b.checkMove(move[0], move[1], player.getPlayer())) {
                    move = player.getMove();
                }
                opponentPlayer.addOpponentMove(move);
                b.makeMove(move[0], move[1], player.getPlayer());
                if (b.isFull()) {
                    b.printBoard();
                    if (b.getScores()[0] > b.getScores()[1]) {
                        System.out.println("Player 1 wins!");
                    } else if (b.getScores()[0] < b.getScores()[1]) {
                        System.out.println("Player 2 wins!");
                    } else {
                        System.out.println("It's a tie!");
                    }
                    return;
                }
            }
        }
    }
    public static void main(String[] args) {
        Test t = new Test();
        t.play(new Board());
    }
}
