import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
 * Professional Property Strip Panel with Print Status
 * Beautiful horizontal property bar with coordinates, print counts, and cursor tracking
 * Enhanced with Today Print, Total Print, and real-time X/Y coordinates
 */
public class PropertyStrip extends JPanel {
    private Mark selectedMark;
    private DrawingCanvas canvas;
    
    // Property input fields removed - now status panel only
    
    // ===============================================================================
    // PRINT STATUS AND CURSOR TRACKING FIELDS - NEW ENHANCEMENT
    // ===============================================================================
    
    // Print count tracking (soft-coded with persistence)
    private static int todayPrintCount = 0;           // Today's print count
    private static int totalPrintCount = 1247;        // Total print count (example starting value)
    
    // Status display fields
    private JLabel todayPrintLabel;                   // "Today Print: 15"
    private JLabel totalPrintLabel;                   // "Total Print: 1247" 
    private JLabel cursorXLabel;                      // "X: 245"
    private JLabel cursorYLabel;                      // "Y: 128"
    
    // Soft-coded configuration for print tracking
    private static final boolean ENABLE_PRINT_TRACKING = true;
    private static final boolean ENABLE_CURSOR_TRACKING = true;
    private static final boolean AUTO_SAVE_PRINT_COUNTS = true;
    private static final String PRINT_DATA_FILE = "database/print_counts.dat";
    
    // Styling constants
    private static final Color STRIP_BACKGROUND = new Color(248, 249, 250);
    private static final Color FIELD_BACKGROUND = Color.WHITE;
    private static final Color LABEL_COLOR = new Color(60, 60, 60);
    private static final Color BORDER_COLOR = new Color(225, 225, 225);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    public PropertyStrip(DrawingCanvas canvas) {
        this.canvas = canvas;
        System.out.println("🔧 PropertyStrip created and connected to canvas");
        setupUI();
        System.out.println("✅ PropertyStrip UI setup complete (Status panel only)");
    }
    
    private void setupUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));
        setBackground(STRIP_BACKGROUND);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(4, 12, 4, 12)
        ));
        setPreferredSize(new Dimension(600, 42));  // Reduced width - status panel only
        setVisible(true); // Ensure PropertyStrip is visible
        System.out.println("🎯 PropertyStrip UI setup: size=" + getPreferredSize() + ", visible=" + isVisible());
        
        // STATUS PANEL: Print Status, Cursor Tracking, and Object Coordinates
        if (ENABLE_PRINT_TRACKING || ENABLE_CURSOR_TRACKING) {
            createPrintStatusFields();
            addSeparator();
            createCursorTrackingFields();
            
            // Add informational label to explain status panel
            addSeparator();
            JLabel infoLabel = new JLabel("Status Panel - Properties available in toolbar above");
            infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
            infoLabel.setForeground(new Color(120, 120, 120));
            add(infoLabel);
        }
        
        // Load print counts from file
        loadPrintCounts();
        updatePrintStatusDisplay();
        
        // Status fields always enabled (no property editing in this panel)
        if (ENABLE_PRINT_TRACKING) {
            todayPrintLabel.setEnabled(true);
            totalPrintLabel.setEnabled(true);
        }
        if (ENABLE_CURSOR_TRACKING) {
            cursorXLabel.setEnabled(true);
            cursorYLabel.setEnabled(true);
        }
    }
    
    // ===============================================================================
    // PRINT STATUS AND CURSOR TRACKING FIELD CREATION
    // ===============================================================================
    
    private void createPrintStatusFields() {
        if (ENABLE_PRINT_TRACKING) {
            // Today Print label
            todayPrintLabel = createStatusLabel("Today Print: 0");
            todayPrintLabel.setForeground(new Color(0, 128, 0)); // Green for today's count
            add(todayPrintLabel);
            
            // Total Print label  
            totalPrintLabel = createStatusLabel("Total Print: 0");
            totalPrintLabel.setForeground(new Color(0, 64, 128)); // Blue for total count
            add(totalPrintLabel);
            
            System.out.println("📊 Print status fields created");
        }
    }
    
    private void createCursorTrackingFields() {
        if (ENABLE_CURSOR_TRACKING) {
            // X coordinate label
            cursorXLabel = createStatusLabel("X: 0");
            cursorXLabel.setForeground(new Color(128, 0, 128)); // Purple for X coordinate
            add(cursorXLabel);
            
            // Y coordinate label
            cursorYLabel = createStatusLabel("Y: 0");  
            cursorYLabel.setForeground(new Color(128, 64, 0)); // Brown for Y coordinate
            add(cursorYLabel);
            
            System.out.println("🎯 Cursor tracking fields created");
        }
    }
    
    private JLabel createStatusLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Bold for status
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        label.setOpaque(true);
        label.setBackground(new Color(252, 252, 252));
        return label;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        return label;
    }
    
    // createTextField and createNumberSpinner methods removed - no longer needed
    
    // createCheckBox method removed - no longer needed
    
    private void addSeparator() {
        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(200, 200, 200));
                g.drawLine(2, 4, 2, getHeight() - 4);
            }
        };
        separator.setPreferredSize(new Dimension(5, 30));
        separator.setOpaque(false);
        add(separator);
    }
    
    // setupEventHandlers and updateMarkProperty methods removed - no property editing in status panel
    
    public void setSelectedMark(Mark mark) {
        this.selectedMark = mark;
        
        if (mark == null) {
            System.out.println("📝 PropertyStrip: No mark selected - Status panel only");
        } else {
            System.out.println("📝 PropertyStrip: Mark selected - " + mark.getClass().getSimpleName() + 
                " at (" + mark.x + "," + mark.y + ") size " + mark.width + "x" + mark.height + " - Status only");
        }
        
        // PropertyStrip now only shows status information, no property editing
        // Properties are handled by the toolbar above
    }
    
    // clearFields method removed - no property fields to clear anymore
    
    // ===============================================================================
    // PRINT COUNT MANAGEMENT METHODS
    // ===============================================================================
    
    /**
     * Increment today's print count (called when print operation occurs)
     */
    public static void incrementTodayPrintCount() {
        if (ENABLE_PRINT_TRACKING) {
            todayPrintCount++;
            totalPrintCount++;
            System.out.println("📊 Print count updated: Today=" + todayPrintCount + ", Total=" + totalPrintCount);
            
            // Auto-save if enabled
            if (AUTO_SAVE_PRINT_COUNTS) {
                savePrintCounts();
            }
        }
    }
    
    /**
     * Reset today's print count (called at start of new day)
     */
    public static void resetTodayPrintCount() {
        if (ENABLE_PRINT_TRACKING) {
            todayPrintCount = 0;
            System.out.println("📊 Today's print count reset");
            savePrintCounts();
        }
    }
    
    /**
     * Update the print status display labels
     */
    private void updatePrintStatusDisplay() {
        if (ENABLE_PRINT_TRACKING && todayPrintLabel != null && totalPrintLabel != null) {
            SwingUtilities.invokeLater(() -> {
                todayPrintLabel.setText("Today Print: " + todayPrintCount);
                totalPrintLabel.setText("Total Print: " + totalPrintCount);
            });
        }
    }
    
    /**
     * Update cursor coordinates display (called by DrawingCanvas mouse tracking)
     */
    public void updateCursorCoordinates(int x, int y) {
        if (ENABLE_CURSOR_TRACKING && cursorXLabel != null && cursorYLabel != null) {
            SwingUtilities.invokeLater(() -> {
                cursorXLabel.setText("X: " + x);
                cursorYLabel.setText("Y: " + y);
            });
        }
    }
    
    // ===============================================================================
    // PRINT COUNT PERSISTENCE METHODS
    // ===============================================================================
    
    /**
     * Save print counts to file
     */
    private static void savePrintCounts() {
        if (!AUTO_SAVE_PRINT_COUNTS) return;
        
        try {
            // Create database directory if it doesn't exist
            new java.io.File("database").mkdirs();
            
            // Write print counts to file
            try (java.io.FileWriter writer = new java.io.FileWriter(PRINT_DATA_FILE)) {
                writer.write("# Dot Pin Marker Print Counts\n");
                writer.write("today_count=" + todayPrintCount + "\n");
                writer.write("total_count=" + totalPrintCount + "\n");
                writer.write("last_updated=" + System.currentTimeMillis() + "\n");
            }
            
            System.out.println("💾 Print counts saved to " + PRINT_DATA_FILE);
        } catch (java.io.IOException e) {
            System.err.println("❌ Error saving print counts: " + e.getMessage());
        }
    }
    
    /**
     * Load print counts from file
     */
    private void loadPrintCounts() {
        try {
            java.io.File file = new java.io.File(PRINT_DATA_FILE);
            if (file.exists()) {
                try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine().trim();
                        if (line.startsWith("today_count=")) {
                            todayPrintCount = Integer.parseInt(line.substring(12));
                        } else if (line.startsWith("total_count=")) {
                            totalPrintCount = Integer.parseInt(line.substring(12));
                        }
                    }
                }
                System.out.println("📂 Print counts loaded: Today=" + todayPrintCount + ", Total=" + totalPrintCount);
            } else {
                System.out.println("📂 No print count file found, using defaults");
            }
        } catch (Exception e) {
            System.err.println("❌ Error loading print counts: " + e.getMessage());
        }
    }
    
    // ===============================================================================
    // PUBLIC GETTER METHODS FOR EXTERNAL ACCESS
    // ===============================================================================
    
    public static int getTodayPrintCount() { return todayPrintCount; }
    public static int getTotalPrintCount() { return totalPrintCount; }
    public static void setTotalPrintCount(int count) { 
        totalPrintCount = Math.max(0, count);
        savePrintCounts();
    }
    
    // populateFields and setListenersEnabled methods removed - no property fields to manage
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        // Status panel is always enabled - no property fields to enable/disable
        setBackground(STRIP_BACKGROUND);
    }
}
