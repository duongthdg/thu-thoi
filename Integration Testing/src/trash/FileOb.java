package trash;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import main.GUIMain;

public class FileOb extends File{
	
	public static Icon iconFolder = new ImageIcon(FileOb.class.getResource("/image/project.png"));
	public static Icon iconC = new ImageIcon("Image/c.png");
	public static Icon iconCpp = new ImageIcon("Image/cpp.png");
	public static Icon iconUk = new ImageIcon(FileOb.class.getResource("/image/text-file.png"));
	

	public FileOb(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString(){
		return this.getName();
	}

}
