import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Professional ThorX6-Style Print Tab Configuration
 * Soft-coded system for creating the Print tab interface with the same design as Mark tab
 */
public class PrintTabConfig {
    
    // ==================== THORX6 PRINT TAB STYLING ====================
    
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
    
    // ==================== PRINT TAB GROUP ORDERING CONFIGURATION (SOFT CODING) ====================
    
    // Group Ordering Control
    public static final boolean ENABLE_GROUPED_TOOLBAR = true;           // ENABLE grouped toolbar structure
    public static final boolean OPERATIONS_BELOW_PRINTER = true;         // PLACE Operations Sub Group below Printer Sub Group
    public static final boolean ADD_GROUP_SEPARATORS = true;             // ADD visual separators between groups
    public static final boolean SHOW_GROUP_LABELS = true;                // SHOW group labels (e.g., "Printer", "Operations")
    
    // Printer Sub Group Configuration
    public static final boolean ENABLE_PRINTER_SUB_GROUP = true;         // ENABLE Printer Sub Group
    public static final String PRINTER_GROUP_LABEL = "Printer";          // Label for Printer Sub Group
    public static final ToolbarButton[] PRINTER_GROUP_BUTTONS = {
        new ToolbarButton("Setup", "⚙", "Print setup (Ctrl+S)", () -> System.out.println("⚙ Print setup")),
        new ToolbarButton("Settings", "🔧", "Print settings (Ctrl+T)", () -> System.out.println("🔧 Print settings"))
    };
    
    // Operations Sub Group Configuration  
    public static final boolean ENABLE_OPERATIONS_SUB_GROUP = true;      // ENABLE Operations Sub Group
    public static final String OPERATIONS_GROUP_LABEL = "Operations";    // Label for Operations Sub Group
    public static final ToolbarButton[] OPERATIONS_GROUP_BUTTONS = {
        new ToolbarButton("Preview", "👁", "Print preview (Ctrl+P)", () -> System.out.println("👁 Print preview")),
        new ToolbarButton("Print", "🖨", "Start printing (Ctrl+Shift+P)", () -> System.out.println("🖨 Print action")),
        new ToolbarButton("Queue", "📋", "Print queue (Ctrl+Q)", () -> System.out.println("📋 Print queue"))
    };
    
    // Legacy toolbar buttons (fallback for non-grouped mode)
    public static final ToolbarButton[] TOOLBAR_BUTTONS = {
        new ToolbarButton("Setup", "⚙", "Print setup (Ctrl+S)", () -> System.out.println("⚙ Print setup")),
        new ToolbarButton("Preview", "👁", "Print preview (Ctrl+P)", () -> System.out.println("👁 Print preview")),
        new ToolbarButton("Print", "🖨", "Start printing (Ctrl+Shift+P)", () -> System.out.println("🖨 Print action")),
        new ToolbarButton("Queue", "📋", "Print queue (Ctrl+Q)", () -> System.out.println("📋 Print queue")),
        new ToolbarButton("Settings", "🔧", "Print settings (Ctrl+T)", () -> System.out.println("🔧 Print settings"))
    };
    
    // ==================== CONTENT PANEL CONFIGURATION ====================
    
    public static class ContentConfig {
        public static final String[] PRINTERS = {"Default Printer", "ThorX6 Laser", "Dot Matrix Printer", "Engraving Machine"};
        public static final String DEFAULT_PRINTER = "Default Printer";
        public static final String[] PAPER_SIZES = {"A4", "Letter", "Legal", "Custom"};
        public static final String DEFAULT_PAPER_SIZE = "A4";
        public static final String[] PRINT_QUALITY = {"Draft", "Normal", "High", "Best"};
        public static final String DEFAULT_QUALITY = "Normal";
        public static final Color CONTENT_BACKGROUND = Color.WHITE;
        public static final Color CONTENT_BORDER = new Color(180, 180, 180);
        public static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
        public static final int CONTENT_AREA_HEIGHT = 150;
    }
    
    // ==================== PRINT CONTROLS CONFIGURATION ====================
    
    public static class PrintConfig {
        public static final String[] PRINTER_OPTIONS = {"Default Printer", "ThorX6 Laser", "Dot Matrix Printer", "Engraving Machine"};
        public static final String[] QUALITY_OPTIONS = {"Draft", "Normal", "High", "Best"};
        public static final String[] PAPER_OPTIONS = {"A4", "Letter", "Legal", "Custom"};
        public static final String[] ORIENTATION_OPTIONS = {"Portrait", "Landscape"};
        public static final String DEFAULT_PRINTER = "Default Printer";
        public static final String DEFAULT_QUALITY = "Normal";
        public static final String DEFAULT_PAPER = "A4";
        public static final String DEFAULT_ORIENTATION = "Portrait";
        public static final Color DROPDOWN_BACKGROUND = Color.WHITE;
        public static final Font DROPDOWN_FONT = new Font("Segoe UI", Font.PLAIN, 11);
        
        // Default values
        public static final int DEFAULT_COPIES = 1;
        public static final double DEFAULT_SCALE = 100.0;
        public static final double DEFAULT_MARGIN = 10.0;
        public static final double DEFAULT_RESOLUTION = 300.0;
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
        new PropertyField("Printer", "Default", 80, true),
        new PropertyField("Copies", "1", 40, true),
        new PropertyField("Quality", "Normal", 60, true),
        new PropertyField("Scale", "100%", 50, true),
        new PropertyField("Paper", "A4", 50, true),
        new PropertyField("Resolution", "300dpi", 70, true),
        new PropertyField("Status", "Ready", 60, false)
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
        new ActionButton("Color Print", true, false, () -> System.out.println("✓ Color Print toggled")),
        new ActionButton("Duplex", true, false, () -> System.out.println("✓ Duplex toggled")),
        new ActionButton("Collate", true, true, () -> System.out.println("✓ Collate toggled")),
        new ActionButton("Print Now", false, false, () -> System.out.println("▶ Print Now"))
    };
    
    // ==================== PROFESSIONAL UI CREATION METHODS ====================
    
    /**
     * Create professional ThorX6-style Print tab panel (Main Method)
     */
    public static JPanel createProfessionalPrintTab(DrawingCanvas canvas) {
        System.out.println("🎯 Creating ThorX6-style Print tab (professional design)...");
        
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
        
        System.out.println("✅ ThorX6-style Print tab created successfully!");
        return mainPanel;
    }
    
    /**
     * Create vertical toolbar with grouped structure (Soft Coding Enhanced)
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
        
        // Soft coding: Use grouped toolbar structure if enabled
        if (ENABLE_GROUPED_TOOLBAR) {
            System.out.println("🔧 Creating grouped Print Tab toolbar: Operations below Printer");
            
            // Determine group order based on soft coding configuration
            if (OPERATIONS_BELOW_PRINTER) {
                // Printer Sub Group first (top)
                if (ENABLE_PRINTER_SUB_GROUP) {
                    addToolbarGroup(toolbar, PRINTER_GROUP_LABEL, PRINTER_GROUP_BUTTONS);
                }
                
                // Operations Sub Group below (bottom)
                if (ENABLE_OPERATIONS_SUB_GROUP) {
                    addToolbarGroup(toolbar, OPERATIONS_GROUP_LABEL, OPERATIONS_GROUP_BUTTONS);
                }
            } else {
                // Operations Sub Group first (top) - alternative order
                if (ENABLE_OPERATIONS_SUB_GROUP) {
                    addToolbarGroup(toolbar, OPERATIONS_GROUP_LABEL, OPERATIONS_GROUP_BUTTONS);
                }
                
                // Printer Sub Group below (bottom)
                if (ENABLE_PRINTER_SUB_GROUP) {
                    addToolbarGroup(toolbar, PRINTER_GROUP_LABEL, PRINTER_GROUP_BUTTONS);
                }
            }
        } else {
            // Legacy mode: Add toolbar buttons linearly
            System.out.println("🔧 Using legacy Print Tab toolbar layout");
            for (ToolbarButton config : TOOLBAR_BUTTONS) {
                JButton button = createToolbarButton(config);
                toolbar.add(button);
                toolbar.add(Box.createVerticalStrut(SPACING));
            }
        }
        
        toolbar.add(Box.createVerticalGlue());
        return toolbar;
    }
    
    /**
     * Add a toolbar group with label and buttons (Soft Coding Helper)
     */
    private static void addToolbarGroup(JPanel toolbar, String groupLabel, ToolbarButton[] buttons) {
        // Add group label if enabled
        if (SHOW_GROUP_LABELS) {
            JLabel label = new JLabel(groupLabel);
            label.setFont(new Font("Segoe UI", Font.BOLD, 9));
            label.setForeground(TEXT_COLOR);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));
            toolbar.add(label);
        }
        
        // Add group buttons
        for (ToolbarButton config : buttons) {
            JButton button = createToolbarButton(config);
            toolbar.add(button);
            toolbar.add(Box.createVerticalStrut(SPACING));
        }
        
        // Add group separator if enabled and not the last group
        if (ADD_GROUP_SEPARATORS) {
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setMaximumSize(new Dimension(TOOLBAR_WIDTH - 10, 1));
            separator.setForeground(BORDER_COLOR);
            toolbar.add(Box.createVerticalStrut(SPACING));
            toolbar.add(separator);
            toolbar.add(Box.createVerticalStrut(SPACING * 2));
        }
    }
    
    /**
     * Create center content area
     */
    private static JPanel createCenterContentArea(DrawingCanvas canvas) {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(CONTENT_BACKGROUND);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Print setup area
        JPanel setupArea = createPrintSetupArea();
        centerPanel.add(setupArea, BorderLayout.NORTH);
        
        // Drawing canvas wrapper (print preview)
        JPanel canvasWrapper = new JPanel(new BorderLayout());
        canvasWrapper.setBackground(Color.WHITE);
        canvasWrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Add preview label
        JLabel previewLabel = new JLabel("Print Preview", SwingConstants.CENTER);
        previewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        previewLabel.setForeground(TEXT_COLOR);
        previewLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        canvasWrapper.add(previewLabel, BorderLayout.NORTH);
        
        canvasWrapper.add(canvas, BorderLayout.CENTER);
        centerPanel.add(canvasWrapper, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    /**
     * Create print setup area
     */
    private static JPanel createPrintSetupArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Print Setup"));
        
        // Printer dropdown
        JLabel printerLabel = new JLabel("Printer:");
        printerLabel.setFont(CONTENT_FONT);
        panel.add(printerLabel);
        
        JComboBox<String> printerCombo = new JComboBox<>(PrintConfig.PRINTER_OPTIONS);
        printerCombo.setSelectedItem(PrintConfig.DEFAULT_PRINTER);
        printerCombo.setFont(PrintConfig.DROPDOWN_FONT);
        printerCombo.setPreferredSize(new Dimension(140, 24));
        panel.add(printerCombo);
        
        // Quality dropdown
        JLabel qualityLabel = new JLabel("Quality:");
        qualityLabel.setFont(CONTENT_FONT);
        panel.add(qualityLabel);
        
        JComboBox<String> qualityCombo = new JComboBox<>(PrintConfig.QUALITY_OPTIONS);
        qualityCombo.setSelectedItem(PrintConfig.DEFAULT_QUALITY);
        qualityCombo.setFont(PrintConfig.DROPDOWN_FONT);
        qualityCombo.setPreferredSize(new Dimension(80, 24));
        panel.add(qualityCombo);
        
        // Paper size dropdown
        JLabel paperLabel = new JLabel("Paper:");
        paperLabel.setFont(CONTENT_FONT);
        panel.add(paperLabel);
        
        JComboBox<String> paperCombo = new JComboBox<>(PrintConfig.PAPER_OPTIONS);
        paperCombo.setSelectedItem(PrintConfig.DEFAULT_PAPER);
        paperCombo.setFont(PrintConfig.DROPDOWN_FONT);
        paperCombo.setPreferredSize(new Dimension(80, 24));
        panel.add(paperCombo);
        
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
        
        // Print controls
        JPanel controlsPanel = createPrintControlsPanel();
        rightPanel.add(controlsPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel actionsPanel = createActionButtonsPanel();
        rightPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        return rightPanel;
    }
    
    /**
     * Create print controls panel
     */
    private static JPanel createPrintControlsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Print Options"));
        
        // Copies spinner
        panel.add(createSpinnerControl("Copies:", (double)PrintConfig.DEFAULT_COPIES, 1, 999, 1));
        panel.add(Box.createVerticalStrut(5));
        
        // Scale spinner
        panel.add(createSpinnerControl("Scale %:", PrintConfig.DEFAULT_SCALE, 10, 500, 5));
        panel.add(Box.createVerticalStrut(5));
        
        // Margin spinner
        panel.add(createSpinnerControl("Margin:", PrintConfig.DEFAULT_MARGIN, 0, 50, 1));
        panel.add(Box.createVerticalStrut(5));
        
        // Resolution spinner
        panel.add(createSpinnerControl("DPI:", PrintConfig.DEFAULT_RESOLUTION, 72, 1200, 72));
        panel.add(Box.createVerticalStrut(5));
        
        // Orientation dropdown
        JLabel orientLabel = new JLabel("Orientation:");
        orientLabel.setFont(PROPERTIES_FONT);
        orientLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(orientLabel);
        
        JComboBox<String> orientCombo = new JComboBox<>(PrintConfig.ORIENTATION_OPTIONS);
        orientCombo.setSelectedItem(PrintConfig.DEFAULT_ORIENTATION);
        orientCombo.setFont(PrintConfig.DROPDOWN_FONT);
        orientCombo.setMaximumSize(new Dimension(100, 24));
        orientCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(orientCombo);
        
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