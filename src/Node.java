import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    private final Node parent;
    private final State state;
    private boolean isTerminal;
    private List<Node> children;
    private int visits;
    private int wins;
    private int losses;

    enum Outcome {
        WIN,
        DRAW,
        LOSE,
        ERROR
    }

    public Node(Node parent, State state) {
        this.parent = parent;
        this.state = state;
        this.isTerminal = true;
        this.children = new ArrayList<Node>();
        this.visits = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public Node(Node parent, Board board, int player, int x, int y) {
        State state = new State(board, player, x, y);
        this.parent = parent;
        this.state = state;
        this.isTerminal = true;
        this.children = new ArrayList<Node>();
        this.visits = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public Node(Node node) {
        this.parent = node.parent;
        this.state = node.state;
        this.isTerminal = node.isTerminal;
        this.children = node.children;
        this.visits = node.visits;
        this.wins = node.wins;
        this.losses = node.losses;
    }

    public Node getParent() {
        return parent;
    }

    public State getState() {
        return state;
    }

    public boolean isTerminal() {
        return this.isTerminal;
    }

    public void setTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public boolean addChild(Node child) {
        this.children.add(child);
        this.isTerminal = false;
        return true;
    }

    public boolean removeChild(Node child) {
        this.children.remove(child);
        if (this.children.size() == 0) {
            this.isTerminal = true;
        }
        return true;
    }

    public int getNumChildren() {
        return this.children.size();
    }

    public int getVisits() {
        return this.visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public void incrementVisits() {
        this.visits++;
    }

    public void decrementVisits() {
        this.visits--;
    }

    public int getWins() {
        return this.wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void decrementWins() {
        this.wins--;
    }

    public int getLosses() {
        return this.losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public void decrementLosses() {
        this.losses--;
    }

    public double getWinLossRate() {
        return visits == 0 ? 0 : (double) this.wins / (double) this.visits;

    }

    public Node getRandomChild() {
        Random rand = new Random();
        int index = rand.nextInt(this.children.size());
        return this.children.get(index);
    }

    public Node getBestChild() {
        return this.children.stream()
            .max((n1, n2) -> Double.compare(n1.getWinLossRate(), n2.getWinLossRate()))
            .get();
    }

    private String moveString() {
        return state == null ? "Undefined"
                : Arrays.toString(this.state.getMove());
    }

    private static <T> T getRandomListElement(List<T> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    private int[] getMove() {
        return this.state.getMove();
    }

    public Node select() {
        Node currentNode = this;
        List<int[]> moves = currentNode.children
                .stream()
                .map(Node::getMove)
                .collect(Collectors.toList());
        List<Node> expandableChildNodes = new ArrayList<Node>();

        currentNode.state.getMoves().forEach(possibleMove -> {
            if (!moves.contains(possibleMove)) {
                expandableChildNodes.add(currentNode);
            }
        });

        return getRandomListElement(expandableChildNodes);
    }

    public void expand() {
        List<int[]> moves = state.getMoves();

        for (int i = 0; i < moves.size(); i++) {
            int[] move = moves.get(i);
            Board newBoard = new Board(state.getBoard());
            newBoard.makeMove(move[0], move[1], state.getPlayer());

            Node child = new Node(
                    this, newBoard, state.getOpponent(), move[0], move[1]);
            if (this.children.contains(child)) {
                continue;
            }
            this.addChild(child);
        }
    }

    /**
     * Simulates a random game from this node.
     * Using random moves.
     */
    public void playout() {
        Node currentNode = getRandomChild();
        int newNodePlayer = currentNode.state.getOpponent();
        List<Node> visited = new ArrayList<Node>();

        visited.add(currentNode);

        while (!currentNode.state.getBoard().gameOver()) {
            // Select a random child node
            List<int[]> moves = currentNode.state
                    .getBoard()
                    .getAllMoves(newNodePlayer);
            Board newBoard = new Board(currentNode.state.getBoard());
            int[] move;
            Node childNode;
            // If there are no moves, then player is skipped
            if (moves.size() > 0) {
                move = getRandomListElement(moves);
                newBoard.makeMove(move[0], move[1], newNodePlayer);
                childNode = new Node(
                        currentNode, newBoard, newNodePlayer, move[0], move[1]);
                if (this.children.contains(childNode)) {
                    continue;
                }
                currentNode.addChild(childNode);
                visited.add(childNode);
                currentNode = childNode;
                newNodePlayer = currentNode.state.getOpponent();
            } else {
                newNodePlayer = currentNode.state.getPlayer();
            }
        }

        // "Playout" is complete, so update the visit count and win/loss
        // counts for the nodes visited during the playout.
        // Replaces the function of the "backpropagate" function.

        int myScore = currentNode.state.getBoard()
                .getScore(this.state.getPlayer());
        int oppScore = currentNode.state.getBoard()
                .getScore(this.state.getOpponent());
        Outcome outcome;
        if (myScore > oppScore) {
            outcome = Outcome.WIN;
        } else if (myScore < oppScore) {
            outcome = Outcome.LOSE;
        } else if (myScore == oppScore) {
            outcome = Outcome.DRAW;
        } else {
            outcome = Outcome.ERROR;
        }
        for (Node node : visited) {
            if (outcome == Outcome.WIN) {
                node.incrementWins();
            } else if (outcome == Outcome.LOSE) {
                node.incrementLosses();
            }
            node.incrementVisits();
        }

    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Node ")
                .append(moveString())
                .append(": \n\t")
                .append("State: ")
                .append(state == null ? "No state" : state.toString())
                .append("\n\tParent: \n\t\t")
                .append(parent == null ? "No Parent" : parent.moveString())
                .append("\n\tVisits: ")
                .append(this.visits)
                .append("\n\tChildren: ")
                .append((this.children.size() > 0)
                        ? Arrays.toString(
                                this.children
                                        .stream()
                                        .map(Node::moveString)
                                        .collect(Collectors.toList())
                                        .toArray())
                        : "None")
                .append("\n\tIs Terminal: ")
                .append(this.isTerminal)
                .append("\nWins: ")
                .append(this.wins)
                .append("\nLosses: ")
                .append(this.losses)
                .append("\nWin/Loss Rate: ")
                .append(this.getWinLossRate())
                .append("\n")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && this.state.equals(((Node) obj).state);
    }

    public static void main(String[] args) {
        Node root = new Node(null, new Board(), 1, 3, 3);
        Node selected = root.select();
        selected.expand();
        selected.playout();
        selected.playout();
        selected.playout();
        System.out.println(selected.toString());
        root.getChildren().forEach(child -> {
            System.out.println(child.toString());
        });
    }
}