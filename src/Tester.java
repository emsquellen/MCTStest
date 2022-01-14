import java.util.Arrays;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        TestPlayer o = new TestPlayer(2);
        Board b = new Board();
        b.printBoard();
        while (true) {
            System.out.println(Arrays.deepToString(b.getAllMoves(1).toArray()));
            System.out.println("Enter move x: ");
            int x = in.nextInt();
            System.out.println("Enter move y: ");
            int y = in.nextInt();
            System.out.println("Player X's choice: " + x + " " + y);
            b.makeMove(x, y, 1);
            b.printBoard();
            o.addOpponentMove(new int[] { x, y });
            // System.out.println("==============================" + 2 + "==============================");
            // o.getTree().getRoot().getState().getBoard().printBoard();
            // System.out.println(o.getTree().getRoot().toString());
            // System.out.println("==============================" + 2 + "==============================");
            // System.out.println("       ==== "+Arrays.deepToString(b.getAllMoves(1).toArray()));
            int[] moves = o.getMove();
            System.out.println("Player O's choice: " + Arrays.toString(moves));
            b.makeMove(moves[0], moves[1], 2);
            b.printBoard();
            if (b.gameOver()) {
                System.out.println("777777777777777777777777777777777777777777");
                return;
            }
        }
    }
}