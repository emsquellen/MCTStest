import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents a Node in the game tree for monte carlo tree search.
 *
 * @author emsquellen
 */
public class Node {
    private final Node parent;
    private final State state;
    private boolean isTerminal;
    private List<Node> children;
    private int visits;
    private int wins;
    private int losses;

    /**
     * Represents a Outcome of a playout
     *
     * @author emsquellen
     */
    private class Outcome {
        private final Node terminalNode;
        private final OutcomeType type;

        /**
         * Type of outcome
         */
        enum OutcomeType {
            WIN, LOSS, DRAW, UNKNOWN
        }

        /**
         * Constructor for an outcome.
         *
         * @param terminalNode the terminal node
         * @param type         the outcome type
         */
        Outcome(OutcomeType type, Node terminalNode) {
            this.terminalNode = terminalNode;
            this.type = type;
        }

        /**
         * Getter for the type of outcome.
         */
        OutcomeType getType() {
            return type;
        }

        /**
         * Getter for the terminal node.
         */
        Node getTerminalNode() {
            return terminalNode;
        }
    }

    /**
     * Constructor for a node.
     *
     * @param parent the parent node
     * @param state  the state of the node
     */
    public Node(Node parent, State state) {
        this.parent = parent;
        this.state = state;
        this.isTerminal = true;
        this.children = new ArrayList<Node>();
        this.visits = 0;
        this.wins = 0;
        this.losses = 0;
    }

    /**
     * Constructor for a node.
     *
     * @param board  the board of the node
     * @param player the player of the node
     * @param x      the x coordinate of move made last
     * @param y      the y coordinate of move made last
     */
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

    /**
     * Clone constructor for a node.
     *
     * @param node the node to clone
     */
    public Node(Node node) {
        this.parent = node.parent;
        this.state = node.state;
        this.isTerminal = node.isTerminal;
        this.children = node.children;
        this.visits = node.visits;
        this.wins = node.wins;
        this.losses = node.losses;
    }

    /**
     * Getter for the parent node.
     * 
     * @return Node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Getter for the state of the node.
     * 
     * @return State
     */
    public State getState() {
        return state;
    }

    /**
     * Getter for the terminal status of the node.
     * 
     * @return boolean
     */
    public boolean isTerminal() {
        return this.isTerminal;
    }

    /**
     * Setter for the terminal status of the node.
     * 
     * @param isTerminal
     */
    public void setTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    /**
     * Getter for the children of the node.
     * 
     * @return List<Node>
     */
    public List<Node> getChildren() {
        return this.children;
    }

    /**
     * Setter for the children of the node.
     * 
     * @param children
     */
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * Adds a child to the node.
     * 
     * @param child
     * @return boolean
     */
    public boolean addChild(Node child) {
        this.children.add(child);
        this.isTerminal = false;
        return true;
    }

    /**
     * Removes a child from the node.
     * 
     * @param child
     * @return boolean
     */
    public boolean removeChild(Node child) {
        this.children.remove(child);
        if (this.children.size() == 0) {
            this.isTerminal = true;
        }
        return true;
    }

    /**
     * Amount of children of the node.
     * 
     * @return int
     */
    public int getNumChildren() {
        return this.children.size();
    }

    /**
     * Getter for the visits of the node.
     * 
     * @return int
     */
    public int getVisits() {
        return this.visits;
    }

    /**
     * Setter for the visits of the node.
     * 
     * @param visits
     */
    public void setVisits(int visits) {
        this.visits = visits;
    }

    /**
     * Increments the visits of the node.
     */
    public void incrementVisits() {
        this.visits++;
    }

    /**
     * Decrements the visits of the node.
     */
    public void decrementVisits() {
        this.visits--;
    }

    /**
     * Getter for the wins of the node.
     * 
     * @return int
     */
    public int getWins() {
        return this.wins;
    }

    /**
     * Setter for the wins of the node.
     * 
     * @param wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Increments the wins of the node.
     */
    public void incrementWins() {
        this.wins++;
    }

    /**
     * Decrements the wins of the node.
     */
    public void decrementWins() {
        this.wins--;
    }

    /**
     * Getter for the losses of the node.
     * 
     * @return int
     */
    public int getLosses() {
        return this.losses;
    }

    /**
     * Setter for the losses of the node.
     * 
     * @param losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Increments the losses of the node.
     */
    public void incrementLosses() {
        this.losses++;
    }

    /**
     * Decrements the losses of the node.
     */
    public void decrementLosses() {
        this.losses--;
    }

    /**
     * Getter for the win loss ratio of the node.
     * 
     * @return double
     */
    public double getWinLossRate() {
        return visits == 0 ? 0 : (double) this.wins / (double) this.visits;

    }

    /**
     * Gets a random child of the node.
     * 
     * @return Node
     */
    public Node getRandomChild() {
        Random rand = new Random();
        int index = rand.nextInt(this.children.size());
        return this.children.get(index);
    }

    /**
     * Gets the child of the node with the highest win loss ratio.
     * 
     * @return Node
     */
    public Node getBestChild() {
        return this.children.stream()
                .max((n1, n2) -> Double.compare(n1.getWinLossRate(), n2.getWinLossRate()))
                .get();
    }

    /**
     * Gets the string representation of the node's move
     * 
     * @return String
     */
    private String moveString() {
        return state == null ? "Undefined"
                : Arrays.toString(this.state.getMove());
    }

    /**
     * Gets a random child of the node.
     * 
     * @param items the items to choose from
     * @return T
     */
    private static <T> T getRandomListElement(List<T> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    /**
     * Getter for the move of the node's state.
     * 
     * @return int[]
     */
    private int[] getMove() {
        return this.state.getMove();
    }

    // Methods for monte carlo tree search

    /**
     * Selects a random node from the tree.
     * 
     * @return Node
     */
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

    /**
     * Expands the node.
     * 
     * @return Node
     */
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
    public Outcome playout() {
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

        int myScore = currentNode.state.getBoard()
                .getScore(this.state.getPlayer());
        int oppScore = currentNode.state.getBoard()
                .getScore(this.state.getOpponent());
        if (myScore > oppScore) {
            return new Outcome(Outcome.OutcomeType.WIN, currentNode);
        } else if (myScore < oppScore) {
            return new Outcome(Outcome.OutcomeType.LOSS, currentNode);
        } else if (myScore == oppScore) {
            return new Outcome(Outcome.OutcomeType.DRAW, currentNode);
        } else {
            return new Outcome(Outcome.OutcomeType.UNKNOWN, currentNode);
        }
    }

    /**
     * Backpropagates the result of a playout.
     * 
     * @param outcome the outcome of the playout
     */
    public void backpropagate(Outcome outcome) {
        Node currentNode = outcome.getTerminalNode();
        while (currentNode != null) {
            currentNode.incrementVisits();
            if (outcome.getType() == Outcome.OutcomeType.WIN) {
                currentNode.incrementWins();
            } else if (outcome.getType() == Outcome.OutcomeType.LOSS) {
                currentNode.incrementLosses();
            }
            currentNode = currentNode.parent;
        }
    }

    /**
     * String representation of the node.
     * 
     * @return String
     */
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

    /**
     * Equals method for the node.
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && this.state.equals(((Node) obj).state);
    }

    /**
     * Main method for testing.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Node root = new Node(null, new Board(), 1, 3, 3);
        Node selected = root.select();
        selected.expand();
        Outcome o = selected.playout();
        selected.backpropagate(o);
    }
}