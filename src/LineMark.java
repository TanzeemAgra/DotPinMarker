import java.awt.*;

public class LineMark extends Mark {
    private int endX, endY;
    private Color lineColor = Color.BLACK;
    private int thickness = 2;
    
    public LineMark(int x, int y) {
        super(x, y);
        this.endX = x + 100;
        this.endY = y + 50;
        this.width = Math.abs(endX - x);
        this.height = Math.abs(endY - y);
    }
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Draw line
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(thickness + 1));
        } else {
            g.setColor(lineColor);
            g.setStroke(new BasicStroke(thickness));
        }
        
        g.drawLine(x, y, endX, endY);
        
        // Draw start and end points if selected
        if (isSelected) {
            g.setColor(Color.RED);
            g.fillOval(x - HANDLE_SIZE/2, y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
            g.fillOval(endX - HANDLE_SIZE/2, endY - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
        }
    }
    
    @Override
    public void resizeTo(int mx, int my) {
        if (resizing) {
            endX = mx;
            endY = my;
            width = Math.abs(endX - x);
            height = Math.abs(endY - y);
        }
    }
    
    @Override
    public boolean overResizeHandle(int px, int py) {
        // Check if over end point
        return Math.abs(px - endX) <= HANDLE_SIZE && Math.abs(py - endY) <= HANDLE_SIZE;
    }
    
    public void setLineColor(Color color) {
        this.lineColor = color;
    }
    
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
}
