package Zduongtd;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class Test {
	public static void main(String[] args){
		JColorChooser tcc = new JColorChooser(Color.YELLOW);
		JFrame frame = new JFrame("thu 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(tcc);
		//frame.setSize(width, height);
	    frame.setMinimumSize(new Dimension(300, 500));
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}

}
