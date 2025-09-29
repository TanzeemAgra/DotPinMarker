/**
 * RulerMark: Comprehensive Precision Measurement Ruler for Dot Pin Marking
 * Advanced ruler system with extensive customization options and soft-coded parameters
 * 
 * Features:
 * - Multiple scale line types (Major, Middle, Minor)
 * - Configurable dimensions and intervals
 * - Number display options with positioning
 * - Radius and span length controls
 * - Horizontal and vertical orientations
 * - Professional measurement accuracy
 * 
 * @author Rugrel DotPin Framework
 */

import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class RulerMark extends Mark {
    
    // ===============================================================================
    // SOFT-CODED RULER CONFIGURATION CONSTANTS
    // ===============================================================================
    
    // Default ruler dimensions and properties
    private static final int DEFAULT_LENGTH = 300;
    private static final int DEFAULT_HEIGHT = 40;
    private static final int DEFAULT_SCALE_SIZE = 10;        // Major scale interval
    private static final int DEFAULT_SCALE_VALUE = 1;        // Value per unit
    private static final int DEFAULT_START_VALUE = 0;        // Starting number
    private static final double DEFAULT_RADIUS = 0.0;       // Corner radius
    private static final int DEFAULT_SPAN_LENGTH = 100;     // Total measurement span
    
    // Scale line height ratios (relative to ruler height)
    private static final double MAJOR_SCALE_HEIGHT_RATIO = 0.8;    // 80% of height
    private static final double MIDDLE_SCALE_HEIGHT_RATIO = 0.6;   // 60% of height  
    private static final double MINOR_SCALE_HEIGHT_RATIO = 0.4;    // 40% of height
    
    // Text and spacing configuration
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int TEXT_OFFSET_FROM_SCALE = 3;
    private static final Color DEFAULT_RULER_COLOR = Color.BLACK;
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(248, 248, 248);
    
    // Scale subdivision ratios
    private static final int MIDDLE_SCALE_SUBDIVISIONS = 2;  // Middle marks between major marks
    private static final int MINOR_SCALE_SUBDIVISIONS = 5;   // Minor marks between middle marks
    
    // ===============================================================================
    // RULER PROPERTIES - ALL CUSTOMIZABLE
    // ===============================================================================
    
    // Core dimensions
    private int rulerLength = DEFAULT_LENGTH;
    private int rulerHeight = DEFAULT_HEIGHT;
    private int scaleSize = DEFAULT_SCALE_SIZE;              // Interval between major marks
    private int scaleValue = DEFAULT_SCALE_VALUE;            // Numerical value increment
    private int startValue = DEFAULT_START_VALUE;            // Starting number
    private double radius = DEFAULT_RADIUS;                  // Corner radius for rounded ruler
    private int spanLength = DEFAULT_SPAN_LENGTH;            // Total measurement range
    
    // Scale line heights (customizable)
    private int majorScaleHeight = (int)(DEFAULT_HEIGHT * MAJOR_SCALE_HEIGHT_RATIO);
    private int middleScaleHeight = (int)(DEFAULT_HEIGHT * MIDDLE_SCALE_HEIGHT_RATIO);
    private int shortScaleHeight = (int)(DEFAULT_HEIGHT * MINOR_SCALE_HEIGHT_RATIO);
    
    // Display options
    private boolean showNumbers = true;                      // Show major scale numbers
    private boolean showMiddleNumbers = false;               // Show middle scale numbers
    private boolean showMinorMarks = true;                   // Show minor scale marks
    private boolean showMiddleMarks = true;                  // Show middle scale marks
    private boolean showMajorMarks = true;                   // Show major scale marks
    
    // ===============================================================================
    // TEXT CUSTOMIZATION PROPERTIES - SIMILAR TO TEXTMARK
    // ===============================================================================
    
    // Font properties with soft-coded defaults
    private Font numberFont = new Font("Arial", Font.BOLD, DEFAULT_TEXT_SIZE);
    private String fontFamily = "Arial";                     // Font family name
    private int fontSize = DEFAULT_TEXT_SIZE;                // Font size in points
    private int fontStyle = Font.BOLD;                       // Font style (PLAIN, BOLD, ITALIC, BOLD+ITALIC)
    
    // Text spacing and positioning
    private double characterWidth = 1.0;                     // Character spacing multiplier
    private double lineSpacing = 1.0;                        // Line spacing multiplier
    private int textOffsetX = 0;                             // Horizontal text offset
    private int textOffsetY = TEXT_OFFSET_FROM_SCALE;        // Vertical text offset from scale
    
    // Text alignment options
    public enum TextAlignment {
        LEFT, CENTER, RIGHT, TOP, MIDDLE, BOTTOM
    }
    private TextAlignment horizontalAlignment = TextAlignment.CENTER;  // Number alignment
    private TextAlignment verticalAlignment = TextAlignment.BOTTOM;    // Vertical text position
    
    // Text appearance
    private Color textColor = DEFAULT_TEXT_COLOR;            // Number text color
    private Color textBackgroundColor = null;                // Text background (null = transparent)
    private boolean showTextBackground = false;              // Enable text background
    private boolean textBold = true;                         // Bold text
    private boolean textItalic = false;                      // Italic text
    private boolean textUnderline = false;                   // Underlined text
    
    // Advanced text options
    private double textRotation = 0.0;                       // Text rotation in degrees
    private boolean antiAliasText = true;                    // Text anti-aliasing
    private int textShadowOffset = 0;                        // Shadow offset (0 = no shadow)
    private Color textShadowColor = Color.GRAY;              // Shadow color
    
    // Orientation and styling
    private boolean isVertical = false;                      // Horizontal by default
    private Color rulerColor = DEFAULT_RULER_COLOR;
    private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int textSize = DEFAULT_TEXT_SIZE;
    private boolean showBackground = true;                   // Show ruler background
    
    // Advanced options
    private String unitSuffix = "";                          // Unit suffix (mm, cm, in, etc.)
    private boolean reverseNumbers = false;                  // Reverse number order
    private int numberOffset = 0;                            // Offset for number positioning
    private double lineThickness = 1.0;                     // Scale line thickness
    
    // ===============================================================================
    // INTELLIGENT DRAG AND DROP SYSTEM - SOFT CODED PARAMETERS
    // ===============================================================================
    
    // Control handle types for different drag operations
    public enum RulerControlType {
        POSITION,        // Blue circle - Move entire ruler
        LENGTH,          // Green rectangle - Adjust ruler length  
        HEIGHT,          // Orange diamond - Adjust ruler height
        SCALE_SIZE,      // Purple triangle - Adjust scale interval
        NUMBER_OFFSET    // Red hexagon - Adjust number positioning
    }
    
    // Soft-coded drag control parameters - SIMPLIFIED FOR BETTER UX
    private static final int CONTROL_HANDLE_SIZE = 8;
    private static final int CONTROL_DETECTION_RADIUS = 12;
    private static final double DRAG_SENSITIVITY = 1.0;
    private static final int MIN_DRAG_DISTANCE = 3;
    private static final Color POSITION_HANDLE_COLOR = new Color(0, 120, 255);     // Blue - Move ruler
    private static final Color LENGTH_HANDLE_COLOR = new Color(0, 200, 100);       // Green - Resize length
    private static final Color HEIGHT_HANDLE_COLOR = new Color(255, 140, 0);       // Orange - Not used (too complex)
    private static final Color SCALE_HANDLE_COLOR = new Color(150, 50, 200);       // Purple - Not used (too complex)
    private static final Color NUMBER_HANDLE_COLOR = new Color(255, 60, 60);       // Red - Not used (too complex)
    
    // Simplified control system - only show essential handles
    private static final boolean SHOW_SIMPLE_HANDLES_ONLY = true;
    
    // Drag state management
    private RulerControlType activeDragType = null;
    private boolean isIntelligentDragging = false;  // Renamed to avoid conflict with base class
    private int dragStartX = 0, dragStartY = 0;
    private int originalLength = 0, originalHeight = 0;
    private int originalScaleSize = 0, originalNumberOffset = 0;
    private Point dragOffset = new Point(0, 0);
    
    // ===============================================================================
    // CONSTRUCTOR AND INITIALIZATION
    // ===============================================================================
    
    public RulerMark(int x, int y) {
        super(x, y);
        this.width = rulerLength;
        this.height = rulerHeight;
        updateScaleHeights();
        
        System.out.println("📏 RulerMark created with soft-coded configuration:");
        System.out.println("   Length: " + rulerLength + "px, Height: " + rulerHeight + "px");
        System.out.println("   Scale Size: " + scaleSize + ", Scale Value: " + scaleValue);
        System.out.println("   Start Value: " + startValue + ", Span Length: " + spanLength);
        System.out.println("   Major/Middle/Minor Heights: " + majorScaleHeight + "/" + middleScaleHeight + "/" + shortScaleHeight);
    }
    
    /**
     * Updates scale line heights based on current ruler height
     */
    private void updateScaleHeights() {
        majorScaleHeight = (int)(rulerHeight * MAJOR_SCALE_HEIGHT_RATIO);
        middleScaleHeight = (int)(rulerHeight * MIDDLE_SCALE_HEIGHT_RATIO);
        shortScaleHeight = (int)(rulerHeight * MINOR_SCALE_HEIGHT_RATIO);
    }
    
    // ===============================================================================
    // ADVANCED RULER DRAWING SYSTEM
    // ===============================================================================
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Set high-quality rendering
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Save original transform for rotation
        AffineTransform originalTransform = g.getTransform();
        
        try {
            // Apply rotation if vertical
            if (isVertical) {
                g.rotate(Math.PI / 2, x + width / 2, y + height / 2);
            }
            
            // Draw ruler background
            if (showBackground) {
                drawRulerBackground(g);
            }
            
            // Draw scale markings
            if (showMajorMarks) drawMajorScaleMarks(g);
            if (showMiddleMarks) drawMiddleScaleMarks(g);
            if (showMinorMarks) drawMinorScaleMarks(g);
            
            // Draw numbers
            if (showNumbers) drawMajorNumbers(g);
            if (showMiddleNumbers) drawMiddleNumbers(g);
            
            // Draw border if selected
            if (isSelected) {
                drawSelectionBorder(g);
            }
            
        } finally {
            // Restore original transform
            g.setTransform(originalTransform);
        }
        
        // Draw resize handles if selected
        if (isSelected) {
            drawResizeHandles(g);
        }
    }
    
    /**
     * Draws the ruler background with optional rounded corners
     */
    private void drawRulerBackground(Graphics2D g) {
        g.setColor(backgroundColor);
        
        if (radius > 0) {
            g.fillRoundRect(x, y, width, height, (int)(radius * 2), (int)(radius * 2));
        } else {
            g.fillRect(x, y, width, height);
        }
        
        // Draw border
        g.setColor(rulerColor);
        g.setStroke(new BasicStroke((float)lineThickness));
        
        if (radius > 0) {
            g.drawRoundRect(x, y, width, height, (int)(radius * 2), (int)(radius * 2));
        } else {
            g.drawRect(x, y, width, height);
        }
    }
    
    /**
     * Draws major scale marks (primary intervals)
     */
    private void drawMajorScaleMarks(Graphics2D g) {
        g.setColor(rulerColor);
        g.setStroke(new BasicStroke((float)(lineThickness * 1.5))); // Thicker for major marks
        
        double pixelsPerUnit = (double) rulerLength / spanLength;
        
        for (int i = 0; i <= spanLength; i += scaleSize) {
            int markX = x + (int)(i * pixelsPerUnit);
            int markTopY = y + height - majorScaleHeight;
            int markBottomY = y + height;
            
            g.drawLine(markX, markTopY, markX, markBottomY);
        }
    }
    
    /**
     * Draws middle scale marks (intermediate intervals)
     */
    private void drawMiddleScaleMarks(Graphics2D g) {
        g.setColor(rulerColor);
        g.setStroke(new BasicStroke((float)lineThickness));
        
        double pixelsPerUnit = (double) rulerLength / spanLength;
        int middleInterval = scaleSize / MIDDLE_SCALE_SUBDIVISIONS;
        
        for (int i = middleInterval; i <= spanLength; i += middleInterval) {
            // Skip major mark positions
            if (i % scaleSize != 0) {
                int markX = x + (int)(i * pixelsPerUnit);
                int markTopY = y + height - middleScaleHeight;
                int markBottomY = y + height;
                
                g.drawLine(markX, markTopY, markX, markBottomY);
            }
        }
    }
    
    /**
     * Draws minor scale marks (smallest intervals)
     */
    private void drawMinorScaleMarks(Graphics2D g) {
        g.setColor(rulerColor);
        g.setStroke(new BasicStroke((float)(lineThickness * 0.8))); // Thinner for minor marks
        
        double pixelsPerUnit = (double) rulerLength / spanLength;
        int minorInterval = scaleSize / (MIDDLE_SCALE_SUBDIVISIONS * MINOR_SCALE_SUBDIVISIONS);
        
        for (int i = minorInterval; i <= spanLength; i += minorInterval) {
            // Skip major and middle mark positions
            int middleInterval = scaleSize / MIDDLE_SCALE_SUBDIVISIONS;
            if (i % scaleSize != 0 && i % middleInterval != 0) {
                int markX = x + (int)(i * pixelsPerUnit);
                int markTopY = y + height - shortScaleHeight;
                int markBottomY = y + height;
                
                g.drawLine(markX, markTopY, markX, markBottomY);
            }
        }
    }
    
    /**
     * Draws major scale numbers with comprehensive text customization
     */
    private void drawMajorNumbers(Graphics2D g) {
        // Apply text anti-aliasing setting
        if (antiAliasText) {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        
        // Set font with current customization
        g.setFont(numberFont);
        FontMetrics fm = g.getFontMetrics();
        
        double pixelsPerUnit = (double) rulerLength / spanLength;
        
        for (int i = 0; i <= spanLength; i += scaleSize) {
            int value = reverseNumbers ? (spanLength - i) * scaleValue + startValue : i * scaleValue + startValue;
            String numberText = value + unitSuffix;
            
            int markX = x + (int)(i * pixelsPerUnit);
            
            // Calculate text position with alignment and offsets
            int textWidth = fm.stringWidth(numberText);
            int textX = calculateTextX(markX, textWidth, horizontalAlignment) + textOffsetX;
            int textY = calculateTextY() + textOffsetY;
            
            // Draw text background if enabled
            if (showTextBackground && textBackgroundColor != null) {
                g.setColor(textBackgroundColor);
                int bgPadding = 2;
                g.fillRect(textX - bgPadding, textY - fm.getAscent() - bgPadding, 
                          textWidth + 2 * bgPadding, fm.getHeight() + 2 * bgPadding);
            }
            
            // Draw text shadow if enabled
            if (textShadowOffset > 0) {
                g.setColor(textShadowColor);
                drawCustomText(g, numberText, textX + textShadowOffset, textY + textShadowOffset);
            }
            
            // Draw main text
            g.setColor(textColor);
            drawCustomText(g, numberText, textX, textY);
        }
    }
    
    /**
     * Draws middle scale numbers with comprehensive text customization
     */
    private void drawMiddleNumbers(Graphics2D g) {
        // Apply text anti-aliasing setting
        if (antiAliasText) {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        
        // Use slightly smaller font for middle numbers
        Font middleFont = new Font(fontFamily, fontStyle, Math.max(6, fontSize - 2));
        g.setFont(middleFont);
        FontMetrics fm = g.getFontMetrics();
        
        double pixelsPerUnit = (double) rulerLength / spanLength;
        int middleInterval = scaleSize / MIDDLE_SCALE_SUBDIVISIONS;
        
        for (int i = middleInterval; i <= spanLength; i += middleInterval) {
            // Skip major mark positions
            if (i % scaleSize != 0) {
                int value = reverseNumbers ? (spanLength - i) * scaleValue + startValue : i * scaleValue + startValue;
                String numberText = value + unitSuffix;
                
                int markX = x + (int)(i * pixelsPerUnit);
                
                // Calculate text position with alignment and offsets
                int textWidth = fm.stringWidth(numberText);
                int textX = calculateTextX(markX, textWidth, horizontalAlignment) + textOffsetX;
                int middleTextY = y + height - middleScaleHeight - textOffsetY - numberOffset;
                
                // Draw text background if enabled
                if (showTextBackground && textBackgroundColor != null) {
                    g.setColor(textBackgroundColor);
                    int bgPadding = 1;
                    g.fillRect(textX - bgPadding, middleTextY - fm.getAscent() - bgPadding, 
                              textWidth + 2 * bgPadding, fm.getHeight() + 2 * bgPadding);
                }
                
                // Draw text shadow if enabled
                if (textShadowOffset > 0) {
                    g.setColor(textShadowColor);
                    drawCustomText(g, numberText, textX + textShadowOffset, middleTextY + textShadowOffset);
                }
                
                // Draw main text
                g.setColor(textColor);
                drawCustomText(g, numberText, textX, middleTextY);
            }
        }
    }
    
    /**
     * Draws selection border when ruler is selected
     */
    private void drawSelectionBorder(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
        g.drawRect(x - 2, y - 2, width + 4, height + 4);
    }
    
    // ===============================================================================
    // INTELLIGENT DRAG HANDLES AND INTERACTION SYSTEM
    // ===============================================================================
    
    /**
     * Draw intelligent color-coded control handles for different operations
     */
    private void drawResizeHandles(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (SHOW_SIMPLE_HANDLES_ONLY) {
            // SIMPLIFIED HANDLES: Only show essential controls for better UX
            
            // Position handle (Blue circle) - Move entire ruler (top-left corner)
            drawControlHandle(g, POSITION_HANDLE_COLOR, x - CONTROL_HANDLE_SIZE, y - CONTROL_HANDLE_SIZE, 
                             RulerControlType.POSITION, "circle");
            
            // Length handles (Green rectangles) - Adjust ruler length (left and right sides)
            drawControlHandle(g, LENGTH_HANDLE_COLOR, x + width, y + height/2 - CONTROL_HANDLE_SIZE/2, 
                             RulerControlType.LENGTH, "rectangle");
            drawControlHandle(g, LENGTH_HANDLE_COLOR, x - CONTROL_HANDLE_SIZE, y + height/2 - CONTROL_HANDLE_SIZE/2, 
                             RulerControlType.LENGTH, "rectangle");
            
            // Height handles (Orange diamonds) - Adjust ruler height (top and bottom)
            drawControlHandle(g, HEIGHT_HANDLE_COLOR, x + width/2 - CONTROL_HANDLE_SIZE/2, y - CONTROL_HANDLE_SIZE, 
                             RulerControlType.HEIGHT, "diamond");
            drawControlHandle(g, HEIGHT_HANDLE_COLOR, x + width/2 - CONTROL_HANDLE_SIZE/2, y + height, 
                             RulerControlType.HEIGHT, "diamond");
        } else {
            // FULL COMPLEX HANDLES: All handles (can be confusing for users)
            
            // Position handles (Blue circles) - Move entire ruler
            drawControlHandle(g, POSITION_HANDLE_COLOR, x - CONTROL_HANDLE_SIZE, y - CONTROL_HANDLE_SIZE, 
                             RulerControlType.POSITION, "circle");
            drawControlHandle(g, POSITION_HANDLE_COLOR, x + width + CONTROL_HANDLE_SIZE/2, y - CONTROL_HANDLE_SIZE, 
                             RulerControlType.POSITION, "circle");
            
            // Length handles (Green rectangles) - Adjust ruler length
            drawControlHandle(g, LENGTH_HANDLE_COLOR, x + width, y + height/2 - CONTROL_HANDLE_SIZE/2, 
                             RulerControlType.LENGTH, "rectangle");
            drawControlHandle(g, LENGTH_HANDLE_COLOR, x - CONTROL_HANDLE_SIZE, y + height/2 - CONTROL_HANDLE_SIZE/2, 
                             RulerControlType.LENGTH, "rectangle");
            
            // Height handles (Orange diamonds) - Adjust ruler height  
            drawControlHandle(g, HEIGHT_HANDLE_COLOR, x + width/2 - CONTROL_HANDLE_SIZE/2, y - CONTROL_HANDLE_SIZE, 
                             RulerControlType.HEIGHT, "diamond");
            drawControlHandle(g, HEIGHT_HANDLE_COLOR, x + width/2 - CONTROL_HANDLE_SIZE/2, y + height, 
                             RulerControlType.HEIGHT, "diamond");
            
            // Scale Size handles (Purple triangles) - Adjust scale interval
            drawControlHandle(g, SCALE_HANDLE_COLOR, x + width/4, y - CONTROL_HANDLE_SIZE - 5, 
                             RulerControlType.SCALE_SIZE, "triangle");
            
            // Number Offset handles (Red hexagons) - Adjust number positioning
            if (showNumbers) {
                drawControlHandle(g, NUMBER_HANDLE_COLOR, x + width*3/4, y - CONTROL_HANDLE_SIZE - 5, 
                                 RulerControlType.NUMBER_OFFSET, "hexagon");
            }
        }
        
        // Draw drag feedback
        if (isIntelligentDragging && activeDragType != null) {
            drawDragFeedback(g);
        }
    }
    
    /**
     * Draw individual control handle with specified shape and color
     */
    private void drawControlHandle(Graphics2D g, Color color, int handleX, int handleY, 
                                  RulerControlType type, String shape) {
        // Handle background with slight transparency
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
        
        switch (shape) {
            case "circle":
                g.fillOval(handleX, handleY, CONTROL_HANDLE_SIZE, CONTROL_HANDLE_SIZE);
                break;
            case "rectangle":
                g.fillRect(handleX, handleY, CONTROL_HANDLE_SIZE, CONTROL_HANDLE_SIZE);
                break;
            case "diamond":
                drawDiamond(g, handleX + CONTROL_HANDLE_SIZE/2, handleY + CONTROL_HANDLE_SIZE/2, CONTROL_HANDLE_SIZE/2);
                break;
            case "triangle":
                drawTriangle(g, handleX + CONTROL_HANDLE_SIZE/2, handleY + CONTROL_HANDLE_SIZE/2, CONTROL_HANDLE_SIZE/2);
                break;
            case "hexagon":
                drawHexagon(g, handleX + CONTROL_HANDLE_SIZE/2, handleY + CONTROL_HANDLE_SIZE/2, CONTROL_HANDLE_SIZE/2);
                break;
        }
        
        // Handle border
        g.setColor(color.darker());
        g.setStroke(new BasicStroke(1.0f));
        
        switch (shape) {
            case "circle":
                g.drawOval(handleX, handleY, CONTROL_HANDLE_SIZE, CONTROL_HANDLE_SIZE);
                break;
            case "rectangle":
                g.drawRect(handleX, handleY, CONTROL_HANDLE_SIZE, CONTROL_HANDLE_SIZE);
                break;
            case "diamond":
                drawDiamondOutline(g, handleX + CONTROL_HANDLE_SIZE/2, handleY + CONTROL_HANDLE_SIZE/2, CONTROL_HANDLE_SIZE/2);
                break;
            case "triangle":
                drawTriangleOutline(g, handleX + CONTROL_HANDLE_SIZE/2, handleY + CONTROL_HANDLE_SIZE/2, CONTROL_HANDLE_SIZE/2);
                break;
            case "hexagon":
                drawHexagonOutline(g, handleX + CONTROL_HANDLE_SIZE/2, handleY + CONTROL_HANDLE_SIZE/2, CONTROL_HANDLE_SIZE/2);
                break;
        }
    }
    
    // Shape drawing helper methods
    private void drawDiamond(Graphics2D g, int centerX, int centerY, int radius) {
        int[] xPoints = {centerX, centerX + radius, centerX, centerX - radius};
        int[] yPoints = {centerY - radius, centerY, centerY + radius, centerY};
        g.fillPolygon(xPoints, yPoints, 4);
    }
    
    private void drawDiamondOutline(Graphics2D g, int centerX, int centerY, int radius) {
        int[] xPoints = {centerX, centerX + radius, centerX, centerX - radius};
        int[] yPoints = {centerY - radius, centerY, centerY + radius, centerY};
        g.drawPolygon(xPoints, yPoints, 4);
    }
    
    private void drawTriangle(Graphics2D g, int centerX, int centerY, int radius) {
        int[] xPoints = {centerX, centerX + radius, centerX - radius};
        int[] yPoints = {centerY - radius, centerY + radius/2, centerY + radius/2};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    private void drawTriangleOutline(Graphics2D g, int centerX, int centerY, int radius) {
        int[] xPoints = {centerX, centerX + radius, centerX - radius};
        int[] yPoints = {centerY - radius, centerY + radius/2, centerY + radius/2};
        g.drawPolygon(xPoints, yPoints, 3);
    }
    
    private void drawHexagon(Graphics2D g, int centerX, int centerY, int radius) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 3 * i;
            xPoints[i] = centerX + (int)(radius * Math.cos(angle));
            yPoints[i] = centerY + (int)(radius * Math.sin(angle));
        }
        g.fillPolygon(xPoints, yPoints, 6);
    }
    
    private void drawHexagonOutline(Graphics2D g, int centerX, int centerY, int radius) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 3 * i;
            xPoints[i] = centerX + (int)(radius * Math.cos(angle));
            yPoints[i] = centerY + (int)(radius * Math.sin(angle));
        }
        g.drawPolygon(xPoints, yPoints, 6);
    }
    
    /**
     * Draw visual feedback during drag operations
     */
    private void drawDragFeedback(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
        
        String feedbackText = "";
        switch (activeDragType) {
            case POSITION:
                feedbackText = "Moving Ruler";
                break;
            case LENGTH:
                feedbackText = "Length: " + rulerLength + "px";
                break;
            case HEIGHT:
                feedbackText = "Height: " + rulerHeight + "px";
                break;
            case SCALE_SIZE:
                feedbackText = "Scale: " + scaleSize + " units";
                break;
            case NUMBER_OFFSET:
                feedbackText = "Offset: " + numberOffset + "px";
                break;
        }
        
        // Draw feedback text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(feedbackText);
        int textX = x + width/2 - textWidth/2;
        int textY = y - 20;
        
        // Text background
        g.setColor(new Color(255, 255, 200, 220));
        g.fillRoundRect(textX - 5, textY - fm.getHeight() + 5, textWidth + 10, fm.getHeight(), 5, 5);
        
        // Text
        g.setColor(Color.BLACK);
        g.drawString(feedbackText, textX, textY);
    }
    
    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
    
    @Override
    public void resizeTo(int newWidth, int newHeight) {
        // Check lock size flag before allowing resize
        if (lockSize && RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("RulerMark resizeTo blocked: Size is locked");
            }
            return;
        }
        
        if (isVertical) {
            this.height = Math.max(50, newWidth);  // Swap for vertical
            this.width = Math.max(20, newHeight);
        } else {
            this.width = Math.max(50, newWidth);   // Minimum length
            this.height = Math.max(20, newHeight); // Minimum height
        }
        
        this.rulerLength = this.width;
        this.rulerHeight = this.height;
        updateScaleHeights();
    }
    
    // ===============================================================================
    // COMPREHENSIVE PROPERTY GETTERS AND SETTERS (SOFT CODING)
    // ===============================================================================
    
    // Core dimension properties
    public int getRulerLength() { return rulerLength; }
    public void setRulerLength(int length) { 
        this.rulerLength = Math.max(50, length);
        this.width = this.rulerLength;
        updateScaleHeights();
    }
    
    public int getRulerHeight() { return rulerHeight; }
    public void setRulerHeight(int height) { 
        this.rulerHeight = Math.max(20, height);
        this.height = this.rulerHeight;
        updateScaleHeights();
    }
    
    public int getScaleSize() { return scaleSize; }
    public void setScaleSize(int scaleSize) { this.scaleSize = Math.max(1, scaleSize); }
    
    public int getScaleValue() { return scaleValue; }
    public void setScaleValue(int scaleValue) { this.scaleValue = Math.max(1, scaleValue); }
    
    public int getStartValue() { return startValue; }
    public void setStartValue(int startValue) { this.startValue = startValue; }
    
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = Math.max(0, Math.min(height/2, radius)); }
    
    public int getSpanLength() { return spanLength; }
    public void setSpanLength(int spanLength) { this.spanLength = Math.max(10, spanLength); }
    
    // Scale line height properties
    public int getMajorScaleHeight() { return majorScaleHeight; }
    public void setMajorScaleHeight(int height) { 
        this.majorScaleHeight = Math.max(5, Math.min(rulerHeight, height));
    }
    
    public int getMiddleScaleHeight() { return middleScaleHeight; }
    public void setMiddleScaleHeight(int height) { 
        this.middleScaleHeight = Math.max(5, Math.min(rulerHeight, height));
    }
    
    public int getShortScaleHeight() { return shortScaleHeight; }
    public void setShortScaleHeight(int height) { 
        this.shortScaleHeight = Math.max(3, Math.min(rulerHeight, height));
    }
    
    // Display option properties
    public boolean isShowNumbers() { return showNumbers; }
    public void setShowNumbers(boolean showNumbers) { this.showNumbers = showNumbers; }
    
    public boolean isShowMiddleNumbers() { return showMiddleNumbers; }
    public void setShowMiddleNumbers(boolean showMiddleNumbers) { this.showMiddleNumbers = showMiddleNumbers; }
    
    public boolean isShowMinorMarks() { return showMinorMarks; }
    public void setShowMinorMarks(boolean showMinorMarks) { this.showMinorMarks = showMinorMarks; }
    
    public boolean isShowMiddleMarks() { return showMiddleMarks; }
    public void setShowMiddleMarks(boolean showMiddleMarks) { this.showMiddleMarks = showMiddleMarks; }
    
    public boolean isShowMajorMarks() { return showMajorMarks; }
    public void setShowMajorMarks(boolean showMajorMarks) { this.showMajorMarks = showMajorMarks; }
    
    // Orientation and styling
    public boolean isVertical() { return isVertical; }
    public void setVertical(boolean vertical) { 
        this.isVertical = vertical;
        if (vertical) {
            // Swap dimensions for vertical orientation
            int temp = width;
            width = height;
            height = temp;
        }
    }
    
    public Color getRulerColor() { return rulerColor; }
    public void setRulerColor(Color rulerColor) { this.rulerColor = rulerColor; }
    
    public Color getTextColor() { return textColor; }
    public void setTextColor(Color textColor) { this.textColor = textColor; }
    
    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }
    
    public int getTextSize() { return textSize; }
    public void setTextSize(int textSize) { this.textSize = Math.max(6, Math.min(24, textSize)); }
    
    public boolean isShowBackground() { return showBackground; }
    public void setShowBackground(boolean showBackground) { this.showBackground = showBackground; }
    
    // Advanced properties
    public String getUnitSuffix() { return unitSuffix; }
    public void setUnitSuffix(String unitSuffix) { this.unitSuffix = unitSuffix != null ? unitSuffix : ""; }
    
    public boolean isReverseNumbers() { return reverseNumbers; }
    public void setReverseNumbers(boolean reverseNumbers) { this.reverseNumbers = reverseNumbers; }
    
    public int getNumberOffset() { return numberOffset; }
    public void setNumberOffset(int numberOffset) { this.numberOffset = numberOffset; }
    
    public double getLineThickness() { return lineThickness; }
    public void setLineThickness(double lineThickness) { 
        this.lineThickness = Math.max(0.5, Math.min(5.0, lineThickness));
    }
    
    // ===============================================================================
    // COMPREHENSIVE TEXT CUSTOMIZATION METHODS - SIMILAR TO TEXTMARK
    // ===============================================================================
    
    // Font properties
    public Font getNumberFont() { return numberFont; }
    public void setNumberFont(Font font) { 
        this.numberFont = font != null ? font : new Font("Arial", Font.BOLD, DEFAULT_TEXT_SIZE);
        this.fontFamily = numberFont.getFontName();
        this.fontSize = numberFont.getSize();
        this.fontStyle = numberFont.getStyle();
    }
    
    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily != null ? fontFamily : "Arial";
        updateNumberFont();
    }
    
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) {
        this.fontSize = Math.max(6, Math.min(72, fontSize));
        updateNumberFont();
    }
    
    public int getFontStyle() { return fontStyle; }
    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        updateNumberFont();
    }
    
    // Helper method to update font when properties change
    private void updateNumberFont() {
        this.numberFont = new Font(fontFamily, fontStyle, fontSize);
    }
    
    // Text spacing and positioning
    public double getCharacterWidth() { return characterWidth; }
    public void setCharacterWidth(double characterWidth) {
        this.characterWidth = Math.max(0.5, Math.min(3.0, characterWidth));
    }
    
    public double getLineSpacing() { return lineSpacing; }
    public void setLineSpacing(double lineSpacing) {
        this.lineSpacing = Math.max(0.5, Math.min(3.0, lineSpacing));
    }
    
    public int getTextOffsetX() { return textOffsetX; }
    public void setTextOffsetX(int textOffsetX) { this.textOffsetX = textOffsetX; }
    
    public int getTextOffsetY() { return textOffsetY; }
    public void setTextOffsetY(int textOffsetY) { this.textOffsetY = textOffsetY; }
    
    // Text alignment
    public TextAlignment getHorizontalAlignment() { return horizontalAlignment; }
    public void setHorizontalAlignment(TextAlignment alignment) {
        this.horizontalAlignment = alignment != null ? alignment : TextAlignment.CENTER;
    }
    
    public TextAlignment getVerticalAlignment() { return verticalAlignment; }
    public void setVerticalAlignment(TextAlignment alignment) {
        this.verticalAlignment = alignment != null ? alignment : TextAlignment.BOTTOM;
    }
    
    // Text appearance
    public Color getTextBackgroundColor() { return textBackgroundColor; }
    public void setTextBackgroundColor(Color color) { this.textBackgroundColor = color; }
    
    public boolean isShowTextBackground() { return showTextBackground; }
    public void setShowTextBackground(boolean show) { this.showTextBackground = show; }
    
    public boolean isTextBold() { return textBold; }
    public void setTextBold(boolean bold) {
        this.textBold = bold;
        updateFontStyle();
    }
    
    public boolean isTextItalic() { return textItalic; }
    public void setTextItalic(boolean italic) {
        this.textItalic = italic;
        updateFontStyle();
    }
    
    public boolean isTextUnderline() { return textUnderline; }
    public void setTextUnderline(boolean underline) { this.textUnderline = underline; }
    
    // Helper method to update font style based on bold/italic flags
    private void updateFontStyle() {
        int style = Font.PLAIN;
        if (textBold) style |= Font.BOLD;
        if (textItalic) style |= Font.ITALIC;
        setFontStyle(style);
    }
    
    // Advanced text options
    public double getTextRotation() { return textRotation; }
    public void setTextRotation(double rotation) {
        this.textRotation = rotation % 360.0;  // Normalize to 0-360 degrees
    }
    
    public boolean isAntiAliasText() { return antiAliasText; }
    public void setAntiAliasText(boolean antiAlias) { this.antiAliasText = antiAlias; }
    
    public int getTextShadowOffset() { return textShadowOffset; }
    public void setTextShadowOffset(int offset) {
        this.textShadowOffset = Math.max(0, Math.min(10, offset));
    }
    
    public Color getTextShadowColor() { return textShadowColor; }
    public void setTextShadowColor(Color color) {
        this.textShadowColor = color != null ? color : Color.GRAY;
    }
    
    // ===============================================================================
    // TEXT DRAWING HELPER METHODS
    // ===============================================================================
    
    /**
     * Calculate horizontal text position based on alignment
     */
    private int calculateTextX(int markX, int textWidth, TextAlignment alignment) {
        switch (alignment) {
            case LEFT: return markX;
            case RIGHT: return markX - textWidth;
            case CENTER:
            default: return markX - textWidth / 2;
        }
    }
    
    /**
     * Calculate vertical text position based on alignment
     */
    private int calculateTextY() {
        switch (verticalAlignment) {
            case TOP: return y + fontSize;
            case MIDDLE: return y + height / 2 + fontSize / 3;
            case BOTTOM:
            default: return y + height - majorScaleHeight - TEXT_OFFSET_FROM_SCALE - numberOffset;
        }
    }
    
    /**
     * Draw text with custom spacing and rotation
     */
    private void drawCustomText(Graphics2D g, String text, int x, int y) {
        if (textRotation != 0.0) {
            // Apply text rotation
            AffineTransform originalTransform = g.getTransform();
            g.rotate(Math.toRadians(textRotation), x, y);
            drawSpacedText(g, text, x, y);
            g.setTransform(originalTransform);
        } else {
            drawSpacedText(g, text, x, y);
        }
    }
    
    /**
     * Draw text with custom character spacing
     */
    private void drawSpacedText(Graphics2D g, String text, int x, int y) {
        if (characterWidth != 1.0) {
            // Custom character spacing
            FontMetrics fm = g.getFontMetrics();
            char[] chars = text.toCharArray();
            int currentX = x;
            for (char c : chars) {
                String ch = String.valueOf(c);
                g.drawString(ch, currentX, y);
                int charWidth = fm.stringWidth(ch);
                currentX += (int) (charWidth * characterWidth);
            }
        } else {
            // Normal text rendering
            g.drawString(text, x, y);
            
            // Add underline if enabled
            if (textUnderline) {
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                g.drawLine(x, y + 2, x + textWidth, y + 2);
            }
        }
    }
    
    // ===============================================================================
    // INTELLIGENT DRAG AND DROP PROCESSING METHODS
    // ===============================================================================
    
    /**
     * Detect which control handle is being clicked for drag operations (SIMPLIFIED)
     */
    public RulerControlType detectControlHandle(int mouseX, int mouseY) {
        if (SHOW_SIMPLE_HANDLES_ONLY) {
            // SIMPLIFIED MODE: Only check essential handles
            
            // Position handle (Blue circle) - top-left only
            if (isWithinHandle(mouseX, mouseY, x - CONTROL_HANDLE_SIZE, y - CONTROL_HANDLE_SIZE)) {
                return RulerControlType.POSITION;
            }
            
            // Length handles (Green rectangles) - left and right sides only
            if (isWithinHandle(mouseX, mouseY, x + width, y + height/2 - CONTROL_HANDLE_SIZE/2) ||
                isWithinHandle(mouseX, mouseY, x - CONTROL_HANDLE_SIZE, y + height/2 - CONTROL_HANDLE_SIZE/2)) {
                return RulerControlType.LENGTH;
            }
            
            // Height handles (Orange diamonds) - top and bottom for vertical resize
            if (isWithinHandle(mouseX, mouseY, x + width/2 - CONTROL_HANDLE_SIZE/2, y - CONTROL_HANDLE_SIZE) ||
                isWithinHandle(mouseX, mouseY, x + width/2 - CONTROL_HANDLE_SIZE/2, y + height)) {
                return RulerControlType.HEIGHT;
            }
        } else {
            // FULL COMPLEX MODE: Check all handles
            
            // Position handles (Blue circles)
            if (isWithinHandle(mouseX, mouseY, x - CONTROL_HANDLE_SIZE, y - CONTROL_HANDLE_SIZE) ||
                isWithinHandle(mouseX, mouseY, x + width + CONTROL_HANDLE_SIZE/2, y - CONTROL_HANDLE_SIZE)) {
                return RulerControlType.POSITION;
            }
            
            // Length handles (Green rectangles)
            if (isWithinHandle(mouseX, mouseY, x + width, y + height/2 - CONTROL_HANDLE_SIZE/2) ||
                isWithinHandle(mouseX, mouseY, x - CONTROL_HANDLE_SIZE, y + height/2 - CONTROL_HANDLE_SIZE/2)) {
                return RulerControlType.LENGTH;
            }
            
            // Height handles (Orange diamonds)
            if (isWithinHandle(mouseX, mouseY, x + width/2 - CONTROL_HANDLE_SIZE/2, y - CONTROL_HANDLE_SIZE) ||
                isWithinHandle(mouseX, mouseY, x + width/2 - CONTROL_HANDLE_SIZE/2, y + height)) {
                return RulerControlType.HEIGHT;
            }
            
            // Scale Size handles (Purple triangles)
            if (isWithinHandle(mouseX, mouseY, x + width/4, y - CONTROL_HANDLE_SIZE - 5)) {
                return RulerControlType.SCALE_SIZE;
            }
            
            // Number Offset handles (Red hexagons)
            if (showNumbers && isWithinHandle(mouseX, mouseY, x + width*3/4, y - CONTROL_HANDLE_SIZE - 5)) {
                return RulerControlType.NUMBER_OFFSET;
            }
        }
        
        return null;
    }
    
    /**
     * Check if mouse is within handle detection area
     */
    private boolean isWithinHandle(int mouseX, int mouseY, int handleX, int handleY) {
        double distance = Math.sqrt(Math.pow(mouseX - (handleX + CONTROL_HANDLE_SIZE/2), 2) + 
                                   Math.pow(mouseY - (handleY + CONTROL_HANDLE_SIZE/2), 2));
        return distance <= CONTROL_DETECTION_RADIUS;
    }
    
    /**
     * Start drag operation with specified control type
     */
    public void startDrag(int mouseX, int mouseY, RulerControlType controlType) {
        if (controlType == null) return;
        
        isIntelligentDragging = true;
        activeDragType = controlType;
        dragStartX = mouseX;
        dragStartY = mouseY;
        
        // Store original values for relative adjustments
        originalLength = rulerLength;
        originalHeight = rulerHeight;
        originalScaleSize = scaleSize;
        originalNumberOffset = numberOffset;
        dragOffset.setLocation(mouseX - x, mouseY - y);
        
        System.out.println("🎯 RulerMark drag started: " + controlType + " at (" + mouseX + ", " + mouseY + ")");
    }
    
    /**
     * Process drag operation with intelligent property adjustment
     */
    public void processDrag(int mouseX, int mouseY) {
        if (!isIntelligentDragging || activeDragType == null) return;
        
        int deltaX = mouseX - dragStartX;
        int deltaY = mouseY - dragStartY;
        
        // Apply minimum drag distance threshold
        if (Math.abs(deltaX) < MIN_DRAG_DISTANCE && Math.abs(deltaY) < MIN_DRAG_DISTANCE) return;
        
        switch (activeDragType) {
            case POSITION:
                // Move entire ruler
                this.x = mouseX - dragOffset.x;
                this.y = mouseY - dragOffset.y;
                break;
                
            case LENGTH:
                // Adjust ruler length based on horizontal drag
                int newLength = (int)(originalLength + (deltaX * DRAG_SENSITIVITY));
                setRulerLength(newLength);
                break;
                
            case HEIGHT:
                // Adjust ruler height based on vertical drag  
                int newHeight = (int)(originalHeight + (deltaY * DRAG_SENSITIVITY));
                setRulerHeight(newHeight);
                break;
                
            case SCALE_SIZE:
                // Adjust scale size based on horizontal drag (more sensitive)
                int scaleChange = (int)(deltaX / 10.0); // Reduce sensitivity for scale
                int newScaleSize = Math.max(1, originalScaleSize + scaleChange);
                setScaleSize(newScaleSize);
                break;
                
            case NUMBER_OFFSET:
                // Adjust number offset based on vertical drag
                int offsetChange = (int)(deltaY * DRAG_SENSITIVITY);
                setNumberOffset(originalNumberOffset + offsetChange);
                break;
        }
    }
    
    /**
     * End drag operation and cleanup
     */
    public void endDrag() {
        if (isIntelligentDragging && activeDragType != null) {
            System.out.println("✅ RulerMark drag completed: " + activeDragType);
        }
        
        isIntelligentDragging = false;
        activeDragType = null;
        dragStartX = dragStartY = 0;
        dragOffset.setLocation(0, 0);
    }
    
    /**
     * Check if ruler is currently being dragged (integrates with base Mark class)
     */
    @Override
    public boolean isDragging() {
        return isIntelligentDragging || super.isDragging();
    }
    
    /**
     * Get current active drag type
     */
    public RulerControlType getActiveDragType() {
        return activeDragType;
    }
    
    // ===============================================================================
    // BASE MARK DRAG INTEGRATION - ENABLE SIMPLE RULER DRAGGING
    // ===============================================================================
    
    /**
     * Override base Mark dragTo method to support simple ruler movement
     */
    @Override
    public void dragTo(int mx, int my) {
        // If using intelligent handles, use the complex drag system
        if (isIntelligentDragging && activeDragType != null) {
            processDrag(mx, my);
        }
        // Otherwise, use simple whole-ruler dragging (most common case)
        else if (super.isDragging()) {
            super.dragTo(mx, my);
            System.out.println("📏 Simple ruler drag to: (" + x + "," + y + ")");
        }
    }
    
    /**
     * Override base Mark startDrag to enable simple ruler movement
     */
    @Override
    public void startDrag(int mx, int my) {
        // Check if clicking on an intelligent handle first
        RulerControlType handleType = detectControlHandle(mx, my);
        
        if (handleType != null) {
            // Use intelligent handle system
            startDrag(mx, my, handleType);
        } else {
            // Use simple whole-ruler dragging
            super.startDrag(mx, my);
            System.out.println("📏 Started simple ruler drag from: (" + mx + "," + my + ")");
        }
    }
    
    /**
     * Override base Mark stopDrag to properly end both drag systems
     */
    @Override
    public void stopDrag() {
        // Stop intelligent handle dragging
        if (isIntelligentDragging && activeDragType != null) {
            endDrag();
        }
        // Stop simple dragging
        super.stopDrag();
        System.out.println("📏 Ruler drag stopped");
    }
    
    /**
     * Enhanced contains method that considers drag handles when selected
     */
    @Override
    public boolean contains(int px, int py) {
        // Check main ruler body
        boolean inBody = px >= x && px <= x + width && py >= y && py <= y + height;
        
        // If not in body, check if over any control handles (when ruler might be selected)
        if (!inBody && detectControlHandle(px, py) != null) {
            return true;
        }
        
        return inBody;
    }
    
    @Override
    public String toString() {
        return "RulerMark{" +
                "length=" + rulerLength +
                ", height=" + rulerHeight +
                ", scaleSize=" + scaleSize +
                ", scaleValue=" + scaleValue +
                ", startValue=" + startValue +
                ", spanLength=" + spanLength +
                ", vertical=" + isVertical +
                ", showNumbers=" + showNumbers +
                ", showMiddleNumbers=" + showMiddleNumbers +
                ", radius=" + radius +
                ", isIntelligentDragging=" + isIntelligentDragging +
                ", activeDragType=" + activeDragType +
                '}';
    }
}