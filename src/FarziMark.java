import java.awt.*;
import java.awt.geom.*;

public class FarziMark extends Mark {
    // Soft-coded configuration constants
    private static final String DEFAULT_TEXT = "ABC123";
    private static final double DEFAULT_CHAR_HEIGHT = 40.0;
    private static final double DEFAULT_CHAR_WIDTH = 30.0;
    private static final double DEFAULT_CHAR_SPACING = 5.0;
    private static final double DEFAULT_STROKE_WIDTH = 1.5;
    private static final double DEFAULT_SCRIPT_SLANT = 0.2; // Italic slant factor
    private static final boolean DEFAULT_SHOW_GRID = false;
    
    // Instance properties
    private String text = DEFAULT_TEXT;
    private double charHeight = DEFAULT_CHAR_HEIGHT;
    private double charWidth = DEFAULT_CHAR_WIDTH;
    private double charSpacing = DEFAULT_CHAR_SPACING;
    private double strokeWidth = DEFAULT_STROKE_WIDTH;
    private double scriptSlant = DEFAULT_SCRIPT_SLANT;
    private boolean showGrid = DEFAULT_SHOW_GRID;
    
    public FarziMark(int x, int y) {
        super(x, y);
        calculateDimensions();
    }
    
    public FarziMark(int x, int y, String text) {
        super(x, y);
        this.text = (text != null && !text.trim().isEmpty()) ? text : DEFAULT_TEXT;
        calculateDimensions();
    }
    
    private void calculateDimensions() {
        int textLength = (text != null) ? text.length() : DEFAULT_TEXT.length();
        width = (int) ((charWidth + charSpacing) * textLength + charSpacing);
        height = (int) (charHeight + 20); // Extra padding for script descenders
    }
    
    public void draw(Graphics2D g2d, boolean isSelected) {
        // Enable antialiasing for smooth engraved script appearance
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // Set engraved script color (no background, dark foreground)
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke((float)strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        drawEngravedScriptText(g2d);
        
        if (isSelected) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f}, 0.0f));
            g2d.drawRect(x - 2, y - 2, width + 4, height + 4);
        }
        
        if (showGrid) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(0.5f));
            drawGrid(g2d);
        }
    }
    
    private void drawEngravedScriptText(Graphics2D g2d) {
        double currentX = x + charSpacing;
        double baseY = y + charHeight * 0.8; // Baseline for script characters
        
        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                currentX += charWidth * 0.6;
                continue;
            }
            drawEngravedScriptChar(g2d, c, currentX, baseY);
            currentX += charWidth + charSpacing;
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        if (!showGrid) return;
        
        double startX = x + charSpacing;
        double startY = y + 10;
        
        for (int i = 0; i < text.length(); i++) {
            double charX = startX + i * (charWidth + charSpacing);
            // Character boundary box
            g2d.drawRect((int)charX, (int)startY, (int)charWidth, (int)charHeight);
            // Baseline
            g2d.drawLine((int)charX, (int)(startY + charHeight * 0.8), 
                        (int)(charX + charWidth), (int)(startY + charHeight * 0.8));
        }
    }
    
    private void drawEngravedScriptChar(Graphics2D g2d, char ch, double px, double py) {
        // Apply script slant transformation
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform scriptTransform = new AffineTransform();
        scriptTransform.shear(scriptSlant, 0); // Italic slant
        scriptTransform.translate(px, py);
        g2d.setTransform(scriptTransform);
        
        // Draw character in engraved script style
        drawScriptCharacter(g2d, Character.toUpperCase(ch));
        
        // Restore original transform
        g2d.setTransform(oldTransform);
    }
    
    private void drawScriptCharacter(Graphics2D g2d, char ch) {
        // Normalize coordinates for character drawing (0,0 is character origin)
        double w = charWidth * 0.8;
        double h = charHeight * 0.7;
        
        switch (ch) {
            case 'A':
                drawScriptA(g2d, w, h);
                break;
            case 'B':
                drawScriptB(g2d, w, h);
                break;
            case 'C':
                drawScriptC(g2d, w, h);
                break;
            case '1':
                drawScript1(g2d, w, h);
                break;
            case '2':
                drawScript2(g2d, w, h);
                break;
            case '3':
                drawScript3(g2d, w, h);
                break;
            default:
                drawScriptDefault(g2d, w, h);
                break;
        }
    }
    
    // Engraved Script Character Drawing Methods
    private void drawScriptA(Graphics2D g2d, double w, double h) {
        // Script A with flowing curves and serifs
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.1, 0);
        path.quadTo(w * 0.3, -h * 0.3, w * 0.5, -h * 0.8); // Left stroke with curve
        path.quadTo(w * 0.7, -h * 0.3, w * 0.9, 0); // Right stroke
        // Crossbar with script flourish
        path.moveTo(w * 0.25, -h * 0.4);
        path.quadTo(w * 0.5, -h * 0.35, w * 0.75, -h * 0.4);
        g2d.draw(path);
    }
    
    private void drawScriptB(Graphics2D g2d, double w, double h) {
        // Script B with rounded humps and connecting strokes
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.1, 0);
        path.lineTo(w * 0.1, -h * 0.8); // Vertical stroke
        // Upper hump
        path.moveTo(w * 0.1, -h * 0.8);
        path.quadTo(w * 0.6, -h * 0.9, w * 0.6, -h * 0.6);
        path.quadTo(w * 0.6, -h * 0.4, w * 0.1, -h * 0.4);
        // Lower hump
        path.moveTo(w * 0.1, -h * 0.4);
        path.quadTo(w * 0.7, -h * 0.5, w * 0.7, -h * 0.2);
        path.quadTo(w * 0.7, 0.05, w * 0.1, 0);
        g2d.draw(path);
    }
    
    private void drawScriptC(Graphics2D g2d, double w, double h) {
        // Script C with elegant curve and opening
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.8, -h * 0.7);
        path.quadTo(w * 0.2, -h * 1.0, w * 0.1, -h * 0.4);
        path.quadTo(w * 0.2, 0.1, w * 0.8, -h * 0.1);
        // Small serif at top
        path.moveTo(w * 0.7, -h * 0.8);
        path.lineTo(w * 0.9, -h * 0.6);
        g2d.draw(path);
    }
    
    private void drawScript1(Graphics2D g2d, double w, double h) {
        // Script 1 with elegant serif and flowing stroke
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.3, -h * 0.6);
        path.lineTo(w * 0.5, -h * 0.8);
        path.lineTo(w * 0.5, 0);
        // Base serif
        path.moveTo(w * 0.3, 0);
        path.lineTo(w * 0.7, 0);
        // Top flourish
        path.moveTo(w * 0.2, -h * 0.7);
        path.quadTo(w * 0.4, -h * 0.9, w * 0.6, -h * 0.7);
        g2d.draw(path);
    }
    
    private void drawScript2(Graphics2D g2d, double w, double h) {
        // Script 2 with curved top and flowing baseline
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.2, -h * 0.6);
        path.quadTo(w * 0.5, -h * 1.0, w * 0.8, -h * 0.7);
        path.quadTo(w * 0.9, -h * 0.5, w * 0.2, 0);
        path.lineTo(w * 0.8, 0);
        // Decorative flourish
        path.moveTo(w * 0.1, -h * 0.1);
        path.quadTo(w * 0.15, -h * 0.2, w * 0.25, -h * 0.05);
        g2d.draw(path);
    }
    
    private void drawScript3(Graphics2D g2d, double w, double h) {
        // Script 3 with double curves and elegant connection
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.2, -h * 0.7);
        path.quadTo(w * 0.7, -h * 0.9, w * 0.7, -h * 0.6);
        path.quadTo(w * 0.5, -h * 0.4, w * 0.7, -h * 0.4);
        path.quadTo(w * 0.9, -h * 0.2, w * 0.7, 0);
        path.quadTo(w * 0.3, 0.1, w * 0.2, -h * 0.2);
        // Connection stroke
        path.moveTo(w * 0.5, -h * 0.4);
        path.lineTo(w * 0.6, -h * 0.5);
        g2d.draw(path);
    }
    
    private void drawScriptDefault(Graphics2D g2d, double w, double h) {
        // Default ornamental character for unsupported letters
        Path2D.Double path = new Path2D.Double();
        path.moveTo(w * 0.2, -h * 0.2);
        path.quadTo(w * 0.5, -h * 0.8, w * 0.8, -h * 0.2);
        path.moveTo(w * 0.3, -h * 0.1);
        path.lineTo(w * 0.7, -h * 0.1);
        // Decorative dots
        g2d.fillOval((int)(w * 0.2), (int)(-h * 0.6), 3, 3);
        g2d.fillOval((int)(w * 0.8), (int)(-h * 0.6), 3, 3);
        g2d.draw(path);
    }
    
    // Soft-coded property accessors
    public String getText() { return text; }
    public void setText(String text) { 
        this.text = (text != null && !text.trim().isEmpty()) ? text : DEFAULT_TEXT;
        calculateDimensions();
    }
    
    public double getCharHeight() { return charHeight; }
    public void setCharHeight(double h) { 
        this.charHeight = (h > 0) ? h : DEFAULT_CHAR_HEIGHT;
        calculateDimensions();
    }
    
    public double getCharWidth() { return charWidth; }
    public void setCharWidth(double w) { 
        this.charWidth = (w > 0) ? w : DEFAULT_CHAR_WIDTH;
        calculateDimensions();
    }
    
    public double getCharSpacing() { return charSpacing; }
    public void setCharSpacing(double s) { 
        this.charSpacing = (s >= 0) ? s : DEFAULT_CHAR_SPACING;
        calculateDimensions();
    }
    
    public double getStrokeWidth() { return strokeWidth; }
    public void setStrokeWidth(double width) { 
        this.strokeWidth = (width > 0) ? width : DEFAULT_STROKE_WIDTH;
    }
    
    public double getScriptSlant() { return scriptSlant; }
    public void setScriptSlant(double slant) { 
        this.scriptSlant = Math.max(-0.5, Math.min(0.5, slant)); // Limit slant range
    }
    
    public boolean isShowGrid() { return showGrid; }
    public void setShowGrid(boolean show) { this.showGrid = show; }
    
    // Legacy compatibility methods (for existing code that uses old names)
    public double getDotSize() { return strokeWidth; }
    public void setDotSize(double size) { setStrokeWidth(size); }
}
