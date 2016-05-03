package trash;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SplitPaneExp extends JFrame {
    
    public SplitPaneExp() {
         
        setTitle("Example of Split Pane");
        setSize(350, 350);
         
        JPanel jsp1 = new JPanel();
        JPanel jsp2 = new JPanel();
        JLabel j1 = new JLabel("Area 1");
        JLabel j2 = new JLabel("Area 2");
         
        jsp1.add(j1);
        jsp2.add(j2);
         
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp1, jsp2);
        //splitPane.setDividerLocation(0.7);
        
        JPanel jsp3 = new JPanel();
        JLabel j3 = new JLabel("Area 3");
        jsp3.add(j3);
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, jsp3);
        splitPane1.setDividerLocation(0.7);
        //splitPane.setOneTouchExpandable(true);
        getContentPane().add(splitPane1);
        splitPane1.setDividerSize(3);
		splitPane1.setBorder(null);
		splitPane.setDividerSize(3);
		splitPane.setBorder(null);
         
    }
    public static void main(String[] args) {
         
        SplitPaneExp sp = new SplitPaneExp();
        sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sp.setVisible(true);
         
    }
}