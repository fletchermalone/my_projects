import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.*;


public class manager extends appWindow{
	public static boolean isOpen=false;
	public static void newFile(){ //gets newfile information
		getNewFileSpecs();
	}
	public static void getNewFileSpecs(){ //creates jpanel with inputs and inserts it into a joptionpane for user entry
		JPanel inputPanel = new JPanel();
		JTextField xPx = new JTextField(4);
		JTextField yPx = new JTextField(4);
		JTextField fn = new JTextField(20);
		JComboBox<String> jc=new JComboBox<>();
		jc.addItem("JPEG"); jc.addItem("PNG");
		inputPanel.add(fn); inputPanel.add(jc);
		inputPanel.add(new JLabel("Width")); inputPanel.add(xPx);
		inputPanel.add(Box.createHorizontalStrut(15));
		inputPanel.add(new JLabel("Height")); inputPanel.add(yPx);
		JOptionPane.showConfirmDialog(null, inputPanel,"Enter desired name of New File and dimensions ", JOptionPane.OK_CANCEL_OPTION);
		try{
			newFile(fn.getText(), (String) jc.getSelectedItem(), Integer.parseInt(xPx.getText()), Integer.parseInt(yPx.getText()));
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error: Filename should be alphanumeric and dimensions must be integers", "Error" , JOptionPane.WARNING_MESSAGE);
			getNewFileSpecs();
		}
		
	}
	public static void newFile(String name, String type, int xdi, int ydi){ //once newfile information is known, creates new file
		picture=new imageImplement();
		String createFileName = picture.makeFileName(name, type);
		BufferedImage file = new BufferedImage(xdi, ydi, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) file.getGraphics();
		g.setColor(Color.WHITE); g.fillRect(0, 0, xdi, ydi);
		try{ ImageIO.write(file, type, new File(createFileName)); }
		catch(IOException e){ e.printStackTrace(); }
		openFile(createFileName);
	}
	public static void loadFile(){ //gets loadfile name
		String fn = null;
		fn = JOptionPane.showInputDialog("Enter File Name");
		openFile(fn);
	}
	public static void openFile(String fn){ //opens already created files
		picture = (imageImplement) new imageInFrame().start(fn, window); 
		current = picture;
		String[] params = fn.split(Pattern.quote("."));
		current.setName(params[0]); current.setType(params[1]); current.setFullName(fn);
		picture.scrollFrame = new JScrollPane(picture);
		picture.scrollFrame.setBorder(BorderFactory.createEmptyBorder());
		window.add(picture.scrollFrame);
		window.pack();
		isOpen=true;
		Dimension temp = window.getSize(); window.setSize((int)temp.getWidth()+50, (int)temp.getHeight()+50);
	}
	public static void saveRequest(){ //asks user if they would like to save (when a save is unprompted but maybe desired)
		String[] options={"Save", "Save As", "Close without Saving"};
		int sv= JOptionPane.showOptionDialog(null, "                            Choose a save option...", "Save File",
		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
		null, options, options[0]);
		if(sv==0) System.out.print("save");
		else if(sv==1) System.out.print("save as");
	}
	public static void saveFile(){ //saves current file as currently named
		try{
			ImageIO.write(current.getImage(), picture.getType() , new File(picture.getFullName()));
			JOptionPane.showMessageDialog(null, "Save Complete! Saved as " +picture.getFullName()); }
		catch(IOException e){ JOptionPane.showMessageDialog(null, "Error: Filename should be alphanumeric.", "Error" , JOptionPane.WARNING_MESSAGE); e.printStackTrace(); }
	}
	public static void saveFileAs(){ //saves file but asks if user would like to name it differently
		String s1="ERROR", s2="ERROR";
		JPanel inputPanel = new JPanel();
		JTextField fnF = new JTextField(20);
		inputPanel.add(new JLabel("File:")); inputPanel.add(fnF);
		inputPanel.add(Box.createHorizontalStrut(15));
		JOptionPane.showConfirmDialog(null, inputPanel,"Enter File Name as name.extension", JOptionPane.OK_CANCEL_OPTION);
		if(!(fnF.getText()).equals(current.getFullName())){
			if(Pattern.compile(Pattern.quote(".")).matcher(fnF.getText()).find()){
				String temp[] = fnF.getText().split(Pattern.quote("."));
				s1=temp[0];
				s2=temp[1]; }
		}
		else{
			int confirmOverwrite=JOptionPane.showConfirmDialog(null, fnF.getText() + " already exists. Overwrite it?");
			if(confirmOverwrite==0) saveFile(); }
		String fn = s1+"."+s2;
		try{
			ImageIO.write(current.getImage(), s2 , new File(fn));
			JOptionPane.showMessageDialog(null, "Save Complete! Saved as " +fn);
		}
		catch(IOException e){ JOptionPane.showMessageDialog(null, "Error: Filename should be alphanumeric and dimensions must be integers", "Error" , JOptionPane.WARNING_MESSAGE);
							e.printStackTrace(); }
	}
	public static void closeFile(){ //closes current image and resets application window
		isOpen=false;
		picture.scrollFrame.remove(picture);
		window.remove(picture.scrollFrame);
		picture=null;
		window.invalidate(); window.validate(); window.repaint(); window.setSize(700, 800);
	}
}