import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Corner Status Panel Configuration
 * Soft-coded left bottom corner feature with Today Print, Total Print, X, Y coordinates
 * Real-time cursor tracking within grid system
 */
public class CornerStatusConfig {
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // Corner Feature Control (Soft Coded)
    public static final boolean ENABLE_CORNER_STATUS = true;         // ENABLE left bottom corner status panel
    public static final boolean ENABLE_PRINT_COUNTERS = true;       // ENABLE Today Print / Total Print counters
    public static final boolean ENABLE_COORDINATE_TRACKING = true;   // ENABLE real-time X,Y coordinate tracking
    public static final boolean ENABLE_CORNER_DEBUGGING = true;      // Enable debug output for corner feature
    
    // Print Counter Configuration (Soft Coded)
    public static final int DEFAULT_TODAY_PRINT = 0;                 // Default Today Print counter
    public static final int DEFAULT_TOTAL_PRINT = 0;                 // Default Total Print counter
    public static final boolean AUTO_INCREMENT_COUNTERS = false;     // Auto increment on actions (future feature)
    
    // Coordinate Display Configuration (Soft Coded)
    public static final String COORDINATE_FORMAT = "%.2f";           // Coordinate decimal format
    public static final boolean SHOW_COORDINATE_LABELS = true;       // Show "X:" and "Y:" labels
    public static final boolean REAL_TIME_TRACKING = true;          // Real-time coordinate updates
    
    // Visual Configuration (Soft Coded)
    public static final int CORNER_PANEL_WIDTH = 180;               // Corner panel width
    public static final int CORNER_PANEL_HEIGHT = 80;               // Corner panel height
    public static final Color CORNER_BACKGROUND = new Color(250, 250, 250);
    public static final Color CORNER_BORDER = new Color(180, 180, 180);
    public static final Font CORNER_LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font CORNER_VALUE_FONT = new Font("Segoe UI", Font.BOLD, 11);
    
    // ==================== GLOBAL REFERENCES ====================
    
    // Static references for real-time updates
    private static JLabel todayPrintLabel = null;
    private static JLabel totalPrintLabel = null;
    private static JLabel xCoordinateLabel = null;
    private static JLabel yCoordinateLabel = null;
    
    // Counter values
    private static int todayPrintCount = DEFAULT_TODAY_PRINT;
    private static int totalPrintCount = DEFAULT_TOTAL_PRINT;
    
    /**
     * Create left bottom corner status panel (Soft Coded Implementation)
     */
    public static JPanel createCornerStatusPanel() {
        if (!ENABLE_CORNER_STATUS) {
            if (ENABLE_CORNER_DEBUGGING) {
                System.out.println("📍 Corner Status: DISABLED (soft-coded configuration)");
            }
            return new JPanel(); // Return empty panel if disabled
        }
        
        if (ENABLE_CORNER_DEBUGGING) {
            System.out.println("📍 Creating Corner Status Panel (Today Print, Total Print, X, Y)");
        }
        
        JPanel cornerPanel = new JPanel();
        cornerPanel.setLayout(new GridBagLayout());
        cornerPanel.setPreferredSize(new Dimension(CORNER_PANEL_WIDTH, CORNER_PANEL_HEIGHT));
        cornerPanel.setBackground(CORNER_BACKGROUND);
        cornerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CORNER_BORDER, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        
        // Row 1: Today Print
        if (ENABLE_PRINT_COUNTERS) {
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel todayLabel = new JLabel("Today Print:");
            todayLabel.setFont(CORNER_LABEL_FONT);
            cornerPanel.add(todayLabel, gbc);
            
            gbc.gridx = 1;
            todayPrintLabel = new JLabel(String.valueOf(todayPrintCount));
            todayPrintLabel.setFont(CORNER_VALUE_FONT);
            todayPrintLabel.setForeground(new Color(0, 120, 0));
            cornerPanel.add(todayPrintLabel, gbc);
            
            // Row 2: Total Print
            gbc.gridx = 0; gbc.gridy = 1;
            JLabel totalLabel = new JLabel("Total Print:");
            totalLabel.setFont(CORNER_LABEL_FONT);
            cornerPanel.add(totalLabel, gbc);
            
            gbc.gridx = 1;
            totalPrintLabel = new JLabel(String.valueOf(totalPrintCount));
            totalPrintLabel.setFont(CORNER_VALUE_FONT);
            totalPrintLabel.setForeground(new Color(0, 0, 150));
            cornerPanel.add(totalPrintLabel, gbc);
        }
        
        // Row 3: X Coordinate
        if (ENABLE_COORDINATE_TRACKING) {
            gbc.gridx = 0; gbc.gridy = 2;
            JLabel xLabel = new JLabel(SHOW_COORDINATE_LABELS ? "X:" : "");
            xLabel.setFont(CORNER_LABEL_FONT);
            cornerPanel.add(xLabel, gbc);
            
            gbc.gridx = 1;
            xCoordinateLabel = new JLabel("0.00");
            xCoordinateLabel.setFont(CORNER_VALUE_FONT);
            xCoordinateLabel.setForeground(new Color(150, 0, 0));
            cornerPanel.add(xCoordinateLabel, gbc);
            
            // Row 4: Y Coordinate
            gbc.gridx = 0; gbc.gridy = 3;
            JLabel yLabel = new JLabel(SHOW_COORDINATE_LABELS ? "Y:" : "");
            yLabel.setFont(CORNER_LABEL_FONT);
            cornerPanel.add(yLabel, gbc);
            
            gbc.gridx = 1;
            yCoordinateLabel = new JLabel("0.00");
            yCoordinateLabel.setFont(CORNER_VALUE_FONT);
            yCoordinateLabel.setForeground(new Color(150, 0, 0));
            cornerPanel.add(yCoordinateLabel, gbc);
        }
        
        if (ENABLE_CORNER_DEBUGGING) {
            System.out.println("✅ Corner Status Panel created with print counters and coordinate tracking");
        }
        
        return cornerPanel;
    }
    
    /**
     * Update coordinates from mouse movement (Soft Coded Real-time Tracking)
     */
    public static void updateCoordinates(double x, double y) {
        if (!ENABLE_COORDINATE_TRACKING || !REAL_TIME_TRACKING) {
            return;
        }
        
        if (xCoordinateLabel != null) {
            xCoordinateLabel.setText(String.format(COORDINATE_FORMAT, x));
        }
        
        if (yCoordinateLabel != null) {
            yCoordinateLabel.setText(String.format(COORDINATE_FORMAT, y));
        }
        
        if (ENABLE_CORNER_DEBUGGING && Math.random() < 0.01) { // Debug only 1% of updates to avoid spam
            System.out.println("📍 Coordinates updated: X=" + String.format(COORDINATE_FORMAT, x) + 
                              ", Y=" + String.format(COORDINATE_FORMAT, y));
        }
    }
    
    /**
     * Increment Today Print counter (Soft Coded)
     */
    public static void incrementTodayPrint() {
        if (!ENABLE_PRINT_COUNTERS) return;
        
        todayPrintCount++;
        if (todayPrintLabel != null) {
            todayPrintLabel.setText(String.valueOf(todayPrintCount));
        }
        
        if (ENABLE_CORNER_DEBUGGING) {
            System.out.println("📍 Today Print incremented: " + todayPrintCount);
        }
    }
    
    /**
     * Increment Total Print counter (Soft Coded)
     */
    public static void incrementTotalPrint() {
        if (!ENABLE_PRINT_COUNTERS) return;
        
        totalPrintCount++;
        if (totalPrintLabel != null) {
            totalPrintLabel.setText(String.valueOf(totalPrintCount));
        }
        
        if (ENABLE_CORNER_DEBUGGING) {
            System.out.println("📍 Total Print incremented: " + totalPrintCount);
        }
    }
    
    /**
     * Create mouse motion listener for coordinate tracking (Soft Coded)
     */
    public static MouseMotionListener createCoordinateTracker() {
        if (!ENABLE_COORDINATE_TRACKING) {
            return null;
        }
        
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (REAL_TIME_TRACKING) {
                    updateCoordinates(e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                if (REAL_TIME_TRACKING) {
                    updateCoordinates(e.getX(), e.getY());
                }
            }
        };
    }
    
    /**
     * Get current print counts (for external access)
     */
    public static int getTodayPrintCount() { return todayPrintCount; }
    public static int getTotalPrintCount() { return totalPrintCount; }
    
    /**
     * Reset counters (Soft Coded Management)
     */
    public static void resetTodayPrint() {
        if (!ENABLE_PRINT_COUNTERS) return;
        
        todayPrintCount = DEFAULT_TODAY_PRINT;
        if (todayPrintLabel != null) {
            todayPrintLabel.setText(String.valueOf(todayPrintCount));
        }
        
        if (ENABLE_CORNER_DEBUGGING) {
            System.out.println("📍 Today Print reset to: " + todayPrintCount);
        }
    }
}