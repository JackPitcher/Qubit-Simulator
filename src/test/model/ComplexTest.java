package model;

import exceptions.DivideByZeroException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

    private Complex comp1;
    private Complex comp2;

    @BeforeEach
    public void setUp() {
        comp1 = new Complex(8.0, 3.0);
        comp2 = new Complex(2.5, 1.5);
    }

    @Test
    public void testEquals() {
        Complex comp3 = new Complex(4.0 * 2.0, 9.0 / 3.0);
        Double num = 2.0;
        assertEquals(comp1, comp3);
        assertNotEquals(comp1, comp2);
        assertEquals(comp1, comp1);
        assertNotEquals(comp1, num);
        assertNotEquals(comp1, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(comp1.hashCode(), 65536961);
    }

    @Test
    public void testAbsoluteValue() {
        assertEquals(comp1.absoluteValue(), Math.sqrt(73.0));
        assertEquals(comp2.absoluteValue(), Math.sqrt(8.5));
    }

    @Test
    public void testConjugate() {
        Complex comp1conj = new Complex(8.0, -3.0);
        assertEquals(comp1.conjugate(), comp1conj);
    }

    @Test
    public void testPlus() {
        Complex comp1PlusComp2 = new Complex(10.5, 4.5);
        assertEquals(comp1.plus(comp2), comp1PlusComp2);
    }

    @Test
    public void testMinus() {
        Complex comp1MinusComp2 = new Complex(5.5, 1.5);
        assertEquals(comp1.minus(comp2), comp1MinusComp2);
    }

    @Test
    public void testTimes() {
        Complex comp1TimesComp2 = new Complex(15.5, 19.5);
        assertEquals(comp1.times(comp2), comp1TimesComp2);
    }

    @Test
    public void testDivides() {
        Complex comp1OverComp2 = new Complex(24.5/8.5, -4.5/8.5);
        try {
            assertEquals(comp1.divides(comp2), comp1OverComp2);
        } catch (DivideByZeroException e) {
            fail("Unexpected DivideByZeroException!");
        }
    }

    @Test
    public void testDividesByZero() {
        Complex zero = new Complex(0, 0);
        try {
            assertEquals(comp1.divides(zero), zero);
        } catch (DivideByZeroException e) {
            // all good!
        }
    }

    @Test
    public void testMultiplyByConstant() {
        Complex comp1Times2 = new Complex(16.0, 6.0);
        assertEquals(comp1.multiplyByConstant(2), comp1Times2);
    }

    @Test
    public void testParseString() {
        Complex comp1String = new Complex("8+3i");
        assertEquals(comp1, comp1String);
        Complex comp2String = new Complex("2.5 + 1.5i");
        assertEquals(comp2, comp2String);
        Complex comp3 = new Complex(1, 0);
        Complex comp4 = new Complex(0, 2.5);
        assertEquals(new Complex("1"), comp3);
        assertEquals(new Complex("2.5i"), comp4);
        Complex comp5 = new Complex(-1, 1);
        Complex comp6 = new Complex(0, 1);
        Complex comp7 = new Complex(0, -1);
        Complex comp8 = new Complex(1, -1);
        Complex comp9 = new Complex(-1, -1);
        assertEquals(new Complex("-1 + i"), comp5);
        assertEquals(new Complex("i"), comp6);
        assertEquals(new Complex("-i"), comp7);
        assertEquals(new Complex("1+-i"), comp8);
        assertEquals(new Complex("-1+-i"), comp9);
    }

    @Test
    public void testToString() {
        Complex comp3 = new Complex(4, 0);
        Complex comp4 = new Complex(-2, 0);
        Complex comp5 = new Complex(0, 4);
        Complex comp6 = new Complex(0, -1);
        Complex comp7 = new Complex(-1, -1);
        assertEquals(comp3.toString(), "4.00");
        assertEquals(comp4.toString(), "-2.00");
        assertEquals(comp5.toString(), "4.00i");
        assertEquals(comp6.toString(), "-1.00i");
        assertEquals(comp7.toString(), "-1.00 + -1.00i");
        assertEquals(comp1.toString(), "8.00 + 3.00i");
    }


}
