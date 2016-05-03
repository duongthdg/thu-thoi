package trash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class TestTable extends JPanel {
	
	private JTable table;
	private Vector<Vector<MyLabel>> labels;
	private JScrollPane scroll;
	
	public TestTable(){
		super(new BorderLayout());
		Vector<Object> columnNames = new Vector<Object>(Arrays.asList(new Object[]{"Name", "Type", "Lower", "Upper"}));
		Vector<MyLabel> firstRow = new Vector<MyLabel>();
		for(int fi = 0; fi < 4; fi++){
			MyLabel label = new MyLabel("0" + fi);
			firstRow.addElement(label);
		}
		Vector<MyLabel> secondRow = new Vector<MyLabel>();
		for(int fi = 0; fi < 4; fi++)
			secondRow.addElement(new MyLabel("1" + fi));
		
		labels = new Vector<Vector<MyLabel>>();
		labels.addElement(firstRow);
		labels.addElement(secondRow);
		setEventLabel();
		table = new JTable(labels, columnNames);
		scroll = new JScrollPane(table);
		this.add(scroll, BorderLayout.CENTER);
		setConstructor();
		
	}
	
	public void setEventLabel(){
		for(int fi = 0; fi < labels.size(); fi++)
			for(int se = 0; se < labels.size(); se++){
				labels.get(fi).get(se).addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
						if(SwingUtilities.isRightMouseButton(arg0))
							new PopUpMenu().show(arg0.getComponent(), arg0.getX(), arg0.getY());
							
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
	}
	
	public void setConstructor(){
		table.getTableHeader().setResizingAllowed(true);
		table.setColumnSelectionAllowed(true);
	    table.setRowSelectionAllowed(true);
	    table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);//cho phép chọn nhiều ô 1 lúc
	}
	
	public class MyLabel extends JLabel{
		public MyLabel(){
			super();
		}
		
		public MyLabel(String text){
			super(text);
		}
		
		@Override
		public String toString(){
			return this.getText();
		}
	}
	
	public class PopUpMenu extends JPopupMenu implements ItemListener {
		//font, font style, font size
		private JMenu fontMenu, fontStyleMenu, fontSizeMenu;
		//nhóm các item lại 1 group
		private ButtonGroup groupFont, groupFontStyle, groupFontSize;
		//các item cho từng menu
		private ArrayList<JRadioButtonMenuItem> fontName, fontStyle, fontSize; 
		
	    public PopUpMenu(){
	        fontMenu = new JMenu("Font");
	        fontStyleMenu = new JMenu("Font style");
	        fontSizeMenu = new JMenu("Font size");
	        
	        //thêm các font lựa chọ cho menu font
	        fontName = new ArrayList<JRadioButtonMenuItem>();
	        fontName.add(new JRadioButtonMenuItem("Arial"));
	        fontName.add(new JRadioButtonMenuItem("Calisto MT"));
	        fontName.add(new JRadioButtonMenuItem("Century"));
	        fontName.add(new JRadioButtonMenuItem("Comic Sans MS"));
	        fontName.add(new JRadioButtonMenuItem("Georgia"));
	        fontName.add(new JRadioButtonMenuItem("SansSerif"));
	        fontName.add(new JRadioButtonMenuItem("Tahoma"));
	        fontName.add(new JRadioButtonMenuItem("Times New Roman"));
	        fontName.add(new JRadioButtonMenuItem("Webdings"));
	        
	        //thêm các style lựa chọn cho font (Plain, Bold, Italic, Bold and Italic)
	        fontStyle = new ArrayList<JRadioButtonMenuItem>();
	        fontStyle.add(new JRadioButtonMenuItem("Plain", true));
	        fontStyle.add(new JRadioButtonMenuItem("Bold"));
	        fontStyle.add(new JRadioButtonMenuItem("Italic"));
	        fontStyle.add(new JRadioButtonMenuItem("Bold and Italic"));
	        
	        //size font (10->20)
	        fontSize = new ArrayList<JRadioButtonMenuItem>();
	        for(int fi = 10; fi < 18; fi++)
	        	fontSize.add(new JRadioButtonMenuItem(fi + ""));
	        
	        groupFont = new ButtonGroup();
	        groupFontStyle = new ButtonGroup();
	        groupFontSize = new ButtonGroup();
	        
	        //thêm sự kiện, gộp vào 1 group
	        for(int fi = 0; fi < fontName.size(); fi++){
	        	fontMenu.add(fontName.get(fi));
	        	fontName.get(fi).addItemListener(this);
	        	groupFont.add(fontName.get(fi));
	        }
	        
	        for(int fi = 0; fi < fontStyle.size(); fi++){
	        	fontStyleMenu.add(fontStyle.get(fi));
	        	fontStyle.get(fi).addItemListener(this);
	        	groupFontStyle.add(fontStyle.get(fi));
	        }
	        
	        for(int fi = 0; fi < fontSize.size(); fi++){
	        	fontSizeMenu.add(fontSize.get(fi));
	        	fontSize.get(fi).addItemListener(this);
	        	groupFontSize.add(fontSize.get(fi));
	        }
	        
	        add(fontMenu);
	        add(fontStyleMenu);
	        add(fontSizeMenu);
	    }

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args){
		TestTable testTable = new TestTable();
		JFrame frame = new JFrame("thu 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(testTable);
		//frame.setSize(width, height);
	    frame.setMinimumSize(new Dimension(300, 500));
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}

}
