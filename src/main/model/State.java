package model;

import java.util.Objects;

// represents a state, having a scale which ensures the state is normalized, and two complex numbers
public class State {

    private Complex firstNumber;
    private Complex secondNumber;

    public State(Complex x, Complex y) {
        firstNumber = x;
        secondNumber = y;
    }

    //getters
    public Complex getFirstNumber() {
        return firstNumber;
    }

    public Complex getSecondNumber() {
        return secondNumber;
    }

    //EFFECTS: returns the inner product of two states
    public Complex innerProduct(State state) {
        return (this.firstNumber.times(state.firstNumber).plus(this.secondNumber.times(state.secondNumber)));
    }

    //EFFECTS: adds two states together
    public State addState(State state) {
        Complex comp1 = (this.firstNumber.plus(state.firstNumber));
        Complex comp2 = (this.secondNumber.plus(state.secondNumber));
        return new State(comp1, comp2);
    }

    @Override
    //EFFECTS: returns true if two states are equal, false otherwise.
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        return Objects.equals(firstNumber, state.firstNumber)
                && Objects.equals(secondNumber, state.secondNumber);
    }

    @Override
    //EFFECTS: returns the object's hash code
    public int hashCode() {
        return Objects.hash(firstNumber, secondNumber);
    }

    //EFFECTS: multiplies the state by a constant and returns the new state
    public State multiplyByConstant(double constant) {
        return new State(this.firstNumber.multiplyByConstant(constant), this.secondNumber.multiplyByConstant(constant));
    }

    public double normalize() {
        Complex x = this.firstNumber;
        Complex y = this.secondNumber;
        return (1 / Math.sqrt(x.absoluteValue() * x.absoluteValue() + y.absoluteValue() * y.absoluteValue()));
    }
}
