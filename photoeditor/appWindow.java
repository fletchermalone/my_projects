import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import javax.imageio.*;
import java.awt.image.*;

public class appWindow extends JFrame implements ActionListener{ //builds application window

	protected static JFrame window;								
	protected static JMenuBar menuBar; private JPanel sideMenu,menuContent ;
	protected static JMenuBar tools;
	protected static JScrollPane scroll;

	protected static imageImplement picture;
	protected static imageImplement current;
	private paint p;  private paint eras;
	private rectangularSelect sel;  private	ellipticalSelect selE;

	private JButton recSel; private JButton eye; private JButton eraser;  
 	private JMenuItem crop;   private JMenuItem blur; private JMenuItem sharpen;
	private JButton elipSel; private JMenuItem smudge; private JButton paint;
	private JMenuItem paintOptions;
	private JMenuItem recShape; 

	private static boolean paintStatus=false; private static boolean eyeDropStatus=false; private static boolean ellipSelStatus=false;
	private static boolean recSelStatus = false; private static boolean eraserStatus=false;

	private JButton currentColor;
	private JMenuItem elipShape;
	private JButton redC;
	private JButton blackC;
	private JButton whiteC;
	private JButton blueC;
	private JButton yellowC;
	private JButton orangeC;
	private JButton greenC; 

	public void makeTopMenu(){ //menu for file, edit, tools, effects and help

		JMenu file = new JMenu("File");//creates File menu
		JMenuItem newFile = new JMenuItem("New File");
		JMenuItem openFile= new JMenuItem("Open File");
		JMenuItem save= new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save As...");
		JMenuItem closeFile = new JMenuItem("Close File");
		file.add(newFile); file.add(openFile); file.add(closeFile);
		file.add(new JSeparator(JSeparator.HORIZONTAL));
		file.add(save); file.add(saveAs);
	
		JMenuItem rtclk = new JMenuItem("Rotate 90º Clockwise");
		JMenuItem rtctclk = new JMenuItem("Rotate 90º Counter-Clockwise");
		

		JMenu selection = new JMenu("Actions");
		recShape = new JMenuItem("Rectangular Shape Create"); elipShape= new JMenuItem("Elliptical Shape Create"); crop = new JMenuItem("Crop Image..."); 
		selection.add(recShape); selection.add(elipShape);	 selection.add(crop); selection.add(new JSeparator(JSeparator.HORIZONTAL));selection.add(rtclk); selection.add(rtctclk);

		JMenu effects = new JMenu("Effects");
		blur = new JMenuItem("Image Blur"); sharpen = new JMenuItem("Sharpen"); smudge = new JMenuItem("Selective Image Blur"); paintOptions = new JMenuItem("Paint Options...");
		effects.add(blur); effects.add(smudge); effects.add(sharpen); effects.add(new JSeparator(JSeparator.HORIZONTAL)); effects.add(paintOptions);
		JMenu help = new JMenu("Help"); 

		newFile.addActionListener(this); openFile.addActionListener(this); save.addActionListener(this); paintOptions.addActionListener(this);
		saveAs.addActionListener(this); closeFile.addActionListener(this);  blur.addActionListener(this); smudge.addActionListener(this);
		crop.addActionListener(this); menuBar.add(file); sharpen.addActionListener(this);  elipShape.addActionListener(this); 
		menuBar.add(selection); menuBar.add(effects); menuBar.add(help); rtclk.addActionListener(this); rtctclk.addActionListener(this);
		recShape.addActionListener(this);
	}
	public void makesideMenuu(){ //builds menu for paint, default paint color options, eraser, marquee selections and eyedropper
		
		eye = new JButton(); //eye.setPreferredSize(new Dimension(40,40)); 
		eraser = new JButton(); //eraser.setPreferredSize(new Dimension(40, 40));
		paint = new JButton(); //paint.setPreferredSize(new Dimension(40, 40));
		recSel = new JButton(); 
		elipSel = new JButton();
		recSel.setIcon(new ImageIcon(this.getClass().getResource("rectangularicon.png")));
		eye.setIcon(new ImageIcon(this.getClass().getResource("eyedroppericon.png")));
		eraser.setIcon(new ImageIcon(this.getClass().getResource("erasericon.png")));
		paint.setIcon(new ImageIcon(this.getClass().getResource("painticon.png")));
		elipSel.setIcon(new ImageIcon(this.getClass().getResource("ellipticalicon.png")));
		redC = new JButton(); redC.setIcon(new ImageIcon(this.getClass().getResource("redIcon.jpg"))); 
		blackC = new JButton(); blackC.setIcon(new ImageIcon(this.getClass().getResource("blackIcon.jpg")));
		whiteC = new JButton(); whiteC.setIcon(new ImageIcon(this.getClass().getResource("whiteIcon.jpg")));
		blueC= new JButton(); blueC.setIcon(new ImageIcon(this.getClass().getResource("blueIcon.jpg")));
	 	yellowC= new JButton(); yellowC.setIcon(new ImageIcon(this.getClass().getResource("yellowIcon.jpg")));
	 	orangeC= new JButton(); orangeC.setIcon(new ImageIcon(this.getClass().getResource("orangeIcon.jpg")));
	 	greenC = new JButton(); greenC.setIcon(new ImageIcon(this.getClass().getResource("greenIcon.jpg")));
		sideMenu.add(recSel);	sideMenu.add(elipSel); sideMenu.add(eye); sideMenu.add(paint); sideMenu.add(eraser); sideMenu.add(new JSeparator(JSeparator.VERTICAL)); 
		sideMenu.add(new JSeparator(JSeparator.HORIZONTAL)); 
		sideMenu.add(redC); sideMenu.add(orangeC); sideMenu.add(yellowC);  sideMenu.add(greenC); sideMenu.add(blueC); sideMenu.add(whiteC); sideMenu.add(blackC); 

		eye.addActionListener(this); redC.addActionListener(this);  recSel.addActionListener(this); 
		blackC.addActionListener(this); whiteC.addActionListener(this); elipSel.addActionListener(this); 
		blueC.addActionListener(this); paint.addActionListener(this); yellowC.addActionListener(this);
		eraser.addActionListener(this); orangeC.addActionListener(this); greenC.addActionListener(this);
	}
	public void initialize(){ //calls methods to create menu objects then adds them to jframe
		makeTopMenu();
		makesideMenuu(); 
		menuContent.add(sideMenu);
		window.add(menuBar, BorderLayout.NORTH);
		window.add(menuContent, BorderLayout.WEST);
		window.setAlwaysOnTop(true);
		window.setVisible(true);
		getIntention(); //ask if user wants a new file, to open a file or exit program
	}
	public appWindow(){ //constructor initializes menu objects, sets layouts and sets defaults for jframe
		menuBar=new JMenuBar();
		menuContent = new JPanel();
		sideMenu = new JPanel();
		sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
		menuContent.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		menuContent.setBackground(Color.darkGray);
		menuContent.setBorder(BorderFactory.createEmptyBorder(0,5,5,5)); 
		window = new JFrame("Image Manipulator"); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setIconImage(new ImageIcon("silly.png").getImage());
		window.getContentPane().setBackground(Color.darkGray);
		window.setSize(700, 800);
		window.setLocationRelativeTo(null);
	}
	public void getIntention(){ //gets instruction from user 
		String[] options = {"New File", "Existing File", "Exit to Desktop"};
		try{
			Thread.sleep(500); }
		catch(Exception e){
			System.out.print(e); }
		
		int action=JOptionPane.showOptionDialog(null, "Choose One", "File Select", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

		if(action==0) manager.newFile(); 
		else if (action==1) manager.loadFile(); 
		else{ JOptionPane.showMessageDialog(null, "         Good-Bye!");
			  System.exit(1); }
	}
	public void closeEllipSelection(){ //closes elliptical selection
			refreshPane(); 
			selE=null;
			picture.revalidate(); picture.repaint(); }
	public void closeRecSelection(){ //closes rectangular selection 
			refreshPane(); sel=null;
			picture.revalidate(); picture.repaint(); 	}
	public void finishEraser(){ //finalizes eraser changes
			picture.setImage(eras.img); refreshPane();
			window.getContentPane().revalidate(); window.getContentPane().repaint(); }
	public void finishPaint(){ //finalizes paint changes
			picture.setImage(p.img); refreshPane(); 
			window.getContentPane().revalidate(); window.getContentPane().repaint(); }
	public void writePaint(){ //finalizes changes then reopens paint to continue
			picture.setImage(p.img); refreshPane();
			window.getContentPane().revalidate(); window.getContentPane().repaint(); 
			p = new paint(); p.setColor(picture.getDesiredColor());
			window.remove(picture.scrollFrame);
			picture.scrollFrame=new JScrollPane(p); 
			window.add(picture.scrollFrame);  
			window.getContentPane().revalidate(); window.getContentPane().repaint(); 
	}
	public void refreshPane(){ //refreshes content containers
		window.remove(picture.scrollFrame); picture.scrollFrame=new JScrollPane(picture); 
		picture.scrollFrame.setBorder(BorderFactory.createEmptyBorder());
		window.add(picture.scrollFrame); window.revalidate(); window.repaint();
	}
	public void actionPerformed(ActionEvent e){ //catches all actions called by menu items
		if(e.getActionCommand()=="New File"){
			if(picture.imgAltered) manager.saveRequest(); if(manager.isOpen) manager.closeFile(); manager.newFile();
		}
		else if(e.getActionCommand()=="Open File"){ //open file routine
			if(ellipSelStatus){
				closeEllipSelection(); ellipSelStatus ^=true; }
			if(recSelStatus){
				closeRecSelection(); recSelStatus ^=true; }
			if(eraserStatus){
				finishEraser(); eraserStatus  ^=true;
			}
			if(paintStatus){
				finishPaint(); paintStatus ^= true; }
			if(eyeDropStatus){
				picture.removeEyeDrop(); eyeDropStatus^=true; }
			if(picture.imgAltered) manager.saveRequest();
			if(manager.isOpen)
				manager.closeFile();
			manager.loadFile();
		}
		else if(e.getActionCommand()=="Save"){ //save routine
			if(manager.isOpen) manager.saveFile(); else JOptionPane.showMessageDialog(null, "A file must be open to be saved", "Error" , JOptionPane.WARNING_MESSAGE);  }
		else if(e.getActionCommand()=="Save As..."){  //save as routine
			if(manager.isOpen) manager.saveFileAs(); else JOptionPane.showMessageDialog(null, "A file must be open to be saved", "Error" , JOptionPane.WARNING_MESSAGE);}
		else if(e.getActionCommand()=="Close File"){ //close file routine
			if(ellipSelStatus==true){
				closeEllipSelection(); ellipSelStatus^=true; }
			if(recSelStatus==true){
				closeRecSelection(); ellipSelStatus^=true; }
			if(eraserStatus){
				finishEraser(); eraserStatus ^= true; }
			if(paintStatus){
				finishPaint(); paintStatus ^= true; }
			if(eyeDropStatus){
				picture.removeEyeDrop(); eyeDropStatus^=true; }
			if(picture.imgAltered) manager.saveRequest(); 
			if(manager.isOpen) manager.closeFile(); 
		}
		else if(e.getSource() ==  recSel){ //open rectangular marquee routine
			if(ellipSelStatus){
				ellipSelStatus^=true; closeEllipSelection(); }
			if(eyeDropStatus){
				picture.removeEyeDrop(); eyeDropStatus^=true; }
			if(paintStatus){
				finishPaint(); paintStatus ^= true; }
			if(eraserStatus){
				finishEraser(); eraserStatus ^= true; }
			recSelStatus ^= true;
			if(recSelStatus){
				sel = new rectangularSelect(); window.remove(picture.scrollFrame); picture.scrollFrame=new JScrollPane(sel); window.add(picture.scrollFrame);//window.remove(picture); window.add(sel);  
				window.getContentPane().validate(); window.getContentPane().repaint();  }
			else
				closeRecSelection();
		}
		else if(e.getActionCommand()=="Crop Image..."){ //image crop routine
				if(sel==null){
					JOptionPane.showMessageDialog(null, "Must have a rectangular selection made before cropping", "Instruction" , JOptionPane.WARNING_MESSAGE); }
				else{
					picture.setImage(picture.crop(sel.getSelectionBounds()));
					refreshPane();
					picture.revalidate(); picture.repaint(); picture.imgAltered=true; }
		}
		else if(e.getActionCommand() == "Rectangular Shape Create"){ //rectangular shape create routine
				if(sel==null){
					JOptionPane.showMessageDialog(null, "Must have a rectangular selection made before cropping", "Warning" , JOptionPane.WARNING_MESSAGE); }
				else{
					if(ellipSelStatus==true){
						closeEllipSelection(); ellipSelStatus^=true; }
					if(eraserStatus){
						finishEraser(); eraserStatus ^= true; }
					if(paintStatus){
						finishPaint(); paintStatus ^= true; }
					if(eyeDropStatus){
						picture.removeEyeDrop(); eyeDropStatus^=true; }

					window.remove(sel); window.add(picture); picture.setImage(picture.makeRectangle(sel.getSelectionBounds()));
					picture.revalidate(); picture.repaint();
					recSelStatus^=true; closeRecSelection(); picture.imgAltered=true; }
			}
		else if(e.getSource() == elipSel){ //elliptical selection marquee routine
			if(recSelStatus){
				recSelStatus^=true; closeRecSelection(); }
			if(paintStatus){
				finishPaint(); paintStatus ^= true;	}
			if(eraserStatus){
				finishEraser(); eraserStatus ^= true; }
			ellipSelStatus ^= true;
			if(ellipSelStatus){
				selE = new ellipticalSelect();
				window.remove(picture.scrollFrame); picture.scrollFrame=new JScrollPane(selE); window.add(picture.scrollFrame);
				window.getContentPane().validate();
				window.getContentPane().repaint();  }
			else
				closeEllipSelection();
		}
		else if(e.getActionCommand() == "Elliptical Shape Create"){ //elliptical shape creation routine
			if(selE==null){
					JOptionPane.showMessageDialog(null, "Must have an elliptical selection made before cropping", "Warning" , JOptionPane.WARNING_MESSAGE); }
			else{
					if(recSelStatus==true){
						closeRecSelection(); ellipSelStatus^=true; }
					if(eraserStatus){
						finishEraser(); eraserStatus ^= true; }
					if(paintStatus){
						finishPaint(); paintStatus ^= true; }
					if(eyeDropStatus){
						picture.removeEyeDrop(); eyeDropStatus^=true; }

				picture.scrollFrame.remove(selE);  picture.setImage(picture.makeElliptical(selE.getSelectionBounds())); closeEllipSelection(); refreshPane();
				selE=null;	ellipSelStatus ^= true;
				picture.revalidate(); picture.repaint(); closeEllipSelection(); picture.imgAltered=true;}	

		}
		else if (e.getSource() == paint){ //paint routine
			if(eraserStatus){ eraserStatus ^= true; finishEraser(); }
			if(eyeDropStatus){ picture.removeEyeDrop(); eyeDropStatus^=true;}
			if(recSelStatus){ closeRecSelection(); recSelStatus^=true; }
			if(ellipSelStatus){ closeRecSelection(); ellipSelStatus^=true; }
			paintStatus ^= true;
			if(paintStatus){
				p = new paint(); p.setSize(picture.getBrushSize()); p.setColor(picture.getDesiredColor()); window.remove(picture.scrollFrame);
				picture.scrollFrame=new JScrollPane(p); 
				window.add(picture.scrollFrame); 
				window.getContentPane().revalidate(); window.getContentPane().repaint(); picture.imgAltered=true; }
			else
				finishPaint();
		}
		else if(e.getSource() == eraser){ //eraser routine
			if(paintStatus){ paintStatus^=true; finishPaint(); }
			if(eyeDropStatus){ picture.removeEyeDrop(); eyeDropStatus^=true; }
			if(recSelStatus){ recSelStatus^=true; closeRecSelection(); } 
			if(ellipSelStatus){ ellipSelStatus^=true; closeRecSelection(); }
			eraserStatus ^= true;
			if(eraserStatus){
				eras = new paint(); eras.setSize(picture.getBrushSize()); eras.setColor(Color.WHITE); 
				window.remove(picture.scrollFrame); picture.scrollFrame=new JScrollPane(eras); window.add(picture.scrollFrame);
				window.getContentPane().revalidate(); window.getContentPane().repaint(); picture.imgAltered=true; }
			else
				finishEraser(); 
		}
		else if(e.getSource() == eye){ //eyedropper routine
				if(ellipSelStatus){ closeEllipSelection(); ellipSelStatus ^=true; }
				if(recSelStatus){ closeRecSelection(); recSelStatus ^=true; }
				if(eraserStatus){ finishEraser(); eraserStatus  ^=true; }
				if(paintStatus){ finishPaint(); paintStatus ^= true; }
				eyeDropStatus^=true;
				if(eyeDropStatus)
					picture.getEyeDropPoint(); 	
		} //default color setters for paint and shape create
		else if(e.getSource()==redC){
			
			picture.setColor(Color.RED); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getSource() == yellowC){
			
			picture.setColor(Color.YELLOW); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getSource() == blueC){
			
			picture.setColor(Color.BLUE); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getSource() == greenC){
			
			picture.setColor(Color.GREEN); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getSource() == blackC){
			
			picture.setColor(Color.BLACK); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getSource() == whiteC){
			
			picture.setColor(Color.WHITE); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getSource() == orangeC){
			picture.setColor(Color.ORANGE); 
			if(paintStatus){ writePaint(); }
		}
		else if(e.getActionCommand() == "Rotate 90º Counter-Clockwise"){
			for(int i = 0; i <3; i++){
				picture.rotateImage();
			}
				picture.revalidate();
				picture.repaint(); picture.imgAltered=true;
		}
		else if(e.getActionCommand() == "Rotate 90º Clockwise"){
				picture.rotateImage();
				picture.revalidate();
				picture.repaint(); picture.imgAltered=true;
		}
		else if(e.getActionCommand() == "Paint Options..."){
			JPanel optionsdialog = new JPanel();
			JTextField sz = new JTextField(3);
			optionsdialog.add(new JLabel("Pixel diameter of brush"));
			optionsdialog.add(sz);
			JTextField color1 = new JTextField(3);
			JTextField color2 = new JTextField(3);
			JTextField color3 = new JTextField(3);
			optionsdialog.add(new JLabel("Set Color: ")); 
			optionsdialog.add(new JLabel("R")); optionsdialog.add(color1); 
			optionsdialog.add(new JLabel("G")); optionsdialog.add(color2); 
			optionsdialog.add(new JLabel("B")); optionsdialog.add(color3);
			JOptionPane.showConfirmDialog(null, optionsdialog ,"Paint Brush & Eraser Options", JOptionPane.OK_CANCEL_OPTION);
			try{
				picture.setBrushSize(Integer.parseInt(sz.getText()));
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Error: Brushsize must be an integer.", "Error" , JOptionPane.WARNING_MESSAGE);
			}
			if(!(color1.getText().equals("")) || !(color2.getText().equals("")) || !(color3.getText().equals(""))){
				try{
					picture.setColor(new Color(Integer.parseInt(color1.getText()), Integer.parseInt(color2.getText()), Integer.parseInt(color3.getText())));
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Error: RGB configurations must be an integer.", "Error" , JOptionPane.WARNING_MESSAGE);
				}
			}

			if(paintStatus) {
				finishPaint(); paintStatus^=true;
			}
		}
		else if(e.getActionCommand() == "Image Blur"){
			if(ellipSelStatus){ closeEllipSelection(); ellipSelStatus ^=true; }
			if(recSelStatus){ closeRecSelection(); recSelStatus ^=true; }
			if(eraserStatus){ finishEraser(); eraserStatus  ^=true; }
			if(paintStatus){ finishPaint(); paintStatus ^= true; }
			picture.blurImage(); picture.revalidate(); picture.repaint(); picture.imgAltered=true;
		}
		else if(e.getActionCommand() == "Selective Image Blur"){
			if(ellipSelStatus){ closeEllipSelection(); ellipSelStatus ^=true; }
			
			if(eraserStatus){ finishEraser(); eraserStatus  ^=true; }
			if(paintStatus){ finishPaint(); paintStatus ^= true; }
			if(sel==null){
					JOptionPane.showMessageDialog(null, "Must have a rectangular selection made before selectivly blurring", "Warning" , JOptionPane.WARNING_MESSAGE); }
			else{ picture.blurImage(sel.getSelectionBounds()); picture.revalidate(); picture.repaint(); picture.imgAltered=true;}
			if(recSelStatus){ closeRecSelection(); recSelStatus ^=true; }
		}
		else if(e.getActionCommand() == "Sharpen"){
			if(ellipSelStatus){ closeEllipSelection(); ellipSelStatus ^=true; }
			if(recSelStatus){ closeRecSelection(); recSelStatus ^=true; }
			if(eraserStatus){ finishEraser(); eraserStatus  ^=true; }
			if(paintStatus){ finishPaint(); paintStatus ^= true; }
			if(eyeDropStatus){ picture.removeEyeDrop(); eyeDropStatus^=true; }
			BufferedImage bi = new BufferedImage(picture.img.getWidth(null),picture.img.getHeight(null),BufferedImage.TYPE_INT_RGB);
			picture.sharpen(picture.img, bi); picture.revalidate(); picture.repaint(); picture.imgAltered=true;
		}
	}
}