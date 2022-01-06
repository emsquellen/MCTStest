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

    public Node addOpponentNode(int x, int y) {
        Board newBoard = new Board(this.state.getBoard());
        newBoard.makeMove(x, y, this.state.getOpponent());
        Node opponentNode = new Node(this, newBoard, this.state.getOpponent(), x, y);
        if (this.children.contains(opponentNode)) {
            return this.children.get(this.children.indexOf(opponentNode));
        } else {
            this.addChild(opponentNode);
            return opponentNode;
        }
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
        // Get a random number between 0 and the number of children
        int index = rand.nextInt(this.children.size());
        // Return the child at that index
        return this.children.get(index);
    }

    /**
     * Gets the child of the node with the highest win loss ratio.
     * 
     * @return Node
     */
    public Node getBestChild() {
        // Get the child with the highest win loss ratio
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
        // Get the move of the node if it has one, else return "Undefined"
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
        // Get all moves from the children of the node using a stream
        List<int[]> moves = currentNode.children
                .stream()
                .map(Node::getMove)
                .collect(Collectors.toList());
        List<Node> expandableChildNodes = new ArrayList<Node>();

        // For each move of the childeren of the node
        currentNode.state.getMoves().forEach(possibleMove -> {
            // If the move is not in the list of moves
            if (!moves.contains(possibleMove)) {
                // Add the node to the list of expandable nodes
                expandableChildNodes.add(currentNode);
            }
        });
        // If there are expandable nodes, get a random node from the list.
        // Else, return null
        return expandableChildNodes.size() > 0 ? getRandomListElement(expandableChildNodes) : null;
    }

    /**
     * Expands the node.
     * 
     * @return Node
     */
    public void expand() {
        List<int[]> moves = state.getMoves();

        // For each possible legal move
        for (int i = 0; i < moves.size(); i++) {
            // Get the move
            int[] move = moves.get(i);
            // Clone the current board
            Board newBoard = new Board(state.getBoard());
            // Make the move on the board
            newBoard.makeMove(move[0], move[1], state.getPlayer());

            // Create a new node with the new board for the opponent
            Node child = new Node(
                    this, newBoard, state.getOpponent(), move[0], move[1]);
            // Skip the node if it is already in the children of the node
            if (this.children.contains(child)) {
                continue;
            }
            // Add the node to the children of the node
            this.addChild(child);
        }
    }

    /**
     * Simulates a random game from this node.
     * Using random moves.
     */
    public Outcome playout() {
        // Get a random node from the children of the node
        Node currentNode = getRandomChild();
        int newNodePlayer = currentNode.state.getOpponent();

        // While the game is not over
        while (!currentNode.state.getBoard().gameOver()) {
            // Get all legal moves from the current node
            List<int[]> moves = currentNode.state
                    .getBoard()
                    .getAllMoves(newNodePlayer);
            // Clone the board
            Board newBoard = new Board(currentNode.state.getBoard());
            int[] move;
            Node childNode;
            // If there are moves
            if (moves.size() > 0) {
                // Get a random move
                move = getRandomListElement(moves);
                // Make the move on the board
                newBoard.makeMove(move[0], move[1], newNodePlayer);
                // Create a new node with the new board for the opponent
                childNode = new Node(
                        currentNode, newBoard, newNodePlayer, move[0], move[1]);
                // Set the current node to the new node
                currentNode = childNode;
                // Set the new node player to the opponent of the new node
                newNodePlayer = currentNode.state.getOpponent();
            } else {
                // If there are no moves, skip the node
                newNodePlayer = currentNode.state.getPlayer();
            }
        }

        // Return the outcome of the game

        // Retrieve the scores of the game
        int myScore = currentNode.state.getBoard()
                .getScore(this.state.getPlayer());
        int oppScore = currentNode.state.getBoard()
                .getScore(this.state.getOpponent());

        if (myScore > oppScore) {
            // If the player won
            return new Outcome(Outcome.WIN, currentNode);
        } else if (myScore < oppScore) {
            // If the player lost
            return new Outcome(Outcome.LOSS, currentNode);
        } else if (myScore == oppScore) {
            // If the game was a draw
            return new Outcome(Outcome.DRAW, currentNode);
        } else {
            // If the game is invalid
            return new Outcome(Outcome.UNKNOWN, currentNode);
        }
    }

    /**
     * Backpropagates the result of a playout.
     * 
     * @param outcome the outcome of the playout
     */
    public void backpropagate(Outcome outcome) {
        // Retrieve the terminal node of the outcome
        Node currentNode = outcome.getTerminalNode();
        // If the outcome is a draw or unknown, skip the node
        if (currentNode == null || outcome.getType() == Outcome.UNKNOWN
                || outcome.getType() == Outcome.DRAW) {
            return;
        }
        // While the current node is not the root node
        while (currentNode != null) {
            // Update the visit count of the node
            currentNode.incrementVisits();
            // If the outcome is a win
            if (outcome.getType() == Outcome.WIN) {
                // Update the win count of the node
                currentNode.incrementWins();
                // If the outcome is a loss
            } else if (outcome.getType() == Outcome.LOSS) {
                // Update the loss count of the node
                currentNode.incrementLosses();
            }
            // Set the current node to the parent of the current node
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
        // StringBuilder is better than string concatenation
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
}