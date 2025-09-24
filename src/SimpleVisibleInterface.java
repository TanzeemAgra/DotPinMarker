import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;

/**
 * Simple Visible Interface
 * Basic approach that guarantees Rugrel logo and all tabs are visible
 */
public class SimpleVisibleInterface {
    
    private static JPanel currentContentPanel;
    private static DrawingCanvas sharedCanvas;
    private static JPanel contentArea;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createSimpleInterface();
        });
    }
    
    /**
     * Create simple interface that definitely shows everything
     */
    private static void createSimpleInterface() {
        System.out.println("🧹 Creating Simple Visible Interface...");
        
        // Validate and display ThorX6 Grid Configuration
        ThorX6GridConfig.validateThorX6GridConfig();
        
        // Create main frame
        JFrame frame = new JFrame("Rugrel Dot Pin Marker - ThorX6 Interface with 14x8 Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        
        // Create shared canvas
        sharedCanvas = new DrawingCanvas();
        MarkingInterfaceApp.setDrawingCanvas(sharedCanvas); // Set static reference for RUGREL integration
        
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Top panel with logo and tabs
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Content area
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Set Mark tab as default content
        showMarkContent();
        mainPanel.add(contentArea, BorderLayout.CENTER);
        
        // Create coordinate tracking panel (Soft Coded Control - ENABLED for dynamic properties)
        JComponent coordinatePanel = PropertyPanelControlConfig.createCoordinateTrackingPanel(sharedCanvas);
        
        // Connect PropertyStrip to canvas if it's the PropertyStrip type
        if (coordinatePanel instanceof PropertyStrip) {
            System.out.println("🎯 PropertyStrip detected - connecting to canvas");
            sharedCanvas.setPropertyStrip((PropertyStrip) coordinatePanel);
            System.out.println("✅ PropertyStrip connected to canvas");
        } else {
            System.out.println("❌ Coordinate panel is not PropertyStrip: " + coordinatePanel.getClass().getSimpleName());
        }
        
        // Create bottom panel to hold both coordinate panel and corner status (Soft Coded Layout)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        
        // Add coordinate panel to center of bottom panel
        System.out.println("🎯 Adding PropertyStrip to bottom center - size: " + coordinatePanel.getPreferredSize());
        bottomPanel.add(coordinatePanel, BorderLayout.CENTER);
        
        // Create and add corner status panel to left bottom (Soft Coded Feature)
        JPanel cornerStatusPanel = CornerStatusConfig.createCornerStatusPanel();
        if (CornerStatusConfig.ENABLE_CORNER_STATUS) {
            System.out.println("📍 Adding Corner Status Panel to left bottom");
            bottomPanel.add(cornerStatusPanel, BorderLayout.WEST);
            
            // Add mouse motion listener to canvas for coordinate tracking
            MouseMotionListener coordinateTracker = CornerStatusConfig.createCoordinateTracker();
            if (coordinateTracker != null) {
                sharedCanvas.addMouseMotionListener(coordinateTracker);
                System.out.println("📍 Mouse coordinate tracking enabled on canvas");
            }
        }
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Initialize DatabasePanel for RUGREL dropdown integration (Soft-coded early initialization)
        try {
            DatabasePanel initialDatabasePanel = new DatabasePanel(sharedCanvas);
            MarkingInterfaceApp.setDatabasePanel(initialDatabasePanel);
            System.out.println("🎯 DatabasePanel initialized for RUGREL dropdown integration");
        } catch (Exception e) {
            System.err.println("⚠️ Warning: Could not initialize DatabasePanel for RUGREL integration: " + e.getMessage());
        }
        
        frame.add(mainPanel);
        frame.setVisible(true);
        
        System.out.println("✅ Simple interface created successfully!");
        System.out.println("🎯 You should see Rugrel logo and all tab buttons!");
    }
    
    /**
     * Create top panel with logo and tab buttons
     */
    private static JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        topPanel.setPreferredSize(new Dimension(0, 60));
        
        // Left: Rugrel logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        logoPanel.setBackground(new Color(240, 240, 240));
        
        // Use soft-coded Rugrel logo with dropdown functionality
        JButton logoButton = RugrelDropdownConfig.createRugrelLogoWithDropdown();
        
        logoPanel.add(logoButton);
        topPanel.add(logoPanel, BorderLayout.WEST);
        
        // Center: Tab buttons
        JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 15));
        tabsPanel.setBackground(new Color(240, 240, 240));
        
        String[] tabs = {"Mark", "Typeset", "Print", "Database", "Barcode"};
        for (String tabName : tabs) {
            JButton tabButton = new JButton(tabName);
            tabButton.setPreferredSize(new Dimension(80, 30));
            tabButton.setBackground(new Color(0, 120, 215));
            tabButton.setForeground(Color.WHITE);
            tabButton.setBorder(BorderFactory.createRaisedBevelBorder());
            tabButton.setFocusPainted(false);
            
            tabButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switchToTab(tabName);
                }
            });
            
            tabsPanel.add(tabButton);
        }
        
        topPanel.add(tabsPanel, BorderLayout.CENTER);
        
        // Right: Help button
        JPanel helpPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        helpPanel.setBackground(new Color(240, 240, 240));
        
        JButton helpButton = new JButton("❓ Help");
        helpButton.setPreferredSize(new Dimension(80, 30));
        helpButton.setBackground(new Color(70, 130, 180));
        helpButton.setForeground(Color.WHITE);
        helpButton.setBorder(BorderFactory.createRaisedBevelBorder());
        helpButton.setFocusPainted(false);
        helpButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        helpButton.setToolTipText("Open Help & User Guide");
        
        // Add help button action - reference the frame from main method
        helpButton.addActionListener(e -> {
            // Get the main frame
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(helpButton);
            showHelpDialog(parentFrame);
        });
        
        helpPanel.add(helpButton);
        topPanel.add(helpPanel, BorderLayout.EAST);
        
        System.out.println("🎯 Top panel created with Rugrel logo, tab buttons, and Help button");
        return topPanel;
    }
    
    /**
     * Switch to specified tab content
     */
    private static void switchToTab(String tabName) {
        System.out.println("📋 Switching to tab: " + tabName);
        
        contentArea.removeAll();
        
        switch (tabName) {
            case "Mark":
                showMarkContent();
                break;
            case "Typeset":
                showTypesetContent();
                break;
            case "Print":
                showPrintContent();
                break;
            case "Database":
                showDatabaseContent();
                break;
            case "Barcode":
                showBarcodeContent();
                break;
        }
        
        contentArea.revalidate();
        contentArea.repaint();
    }
    
    /**
     * Show Mark tab content (ThorX6 style)
     */
    private static void showMarkContent() {
        try {
            ThorX6HorizontalMarkTab markTab = new ThorX6HorizontalMarkTab(sharedCanvas);
            contentArea.add(markTab, BorderLayout.CENTER);
            System.out.println("✅ Mark tab content loaded (ThorX6 style)");
        } catch (Exception e) {
            // Fallback to simple panel
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.add(new JLabel("Mark Tab - ThorX6 Interface"));
            contentArea.add(fallbackPanel, BorderLayout.CENTER);
            System.out.println("⚠️ Using fallback Mark tab content");
        }
    }
    
    /**
     * Show Typeset tab content
     */
    private static void showTypesetContent() {
        try {
            TypesetPanel typesetPanel = new TypesetPanel(sharedCanvas);
            contentArea.add(typesetPanel, BorderLayout.NORTH);
            contentArea.add(sharedCanvas, BorderLayout.CENTER);
            System.out.println("✅ Typeset tab content loaded");
        } catch (Exception e) {
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.add(new JLabel("Typeset Tab"));
            contentArea.add(fallbackPanel, BorderLayout.CENTER);
            System.out.println("⚠️ Using fallback Typeset tab content");
        }
    }
    
    /**
     * Show Print tab content
     */
    private static void showPrintContent() {
        try {
            PrintPanel printPanel = new PrintPanel(sharedCanvas);
            contentArea.add(printPanel, BorderLayout.NORTH);
            contentArea.add(sharedCanvas, BorderLayout.CENTER);
            System.out.println("✅ Print tab content loaded");
        } catch (Exception e) {
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.add(new JLabel("Print Tab"));
            contentArea.add(fallbackPanel, BorderLayout.CENTER);
            System.out.println("⚠️ Using fallback Print tab content");
        }
    }
    
    /**
     * Show Database tab content
     */
    private static void showDatabaseContent() {
        try {
            DatabasePanel databasePanel = new DatabasePanel(sharedCanvas);
            contentArea.add(databasePanel, BorderLayout.CENTER);
            
            // Set static reference for RUGREL dropdown integration
            MarkingInterfaceApp.setDatabasePanel(databasePanel);
            
            System.out.println("✅ Database tab content loaded");
        } catch (Exception e) {
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.add(new JLabel("Database Tab"));
            contentArea.add(fallbackPanel, BorderLayout.CENTER);
            System.out.println("⚠️ Using fallback Database tab content");
        }
    }
    
    /**
     * Show Barcode tab content
     */
    private static void showBarcodeContent() {
        try {
            BarcodePanel barcodePanel = new BarcodePanel(sharedCanvas);
            contentArea.add(barcodePanel, BorderLayout.NORTH);
            contentArea.add(sharedCanvas, BorderLayout.CENTER);
            System.out.println("✅ Barcode tab content loaded");
        } catch (Exception e) {
            JPanel fallbackPanel = new JPanel();
            fallbackPanel.add(new JLabel("Barcode Tab"));
            contentArea.add(fallbackPanel, BorderLayout.CENTER);
            System.out.println("⚠️ Using fallback Barcode tab content");
        }
    }
    
    /**
     * Show professional help dialog with comprehensive documentation
     */
    private static void showHelpDialog(JFrame parent) {
        JDialog helpDialog = new JDialog(parent, "📖 Dot Pin Marking Interface - Professional User Manual", true);
        helpDialog.setSize(1200, 850);
        helpDialog.setLocationRelativeTo(parent);
        
        // Create main content panel with tabbed interface
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(new Color(248, 249, 250));
        tabbedPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 5, 10),
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));
        
        // Add comprehensive help tabs
        tabbedPane.addTab("🏠 Overview", createOverviewPanel());
        tabbedPane.addTab("🚀 Quick Start", createQuickStartPanel());
        tabbedPane.addTab("📝 Mark Panel", createMarkPanelHelpPanel());
        tabbedPane.addTab("✨ Features", createFeaturesPanel());
        tabbedPane.addTab("⌨️ Shortcuts", createShortcutsPanel());
        
        helpDialog.add(tabbedPane, BorderLayout.CENTER);
        
        // Footer panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        JLabel versionLabel = new JLabel("Dot Pin Marking Interface v2.1.0 | User Manual");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(versionLabel, BorderLayout.WEST);
        
        JButton closeButton = new JButton("✕ Close Help");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        closeButton.setPreferredSize(new Dimension(120, 32));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(new Color(52, 144, 220));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        closeButton.addActionListener(e -> helpDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        footerPanel.add(buttonPanel, BorderLayout.EAST);
        
        helpDialog.add(footerPanel, BorderLayout.SOUTH);
        helpDialog.setVisible(true);
    }
    
    private static JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        textArea.setText("Welcome to Dot Pin Marking Interface\n\n" +
                "This professional application provides comprehensive tools for:\n\n" +
                "• Mark Creation and Editing\n" +
                "• Grid-based Layout System\n" +
                "• Professional Icons and Tools\n" +
                "• Smart Zoom and Panning\n" +
                "• Database Integration\n" +
                "• Print and Export Functions\n\n" +
                "Navigate through the tabs above to learn more about specific features.");
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createQuickStartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        textArea.setText("Quick Start Guide\n\n" +
                "1. Creating Marks:\n" +
                "   • Click 'Mark' tab if not already selected\n" +
                "   • Use 'Add Mark' dropdown to select mark type\n" +
                "   • Click on canvas to place the mark\n\n" +
                "2. Navigation Controls:\n" +
                "   • Mouse wheel = Zoom in/out\n" +
                "   • Middle mouse + drag = Pan around canvas\n" +
                "   • Ctrl + Left click + drag = Alternative panning\n\n" +
                "3. Mark Properties:\n" +
                "   • Select a mark to see properties at bottom\n" +
                "   • Use PropertyStrip to modify selected marks\n\n" +
                "4. Help Button:\n" +
                "   • Located on the right side of the top panel\n" +
                "   • Click '❓ Help' for this comprehensive manual");
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createMarkPanelHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        textArea.setText("Mark Panel - Professional Tools\n\n" +
                "Available Mark Types:\n" +
                "• Text Mark - Standard text elements\n" +
                "• Bow Text - Curved text along arcs\n" +
                "• DataMatrix - 2D barcode patterns\n" +
                "• Rectangle - Geometric shapes\n" +
                "• Line Mark - Straight line elements\n" +
                "• Graph Mark - Chart and graph elements\n" +
                "• Arc Letters - Text following circular paths\n" +
                "• Avoid Point - Exclusion zones\n" +
                "• Farsi Text - Right-to-left text support\n\n" +
                "Mark Operations:\n" +
                "• Copy, Cut, Paste, Delete selected marks\n" +
                "• Undo/Redo operations\n" +
                "• Smart positioning and alignment\n" +
                "• Professional icon indicators");
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createFeaturesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        textArea.setText("Professional Features\n\n" +
                "✅ Professional Icons\n" +
                "   • High-quality 24x24 PNG icons\n" +
                "   • Professional blue/gray design\n" +
                "   • No more single-letter fallbacks\n\n" +
                "✅ Smart Zoom System\n" +
                "   • Mouse wheel zooming toward cursor\n" +
                "   • Adaptive zoom steps (10% to 1000%)\n" +
                "   • Smart snapping to common levels\n" +
                "   • Real-time visual feedback\n\n" +
                "✅ Intelligent Panning\n" +
                "   • Multiple activation methods:\n" +
                "     - Middle mouse button + drag\n" +
                "     - Right mouse button + drag\n" +
                "     - Ctrl + Left click + drag\n" +
                "     - Shift + Left click + drag\n" +
                "   • Smart boundary constraints\n" +
                "   • Zoom-aware movement limits\n" +
                "   • Automatic cursor transitions\n\n" +
                "✅ Help System\n" +
                "   • Comprehensive user manual\n" +
                "   • Context-sensitive documentation\n" +
                "   • Professional tabbed interface");
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createShortcutsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        textArea.setText("Keyboard Shortcuts & Mouse Controls\n\n" +
                "NAVIGATION:\n" +
                "Mouse Wheel          Zoom in/out toward cursor\n" +
                "Middle Mouse + Drag  Pan around canvas\n" +
                "Right Mouse + Drag   Pan around canvas\n" +
                "Ctrl + Left + Drag   Pan around canvas\n" +
                "Shift + Left + Drag  Pan around canvas\n\n" +
                "EDITING:\n" +
                "Ctrl + C             Copy selected marks\n" +
                "Ctrl + V             Paste marks\n" +
                "Ctrl + X             Cut selected marks\n" +
                "Delete               Delete selected marks\n" +
                "Ctrl + Z             Undo last operation\n" +
                "Ctrl + Y             Redo operation\n\n" +
                "INTERFACE:\n" +
                "❓ Help Button       Open this help manual\n" +
                "Tab Buttons          Switch between panels\n" +
                "Add Mark Dropdown    Access all mark types");
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
}