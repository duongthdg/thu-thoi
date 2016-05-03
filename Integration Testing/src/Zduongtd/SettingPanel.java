package Zduongtd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 * Panel setting định dạng cho input
 * @author Duong Td
 *
 */
public class SettingPanel extends JPanel{

	//chia panel thành 2 phần trên dưới
	private JSplitPane splitPane;
	private JPanel panelUp, panelDown;
	
	private JScrollPane scroll;
	private BoxLayout boxLayout;
	private JComboBox coverage;
	
	//2 tabs source code và Input constraints
	private JTabbedPane tabs;
	
	//bảng định dạng cho input
	private MyTable table;
	
	//menu cho chuột phải của bảng
	private PopUpMenu popUpMenu;
	
	
	public SettingPanel(){
		super(new BorderLayout());
		
		//panelUp chứa scroll-chứa tabs
		panelUp = new JPanel(new BorderLayout());
		tabs = new JTabbedPane();
		scroll = new JScrollPane(tabs);
		panelUp.add(scroll, BorderLayout.CENTER);
		
		panelDown = new JPanel();
		Integer[] coverTypes = new Integer[] {1, 2, 3};
		coverage = new JComboBox<>(coverTypes);
		
		//Panel bên trên
		SetPanelUp();
		//Panel bên dưới
		SetPanelDown();
		
		//thêm 2 panel vào main panel dạng split-có thể thay đổi độ rộng
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelUp, panelDown);
		splitPane.setDividerSize(1);
		splitPane.setBorder(null);
		splitPane.setDividerLocation(200);
		this.add(splitPane, BorderLayout.CENTER);
	}
	
	//panel bên trên
	public void SetPanelUp(){
		//tạo bảng định dạng cho input
		CreateTable();
		
		//tạo 2 tabs (tab source code để trống)
		tabs.addTab("Source code", new JPanel());
		tabs.addTab("Input constraints", new JScrollPane(table));
	}
	
	//tạo bảng đinh dạng cho input
	public void CreateTable(){
		//ví dụ 1 bảng có data như dưới.
		
		table = new MyTable(new Vector(Arrays.asList(new Object[] {"Name", "Type", "Lower bound", "Upper bound"})));
		table.setRowHeight(20);
	    
		SetEventTable();
	}
	
	//panel bên dưới, chưa có gì vẫn để trống.
	public void SetPanelDown(){
		panelDown.add(coverage);
	}
	
	//tạo sự kiện chuột phải cho bảng định dạng
	public void SetEventTable(){
		popUpMenu = new PopUpMenu();
		//bắt sự kiện cho cả bảng, chính xác hơn là cần bắt sự kiện cho ô selected thôi, nhưng còn nhều bug
		table.addMouseListener(new MouseAdapter(){
			@Override
		    public void mouseClicked(MouseEvent e){
				if(SwingUtilities.isRightMouseButton(e))
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
		    }
		});
	}
	
	/**
	 * menu cho chuột phải
	 * @author Duong Td
	 *
	 */
	class PopUpMenu extends JPopupMenu implements ItemListener {
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
	    
	    /**
	     * Bắt sự kiện cho 1 item khi clicked
	     */
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			//xác định xam là item nào
			JRadioButtonMenuItem mi = (JRadioButtonMenuItem) arg0.getSource();
			
			//xác định Jmenu của Item (ví dụ item "Arial" là của Menu "Font"
			JPopupMenu jpm = (JPopupMenu) mi.getParent();
			JMenu menu = (JMenu) jpm.getInvoker();
			String menuName = menu.getText();//trả về tên của Menu chứa item đó
			
			//nếu là item của menu Font thì cài đặt font cho cells
			if(menuName.equalsIgnoreCase("Font")){
				String newFontName = mi.getText();
				table.setFont(newFontName, -1, -1);
			}
			
			//set Font Style
			else if(menuName.equalsIgnoreCase("Font style")){
				String newFontStyle = mi.getText();
				int newFontStyleInt = -1;
				if(newFontStyle.equalsIgnoreCase("Plain"))
					newFontStyleInt = Font.PLAIN;
				else if(newFontStyle.equalsIgnoreCase("Bold"))
					newFontStyleInt = Font.BOLD;
				else if(newFontStyle.equalsIgnoreCase("Italic"))
					newFontStyleInt = Font.ITALIC;
				else
					newFontStyleInt = Font.BOLD + Font.ITALIC;
				table.setFont(null, newFontStyleInt, -1);
			}
			
			//set Font Size
			else if(menuName.equalsIgnoreCase("Font size")){
				int newFontSize = Integer.parseInt(mi.getText());
				table.setFont(null, -1, newFontSize);
			}
		}
	}
	
	//test
	public static void main(String[] args){
		SettingPanel settingPanel = new SettingPanel();
		JFrame frame = new JFrame("thu 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(settingPanel);
		//frame.setSize(width, height);
	    frame.setMinimumSize(new Dimension(300, 500));
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
}
