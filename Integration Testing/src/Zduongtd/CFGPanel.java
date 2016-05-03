package Zduongtd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class CFGPanel extends JPanel {
	private JTabbedPane tabs;
	
	public CFGPanel(){
		super(new BorderLayout());
		tabs = new JTabbedPane();
		add(tabs, BorderLayout.CENTER);
	}
	
	public void AddTab(JComponent c){
		tabs.addTab(c.getName(), c);
	}
	
	public void AddTabWithPanel(int index){
		JButton btn = new JButton("button " + index);
		JPanel panel = new JPanel();
		panel.setName("TAB - " + index);
		panel.add(btn);
		AddTab(panel);
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				AddTabWithPanel(index + 1);
			}
		});
	}
}
