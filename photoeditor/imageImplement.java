import javax.swing.ImageIcon; 
import java.util.regex.*;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class imageImplement extends JPanel{ //contains and alters image in program
	public static boolean imgAltered=false;
	private static String type; private static String name; private static String fullName;
	protected static BufferedImage img; int x, y; protected static JFrame frame; public JScrollPane scrollFrame;
	protected static Color desiredColor; protected static int brushSize = 12; private double zoom;
	private Point p; 
	private MouseAdapter mouse;
	public imageImplement(){
		super();
	}
	public imageImplement(Image i, JFrame t){	
		imgAltered=false;
		desiredColor=Color.BLACK;
		frame = t; setBackground(Color.darkGray);
		this.img = convertToBufferedImage(i);
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size); setMinimumSize(size); setMaximumSize(size);
		setSize(size);
		repaint();

		 this.addMouseWheelListener(new MouseWheelListener() { //implements mousewheel for zoom in/out function
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                resizeImage(notches);
            }
        });
	}
	public void setColor(Color c){ desiredColor=c; makeColorIcon(c); repaint(); } //getters/setters
	public void setBrushSize(int s){ brushSize=s; }
	public int getBrushSize(){ return brushSize;}
	public Color getDesiredColor(){ return this.desiredColor; }
	public void setFullName(String s){ fullName = s; }
	public String getFullName(){return fullName;}
	public void setName(String n){ name = n;}
	public void setType(String t){ type = t; }
	public String getName() { return name;}
	public String getType() { return type;}
	public BufferedImage getImage(){ return img;}
	public Color getPixelColorAt(int a, int b){ return new Color(img.getRGB(a-x, b-y)); }
	public Point getP(){ return p; }
	public void makeColorIcon(Color c){ //creates color icon for menus
		BufferedImage file = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) file.getGraphics();
		g.setColor(c); g.fillRect(0, 0, 30, 30);
		try{ ImageIO.write(file, "jpg", new File("currentColorIcon.jpg")); }
		catch(IOException e){ e.printStackTrace(); }
	}
	@Override
	protected void paintComponent(Graphics g){ //overrides paintComponent, implemented such that it centers image on window resize
		super.paintComponent(g);
		x = (this.getWidth() - img.getWidth(null)) / 2; y = (this.getHeight() - img.getHeight(null)) / 2;
		g.drawImage(img, x, y, null);
	}
	public static BufferedImage convertToBufferedImage(Image im){ //takes an image and returns it as bufferedImage
		BufferedImage bi = new BufferedImage(im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
     	Graphics bg = bi.getGraphics();
     	bg.drawImage(im, 0, 0, null); bg.dispose();
     	return bi;
	}
	public String makeFileName(String s1, String s2){ //concatonates filename and filetype to create a full-string identification of file
		StringBuffer n = new StringBuffer();
		if(Pattern.compile(Pattern.quote("jpeg"), Pattern.CASE_INSENSITIVE).matcher(s2).find()){
			n.append(s1).append(".jpg");
			setName(s1); setType("jpg"); setFullName(n.toString()); }
		else if(Pattern.compile(Pattern.quote("png"), Pattern.CASE_INSENSITIVE).matcher(s2).find()){
			n.append(s1).append(".png");
			setName(s1); setType("png"); setFullName(n.toString()); }
		return n.toString();
	}
	public BufferedImage crop(Rectangle sz){ //crops by returning subimage whose selection bounds are indicated by attributes of a rectangle
		if(sz.width <= img.getWidth() && sz.height <= img.getHeight())
			return img.getSubimage(sz.x-x, sz.y-y, sz.width, sz.height);
		else{
			JOptionPane.showMessageDialog(null, "Bounds of crop cannot exceed bounds of image", "Error" , JOptionPane.WARNING_MESSAGE);
			return img;
		}
	}
	public void setImage(BufferedImage i){ //sets image to be shown in application
		img = i;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size); setMinimumSize(size); setMaximumSize(size); setSize(size); setLayout(null);
		repaint();
	}
	public BufferedImage makeRectangle(Rectangle sz){ //rectangular shape superimposed on application img
			Graphics g = img.createGraphics();
			g.drawImage(img, 0, 0, null); g.setColor(this.getDesiredColor()); g.fillRect(sz.x-x,sz.y-y , sz.width, sz.height);
			return img;
	}
	public BufferedImage makeElliptical(Ellipse2D.Double e){ //elliptical shape superimposed on application img
		Graphics2D g = img.createGraphics();
		g.drawImage(img, 0, 0, null); g.setColor(this.getDesiredColor()); g.fill(new Ellipse2D.Double(e.x-x, e.y-y, e.getWidth(), e.getHeight()));
		return img;
	}
	@Override
    public Dimension getPreferredSize() { //overrides preferredSize to make slightly larger window for formatting purposes
        return new Dimension(img.getWidth()+10, img.getHeight()+10);
    }
    public void getEyeDropPoint(){ //creates mouseAdapter to gather point where mouse was clicked then gets pixel color at the point, used for paint/shape create colors
    	mouse = new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					p=e.getPoint(); 
					Color cTemp = getPixelColorAt(p.x, p.y);
					setColor(cTemp);
					makeColorIcon(cTemp);
					removeEyeDrop();
				}
			};
			addMouseListener(mouse); 
    }
    public void removeEyeDrop(){removeMouseListener(mouse); } //removes mouseAdapter once eyedrop-point has been selected
    public void rotateImage(){ //uses affineTransform to rotate image
		Graphics2D g = img.createGraphics();
		AffineTransform transform = new AffineTransform();
    	transform.rotate(Math.toRadians(90), img.getWidth()/2, img.getHeight()/2);
    	AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    	setImage(op.filter(img, null));
		repaint();
    }
    public void resizeImage(int way){ //called on mouseWheel movement, dependent on direction moved (indicated by +1 or -1) then calls for appropriate zoom function
    	if(way < 0) decreaseSize(img);
    	else if(way > 0) increaseSize(img);
    }
    public void decreaseSize(BufferedImage img) {  //rescales image to be smaller by -.33 of previous size
    	if(img.getWidth() > 20 && img.getHeight() > 20){
    		int newW=10 * img.getWidth() / 15; int newH=10 * img.getHeight() / 15;
    		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    		Graphics2D g2d = dimg.createGraphics();
    		g2d.drawImage(tmp, 0, 0, null);
    		g2d.dispose();
    		setImage(dimg); repaint();
    	}
   }
   public void increaseSize(BufferedImage img) { //rescales image to be larger by +.33 of previous size
   		if(img.getWidth() < 4000 && img.getHeight() < 4000){
   			int newW=12 * img.getWidth() / 9; int newH=12 * img.getHeight() / 9;
    		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    		Graphics2D g2d = dimg.createGraphics();
    		g2d.drawImage(tmp, 0, 0, null);
    		g2d.dispose();
    		setImage(dimg); repaint();
   		}
	}
	public void blurImage(){ //blurs image by convolution of a matrix of 3 pixels by 3 pixels (equally weighted)
		float[] blurKernal= {
        	0.111f, 0.111f, 0.111f, 
       	 	0.111f, 0.111f, 0.111f, 
        	0.111f, 0.111f, 0.111f };
   	 	BufferedImage blurredImage = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
    	BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, blurKernal) );
		blurredImage = op.filter(img, blurredImage);
		setImage(blurredImage); repaint();
	}
	public void blurImage(Rectangle selectionBounds){ //selectively blurs a rectangular region of an image by convolution of a matrix of 3 pixels by 3 pixels (equally weighted)
			float[] blurKernal= {
        	0.111f, 0.111f, 0.111f, 
        	0.111f, 0.111f, 0.111f, 
       		0.111f, 0.111f, 0.111f };
			BufferedImage temp;
			temp=crop(selectionBounds);
			BufferedImage blurredImage = new BufferedImage(temp.getWidth(null),temp.getHeight(null),BufferedImage.TYPE_INT_RGB);
    		BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, blurKernal) );
			blurredImage = op.filter(temp, blurredImage);
			Graphics2D g2d=img.createGraphics();
			g2d.drawImage(blurredImage, (int)selectionBounds.getX()-x, (int)selectionBounds.getY()-y,null);

			repaint();

	}
	 public void sharpen(BufferedImage biSrc, BufferedImage biDest) { //uses unsharp masking, subtracts a blurred version of an image from the image itself
    		float sharpenKernal[] = { -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f, -1.0f };
   		 	Kernel kernel = new Kernel(3, 3, sharpenKernal);
    		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    		convolve.filter(biSrc, biDest);
    		setImage(biDest); repaint();
  }
}
