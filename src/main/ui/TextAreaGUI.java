package ui;

import javax.swing.*;
import java.awt.*;

public class TextAreaGUI extends JFrame {

    private JTextArea results;
    private QubitApp qubitApp;

    public TextAreaGUI(QubitApp qubitApp) {
        super("Text Area");
        this.qubitApp = qubitApp;
        addTextArea();
    }

    public JTextArea getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results.setText(results);
    }

    //MODIFIES: this
    //EFFECTS: adds a text area where measurement results are displayed.
    private void addTextArea() {
        results = new JTextArea("Measurement Results: ");
        results.setFont(new Font("Arial", Font.BOLD, 30));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        results.setLocation(0, HEIGHT);
        qubitApp.add(results, c);
    }
}
