/**
 * Monte Carlo Tree Search
 * 
 * @author emsquellen
 */
public class MCTSTree {
    private Node root;

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
     * monte carlo tree search
     */
    public void monteCarlo() {
        Node selected = root.select();
        if (selected != null) {
            selected.expand();
            Outcome outcome = selected.playout();
            selected.backpropagate(outcome);
        }
    }

    /**
     * gets the best move
     */
    public Node getBestMove() {
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        this.monteCarlo();
        return root.getBestChild();
    }

    public static void main(String[] args) {
        MCTSTree tree = new MCTSTree();
        System.out.println(tree.getBestMove().toString());
    }

}
