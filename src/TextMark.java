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
    
    // Font size and spacing limits for safe resizing
    private static final float MIN_FONT_SIZE = 6.0f;
    private static final float MAX_FONT_SIZE = 72.0f;
    private static final double MIN_CHARACTER_WIDTH = 0.5;
    private static final double MAX_CHARACTER_WIDTH = 3.0;

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
        
        // 3. CORNER COMBO CONTROLS (Orange Diamonds) - Combined font + spacing
        int topLeftX = x - 5;
        int topLeftY = y - 5;
        int topRightX = x + width + 5;
        int topRightY = y - 5;
        int bottomLeftX = x - 5;
        int bottomLeftY = y + height + 5;
        int bottomRightX = x + width + 5;
        int bottomRightY = y + height + 5;
        
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
    
    // Soft-coded intelligent handle detection system with enhanced geometry and debug support
    public TextResizeHandle getTextResizeHandle(int px, int py) {
        // Soft-coded configuration parameters for easy tuning
        final int FONTSIZE_HIT_RADIUS = 12;     // Larger for font size controls
        final int SPACING_HIT_RADIUS = 10;      // Good for spacing controls
        final int ALIGNMENT_HIT_RADIUS = 8;     // Smaller for precision controls
        final int CORNER_HIT_RADIUS = 10;       // Medium for combined controls
        
        // Enhanced geometry calculations for better handle positioning
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int midY = y + height / 2;
        
        // Priority 1: Font Size Control Handles (TOP/BOTTOM) - Most Important
        int fontTopX = centerX;
        int fontTopY = y - 8;  // Extend outside for easier access
        int fontBottomX = centerX;
        int fontBottomY = y + height + 8;  // Extend outside for easier access
        
        System.out.println("📝 Font Size UP handle at (" + fontTopX + "," + fontTopY + ") - radius:" + FONTSIZE_HIT_RADIUS);
        if (isInTextHandle(px, py, fontTopX, fontTopY, FONTSIZE_HIT_RADIUS)) {
            System.out.println("✅ FONT SIZE UP detected!");
            return TextResizeHandle.TOP;
        }
        
        System.out.println("📝 Font Size DOWN handle at (" + fontBottomX + "," + fontBottomY + ") - radius:" + FONTSIZE_HIT_RADIUS);
        if (isInTextHandle(px, py, fontBottomX, fontBottomY, FONTSIZE_HIT_RADIUS)) {
            System.out.println("✅ FONT SIZE DOWN detected!");
            return TextResizeHandle.BOTTOM;
        }
        
        // Priority 2: Character Spacing Controls (LEFT/RIGHT) - Essential for text layout
        int spacingLeftX = x - 8;   // Extend outside bounds for easier access
        int spacingRightX = x + width + 8;
        
        System.out.println("⬅️ Spacing DECREASE handle at (" + spacingLeftX + "," + midY + ") - radius:" + SPACING_HIT_RADIUS);
        if (isInTextHandle(px, py, spacingLeftX, midY, SPACING_HIT_RADIUS)) {
            System.out.println("✅ SPACING DECREASE detected!");
            return TextResizeHandle.LEFT;
        }
        
        System.out.println("➡️ Spacing INCREASE handle at (" + spacingRightX + "," + midY + ") - radius:" + SPACING_HIT_RADIUS);
        if (isInTextHandle(px, py, spacingRightX, midY, SPACING_HIT_RADIUS)) {
            System.out.println("✅ SPACING INCREASE detected!");
            return TextResizeHandle.RIGHT;
        }
        
        // Priority 3: Corner Combo Controls (Font + Spacing combined)
        int topLeftX = x - 5;
        int topLeftY = y - 5;
        int topRightX = x + width + 5;
        int topRightY = y - 5;
        int bottomLeftX = x - 5;
        int bottomLeftY = y + height + 5;
        int bottomRightX = x + width + 5;
        int bottomRightY = y + height + 5;
        
        System.out.println("🔧 Top-Left combo at (" + topLeftX + "," + topLeftY + ") - radius:" + CORNER_HIT_RADIUS);
        if (isInTextHandle(px, py, topLeftX, topLeftY, CORNER_HIT_RADIUS)) {
            System.out.println("✅ TOP-LEFT COMBO detected!");
            return TextResizeHandle.TOP_LEFT;
        }
        
        System.out.println("🔧 Top-Right combo at (" + topRightX + "," + topRightY + ") - radius:" + CORNER_HIT_RADIUS);
        if (isInTextHandle(px, py, topRightX, topRightY, CORNER_HIT_RADIUS)) {
            System.out.println("✅ TOP-RIGHT COMBO detected!");
            return TextResizeHandle.TOP_RIGHT;
        }
        
        System.out.println("🔧 Bottom-Left combo at (" + bottomLeftX + "," + bottomLeftY + ") - radius:" + CORNER_HIT_RADIUS);
        if (isInTextHandle(px, py, bottomLeftX, bottomLeftY, CORNER_HIT_RADIUS)) {
            System.out.println("✅ BOTTOM-LEFT COMBO detected!");
            return TextResizeHandle.BOTTOM_LEFT;
        }
        
        System.out.println("🔧 Bottom-Right combo at (" + bottomRightX + "," + bottomRightY + ") - radius:" + CORNER_HIT_RADIUS);
        if (isInTextHandle(px, py, bottomRightX, bottomRightY, CORNER_HIT_RADIUS)) {
            System.out.println("✅ BOTTOM-RIGHT COMBO detected!");
            return TextResizeHandle.BOTTOM_RIGHT;
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
    
    // Default hit detection method (backward compatibility)
    private boolean isInHandle(int px, int py, int handleX, int handleY) {
        return isInTextHandle(px, py, handleX, handleY, HANDLE_SIZE + HANDLE_BORDER + 5);
    }
    
    // Override the base resize methods to use dynamic text resizing
    @Override
    public boolean overResizeHandle(int px, int py) {
        return getTextResizeHandle(px, py) != TextResizeHandle.NONE;
    }
    
    @Override
    public void startResize() {
        if (!getCanResize()) return;
        dynamicResizing = true;
        originalFontSize = font.getSize();
        originalCharacterWidth = characterWidth;
    }
    
    public void startDynamicResize(int startX, int startY, TextResizeHandle handle) {
        if (!getCanResize()) return;
        activeHandle = handle;
        resizeStartX = startX;
        resizeStartY = startY;
        dynamicResizing = true;
        originalFontSize = font.getSize();
        originalCharacterWidth = characterWidth;
    }
    
    // Enhanced Smart TextMark control system with full multi-directional support
    public void resizeText(int deltaX, int deltaY, TextResizeHandle handle) {
        System.out.println("📝 TextMark Multi-Directional Resize - Handle: " + handle + ", Delta: (" + deltaX + "," + deltaY + ")");
        
        // Soft-coded sensitivity parameters for fine-tuning
        final double FONTSIZE_SENSITIVITY = 0.5;      // Font size change per pixel
        final double SPACING_SENSITIVITY = 0.02;      // Character spacing change per pixel
        final double FINE_FONTSIZE_SENSITIVITY = 0.3; // Fine font size control
        final double FINE_SPACING_SENSITIVITY = 0.01; // Fine spacing control
        
        switch (handle) {
            case TOP:
                // TOP = Multi-Directional Font Size Control (Blue Circle +)
                // Vertical movement = font size increase (UP = increase)
                double fontIncrease = -deltaY * FONTSIZE_SENSITIVITY;  // Negative because up is negative
                float newTopSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)fontIncrease));
                font = font.deriveFont(newTopSize);
                
                // Horizontal movement = line spacing adjustment
                if (Math.abs(deltaX) > 3) {
                    double lineSpacingDelta = deltaX * 0.01;
                    lineSpacing = Math.max(0.5, Math.min(3.0, lineSpacing + lineSpacingDelta));
                }
                
                System.out.println("   📝 Font Size UP: " + font.getSize() + "pt" + 
                                 (Math.abs(deltaX) > 3 ? ", Line Spacing: " + String.format("%.2f", lineSpacing) : "") +
                                 " (V:" + deltaY + "→size, H:" + deltaX + "→line)");
                break;
                
            case BOTTOM:
                // BOTTOM = Multi-Directional Font Size Control (Blue Circle -)
                // Vertical movement = font size decrease (DOWN = decrease)
                double fontDecrease = deltaY * FONTSIZE_SENSITIVITY;
                float newBottomSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)fontDecrease));
                font = font.deriveFont(newBottomSize);
                
                // Horizontal movement = line spacing adjustment
                if (Math.abs(deltaX) > 3) {
                    double lineSpacingDelta = deltaX * 0.01;
                    lineSpacing = Math.max(0.5, Math.min(3.0, lineSpacing + lineSpacingDelta));
                }
                
                System.out.println("   📝 Font Size DOWN: " + font.getSize() + "pt" + 
                                 (Math.abs(deltaX) > 3 ? ", Line Spacing: " + String.format("%.2f", lineSpacing) : "") +
                                 " (V:" + deltaY + "→size, H:" + deltaX + "→line)");
                break;
                
            case LEFT:
                // LEFT = Multi-Directional Character Spacing Control (Green Rectangle ◄)
                // Horizontal = spacing decrease (LEFT = tighter)
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    double spacingDecrease = -deltaX * SPACING_SENSITIVITY;  // Negative for decrease
                    characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                        characterWidth + spacingDecrease));
                    System.out.println("   ⬅️ Spacing decreased to: " + String.format("%.2f", characterWidth) + " (H-drag dominant)");
                } else {
                    // Vertical = position adjustment
                    y += deltaY / 2;
                    System.out.println("   🔽 Position adjusted by: " + (deltaY/2) + " (V-drag dominant)");
                }
                break;
                
            case RIGHT:
                // RIGHT = Multi-Directional Character Spacing Control (Green Rectangle ►)
                // Horizontal = spacing increase (RIGHT = wider)
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    double spacingIncrease = deltaX * SPACING_SENSITIVITY;
                    characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                        characterWidth + spacingIncrease));
                    System.out.println("   ➡️ Spacing increased to: " + String.format("%.2f", characterWidth) + " (H-drag dominant)");
                } else {
                    // Vertical = position adjustment
                    y += deltaY / 2;
                    System.out.println("   🔽 Position adjusted by: " + (deltaY/2) + " (V-drag dominant)");
                }
                break;
                
            case TOP_LEFT:
                // TOP_LEFT = Multi-Directional Combo Control (Orange Diamond)
                // Horizontal = spacing decrease, Vertical = font size increase
                double tlSpacingDelta = -deltaX * FINE_SPACING_SENSITIVITY;
                double tlFontDelta = -deltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + tlSpacingDelta));
                float tlNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)tlFontDelta));
                font = font.deriveFont(tlNewSize);
                
                System.out.println("   🔧 Top-Left Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + deltaX + "→spacing, V:" + deltaY + "→font)");
                break;
                
            case TOP_RIGHT:
                // TOP_RIGHT = Multi-Directional Combo Control (Orange Diamond)
                // Horizontal = spacing increase, Vertical = font size increase
                double trSpacingDelta = deltaX * FINE_SPACING_SENSITIVITY;
                double trFontDelta = -deltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + trSpacingDelta));
                float trNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)trFontDelta));
                font = font.deriveFont(trNewSize);
                
                System.out.println("   🔧 Top-Right Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + deltaX + "→spacing, V:" + deltaY + "→font)");
                break;
                
            case BOTTOM_LEFT:
                // BOTTOM_LEFT = Multi-Directional Combo Control (Orange Diamond)
                // Horizontal = spacing decrease, Vertical = font size decrease
                double blSpacingDelta = -deltaX * FINE_SPACING_SENSITIVITY;
                double blFontDelta = deltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + blSpacingDelta));
                float blNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)blFontDelta));
                font = font.deriveFont(blNewSize);
                
                System.out.println("   🔧 Bottom-Left Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + deltaX + "→spacing, V:" + deltaY + "→font)");
                break;
                
            case BOTTOM_RIGHT:
                // BOTTOM_RIGHT = Multi-Directional Combo Control (Orange Diamond)
                // Horizontal = spacing increase, Vertical = font size decrease
                double brSpacingDelta = deltaX * FINE_SPACING_SENSITIVITY;
                double brFontDelta = deltaY * FINE_FONTSIZE_SENSITIVITY;
                
                characterWidth = Math.max(MIN_CHARACTER_WIDTH, Math.min(MAX_CHARACTER_WIDTH, 
                    characterWidth + brSpacingDelta));
                float brNewSize = Math.max(MIN_FONT_SIZE, Math.min(MAX_FONT_SIZE, 
                    font.getSize() + (float)brFontDelta));
                font = font.deriveFont(brNewSize);
                
                System.out.println("   🔧 Bottom-Right Combo - Font: " + font.getSize() + 
                                 "pt, Spacing: " + String.format("%.2f", characterWidth) +
                                 " (H:" + deltaX + "→spacing, V:" + deltaY + "→font)");
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
}
