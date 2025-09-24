import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Professional UI Configuration System - Advanced Soft Coding
 * Provides comprehensive styling, animations, and user experience enhancements
 */
public class ProfessionalUIConfig {
    
    // ==================== PROFESSIONAL THEME SYSTEM ====================
    
    public enum UITheme {
        THORX6_PROFESSIONAL("ThorX6 Professional"),
        MODERN_DARK("Modern Dark"),
        CLASSIC_LIGHT("Classic Light"),
        HIGH_CONTRAST("High Contrast");
        
        private final String displayName;
        UITheme(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }
    
    private static UITheme currentTheme = UITheme.THORX6_PROFESSIONAL;
    
    // ==================== COLOR SCHEMES BY THEME ====================
    
    public static class ThemeColors {
        public final Color primary;
        public final Color secondary;
        public final Color accent;
        public final Color background;
        public final Color surface;
        public final Color text;
        public final Color textSecondary;
        public final Color border;
        public final Color hover;
        public final Color selected;
        
        public ThemeColors(Color primary, Color secondary, Color accent, Color background, 
                          Color surface, Color text, Color textSecondary, Color border, 
                          Color hover, Color selected) {
            this.primary = primary;
            this.secondary = secondary;
            this.accent = accent;
            this.background = background;
            this.surface = surface;
            this.text = text;
            this.textSecondary = textSecondary;
            this.border = border;
            this.hover = hover;
            this.selected = selected;
        }
    }
    
    private static final Map<UITheme, ThemeColors> THEME_COLORS = new HashMap<>();
    static {
        // ThorX6 Professional Theme
        THEME_COLORS.put(UITheme.THORX6_PROFESSIONAL, new ThemeColors(
            new Color(0, 120, 215),      // primary - ThorX6 Blue
            new Color(40, 40, 40),       // secondary - Dark Gray
            new Color(255, 165, 0),      // accent - Orange
            new Color(245, 245, 245),    // background - Light Gray
            Color.WHITE,                 // surface - White
            new Color(33, 33, 33),       // text - Dark
            new Color(117, 117, 117),    // textSecondary - Medium Gray
            new Color(200, 200, 200),    // border - Light Border
            new Color(230, 240, 250),    // hover - Light Blue
            new Color(225, 245, 254)     // selected - Very Light Blue
        ));
        
        // Modern Dark Theme
        THEME_COLORS.put(UITheme.MODERN_DARK, new ThemeColors(
            new Color(100, 149, 237),    // primary - Cornflower Blue
            new Color(30, 30, 30),       // secondary - Very Dark Gray
            new Color(255, 107, 107),    // accent - Coral Red
            new Color(18, 18, 18),       // background - Almost Black
            new Color(37, 37, 37),       // surface - Dark Gray
            new Color(255, 255, 255),    // text - White
            new Color(158, 158, 158),    // textSecondary - Light Gray
            new Color(82, 82, 82),       // border - Medium Gray
            new Color(50, 50, 50),       // hover - Lighter Dark
            new Color(60, 80, 100)       // selected - Dark Blue
        ));
    }
    
    // ==================== ANIMATION CONFIGURATION ====================
    
    public static class AnimationConfig {
        public static final int FADE_DURATION = 200;
        public static final int SLIDE_DURATION = 150;
        public static final int BUTTON_PRESS_DURATION = 100;
        public static final int TAB_SWITCH_DURATION = 250;
        public static final float HOVER_SCALE = 1.02f;
    }
    
    // ==================== TYPOGRAPHY SYSTEM ====================
    
    public static class Typography {
        // Primary Fonts
        public static final Font HEADING_LARGE = new Font("Segoe UI", Font.BOLD, 24);
        public static final Font HEADING_MEDIUM = new Font("Segoe UI", Font.BOLD, 18);
        public static final Font HEADING_SMALL = new Font("Segoe UI", Font.BOLD, 14);
        
        // Body Fonts
        public static final Font BODY_LARGE = new Font("Segoe UI", Font.PLAIN, 14);
        public static final Font BODY_MEDIUM = new Font("Segoe UI", Font.PLAIN, 12);
        public static final Font BODY_SMALL = new Font("Segoe UI", Font.PLAIN, 10);
        
        // UI Element Fonts
        public static final Font TAB_FONT = new Font("Segoe UI", Font.PLAIN, 12);
        public static final Font TAB_SELECTED_FONT = new Font("Segoe UI", Font.BOLD, 12);
        public static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 12);
        public static final Font TOOLTIP_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    }
    
    // ==================== SPACING AND LAYOUT ====================
    
    public static class Spacing {
        public static final int XS = 4;
        public static final int SM = 8;
        public static final int MD = 16;
        public static final int LG = 24;
        public static final int XL = 32;
        public static final int XXL = 48;
        
        // Component Specific
        public static final int TAB_PADDING_H = 16;
        public static final int TAB_PADDING_V = 8;
        public static final int BUTTON_PADDING_H = 20;
        public static final int BUTTON_PADDING_V = 10;
    }
    
    // ==================== COMPONENT STYLING ====================
    
    /**
     * Create professional button with current theme
     */
    public static JButton createProfessionalButton(String text, String tooltip) {
        JButton button = new JButton(text);
        ThemeColors colors = getCurrentThemeColors();
        
        // Apply professional styling
        button.setFont(Typography.BUTTON_FONT);
        button.setBackground(colors.primary);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colors.border, 1),
            BorderFactory.createEmptyBorder(Spacing.BUTTON_PADDING_V, Spacing.BUTTON_PADDING_H, 
                                          Spacing.BUTTON_PADDING_V, Spacing.BUTTON_PADDING_H)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (tooltip != null) {
            button.setToolTipText(tooltip);
        }
        
        // Professional hover effects
        addButtonHoverEffects(button, colors);
        
        return button;
    }
    
    /**
     * Create professional panel with current theme
     */
    public static JPanel createProfessionalPanel() {
        JPanel panel = new JPanel();
        ThemeColors colors = getCurrentThemeColors();
        
        panel.setBackground(colors.surface);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colors.border, 1),
            BorderFactory.createEmptyBorder(Spacing.MD, Spacing.MD, Spacing.MD, Spacing.MD)
        ));
        
        return panel;
    }
    
    /**
     * Add professional hover effects to button
     */
    private static void addButtonHoverEffects(JButton button, ThemeColors colors) {
        Color originalBg = button.getBackground();
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Animate to hover color
                animateColorChange(button, originalBg, colors.hover, AnimationConfig.FADE_DURATION);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Animate back to original color
                animateColorChange(button, colors.hover, originalBg, AnimationConfig.FADE_DURATION);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                // Quick press animation
                button.setBackground(colors.selected);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                // Return to hover state
                button.setBackground(colors.hover);
            }
        });
    }
    
    /**
     * Smooth color animation between two colors
     */
    private static void animateColorChange(JComponent component, Color from, Color to, int duration) {
        Timer timer = new Timer(10, null);
        long startTime = System.currentTimeMillis();
        
        timer.addActionListener(e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = Math.min(1.0f, (float) elapsed / duration);
            
            // Interpolate between colors
            int r = (int) (from.getRed() + (to.getRed() - from.getRed()) * progress);
            int g = (int) (from.getGreen() + (to.getGreen() - from.getGreen()) * progress);
            int b = (int) (from.getBlue() + (to.getBlue() - from.getBlue()) * progress);
            
            component.setBackground(new Color(r, g, b));
            
            if (progress >= 1.0f) {
                timer.stop();
            }
        });
        
        timer.start();
    }
    
    // ==================== ACCESSIBILITY FEATURES ====================
    
    /**
     * Add keyboard navigation support
     */
    public static void addKeyboardSupport(JComponent component, Runnable onEnter) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    onEnter.run();
                }
            }
        });
        
        component.setFocusable(true);
    }
    
    /**
     * Add professional tooltip with styling
     */
    public static void addProfessionalTooltip(JComponent component, String text) {
        component.setToolTipText(text);
        
        // Override tooltip appearance
        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(5000);
    }
    
    // ==================== THEME MANAGEMENT ====================
    
    /**
     * Get current theme colors
     */
    public static ThemeColors getCurrentThemeColors() {
        return THEME_COLORS.get(currentTheme);
    }
    
    /**
     * Change application theme
     */
    public static void setTheme(UITheme theme) {
        currentTheme = theme;
        System.out.println("🎨 Theme changed to: " + theme.getDisplayName());
        // Would trigger UI refresh in real implementation
    }
    
    /**
     * Get available themes
     */
    public static UITheme[] getAvailableThemes() {
        return UITheme.values();
    }
    
    // ==================== STATUS AND FEEDBACK SYSTEM ====================
    
    /**
     * Create professional status indicator
     */
    public static JLabel createStatusIndicator(String status, boolean isPositive) {
        ThemeColors colors = getCurrentThemeColors();
        JLabel indicator = new JLabel("● " + status);
        
        indicator.setFont(Typography.BODY_SMALL);
        indicator.setForeground(isPositive ? new Color(76, 175, 80) : new Color(244, 67, 54));
        indicator.setBorder(BorderFactory.createEmptyBorder(Spacing.XS, Spacing.SM, Spacing.XS, Spacing.SM));
        
        return indicator;
    }
    
    /**
     * Show professional notification
     */
    public static void showNotification(Container parent, String message, boolean isSuccess) {
        ThemeColors colors = getCurrentThemeColors();
        
        JLabel notification = new JLabel(message);
        notification.setFont(Typography.BODY_MEDIUM);
        notification.setOpaque(true);
        notification.setBackground(isSuccess ? new Color(76, 175, 80, 230) : new Color(244, 67, 54, 230));
        notification.setForeground(Color.WHITE);
        notification.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(Spacing.SM, Spacing.MD, Spacing.SM, Spacing.MD)
        ));
        
        // Position notification (would be implemented based on parent layout)
        System.out.println("📢 Notification: " + message);
    }
    
    /**
     * Print configuration summary
     */
    public static void printConfigSummary() {
        ThemeColors colors = getCurrentThemeColors();
        System.out.println("🎨 Professional UI Configuration:");
        System.out.println("   Current Theme: " + currentTheme.getDisplayName());
        System.out.println("   Primary Color: " + String.format("#%06X", colors.primary.getRGB() & 0xFFFFFF));
        System.out.println("   Animation Duration: " + AnimationConfig.FADE_DURATION + "ms");
        System.out.println("   Typography: " + Typography.BODY_MEDIUM.getName());
    }
}