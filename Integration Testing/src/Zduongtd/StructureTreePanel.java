package Zduongtd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;

import trash.FileOb;

/**
 * Tạo một panel bên trái
 * @author Duong Td
 *
 */
public class StructureTreePanel extends JPanel{
	
	//chiều dài và chiều rộng
	private int width, height;
	
	//2 button OPen và Setting
	private JButton btnOpen, btnSetting;
	
	//sử dụng box layout cho thiết kế
	//thêm 1 headerboxlayout để chứa 2 button bên trên
	private BoxLayout boxLayout, headerBoxLayout;
	
	//chia panel làm 2 phần làm header và main
	private JPanel headerPanel, mainPanel;
	
	private JScrollPane scroll;
	private StructTree tree;
	private JFileChooser fileChooser;
	
	public StructureTreePanel(){
		super();
		boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxLayout);
		
		headerPanel = new JPanel();
		mainPanel = new JPanel(new BorderLayout());
		this.add(headerPanel);
		this.add(mainPanel);
		
		//header Panel
		headerBoxLayout = new BoxLayout(headerPanel, BoxLayout.X_AXIS);
		headerPanel.setLayout(headerBoxLayout);
		btnOpen = new JButton("Open");
		
		btnSetting = new JButton("Setting");
	    headerPanel.add(btnOpen);
	    headerPanel.add(btnSetting);
	    
	    fileChooser = new JFileChooser();
	    FileSystemView view = fileChooser.getFileSystemView();
	    scroll = new JScrollPane();
	    tree = new StructTree(new FileOb(view.getDefaultDirectory().getAbsolutePath()));
	    scroll.setViewportView(tree);
	    mainPanel.add(scroll, BorderLayout.CENTER);
	    SetEventBtn(this);
	}
	
	public void DrawTree(File fileRoot){
		fileChooser = new JFileChooser();
	    String strURL = fileRoot.getAbsolutePath();
	    System.out.println(strURL);
	    FileOb fileRootOb = new FileOb(strURL);
		tree = new StructTree(fileRootOb);
		tree.setEditable(true);
		scroll.setViewportView(tree);
	}
	
	public void SetEventBtn(JPanel panel){
		//scroll.setViewportView(null);
		btnOpen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    // Demonstrate "Open" dialog:
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    int rVal = fileChooser.showOpenDialog(headerPanel);
			    if (rVal == JFileChooser.APPROVE_OPTION) {
			    	File selectedFile = fileChooser.getSelectedFile();
			    	DrawTree(selectedFile);
			    }
			    if (rVal == JFileChooser.CANCEL_OPTION) {
			    	
			    }
			}
		});
	}
	
	public static void main(String[] args)
	{
		StructureTreePanel stPanel = new StructureTreePanel();
		JFrame frame = new JFrame("thu 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(stPanel);
		//frame.setSize(width, height);
	    frame.setMinimumSize(new Dimension(300, 500));
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
		
	}

}
