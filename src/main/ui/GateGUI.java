package ui;

import model.Gate;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GateGUI extends JFrame {

    private QubitApp qubitApp;
    private JPanel gateArea;
    private JTable gateTable;
    private JPanel buttArea;
    private JButton updateButt;
    private JButton transformButt;
    private JButton measureButt;
    private JButton measureInBasisButt;
    private JButton measureEVButt;

    public GateGUI(QubitApp qubitApp) {
        super("GateGUI");
        this.qubitApp = qubitApp;
        createGate();
    }

    public JPanel getGateArea() {
        return gateArea;
    }

    public JTable getGateTable() {
        return gateTable;
    }

    //MODIFIES: this
    //EFFECTS: creates a gate with initial values set to display a general Hermitian matrix
    private void createGate() {
        GridBagConstraints c = new GridBagConstraints();
        gateArea = new JPanel();
        gateArea.setLayout(new GridLayout(7, 2));
        gateArea.setSize(new Dimension(gateArea.getPreferredSize()));
        createButtArea();
        initializeButtons();
        initializeGate();
        this.gateArea.add(updateButt);
        this.gateArea.add(measureButt);
        this.gateArea.add(measureEVButt);
        this.gateArea.add(measureInBasisButt);
        this.gateArea.add(transformButt);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.ABOVE_BASELINE;
        c.fill = GridBagConstraints.BOTH;
        qubitApp.add(gateArea, c);
    }

    //EFFECTS: initializes the gate with a general Hermitian matrix
    private void initializeGate() {
        gateTable = new JTable(2, 2);
        gateTable.setRowHeight(40);
        gateTable.setValueAt("a", 0, 0);
        gateTable.setValueAt("b - ci", 0, 1);
        gateTable.setValueAt("b + ci", 1, 0);
        gateTable.setValueAt("-a", 1, 1);
        gateArea.add(gateTable);
    }

    //EFFECTS: creates an area where save, add, load, and remove buttons are displayed
    private void createButtArea() {
        buttArea = new JPanel();
        buttArea.setLayout(new GridLayout(2,2));
        createAddButton();
        createRemoveButton();
        createSaveButton();
        createLoadButton();
        this.gateArea.add(buttArea);
    }

    //EFFECTS: initializes all buttons
    private void initializeButtons() {

        createUpdateButton();
        createTransformButton();
        createMeasureButton();
        createMeasureInBasisButton();
        createMeasureExpectationValueButton();
    }

    private void createLoadButton() {
        JButton loadButt = new JButton("Load");
        loadButt.addActionListener(e -> {
            try {
                this.qubitApp.loadQubit();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not find file!");
            }
        });
        buttArea.add(loadButt);
    }

    private void createSaveButton() {
        JButton saveButt = new JButton("Save");
        saveButt.addActionListener(e -> {
            try {
                this.qubitApp.saveQubit();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not save qubit!");
            }
        });
        buttArea.add(saveButt);
    }

    private void createRemoveButton() {
        JButton removeButt = new JButton("Remove a State");
        removeButt.addActionListener(e -> this.qubitApp.remove());
        buttArea.add(removeButt);
    }

    private void createAddButton() {
        JButton addButt = new JButton("Add a new State");
        addButt.addActionListener(e -> this.qubitApp.addState());
        buttArea.add(addButt);
    }

    private void createMeasureExpectationValueButton() {
        measureEVButt = new JButton("Measure Qubit's expectation value with Gate");
        measureEVButt.addActionListener(e -> this.qubitApp.expectationValue());

    }

    private void createMeasureInBasisButton() {
        measureInBasisButt = new JButton("Measure Qubit with Gate");
        measureInBasisButt.addActionListener(e -> this.qubitApp.measureInBasis());

    }

    private void createMeasureButton() {
        measureButt = new JButton("Measure Qubit");
        measureButt.addActionListener(e -> this.qubitApp.measureQubit());

    }

    private void createTransformButton() {
        transformButt = new JButton("Transform Qubit");
        transformButt.addActionListener(e -> this.qubitApp.transformQubit());

    }

    private void createUpdateButton() {
        updateButt = new JButton("Update Gate");
        updateButt.addActionListener(e -> this.qubitApp.updateGate());

    }
}
