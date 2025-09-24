import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ThorX6MarkPanel - Professional CAD/CAM Style Mark Panel
 * Implements ThorX6 industrial interface design using comprehensive soft coding
 * Replaces the original MarkPanel with exact ThorX6 styling and functionality
 */
public class ThorX6MarkPanel extends JPanel implements DrawingCanvas.SelectionListener {
    
    // ==================== SOFT-CODED PANEL CONFIGURATION ====================
    
    // Panel layout settings
    private static final int MAIN_TOOLBAR_SECTIONS = 6;           // Number of toolbar sections
    private static final int PROPERTY_PANEL_SECTIONS = 4;         // Number of property sections
    private static final boolean SHOW_TOOL_CATEGORIES = true;     // Show grouped tool categories
    private static final boolean ENABLE_ANIMATIONS = true;        // Enable UI animations
    
    // Component references
    private final DrawingCanvas canvas;
    private final Map<String, JButton> toolButtons;
    private final Map<String, JComponent> propertyControls;
    private String currentTool = "SELECT";
    
    // Property controls with soft-coded initialization
    private JTextField contentField;
    private JSpinner heightSpinner;
    private JSpinner widthSpinner;
    private JSpinner spacingSpinner;
    private JComboBox<String> fontComboBox;
    private JComboBox<String> styleComboBox;
    
    // ==================== CONSTRUCTOR ====================
    
    public ThorX6MarkPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        this.toolButtons = new HashMap<>();
        this.propertyControls = new HashMap<>();
        
        // Register as selection listener
        canvas.addSelectionListener(this);
        
        // Initialize with ThorX6 styling
        initializeThorX6Interface();
        buildThorX6Layout();
    }
    
    // ==================== THORX6 INTERFACE INITIALIZATION ====================
    
    /**
     * Initialize ThorX6 interface with soft-coded styling
     */
    private void initializeThorX6Interface() {
        setLayout(new BorderLayout());
        setBackground(ThorX6Configuration.THORX6_DARK_BLUE);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Initialize property controls with soft-coded styling
        initializePropertyControls();
    }
    
    /**
     * Initialize property controls with ThorX6 styling
     */
    private void initializePropertyControls() {
        // Content field with ThorX6 styling
        contentField = ThorX6ToolbarFactory.createThorX6InputField("Enter text content");
        contentField.setText("Sample Text");
        contentField.addActionListener(e -> updateSelectedMarkProperties());
        propertyControls.put("content", contentField);
        
        // Font selection with system fonts
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = createThorX6ComboBox(fonts);
        fontComboBox.setSelectedItem("Arial");
        fontComboBox.addActionListener(e -> updateSelectedMarkProperties());
        propertyControls.put("font", fontComboBox);
        
        // Style selection
        String[] styles = {"Regular", "Bold", "Italic", "Bold Italic"};
        styleComboBox = createThorX6ComboBox(styles);
        styleComboBox.addActionListener(e -> updateSelectedMarkProperties());
        propertyControls.put("style", styleComboBox);
        
        // Height spinner with soft-coded range
        heightSpinner = ThorX6ToolbarFactory.createThorX6Spinner(
            new SpinnerNumberModel(12, 6, 72, 1));
        heightSpinner.addChangeListener(e -> updateSelectedMarkProperties());
        propertyControls.put("height", heightSpinner);
        
        // Width spinner with soft-coded range
        widthSpinner = ThorX6ToolbarFactory.createThorX6Spinner(
            new SpinnerNumberModel(100, 20, 500, 5));
        widthSpinner.addChangeListener(e -> updateSelectedMarkProperties());
        propertyControls.put("width", widthSpinner);
        
        // Spacing spinner with soft-coded range
        spacingSpinner = ThorX6ToolbarFactory.createThorX6Spinner(
            new SpinnerNumberModel(2, 0, 20, 1));
        spacingSpinner.addChangeListener(e -> updateSelectedMarkProperties());
        propertyControls.put("spacing", spacingSpinner);
    }
    
    /**
     * Create ThorX6-styled combo box
     */
    private JComboBox<String> createThorX6ComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(ThorX6Configuration.THORX6_MAIN_FONT);
        comboBox.setBackground(ThorX6Configuration.THORX6_LIGHT_GRAY);
        comboBox.setForeground(ThorX6Configuration.THORX6_BLACK);
        comboBox.setPreferredSize(new Dimension(
            ThorX6Configuration.COMBOBOX_WIDTH,
            ThorX6Configuration.INPUT_FIELD_HEIGHT
        ));
        return comboBox;
    }
    
    // ==================== THORX6 LAYOUT CONSTRUCTION ====================
    
    /**
     * Build complete ThorX6 layout structure
     */
    private void buildThorX6Layout() {
        // Main toolbar at top
        add(createMainThorX6Toolbar(), BorderLayout.NORTH);
        
        // Property panel on right side (ThorX6 style)
        add(createThorX6PropertyPanel(), BorderLayout.EAST);
        
        // Status information at bottom
        add(createThorX6StatusPanel(), BorderLayout.SOUTH);
    }
    
    /**
     * Create main ThorX6 toolbar with all tool categories
     */
    private JPanel createMainThorX6Toolbar() {
        JPanel mainToolbar = ThorX6ToolbarFactory.createThorX6ToolbarPanel();
        
        // Add tool categories with soft-coded configuration
        if (SHOW_TOOL_CATEGORIES) {
            // Selection tools
            mainToolbar.add(createSelectionToolGroup());
            mainToolbar.add(ThorX6ToolbarFactory.createThorX6Separator());
            
            // Drawing tools
            mainToolbar.add(createDrawingToolGroup());
            mainToolbar.add(ThorX6ToolbarFactory.createThorX6Separator());
            
            // Text tools
            mainToolbar.add(createTextToolGroup());
            mainToolbar.add(ThorX6ToolbarFactory.createThorX6Separator());
            
            // Pattern tools
            mainToolbar.add(createPatternToolGroup());
        }
        
        return mainToolbar;
    }
    
    // ==================== TOOL GROUPS (SOFT-CODED) ====================
    
    /**
     * Create selection tool group with ThorX6 styling
     */
    private JPanel createSelectionToolGroup() {
        JPanel group = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        group.setOpaque(false);
        
        // Selection tools with soft-coded configuration
        addToolButton(group, "Select", "S", "Selection and manipulation tool", "SELECT");
        addToolButton(group, "Move", "M", "Move objects and view", "MOVE");
        
        return group;
    }
    
    /**
     * Create drawing tool group with ThorX6 styling
     */
    private JPanel createDrawingToolGroup() {
        JPanel group = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        group.setOpaque(false);
        
        // Drawing tools with soft-coded configuration
        addToolButton(group, "Line", "L", "Draw straight lines", "LINE");
        addToolButton(group, "Rect", "R", "Draw rectangles and squares", "RECTANGLE");
        
        return group;
    }
    
    /**
     * Create text tool group with ThorX6 styling
     */
    private JPanel createTextToolGroup() {
        JPanel group = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        group.setOpaque(false);
        
        // Text tools with soft-coded configuration
        addToolButton(group, "Text", "T", "Create text objects", "TEXT");
        addToolButton(group, "Arc Text", "A", "Create curved text", "ARC_TEXT");
        
        return group;
    }
    
    /**
     * Create pattern tool group with ThorX6 styling
     */
    private JPanel createPatternToolGroup() {
        JPanel group = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        group.setOpaque(false);
        
        // Pattern tools with soft-coded configuration
        addToolButton(group, "Matrix", "M", "Create dot matrix patterns", "DOT_MATRIX");
        addToolButton(group, "Graph", "G", "Create technical graphs", "GRAPH");
        
        return group;
    }
    
    /**
     * Add tool button to group with soft-coded styling
     */
    private void addToolButton(JPanel group, String text, String icon, String tooltip, String toolId) {
        JButton button = ThorX6ToolbarFactory.createThorX6ToolbarButton(
            text, icon, tooltip, 
            e -> selectTool(toolId)
        );
        
        toolButtons.put(toolId, button);
        group.add(button);
    }
    
    // ==================== PROPERTY PANEL (THORX6 STYLE) ====================
    
    /**
     * Create ThorX6-style property panel
     */
    private JPanel createThorX6PropertyPanel() {
        JPanel propertyPanel = new JPanel();
        propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));
        propertyPanel.setBackground(ThorX6Configuration.getPanelBackground(1));
        propertyPanel.setPreferredSize(new Dimension(
            ThorX6Configuration.PROPERTY_PANEL_WIDTH, 0
        ));
        propertyPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        // Add property sections with soft-coded configuration
        propertyPanel.add(createContentPropertySection());
        propertyPanel.add(createFormatPropertySection());
        
        // Add flexible space at bottom
        propertyPanel.add(Box.createVerticalGlue());
        
        return propertyPanel;
    }
    
    /**
     * Create content property section
     */
    private JPanel createContentPropertySection() {
        JPanel section = createPropertySection("Content");
        
        addPropertyRow(section, "Text:", contentField);
        addPropertyRow(section, "Font:", fontComboBox);
        addPropertyRow(section, "Style:", styleComboBox);
        
        return section;
    }
    
    /**
     * Create format property section
     */
    private JPanel createFormatPropertySection() {
        JPanel section = createPropertySection("Format");
        
        addPropertyRow(section, "Height:", heightSpinner);
        addPropertyRow(section, "Width:", widthSpinner);
        addPropertyRow(section, "Spacing:", spacingSpinner);
        
        return section;
    }
    
    /**
     * Create property section with ThorX6 styling
     */
    private JPanel createPropertySection(String title) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(ThorX6Configuration.getPanelBackground(2));
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(),
                title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                ThorX6Configuration.THORX6_BOLD_FONT,
                ThorX6Configuration.THORX6_WHITE
            ),
            BorderFactory.createEmptyBorder(4, 6, 6, 6)
        ));
        section.setMaximumSize(new Dimension(
            ThorX6Configuration.PROPERTY_PANEL_WIDTH - 20,
            Integer.MAX_VALUE
        ));
        
        return section;
    }
    
    /**
     * Add property row with label and control
     */
    private void addPropertyRow(JPanel section, String labelText, JComponent control) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(
            ThorX6Configuration.PROPERTY_PANEL_WIDTH - 40,
            ThorX6Configuration.INPUT_FIELD_HEIGHT + 4
        ));
        
        if (!labelText.isEmpty()) {
            JLabel label = ThorX6ToolbarFactory.createThorX6Label(
                labelText, ThorX6Configuration.THORX6_MAIN_FONT);
            label.setPreferredSize(new Dimension(60, ThorX6Configuration.INPUT_FIELD_HEIGHT));
            row.add(label, BorderLayout.WEST);
        }
        
        row.add(control, BorderLayout.CENTER);
        row.add(Box.createVerticalStrut(2), BorderLayout.SOUTH);
        
        section.add(row);
    }
    
    // ==================== STATUS PANEL ====================
    
    /**
     * Create ThorX6-style status panel
     */
    private JPanel createThorX6StatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(ThorX6Configuration.THORX6_DARK_BLUE);
        statusPanel.setPreferredSize(new Dimension(0, ThorX6Configuration.STATUS_BAR_HEIGHT));
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        
        // Status information labels
        JLabel toolLabel = ThorX6ToolbarFactory.createThorX6Label(
            "Tool: " + currentTool, ThorX6Configuration.THORX6_STATUS_FONT);
        JLabel coordLabel = ThorX6ToolbarFactory.createThorX6Label(
            "X: 0, Y: 0", ThorX6Configuration.THORX6_STATUS_FONT);
        JLabel zoomLabel = ThorX6ToolbarFactory.createThorX6Label(
            "Zoom: 100%", ThorX6Configuration.THORX6_STATUS_FONT);
        
        statusPanel.add(toolLabel, BorderLayout.WEST);
        statusPanel.add(coordLabel, BorderLayout.CENTER);
        statusPanel.add(zoomLabel, BorderLayout.EAST);
        
        return statusPanel;
    }
    
    // ==================== TOOL SELECTION ====================
    
    /**
     * Select tool and update UI state
     */
    private void selectTool(String toolId) {
        // Update current tool
        String previousTool = currentTool;
        currentTool = toolId;
        
        // Update button states
        if (toolButtons.containsKey(previousTool)) {
            toolButtons.get(previousTool).setSelected(false);
        }
        if (toolButtons.containsKey(toolId)) {
            toolButtons.get(toolId).setSelected(true);
        }
        
        // Set canvas tool
        canvas.setCurrentTool(toolId);
        
        // Create mark based on tool selection
        createMarkForTool(toolId);
        
        System.out.println("Tool Selected: " + toolId);
    }
    
    /**
     * Create mark based on selected tool
     */
    private void createMarkForTool(String toolId) {
        String content = contentField.getText();
        int height = (Integer) heightSpinner.getValue();
        int width = (Integer) widthSpinner.getValue();
        String font = (String) fontComboBox.getSelectedItem();
        
        // Tool-specific mark creation with soft-coded mapping
        switch (toolId) {
            case "TEXT":
                canvas.addMark("Text", content, height, width, font);
                break;
            case "ARC_TEXT":
                canvas.addMark("ArcLetters", content, height, width, font);
                break;
            case "DOT_MATRIX":
                canvas.addMark("Dot Matrix", content, height, width, font);
                break;
            case "RECTANGLE":
                canvas.addMark("Rectangle", content, height, width, font);
                break;
            case "LINE":
                canvas.addMark("Line", content, height, width, font);
                break;
            case "GRAPH":
                canvas.addMark("Graph", content, height, width, font);
                break;
            // Selection and manipulation tools don't create marks
            case "SELECT":
            case "MOVE":
                break;
            default:
                System.out.println("Tool not yet implemented: " + toolId);
                break;
        }
    }
    
    // ==================== PROPERTY UPDATES ====================
    
    /**
     * Update selected mark properties from UI controls
     */
    private void updateSelectedMarkProperties() {
        Mark selectedMark = canvas.getSelectedMark();
        if (selectedMark == null) return;
        
        // Update mark properties using soft-coded approach
        if (selectedMark instanceof TextMark) {
            TextMark textMark = (TextMark) selectedMark;
            textMark.setText(contentField.getText());
            
            // Update font with new size and family
            Font currentFont = textMark.getFont();
            int fontSize = (Integer) heightSpinner.getValue();
            String fontFamily = (String) fontComboBox.getSelectedItem();
            Font newFont = new Font(fontFamily, currentFont.getStyle(), fontSize);
            textMark.setFont(newFont);
        }
        
        // Update common properties
        selectedMark.width = (Integer) widthSpinner.getValue();
        selectedMark.height = (Integer) heightSpinner.getValue();
        
        // Repaint canvas to show changes
        canvas.repaint();
    }
    
    // ==================== SELECTION LISTENER ====================
    
    @Override
    public void onSelectionChanged(Mark selectedMark) {
        if (selectedMark != null) {
            // Update property controls with selected mark data
            if (selectedMark instanceof TextMark) {
                TextMark textMark = (TextMark) selectedMark;
                contentField.setText(textMark.getText());
                
                // Get font information
                Font font = textMark.getFont();
                heightSpinner.setValue(font.getSize());
                fontComboBox.setSelectedItem(font.getFamily());
            }
            
            widthSpinner.setValue(selectedMark.width);
            heightSpinner.setValue(selectedMark.height);
        }
    }
}