import java.awt.*;
import java.awt.geom.Area;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Image; 
import java.awt.image.*;
import java.util.HashMap;

public class paint extends imageImplement{
	private Point points[] = new Point[1000];
	private int pointCount;
	private Point clickPoint;
	BufferedImage bi;
	private Color color;
	private int size;
	private static HashMap <Point, Point> map;
	public paint(){
		color=Color.BLACK; size=12; 
		MouseAdapter mouseHandler = new MouseAdapter(){ //creates mouseAdapter to populate points array
			@Override
                public void mousePressed(MouseEvent e) {
                	 if (SwingUtilities.isLeftMouseButton(e)) {
                	 	clickPoint = e.getPoint();
                	 	points[pointCount] = e.getPoint();
                        pointCount++;
                	 	repaint(); }
                }
			@Override
			public void mouseDragged(MouseEvent e){
                        if(pointCount < points.length){
                            points[pointCount] = e.getPoint();
                            pointCount++;
                            repaint(); }
			}
		};
		addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
	}
	public void setSize(int s){ size=s; }
	public void setColor(Color c){ color=c; }
	@Override
	protected void paintComponent(Graphics g){ //once points has been populated with where user wishes to paint pointComponent then paints one oval per each point
			map = new HashMap<Point, Point>();
		  	super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            g2d.setColor(color);
            for(int i =0; i < pointCount; i++){
            	if(!map.containsKey(points[i])){  //cache in place to not allow duplitcate paint points, as they would be redundant 
           	 		g2d.fillOval(points[i].x-super.x,points[i].y-super.y,size,size);
           	 		map.put(points[i], points[i]); }
            } pointCount=0;
           	g2d.dispose();
	}
}