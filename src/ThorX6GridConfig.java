import java.awt.*;

/**
 * ThorX6 Grid Configuration - Professional Grid System
 * Soft-coded grid system matching ThorX6 software standards
 * Default: 14x8 cells as per ThorX6 specification
 */
public class ThorX6GridConfig {
    
    // ==================== SOFT CODING GRID CONFIGURATION ====================
    
    // Simple ThorX6 Grid Configuration (Soft Coded)
    public static final int THORX6_GRID_COLUMNS = 14;           // 14 columns (ThorX6 standard)
    public static final int THORX6_GRID_ROWS = 8;               // 8 rows (ThorX6 standard)
    public static final boolean ENABLE_THORX6_GRID = true;      // Enable ThorX6 grid system
    public static final boolean SHOW_CELL_LABELS = false;       // Keep grid simple without labels
    public static final boolean SIMPLE_GRID_MODE = true;        // Use simple, clean grid styling
    
    // Simple Grid Visual Configuration (Soft Coded)
    public static final Color THORX6_GRID_COLOR = new Color(200, 200, 200, 180);     // Simple light gray grid
    public static final Color THORX6_GRID_LABEL_COLOR = new Color(120, 120, 120, 200); // Simple dark gray labels
    public static final Color THORX6_MAJOR_GRID_COLOR = new Color(150, 150, 150, 200); // Simple darker grid lines
    
    // Simple Grid Line Styling (Soft Coded)
    public static final float THORX6_GRID_LINE_WIDTH = 1.0f;    // Simple 1px lines
    public static final boolean USE_SOLID_LINES = true;         // Use solid lines instead of dashed
    public static final float[] THORX6_DASH_PATTERN = null;     // No dashing for simplicity
    
    // Simple Grid Typography (Soft Coded)
    public static final Font THORX6_GRID_LABEL_FONT = new Font("Arial", Font.PLAIN, 8);  // Smaller, simpler font
    public static final Font THORX6_GRID_COORD_FONT = new Font("Arial", Font.PLAIN, 8);  // Consistent simple font
    
    // Grid Behavior Configuration (Soft Coded)
    public static final boolean ENABLE_GRID_SNAPPING = true;     // Snap to grid
    public static final boolean ENABLE_MAJOR_GRID_LINES = true;  // Show major grid every 5th line
    public static final int MAJOR_GRID_INTERVAL = 5;             // Major grid line interval
    public static final double GRID_SNAP_TOLERANCE = 8.0;       // Snap distance in pixels
    
    // Grid Cell Configuration (Soft Coded)
    public static final boolean SHOW_CELL_COORDINATES = true;    // Show coordinate numbers
    public static final boolean ALTERNATE_CELL_SHADING = false;  // Checkerboard pattern
    public static final Color ALTERNATE_CELL_COLOR = new Color(245, 248, 255, 30);
    
    // Working Area Integration (Soft Coded)
    public static final boolean ALIGN_TO_WORKING_AREA = true;    // Align grid to working area
    public static final int GRID_MARGIN = 5;                     // Margin from working area edges
    
    // ==================== THORX6 GRID CALCULATION METHODS ====================
    
    /**
     * Calculate ThorX6 cell dimensions based on working area
     */
    public static Dimension calculateThorX6CellSize(int workingWidth, int workingHeight) {
        if (!ENABLE_THORX6_GRID) {
            return new Dimension(20, 20); // Default fallback
        }
        
        double cellWidth = (double) workingWidth / THORX6_GRID_COLUMNS;
        double cellHeight = (double) workingHeight / THORX6_GRID_ROWS;
        
        return new Dimension((int) cellWidth, (int) cellHeight);
    }
    
    /**
     * Get ThorX6 cell label (A1, B2, C3, etc.)
     */
    public static String getThorX6CellLabel(int column, int row) {
        if (!SHOW_CELL_LABELS) return "";
        
        // Convert to Excel-style naming: A1, B2, etc.
        char columnLetter = (char) ('A' + column);
        int rowNumber = row + 1;
        
        return columnLetter + String.valueOf(rowNumber);
    }
    
    /**
     * Check if this is a major grid line
     */
    public static boolean isMajorGridLine(int index) {
        if (!ENABLE_MAJOR_GRID_LINES) return false;
        return (index % MAJOR_GRID_INTERVAL) == 0;
    }
    
    /**
     * Get simple grid line stroke (Solid lines for simplicity)
     */
    public static BasicStroke getThorX6GridStroke(boolean isMajorLine) {
        if (USE_SOLID_LINES) {
            // Simple solid lines
            float width = isMajorLine ? THORX6_GRID_LINE_WIDTH + 0.5f : THORX6_GRID_LINE_WIDTH;
            return new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        } else {
            // Legacy dashed lines (if needed)
            if (isMajorLine && ENABLE_MAJOR_GRID_LINES) {
                return new BasicStroke(THORX6_GRID_LINE_WIDTH + 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            } else {
                return new BasicStroke(THORX6_GRID_LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            }
        }
    }
    
    /**
     * Get grid color based on line type
     */
    public static Color getThorX6GridColor(boolean isMajorLine) {
        return isMajorLine ? THORX6_MAJOR_GRID_COLOR : THORX6_GRID_COLOR;
    }
    
    /**
     * Calculate snap position for ThorX6 grid
     */
    public static Point snapToThorX6Grid(Point position, Dimension cellSize, int offsetX, int offsetY) {
        if (!ENABLE_GRID_SNAPPING) return position;
        
        // Calculate nearest grid intersection
        int gridX = Math.round((float)(position.x - offsetX) / cellSize.width) * cellSize.width + offsetX;
        int gridY = Math.round((float)(position.y - offsetY) / cellSize.height) * cellSize.height + offsetY;
        
        // Check if within snap tolerance
        double distance = Math.sqrt(Math.pow(position.x - gridX, 2) + Math.pow(position.y - gridY, 2));
        
        if (distance <= GRID_SNAP_TOLERANCE) {
            return new Point(gridX, gridY);
        }
        
        return position; // No snapping
    }
    
    /**
     * Get grid information summary for debugging
     */
    public static String getThorX6GridInfo() {
        return String.format("ThorX6 Grid: %dx%d cells, Snapping: %s, Labels: %s, Major Lines: %s", 
                           THORX6_GRID_COLUMNS, THORX6_GRID_ROWS,
                           ENABLE_GRID_SNAPPING ? "ON" : "OFF",
                           SHOW_CELL_LABELS ? "ON" : "OFF",
                           ENABLE_MAJOR_GRID_LINES ? "ON" : "OFF");
    }
    
    /**
     * Validate ThorX6 grid configuration
     */
    public static void validateThorX6GridConfig() {
        System.out.println("🎯 === ThorX6 GRID CONFIGURATION ===");
        System.out.println("   Grid Size: " + THORX6_GRID_COLUMNS + " x " + THORX6_GRID_ROWS + " cells");
        System.out.println("   Grid Enabled: " + ENABLE_THORX6_GRID);
        System.out.println("   Cell Labels: " + SHOW_CELL_LABELS);
        System.out.println("   Snapping: " + ENABLE_GRID_SNAPPING);
        System.out.println("   Major Lines: " + ENABLE_MAJOR_GRID_LINES);
        System.out.println("   Snap Tolerance: " + GRID_SNAP_TOLERANCE + "px");
        
        if (THORX6_GRID_COLUMNS != 14 || THORX6_GRID_ROWS != 8) {
            System.out.println("   ⚠️ WARNING: Non-standard ThorX6 grid size detected!");
            System.out.println("   ✅ Standard ThorX6: 14x8 cells");
        } else {
            System.out.println("   ✅ Standard ThorX6 grid configuration confirmed");
        }
    }
}