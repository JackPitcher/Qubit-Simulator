package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GateTest {

    private Gate pauliX;
    private Gate pauliY;
    private Gate pauliZ;
    private Gate identity;
    private Gate hadamard;

    private State state1;
    private State state2;
    private State state3;

    private Complex comp1;
    private Complex comp2;
    private Complex comp3;
    private Complex comp4;

    @BeforeEach
    public void setUp() {
        pauliX = new Gate(0, 1, 0 );
        pauliY = new Gate(0, 0, 1 );
        pauliZ = new Gate(1, 0, 0 );
        hadamard = new Gate(1, 1, 0);

        comp1 = new Complex(1, 0);
        comp2 = new Complex(0, 0);
        comp3 = new Complex(0, 1);
        comp4 = new Complex(-1, 0);
        Complex comp5 = new Complex(1, 1);
        Complex comp6 = new Complex(-1, 1);

        state1 = new State(comp1, comp2);
        state2 = new State(comp1, comp4);
        state3 = new State(comp5, comp6);
        identity = new Gate(comp1, comp2, comp2, comp1);
    }

    @Test
    public void testConstructor() {
        assertEquals(hadamard.getScale(), 1 / Math.sqrt(2), 0.01);
        assertEquals(hadamard.getBotLeft().getX(), 1, 0.01);
        assertEquals(hadamard.getBotLeft().getY(), 0, 0.01);
        assertEquals(hadamard.getBotRight().getX(), -1, 0.01);
        assertEquals(hadamard.getBotRight().getY(), 0, 0.01);
        assertEquals(hadamard.getTopLeft().getX(), 1, 0.01);
        assertEquals(hadamard.getTopLeft().getY(), 0, 0.01);
        assertEquals(hadamard.getTopRight().getX(), 1, 0.01);
        assertEquals(hadamard.getTopRight().getY(), 0, 0.01);
    }

    @Test
    public void testMultiply() {
        assertEquals(pauliZ.multiply(state1).getFirstNumber().getX(), 1, 0.01);
        assertEquals(pauliZ.multiply(state1).getFirstNumber().getY(), 0, 0.01);
        assertEquals(pauliZ.multiply(state1).getSecondNumber().getX(), 0, 0.01);
        assertEquals(pauliZ.multiply(state1).getSecondNumber().getY(), 0, 0.01);
        assertEquals(pauliX.multiply(state1).getFirstNumber().getX(), 0, 0.01);
        assertEquals(pauliX.multiply(state1).getFirstNumber().getY(), 0, 0.01);
        assertEquals(pauliX.multiply(state1).getSecondNumber().getX(), 1, 0.01);
        assertEquals(pauliX.multiply(state1).getSecondNumber().getY(), 0, 0.01);
        assertEquals(pauliY.multiply(state2).getFirstNumber().getX(), 0,0.01);
        assertEquals(pauliY.multiply(state2).getFirstNumber().getY(), -1, 0.01);
        assertEquals(pauliY.multiply(state2).getSecondNumber().getX(), 0, 0.01);
        assertEquals(pauliY.multiply(state2).getSecondNumber().getY(), -1, 0.01);
        assertEquals(hadamard.multiply(state3).getFirstNumber().getX(), 0,0.01);
        assertEquals(hadamard.multiply(state3).getFirstNumber().getY(), 2, 0.01);
        assertEquals(hadamard.multiply(state3).getSecondNumber().getX(), 2, 0.01);
        assertEquals(hadamard.multiply(state3).getSecondNumber().getY(), 0, 0.01);
    }

    @Test
    public void testMinus() {
        assertEquals(pauliZ.minus(pauliX).getTopLeft(), comp1);
        assertEquals(pauliZ.minus(pauliX).getTopRight(), comp4);
        assertEquals(pauliZ.minus(pauliX).getBotLeft(), comp4);
        assertEquals(pauliZ.minus(pauliX).getBotRight(), comp4);
    }

    @Test
    public void testMultiplyByConstant() {
        assertEquals(pauliX.multiplyByConstant(-1).getTopLeft(), comp2);
        assertEquals(pauliX.multiplyByConstant(-1).getTopRight(), comp4);
        assertEquals(pauliX.multiplyByConstant(-1).getBotLeft(), comp4);
        assertEquals(pauliX.multiplyByConstant(-1).getBotRight(), comp2);
    }

    @Test
    public void testGetTrace() {
        assertEquals(pauliX.getTrace(), comp2);
        assertEquals(identity.getTrace(), comp1.multiplyByConstant(2));
    }

    @Test
    public void testGetDeterminant() {
        assertEquals(pauliZ.getDeterminant(), comp1.multiplyByConstant(-1));
        assertEquals(hadamard.getDeterminant(), comp1.multiplyByConstant(-1));
    }

    @Test
    public void testGetEigenvalue() {
        assertEquals(pauliZ.getEigenValue(true), 1, 0.001);
        assertEquals(pauliZ.getEigenValue(false), -1, 0.001);
        assertEquals(hadamard.getEigenValue(true), 1, 0.001);
        assertEquals(hadamard.getEigenValue(false), -1, 0.001);
    }

    @Test
    public void testGetEigenstate() {
        assertEquals(pauliZ.getEigenState(true), state1);
        State flip1 = new State(comp2, comp4);
        assertEquals(pauliZ.getEigenState(false), flip1);
        State chiXPlus = new State(comp1, comp1);
        State chiXMinus = new State(comp1, comp4);
        assertEquals(pauliX.getEigenState(true), chiXPlus.multiplyByConstant(chiXPlus.normalize()));
        assertEquals(pauliX.getEigenState(false), chiXMinus.multiplyByConstant(chiXMinus.normalize()));
        State chiYPlus = new State(comp3.multiplyByConstant(-1), comp1);
        State chiYMinus = new State(comp3.multiplyByConstant(-1), comp4);
        assertEquals(pauliY.getEigenState(true), chiYPlus.multiplyByConstant(chiYPlus.normalize()));
        assertEquals(pauliY.getEigenState(false), chiYMinus.multiplyByConstant(chiYMinus.normalize()));
        Gate gate = new Gate(-1, 0, 0 );
        State flip2 = new State(comp2, comp1);
        State flip3 = new State(comp4, comp2);
        assertEquals(gate.getEigenState(true), flip2);
        assertEquals(gate.getEigenState(false), flip3);
        Complex hcomp1 = new Complex(0.92388, 0);
        Complex hcomp2 = new Complex(0.38268, 0);
        State hstate = new State(hcomp1, hcomp2);
        assertEquals(hadamard.getEigenState(true), hstate);
    }
}
