/**
 * Vertical Layout Configuration System
 * Soft-coded parameters for optimizing tab and logo alignment to maximize canvas space
 */
public class VerticalLayoutConfiguration {
    
    // ==================== VERTICAL ALIGNMENT CONFIGURATION ====================
    
    // Layout orientation settings (soft-coded)
    public static final boolean ENABLE_VERTICAL_TAB_LAYOUT = false;       // Disable vertical tab layout (reverted)
    public static final boolean ALIGN_LOGO_WITH_TABS = true;              // Align logo with tab column
    public static final boolean MAXIMIZE_CANVAS_SPACE = true;             // Optimize for maximum canvas area
    
    // Tab positioning and dimensions (soft-coded)
    public static final int VERTICAL_TAB_WIDTH = 120;                     // Width of vertical tabs
    public static final int VERTICAL_TAB_HEIGHT = 45;                     // Height of each tab
    public static final int TAB_SPACING = 5;                              // Spacing between tabs
    public static final int TAB_MARGIN_TOP = 10;                          // Top margin for tab column
    public static final int TAB_MARGIN_BOTTOM = 10;                       // Bottom margin for tab column
    
    // Logo positioning with tabs (soft-coded)
    public static final int LOGO_TAB_ALIGNMENT = 0;                       // 0=top-aligned, 1=center, 2=bottom
    public static final int LOGO_TAB_SPACING = 15;                        // Space between logo and first tab
    public static final int LOGO_SIZE_VERTICAL = 65;                      // Logo size in vertical layout
    public static final boolean LOGO_ABOVE_TABS = true;                   // Position logo above tab column
    
    // Canvas space optimization (soft-coded)
    public static final int LEFT_PANEL_WIDTH = VERTICAL_TAB_WIDTH + 15;   // Total width of left panel (reduced)
    public static final int CANVAS_MARGIN_LEFT = 3;                       // Minimal left margin for maximum space
    public static final int CANVAS_MARGIN_RIGHT = 15;                     // Reduced right margin for canvas
    public static final int CANVAS_MARGIN_TOP = 5;                        // Minimal top margin for canvas
    public static final int CANVAS_MARGIN_BOTTOM = 15;                    // Reduced bottom margin for canvas
    
    // Professional styling (soft-coded)
    public static final java.awt.Color VERTICAL_TAB_BACKGROUND = new java.awt.Color(248, 249, 250);
    public static final java.awt.Color VERTICAL_TAB_SELECTED = new java.awt.Color(0, 120, 215);
    public static final java.awt.Color VERTICAL_TAB_HOVER = new java.awt.Color(240, 240, 245);
    public static final java.awt.Color VERTICAL_TAB_TEXT = new java.awt.Color(60, 60, 60);
    public static final java.awt.Color VERTICAL_TAB_BORDER = new java.awt.Color(225, 225, 225);
    
    // Animation settings (soft-coded)
    public static final boolean ENABLE_TAB_ANIMATIONS = true;             // Enable smooth tab transitions
    public static final int ANIMATION_DURATION_MS = 200;                  // Animation duration in milliseconds
    public static final boolean ENABLE_LOGO_HOVER_EFFECTS = true;         // Enable logo hover animations
    
    // Space optimization calculations (soft-coded methods)
    
    /**
     * Calculate total height needed for vertical tab column
     */
    public static int calculateTabColumnHeight(int numberOfTabs) {
        return TAB_MARGIN_TOP + 
               (numberOfTabs * VERTICAL_TAB_HEIGHT) + 
               ((numberOfTabs - 1) * TAB_SPACING) + 
               TAB_MARGIN_BOTTOM;
    }
    
    /**
     * Calculate logo position relative to tab column
     */
    public static int calculateLogoYPosition(int tabColumnStartY, int tabColumnHeight) {
        switch (LOGO_TAB_ALIGNMENT) {
            case 1: // Center alignment
                return tabColumnStartY + (tabColumnHeight / 2) - (LOGO_SIZE_VERTICAL / 2);
            case 2: // Bottom alignment
                return tabColumnStartY + tabColumnHeight - LOGO_SIZE_VERTICAL;
            default: // Top alignment (0)
                if (LOGO_ABOVE_TABS) {
                    return tabColumnStartY - LOGO_SIZE_VERTICAL - LOGO_TAB_SPACING;
                } else {
                    return tabColumnStartY;
                }
        }
    }
    
    /**
     * Calculate available canvas width with vertical layout
     */
    public static int calculateCanvasWidth(int totalFrameWidth) {
        return totalFrameWidth - LEFT_PANEL_WIDTH - CANVAS_MARGIN_LEFT - CANVAS_MARGIN_RIGHT;
    }
    
    /**
     * Calculate available canvas height with vertical layout
     */
    public static int calculateCanvasHeight(int totalFrameHeight) {
        return totalFrameHeight - CANVAS_MARGIN_TOP - CANVAS_MARGIN_BOTTOM - 80; // 80 for property strip and title
    }
    
    // ==================== TAB CONFIGURATION DATA ====================
    
    // Soft-coded tab definitions
    public static class TabDefinition {
        public final String title;
        public final String icon;
        public final String tooltip;
        public final java.awt.Color accentColor;
        
        public TabDefinition(String title, String icon, String tooltip, java.awt.Color accentColor) {
            this.title = title;
            this.icon = icon;
            this.tooltip = tooltip;
            this.accentColor = accentColor;
        }
    }
    
    // Soft-coded tab definitions with professional styling
    public static final TabDefinition[] VERTICAL_TABS = {
        new TabDefinition("Mark", "✏️", "Design and create marks with professional tools", 
                         new java.awt.Color(0, 120, 215)),
        new TabDefinition("Typeset", "📝", "Advanced text formatting and typography", 
                         new java.awt.Color(16, 137, 62)),
        new TabDefinition("Print", "🖨️", "Professional printing and engraving options", 
                         new java.awt.Color(196, 89, 17)),
        new TabDefinition("Database", "🗃️", "Project and template management system", 
                         new java.awt.Color(142, 68, 173)),
        new TabDefinition("Barcode", "⬚", "Barcode generation and configuration", 
                         new java.awt.Color(68, 114, 196))
    };
    
    // ==================== RESPONSIVE LAYOUT SETTINGS ====================
    
    // Soft-coded responsive behavior
    public static final int MIN_CANVAS_WIDTH = 600;                       // Minimum canvas width
    public static final int MIN_CANVAS_HEIGHT = 400;                      // Minimum canvas height
    public static final boolean AUTO_ADJUST_TAB_SIZE = true;              // Auto-adjust tab size for small screens
    public static final boolean COLLAPSE_TO_ICONS_WHEN_NARROW = true;     // Show only icons when space is limited
    public static final int NARROW_THRESHOLD_WIDTH = 900;                 // Width threshold for narrow mode
    
    /**
     * Check if layout should switch to narrow mode
     */
    public static boolean shouldUseNarrowMode(int frameWidth) {
        return COLLAPSE_TO_ICONS_WHEN_NARROW && frameWidth < NARROW_THRESHOLD_WIDTH;
    }
    
    /**
     * Get tab width based on screen size
     */
    public static int getAdaptiveTabWidth(int frameWidth) {
        if (shouldUseNarrowMode(frameWidth)) {
            return 50; // Icon-only mode
        }
        return AUTO_ADJUST_TAB_SIZE ? Math.max(80, VERTICAL_TAB_WIDTH) : VERTICAL_TAB_WIDTH;
    }
    
    // ==================== CANVAS FRAME STYLING ====================
    
    // Soft-coded canvas frame configuration
    public static final java.awt.Color CANVAS_FRAME_COLOR = java.awt.Color.GRAY;
    public static final int CANVAS_FRAME_THICKNESS = 20;                  // Reduced thickness for more space
    public static final java.awt.Color CANVAS_BACKGROUND = java.awt.Color.WHITE;
    public static final boolean SHOW_CANVAS_SHADOW = true;                // Add drop shadow to canvas
    public static final java.awt.Color CANVAS_SHADOW_COLOR = new java.awt.Color(0, 0, 0, 30);
    
    // ==================== PROPERTY STRIP CONFIGURATION ====================
    
    // Soft-coded property strip positioning
    public static final int PROPERTY_STRIP_HEIGHT = 45;                   // Height of property strip
    public static final boolean PROPERTY_STRIP_SPANS_FULL_WIDTH = true;   // Span full width or just canvas area
    public static final java.awt.Color PROPERTY_STRIP_BACKGROUND = new java.awt.Color(250, 250, 250);
    
    /**
     * Calculate property strip width based on layout
     */
    public static int calculatePropertyStripWidth(int totalFrameWidth) {
        if (PROPERTY_STRIP_SPANS_FULL_WIDTH) {
            return totalFrameWidth;
        } else {
            return calculateCanvasWidth(totalFrameWidth) + CANVAS_MARGIN_LEFT + CANVAS_MARGIN_RIGHT;
        }
    }
}