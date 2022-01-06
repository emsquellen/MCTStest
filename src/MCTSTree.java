/**
 * Monte Carlo Tree Search
 * 
 * @author emsquellen
 */
public class MCTSTree {
    private Node root;
    private boolean gameEnded;

    /**
     * Constructor for MCTS tree
     */
    public MCTSTree() {
        this.root = new Node(null, new Board(), 1, 3, 3);
    }

    /**
     * Getter for root node
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Setter for root node
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Getter for game ended
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * monte carlo tree search
     */
    public void mcts() {
        Node selected = root.select();
        if (selected != null) {
            selected.expand();
            Outcome outcome = selected.playout();
            selected.backpropagate(outcome);
        } else {
            gameEnded = true;
        }
    }

    /**
     * Gets the best move
     */
    public Node getBestMove() {
        this.mcts();
        return root.getBestChild();
    }
}
