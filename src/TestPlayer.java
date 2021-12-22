public class TestPlayer {
    private final int player;
    MCTSTree tree;

    public TestPlayer(int player) {
        this.player = player;
        this.tree = new MCTSTree();
    }

    public int getPlayer() {
        return player;
    }

    public MCTSTree getTree() {
        return tree;
    }

    public int[] getMove() {
        tree.mcts();
        return tree.getBestMove().getState().getMove();
    }

    public void addOpponentMove(int[] move) {
        Node oppMove = tree.getRoot().addOpponentNode(move[0], move[1]);
        tree.setRoot(oppMove);
    }



}
