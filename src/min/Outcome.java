package min;

public class Outcome {
    private final Node terminalNode;
    private final OutcomeType type;
    enum OutcomeType {WIN, LOSS, DRAW, UNKNOWN}

    Outcome(OutcomeType type, Node terminalNode) {
        this.terminalNode = terminalNode;
        this.type = type;
    }

    public OutcomeType getType() {return type;}

    public Node getTerminalNode() {return terminalNode;}
}

