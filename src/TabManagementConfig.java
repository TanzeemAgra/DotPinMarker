import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Soft-coded configuration for tab management with logo alignment
 * Professional tab system that maintains content-header synchronization
 */
public class TabManagementConfig {
    
    // ==================== SOFT-CODED TAB CONFIGURATION ====================
    
    // Tab Visual Configuration - Now using Professional UI System
    public static Color getTabSelectedBackground() { return ProfessionalUIConfig.getCurrentThemeColors().selected; }
    public static Color getTabUnselectedBackground() { return ProfessionalUIConfig.getCurrentThemeColors().surface; }
    public static Color getTabHoverBackground() { return ProfessionalUIConfig.getCurrentThemeColors().hover; }
    public static Color getTabTextColor() { return ProfessionalUIConfig.getCurrentThemeColors().text; }
    public static Color getTabBorderColor() { return ProfessionalUIConfig.getCurrentThemeColors().border; }
    public static Color getTabAccentColor() { return ProfessionalUIConfig.getCurrentThemeColors().primary; }
    
    // Tab Dimensions - Professional Spacing System
    public static final int TAB_HEIGHT = HorizontalTabAlignmentConfig.TOTAL_HEADER_HEIGHT;
    public static final int TAB_PADDING_HORIZONTAL = ProfessionalUIConfig.Spacing.TAB_PADDING_H;
    public static final int TAB_PADDING_VERTICAL = ProfessionalUIConfig.Spacing.TAB_PADDING_V;
    public static final int TAB_BORDER_THICKNESS = 1;
    public static final int TAB_ACCENT_LINE_THICKNESS = 3;
    
    // Tab Font Configuration - Professional Typography
    public static final Font TAB_FONT = ProfessionalUIConfig.Typography.TAB_FONT;
    public static final Font TAB_SELECTED_FONT = ProfessionalUIConfig.Typography.TAB_SELECTED_FONT;
    
    // Content Management
    private static Map<String, JPanel> tabContentMap = new HashMap<>();
    private static JPanel currentContentArea;
    private static String currentSelectedTab = "";
    
    // Tab Button References for Visual Updates
    private static Map<String, JButton> tabButtonMap = new HashMap<>();
    
    // ==================== TAB CONTENT MANAGEMENT ====================
    
    /**
     * Register a tab with its content panel
     */
    public static void registerTab(String tabName, JPanel content, JButton tabButton) {
        tabContentMap.put(tabName, content);
        
        // Only register button if it's not null
        if (tabButton != null) {
            tabButtonMap.put(tabName, tabButton);
            System.out.println("📋 Tab registered with button: " + tabName);
        } else {
            System.out.println("📋 Tab content registered: " + tabName + " (button will be set separately)");
        }
    }
    
    /**
     * Set the content area where tab contents will be displayed
     */
    public static void setContentArea(JPanel contentArea) {
        currentContentArea = contentArea;
        System.out.println("📋 Content area set for tab management");
    }
    
    /**
     * Switch to specified tab with professional animations and feedback
     */
    public static void switchToTab(String tabName) {
        if (!tabContentMap.containsKey(tabName)) {
            System.out.println("❌ Tab not found: " + tabName);
            ProfessionalUIConfig.showNotification(null, "Tab '" + tabName + "' not found", false);
            return;
        }
        
        // Don't switch if already selected
        if (tabName.equals(currentSelectedTab)) {
            System.out.println("📋 Tab already active: " + tabName);
            return;
        }
        
        String previousTab = currentSelectedTab;
        
        // Update content area with smooth transition
        if (currentContentArea != null) {
            // Fade out current content
            animateContentTransition(currentContentArea, tabContentMap.get(tabName));
        }
        
        // Update visual state of all tab buttons
        updateTabButtonStates(tabName);
        
        currentSelectedTab = tabName;
        
        // Professional feedback
        System.out.println("📋 Professional tab switch: " + previousTab + " → " + tabName);
        
        // Status update
        createTabSwitchStatus(tabName);
    }
    
    /**
     * Animate content transition between tabs
     */
    private static void animateContentTransition(JPanel contentArea, JPanel newContent) {
        // Simple fade transition (in real implementation would be more sophisticated)
        contentArea.removeAll();
        contentArea.add(newContent, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
        
        // Simulate fade animation feedback
        System.out.println("✨ Content transition animated");
    }
    
    /**
     * Create professional status indicator for tab switch
     */
    private static void createTabSwitchStatus(String tabName) {
        // In a real implementation, this would show a status indicator
        System.out.println("🎯 Active Module: " + tabName);
        
        // Show brief notification
        Timer statusTimer = new Timer(2000, e -> {
            System.out.println("� " + tabName + " module ready");
        });
        statusTimer.setRepeats(false);
        statusTimer.start();
    }
    
    /**
     * Update visual state of all tab buttons with professional animations
     */
    private static void updateTabButtonStates(String selectedTab) {
        for (Map.Entry<String, JButton> entry : tabButtonMap.entrySet()) {
            String tabName = entry.getKey();
            JButton button = entry.getValue();
            
            // Skip if button is null (safety check)
            if (button == null) {
                System.out.println("⚠️ Warning: Null button for tab: " + tabName);
                continue;
            }
            
            boolean isSelected = tabName.equals(selectedTab);
            
            // Animate appearance changes
            Color targetBg = isSelected ? getTabSelectedBackground() : getTabUnselectedBackground();
            animateTabColorChange(button, button.getBackground(), targetBg, 200);
            
            // Update font with smooth transition
            button.setFont(isSelected ? TAB_SELECTED_FONT : TAB_FONT);
            button.setForeground(getTabTextColor());
            
            // Professional border styling
            if (isSelected) {
                // Selected tab gets accent border and elevated appearance
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 1, 0, 1, getTabBorderColor()),
                        BorderFactory.createMatteBorder(0, 0, TAB_ACCENT_LINE_THICKNESS, 0, getTabAccentColor())
                    ),
                    BorderFactory.createEmptyBorder(TAB_PADDING_VERTICAL, TAB_PADDING_HORIZONTAL, 
                                                  TAB_PADDING_VERTICAL - TAB_ACCENT_LINE_THICKNESS, TAB_PADDING_HORIZONTAL)
                ));
                
                // Add subtle shadow effect (simulated with darker border)
                button.setBorder(BorderFactory.createCompoundBorder(
                    button.getBorder(),
                    BorderFactory.createMatteBorder(1, 0, 0, 0, getTabBorderColor().darker())
                ));
            } else {
                // Unselected tabs have standard border
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 1, 0, 1, getTabBorderColor()),
                    BorderFactory.createEmptyBorder(TAB_PADDING_VERTICAL, TAB_PADDING_HORIZONTAL, 
                                                  TAB_PADDING_VERTICAL, TAB_PADDING_HORIZONTAL)
                ));
            }
            
            // Update tooltip for current state
            String tooltip = isSelected ? 
                tabName + " tab (Currently Active)" : 
                "Switch to " + tabName + " tab (Click or press Enter)";
            ProfessionalUIConfig.addProfessionalTooltip(button, tooltip);
        }
        
        System.out.println("🎨 Tab states updated with professional styling for: " + selectedTab);
    }
    
    /**
     * Get current selected tab
     */
    public static String getCurrentSelectedTab() {
        return currentSelectedTab;
    }
    
    /**
     * Initialize with default tab selection
     */
    public static void initializeWithDefaultTab(String defaultTab) {
        if (tabContentMap.containsKey(defaultTab)) {
            switchToTab(defaultTab);
        }
    }
    
    // ==================== TAB BUTTON CREATION ====================
    
    /**
     * Create professional tab button with advanced features
     */
    public static JButton createProfessionalTabButton(String title) {
        JButton tabButton = new JButton(title);
        
        // Apply professional styling using theme system
        tabButton.setFont(TAB_FONT);
        tabButton.setBackground(getTabUnselectedBackground());
        tabButton.setForeground(getTabTextColor());
        tabButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, TAB_BORDER_THICKNESS, 0, TAB_BORDER_THICKNESS, getTabBorderColor()),
            BorderFactory.createEmptyBorder(TAB_PADDING_VERTICAL, TAB_PADDING_HORIZONTAL, 
                                          TAB_PADDING_VERTICAL, TAB_PADDING_HORIZONTAL)
        ));
        tabButton.setFocusPainted(false);
        tabButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add professional tooltip
        ProfessionalUIConfig.addProfessionalTooltip(tabButton, "Switch to " + title + " tab (Click or press Enter)");
        
        // Add keyboard accessibility
        ProfessionalUIConfig.addKeyboardSupport(tabButton, () -> switchToTab(title));
        
        // Professional animated hover effects
        addProfessionalTabHoverEffects(tabButton, title);
        
        // Tab selection action with feedback
        tabButton.addActionListener(e -> {
            switchToTab(title);
            ProfessionalUIConfig.showNotification(null, title + " tab activated", true);
        });
        
        return tabButton;
    }
    
    /**
     * Add professional animated hover effects to tab button
     */
    private static void addProfessionalTabHoverEffects(JButton tabButton, String title) {
        Color originalBg = getTabUnselectedBackground();
        Color hoverBg = getTabHoverBackground();
        
        tabButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!title.equals(currentSelectedTab)) {
                    // Smooth animation to hover state
                    animateTabColorChange(tabButton, originalBg, hoverBg, 150);
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!title.equals(currentSelectedTab)) {
                    // Smooth animation back to original
                    animateTabColorChange(tabButton, hoverBg, originalBg, 150);
                }
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Quick press feedback
                tabButton.setBackground(getTabAccentColor());
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (!title.equals(currentSelectedTab)) {
                    tabButton.setBackground(hoverBg);
                }
            }
        });
    }
    
    /**
     * Smooth color animation for tab transitions
     */
    private static void animateTabColorChange(JButton button, Color from, Color to, int duration) {
        Timer timer = new Timer(10, null);
        long startTime = System.currentTimeMillis();
        
        timer.addActionListener(e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = Math.min(1.0f, (float) elapsed / duration);
            
            // Smooth color interpolation
            int r = (int) (from.getRed() + (to.getRed() - from.getRed()) * progress);
            int g = (int) (from.getGreen() + (to.getGreen() - from.getGreen()) * progress);
            int b = (int) (from.getBlue() + (to.getBlue() - from.getBlue()) * progress);
            
            button.setBackground(new Color(r, g, b));
            
            if (progress >= 1.0f) {
                timer.stop();
            }
        });
        
        timer.start();
    }
    
    /**
     * Print debug information about registered tabs
     */
    public static void printTabDebugInfo() {
        System.out.println("📋 Tab Management Debug Info:");
        System.out.println("   Registered tabs: " + tabContentMap.keySet());
        System.out.println("   Current selected: " + currentSelectedTab);
        System.out.println("   Content area set: " + (currentContentArea != null));
    }
}