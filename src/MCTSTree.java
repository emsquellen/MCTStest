public class MCTSTree {
    private Node root;

    public MCTSTree() {
        this.root = new Node(null, new Board(), 1, 3, 3);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node MCTS() {
        Node node = root;
        Node selected = node.select();
        selected.expand();
        Node bestChild = selected.getBestChild();
        root = bestChild;
        return bestChild;
    }

    public void monteCarlo() {
        Node selected = root.select();
        if (selected != null) {
            selected.expand();
            Outcome outcome = selected.playout();
            selected.backpropagate(outcome);
        }
    }

    public Node monteCarloTreeSearchAI() {
        this.monteCarlo();
        return root.getBestChild();
    }

    public static void main(String[] args) {
        MCTSTree tree = new MCTSTree();
        System.out.println(tree.MCTS().toString());
        System.out.println(tree.MCTS().toString());
        System.out.println(tree.MCTS().toString());
    }

}
