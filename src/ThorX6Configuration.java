import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ThorX6Configuration - Comprehensive Soft Coding Configuration System
 * Centralizes all ThorX6 interface styling, dimensions, colors, and behavior parameters
 * Enables easy customization and maintenance of the professional interface
 */
public class ThorX6Configuration {
    
    // ==================== COLOR SCHEME (SOFT CODED) ====================
    
    // Primary ThorX6 Professional Colors
    public static final Color THORX6_DARK_BLUE = new Color(32, 48, 64);      // Main background
    public static final Color THORX6_MEDIUM_BLUE = new Color(48, 64, 80);    // Panel backgrounds  
    public static final Color THORX6_LIGHT_BLUE = new Color(64, 80, 96);     // Hover states
    public static final Color THORX6_ACCENT_BLUE = new Color(0, 122, 204);   // Active elements
    public static final Color THORX6_BRIGHT_BLUE = new Color(0, 153, 255);   // Highlights
    
    // ThorX6 Neutral Colors
    public static final Color THORX6_WHITE = new Color(255, 255, 255);       // Text/icons
    public static final Color THORX6_LIGHT_GRAY = new Color(240, 240, 240);  // Input fields
    public static final Color THORX6_MEDIUM_GRAY = new Color(200, 200, 200); // Borders
    public static final Color THORX6_DARK_GRAY = new Color(128, 128, 128);   // Disabled text
    public static final Color THORX6_BLACK = new Color(40, 40, 40);          // Strong text
    
    // ThorX6 Status Colors
    public static final Color THORX6_SUCCESS_GREEN = new Color(0, 150, 80);  // Success indicators
    public static final Color THORX6_WARNING_ORANGE = new Color(255, 165, 0); // Warning indicators
    public static final Color THORX6_ERROR_RED = new Color(220, 50, 50);     // Error indicators
    public static final Color THORX6_INFO_CYAN = new Color(0, 180, 200);     // Info indicators
    
    // ==================== DIMENSIONS (SOFT CODED) ====================
    
    // Toolbar Dimensions
    public static final int TOOLBAR_HEIGHT = 64;                    // Main toolbar height
    public static final int TOOLBAR_BUTTON_WIDTH = 56;              // Standard button width
    public static final int TOOLBAR_BUTTON_HEIGHT = 48;             // Standard button height
    public static final int TOOLBAR_SEPARATOR_WIDTH = 8;            // Separator spacing
    public static final int TOOLBAR_PADDING = 6;                    // Internal padding
    public static final int TOOLBAR_MARGIN = 4;                     // External margins
    
    // Panel Dimensions  
    public static final int PROPERTY_PANEL_WIDTH = 280;             // Property panel width
    public static final int STATUS_BAR_HEIGHT = 32;                 // Status bar height
    public static final int TAB_HEIGHT = 28;                        // Tab height
    public static final int SPLITTER_SIZE = 4;                      // Splitter thickness
    
    // Control Dimensions
    public static final int INPUT_FIELD_HEIGHT = 24;                // Input field height
    public static final int SPINNER_WIDTH = 80;                     // Spinner width
    public static final int COMBOBOX_WIDTH = 120;                   // ComboBox width
    public static final int CHECKBOX_SIZE = 16;                     // Checkbox size
    
    // ==================== FONTS (SOFT CODED) ====================
    
    // ThorX6 Font Family
    public static final String THORX6_FONT_FAMILY = "Segoe UI";
    
    // ThorX6 Font Styles
    public static final Font THORX6_MAIN_FONT = new Font(THORX6_FONT_FAMILY, Font.PLAIN, 11);
    public static final Font THORX6_BOLD_FONT = new Font(THORX6_FONT_FAMILY, Font.BOLD, 11);
    public static final Font THORX6_SMALL_FONT = new Font(THORX6_FONT_FAMILY, Font.PLAIN, 9);
    public static final Font THORX6_LARGE_FONT = new Font(THORX6_FONT_FAMILY, Font.BOLD, 13);
    public static final Font THORX6_TOOLBAR_FONT = new Font(THORX6_FONT_FAMILY, Font.PLAIN, 10);
    public static final Font THORX6_STATUS_FONT = new Font(THORX6_FONT_FAMILY, Font.PLAIN, 10);
    
    // ==================== TOOL CATEGORIES (SOFT CODED) ====================
    
    // ThorX6 Tool Categories with Icons and Descriptions
    public static final Map<String, ToolCategory> TOOL_CATEGORIES = new HashMap<>();
    
    static {
        // Selection Tools
        TOOL_CATEGORIES.put("SELECTION", new ToolCategory(
            "Selection", "🎯", "Selection and manipulation tools",
            new String[]{"Select", "Move", "Rotate", "Scale"}
        ));
        
        // Drawing Tools
        TOOL_CATEGORIES.put("DRAWING", new ToolCategory(
            "Drawing", "✏️", "Basic drawing and sketching tools", 
            new String[]{"Line", "Arc", "Circle", "Rectangle", "Polygon", "Spline"}
        ));
        
        // Text Tools
        TOOL_CATEGORIES.put("TEXT", new ToolCategory(
            "Text", "🅰️", "Text creation and formatting tools",
            new String[]{"Text", "Arc Text", "Path Text", "Dimension Text"}
        ));
        
        // Pattern Tools
        TOOL_CATEGORIES.put("PATTERN", new ToolCategory(
            "Pattern", "🔳", "Pattern and matrix generation tools",
            new String[]{"Dot Matrix", "Grid Pattern", "Array Copy", "Hatch Fill"}
        ));
        
        // Technical Tools
        TOOL_CATEGORIES.put("TECHNICAL", new ToolCategory(
            "Technical", "📐", "Technical drawing and measurement tools",
            new String[]{"Dimension", "Leader", "Symbol", "Table", "Graph"}
        ));
        
        // Special Tools
        TOOL_CATEGORIES.put("SPECIAL", new ToolCategory(
            "Special", "⚡", "Specialized marking tools",
            new String[]{"Barcode", "QR Code", "Serial Number", "Date/Time"}
        ));
    }
    
    // ==================== BUTTON STYLES (SOFT CODED) ====================
    
    // ThorX6 Button Style Configuration
    public static final int BUTTON_BORDER_RADIUS = 4;               // Rounded corners
    public static final int BUTTON_BORDER_WIDTH = 1;                // Border thickness
    public static final int BUTTON_ICON_SIZE = 24;                  // Icon dimensions
    public static final int BUTTON_TEXT_OFFSET = 2;                 // Text spacing
    
    // Button State Colors
    public static final Color BUTTON_NORMAL_BG = THORX6_MEDIUM_BLUE;
    public static final Color BUTTON_HOVER_BG = THORX6_LIGHT_BLUE;
    public static final Color BUTTON_PRESSED_BG = THORX6_DARK_BLUE;
    public static final Color BUTTON_SELECTED_BG = THORX6_ACCENT_BLUE;
    public static final Color BUTTON_DISABLED_BG = THORX6_DARK_GRAY;
    
    // ==================== LAYOUT CONFIGURATION (SOFT CODED) ====================
    
    // Grid Settings
    public static final int GRID_SIZE = 10;                         // Grid spacing
    public static final boolean SHOW_GRID_BY_DEFAULT = true;        // Grid visibility
    public static final Color GRID_COLOR = new Color(80, 80, 80);   // Grid line color
    
    // Snap Settings
    public static final int SNAP_TOLERANCE = 8;                     // Snap distance
    public static final boolean SNAP_TO_GRID = true;                // Grid snapping
    public static final boolean SNAP_TO_OBJECTS = true;             // Object snapping
    
    // ==================== ANIMATION SETTINGS (SOFT CODED) ====================
    
    // Animation Timing
    public static final int HOVER_ANIMATION_DURATION = 150;         // Hover effect time (ms)
    public static final int CLICK_ANIMATION_DURATION = 100;         // Click effect time (ms)
    public static final int PANEL_SLIDE_DURATION = 200;             // Panel slide time (ms)
    
    // ==================== TOOL CATEGORY CLASS ====================
    
    /**
     * Tool Category Configuration Class
     */
    public static class ToolCategory {
        public final String name;
        public final String icon;
        public final String description;
        public final String[] tools;
        
        public ToolCategory(String name, String icon, String description, String[] tools) {
            this.name = name;
            this.icon = icon;
            this.description = description;
            this.tools = tools;
        }
    }
    
    // ==================== CONFIGURATION METHODS ====================
    
    /**
     * Get toolbar button dimensions with soft-coded values
     */
    public static Dimension getToolbarButtonSize() {
        return new Dimension(TOOLBAR_BUTTON_WIDTH, TOOLBAR_BUTTON_HEIGHT);
    }
    
    /**
     * Get input field dimensions with soft-coded values
     */
    public static Dimension getInputFieldSize() {
        return new Dimension(SPINNER_WIDTH, INPUT_FIELD_HEIGHT);
    }
    
    /**
     * Get professional border with ThorX6 styling
     */
    public static javax.swing.border.Border getThorX6Border() {
        return javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createRaisedBevelBorder(),
            javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4)
        );
    }
    
    /**
     * Get ThorX6 panel background color based on level
     */
    public static Color getPanelBackground(int level) {
        switch (level) {
            case 0: return THORX6_DARK_BLUE;      // Main background
            case 1: return THORX6_MEDIUM_BLUE;    // Panel level 1
            case 2: return THORX6_LIGHT_BLUE;     // Panel level 2  
            default: return THORX6_MEDIUM_BLUE;
        }
    }
    
    /**
     * Get status color by type
     */
    public static Color getStatusColor(String status) {
        switch (status.toLowerCase()) {
            case "success": case "ok": case "ready": return THORX6_SUCCESS_GREEN;
            case "warning": case "caution": return THORX6_WARNING_ORANGE;
            case "error": case "fail": case "problem": return THORX6_ERROR_RED;
            case "info": case "note": return THORX6_INFO_CYAN;
            default: return THORX6_WHITE;
        }
    }
    
    /**
     * Create ThorX6-style insets with soft-coded padding
     */
    public static Insets getThorX6Insets(int multiplier) {
        int base = TOOLBAR_PADDING * multiplier;
        return new Insets(base, base, base, base);
    }
}