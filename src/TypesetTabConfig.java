import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Professional ThorX6-Style Typeset Tab Configuration
 * Soft-coded system for creating the Typeset tab interface with the same design as Mark tab
 */
public class TypesetTabConfig {
    
    // ==================== THORX6 TYPESET TAB STYLING ====================
    
    // Layout Configuration (matching Mark Tab)
    public static final int TOOLBAR_WIDTH = 85;
    public static final int CODER_PANEL_WIDTH = 120;
    public static final int PROPERTIES_HEIGHT = 45;
    public static final int BUTTON_SIZE = 32;
    public static final int SPACING = 4;
    
    // Colors (matching ThorX6 Professional theme)
    public static final Color TOOLBAR_BACKGROUND = new Color(240, 240, 240);
    public static final Color CONTENT_BACKGROUND = Color.WHITE;
    public static final Color BUTTON_BACKGROUND = new Color(245, 245, 245);
    public static final Color BUTTON_HOVER = new Color(225, 240, 255);
    public static final Color BUTTON_PRESSED = new Color(200, 230, 255);
    public static final Color BORDER_COLOR = new Color(200, 200, 200);
    public static final Color TEXT_COLOR = new Color(60, 60, 60);
    
    // Typography
    public static final Font TOOLBAR_FONT = new Font("Segoe UI", Font.PLAIN, 9);
    public static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font PROPERTIES_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    // ==================== TOOLBAR BUTTON CONFIGURATION ====================
    
    public static class ToolbarButton {
        public final String text;
        public final String icon;
        public final String tooltip;
        public final Runnable action;
        
        public ToolbarButton(String text, String icon, String tooltip, Runnable action) {
            this.text = text;
            this.icon = icon;
            this.tooltip = tooltip;
            this.action = action;
        }
    }
    
    // Typeset-specific toolbar buttons
    public static final ToolbarButton[] TOOLBAR_BUTTONS = {
        new ToolbarButton("Template", "📝", "Select template (Ctrl+T)", () -> System.out.println("📝 Template action")),
        new ToolbarButton("Format", "🎨", "Format text (Ctrl+F)", () -> System.out.println("🎨 Format action")),
        new ToolbarButton("Align", "⚏", "Align text (Ctrl+A)", () -> System.out.println("⚏ Align action")),
        new ToolbarButton("Columns", "▦", "Set columns (Ctrl+L)", () -> System.out.println("▦ Columns action")),
        new ToolbarButton("Preview", "👁", "Preview typeset (Ctrl+P)", () -> System.out.println("👁 Preview action"))
    };
    
    // ==================== CONTENT PANEL CONFIGURATION ====================
    
    public static class ContentConfig {
        public static final String[] TEMPLATES = {"Custom", "Business Cards", "Labels", "Name Tags", "Address Labels", "CD Labels"};
        public static final String DEFAULT_TEMPLATE = "Custom";
        public static final String[] ALIGNMENTS = {"Left", "Center", "Right", "Justify"};
        public static final String DEFAULT_ALIGNMENT = "Left";
        public static final Color CONTENT_BACKGROUND = Color.WHITE;
        public static final Color CONTENT_BORDER = new Color(180, 180, 180);
        public static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
        public static final int CONTENT_AREA_HEIGHT = 150;
    }
    
    // ==================== TYPESET CONTROLS CONFIGURATION ====================
    
    public static class TypesetConfig {
        public static final String[] TEMPLATE_OPTIONS = {"Custom", "Business Cards", "Labels", "Name Tags", "Address Labels", "CD Labels"};
        public static final String[] ALIGNMENT_OPTIONS = {"Left", "Center", "Right", "Justify"};
        public static final String[] REFERENCE_OPTIONS = {"Page", "Selection", "Margins"};
        public static final String DEFAULT_TEMPLATE = "Custom";
        public static final String DEFAULT_ALIGNMENT = "Left";
        public static final String DEFAULT_REFERENCE = "Page";
        public static final Color DROPDOWN_BACKGROUND = Color.WHITE;
        public static final Font DROPDOWN_FONT = new Font("Segoe UI", Font.PLAIN, 11);
        
        // Default values
        public static final double DEFAULT_MARGIN = 10.0;
        public static final double DEFAULT_LINE_SPACING = 1.2;
        public static final int DEFAULT_COLUMNS = 1;
        public static final double DEFAULT_MOVE_STEP = 1.0;
    }
    
    // ==================== PROPERTIES BAR CONFIGURATION ====================
    
    public static class PropertyField {
        public final String label;
        public final String defaultValue;
        public final int width;
        public final boolean editable;
        
        public PropertyField(String label, String defaultValue, int width, boolean editable) {
            this.label = label;
            this.defaultValue = defaultValue;
            this.width = width;
            this.editable = editable;
        }
    }
    
    public static final PropertyField[] PROPERTY_FIELDS = {
        new PropertyField("Template", "Custom", 80, true),
        new PropertyField("Margin", "10", 60, true),
        new PropertyField("Spacing", "1.2", 60, true),
        new PropertyField("Columns", "1", 40, true),
        new PropertyField("Align", "Left", 60, true),
        new PropertyField("Reference", "Page", 70, true),
        new PropertyField("Preview", "Off", 50, false)
    };
    
    // ==================== ACTION BUTTONS CONFIGURATION ====================
    
    public static class ActionButton {
        public final String text;
        public final boolean isCheckbox;
        public final boolean defaultState;
        public final Runnable action;
        
        public ActionButton(String text, boolean isCheckbox, boolean defaultState, Runnable action) {
            this.text = text;
            this.isCheckbox = isCheckbox;
            this.defaultState = defaultState;
            this.action = action;
        }
    }
    
    public static final ActionButton[] ACTION_BUTTONS = {
        new ActionButton("Make Line", true, false, () -> System.out.println("✓ Make Line toggled")),
        new ActionButton("Disable Aim", true, false, () -> System.out.println("✓ Disable Aim toggled")),
        new ActionButton("Lock", true, false, () -> System.out.println("✓ Lock toggled")),
        new ActionButton("Apply", false, false, () -> System.out.println("▶ Apply typeset"))
    };
    
    // ==================== PROFESSIONAL UI CREATION METHODS ====================
    
    /**
     * Create professional ThorX6-style Typeset tab panel (Main Method)
     */
    public static JPanel createProfessionalTypesetTab(DrawingCanvas canvas) {
        System.out.println("🎯 Creating ThorX6-style Typeset tab (professional design)...");
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CONTENT_BACKGROUND);
        
        // Left toolbar (vertical)
        JPanel leftToolbar = createVerticalToolbar();
        mainPanel.add(leftToolbar, BorderLayout.WEST);
        
        // Center content area
        JPanel centerContent = createCenterContentArea(canvas);
        mainPanel.add(centerContent, BorderLayout.CENTER);
        
        // Right controls panel
        JPanel rightPanel = createRightControlsPanel();
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        // Bottom properties panel
        JPanel bottomProperties = createBottomPropertiesPanel();
        mainPanel.add(bottomProperties, BorderLayout.SOUTH);
        
        System.out.println("✅ ThorX6-style Typeset tab created successfully!");
        return mainPanel;
    }
    
    /**
     * Create vertical toolbar (same style as Mark tab)
     */
    private static JPanel createVerticalToolbar() {
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(TOOLBAR_BACKGROUND);
        toolbar.setPreferredSize(new Dimension(TOOLBAR_WIDTH, 0));
        toolbar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR),
            BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING)
        ));
        
        // Add toolbar buttons
        for (ToolbarButton config : TOOLBAR_BUTTONS) {
            JButton button = createToolbarButton(config);
            toolbar.add(button);
            toolbar.add(Box.createVerticalStrut(SPACING));
        }
        
        toolbar.add(Box.createVerticalGlue());
        return toolbar;
    }
    
    /**
     * Create center content area
     */
    private static JPanel createCenterContentArea(DrawingCanvas canvas) {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(CONTENT_BACKGROUND);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Template selection area
        JPanel templateArea = createTemplateSelectionArea();
        centerPanel.add(templateArea, BorderLayout.NORTH);
        
        // Drawing canvas wrapper
        JPanel canvasWrapper = new JPanel(new BorderLayout());
        canvasWrapper.setBackground(Color.WHITE);
        canvasWrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        canvasWrapper.add(canvas, BorderLayout.CENTER);
        centerPanel.add(canvasWrapper, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    /**
     * Create template selection area
     */
    private static JPanel createTemplateSelectionArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Template & Format"));
        
        // Template dropdown
        JLabel templateLabel = new JLabel("Template:");
        templateLabel.setFont(CONTENT_FONT);
        panel.add(templateLabel);
        
        JComboBox<String> templateCombo = new JComboBox<>(TypesetConfig.TEMPLATE_OPTIONS);
        templateCombo.setSelectedItem(TypesetConfig.DEFAULT_TEMPLATE);
        templateCombo.setFont(TypesetConfig.DROPDOWN_FONT);
        templateCombo.setPreferredSize(new Dimension(120, 24));
        panel.add(templateCombo);
        
        // Alignment dropdown
        JLabel alignLabel = new JLabel("Alignment:");
        alignLabel.setFont(CONTENT_FONT);
        panel.add(alignLabel);
        
        JComboBox<String> alignCombo = new JComboBox<>(TypesetConfig.ALIGNMENT_OPTIONS);
        alignCombo.setSelectedItem(TypesetConfig.DEFAULT_ALIGNMENT);
        alignCombo.setFont(TypesetConfig.DROPDOWN_FONT);
        alignCombo.setPreferredSize(new Dimension(80, 24));
        panel.add(alignCombo);
        
        return panel;
    }
    
    /**
     * Create right controls panel
     */
    private static JPanel createRightControlsPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(CODER_PANEL_WIDTH, 0));
        rightPanel.setBackground(TOOLBAR_BACKGROUND);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING)
        ));
        
        // Typeset controls
        JPanel controlsPanel = createTypesetControlsPanel();
        rightPanel.add(controlsPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel actionsPanel = createActionButtonsPanel();
        rightPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        return rightPanel;
    }
    
    /**
     * Create typeset controls panel
     */
    private static JPanel createTypesetControlsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Controls"));
        
        // Margin spinner
        panel.add(createSpinnerControl("Margin:", TypesetConfig.DEFAULT_MARGIN, 0, 100, 1));
        panel.add(Box.createVerticalStrut(5));
        
        // Line spacing spinner
        panel.add(createSpinnerControl("Spacing:", TypesetConfig.DEFAULT_LINE_SPACING, 0.5, 3.0, 0.1));
        panel.add(Box.createVerticalStrut(5));
        
        // Columns spinner
        panel.add(createSpinnerControl("Columns:", (double)TypesetConfig.DEFAULT_COLUMNS, 1, 10, 1));
        panel.add(Box.createVerticalStrut(5));
        
        // Reference dropdown
        JLabel refLabel = new JLabel("Reference:");
        refLabel.setFont(PROPERTIES_FONT);
        refLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(refLabel);
        
        JComboBox<String> refCombo = new JComboBox<>(TypesetConfig.REFERENCE_OPTIONS);
        refCombo.setSelectedItem(TypesetConfig.DEFAULT_REFERENCE);
        refCombo.setFont(TypesetConfig.DROPDOWN_FONT);
        refCombo.setMaximumSize(new Dimension(100, 24));
        refCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(refCombo);
        
        return panel;
    }
    
    /**
     * Create action buttons panel
     */
    private static JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));
        
        for (ActionButton config : ACTION_BUTTONS) {
            JComponent button = createActionButton(config);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(button);
            panel.add(Box.createVerticalStrut(3));
        }
        
        return panel;
    }
    
    /**
     * Create bottom properties panel (same style as Mark tab)
     */
    private static JPanel createBottomPropertiesPanel() {
        JPanel propertiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        propertiesPanel.setBackground(TOOLBAR_BACKGROUND);
        propertiesPanel.setPreferredSize(new Dimension(0, PROPERTIES_HEIGHT));
        propertiesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        
        // Add property fields
        for (PropertyField config : PROPERTY_FIELDS) {
            JPanel fieldPanel = createPropertyField(config);
            propertiesPanel.add(fieldPanel);
        }
        
        return propertiesPanel;
    }
    
    // ==================== UI COMPONENT CREATION HELPERS ====================
    
    /**
     * Create professional toolbar button (same style as Mark tab)
     */
    private static JButton createToolbarButton(ToolbarButton config) {
        JButton button = new JButton();
        
        // Multi-line layout for icon and text
        button.setLayout(new BorderLayout());
        
        // Icon label
        JLabel iconLabel = new JLabel(config.icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        button.add(iconLabel, BorderLayout.CENTER);
        
        // Text label
        JLabel textLabel = new JLabel(config.text, SwingConstants.CENTER);
        textLabel.setFont(TOOLBAR_FONT);
        textLabel.setForeground(TEXT_COLOR);
        button.add(textLabel, BorderLayout.SOUTH);
        
        // Professional styling
        button.setPreferredSize(new Dimension(BUTTON_SIZE + 20, BUTTON_SIZE + 15));
        button.setBackground(BUTTON_BACKGROUND);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Tooltip
        button.setToolTipText(config.tooltip);
        
        // Professional hover effects
        addButtonHoverEffects(button);
        
        // Action
        button.addActionListener(e -> config.action.run());
        
        return button;
    }
    
    /**
     * Create spinner control with label
     */
    private static JPanel createSpinnerControl(String labelText, double defaultValue, double min, double max, double step) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel label = new JLabel(labelText);
        label.setFont(PROPERTIES_FONT);
        label.setPreferredSize(new Dimension(55, 20));
        panel.add(label);
        
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, step));
        spinner.setFont(PROPERTIES_FONT);
        spinner.setPreferredSize(new Dimension(55, 20));
        
        // Center align spinner text
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        panel.add(spinner);
        return panel;
    }
    
    /**
     * Create property field with label and input (same as Mark tab)
     */
    private static JPanel createPropertyField(PropertyField config) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        panel.setBackground(TOOLBAR_BACKGROUND);
        
        // Label
        JLabel label = new JLabel(config.label + ":");
        label.setFont(PROPERTIES_FONT);
        label.setForeground(TEXT_COLOR);
        panel.add(label);
        
        // Input field
        JTextField field = new JTextField(config.defaultValue);
        field.setFont(PROPERTIES_FONT);
        field.setPreferredSize(new Dimension(config.width, 20));
        field.setEditable(config.editable);
        field.setBackground(config.editable ? Color.WHITE : new Color(250, 250, 250));
        field.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        panel.add(field);
        
        return panel;
    }
    
    /**
     * Create action button (checkbox or regular button) - same as Mark tab
     */
    private static JComponent createActionButton(ActionButton config) {
        if (config.isCheckbox) {
            JCheckBox checkBox = new JCheckBox(config.text, config.defaultState);
            checkBox.setBackground(TOOLBAR_BACKGROUND);
            checkBox.setFont(PROPERTIES_FONT);
            checkBox.setForeground(TEXT_COLOR);
            checkBox.setFocusPainted(false);
            checkBox.addActionListener(e -> config.action.run());
            return checkBox;
        } else {
            JButton button = new JButton(config.text);
            button.setFont(PROPERTIES_FONT);
            button.setBackground(BUTTON_BACKGROUND);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addActionListener(e -> config.action.run());
            addButtonHoverEffects(button);
            return button;
        }
    }
    
    /**
     * Add professional hover effects to buttons (same as Mark tab)
     */
    private static void addButtonHoverEffects(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_HOVER);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_BACKGROUND);
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_PRESSED);
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_HOVER);
            }
        });
    }
}