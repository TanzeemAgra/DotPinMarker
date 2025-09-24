import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Professional ThorX6-Style Mark Tab Panel
 * Exact recreation of the Mark tab interface shown in Tab_Design.PNG
 */
public class ProfessionalMarkTab extends JPanel {
    
    private JTextArea contentArea;
    private JComboBox<String> coderDropdown;
    private JTextField[] propertyFields;
    
    public ProfessionalMarkTab(DrawingCanvas drawingCanvas) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Print configuration info
        MarkTabConfig.printConfigSummary();
        
        // Create main layout matching the image
        createThorX6MarkInterface(drawingCanvas);
        
        System.out.println("✨ Professional ThorX6 Mark Tab created successfully!");
    }
    
    /**
     * Create the complete ThorX6 Mark interface layout
     */
    private void createThorX6MarkInterface(DrawingCanvas drawingCanvas) {
        // Main content area with BorderLayout
        JPanel mainContent = new JPanel(new BorderLayout());
        
        // Left toolbar (Undo, Paste, Cut, Copy, Erase)
        JPanel leftToolbar = createLeftToolbar();
        mainContent.add(leftToolbar, BorderLayout.WEST);
        
        // Center content area
        JPanel centerContent = createCenterContentArea();
        mainContent.add(centerContent, BorderLayout.CENTER);
        
        // Right coder panel
        JPanel rightCoderPanel = createRightCoderPanel();
        mainContent.add(rightCoderPanel, BorderLayout.EAST);
        
        add(mainContent, BorderLayout.CENTER);
        
        // Bottom properties bar
        JPanel propertiesBar = createPropertiesBar();
        add(propertiesBar, BorderLayout.SOUTH);
    }
    
    /**
     * Create left toolbar with Undo, Paste, Cut, Copy, Erase buttons
     */
    private JPanel createLeftToolbar() {
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(MarkTabConfig.TOOLBAR_BACKGROUND);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, MarkTabConfig.BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 4, 8, 4)
        ));
        toolbar.setPreferredSize(new Dimension(MarkTabConfig.TOOLBAR_WIDTH, 0));
        
        // Add title label
        JLabel toolbarTitle = new JLabel("Clipboard", SwingConstants.CENTER);
        toolbarTitle.setFont(new Font("Segoe UI", Font.BOLD, 9));
        toolbarTitle.setForeground(MarkTabConfig.TEXT_COLOR);
        toolbarTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolbar.add(toolbarTitle);
        toolbar.add(Box.createVerticalStrut(4));
        
        // Add clipboard buttons (including new Undo and Erase)
        for (MarkTabConfig.ToolbarButton buttonConfig : MarkTabConfig.TOOLBAR_BUTTONS) {
            JButton button = MarkTabConfig.createToolbarButton(buttonConfig);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            toolbar.add(button);
            toolbar.add(Box.createVerticalStrut(MarkTabConfig.SPACING));
        }
        
        // Add flexible space at bottom
        toolbar.add(Box.createVerticalGlue());
        
        return toolbar;
    }
    
    /**
     * Create center content area with text input
     */
    private JPanel createCenterContentArea() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(MarkTabConfig.CONTENT_BACKGROUND);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        // Content section header
        JPanel contentHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contentHeader.setBackground(MarkTabConfig.CONTENT_BACKGROUND);
        
        JLabel contentLabel = new JLabel("Content");
        contentLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        contentLabel.setForeground(MarkTabConfig.TEXT_COLOR);
        contentHeader.add(contentLabel);
        
        // Text input field matching the image
        JTextField textInput = new JTextField(MarkTabConfig.ContentConfig.DEFAULT_TEXT);
        textInput.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textInput.setPreferredSize(new Dimension(200, 24));
        textInput.setBorder(BorderFactory.createLineBorder(MarkTabConfig.ContentConfig.TEXT_BORDER, 1));
        contentHeader.add(textInput);
        
        centerPanel.add(contentHeader, BorderLayout.NORTH);
        
        // Font controls section
        JPanel fontControls = createFontControlsPanel();
        centerPanel.add(fontControls, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    /**
     * Create font controls panel matching the image layout
     */
    private JPanel createFontControlsPanel() {
        JPanel fontPanel = new JPanel(new GridBagLayout());
        fontPanel.setBackground(MarkTabConfig.CONTENT_BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Font label
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel fontLabel = new JLabel("Font");
        fontLabel.setFont(MarkTabConfig.PROPERTIES_FONT);
        fontPanel.add(fontLabel, gbc);
        
        // Font dropdown
        gbc.gridx = 1; gbc.gridy = 0;
        JComboBox<String> fontDropdown = new JComboBox<>(new String[]{"0.15OCP.SLF", "Arial", "Times New Roman"});
        fontDropdown.setPreferredSize(new Dimension(120, 24));
        fontPanel.add(fontDropdown, gbc);
        
        // Height label and field
        gbc.gridx = 2; gbc.gridy = 0;
        fontPanel.add(new JLabel("Height"), gbc);
        gbc.gridx = 3; gbc.gridy = 0;
        JTextField heightField = new JTextField("7");
        heightField.setPreferredSize(new Dimension(50, 24));
        fontPanel.add(heightField, gbc);
        
        // ExFont label
        gbc.gridx = 0; gbc.gridy = 1;
        fontPanel.add(new JLabel("ExFont"), gbc);
        
        // ExFont dropdown
        gbc.gridx = 1; gbc.gridy = 1;
        JComboBox<String> exFontDropdown = new JComboBox<>(new String[]{"0.HZT.SLF"});
        exFontDropdown.setPreferredSize(new Dimension(120, 24));
        fontPanel.add(exFontDropdown, gbc);
        
        // Width label and field
        gbc.gridx = 2; gbc.gridy = 1;
        fontPanel.add(new JLabel("Width"), gbc);
        gbc.gridx = 3; gbc.gridy = 1;
        JTextField widthField = new JTextField("5");
        widthField.setPreferredSize(new Dimension(50, 24));
        fontPanel.add(widthField, gbc);
        
        // Add Mark button and Spacing
        gbc.gridx = 0; gbc.gridy = 2;
        JButton addMarkButton = new JButton("Add Mark");
        addMarkButton.setFont(MarkTabConfig.PROPERTIES_FONT);
        addMarkButton.setBackground(MarkTabConfig.BUTTON_BACKGROUND);
        addMarkButton.setBorder(BorderFactory.createLineBorder(MarkTabConfig.BORDER_COLOR, 1));
        fontPanel.add(addMarkButton, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2;
        fontPanel.add(new JLabel("Spacing"), gbc);
        gbc.gridx = 3; gbc.gridy = 2;
        JTextField spacingField = new JTextField("0");
        spacingField.setPreferredSize(new Dimension(50, 24));
        fontPanel.add(spacingField, gbc);
        
        return fontPanel;
    }
    
    /**
     * Create right coder panel with dropdown
     */
    private JPanel createRightCoderPanel() {
        JPanel coderPanel = new JPanel();
        coderPanel.setLayout(new BoxLayout(coderPanel, BoxLayout.Y_AXIS));
        coderPanel.setBackground(MarkTabConfig.TOOLBAR_BACKGROUND);
        coderPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, MarkTabConfig.BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        coderPanel.setPreferredSize(new Dimension(MarkTabConfig.CODER_PANEL_WIDTH, 0));
        
        // Coder section
        JLabel coderLabel = new JLabel("Coder");
        coderLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        coderLabel.setForeground(MarkTabConfig.TEXT_COLOR);
        coderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        coderPanel.add(coderLabel);
        
        coderPanel.add(Box.createVerticalStrut(8));
        
        // Coder type dropdown
        JLabel typeLabel = new JLabel("Type");
        typeLabel.setFont(MarkTabConfig.PROPERTIES_FONT);
        typeLabel.setForeground(MarkTabConfig.TEXT_COLOR);
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        coderPanel.add(typeLabel);
        
        coderDropdown = new JComboBox<>(MarkTabConfig.CoderConfig.CODER_OPTIONS);
        coderDropdown.setSelectedItem(MarkTabConfig.CoderConfig.DEFAULT_SELECTION);
        coderDropdown.setFont(MarkTabConfig.CoderConfig.DROPDOWN_FONT);
        coderDropdown.setBackground(MarkTabConfig.CoderConfig.DROPDOWN_BACKGROUND);
        coderDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        coderDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        coderPanel.add(coderDropdown);
        
        // Coder content area
        coderPanel.add(Box.createVerticalStrut(8));
        JLabel coderContentLabel = new JLabel("Coder", SwingConstants.CENTER);
        coderContentLabel.setFont(MarkTabConfig.PROPERTIES_FONT);
        coderContentLabel.setForeground(MarkTabConfig.TEXT_COLOR);
        coderContentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coderPanel.add(coderContentLabel);
        
        // Flexible space
        coderPanel.add(Box.createVerticalGlue());
        
        return coderPanel;
    }
    
    /**
     * Create bottom properties bar with all controls
     */
    private JPanel createPropertiesBar() {
        JPanel propertiesPanel = new JPanel(new BorderLayout());
        propertiesPanel.setBackground(MarkTabConfig.TOOLBAR_BACKGROUND);
        propertiesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, MarkTabConfig.BORDER_COLOR),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        propertiesPanel.setPreferredSize(new Dimension(0, MarkTabConfig.PROPERTIES_HEIGHT));
        
        // Left side: Property fields
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        fieldsPanel.setBackground(MarkTabConfig.TOOLBAR_BACKGROUND);
        
        propertyFields = new JTextField[MarkTabConfig.PROPERTY_FIELDS.length];
        for (int i = 0; i < MarkTabConfig.PROPERTY_FIELDS.length; i++) {
            JPanel fieldPanel = MarkTabConfig.createPropertyField(MarkTabConfig.PROPERTY_FIELDS[i]);
            fieldsPanel.add(fieldPanel);
        }
        
        propertiesPanel.add(fieldsPanel, BorderLayout.WEST);
        
        // Right side: Action buttons
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 2));
        actionsPanel.setBackground(MarkTabConfig.TOOLBAR_BACKGROUND);
        
        for (MarkTabConfig.ActionButton actionConfig : MarkTabConfig.ACTION_BUTTONS) {
            JComponent actionButton = MarkTabConfig.createActionButton(actionConfig);
            actionsPanel.add(actionButton);
        }
        
        propertiesPanel.add(actionsPanel, BorderLayout.EAST);
        
        return propertiesPanel;
    }
    
    /**
     * Get current content text
     */
    public String getContentText() {
        return contentArea != null ? contentArea.getText() : MarkTabConfig.ContentConfig.DEFAULT_TEXT;
    }
    
    /**
     * Set content text
     */
    public void setContentText(String text) {
        if (contentArea != null) {
            contentArea.setText(text);
        }
    }
    
    /**
     * Get selected coder type
     */
    public String getSelectedCoderType() {
        return coderDropdown != null ? (String) coderDropdown.getSelectedItem() : MarkTabConfig.CoderConfig.DEFAULT_SELECTION;
    }
}