import java.awt.*;

public class RectangleMark extends Mark {
    private Color fillColor = Color.LIGHT_GRAY;
    private Color borderColor = Color.BLACK;
    
    public RectangleMark(int x, int y) {
        super(x, y);
        this.width = 120;
        this.height = 80;
    }
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Fill rectangle
        g.setColor(fillColor);
        g.fillRect(x, y, width, height);
        
        // Draw border
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(2));
        } else {
            g.setColor(borderColor);
            g.setStroke(new BasicStroke(1));
        }
        g.drawRect(x, y, width, height);
        
        // Draw resize handle if selected
        if (isSelected) {
            g.setColor(Color.RED);
            g.fillRect(x + width - HANDLE_SIZE, y + height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE);
        }
    }
    
    public void setFillColor(Color color) {
        this.fillColor = color;
    }
    
    public void setBorderColor(Color color) {
        this.borderColor = color;
    }
}
