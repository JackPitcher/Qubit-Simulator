package model;

import java.text.DecimalFormat;

import static model.Complex.THRESHOLD;

public class ComplexString {

    private Complex num;
    private double real;
    private double im;

    public ComplexString(Complex num) {
        this.num = num;
    }

    //EFFECTS: parses a string and returns a new complex number
    public void toComplex(String s) {
        if (s.contains("+")) {
            String[] parts = s.split("\\+");
            this.real = Double.parseDouble(parts[0].replaceAll("[^\\d.\\-]", ""));
            parseImaginaryPart(parts[1]);
        } else if (s.contains("i")) {
            this.real = 0.00;
            parseImaginaryPart(s);
        } else {
            this.real = Double.parseDouble(s.replaceAll("[^\\d.\\-]", ""));
            this.im = 0.00;
        }
        num.setX(real);
        num.setY(im);
    }

    //EFFECTS: helper function to calculate the imaginary part of a complex string
    public void parseImaginaryPart(String s) {
        try {
            this.im = Double.parseDouble(s.replaceAll("[^\\d.\\-]", ""));
        } catch (Exception e) {
            if (s.contains("-")) {
                this.im = -1.00;
            } else {
                this.im = 1.00;
            }
        }
    }

    //EFFECTS: turns the complex number into a string
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        if (Math.abs(num.getY()) < THRESHOLD) {
            return df.format(num.getX());
        }
        if (Math.abs(num.getX()) < THRESHOLD) {
            return df.format(num.getY()) + "i";
        } else {
            return df.format(num.getX()) + " + " + df.format(num.getY()) + "i";
        }
    }
}
