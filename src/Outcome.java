/**
 * Represents a Outcome of a playout
 *
 * @author emsquellen
 */
public class Outcome {
    public static final byte WIN = -0x01;
    public static final byte LOSS = -0x02;
    public static final byte DRAW = -0x03;
    public static final byte UNKNOWN = -0x04;

    private final Node terminalNode;
    private final byte type;


    /**
     * Constructor for an outcome.
     *
     * @param terminalNode the terminal node
     * @param type         the outcome type
     */
    Outcome(byte type, Node terminalNode) {
        this.terminalNode = terminalNode;
        this.type = type;
    }

    /**
     * Getter for the type of outcome.
     */
    public byte getType() {
        return type;
    }

    /**
     * Getter for the terminal node.
     */
    public Node getTerminalNode() {
        return terminalNode;
    }
}
