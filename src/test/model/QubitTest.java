package model;

import exceptions.NoStateToRemoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QubitTest {

    private Qubit qb1;
    private Qubit qb2;

    private Complex comp1;
    private Complex comp4;

    private State state1;
    private State state2;

    private Gate pauliZ;

    @BeforeEach
    public void setUp() {
        comp1 = new Complex(1, 0);
        Complex comp2 = new Complex(0, 1);
        Complex comp3 = new Complex(1, 1);
        comp4 = new Complex(3, 4);

        state1 = new State(comp1, comp2);
        state2 = new State(comp3, comp4);

        qb1 = new Qubit(state1);
        qb2 = new Qubit(state2);

        pauliZ = new Gate(1, 0, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals(qb1.getStates().size(), 1);
        assertEquals(qb1.getScaleFactor(), 1 / Math.sqrt(2), 0.01);
    }

    @Test
    public void testLoadConstructor() {
        Qubit qb = new Qubit();
        assertEquals(qb.getStates().size(), 0);
    }

    @Test
    public void testSetState() {
        qb1.addState(state2);
        assertEquals(qb1.getStates().size(), 2);
        assertEquals(qb1.getStates().get(0).getFirstNumber().getX(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(0).getFirstNumber().getY(), 0.0, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getX(), 0.0, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getY(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(1).getFirstNumber().getX(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(1).getFirstNumber().getY(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(1).getSecondNumber().getX(), 3.0, 0.01);
        assertEquals(qb1.getStates().get(1).getSecondNumber().getY(), 4.0, 0.01);
        assertEquals(qb1.getScaleFactor(), 1 / Math.sqrt(29), 0.01);
    }

    @Test
    public void testRemoveState() {
        State anotherState = new State(comp1, comp4);
        State theSameState = new State(comp1, comp4);
        qb1.addState(anotherState);
        qb1.addState(state2);
        try {
            qb1.removeState(theSameState);
        } catch (NoStateToRemoveException e) {
            fail("Unexpected NoStateToRemoveException");
        }
        assertEquals(qb1.getStates().get(0).getFirstNumber().getX(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(0).getFirstNumber().getY(), 0.0, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getX(), 0.0, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getY(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(1).getFirstNumber().getX(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(1).getFirstNumber().getY(), 1.0, 0.01);
        assertEquals(qb1.getStates().get(1).getSecondNumber().getX(), 3.0, 0.01);
        assertEquals(qb1.getStates().get(1).getSecondNumber().getY(), 4.0, 0.01);
        assertEquals(qb1.getScaleFactor(), 1 / Math.sqrt(39), 0.01);
    }

    @Test
    public void testRemoveStateNothingToRemove() {
        try {
            qb1.removeState(state2);
            fail("Did not catch exception!");
        } catch (NoStateToRemoveException e) {
            // all good!
        }
        assertEquals(1, qb1.getStates().size());
        assertEquals(qb1.getStates().get(0), state1);
    }

    @Test
    public void testMeasure() {
        Qubit qb = qb1.measure();
        assertEquals(qb.getStates().size(), 1);
        State state = qb.getStates().get(0);
        assertEquals(state.getFirstNumber().getX(), 1.0, 0.01);
        assertEquals(state.getFirstNumber().getY(), 0.0, 0.01);
        assertEquals(state.getSecondNumber().getX(), 0.0, 0.01);
        assertEquals(state.getSecondNumber().getY(), 1.0, 0.01);
        qb.addState(state2);
        qb = qb.measure();
        assertEquals(qb.getStates().size(), 1);
    }

    @Test
    public void testTransformSingleQubit() {
        Gate pauliX = new Gate(0, 1, 0);
        qb1.transform(pauliX);
        assertEquals(qb1.getStates().get(0).getFirstNumber().getX(), 0, 0.01);
        assertEquals(qb1.getStates().get(0).getFirstNumber().getY(), 1, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getX(), 1, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getY(), 0, 0.01);
        assertEquals(1, qb1.getStates().size());
    }

    @Test
    public void testTransformMultiQubit() {
        Gate pauliX = new Gate(0, 1, 0);
        qb1.addState(state2);
        qb1.transform(pauliX);
        assertEquals(2, qb1.getStates().size());
        assertEquals(qb1.getStates().get(0).getFirstNumber().getX(), 0, 0.01);
        assertEquals(qb1.getStates().get(0).getFirstNumber().getY(), 1, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getX(), 1, 0.01);
        assertEquals(qb1.getStates().get(0).getSecondNumber().getY(), 0, 0.01);
        assertEquals(qb1.getStates().get(1).getFirstNumber().getX(), 3, 0.01);
        assertEquals(qb1.getStates().get(1).getFirstNumber().getY(), 4, 0.01);
        assertEquals(qb1.getStates().get(1).getSecondNumber().getX(), 1, 0.01);
        assertEquals(qb1.getStates().get(1).getSecondNumber().getY(), 1, 0.01);
    }

    @Test
    public void testMeasureSpinPositiveZ() {
        Complex c1 = new Complex(1, 0);
        Complex zero = new Complex(0, 0);
        State eigenState = new State(c1, zero);
        State neverState = new State(zero, c1);
        Qubit eigenQB = new Qubit(eigenState);
        Qubit neverQB = new Qubit(neverState);
        assertEquals(eigenQB.measureProbability(pauliZ, true), 1, 0.01);
        assertEquals(neverQB.measureProbability(pauliZ, true), 0, 0.01);
        eigenQB.addState(neverState);
        assertEquals(eigenQB.measureProbability(pauliZ, true), 0.5, 0.01);
    }

    @Test
    public void testMeasureSpinNegativeZ() {
        Complex c1 = new Complex(1, 0);
        Complex zero = new Complex(0, 0);
        State eigenState = new State(c1, zero);
        State neverState = new State(zero, c1);
        Qubit eigenQB = new Qubit(eigenState);
        Qubit neverQB = new Qubit(neverState);
        assertEquals(eigenQB.measureProbability(pauliZ, false), 0, 0.01);
        assertEquals(neverQB.measureProbability(pauliZ, false), 1, 0.01);
        eigenQB.addState(neverState);
        assertEquals(eigenQB.measureProbability(pauliZ, false), 0.5, 0.01);
    }

    @Test
    public void testMeasureExpectationValueZ() {
        Complex c1 = new Complex(1, 0);
        Complex zero = new Complex(0, 0);
        State eigenState = new State(c1, zero);
        State neverState = new State(zero, c1);
        Qubit eigenQB = new Qubit(eigenState);
        Qubit neverQB = new Qubit(neverState);
        assertEquals(eigenQB.measureExpectationValue(pauliZ), 1.0, 0.01);
        assertEquals(neverQB.measureExpectationValue(pauliZ), -1.0, 0.01);
        assertEquals(qb1.measureExpectationValue(pauliZ), 0, 0.01);
        assertEquals(qb2.measureExpectationValue(pauliZ), -23.0 / 27.0, 0.01);
        eigenQB.addState(neverState);
        assertEquals(eigenQB.measureExpectationValue(pauliZ), 0, 0.01);
    }

    @Test
    public void testIterator() {
        qb1.addState(state2);
        List<State> states = new ArrayList<>();
        states.add(state1);
        states.add(state2);
        int i = 0;
        for (State state : qb1) {
            assertTrue(qb1.iterator().hasNext());
            assertEquals(state, states.get(i));
            i++;
        }
    }
}