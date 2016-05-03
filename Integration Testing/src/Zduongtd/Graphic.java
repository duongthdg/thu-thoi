package Zduongtd;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Graphic {
	
	private JFrame frame;
	private StructureTreePanel structTreePanel;
	private CFGPanel cFGPanel;
	private SettingPanel settingPanel;
	private JSplitPane splitPaneLeft, splitPane;
	
	public Graphic(){
		structTreePanel = new StructureTreePanel();
		
		cFGPanel = new CFGPanel();
		cFGPanel.AddTabWithPanel(1);
		settingPanel = new SettingPanel();
		
		splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, structTreePanel, cFGPanel);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPaneLeft, settingPanel);
		splitPane.setDividerSize(2);
		splitPane.setBorder(null);
		splitPaneLeft.setDividerSize(1);
		splitPaneLeft.setBorder(null);
		
		frame = new JFrame("Main GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(splitPane);
		//frame.setSize(width, height);
	    frame.setMinimumSize(new Dimension(600, 500));
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new Graphic();
	}
	
	

}
