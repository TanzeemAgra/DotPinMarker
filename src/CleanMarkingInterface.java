import javax.swing.*;
import java.awt.*;

/**
 * Clean Marking Interface
 * Simple, soft-coded approach that ensures everything is visible
 */
public class CleanMarkingInterface {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createCleanInterface();
        });
    }
    
    /**
     * Create clean, visible interface with soft coding
     */
    private static void createCleanInterface() {
        System.out.println("🚀 Starting Clean Marking Interface...");
        CleanInterfaceConfig.printCleanConfig();
        
        // Create main frame
        JFrame frame = new JFrame("Rugrel Dot Pin Marker - Clean Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        
        // Create main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(CleanInterfaceConfig.BACKGROUND_COLOR);
        
        // Create drawing canvas
        DrawingCanvas drawingCanvas = new DrawingCanvas();
        
        // Create simple tabbed pane for content management
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setVisible(false); // Hide the actual tabbed pane, use our custom tabs
        
        // Create tab contents using soft coding
        createTabContents(tabbedPane, drawingCanvas);
        
        // Create clean header with logo and tabs
        JPanel cleanHeader = CleanInterfaceConfig.createCleanHeader(tabbedPane);
        mainContainer.add(cleanHeader, BorderLayout.NORTH);
        
        // Create content area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Add Mark tab content by default
        ThorX6HorizontalMarkTab markTab = new ThorX6HorizontalMarkTab(drawingCanvas);
        contentArea.add(markTab, BorderLayout.CENTER);
        
        mainContainer.add(contentArea, BorderLayout.CENTER);
        
        // Set content area for tab management
        TabManagementConfig.setContentArea(contentArea);
        
        // Create simple status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(CleanInterfaceConfig.HEADER_COLOR);
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        
        JLabel statusLabel = new JLabel("✅ Clean Interface Ready - All components should be visible");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.add(statusLabel);
        
        mainContainer.add(statusBar, BorderLayout.SOUTH);
        
        // Add to frame
        frame.add(mainContainer);
        
        // Force visibility
        CleanInterfaceConfig.forceVisibility(frame);
        
        // Initialize first tab
        TabManagementConfig.switchToTab("Mark");
        
        System.out.println("✅ Clean interface created successfully!");
        System.out.println("🎯 You should see:");
        System.out.println("   • Rugrel logo with circle on the left");
        System.out.println("   • Tab buttons: Mark, Typeset, Print, Database, Barcode");
        System.out.println("   • Mark tab content with ThorX6 options");
        System.out.println("   • Status bar at bottom");
    }
    
    /**
     * Create tab contents with soft coding approach
     */
    private static void createTabContents(JTabbedPane tabbedPane, DrawingCanvas drawingCanvas) {
        System.out.println("📋 Creating tab contents with soft coding...");
        
        // Mark tab
        JPanel markPanel = new ThorX6HorizontalMarkTab(drawingCanvas);
        TabManagementConfig.registerTab("Mark", markPanel, null);
        tabbedPane.addTab("Mark", markPanel);
        
        // Typeset tab
        JPanel typesetPanel = new TypesetPanel(drawingCanvas);
        TabManagementConfig.registerTab("Typeset", typesetPanel, null);
        tabbedPane.addTab("Typeset", typesetPanel);
        
        // Print tab
        JPanel printPanel = new PrintPanel(drawingCanvas);
        TabManagementConfig.registerTab("Print", printPanel, null);
        tabbedPane.addTab("Print", printPanel);
        
        // Database tab
        JPanel databasePanel = new DatabasePanel(drawingCanvas);
        TabManagementConfig.registerTab("Database", databasePanel, null);
        tabbedPane.addTab("Database", databasePanel);
        
        // Barcode tab
        JPanel barcodePanel = new BarcodePanel(drawingCanvas);
        TabManagementConfig.registerTab("Barcode", barcodePanel, null);
        tabbedPane.addTab("Barcode", barcodePanel);
        
        System.out.println("✅ All tab contents registered");
    }
}