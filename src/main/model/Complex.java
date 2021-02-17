package model;

import exceptions.DivideByZeroException;

import java.text.DecimalFormat;
import java.util.Objects;

// represents a complex number, having a real and an imaginary part
public class Complex {

    public static final double THRESHOLD = 0.001;

    private double re;
    private double im;

    public Complex(double real, double imaginary) {
        this.re = real;
        this.im = imaginary;
    }


    //EFFECTS: parses a string and returns a new complex number
    public Complex(String s) {
        ComplexString compString = new ComplexString(this);
        compString.toComplex(s);
    }

    //getters and setters

    public double getX() {
        return re;
    }

    public double getY() {
        return im;
    }

    public void setX(double re) {
        this.re = re;
    }

    public void setY(double im) {
        this.im = im;
    }

    //EFFECTS: returns the absolute value of this
    public double absoluteValue() {
        return Math.sqrt((this.re * this.re) + (this.im * this.im));
    }

    //EFFECTS: flips the sign on the imaginary part of this
    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }

    //EFFECTS: performs complex addition on two complex numbers.
    public Complex plus(Complex num) {
        double x = this.re + num.re;
        double y = this.im + num.im;
        return new Complex(x, y);
    }

    //EFFECTS: performs complex subtraction on two complex numbers
    public Complex minus(Complex num) {
        double x = this.re - num.re;
        double y = this.im - num.im;
        return new Complex(x, y);
    }

    //EFFECTS: performs complex multiplication on two complex numbers
    public Complex times(Complex num) {
        double x = this.re * num.re - this.im * num.im;
        double y = this.im * num.re + this.re * num.im;
        return new Complex(x, y);
    }

    //EFFECTS: divides this by the given complex number
    public Complex divides(Complex num) throws DivideByZeroException {
        Complex numerator = this.times(num.conjugate());
        double denominator = num.absoluteValue() * num.absoluteValue();
        if (denominator == 0) {
            throw new DivideByZeroException();
        }
        double x = numerator.re / denominator;
        double y = numerator.im / denominator;
        return new Complex(x, y);
    }

    //EFFECTS: multiplies this by a constant and returns the new value
    public Complex multiplyByConstant(double scale) {
        return new Complex(re * scale, im * scale);
    }


    //EFFECTS: turns the complex number into a string
    @Override
    public String toString() {
        ComplexString compString = new ComplexString(this);
        return compString.toString();
    }


    @Override
    // EFFECTS: returns true if two complex numbers are equal, false otherwise
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Complex complex = (Complex) o;
        return (Math.abs(this.re - complex.re) < THRESHOLD) && (Math.abs(this.im - complex.im) < THRESHOLD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }
}
