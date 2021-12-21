/**
 * Represents a Outcome of a playout
 *
 * @author emsquellen
 */
public class Outcome {
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
    public OutcomeType getType() {
        return type;
    }

    /**
     * Getter for the terminal node.
     */
    public Node getTerminalNode() {
        return terminalNode;
    }
}
