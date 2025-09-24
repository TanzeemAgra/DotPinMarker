import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Professional ThorX6-Style Database Tab Configuration
 * Soft-coded system for creating the Database tab interface with the same design as Mark tab
 */
public class DatabaseTabConfig {
    
    // ==================== THORX6 DATABASE TAB STYLING ====================
    
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
    
    // Database-specific toolbar buttons
    public static final ToolbarButton[] TOOLBAR_BUTTONS = {
        new ToolbarButton("Connect", "🔗", "Connect to database (Ctrl+C)", () -> System.out.println("🔗 Database connect")),
        new ToolbarButton("Import", "📥", "Import data (Ctrl+I)", () -> System.out.println("📥 Import data")),
        new ToolbarButton("Export", "📤", "Export data (Ctrl+E)", () -> System.out.println("📤 Export data")),
        new ToolbarButton("Query", "🔍", "Run query (Ctrl+Q)", () -> System.out.println("🔍 Run query")),
        new ToolbarButton("Backup", "💾", "Backup database (Ctrl+B)", () -> System.out.println("💾 Backup database"))
    };
    
    // ==================== CONTENT PANEL CONFIGURATION ====================
    
    public static class ContentConfig {
        public static final String[] DATABASES = {"Projects", "Materials", "Templates", "Settings", "Custom"};
        public static final String DEFAULT_DATABASE = "Projects";
        public static final String[] TABLE_TYPES = {"Projects", "Materials", "Templates", "Users", "Settings"};
        public static final String DEFAULT_TABLE_TYPE = "Projects";
        public static final String[] RECORD_FORMATS = {"Standard", "Extended", "Minimal", "Custom"};
        public static final String DEFAULT_FORMAT = "Standard";
        public static final Color CONTENT_BACKGROUND = Color.WHITE;
        public static final Color CONTENT_BORDER = new Color(180, 180, 180);
        public static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
        public static final int CONTENT_AREA_HEIGHT = 150;
    }
    
    // ==================== DATABASE CONTROLS CONFIGURATION ====================
    
    public static class DatabaseConfig {
        public static final String[] DATABASE_OPTIONS = {"Projects", "Materials", "Templates", "Settings", "Custom"};
        public static final String[] TABLE_OPTIONS = {"Projects", "Materials", "Templates", "Users", "Settings"};
        public static final String[] FIELD_OPTIONS = {"Name", "Description", "Date", "ID", "Status", "Custom"};
        public static final String[] SORT_OPTIONS = {"Name", "Date", "ID", "Status", "Custom"};
        public static final String DEFAULT_DATABASE = "Projects";
        public static final String DEFAULT_TABLE = "Projects";
        public static final String DEFAULT_FIELD = "Name";
        public static final String DEFAULT_SORT = "Name";
        public static final Color DROPDOWN_BACKGROUND = Color.WHITE;
        public static final Font DROPDOWN_FONT = new Font("Segoe UI", Font.PLAIN, 11);
        
        // Default values
        public static final int DEFAULT_PAGE_SIZE = 50;
        public static final int DEFAULT_MAX_RECORDS = 1000;
        public static final double DEFAULT_CACHE_SIZE = 100.0;
        public static final boolean DEFAULT_AUTO_SAVE = true;
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
        new PropertyField("Database", "Projects", 80, true),
        new PropertyField("Table", "Projects", 70, true),
        new PropertyField("Records", "0", 50, false),
        new PropertyField("Page", "1", 40, true),
        new PropertyField("Sort", "Name", 60, true),
        new PropertyField("Filter", "None", 70, true),
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
        new ActionButton("Auto Save", true, true, () -> System.out.println("✓ Auto Save toggled")),
        new ActionButton("Real-time", true, false, () -> System.out.println("✓ Real-time toggled")),
        new ActionButton("Validate", true, true, () -> System.out.println("✓ Validate toggled")),
        new ActionButton("Refresh", false, false, () -> System.out.println("▶ Refresh data"))
    };
    
    // ==================== PROFESSIONAL UI CREATION METHODS ====================
    
    /**
     * Create professional ThorX6-style Database tab panel (Main Method)
     */
    public static JPanel createProfessionalDatabaseTab(DrawingCanvas canvas) {
        System.out.println("🎯 Creating ThorX6-style Database tab (professional design)...");
        
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
        
        System.out.println("✅ ThorX6-style Database tab created successfully!");
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
        
        // Database selection area
        JPanel selectionArea = createDatabaseSelectionArea();
        centerPanel.add(selectionArea, BorderLayout.NORTH);
        
        // Data table area (using canvas as placeholder)
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Add table header
        JLabel tableLabel = new JLabel("Database Records", SwingConstants.CENTER);
        tableLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableLabel.setForeground(TEXT_COLOR);
        tableLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        tableWrapper.add(tableLabel, BorderLayout.NORTH);
        
        // Create mock data table
        JPanel dataTable = createMockDataTable();
        tableWrapper.add(dataTable, BorderLayout.CENTER);
        
        centerPanel.add(tableWrapper, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    /**
     * Create database selection area
     */
    private static JPanel createDatabaseSelectionArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("Database Selection"));
        
        // Database dropdown
        JLabel dbLabel = new JLabel("Database:");
        dbLabel.setFont(CONTENT_FONT);
        panel.add(dbLabel);
        
        JComboBox<String> dbCombo = new JComboBox<>(DatabaseConfig.DATABASE_OPTIONS);
        dbCombo.setSelectedItem(DatabaseConfig.DEFAULT_DATABASE);
        dbCombo.setFont(DatabaseConfig.DROPDOWN_FONT);
        dbCombo.setPreferredSize(new Dimension(100, 24));
        panel.add(dbCombo);
        
        // Table dropdown
        JLabel tableLabel = new JLabel("Table:");
        tableLabel.setFont(CONTENT_FONT);
        panel.add(tableLabel);
        
        JComboBox<String> tableCombo = new JComboBox<>(DatabaseConfig.TABLE_OPTIONS);
        tableCombo.setSelectedItem(DatabaseConfig.DEFAULT_TABLE);
        tableCombo.setFont(DatabaseConfig.DROPDOWN_FONT);
        tableCombo.setPreferredSize(new Dimension(100, 24));
        panel.add(tableCombo);
        
        // Field dropdown
        JLabel fieldLabel = new JLabel("Field:");
        fieldLabel.setFont(CONTENT_FONT);
        panel.add(fieldLabel);
        
        JComboBox<String> fieldCombo = new JComboBox<>(DatabaseConfig.FIELD_OPTIONS);
        fieldCombo.setSelectedItem(DatabaseConfig.DEFAULT_FIELD);
        fieldCombo.setFont(DatabaseConfig.DROPDOWN_FONT);
        fieldCombo.setPreferredSize(new Dimension(80, 24));
        panel.add(fieldCombo);
        
        return panel;
    }
    
    /**
     * Create mock data table for display
     */
    private static JPanel createMockDataTable() {
        JPanel tablePanel = new JPanel(new GridLayout(6, 4, 1, 1));
        tablePanel.setBackground(Color.WHITE);
        
        // Header row
        String[] headers = {"ID", "Name", "Date", "Status"};
        for (String header : headers) {
            JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
            headerLabel.setBackground(TOOLBAR_BACKGROUND);
            headerLabel.setOpaque(true);
            headerLabel.setBorder(BorderFactory.createRaisedBevelBorder());
            tablePanel.add(headerLabel);
        }
        
        // Sample data rows
        String[][] sampleData = {
            {"001", "Project Alpha", "2025-01-15", "Active"},
            {"002", "Project Beta", "2025-01-14", "Pending"},
            {"003", "Project Gamma", "2025-01-13", "Complete"},
            {"004", "Project Delta", "2025-01-12", "Active"},
            {"005", "Project Epsilon", "2025-01-11", "Pending"}
        };
        
        for (String[] row : sampleData) {
            for (String cell : row) {
                JLabel cellLabel = new JLabel(cell, SwingConstants.CENTER);
                cellLabel.setFont(PROPERTIES_FONT);
                cellLabel.setBackground(Color.WHITE);
                cellLabel.setOpaque(true);
                cellLabel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
                tablePanel.add(cellLabel);
            }
        }
        
        return tablePanel;
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
        
        // Database controls
        JPanel controlsPanel = createDatabaseControlsPanel();
        rightPanel.add(controlsPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel actionsPanel = createActionButtonsPanel();
        rightPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        return rightPanel;
    }
    
    /**
     * Create database controls panel
     */
    private static JPanel createDatabaseControlsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(TOOLBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder("DB Options"));
        
        // Page size spinner
        panel.add(createSpinnerControl("Page Size:", (double)DatabaseConfig.DEFAULT_PAGE_SIZE, 10, 1000, 10));
        panel.add(Box.createVerticalStrut(5));
        
        // Max records spinner
        panel.add(createSpinnerControl("Max Rec:", (double)DatabaseConfig.DEFAULT_MAX_RECORDS, 100, 10000, 100));
        panel.add(Box.createVerticalStrut(5));
        
        // Cache size spinner
        panel.add(createSpinnerControl("Cache MB:", DatabaseConfig.DEFAULT_CACHE_SIZE, 10, 1000, 10));
        panel.add(Box.createVerticalStrut(5));
        
        // Sort dropdown
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(PROPERTIES_FONT);
        sortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sortLabel);
        
        JComboBox<String> sortCombo = new JComboBox<>(DatabaseConfig.SORT_OPTIONS);
        sortCombo.setSelectedItem(DatabaseConfig.DEFAULT_SORT);
        sortCombo.setFont(DatabaseConfig.DROPDOWN_FONT);
        sortCombo.setMaximumSize(new Dimension(100, 24));
        sortCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sortCombo);
        
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