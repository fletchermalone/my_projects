import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Image; 
import javax.swing.ImageIcon; 
import javax.swing.JFrame; 
import javax.swing.JPanel;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class imageInFrame extends JFrame{
	public JPanel start(String imgname, JFrame frame){
		JPanel panel;
		if(checkIfFileExists(imgname)){
			panel = new imageImplement(new ImageIcon(imgname).getImage(), frame);
			return panel;}
		else{
			JOptionPane.showMessageDialog(null, "File not found :(", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			 }
		return null;
	}	
	public boolean checkIfFileExists(String imgname){
		File fn; fn = new File(imgname);
		return fn.exists();
	}
}