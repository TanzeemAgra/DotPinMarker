import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ArcLettersMark extends Mark {
    private String letters = "ABCDE";
    private double startAngle = 45; // Start angle in degrees
    private double arcAngle = 90;   // Arc span in degrees
    private Font font = new Font("Arial", Font.BOLD, 24);
    private Color textColor = Color.BLACK;
    private Color arcColor = Color.LIGHT_GRAY;
    private boolean showArc = true;
    private double radius = 80;
    
    public ArcLettersMark(int x, int y) {
        super(x, y);
        this.width = 200;
        this.height = 200;
    }
    
    public ArcLettersMark(int x, int y, String letters) {
        super(x, y);
        this.letters = letters.toUpperCase();
        this.width = 200;
        this.height = 200;
    }
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Enable antialiasing for smooth curves
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Calculate center point
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        // Draw the arc sector (pie wedge) if enabled
        if (showArc) {
            drawArcSector(g, centerX, centerY);
        }
        
        // Draw the curved letters
        drawCurvedLetters(g, centerX, centerY);
        
        // Draw selection indicators if selected
        if (isSelected) {
            drawSelectionIndicators(g);
        }
    }
    
    private void drawArcSector(Graphics2D g, double centerX, double centerY) {
        // Create the pie wedge shape
        Arc2D.Double arcSector = new Arc2D.Double(
            centerX - radius, centerY - radius, 
            radius * 2, radius * 2,
            startAngle, arcAngle, Arc2D.PIE
        );
        
        // Fill the sector with a light color
        g.setColor(new Color(240, 240, 240, 100)); // Light gray with transparency
        g.fill(arcSector);
        
        // Draw the sector outline
        g.setColor(arcColor);
        g.setStroke(new BasicStroke(2));
        g.draw(arcSector);
        
        // Draw the inner arc where letters will be placed
        double innerRadius = radius * 0.7; // Letters placed at 70% of radius
        Arc2D.Double innerArc = new Arc2D.Double(
            centerX - innerRadius, centerY - innerRadius,
            innerRadius * 2, innerRadius * 2,
            startAngle, arcAngle, Arc2D.OPEN
        );
        
        g.setColor(new Color(arcColor.getRed(), arcColor.getGreen(), arcColor.getBlue(), 150));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
        g.draw(innerArc);
    }
    
    private void drawCurvedLetters(Graphics2D g, double centerX, double centerY) {
        if (letters == null || letters.isEmpty()) return;
        
        g.setFont(font);
        g.setColor(textColor);
        
        char[] chars = letters.toCharArray();
        double letterRadius = radius * 0.7; // Place letters at 70% of the radius
        
        // Calculate angles for each letter
        double totalAngle = Math.toRadians(arcAngle);
        double startAngleRad = Math.toRadians(startAngle);
        
        // Distribute letters evenly along the arc
        double angleStep = chars.length > 1 ? totalAngle / (chars.length - 1) : 0;
        
        for (int i = 0; i < chars.length; i++) {
            double currentAngle = startAngleRad + i * angleStep;
            
            // Calculate position on the arc (note: Java2D angles are measured differently)
            // Convert to standard math coordinates where 0° is east, 90° is north
            double mathAngle = -currentAngle + Math.PI/2; // Convert Java2D angle to math angle
            double letterX = centerX + letterRadius * Math.cos(mathAngle);
            double letterY = centerY - letterRadius * Math.sin(mathAngle); // Negative because Y increases downward
            
            // Save current transform
            AffineTransform oldTransform = g.getTransform();
            
            // Move to letter position
            g.translate(letterX, letterY);
            
            // Rotate letter to be tangent to the arc
            // The tangent angle is perpendicular to the radius
            double tangentAngle = mathAngle + Math.PI/2;
            g.rotate(tangentAngle);
            
            // Draw the letter centered
            String letterStr = String.valueOf(chars[i]);
            FontMetrics fm = g.getFontMetrics();
            int letterWidth = fm.stringWidth(letterStr);
            int letterHeight = fm.getAscent();
            
            // Draw letter centered on the arc
            g.drawString(letterStr, -letterWidth/2, letterHeight/3);
            
            // Restore transform
            g.setTransform(oldTransform);
        }
    }
    
    private void drawSelectionIndicators(Graphics2D g) {
        // Draw bounding box (GREEN) - draggable for size control
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(2));
        g.drawRect(x, y, width, height);
        
        // Draw resize handle
        g.setColor(Color.RED);
        g.fillRect(x + width - HANDLE_SIZE, y + height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE);
        
        // Draw control points for arc adjustment with improved visibility
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        // Start angle control (BLUE) with bi-directional arrow
        double startAngleRad = Math.toRadians(startAngle);
        double startControlX = centerX + (radius + 20) * Math.cos(-startAngleRad + Math.PI/2);
        double startControlY = centerY - (radius + 20) * Math.sin(-startAngleRad + Math.PI/2);
        g.setColor(Color.BLUE);
        g.fillOval((int)startControlX - 6, (int)startControlY - 6, 12, 12);
        drawBidirectionalArrow(g, startControlX, startControlY, startAngleRad, Color.BLUE, "START");
        
        // End angle control (ORANGE) with bi-directional arrow
        double endAngleRad = Math.toRadians(startAngle + arcAngle);
        double endControlX = centerX + (radius + 20) * Math.cos(-endAngleRad + Math.PI/2);
        double endControlY = centerY - (radius + 20) * Math.sin(-endAngleRad + Math.PI/2);
        g.setColor(Color.ORANGE);
        g.fillOval((int)endControlX - 6, (int)endControlY - 6, 12, 12);
        drawBidirectionalArrow(g, endControlX, endControlY, endAngleRad, Color.ORANGE, "END");
        
        // 360-degree rotation handle (CYAN) with rotation symbol
        double rotationHandleDistance = radius + 40;
        double rotationHandleX = centerX + rotationHandleDistance * Math.cos(-startAngleRad + Math.PI/2 + Math.PI/4);
        double rotationHandleY = centerY - rotationHandleDistance * Math.sin(-startAngleRad + Math.PI/2 + Math.PI/4);
        g.setColor(Color.CYAN);
        g.fillOval((int)rotationHandleX - 8, (int)rotationHandleY - 8, 16, 16);
        drawRotationSymbol(g, rotationHandleX, rotationHandleY, Color.CYAN);
        
        // Radius control (MAGENTA) with radial arrows
        g.setColor(Color.MAGENTA);
        g.fillOval((int)centerX - 6, (int)centerY - 6, 12, 12);
        drawRadialArrows(g, centerX, centerY, Color.MAGENTA);
        
        // Text size control (YELLOW) on the inner arc where text is placed
        double textRadius = radius * 0.7; // Same as letter radius
        double midAngle = Math.toRadians(startAngle + arcAngle / 2.0); // Middle of the arc
        double textSizeHandleX = centerX + textRadius * Math.cos(-midAngle + Math.PI/2);
        double textSizeHandleY = centerY - textRadius * Math.sin(-midAngle + Math.PI/2);
        g.setColor(Color.YELLOW);
        g.fillOval((int)textSizeHandleX - 6, (int)textSizeHandleY - 6, 12, 12);
        drawTextSizeIndicator(g, textSizeHandleX, textSizeHandleY, Color.YELLOW);
        
        // Draw arc span indicator
        drawArcSpanIndicator(g, centerX, centerY);
        
        // Draw labels for clarity
        drawControlLabels(g, centerX, centerY);
    }
    
    private void drawBidirectionalArrow(Graphics2D g, double x, double y, double angle, Color color, String label) {
        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        
        // Draw tangent arrows (perpendicular to radius)
        double tangentAngle = angle + Math.PI/2;
        double arrowLength = 20;
        
        // Arrow pointing clockwise
        double x1 = x + arrowLength * Math.cos(tangentAngle);
        double y1 = y - arrowLength * Math.sin(tangentAngle);
        g.drawLine((int)x, (int)y, (int)x1, (int)y1);
        drawArrowHead(g, x1, y1, tangentAngle);
        
        // Arrow pointing counter-clockwise
        double x2 = x + arrowLength * Math.cos(tangentAngle + Math.PI);
        double y2 = y - arrowLength * Math.sin(tangentAngle + Math.PI);
        g.drawLine((int)x, (int)y, (int)x2, (int)y2);
        drawArrowHead(g, x2, y2, tangentAngle + Math.PI);
        
        // Draw label
        g.setColor(color.darker());
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString(label, (int)x + 12, (int)y - 12);
    }
    
    private void drawRotationSymbol(Graphics2D g, double x, double y, Color color) {
        g.setColor(color.darker());
        g.setStroke(new BasicStroke(2.0f));
        
        // Draw circular arrow
        int circleRadius = 6;
        g.drawArc((int)x - circleRadius, (int)y - circleRadius, circleRadius * 2, circleRadius * 2, 45, 270);
        
        // Draw arrow head at the end
        double arrowX = x + circleRadius * Math.cos(Math.toRadians(-45));
        double arrowY = y - circleRadius * Math.sin(Math.toRadians(-45));
        drawArrowHead(g, arrowX, arrowY, Math.toRadians(-45 + 90));
    }
    
    private void drawRadialArrows(Graphics2D g, double centerX, double centerY, Color color) {
        g.setColor(color.darker());
        g.setStroke(new BasicStroke(2));
        
        // Draw arrows pointing inward and outward
        int arrowLength = 15;
        
        // Outward arrow (increase radius)
        g.drawLine((int)centerX, (int)centerY, (int)centerX + arrowLength, (int)centerY);
        drawArrowHead(g, centerX + arrowLength, centerY, 0);
        
        // Inward arrow (decrease radius)
        g.drawLine((int)centerX, (int)centerY, (int)centerX - arrowLength, (int)centerY);
        drawArrowHead(g, centerX - arrowLength, centerY, Math.PI);
    }
    
    private void drawArrowHead(Graphics2D g, double x, double y, double angle) {
        int arrowSize = 4;
        double x1 = x + arrowSize * Math.cos(angle - Math.PI/6);
        double y1 = y - arrowSize * Math.sin(angle - Math.PI/6);
        double x2 = x + arrowSize * Math.cos(angle + Math.PI/6);
        double y2 = y - arrowSize * Math.sin(angle + Math.PI/6);
        
        int[] xPoints = {(int)x, (int)x1, (int)x2};
        int[] yPoints = {(int)y, (int)y1, (int)y2};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    private void drawArcSpanIndicator(Graphics2D g, double centerX, double centerY) {
        g.setColor(new Color(255, 0, 255, 100)); // Semi-transparent magenta
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 3}, 0));
        
        // Draw arc span indicator at a slightly larger radius
        double indicatorRadius = radius + 10;
        Arc2D.Double spanArc = new Arc2D.Double(
            centerX - indicatorRadius, centerY - indicatorRadius,
            indicatorRadius * 2, indicatorRadius * 2,
            startAngle, arcAngle, Arc2D.OPEN
        );
        g.draw(spanArc);
    }
    
    private void drawControlLabels(Graphics2D g, double centerX, double centerY) {
        g.setFont(new Font("Arial", Font.BOLD, 11));
        
        // Arc angle label
        g.setColor(Color.BLACK);
        String angleLabel = String.format("Arc: %.0f°", arcAngle);
        g.drawString(angleLabel, (int)centerX - 30, (int)centerY - (int)radius - 25);
        
        // Radius label
        String radiusLabel = String.format("R: %.0f", radius);
        g.drawString(radiusLabel, (int)centerX + 15, (int)centerY + 5);
        
        // Character count and spacing info
        if (letters != null && !letters.isEmpty()) {
            String infoLabel = String.format("%d chars", letters.length());
            g.drawString(infoLabel, (int)centerX - 60, (int)centerY + (int)radius + 40);
        }
    }
    
    private void drawTextSizeIndicator(Graphics2D g, double x, double y, Color color) {
        g.setColor(color.darker());
        g.setStroke(new BasicStroke(2.0f));
        
        // Draw bidirectional size arrows (horizontal and vertical)
        int arrowSize = 6;
        
        // Horizontal size arrows
        g.drawLine((int)(x - arrowSize), (int)y, (int)(x + arrowSize), (int)y);
        // Left arrow head
        g.drawLine((int)(x - arrowSize), (int)y, (int)(x - arrowSize + 2), (int)(y - 2));
        g.drawLine((int)(x - arrowSize), (int)y, (int)(x - arrowSize + 2), (int)(y + 2));
        // Right arrow head
        g.drawLine((int)(x + arrowSize), (int)y, (int)(x + arrowSize - 2), (int)(y - 2));
        g.drawLine((int)(x + arrowSize), (int)y, (int)(x + arrowSize - 2), (int)(y + 2));
        
        // Vertical size arrows
        g.drawLine((int)x, (int)(y - arrowSize), (int)x, (int)(y + arrowSize));
        // Up arrow head
        g.drawLine((int)x, (int)(y - arrowSize), (int)(x - 2), (int)(y - arrowSize + 2));
        g.drawLine((int)x, (int)(y - arrowSize), (int)(x + 2), (int)(y - arrowSize + 2));
        // Down arrow head
        g.drawLine((int)x, (int)(y + arrowSize), (int)(x - 2), (int)(y + arrowSize - 2));
        g.drawLine((int)x, (int)(y + arrowSize), (int)(x + 2), (int)(y + arrowSize - 2));
        
        // Add text "A" symbol to indicate text size
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("A", (int)x - 3, (int)y + 15);
    }
    
    @Override
    public boolean contains(int px, int py) {
        // Check if point is within the arc sector
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        double dx = px - centerX;
        double dy = py - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Check if within radius
        if (distance > radius) return false;
        
        // Check if within angle range
        double angle = Math.toDegrees(Math.atan2(-dy, dx)) + 90; // Convert to Java2D angle
        if (angle < 0) angle += 360;
        
        double endAngle = (startAngle + arcAngle) % 360;
        
        if (startAngle <= endAngle) {
            return angle >= startAngle && angle <= endAngle;
        } else {
            return angle >= startAngle || angle <= endAngle;
        }
    }
    
    // Control handle types
    public enum ControlHandle {
        NONE, RESIZE, START_ANGLE, END_ANGLE, ROTATION, RADIUS, GREEN_BOX, TEXT_SIZE
    }
    
    // Check which control handle is being clicked
    public ControlHandle getControlHandle(int px, int py) {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        // Check resize handle
        if (px >= x + width - HANDLE_SIZE && px <= x + width &&
            py >= y + height - HANDLE_SIZE && py <= y + height) {
            return ControlHandle.RESIZE;
        }
        
        // Check radius control (center point)
        if (Math.sqrt((px - centerX) * (px - centerX) + (py - centerY) * (py - centerY)) <= 6) {
            return ControlHandle.RADIUS;
        }
        
        // Check start angle control
        double startAngleRad = Math.toRadians(startAngle);
        double startControlX = centerX + (radius + 20) * Math.cos(-startAngleRad + Math.PI/2);
        double startControlY = centerY - (radius + 20) * Math.sin(-startAngleRad + Math.PI/2);
        if (Math.sqrt((px - startControlX) * (px - startControlX) + (py - startControlY) * (py - startControlY)) <= 8) {
            return ControlHandle.START_ANGLE;
        }
        
        // Check end angle control
        double endAngleRad = Math.toRadians(startAngle + arcAngle);
        double endControlX = centerX + (radius + 20) * Math.cos(-endAngleRad + Math.PI/2);
        double endControlY = centerY - (radius + 20) * Math.sin(-endAngleRad + Math.PI/2);
        if (Math.sqrt((px - endControlX) * (px - endControlX) + (py - endControlY) * (py - endControlY)) <= 8) {
            return ControlHandle.END_ANGLE;
        }
        
        // Check rotation handle
        double rotationHandleDistance = radius + 40;
        double rotationHandleX = centerX + rotationHandleDistance * Math.cos(-startAngleRad + Math.PI/2 + Math.PI/4);
        double rotationHandleY = centerY - rotationHandleDistance * Math.sin(-startAngleRad + Math.PI/2 + Math.PI/4);
        if (Math.sqrt((px - rotationHandleX) * (px - rotationHandleX) + (py - rotationHandleY) * (py - rotationHandleY)) <= 10) {
            return ControlHandle.ROTATION;
        }
        
        // Check text size control handle (on the inner arc where text is placed)
        double textRadius = radius * 0.7; // Same as letter radius
        double midAngle = Math.toRadians(startAngle + arcAngle / 2.0); // Middle of the arc
        double textSizeHandleX = centerX + textRadius * Math.cos(-midAngle + Math.PI/2);
        double textSizeHandleY = centerY - textRadius * Math.sin(-midAngle + Math.PI/2);
        if (Math.sqrt((px - textSizeHandleX) * (px - textSizeHandleX) + (py - textSizeHandleY) * (py - textSizeHandleY)) <= 8) {
            return ControlHandle.TEXT_SIZE;
        }
        
        // Check green box (bounding box edges) for size control
        // Check if click is on the green border but not on other controls
        boolean onLeftEdge = (px >= x - 5 && px <= x + 5 && py >= y && py <= y + height);
        boolean onRightEdge = (px >= x + width - 5 && px <= x + width + 5 && py >= y && py <= y + height);
        boolean onTopEdge = (px >= x && px <= x + width && py >= y - 5 && py <= y + 5);
        boolean onBottomEdge = (px >= x && px <= x + width && py >= y + height - 5 && py <= y + height + 5);
        
        if (onLeftEdge || onRightEdge || onTopEdge || onBottomEdge) {
            return ControlHandle.GREEN_BOX;
        }
        
        return ControlHandle.NONE;
    }
    
    // Handle rotation - rotates the entire arc around its center
    public void rotate360(int mouseX, int mouseY) {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        // Calculate angle from center to mouse position
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double angleToMouse = Math.toDegrees(Math.atan2(-dy, dx)) + 90; // Convert to Java2D angle
        
        // Normalize angle to 0-360 range
        if (angleToMouse < 0) angleToMouse += 360;
        if (angleToMouse >= 360) angleToMouse -= 360;
        
        // Set the start angle to rotate the entire arc
        this.startAngle = angleToMouse;
    }
    
    // Handle start angle adjustment
    public void adjustStartAngle(int mouseX, int mouseY) {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double newStartAngle = Math.toDegrees(Math.atan2(-dy, dx)) + 90;
        
        if (newStartAngle < 0) newStartAngle += 360;
        if (newStartAngle >= 360) newStartAngle -= 360;
        
        this.startAngle = newStartAngle;
    }
    
    // Handle end angle adjustment (by adjusting arc span)
    public void adjustEndAngle(int mouseX, int mouseY) {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double endAngle = Math.toDegrees(Math.atan2(-dy, dx)) + 90;
        
        if (endAngle < 0) endAngle += 360;
        if (endAngle >= 360) endAngle -= 360;
        
        // Calculate new arc angle
        double newArcAngle = endAngle - startAngle;
        if (newArcAngle < 0) newArcAngle += 360;
        
        setArcAngle(newArcAngle);
    }
    
    // Handle radius adjustment
    public void adjustRadius(int mouseX, int mouseY) {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double newRadius = Math.sqrt(dx * dx + dy * dy);
        
        setRadius(newRadius);
    }
    
    // Handle draggable size control via green box - drag to increase/decrease size
    public void adjustSizeByGreenBox(int mouseX, int mouseY, int startX, int startY) {
        // Calculate the distance mouse has moved from start position
        double deltaX = mouseX - startX;
        double deltaY = mouseY - startY;
        
        // Use the larger movement (X or Y) to determine size change
        double movement = Math.max(Math.abs(deltaX), Math.abs(deltaY));
        
        // Determine direction: positive if moving away from center, negative if moving toward center
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        // Check if mouse is moving away from or toward center
        double startDistanceFromCenter = Math.sqrt((startX - centerX) * (startX - centerX) + (startY - centerY) * (startY - centerY));
        double currentDistanceFromCenter = Math.sqrt((mouseX - centerX) * (mouseX - centerX) + (mouseY - centerY) * (mouseY - centerY));
        
        double direction = (currentDistanceFromCenter > startDistanceFromCenter) ? 1.0 : -1.0;
        
        // Convert movement to size change (scale factor)
        double sizeChange = direction * movement / 100.0; // 100 pixels = 100% change
        double sizeMultiplier = 1.0 + sizeChange;
        
        // Clamp the multiplier to reasonable bounds
        sizeMultiplier = Math.max(0.3, Math.min(3.0, sizeMultiplier));
        
        // Calculate new radius and font size
        double baseRadius = 80; // Original radius
        double newRadius = baseRadius * sizeMultiplier;
        newRadius = Math.max(30, Math.min(200, newRadius));
        
        float baseFontSize = 24f; // Original font size
        float newFontSize = baseFontSize * (float)sizeMultiplier;
        newFontSize = Math.max(8f, Math.min(48f, newFontSize));
        
        // Apply the changes
        this.radius = newRadius;
        this.font = font.deriveFont(newFontSize);
        
        // Adjust width and height to accommodate new radius
        this.width = (int)(radius * 2.5);
        this.height = (int)(radius * 2.5);
    }
    
    // Handle text size adjustment via yellow control handle
    public void adjustTextSize(int mouseX, int mouseY, int startX, int startY) {
        // Calculate the distance mouse has moved from start position
        double deltaY = startY - mouseY; // Inverted: moving up increases size, down decreases
        
        // Convert pixel movement to font size change (more sensitive scaling)
        double sizeChange = deltaY * 0.2; // 0.2 points per pixel moved
        
        // Get current font size and apply change
        float currentSize = getFontSize();
        float newSize = (float)(currentSize + sizeChange);
        
        // Set the new font size (setFontSize already has min/max bounds)
        setFontSize(newSize);
    }

    // Get current font size for reference
    public float getFontSize() {
        return font.getSize();
    }
    
    // Set font size directly
    public void setFontSize(float size) {
        this.font = font.deriveFont(Math.max(8f, Math.min(48f, size)));
    }
    
    // Getters and setters
    public String getLetters() {
        return letters;
    }
    
    public void setLetters(String letters) {
        this.letters = letters.toUpperCase();
    }
    
    public double getStartAngle() {
        return startAngle;
    }
    
    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }
    
    public double getArcAngle() {
        return arcAngle;
    }
    
    public void setArcAngle(double arcAngle) {
        this.arcAngle = Math.max(10, Math.min(360, arcAngle)); // Limit between 10 and 360 degrees
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = Math.max(30, radius); // Minimum radius of 30
    }
    
    public boolean isShowArc() {
        return showArc;
    }
    
    public void setShowArc(boolean showArc) {
        this.showArc = showArc;
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
    
    public Color getArcColor() {
        return arcColor;
    }
    
    public void setArcColor(Color arcColor) {
        this.arcColor = arcColor;
    }
    
    // === DOT PIN EXPORT FUNCTIONALITY ===
    
    /**
     * Generate path points for dot pin marker rendering
     * Returns a list of coordinate points that trace the letter outlines
     */
    public java.util.List<Point2D.Double> generatePathPoints(double dotSpacing) {
        java.util.List<Point2D.Double> pathPoints = new java.util.ArrayList<>();
        
        if (letters == null || letters.isEmpty()) return pathPoints;
        
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        char[] chars = letters.toCharArray();
        double letterRadius = radius * 0.7; // Place letters at 70% of the radius
        
        // Calculate angles for each letter
        double totalAngle = Math.toRadians(arcAngle);
        double startAngleRad = Math.toRadians(startAngle);
        double angleStep = chars.length > 1 ? totalAngle / (chars.length - 1) : 0;
        
        for (int i = 0; i < chars.length; i++) {
            double currentAngle = startAngleRad + i * angleStep;
            double mathAngle = -currentAngle + Math.PI/2;
            double letterX = centerX + letterRadius * Math.cos(mathAngle);
            double letterY = centerY - letterRadius * Math.sin(mathAngle);
            
            // Get character outline points
            java.util.List<Point2D.Double> charPoints = getCharacterOutlinePoints(chars[i], letterX, letterY, 
                mathAngle + Math.PI/2, dotSpacing);
            pathPoints.addAll(charPoints);
        }
        
        return pathPoints;
    }
    
    /**
     * Generate outline points for a single character
     */
    private java.util.List<Point2D.Double> getCharacterOutlinePoints(char c, double x, double y, 
            double rotation, double dotSpacing) {
        java.util.List<Point2D.Double> points = new java.util.ArrayList<>();
        
        // Create a simple outline for the character using font metrics
        // This is a simplified version - in practice, you'd want to use font outline data
        Font tempFont = font.deriveFont((float)(font.getSize() * 0.8));
        FontMetrics fm = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
            .getGraphics().getFontMetrics(tempFont);
        
        String charStr = String.valueOf(c);
        Rectangle2D charBounds = fm.getStringBounds(charStr, null);
        
        // Generate points around character perimeter
        double charWidth = charBounds.getWidth();
        double charHeight = charBounds.getHeight();
        
        // Create rectangular outline points (simplified)
        int numPoints = (int)Math.max(8, (charWidth + charHeight) / dotSpacing);
        
        for (int i = 0; i < numPoints; i++) {
            double t = (double)i / numPoints;
            double localX, localY;
            
            // Create outline path around character bounds
            if (t < 0.25) { // Top edge
                double progress = t * 4;
                localX = -charWidth/2 + progress * charWidth;
                localY = -charHeight/2;
            } else if (t < 0.5) { // Right edge
                double progress = (t - 0.25) * 4;
                localX = charWidth/2;
                localY = -charHeight/2 + progress * charHeight;
            } else if (t < 0.75) { // Bottom edge
                double progress = (t - 0.5) * 4;
                localX = charWidth/2 - progress * charWidth;
                localY = charHeight/2;
            } else { // Left edge
                double progress = (t - 0.75) * 4;
                localX = -charWidth/2;
                localY = charHeight/2 - progress * charHeight;
            }
            
            // Apply rotation and translation
            double rotatedX = localX * Math.cos(rotation) - localY * Math.sin(rotation);
            double rotatedY = localX * Math.sin(rotation) + localY * Math.cos(rotation);
            
            points.add(new Point2D.Double(x + rotatedX, y + rotatedY));
        }
        
        return points;
    }
    
    /**
     * Generate optimized tool path for continuous motion
     */
    public java.util.List<Point2D.Double> generateOptimizedPath(double dotSpacing) {
        java.util.List<Point2D.Double> pathPoints = generatePathPoints(dotSpacing);
        java.util.List<Point2D.Double> optimizedPath = new java.util.ArrayList<>();
        
        if (pathPoints.isEmpty()) return optimizedPath;
        
        // Sort points by angle to create continuous arc motion
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        
        pathPoints.sort((p1, p2) -> {
            double angle1 = Math.atan2(p1.y - centerY, p1.x - centerX);
            double angle2 = Math.atan2(p2.y - centerY, p2.x - centerX);
            return Double.compare(angle1, angle2);
        });
        
        optimizedPath.addAll(pathPoints);
        return optimizedPath;
    }
    
    /**
     * Export arc letters as G-code for CNC marking
     */
    public String generateGCode() {
        StringBuilder gcode = new StringBuilder();
        java.util.List<Point2D.Double> pathPoints = generatePathPoints(2.0); // 2mm dot spacing
        
        gcode.append("# Arc Letters G-Code - ").append(letters).append("\n");
        gcode.append("# Arc Angle: ").append(String.format("%.1f", arcAngle)).append("°\n");
        gcode.append("# Radius: ").append(String.format("%.2f", radius)).append("\n");
        gcode.append("# Center: (").append(String.format("%.3f", x + width/2.0))
              .append(", ").append(String.format("%.3f", y + height/2.0)).append(")\n");
        gcode.append("# Total Points: ").append(pathPoints.size()).append("\n\n");
        
        gcode.append("G90 ; Absolute positioning\n");
        gcode.append("G21 ; Millimeter units\n");
        gcode.append("G00 Z5.0 ; Raise tool\n\n");
        
        if (!pathPoints.isEmpty()) {
            // Move to first point
            Point2D.Double firstPoint = pathPoints.get(0);
            gcode.append("G00 X").append(String.format("%.3f", firstPoint.x))
                  .append(" Y").append(String.format("%.3f", firstPoint.y)).append(" ; Move to start\n");
            gcode.append("G01 Z-1.0 F300 ; Lower tool\n");
            
            // Mark all points
            for (int i = 1; i < pathPoints.size(); i++) {
                Point2D.Double point = pathPoints.get(i);
                gcode.append("G01 X").append(String.format("%.3f", point.x))
                      .append(" Y").append(String.format("%.3f", point.y))
                      .append(" F500\n");
            }
            
            gcode.append("G00 Z5.0 ; Raise tool\n");
        }
        
        gcode.append("M30 ; Program end\n");
        return gcode.toString();
    }
    
    /**
     * Export coordinate array for dot pin plotting
     */
    public String generateCoordinateArray() {
        java.util.List<Point2D.Double> pathPoints = generatePathPoints(1.5); // 1.5mm spacing for high detail
        StringBuilder coords = new StringBuilder();
        
        coords.append("# Arc Letters - Coordinate Array\n");
        coords.append("# Text: ").append(letters).append("\n");
        coords.append("# Arc Angle: ").append(String.format("%.1f", arcAngle)).append("°\n");
        coords.append("# Radius: ").append(String.format("%.2f", radius)).append("\n");
        coords.append("# Total Coordinates: ").append(pathPoints.size()).append("\n");
        coords.append("# Format: X,Y (one per line)\n\n");
        
        for (Point2D.Double point : pathPoints) {
            coords.append(String.format("%.3f,%.3f\n", point.x, point.y));
        }
        
        return coords.toString();
    }
    
    /**
     * Get arc geometry information for analysis
     */
    public String getArcInfo() {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        double letterRadius = radius * 0.7;
        double arcLength = Math.toRadians(arcAngle) * letterRadius;
        double charSpacing = letters.length() > 1 ? arcLength / (letters.length() - 1) : 0;
        
        StringBuilder info = new StringBuilder();
        info.append("Arc Letters Information:\n");
        info.append("Text: ").append(letters).append("\n");
        info.append("Characters: ").append(letters.length()).append("\n");
        info.append("Center: (").append(String.format("%.1f", centerX))
            .append(", ").append(String.format("%.1f", centerY)).append(")\n");
        info.append("Radius: ").append(String.format("%.1f", radius)).append("\n");
        info.append("Letter Radius: ").append(String.format("%.1f", letterRadius)).append("\n");
        info.append("Start Angle: ").append(String.format("%.1f", startAngle)).append("°\n");
        info.append("Arc Span: ").append(String.format("%.1f", arcAngle)).append("°\n");
        info.append("Arc Length: ").append(String.format("%.1f", arcLength)).append("\n");
        info.append("Character Spacing: ").append(String.format("%.1f", charSpacing)).append("\n");
        info.append("Font: ").append(font.getName()).append(" ").append(font.getSize()).append("pt\n");
        
        return info.toString();
    }
}
