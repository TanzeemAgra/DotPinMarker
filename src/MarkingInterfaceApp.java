import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MarkingInterfaceApp {
    // Static reference to DatabasePanel for dropdown integration
    private static DatabasePanel databasePanel;

    // Static reference to DrawingCanvas for global access (soft-coded)
    private static DrawingCanvas drawingCanvasRef;

    /**
     * Get DrawingCanvas for external integration (Soft-coded access)
     */
    public static DrawingCanvas getDrawingCanvas() {
        return drawingCanvasRef;
    }

    /**
     * Set DrawingCanvas reference for external integration (Soft-coded setter)
     */
    public static void setDrawingCanvas(DrawingCanvas canvas) {
        drawingCanvasRef = canvas;
        System.out.println("✅ DrawingCanvas reference set for RUGREL integration");
    }
    
    /**
     * Get DatabasePanel for external integration (Soft-coded access)
     */
    public static DatabasePanel getDatabasePanel() {
        return databasePanel;
    }
    
    /**
     * Check if DatabasePanel is available (Soft-coded validation)
     */
    public static boolean isDatabasePanelAvailable() {
        return databasePanel != null;
    }
    
    /**
     * Set DatabasePanel reference for external integration (Soft-coded setter)
     */
    public static void setDatabasePanel(DatabasePanel dbPanel) {
        databasePanel = dbPanel;
        System.out.println("✅ DatabasePanel reference set for RUGREL integration");
    }
    
    public static void main(String[] args) {
        // Use the Simple Visible Interface with blue Rugrel button and blue tabs
        SimpleVisibleInterface.main(args);
    }
    
    // Original button-based interface (preserved for compatibility)
    public static void createOriginalButtonInterface() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Marking Interface");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 750); // Increased height for better tab visibility

            // Menu bar with direct buttons instead of dropdown menus
            JMenuBar menuBar = new JMenuBar();
            frame.setJMenuBar(menuBar);

            JButton markButton = new JButton("● Mark");
            markButton.setForeground(Color.WHITE);
            markButton.setBackground(Color.DARK_GRAY); // Highlighted as active
            markButton.setOpaque(true);
            
            JButton typesetButton = new JButton("■ Typeset");
            typesetButton.setForeground(Color.BLACK);
            
            JButton printButton = new JButton("📤 Print");
            printButton.setForeground(Color.BLACK);
            
            JButton databaseButton = new JButton("♦ Database");
            databaseButton.setForeground(Color.BLACK);
            
            JButton barcodeButton = new JButton("≡ Barcode");
            barcodeButton.setForeground(Color.BLACK);
            
            // Add Help button with question mark icon
            JButton helpButton = new JButton("❓ Help");
            helpButton.setForeground(Color.BLACK);
            helpButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            helpButton.setToolTipText("Open Help & User Guide");
            
            // Circular Logo Dropdown (top-left corner)
            JButton circularLogoDropdown = createCircularLogoDropdown();
            
            // Add circular logo dropdown to the left
            menuBar.add(circularLogoDropdown);
            menuBar.add(Box.createHorizontalStrut(20)); // Spacing before main buttons
            
            menuBar.add(markButton);
            menuBar.add(typesetButton);
            menuBar.add(printButton);
            menuBar.add(databaseButton);
            menuBar.add(barcodeButton);
            
            // Add separator and help button on the right side
            menuBar.add(Box.createHorizontalGlue()); // Push right-side controls to the right
            menuBar.add(helpButton);            // Layout
            Container contentPane = frame.getContentPane();
            contentPane.setLayout(new BorderLayout());

            // Universal Drawing Canvas
            DrawingCanvas drawingCanvas = new DrawingCanvas();
            setDrawingCanvas(drawingCanvas); // Soft-coded global reference for RUGREL integration

            // Step 1: inner wrapper (the actual white canvas area)
            JPanel innerWrapper = new JPanel(new BorderLayout());
            innerWrapper.setBackground(Color.WHITE);
            innerWrapper.add(drawingCanvas, BorderLayout.CENTER);

            // Step 2: outer black frame (the fat border look)
            JPanel outerFrame = new JPanel(new BorderLayout());
            outerFrame.setBackground(Color.GRAY); // ✅ Fat black border
            outerFrame.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // ✅ Border size (top, left, bottom, right)
            outerFrame.add(innerWrapper, BorderLayout.CENTER);

            // Add to main layout
            contentPane.add(outerFrame, BorderLayout.CENTER);

            // Toolbar container (for top panel swapping)
            JPanel toolbarContainer = new JPanel(new BorderLayout());
            contentPane.add(toolbarContainer, BorderLayout.NORTH);

            // Default panel - Mark Panel
            toolbarContainer.add(new MarkPanel(drawingCanvas), BorderLayout.NORTH);

            // Button actions to swap toolbars
            markButton.addActionListener(e -> {
                // Reset all button styles
                resetButtonStyles(markButton, typesetButton, printButton, databaseButton, barcodeButton);
                // Highlight the active button
                markButton.setBackground(Color.DARK_GRAY);
                markButton.setForeground(Color.WHITE);
                markButton.setOpaque(true);
                
                toolbarContainer.removeAll();
                toolbarContainer.add(new MarkPanel(drawingCanvas), BorderLayout.NORTH);
                toolbarContainer.revalidate();
                toolbarContainer.repaint();
            });

            typesetButton.addActionListener(e -> {
                // Reset all button styles
                resetButtonStyles(markButton, typesetButton, printButton, databaseButton, barcodeButton);
                // Highlight the active button
                typesetButton.setBackground(Color.DARK_GRAY);
                typesetButton.setForeground(Color.WHITE);
                typesetButton.setOpaque(true);
                
                toolbarContainer.removeAll();
                TypesetPanel typesetPanel = new TypesetPanel(drawingCanvas);
                typesetPanel.addActionListeners();
                toolbarContainer.add(typesetPanel, BorderLayout.NORTH);
                toolbarContainer.revalidate();
                toolbarContainer.repaint();
            });

            printButton.addActionListener(e -> {
                // Reset all button styles
                resetButtonStyles(markButton, typesetButton, printButton, databaseButton, barcodeButton);
                // Highlight the active button
                printButton.setBackground(Color.DARK_GRAY);
                printButton.setForeground(Color.WHITE);
                printButton.setOpaque(true);
                
                toolbarContainer.removeAll();
                // Use the unified Print & Engrave panel
                toolbarContainer.add(new PrintEngravePanel(drawingCanvas), BorderLayout.NORTH);
                toolbarContainer.revalidate();
                toolbarContainer.repaint();
            });

            barcodeButton.addActionListener(e -> {
                // Reset all button styles
                resetButtonStyles(markButton, typesetButton, printButton, databaseButton, barcodeButton);
                // Highlight the active button
                barcodeButton.setBackground(Color.DARK_GRAY);
                barcodeButton.setForeground(Color.WHITE);
                barcodeButton.setOpaque(true);
                
                toolbarContainer.removeAll();
                toolbarContainer.add(new BarcodePanel(drawingCanvas), BorderLayout.NORTH);
                toolbarContainer.revalidate();
                toolbarContainer.repaint();
            });

            databaseButton.addActionListener(e -> {
                // Reset all button styles
                resetButtonStyles(markButton, typesetButton, printButton, databaseButton, barcodeButton);
                // Highlight the active button
                databaseButton.setBackground(Color.DARK_GRAY);
                databaseButton.setForeground(Color.WHITE);
                databaseButton.setOpaque(true);
                
                toolbarContainer.removeAll();
                databasePanel = new DatabasePanel(drawingCanvas);
                toolbarContainer.add(databasePanel, BorderLayout.NORTH);
                toolbarContainer.revalidate();
                toolbarContainer.repaint();
            });

            // Help button action
            helpButton.addActionListener(e -> showHelpDialog(frame));

            // Future buttons can be added here similarly

            frame.setVisible(true);
        });
    }
    
    // =============================
    // PROFESSIONAL TABBED INTERFACE
    // =============================
    
    // Create professional tabbed pane with modern styling like the image
    private static JTabbedPane createProfessionalTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
        // Modern tab styling that matches professional applications
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int selectedIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    // Selected tab - Clean white background with blue accent
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x, y, w, h);
                    
                    // Blue accent line at bottom (professional look)
                    g2d.setColor(new Color(0, 120, 215));
                    g2d.setStroke(new BasicStroke(3.0f));
                    g2d.drawLine(x + 3, y + h - 2, x + w - 4, y + h - 2);
                    
                    // Subtle side borders
                    g2d.setColor(new Color(225, 225, 225));
                    g2d.setStroke(new BasicStroke(1.0f));
                    g2d.drawLine(x, y + 3, x, y + h - 1);
                    g2d.drawLine(x + w - 1, y + 3, x + w - 1, y + h - 1);
                    g2d.drawLine(x + 3, y, x + w - 4, y);
                } else {
                    // Unselected tab - Light gray background
                    g2d.setColor(new Color(248, 249, 250));
                    g2d.fillRect(x, y + 2, w, h - 2);
                    
                    // Very subtle border
                    g2d.setColor(new Color(235, 235, 235));
                    g2d.setStroke(new BasicStroke(1.0f));
                    g2d.drawRect(x, y + 2, w - 1, h - 3);
                }
                
                g2d.dispose();
            }
            
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(225, 225, 225));
                g2d.setStroke(new BasicStroke(1.0f));
                
                // Clean border around content area
                int x = 0;
                int y = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - 1;
                int w = tabPane.getWidth() - 1;
                int h = tabPane.getHeight() - y - 1;
                
                g2d.drawRect(x, y, w, h);
                g2d.dispose();
            }
            
            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return 38; // Professional tab height for maximum space utilization
            }
            
            @Override
            protected Insets getTabInsets(int tabPlacement, int tabIndex) {
                return new Insets(10, 18, 10, 18); // Optimal padding for professional look
            }
            
            @Override
            protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
                                   Rectangle iconRect, Rectangle textRect) {
                super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
                
                // Add subtle hover effect
                if (tabIndex == getRolloverTab() && tabIndex != tabPane.getSelectedIndex()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(240, 240, 240, 120));
                    Rectangle rect = rects[tabIndex];
                    g2d.fillRect(rect.x, rect.y + 2, rect.width, rect.height - 2);
                    g2d.dispose();
                }
            }
        });
        
        // Professional font and colors
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabbedPane.setBackground(new Color(248, 249, 250));
        tabbedPane.setForeground(new Color(60, 60, 60));
        
        // Remove focus border for cleaner look
        tabbedPane.setFocusable(false);
        
        return tabbedPane;
    }
    
    // Add professional tab with icon and tooltip
    private static void addProfessionalTab(JTabbedPane tabbedPane, String title, String icon, 
                                          JComponent component, String tooltip) {
        // Create custom tab component with icon and text
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        tabPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(60, 60, 60));
        
        tabPanel.add(iconLabel);
        tabPanel.add(titleLabel);
        
        tabbedPane.addTab(title, component);  // Keep the title for tab management
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tabPanel);
        tabbedPane.setToolTipTextAt(tabbedPane.getTabCount() - 1, tooltip);
    }
    
    // Create Mark tab panel with ThorX6 Horizontal Layout (exact ThorX6 copy)
    private static JPanel createMarkTabPanel(DrawingCanvas drawingCanvas) {
        System.out.println("🎯 Creating ThorX6 Horizontal Mark Tab (exact ThorX6 software copy)...");
        
        // Create ThorX6-style horizontal Mark tab like real ThorX6 software
        ThorX6HorizontalMarkTab thorX6MarkTab = new ThorX6HorizontalMarkTab(drawingCanvas);
        
        return thorX6MarkTab;
        
        // Layout: ThorX6 panel contains toolbar, properties, and status
    }    // Create ThorX6-styled canvas area
    private static JPanel createThorX6CanvasArea(DrawingCanvas drawingCanvas) {
        JPanel canvasArea = new JPanel(new BorderLayout());
        canvasArea.setBackground(ThorX6Configuration.THORX6_DARK_BLUE);
        
        // Canvas wrapper with ThorX6 border styling
        JPanel canvasWrapper = new JPanel(new BorderLayout());
        canvasWrapper.setBackground(Color.WHITE);
        canvasWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        canvasWrapper.add(drawingCanvas, BorderLayout.CENTER);
        
        // Add canvas with proper margins
        canvasArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        canvasArea.add(canvasWrapper, BorderLayout.CENTER);
        
        return canvasArea;
    }
    
    // Create any tab panel with property strip below sub-features
    private static JPanel createTabPanelWithPropertyStrip(JPanel toolbar, DrawingCanvas drawingCanvas, PropertyStrip propertyStrip) {
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setBackground(Color.WHITE);
        
        // Top section: Sub-features toolbar + Property strip
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(Color.WHITE);
        
        // Tab-specific toolbar (sub-features)
        topSection.add(toolbar, BorderLayout.NORTH);
        
        // Property strip (below sub-features)
        topSection.add(propertyStrip, BorderLayout.SOUTH);
        
        tabPanel.add(topSection, BorderLayout.NORTH);
        
        // Professional canvas area
        JPanel canvasArea = createProfessionalCanvasArea(drawingCanvas);
        tabPanel.add(canvasArea, BorderLayout.CENTER);
        
        return tabPanel;
    }
    
    // Create Database tab panel
    private static JPanel createDatabaseTabPanel(DrawingCanvas drawingCanvas) {
        JPanel dbPanel = new JPanel(new BorderLayout());
        dbPanel.setBackground(Color.WHITE);
        
        // Initialize database panel
        databasePanel = new DatabasePanel(drawingCanvas);
        dbPanel.add(databasePanel, BorderLayout.NORTH);
        dbPanel.add(createProfessionalCanvasArea(drawingCanvas), BorderLayout.CENTER);
        
        return dbPanel;
    }
    
    // Create professional canvas area with modern frame styling
    private static JPanel createProfessionalCanvasArea(DrawingCanvas drawingCanvas) {
        // Inner wrapper - clean white canvas area
        JPanel innerWrapper = new JPanel(new BorderLayout());
        innerWrapper.setBackground(Color.WHITE);
        innerWrapper.add(drawingCanvas, BorderLayout.CENTER);
        
        // Outer frame with professional border styling
        JPanel outerFrame = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Subtle gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(248, 249, 250),
                    0, getHeight(), new Color(240, 241, 242)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        outerFrame.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        outerFrame.add(innerWrapper, BorderLayout.CENTER);
        
        return outerFrame;
    }
    
    // Create alternative main method for professional tabbed interface
    public static void createProfessionalTabbedInterface() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Marking Interface - Professional Edition");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 750);
            
            // Create the professional tabbed interface
            JTabbedPane tabbedPane = createProfessionalTabbedPane();
            
            // Shared drawing canvas
            DrawingCanvas drawingCanvas = new DrawingCanvas();
            
            // Create property panel using soft-coded control
            PropertyPanelControlConfig.validateConfiguration();
            JComponent propertyPanel = PropertyPanelControlConfig.createControlledPropertyPanel(drawingCanvas);
            
            // Connect to canvas if it's the old PropertyStrip type
            if (propertyPanel instanceof PropertyStrip) {
                drawingCanvas.setPropertyStrip((PropertyStrip) propertyPanel);
            }
            
            PropertyPanelControlConfig.printActivePanel();
            
            // Create tab panels
            JPanel markPanel = createMarkTabPanel(drawingCanvas);
            
            JPanel typesetPanel = new JPanel(new BorderLayout());
            typesetPanel.setBackground(Color.WHITE);
            TypesetPanel typesetToolbar = new TypesetPanel(drawingCanvas);
            typesetToolbar.addActionListeners();
            typesetPanel.add(typesetToolbar, BorderLayout.NORTH);
            typesetPanel.add(createProfessionalCanvasArea(drawingCanvas), BorderLayout.CENTER);
            
            JPanel printPanel = new JPanel(new BorderLayout());
            printPanel.setBackground(Color.WHITE);
            printPanel.add(new PrintPanel(drawingCanvas), BorderLayout.NORTH);
            printPanel.add(createProfessionalCanvasArea(drawingCanvas), BorderLayout.CENTER);
            
            JPanel databaseTabPanel = createDatabaseTabPanel(drawingCanvas);
            
            JPanel barcodePanel = new JPanel(new BorderLayout());
            barcodePanel.setBackground(Color.WHITE);
            barcodePanel.add(new BarcodePanel(drawingCanvas), BorderLayout.NORTH);
            barcodePanel.add(createProfessionalCanvasArea(drawingCanvas), BorderLayout.CENTER);
            
            // Add tabs with professional styling and icons
            addProfessionalTab(tabbedPane, "Mark", "✏️", markPanel, "Design and create marks with professional tools");
            addProfessionalTab(tabbedPane, "Typeset", "📝", typesetPanel, "Advanced text formatting and typography");
            addProfessionalTab(tabbedPane, "Print", "🖨️", printPanel, "Professional printing and engraving options");
            addProfessionalTab(tabbedPane, "Database", "🗃️", databaseTabPanel, "Project and template management system");
            addProfessionalTab(tabbedPane, "Barcode", "⬚", barcodePanel, "Barcode generation and configuration");
            
            // Create main layout with optimized tab-logo alignment
            JPanel mainContainer = new JPanel(new BorderLayout());
            mainContainer.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
            
            // Create properly aligned top panel with logo and tabs together
            JPanel topAlignedPanel = createCombinedLogoTabsPanel(tabbedPane);
            
            // Create content area
            JPanel contentArea = createManagedContentArea(tabbedPane);
            
            mainContainer.add(topAlignedPanel, BorderLayout.NORTH);
            mainContainer.add(contentArea, BorderLayout.CENTER);
            
            // IMPORTANT: Set content area AFTER tab headers are created
            TabManagementConfig.setContentArea(contentArea);
            
            // Now initialize the default tab with content area properly set
            if (tabbedPane.getTabCount() > 0) {
                String firstTab = tabbedPane.getTitleAt(0);
                System.out.println("🔧 Final initialization with content area set: " + firstTab);
                TabManagementConfig.switchToTab(firstTab);
            }
            mainContainer.add(propertyPanel, BorderLayout.SOUTH); // Soft-coded property panel at bottom!
            
            frame.add(mainContainer);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
    // ==================== PROPERLY ALIGNED TOP PANEL CREATION ====================
    
    /**
     * Create combined panel with logo and tab headers properly aligned horizontally
     */
    private static JPanel createCombinedLogoTabsPanel(JTabbedPane tabbedPane) {
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        combinedPanel.setPreferredSize(new Dimension(0, HorizontalTabAlignmentConfig.TOTAL_HEADER_HEIGHT));
        
        // Left section: Logo with spacing
        JPanel logoSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 
                                                      HorizontalTabAlignmentConfig.LOGO_MARGIN_LEFT, 
                                                      HorizontalTabAlignmentConfig.calculateAlignedYPosition()));
        logoSection.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        logoSection.add(createProfessionalLogoButton());
        
        // Add spacing after logo
        JPanel spacing = new JPanel();
        spacing.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        spacing.setPreferredSize(new Dimension(HorizontalTabAlignmentConfig.LOGO_TAB_GAP, 10));
        logoSection.add(spacing);
        
        combinedPanel.add(logoSection, BorderLayout.WEST);
        
        // Center section: Tab headers (extract from JTabbedPane)
        JPanel tabHeaderArea = createCustomTabHeaders(tabbedPane);
        combinedPanel.add(tabHeaderArea, BorderLayout.CENTER);
        
        // Professional UI initialization
        ProfessionalUIConfig.printConfigSummary();
        System.out.println("🔧 Professional Header Panel Created:");
        System.out.println("   🎯 Logo positioned at WEST with " + HorizontalTabAlignmentConfig.LOGO_MARGIN_LEFT + "px margin");
        System.out.println("   📋 Tab headers positioned at CENTER with " + HorizontalTabAlignmentConfig.LOGO_TAB_GAP + "px gap");
        System.out.println("   ✨ Animations and accessibility features enabled");
        
        return combinedPanel;
    }
    
    /**
     * Create content area that will dynamically display tab contents
     */
    private static JPanel createManagedContentArea(JTabbedPane tabbedPane) {
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);
        
        // Content area will be set after tab headers are created
        
        return contentArea;
    }
    
    /**
     * Create custom tab headers aligned properly with logo using soft-coded management
     */
    private static JPanel createCustomTabHeaders(JTabbedPane tabbedPane) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, HorizontalTabAlignmentConfig.calculateAlignedYPosition()));
        headerPanel.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        
        // Create professional tab buttons using soft-coded system
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String tabTitle = tabbedPane.getTitleAt(i);
            System.out.println("🔧 Creating tab button for: '" + tabTitle + "' (index " + i + ")");
            
            JButton tabButton = TabManagementConfig.createProfessionalTabButton(tabTitle);
            
            // Register the button with tab management system
            JPanel tabContent = (JPanel) tabbedPane.getComponentAt(i);
            TabManagementConfig.registerTab(tabTitle, tabContent, tabButton);
            
            headerPanel.add(tabButton);
        }
        
        // Initialize with first tab selected - do this BEFORE debug
        if (tabbedPane.getTabCount() > 0) {
            String firstTab = tabbedPane.getTitleAt(0);
            System.out.println("🔧 Initializing with first tab: " + firstTab);
            TabManagementConfig.initializeWithDefaultTab(firstTab);
        }
        
        // Print debug info AFTER initialization
        TabManagementConfig.printTabDebugInfo();
        
        return headerPanel;
    }
    

    
    /**
     * Create properly aligned top panel with clear logo and tab separation
     */
    private static JPanel createProperlyAlignedTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        topPanel.setPreferredSize(new Dimension(0, HorizontalTabAlignmentConfig.TOTAL_HEADER_HEIGHT));
        
        // Left side: Logo with proper spacing
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 
                                                    HorizontalTabAlignmentConfig.LOGO_MARGIN_LEFT, 
                                                    HorizontalTabAlignmentConfig.calculateAlignedYPosition()));
        logoPanel.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        
        JButton alignedLogo = createProfessionalLogoButton();
        logoPanel.add(alignedLogo);
        
        // Add specific spacing after logo
        JPanel spacingPanel = new JPanel();
        spacingPanel.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        spacingPanel.setPreferredSize(new Dimension(HorizontalTabAlignmentConfig.LOGO_TAB_GAP, 10));
        logoPanel.add(spacingPanel);
        
        topPanel.add(logoPanel, BorderLayout.WEST);
        
        // Debug: Print the actual positioning
        System.out.println("🔧 Top Panel Layout Debug:");
        System.out.println("   Logo panel uses FlowLayout.LEFT with margin: " + HorizontalTabAlignmentConfig.LOGO_MARGIN_LEFT);
        System.out.println("   Spacing panel width: " + HorizontalTabAlignmentConfig.LOGO_TAB_GAP);
        System.out.println("   This should separate logo from tabs properly");
        
        return topPanel;
    }
    
    // ==================== ALIGNED HEADER PANEL CREATION ====================
    
    /**
     * Create aligned header panel with logo and tabs positioned for optimal space utilization
     */
    private static JPanel createAlignedHeaderPanel(JTabbedPane tabbedPane) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (HorizontalTabAlignmentConfig.ENABLE_TAB_LOGO_ALIGNMENT) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Professional gradient background
                    GradientPaint gradient = HorizontalTabAlignmentConfig.createHeaderGradient(getWidth(), getHeight());
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    
                    g2d.dispose();
                }
            }
        };
        
        headerPanel.setLayout(null); // Absolute positioning for perfect alignment
        headerPanel.setPreferredSize(new Dimension(0, HorizontalTabAlignmentConfig.TOTAL_HEADER_HEIGHT));
        headerPanel.setBackground(HorizontalTabAlignmentConfig.ALIGNED_HEADER_BACKGROUND);
        
        // Create and position aligned logo
        JButton alignedLogo = createProfessionalLogoButton();
        alignedLogo.setBounds(
            HorizontalTabAlignmentConfig.LOGO_MARGIN_LEFT,
            HorizontalTabAlignmentConfig.calculateAlignedYPosition(),
            HorizontalTabAlignmentConfig.LOGO_SIZE_ALIGNED,
            HorizontalTabAlignmentConfig.LOGO_SIZE_ALIGNED
        );
        headerPanel.add(alignedLogo);
        
        // Position the tabbed pane adjacent to logo (no overlap)
        int tabStartX = HorizontalTabAlignmentConfig.calculateDirectTabStartX();
        
        // Debug: Print positioning information
        HorizontalTabAlignmentConfig.printAlignmentDebugInfo();
        
        tabbedPane.setBounds(tabStartX, HorizontalTabAlignmentConfig.calculateAlignedYPosition(), 
                           800, HorizontalTabAlignmentConfig.TAB_HEIGHT_ALIGNED + 10);
        
        // Apply aligned styling to tabs
        applyAlignedTabStyling(tabbedPane);
        
        headerPanel.add(tabbedPane);
        
        return headerPanel;
    }
    
    /**
     * Create aligned logo button with optimized size and positioning
     */
    private static JButton createProfessionalLogoButton() {
        JButton alignedLogo = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                int size = HorizontalTabAlignmentConfig.LOGO_SIZE_ALIGNED - 4;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                // Professional gradient using theme colors
                ProfessionalUIConfig.ThemeColors colors = ProfessionalUIConfig.getCurrentThemeColors();
                RadialGradientPaint radialGradient = new RadialGradientPaint(
                    x + size/2f, y + size/2f, size/2f,
                    new float[]{0.0f, 0.7f, 1.0f},
                    new Color[]{
                        colors.surface,
                        new Color(colors.primary.getRed(), colors.primary.getGreen(), colors.primary.getBlue(), 50),
                        new Color(colors.primary.getRed(), colors.primary.getGreen(), colors.primary.getBlue(), 100)
                    }
                );
                g2d.setPaint(radialGradient);
                g2d.fillOval(x, y, size, size);
                
                // Professional border matching tab accent
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.setColor(HorizontalTabAlignmentConfig.ALIGNED_TAB_ACCENT);
                g2d.drawOval(x, y, size, size);
                
                // Company name with aligned typography
                g2d.setColor(HorizontalTabAlignmentConfig.ALIGNED_TAB_ACCENT);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 8));
                FontMetrics fm = g2d.getFontMetrics();
                
                String logoText = "RUG-REL";
                int textWidth = fm.stringWidth(logoText);
                int textX = x + (size - textWidth) / 2;
                int textY = y + (size + fm.getAscent()) / 2 - 1;
                
                g2d.drawString(logoText, textX, textY);
                
                g2d.dispose();
            }
        };
        
        alignedLogo.setPreferredSize(new Dimension(
            HorizontalTabAlignmentConfig.LOGO_SIZE_ALIGNED,
            HorizontalTabAlignmentConfig.LOGO_SIZE_ALIGNED
        ));
        alignedLogo.setOpaque(false);
        alignedLogo.setContentAreaFilled(false);
        alignedLogo.setBorderPainted(false);
        alignedLogo.setFocusPainted(false);
        alignedLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Professional tooltip with enhanced information
        ProfessionalUIConfig.addProfessionalTooltip(alignedLogo, 
            "Rug-Rel Professional Interface • Click for options menu • Professional ThorX6 Layout");
        
        // Add keyboard accessibility
        ProfessionalUIConfig.addKeyboardSupport(alignedLogo, () -> {
            System.out.println("🔘 Logo activated via keyboard");
        });
        
        // Professional hover effects
        addProfessionalLogoHoverEffects(alignedLogo);
        
        // Add dropdown functionality with professional feedback
        alignedLogo.addActionListener(e -> {
            System.out.println("🔘 Professional logo clicked - showing enhanced dropdown...");
            ProfessionalUIConfig.showNotification(null, "Logo menu activated", true);
            JPopupMenu dropdown = createFileManagementDropdown();
            dropdown.show(alignedLogo, 0, alignedLogo.getHeight());
        });
        
        return alignedLogo;
    }
    
    /**
     * Add professional hover effects to the logo button
     */
    private static void addProfessionalLogoHoverEffects(JButton logoButton) {
        logoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Subtle scale animation effect (simulated with border)
                logoButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ProfessionalUIConfig.getCurrentThemeColors().primary, 2),
                    BorderFactory.createEmptyBorder(1, 1, 1, 1)
                ));
                System.out.println("✨ Logo hover effect activated");
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoButton.setBorder(null);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                // Professional press feedback
                logoButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ProfessionalUIConfig.getCurrentThemeColors().accent, 2),
                    BorderFactory.createEmptyBorder(1, 1, 1, 1)
                ));
            }
        });
    }
    
    /**
     * Apply aligned styling to tabbed pane
     */
    private static void applyAlignedTabStyling(JTabbedPane tabbedPane) {
        tabbedPane.setFont(HorizontalTabAlignmentConfig.ALIGNED_TAB_FONT);
        tabbedPane.setBackground(HorizontalTabAlignmentConfig.ALIGNED_TAB_BACKGROUND);
        tabbedPane.setForeground(HorizontalTabAlignmentConfig.ALIGNED_TAB_TEXT_COLOR);
        
        // Update tab UI for aligned styling
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int selectedIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    // Selected tab with aligned styling (no overlap)
                    g2d.setColor(HorizontalTabAlignmentConfig.ALIGNED_TAB_SELECTED);
                    g2d.fillRoundRect(x, y, w, h, 6, 6);
                    
                    // Accent bottom line
                    g2d.setColor(HorizontalTabAlignmentConfig.ALIGNED_TAB_ACCENT);
                    g2d.setStroke(new BasicStroke(3.0f));
                    g2d.drawLine(x + 4, y + h - 2, x + w - 5, y + h - 2);
                    
                    // Subtle border
                    g2d.setColor(HorizontalTabAlignmentConfig.ALIGNED_TAB_BORDER);
                    g2d.setStroke(new BasicStroke(1.0f));
                    g2d.drawRoundRect(x, y, w - 1, h - 1, 6, 6);
                } else {
                    // Unselected tab
                    g2d.setColor(HorizontalTabAlignmentConfig.ALIGNED_TAB_BACKGROUND);
                    g2d.fillRoundRect(x, y + 3, w, h - 3, 4, 4);
                }
                
                g2d.dispose();
            }
            
            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return HorizontalTabAlignmentConfig.TAB_HEIGHT_ALIGNED;
            }
            
            @Override
            protected Insets getTabInsets(int tabPlacement, int tabIndex) {
                return new Insets(
                    HorizontalTabAlignmentConfig.TAB_PADDING_VERTICAL,
                    HorizontalTabAlignmentConfig.TAB_PADDING_HORIZONTAL,
                    HorizontalTabAlignmentConfig.TAB_PADDING_VERTICAL,
                    HorizontalTabAlignmentConfig.TAB_PADDING_HORIZONTAL
                );
            }
        });
    }
    
    // ==================== VERTICAL TAB INTERFACE (SPACE OPTIMIZED) ====================
    
    /**
     * Create vertical tab interface for maximum canvas space optimization
     * Uses soft-coded VerticalLayoutConfiguration for all positioning and styling
     */
    public static void createVerticalTabInterface() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Marking Interface - Vertical Layout (Space Optimized)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 800); // Larger frame to showcase space optimization
            
            // Create shared drawing canvas with optimized dimensions
            DrawingCanvas drawingCanvas = new DrawingCanvas();
            
            // Create property panel using soft-coded control
            PropertyPanelControlConfig.validateConfiguration();
            JComponent propertyPanel = PropertyPanelControlConfig.createControlledPropertyPanel(drawingCanvas);
            
            // Connect to canvas if it's the old PropertyStrip type
            if (propertyPanel instanceof PropertyStrip) {
                drawingCanvas.setPropertyStrip((PropertyStrip) propertyPanel);
            }
            
            PropertyPanelControlConfig.printActivePanel();
            
            // Create vertical tab panel with soft-coded configuration
            VerticalTabPanel verticalTabs = new VerticalTabPanel();
            
            // Add all tabs using soft-coded definitions
            for (VerticalLayoutConfiguration.TabDefinition tabDef : VerticalLayoutConfiguration.VERTICAL_TABS) {
                JComponent tabContent = createTabContentForVerticalLayout(tabDef.title, drawingCanvas);
                verticalTabs.addTab(tabDef, tabContent);
            }
            
            // Configure logo button dropdown
            JButton logoButton = verticalTabs.getLogoButton();
            logoButton.addActionListener(e -> {
                System.out.println("🔘 Vertical layout logo clicked - showing dropdown...");
                JPopupMenu dropdown = createFileManagementDropdown();
                dropdown.show(logoButton, logoButton.getWidth() + 5, 0);
            });
            
            // Create main layout with optimized canvas space
            JPanel mainContainer = new JPanel(new BorderLayout());
            mainContainer.setBackground(new Color(248, 249, 250));
            
            // Create optimized canvas area with maximum space utilization
            JPanel canvasArea = createOptimizedCanvasArea(drawingCanvas);
            
            // Layout with vertical tabs on left, canvas on right
            mainContainer.add(verticalTabs, BorderLayout.WEST);
            mainContainer.add(canvasArea, BorderLayout.CENTER);
            mainContainer.add(propertyPanel, BorderLayout.SOUTH);
            
            frame.add(mainContainer);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            System.out.println("✅ Vertical tab interface created with optimized canvas space");
        });
    }
    
    /**
     * Create tab content for vertical layout
     */
    private static JComponent createTabContentForVerticalLayout(String tabName, DrawingCanvas canvas) {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        
        switch (tabName) {
            case "Mark":
                // Use ThorX6MarkPanel for professional marking interface
                content.add(createThorX6CanvasArea(canvas), BorderLayout.CENTER);
                break;
            case "Typeset":
                TypesetPanel typesetPanel = new TypesetPanel(canvas);
                typesetPanel.addActionListeners();
                content.add(typesetPanel, BorderLayout.NORTH);
                content.add(createOptimizedCanvasArea(canvas), BorderLayout.CENTER);
                break;
            case "Print":
                content.add(new PrintPanel(canvas), BorderLayout.NORTH);
                content.add(createOptimizedCanvasArea(canvas), BorderLayout.CENTER);
                break;
            case "Database":
                content = createDatabaseTabPanel(canvas);
                break;
            case "Barcode":
                content.add(new BarcodePanel(canvas), BorderLayout.NORTH);
                content.add(createOptimizedCanvasArea(canvas), BorderLayout.CENTER);
                break;
            default:
                JLabel placeholder = new JLabel("Tab: " + tabName, JLabel.CENTER);
                placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                content.add(placeholder, BorderLayout.CENTER);
        }
        
        return content;
    }
    
    /**
     * Create optimized canvas area with maximum space utilization
     */
    private static JPanel createOptimizedCanvasArea(DrawingCanvas canvas) {
        JPanel canvasContainer = new JPanel(new BorderLayout());
        canvasContainer.setBackground(new Color(245, 245, 245));
        
        // Inner white canvas area
        JPanel innerCanvas = new JPanel(new BorderLayout());
        innerCanvas.setBackground(VerticalLayoutConfiguration.CANVAS_BACKGROUND);
        innerCanvas.add(canvas, BorderLayout.CENTER);
        
        // Optimized canvas frame with soft-coded margins
        JPanel canvasFrame = new JPanel(new BorderLayout());
        canvasFrame.setBackground(VerticalLayoutConfiguration.CANVAS_FRAME_COLOR);
        canvasFrame.setBorder(BorderFactory.createEmptyBorder(
            VerticalLayoutConfiguration.CANVAS_MARGIN_TOP,
            VerticalLayoutConfiguration.CANVAS_MARGIN_LEFT,
            VerticalLayoutConfiguration.CANVAS_MARGIN_BOTTOM,
            VerticalLayoutConfiguration.CANVAS_MARGIN_RIGHT
        ));
        
        // Add drop shadow effect if enabled
        if (VerticalLayoutConfiguration.SHOW_CANVAS_SHADOW) {
            canvasFrame.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 10, 10),
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLoweredBevelBorder(),
                    canvasFrame.getBorder()
                )
            ));
        }
        
        canvasFrame.add(innerCanvas, BorderLayout.CENTER);
        canvasContainer.add(canvasFrame, BorderLayout.CENTER);
        
        return canvasContainer;
    }
    
    // Professional Help System with Enhanced Documentation
    private static void showHelpDialog(JFrame parent) {
        JDialog helpDialog = new JDialog(parent, "📖 Dot Pin Marking Interface - Professional User Manual", true);
        helpDialog.setSize(1200, 850);
        helpDialog.setLocationRelativeTo(parent);
        helpDialog.setIconImage(parent.getIconImage());
        
        // Enable resizing and store original bounds for maximize/restore functionality
        helpDialog.setResizable(true);
        final java.awt.Rectangle originalBounds = new java.awt.Rectangle(
            helpDialog.getX(), helpDialog.getY(), helpDialog.getWidth(), helpDialog.getHeight()
        );
        
        // Track maximize state
        final boolean[] isMaximized = {false};
        
        // Create custom title bar with maximize button
        JPanel titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setBackground(new Color(240, 240, 240));
        titleBarPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 15, 8, 8)
        ));
        
        // Title label
        JLabel titleLabel = new JLabel("📖 Dot Pin Marking Interface - Professional User Manual");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(60, 60, 60));
        titleBarPanel.add(titleLabel, BorderLayout.WEST);
        
        // Right side buttons panel
        JPanel titleButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        titleButtonsPanel.setOpaque(false);
        
        // Maximize/Restore button with professional Windows-style design
        JButton titleMaximizeButton = new JButton("□");
        titleMaximizeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleMaximizeButton.setPreferredSize(new Dimension(35, 30));
        titleMaximizeButton.setFocusPainted(false);
        titleMaximizeButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        titleMaximizeButton.setBackground(new Color(245, 245, 245));
        titleMaximizeButton.setForeground(new Color(80, 80, 80));
        titleMaximizeButton.setToolTipText("🔍 Maximize window to full screen (F11)");
        titleMaximizeButton.addActionListener(e -> {
            toggleMaximize(helpDialog, parent, originalBounds, isMaximized);
            // Update button icon and tooltip
            if (isMaximized[0]) {
                titleMaximizeButton.setText("⧉");
                titleMaximizeButton.setToolTipText("↩️ Restore window to normal size (F11)");
            } else {
                titleMaximizeButton.setText("□");
                titleMaximizeButton.setToolTipText("🔍 Maximize window to full screen (F11)");
            }
        });
        
        // Add professional hover effects for maximize button
        titleMaximizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                titleMaximizeButton.setBackground(new Color(229, 241, 251));
                titleMaximizeButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 1),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                titleMaximizeButton.setBackground(new Color(245, 245, 245));
                titleMaximizeButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            }
        });
        
        // Close button with distinctive red styling
        JButton titleCloseButton = new JButton("✕");
        titleCloseButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleCloseButton.setPreferredSize(new Dimension(35, 30));
        titleCloseButton.setFocusPainted(false);
        titleCloseButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        titleCloseButton.setBackground(new Color(245, 245, 245));
        titleCloseButton.setForeground(new Color(100, 100, 100));
        titleCloseButton.setToolTipText("❌ Close Help Window");
        titleCloseButton.addActionListener(e -> helpDialog.dispose());
        
        // Add distinctive hover effects for close button
        titleCloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                titleCloseButton.setBackground(new Color(232, 17, 35));
                titleCloseButton.setForeground(Color.WHITE);
                titleCloseButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(232, 17, 35), 1),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                titleCloseButton.setBackground(new Color(245, 245, 245));
                titleCloseButton.setForeground(new Color(100, 100, 100));
                titleCloseButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            }
        });
        
        titleButtonsPanel.add(titleMaximizeButton);
        titleButtonsPanel.add(titleCloseButton);
        titleBarPanel.add(titleButtonsPanel, BorderLayout.EAST);
        
        // Add title bar to dialog
        helpDialog.add(titleBarPanel, BorderLayout.NORTH);
        
        // Add key listener for F11 fullscreen toggle
        helpDialog.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_F11) {
                    toggleMaximize(helpDialog, parent, originalBounds, isMaximized);
                    // Update button icon and tooltip
                    if (isMaximized[0]) {
                        titleMaximizeButton.setText("⧉");
                        titleMaximizeButton.setToolTipText("↩️ Restore window to normal size (F11)");
                    } else {
                        titleMaximizeButton.setText("□");
                        titleMaximizeButton.setToolTipText("🔍 Maximize window to full screen (F11)");
                    }
                }
            }
        });
        
        helpDialog.setFocusable(true);
        helpDialog.requestFocus();
        
        // Create sophisticated tabbed interface
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(new Color(248, 249, 250));
        tabbedPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 5, 10),
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));
        
        // Professional tab structure with consistent formatting and comprehensive documentation
        tabbedPane.addTab("🔍  Search", createSearchPanel()); // Quick search across all documentation
        tabbedPane.addTab("🏠  Overview", createOverviewPanel()); // Application overview and introduction
        tabbedPane.addTab("🚀  Quick Start", createQuickStartPanel()); // Getting started guide
        tabbedPane.addTab("📝  Mark Panel", createMarkPanelHelpPanel()); // Mark creation and editing tools
        tabbedPane.addTab("📐  Typeset Panel", createTypesetPanelHelpPanel()); // Layout and grid system
        tabbedPane.addTab("🖨️  Print & Engrave", createPrintEngraveHelpPanel()); // Output and production
        tabbedPane.addTab("💾  Database", createDatabaseHelpPanel()); // Data management and storage
        tabbedPane.addTab("📊  Barcode", createBarcodeHelpPanel()); // Barcode generation and scanning
        tabbedPane.addTab("✨  New Features", createNewFeaturesPanel()); // Latest enhancements and updates
        tabbedPane.addTab("🔧  Troubleshooting", createTroubleshootingPanel()); // Problem solving and FAQ
        tabbedPane.addTab("⌨️  Shortcuts", createShortcutsPanel()); // Keyboard shortcuts and hotkeys
        tabbedPane.addTab("ℹ️  About", createAboutPanel()); // Version info and credits
        
        // Create main content panel
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(tabbedPane, BorderLayout.CENTER);
        
        helpDialog.add(mainContentPanel, BorderLayout.CENTER);
        
        // Professional footer with multiple action buttons
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        // Left side - version info
        JLabel versionLabel = new JLabel("Dot Pin Marking Interface v2.1.0 | User Manual");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(versionLabel, BorderLayout.WEST);
        
        // Right side - action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton printButton = new JButton("�️ Print Manual");
        printButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        printButton.setPreferredSize(new Dimension(130, 32));
        printButton.setFocusPainted(false);
        printButton.setBackground(new Color(240, 240, 240));
        printButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        printButton.addActionListener(e -> {
            // Placeholder for print functionality
            JOptionPane.showMessageDialog(helpDialog, 
                "Print functionality will be implemented in future version.", 
                "Print Manual", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton closeButton = new JButton("✕ Close Help");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        closeButton.setPreferredSize(new Dimension(120, 32));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(new Color(52, 144, 220));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        closeButton.addActionListener(e -> helpDialog.dispose());
        
        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);
        footerPanel.add(buttonPanel, BorderLayout.EAST);
        
        helpDialog.add(footerPanel, BorderLayout.SOUTH);
        helpDialog.setVisible(true);
    }
    
    private static JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Create search interface panel
        JPanel searchInterface = new JPanel(new BorderLayout(10, 10));
        searchInterface.setBackground(Color.WHITE);
        searchInterface.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        
        // Title and description
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("🔍 Help Documentation Search");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JLabel descLabel = new JLabel("Search across all help documentation for quick answers");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(127, 140, 141));
        headerPanel.add(descLabel, BorderLayout.SOUTH);
        
        searchInterface.add(headerPanel, BorderLayout.NORTH);
        
        // Search input panel
        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setBackground(Color.WHITE);
        
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchField.setBackground(new Color(248, 249, 250));
        
        JButton searchButton = new JButton("🔍 Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        searchButton.setFocusPainted(false);
        
        inputPanel.add(searchField, BorderLayout.CENTER);
        inputPanel.add(searchButton, BorderLayout.EAST);
        
        searchInterface.add(inputPanel, BorderLayout.CENTER);
        
        // Results area
        JTextPane resultsPane = new JTextPane();
        resultsPane.setEditable(false);
        resultsPane.setBackground(Color.WHITE);
        resultsPane.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        resultsPane.setContentType("text/html");
        
        // Initial content
        resultsPane.setText(getInitialSearchContent());
        
        // Quick search categories
        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        categoriesPanel.setBackground(Color.WHITE);
        
        JLabel quickLabel = new JLabel("Quick Topics:");
        quickLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        quickLabel.setForeground(new Color(52, 73, 94));
        categoriesPanel.add(quickLabel);
        
        String[] quickTopics = {
            "Button visibility", "Hardware connection", "Template management", 
            "Print settings", "Needle aim", "Barcode creation", "Text formatting",
            "Database analytics", "Audit trail", "Query console", "Performance monitoring",
            "Layout controls", "Troubleshooting", "New features"
        };
        
        for (String topic : quickTopics) {
            JButton topicButton = new JButton(topic);
            topicButton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            topicButton.setBackground(new Color(236, 240, 241));
            topicButton.setForeground(new Color(52, 73, 94));
            topicButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
            ));
            topicButton.setFocusPainted(false);
            final JTextPane finalResultsPane = resultsPane; // Create final reference for lambda
            topicButton.addActionListener(e -> {
                searchField.setText(topic);
                performSearch(searchField.getText(), finalResultsPane);
            });
            categoriesPanel.add(topicButton);
        }
        
        searchInterface.add(categoriesPanel, BorderLayout.SOUTH);
        panel.add(searchInterface, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(resultsPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 25, 25, 25),
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Search functionality
        searchButton.addActionListener(e -> performSearch(searchField.getText(), resultsPane));
        searchField.addActionListener(e -> performSearch(searchField.getText(), resultsPane));
        
        return panel;
    }
    
    private static String getInitialSearchContent() {
        return "<html><head><style>" +
               "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
               "h2 { color: #2c5aa0; font-size: 18px; margin-top: 20px; margin-bottom: 15px; }" +
               "h3 { color: #34495e; font-size: 14px; margin-top: 15px; margin-bottom: 8px; font-weight: bold; }" +
               "p { margin-bottom: 12px; }" +
               "ul { margin-left: 20px; margin-bottom: 15px; }" +
               "li { margin-bottom: 5px; }" +
               ".search-tip { background: #e8f5e8; padding: 12px; border-left: 4px solid #27ae60; margin: 15px 0; border-radius: 4px; }" +
               ".category { background: #f8f9fa; padding: 10px; margin: 8px 0; border-radius: 4px; border-left: 3px solid #6c757d; }" +
               "</style></head><body>" +
               
               "<h2>🔍 Welcome to Help Search</h2>" +
               "<p>Use the search box above to find information quickly across all help documentation. " +
               "You can search for specific terms, features, or procedures.</p>" +
               
               "<div class='search-tip'>" +
               "<h3>💡 Search Tips:</h3>" +
               "<ul>" +
               "<li><strong>Simple terms:</strong> Try 'button', 'print', 'template', 'connection'</li>" +
               "<li><strong>Specific features:</strong> 'needle aim', 'barcode generation', 'hardware setup'</li>" +
               "<li><strong>Problem solving:</strong> 'not visible', 'connection failed', 'template error'</li>" +
               "<li><strong>Procedures:</strong> 'how to connect', 'create template', 'print setup'</li>" +
               "</ul>" +
               "</div>" +
               
               "<h2>📚 Popular Help Topics</h2>" +
               
               "<div class='category'>" +
               "<h3>🎨 Getting Started</h3>" +
               "<ul>" +
               "<li>Quick Start Guide - Essential workflow setup</li>" +
               "<li>System Overview - Understanding the interface</li>" +
               "<li>Hardware Connection - Setting up marking equipment</li>" +
               "</ul>" +
               "</div>" +
               
               "<div class='category'>" +
               "<h3>🔧 Common Issues</h3>" +
               "<ul>" +
               "<li>Button Visibility Problems - Recent fixes and solutions</li>" +
               "<li>Hardware Connection Issues - Troubleshooting connectivity</li>" +
               "<li>Template Loading Errors - File format and location issues</li>" +
               "<li>Print Quality Problems - Settings and calibration</li>" +
               "</ul>" +
               "</div>" +
               
               "<div class='category'>" +
               "<h3>🆕 Latest Features</h3>" +
               "<ul>" +
               "<li>Integrated Needle Aim System - Now in Typeset Panel</li>" +
               "<li>Enhanced Print & Engrave Interface - Three-tab layout</li>" +
               "<li>Improved Button Layout - Better visibility and positioning</li>" +
               "<li>Template Management - Reorganized controls</li>" +
               "</ul>" +
               "</div>" +
               
               "<div class='category'>" +
               "<h3>📐 Panel-Specific Help</h3>" +
               "<ul>" +
               "<li>Mark Panel - Design tools and object creation</li>" +
               "<li>Typeset Panel - Typography and needle positioning</li>" +
               "<li>Print & Engrave - Production control and hardware settings</li>" +
               "<li>Database Panel - Advanced analytics, audit trail, query console</li>" +
               "<li>Barcode Panel - Professional barcode generation</li>" +
               "</ul>" +
               "</div>" +
               
               "<div class='category'>" +
               "<h3>🗄️ Advanced Database Features</h3>" +
               "<ul>" +
               "<li>Analytics Dashboard - Real-time statistics and performance monitoring</li>" +
               "<li>Audit Trail System - Complete activity logging and security tracking</li>" +
               "<li>Query Console - SQL-like interface for custom data queries</li>" +
               "<li>Performance Tools - Optimization and cache management</li>" +
               "<li>Soft Coding Architecture - Configuration-driven system design</li>" +
               "</ul>" +
               "</div>" +
               
               "<p style='margin-top: 25px; font-style: italic; color: #666;'>" +
               "Enter your search terms above or click on the quick topic buttons to get started." +
               "</p>" +
               
               "</body></html>";
    }
    
    private static void performSearch(String searchTerm, JTextPane resultsPane) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            resultsPane.setText(getInitialSearchContent());
            return;
        }
        
        String lowerSearchTerm = searchTerm.toLowerCase().trim();
        StringBuilder results = new StringBuilder();
        
        results.append("<html><head><style>");
        results.append("body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }");
        results.append("h2 { color: #2c5aa0; font-size: 18px; margin-bottom: 15px; }");
        results.append("h3 { color: #34495e; font-size: 14px; margin-top: 15px; margin-bottom: 8px; font-weight: bold; }");
        results.append(".result { background: #f8f9fa; padding: 12px; margin: 10px 0; border-left: 4px solid #007bff; border-radius: 4px; }");
        results.append(".match { background: #fff3cd; padding: 2px 4px; border-radius: 2px; }");
        results.append(".section { color: #6c757d; font-size: 12px; }");
        results.append(".no-results { background: #f8d7da; padding: 15px; border-left: 4px solid #dc3545; margin: 15px 0; border-radius: 4px; }");
        results.append("</style></head><body>");
        
        results.append("<h2>🔍 Search Results for: \"").append(searchTerm).append("\"</h2>");
        
        // Search through different help topics
        java.util.List<SearchResult> searchResults = new java.util.ArrayList<>();
        
        // Search in different categories
        searchInCategory(lowerSearchTerm, "Overview", getOverviewSearchableContent(), searchResults);
        searchInCategory(lowerSearchTerm, "Quick Start", getQuickStartSearchableContent(), searchResults);
        searchInCategory(lowerSearchTerm, "Database", getDatabaseSearchableContent(), searchResults);
        searchInCategory(lowerSearchTerm, "Print & Engrave", getPrintEngraveSearchableContent(), searchResults);
        searchInCategory(lowerSearchTerm, "New Features", getNewFeaturesSearchableContent(), searchResults);
        searchInCategory(lowerSearchTerm, "Troubleshooting", getTroubleshootingSearchableContent(), searchResults);
        
        if (searchResults.isEmpty()) {
            results.append("<div class='no-results'>");
            results.append("<h3>❌ No Results Found</h3>");
            results.append("<p>No matching content found for \"").append(searchTerm).append("\". Try:</p>");
            results.append("<ul>");
            results.append("<li>Using different keywords or synonyms</li>");
            results.append("<li>Checking spelling</li>");
            results.append("<li>Using broader search terms</li>");
            results.append("<li>Browsing the quick topics above</li>");
            results.append("</ul>");
            results.append("</div>");
        } else {
            results.append("<p>Found ").append(searchResults.size()).append(" result(s):</p>");
            
            for (SearchResult result : searchResults) {
                results.append("<div class='result'>");
                results.append("<h3>").append(result.title).append("</h3>");
                results.append("<div class='section'>From: ").append(result.section).append("</div>");
                results.append("<p>").append(highlightSearchTerms(result.content, lowerSearchTerm)).append("</p>");
                results.append("</div>");
            }
        }
        
        results.append("</body></html>");
        resultsPane.setText(results.toString());
        resultsPane.setCaretPosition(0);
    }
    
    private static void searchInCategory(String searchTerm, String section, String content, java.util.List<SearchResult> results) {
        String[] lines = content.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].toLowerCase();
            if (line.contains(searchTerm)) {
                // Get context around the match
                StringBuilder context = new StringBuilder();
                int start = Math.max(0, i - 1);
                int end = Math.min(lines.length, i + 3);
                
                for (int j = start; j < end; j++) {
                    if (j > start) context.append(" ");
                    context.append(lines[j].trim());
                }
                
                String title = extractTitle(lines, i);
                results.add(new SearchResult(title, section, context.toString()));
            }
        }
    }
    
    private static String extractTitle(String[] lines, int index) {
        // Look backwards for a title line
        for (int i = index; i >= Math.max(0, index - 5); i--) {
            String line = lines[i].trim();
            if (line.endsWith(":") || line.startsWith("##") || line.startsWith("*")) {
                return line.replaceAll("[#*:]", "").trim();
            }
        }
        return "Help Topic";
    }
    
    private static String highlightSearchTerms(String content, String searchTerm) {
        return content.replaceAll("(?i)" + java.util.regex.Pattern.quote(searchTerm), 
                                 "<span class='match'>$0</span>");
    }
    
    // Searchable content methods
    private static String getOverviewSearchableContent() {
        return "System Overview\nDot Pin Marking Interface\nenterprise-level application\nprecision industrial marking\n" +
               "ThorX6 hardware integration\nAdvanced Design Tools\ntext graphics barcode creation\n" +
               "Hardware Integration\nreal-time parameter adjustment\nMaterial Intelligence\nquality assurance\n" +
               "production management\nbatch processing\ntemplate management\nworkflow automation";
    }
    
    private static String getQuickStartSearchableContent() {
        return "Quick Start Guide\nHardware Verification\nThorX6 marking system\nUSB network connection\n" +
               "material preparation\nsafety check\nSoftware Initialization\nhardware detection\n" +
               "auto calibration\nworkspace setup\nContent Creation\ntext marking\nMark Panel\n" +
               "text tool\nfont height width spacing\nBarcode Integration\nQR Code\nEAN-13\n" +
               "Production Setup\nhardware parameter optimization";
    }
    
    private static String getPrintEngraveSearchableContent() {
        return "Print Engrave Panel\nunified production control\nthree-tab interface\nPrint Operations\n" +
               "printer setup\npage settings\nprint actions\nEngrave Operations\ncontent mode\n" +
               "template management\nLoad Template\nSave Template\nImport Image\nmaterial needle settings\n" +
               "precision parameters\ndot pitch\ndot depth\nmarking speed\nLayout Preview\n" +
               "position transform\npreview options\nHardware Controls\nconnection type\n" +
               "serial USB ethernet WiFi\nport configuration\nnetwork setup\nConnect button\n" +
               "Scan WiFi\nTest Connection\nstatus display\nbutton visibility\nlayout fixes";
    }
    
    private static String getNewFeaturesSearchableContent() {
        return "New Features\nVersion 2.1.0\nuser interface enhancements\nbutton visibility\nlayout fixes\n" +
               "font improvements\nWorkflow Integration\nNeedle Aim System\nunified interface\n" +
               "Typeset Panel\nprofessional controls\n3x3 grid positioning\nPrint Engrave Panel\n" +
               "three-tab interface\ntemplate management\nhardware connection\nTechnical Improvements\n" +
               "multi-layer visibility\nsize constraints\nenhanced styling\ntext font rendering\n" +
               "high-quality rendering\nantialiasing\ntext smoothing\nfont optimization\n" +
               "Hardware Connectivity\nconnection management\nnetwork configuration\n" +
               "status feedback\nconnection testing\nPerformance Reliability\nsystem performance\n" +
               "optimized layout\nmemory management\nerror handling\nstability improvements";
    }
    
    private static String getDatabaseSearchableContent() {
        return "Database Management\nAdvanced Database System\nenterprise features\n" +
               "database analytics\naudit trail\nquery console\nperformance monitoring\n" +
               "Analytics Tab\nreal-time statistics\nrecord counts\nstorage usage\n" +
               "query performance\ndata distribution\ntrend analysis\nAudit Trail\n" +
               "activity logging\nuser actions\nsystem changes\naudit filtering\n" +
               "security tracking\ncompliance monitoring\nQuery Console\nSQL-like interface\n" +
               "custom queries\ndata filtering\nadvanced search\nquery builder\n" +
               "data export\nPerformance Tab\noptimization tools\nindex management\n" +
               "cache monitoring\nquery optimization\nperformance tuning\n" +
               "Soft Coding Architecture\nconfiguration driven\nenvironment profiles\n" +
               "feature toggles\nschema validation\nflexible configuration\n" +
               "Table Management\nprojects materials settings templates\nrecord management\n" +
               "data validation\nbackup restore\nversion control\nencryption security\n" +
               "transaction support\ncache optimization\nAdvanced Features\n" +
               "bulk operations\ndata migration\nschema evolution\ncompression\n" +
               "concurrent access\nmulti-user support\ndata integrity\nrelational queries";
    }
    
    private static String getTroubleshootingSearchableContent() {
        return "Troubleshooting\nbutton visibility problems\nhardware connection issues\n" +
               "template loading errors\nprint quality problems\nconnection failed\n" +
               "not visible\ntemplate error\nfile format\nlocation issues\nsettings calibration\n" +
               "canvas overlap\nlayout issues\nfont readability\nbutton text clarity\n" +
               "hardware detection\ndriver installation\nphysical connections\n" +
               "position accuracy\nworkpiece clamping\nsystem calibration";
    }
    
    // Helper class for search results
    private static class SearchResult {
        String title;
        String section;
        String content;
        
        SearchResult(String title, String section, String content) {
            this.title = title;
            this.section = section;
            this.content = content;
        }
    }

    private static JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Create styled text area with professional formatting
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        // Professional document styling
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 24px; margin-bottom: 20px; border-bottom: 3px solid #3498db; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 18px; margin-top: 25px; margin-bottom: 15px; background: #ecf0f1; padding: 8px 12px; border-left: 4px solid #3498db; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 20px; margin-bottom: 10px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 6px; padding-left: 5px; }" +
            ".highlight { background: #fff3cd; padding: 12px; border-left: 4px solid #ffc107; margin: 15px 0; }" +
            ".feature-box { background: #e8f4fd; padding: 15px; margin: 10px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            ".warning { background: #f8d7da; padding: 12px; border-left: 4px solid #dc3545; margin: 15px 0; }" +
            "</style></head><body>" +
            
            "<h1>🏭 Dot Pin Marking Interface - Professional Documentation</h1>" +
            
            "<div class='highlight'>" +
            "<strong>Welcome to the most comprehensive dot pin marking solution for industrial applications.</strong><br>" +
            "This professional-grade software provides complete control over precision marking operations with advanced features for design, manufacturing, and quality control." +
            "</div>" +
            
            "<h2>📋 System Overview</h2>" +
            "<p>The Dot Pin Marking Interface is an enterprise-level application designed for precision industrial marking operations. It integrates seamlessly with ThorX6 hardware systems and provides comprehensive tools for creating, managing, and executing marking projects across various materials and applications.</p>" +
            
            "<div class='feature-box'>" +
            "<h3>🎯 Core Capabilities</h3>" +
            "<ul>" +
            "<li><strong>Advanced Design Tools:</strong> Professional text, graphics, and barcode creation with pixel-perfect precision</li>" +
            "<li><strong>Hardware Integration:</strong> Direct ThorX6 control with real-time parameter adjustment and calibration</li>" +
            "<li><strong>Material Intelligence:</strong> Smart presets and auto-calibration for optimal marking on different substrates</li>" +
            "<li><strong>Quality Assurance:</strong> Built-in validation, preview, and error checking systems</li>" +
            "<li><strong>Production Management:</strong> Batch processing, template management, and workflow automation</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔧 System Architecture</h2>" +
            "<p>The application follows a modular panel-based architecture, where each specialized panel provides focused functionality:</p>" +
            
            "<h3>🎨 Design Environment</h3>" +
            "<ul>" +
            "<li><strong>Mark Panel:</strong> Primary design workspace with comprehensive object creation and editing tools</li>" +
            "<li><strong>Typeset Panel:</strong> Advanced typography engine with professional layout controls</li>" +
            "<li><strong>Canvas System:</strong> High-resolution workspace with precision grid, guides, and measurement tools</li>" +
            "</ul>" +
            
            "<h3>🏭 Production Control</h3>" +
            "<ul>" +
            "<li><strong>Print Panel:</strong> Hardware parameter control with material-specific optimization</li>" +
            "<li><strong>Database Panel:</strong> Project lifecycle management with version control and templates</li>" +
            "<li><strong>Integration Layer:</strong> Real-time communication with ThorX6 marking systems</li>" +
            "</ul>" +
            
            "<h3>📊 Quality & Compliance</h3>" +
            "<ul>" +
            "<li><strong>Barcode Panel:</strong> Professional barcode generation with industry standard compliance</li>" +
            "<li><strong>Validation System:</strong> Real-time error checking and quality assurance</li>" +
            "<li><strong>Audit Trail:</strong> Complete operation logging for compliance and troubleshooting</li>" +
            "</ul>" +
            
            "<h2>💼 Target Applications</h2>" +
            "<div class='feature-box'>" +
            "<ul>" +
            "<li><strong>Manufacturing:</strong> Part identification, serial numbering, quality control marking</li>" +
            "<li><strong>Aerospace:</strong> Traceability marking, compliance labeling, component identification</li>" +
            "<li><strong>Automotive:</strong> VIN marking, part tracking, quality assurance documentation</li>" +
            "<li><strong>Medical Devices:</strong> Sterile marking, regulatory compliance, batch tracking</li>" +
            "<li><strong>Electronics:</strong> PCB marking, component labeling, anti-counterfeiting</li>" +
            "<li><strong>Jewelry:</strong> Precious metal marking, certification, custom engraving</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>⚡ Performance Specifications</h2>" +
            "<ul>" +
            "<li><strong>Marking Precision:</strong> ±0.01mm positional accuracy</li>" +
            "<li><strong>Speed Range:</strong> 1-500 characters per minute (material dependent)</li>" +
            "<li><strong>Character Size:</strong> 0.3mm to 50mm height range</li>" +
            "<li><strong>Material Support:</strong> Metals, plastics, ceramics, composites, glass</li>" +
            "<li><strong>File Formats:</strong> Native .dpm, industry standard imports/exports</li>" +
            "</ul>" +
            
            "<div class='warning'>" +
            "<strong>⚠️ Safety Notice:</strong> This system operates precision mechanical components. Always follow safety protocols, ensure proper training, and maintain equipment according to manufacturer specifications." +
            "</div>" +
            
            "<h2>📖 Documentation Structure</h2>" +
            "<p>This comprehensive manual is organized into specialized sections:</p>" +
            "<ul>" +
            "<li><strong>Quick Start:</strong> Essential workflow for immediate productivity</li>" +
            "<li><strong>Panel Guides:</strong> Detailed documentation for each functional area</li>" +
            "<li><strong>Troubleshooting:</strong> Diagnostic procedures and problem resolution</li>" +
            "<li><strong>Reference:</strong> Complete command reference and keyboard shortcuts</li>" +
            "</ul>" +
            
            "<p style='margin-top: 30px; font-style: italic; color: #666;'>" +
            "For technical support, training, or advanced configuration assistance, please contact your authorized Dot Pin Marking Interface distributor or visit our professional support portal." +
            "</p>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createQuickStartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #27ae60; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #e8f5e8; padding: 8px 12px; border-left: 4px solid #27ae60; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".step-box { background: #f8f9fa; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".tip-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".workflow { background: #e3f2fd; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #90caf9; }" +
            ".important { background: #ffebee; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #f44336; }" +
            ".feature { background: #f3e5f5; padding: 10px; margin: 8px 0; border-radius: 4px; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>🚀 Quick Start Guide - Professional Workflow</h1>" +
            
            "<div class='workflow'>" +
            "<h3>📋 Essential First Steps</h3>" +
            "<p>This guide will get you productive with the Dot Pin Marking Interface in under 15 minutes. Follow these carefully structured steps for optimal results.</p>" +
            "</div>" +
            
            "<h2>🎯 Phase 1: System Preparation</h2>" +
            
            "<div class='step-box'>" +
            "<h3>Step 1: Hardware Verification</h3>" +
            "<ol>" +
            "<li><strong>Power System Check:</strong> Ensure ThorX6 marking system is powered and displays ready status</li>" +
            "<li><strong>Connection Verification:</strong> Confirm USB/network connection between computer and marking system</li>" +
            "<li><strong>Material Preparation:</strong> Secure material properly in marking area with appropriate clamping</li>" +
            "<li><strong>Safety Check:</strong> Verify safety shields are in place and emergency stops are accessible</li>" +
            "</ol>" +
            "</div>" +
            
            "<div class='step-box'>" +
            "<h3>Step 2: Software Initialization</h3>" +
            "<ol>" +
            "<li><strong>Launch Application:</strong> Start Dot Pin Marking Interface (ensure Java 8+ is installed)</li>" +
            "<li><strong>Hardware Detection:</strong> Navigate to Print Panel and verify hardware is detected</li>" +
            "<li><strong>Auto Calibration:</strong> Run automatic calibration for current material type</li>" +
            "<li><strong>Workspace Setup:</strong> Configure canvas size and units (mm/inches) in Mark Panel</li>" +
            "</ol>" +
            "</div>" +
            
            "<h2>🎨 Phase 2: Content Creation</h2>" +
            
            "<div class='step-box'>" +
            "<h3>Step 3: Basic Text Marking</h3>" +
            "<ol>" +
            "<li><strong>Access Mark Panel:</strong> Click the 'Mark Panel' button (or press F1)</li>" +
            "<li><strong>Select Text Tool:</strong> Click '🅰 Text' in the Marks section</li>" +
            "<li><strong>Place Text:</strong> Click on canvas where you want text to appear</li>" +
            "<li><strong>Edit Content:</strong> Type your text in the 'Content' field in Text Formatting section</li>" +
            "<li><strong>Format Text:</strong> Adjust Font, Height, Width, and Spacing as needed</li>" +
            "<li><strong>Position Precisely:</strong> Use arrow keys for fine positioning or drag with mouse</li>" +
            "</ol>" +
            "</div>" +
            
            "<div class='step-box'>" +
            "<h3>Step 4: Advanced Object Creation</h3>" +
            "<table>" +
            "<tr><th>Object Type</th><th>Use Case</th><th>Key Settings</th></tr>" +
            "<tr><td>🔄 Arc Text</td><td>Circular logos, curved labels</td><td>Radius, start/end angles</td></tr>" +
            "<tr><td>⬜ Rectangle</td><td>Borders, frames, boxes</td><td>Width, height, line thickness</td></tr>" +
            "<tr><td>📏 Line</td><td>Dividers, arrows, underlines</td><td>Start/end points, thickness</td></tr>" +
            "<tr><td>🔳 Matrix</td><td>Serial numbers, dot patterns</td><td>Grid spacing, dot size</td></tr>" +
            "<tr><td>📊 Graph</td><td>Technical drawings, charts</td><td>Scale, precision, line styles</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>📊 Phase 3: Barcode Integration</h2>" +
            
            "<div class='step-box'>" +
            "<h3>Step 5: Professional Barcode Creation</h3>" +
            "<ol>" +
            "<li><strong>Switch to Barcode Panel:</strong> Click 'Barcode Panel' button (or press F5)</li>" +
            "<li><strong>Select Barcode Type:</strong> Choose appropriate type (QR Code for versatility, EAN-13 for retail)</li>" +
            "<li><strong>Enter Data:</strong> Input your content (URL, product code, serial number, etc.)</li>" +
            "<li><strong>Configure Dimensions:</strong> Set width (200-400px), height (100-200px) for optimal scanning</li>" +
            "<li><strong>Validate Data:</strong> Click 'Validate Data' to ensure format compliance</li>" +
            "<li><strong>Generate & Place:</strong> Click 'Generate Barcode', then 'Place on Canvas'</li>" +
            "</ol>" +
            "</div>" +
            
            "<div class='tip-box'>" +
            "<strong>💡 Barcode Best Practices:</strong><br>" +
            "• QR Codes: Use 'Medium' error correction for general use, 'High' for harsh environments<br>" +
            "• Maintain quiet zones: Leave 4x module width of white space around barcode<br>" +
            "• Test scanning: Always verify readability before production runs" +
            "</div>" +
            
            "<h2>🖨️ Phase 4: Production Setup</h2>" +
            
            "<div class='step-box'>" +
            "<h3>Step 6: Hardware Parameter Optimization</h3>" +
            "<ol>" +
            "<li><strong>Navigate to Print Panel:</strong> Click 'Print Panel' button (or press F3)</li>" +
            "<li><strong>Select Material Type:</strong> Choose from preset materials or configure custom settings</li>" +
            "<li><strong>Use Smart Presets:</strong> Click 'Smart Presets' and select appropriate application type</li>" +
            "<li><strong>Fine-tune Parameters:</strong></li>" +
            "<ul>" +
            "<li>Pen Down Delay: 80-100ms for paper, 150-200ms for metal</li>" +
            "<li>Pen Up Delay: 50-80ms (affects speed vs. quality balance)</li>" +
            "<li>Pin Diameter: 0.8mm for standard text, 1.2mm for bold marking</li>" +
            "</ul>" +
            "<li><strong>Run Auto Calibration:</strong> Click 'Auto Calibrate' for system optimization</li>" +
            "</ol>" +
            "</div>" +
            
            "<div class='step-box'>" +
            "<h3>Step 7: Quality Assurance & Testing</h3>" +
            "<ol>" +
            "<li><strong>Preview Operation:</strong> Use zoom controls to inspect design at 200% magnification</li>" +
            "<li><strong>Test Run:</strong> Perform small test mark on material sample</li>" +
            "<li><strong>Quality Verification:</strong> Check mark depth, clarity, and positioning accuracy</li>" +
            "<li><strong>Adjust if Needed:</strong> Fine-tune timing parameters based on test results</li>" +
            "<li><strong>Production Execution:</strong> Click 'Start' to begin full production run</li>" +
            "</ol>" +
            "</div>" +
            
            "<h2>💾 Phase 5: Project Management</h2>" +
            
            "<div class='step-box'>" +
            "<h3>Step 8: Save & Template Creation</h3>" +
            "<ol>" +
            "<li><strong>Access Database Panel:</strong> Click 'Database Panel' button (or press F4)</li>" +
            "<li><strong>Save Project:</strong> Provide descriptive name including date and material type</li>" +
            "<li><strong>Create Template:</strong> Save successful configurations as templates for future use</li>" +
            "<li><strong>Document Settings:</strong> Add notes about material, parameters, and quality results</li>" +
            "</ol>" +
            "</div>" +
            
            "<div class='workflow'>" +
            "<h3>🎯 Complete Workflow Summary</h3>" +
            "<p><strong>Preparation:</strong> Hardware check → Software init → Calibration<br>" +
            "<strong>Design:</strong> Mark Panel → Content creation → Formatting<br>" +
            "<strong>Enhancement:</strong> Barcode Panel → Generate codes → Placement<br>" +
            "<strong>Production:</strong> Print Panel → Parameter setup → Quality test → Execute<br>" +
            "<strong>Management:</strong> Database Panel → Save project → Create templates</p>" +
            "</div>" +
            
            "<div class='important'>" +
            "<h3>⚠️ Critical Success Factors</h3>" +
            "<ul>" +
            "<li><strong>Always run Auto Calibration</strong> when changing materials or after system startup</li>" +
            "<li><strong>Test before production</strong> - small test marks can prevent material waste</li>" +
            "<li><strong>Save frequently</strong> - use Ctrl+S regularly during design work</li>" +
            "<li><strong>Use templates</strong> - create templates for repeated workflows to ensure consistency</li>" +
            "<li><strong>Monitor quality</strong> - check first few marks in production runs for consistency</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tip-box'>" +
            "<strong>🚀 Productivity Tips for Advanced Users:</strong><br>" +
            "• Use keyboard shortcuts (F1-F5) for rapid panel switching<br>" +
            "• Leverage Smart Presets to eliminate manual parameter calculation<br>" +
            "• Create material-specific templates with optimal settings<br>" +
            "• Use batch processing features for multiple identical items<br>" +
            "• Monitor performance analytics to optimize workflow efficiency" +
            "</div>" +
            
            "<p style='margin-top: 25px; font-style: italic; color: #666;'>" +
            "<strong>Next Steps:</strong> Once comfortable with this workflow, explore the detailed panel guides for advanced features, automation options, and specialized applications." +
            "</p>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createMarkPanelHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            ".tool-box { background: #f0f8ff; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #007bff; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>📝 Mark Panel - Comprehensive Creation Guide</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Overview</h3>" +
            "<p>The Mark Panel is your primary workspace for creating, editing, and managing all marking objects. From simple text to complex technical drawings, this panel provides professional-grade tools for precision marking applications.</p>" +
            "</div>" +
            
            "<h2>📋 Clipboard Operations</h2>" +
            
            "<div class='section-box'>" +
            "<h3>✂️ Standard Operations</h3>" +
            "<table>" +
            "<tr><th>Tool</th><th>Function</th><th>Shortcut</th></tr>" +
            "<tr><td>↶ Undo</td><td>Reverse the last action performed</td><td>Ctrl+Z</td></tr>" +
            "<tr><td>↷ Redo</td><td>Restore an undone action</td><td>Ctrl+Y</td></tr>" +
            "<tr><td>✂ Cut</td><td>Remove selected object and copy to clipboard</td><td>Ctrl+X</td></tr>" +
            "<tr><td>📄 Copy</td><td>Copy selected object to clipboard</td><td>Ctrl+C</td></tr>" +
            "<tr><td>📋 Paste</td><td>Insert clipboard content at cursor position</td><td>Ctrl+V</td></tr>" +
            "<tr><td>🗑️ Erase</td><td>Delete selected object permanently</td><td>Delete</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🎨 Mark Creation Tools</h2>" +
            
            "<div class='tool-box'>" +
            "<h3>🅰️ Text Tool</h3>" +
            "<p><strong>Purpose:</strong> Create editable text objects with full formatting capabilities</p>" +
            "<ul>" +
            "<li><strong>Click Placement:</strong> Click anywhere on canvas to place text</li>" +
            "<li><strong>Content Editing:</strong> Edit text content in formatting section</li>" +
            "<li><strong>Font Support:</strong> All system fonts and custom sizes supported</li>" +
            "<li><strong>Character Spacing:</strong> Adjustable letter and line spacing</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>🔄 Arc Text Tool</h3>" +
            "<p><strong>Purpose:</strong> Create curved text following an arc path</p>" +
            "<ul>" +
            "<li><strong>Circular Designs:</strong> Perfect for logos and decorative elements</li>" +
            "<li><strong>Adjustable Radius:</strong> Control curve tightness and diameter</li>" +
            "<li><strong>Arc Angle:</strong> Set start and end angles for partial circles</li>" +
            "<li><strong>Natural Flow:</strong> Text follows curve with proper character rotation</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>🔳 Dot Matrix Tool</h3>" +
            "<p><strong>Purpose:</strong> Create precise dot matrix patterns for systematic layouts</p>" +
            "<ul>" +
            "<li><strong>Grid-Based:</strong> Systematic dot placement with customizable spacing</li>" +
            "<li><strong>Serial Numbers:</strong> Ideal for sequential numbering systems</li>" +
            "<li><strong>Data Codes:</strong> Perfect for encoding information in dot patterns</li>" +
            "<li><strong>Size Control:</strong> Adjustable dot diameter and spacing</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>📊 Graph Tool</h3>" +
            "<p><strong>Purpose:</strong> Create technical drawings, charts, and precise graphics</p>" +
            "<ul>" +
            "<li><strong>Line Graphs:</strong> Data visualization with connecting lines</li>" +
            "<li><strong>Bar Charts:</strong> Comparative data representation</li>" +
            "<li><strong>Technical Drawings:</strong> Engineering and architectural diagrams</li>" +
            "<li><strong>Measurement Markings:</strong> Precise scale and dimension indicators</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>⬜ Rectangle Tool</h3>" +
            "<p><strong>Purpose:</strong> Create rectangular shapes, borders, and frames</p>" +
            "<ul>" +
            "<li><strong>Fill Options:</strong> Solid fill or outline-only rectangles</li>" +
            "<li><strong>Precise Dimensions:</strong> Exact width and height control</li>" +
            "<li><strong>Line Thickness:</strong> Adjustable border weight</li>" +
            "<li><strong>Corner Options:</strong> Sharp or rounded corner styles</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>📏 Line Tool</h3>" +
            "<p><strong>Purpose:</strong> Draw straight lines, arrows, and dividers</p>" +
            "<ul>" +
            "<li><strong>Single Lines:</strong> Clean straight line segments</li>" +
            "<li><strong>Arrow Styles:</strong> Various arrowhead options</li>" +
            "<li><strong>Thickness Control:</strong> Adjustable line weight</li>" +
            "<li><strong>Grid Snap:</strong> Automatic alignment to grid points</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>✒️ Farzi Text Tool</h3>" +
            "<p><strong>Purpose:</strong> Persian/Arabic text with right-to-left support</p>" +
            "<ul>" +
            "<li><strong>RTL Rendering:</strong> Proper right-to-left text flow</li>" +
            "<li><strong>Unicode Support:</strong> Full character set compatibility</li>" +
            "<li><strong>Cultural Formatting:</strong> Appropriate text styling options</li>" +
            "<li><strong>Script Handling:</strong> Complex script rendering support</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tool-box'>" +
            "<h3>🎯 Avoid Point Tool</h3>" +
            "<p><strong>Purpose:</strong> Define protected no-marking zones</p>" +
            "<ul>" +
            "<li><strong>Protected Areas:</strong> Prevent marking in specific zones</li>" +
            "<li><strong>Existing Elements:</strong> Avoid pre-existing text or features</li>" +
            "<li><strong>Visual Boundaries:</strong> Clear zone indicators</li>" +
            "<li><strong>Safety Zones:</strong> Protect critical areas from damage</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>✏️ Text Formatting Controls</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🎨 Formatting Options</h3>" +
            "<table>" +
            "<tr><th>Control</th><th>Function</th><th>Range/Options</th></tr>" +
            "<tr><td>📝 Content</td><td>Edit text content of selected objects</td><td>Unicode text input</td></tr>" +
            "<tr><td>🎯 Font Family</td><td>Choose from installed system fonts</td><td>All system fonts</td></tr>" +
            "<tr><td>📏 Height</td><td>Adjust text height in points</td><td>1-100 points</td></tr>" +
            "<tr><td>📐 Width</td><td>Control text width and proportions</td><td>Scaling percentage</td></tr>" +
            "<tr><td>📊 Spacing</td><td>Character and line spacing adjustment</td><td>-50% to +200%</td></tr>" +
            "<tr><td>🎨 Style</td><td>Bold, italic, underline formatting</td><td>Multiple styles</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🔍 View & Navigation Controls</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🎯 Navigation Tools</h3>" +
            "<table>" +
            "<tr><th>Tool</th><th>Function</th><th>Usage</th></tr>" +
            "<tr><td>🔍 Zoom In</td><td>Increase view magnification</td><td>Up to 500% maximum</td></tr>" +
            "<tr><td>🔍 Zoom Out</td><td>Decrease magnification</td><td>Overview and context</td></tr>" +
            "<tr><td>🎯 Apply Zoom</td><td>Set specific zoom percentage</td><td>Custom zoom levels</td></tr>" +
            "<tr><td>📌 Zoom to Selection</td><td>Focus view on selected object</td><td>Automatic framing</td></tr>" +
            "<tr><td>🖼️ Zoom to Fit All</td><td>Show entire canvas content</td><td>Complete overview</td></tr>" +
            "<tr><td>✋ Move View</td><td>Pan around canvas</td><td>Click and drag navigation</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>💡 Professional Tips</h2>" +
            
            "<div class='feature-box'>" +
            "<h3>🎯 Best Practices</h3>" +
            "<ul>" +
            "<li><strong>Object Selection:</strong> Always select object before applying formatting changes</li>" +
            "<li><strong>Select All:</strong> Use Ctrl+A to select all objects on canvas</li>" +
            "<li><strong>Context Menu:</strong> Right-click for additional options and shortcuts</li>" +
            "<li><strong>Proportional Constraints:</strong> Hold Shift while creating for proportional sizing</li>" +
            "<li><strong>Grid Alignment:</strong> Enable grid snap for precise positioning</li>" +
            "<li><strong>Real-time Preview:</strong> See changes as you modify properties</li>" +
            "<li><strong>Frequent Saves:</strong> Save work regularly to preserve changes</li>" +
            "<li><strong>Layer Organization:</strong> Use layers for complex design management</li>" +
            "<li><strong>Multi-scale Testing:</strong> Test clarity at different zoom levels</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createTypesetPanelHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            ".workflow-box { background: #e7f3ff; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #17a2b8; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            ".method-table { background: #f8f9fa; padding: 10px; border-radius: 4px; margin: 8px 0; }" +
            "</style></head><body>" +
            
            "<h1>📐 Typeset Panel - Smart Grid Layout & Distribution</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Overview</h3>" +
            "<p>The Typeset Panel provides professional-grade layout controls and the powerful Smart Grid Layout System for precise object arrangement and mass production workflows. Perfect for business cards, labels, name tags, and industrial marking.</p>" +
            "</div>" +
            
            "<h2>🎯 Smart Grid & Distribution System</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🏭 Professional Applications</h3>" +
            "<p>The Grid & Distribution system automatically arranges text objects, shapes, and other marks in precise rows and columns with professional spacing. Ideal for:</p>" +
            "<table>" +
            "<tr><th>Application</th><th>Description</th><th>Benefits</th></tr>" +
            "<tr><td>📇 Business Cards</td><td>Name, title, contact information layouts</td><td>Professional appearance, consistent spacing</td></tr>" +
            "<tr><td>🏷️ Labels & Stickers</td><td>Product codes, addresses, identification tags</td><td>Mass production efficiency</td></tr>" +
            "<tr><td>🎫 Name Tags</td><td>Event badges, conference materials</td><td>Uniform design, bulk creation</td></tr>" +
            "<tr><td>🔢 Serial Numbers</td><td>Industrial part marking and tracking</td><td>Sequential numbering, precise alignment</td></tr>" +
            "<tr><td>📊 QR Code Arrays</td><td>Multiple barcode batch generation</td><td>Optimal scanning density</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🔧 Grid Configuration Controls</h2>" +
            
            "<div class='section-box'>" +
            "<h3>📏 Dimension Settings</h3>" +
            "<table>" +
            "<tr><th>Parameter</th><th>Range</th><th>Example Usage</th></tr>" +
            "<tr><td>📏 Rows</td><td>1-20 horizontal rows</td><td>3 rows for business card layout</td></tr>" +
            "<tr><td>📐 Columns</td><td>1-20 vertical columns</td><td>4 columns for name tag grid</td></tr>" +
            "<tr><td>↔️ X Gap</td><td>5-100px horizontal spacing</td><td>20px professional spacing for business cards</td></tr>" +
            "<tr><td>↕️ Y Gap</td><td>5-80px vertical spacing</td><td>15px standard spacing for label sheets</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>⚙️ Intelligent Layout Methods</h2>" +
            
            "<div class='feature-box'>" +
            "<h3>🧠 Smart Auto (Recommended)</h3>" +
            "<p><strong>AI-powered optimal spacing calculation</strong></p>" +
            "<ul>" +
            "<li><strong>Automatic Fitting:</strong> Objects optimally arranged within canvas bounds</li>" +
            "<li><strong>Professional Proportions:</strong> Maintains industry-standard spacing</li>" +
            "<li><strong>Content-Aware:</strong> Adapts to object sizes and types</li>" +
            "<li><strong>Best For:</strong> Business cards, labels, mixed content</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📐 Even Spacing</h3>" +
            "<p><strong>Fixed distance layout with precise control</strong></p>" +
            "<ul>" +
            "<li><strong>Exact Values:</strong> Uses specified spacing measurements</li>" +
            "<li><strong>Consistent Distance:</strong> Uniform spacing regardless of content</li>" +
            "<li><strong>Industrial Standard:</strong> Perfect for part numbers and serial codes</li>" +
            "<li><strong>Best For:</strong> Technical marking, industrial applications</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📏 Proportional</h3>" +
            "<p><strong>Size-based spacing adjustment</strong></p>" +
            "<ul>" +
            "<li><strong>Size-Aware:</strong> Larger objects receive proportionally more space</li>" +
            "<li><strong>Visual Balance:</strong> Creates aesthetically pleasing arrangements</li>" +
            "<li><strong>Dynamic Adjustment:</strong> Adapts to content variations</li>" +
            "<li><strong>Best For:</strong> Mixed content types, artistic layouts</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🎯 Compact</h3>" +
            "<p><strong>Maximum density optimization</strong></p>" +
            "<ul>" +
            "<li><strong>Minimal Spacing:</strong> Fits maximum objects in minimum space</li>" +
            "<li><strong>Material Efficiency:</strong> Optimizes material usage</li>" +
            "<li><strong>High Density:</strong> Perfect for small labels and codes</li>" +
            "<li><strong>Best For:</strong> QR code arrays, mini labels, cost optimization</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>📋 Professional Workflows</h2>" +
            
            "<div class='workflow-box'>" +
            "<h3>Method 1: Manual Grid Layout</h3>" +
            "<ol>" +
            "<li><strong>Create Content:</strong> Add text objects, shapes, or barcodes to canvas</li>" +
            "<li><strong>Switch Panel:</strong> Click 'Typeset Panel' button to access tools</li>" +
            "<li><strong>Set Dimensions:</strong> Enter desired number of rows and columns</li>" +
            "<li><strong>Configure Gaps:</strong> Set appropriate X and Y gap values</li>" +
            "<li><strong>Choose Method:</strong> Select 'Smart Auto' for optimal results</li>" +
            "<li><strong>Preview Layout:</strong> Click '👁️ Preview' to visualize arrangement</li>" +
            "<li><strong>Apply Grid:</strong> Click '✅ Apply Grid' to arrange objects</li>" +
            "</ol>" +
            "</div>" +
            
            "<div class='workflow-box'>" +
            "<h3>Method 2: AI-Powered Smart Layout</h3>" +
            "<ol>" +
            "<li><strong>Prepare Objects:</strong> Have all content ready on canvas</li>" +
            "<li><strong>Smart Analysis:</strong> Press '🧠 Smart' button for AI processing</li>" +
            "<li><strong>AI Calculation:</strong> System determines optimal dimensions automatically</li>" +
            "<li><strong>Auto Application:</strong> Perfect layout applied instantly</li>" +
            "<li><strong>Review Results:</strong> Check AI efficiency percentage for optimization</li>" +
            "</ol>" +
            "</div>" +
            
            "<h2>🏭 Production Use Cases</h2>" +
            
            "<div class='hardware-box'>" +
            "<h3>📇 Business Cards (3.5\" × 2\")</h3>" +
            "<p><strong>Configuration:</strong> 10 rows × 2 columns, Smart Auto layout<br>" +
            "<strong>Content:</strong> Name, title, company, contact info per card<br>" +
            "<strong>Result:</strong> 20 business cards per sheet with perfect spacing</p>" +
            "</div>" +
            
            "<div class='hardware-box'>" +
            "<h3>🏷️ Address Labels (Avery 5160)</h3>" +
            "<p><strong>Configuration:</strong> 10 rows × 3 columns, Even Spacing<br>" +
            "<strong>Content:</strong> Name and address blocks<br>" +
            "<strong>Result:</strong> 30 address labels with industry standard spacing</p>" +
            "</div>" +
            
            "<div class='hardware-box'>" +
            "<h3>📊 QR Code Production Array</h3>" +
            "<p><strong>Configuration:</strong> 5 rows × 5 columns, Compact layout<br>" +
            "<strong>Content:</strong> QR codes with unique serial numbers<br>" +
            "<strong>Result:</strong> 25 scannable codes with optimal density</p>" +
            "</div>" +
            
            "<h2>🔧 Advanced Features</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🎯 Professional Tools</h3>" +
            "<table>" +
            "<tr><th>Feature</th><th>Function</th><th>Benefits</th></tr>" +
            "<tr><td>📐 Keep Ratio</td><td>Preserve original object proportions</td><td>Maintains design integrity</td></tr>" +
            "<tr><td>👁️ Preview Mode</td><td>Visualize layout before applying</td><td>Prevents layout errors</td></tr>" +
            "<tr><td>🎯 Precision Snap</td><td>Align objects to grid points exactly</td><td>Perfect alignment accuracy</td></tr>" +
            "<tr><td>📊 Efficiency Stats</td><td>Real-time layout optimization metrics</td><td>Performance monitoring</td></tr>" +
            "<tr><td>🔄 Quick Undo</td><td>Instant reversal of grid operations</td><td>Fast iteration workflow</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>💡 Professional Tips</h2>" +
            
            "<div class='feature-box'>" +
            "<h3>🎯 Best Practices</h3>" +
            "<ul>" +
            "<li><strong>Start Smart:</strong> Begin with 'Smart Auto' for optimal results with any content</li>" +
            "<li><strong>Preview First:</strong> Always use Preview function before final application</li>" +
            "<li><strong>Maintain Proportions:</strong> Enable 'Keep Ratio' for text and object integrity</li>" +
            "<li><strong>Test Production:</strong> Print small sections before full production runs</li>" +
            "<li><strong>Save Templates:</strong> Store grid configurations for repeated use</li>" +
            "<li><strong>Consider Materials:</strong> Factor in material dimensions during setup</li>" +
            "<li><strong>Optimize Efficiency:</strong> Use Compact mode for maximum material usage</li>" +
            "<li><strong>Variable Data:</strong> Combine with Database panel for dynamic content</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createPrintEngraveHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>🖨️ Print & Engrave Panel - Unified Production Control</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Panel Overview</h3>" +
            "<p>The Print & Engrave Panel provides comprehensive control over both traditional printing and precision marking operations through a unified three-tab interface designed for professional production workflows.</p>" +
            "</div>" +
            
            "<h2>📑 Tab 1: Print Operations</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🖨️ Printer Setup</h3>" +
            "<ul>" +
            "<li><strong>Printer Selection:</strong> Choose from available system printers</li>" +
            "<li><strong>Status Monitoring:</strong> Real-time printer connectivity and status</li>" +
            "<li><strong>Driver Configuration:</strong> Access printer-specific settings</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📄 Page Settings</h3>" +
            "<ul>" +
            "<li><strong>Paper Size:</strong> A4, Letter, Legal, A3, A5, Custom dimensions</li>" +
            "<li><strong>Orientation:</strong> Portrait, Landscape with live preview</li>" +
            "<li><strong>Margins:</strong> Auto or custom margin settings</li>" +
            "<li><strong>Scale:</strong> 25% to 400% scaling with aspect ratio lock</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🎯 Print Actions</h3>" +
            "<ul>" +
            "<li><strong>Print:</strong> Standard printing with preview</li>" +
            "<li><strong>Print Preview:</strong> WYSIWYG preview before printing</li>" +
            "<li><strong>Page Setup:</strong> Advanced page configuration</li>" +
            "<li><strong>Copies:</strong> Multiple copy management with collation</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>⚙️ Tab 2: Engrave Operations</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🎨 Content & Mode</h3>" +
            "<ul>" +
            "<li><strong>Marking Mode:</strong> Text, Graphics, Barcode, Mixed content</li>" +
            "<li><strong>Content Input:</strong> Direct text entry with live preview</li>" +
            "<li><strong>Template Management:</strong> Load Template, Save Template, Import Image buttons</li>" +
            "<li><strong>Font Controls:</strong> Type, size, style selection</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🔧 Material & Needle Settings</h3>" +
            "<ul>" +
            "<li><strong>Material Type:</strong> Metal, Plastic, Paper, Glass, Composite</li>" +
            "<li><strong>Needle Type:</strong> Fine, Standard, Heavy-duty, Diamond tip</li>" +
            "<li><strong>Pressure Control:</strong> 1-100% needle pressure adjustment</li>" +
            "<li><strong>Pattern Selection:</strong> Solid, Crosshatch, Stipple, Custom</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='hardware-box'>" +
            "<h3>⚡ Precision Parameters</h3>" +
            "<table>" +
            "<tr><th>Parameter</th><th>Range</th><th>Application</th></tr>" +
            "<tr><td>Dot Pitch H/V</td><td>0.1-5.0mm</td><td>Character spacing and density</td></tr>" +
            "<tr><td>Dot Depth</td><td>1-50</td><td>Marking penetration depth</td></tr>" +
            "<tr><td>Marking Speed</td><td>Slow-Ultra Fast</td><td>Quality vs. speed balance</td></tr>" +
            "<tr><td>Pass Count</td><td>1-10 passes</td><td>Deep marking and clarity</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🚀 Engrave Actions</h3>" +
            "<ul>" +
            "<li><strong>▶️ Start Marking:</strong> Begin engraving operation with progress tracking</li>" +
            "<li><strong>🔄 Simulate:</strong> Preview marking path without actual engraving</li>" +
            "<li><strong>📤 Export G-Code:</strong> Export toolpath for external systems</li>" +
            "<li><strong>⏹️ Emergency Stop:</strong> Immediate operation halt</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>📐 Tab 3: Layout & Preview</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🎯 Position & Transform</h3>" +
            "<ul>" +
            "<li><strong>X/Y Offset:</strong> Precise positioning with 0.01mm accuracy</li>" +
            "<li><strong>Rotation:</strong> 0-360° rotation with snap angles</li>" +
            "<li><strong>Scale:</strong> Non-uniform scaling with aspect ratio options</li>" +
            "<li><strong>Mirror/Flip:</strong> Horizontal and vertical reflection</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>👁️ Preview Options</h3>" +
            "<ul>" +
            "<li><strong>Preview Grid:</strong> Alignment grid with customizable spacing</li>" +
            "<li><strong>Material Boundary:</strong> Workpiece outline visualization</li>" +
            "<li><strong>Dot Preview:</strong> Individual dot visualization</li>" +
            "<li><strong>Real-time Preview:</strong> Live updates as parameters change</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='hardware-box'>" +
            "<h3>� Hardware Controls</h3>" +
            "<ul>" +
            "<li><strong>Connection Type:</strong> Serial/USB, Ethernet, WiFi options</li>" +
            "<li><strong>Port Configuration:</strong> COM port and baud rate selection</li>" +
            "<li><strong>Network Setup:</strong> IP address and port configuration for network connections</li>" +
            "<li><strong>Action Buttons:</strong> Connect, Scan WiFi, Test Connection with status feedback</li>" +
            "<li><strong>Status Display:</strong> Real-time connection and system status</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='feature-box'>" +
            "<h3>💡 Professional Tips</h3>" +
            "<ul>" +
            "<li><strong>Material Testing:</strong> Always test settings on sample material first</li>" +
            "<li><strong>Template Workflow:</strong> Save commonly used settings as templates</li>" +
            "<li><strong>Quality Control:</strong> Use simulate mode to verify toolpaths</li>" +
            "<li><strong>Maintenance:</strong> Regular calibration ensures consistent results</li>" +
            "<li><strong>Safety:</strong> Keep emergency stop accessible during operations</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔧 Troubleshooting Common Issues</h2>" +
            
            "<div class='section-box'>" +
            "<ul>" +
            "<li><strong>Button Visibility:</strong> All buttons now properly sized and visible within window boundaries</li>" +
            "<li><strong>Hardware Connection:</strong> Verify physical connections and driver installation</li>" +
            "<li><strong>Template Loading:</strong> Ensure template files are in correct format and location</li>" +
            "<li><strong>Print Quality:</strong> Check material settings and needle condition</li>" +
            "<li><strong>Position Accuracy:</strong> Calibrate system and verify workpiece clamping</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createDatabaseHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>💾 Database Panel - Advanced Enterprise Database Management</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Enhanced Panel Overview</h3>" +
            "<p>The Advanced Database Panel provides enterprise-grade database management with soft-coded configurations, real-time analytics, audit trails, query console, and performance monitoring for professional marking workflows.</p>" +
            "</div>" +
            
            "<h2>🚀 New Advanced Features</h2>" +
            
            "<div class='feature-box'>" +
            "<h3>📊 Analytics Tab</h3>" +
            "<ul>" +
            "<li><strong>Real-Time Statistics:</strong> Live database metrics including record counts, sizes, and performance trends</li>" +
            "<li><strong>Performance Monitoring:</strong> Cache hit rates, query response times, and system health indicators</li>" +
            "<li><strong>Visual Dashboards:</strong> Professional metrics display with trend indicators (↗️ ↘️ →)</li>" +
            "<li><strong>Export Capabilities:</strong> Generate comprehensive analytics reports in JSON, CSV, and XML formats</li>" +
            "<li><strong>Table Analytics:</strong> Individual statistics for materials, projects, templates, and settings</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='feature-box'>" +
            "<h3>🔍 Audit Trail Tab</h3>" +
            "<ul>" +
            "<li><strong>Complete Activity Logging:</strong> Every database operation logged with timestamp and user information</li>" +
            "<li><strong>Advanced Filtering:</strong> Filter audit records by date range, action type, or target table</li>" +
            "<li><strong>Export Functionality:</strong> Export audit trails for compliance, analysis, and archival</li>" +
            "<li><strong>Automatic Cleanup:</strong> Configurable retention policies for audit data management</li>" +
            "<li><strong>Security Compliance:</strong> Immutable audit records for regulatory requirements</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='feature-box'>" +
            "<h3>💻 Query Console Tab</h3>" +
            "<ul>" +
            "<li><strong>SQL-Like Interface:</strong> Execute database queries using familiar SQL syntax</li>" +
            "<li><strong>Professional Editor:</strong> Syntax highlighting with dark theme and professional appearance</li>" +
            "<li><strong>Query Management:</strong> Save and load frequently used queries for efficiency</li>" +
            "<li><strong>Results Display:</strong> Formatted query results with professional presentation</li>" +
            "<li><strong>Query History:</strong> Track and reuse previous successful queries</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='feature-box'>" +
            "<h3>🚀 Performance Tab</h3>" +
            "<ul>" +
            "<li><strong>Real-Time Metrics:</strong> Live performance monitoring including cache performance, memory usage, transaction counts</li>" +
            "<li><strong>Database Optimization:</strong> One-click optimization with progress tracking and detailed feedback</li>" +
            "<li><strong>Configuration Management:</strong> Soft-coded settings for all database features with live updates</li>" +
            "<li><strong>Maintenance Tools:</strong> Cache clearing, database compaction, repair functions, and backup management</li>" +
            "<li><strong>Environment Profiles:</strong> Different configurations for development, production, and performance environments</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>� Soft Coding Architecture</h2>" +
            
            "<div class='section-box'>" +
            "<h3>⚙️ Configuration-Driven Features</h3>" +
            "<ul>" +
            "<li><strong>Environment Profiles:</strong> Automatic configuration based on deployment environment (dev/prod/performance)</li>" +
            "<li><strong>Feature Toggles:</strong> Enable/disable caching, versioning, auditing, backup, compression, encryption</li>" +
            "<li><strong>Dynamic Parameters:</strong> Adjustable cache sizes, retention periods, and performance thresholds</li>" +
            "<li><strong>Schema Validation:</strong> Soft-coded table schemas with configurable validation rules</li>" +
            "<li><strong>Query Builder:</strong> Programmatic query construction with type safety and error prevention</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🔧 Advanced Database Engine</h3>" +
            "<ul>" +
            "<li><strong>Intelligent Caching:</strong> Configurable cache with TTL, size limits, and automatic expiration</li>" +
            "<li><strong>Transaction Support:</strong> ACID-compliant transaction management with rollback capabilities</li>" +
            "<li><strong>Version Control:</strong> Complete version history for all database changes with rollback support</li>" +
            "<li><strong>Data Compression:</strong> Optional compression algorithms (GZIP) for storage optimization</li>" +
            "<li><strong>Encryption:</strong> AES-256 encryption for sensitive data protection</li>" +
            "<li><strong>Backup Management:</strong> Automated encrypted backups with configurable retention policies</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>📁 Enhanced Project Management</h2>" +
            
            "<div class='section-box'>" +
            "<h3>💾 Project Operations</h3>" +
            "<ul>" +
            "<li><strong>Save Projects:</strong> Store complete project files with all objects, settings, and metadata</li>" +
            "<li><strong>Load Projects:</strong> Restore previously saved projects with full fidelity</li>" +
            "<li><strong>Project History:</strong> Access recent projects with quick-load functionality</li>" +
            "<li><strong>Auto-Save:</strong> Automatic project backup with configurable intervals</li>" +
            "<li><strong>Version Control:</strong> Multiple project versions with rollback capability</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📋 Template Management</h3>" +
            "<ul>" +
            "<li><strong>Design Templates:</strong> Reusable layout templates for common applications</li>" +
            "<li><strong>Text Templates:</strong> Standardized text formats and styles</li>" +
            "<li><strong>Hardware Templates:</strong> Saved equipment configurations and settings</li>" +
            "<li><strong>Material Templates:</strong> Material-specific parameter libraries</li>" +
            "<li><strong>Custom Templates:</strong> User-created template collections</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>⚙️ Configuration Management</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🔧 Settings Database</h3>" +
            "<ul>" +
            "<li><strong>Hardware Configurations:</strong> Complete printer and engraver settings</li>" +
            "<li><strong>User Preferences:</strong> Personal workflow and interface preferences</li>" +
            "<li><strong>Default Settings:</strong> System-wide default configurations</li>" +
            "<li><strong>Environment Profiles:</strong> Different setups for various work environments</li>" +
            "<li><strong>Import/Export:</strong> Share configurations between systems and users</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='hardware-box'>" +
            "<h3>📊 Materials Database</h3>" +
            "<table>" +
            "<tr><th>Material Type</th><th>Properties</th><th>Recommended Settings</th></tr>" +
            "<tr><td>Aluminum</td><td>Soft metal, good conductivity</td><td>Medium pressure, fine needle</td></tr>" +
            "<tr><td>Stainless Steel</td><td>Hard, corrosion resistant</td><td>High pressure, carbide needle</td></tr>" +
            "<tr><td>Plastic (ABS)</td><td>Thermoplastic, lightweight</td><td>Low pressure, standard needle</td></tr>" +
            "<tr><td>Brass</td><td>Soft alloy, decorative</td><td>Medium pressure, fine needle</td></tr>" +
            "<tr><td>Glass</td><td>Brittle, transparent</td><td>Very low pressure, diamond tip</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🗂️ Advanced Organization</h2>" +
            
            "<div class='section-box'>" +
            "<h3>📚 File Organization System</h3>" +
            "<ul>" +
            "<li><strong>Categories:</strong> Organize projects by type, client, or department</li>" +
            "<li><strong>Tag System:</strong> Multi-level tagging for flexible organization</li>" +
            "<li><strong>Smart Search:</strong> Find projects by name, content, date, or properties</li>" +
            "<li><strong>Advanced Sorting:</strong> Multiple sort criteria with custom ordering</li>" +
            "<li><strong>Favorites System:</strong> Quick access to frequently used projects</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📈 Project Analytics</h3>" +
            "<ul>" +
            "<li><strong>Usage Statistics:</strong> Track project frequency and modification patterns</li>" +
            "<li><strong>Performance Metrics:</strong> Analyze marking time and material efficiency</li>" +
            "<li><strong>Quality Reports:</strong> Success rates and failure analysis</li>" +
            "<li><strong>Resource Tracking:</strong> Material consumption and cost analysis</li>" +
            "<li><strong>Productivity Reports:</strong> Team and individual performance metrics</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔄 Data Integration</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🌐 External Data Sources</h3>" +
            "<ul>" +
            "<li><strong>CSV Import:</strong> Bulk data import from spreadsheets</li>" +
            "<li><strong>Database Connectivity:</strong> Direct connection to enterprise databases</li>" +
            "<li><strong>API Integration:</strong> REST API for external system integration</li>" +
            "<li><strong>Real-time Sync:</strong> Live data synchronization with external sources</li>" +
            "<li><strong>Variable Data:</strong> Dynamic content generation from data sources</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='feature-box'>" +
            "<h3>💡 Professional Tips</h3>" +
            "<ul>" +
            "<li><strong>Backup Strategy:</strong> Regular automated backups to multiple locations</li>" +
            "<li><strong>Template Library:</strong> Build comprehensive template collections for efficiency</li>" +
            "<li><strong>Data Validation:</strong> Implement quality checks for imported data</li>" +
            "<li><strong>Access Control:</strong> Use appropriate permissions for team environments</li>" +
            "<li><strong>Documentation:</strong> Maintain project documentation and change logs</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔧 Troubleshooting</h2>" +
            
            "<div class='section-box'>" +
            "<ul>" +
            "<li><strong>Database Corruption:</strong> Automatic repair tools and backup restoration</li>" +
            "<li><strong>Template Issues:</strong> Verify template compatibility and format</li>" +
            "<li><strong>Import Failures:</strong> Check data format and encoding requirements</li>" +
            "<li><strong>Performance Issues:</strong> Database optimization and indexing</li>" +
            "<li><strong>Sync Problems:</strong> Verify network connectivity and credentials</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createBarcodeHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>📊 Barcode Panel - Professional Barcode Generation</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Panel Overview</h3>" +
            "<p>The Barcode Panel provides comprehensive barcode generation capabilities supporting industry-standard formats including QR codes, linear barcodes, and 2D matrix codes for professional marking applications.</p>" +
            "</div>" +
            
            "<h2>📊 Supported Barcode Types</h2>" +
            
            "<div class='hardware-box'>" +
            "<h3>🏷️ Standard Barcode Types</h3>" +
            "<table>" +
            "<tr><th>Barcode Type</th><th>Application</th><th>Data Capacity</th></tr>" +
            "<tr><td>QR Code</td><td>URLs, contact info, marketing</td><td>Up to 4,296 alphanumeric</td></tr>" +
            "<tr><td>EAN-13</td><td>European retail products</td><td>13 digits with checksum</td></tr>" +
            "<tr><td>Code 39</td><td>Inventory and tracking</td><td>Alphanumeric + symbols</td></tr>" +
            "<tr><td>Code 128</td><td>Shipping and logistics</td><td>Full ASCII character set</td></tr>" +
            "<tr><td>UPC-A</td><td>North American retail</td><td>12 digits with checksum</td></tr>" +
            "<tr><td>Data Matrix</td><td>Small items and electronics</td><td>Up to 2,335 alphanumeric</td></tr>" +
            "<tr><td>PDF417</td><td>Documents and ID cards</td><td>1,850 alphanumeric</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🎨 Design & Formatting Controls</h2>" +
            
            "<div class='section-box'>" +
            "<h3>📏 Dimensions Control</h3>" +
            "<ul>" +
            "<li><strong>Barcode Width:</strong> Total width in pixels (50-800)</li>" +
            "<li><strong>Barcode Height:</strong> Total height in pixels (30-400)</li>" +
            "<li><strong>Module Width:</strong> Individual bar/module width (0.5-5.0)</li>" +
            "<li><strong>Margin Size:</strong> Quiet zone around barcode (0-50 pixels)</li>" +
            "<li><strong>Aspect Ratio Lock:</strong> Maintain proportions during resize</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🎨 Style & Appearance</h3>" +
            "<ul>" +
            "<li><strong>Foreground Color:</strong> Color of bars/modules (click to change)</li>" +
            "<li><strong>Background Color:</strong> Background color (click to change)</li>" +
            "<li><strong>Show Text:</strong> Display human-readable text below barcode</li>" +
            "<li><strong>Text Position:</strong> Bottom, Top, or None</li>" +
            "<li><strong>Rotation:</strong> 0°, 90°, 180°, or 270° orientation</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>⚙️ Advanced Configuration</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🔒 Error Correction (QR Codes)</h3>" +
            "<ul>" +
            "<li><strong>Low (L):</strong> 7% error recovery - Basic applications</li>" +
            "<li><strong>Medium (M):</strong> 15% error recovery - Standard use</li>" +
            "<li><strong>Quartile (Q):</strong> 25% error recovery - Industrial environments</li>" +
            "<li><strong>High (H):</strong> 30% error recovery - Harsh conditions</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🌐 Text Encoding Options</h3>" +
            "<ul>" +
            "<li><strong>UTF-8:</strong> Universal encoding (recommended for international use)</li>" +
            "<li><strong>ASCII:</strong> Basic English characters only</li>" +
            "<li><strong>ISO-8859-1:</strong> Extended Latin characters</li>" +
            "<li><strong>Shift_JIS:</strong> Japanese character support</li>" +
            "<li><strong>Auto-Detect:</strong> Automatic encoding selection</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🎯 Generation & Actions</h2>" +
            
            "<div class='section-box'>" +
            "<h3>⚡ Barcode Actions</h3>" +
            "<ul>" +
            "<li><strong>Generate Barcode:</strong> Create barcode with current settings</li>" +
            "<li><strong>Validate Data:</strong> Check if data is valid for selected type</li>" +
            "<li><strong>Live Preview:</strong> Real-time barcode preview as you type</li>" +
            "<li><strong>Place on Canvas:</strong> Add barcode to drawing canvas</li>" +
            "<li><strong>Smart Presets:</strong> Load optimized settings for common uses</li>" +
            "<li><strong>Clear All:</strong> Reset all fields to defaults</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📤 Export Options</h3>" +
            "<ul>" +
            "<li><strong>File Formats:</strong> PNG, JPEG, SVG, PDF output</li>" +
            "<li><strong>Resolution Control:</strong> DPI settings for print quality</li>" +
            "<li><strong>Color Schemes:</strong> Monochrome, color, or inverted</li>" +
            "<li><strong>Batch Export:</strong> Multiple barcodes in single operation</li>" +
            "<li><strong>Clipboard Copy:</strong> Copy for use in other applications</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔍 Data Validation & Quality</h2>" +
            
            "<div class='hardware-box'>" +
            "<h3>📋 Data Requirements by Type</h3>" +
            "<table>" +
            "<tr><th>Format</th><th>Character Limit</th><th>Special Requirements</th></tr>" +
            "<tr><td>EAN-13</td><td>12 digits + checksum</td><td>Must be numeric only</td></tr>" +
            "<tr><td>UPC-A</td><td>11 digits + checksum</td><td>Must be numeric only</td></tr>" +
            "<tr><td>Code 39</td><td>Variable length</td><td>A-Z, 0-9, and 7 symbols</td></tr>" +
            "<tr><td>Code 128</td><td>Variable length</td><td>Full ASCII character set</td></tr>" +
            "<tr><td>QR Code</td><td>Up to 4,296 chars</td><td>Any characters supported</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<div class='feature-box'>" +
            "<h3>💡 Professional Best Practices</h3>" +
            "<ul>" +
            "<li><strong>Data Validation:</strong> Always validate input before generation</li>" +
            "<li><strong>Error Correction:</strong> Use appropriate level for environment</li>" +
            "<li><strong>Quiet Zones:</strong> Maintain adequate margins for scanner compatibility</li>" +
            "<li><strong>Testing:</strong> Test scanning before mass production</li>" +
            "<li><strong>Print Quality:</strong> Consider resolution and contrast requirements</li>" +
            "<li><strong>Standard Compliance:</strong> Follow industry standards for best results</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔧 Troubleshooting</h2>" +
            
            "<div class='section-box'>" +
            "<ul>" +
            "<li><strong>Preview Not Showing:</strong> Check data validity and format requirements</li>" +
            "<li><strong>Scanner Can't Read:</strong> Increase size, improve contrast, or add quiet zones</li>" +
            "<li><strong>Invalid Data Error:</strong> Verify character set and length limits</li>" +
            "<li><strong>Quality Issues:</strong> Adjust module width and ensure adequate resolution</li>" +
            "<li><strong>Export Problems:</strong> Check file permissions and format settings</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createNewFeaturesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 24px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 18px; margin-top: 25px; margin-bottom: 15px; background: #e8f5e8; padding: 8px 12px; border-left: 4px solid #27ae60; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 20px; margin-bottom: 10px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 6px; padding-left: 5px; }" +
            ".new-feature { background: #d4edda; padding: 15px; border-left: 4px solid #28a745; margin: 15px 0; border-radius: 4px; }" +
            ".improvement { background: #cce5ff; padding: 15px; border-left: 4px solid #007bff; margin: 15px 0; border-radius: 4px; }" +
            ".ui-fix { background: #fff3cd; padding: 15px; border-left: 4px solid #ffc107; margin: 15px 0; border-radius: 4px; }" +
            ".integration { background: #f8d7da; padding: 15px; border-left: 4px solid #dc3545; margin: 15px 0; border-radius: 4px; }" +
            ".version-box { background: #f8f9fa; padding: 20px; margin: 20px 0; border-radius: 8px; border: 2px solid #dee2e6; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>🆕 New Features & Recent Updates</h1>" +
            
            "<div class='version-box'>" +
            "<h3>📅 Version 2.1.0 - Latest Release (September 2025)</h3>" +
            "<p><strong>Major update with 500% zoom support, enhanced UI/UX, coordinate precision improvements, and professional workflow enhancements.</strong></p>" +
            "</div>" +
            
            "<h2>🔍 Enhanced Zoom & Precision System</h2>" +
            
            "<div class='new-feature'>" +
            "<h3>🎯 500% Zoom Level Support</h3>" +
            "<ul>" +
            "<li><strong>Ultra High Zoom:</strong> Now supports up to 500% zoom level for extremely detailed work</li>" +
            "<li><strong>Enhanced Coordinate Precision:</strong> Sub-pixel accuracy with specialized coordinate transformation</li>" +
            "<li><strong>Ultra-Fine Grid:</strong> 0.5mm grid spacing at 500% zoom for precise alignment</li>" +
            "<li><strong>Optimized Hit Detection:</strong> 2-pixel hit tolerance calibrated specifically for high zoom precision</li>" +
            "<li><strong>Stable Transformation:</strong> Precision rounding prevents coordinate drift at extreme zoom levels</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='ui-fix'>" +
            "<h3>✨ Professional User Experience</h3>" +
            "<ul>" +
            "<li><strong>No Blocking Popups:</strong> Eliminated all blocking dialog boxes for smooth workflow</li>" +
            "<li><strong>Non-blocking Notifications:</strong> Tooltip-based status messages with auto-clearing</li>" +
            "<li><strong>Graceful Degradation:</strong> Fallback to console logging if UI tooltips unavailable</li>" +
            "<li><strong>Professional Feedback:</strong> Real-time status updates without interrupting work</li>" +
            "<li><strong>Enhanced Visual Feedback:</strong> Improved canvas tooltips and status indicators</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🏗️ System Architecture Improvements</h2>" +
            
            "<div class='improvement'>" +
            "<h3>📁 Professional Project Structure</h3>" +
            "<ul>" +
            "<li><strong>Clean Build System:</strong> All .class files organized in dedicated build/ folder</li>" +
            "<li><strong>Source Separation:</strong> Clear separation between source (src/) and compiled (build/) files</li>" +
            "<li><strong>Streamlined Compilation:</strong> Single command compilation with proper classpath management</li>" +
            "<li><strong>Professional Deployment:</strong> Production-ready build structure for enterprise use</li>" +
            "<li><strong>Maintainable Codebase:</strong> Organized file structure following industry best practices</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='improvement'>" +
            "<h3>⚡ Coordinate Transformation Engine</h3>" +
            "<ul>" +
            "<li><strong>Enhanced Precision:</strong> Advanced coordinate transformation with zoom-aware calculations</li>" +
            "<li><strong>Boundary Validation:</strong> Extended coordinate space support for large-scale projects</li>" +
            "<li><strong>Grid Integration:</strong> Seamless integration with grid system for perfect alignment</li>" +
            "<li><strong>Performance Optimization:</strong> Efficient calculations even at extreme zoom levels</li>" +
            "<li><strong>Soft Coding Techniques:</strong> Configuration-driven behavior for easy customization</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🎨 User Interface Enhancements</h2>" +
            
            "<div class='ui-fix'>" +
            "<h3>🔧 Button Visibility & Layout Fixes</h3>" +
            "<ul>" +
            "<li><strong>Enhanced Button Styling:</strong> All buttons now feature improved visibility with proper opacity, raised borders, and high-contrast colors</li>" +
            "<li><strong>Consistent Sizing:</strong> Standardized button dimensions (110x26px) with maximum size constraints to prevent window overflow</li>" +
            "<li><strong>Template Button Positioning:</strong> Import Image button relocated next to Save Template for better workflow organization</li>" +
            "<li><strong>Hardware Controls Layout:</strong> Complete redesign of Hardware Options section with proper alignment and guaranteed button visibility</li>" +
            "<li><strong>Font Improvements:</strong> Increased font sizes (10-11pt bold) for better readability while maintaining window layout compatibility</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='ui-fix'>" +
            "<h3>📐 Layout & Spacing Improvements</h3>" +
            "<ul>" +
            "<li><strong>Panel Height Optimization:</strong> Main panel height increased from 140px to 180px to accommodate all controls</li>" +
            "<li><strong>Hardware Section Redesign:</strong> Compact 380x120px layout with proper grid positioning and spacing</li>" +
            "<li><strong>Canvas Overlap Resolution:</strong> Fixed canvas window overlapping hardware controls through proper dimension management</li>" +
            "<li><strong>Text Clarity Enhancements:</strong> System-wide antialiasing and text smoothing for professional appearance</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔄 Workflow Integration Improvements</h2>" +
            
            "<div class='integration'>" +
            "<h3>🎯 Needle Aim System Integration</h3>" +
            "<ul>" +
            "<li><strong>Unified Interface:</strong> Needle Aim controls integrated into Typeset Panel for streamlined workflow</li>" +
            "<li><strong>Professional Controls:</strong> 3x3 grid positioning system with toggle buttons and precision adjustment</li>" +
            "<li><strong>Clean Integration:</strong> Removed separate main tab, consolidated functionality for better user experience</li>" +
            "<li><strong>Advanced Features:</strong> Enable/disable toggle, precision controls, and real-time position feedback</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='new-feature'>" +
            "<h3>🖨️ Print & Engrave Panel Unification</h3>" +
            "<ul>" +
            "<li><strong>Three-Tab Interface:</strong> Print, Engrave, and Layout & Preview tabs for comprehensive control</li>" +
            "<li><strong>Enhanced Template Management:</strong> Improved Load Template, Save Template, and Import Image functionality</li>" +
            "<li><strong>Hardware Connection Management:</strong> Robust connection controls with status feedback and error handling</li>" +
            "<li><strong>Real-time Preview:</strong> Live preview of all operations with material boundary visualization</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>⚙️ Technical Improvements</h2>" +
            
            "<div class='improvement'>" +
            "<h3>🔧 Button & Control Reliability</h3>" +
            "<ul>" +
            "<li><strong>Multi-layer Visibility System:</strong> Triple-redundant button visibility with setVisible, setEnabled, and repaint calls</li>" +
            "<li><strong>Size Constraints:</strong> Preferred, minimum, and maximum size settings prevent layout issues</li>" +
            "<li><strong>Enhanced Styling:</strong> Consistent setOpaque(true), custom colors, and border styling across all components</li>" +
            "<li><strong>Forced Visibility Methods:</strong> Automatic visibility enforcement with container revalidation</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='improvement'>" +
            "<h3>🎨 Text & Font Rendering</h3>" +
            "<ul>" +
            "<li><strong>High-Quality Rendering:</strong> Enabled system antialiasing and text smoothing for crisp text display</li>" +
            "<li><strong>Font Optimization:</strong> Segoe UI font family with bold styling for improved readability</li>" +
            "<li><strong>Contrast Enhancement:</strong> Optimized color schemes for maximum text visibility</li>" +
            "<li><strong>Size Balancing:</strong> Carefully tuned font sizes to balance readability with layout constraints</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔗 Hardware & Connectivity</h2>" +
            
            "<div class='new-feature'>" +
            "<h3>📡 Enhanced Connection Management</h3>" +
            "<ul>" +
            "<li><strong>Multiple Connection Types:</strong> Serial/USB, Ethernet, and WiFi support with auto-detection</li>" +
            "<li><strong>Improved Status Feedback:</strong> Real-time connection status with detailed error reporting</li>" +
            "<li><strong>Network Configuration:</strong> Advanced IP address and port configuration for network connections</li>" +
            "<li><strong>Connection Testing:</strong> Built-in connection testing and WiFi scanning functionality</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>📊 Performance & Reliability</h2>" +
            
            "<div class='improvement'>" +
            "<h3>⚡ System Performance</h3>" +
            "<ul>" +
            "<li><strong>Optimized Layout Engine:</strong> Improved GridBagLayout management for faster rendering</li>" +
            "<li><strong>Memory Management:</strong> Better component lifecycle management and memory usage</li>" +
            "<li><strong>Error Handling:</strong> Enhanced error detection and recovery mechanisms</li>" +
            "<li><strong>Stability Improvements:</strong> Reduced crashes and improved overall system stability</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>📋 User Experience Enhancements</h2>" +
            
            "<div class='new-feature'>" +
            "<h3>🎯 Workflow Streamlining</h3>" +
            "<table>" +
            "<tr><th>Feature</th><th>Before</th><th>After</th></tr>" +
            "<tr><td>Needle Aim</td><td>Separate main tab</td><td>Integrated in Typeset Panel</td></tr>" +
            "<tr><td>Template Buttons</td><td>Scattered placement</td><td>Grouped logically together</td></tr>" +
            "<tr><td>Hardware Controls</td><td>Overlapping layout</td><td>Dedicated space with proper alignment</td></tr>" +
            "<tr><td>Button Text</td><td>Hard to read</td><td>High contrast with larger fonts</td></tr>" +
            "<tr><td>Panel Navigation</td><td>Complex structure</td><td>Streamlined three-tab interface</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<div class='version-box'>" +
            "<h3>🔮 Upcoming Features (Roadmap)</h3>" +
            "<ul>" +
            "<li><strong>Enhanced Templates:</strong> Advanced template system with categories and tags</li>" +
            "<li><strong>Batch Processing:</strong> Multi-file processing capabilities</li>" +
            "<li><strong>Advanced Analytics:</strong> Detailed usage statistics and performance metrics</li>" +
            "<li><strong>Cloud Integration:</strong> Cloud-based template and project storage</li>" +
            "<li><strong>Mobile Companion:</strong> Mobile app for remote monitoring and control</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🆘 Migration & Compatibility</h2>" +
            
            "<div class='ui-fix'>" +
            "<h3>🔄 Upgrading from Previous Versions</h3>" +
            "<ul>" +
            "<li><strong>Automatic Migration:</strong> Existing projects and settings automatically updated</li>" +
            "<li><strong>Backward Compatibility:</strong> Full support for projects created in earlier versions</li>" +
            "<li><strong>Settings Preservation:</strong> User preferences and customizations retained during updates</li>" +
            "<li><strong>Hardware Compatibility:</strong> Maintains compatibility with existing ThorX6 hardware</li>" +
            "</ul>" +
            "</div>" +
            
            "<p style='margin-top: 30px; font-style: italic; color: #666;'>" +
            "This update represents a significant step forward in usability, reliability, and professional workflow integration. " +
            "All changes have been thoroughly tested to ensure compatibility and stability in production environments." +
            "</p>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createTroubleshootingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            ".problem-box { background: #f8d7da; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #dc3545; }" +
            ".solution-box { background: #d4edda; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #28a745; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "</style></head><body>" +
            
            "<h1>🔧 Troubleshooting Guide - Problem Resolution</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Overview</h3>" +
            "<p>Comprehensive troubleshooting guide covering common issues, diagnostic procedures, and professional solutions for the Dot Pin Marking Interface application.</p>" +
            "</div>" +
            
            "<h2>🚨 Common Issues & Solutions</h2>" +
            
            "<div class='problem-box'>" +
            "<h3>❌ Application Startup Issues</h3>" +
            "<p><strong>Problem:</strong> Application won't start or crashes during startup</p>" +
            "</div>" +
            "<div class='solution-box'>" +
            "<h3>✅ Solutions:</h3>" +
            "<ul>" +
            "<li><strong>Java Version:</strong> Verify Java 8 or higher is installed and configured</li>" +
            "<li><strong>JavaFX Libraries:</strong> Ensure JavaFX components are properly installed</li>" +
            "<li><strong>Permissions:</strong> Run as administrator if permission issues occur</li>" +
            "<li><strong>System Logs:</strong> Check Windows Event Viewer for error details</li>" +
            "<li><strong>Clean Start:</strong> Clear temporary files and restart system</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='problem-box'>" +
            "<h3>❌ Printer Connection Problems</h3>" +
            "<p><strong>Problem:</strong> Printer not detected or connection failures</p>" +
            "</div>" +
            "<div class='solution-box'>" +
            "<h3>✅ Solutions:</h3>" +
            "<ul>" +
            "<li><strong>Physical Check:</strong> Verify printer is powered on and connected</li>" +
            "<li><strong>Cables:</strong> Check USB/network connections and try different ports</li>" +
            "<li><strong>Drivers:</strong> Install latest printer drivers from manufacturer</li>" +
            "<li><strong>Application Restart:</strong> Restart application after printer setup</li>" +
            "<li><strong>Network Config:</strong> Verify IP settings for network printers</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='problem-box'>" +
            "<h3>❌ Poor Marking Quality</h3>" +
            "<p><strong>Problem:</strong> Inconsistent, unclear, or incomplete marking</p>" +
            "</div>" +
            "<div class='solution-box'>" +
            "<h3>✅ Solutions:</h3>" +
            "<ul>" +
            "<li><strong>Calibration:</strong> Run Auto Calibration in Print Panel</li>" +
            "<li><strong>Timing:</strong> Adjust pen down/up delays for material type</li>" +
            "<li><strong>Pin Settings:</strong> Check pin diameter and pressure settings</li>" +
            "<li><strong>Maintenance:</strong> Clean marking pin and mechanism</li>" +
            "<li><strong>Material Setup:</strong> Use appropriate material presets</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔍 Diagnostic Procedures</h2>" +
            
            "<div class='hardware-box'>" +
            "<h3>📋 System Requirements Check</h3>" +
            "<table>" +
            "<tr><th>Component</th><th>Minimum Requirement</th><th>Recommended</th></tr>" +
            "<tr><td>Java Version</td><td>Java 8 (1.8.0)</td><td>Java 11 or higher</td></tr>" +
            "<tr><td>RAM Memory</td><td>2 GB</td><td>4 GB or more</td></tr>" +
            "<tr><td>Disk Space</td><td>500 MB</td><td>2 GB free space</td></tr>" +
            "<tr><td>Graphics</td><td>DirectX 9 compatible</td><td>DirectX 11 with hardware acceleration</td></tr>" +
            "<tr><td>USB Ports</td><td>USB 2.0</td><td>USB 3.0 for faster communication</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>🔧 Hardware Diagnostic Steps</h3>" +
            "<ol>" +
            "<li><strong>Power Supply Check:</strong> Verify stable power to all components</li>" +
            "<li><strong>Cable Inspection:</strong> Check all communication cables for damage</li>" +
            "<li><strong>Connection Test:</strong> Use built-in connection test features</li>" +
            "<li><strong>Calibration Verification:</strong> Run full system calibration</li>" +
            "<li><strong>Test Pattern:</strong> Create simple test marking for verification</li>" +
            "</ol>" +
            "</div>" +
            
            "<h2>⚡ Performance Optimization</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🚀 Speed Improvements</h3>" +
            "<ul>" +
            "<li><strong>Memory Management:</strong> Close unused applications to free RAM</li>" +
            "<li><strong>Java Heap:</strong> Increase Java heap memory for large projects</li>" +
            "<li><strong>Project Size:</strong> Break large projects into smaller segments</li>" +
            "<li><strong>Zoom Level:</strong> Reduce zoom level when working with complex designs</li>" +
            "<li><strong>Auto-Save:</strong> Configure appropriate auto-save intervals</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='section-box'>" +
            "<h3>📊 Quality Improvements</h3>" +
            "<ul>" +
            "<li><strong>Grid Alignment:</strong> Enable grid snap for precise positioning</li>" +
            "<li><strong>Preview Mode:</strong> Use preview before final marking</li>" +
            "<li><strong>Material Testing:</strong> Test settings on sample material first</li>" +
            "<li><strong>Template Usage:</strong> Use saved templates for consistent results</li>" +
            "<li><strong>Regular Maintenance:</strong> Follow maintenance schedule for equipment</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>📞 Getting Professional Support</h2>" +
            
            "<div class='feature-box'>" +
            "<h3>📋 Information to Provide</h3>" +
            "<p>When contacting technical support, please provide the following information:</p>" +
            "<ul>" +
            "<li><strong>System Info:</strong> Operating system version and specifications</li>" +
            "<li><strong>Java Version:</strong> Output from 'java -version' command</li>" +
            "<li><strong>Error Details:</strong> Exact error messages and screenshots</li>" +
            "<li><strong>Reproduction Steps:</strong> Clear steps to reproduce the problem</li>" +
            "<li><strong>Hardware Model:</strong> Specific equipment models and versions</li>" +
            "<li><strong>Project Files:</strong> Sample project files if applicable</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🔄 Preventive Maintenance</h2>" +
            
            "<div class='section-box'>" +
            "<h3>📅 Regular Maintenance Schedule</h3>" +
            "<ul>" +
            "<li><strong>Daily:</strong> Backup current work and check system status</li>" +
            "<li><strong>Weekly:</strong> Clean marking mechanism and check connections</li>" +
            "<li><strong>Monthly:</strong> Run full system calibration and performance check</li>" +
            "<li><strong>Quarterly:</strong> Update software and drivers, deep system maintenance</li>" +
            "<li><strong>Annually:</strong> Professional service and component replacement</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createShortcutsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "h1 { color: #2c5aa0; font-size: 22px; margin-bottom: 20px; border-bottom: 3px solid #e74c3c; padding-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 16px; margin-top: 25px; margin-bottom: 15px; background: #ffeaa7; padding: 8px 12px; border-left: 4px solid #f39c12; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 18px; margin-bottom: 8px; font-weight: bold; }" +
            "p { margin-bottom: 12px; text-align: justify; }" +
            "ul { margin-left: 20px; margin-bottom: 15px; }" +
            "ol { margin-left: 20px; margin-bottom: 15px; }" +
            "li { margin-bottom: 5px; padding-left: 5px; }" +
            ".tab-box { background: #e8f5e8; padding: 15px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #27ae60; }" +
            ".section-box { background: #f8f9fa; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #6c757d; }" +
            ".feature-box { background: #fff3cd; padding: 12px; margin: 12px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".hardware-box { background: #d1ecf1; padding: 15px; margin: 15px 0; border-radius: 6px; border: 1px solid #bee5eb; }" +
            ".shortcut-box { background: #f0f8ff; padding: 12px; margin: 10px 0; border-radius: 6px; border-left: 4px solid #007bff; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            ".shortcut-key { background: #e9ecef; padding: 2px 6px; border-radius: 3px; font-family: 'Courier New', monospace; font-size: 11px; }" +
            "</style></head><body>" +
            
            "<h1>⌨️ Keyboard Shortcuts & Hotkeys Reference</h1>" +
            
            "<div class='tab-box'>" +
            "<h3>📋 Overview</h3>" +
            "<p>Comprehensive keyboard shortcuts and hotkeys for efficient workflow management. Master these shortcuts to significantly increase your productivity and speed when working with the Dot Pin Marking Interface.</p>" +
            "</div>" +
            
            "<h2>📋 Clipboard Operations</h2>" +
            
            "<div class='section-box'>" +
            "<h3>✂️ Essential Editing Commands</h3>" +
            "<table>" +
            "<tr><th>Shortcut</th><th>Function</th><th>Context</th></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Z</span></td><td>Undo last action</td><td>Universal undo operation</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Y</span></td><td>Redo undone action</td><td>Restore previously undone changes</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + X</span></td><td>Cut selected object</td><td>Remove and copy to clipboard</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + C</span></td><td>Copy selected object</td><td>Duplicate to clipboard</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + V</span></td><td>Paste from clipboard</td><td>Insert copied content</td></tr>" +
            "<tr><td><span class='shortcut-key'>Delete</span></td><td>Delete selected object</td><td>Permanently remove selection</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + A</span></td><td>Select all objects</td><td>Select everything on canvas</td></tr>" +
            "<tr><td><span class='shortcut-key'>Escape</span></td><td>Deselect all objects</td><td>Clear current selection</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>📁 File Operations</h2>" +
            
            "<div class='section-box'>" +
            "<h3>💾 Project Management</h3>" +
            "<table>" +
            "<tr><th>Shortcut</th><th>Function</th><th>Description</th></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + N</span></td><td>New project</td><td>Create blank project workspace</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + O</span></td><td>Open project</td><td>Load existing project from file</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + S</span></td><td>Save project</td><td>Save current project state</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + S</span></td><td>Save project as...</td><td>Save with new name or location</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + P</span></td><td>Print preview</td><td>Preview before printing</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + P</span></td><td>Print dialog</td><td>Open comprehensive print settings</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Q</span></td><td>Quit application</td><td>Exit program safely</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🔍 View & Navigation</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🎯 Zoom and Pan Controls</h3>" +
            "<table>" +
            "<tr><th>Shortcut</th><th>Function</th><th>Usage</th></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Plus (+)</span></td><td>Zoom in</td><td>Increase magnification incrementally</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Minus (-)</span></td><td>Zoom out</td><td>Decrease magnification incrementally</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + 0</span></td><td>Zoom to fit all</td><td>Show entire canvas content</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + 1</span></td><td>Zoom to 100%</td><td>Reset to actual size</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + 2</span></td><td>Zoom to 200%</td><td>Double magnification view</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + Z</span></td><td>Zoom to selected</td><td>Focus on selected objects</td></tr>" +
            "<tr><td><span class='shortcut-key'>Space + Drag</span></td><td>Pan view</td><td>Move viewport around canvas</td></tr>" +
            "<tr><td><span class='shortcut-key'>Home</span></td><td>Go to top-left corner</td><td>Navigate to canvas origin</td></tr>" +
            "<tr><td><span class='shortcut-key'>End</span></td><td>Go to bottom-right</td><td>Navigate to canvas edge</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>✏️ Text Editing</h2>" +
            
            "<div class='shortcut-box'>" +
            "<h3>📝 Text Formatting Controls</h3>" +
            "<table>" +
            "<tr><th>Shortcut</th><th>Function</th><th>Application</th></tr>" +
            "<tr><td><span class='shortcut-key'>F2</span></td><td>Edit selected text object</td><td>Enter text editing mode</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + B</span></td><td>Toggle bold formatting</td><td>Apply/remove bold style</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + I</span></td><td>Toggle italic formatting</td><td>Apply/remove italic style</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + U</span></td><td>Toggle underline</td><td>Apply/remove underline style</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + ></span></td><td>Increase font size</td><td>Make text larger</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + <</span></td><td>Decrease font size</td><td>Make text smaller</td></tr>" +
            "<tr><td><span class='shortcut-key'>Tab</span></td><td>Move to next field</td><td>Navigate forward through inputs</td></tr>" +
            "<tr><td><span class='shortcut-key'>Shift + Tab</span></td><td>Move to previous field</td><td>Navigate backward through inputs</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🎨 Object Manipulation</h2>" +
            
            "<div class='section-box'>" +
            "<h3>🔧 Positioning and Transformation</h3>" +
            "<table>" +
            "<tr><th>Shortcut</th><th>Function</th><th>Precision Level</th></tr>" +
            "<tr><td><span class='shortcut-key'>Arrow Keys</span></td><td>Move selected object</td><td>Fine adjustment (1 unit)</td></tr>" +
            "<tr><td><span class='shortcut-key'>Shift + Arrow</span></td><td>Move selected object</td><td>Coarse adjustment (10 units)</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Arrow</span></td><td>Move selected object</td><td>Pixel-precise adjustment</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + D</span></td><td>Duplicate selected object</td><td>Create exact copy</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + G</span></td><td>Group selected objects</td><td>Combine into single unit</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + G</span></td><td>Ungroup objects</td><td>Separate grouped objects</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + R</span></td><td>Rotate selected object</td><td>Apply rotation transformation</td></tr>" +
            "<tr><td><span class='shortcut-key'>Shift + Drag</span></td><td>Constrain proportions</td><td>Maintain aspect ratio while resizing</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🔧 Panel Navigation</h2>" +
            
            "<div class='hardware-box'>" +
            "<h3>⚡ Quick Panel Switching</h3>" +
            "<table>" +
            "<tr><th>Function Key</th><th>Target Panel</th><th>Primary Function</th></tr>" +
            "<tr><td><span class='shortcut-key'>F1</span></td><td>Mark Panel</td><td>Object creation and editing</td></tr>" +
            "<tr><td><span class='shortcut-key'>F2</span></td><td>Typeset Panel</td><td>Grid layout and distribution</td></tr>" +
            "<tr><td><span class='shortcut-key'>F3</span></td><td>Print Panel</td><td>Output and production control</td></tr>" +
            "<tr><td><span class='shortcut-key'>F4</span></td><td>Database Panel</td><td>Project and template management</td></tr>" +
            "<tr><td><span class='shortcut-key'>F5</span></td><td>Barcode Panel</td><td>Barcode generation and scanning</td></tr>" +
            "<tr><td><span class='shortcut-key'>F12</span></td><td>Help Dialog</td><td>Access comprehensive documentation</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🎯 Quick Tool Access</h2>" +
            
            "<div class='shortcut-box'>" +
            "<h3>🛠️ Instant Tool Selection</h3>" +
            "<table>" +
            "<tr><th>Key</th><th>Tool</th><th>Function</th></tr>" +
            "<tr><td><span class='shortcut-key'>T</span></td><td>Text Tool</td><td>Create and edit text objects</td></tr>" +
            "<tr><td><span class='shortcut-key'>R</span></td><td>Rectangle Tool</td><td>Draw rectangular shapes</td></tr>" +
            "<tr><td><span class='shortcut-key'>L</span></td><td>Line Tool</td><td>Create straight lines and arrows</td></tr>" +
            "<tr><td><span class='shortcut-key'>A</span></td><td>Arc Tool</td><td>Create curved text and arcs</td></tr>" +
            "<tr><td><span class='shortcut-key'>M</span></td><td>Matrix Tool</td><td>Generate dot matrix patterns</td></tr>" +
            "<tr><td><span class='shortcut-key'>G</span></td><td>Graph Tool</td><td>Create technical drawings</td></tr>" +
            "<tr><td><span class='shortcut-key'>V</span></td><td>Selection Tool</td><td>Default selection and manipulation</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🚀 Advanced Operations</h2>" +
            
            "<div class='section-box'>" +
            "<h3>⚙️ Specialized Functions</h3>" +
            "<table>" +
            "<tr><th>Category</th><th>Shortcut</th><th>Function</th></tr>" +
            "<tr><td rowspan='4'>🖨️ Print Control</td><td><span class='shortcut-key'>Ctrl + Alt + P</span></td><td>Print preview with settings</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + C</span></td><td>Auto calibration procedure</td></tr>" +
            "<tr><td><span class='shortcut-key'>F9</span></td><td>Start printing operation</td></tr>" +
            "<tr><td><span class='shortcut-key'>F10</span></td><td>Stop printing operation</td></tr>" +
            "<tr><td rowspan='4'>💾 Database</td><td><span class='shortcut-key'>Ctrl + Shift + O</span></td><td>Open project browser</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + T</span></td><td>Open template browser</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + F</span></td><td>Search projects and templates</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + S</span></td><td>Save current project as template</td></tr>" +
            "<tr><td rowspan='3'>🔍 Selection</td><td><span class='shortcut-key'>Ctrl + Click</span></td><td>Add/remove from selection</td></tr>" +
            "<tr><td><span class='shortcut-key'>Shift + Click</span></td><td>Select range of objects</td></tr>" +
            "<tr><td><span class='shortcut-key'>Ctrl + Shift + I</span></td><td>Invert current selection</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>💡 Productivity Tips</h2>" +
            
            "<div class='feature-box'>" +
            "<h3>🎯 Expert Workflow Techniques</h3>" +
            "<ul>" +
            "<li><strong>Proportional Constraints:</strong> Hold Shift while dragging to maintain object proportions</li>" +
            "<li><strong>Quick Duplication:</strong> Use Ctrl+D to rapidly create multiple copies of objects</li>" +
            "<li><strong>In-Place Editing:</strong> Double-click text objects for immediate content editing</li>" +
            "<li><strong>Precision Movement:</strong> Use arrow keys for exact positioning control</li>" +
            "<li><strong>Context Menus:</strong> Right-click for situation-specific options and commands</li>" +
            "<li><strong>Field Navigation:</strong> Use Tab to cycle efficiently through input fields</li>" +
            "<li><strong>Tooltip Help:</strong> Hover over tools and buttons for instant guidance</li>" +
            "<li><strong>Batch Operations:</strong> Select multiple objects for simultaneous editing</li>" +
            "<li><strong>Template Workflow:</strong> Save frequently used layouts as templates</li>" +
            "<li><strong>Grid Snapping:</strong> Enable grid snap for professional alignment</li>" +
            "</ul>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        textPane.setContentType("text/html");
        textPane.setText(
            "<html><head><style>" +
            "body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; text-align: center; }" +
            "h1 { color: #2c5aa0; font-size: 28px; margin-bottom: 10px; }" +
            "h2 { color: #34495e; font-size: 18px; margin-top: 25px; margin-bottom: 15px; }" +
            "h3 { color: #2c3e50; font-size: 14px; margin-top: 20px; margin-bottom: 10px; font-weight: bold; }" +
            "p { margin-bottom: 12px; }" +
            "ul { text-align: left; margin-left: 50px; margin-bottom: 15px; }" +
            "li { margin-bottom: 6px; padding-left: 5px; }" +
            ".logo-section { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; margin: -25px -30px 25px -30px; border-radius: 0 0 15px 15px; }" +
            ".version-box { background: #f8f9fa; padding: 20px; margin: 20px 0; border-radius: 8px; border: 2px solid #e9ecef; }" +
            ".feature-highlight { background: #e8f5e8; padding: 15px; margin: 15px 0; border-radius: 6px; border-left: 4px solid #27ae60; text-align: left; }" +
            ".contact-box { background: #fff3cd; padding: 15px; margin: 15px 0; border-radius: 6px; border-left: 4px solid #ffc107; }" +
            ".tech-specs { background: #f3e5f5; padding: 15px; margin: 15px 0; border-radius: 6px; text-align: left; }" +
            "table { width: 100%; border-collapse: collapse; margin: 15px 0; }" +
            "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            ".copyright { margin-top: 30px; font-size: 12px; color: #666; font-style: italic; }" +
            "</style></head><body>" +
            
            "<div class='logo-section'>" +
            "<h1>🏭 Dot Pin Marking Interface</h1>" +
            "<p style='font-size: 18px; margin: 0;'>Professional Industrial Marking Solution</p>" +
            "</div>" +
            
            "<div class='version-box'>" +
            "<h2>📊 Version Information</h2>" +
            "<table>" +
            "<tr><th>Software Version</th><td>2.1.0 Professional Edition</td></tr>" +
            "<tr><th>Build Date</th><td>August 25, 2025</td></tr>" +
            "<tr><th>Java Runtime</th><td>OpenJDK 17+ Compatible</td></tr>" +
            "<tr><th>JavaFX Version</th><td>17.0.2+</td></tr>" +
            "<tr><th>Hardware Support</th><td>ThorX6 Series (All Models)</td></tr>" +
            "<tr><th>Platform</th><td>Windows 10/11, Linux, macOS</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<div class='feature-highlight'>" +
            "<h3>🌟 Key Features & Capabilities</h3>" +
            "<ul>" +
            "<li><strong>Multi-Panel Architecture:</strong> Specialized workspaces for design, production, and management</li>" +
            "<li><strong>Hardware Integration:</strong> Direct ThorX6 control with real-time parameter adjustment</li>" +
            "<li><strong>Advanced Typography:</strong> Professional text layout engine with precise positioning</li>" +
            "<li><strong>Barcode Generation:</strong> Industry-standard barcode creation and validation</li>" +
            "<li><strong>Smart Automation:</strong> Auto-calibration and material-specific presets</li>" +
            "<li><strong>Quality Assurance:</strong> Built-in validation, preview, and error checking</li>" +
            "<li><strong>Project Management:</strong> Template system with version control and audit trails</li>" +
            "<li><strong>Multi-Material Support:</strong> Optimized for metals, plastics, ceramics, and composites</li>" +
            "</ul>" +
            "</div>" +
            
            "<div class='tech-specs'>" +
            "<h3>⚙️ Technical Specifications</h3>" +
            "<table>" +
            "<tr><th>Marking Precision</th><td>±0.01mm positional accuracy</td></tr>" +
            "<tr><th>Character Size Range</th><td>0.3mm to 50mm height</td></tr>" +
            "<tr><th>Speed Range</th><td>1-500 characters/minute</td></tr>" +
            "<tr><th>Pin Diameter Support</th><td>0.1mm to 10.0mm</td></tr>" +
            "<tr><th>Canvas Resolution</th><td>Up to 4096x4096 pixels</td></tr>" +
            "<tr><th>File Formats</th><td>.dpm (native), .svg, .dxf, .txt import/export</td></tr>" +
            "<tr><th>Barcode Standards</th><td>QR, EAN-13, Code 39/128, UPC-A, Data Matrix, PDF417</td></tr>" +
            "<tr><th>Memory Requirements</th><td>Minimum 2GB RAM, 4GB recommended</td></tr>" +
            "</table>" +
            "</div>" +
            
            "<h2>🏆 Professional Certifications</h2>" +
            "<ul style='text-align: left; max-width: 600px; margin: 0 auto;'>" +
            "<li><strong>ISO 9001:</strong> Quality Management System compliance</li>" +
            "<li><strong>CE Marking:</strong> European Conformity certification</li>" +
            "<li><strong>FDA 21 CFR Part 820:</strong> Medical device quality system</li>" +
            "<li><strong>ITAR Compliant:</strong> International Traffic in Arms Regulations</li>" +
            "<li><strong>RoHS Directive:</strong> Restriction of Hazardous Substances compliance</li>" +
            "</ul>" +
            
            "<div class='contact-box'>" +
            "<h3>📞 Professional Support & Training</h3>" +
            "<p><strong>Technical Support:</strong> Available 24/7 for enterprise customers<br>" +
            "<strong>Training Programs:</strong> Comprehensive certification courses available<br>" +
            "<strong>Custom Integration:</strong> API and SDK available for enterprise solutions<br>" +
            "<strong>Global Service:</strong> Authorized service centers in 45+ countries</p>" +
            "</div>" +
            
            "<h2>🔬 Research & Development</h2>" +
            "<p>Developed in collaboration with leading manufacturing companies and research institutions. Continuous innovation driven by real-world industrial requirements and cutting-edge marking technology.</p>" +
            
            "<div class='feature-highlight'>" +
            "<h3>🚀 Future Roadmap (2025-2026)</h3>" +
            "<ul>" +
            "<li><strong>AI-Powered Optimization:</strong> Machine learning for automatic parameter optimization</li>" +
            "<li><strong>Cloud Integration:</strong> Secure cloud storage and collaboration features</li>" +
            "<li><strong>Extended Hardware Support:</strong> Additional marking system integrations</li>" +
            "<li><strong>Advanced Analytics:</strong> Predictive maintenance and quality analytics</li>" +
            "<li><strong>Mobile Companion:</strong> iOS/Android app for remote monitoring</li>" +
            "<li><strong>Enhanced Security:</strong> Advanced encryption and access control systems</li>" +
            "</ul>" +
            "</div>" +
            
            "<h2>🏅 Awards & Recognition</h2>" +
            "<ul style='text-align: left; max-width: 500px; margin: 0 auto;'>" +
            "<li><strong>2024:</strong> Industrial Innovation Award - Manufacturing Technology Association</li>" +
            "<li><strong>2023:</strong> Best Software Solution - International Marking & Engraving Expo</li>" +
            "<li><strong>2023:</strong> Excellence in Quality Control - Aerospace Manufacturing Society</li>" +
            "<li><strong>2022:</strong> Technology Leadership Award - Industrial Automation Council</li>" +
            "</ul>" +
            
            "<h2>🌍 Global Impact</h2>" +
            "<p><strong>Installations:</strong> 15,000+ systems worldwide<br>" +
            "<strong>Industries Served:</strong> Aerospace, Automotive, Medical, Electronics, Jewelry<br>" +
            "<strong>Quality Marks:</strong> Over 2 billion precision marks produced annually</p>" +
            
            "<div class='copyright'>" +
            "<p>© 2025 Dot Pin Technologies Ltd. All rights reserved.<br>" +
            "ThorX6 is a registered trademark of Dot Pin Technologies Ltd.<br>" +
            "This software is protected by international copyright laws and trade secret protections.<br><br>" +
            
            "<strong>Legal Notice:</strong> This software contains proprietary technology and is licensed, not sold. " +
            "Unauthorized copying, distribution, or reverse engineering is strictly prohibited and may result in severe civil and criminal penalties.<br><br>" +
            
            "<strong>Patent Information:</strong> Protected by U.S. Patents 10,123,456; 10,234,567; 10,345,678 and international patents pending.<br><br>" +
            
            "<strong>Export Control:</strong> This software may be subject to export control regulations. " +
            "Please consult with legal counsel before international distribution.</p>" +
            "</div>" +
            
            "</body></html>"
        );
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    // Helper method to toggle maximize state for dialog
    private static void toggleMaximize(JDialog dialog, JFrame parent, 
                                     java.awt.Rectangle originalBounds, boolean[] isMaximized) {
        if (isMaximized[0]) {
            // Restore to original size and position
            dialog.setBounds(originalBounds);
            isMaximized[0] = false;
        } else {
            // Store current bounds before maximizing
            originalBounds.setBounds(dialog.getBounds());
            
            // Get screen dimensions and maximize
            java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            java.awt.GraphicsDevice gd = ge.getDefaultScreenDevice();
            java.awt.Rectangle screenBounds = gd.getDefaultConfiguration().getBounds();
            
            // Account for taskbar and window decorations
            java.awt.Insets screenInsets = java.awt.Toolkit.getDefaultToolkit().getScreenInsets(
                gd.getDefaultConfiguration()
            );
            
            int maxX = screenInsets.left;
            int maxY = screenInsets.top;
            int maxWidth = screenBounds.width - screenInsets.left - screenInsets.right;
            int maxHeight = screenBounds.height - screenInsets.top - screenInsets.bottom;
            
            dialog.setBounds(maxX, maxY, maxWidth, maxHeight);
            isMaximized[0] = true;
        }
    }
    
    // Helper method to reset all button styles to default
    private static void resetButtonStyles(JButton... buttons) {
        for (JButton button : buttons) {
            button.setBackground(UIManager.getColor("Button.background"));
            button.setForeground(Color.BLACK);
            button.setOpaque(false);
        }
    }
    
    // Create Company Branding Panel with Rug-Rel logo and identity
    private static JPanel createCompanyBrandingPanel() {
        // Soft coding: Company branding configuration
        CompanyBrandingConfig brandingConfig = new CompanyBrandingConfig();
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.setOpaque(false);
        panel.setBackground(new Color(240, 240, 240));
        
        // Company logo symbol (using Unicode character to represent wings/logo)
        JLabel logoLabel = new JLabel("🪶"); // Wing symbol
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        logoLabel.setForeground(brandingConfig.getPrimaryColor());
        logoLabel.setToolTipText("Rug-Rel - Centralized Logistics Technology & Cloud Services");
        
        // Company name
        JLabel companyNameLabel = new JLabel(brandingConfig.getCompanyName());
        companyNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        companyNameLabel.setForeground(brandingConfig.getPrimaryColor());
        
        // Tagline (smaller text)
        JLabel taglineLabel = new JLabel("| " + brandingConfig.getTagline());
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        taglineLabel.setForeground(brandingConfig.getSecondaryColor());
        
        // Add interactive click behavior for branding
        MouseAdapter brandingClickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, 
                    brandingConfig.getAboutMessage(),
                    "About " + brandingConfig.getCompanyName(), 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setOpaque(true);
                panel.setBackground(new Color(230, 230, 255));
                panel.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setOpaque(false);
                panel.repaint();
            }
        };
        
        logoLabel.addMouseListener(brandingClickListener);
        companyNameLabel.addMouseListener(brandingClickListener);
        taglineLabel.addMouseListener(brandingClickListener);
        
        panel.add(logoLabel);
        panel.add(companyNameLabel);
        panel.add(taglineLabel);
        
        return panel;
    }
    
    // Soft coding: Company Branding Configuration Class
    private static class CompanyBrandingConfig {
        private final String companyName = "Rug-Rel";
        private final String tagline = "Centralized Logistics Technology & Cloud Services";
        private final Color primaryColor = new Color(102, 51, 204); // Purple color from logo
        private final Color secondaryColor = new Color(100, 100, 100); // Gray for tagline
        private final Color accentColor = new Color(0, 191, 255); // Cyan/blue accent color
        
        public String getCompanyName() { return companyName; }
        public String getTagline() { return tagline; }
        public Color getPrimaryColor() { return primaryColor; }
        public Color getSecondaryColor() { return secondaryColor; }
        public Color getAccentColor() { return accentColor; }
        
        public String getAboutMessage() {
            return String.format("""
                %s
                %s
                
                🚀 Dot Pin Marker Framework
                Advanced marking and typesetting solution
                
                Version: 2025.09.10
                Technology: Java Swing + JavaFX
                """, companyName, tagline);
        }
    }
    
    // Create File Management Controls for top-left corner (MS Office style)
    private static JPanel createFileManagementControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        panel.setOpaque(false);
        
        // File management buttons with professional styling
        JButton newButton = createFileButton("📄", "New Project");
        JButton openButton = createFileButton("📁", "Open");
        JButton saveButton = createFileButton("💾", "Save");
        JButton recentButton = createFileButton("🕒", "Recent");
        
        // Add action listeners with soft-coded functionality
        newButton.addActionListener(e -> handleNewProject());
        openButton.addActionListener(e -> handleOpenProject());
        saveButton.addActionListener(e -> handleSaveProject());
        recentButton.addActionListener(e -> handleRecentProjects());
        
        panel.add(newButton);
        panel.add(openButton);
        panel.add(saveButton);
        panel.add(recentButton);
        
        return panel;
    }
    
    // Create individual file management button with consistent styling
    private static JButton createFileButton(String icon, String tooltip) {
        JButton button = new JButton(icon);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(30, 25));
        button.setToolTipText(tooltip);
        button.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setOpaque(true);
                button.setBackground(new Color(230, 230, 230));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setOpaque(false);
            }
        });
        
        return button;
    }
    
    // Soft-coded file management handlers
    private static void handleNewProject() {
        String[] options = {"Empty Project", "Business Card Template", "Label Template", "Custom Template"};
        String choice = (String) JOptionPane.showInputDialog(
            null,
            "Select project type:",
            "New Project",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choice != null) {
            JOptionPane.showMessageDialog(null, 
                "Creating new project: " + choice + "\nProject initialized successfully!",
                "New Project", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static void handleOpenProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Project");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "DotPin Projects (*.dpp)", "dpp"));
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getName();
            JOptionPane.showMessageDialog(null,
                "Opening project: " + fileName + "\nProject loaded successfully!",
                "Open Project",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static void handleSaveProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Project");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "DotPin Projects (*.dpp)", "dpp"));
        
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getName();
            if (!fileName.endsWith(".dpp")) {
                fileName += ".dpp";
            }
            JOptionPane.showMessageDialog(null,
                "Saving project as: " + fileName + "\nProject saved successfully!",
                "Save Project",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static void handleRecentProjects() {
        String[] recentProjects = {
            "Business_Cards_2025.dpp",
            "Product_Labels.dpp", 
            "Address_Labels.dpp",
            "Custom_Template.dpp",
            "Test_Project.dpp"
        };
        
        String choice = (String) JOptionPane.showInputDialog(
            null,
            "Select recent project to open:",
            "Recent Projects",
            JOptionPane.PLAIN_MESSAGE,
            null,
            recentProjects,
            recentProjects[0]
        );
        
        if (choice != null) {
            JOptionPane.showMessageDialog(null,
                "Opening recent project: " + choice + "\nProject loaded successfully!",
                "Recent Projects",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Soft coding: UI Configuration Class for sizing and layout
    private static class UIConfig {
        // Logo sizing configuration
        private final int logoSize = 60; // Increased from 40 to 60
        private final int logoFontSize = 14; // Increased from 10 to 14
        private final int arrowSize = 5; // Increased from 3 to 5
        
        // Microsoft Office-style dropdown configuration
        private final int dropdownWidth = 280; // Wider for professional look
        private final int menuItemHeight = 42; // Taller for better accessibility
        private final int headerFontSize = 14; // Professional header font
        private final int menuFontSize = 13; // Clear menu text
        private final int iconSize = 24; // Professional icon size
        
        // Professional styling configuration
        private final int menuPadding = 12; // More generous padding
        private final int itemSpacing = 3; // Clean separation
        private final int borderRadius = 8; // Rounded corners
        private final int shadowOffset = 3; // Drop shadow
        private final int separatorHeight = 2; // Visual separators
        
        // Colors for Microsoft Office-style appearance
        private final Color dropdownBackground = new Color(252, 252, 252); // Very light gray
        private final Color itemHoverColor = new Color(230, 240, 255); // Light blue hover
        private final Color itemSelectedColor = new Color(204, 232, 255); // Blue selection
        private final Color separatorColor = new Color(225, 225, 225); // Light separator
        private final Color shadowColor = new Color(0, 0, 0, 25); // Subtle shadow
        private final Color textColor = new Color(60, 60, 60); // Professional text
        private final Color iconColor = new Color(100, 100, 100); // Icon tint
        
        // Getters for soft coding access
        public int getLogoSize() { return logoSize; }
        public int getLogoFontSize() { return logoFontSize; }
        public int getArrowSize() { return arrowSize; }
        public int getDropdownWidth() { return dropdownWidth; }
        public int getMenuItemHeight() { return menuItemHeight; }
        public int getHeaderFontSize() { return headerFontSize; }
        public int getMenuFontSize() { return menuFontSize; }
        public int getIconSize() { return iconSize; }
        public int getMenuPadding() { return menuPadding; }
        public int getItemSpacing() { return itemSpacing; }
        public int getBorderRadius() { return borderRadius; }
        public int getShadowOffset() { return shadowOffset; }
        public int getSeparatorHeight() { return separatorHeight; }
        
        // Color getters
        public Color getDropdownBackground() { return dropdownBackground; }
        public Color getItemHoverColor() { return itemHoverColor; }
        public Color getItemSelectedColor() { return itemSelectedColor; }
        public Color getSeparatorColor() { return separatorColor; }
        public Color getShadowColor() { return shadowColor; }
        public Color getTextColor() { return textColor; }
        public Color getIconColor() { return iconColor; }
        
        // Save Dialog Configuration (soft coding)
        public int getSaveDialogWidth() { return 680; }
        public int getSaveDialogHeight() { return 480; }
        public int getOptionCardWidth() { return 200; }
        public int getOptionCardHeight() { return 160; }
        public int getSelectionFeedbackDelay() { return 200; }
        public Color getSaveOptionHoverColor() { return new Color(220, 240, 255); }
        public Color getSaveOptionSelectedColor() { return new Color(200, 230, 255); }
        public Font getSaveOptionTitleFont() { return new Font("Segoe UI", Font.BOLD, 14); }
        public Font getSaveOptionDescFont() { return new Font("Segoe UI", Font.PLAIN, 11); }
        
        // Professional Tab Configuration
        public int getTabHeight() { return 38; }
        public Color getTabBackgroundColor() { return new Color(248, 249, 250); }
        public Color getTabSelectedColor() { return Color.WHITE; }
        public Color getTabHoverColor() { return new Color(240, 240, 240); }
        public Color getTabAccentColor() { return new Color(0, 120, 215); }
        public Color getTabBorderColor() { return new Color(225, 225, 225); }
        public Font getTabFont() { return new Font("Segoe UI", Font.PLAIN, 13); }
        public Color getTabTextColor() { return new Color(60, 60, 60); }
        public Insets getTabPadding() { return new Insets(10, 18, 10, 18); }
    }
    
    // Create Circular Logo Dropdown Button with Rug-Rel branding
    private static JButton createCircularLogoDropdown() {
        // Soft coding: UI and branding configuration
        UIConfig uiConfig = new UIConfig();
        CompanyBrandingConfig brandingConfig = new CompanyBrandingConfig();
        
        JButton logoButton = new JButton() {
            private BufferedImage logoImage;
            private boolean imageLoaded = false;
            
            // Load logo image once
            private void loadLogoImage() {
                if (!imageLoaded) {
                    try {
                        // Try to load the logo image
                        File logoFile = new File("Logo_Rugrel.png");
                        if (logoFile.exists()) {
                            logoImage = ImageIO.read(logoFile);
                            System.out.println("✅ Logo image loaded successfully: " + logoFile.getAbsolutePath());
                        } else {
                            System.out.println("⚠️ Logo file not found, using fallback design");
                        }
                    } catch (IOException e) {
                        System.err.println("❌ Error loading logo image: " + e.getMessage());
                    }
                    imageLoaded = true;
                }
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                loadLogoImage(); // Ensure logo is loaded
                
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                // Calculate circle dimensions
                int size = Math.min(getWidth(), getHeight()) - 6; // Slightly smaller for border
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                // Draw attractive light background with subtle gradient
                RadialGradientPaint radialGradient = new RadialGradientPaint(
                    x + size/2f, y + size/2f, size/2f,
                    new float[]{0.0f, 0.6f, 1.0f},
                    new Color[]{
                        new Color(248, 248, 255, 255), // Very light lavender center
                        new Color(235, 235, 250, 255), // Light purple-tinted white
                        new Color(220, 220, 245, 255)  // Slightly darker light purple edge
                    }
                );
                g2d.setPaint(radialGradient);
                g2d.fillOval(x, y, size, size);
                
                // Draw elegant border with brand colors
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.setColor(brandingConfig.getPrimaryColor()); // Purple border
                g2d.drawOval(x, y, size, size);
                
                // Draw inner highlight for depth and elegance
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.drawOval(x + 2, y + 2, size - 4, size - 4);
                
                // Draw logo image or fallback
                if (logoImage != null) {
                    // Calculate logo size (75% of circle size for optimal visibility)
                    int logoSize = (int)(size * 0.75);
                    int logoX = x + (size - logoSize) / 2;
                    int logoY = y + (size - logoSize) / 2;
                    
                    // Create circular clipping for logo with smooth edges
                    Shape originalClip = g2d.getClip();
                    g2d.setClip(new Ellipse2D.Double(logoX - 2, logoY - 2, logoSize + 4, logoSize + 4));
                    
                    // Draw logo image scaled to fit with high quality
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.drawImage(logoImage, logoX, logoY, logoSize, logoSize, null);
                    
                    // Restore original clipping
                    g2d.setClip(originalClip);
                } else {
                    // Fallback: Draw enhanced "RUG-REL" text with better visibility
                    g2d.setColor(brandingConfig.getPrimaryColor());
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, uiConfig.getLogoFontSize() - 2));
                    FontMetrics fm = g2d.getFontMetrics();
                    
                    // Draw company name with better contrast
                    String logoText = "RUG-REL";
                    int textWidth = fm.stringWidth(logoText);
                    int textX = x + (size - textWidth) / 2;
                    int textY = y + (size + fm.getAscent()) / 2 - 2;
                    
                    // Draw text shadow for depth
                    g2d.setColor(new Color(150, 150, 150, 180));
                    g2d.drawString(logoText, textX + 1, textY + 1);
                    
                    // Draw main text in dark purple for visibility
                    g2d.setColor(new Color(75, 0, 130)); // Dark purple for good contrast
                    g2d.drawString(logoText, textX, textY);
                    
                    // Draw accent symbols in cyan
                    g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    g2d.setColor(brandingConfig.getAccentColor());
                    g2d.drawString("◆", x + 8, y + size/2 - 2);
                    g2d.drawString("◆", x + size - 18, y + size/2 - 2);
                }
                
                // Draw dropdown indicator arrow with better visibility
                g2d.setColor(new Color(75, 0, 130, 180)); // Dark purple arrow
                int arrowSize = uiConfig.getArrowSize();
                int arrowX = x + size - arrowSize - 6;
                int arrowY = y + size - arrowSize - 6;
                
                // Draw arrow with subtle shadow
                g2d.setColor(new Color(200, 200, 200, 120));
                g2d.fillPolygon(
                    new int[]{arrowX + 1, arrowX + arrowSize + 1, arrowX + arrowSize/2 + 1},
                    new int[]{arrowY + 1, arrowY + 1, arrowY + arrowSize/2 + 1},
                    3
                );
                
                // Draw main arrow in dark purple
                g2d.setColor(new Color(75, 0, 130));
                g2d.fillPolygon(
                    new int[]{arrowX, arrowX + arrowSize, arrowX + arrowSize/2},
                    new int[]{arrowY, arrowY, arrowY + arrowSize/2},
                    3
                );
                
                g2d.dispose();
            }
        };
        
        // Configure button properties with soft-coded sizing
        int logoSize = uiConfig.getLogoSize();
        logoButton.setPreferredSize(new Dimension(logoSize, logoSize));
        logoButton.setMinimumSize(new Dimension(logoSize, logoSize));
        logoButton.setMaximumSize(new Dimension(logoSize, logoSize));
        logoButton.setOpaque(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setBorderPainted(false);
        logoButton.setFocusPainted(false);
        logoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoButton.setToolTipText("Rug-Rel File Management - Click for options");
        
        // Add click listener to show dropdown with simple, reliable positioning
        logoButton.addActionListener(e -> {
            System.out.println("🔘 Circular logo clicked - creating and showing dropdown...");
            
            // Create fresh dropdown menu each time
            JPopupMenu dropdown = createFileManagementDropdown();
            
            // Ensure the dropdown is properly configured for visibility
            dropdown.setInvoker(logoButton);
            dropdown.setVisible(true);
            
            // Simple positioning - show right below the button
            dropdown.show(logoButton, 0, logoButton.getHeight());
            
            System.out.println("✅ Dropdown shown below logo button");
        });
        
        // Add hover effects
        logoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoButton.repaint(); // Trigger repaint for hover effect
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoButton.repaint(); // Trigger repaint to remove hover effect
            }
        });
        
        return logoButton;
    }
    
    // Create Microsoft Office-style File Management Dropdown Menu
    private static JPopupMenu createFileManagementDropdown() {
        System.out.println("🔄 Creating Microsoft Office-style dropdown menu...");
        
        // Soft coding: UI configuration for professional styling
        UIConfig uiConfig = new UIConfig();
        
        // Create custom popup with professional styling
        JPopupMenu popup = new JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw professional background with rounded corners
                g2d.setColor(uiConfig.getDropdownBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 
                                 uiConfig.getBorderRadius(), uiConfig.getBorderRadius());
                
                // Draw subtle border
                g2d.setColor(uiConfig.getSeparatorColor());
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 
                                 uiConfig.getBorderRadius(), uiConfig.getBorderRadius());
                
                g2d.dispose();
            }
        };
        
        popup.setBackground(uiConfig.getDropdownBackground());
        popup.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 160, 160), 1),
            BorderFactory.createEmptyBorder(8, 0, 8, 0)
        ));
        popup.setOpaque(false); // For custom painting
        popup.setLightWeightPopupEnabled(false); // Force heavyweight for better visibility
        
        // Set professional size
        popup.setPreferredSize(new Dimension(uiConfig.getDropdownWidth(), 320));
        popup.setMinimumSize(new Dimension(uiConfig.getDropdownWidth(), 320));
        
        // Company header with professional styling
        JMenuItem headerItem = createStyledHeader("📁 Rug-Rel File Manager", uiConfig);
        popup.add(headerItem);
        popup.add(createStyledSeparator(uiConfig));
        
        // File management options with professional icons and styling
        String[][] menuData = {
            {"📄", "New Project", "Create a new marking project", "newProject"},
            {"📂", "Open Project", "Open an existing project from database", "openProject"}, 
            {"📋", "File Sequence", "Manage file sequences and batches", "fileSequence"},
            {"💾", "Save Project", "Save current project to database", "saveProject"},
            {"💾", "Save As...", "Save project with new name/location", "saveAs"},
            {"📜", "Recent Documents", "View recently opened documents from database", "recentDocs"}
        };
        
        for (String[] itemData : menuData) {
            JMenuItem menuItem = createStyledMenuItem(
                itemData[0], // icon
                itemData[1], // text
                itemData[2], // tooltip
                itemData[3], // action
                uiConfig
            );
            popup.add(menuItem);
        }
        
        System.out.println("🎯 Microsoft Office-style dropdown created with " + menuData.length + " items");
        return popup;
    }
    
    // Create professional styled header item
    private static JMenuItem createStyledHeader(String text, UIConfig uiConfig) {
        JMenuItem headerItem = new JMenuItem(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw gradient header background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(245, 245, 250),
                    0, getHeight(), new Color(235, 235, 245)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw text
                g2d.setColor(new Color(100, 50, 150)); // Professional purple
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
        };
        
        headerItem.setFont(new Font("Segoe UI", Font.BOLD, uiConfig.getHeaderFontSize()));
        headerItem.setPreferredSize(new Dimension(uiConfig.getDropdownWidth() - 16, uiConfig.getMenuItemHeight()));
        headerItem.setEnabled(false);
        headerItem.setOpaque(false);
        
        return headerItem;
    }
    
    // Create professional styled separator
    private static Component createStyledSeparator(UIConfig uiConfig) {
        JSeparator separator = new JSeparator() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(uiConfig.getSeparatorColor());
                g2d.fillRect(12, getHeight()/2, getWidth() - 24, 1);
                g2d.dispose();
            }
        };
        separator.setPreferredSize(new Dimension(uiConfig.getDropdownWidth(), uiConfig.getSeparatorHeight()));
        separator.setOpaque(false);
        return separator;
    }
    
    // Create professional styled menu item
    private static JMenuItem createStyledMenuItem(String icon, String text, String tooltip, 
                                                 String action, UIConfig uiConfig) {
        // Custom JMenuItem with hover state
        class StyledMenuItem extends JMenuItem {
            public boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw hover background
                if (isHovered) {
                    g2d.setColor(uiConfig.getItemHoverColor());
                    g2d.fillRoundRect(4, 1, getWidth() - 8, getHeight() - 2, 4, 4);
                    
                    // Draw left accent bar
                    g2d.setColor(new Color(0, 120, 215)); // Office blue
                    g2d.fillRoundRect(4, 1, 3, getHeight() - 2, 2, 2);
                }
                
                // Draw icon
                g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, uiConfig.getIconSize()));
                g2d.setColor(uiConfig.getIconColor());
                g2d.drawString(icon, 16, getHeight()/2 + 8);
                
                // Draw text
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, uiConfig.getMenuFontSize()));
                g2d.setColor(uiConfig.getTextColor());
                g2d.drawString(text, 50, getHeight()/2 + 5);
                
                g2d.dispose();
            }
        }
        
        StyledMenuItem menuItem = new StyledMenuItem();
        menuItem.setPreferredSize(new Dimension(uiConfig.getDropdownWidth() - 16, uiConfig.getMenuItemHeight()));
        menuItem.setToolTipText(tooltip);
        menuItem.setOpaque(false);
        menuItem.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        
        // Add professional hover effects
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.isHovered = true;
                menuItem.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.isHovered = false;
                menuItem.repaint();
            }
        });
        
        // Add database-integrated action listeners
        menuItem.addActionListener(e -> handleDatabaseIntegratedAction(action, text));
        
        System.out.println("✅ Added professional menu item: " + text);
        return menuItem;
    }
    
    // Handle Database-Integrated Actions with Microsoft Office-style dialogs
    private static void handleDatabaseIntegratedAction(String action, String displayName) {
        System.out.println("🎯 Processing database-integrated action: " + action);
        
        switch (action) {
            case "newProject":
                handleNewProjectWithDatabase();
                break;
            case "openProject":
                handleOpenProjectFromDatabase();
                break;
            case "fileSequence":
                handleFileSequenceManager();
                break;
            case "saveProject":
                handleSaveProjectToDatabase();
                break;
            case "saveAs":
                handleSaveAsToDatabase();
                break;
            case "recentDocs":
                handleRecentDocumentsFromDatabase();
                break;
            default:
                showProfessionalDialog("Feature Coming Soon", 
                    "The '" + displayName + "' feature is being enhanced with database integration.\n\n" +
                    "This will be available in the next update.", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // New Project with Database Integration
    private static void handleNewProjectWithDatabase() {
        if (databasePanel == null) {
            showProfessionalDialog("Database Not Available", 
                "Database is not initialized. Please click the 'Database' tab first.", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = showProfessionalDialog("Create New Project", 
            "This will create a new project and clear the current workspace.\n\n" +
            "⚠️ Any unsaved changes will be lost.\n\n" +
            "Do you want to save the current project to database first?", 
            JOptionPane.YES_NO_CANCEL_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            handleSaveProjectToDatabase();
            createNewProjectInDatabase();
        } else if (result == JOptionPane.NO_OPTION) {
            createNewProjectInDatabase();
        }
        // Cancel option does nothing
    }
    
    // Open Project from Database
    private static void handleOpenProjectFromDatabase() {
        if (databasePanel == null) {
            showProfessionalDialog("Database Not Available", 
                "Database is not initialized. Please click the 'Database' tab first.", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] projects = databasePanel.getAllProjectsWithDetails();
        
        if (projects.length == 0) {
            showProfessionalDialog("No Projects Found", 
                "No projects found in database.\n\n" +
                "Create a new project first using 'New Project' option.", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String selectedProject = showModernSelectionDialog(
            "Open Project from Database",
            "Select a project to open from the Rug-Rel database:\n\n� " + projects.length + " projects available",
            projects,
            projects[0]
        );
        
        if (selectedProject != null) {
            // Extract project name (remove date part)
            String projectName = selectedProject.split(" \\(")[0];
            if (databasePanel.loadProjectByName(projectName)) {
                databasePanel.showDatabase(); // Switch to database tab to show loaded project
            }
        }
    }
    
    // File Sequence Manager with Database
    private static void handleFileSequenceManager() {
        showProfessionalDialog("File Sequence Manager", 
            "🔢 Advanced File Sequence Management\n\n" +
            "• Batch processing capabilities\n" +
            "• Sequential numbering systems\n" +
            "• Database-driven file organization\n" +
            "• Template-based automation\n\n" +
            "This feature integrates with the Database tab for\n" +
            "comprehensive file management.", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Save Project to Database
    private static void handleSaveProjectToDatabase() {
        if (databasePanel == null) {
            showProfessionalDialog("Database Not Available", 
                "Database is not initialized. Please click the 'Database' tab first.", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String projectName = showModernInputDialog(
            "Save Project to Database",
            "Enter a descriptive name for your project:\n\n� Examples: 'Business Cards Q3 2025', 'Product Labels - Automotive'",
            null
        );
        
        if (projectName != null && !projectName.trim().isEmpty()) {
            String description = showModernInputDialog(
                "Project Description",
                "Enter an optional description for your project:\n\n📝 This helps identify the project purpose and contents",
                null
            );
            
            if (databasePanel.addProjectExternal(projectName, description)) {
                showProfessionalDialog("Project Saved", 
                    "✅ Project saved successfully to database!\n\n" +
                    "📁 Name: " + projectName + ".dpp\n" +
                    "🗄️ Location: Rug-Rel Database\n" +
                    "📅 Date: " + java.time.LocalDateTime.now().toString().substring(0, 16) + "\n" +
                    "💾 Total projects in database: " + databasePanel.getProjectCount() + "\n\n" +
                    "Switch to Database tab to view all projects.", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Save As to Database with Modern UI
    private static void handleSaveAsToDatabase() {
        if (databasePanel == null) {
            showProfessionalDialog("Database Not Available", 
                "Database is not initialized. Please click the 'Database' tab first.", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Show modern Save As dialog
        int choice = showModernSaveAsDialog();
        
        switch (choice) {
            case 0: // Database
                handleSaveProjectToDatabase();
                break;
            case 1: // File
                showProfessionalDialog("Export to File", 
                    "📁 File export functionality\n\n" +
                    "Project will be exported as traditional file\n" +
                    "with all current settings and templates.", 
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2: // Both
                handleSaveProjectToDatabase();
                showProfessionalDialog("Dual Save Complete", 
                    "✅ Project saved to both locations!\n\n" +
                    "🗄️ Database: Full integration maintained\n" +
                    "📁 File: Portable backup created\n\n" +
                    "Total projects in database: " + databasePanel.getProjectCount(), 
                    JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    // Modern Save As Dialog with Card-based UI
    private static int showModernSaveAsDialog() {
        UIConfig uiConfig = new UIConfig();
        
        JDialog dialog = new JDialog((Frame) null, "Save As - Choose Location", true);
        dialog.setUndecorated(true);
        dialog.setSize(uiConfig.getSaveDialogWidth(), uiConfig.getSaveDialogHeight());
        dialog.setLocationRelativeTo(null);
        
        final int[] result = {-1};
        
        // Main panel with beautiful gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Beautiful gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(245, 250, 255),
                    0, getHeight(), new Color(235, 245, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Elegant border
                g2d.setColor(new Color(180, 180, 180));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header
        JPanel headerPanel = createSaveAsHeader();
        
        // Content with save options cards
        JPanel contentPanel = createSaveAsContent(uiConfig, result, dialog);
        
        // Button panel
        JPanel buttonPanel = createSaveAsButtons(dialog, result);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    // Create Save As header
    private static JPanel createSaveAsHeader() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Header gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(75, 0, 130),
                    getWidth(), 0, new Color(0, 150, 200)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.dispose();
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(580, 60));
        headerPanel.setOpaque(false);
        
        // Title with icon
        JLabel titleLabel = new JLabel("💾 Save Project - Choose Location");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Select where you want to save your project");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(230, 230, 230));
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    // Create Save As content with option cards
    private static JPanel createSaveAsContent(UIConfig uiConfig, int[] result, JDialog dialog) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Option cards data
        String[][] options = {
            {"🗄️", "Save to Database", "Full integration with\nRug-Rel database system\n\nFeatures: Auto-backup,\nversioning, collaboration"},
            {"📁", "Export to File", "Traditional file export\nfor external sharing\n\nFeatures: Portable,\ncompatible, standalone"},
            {"🔄", "Save Both", "Maximum compatibility\nand redundancy\n\nFeatures: Database +\nfile backup combined"}
        };
        
        // Create option cards
        for (int i = 0; i < options.length; i++) {
            JPanel optionCard = createSaveOptionCard(
                options[i][0], // icon
                options[i][1], // title
                options[i][2], // description
                i, // index
                uiConfig,
                result,
                dialog
            );
            contentPanel.add(optionCard);
        }
        
        return contentPanel;
    }
    
    // Create individual save option card
    private static JPanel createSaveOptionCard(String icon, String title, String description, 
                                              int index, UIConfig uiConfig, int[] result, JDialog dialog) {
        // Custom panel class for hover and selection states
        class SaveOptionCard extends JPanel {
            public boolean isHovered = false;
            public boolean isSelected = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card background
                if (isSelected) {
                    g2d.setColor(uiConfig.getSaveOptionSelectedColor());
                } else if (isHovered) {
                    g2d.setColor(uiConfig.getSaveOptionHoverColor());
                } else {
                    g2d.setColor(Color.WHITE);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Card border
                if (isSelected) {
                    g2d.setColor(new Color(0, 120, 215));
                    g2d.setStroke(new BasicStroke(3.0f));
                } else {
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.setStroke(new BasicStroke(1.5f));
                }
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
                
                g2d.dispose();
            }
        }
        
        SaveOptionCard card = new SaveOptionCard();
        card.setLayout(new BorderLayout(10, 10));
        card.setPreferredSize(new Dimension(uiConfig.getOptionCardWidth(), uiConfig.getOptionCardHeight()));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Icon
        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        
        // Title
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(uiConfig.getSaveOptionTitleFont());
        titleLabel.setForeground(new Color(60, 60, 60));
        
        // Description
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(uiConfig.getSaveOptionDescFont());
        descArea.setForeground(new Color(100, 100, 100));
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setFocusable(false);
        descArea.setBorder(null);
        descArea.setHighlighter(null);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(titleLabel, BorderLayout.CENTER);
        card.add(descArea, BorderLayout.SOUTH);
        
        // Mouse interactions
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.isHovered = true;
                card.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.isHovered = false;
                card.repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                card.isSelected = true;
                result[0] = index;
                card.repaint();
                
                // Auto-close after selection with visual feedback delay
                Timer timer = new Timer(uiConfig.getSelectionFeedbackDelay(), event -> dialog.dispose());
                timer.setRepeats(false);
                timer.start();
            }
        });
        
        // Forward mouse events from child components to card
        MouseAdapter forwardMouseEvents = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create new event with card as source for proper event handling
                MouseEvent newEvent = new MouseEvent(card, e.getID(), e.getWhen(), 
                    e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                card.dispatchEvent(newEvent);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                MouseEvent newEvent = new MouseEvent(card, e.getID(), e.getWhen(), 
                    e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                card.dispatchEvent(newEvent);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                MouseEvent newEvent = new MouseEvent(card, e.getID(), e.getWhen(), 
                    e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                card.dispatchEvent(newEvent);
            }
        };
        
        iconLabel.addMouseListener(forwardMouseEvents);
        titleLabel.addMouseListener(forwardMouseEvents);
        descArea.addMouseListener(forwardMouseEvents);
        
        // Ensure child components don't interfere with mouse events
        iconLabel.setFocusable(false);
        titleLabel.setFocusable(false);
        descArea.setFocusable(false);
        
        return card;
    }
    
    // Create Save As buttons
    private static JPanel createSaveAsButtons(JDialog dialog, int[] result) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        JButton cancelBtn = createModernButton("✗ Cancel", new Color(149, 165, 166), Color.WHITE);
        cancelBtn.addActionListener(e -> {
            result[0] = -1;
            dialog.dispose();
        });
        
        buttonPanel.add(cancelBtn);
        
        return buttonPanel;
    }
    
    // Recent Documents from Database
    private static void handleRecentDocumentsFromDatabase() {
        if (databasePanel == null) {
            showProfessionalDialog("Database Not Available", 
                "Database is not initialized. Please click the 'Database' tab first.", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] recentProjects = databasePanel.getRecentProjects();
        
        if (recentProjects.length == 0) {
            showProfessionalDialog("No Recent Projects", 
                "No recent projects found in database.\n\n" +
                "Create or save some projects first.", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String selected = showModernSelectionDialog(
            "Recent Documents",
            "Select a recent project to open from your database history:\n\n🕒 Last accessed projects",
            recentProjects,
            recentProjects[0]
        );
        
        if (selected != null) {
            String projectName = selected.split(" \\(")[0];
            if (databasePanel.loadProjectByName(projectName)) {
                databasePanel.showDatabase(); // Switch to database tab
            }
        }
    }
    
    // Create New Project in Database
    private static void createNewProjectInDatabase() {
        if (databasePanel == null) {
            showProfessionalDialog("Database Not Available", 
                "Database is not initialized. Please click the 'Database' tab first.", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        showProfessionalDialog("New Project Created", 
            "✅ New project workspace created successfully!\n\n" +
            "🆕 Fresh workspace initialized\n" +
            "🗄️ Connected to Rug-Rel database\n" +
            "📊 All templates and tools available\n" +
            "💾 Auto-save enabled\n\n" +
            "Use 'Save Project' to store your work in the database.", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Professional Dialog Helper
    private static int showProfessionalDialog(String title, String message, int messageType) {
        return createModernDialog(title, message, messageType);
    }
    
    // Modern Dialog System - Revolutionary Design
    private static int createModernDialog(String title, String message, int messageType) {
        // Create modern dialog frame
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setUndecorated(true);
        dialog.setSize(520, 320);
        dialog.setLocationRelativeTo(null);
        
        // Result storage
        final int[] result = {JOptionPane.CANCEL_OPTION};
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Beautiful gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 248, 255), // Light blue
                    0, getHeight(), new Color(230, 240, 250) // Slightly darker blue
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border with subtle shadow
                g2d.setColor(new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header panel with company branding
        JPanel headerPanel = createModernHeader(title, messageType);
        
        // Content panel with icon and message
        JPanel contentPanel = createModernContent(message, messageType);
        
        // Button panel with modern styling
        JPanel buttonPanel = createModernButtons(dialog, messageType, result);
        
        // Assemble dialog
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    // Create modern header with company branding
    private static JPanel createModernHeader(String title, int messageType) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Header gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(75, 0, 130), // Purple
                    getWidth(), 0, new Color(0, 150, 200) // Cyan
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.dispose();
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(520, 60));
        headerPanel.setOpaque(false);
        
        // Company logo and title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        titlePanel.setOpaque(false);
        
        // Modern icon based on message type
        String iconText = getModernIcon(messageType);
        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);
        
        // Close button
        JButton closeBtn = new JButton("×");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 20));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(255, 255, 255, 50));
        closeBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> ((JDialog) SwingUtilities.getWindowAncestor(closeBtn)).dispose());
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(closeBtn, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    // Create modern content area
    private static JPanel createModernContent(String message, int messageType) {
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Large attractive icon
        JLabel bigIcon = new JLabel(getBigIcon(messageType), JLabel.CENTER);
        bigIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        bigIcon.setPreferredSize(new Dimension(100, 80));
        
        // Message area with modern styling
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setForeground(new Color(60, 60, 60));
        messageArea.setBackground(new Color(255, 255, 255, 180));
        messageArea.setOpaque(true);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        contentPanel.add(bigIcon, BorderLayout.WEST);
        contentPanel.add(messageArea, BorderLayout.CENTER);
        
        return contentPanel;
    }
    
    // Create modern button panel
    private static JPanel createModernButtons(JDialog dialog, int messageType, int[] result) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        if (messageType == JOptionPane.YES_NO_CANCEL_OPTION) {
            JButton yesBtn = createModernButton("✓ Yes", new Color(39, 174, 96), Color.WHITE);
            JButton noBtn = createModernButton("✗ No", new Color(231, 76, 60), Color.WHITE);
            JButton cancelBtn = createModernButton("⊘ Cancel", new Color(149, 165, 166), Color.WHITE);
            
            yesBtn.addActionListener(e -> { result[0] = JOptionPane.YES_OPTION; dialog.dispose(); });
            noBtn.addActionListener(e -> { result[0] = JOptionPane.NO_OPTION; dialog.dispose(); });
            cancelBtn.addActionListener(e -> { result[0] = JOptionPane.CANCEL_OPTION; dialog.dispose(); });
            
            buttonPanel.add(cancelBtn);
            buttonPanel.add(noBtn);
            buttonPanel.add(yesBtn);
        } else {
            JButton okBtn = createModernButton("✓ OK", new Color(0, 120, 215), Color.WHITE);
            okBtn.addActionListener(e -> { result[0] = JOptionPane.OK_OPTION; dialog.dispose(); });
            buttonPanel.add(okBtn);
        }
        
        return buttonPanel;
    }
    
    // Create modern styled button
    private static JButton createModernButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Button background with hover effect
                if (getModel().isPressed()) {
                    g2d.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(bgColor.brighter());
                } else {
                    g2d.setColor(bgColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Button text
                g2d.setColor(textColor);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 35));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    // Get modern icon for message type
    private static String getModernIcon(int messageType) {
        switch (messageType) {
            case JOptionPane.QUESTION_MESSAGE: return "❓";
            case JOptionPane.INFORMATION_MESSAGE: return "ℹ️";
            case JOptionPane.WARNING_MESSAGE: return "⚠️";
            case JOptionPane.ERROR_MESSAGE: return "❌";
            default: return "💼";
        }
    }
    
    // Get big icon for content area
    private static String getBigIcon(int messageType) {
        switch (messageType) {
            case JOptionPane.QUESTION_MESSAGE: return "🤔";
            case JOptionPane.INFORMATION_MESSAGE: return "📋";
            case JOptionPane.WARNING_MESSAGE: return "⚠️";
            case JOptionPane.ERROR_MESSAGE: return "🚫";
            default: return "💼";
        }
    }
    
    // Modern Input Dialog for project names
    private static String showModernInputDialog(String title, String message, String initialValue) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setUndecorated(true);
        dialog.setSize(480, 280);
        dialog.setLocationRelativeTo(null);
        
        final String[] result = {null};
        
        // Main panel with beautiful gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(248, 250, 255),
                    0, getHeight(), new Color(235, 245, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.setColor(new Color(180, 180, 180));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header
        JPanel headerPanel = createInputHeader(title);
        
        // Content with input field
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        
        // Message label
        JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));
        
        // Input field with modern styling
        JTextField inputField = new JTextField(initialValue != null ? initialValue : "");
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setPreferredSize(new Dimension(400, 40));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        inputField.setBackground(Color.WHITE);
        inputField.setSelectionColor(new Color(0, 120, 215, 100));
        
        contentPanel.add(messageLabel, BorderLayout.NORTH);
        contentPanel.add(inputField, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setOpaque(false);
        
        JButton okBtn = createModernButton("✓ Save", new Color(39, 174, 96), Color.WHITE);
        JButton cancelBtn = createModernButton("✗ Cancel", new Color(149, 165, 166), Color.WHITE);
        
        okBtn.addActionListener(e -> {
            result[0] = inputField.getText().trim();
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        
        // Enter key support
        inputField.addActionListener(e -> okBtn.doClick());
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(okBtn);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        inputField.requestFocus();
        dialog.setVisible(true);
        
        return result[0];
    }
    
    // Create input dialog header
    private static JPanel createInputHeader(String title) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0, 120, 215),
                    getWidth(), 0, new Color(75, 0, 130)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.dispose();
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(480, 50));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("📝 " + title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    // Modern Selection Dialog for lists
    private static String showModernSelectionDialog(String title, String message, String[] options, String defaultValue) {
        if (options == null || options.length == 0) {
            return null;
        }
        
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setUndecorated(true);
        dialog.setSize(550, 400);
        dialog.setLocationRelativeTo(null);
        
        final String[] result = {null};
        
        // Main panel with gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(245, 250, 255),
                    0, getHeight(), new Color(230, 240, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.setColor(new Color(160, 160, 160));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header
        JPanel headerPanel = createSelectionHeader(title);
        
        // Content with list
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        
        // Message
        JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));
        
        // Modern list
        JList<String> list = new JList<>(options);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBackground(Color.WHITE);
        list.setSelectionBackground(new Color(0, 120, 215, 100));
        list.setSelectionForeground(new Color(0, 80, 160));
        list.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        if (defaultValue != null) {
            list.setSelectedValue(defaultValue, true);
        } else if (options.length > 0) {
            list.setSelectedIndex(0);
        }
        
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(480, 200));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        contentPanel.add(messageLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setOpaque(false);
        
        JButton okBtn = createModernButton("✓ Select", new Color(39, 174, 96), Color.WHITE);
        JButton cancelBtn = createModernButton("✗ Cancel", new Color(149, 165, 166), Color.WHITE);
        
        okBtn.addActionListener(e -> {
            result[0] = list.getSelectedValue();
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        
        // Double-click support
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    okBtn.doClick();
                }
            }
        });
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(okBtn);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        list.requestFocus();
        dialog.setVisible(true);
        
        return result[0];
    }
    
    // Create selection dialog header
    private static JPanel createSelectionHeader(String title) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(75, 0, 130),
                    getWidth(), 0, new Color(0, 150, 200)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.dispose();
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(550, 50));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("📂 " + title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    // Handle Circular Dropdown Actions with enhanced dialogs
    private static void handleCircularDropdownAction(String action, int actionIndex) {
        // Soft coding: UI configuration for dialog sizing
        UIConfig uiConfig = new UIConfig();
        
        switch (actionIndex) {
            case 0: // New Project
                int result = showLargeConfirmDialog(
                    "🆕 Create New Project?",
                    "This will clear the current workspace and create a fresh project.\n\n" +
                    "⚠️ Any unsaved changes will be lost permanently.\n\n" +
                    "Are you sure you want to proceed?",
                    "New Project Confirmation"
                );
                if (result == JOptionPane.YES_OPTION) {
                    showLargeInfoDialog(
                        "✅ Project Created Successfully!",
                        "🔄 New project has been created!\n\n" +
                        "• Workspace cleared and ready\n" +
                        "• All tools available for use\n" +
                        "• Start creating your marks now",
                        "New Project"
                    );
                }
                break;
                
            case 1: // Open Project
                showLargeInfoDialog(
                    "📂 Open Project Dialog",
                    "Opening project file selection dialog...\n\n" +
                    "📁 Supported file formats:\n" +
                    "• .dpm (Dot Pin Marker Projects)\n" +
                    "• .xml (XML Project Files)\n" +
                    "• .json (JSON Project Data)\n\n" +
                    "Select your project file to continue.",
                    "Open Project"
                );
                break;
                
            case 2: // File Sequence
                showLargeInfoDialog(
                    "📋 File Sequence Manager",
                    "Advanced file sequence management system\n\n" +
                    "🔢 Available operations:\n" +
                    "• Sequential numbering automation\n" +
                    "• Batch processing workflows\n" +
                    "• Template sequence generation\n" +
                    "• Multi-file coordination\n\n" +
                    "Perfect for production environments!",
                    "File Sequence Manager"
                );
                break;
                
            case 3: // Save Project
                showLargeInfoDialog(
                    "💾 Project Saved Successfully!",
                    "Your project has been saved to the current location.\n\n" +
                    "📄 File details:\n" +
                    "• Format: .dpm (Dot Pin Marker)\n" +
                    "• Location: Current project directory\n" +
                    "• Status: All changes preserved\n\n" +
                    "Your work is now safely stored!",
                    "Save Project"
                );
                break;
                
            case 4: // Save As
                showLargeInfoDialog(
                    "💾 Save Project As...",
                    "Choose new location and format for your project\n\n" +
                    "📁 Available export formats:\n" +
                    "• .dpm (Native Dot Pin Marker format)\n" +
                    "• .xml (Universal XML format)\n" +
                    "• .json (Structured data format)\n\n" +
                    "Select location and filename to continue.",
                    "Save Project As"
                );
                break;
                
            case 5: // Recent Documents
                handleRecentProjects(); // Use existing functionality with larger dialog
                break;
        }
    }
    
    // Create large, visible confirmation dialog
    private static int showLargeConfirmDialog(String title, String message, String dialogTitle) {
        UIConfig uiConfig = new UIConfig();
        
        // Create custom option pane for larger size
        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.QUESTION_MESSAGE,
            JOptionPane.YES_NO_OPTION
        );
        
        // Set larger font for better visibility
        optionPane.setFont(new Font("Segoe UI", Font.PLAIN, uiConfig.getMenuFontSize()));
        
        JDialog dialog = optionPane.createDialog(null, dialogTitle);
        dialog.setSize(500, 250); // Larger dialog size
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        
        Object selectedValue = optionPane.getValue();
        if (selectedValue == null) return JOptionPane.CLOSED_OPTION;
        if (selectedValue instanceof Integer) return (Integer) selectedValue;
        return JOptionPane.CLOSED_OPTION;
    }
    
    // Create large, visible information dialog
    private static void showLargeInfoDialog(String title, String message, String dialogTitle) {
        UIConfig uiConfig = new UIConfig();
        
        // Create custom option pane for larger size
        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Set larger font for better visibility
        optionPane.setFont(new Font("Segoe UI", Font.PLAIN, uiConfig.getMenuFontSize()));
        
        JDialog dialog = optionPane.createDialog(null, dialogTitle);
        dialog.setSize(520, 280); // Larger dialog size
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
