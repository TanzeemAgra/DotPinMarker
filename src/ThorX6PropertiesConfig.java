import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * ThorX6 Properties Panel Configuration
 * Soft-coded dynamic properties panel matching ThorX6 software exactly
 * Using Dynamic Technique for flexible property management
 */
public class ThorX6PropertiesConfig {
    
    // ==================== GLOBAL PANEL REFERENCE ====================
    
    // Static reference to the current ThorX6 properties panel for coordinate updates
    private static JPanel currentPropertiesPanel = null;
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // ThorX6 Properties (Exact Order from ThorX6 Software)
    public static final String[] THORX6_PROPERTIES = {
        "Name", "X", "Y", "Z", "Angle", "Width", "Height", 
        "Clear Trans", "Mirror", "Lock Size", "Disable Print"
    };
    
    // Property Types (Dynamic Technique)
    public static final Map<String, String> PROPERTY_TYPES = new HashMap<String, String>() {{
        put("Name", "TEXT");
        put("X", "NUMBER");
        put("Y", "NUMBER"); 
        put("Z", "NUMBER");
        put("Angle", "DECIMAL");
        put("Width", "NUMBER");
        put("Height", "NUMBER");
        put("Clear Trans", "CHECKBOX");
        put("Mirror", "CHECKBOX");
        put("Lock Size", "CHECKBOX");
        put("Disable Print", "CHECKBOX");
    }};
    
    // Default Values (Soft Coded)
    public static final Map<String, Object> DEFAULT_VALUES = new HashMap<String, Object>() {{
        put("Name", "Mark1");
        put("X", 0);
        put("Y", 0);
        put("Z", 0);
        put("Angle", 0.0);
        put("Width", 100);
        put("Height", 40);
        put("Clear Trans", false);
        put("Mirror", false);
        put("Lock Size", false);
        put("Disable Print", false);
    }};
    
    // Visual Configuration
    public static final Color PANEL_BACKGROUND = new Color(248, 249, 250);
    public static final Color FIELD_BACKGROUND = Color.WHITE;
    public static final Color LABEL_COLOR = new Color(60, 60, 60);
    public static final Color BORDER_COLOR = new Color(225, 225, 225);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    // Sizing Configuration
    public static final int PANEL_HEIGHT = 45;
    public static final int TEXT_FIELD_WIDTH = 80;
    public static final int NUMBER_FIELD_WIDTH = 60;
    public static final int FIELD_HEIGHT = 24;
    public static final int SPACING = 8;
    
    // Feature Flags
    public static final boolean ENABLE_DYNAMIC_UPDATES = true;
    public static final boolean ENABLE_VALIDATION = true;
    public static final boolean ENABLE_DEBUG_OUTPUT = true;
    public static final boolean SHOW_SEPARATORS = true;
    
    /**
     * Create ThorX6-style properties panel using dynamic technique
     */
    public static JPanel createThorX6PropertiesPanel() {
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("🔧 Creating ThorX6 Properties Panel (Dynamic Technique)...");
            System.out.println("   Properties: " + java.util.Arrays.toString(THORX6_PROPERTIES));
        }
        
        JPanel propertiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, SPACING, 6));
        propertiesPanel.setBackground(PANEL_BACKGROUND);
        propertiesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(4, 12, 4, 12)
        ));
        propertiesPanel.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
        
        // Dynamic creation of properties
        Map<String, JComponent> propertyComponents = new HashMap<>();
        
        for (int i = 0; i < THORX6_PROPERTIES.length; i++) {
            String propertyName = THORX6_PROPERTIES[i];
            
            // Add separator before certain properties (ThorX6 style grouping)
            if (SHOW_SEPARATORS && shouldAddSeparator(i)) {
                addSeparator(propertiesPanel);
            }
            
            // Create label
            JLabel label = createDynamicLabel(propertyName);
            propertiesPanel.add(label);
            
            // Create component based on type (Dynamic Technique)
            JComponent component = createDynamicComponent(propertyName);
            propertyComponents.put(propertyName, component);
            propertiesPanel.add(component);
            
            // Add units or special formatting
            addUnitsIfNeeded(propertiesPanel, propertyName);
        }
        
        // Store components reference for dynamic updates
        propertiesPanel.putClientProperty("propertyComponents", propertyComponents);
        
        // Store global reference for coordinate updates
        currentPropertiesPanel = propertiesPanel;
        
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("✅ ThorX6 Properties Panel created with " + THORX6_PROPERTIES.length + " properties");
        }
        
        return propertiesPanel;
    }
    
    /**
     * Dynamic component creation based on property type
     */
    private static JComponent createDynamicComponent(String propertyName) {
        String type = PROPERTY_TYPES.get(propertyName);
        Object defaultValue = DEFAULT_VALUES.get(propertyName);
        
        switch (type) {
            case "TEXT":
                return createDynamicTextField((String) defaultValue);
            case "NUMBER":
                return createDynamicNumberSpinner((Integer) defaultValue, -9999, 9999, 1);
            case "DECIMAL":
                return createDynamicDecimalSpinner((Double) defaultValue, 0.0, 360.0, 0.1);
            case "CHECKBOX":
                return createDynamicCheckBox(propertyName, (Boolean) defaultValue);
            default:
                return new JLabel("Unknown");
        }
    }
    
    /**
     * Create dynamic text field
     */
    private static JTextField createDynamicTextField(String defaultValue) {
        JTextField field = new JTextField(defaultValue);
        field.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, FIELD_HEIGHT));
        field.setFont(FIELD_FONT);
        field.setBackground(FIELD_BACKGROUND);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        
        if (ENABLE_DYNAMIC_UPDATES) {
            field.addActionListener(e -> handlePropertyUpdate("TEXT", field.getText()));
        }
        
        return field;
    }
    
    /**
     * Create dynamic number spinner
     */
    private static JSpinner createDynamicNumberSpinner(int defaultValue, int min, int max, int step) {
        SpinnerNumberModel model = new SpinnerNumberModel(defaultValue, min, max, step);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(NUMBER_FIELD_WIDTH, FIELD_HEIGHT));
        spinner.setFont(FIELD_FONT);
        
        if (ENABLE_DYNAMIC_UPDATES) {
            spinner.addChangeListener(e -> handlePropertyUpdate("NUMBER", spinner.getValue()));
        }
        
        return spinner;
    }
    
    /**
     * Create dynamic decimal spinner
     */
    private static JSpinner createDynamicDecimalSpinner(double defaultValue, double min, double max, double step) {
        SpinnerNumberModel model = new SpinnerNumberModel(defaultValue, min, max, step);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(NUMBER_FIELD_WIDTH, FIELD_HEIGHT));
        spinner.setFont(FIELD_FONT);
        
        if (ENABLE_DYNAMIC_UPDATES) {
            spinner.addChangeListener(e -> handlePropertyUpdate("DECIMAL", spinner.getValue()));
        }
        
        return spinner;
    }
    
    /**
     * Create dynamic checkbox
     */
    private static JCheckBox createDynamicCheckBox(String text, boolean defaultValue) {
        JCheckBox checkBox = new JCheckBox(text, defaultValue);
        checkBox.setBackground(PANEL_BACKGROUND);
        checkBox.setFont(FIELD_FONT);
        checkBox.setForeground(LABEL_COLOR);
        
        if (ENABLE_DYNAMIC_UPDATES) {
            checkBox.addActionListener(e -> handlePropertyUpdate("CHECKBOX", checkBox.isSelected()));
        }
        
        return checkBox;
    }
    
    /**
     * Create dynamic label
     */
    private static JLabel createDynamicLabel(String text) {
        JLabel label = new JLabel(text + ":");
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        return label;
    }
    
    /**
     * Determine where to add separators (ThorX6 style grouping)
     */
    private static boolean shouldAddSeparator(int index) {
        // Add separators before certain logical groups (like ThorX6)
        return index == 1 ||  // Before X (after Name)
               index == 4 ||  // Before Angle (after Z)
               index == 5 ||  // Before Width (after Angle)
               index == 7;    // Before Clear Trans (after Height)
    }
    
    /**
     * Add visual separator
     */
    private static void addSeparator(JPanel panel) {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 20));
        separator.setBackground(BORDER_COLOR);
        panel.add(separator);
    }
    
    /**
     * Add units or special formatting after certain fields
     */
    private static void addUnitsIfNeeded(JPanel panel, String propertyName) {
        if (propertyName.equals("Angle")) {
            JLabel degreeLabel = new JLabel("°");
            degreeLabel.setFont(LABEL_FONT);
            degreeLabel.setForeground(LABEL_COLOR);
            panel.add(degreeLabel);
        }
    }
    
    /**
     * Handle dynamic property updates
     */
    private static void handlePropertyUpdate(String type, Object value) {
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("🔧 Property updated: " + type + " = " + value);
        }
        
        // Dynamic validation if enabled
        if (ENABLE_VALIDATION) {
            validatePropertyValue(type, value);
        }
    }
    
    /**
     * Dynamic property validation
     */
    private static void validatePropertyValue(String type, Object value) {
        // Implement validation logic based on type
        switch (type) {
            case "NUMBER":
                if (value instanceof Number) {
                    int intValue = ((Number) value).intValue();
                    if (intValue < -9999 || intValue > 9999) {
                        System.out.println("⚠️ Number value out of range: " + intValue);
                    }
                }
                break;
            case "DECIMAL":
                if (value instanceof Number) {
                    double doubleValue = ((Number) value).doubleValue();
                    if (doubleValue < 0.0 || doubleValue > 360.0) {
                        System.out.println("⚠️ Angle value out of range: " + doubleValue);
                    }
                }
                break;
        }
    }
    
    /**
     * Update coordinate fields when a mark is selected (COORDINATE TRACKING FIX)
     */
    public static void updateSelectedMarkCoordinates(Object selectedMark) {
        if (currentPropertiesPanel == null) return;
        
        Map<String, Object> coordinates = new HashMap<>();
        
        if (selectedMark == null) {
            // Clear coordinates when no object selected
            coordinates.put("Name", "");
            coordinates.put("X", 0);
            coordinates.put("Y", 0);
            coordinates.put("Z", 0);
            coordinates.put("Angle", 0.0);
            coordinates.put("Width", 0);
            coordinates.put("Height", 0);
        } else {
            // Update with selected mark coordinates using Mark base class properties
            try {
                // Cast to Mark base class to access common properties
                if (selectedMark instanceof Mark) {
                    Mark mark = (Mark) selectedMark;
                    
                    coordinates.put("Name", mark.getClass().getSimpleName().replace("Mark", "").toLowerCase());
                    coordinates.put("X", (int) Math.round(mark.x / 10.0)); // Convert to mm as integer
                    coordinates.put("Y", (int) Math.round(mark.y / 10.0)); // Convert to mm as integer
                    coordinates.put("Z", 0); // Integer value
                    coordinates.put("Angle", 0.0); // Double value for DECIMAL type
                    coordinates.put("Width", (int) Math.round(mark.width / 10.0)); // Convert to mm as integer
                    coordinates.put("Height", (int) Math.round(mark.height / 10.0)); // Convert to mm as integer
                    
                    System.out.println("📍 Coordinates updated: " + mark.getClass().getSimpleName() + " at (" + mark.x + "," + mark.y + ") size " + mark.width + "x" + mark.height);
                }
            } catch (Exception e) {
                System.err.println("❌ Error updating coordinates: " + e.getMessage());
            }
        }
        
        updatePropertiesPanel(currentPropertiesPanel, coordinates);
    }
    
    /**
     * Update properties panel with new values (Dynamic Technique)
     */
    public static void updatePropertiesPanel(JPanel propertiesPanel, Map<String, Object> newValues) {
        @SuppressWarnings("unchecked")
        Map<String, JComponent> components = (Map<String, JComponent>) propertiesPanel.getClientProperty("propertyComponents");
        
        if (components == null) return;
        
        for (Map.Entry<String, Object> entry : newValues.entrySet()) {
            String propertyName = entry.getKey();
            Object value = entry.getValue();
            JComponent component = components.get(propertyName);
            
            if (component != null) {
                updateComponentValue(component, PROPERTY_TYPES.get(propertyName), value);
            }
        }
    }
    
    /**
     * Update individual component value dynamically
     */
    private static void updateComponentValue(JComponent component, String type, Object value) {
        switch (type) {
            case "TEXT":
                if (component instanceof JTextField) {
                    ((JTextField) component).setText((String) value);
                }
                break;
            case "NUMBER":
            case "DECIMAL":
                if (component instanceof JSpinner) {
                    ((JSpinner) component).setValue(value);
                }
                break;
            case "CHECKBOX":
                if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected((Boolean) value);
                }
                break;
        }
    }
}