import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class BowTextMark extends Mark {
    // Control handle types - simple and visual like your reference image
    public enum ControlHandle {
        NONE,
        LEFT_ANGLE,     // Left angle control (bidirectional arrow)
        RIGHT_ANGLE,    // Right angle control (bidirectional arrow) 
        WIDTH_LEFT,     // Width adjustment left
        WIDTH_RIGHT,    // Width adjustment right
        CURVE_TOP,      // Curvature control top
        CURVE_BOTTOM,   // Curvature control bottom
        MOVE            // Move handle (entire text)
    }
    
    public enum BowTextResizeHandle {
        LEFT, RIGHT, TOP, BOTTOM, 
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, 
        NONE
    }
    
    private String text = "ABCDEF";
    private double arcAngle = 120; // Arc span in degrees (60-180)
    private double arcRadius = 80; // Arc radius for curvature
    private double curvature = 0.5; // Curvature amount (0.1 to 1.0)
    private boolean bowUp = true; // true = bow up, false = bow down
    private Font font = new Font("Arial", Font.BOLD, 16);
    private Color textColor = Color.BLACK;
    
    // Control handle positions
    private Point2D.Double leftAngleHandle, rightAngleHandle;
    private Point2D.Double widthLeftHandle, widthRightHandle;
    private Point2D.Double curveTopHandle, curveBottomHandle;
    private ControlHandle activeHandle = ControlHandle.NONE;
    
    public BowTextMark(int x, int y) {
        super(x, y);
        this.width = 200;
        this.height = 120;
        calculateControlHandles();
    }
    
    public BowTextMark(int x, int y, String text) {
        super(x, y);
        this.text = text;
        this.width = Math.max(200, text.length() * 15);
        this.height = 120;
        calculateControlHandles();
    }
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Enable antialiasing for smooth curves
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw the curved text
        drawCurvedText(g);
        
        // Draw smart visual control handles when selected (like your reference image)
        if (isSelected) {
            drawBowTextResizeHandles(g);
            drawAlignedBoundingBox(g, isSelected);
        }
    }
    
    /**
     * Draw curved text - simple and effective implementation
     */
    private void drawCurvedText(Graphics2D g) {
        if (text == null || text.isEmpty()) return;
        
        g.setFont(font);
        g.setColor(textColor);
        
        // Calculate arc center and parameters
        double centerX = x + width / 2.0;
        double centerY = bowUp ? (y + height - 30) : (y + 30);
        
        // Draw each character along the arc
        char[] chars = text.toCharArray();
        FontMetrics fm = g.getFontMetrics(font);
        
        // Calculate starting angle and angle step
        double startAngle = Math.toRadians(-arcAngle / 2);
        double angleStep = chars.length > 1 ? Math.toRadians(arcAngle) / (chars.length - 1) : 0;
        
        for (int i = 0; i < chars.length; i++) {
            double angle = startAngle + i * angleStep;
            
            // Calculate character position
            double charX = centerX + arcRadius * Math.sin(angle);
            double charY = centerY - arcRadius * Math.cos(angle) * (bowUp ? 1 : -1);
            
            // Save transform and rotate character
            AffineTransform oldTransform = g.getTransform();
            g.translate(charX, charY);
            g.rotate(angle + (bowUp ? 0 : Math.PI));
            
            // Draw character centered
            String ch = String.valueOf(chars[i]);
            int charWidth = fm.charWidth(chars[i]);
            g.drawString(ch, -charWidth/2, fm.getAscent()/2);
            
            // Restore transform
            g.setTransform(oldTransform);
        }
    }
    
    /**
     * Calculate positions of control handles (like in your reference image)
     */
    private void calculateControlHandles() {
        double centerX = x + width / 2.0;
        double centerY = bowUp ? (y + height - 30) : (y + 30);
        
        // Angle control handles (left and right ends of arc)
        double leftAngle = Math.toRadians(-arcAngle / 2);
        double rightAngle = Math.toRadians(arcAngle / 2);
        
        leftAngleHandle = new Point2D.Double(
            centerX + arcRadius * Math.sin(leftAngle),
            centerY - arcRadius * Math.cos(leftAngle) * (bowUp ? 1 : -1)
        );
        
        rightAngleHandle = new Point2D.Double(
            centerX + arcRadius * Math.sin(rightAngle),
            centerY - arcRadius * Math.cos(rightAngle) * (bowUp ? 1 : -1)
        );
        
        // Width control handles (extend arc width)
        widthLeftHandle = new Point2D.Double(leftAngleHandle.x - 20, leftAngleHandle.y);
        widthRightHandle = new Point2D.Double(rightAngleHandle.x + 20, rightAngleHandle.y);
        
        // Curvature control handles (adjust arc radius)
        curveTopHandle = new Point2D.Double(centerX, centerY - arcRadius - 20);
        curveBottomHandle = new Point2D.Double(centerX, centerY + arcRadius + 20);
    }
    
    /**
     * Draw control handles - visual and user-friendly like your reference
     */
    private void drawControlHandles(Graphics2D g) {
        // Draw bounding rectangle
        g.setColor(new Color(0, 100, 200, 100));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 3}, 0));
        g.drawRect(x, y, width, height);
        
        // Draw control handles as small squares
        g.setColor(new Color(0, 100, 200));
        g.setStroke(new BasicStroke(2));
        
        int handleSize = 8;
        
        // Angle control handles (main adjustment points)
        drawHandle(g, leftAngleHandle, handleSize, Color.RED);
        drawHandle(g, rightAngleHandle, handleSize, Color.RED);
        
        // Curvature control handles
        drawHandle(g, curveTopHandle, handleSize, Color.BLUE);
        drawHandle(g, curveBottomHandle, handleSize, Color.BLUE);
        
        // Width control handles
        drawHandle(g, widthLeftHandle, handleSize, Color.GREEN);
        drawHandle(g, widthRightHandle, handleSize, Color.GREEN);
    }
    
    /**
     * Draw individual handle with specific color
     */
    private void drawHandle(Graphics2D g, Point2D.Double handle, int size, Color color) {
        if (handle == null) return;
        
        g.setColor(Color.WHITE);
        g.fillRect((int)handle.x - size/2, (int)handle.y - size/2, size, size);
        
        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        g.drawRect((int)handle.x - size/2, (int)handle.y - size/2, size, size);
    }
    
    /**
     * Draw bidirectional arrows (like in your reference image)
     */
    private void drawBidirectionalArrows(Graphics2D g) {
        g.setColor(new Color(0, 150, 0));
        g.setStroke(new BasicStroke(2));
        
        // Horizontal width adjustment arrows
        drawArrowLine(g, widthLeftHandle.x - 15, widthLeftHandle.y, widthLeftHandle.x + 5, widthLeftHandle.y);
        drawArrowLine(g, widthRightHandle.x - 5, widthRightHandle.y, widthRightHandle.x + 15, widthRightHandle.y);
        
        // Vertical curvature adjustment arrows
        drawArrowLine(g, curveTopHandle.x, curveTopHandle.y - 15, curveTopHandle.x, curveTopHandle.y + 5);
        drawArrowLine(g, curveBottomHandle.x, curveBottomHandle.y - 5, curveBottomHandle.x, curveBottomHandle.y + 15);
    }
    
    /**
     * Update bounding box to match actual text alignment
     */
    private void updateAlignedBoundingBox(AlignmentParameters params, double radius, double totalAngle) {
        // Calculate actual text bounds based on arc
        double arcWidth = 2 * radius * Math.sin(totalAngle / 2);
        double arcHeight = radius * (1 - Math.cos(totalAngle / 2));
        
        // Adjust bounding box for better visual alignment
        int textHeight = font.getSize(); // Use font size for height calculation
        
        if (bowUp) {
            // Text curves up - expand upward
            this.height = Math.max((int)(arcHeight + textHeight), textHeight * 2);
        } else {
            // Text curves down - expand downward  
            this.height = Math.max((int)(arcHeight + textHeight), textHeight * 2);
        }
        
        // Horizontal alignment - center the arc
        this.width = Math.max((int)(arcWidth * 1.2), text.length() * font.getSize());
    }
    
    /**
     * Helper method to get FontMetrics safely
     */
    private FontMetrics getFontMetrics(Graphics g, Font font) {
        if (g != null) {
            return g.getFontMetrics(font);
        }
        // Fallback if graphics is null
        return null;
    }
    
    /**
     * Alignment parameters structure for organized soft coding
     */
    private static class AlignmentParameters {
        double centerX;
        double centerY; 
        double radius;
        int bowDirection; // 1 for up, -1 for down
    }
    
    // Draw smart BowText control handles - visual and intuitive like your reference
    // Soft-coded visual handle rendering system that matches detection geometry
    private void drawBowTextResizeHandles(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        
        // Enhanced geometry calculations matching the detection logic
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;  // Use actual center
        int midY = y + height / 2;
        
        // Calculate curved text arc parameters with soft coding
        double effectiveRadius = Math.max(30, arcRadius * (0.5 + curvature));  // Dynamic radius
        double curvatureOffset = effectiveRadius * Math.sin(curvature * Math.PI / 2);
        
        // 1. CURVATURE CONTROL HANDLE (Blue Circle) - Most Important
        int curvatureX = (int)centerX;
        int curvatureY = (int)(centerY + (bowUp ? -curvatureOffset - 20 : curvatureOffset + 20));
        
        g.setColor(new Color(0, 120, 255));
        g.fillOval(curvatureX - 10, curvatureY - 10, 20, 20);
        g.setColor(Color.WHITE);
        g.fillOval(curvatureX - 6, curvatureY - 6, 12, 12);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("C", curvatureX - 3, curvatureY + 3);
        
        // Draw curvature guide line
        g.setColor(new Color(0, 120, 255, 100));
        g.drawLine((int)centerX, (int)centerY, curvatureX, curvatureY);
        
        // 2. BOW DIRECTION TOGGLE (Yellow Circle) - Enhanced positioning
        int toggleX = x + width - 20;  // Better positioning
        int toggleY = y + 15;
        
        g.setColor(bowUp ? new Color(255, 200, 0) : new Color(255, 100, 0));
        g.fillOval(toggleX - 8, toggleY - 8, 16, 16);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString(bowUp ? "↑" : "↓", toggleX - 3, toggleY + 3);
        
        // 3. WIDTH CONTROL HANDLES (Green Rectangles) - Enhanced left/right positioning
        int leftWidthX = x - 5;   // Extend outside bounds for easier access
        int rightWidthX = x + width + 5;
        
        g.setColor(new Color(0, 180, 0));
        // Left width handle - larger and more visible
        g.fillRect(leftWidthX - 6, midY - 6, 12, 12);
        g.setColor(Color.WHITE);
        g.drawString("L", leftWidthX - 3, midY + 3);
        
        // Right width handle - larger and more visible
        g.setColor(new Color(0, 180, 0));
        g.fillRect(rightWidthX - 6, midY - 6, 12, 12);
        g.setColor(Color.WHITE);
        g.drawString("R", rightWidthX - 3, midY + 3);
        
        // 4. ARC ANGLE CONTROL HANDLES (Purple Diamonds) - Improved arc positioning
        g.setColor(new Color(150, 0, 200));
        double arcSpread = Math.PI * 0.3;  // Soft-coded arc spread
        double leftArcAngle = -arcSpread / 2;
        double rightArcAngle = arcSpread / 2;
        
        int leftArcX = (int)(centerX + effectiveRadius * Math.sin(leftArcAngle) * 0.8);
        int leftArcY = (int)(centerY - effectiveRadius * Math.cos(leftArcAngle) * (bowUp ? 0.8 : -0.8));
        int rightArcX = (int)(centerX + effectiveRadius * Math.sin(rightArcAngle) * 0.8);
        int rightArcY = (int)(centerY - effectiveRadius * Math.cos(rightArcAngle) * (bowUp ? 0.8 : -0.8));
        
        // Draw diamond-shaped handles for arc endpoints
        drawDiamond(g, leftArcX, leftArcY, 6);
        drawDiamond(g, rightArcX, rightArcY, 6);
        
        // 5. Corner Fine-tuning Handles (Small Orange Squares) - Better corner positioning
        int topLeftX = x + 5;
        int topLeftY = y + 5;
        int bottomRightX = x + width - 5;
        int bottomRightY = y + height - 5;
        
        g.setColor(new Color(255, 150, 0));
        g.fillRect(topLeftX - 4, topLeftY - 4, 8, 8);
        g.fillRect(bottomRightX - 4, bottomRightY - 4, 8, 8);
        
        // 6. Draw arc path preview (Dotted line showing text path)
        g.setColor(new Color(100, 100, 100, 150));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{3, 3}, 0));
        drawArcPreview(g, centerX, centerY, effectiveRadius, leftArcAngle, rightArcAngle, bowUp ? 1 : -1);
        
        // 7. Visual debugging - show effective bounds
        g.setColor(new Color(255, 0, 0, 50));
        g.setStroke(new BasicStroke(1));
        g.drawRect(x, y, width, height);
    }
    
    private void drawDiamond(Graphics2D g, int x, int y, int size) {
        int[] xPoints = {x, x + size, x, x - size};
        int[] yPoints = {y - size, y, y + size, y};
        g.fillPolygon(xPoints, yPoints, 4);
    }
    
    private void drawArcPreview(Graphics2D g, double centerX, double centerY, double radius, double startAngle, double endAngle, int bowDirection) {
        int numPoints = 20;
        double angleStep = (endAngle - startAngle) / numPoints;
        
        for (int i = 0; i < numPoints; i++) {
            double angle1 = startAngle + i * angleStep;
            double angle2 = startAngle + (i + 1) * angleStep;
            
            int x1 = (int)(centerX + radius * Math.sin(angle1));
            int y1 = (int)(centerY - radius * Math.cos(angle1) * bowDirection);
            int x2 = (int)(centerX + radius * Math.sin(angle2));
            int y2 = (int)(centerY - radius * Math.cos(angle2) * bowDirection);
            
            g.drawLine(x1, y1, x2, y2);
        }
    }
    
    private void drawCurvatureControls(Graphics2D g) {
        // Draw curvature control point
        int controlX = x + width / 2;
        int controlY = bowUp ? y + 10 : y + height - 10;
        
        g.setColor(Color.BLUE);
        g.fillOval(controlX - 5, controlY - 5, 10, 10);
        
        // Draw direction toggle button
        int toggleX = x + width - 30;
        int toggleY = y + 10;
        g.setColor(bowUp ? Color.GREEN : Color.ORANGE);
        g.fillRect(toggleX, toggleY, 20, 15);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(bowUp ? "↑" : "↓", toggleX + 7, toggleY + 11);
    }
    
    /**
     * Draw properly aligned bounding box that follows text curvature
     */
    private void drawAlignedBoundingBox(Graphics2D g, boolean isSelected) {
        if (text == null || text.isEmpty()) {
            // Fallback to regular rectangle for empty text
            g.setColor(isSelected ? Color.GREEN : Color.LIGHT_GRAY);
            g.setStroke(new BasicStroke(isSelected ? 2 : 1));
            g.drawRect(x, y, width, height);
            return;
        }
        
        // Calculate alignment parameters
        FontMetrics fm = getFontMetrics(g, font);
        AlignmentParameters alignment = calculateSmartAlignment(fm);
        
        if (fm == null) {
            // Fallback if font metrics unavailable
            g.setColor(isSelected ? Color.GREEN : Color.LIGHT_GRAY);
            g.setStroke(new BasicStroke(isSelected ? 2 : 1));
            g.drawRect(x, y, width, height);
            return;
        }
        
        // Smart bounding box that follows text curve
        g.setColor(isSelected ? Color.GREEN : Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(isSelected ? 2 : 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        if (isSelected) {
            // Detailed bounding box for selected state
            drawDetailedTextBounds(g, alignment, fm);
        } else {
            // Simple dashed outline for unselected
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
            g.drawRect(x, y, width, height);
        }
    }
    
    /**
     * Draw detailed text bounds that follow the arc curvature
     */
    private void drawDetailedTextBounds(Graphics2D g, AlignmentParameters alignment, FontMetrics fm) {
        double centerX = alignment.centerX;
        double centerY = alignment.centerY;
        double radius = alignment.radius;
        int textHeight = fm.getHeight();
        
        // Calculate arc parameters
        double totalAngle = Math.PI * curvature;
        double startAngle = -totalAngle / 2;
        double endAngle = totalAngle / 2;
        
        // Draw outer arc (top of text)
        double outerRadius = radius + textHeight / 2;
        drawArcBounds(g, centerX, centerY, outerRadius, startAngle, endAngle, alignment.bowDirection);
        
        // Draw inner arc (bottom of text)  
        double innerRadius = Math.max(radius - textHeight / 2, textHeight / 4);
        drawArcBounds(g, centerX, centerY, innerRadius, startAngle, endAngle, alignment.bowDirection);
        
        // Connect arcs with side lines
        connectArcBounds(g, centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, alignment.bowDirection);
        
        // Draw regular bounding box as reference
        g.setColor(new Color(0, 255, 0, 50)); // Semi-transparent green
        g.drawRect(x, y, width, height);
    }
    
    /**
     * Draw arc bounds for text alignment
     */
    private void drawArcBounds(Graphics2D g, double centerX, double centerY, double radius, 
                              double startAngle, double endAngle, int bowDirection) {
        int numPoints = 20; // Number of points for smooth arc
        double angleStep = (endAngle - startAngle) / numPoints;
        
        int[] xPoints = new int[numPoints + 1];
        int[] yPoints = new int[numPoints + 1];
        
        for (int i = 0; i <= numPoints; i++) {
            double angle = startAngle + i * angleStep;
            xPoints[i] = (int)(centerX + radius * Math.sin(angle));
            yPoints[i] = (int)(centerY - radius * Math.cos(angle) * bowDirection);
        }
        
        // Draw smooth polyline
        for (int i = 0; i < numPoints; i++) {
            g.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
        }
    }
    
    /**
     * Connect inner and outer arc bounds
     */
    private void connectArcBounds(Graphics2D g, double centerX, double centerY, double innerRadius, 
                                 double outerRadius, double startAngle, double endAngle, int bowDirection) {
        // Left connection
        int x1 = (int)(centerX + innerRadius * Math.sin(startAngle));
        int y1 = (int)(centerY - innerRadius * Math.cos(startAngle) * bowDirection);
        int x2 = (int)(centerX + outerRadius * Math.sin(startAngle));
        int y2 = (int)(centerY - outerRadius * Math.cos(startAngle) * bowDirection);
        g.drawLine(x1, y1, x2, y2);
        
        // Right connection
        x1 = (int)(centerX + innerRadius * Math.sin(endAngle));
        y1 = (int)(centerY - innerRadius * Math.cos(endAngle) * bowDirection);
        x2 = (int)(centerX + outerRadius * Math.sin(endAngle));
        y2 = (int)(centerY - outerRadius * Math.cos(endAngle) * bowDirection);
        g.drawLine(x1, y1, x2, y2);
    }
    
    /**
     * Draw aligned curvature controls that follow text positioning
     */
    private void drawAlignedCurvatureControls(Graphics2D g) {
        FontMetrics fm = getFontMetrics(g, font);
        AlignmentParameters alignment = calculateSmartAlignment(fm);
        
        // Curvature control - position relative to text center
        int controlX = (int)alignment.centerX;
        int controlY = bowUp ? 
            (int)(alignment.centerY - alignment.radius - 20) : 
            (int)(alignment.centerY + alignment.radius + 20);
        
        g.setColor(Color.BLUE);
        g.fillOval(controlX - 6, controlY - 6, 12, 12);
        g.setColor(Color.WHITE);
        g.fillOval(controlX - 3, controlY - 3, 6, 6);
        
        // Direction toggle - position at text edge
        int toggleX = x + width - 25;
        int toggleY = y + 5;
        g.setColor(bowUp ? new Color(0, 200, 0) : new Color(255, 140, 0));
        g.fillRoundRect(toggleX, toggleY, 20, 15, 5, 5);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString(bowUp ? "↑" : "↓", toggleX + 7, toggleY + 11);
        
        // Curvature indicator line
        g.setColor(new Color(100, 100, 255, 100));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0));
        g.drawLine(controlX, controlY, (int)alignment.centerX, (int)alignment.centerY);
    }
    
    @Override
    public boolean contains(int px, int py) {
        // Check if point is in the control areas when selected
        // Curvature control
        int controlX = x + width / 2;
        int controlY = bowUp ? y + 10 : y + height - 10;
        if (Math.abs(px - controlX) <= 5 && Math.abs(py - controlY) <= 5) {
            return true;
        }
        
        // Direction toggle
        int toggleX = x + width - 30;
        int toggleY = y + 10;
        if (px >= toggleX && px <= toggleX + 20 && py >= toggleY && py <= toggleY + 15) {
            return true;
        }
        
        return super.contains(px, py);
    }
    
    @Override
    public void startDrag(int mx, int my) {
        // Check if dragging curvature control
        int controlX = x + width / 2;
        int controlY = bowUp ? y + 10 : y + height - 10;
        if (Math.abs(mx - controlX) <= 5 && Math.abs(my - controlY) <= 5) {
            // Start curvature adjustment
            return;
        }
        
        // Check if clicking direction toggle
        int toggleX = x + width - 30;
        int toggleY = y + 10;
        if (mx >= toggleX && mx <= toggleX + 20 && my >= toggleY && my <= toggleY + 15) {
            bowUp = !bowUp;
            return;
        }
        
        // Normal drag
        super.startDrag(mx, my);
    }
    
    @Override
    public void dragTo(int mx, int my) {
        // Check if adjusting curvature
        int controlX = x + width / 2;
        int controlY = bowUp ? y + 10 : y + height - 10;
        if (Math.abs(mx - controlX) <= 20) {
            // Adjust curvature based on vertical movement
            double deltaY = my - controlY;
            curvature = Math.max(0.1, Math.min(1.0, curvature + deltaY * 0.01));
            return;
        }
        
        super.dragTo(mx, my);
    }
    
    // Getters and setters
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
        this.width = Math.max(200, text.length() * 15);
    }
    
    public double getCurvature() {
        return curvature;
    }
    
    public void setCurvature(double curvature) {
        this.curvature = Math.max(0.1, Math.min(1.0, curvature));
    }
    
    // Stub methods for missing functionality
    private void drawGuideLines(Graphics2D g) {
        // Optional: Draw guide lines for debugging
    }
    
    private void drawArrowLine(Graphics2D g, double x1, double y1, double x2, double y2) {
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        
        // Draw arrow head
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);
        
        if (length > 5) {
            double angle = Math.atan2(dy, dx);
            double arrowLength = 5;
            double arrowAngle = Math.PI / 6;
            
            double x3 = x2 - arrowLength * Math.cos(angle - arrowAngle);
            double y3 = y2 - arrowLength * Math.sin(angle - arrowAngle);
            
            double x4 = x2 - arrowLength * Math.cos(angle + arrowAngle);
            double y4 = y2 - arrowLength * Math.sin(angle + arrowAngle);
            
            g.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
            g.drawLine((int)x2, (int)y2, (int)x4, (int)y4);
        }
    }
    

    
    private AlignmentParameters calculateSmartAlignment(FontMetrics fm) {
        // Simple alignment calculation
        AlignmentParameters params = new AlignmentParameters();
        params.centerX = x + width / 2.0;
        params.centerY = bowUp ? (y + height - 30) : (y + 30);
        params.radius = arcRadius;
        params.bowDirection = bowUp ? 1 : -1;
        
        return params;
    }
    
    public boolean isBowUp() {
        return bowUp;
    }
    
    public void setBowUp(boolean bowUp) {
        this.bowUp = bowUp;
    }
    
    public Font getFont() {
        return font;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public Color getTextColor() {
        return textColor;
    }
    
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    // Smart handle detection for BowText controls - prioritize curve controls
    // Soft-coded intelligent handle detection system with enhanced geometry and debug support
    public BowTextResizeHandle getBowTextResizeHandle(int px, int py) {
        // Soft-coded configuration parameters for easy tuning
        final int CURVATURE_HIT_RADIUS = 15;  // Larger for main control
        final int DIRECTION_HIT_RADIUS = 12;  // Medium for direction toggle
        final int WIDTH_HIT_RADIUS = 10;      // Good for width controls
        final int ARC_HIT_RADIUS = 8;         // Smaller for precision controls
        final int CORNER_HIT_RADIUS = 8;      // Small for fine controls
        
        // Enhanced geometry calculations for better handle positioning
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;  // Use actual center
        int midY = y + height / 2;
        
        // Calculate curved text arc parameters with soft coding
        double effectiveRadius = Math.max(30, arcRadius * (0.5 + curvature));  // Dynamic radius
        double curvatureOffset = effectiveRadius * Math.sin(curvature * Math.PI / 2);
        
        // Priority 1: Curvature Control Handle (Blue Circle) - Most Important
        int curvatureX = (int)centerX;
        int curvatureY = (int)(centerY + (bowUp ? -curvatureOffset - 20 : curvatureOffset + 20));
        
        System.out.println("🎯 Curvature handle at (" + curvatureX + "," + curvatureY + ") - radius:" + CURVATURE_HIT_RADIUS);
        if (isInBowTextHandle(px, py, curvatureX, curvatureY, CURVATURE_HIT_RADIUS)) {
            System.out.println("✅ CURVATURE CONTROL detected!");
            return BowTextResizeHandle.TOP;
        }
        
        // Priority 2: Bow Direction Toggle (Yellow Circle) - Enhanced positioning
        int toggleX = x + width - 20;  // Better positioning
        int toggleY = y + 15;
        
        System.out.println("🔄 Direction handle at (" + toggleX + "," + toggleY + ") - radius:" + DIRECTION_HIT_RADIUS);
        if (isInBowTextHandle(px, py, toggleX, toggleY, DIRECTION_HIT_RADIUS)) {
            System.out.println("✅ DIRECTION TOGGLE detected!");
            return BowTextResizeHandle.BOTTOM;
        }
        
        // Priority 3: Width Controls (Green Rectangles) - Enhanced left/right positioning
        int leftWidthX = x - 5;   // Extend outside bounds for easier access
        int rightWidthX = x + width + 5;
        
        System.out.println("⬅️ Left width handle at (" + leftWidthX + "," + midY + ") - radius:" + WIDTH_HIT_RADIUS);
        if (isInBowTextHandle(px, py, leftWidthX, midY, WIDTH_HIT_RADIUS)) {
            System.out.println("✅ LEFT WIDTH CONTROL detected!");
            return BowTextResizeHandle.LEFT;
        }
        
        System.out.println("➡️ Right width handle at (" + rightWidthX + "," + midY + ") - radius:" + WIDTH_HIT_RADIUS);
        if (isInBowTextHandle(px, py, rightWidthX, midY, WIDTH_HIT_RADIUS)) {
            System.out.println("✅ RIGHT WIDTH CONTROL detected!");
            return BowTextResizeHandle.RIGHT;
        }
        
        // Priority 4: Arc Angle Controls (Purple Diamonds) - Improved arc positioning
        double arcSpread = Math.PI * 0.3;  // Soft-coded arc spread
        double leftArcAngle = -arcSpread / 2;
        double rightArcAngle = arcSpread / 2;
        
        int leftArcX = (int)(centerX + effectiveRadius * Math.sin(leftArcAngle) * 0.8);
        int leftArcY = (int)(centerY - effectiveRadius * Math.cos(leftArcAngle) * (bowUp ? 0.8 : -0.8));
        int rightArcX = (int)(centerX + effectiveRadius * Math.sin(rightArcAngle) * 0.8);
        int rightArcY = (int)(centerY - effectiveRadius * Math.cos(rightArcAngle) * (bowUp ? 0.8 : -0.8));
        
        System.out.println("🔺 Left arc handle at (" + leftArcX + "," + leftArcY + ") - radius:" + ARC_HIT_RADIUS);
        if (isInBowTextHandle(px, py, leftArcX, leftArcY, ARC_HIT_RADIUS)) {
            System.out.println("✅ LEFT ARC CONTROL detected!");
            return BowTextResizeHandle.TOP_LEFT;
        }
        
        System.out.println("🔻 Right arc handle at (" + rightArcX + "," + rightArcY + ") - radius:" + ARC_HIT_RADIUS);
        if (isInBowTextHandle(px, py, rightArcX, rightArcY, ARC_HIT_RADIUS)) {
            System.out.println("✅ RIGHT ARC CONTROL detected!");
            return BowTextResizeHandle.TOP_RIGHT;
        }
        
        // Priority 5: Corner Fine-tuning Handles - Better corner positioning
        int topLeftX = x + 5;
        int topLeftY = y + 5;
        int bottomRightX = x + width - 5;
        int bottomRightY = y + height - 5;
        
        System.out.println("🔧 Top-left corner at (" + topLeftX + "," + topLeftY + ") - radius:" + CORNER_HIT_RADIUS);
        if (isInBowTextHandle(px, py, topLeftX, topLeftY, CORNER_HIT_RADIUS)) {
            System.out.println("✅ TOP-LEFT CORNER detected!");
            return BowTextResizeHandle.BOTTOM_LEFT;
        }
        
        System.out.println("🔧 Bottom-right corner at (" + bottomRightX + "," + bottomRightY + ") - radius:" + CORNER_HIT_RADIUS);
        if (isInBowTextHandle(px, py, bottomRightX, bottomRightY, CORNER_HIT_RADIUS)) {
            System.out.println("✅ BOTTOM-RIGHT CORNER detected!");
            return BowTextResizeHandle.BOTTOM_RIGHT;
        }
        
        System.out.println("❌ No handle detected at (" + px + "," + py + ")");
        System.out.println("   📍 All handle positions:");
        System.out.println("   🎯 Curvature: (" + curvatureX + "," + curvatureY + ")");
        System.out.println("   🔄 Direction: (" + toggleX + "," + toggleY + ")");
        System.out.println("   ⬅️ Left Width: (" + leftWidthX + "," + midY + ")");
        System.out.println("   ➡️ Right Width: (" + rightWidthX + "," + midY + ")");
        return BowTextResizeHandle.NONE;
    }
    
    // Default hit detection method
    private boolean isInBowTextHandle(int px, int py, int handleX, int handleY) {
        return isInBowTextHandle(px, py, handleX, handleY, HANDLE_SIZE + 3);
    }
    
    // Enhanced hit detection with custom radius
    private boolean isInBowTextHandle(int px, int py, int handleX, int handleY, int hitRadius) {
        return Math.abs(px - handleX) <= hitRadius && Math.abs(py - handleY) <= hitRadius;
    }
    
    // Override resize handle detection to use dynamic BowText resizing
    @Override
    public boolean overResizeHandle(int px, int py) {
        BowTextResizeHandle handle = getBowTextResizeHandle(px, py);
        return handle != BowTextResizeHandle.NONE;
    }
    
    // Enhanced Smart BowText control system with full multi-directional support
    public void resizeBowText(int deltaX, int deltaY, BowTextResizeHandle handle) {
        System.out.println("🏹 BowText Multi-Directional Resize - Handle: " + handle + ", Delta: (" + deltaX + "," + deltaY + ")");
        
        // Soft-coded sensitivity parameters for fine-tuning
        final double CURVATURE_SENSITIVITY = 0.008;
        final double RADIUS_SENSITIVITY = 0.3;
        final double ARC_ANGLE_SENSITIVITY = 0.02;
        final double FINE_CURVATURE_SENSITIVITY = 0.003;
        final int MIN_WIDTH = 50;
        final int MAX_RADIUS = 150;
        final int MIN_RADIUS = 30;
        
        switch (handle) {
            case TOP:
                // TOP = Multi-Directional Curvature Control (Blue Circle)
                // Vertical movement = curvature adjustment
                double curvatureDelta = deltaY * CURVATURE_SENSITIVITY;
                if (bowUp) curvatureDelta = -curvatureDelta;
                setCurvature(curvature + curvatureDelta);
                
                // Horizontal movement = arc radius adjustment
                double radiusDelta = deltaX * RADIUS_SENSITIVITY;
                arcRadius = Math.max(MIN_RADIUS, Math.min(MAX_RADIUS, arcRadius + radiusDelta));
                
                System.out.println("   🎯 Curvature: " + String.format("%.3f", curvature) + 
                                 ", Radius: " + String.format("%.1f", arcRadius) + 
                                 " (V:" + deltaY + "→curve, H:" + deltaX + "→radius)");
                break;
                
            case BOTTOM:
                // BOTTOM = Multi-Directional Direction & Position Control (Yellow Circle)
                // Large movement = direction toggle
                if (Math.abs(deltaX) > 8 || Math.abs(deltaY) > 8) {
                    bowUp = !bowUp;
                    System.out.println("   🔄 Direction toggled to: " + (bowUp ? "UP ⬆️" : "DOWN ⬇️"));
                } else {
                    // Small movement = fine position adjustment
                    x += deltaX / 2;
                    y += deltaY / 2;
                    System.out.println("   🔀 Position adjusted by: (" + (deltaX/2) + "," + (deltaY/2) + ")");
                }
                break;
                
            case LEFT:
                // LEFT = Multi-Directional Width & Height Control (Green Rectangle)
                // Horizontal = width control (decrease from left)
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    int widthChange = Math.abs(deltaX);
                    width = Math.max(MIN_WIDTH, width - widthChange);
                    x += widthChange / 2; // Keep centered
                    System.out.println("   ⬅️ Width decreased to: " + width + " (H-drag dominant)");
                } else {
                    // Vertical = height control
                    height = Math.max(20, height + deltaY);
                    System.out.println("   🔽 Height adjusted to: " + height + " (V-drag dominant)");
                }
                break;
                
            case RIGHT:
                // RIGHT = Multi-Directional Width & Height Control (Green Rectangle)
                // Horizontal = width control (increase to right)
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    width += Math.abs(deltaX);
                    System.out.println("   ➡️ Width increased to: " + width + " (H-drag dominant)");
                } else {
                    // Vertical = height control
                    height = Math.max(20, height + deltaY);
                    System.out.println("   🔽 Height adjusted to: " + height + " (V-drag dominant)");
                }
                break;
                
            case TOP_LEFT:
                // TOP_LEFT = Multi-Directional Arc Control (Purple Diamond)
                // Horizontal = arc angle adjustment
                double leftAngleDelta = deltaX * ARC_ANGLE_SENSITIVITY;
                arcAngle = Math.max(30, Math.min(180, arcAngle + leftAngleDelta));
                
                // Vertical = curvature fine-tuning
                double leftCurvatureDelta = deltaY * FINE_CURVATURE_SENSITIVITY;
                setCurvature(curvature + leftCurvatureDelta);
                
                System.out.println("   🔺 Left Arc - Angle: " + String.format("%.1f", arcAngle) + 
                                 "°, Curve: " + String.format("%.3f", curvature) + 
                                 " (H:" + deltaX + "→angle, V:" + deltaY + "→curve)");
                break;
                
            case TOP_RIGHT:
                // TOP_RIGHT = Multi-Directional Arc Control (Purple Diamond)
                // Horizontal = arc angle adjustment
                double rightAngleDelta = deltaX * ARC_ANGLE_SENSITIVITY;
                arcAngle = Math.max(30, Math.min(180, arcAngle + rightAngleDelta));
                
                // Vertical = curvature fine-tuning
                double rightCurvatureDelta = deltaY * FINE_CURVATURE_SENSITIVITY;
                setCurvature(curvature + rightCurvatureDelta);
                
                System.out.println("   🔻 Right Arc - Angle: " + String.format("%.1f", arcAngle) + 
                                 "°, Curve: " + String.format("%.3f", curvature) + 
                                 " (H:" + deltaX + "→angle, V:" + deltaY + "→curve)");
                break;
                
            case BOTTOM_LEFT:
                // BOTTOM_LEFT = Multi-Directional Corner Control (Orange Square)
                // Combine width reduction and position adjustment
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // Horizontal dominant = width control
                    int widthReduction = Math.abs(deltaX);
                    width = Math.max(MIN_WIDTH, width - widthReduction);
                    x += widthReduction / 2;
                    System.out.println("   🔧 Corner - Width reduced: " + width + " (H-dominant)");
                } else {
                    // Vertical dominant = curvature control
                    double cornerCurvature = deltaY * FINE_CURVATURE_SENSITIVITY;
                    setCurvature(curvature + cornerCurvature);
                    System.out.println("   🔧 Corner - Curvature: " + String.format("%.3f", curvature) + " (V-dominant)");
                }
                break;
                
            case BOTTOM_RIGHT:
                // BOTTOM_RIGHT = Multi-Directional Corner Control (Orange Square)
                // Combine width expansion and position adjustment
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // Horizontal dominant = width control
                    width += Math.abs(deltaX);
                    System.out.println("   🔧 Corner - Width expanded: " + width + " (H-dominant)");
                } else {
                    // Vertical dominant = curvature control
                    double cornerCurvature = deltaY * FINE_CURVATURE_SENSITIVITY;
                    setCurvature(curvature + cornerCurvature);
                    System.out.println("   🔧 Corner - Curvature: " + String.format("%.3f", curvature) + " (V-dominant)");
                }
                break;
                
            default:
                System.out.println("   ❌ Unknown handle type: " + handle);
        }
        
        // Update bounds and refresh display  
        System.out.println("   📐 Final bounds: (" + x + "," + y + ") " + width + "x" + height);
    }
}
