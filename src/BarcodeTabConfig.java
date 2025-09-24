import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Professional ThorX6-Style Barcode Tab Configuration
 * Soft-coded system for creating the Barcode tab interface with the same design as Mark tab
 */
public class BarcodeTabConfig {
    
    // ==================== THORX6 BARCODE TAB STYLING ====================
    
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
    
    // Barcode-specific toolbar buttons
    public static final ToolbarButton[] TOOLBAR_BUTTONS = {
        new ToolbarButton("Generate", "▣", "Generate barcode (Ctrl+G)", () -> System.out.println("▣ Generate barcode")),
        new ToolbarButton("Validate", "✓", "Validate barcode (Ctrl+V)", () -> System.out.println("✓ Validate barcode")),
        new ToolbarButton("Scan", "📷", "Scan barcode (Ctrl+S)", () -> System.out.println("📷 Scan barcode")),
        new ToolbarButton("Export", "💾", "Export barcode (Ctrl+E)", () -> System.out.println("💾 Export barcode")),
        new ToolbarButton("Settings", "⚙", "Barcode settings (Ctrl+T)", () -> System.out.println("⚙ Barcode settings"))
    };
    
    // ==================== CONTENT PANEL CONFIGURATION ====================
    
    public static class ContentConfig {
        public static final String[] BARCODE_TYPES = {"Code128", "Code39", "QR Code", "DataMatrix", "EAN13", "UPC-A"};
        public static final String DEFAULT_BARCODE_TYPE = "Code128";
        public static final String[] ERROR_CORRECTIONS = {"L (7%)", "M (15%)", "Q (25%)", "H (30%)"};
        public static final String DEFAULT_ERROR_CORRECTION = "M (15%)";
        public static final String[] ORIENTATIONS = {"0°", "90°", "180°", "270°"};
        public static final String DEFAULT_ORIENTATION = "0°";
        public static final Color CONTENT_BACKGROUND = Color.WHITE;
        public static final Color CONTENT_BORDER = new Color(180, 180, 180);
        public static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
        public static final int CONTENT_AREA_HEIGHT = 150;
    }
    
    // ==================== BARCODE CONTROLS CONFIGURATION ====================
    
    public static class BarcodeConfig {
        public static final String[] TYPE_OPTIONS = {"Code128", "Code39", "QR Code", "DataMatrix", "EAN13", "UPC-A"};
        public static final String[] ERROR_OPTIONS = {"L (7%)", "M (15%)", "Q (25%)", "H (30%)"};
        public static final String[] SIZE_OPTIONS = {"Small", "Medium", "Large", "X-Large", "Custom"};
        public static final String[] FORMAT_OPTIONS = {"PNG", "JPG", "BMP", "SVG", "PDF"};
        public static final String DEFAULT_TYPE = "Code128";
        public static final String DEFAULT_ERROR = "M (15%)";
        public static final String DEFAULT_SIZE = "Medium";
        public static final String DEFAULT_FORMAT = "PNG";
        public static final Color DROPDOWN_BACKGROUND = Color.WHITE;
        public static final Font DROPDOWN_FONT = new Font("Segoe UI", Font.PLAIN, 11);
        
        // Default values
        public static final double DEFAULT_WIDTH = 50.0;
        public static final double DEFAULT_HEIGHT = 15.0;
        public static final double DEFAULT_MARGIN = 2.0;
        public static final double DEFAULT_ROTATION = 0.0;
        public static final boolean DEFAULT_SHOW_TEXT = true;
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
        new PropertyField("Type", "Code128", 70, true),
        new PropertyField("Data", "123456", 80, true),
        new PropertyField("Width", "50mm", 50, true),
        new PropertyField("Height", "15mm", 50, true),
        new PropertyField("Margin", "2mm", 50, true),
        new PropertyField("Rotation", "0°", 40, true),
        new PropertyField("Format", "PNG", 50, true)
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
        new ActionButton("Show Text", true, true, () -> System.out.println("✓ Show Text toggled")),
        new ActionButton("Quiet Zone", true, true, () -> System.out.println("✓ Quiet Zone toggled")),
        new ActionButton("Auto Size", true, false, () -> System.out.println("✓ Auto Size toggled")),
        new ActionButton("Generate", false, false, () -> System.out.println("▶ Generate barcode"))
    };
    
    // ==================== PROFESSIONAL UI CREATION METHODS ====================
    
    /**
     * Create professional ThorX6-style Barcode tab panel (Main Method)
     */
    public static JPanel createProfessionalBarcodeTab(DrawingCanvas canvas) {
        System.out.println("🎯 Creating ThorX6-style Barcode tab (professional design)...");
        
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
        
        System.out.println("✅ ThorX6-style Barcode tab created successfully!");
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
        
        // Barcode configuration area
        JPanel configArea = createBarcodeConfigArea();
        centerPanel.add(configArea, BorderLayout.NORTH);
        
        // Barcode preview area (using canvas as placeholder)
        JPanel previewWrapper = new JPanel(new BorderLayout());
        previewWrapper.setBackground(Color.WHITE);
        previewWrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Add preview label
        JLabel previewLabel = new JLabel("Barcode Preview", SwingConstants.CENTER);
        previewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        previewLabel.setForeground(TEXT_COLOR);
        previewLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        previewWrapper.add(previewLabel, BorderLayout.NORTH);
        
        // Create mock barcode preview
        JPanel barcodePreview = createMockBarcodePreview();
        previewWrapper.add(barcodePreview, BorderLayout.CENTER);
        
        centerPanel.add(previewWrapper, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    /**
     * Create barcode configuration area
     */
    private static JPanel createBarcodeConfigArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Barcode Configuration"));
        
        // Barcode type dropdown
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(CONTENT_FONT);
        panel.add(typeLabel);
        
        JComboBox<String> typeCombo = new JComboBox<>(BarcodeConfig.TYPE_OPTIONS);
        typeCombo.setSelectedItem(BarcodeConfig.DEFAULT_TYPE);
        typeCombo.setFont(BarcodeConfig.DROPDOWN_FONT);
        typeCombo.setPreferredSize(new Dimension(100, 24));
        panel.add(typeCombo);
        
        // Data input field
        JLabel dataLabel = new JLabel("Data:");
        dataLabel.setFont(CONTENT_FONT);
        panel.add(dataLabel);
        
        JTextField dataField = new JTextField("123456789");
        dataField.setFont(CONTENT_FONT);
        dataField.setPreferredSize(new Dimension(120, 24));
        panel.add(dataField);
        
        // Error correction dropdown (for QR codes)
        JLabel errorLabel = new JLabel("Error:");
        errorLabel.setFont(CONTENT_FONT);
        panel.add(errorLabel);
        
        JComboBox<String> errorCombo = new JComboBox<>(BarcodeConfig.ERROR_OPTIONS);
        errorCombo.setSelectedItem(BarcodeConfig.DEFAULT_ERROR);
        errorCombo.setFont(BarcodeConfig.DROPDOWN_FONT);
        errorCombo.setPreferredSize(new Dimension(80, 24));
        panel.add(errorCombo);
        
        return panel;
    }
    
    /**
     * Create mock barcode preview
     */
    private static JPanel createMockBarcodePreview() {
        JPanel previewPanel = new JPanel();
        previewPanel.setBackground(Color.WHITE);
        previewPanel.setPreferredSize(new Dimension(200, 100));
        
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Draw mock barcode
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Draw barcode bars
                g2d.setColor(Color.BLACK);
                for (int i = 0; i < 30; i++) {
                    int x = centerX - 75 + (i * 5);
                    int barHeight = (i % 3 == 0) ? 40 : 35;
                    g2d.fillRect(x, centerY - barHeight/2, (i % 2 == 0) ? 2 : 3, barHeight);
                }
                
                // Draw text below barcode
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
                g2d.drawString("123456789", centerX - 30, centerY + 30);
            }
        };
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
        
        // Barcode controls
        JPanel controlsPanel = createBarcodeControlsPanel();
        rightPanel.add(controlsPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel actionsPanel = createActionButtonsPanel();
        rightPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        return rightPanel;
    }
    
    /**
     * Create barcode controls panel
     */
    private static JPanel createBarcodeControlsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Barcode Options"));
        
        // Width spinner
        panel.add(createSpinnerControl("Width:", BarcodeConfig.DEFAULT_WIDTH, 10, 200, 5));
        panel.add(Box.createVerticalStrut(5));
        
        // Height spinner
        panel.add(createSpinnerControl("Height:", BarcodeConfig.DEFAULT_HEIGHT, 5, 100, 2));
        panel.add(Box.createVerticalStrut(5));
        
        // Margin spinner
        panel.add(createSpinnerControl("Margin:", BarcodeConfig.DEFAULT_MARGIN, 0, 20, 1));
        panel.add(Box.createVerticalStrut(5));
        
        // Rotation spinner
        panel.add(createSpinnerControl("Rotation:", BarcodeConfig.DEFAULT_ROTATION, 0, 360, 90));
        panel.add(Box.createVerticalStrut(5));
        
        // Size dropdown
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setFont(PROPERTIES_FONT);
        sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sizeLabel);
        
        JComboBox<String> sizeCombo = new JComboBox<>(BarcodeConfig.SIZE_OPTIONS);
        sizeCombo.setSelectedItem(BarcodeConfig.DEFAULT_SIZE);
        sizeCombo.setFont(BarcodeConfig.DROPDOWN_FONT);
        sizeCombo.setMaximumSize(new Dimension(100, 24));
        sizeCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sizeCombo);
        
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