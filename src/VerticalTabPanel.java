import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Vertical Tab Panel - Professional vertical tab system for space optimization
 * Implements vertical tab layout with Rugrel logo integration using soft-coded configuration
 */
public class VerticalTabPanel extends JPanel {
    
    // ==================== SOFT-CODED COMPONENT CONFIGURATION ====================
    
    private final JPanel tabButtonPanel;
    private final JPanel contentPanel;
    private final JButton logoButton;
    private int selectedTabIndex = 0;
    private final java.util.List<TabButton> tabButtons;
    private final java.util.List<JComponent> tabContents;
    
    // Animation support (soft-coded)
    private Timer animationTimer;
    private boolean animationEnabled = VerticalLayoutConfiguration.ENABLE_TAB_ANIMATIONS;
    
    // ==================== CONSTRUCTOR ====================
    
    public VerticalTabPanel() {
        this.tabButtons = new java.util.ArrayList<>();
        this.tabContents = new java.util.ArrayList<>();
        
        // Initialize with vertical layout configuration
        initializeVerticalLayout();
        
        // Create logo button with vertical styling
        this.logoButton = createVerticalLogoButton();
        
        // Create tab button panel
        this.tabButtonPanel = createTabButtonPanel();
        
        // Create content panel
        this.contentPanel = createContentPanel();
        
        // Build complete layout
        buildVerticalTabLayout();
    }
    
    // ==================== VERTICAL LAYOUT INITIALIZATION ====================
    
    /**
     * Initialize vertical layout with soft-coded configuration
     */
    private void initializeVerticalLayout() {
        setLayout(new BorderLayout());
        setBackground(VerticalLayoutConfiguration.VERTICAL_TAB_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setPreferredSize(new Dimension(
            VerticalLayoutConfiguration.LEFT_PANEL_WIDTH,
            600 // Will be adjusted dynamically
        ));
    }
    
    /**
     * Create vertical logo button with soft-coded styling
     */
    private JButton createVerticalLogoButton() {
        JButton logo = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Calculate circle dimensions for vertical layout
                int size = VerticalLayoutConfiguration.LOGO_SIZE_VERTICAL - 6;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                // Professional gradient background
                RadialGradientPaint gradient = new RadialGradientPaint(
                    x + size/2f, y + size/2f, size/2f,
                    new float[]{0.0f, 0.8f, 1.0f},
                    new Color[]{
                        new Color(248, 248, 255),
                        new Color(235, 235, 250),
                        new Color(220, 220, 245)
                    }
                );
                g2d.setPaint(gradient);
                g2d.fillOval(x, y, size, size);
                
                // Professional border with accent color
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.setColor(new Color(75, 0, 130));
                g2d.drawOval(x, y, size, size);
                
                // Company name text (fallback if no logo image)
                g2d.setColor(new Color(75, 0, 130));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 9));
                FontMetrics fm = g2d.getFontMetrics();
                
                String logoText = "RUG-REL";
                int textWidth = fm.stringWidth(logoText);
                int textX = x + (size - textWidth) / 2;
                int textY = y + (size + fm.getAscent()) / 2;
                
                g2d.drawString(logoText, textX, textY);
                
                g2d.dispose();
            }
        };
        
        logo.setPreferredSize(new Dimension(
            VerticalLayoutConfiguration.LOGO_SIZE_VERTICAL,
            VerticalLayoutConfiguration.LOGO_SIZE_VERTICAL
        ));
        logo.setOpaque(false);
        logo.setContentAreaFilled(false);
        logo.setBorderPainted(false);
        logo.setFocusPainted(false);
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.setToolTipText("Rug-Rel File Management");
        
        return logo;
    }
    
    /**
     * Create tab button panel with vertical arrangement
     */
    private JPanel createTabButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(VerticalLayoutConfiguration.VERTICAL_TAB_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(
            VerticalLayoutConfiguration.TAB_MARGIN_TOP, 5, 
            VerticalLayoutConfiguration.TAB_MARGIN_BOTTOM, 5
        ));
        
        return panel;
    }
    
    /**
     * Create content panel for tab contents
     */
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new CardLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, VerticalLayoutConfiguration.VERTICAL_TAB_BORDER),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        
        return panel;
    }
    
    // ==================== LAYOUT CONSTRUCTION ====================
    
    /**
     * Build complete vertical tab layout
     */
    private void buildVerticalTabLayout() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(VerticalLayoutConfiguration.VERTICAL_TAB_BACKGROUND);
        leftPanel.setPreferredSize(new Dimension(
            VerticalLayoutConfiguration.VERTICAL_TAB_WIDTH + 20, 0
        ));
        
        // Add logo at top of left panel
        if (VerticalLayoutConfiguration.LOGO_ABOVE_TABS) {
            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            logoPanel.setBackground(VerticalLayoutConfiguration.VERTICAL_TAB_BACKGROUND);
            logoPanel.add(logoButton);
            leftPanel.add(logoPanel, BorderLayout.NORTH);
        }
        
        // Add tab buttons in center
        leftPanel.add(tabButtonPanel, BorderLayout.CENTER);
        
        // Add to main layout
        add(leftPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    // ==================== TAB BUTTON IMPLEMENTATION ====================
    
    /**
     * Custom vertical tab button with professional styling
     */
    private class TabButton extends JButton {
        private final int tabIndex;
        private final VerticalLayoutConfiguration.TabDefinition tabDef;
        private boolean isHovered = false;
        
        public TabButton(VerticalLayoutConfiguration.TabDefinition tabDef, int tabIndex) {
            super();
            this.tabDef = tabDef;
            this.tabIndex = tabIndex;
            
            initializeTabButton();
            setupTabInteractions();
        }
        
        private void initializeTabButton() {
            setPreferredSize(new Dimension(
                VerticalLayoutConfiguration.VERTICAL_TAB_WIDTH,
                VerticalLayoutConfiguration.VERTICAL_TAB_HEIGHT
            ));
            setMaximumSize(new Dimension(
                VerticalLayoutConfiguration.VERTICAL_TAB_WIDTH,
                VerticalLayoutConfiguration.VERTICAL_TAB_HEIGHT
            ));
            
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setToolTipText(tabDef.tooltip);
            
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }
        
        private void setupTabInteractions() {
            addActionListener(e -> selectTab(tabIndex));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    if (VerticalLayoutConfiguration.ENABLE_TAB_ANIMATIONS) {
                        startHoverAnimation(true);
                    } else {
                        repaint();
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    if (VerticalLayoutConfiguration.ENABLE_TAB_ANIMATIONS) {
                        startHoverAnimation(false);
                    } else {
                        repaint();
                    }
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            boolean isSelected = (tabIndex == selectedTabIndex);
            
            // Background
            if (isSelected) {
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 8, 8);
                
                // Selected accent line (left side)
                g2d.setColor(tabDef.accentColor);
                g2d.setStroke(new BasicStroke(4.0f));
                g2d.drawLine(2, 8, 2, getHeight() - 8);
            } else if (isHovered) {
                g2d.setColor(VerticalLayoutConfiguration.VERTICAL_TAB_HOVER);
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 8, 8);
            }
            
            // Border for selected tab
            if (isSelected) {
                g2d.setColor(VerticalLayoutConfiguration.VERTICAL_TAB_BORDER);
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 8, 8);
            }
            
            // Icon and text
            g2d.setColor(isSelected ? tabDef.accentColor : VerticalLayoutConfiguration.VERTICAL_TAB_TEXT);
            g2d.setFont(getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN));
            
            FontMetrics fm = g2d.getFontMetrics();
            
            // Draw icon
            String icon = tabDef.icon;
            int iconY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent() - 3;
            g2d.drawString(icon, 12, iconY);
            
            // Draw text
            String text = tabDef.title;
            int textX = 32;
            int textY = iconY;
            g2d.drawString(text, textX, textY);
            
            g2d.dispose();
        }
        
        private void startHoverAnimation(boolean entering) {
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
            
            // Simple repaint animation - can be enhanced with smooth transitions
            repaint();
        }
    }
    
    // ==================== PUBLIC TAB MANAGEMENT METHODS ====================
    
    /**
     * Add tab to vertical tab panel
     */
    public void addTab(VerticalLayoutConfiguration.TabDefinition tabDef, JComponent content) {
        TabButton tabButton = new TabButton(tabDef, tabButtons.size());
        tabButtons.add(tabButton);
        tabContents.add(content);
        
        // Add button to panel with spacing
        tabButtonPanel.add(tabButton);
        if (tabButtons.size() > 1) {
            tabButtonPanel.add(Box.createVerticalStrut(VerticalLayoutConfiguration.TAB_SPACING));
        }
        
        // Add content to card layout
        contentPanel.add(content, String.valueOf(tabButtons.size() - 1));
        
        // Select first tab by default
        if (tabButtons.size() == 1) {
            selectTab(0);
        }
        
        revalidate();
        repaint();
    }
    
    /**
     * Select tab by index
     */
    public void selectTab(int index) {
        if (index >= 0 && index < tabButtons.size()) {
            selectedTabIndex = index;
            
            // Update button states
            for (TabButton button : tabButtons) {
                button.repaint();
            }
            
            // Show corresponding content
            CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
            cardLayout.show(contentPanel, String.valueOf(index));
            
            System.out.println("📋 Vertical tab selected: " + 
                              VerticalLayoutConfiguration.VERTICAL_TABS[index].title);
        }
    }
    
    /**
     * Get selected tab index
     */
    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }
    
    /**
     * Get logo button for external configuration
     */
    public JButton getLogoButton() {
        return logoButton;
    }
}