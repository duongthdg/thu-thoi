package trash;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Main {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().setLayout(new BorderLayout());

    Object rowData[][] = {{"a", "int", "-10", "10"}, {"b", "int", "-10", "10"}};
	Object columnNames[] = {"Name", "Type", "Lower Bound", "Upper Bound"};
	JTable table = new JTable(rowData, columnNames);
    table.setRowHeight(80);
    table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
    JScrollPane pane = new JScrollPane(table);
    frame.getContentPane().add(BorderLayout.CENTER, pane);
    frame.setSize(300, 200);
    frame.setVisible(true);
  }
}

class ImageRenderer extends DefaultTableCellRenderer {
  JLabel lbl = new JLabel();

  ImageIcon icon = new ImageIcon(getClass().getResource("/image/close.png"));

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    //lbl.setText((String) value);
    lbl.setIcon(icon);
    return lbl;
  }
}