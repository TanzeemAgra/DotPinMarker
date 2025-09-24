import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;

/**
 * ThorX6ToolbarFactory - Professional Toolbar Component Factory
 * Creates ThorX6-style toolbar components using soft-coded configuration values
 * Implements industrial CAD/CAM interface standards with full customization
 */
public class ThorX6ToolbarFactory {
    
    // ==================== SOFT-CODED FACTORY SETTINGS ====================
    
    // Animation and interaction settings
    private static final int HOVER_FADE_STEPS = 8;
    private static final int ANIMATION_TIMER_DELAY = 15;
    private static final float DISABLED_ALPHA = 0.5f;
    private static final float PRESSED_SCALE = 0.95f;
    
    // ==================== PROFESSIONAL TOOLBAR BUTTON ====================
    
    /**
     * Create ThorX6-style professional toolbar button with soft-coded styling
     */
    public static JButton createThorX6ToolbarButton(String text, String icon, String tooltip, 
                                                   ActionListener action) {
        JButton button = new JButton() {
            private boolean isHovered = false;
            private boolean isPressed = false;
            private float hoverAlpha = 0.0f;
            private Timer hoverTimer;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                // Background with soft-coded colors
                Color bgColor;
                if (!isEnabled()) {
                    bgColor = ThorX6Configuration.BUTTON_DISABLED_BG;
                } else if (isPressed) {
                    bgColor = ThorX6Configuration.BUTTON_PRESSED_BG;
                } else if (isSelected()) {
                    bgColor = ThorX6Configuration.BUTTON_SELECTED_BG;
                } else {
                    // Interpolate between normal and hover colors
                    bgColor = interpolateColor(
                        ThorX6Configuration.BUTTON_NORMAL_BG,
                        ThorX6Configuration.BUTTON_HOVER_BG,
                        hoverAlpha
                    );
                }
                
                // Draw rounded rectangle background
                RoundRectangle2D roundRect = new RoundRectangle2D.Float(
                    1, 1, width - 2, height - 2,
                    ThorX6Configuration.BUTTON_BORDER_RADIUS,
                    ThorX6Configuration.BUTTON_BORDER_RADIUS
                );
                
                g2.setColor(bgColor);
                g2.fill(roundRect);
                
                // Draw border
                g2.setColor(ThorX6Configuration.THORX6_MEDIUM_GRAY);
                g2.setStroke(new BasicStroke(ThorX6Configuration.BUTTON_BORDER_WIDTH));
                g2.draw(roundRect);
                
                // Draw icon and text
                drawButtonContent(g2, width, height);
                
                g2.dispose();
            }
            
            private void drawButtonContent(Graphics2D g2, int width, int height) {
                g2.setFont(ThorX6Configuration.THORX6_TOOLBAR_FONT);
                
                // Set text color based on state
                Color textColor = isEnabled() ? 
                    ThorX6Configuration.THORX6_WHITE : 
                    ThorX6Configuration.THORX6_DARK_GRAY;
                g2.setColor(textColor);
                
                // Calculate layout
                FontMetrics fm = g2.getFontMetrics();
                int iconSize = ThorX6Configuration.BUTTON_ICON_SIZE;
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                
                // Center content
                int contentHeight = iconSize + ThorX6Configuration.BUTTON_TEXT_OFFSET + textHeight;
                int startY = (height - contentHeight) / 2;
                
                // Draw icon (emoji/symbol)
                if (icon != null && !icon.isEmpty()) {
                    Font iconFont = new Font("Segoe UI Emoji", Font.PLAIN, iconSize);
                    g2.setFont(iconFont);
                    FontMetrics iconFm = g2.getFontMetrics();
                    int iconWidth = iconFm.stringWidth(icon);
                    int iconX = (width - iconWidth) / 2;
                    int iconY = startY + iconFm.getAscent();
                    
                    g2.drawString(icon, iconX, iconY);
                }
                
                // Draw text
                if (text != null && !text.isEmpty()) {
                    g2.setFont(ThorX6Configuration.THORX6_TOOLBAR_FONT);
                    int textX = (width - textWidth) / 2;
                    int textY = startY + iconSize + ThorX6Configuration.BUTTON_TEXT_OFFSET + fm.getAscent();
                    
                    g2.drawString(text, textX, textY);
                }
            }
            
            private Color interpolateColor(Color c1, Color c2, float factor) {
                factor = Math.max(0, Math.min(1, factor));
                int r = (int) (c1.getRed() + factor * (c2.getRed() - c1.getRed()));
                int g = (int) (c1.getGreen() + factor * (c2.getGreen() - c1.getGreen()));
                int b = (int) (c1.getBlue() + factor * (c2.getBlue() - c1.getBlue()));
                return new Color(r, g, b);
            }
            
            private void animateHover(boolean entering) {
                if (hoverTimer != null && hoverTimer.isRunning()) {
                    hoverTimer.stop();
                }
                
                final float targetAlpha = entering ? 1.0f : 0.0f;
                final float startAlpha = hoverAlpha;
                final float deltaAlpha = (targetAlpha - startAlpha) / HOVER_FADE_STEPS;
                
                hoverTimer = new Timer(ANIMATION_TIMER_DELAY, new ActionListener() {
                    private int step = 0;
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        step++;
                        hoverAlpha = startAlpha + (deltaAlpha * step);
                        
                        if (step >= HOVER_FADE_STEPS) {
                            hoverAlpha = targetAlpha;
                            hoverTimer.stop();
                        }
                        
                        repaint();
                    }
                });
                hoverTimer.start();
            }
            
            @Override
            protected void processMouseEvent(MouseEvent e) {
                switch (e.getID()) {
                    case MouseEvent.MOUSE_ENTERED:
                        isHovered = true;
                        animateHover(true);
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        break;
                        
                    case MouseEvent.MOUSE_EXITED:
                        isHovered = false;
                        isPressed = false;
                        animateHover(false);
                        setCursor(Cursor.getDefaultCursor());
                        break;
                        
                    case MouseEvent.MOUSE_PRESSED:
                        isPressed = true;
                        repaint();
                        break;
                        
                    case MouseEvent.MOUSE_RELEASED:
                        isPressed = false;
                        repaint();
                        break;
                }
                super.processMouseEvent(e);
            }
        };
        
        // Configure button properties using soft-coded values
        button.setPreferredSize(ThorX6Configuration.getToolbarButtonSize());
        button.setMinimumSize(ThorX6Configuration.getToolbarButtonSize());
        button.setMaximumSize(ThorX6Configuration.getToolbarButtonSize());
        button.setToolTipText(tooltip);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        
        // Add action listener
        if (action != null) {
            button.addActionListener(action);
        }
        
        return button;
    }
    
    // ==================== TOOLBAR SEPARATOR ====================
    
    /**
     * Create ThorX6-style toolbar separator with soft-coded styling
     */
    public static JComponent createThorX6Separator() {
        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                // Draw vertical separator line
                g2.setColor(ThorX6Configuration.THORX6_MEDIUM_GRAY);
                g2.setStroke(new BasicStroke(1));
                int x = width / 2;
                int margin = ThorX6Configuration.TOOLBAR_PADDING * 2;
                g2.drawLine(x, margin, x, height - margin);
                
                g2.dispose();
            }
        };
        
        separator.setPreferredSize(new Dimension(
            ThorX6Configuration.TOOLBAR_SEPARATOR_WIDTH,
            ThorX6Configuration.TOOLBAR_HEIGHT
        ));
        separator.setOpaque(false);
        
        return separator;
    }
    
    // ==================== TOOLBAR PANEL ====================
    
    /**
     * Create ThorX6-style toolbar panel with soft-coded styling
     */
    public static JPanel createThorX6ToolbarPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                
                int width = getWidth();
                int height = getHeight();
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, ThorX6Configuration.THORX6_MEDIUM_BLUE,
                    0, height, ThorX6Configuration.THORX6_DARK_BLUE
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, width, height);
                
                // Bottom border
                g2.setColor(ThorX6Configuration.THORX6_MEDIUM_GRAY);
                g2.drawLine(0, height - 1, width, height - 1);
                
                g2.dispose();
            }
        };
        
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 
            ThorX6Configuration.TOOLBAR_MARGIN, 
            ThorX6Configuration.TOOLBAR_MARGIN));
        panel.setPreferredSize(new Dimension(0, ThorX6Configuration.TOOLBAR_HEIGHT));
        panel.setOpaque(false);
        
        return panel;
    }
    
    // ==================== TOOL CATEGORY GROUP ====================
    
    /**
     * Create grouped tool category with soft-coded styling
     */
    public static JPanel createToolCategoryGroup(String categoryKey, DrawingCanvas canvas) {
        ThorX6Configuration.ToolCategory category = 
            ThorX6Configuration.TOOL_CATEGORIES.get(categoryKey);
        
        if (category == null) {
            return new JPanel(); // Return empty panel if category not found
        }
        
        JPanel group = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        group.setOpaque(false);
        group.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLoweredBevelBorder(),
            category.name
        ));
        
        // Create buttons for each tool in category
        for (String tool : category.tools) {
            JButton toolButton = createThorX6ToolbarButton(
                tool,
                category.icon,
                category.description + " - " + tool,
                createToolAction(tool, canvas)
            );
            
            group.add(toolButton);
        }
        
        return group;
    }
    
    // ==================== INPUT CONTROLS ====================
    
    /**
     * Create ThorX6-style input field with soft-coded styling
     */
    public static JTextField createThorX6InputField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(ThorX6Configuration.THORX6_LIGHT_GRAY);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                
                // Border
                g2.setColor(ThorX6Configuration.THORX6_MEDIUM_GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        field.setPreferredSize(ThorX6Configuration.getInputFieldSize());
        field.setFont(ThorX6Configuration.THORX6_MAIN_FONT);
        field.setForeground(ThorX6Configuration.THORX6_BLACK);
        field.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        field.setOpaque(false);
        
        return field;
    }
    
    /**
     * Create ThorX6-style spinner with soft-coded styling
     */
    public static JSpinner createThorX6Spinner(SpinnerNumberModel model) {
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(ThorX6Configuration.getInputFieldSize());
        spinner.setFont(ThorX6Configuration.THORX6_MAIN_FONT);
        
        // Style the editor field
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setFont(ThorX6Configuration.THORX6_MAIN_FONT);
            textField.setBackground(ThorX6Configuration.THORX6_LIGHT_GRAY);
        }
        
        return spinner;
    }
    
    // ==================== ACTION HELPERS ====================
    
    /**
     * Create tool-specific action listener
     */
    private static ActionListener createToolAction(String tool, DrawingCanvas canvas) {
        return e -> {
            // Soft-coded tool actions based on tool name
            switch (tool.toLowerCase()) {
                case "select":
                    canvas.setCurrentTool("SELECT");
                    break;
                case "text":
                    canvas.setCurrentTool("TEXT");
                    break;
                case "line":
                    canvas.setCurrentTool("LINE");
                    break;
                case "rectangle":
                    canvas.setCurrentTool("RECTANGLE");
                    break;
                case "circle":
                    canvas.setCurrentTool("CIRCLE");
                    break;
                // Add more tools as needed
                default:
                    System.out.println("🔧 ThorX6 Tool Selected: " + tool);
                    canvas.setCurrentTool(tool.toUpperCase());
                    break;
            }
        };
    }
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Create professional label with ThorX6 styling
     */
    public static JLabel createThorX6Label(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font != null ? font : ThorX6Configuration.THORX6_MAIN_FONT);
        label.setForeground(ThorX6Configuration.THORX6_WHITE);
        return label;
    }
    
    /**
     * Apply ThorX6 styling to existing component
     */
    public static void applyThorX6Styling(JComponent component) {
        component.setFont(ThorX6Configuration.THORX6_MAIN_FONT);
        component.setForeground(ThorX6Configuration.THORX6_WHITE);
        component.setBackground(ThorX6Configuration.THORX6_MEDIUM_BLUE);
        component.setBorder(ThorX6Configuration.getThorX6Border());
    }
}