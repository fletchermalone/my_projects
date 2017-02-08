import java.awt.event.*;
import java.awt.geom.Area;
import javax.swing.SwingUtilities;
import java.awt.*;
public class rectangularSelect extends imageImplement{ //create rectangular marquee selection object, is a translucent layer over image

        private Rectangle selectionBounds;
        private Point clickPoint;
        public boolean selected=false;
        public rectangularSelect() {
            setOpaque(false);
            MouseAdapter mouseHandler = new MouseAdapter() { //makes mouse adaptor to get point of click then calculates selectionBounds 
                @Override                                    //by finding difference of original and end locations of mouseclicks
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        selectionBounds=null;
                        clickPoint=null;
                        repaint(); }
                }
                @Override
                public void mousePressed(MouseEvent e) {
                	 if (SwingUtilities.isLeftMouseButton(e)) {
                	 	clickPoint = e.getPoint();
                    	selectionBounds = null; }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                	if (SwingUtilities.isLeftMouseButton(e)) clickPoint = null;
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                	if (SwingUtilities.isLeftMouseButton(e)) {
                		Point dragPoint = e.getPoint();
                    	int x = Math.min(clickPoint.x, dragPoint.x); int y = Math.min(clickPoint.y, dragPoint.y);
                   	 	int width = Math.max(clickPoint.x - dragPoint.x, dragPoint.x - clickPoint.x);
                    	int height = Math.max(clickPoint.y - dragPoint.y, dragPoint.y - clickPoint.y);
                    	selectionBounds = new Rectangle(x, y, width, height);
                    	repaint(); }    
                }
            };
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }
        public Rectangle getSelectionBounds(){ return selectionBounds; }
        @Override
        protected void paintComponent(Graphics g) { //creates translucent area and shows marquee if not null
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(255, 255, 255, 128)); 
            Area fill = new Area(new Rectangle(new Point(0, 0), getSize()));
            if (selectionBounds != null) { fill.subtract(new Area(selectionBounds)); } //creates a clear window through to image
            g2d.fill(fill);
            if (selectionBounds != null) {
                g2d.setColor(Color.BLACK);
                g2d.draw(selectionBounds); } //draw selectionBounds area outline
            g2d.dispose();
        }    
}

