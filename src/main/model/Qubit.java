package model;

import exceptions.NoStateToRemoveException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

// Represents a qubit, having a list of states and a list of their associated scaling in order to normalize the qubit
public class Qubit implements Iterable<State> {

    private ArrayList<State> states;
    private double scaleFactor;

    //EFFECTS: constructs a qubit in a single state
    public Qubit(State state) {
        states = new ArrayList<>();
        states.add(state);
        scaleFactor = state.normalize();
    }

    //EFFECTS: constructs a qubit with no states
    //NOTE: should only be called when loading a new qubit
    public Qubit() {
        states = new ArrayList<>();
    }

    //getters
    public double getScaleFactor() {
        return scaleFactor;
    }

    public ArrayList<State> getStates() {
        return this.states;
    }

    //MODIFIES: this
    //EFFECTS: adds a new state to states, along with its scaling factor to scales, and normalizes scales
    public void addState(State state) {
        double sum = 0;
        states.add(state);
        for (State s : states) {
            sum += 1 / (s.normalize() * s.normalize());
        }
        scaleFactor = 1 / Math.sqrt(sum);
    }

    //MODIFIES: this
    //EFFECTS: removes the given state and re-normalizes scales
    public void removeState(State state) throws NoStateToRemoveException {
        if (!(states.contains(state))) {
            throw new NoStateToRemoveException();
        }
        Complex zero = new Complex(0, 0);
        State sumState = new State(zero, zero);
        states.remove(state);
        for (State s : states) {
            sumState = sumState.addState(s);
        }
        scaleFactor = sumState.normalize();
    }

    //EFFECTS: returns a random state that this could be in, with the probability dependent on the coefficients
    public Qubit measure() {
        ArrayList<State> weightedList = new ArrayList<>();
        for (State s : states) {
            double weight = (scaleFactor * scaleFactor) / (s.normalize() * s.normalize());
            int intWeight = (int) (weight * 100);
            while (intWeight > 0) {
                weightedList.add(s);
                intWeight--;
            }
        }
        Random rand = new Random();
        return new Qubit(weightedList.get(rand.nextInt(weightedList.size())));
    }

    //MODIFIES: this
    //EFFECTS: transforms this using the given unitary matrix
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void transform(Gate gate) {
        for (int i = 0; i < states.size(); i++) {
            State newState = gate.multiply(states.get(i));
            this.states.remove(i);
            this.states.add(i, newState);
        }
        scaleFactor *= gate.getScale();
    }

    //EFFECTS: measures the probability of an eigenvalue of a given gate.
    //         returns probability of measuring the positive eigenvalue if posOrNeg is true, negative otherwise.
    public double measureProbability(Gate gate, boolean posOrNeg) {
        State eigenState = gate.getEigenState(posOrNeg);
        Complex comp1 = eigenState.getFirstNumber();
        Complex comp2 = eigenState.getSecondNumber();
        State conjugate = new State(comp1.conjugate(), comp2.conjugate());
        double sum = 0;
        for (State s : states) {
            sum += Math.pow(conjugate.innerProduct(s).absoluteValue(), 2);
        }
        double constant = scaleFactor * scaleFactor;
        return sum * constant;
    }

    //EFFECTS: measures the expectation value, or the average of the eigenvalues, of a given gate
    public double measureExpectationValue(Gate gate) {
        Complex sum = new Complex(0, 0);
        for (State s : states) {
            State conjugate = new State(s.getFirstNumber().conjugate(), s.getSecondNumber().conjugate());
            sum = sum.plus(s.innerProduct(gate.multiply(conjugate)));
        }
        double constant = scaleFactor * scaleFactor * gate.getScale();
        return sum.getX() * constant;
    }

    @Override
    public Iterator<State> iterator() {
        return this.states.iterator();
    }
}