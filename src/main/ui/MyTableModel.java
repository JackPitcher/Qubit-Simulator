package ui;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Vector;

// A table model that lets the user remove columns
public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
        super();
    }

    //MODIFIES: this
    //EFFECTS: removes the column at the given index
    //SOURCES: code inspired from https://stackoverflow.com/questions/19758002/how-to-insert-delete-column-to-jtable-java
    @SuppressWarnings("rawtypes")
    public void removeColumn(int index) {
        Vector rows = dataVector;
        for (Object row : rows) {
            ((Vector) row).remove(index);
        }
        columnIdentifiers.remove(index);
        fireTableStructureChanged();
    }
}
