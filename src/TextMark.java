import java.awt.*;
import java.awt.geom.*;

public class TextMark extends Mark {
    // Enhanced text resizing system with 8 directional handles
    public enum TextResizeHandle {
        NONE,
        TOP,          // Vertical font size adjustment
        BOTTOM,       // Vertical font size adjustment
        LEFT,         // Character spacing adjustment
        RIGHT,        // Character spacing adjustment
        TOP_LEFT,     // Both font size and character spacing
        TOP_RIGHT,    // Both font size and character spacing
        BOTTOM_LEFT,  // Both font size and character spacing
        BOTTOM_RIGHT  // Both font size and character spacing
    }
    
    private String text = "ABC123";
    private Font font = new Font("Arial", Font.BOLD, 14);
    
    // Soft coding: Configurable text formatting parameters
    private double characterWidth = 1.0;    // Width multiplier for character spacing
    private double lineSpacing = 1.0;       // Spacing multiplier for line height
    private int minWidth = 50;              // Minimum width for text box
    private int padding = 20;               // Padding around text
    
    // Dynamic resizing properties
    private static final int HANDLE_SIZE = 8;
    private static final int HANDLE_BORDER = 3;
    private TextResizeHandle activeHandle = TextResizeHandle.NONE;
    private boolean dynamicResizing = false;
    private int resizeStartX, resizeStartY;
    private float originalFontSize;
    private double originalCharacterWidth;
    
    // Font size and spacing limits for safe resizing (using soft coding)
    private static final float MIN_FONT_SIZE = RugrelDropdownConfig.TEXTMARK_MIN_FONT_SIZE;
    private static final float MAX_FONT_SIZE = RugrelDropdownConfig.TEXTMARK_MAX_FONT_SIZE;
    private static final double MIN_CHARACTER_WIDTH = RugrelDropdownConfig.TEXTMARK_MIN_CHARACTER_WIDTH;
    private static final double MAX_CHARACTER_WIDTH = RugrelDropdownConfig.TEXTMARK_MAX_CHARACTER_WIDTH;
    
    // ==================== POSITION LOCKING SYSTEM (SOFT CODED) ====================
    
    // Position locking state (prevents movement after placement)
    private boolean positionLocked = false;                     // Main position lock state
    private int lockedTopLeftX = -1;                           // Locked top-left X coordinate
    private int lockedTopLeftY = -1;                           // Locked top-left Y coordinate
    private long positionLockTime = 0;                         // Time when position was locked
    private String lockReason = "PLACEMENT";                   // Reason for position lock

    public TextMark(int x, int y) {
        super(x, y);
        updateDimensions();
    }
    
    public TextMark(int x, int y, String text) {
        super(x, y);
        // Soft coding: Null-safe text handling with validation
        this.text = (text != null) ? text : "ABC123";
        updateDimensions();
    }
    
    public void setText(String text) {
        // Soft coding: Null-safe text setting with validation
        this.text = (text != null) ? text : "ABC123";
        updateDimensions();
    }
    
    public String getText() {
        return text;
    }
    
    // Soft coding: Additional convenience methods for consistency
    public void updateContent(String newText) {
        setText(newText);
    }
    
    public String getContent() {
        return getText();
    }
    
    public void setFont(Font font) {
        this.font = font;
        updateDimensions();
    }
    
    public Font getFont() {
        return font;
    }
    
    // Soft coding: Configurable width and spacing methods
    public void setCharacterWidth(double characterWidth) {
        this.characterWidth = Math.max(0.5, Math.min(3.0, characterWidth)); // Limit range
        updateDimensions();
    }
    
    public double getCharacterWidth() {
        return characterWidth;
    }
    
    public void setLineSpacing(double lineSpacing) {
        this.lineSpacing = Math.max(0.5, Math.min(3.0, lineSpacing)); // Limit range
        updateDimensions();
    }
    
    public double getLineSpacing() {
        return lineSpacing;
    }
    
    // Soft coding: Update dimensions based on configurable parameters
    private void updateDimensions() {
        if (text == null || text.isEmpty()) {
            this.width = minWidth;
            this.height = (int) (font.getSize() * lineSpacing + padding);
            return;
        }
        
        FontMetrics fm = new Canvas().getFontMetrics(font);
        
        // Calculate width with character width multiplier
        int baseWidth = fm.stringWidth(text);
        this.width = Math.max(minWidth, (int) (baseWidth * characterWidth + padding));
        
        // Calculate height with line spacing multiplier
        int baseHeight = font.getSize();
        this.height = Math.max(20, (int) (baseHeight * lineSpacing + padding));
    }

    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Use the stored font
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        // Calculate baseline position for text inside the box with line spacing
        int baselineOffset = (int) (fm.getAscent() * lineSpacing);
        int textY = y + (height + baselineOffset - fm.getDescent()) / 2;
        
        // Calculate horizontal position with character width adjustment
        int textX = x + padding / 2;
        
        g.setColor(Color.BLACK);
        
        // Soft coding: Draw text with configurable character spacing
        if (characterWidth != 1.0) {
            // Custom character spacing
            char[] chars = text.toCharArray();
            int currentX = textX;
            for (char c : chars) {
                String ch = String.valueOf(c);
                g.drawString(ch, currentX, textY);
                int charWidth = fm.stringWidth(ch);
                currentX += (int) (charWidth * characterWidth);
            }
        } else {
            // Normal text rendering
            g.drawString(text, textX, textY);
        }

        // Draw bounding box - different color if selected
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(2)); // Thicker line for selection
        } else {
            g.setColor(Color.BLUE);
            g.setStroke(new BasicStroke(1));
        }
        g.drawRect(x, y, width, height);
        
        // Draw position lock indicators (if position is locked)
        if (isPositionLocked() && RugrelDropdownConfig.SHOW_POSITION_LOCK_INDICATOR) {
            drawPositionLockIndicators(g);
        }

        // Draw 8 directional resize handles - only show if selected
        if (isSelected) {
            drawResizeHandles(g);
        }
    }
    
    // Soft-coded intelligent visual handle rendering system with color coding
    private void drawResizeHandles(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        
        // Enhanced geometry calculations matching the detection logic
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int midY = y + height / 2;
        
        // 1. FONT SIZE CONTROL HANDLES (Blue Circles) - Most Important
        int fontTopX = centerX;
        int fontTopY = y - 8;  // Extend outside for easier access
        int fontBottomX = centerX;
        int fontBottomY = y + height + 8;  // Extend outside for easier access
        
        g.setColor(new Color(0, 120, 255));  // Blue for font size
        g.fillOval(fontTopX - 8, fontTopY - 8, 16, 16);
        g.fillOval(fontBottomX - 8, fontBottomY - 8, 16, 16);
        g.setColor(Color.WHITE);
        g.fillOval(fontTopX - 4, fontTopY - 4, 8, 8);
        g.fillOval(fontBottomX - 4, fontBottomY - 4, 8, 8);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 8));
        g.drawString("+", fontTopX - 2, fontTopY + 2);
        g.drawString("-", fontBottomX - 2, fontBottomY + 2);
        
        // 2. CHARACTER SPACING CONTROL HANDLES (Green Rectangles)
        int spacingLeftX = x - 8;   // Extend outside bounds for easier access
        int spacingRightX = x + width + 8;
        
        g.setColor(new Color(0, 180, 0));  // Green for character spacing
        g.fillRect(spacingLeftX - 6, midY - 6, 12, 12);
        g.fillRect(spacingRightX - 6, midY - 6, 12, 12);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 8));
        g.drawString("◄", spacingLeftX - 3, midY + 2);
        g.drawString("►", spacingRightX - 3, midY + 2);
        
        // 3. CORNER COMBO CONTROLS (Orange Diamonds) - Combined font + spacing WITH ANCHOR LOCKING
        int topLeftX = x - 5;
        int topLeftY = y - 5;
        int topRightX = x + width + 5;
        int topRightY = y - 5;
        int bottomLeftX = x - 5;
        int bottomLeftY = y + height + 5;
        int bottomRightX = x + width + 5;
        int bottomRightY = y + height + 5;
        
        // Draw corner controls (orange diamonds)
        g.setColor(new Color(255, 150, 0));  // Orange for combined controls
        drawTextDiamond(g, topLeftX, topLeftY, 6);
        drawTextDiamond(g, topRightX, topRightY, 6);
        drawTextDiamond(g, bottomLeftX, bottomLeftY, 6);
        drawTextDiamond(g, bottomRightX, bottomRightY, 6);
        
        // 4. Visual debugging - show text bounds with dotted line
        g.setColor(new Color(100, 100, 100, 100));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{3, 3}, 0));
        g.drawRect(x, y, width, height);
        
        // 5. Font information display
        g.setColor(new Color(80, 80, 80, 200));
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString("Font: " + font.getSize() + "pt", x + 5, y - 12);
        g.drawString("Spacing: " + String.format("%.1f", characterWidth), x + 5, y + height + 15);
    }
    
    // Helper method to draw diamond-shaped handles
    private void drawTextDiamond(Graphics2D g, int x, int y, int size) {
        int[] xPoints = {x, x + size, x, x - size};
        int[] yPoints = {y - size, y, y + size, y};
        g.fillPolygon(xPoints, yPoints, 4);
    }
    
    /**
     * Draw position lock visual indicators
     */
    private void drawPositionLockIndicators(Graphics2D g) {
        // Save original stroke and color
        Stroke originalStroke = g.getStroke();
        Color originalColor = g.getColor();
        
        if (RugrelDropdownConfig.HIGHLIGHT_LOCKED_POSITION) {
            // Draw special border highlighting the locked position
            g.setColor(new Color(255, 0, 0, 150));  // Semi-transparent red
            g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                                      1.0f, new float[]{8, 4}, 0));
            g.drawRect(x - 2, y - 2, width + 4, height + 4);
        }
        
        if (RugrelDropdownConfig.USE_LOCK_ICON_OVERLAY && RugrelDropdownConfig.LOCK_TOP_LEFT_CORNER) {
            // Draw lock icon in the top-left corner
            int lockSize = 16;
            int lockX = x - 8;  // Position slightly outside top-left corner
            int lockY = y - 8;
            
            // Draw lock background
            g.setColor(new Color(255, 255, 255, 200));
            g.fillRoundRect(lockX - 2, lockY - 2, lockSize + 4, lockSize + 4, 4, 4);
            
            // Draw lock icon
            g.setColor(new Color(255, 0, 0));  // Red lock
            g.setStroke(new BasicStroke(2.0f));
            
            // Lock body (rectangle)
            g.fillRect(lockX + 4, lockY + 8, 8, 6);
            
            // Lock shackle (arc)
            g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawArc(lockX + 5, lockY + 2, 6, 8, 0, 180);
            
            // Lock indicator text
            if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 8));
                g.drawString("LOCKED", lockX - 5, lockY + lockSize + 12);
            }
        }
        
        // Draw lock status tooltip near object
        if (RugrelDropdownConfig.SHOW_LOCK_STATUS_IN_TOOLTIP) {
            g.setColor(new Color(0, 0, 0, 180));
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            String lockInfo = getPositionLockInfo();
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(lockInfo);
            int tooltipX = x + width + 10;
            int tooltipY = y + 15;
            
            // Draw tooltip background
            g.setColor(new Color(255, 255, 200, 200));
            g.fillRoundRect(tooltipX - 3, tooltipY - 12, textWidth + 6, 16, 4, 4);
            
            // Draw tooltip text
            g.setColor(Color.BLACK);
            g.drawString(lockInfo, tooltipX, tooltipY);
        }
        
        // Restore original graphics settings
        g.setStroke(originalStroke);
        g.setColor(originalColor);
    }
    

    
    // Soft-coded intelligent handle detection system with enhanced geometry and debug support
    public TextResizeHandle getTextResizeHandle(int px, int py) {
        // Use soft-coded configuration parameters for handle detection
        final int FONTSIZE_HIT_RADIUS = RugrelDropdownConfig.TEXTMARK_FONT_HANDLE_RADIUS;
        final int SPACING_HIT_RADIUS = RugrelDropdownConfig.TEXTMARK_SPACING_HANDLE_RADIUS;
        final int CORNER_HIT_RADIUS = RugrelDropdownConfig.TEXTMARK_CORNER_HANDLE_RADIUS;
        
        // Enhanced geometry calculations for better handle positioning
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int midY = y + height / 2;
        
        // Priority 1: Font Size Control Handles (TOP/BOTTOM) - Most Important
        if (RugrelDropdownConfig.ENABLE_TEXTMARK_FONT_SIZE_HANDLES) {
            int fontTopX = centerX;
            int fontTopY = y - 8;  // Extend outside for easier access
            int fontBottomX = centerX;
            int fontBottomY = y + height + 8;  // Extend outside for easier access
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("📝 Font Size UP handle at (" + fontTopX + "," + fontTopY + ") - radius:" + FONTSIZE_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, fontTopX, fontTopY, FONTSIZE_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ FONT SIZE UP detected!");
                }
                return TextResizeHandle.TOP;
            }
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("📝 Font Size DOWN handle at (" + fontBottomX + "," + fontBottomY + ") - radius:" + FONTSIZE_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, fontBottomX, fontBottomY, FONTSIZE_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ FONT SIZE DOWN detected!");
                }
                return TextResizeHandle.BOTTOM;
            }
        }
        
        // Priority 2: Character Spacing Controls (LEFT/RIGHT) - Essential for text layout
        if (RugrelDropdownConfig.ENABLE_TEXTMARK_SPACING_HANDLES) {
            int spacingLeftX = x - 8;   // Extend outside bounds for easier access
            int spacingRightX = x + width + 8;
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("⬅️ Spacing DECREASE handle at (" + spacingLeftX + "," + midY + ") - radius:" + SPACING_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, spacingLeftX, midY, SPACING_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ SPACING DECREASE detected!");
                }
                return TextResizeHandle.LEFT;
            }
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("➡️ Spacing INCREASE handle at (" + spacingRightX + "," + midY + ") - radius:" + SPACING_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, spacingRightX, midY, SPACING_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ SPACING INCREASE detected!");
                }
                return TextResizeHandle.RIGHT;
            }
        }
        
        // Priority 3: Corner Combo Controls (Font + Spacing combined)
        if (RugrelDropdownConfig.ENABLE_TEXTMARK_DIAGONAL_HANDLES) {
            int topLeftX = x - 5;
            int topLeftY = y - 5;
            int topRightX = x + width + 5;
            int topRightY = y - 5;
            int bottomLeftX = x - 5;
            int bottomLeftY = y + height + 5;
            int bottomRightX = x + width + 5;
            int bottomRightY = y + height + 5;
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("🔧 Top-Left combo at (" + topLeftX + "," + topLeftY + ") - radius:" + CORNER_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, topLeftX, topLeftY, CORNER_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ TOP-LEFT COMBO detected!");
                }
                return TextResizeHandle.TOP_LEFT;
            }
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("🔧 Top-Right combo at (" + topRightX + "," + topRightY + ") - radius:" + CORNER_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, topRightX, topRightY, CORNER_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ TOP-RIGHT COMBO detected!");
                }
                return TextResizeHandle.TOP_RIGHT;
            }
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("🔧 Bottom-Left combo at (" + bottomLeftX + "," + bottomLeftY + ") - radius:" + CORNER_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, bottomLeftX, bottomLeftY, CORNER_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ BOTTOM-LEFT COMBO detected!");
                }
                return TextResizeHandle.BOTTOM_LEFT;
            }
            
            if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                System.out.println("🔧 Bottom-Right combo at (" + bottomRightX + "," + bottomRightY + ") - radius:" + CORNER_HIT_RADIUS);
            }
            if (isInTextHandle(px, py, bottomRightX, bottomRightY, CORNER_HIT_RADIUS)) {
                if (RugrelDropdownConfig.DEBUG_TEXTMARK_HANDLE_DETECTION) {
                    System.out.println("✅ BOTTOM-RIGHT COMBO detected!");
                }
                return TextResizeHandle.BOTTOM_RIGHT;
            }
        }
        
        // Debug output disabled to reduce console noise
        // System.out.println("❌ No TextMark handle detected at (" + px + "," + py + ")");
        // System.out.println("   📍 All handle positions:");
        // System.out.println("   📝 Font UP: (" + fontTopX + "," + fontTopY + ")");
        // System.out.println("   📝 Font DOWN: (" + fontBottomX + "," + fontBottomY + ")");
        // System.out.println("   ⬅️ Spacing LEFT: (" + spacingLeftX + "," + midY + ")");
        // System.out.println("   ➡️ Spacing RIGHT: (" + spacingRightX + "," + midY + ")");
        return TextResizeHandle.NONE;
    }
    
    // Enhanced hit detection with custom radius support
    private boolean isInTextHandle(int px, int py, int handleX, int handleY, int hitRadius) {
        return Math.abs(px - handleX) <= hitRadius && Math.abs(py - handleY) <= hitRadius;
    }
    
    // Soft coding: Helper method to determine directional dominance
    private boolean isHorizontalDominant(int deltaX, int deltaY) {
        if (!RugrelDropdownConfig.TEXTMARK_USE_DOMINANT_DIRECTION_ONLY) {
            return false; // If not using dominance, neither direction is dominant
        }
        
        double ratio = RugrelDropdownConfig.TEXTMARK_DIRECTIONAL_DOMINANCE_RATIO;
        return Math.abs(deltaX) >= Math.abs(deltaY) * ratio;
    }
    
    private boolean isVerticalDominant(int deltaX, int deltaY) {
        if (!RugrelDropdownConfig.TEXTMARK_USE_DOMINANT_DIRECTION_ONLY) {
            return false; // If not using dominance, neither direction is dominant
        }
        
        double ratio = RugrelDropdownConfig.TEXTMARK_DIRECTIONAL_DOMINANCE_RATIO;
        return Math.abs(deltaY) >= Math.abs(deltaX) * ratio;
    }
    
    // Default hit detection method (backward compatibility)
    private boolean isInHandle(int px, int py, int handleX, int handleY) {
        return isInTextHandle(px, py, handleX, handleY, HANDLE_SIZE + HANDLE_BORDER + 5);
    }
    
    // Override the base resize methods to use dynamic text resizing
    @Override
    public boolean overResizeHandle(int px, int py) {
        // Soft coding: Check Lock Size flag first
        if (RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY && 
            RugrelDropdownConfig.BLOCK_RESIZE_HANDLES_WHEN_LOCKED && 
            lockSize) {
            return false; // No resize handle interaction when size is locked
        }
        return getTextResizeHandle(px, py) != TextResizeHandle.NONE;
    }
    
    @Override
    public void startResize() {
        if (!getCanResize()) return;
        
        // Soft coding: Check Lock Size functionality
        if (RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY && 
            RugrelDropdownConfig.ENABLE_INTELLIGENT_LOCK_SIZE && 
            lockSize && 
            RugrelDropdownConfig.PREVENT_RESIZE_OPERATIONS_WHEN_LOCKED) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("TextMark resize blocked: Size is locked");
            }
            return;
        }
        
        dynamicResizing = true;
        originalFontSize = font.getSize();
        originalCharacterWidth = characterWidth;
    }
    
    public void startDynamicResize(int startX, int startY, TextResizeHandle handle) {
        if (!getCanResize()) return;
        
        // Soft coding: Check Lock Size flag
        if (RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY && 
            RugrelDropdownConfig.ENABLE_INTELLIGENT_LOCK_SIZE && 
            lockSize && 
            RugrelDropdownConfig.PREVENT_RESIZE_OPERATIONS_WHEN_LOCKED) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("TextMark dynamic resize blocked: Size is locked");
            }
            return;
        }
        
        activeHandle = handle;
        resizeStartX = startX;
        resizeStartY = startY;
        dynamicResizing = true;
        originalFontSize = font.getSize();
        originalCharacterWidth = characterWidth;
    }
    
    // Enhanced Smart TextMark control system with dynamic directional locking
    public void resizeText(int deltaX, int deltaY, TextResizeHandle handle) {
        // Check if multi-directional resize is enabled via soft coding
        if (!RugrelDropdownConfig.ENABLE_TEXTMARK_MULTI_DIRECTIONAL_RESIZE) {
            if (RugrelDropdownConfig.LOG_TEXTMARK_RESIZE_OPERATIONS) {
                System.out.println("📝 TextMark multi-directional resize disabled via soft coding");
            }
            return;
        }
        
        // DYNAMIC DIRECTIONAL LOCKING TECHNIQUE
        // Apply strict directional filtering to completely eliminate cross-directional effects
        int filteredDeltaX = deltaX;
        int filteredDeltaY = deltaY;
        
        // For vertical handles (TOP/BOTTOM), completely zero out horizontal movement
        if (handle == TextResizeHandle.TOP || handle == TextResizeHandle.BOTTOM) {
            if (RugrelDropdownConfig.TEXTMARK_VERTICAL_ONLY_FONT_HANDLES) {
                filteredDeltaX = 0; // Dynamic lock: Force horizontal delta to absolute zero
            }
        }
        
        // For horizontal handles (LEFT/RIGHT), completely zero out vertical movement
        if (handle == TextResizeHandle.LEFT || handle == TextResizeHandle.RIGHT) {
            if (RugrelDropdownConfig.TEXTMARK_HORIZONTAL_ONLY_SPACING_HANDLES) {
                filteredDeltaY = 0; // Dynamic lock: Force vertical delta to absolute zero
            }
        }
        
        if (RugrelDropdownConfig.LOG_TEXTMARK_RESIZE_OPERATIONS) {
            System.out.println("📝 TextMark Multi-Directional Resize - Handle: " + handle + 
                             ", Original Delta: (" + deltaX + "," + deltaY + ")" +
                             ", Filtered Delta: (" + filteredDeltaX + "," + filteredDeltaY + ")");
        }
        
        // Use soft-coded sensitivity parameters for fine-tuning
        final double FONTSIZE_SENSITIVITY = RugrelDropdownConfig.TEXTMARK_FONT_SIZE_SENSITIVITY;
        final double SPACING_SENSITIVITY = RugrelDropdownConfig.TEXTMARK_SPACING_SENSITIVITY;
        final double FINE_FONTSIZE_SENSITIVITY = RugrelDropdownConfig.TEXTMARK_FINE_FONT_SENSITIVITY;
        final double FINE_SPACING_SENSITIVITY = RugrelDropdownConfig.TEXTMARK_FINE_SPACING_SENSITIVITY;
        final double LINE_SPACING_SENSITIVITY = RugrelDropdownConfig.TEXTMARK_LINE_SPACING_SENSITIVITY;
        
        switch (handle) {
            case TOP:
                // TOP = Vertical Font Size Control (Blue Circle +) - STRICT DIRECTIONAL
                // Use filtered delta for strict directional control  
                double fontIncrease = -filteredDeltaY * FONTSIZE_SENSITIVITY;  // Negative because up is negative
                float newTopSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)fontIncrease));
                font = font.deriveFont(newTopSize);
                
                // Soft coding: Only allow horizontal effects if specifically enabled AND filtered delta allows it
                if (!RugrelDropdownConfig.TEXTMARK_VERTICAL_ONLY_FONT_HANDLES && 
                    RugrelDropdownConfig.TEXTMARK_ALLOW_LINE_SPACING_IN_FONT_RESIZE && 
                    Math.abs(filteredDeltaX) > RugrelDropdownConfig.TEXTMARK_CROSS_AXIS_THRESHOLD) {
                    double lineSpacingDelta = filteredDeltaX * LINE_SPACING_SENSITIVITY;
                    lineSpacing = Math.max(RugrelDropdownConfig.TEXTMARK_MIN_LINE_SPACING, 
                                         Math.min(RugrelDropdownConfig.TEXTMARK_MAX_LINE_SPACING, 
                                                lineSpacing + lineSpacingDelta));
                    System.out.println("   📝 Font Size UP: " + font.getSize() + "pt, Line Spacing: " + String.format("%.2f", lineSpacing) +
                                     " (V:" + filteredDeltaY + "→size, H:" + filteredDeltaX + "→line)");
                } else {
                    // Strict directional mode - only font size changes (horizontal movement completely blocked)
                    System.out.println("   📝 Font Size UP: " + font.getSize() + "pt (V:" + filteredDeltaY + "→size) [STRICT VERTICAL - H LOCKED]");
                }
                break;
                
            case BOTTOM:
                // BOTTOM = Vertical Font Size Control (Blue Circle -) - STRICT DIRECTIONAL
                // Use filtered delta for strict directional control
                double fontDecrease = filteredDeltaY * FONTSIZE_SENSITIVITY;
                float newBottomSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)fontDecrease));
                font = font.deriveFont(newBottomSize);
                
                // Soft coding: Only allow horizontal effects if specifically enabled AND filtered delta allows it
                if (!RugrelDropdownConfig.TEXTMARK_VERTICAL_ONLY_FONT_HANDLES && 
                    RugrelDropdownConfig.TEXTMARK_ALLOW_LINE_SPACING_IN_FONT_RESIZE && 
                    Math.abs(filteredDeltaX) > RugrelDropdownConfig.TEXTMARK_CROSS_AXIS_THRESHOLD) {
                    double lineSpacingDelta = filteredDeltaX * LINE_SPACING_SENSITIVITY;
                    lineSpacing = Math.max(RugrelDropdownConfig.TEXTMARK_MIN_LINE_SPACING, 
                                         Math.min(RugrelDropdownConfig.TEXTMARK_MAX_LINE_SPACING, 
                                                lineSpacing + lineSpacingDelta));
                    System.out.println("   📝 Font Size DOWN: " + font.getSize() + "pt, Line Spacing: " + String.format("%.2f", lineSpacing) +
                                     " (V:" + filteredDeltaY + "→size, H:" + filteredDeltaX + "→line)");
                } else {
                    // Strict directional mode - only font size changes (horizontal movement completely blocked)
                    System.out.println("   📝 Font Size DOWN: " + font.getSize() + "pt (V:" + filteredDeltaY + "→size) [STRICT VERTICAL - H LOCKED]");
                }
                break;
                
            case LEFT:
                // LEFT = Horizontal Character Spacing Control (Green Rectangle ◄) - STRICT DIRECTIONAL
                // Use filtered delta for strict directional control
                
                if (RugrelDropdownConfig.TEXTMARK_HORIZONTAL_ONLY_SPACING_HANDLES) {
                    // Strict horizontal mode - only affect character spacing (vertical movement completely blocked)
                    double spacingDecrease = -filteredDeltaX * SPACING_SENSITIVITY;  // Negative for decrease
                    characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                        characterWidth + spacingDecrease));
                    System.out.println("   ⬅️ Spacing decreased to: " + String.format("%.2f", characterWidth) + 
                                     " (H:" + filteredDeltaX + "→spacing) [STRICT HORIZONTAL - V LOCKED]");
                } else {
                    // Legacy multi-directional mode - but prevent cross-directional movement
                    if (Math.abs(filteredDeltaX) > Math.abs(filteredDeltaY)) {
                        double spacingDecrease = -filteredDeltaX * SPACING_SENSITIVITY;  // Negative for decrease
                        characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                            characterWidth + spacingDecrease));
                        System.out.println("   ⬅️ Spacing decreased to: " + String.format("%.2f", characterWidth) + " (H-drag dominant)");
                    }
                    // REMOVED: Automatic position adjustment to prevent cross-directional movement
                    // Position adjustments during resize operations cause unwanted drag behavior
                }
                break;
                
            case RIGHT:
                // RIGHT = Horizontal Character Spacing Control (Green Rectangle ►) - STRICT DIRECTIONAL
                // Use filtered delta for strict directional control
                
                if (RugrelDropdownConfig.TEXTMARK_HORIZONTAL_ONLY_SPACING_HANDLES) {
                    // Strict horizontal mode - only affect character spacing (vertical movement completely blocked)
                    double spacingIncrease = filteredDeltaX * SPACING_SENSITIVITY;
                    characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                        characterWidth + spacingIncrease));
                    System.out.println("   ➡️ Spacing increased to: " + String.format("%.2f", characterWidth) + 
                                     " (H:" + filteredDeltaX + "→spacing) [STRICT HORIZONTAL - V LOCKED]");
                } else {
                    // Legacy multi-directional mode - but prevent cross-directional movement
                    if (Math.abs(filteredDeltaX) > Math.abs(filteredDeltaY)) {
                        double spacingIncrease = filteredDeltaX * SPACING_SENSITIVITY;
                        characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                            characterWidth + spacingIncrease));
                        System.out.println("   ➡️ Spacing increased to: " + String.format("%.2f", characterWidth) + " (H-drag dominant)");
                    }
                    // REMOVED: Automatic position adjustment to prevent cross-directional movement
                    // Position adjustments during resize operations cause unwanted drag behavior
                }
                break;
                
            case TOP_LEFT:
                // TOP_LEFT = Multi-Directional Combo Control (Orange Diamond)
                // Use filtered deltas for precise directional control
                double tlSpacingDelta = -filteredDeltaX * FINE_SPACING_SENSITIVITY;
                double tlFontDelta = -filteredDeltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + tlSpacingDelta));
                float tlNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)tlFontDelta));
                font = font.deriveFont(tlNewSize);
                
                System.out.println("   🔧 Top-Left Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + filteredDeltaX + "→spacing, V:" + filteredDeltaY + "→font) [FILTERED]");
                break;
                
            case TOP_RIGHT:
                // TOP_RIGHT = Multi-Directional Combo Control (Orange Diamond)
                // Use filtered deltas for precise directional control
                double trSpacingDelta = filteredDeltaX * FINE_SPACING_SENSITIVITY;
                double trFontDelta = -filteredDeltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + trSpacingDelta));
                float trNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)trFontDelta));
                font = font.deriveFont(trNewSize);
                
                System.out.println("   🔧 Top-Right Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + filteredDeltaX + "→spacing, V:" + filteredDeltaY + "→font) [FILTERED]");
                break;
                
            case BOTTOM_LEFT:
                // BOTTOM_LEFT = Multi-Directional Combo Control (Orange Diamond)
                // Use filtered deltas for precise directional control
                double blSpacingDelta = -filteredDeltaX * FINE_SPACING_SENSITIVITY;
                double blFontDelta = filteredDeltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + blSpacingDelta));
                float blNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)blFontDelta));
                font = font.deriveFont(blNewSize);
                
                System.out.println("   🔧 Bottom-Left Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + filteredDeltaX + "→spacing, V:" + filteredDeltaY + "→font) [FILTERED]");
                break;
                
            case BOTTOM_RIGHT:
                // BOTTOM_RIGHT = Diagonal Combo Control (Orange Diamond) - DIRECTIONAL CONTROL
                // Horizontal = spacing increase, Vertical = font size increase
                
                if (RugrelDropdownConfig.TEXTMARK_STRICT_DIRECTIONAL_RESIZE && 
                    RugrelDropdownConfig.TEXTMARK_USE_DOMINANT_DIRECTION_ONLY) {
                    
                    // Check for directional dominance using filtered deltas
                    if (isHorizontalDominant(filteredDeltaX, filteredDeltaY)) {
                        // Horizontal dominant - only adjust spacing
                        double brSpacingDelta = filteredDeltaX * SPACING_SENSITIVITY;
                        characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                            characterWidth + brSpacingDelta));
                        System.out.println("   🔧 Bottom-Right Horizontal - Spacing: " + String.format("%.2f", characterWidth) +
                                         " (H:" + filteredDeltaX + "→spacing) [H-DOMINANT FILTERED]");
                    } else if (isVerticalDominant(filteredDeltaX, filteredDeltaY)) {
                        // Vertical dominant - only adjust font size
                        double brFontDelta = filteredDeltaY * FONTSIZE_SENSITIVITY;
                        float brNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                            font.getSize() + (float)brFontDelta));
                        font = font.deriveFont(brNewSize);
                        System.out.println("   🔧 Bottom-Right Vertical - Font: " + font.getSize() + "pt" +
                                         " (V:" + filteredDeltaY + "→font) [V-DOMINANT FILTERED]");
                    } else {
                        // Balanced movement - apply both (traditional diagonal behavior)
                        double brSpacingDelta = filteredDeltaX * FINE_SPACING_SENSITIVITY;
                        double brFontDelta = filteredDeltaY * FINE_FONTSIZE_SENSITIVITY;
                        
                        characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                            characterWidth + brSpacingDelta));
                        float brNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                            font.getSize() + (float)brFontDelta));
                        font = font.deriveFont(brNewSize);
                        
                        System.out.println("   🔧 Bottom-Right Combo - Font: " + font.getSize() + 
                                         "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                         " (H:" + filteredDeltaX + "→spacing, V:" + filteredDeltaY + "→font) [BALANCED FILTERED]");
                    }
                } else {
                    // Legacy combined behavior
                    double brSpacingDelta = filteredDeltaX * FINE_SPACING_SENSITIVITY;
                    double brFontDelta = filteredDeltaY * FINE_FONTSIZE_SENSITIVITY;
                    
                    characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                        characterWidth + brSpacingDelta));
                    float brNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                        font.getSize() + (float)brFontDelta));
                    font = font.deriveFont(brNewSize);
                    
                    System.out.println("   🔧 Bottom-Right Combo - Font: " + font.getSize() + 
                                     "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                     " (H:" + filteredDeltaX + "→spacing, V:" + filteredDeltaY + "→font) [LEGACY FILTERED]");
                }
                break;
                
            default:
                System.out.println("   ❌ Unknown handle type: " + handle);
        }
        
        // Update text box dimensions and refresh display
        updateDimensions();
        System.out.println("   📐 Final bounds: (" + x + "," + y + ") " + width + "x" + height);
    }

    @Override
    public void resizeTo(int mx, int my) {
        // Soft coding: Check Lock Size flag before any resize operation
        if (RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY && 
            RugrelDropdownConfig.ENABLE_INTELLIGENT_LOCK_SIZE && 
            lockSize && 
            RugrelDropdownConfig.PREVENT_RESIZE_OPERATIONS_WHEN_LOCKED) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("TextMark resizeTo blocked: Size is locked");
            }
            return;
        }
        
        if (!dynamicResizing || !getCanResize() || activeHandle == TextResizeHandle.NONE) {
            // Fall back to standard resize if no active handle
            super.resizeTo(mx, my);
            return;
        }
        
        // Calculate drag distances
        int deltaX = mx - resizeStartX;
        int deltaY = my - resizeStartY;
        
        // Use the enhanced multi-directional system
        resizeText(deltaX, deltaY, activeHandle);
        
        // Update start position for continuous dragging
        resizeStartX = mx;
        resizeStartY = my;
    }
    
    private void adjustFontSize(int delta) {
        // More dramatic scaling: 1 pixel = 1 point size change for better visibility
        float scaleFactor = 1.0f + (delta * 0.05f);
        float newSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, originalFontSize * scaleFactor));
        
        // Always update font if size changed
        font = font.deriveFont(newSize);
        updateDimensions(); // Recalculate text box after font change
    }
    
    private void adjustCharacterSpacing(int delta) {
        // More dramatic scaling: 1 pixel = 1% spacing change for better visibility
        double scaleFactor = 1.0 + (delta * 0.01);
        characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
            originalCharacterWidth * scaleFactor));
        
        updateDimensions(); // Recalculate text box after spacing change
    }
    
    @Override
    public void stopResize() {
        dynamicResizing = false;
        activeHandle = TextResizeHandle.NONE;
        resizing = false;
    }
    
    // ==================== POSITION LOCKING INTEGRATION ====================
    
    /**
     * Override drag start to check position lock permissions
     */
    @Override
    public void startDrag(int mx, int my) {
        if (!isMovementAllowed()) {
            if (RugrelDropdownConfig.LOG_POSITION_LOCK_EVENTS) {
                System.out.println("🚫 Drag start blocked - position is locked");
            }
            return;  // Block drag initiation
        }
        
        super.startDrag(mx, my);
        
        if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
            System.out.println("▶️ Drag started for " + (isPositionLocked() ? "locked" : "unlocked") + " object");
        }
    }
    
    /**
     * Override drag to enforce position locking
     */
    @Override
    public void dragTo(int mx, int my) {
        if (!isMovementAllowed()) {
            if (RugrelDropdownConfig.LOG_POSITION_LOCK_EVENTS) {
                System.out.println("🚫 Drag movement blocked - position is locked");
            }
            return;  // Block drag movement
        }
        
        // Store position before drag
        int oldX = this.x;
        int oldY = this.y;
        
        super.dragTo(mx, my);
        
        // If position is locked, enforce the lock after any movement
        if (isPositionLocked()) {
            enforcePositionLock();
            
            // If position was reset due to lock, log it
            if (this.x != oldX || this.y != oldY) {
                if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
                    System.out.println("⚡ Position enforced during drag - reset from (" + oldX + "," + oldY + ") to (" + this.x + "," + this.y + ")");
                }
            }
        }
    }
    
    /**
     * Override to check drag permissions
     */
    @Override
    public boolean canDrag() {
        boolean parentCanDrag = super.canDrag();
        boolean movementAllowed = isMovementAllowed();
        
        return parentCanDrag && movementAllowed;
    }
    
    /**
     * Override to automatically lock position after placement
     */
    @Override
    public void stopDrag() {
        super.stopDrag();
        
        // Auto-lock position after drag completion (placement)
        if (!isPositionLocked() && RugrelDropdownConfig.AUTO_LOCK_ON_PLACEMENT) {
            autoLockOnPlacement();
        }
        
        if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
            System.out.println("⏹️ Drag stopped - Position lock status: " + (isPositionLocked() ? "LOCKED" : "UNLOCKED"));
        }
    }
    
    // ==================== POSITION LOCKING METHODS ====================
    
    /**
     * Lock the position of this object (prevents movement)
     */
    public void lockPosition(String reason) {
        if (!RugrelDropdownConfig.ENABLE_POSITION_LOCKING) return;
        
        this.positionLocked = true;
        this.lockedTopLeftX = this.x;
        this.lockedTopLeftY = this.y;
        this.positionLockTime = System.currentTimeMillis();
        this.lockReason = (reason != null) ? reason : "MANUAL";
        
        if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
            System.out.println("🔒 Position locked at (" + lockedTopLeftX + "," + lockedTopLeftY + ") - Reason: " + lockReason);
        }
    }
    
    /**
     * Automatically lock position after placement (if enabled)
     */
    public void autoLockOnPlacement() {
        if (RugrelDropdownConfig.AUTO_LOCK_ON_PLACEMENT && RugrelDropdownConfig.LOCK_TOP_LEFT_CORNER) {
            lockPosition("AUTO_PLACEMENT");
        }
    }
    
    /**
     * Check if position is locked
     */
    public boolean isPositionLocked() {
        return RugrelDropdownConfig.ENABLE_POSITION_LOCKING && positionLocked;
    }
    
    /**
     * Attempt to unlock position (only if manual unlock is enabled)
     */
    public boolean attemptUnlockPosition() {
        if (!RugrelDropdownConfig.ENABLE_MANUAL_POSITION_UNLOCK) {
            if (RugrelDropdownConfig.LOG_POSITION_LOCK_EVENTS) {
                System.out.println("🚫 Position unlock blocked - manual unlock disabled");
            }
            return false;
        }
        
        this.positionLocked = false;
        this.lockedTopLeftX = -1;
        this.lockedTopLeftY = -1;
        this.lockReason = null;
        
        if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
            System.out.println("🔓 Position unlocked");
        }
        
        return true;
    }
    
    /**
     * Check if movement is allowed (respects position lock)
     */
    public boolean isMovementAllowed() {
        if (!isPositionLocked()) {
            return true;  // Not locked, movement is allowed
        }
        
        if (RugrelDropdownConfig.PREVENT_DRAG_WHEN_LOCKED) {
            if (RugrelDropdownConfig.LOG_POSITION_LOCK_EVENTS) {
                System.out.println("🚫 Movement blocked - position is locked");
            }
            return false;
        }
        
        return true;  // Position locked but movement still allowed by config
    }
    
    /**
     * Enforce position lock (reset to locked position if moved)
     */
    public void enforcePositionLock() {
        if (!isPositionLocked() || lockedTopLeftX == -1 || lockedTopLeftY == -1) {
            return;
        }
        
        // Check if object has been moved from locked position
        if (this.x != lockedTopLeftX || this.y != lockedTopLeftY) {
            if (RugrelDropdownConfig.DEBUG_POSITION_LOCKING) {
                System.out.println("⚡ Enforcing position lock: resetting to (" + lockedTopLeftX + "," + lockedTopLeftY + ")");
            }
            
            // Reset to locked position
            this.x = lockedTopLeftX;
            this.y = lockedTopLeftY;
        }
    }
    
    /**
     * Get position lock information for display
     */
    public String getPositionLockInfo() {
        if (!isPositionLocked()) {
            return "Position: Unlocked";
        }
        
        return String.format("Position: Locked at (%d,%d) [%s]", 
                            lockedTopLeftX, lockedTopLeftY, lockReason);
    }

}
