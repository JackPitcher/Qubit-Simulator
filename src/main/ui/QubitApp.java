package ui;

import com.google.gson.*;
import exceptions.NoStateToRemoveException;
import model.Complex;
import model.Gate;
import model.Qubit;
import model.State;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;

// An app that simulates a qubit
public class QubitApp extends JFrame {

    public static final String QUBIT_FILE = "./data/";
    private static DecimalFormat df = new DecimalFormat("0.00");
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private Qubit myQubit;
    private Gate currGate;

    private QubitGUI qubitArea;
    private GateGUI gateArea;
    private TextAreaGUI textArea;

    //EFFECTS: runs the qubit app
    public QubitApp() {
        super("Qubit App");
        initializeGraphics();
    }

    public Qubit getMyQubit() {
        return myQubit;
    }

    public void setMyQubit(Qubit myQubit) {
        this.myQubit = myQubit;
    }

    // MODIFIES: this
    // EFFECTS:  initializes graphics so that the qubit app will run in the gui
    private void initializeGraphics() {
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        qubitArea = new QubitGUI(this);
        qubitArea.addNewQubit();
        gateArea = new GateGUI(this);
        textArea = new TextAreaGUI(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //EFFECTS: lets a user choose which state they want to add, and then adds it to the qubit
    public void addState() {
        String s1 = JOptionPane.showInputDialog("Please input the first number in your qubit's initial state here:");
        String s2 = JOptionPane.showInputDialog("Please input the second number in your qubit's initial state here:");
        int size = myQubit.getStates().size();
        while (size > 0) {
            qubitArea.getModel().removeColumn(size - 1);
            size--;
        }
        Complex comp1 = new Complex(s1);
        Complex comp2 = new Complex(s2);
        State newState = new State(comp1, comp2);
        myQubit.addState(newState);
        qubitArea.updateTable();
    }

    //EFFECTS: lets the user choose a state, and then removes it.
    public void remove() {
        String s1 = JOptionPane.showInputDialog("Please input the first number in your qubit's initial state here:");
        String s2 = JOptionPane.showInputDialog("Please input the second number in your qubit's initial state here:");
        Complex comp1 = new Complex(s1);
        Complex comp2 = new Complex(s2);
        State newState = new State(comp1, comp2);
        try {
            myQubit.removeState(newState);
            qubitArea.getModel().setColumnCount(0);
            qubitArea.updateTable();
        } catch (NoStateToRemoveException e) {
            JOptionPane.showMessageDialog(this, "That state does not exist!");
        }
    }

    //MODIFIES: this
    //EFFECTS: saves a qubit to a file
    //SOURCES: code inspired from https://stackoverflow.com/questions/19459082/read-and-write-data-with-gson/19459884
    public void saveQubit() throws IOException {
        String fname = JOptionPane.showInputDialog("What would you like your file to be called?",
                JOptionPane.CANCEL_OPTION);
        Gson gson = new Gson();
        String output = gson.toJson(myQubit.getStates());
        File myFile = new File(QUBIT_FILE + fname);
        //noinspection ResultOfMethodCallIgnored
        myFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fout);
        myOutWriter.append(output);
        myOutWriter.close();
        fout.close();
    }

    // EFFECTS: loads a qubit from a file
    // SOURCES: code inspired from https://howtodoinjava.com/gson/gson-jsonparser/
    //          and https://stackoverflow.com/questions/19459082/read-and-write-data-with-gson/19459884
    public void loadQubit() throws IOException {
        String fname = JOptionPane.showInputDialog("Which file would you like to open?", JOptionPane.CANCEL_OPTION);
        File myFile = new File(QUBIT_FILE + fname);
        FileInputStream fileIn = new FileInputStream(myFile);
        BufferedReader myReader = new BufferedReader(new InputStreamReader(fileIn));
        Gson gson = new Gson();
        JsonArray json = gson.fromJson(myReader, JsonArray.class);
        myQubit = new Qubit();
        for (JsonElement jo : json) {
            JsonObject firstNum = (JsonObject) ((JsonObject) jo).get("firstNumber");
            JsonObject secondNum = (JsonObject) ((JsonObject) jo).get("secondNumber");
            double re1 = firstNum.get("re").getAsDouble();
            double re2 = secondNum.get("re").getAsDouble();
            double im1 = firstNum.get("im").getAsDouble();
            double im2 = secondNum.get("im").getAsDouble();
            Complex comp1 = new Complex(re1, im1);
            Complex comp2 = new Complex(re2, im2);
            State state = new State(comp1, comp2);
            myQubit.addState(state);
        }
        qubitArea.generateQubitArea();
    }

    //MODIFIES: this
    //EFFECTS: updates the gate with new values
    void updateGate() {
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();

        Object[] inputFields = {"Please enter your A value:", field1,
                "Please enter your B value:", field2,
                "Please enter your C value:", field3};

        int option = JOptionPane.showConfirmDialog(this, inputFields, "Multiple Inputs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());
            double c = Double.parseDouble(field3.getText());
            gateArea.getGateTable().setValueAt(df.format(a), 0, 0);
            gateArea.getGateTable().setValueAt(df.format(-a), 1, 1);
            gateArea.getGateTable().setValueAt(df.format(b) + "+" + df.format(c) + "i", 1, 0);
            gateArea.getGateTable().setValueAt(df.format(b) + "-" + df.format(c) + "i", 0, 1);
            currGate = new Gate(a, b, c);
        }
    }

    //EFFECTS: lets a user choose a gate in order to transform a qubit, and shows them the states afterwards
    void transformQubit() {
        myQubit.transform(currGate);
        int size = myQubit.getStates().size();
        while (size > 0) {
            qubitArea.getModel().removeColumn(size - 1);
            size--;
        }
        qubitArea.updateTable();
    }

    //EFFECTS: measures a qubit and returns a random state
    void measureQubit() {
        Qubit luckyQubit = myQubit.measure();
        State luckyState = luckyQubit.getStates().get(0);
        double x1 = luckyState.getFirstNumber().getX();
        double y1 = luckyState.getFirstNumber().getY();
        double x2 = luckyState.getSecondNumber().getX();
        double y2 = luckyState.getSecondNumber().getY();
        double prob = (myQubit.getScaleFactor() * myQubit.getScaleFactor())
                / (luckyState.normalize() * luckyState.normalize());
        textArea.setResults("You got the state " + "( " + df.format(x1 * myQubit.getScaleFactor()) + " + "
                + df.format(y1 * myQubit.getScaleFactor()) + "i, "
                + df.format(x2 * myQubit.getScaleFactor()) + " + "
                + df.format(y2 * myQubit.getScaleFactor()) + "i ), which had a"
                + " probability of " + df.format(prob * 100) + "%!");
        int size = myQubit.getStates().size();
        while (size > 0) {
            qubitArea.getModel().removeColumn(size - 1);
            size--;
        }
        myQubit = luckyQubit;
        qubitArea.updateTable();
    }

    //EFFECTS: lets a user choose a gate and which direction they want to measure in, then outputs the probability that
    //         they measure that eigenvalue.
    void measureInBasis() {
        String[] buttons = {"positive", "negative"};
        int input = JOptionPane.showOptionDialog(this, "Would you "
                + "like to measure in the positive or negative direction?", "Positive/Negative Direction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        boolean isPositive = false;
        if (input == 0) {
            isPositive = true;
        }
        double prob = myQubit.measureProbability(currGate, isPositive);
        textArea.setResults("Measurement Results: The probability that you measure "
                + (int) (currGate.getEigenValue(isPositive))
                + " is: " + df.format(prob * 100) + "%.");
    }

    //EFFECTS: lets a user choose a gate, then outputs the expectation value
    public void expectationValue() {
        double prob = myQubit.measureExpectationValue(currGate);
        textArea.setResults("Measurement Results: The average value you would measure with this gate is: "
                + df.format(prob));
    }

    public static void main(String[] args) {
        new QubitApp();
    }
}
