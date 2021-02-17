package model;

// represents a quantum gate, or a 2x2 Hermitian matrix, with 4 numbers and a normalizing scale
public class Gate {

    public static final double THRESHOLD = 0.001;

    private Complex topRight;
    private Complex topLeft;
    private Complex botRight;
    private Complex botLeft;
    private double scale;

    //EFFECTS: constructs an 2 x 2 sized Hermitian unitary matrix with complex numbers
    public Gate(double a, double b, double c) {
        topLeft = new Complex(a, 0);
        topRight = new Complex(b, -c);
        botLeft = new Complex(b, c);
        botRight = new Complex(-a, 0);
        scale = 1 / Math.sqrt(topLeft.absoluteValue() * topLeft.absoluteValue()
                + topRight.absoluteValue() * topRight.absoluteValue());
    }

    //EFFECTS: constructs a 2 x 2 unitary matrix with complex numbers
    public Gate(Complex topLeft, Complex topRight, Complex botLeft, Complex botRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.botLeft = botLeft;
        this.botRight = botRight;
        scale = 1 / Math.sqrt(topLeft.absoluteValue() * topLeft.absoluteValue()
                + topRight.absoluteValue() * topRight.absoluteValue());
    }

    //getters
    public double getScale() {
        return scale;
    }

    public Complex getTopRight() {
        return topRight;
    }

    public Complex getTopLeft() {
        return topLeft;
    }

    public Complex getBotRight() {
        return botRight;
    }

    public Complex getBotLeft() {
        return botLeft;
    }

    //MODIFIES: state
    //EFFECTS: performs matrix multiplication on the given state
    @SuppressWarnings("SuspiciousNameCombination")
    public State multiply(State state) {
        State temp1 = new State(topLeft, botLeft);
        State temp2 = new State(topRight, botRight);
        Complex comp1 = state.innerProduct(temp1);
        Complex comp2 = state.innerProduct(temp2);
        return new State(comp1, comp2);
    }

    //EFFECTS: returns this gate minus the other gate
    public Gate minus(Gate other) {
        return new Gate(this.topLeft.minus(other.topLeft), this.topRight.minus(other.topRight),
                this.botLeft.minus(other.botLeft), this.botRight.minus(other.botRight));
    }

    //EFFECTS: multiplies the gate by a constant
    public Gate multiplyByConstant(double constant) {
        return new Gate(this.topLeft.multiplyByConstant(constant), this.topRight.multiplyByConstant(constant),
                this.botLeft.multiplyByConstant(constant), this.botRight.multiplyByConstant(constant));
    }

    //EFFECTS: returns the eigenstate associated with this gate. if posOrNeg is true, returns positive eigenstate.
    //         otherwise, returns negative eigenstate
    public State getEigenState(boolean posOrNeg) {
        double eigen = this.getEigenValue(posOrNeg);
        Complex lambda = new Complex(eigen, 0);
        Complex a = this.getTopLeft().multiplyByConstant(scale);
        Complex b = this.getTopRight().multiplyByConstant(scale);
        Complex c = this.getBotLeft().multiplyByConstant(scale);
        Complex d = this.getBotRight().multiplyByConstant(scale);
        if (!(((a.minus(lambda)).absoluteValue() < THRESHOLD))) {
            State result = new State(b, lambda.minus(a));
            return result.multiplyByConstant(result.normalize());
        } else {
            State result = new State(lambda.minus(d), c);
            return result.multiplyByConstant(result.normalize());
        }
    }

    //EFFECTS: returns the eigenvalue associated with this gate. if posOrNeg is true, returns positive eigenvalue.
    //         otherwise, returns negative eigenvalue.
    public double getEigenValue(boolean posOrNeg) {
        double tr = this.getTrace().getX();
        double det = this.getDeterminant().getX();
        double gap = (Math.sqrt(tr * tr - 4 * det)) / 2;
        if (posOrNeg) {
            return (tr + gap);
        } else {
            return (tr - gap);
        }
    }

    //EFFECTS: returns the trace of the gate
    public Complex getTrace() {
        return topLeft.plus(botRight).multiplyByConstant(scale);
    }

    //EFFECTS: returns the determinant of the gate
    public Complex getDeterminant() {
        return topLeft.times(botRight).minus(topRight.times(botLeft)).multiplyByConstant(scale * scale);
    }
}
