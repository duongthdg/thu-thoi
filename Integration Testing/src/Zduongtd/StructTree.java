package Zduongtd;

import java.awt.Component;
import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import trash.FileOb;


/**
 * tạo cây file explorer
 * @author Duong Td
 *
 */

public class StructTree extends JTree{
	
	private FileOb rootFile;
	private DefaultMutableTreeNode rootNode;
	
	public StructTree(){}
	
	public StructTree(FileOb f){
		super(new DefaultMutableTreeNode(f));
		if(f != null){
		    rootFile = f;
		    rootNode = (DefaultMutableTreeNode) this.getModel().getRoot();
		    setCellRenderer(new TreeRender());
		    Process(rootFile, null);
		}
	}
	
	public void Process(FileOb file, DefaultMutableTreeNode parent){
		DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(file);
		
		if(parent != null)
			parent.add(dmtn);
		else{
			dmtn = rootNode; 
		}
		File[] childrenFiles = file.listFiles();
		if(childrenFiles != null){
			for(int fi = 0; fi < childrenFiles.length; fi++){
				Process(new FileOb(childrenFiles[fi].getAbsolutePath()), dmtn);
			}
		}
	}
	
	//class
	public class TreeRender extends DefaultTreeCellRenderer{
		public TreeRender(){}

		@Override
	    public Component getTreeCellRendererComponent(JTree tree, Object value,
		    boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
			//Icon icon;
			Object o = ((DefaultMutableTreeNode) value).getUserObject();
			if(o instanceof FileOb){
				FileOb file = (FileOb)o;
				if(file.listFiles() != null){
					setIcon(FileOb.iconFolder);
				}
				else{
					setIcon(FileOb.iconUk);
					System.out.println("file");
				}
			}
			
		    return this;
		}

	}

}
