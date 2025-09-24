/**
 * Clean Interface Configuration
 * Soft-coded approach to ensure Rugrel logo, tabs, and all options are visible
 */
public class CleanInterfaceConfig {
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // Layout Configuration
    public static final String LAYOUT_STYLE = "CLEAN_VISIBLE";
    public static final boolean ENABLE_LOGO = true;
    public static final boolean ENABLE_TABS = true;
    public static final boolean ENABLE_CONTENT = true;
    public static final boolean ENABLE_DEBUG_OUTPUT = true;
    
    // Visual Configuration
    public static final int LOGO_SIZE = 40;
    public static final int TAB_HEIGHT = 35;
    public static final int HEADER_HEIGHT = 50;
    public static final int MARGIN_SIZE = 10;
    
    // Colors (Soft Coded)
    public static final java.awt.Color BACKGROUND_COLOR = java.awt.Color.WHITE;
    public static final java.awt.Color HEADER_COLOR = new java.awt.Color(240, 240, 240);
    public static final java.awt.Color TAB_COLOR = new java.awt.Color(0, 120, 215);
    public static final java.awt.Color LOGO_COLOR = java.awt.Color.BLACK;
    
    // Feature Flags (Soft Coded)
    public static final boolean USE_PROFESSIONAL_STYLING = true;
    public static final boolean USE_ANIMATIONS = false; // Disabled for stability
    public static final boolean USE_COMPLEX_LAYOUT = false; // Use simple layout
    public static final boolean FORCE_VISIBILITY = true;
    
    /**
     * Print current configuration
     */
    public static void printCleanConfig() {
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("🧹 === CLEAN INTERFACE CONFIGURATION ===");
            System.out.println("   Layout Style: " + LAYOUT_STYLE);
            System.out.println("   Logo Enabled: " + ENABLE_LOGO);
            System.out.println("   Tabs Enabled: " + ENABLE_TABS);
            System.out.println("   Content Enabled: " + ENABLE_CONTENT);
            System.out.println("   Force Visibility: " + FORCE_VISIBILITY);
            System.out.println("   Professional Styling: " + USE_PROFESSIONAL_STYLING);
            System.out.println("   Complex Layout: " + USE_COMPLEX_LAYOUT);
            System.out.println("🧹 ==========================================");
        }
    }
    
    /**
     * Create simple, guaranteed visible header with logo and tabs
     */
    public static javax.swing.JPanel createCleanHeader(javax.swing.JTabbedPane tabbedPane) {
        printCleanConfig();
        
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new java.awt.Dimension(0, HEADER_HEIGHT));
        
        if (ENABLE_LOGO) {
            // Simple logo section
            javax.swing.JPanel logoPanel = createSimpleLogoPanel();
            headerPanel.add(logoPanel, java.awt.BorderLayout.WEST);
        }
        
        if (ENABLE_TABS) {
            // Simple tab section
            javax.swing.JPanel tabsPanel = createSimpleTabsPanel(tabbedPane);
            headerPanel.add(tabsPanel, java.awt.BorderLayout.CENTER);
        }
        
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("✅ Clean header created with logo and tabs");
        }
        
        return headerPanel;
    }
    
    /**
     * Create simple, visible logo panel
     */
    private static javax.swing.JPanel createSimpleLogoPanel() {
        javax.swing.JPanel logoPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, MARGIN_SIZE, MARGIN_SIZE));
        logoPanel.setBackground(HEADER_COLOR);
        
        // Create simple Rugrel logo button
        javax.swing.JButton logoButton = new javax.swing.JButton("⚙ RUGREL");
        logoButton.setPreferredSize(new java.awt.Dimension(80, LOGO_SIZE));
        logoButton.setBackground(LOGO_COLOR);
        logoButton.setForeground(java.awt.Color.WHITE);
        logoButton.setBorder(javax.swing.BorderFactory.createRaisedBevelBorder());
        logoButton.setFocusPainted(false);
        
        logoPanel.add(logoButton);
        
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("🎯 Simple Rugrel logo created and positioned");
        }
        
        return logoPanel;
    }
    
    /**
     * Create simple, visible tabs panel
     */
    private static javax.swing.JPanel createSimpleTabsPanel(javax.swing.JTabbedPane tabbedPane) {
        javax.swing.JPanel tabsPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, MARGIN_SIZE));
        tabsPanel.setBackground(HEADER_COLOR);
        
        // Create simple tab buttons
        String[] tabNames = {"Mark", "Typeset", "Print", "Database", "Barcode"};
        
        for (String tabName : tabNames) {
            javax.swing.JButton tabButton = new javax.swing.JButton(tabName);
            tabButton.setPreferredSize(new java.awt.Dimension(80, TAB_HEIGHT));
            tabButton.setBackground(TAB_COLOR);
            tabButton.setForeground(java.awt.Color.WHITE);
            tabButton.setBorder(javax.swing.BorderFactory.createRaisedBevelBorder());
            tabButton.setFocusPainted(false);
            
            // Add click action
            tabButton.addActionListener(e -> {
                System.out.println("📋 Tab clicked: " + tabName);
                TabManagementConfig.switchToTab(tabName);
            });
            
            tabsPanel.add(tabButton);
        }
        
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("📋 Simple tabs created: " + java.util.Arrays.toString(tabNames));
        }
        
        return tabsPanel;
    }
    
    /**
     * Force all components to be visible
     */
    public static void forceVisibility(javax.swing.JFrame frame) {
        if (FORCE_VISIBILITY) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.revalidate();
                frame.repaint();
                frame.setVisible(true);
                frame.toFront();
                
                if (ENABLE_DEBUG_OUTPUT) {
                    System.out.println("🔧 Forced frame visibility and brought to front");
                }
            });
        }
    }
}