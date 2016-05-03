package Zduongtd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


/**
 * tạo table thừa kế từ JTable có sẵn để có thể định dạng cho từng ô khác nhau (JTable chỉ cho làm theo dòng or cột or all)
 * @author Duong Td
 * có 2 phương thức khởi tạo:
 * new MyTable(
 */
public class MyTable extends JTable{
	
	//số dòng số cột
	private int col, row;
	
	//các dữ liệu của bảng
	private Vector<Vector<Object>> rowData;
	
	//tiêu để các cột
	private Vector<Object> columnNames;
	
	//List<Cell>
	public CellTable cells;
	public TableCellRenderer tableCellRenderer;
	
	public MyTable(Vector<Object> colNames){
		super(null, colNames);
		columnNames = colNames;
		rowData = new Vector<Vector<Object>>();
		col = colNames.size();
		row = 0;
		cells = new CellTable(0, col);
		Test();
		setConstructor();
		
	}
	
	public MyTable(Vector<Vector<Object>> data, Vector<Object> colNames){
		super(data, colNames);
		rowData = data;
		columnNames = colNames;
		
		row = data.size();
		col = colNames.size();
		setConstructor();
		for(int fi = 0; fi < row; fi++){
    		for(int se = 0; se< col; se++){
    			Cell c = cells.ta.get(fi).get(se);
    			c.background = Color.WHITE;//bỏ đi cũng ok giừo kô dùng nữa
    			c.foreground = Color.BLACK;
    		}
    	}
		cells = new CellTable(row, col);
	}
	
	//Cài đặt 1 số thứ linh tinh
	public void setConstructor(){
		getTableHeader().setResizingAllowed(true);
		this.setColumnSelectionAllowed(true);
	    this.setRowSelectionAllowed(true);
	    this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);//cho phép chọn nhiều ô 1 lúc
		tableCellRenderer = new TableCellRenderer();
		setDefaultRenderer(Object.class, tableCellRenderer);
	}
	
	//test thử bằng data như dưới
	public void Test(){
		this.addRow(new Object[]{"a", "int", "-10", "10"});
		this.addRow(new Object[]{"b", "real", "-100", "100"});
		this.addRow(new Object[]{"m", "double", "-10000", "10000"});
	}
	
	//add thêm data
	public void addRow(Object[] rowDt){
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		model.addRow(rowDt);
		rowData.add(new Vector(Arrays.asList(rowDt)));
		row++;
		cells.addRow();
	}
	
	//set foreground - font color; nhưng kô dùng có thể bỏ đi
	public void setBg(Color _color){
		for(int fi = 0; fi < row; fi++){
    		for(int se = 0; se< col; se++){
    			Cell c = cells.ta.get(fi).get(se);
    			if(c.isSelected)
    				c.background = _color;
    		}
    	}
	}
	
	//set background - background cho cell; nhưng kô dùng có thể bỏ đi
	public void setFg(Color _color){
		for(int fi = 0; fi < row; fi++){
    		for(int se = 0; se< col; se++){
    			Cell c = cells.ta.get(fi).get(se);
    			if(c.isSelected)
    				c.foreground = _color;
    		}
    	}
	}
	
	//set Font
	public void setFont(Font font){
		for(int fi = 0; fi < row; fi++){
    		for(int se = 0; se< col; se++){
    			Cell c = cells.ta.get(fi).get(se);
    			if(c.isSelected){
    				c.font = font;
    			}
    		}
		}
	}
	
	/**
	 * set Font bằng 3 params
	 * @param newFontName: tên Font; = null nếu kô thay đổi so vs trươcs
	 * @param newFontStyle: font style (bold, italic,..); =-1 nếu kô thay đổi so vs trước
	 * @param newFontSize: font size; =-1 nếu kô thay dổi so vs trước
	 */
	public void setFont(String newFontName, int newFontStyle, int newFontSize){
		for(int fi = 0; fi < row; fi++){
    		for(int se = 0; se< col; se++){
    			Cell c = cells.ta.get(fi).get(se);
    			if(c.isSelected){
    				if(newFontName == null)
    					newFontName = c.font.getFontName();
    				if(newFontStyle == -1)
    					newFontStyle = c.font.getStyle();
    				if(newFontSize == -1)
    					newFontSize = c.font.getSize();
    				c.font = new Font(newFontName, newFontStyle, newFontSize);
    			}
    		}
		}
	}
	
	
	/**
	 * 1 ô trong bảng
	 * @author Duong Td
	 *
	 */
	class Cell{
		private String name;
		private boolean isSelected;
		private Color foreground = Color.BLACK, background = Color.WHITE;//kô dùng nữa bỏ đi cũng được
		private Font font;//font của ô
		
		public Cell(){}
		public Cell(String n){
			name = n;
			isSelected = false;
			font = new Font("Arial", Font.PLAIN, 12);
		}
	}
	
	
	/**
	 * các ô trong bảng
	 * @author Duong Td
	 *
	 */
	class CellTable{
		private int x, y;//độ dài rộng
		Vector<Vector<Cell>> ta = new Vector<>();
		
		public CellTable(int _x, int _y){
			x = _x;
			y = _y;
			
			for(int fi = 0; fi < x; fi++){
				Vector<Cell> row = new Vector<Cell>();
				for(int se = 0; se< y; se++){
					row.addElement(new Cell(fi + "" + se));
				}
				ta.addElement(row);
			}
		}
		
		public void setSelect(int _x, int _y){
			ta.get(_x).get(_y).isSelected = true;
		}
		
		public void setUnSelect(int _x, int _y){
			ta.get(_x).get(_y).isSelected = false;
		}
		
		public void addRow(){
			Vector<Cell> newRow = new Vector<Cell>();
			for(int fi = 0; fi < y; fi++){
				newRow.addElement(new Cell(x + "" + fi));
			}
			x++;
			ta.addElement(newRow);
		}
	}
	
	
	/**
	 * định dạng vẽ cho ô trong bảng
	 * @author Duong Td
	 *
	 */
	class TableCellRenderer extends DefaultTableCellRenderer{
		
	    Component c;
	    private static final long serialVersionUID = 1L;
	    private final Color selectionBlue = new Color(131,166,198);
	    private final MatteBorder border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
	    
	     @Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		    c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		    if (table.isCellSelected(row, column)){
		        c.setBackground(this.selectionBlue);
		        setBorder(this.border);
		        //thay đổi font
		        c.setFont(cells.ta.get(row).get(column).font);
		        
		        cells.setSelect(row, column);
		    }
		    else {
		    	cells.setUnSelect(row, column);
		    	Cell tempCell = cells.ta.get(row).get(column);
		        c.setBackground(tempCell.background);
		        //c.setForeground(tempCell.foreground);
		        //thay đổi font
		        c.setFont(tempCell.font);
		    }
		    return c;
		}
	}
}

