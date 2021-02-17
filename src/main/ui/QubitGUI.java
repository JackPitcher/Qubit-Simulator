package ui;

import model.Complex;
import model.Qubit;
import model.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ui.QubitApp.QUBIT_FILE;

public class QubitGUI extends JFrame {

    private QubitApp qubitApp;
    private JPanel qubitArea;
    private MyTableModel model;

    public QubitGUI(QubitApp qubitApp) {
        super("QubitArea");
        this.qubitApp = qubitApp;
        this.model = new MyTableModel();
    }

    public MyTableModel getModel() {
        return model;
    }

    public JPanel getQubitArea() {
        return qubitArea;
    }

    // MODIFIES: this
    // EFFECTS:  declares and instantiates a Qubit (newQubit), and adds it to drawings
    void addNewQubit() {
        int reply = JOptionPane.showConfirmDialog(qubitApp,
                "Would you like to load a previously saved qubit?", "Load file",
                JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                qubitApp.loadQubit();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(qubitApp, "Could not find file!");
            }
        } else {
            String s1 =
                    JOptionPane.showInputDialog("Please input the first number in your qubit's initial state here:");
            String s2 =
                    JOptionPane.showInputDialog("Please input the second number in your qubit's initial state here:");
            Complex comp1 = new Complex(s1);
            Complex comp2 = new Complex(s2);
            State state = new State(comp1, comp2);
            qubitApp.setMyQubit(new Qubit(state));
            generateQubitArea();
        }
    }

    //EFFECTS: creates a new JPanel for the qubit area
    void generateQubitArea() {
        GridBagConstraints c = new GridBagConstraints();
        qubitArea = new JPanel();
        qubitArea.setLayout(new GridBagLayout());
        qubitArea.setSize(new Dimension(this.getPreferredSize()));

        initializeBlochSphere();
        generateTable();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;

        qubitApp.add(qubitArea, c);
    }

    //MODIFIES: this
    //EFFECTS: generates a table to display the states
    private void generateTable() {
        GridBagConstraints c = new GridBagConstraints();
        JTable table = new JTable(model);
        updateTable();
        table.setRowHeight(50);
        JScrollPane sp = new JScrollPane(table);
        sp.createHorizontalScrollBar();
        c.gridy = 1;
        c.gridx = 0;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        qubitArea.add(sp, c);
        qubitApp.validate();
    }

    //EFFECTS: updates the state table
    void updateTable() {
        int size = qubitApp.getMyQubit().getStates().size();
        String[][] data = new String[2][size];
        String[] names = new String[size];
        int i = 0;
        double scaleFactor = qubitApp.getMyQubit().getScaleFactor();
        for (State state : qubitApp.getMyQubit()) {
            data[0][i] = state.getFirstNumber().multiplyByConstant(scaleFactor).toString();
            data[1][i] = state.getSecondNumber().multiplyByConstant(scaleFactor).toString();
            int ind = i +  1;
            names[i] = "State " + ind;
            model.addColumn(names[i], new String[]{data[0][i], data[1][i]});
            i++;
        }
    }

    //MODIFIES: this
    //EFFECTS: places an image of a bloch sphere in the qubit app
    //SOURCES: The image used was found here: https://computer.howstuffworks.com/quantum-computer1.htm
    private void initializeBlochSphere() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        BufferedImage myPicture = null;
        try {
            ImageResizer.resize(QUBIT_FILE + "bloch-sphere.jpg",
                    QUBIT_FILE + "resized-bloch-sphere.jpg", 0.55);
            myPicture = ImageIO.read(new File(QUBIT_FILE + "resized-bloch-sphere.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(qubitApp, "Something went wrong loading the image!");
        }
        assert myPicture != null;
        panel.setSize(myPicture.getWidth(), myPicture.getHeight());
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panel.add(picLabel);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.weighty = 0.05;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
        c.fill = GridBagConstraints.BOTH;
        qubitArea.add(panel, c);
    }
}
