package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    private State state;
    private State state1;
    private State state2;
    private State state3;
    private State theSameState;

    @BeforeEach
    public void setUp() {
        Complex x = new Complex(1, 0);
        Complex y = new Complex(0, 1);
        Complex x1 = new Complex(2, 3);
        Complex y1 = new Complex(1, 2);
        state = new State(x, y);
        state1 = new State(x1, y1);
        state2 = new State(x, y1);
        state3 = new State(x1, y);
        theSameState = new State(x, y);
    }

    @Test
    public void testEquals() {
        Complex comp = new Complex(0, 0);
        assertEquals(state, state);
        assertEquals(state, theSameState);
        assertNotEquals(state, state1);
        assertNotEquals(state, state2);
        assertNotEquals(state, state3);
        assertNotEquals(state, comp);
        assertNotEquals(state1, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(1138785249, state.hashCode());
    }

    @Test
    public void testConstructor() {
        assertEquals(state.getFirstNumber().getX(), 1, 0.01);
        assertEquals(state.getFirstNumber().getY(), 0, 0.01);
        assertEquals(state.getSecondNumber().getX(), 0, 0.01);
        assertEquals(state.getSecondNumber().getY(), 1, 0.01);
        assertEquals(state.normalize(), 1 / Math.sqrt(2.0), 0.01);
    }

    @Test
    public void testInnerProduct() {
        assertEquals(state1.innerProduct(state).getX(), 0, 0.01);
        assertEquals(state1.innerProduct(state).getY(), 4.0, 0.01);
    }

    @Test
    public void testMultiplyByConstant() {
        Complex x1Times2 = new Complex(4, 6);
        Complex y1Times2 = new Complex(2, 4);
        State state1Times2 = new State(x1Times2, y1Times2);
        assertEquals(state1.multiplyByConstant(2), state1Times2);
    }
}
