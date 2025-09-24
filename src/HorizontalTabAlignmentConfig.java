/**
 * Horizontal Tab Alignment Configuration System
 * Soft-coded parameters for optimizing tab alignment with Rugrel logo to save space and improve aesthetics
 */
public class HorizontalTabAlignmentConfig {
    
    // ==================== TAB-LOGO ALIGNMENT CONFIGURATION ====================
    
    // Alignment strategy settings (soft-coded)
    public static final boolean ENABLE_TAB_LOGO_ALIGNMENT = true;          // Enable tab-logo alignment optimization
    public static final boolean COMPACT_LAYOUT_MODE = true;               // Enable compact layout for space saving
    public static final boolean PROFESSIONAL_SPACING = true;              // Use professional spacing standards
    
    // Logo positioning (soft-coded)
    public static final int LOGO_SIZE_ALIGNED = 58;                       // Optimized logo size for alignment
    public static final int LOGO_MARGIN_LEFT = 8;                         // Left margin for logo
    public static final int LOGO_MARGIN_TOP = 6;                          // Top margin for logo alignment with tabs
    public static final int LOGO_TAB_GAP = 5;                             // Small gap to prevent overlap while staying close
    
    // Tab alignment settings (soft-coded)
    public static final int TAB_HEIGHT_ALIGNED = 42;                      // Tab height to match logo alignment
    public static final int TAB_PADDING_HORIZONTAL = 16;                  // Horizontal padding inside tabs
    public static final int TAB_PADDING_VERTICAL = 8;                     // Vertical padding inside tabs
    public static final int TAB_SPACING = 2;                              // Spacing between tabs
    public static final int TAB_MIN_WIDTH = 85;                           // Minimum tab width
    public static final int TAB_MAX_WIDTH = 120;                          // Maximum tab width for consistency
    
    // Space optimization (soft-coded)
    public static final int TOP_PANEL_HEIGHT_COMPACT = 52;                // Compact top panel height
    public static final int TOTAL_HEADER_HEIGHT = 58;                     // Total height of header area
    public static final boolean OVERLAP_LOGO_TABS = false;                // Allow slight overlap for space saving
    public static final int OVERLAP_PIXELS = 0;                           // Overlap amount if enabled
    
    // Professional styling (soft-coded)
    public static final java.awt.Color ALIGNED_TAB_BACKGROUND = new java.awt.Color(248, 249, 250);
    public static final java.awt.Color ALIGNED_TAB_SELECTED = new java.awt.Color(255, 255, 255);
    public static final java.awt.Color ALIGNED_TAB_HOVER = new java.awt.Color(240, 245, 250);
    public static final java.awt.Color ALIGNED_TAB_ACCENT = new java.awt.Color(0, 120, 215);
    public static final java.awt.Color ALIGNED_TAB_BORDER = new java.awt.Color(225, 225, 225);
    public static final java.awt.Color ALIGNED_HEADER_BACKGROUND = new java.awt.Color(248, 249, 250);
    
    // Typography alignment (soft-coded)
    public static final java.awt.Font ALIGNED_TAB_FONT = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13);
    public static final java.awt.Font ALIGNED_TAB_FONT_SELECTED = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13);
    public static final java.awt.Color ALIGNED_TAB_TEXT_COLOR = new java.awt.Color(60, 60, 60);
    public static final java.awt.Color ALIGNED_TAB_TEXT_SELECTED = new java.awt.Color(0, 120, 215);
    
    // ==================== TAB DEFINITION DATA ====================
    
    // Soft-coded tab configuration for aligned layout
    public static class AlignedTabDefinition {
        public final String title;
        public final String icon;
        public final String tooltip;
        public final java.awt.Color accentColor;
        public final int preferredWidth;
        
        public AlignedTabDefinition(String title, String icon, String tooltip, 
                                   java.awt.Color accentColor, int preferredWidth) {
            this.title = title;
            this.icon = icon;
            this.tooltip = tooltip;
            this.accentColor = accentColor;
            this.preferredWidth = preferredWidth;
        }
    }
    
    // Soft-coded aligned tab definitions
    public static final AlignedTabDefinition[] ALIGNED_TABS = {
        new AlignedTabDefinition("Mark", "✏️", "Design and create marks with professional tools", 
                               new java.awt.Color(0, 120, 215), 90),
        new AlignedTabDefinition("Typeset", "📝", "Advanced text formatting and typography", 
                               new java.awt.Color(16, 137, 62), 95),
        new AlignedTabDefinition("Print", "🖨️", "Professional printing and engraving options", 
                               new java.awt.Color(196, 89, 17), 85),
        new AlignedTabDefinition("Database", "🗃️", "Project and template management system", 
                               new java.awt.Color(142, 68, 173), 110),
        new AlignedTabDefinition("Barcode", "⬚", "Barcode generation and configuration", 
                               new java.awt.Color(68, 114, 196), 95)
    };
    
    // ==================== LAYOUT CALCULATION METHODS ====================
    
    /**
     * Calculate optimal tab positions for alignment with logo
     * Tabs start immediately after the circle for perfect alignment
     */
    public static int calculateTabStartX() {
        return LOGO_MARGIN_LEFT + LOGO_SIZE_ALIGNED + LOGO_TAB_GAP;
    }
    
    /**
     * Calculate tab start position adjacent to logo (no overlap)
     */
    public static int calculateDirectTabStartX() {
        return LOGO_MARGIN_LEFT + LOGO_SIZE_ALIGNED + 3; // Start right after logo with small spacing
    }
    
    /**
     * Check if visual connection between logo and first tab should be drawn
     */
    public static boolean shouldShowLogoTabConnection() {
        return ENABLE_LOGO_TAB_CONNECTION;
    }
    
    /**
     * Debug method to print positioning values
     */
    public static void printAlignmentDebugInfo() {
        System.out.println("🔧 Logo Alignment Debug:");
        System.out.println("   Logo starts at: " + LOGO_MARGIN_LEFT + "px");
        System.out.println("   Logo ends at: " + (LOGO_MARGIN_LEFT + LOGO_SIZE_ALIGNED) + "px");
        System.out.println("   Gap size: " + LOGO_TAB_GAP + "px");
        System.out.println("   Tabs start at: " + calculateTabStartX() + "px");
        System.out.println("   Direct tabs start at: " + calculateDirectTabStartX() + "px");
    }
    
    /**
     * Calculate tab width based on available space and content
     */
    public static int calculateOptimalTabWidth(String tabTitle, int availableSpace, int numberOfTabs) {
        // Base calculation on content length and available space
        int contentBasedWidth = Math.max(TAB_MIN_WIDTH, tabTitle.length() * 8 + TAB_PADDING_HORIZONTAL * 2);
        int spaceBasedWidth = (availableSpace - calculateTabStartX()) / numberOfTabs - TAB_SPACING;
        
        // Use the smaller of content-based or space-based, but respect min/max limits
        int optimalWidth = Math.min(contentBasedWidth, spaceBasedWidth);
        return Math.max(TAB_MIN_WIDTH, Math.min(TAB_MAX_WIDTH, optimalWidth));
    }
    
    /**
     * Calculate total width needed for all tabs
     */
    public static int calculateTotalTabsWidth() {
        int totalWidth = 0;
        for (AlignedTabDefinition tab : ALIGNED_TABS) {
            totalWidth += tab.preferredWidth + TAB_SPACING;
        }
        return totalWidth - TAB_SPACING; // Remove last spacing
    }
    
    /**
     * Calculate Y position for vertical alignment of logo and tabs
     */
    public static int calculateAlignedYPosition() {
        return LOGO_MARGIN_TOP;
    }
    
    /**
     * Check if compact mode should be used based on window width
     */
    public static boolean shouldUseCompactMode(int windowWidth) {
        int requiredWidth = calculateTabStartX() + calculateTotalTabsWidth() + 50; // 50px buffer
        return COMPACT_LAYOUT_MODE && (windowWidth < requiredWidth + 200);
    }
    
    // ==================== CANVAS SPACE OPTIMIZATION ====================
    
    /**
     * Calculate canvas area with optimized header
     */
    public static java.awt.Rectangle calculateOptimizedCanvasArea(int frameWidth, int frameHeight) {
        int canvasX = 10;  // Minimal left margin
        int canvasY = TOTAL_HEADER_HEIGHT + 5;  // Just below optimized header
        int canvasWidth = frameWidth - 40;  // Minimal side margins
        int canvasHeight = frameHeight - canvasY - 60;  // Space for property strip
        
        return new java.awt.Rectangle(canvasX, canvasY, canvasWidth, canvasHeight);
    }
    
    /**
     * Calculate space saved compared to original layout
     */
    public static int calculateSpaceSaved() {
        int originalHeaderHeight = 80;  // Approximate original header height
        return originalHeaderHeight - TOTAL_HEADER_HEIGHT;
    }
    
    // ==================== VISUAL ENHANCEMENT SETTINGS ====================
    
    // Animation and visual effects (soft-coded)
    public static final boolean ENABLE_TAB_ANIMATIONS = true;             // Smooth tab transitions
    public static final boolean ENABLE_LOGO_TAB_SYNC_EFFECTS = true;      // Synchronized hover effects
    public static final int ANIMATION_DURATION_MS = 150;                  // Animation speed
    public static final boolean SHOW_ALIGNMENT_GUIDES = false;            // Debug: show alignment guides
    
    // Shadow and depth effects (soft-coded)
    public static final boolean ENABLE_TAB_SHADOWS = true;                // Subtle tab shadows
    public static final java.awt.Color TAB_SHADOW_COLOR = new java.awt.Color(0, 0, 0, 15);
    public static final boolean ENABLE_LOGO_TAB_CONNECTION = false;        // Visual connection line
    
    // Responsive behavior (soft-coded)
    public static final boolean AUTO_ADJUST_TAB_SIZES = true;             // Auto-adjust for different screen sizes
    public static final int NARROW_SCREEN_THRESHOLD = 1000;               // Threshold for narrow screen adjustments
    public static final boolean COLLAPSE_TO_ICONS_ON_NARROW = false;      // Keep text visible
    
    /**
     * Get responsive tab configuration based on screen width
     */
    public static AlignedTabDefinition[] getResponsiveTabConfig(int screenWidth) {
        if (screenWidth < NARROW_SCREEN_THRESHOLD && AUTO_ADJUST_TAB_SIZES) {
            // Return tabs with adjusted widths for narrow screens
            AlignedTabDefinition[] narrowTabs = new AlignedTabDefinition[ALIGNED_TABS.length];
            for (int i = 0; i < ALIGNED_TABS.length; i++) {
                AlignedTabDefinition original = ALIGNED_TABS[i];
                narrowTabs[i] = new AlignedTabDefinition(
                    original.title,
                    original.icon,
                    original.tooltip,
                    original.accentColor,
                    Math.max(TAB_MIN_WIDTH, original.preferredWidth - 15)  // Reduce width by 15px
                );
            }
            return narrowTabs;
        }
        return ALIGNED_TABS;
    }
    
    // ==================== AESTHETIC ENHANCEMENT ====================
    
    /**
     * Professional gradient colors for enhanced visual appeal
     */
    public static final java.awt.Color GRADIENT_START = new java.awt.Color(250, 250, 252);
    public static final java.awt.Color GRADIENT_END = new java.awt.Color(245, 246, 248);
    
    /**
     * Calculate gradient paint for professional header background
     */
    public static java.awt.GradientPaint createHeaderGradient(int width, int height) {
        return new java.awt.GradientPaint(
            0, 0, GRADIENT_START,
            0, height, GRADIENT_END
        );
    }
}