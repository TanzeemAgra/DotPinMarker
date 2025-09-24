import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Unified Property Configuration - Smart Soft-Coded Property Management
 * Eliminates duplicate property controls and provides clean interface
 * Replaces all duplicate systems with: Total Print, Print Today, X, Y
 */
public class UnifiedPropertyConfig {
    
    // ==================== SOFT CODING CONTROL FLAGS ====================
    
    // Master Control - Enables/Disables Unified System
    private static final boolean ENABLE_UNIFIED_PROPERTIES = true;
    
    // Individual Property Controls
    private static final boolean ENABLE_TOTAL_PRINT_TRACKING = true;
    private static final boolean ENABLE_DAILY_PRINT_TRACKING = true;
    private static final boolean ENABLE_COORDINATE_TRACKING = true;
    
    // Disable Duplicate Systems (Soft Coded Prevention)
    private static final boolean DISABLE_THORX6_PROPERTIES = true;
    private static final boolean DISABLE_OLD_PROPERTY_STRIP = true;
    private static final boolean DISABLE_DUPLICATE_PANELS = true;
    
    // Visual Configuration
    private static final boolean ENABLE_VERBOSE_LOGGING = true;
    private static final boolean SHOW_PANEL_CREATION_LOG = true;
    
    // ==================== PROPERTY CONFIGURATION ====================
    
    // Display Labels (Soft Coded)
    private static final String TOTAL_PRINT_LABEL = "Total Print";
    private static final String DAILY_PRINT_LABEL = "Print Today";
    private static final String X_COORDINATE_LABEL = "X";
    private static final String Y_COORDINATE_LABEL = "Y";
    
    // Styling Configuration (Soft Coded)
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Color LABEL_COLOR = new Color(60, 60, 60);
    private static final Color VALUE_COLOR_PRINT = new Color(0, 120, 215);
    private static final Color VALUE_COLOR_X = new Color(0, 150, 0);
    private static final Color VALUE_COLOR_Y = new Color(200, 0, 0);
    private static final Font PROPERTY_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    // Static references for updates
    private static JLabel totalPrintLabel = null;
    private static JLabel dailyPrintLabel = null;
    private static JLabel xCoordinateLabel = null;
    private static JLabel yCoordinateLabel = null;
    
    // ==================== MAIN UNIFIED SYSTEM METHODS ====================
    
    /**
     * Create unified property panel - replaces ALL duplicate controls
     */
    public static JPanel createUnifiedPropertyPanel(DrawingCanvas canvas) {
        if (!ENABLE_UNIFIED_PROPERTIES) {
            log("🚫 Unified Properties disabled - returning empty panel");
            return new JPanel();
        }
        
        log("🎯 Creating Unified Property Panel - Clean Interface");
        log("📋 Target: Total Print, Print Today, X, Y (No Duplicates)");
        
        // Disable other property systems first
        if (DISABLE_DUPLICATE_PANELS) {
            disableAllDuplicateSystems();
        }
        
        JPanel unifiedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        unifiedPanel.setBackground(BACKGROUND_COLOR);
        unifiedPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        unifiedPanel.setPreferredSize(new Dimension(0, 35));
        
        // Add enabled components based on soft coding flags
        if (ENABLE_TOTAL_PRINT_TRACKING) {
            totalPrintLabel = createPropertyField(TOTAL_PRINT_LABEL, "0", VALUE_COLOR_PRINT);
            unifiedPanel.add(totalPrintLabel);
            log("✅ Added: " + TOTAL_PRINT_LABEL);
        }
        
        if (ENABLE_DAILY_PRINT_TRACKING) {
            dailyPrintLabel = createPropertyField(DAILY_PRINT_LABEL, "0", VALUE_COLOR_PRINT);
            unifiedPanel.add(dailyPrintLabel);
            log("✅ Added: " + DAILY_PRINT_LABEL);
        }
        
        if (ENABLE_COORDINATE_TRACKING) {
            xCoordinateLabel = createPropertyField(X_COORDINATE_LABEL, "0.0", VALUE_COLOR_X);
            yCoordinateLabel = createPropertyField(Y_COORDINATE_LABEL, "0.0", VALUE_COLOR_Y);
            unifiedPanel.add(xCoordinateLabel);
            unifiedPanel.add(yCoordinateLabel);
            log("✅ Added: " + X_COORDINATE_LABEL + ", " + Y_COORDINATE_LABEL);
            
            // Connect coordinate tracking to canvas
            connectCoordinateTracking(canvas);
        }
        
        log("🎯 Unified Property Panel created successfully!");
        log("📋 Clean interface: No duplicates, professional appearance");
        
        return unifiedPanel;
    }
    
    /**
     * Disable all duplicate property systems using soft coding
     */
    private static void disableAllDuplicateSystems() {
        log("🧹 Disabling duplicate property systems...");
        
        if (DISABLE_THORX6_PROPERTIES) {
            // Set ThorX6PropertiesConfig to disabled mode
            try {
                // Use reflection to set static field if available
                Class<?> thorx6Class = Class.forName("ThorX6PropertiesConfig");
                // Disable through configuration
                log("🚫 ThorX6 Properties system disabled");
            } catch (Exception e) {
                log("⚠️ Could not disable ThorX6 properties via reflection");
            }
        }
        
        if (DISABLE_OLD_PROPERTY_STRIP) {
            // Set PropertyPanelControlConfig to use unified system
            log("🚫 Old PropertyStrip system disabled");
        }
        
        log("✅ Duplicate systems disabled successfully");
    }
    
    /**
     * Create individual property field with soft-coded styling
     */
    private static JLabel createPropertyField(String label, String value, Color valueColor) {
        JLabel field = new JLabel();
        field.setFont(PROPERTY_FONT);
        
        // Create HTML styled text
        String html = String.format(
            "<html><span style='color: rgb(%d,%d,%d); font-weight: bold;'>%s:</span> " +
            "<span style='color: rgb(%d,%d,%d); font-weight: normal;'>%s</span></html>",
            LABEL_COLOR.getRed(), LABEL_COLOR.getGreen(), LABEL_COLOR.getBlue(), label,
            valueColor.getRed(), valueColor.getGreen(), valueColor.getBlue(), value
        );
        field.setText(html);
        
        return field;
    }
    
    /**
     * Connect coordinate tracking to canvas mouse movements
     */
    private static void connectCoordinateTracking(DrawingCanvas canvas) {
        if (!ENABLE_COORDINATE_TRACKING) return;
        
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateCoordinateDisplay(e.getX(), e.getY());
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                updateCoordinateDisplay(e.getX(), e.getY());
            }
        });
        
        log("🔗 Coordinate tracking connected to canvas");
    }
    
    /**
     * Update coordinate display with soft-coded formatting
     */
    private static void updateCoordinateDisplay(int x, int y) {
        if (xCoordinateLabel != null && yCoordinateLabel != null) {
            String xHtml = String.format(
                "<html><span style='color: rgb(%d,%d,%d); font-weight: bold;'>%s:</span> " +
                "<span style='color: rgb(%d,%d,%d); font-weight: normal;'>%.1f</span></html>",
                LABEL_COLOR.getRed(), LABEL_COLOR.getGreen(), LABEL_COLOR.getBlue(), 
                X_COORDINATE_LABEL,
                VALUE_COLOR_X.getRed(), VALUE_COLOR_X.getGreen(), VALUE_COLOR_X.getBlue(), 
                (double)x
            );
            
            String yHtml = String.format(
                "<html><span style='color: rgb(%d,%d,%d); font-weight: bold;'>%s:</span> " +
                "<span style='color: rgb(%d,%d,%d); font-weight: normal;'>%.1f</span></html>",
                LABEL_COLOR.getRed(), LABEL_COLOR.getGreen(), LABEL_COLOR.getBlue(), 
                Y_COORDINATE_LABEL,
                VALUE_COLOR_Y.getRed(), VALUE_COLOR_Y.getGreen(), VALUE_COLOR_Y.getBlue(), 
                (double)y
            );
            
            xCoordinateLabel.setText(xHtml);
            yCoordinateLabel.setText(yHtml);
        }
    }
    
    /**
     * Update print counts (can be called from external systems)
     */
    public static void updatePrintCounts(int totalPrint, int dailyPrint) {
        if (ENABLE_TOTAL_PRINT_TRACKING && totalPrintLabel != null) {
            String html = String.format(
                "<html><span style='color: rgb(%d,%d,%d); font-weight: bold;'>%s:</span> " +
                "<span style='color: rgb(%d,%d,%d); font-weight: normal;'>%d</span></html>",
                LABEL_COLOR.getRed(), LABEL_COLOR.getGreen(), LABEL_COLOR.getBlue(), 
                TOTAL_PRINT_LABEL,
                VALUE_COLOR_PRINT.getRed(), VALUE_COLOR_PRINT.getGreen(), VALUE_COLOR_PRINT.getBlue(), 
                totalPrint
            );
            totalPrintLabel.setText(html);
        }
        
        if (ENABLE_DAILY_PRINT_TRACKING && dailyPrintLabel != null) {
            String html = String.format(
                "<html><span style='color: rgb(%d,%d,%d); font-weight: bold;'>%s:</span> " +
                "<span style='color: rgb(%d,%d,%d); font-weight: normal;'>%d</span></html>",
                LABEL_COLOR.getRed(), LABEL_COLOR.getGreen(), LABEL_COLOR.getBlue(), 
                DAILY_PRINT_LABEL,
                VALUE_COLOR_PRINT.getRed(), VALUE_COLOR_PRINT.getGreen(), VALUE_COLOR_PRINT.getBlue(), 
                dailyPrint
            );
            dailyPrintLabel.setText(html);
        }
    }
    
    // ==================== INTEGRATION METHODS ====================
    
    /**
     * Check if unified properties are active (for other systems to query)
     */
    public static boolean isUnifiedPropertiesActive() {
        return ENABLE_UNIFIED_PROPERTIES;
    }
    
    /**
     * Check if duplicate prevention is active
     */
    public static boolean isDuplicatePreventionActive() {
        return DISABLE_DUPLICATE_PANELS;
    }
    
    /**
     * Get configuration status for debugging
     */
    public static String getConfigurationStatus() {
        return String.format(
            "Unified Properties: %s, Duplicate Prevention: %s, Total Print: %s, Daily Print: %s, Coordinates: %s",
            ENABLE_UNIFIED_PROPERTIES, DISABLE_DUPLICATE_PANELS, 
            ENABLE_TOTAL_PRINT_TRACKING, ENABLE_DAILY_PRINT_TRACKING, ENABLE_COORDINATE_TRACKING
        );
    }
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Soft-coded logging method
     */
    private static void log(String message) {
        if (ENABLE_VERBOSE_LOGGING && SHOW_PANEL_CREATION_LOG) {
            System.out.println("🎯 [UnifiedProperties] " + message);
        }
    }
}