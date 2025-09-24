import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * AvoidPointMark: Comprehensive Avoid Point system with intelligent output modes
 * FINAL OUTPUT: Only inner circle (actual avoid point) 
 * DESIGN MODE: Both circles for customization and visualization
 * Features robust soft-coded configuration with smart defaults
 */
public class AvoidPointMark extends Mark {
    
    // ===============================================================================
    // SOFT-CODED CONFIGURATION SYSTEM (Like RulerMark)
    // ===============================================================================
    
    // Output Mode Control
    private static final boolean FINAL_OUTPUT_ONLY = true;  // TRUE = Only inner circle, FALSE = Both circles
    private static final boolean SHOW_DESIGN_GUIDES = false; // Design guides in final output mode
    
    // Smart Default Values
    private static final double DEFAULT_AVOID_RADIUS = 25.0;        // Main avoid point size
    private static final double DEFAULT_DESIGN_BUFFER = 15.0;       // Buffer space for design
    private static final double DEFAULT_MIN_RADIUS = 8.0;           // Minimum practical size
    private static final double DEFAULT_MAX_RADIUS = 200.0;         // Maximum reasonable size
    
    // Visual Configuration  
    private static final Color AVOID_POINT_COLOR = Color.BLACK;     // Final output color
    private static final Color DESIGN_GUIDE_COLOR = new Color(128, 128, 128, 100); // Light gray design guide
    private static final float AVOID_POINT_THICKNESS = 2.0f;        // Final output line thickness
    private static final float DESIGN_GUIDE_THICKNESS = 1.0f;       // Design guide thickness
    
    // Dot Plotting Configuration
    private static final double DEFAULT_DOT_DIAMETER = 1.5;         // Individual dot size
    private static final double DEFAULT_DOT_SPACING = 2.5;          // Space between dots
    private static final int DEFAULT_CIRCLE_RESOLUTION = 72;        // Smoothness (5-degree increments)
    
    // Validation Limits
    private static final double MIN_DOT_DIAMETER = 0.5;
    private static final double MAX_DOT_DIAMETER = 5.0;
    private static final double MIN_DOT_SPACING = 1.0;
    private static final double MAX_DOT_SPACING = 10.0;
    private static final int MIN_RESOLUTION = 16;
    private static final int MAX_RESOLUTION = 360;
    
    // ===============================================================================
    // INTELLIGENT AVOID POINT PROPERTIES
    // ===============================================================================
    
    private double avoidRadius;           // The actual avoid point radius (final output)
    private double designRadius;          // Outer design/customization radius 
    private double designBuffer;          // Buffer space between avoid and design circles
    private boolean showDots;             // Dot representation vs vector
    private double dotDiameter;           // Individual dot size
    private double dotSpacing;            // Space between dots
    private int circleResolution;         // Number of points for smooth circles
    private boolean showDesignGuides;     // Show design guides in final output mode
    
    /**
     * Smart Constructor with Intelligent Defaults
     * Automatically configures optimal avoid point settings
     */
    public AvoidPointMark(int x, int y) {
        super(x, y);
        
        // Initialize with smart defaults
        this.avoidRadius = DEFAULT_AVOID_RADIUS;
        this.designBuffer = DEFAULT_DESIGN_BUFFER;
        this.designRadius = avoidRadius + designBuffer;
        
        // Dot plotting configuration
        this.showDots = false;  // Default to vector mode
        this.dotDiameter = DEFAULT_DOT_DIAMETER;
        this.dotSpacing = DEFAULT_DOT_SPACING;
        this.circleResolution = DEFAULT_CIRCLE_RESOLUTION;
        
        // Design guide configuration
        this.showDesignGuides = SHOW_DESIGN_GUIDES;
        
        // Smart sizing based on avoid point requirements
        updateIntelligentBounds();
        
        System.out.println("🎯 Smart AvoidPoint created:");
        System.out.println("   Avoid Radius: " + avoidRadius + "px");
        System.out.println("   Design Radius: " + designRadius + "px"); 
        System.out.println("   Output Mode: " + (FINAL_OUTPUT_ONLY ? "FINAL OUTPUT ONLY" : "DESIGN MODE"));
        System.out.println("   Final output shows: " + (FINAL_OUTPUT_ONLY ? "Inner circle only" : "Both circles"));
    }
    
    @Override
    public void draw(Graphics2D g2d, boolean isSelected) {
        // Save original settings
        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();
        RenderingHints originalHints = g2d.getRenderingHints();
        
        // Enable antialiasing for smooth circles
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // Draw based on output mode
        if (FINAL_OUTPUT_ONLY) {
            drawFinalOutputMode(g2d, isSelected);
        } else {
            drawDesignMode(g2d, isSelected);
        }
        
        // Draw selection handles if selected
        if (isSelected) {
            drawIntelligentSelectionHandles(g2d);
        }
        
        // Restore original settings
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
        g2d.setRenderingHints(originalHints);
    }
    
    /**
     * Final Output Mode: Only show the actual avoid point (inner circle)
     */
    private void drawFinalOutputMode(Graphics2D g2d, boolean isSelected) {
        double centerX = this.x + designRadius;
        double centerY = this.y + designRadius;
        
        // Draw only the actual avoid point (inner circle)
        g2d.setColor(AVOID_POINT_COLOR);
        g2d.setStroke(new BasicStroke(AVOID_POINT_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        if (showDots) {
            drawAvoidPointDots(g2d, centerX, centerY, avoidRadius);
        } else {
            drawAvoidPointVector(g2d, centerX, centerY, avoidRadius);
        }
        
        // Optionally show design guides (very subtle)
        if (showDesignGuides || isSelected) {
            g2d.setColor(DESIGN_GUIDE_COLOR);
            g2d.setStroke(new BasicStroke(DESIGN_GUIDE_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawAvoidPointVector(g2d, centerX, centerY, designRadius);
        }
    }
    
    /**
     * Design Mode: Show both circles for customization
     */
    private void drawDesignMode(Graphics2D g2d, boolean isSelected) {
        double centerX = this.x + designRadius;
        double centerY = this.y + designRadius;
        
        // Draw design/outer circle (for customization)
        g2d.setColor(DESIGN_GUIDE_COLOR);
        g2d.setStroke(new BasicStroke(DESIGN_GUIDE_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawAvoidPointVector(g2d, centerX, centerY, designRadius);
        
        // Draw actual avoid point (inner circle)
        g2d.setColor(AVOID_POINT_COLOR);
        g2d.setStroke(new BasicStroke(AVOID_POINT_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        if (showDots) {
            drawAvoidPointDots(g2d, centerX, centerY, avoidRadius);
        } else {
            drawAvoidPointVector(g2d, centerX, centerY, avoidRadius);
        }
    }
    
    /**
     * Draw avoid point as vector circle (smooth line)
     */
    private void drawAvoidPointVector(Graphics2D g2d, double centerX, double centerY, double radius) {
        double diameter = radius * 2;
        Ellipse2D circle = new Ellipse2D.Double(
            centerX - radius, 
            centerY - radius, 
            diameter, 
            diameter
        );
        g2d.draw(circle);
    }
    
    /**
     * Draw avoid point as individual dots (dot pin plotting)
     */
    private void drawAvoidPointDots(Graphics2D g2d, double centerX, double centerY, double radius) {
        List<Point2D.Double> dots = generateCircleDotPoints(centerX, centerY, radius);
        
        // Draw dots with current color (set by calling method)
        for (Point2D.Double dot : dots) {
            double dotX = dot.x - dotDiameter / 2;
            double dotY = dot.y - dotDiameter / 2;
            g2d.fill(new Ellipse2D.Double(dotX, dotY, dotDiameter, dotDiameter));
        }
    }
    
    /**
     * Generate all dots for final output (only avoid point in FINAL_OUTPUT_ONLY mode)
     */
    private List<Point2D.Double> generateAllOutputDots() {
        List<Point2D.Double> dots = new ArrayList<>();
        double centerX = this.x + designRadius;
        double centerY = this.y + designRadius;
        
        if (FINAL_OUTPUT_ONLY) {
            // Final output: Only avoid point dots
            dots.addAll(generateCircleDotPoints(centerX, centerY, avoidRadius));
        } else {
            // Design mode: Both circles
            dots.addAll(generateCircleDotPoints(centerX, centerY, designRadius));
            dots.addAll(generateCircleDotPoints(centerX, centerY, avoidRadius));
        }
        
        return dots;
    }
    
    private List<Point2D.Double> generateCircleDotPoints(double centerX, double centerY, double radius) {
        List<Point2D.Double> dots = new ArrayList<>();
        
        // Calculate number of dots based on circumference and dot spacing
        double circumference = 2 * Math.PI * radius;
        int numDots = (int) Math.ceil(circumference / dotSpacing);
        
        for (int i = 0; i < numDots; i++) {
            double angle = (2 * Math.PI * i) / numDots;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            dots.add(new Point2D.Double(x, y));
        }
        
        return dots;
    }
    
    @Override
    public boolean contains(int x, int y) {
        Rectangle2D bounds = getBounds();
        return bounds.contains(x, y);
    }
    
    public Rectangle2D getBounds() {
        double diameter = designRadius * 2;
        return new Rectangle2D.Double(this.x, this.y, diameter, diameter);
    }
    
    /**
     * Intelligent selection handles with clear avoid point indication
     */
    private void drawIntelligentSelectionHandles(Graphics2D g2d) {
        Rectangle2D bounds = getBounds();
        double centerX = this.x + designRadius;
        double centerY = this.y + designRadius;
        
        // Draw subtle background fill
        g2d.setColor(new Color(173, 216, 230, 30)); // Very light blue background
        g2d.fill(bounds);
        
        // Draw bounding rectangle outline  
        g2d.setColor(new Color(0, 100, 200, 150));
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(bounds);
        
        // Draw corner resize handles
        int handleSize = 8;
        int x1 = (int) bounds.getX();
        int y1 = (int) bounds.getY();
        int x2 = (int) (bounds.getX() + bounds.getWidth());
        int y2 = (int) (bounds.getY() + bounds.getHeight());
        
        g2d.setColor(new Color(0, 100, 200));
        // Corner handles for resizing
        g2d.fillRect(x1 - handleSize/2, y1 - handleSize/2, handleSize, handleSize);
        g2d.fillRect(x2 - handleSize/2, y1 - handleSize/2, handleSize, handleSize);
        g2d.fillRect(x1 - handleSize/2, y2 - handleSize/2, handleSize, handleSize);
        g2d.fillRect(x2 - handleSize/2, y2 - handleSize/2, handleSize, handleSize);
        
        // Draw avoid point center (primary reference)
        g2d.setColor(Color.RED);
        g2d.fillOval((int)centerX - 4, (int)centerY - 4, 8, 8);
        
        // Draw avoid radius indicator
        g2d.setColor(new Color(255, 0, 0, 100));
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                                     1.0f, new float[]{3.0f, 3.0f}, 0.0f)); // Dashed line
        g2d.drawOval((int)(centerX - avoidRadius), (int)(centerY - avoidRadius), 
                    (int)(avoidRadius * 2), (int)(avoidRadius * 2));
        
        // Selection info label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        String info = String.format("Avoid: %.1fpx", avoidRadius);
        g2d.drawString(info, (int)(centerX - 25), (int)(centerY - avoidRadius - 8));
    }
    
    @Override
    public void resizeTo(int mx, int my) {
        if (resizing) {
            // Calculate new size maintaining circular aspect ratio
            double newSize = Math.max(DEFAULT_MIN_RADIUS * 2, Math.max(mx - this.x, my - this.y));
            double newDesignRadius = Math.min(DEFAULT_MAX_RADIUS, newSize / 2);
            
            // Intelligent avoid radius calculation (proportional)
            double ratio = avoidRadius / designRadius; // Maintain proportion
            setDesignRadius(newDesignRadius);
            setAvoidRadius(newDesignRadius * ratio);
            
            // Update Mark base class dimensions
            this.width = (int) (designRadius * 2);
            this.height = (int) (designRadius * 2);
            
            updateIntelligentBounds();
            
            System.out.println("🎯 AvoidPoint resized - Avoid: " + String.format("%.1f", avoidRadius) + 
                             "px, Design: " + String.format("%.1f", designRadius) + "px");
        }
    }
    
    /**
     * Intelligent bounds update with validation
     */
    private void updateIntelligentBounds() {
        Rectangle2D bounds = getBounds();
        this.width = (int) bounds.getWidth();
        this.height = (int) bounds.getHeight();
        
        // Validate bounds are reasonable
        if (this.width < DEFAULT_MIN_RADIUS * 2 || this.height < DEFAULT_MIN_RADIUS * 2) {
            System.out.println("⚠️  AvoidPoint too small, adjusting to minimum size");
            setDesignRadius(DEFAULT_MIN_RADIUS);
            setAvoidRadius(DEFAULT_MIN_RADIUS * 0.6);
        }
    }
    
    // ===============================================================================
    // INTELLIGENT PROPERTY ACCESSORS WITH VALIDATION
    // ===============================================================================
    
    /**
     * Avoid Point Radius (The actual avoid area - final output)
     */
    public double getAvoidRadius() { return avoidRadius; }
    public void setAvoidRadius(double avoidRadius) { 
        this.avoidRadius = Math.max(DEFAULT_MIN_RADIUS, Math.min(DEFAULT_MAX_RADIUS, avoidRadius));
        
        // Ensure design radius is larger than avoid radius
        if (this.avoidRadius >= designRadius) {
            this.designRadius = this.avoidRadius + DEFAULT_DESIGN_BUFFER;
        }
        
        updateIntelligentBounds();
        System.out.println("🎯 Avoid radius set to: " + String.format("%.1f", this.avoidRadius) + "px");
    }
    
    /**
     * Design Radius (Outer customization boundary)  
     */
    public double getDesignRadius() { return designRadius; }
    public void setDesignRadius(double designRadius) { 
        this.designRadius = Math.max(DEFAULT_MIN_RADIUS + 5, Math.min(DEFAULT_MAX_RADIUS, designRadius));
        
        // Ensure avoid radius is smaller than design radius
        if (this.avoidRadius >= this.designRadius) {
            this.avoidRadius = Math.max(DEFAULT_MIN_RADIUS, this.designRadius - DEFAULT_DESIGN_BUFFER);
        }
        
        updateIntelligentBounds();
    }
    
    /**
     * Design Buffer (Space between avoid and design circles)
     */
    public double getDesignBuffer() { return designBuffer; }
    public void setDesignBuffer(double designBuffer) { 
        this.designBuffer = Math.max(2.0, Math.min(50.0, designBuffer));
        this.designRadius = this.avoidRadius + this.designBuffer;
        updateIntelligentBounds();
    }
    
    /**
     * Dot Plotting Configuration
     */
    public boolean isShowDots() { return showDots; }
    public void setShowDots(boolean showDots) { 
        this.showDots = showDots; 
        System.out.println("🎯 AvoidPoint mode: " + (showDots ? "Dot Plotting" : "Vector Output"));
    }
    
    public double getDotDiameter() { return dotDiameter; }
    public void setDotDiameter(double dotDiameter) { 
        this.dotDiameter = Math.max(MIN_DOT_DIAMETER, Math.min(MAX_DOT_DIAMETER, dotDiameter)); 
    }
    
    public double getDotSpacing() { return dotSpacing; }
    public void setDotSpacing(double dotSpacing) { 
        this.dotSpacing = Math.max(MIN_DOT_SPACING, Math.min(MAX_DOT_SPACING, dotSpacing)); 
    }
    
    public int getCircleResolution() { return circleResolution; }
    public void setCircleResolution(int circleResolution) { 
        this.circleResolution = Math.max(MIN_RESOLUTION, Math.min(MAX_RESOLUTION, circleResolution)); 
    }
    
    /**
     * Design Guide Configuration
     */
    public boolean isShowDesignGuides() { return showDesignGuides; }
    public void setShowDesignGuides(boolean showDesignGuides) { 
        this.showDesignGuides = showDesignGuides; 
    }
    
    // ===============================================================================
    // INTELLIGENT OUTPUT GENERATION (FINAL OUTPUT ONLY)
    // ===============================================================================
    
    /**
     * Generate G-code for CNC/dot pin marking (FINAL OUTPUT ONLY)
     */
    public String generateGCode() {
        StringBuilder gcode = new StringBuilder();
        double centerX = this.x + designRadius;
        double centerY = this.y + designRadius;
        
        gcode.append("# Avoid Point - Final Output G-Code\n");
        gcode.append("# Center: (").append(String.format("%.3f", centerX)).append(", ").append(String.format("%.3f", centerY)).append(")\n");
        gcode.append("# Avoid Radius: ").append(String.format("%.3f", avoidRadius)).append("\n");
        gcode.append("# Output Mode: ").append(FINAL_OUTPUT_ONLY ? "FINAL OUTPUT ONLY" : "DESIGN MODE").append("\n\n");
        
        if (FINAL_OUTPUT_ONLY) {
            // Final output: Only avoid point circle
            gcode.append("# Avoid Point Circle (Final Output)\n");
            gcode.append("G00 X").append(String.format("%.3f", centerX + avoidRadius)).append(" Y").append(String.format("%.3f", centerY)).append(" ; Move to start\n");
            gcode.append("G01 Z-1.0 ; Lower tool\n");
            gcode.append("G02 X").append(String.format("%.3f", centerX + avoidRadius)).append(" Y").append(String.format("%.3f", centerY))
                   .append(" I").append(String.format("%.3f", -avoidRadius)).append(" J0 ; Clockwise circle\n");
            gcode.append("G00 Z1.0 ; Raise tool\n");
        } else {
            // Design mode: Both circles
            gcode.append("# Design Circle (Outer)\n");
            gcode.append("G00 X").append(String.format("%.3f", centerX + designRadius)).append(" Y").append(String.format("%.3f", centerY)).append(" ; Move to start\n");
            gcode.append("G01 Z-1.0 ; Lower tool\n");
            gcode.append("G02 X").append(String.format("%.3f", centerX + designRadius)).append(" Y").append(String.format("%.3f", centerY))
                   .append(" I").append(String.format("%.3f", -designRadius)).append(" J0 ; Clockwise circle\n");
            gcode.append("G00 Z1.0 ; Raise tool\n\n");
            
            gcode.append("# Avoid Point Circle (Inner)\n");
            gcode.append("G00 X").append(String.format("%.3f", centerX + avoidRadius)).append(" Y").append(String.format("%.3f", centerY)).append(" ; Move to start\n");
            gcode.append("G01 Z-1.0 ; Lower tool\n");
            gcode.append("G02 X").append(String.format("%.3f", centerX + avoidRadius)).append(" Y").append(String.format("%.3f", centerY))
                   .append(" I").append(String.format("%.3f", -avoidRadius)).append(" J0 ; Clockwise circle\n");
            gcode.append("G00 Z1.0 ; Raise tool\n");
        }
        
        return gcode.toString();
    }
    
    /**
     * Generate coordinate array for dot pin plotting (FINAL OUTPUT ONLY)
     */
    public String generateCoordinateArray() {
        StringBuilder coords = new StringBuilder();
        List<Point2D.Double> allDots = generateAllOutputDots();
        
        coords.append("# Avoid Point - Final Output Dot Coordinates\n");
        coords.append("# Avoid Radius: ").append(String.format("%.3f", avoidRadius)).append("\n");
        coords.append("# Output Mode: ").append(FINAL_OUTPUT_ONLY ? "FINAL OUTPUT ONLY" : "DESIGN MODE").append("\n");
        coords.append("# Total Dots: ").append(allDots.size()).append("\n");
        coords.append("# Format: X,Y (one per line)\n\n");
        
        for (Point2D.Double dot : allDots) {
            coords.append(String.format("%.3f,%.3f\n", dot.x, dot.y));
        }
        
        return coords.toString();
    }
    
    /**
     * Generate optimized path for continuous motion (FINAL OUTPUT ONLY)
     */
    public String generateOptimizedPath() {
        StringBuilder path = new StringBuilder();
        double centerX = this.x + designRadius;
        double centerY = this.y + designRadius;
        
        path.append("# Avoid Point - Optimized Final Output Path\n");
        path.append("# Continuous motion with minimal tool lift\n");
        path.append("# Output Mode: ").append(FINAL_OUTPUT_ONLY ? "FINAL OUTPUT ONLY" : "DESIGN MODE").append("\n\n");
        
        if (FINAL_OUTPUT_ONLY) {
            // Final output: Only avoid point path
            path.append("# Avoid Point Circle Path (Final Output)\n");
            path.append("MOVE_TO ").append(String.format("%.3f %.3f", centerX + avoidRadius, centerY)).append("\n");
            path.append("TOOL_DOWN\n");
            
            // Generate smooth avoid point circle
            for (int i = 1; i <= circleResolution; i++) {
                double angle = (2 * Math.PI * i) / circleResolution;
                double x = centerX + avoidRadius * Math.cos(angle);
                double y = centerY + avoidRadius * Math.sin(angle);
                path.append("LINE_TO ").append(String.format("%.3f %.3f", x, y)).append("\n");
            }
            
            path.append("TOOL_UP\n");
        } else {
            // Design mode: Both circles
            path.append("# Design Circle Path (Outer)\n");
            path.append("MOVE_TO ").append(String.format("%.3f %.3f", centerX + designRadius, centerY)).append("\n");
            path.append("TOOL_DOWN\n");
            
            for (int i = 1; i <= circleResolution; i++) {
                double angle = (2 * Math.PI * i) / circleResolution;
                double x = centerX + designRadius * Math.cos(angle);
                double y = centerY + designRadius * Math.sin(angle);
                path.append("LINE_TO ").append(String.format("%.3f %.3f", x, y)).append("\n");
            }
            
            path.append("TOOL_UP\n\n");
            
            path.append("# Avoid Point Circle Path (Inner)\n");
            path.append("MOVE_TO ").append(String.format("%.3f %.3f", centerX + avoidRadius, centerY)).append("\n");
            path.append("TOOL_DOWN\n");
            
            for (int i = 1; i <= circleResolution; i++) {
                double angle = (2 * Math.PI * i) / circleResolution;
                double x = centerX + avoidRadius * Math.cos(angle);
                double y = centerY + avoidRadius * Math.sin(angle);
                path.append("LINE_TO ").append(String.format("%.3f %.3f", x, y)).append("\n");
            }
            
            path.append("TOOL_UP\n");
        }
        
        path.append("# Path Complete\n");
        return path.toString();
    }
    
    // ===============================================================================
    // INTELLIGENT ANALYSIS AND STATISTICS
    // ===============================================================================
    
    /**
     * Get estimated machining time (FINAL OUTPUT ONLY)
     */
    public double getEstimatedMachiningTime(double feedRate) {
        double totalDistance;
        
        if (FINAL_OUTPUT_ONLY) {
            // Only avoid point circumference
            totalDistance = 2 * Math.PI * avoidRadius;
        } else {
            // Both circles
            double avoidCircumference = 2 * Math.PI * avoidRadius;
            double designCircumference = 2 * Math.PI * designRadius;
            totalDistance = avoidCircumference + designCircumference;
        }
        
        // Add time for tool movements (approximate)
        double moveTime = FINAL_OUTPUT_ONLY ? 3.0 : 6.0; // seconds for positioning
        double machineTime = totalDistance / feedRate * 60; // minutes
        
        return machineTime + (moveTime / 60);
    }
    
    /**
     * Get total dot count for planning (FINAL OUTPUT ONLY)
     */
    public int getTotalDotCount() {
        return generateAllOutputDots().size();
    }
    
    /**
     * Get avoid point specifications for documentation
     */
    public String getAvoidPointSpecs() {
        StringBuilder specs = new StringBuilder();
        specs.append("🎯 AvoidPoint Specifications:\n");
        specs.append("   Output Mode: ").append(FINAL_OUTPUT_ONLY ? "FINAL OUTPUT ONLY" : "DESIGN MODE").append("\n");
        specs.append("   Avoid Radius: ").append(String.format("%.1f", avoidRadius)).append("px\n");
        specs.append("   Design Radius: ").append(String.format("%.1f", designRadius)).append("px\n");
        specs.append("   Buffer Space: ").append(String.format("%.1f", designBuffer)).append("px\n");
        specs.append("   Output Type: ").append(showDots ? "Dot Plotting" : "Vector").append("\n");
        
        if (showDots) {
            specs.append("   Dot Diameter: ").append(String.format("%.1f", dotDiameter)).append("px\n");
            specs.append("   Dot Spacing: ").append(String.format("%.1f", dotSpacing)).append("px\n");
            specs.append("   Total Dots: ").append(getTotalDotCount()).append("\n");
        }
        
        specs.append("   Resolution: ").append(circleResolution).append(" points\n");
        
        return specs.toString();
    }
    
    /**
     * Validate current configuration
     */
    public boolean isValidConfiguration() {
        return avoidRadius >= DEFAULT_MIN_RADIUS && 
               avoidRadius <= DEFAULT_MAX_RADIUS &&
               designRadius > avoidRadius &&
               designBuffer >= 2.0;
    }
}
