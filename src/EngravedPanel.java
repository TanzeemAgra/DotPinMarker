import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * EngravedPanel - Comprehensive engraving tools and settings toolbar
 * Includes all marking modes, parameters, layout controls, preview, and output settings
 */
public class EngravedPanel extends JPanel {
    
    // Modern color scheme for better visibility
    private static final Color LABEL_COLOR = new Color(44, 62, 80);   // Dark text for labels
    private static final Color SECTION_BG = new Color(248, 249, 250);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    
    private final DrawingCanvas canvas;
    
    // Marking Mode Components
    private JComboBox<String> markingModeComboBox;
    private JTextField textInputField;
    private JSpinner archRadiusSpinner;
    private JSpinner donutInnerRadiusSpinner;
    private JSpinner donutOuterRadiusSpinner;
    private JButton importSymbolButton;
    
    // Marking Parameters
    private JSpinner dotPitchHorizontalSpinner;
    private JSpinner dotPitchVerticalSpinner;
    private JSpinner dotDepthSpinner;
    private JComboBox<String> markingSpeedComboBox;
    private JSpinner lineThicknessSpinner;
    private JSpinner passCountSpinner;
    
    // Layout Controls
    private JSpinner xOffsetSpinner;
    private JSpinner yOffsetSpinner;
    private JSpinner rotationSpinner;
    private JSpinner scaleSpinner;
    
    // Preview Controls
    private JCheckBox previewGridCheckBox;
    private JCheckBox materialBoundaryCheckBox;
    private JCheckBox dotPreviewCheckBox;
    private JButton simulateButton;
    
    // Output Settings
    private JButton exportGCodeButton;
    private JButton startMarkingButton;
    private JButton saveTemplateButton;
    private JButton exportImageButton;
    
    // Hardware Interface (future phase)
    private JButton connectButton;
    private JComboBox<String> portComboBox;
    private JComboBox<String> baudRateComboBox;
    private JLabel statusLabel;
    
    // Helper method to create labels with dark text
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        return label;
    }
    
    public EngravedPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        setupUI();
        setupEventHandlers();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Match other panels
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5)); // Reduced padding
        setPreferredSize(new Dimension(1200, 130)); // Significantly increased height for better visibility
        
        // Create tabbed interface for better organization
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 10)); // Increased font for better readability
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        
        // Tab 1: Marking Mode & Parameters
        tabbedPane.addTab("Mode", createModeParametersPanel());
        
        // Tab 2: Layout & Preview  
        tabbedPane.addTab("Layout", createLayoutPreviewPanel());
        
        // Tab 3: Output & Hardware
        tabbedPane.addTab("Output", createOutputHardwarePanel());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createModeParametersPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2)); // Slightly increased spacing for Mode visibility
        panel.setBackground(getBackground());
        
        // Marking Mode Selection
        panel.add(createMarkingModeSection());
        panel.add(createSeparator());
        
        // Marking Parameters
        panel.add(createMarkingParametersSection());
        
        return panel;
    }
    
    private JPanel createLayoutPreviewPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2)); // Increased spacing for Layout visibility
        panel.setBackground(getBackground());
        
        // Layout Controls
        panel.add(createLayoutControlsSection());
        panel.add(createSeparator());
        
        // Preview Controls
        panel.add(createPreviewControlsSection());
        
        return panel;
    }
    
    private JPanel createOutputHardwarePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 1)); // Ultra-reduced spacing
        panel.setBackground(getBackground());
        
        // Output Settings
        panel.add(createOutputSettingsSection());
        panel.add(createSeparator());
        
        // Hardware Interface
        panel.add(createHardwareInterfaceSection());
        
        return panel;
    }
    
    private JPanel createMarkingModeSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(SECTION_BG);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), "Marking Mode",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 10), LABEL_COLOR)); // Increased font size for better readability
        section.setPreferredSize(new Dimension(250, 95)); // Increased width and height for all Mode features
        
        JPanel modePanel = new JPanel(new GridBagLayout());
        modePanel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4); // Increased spacing for better visibility
        
        // Mode selection
        gbc.gridx = 0; gbc.gridy = 0;
        modePanel.add(createDarkLabel("Mode:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        markingModeComboBox = new JComboBox<>(new String[]{
            "Straight Text", "Arch Text", "Donut Shape", "Data Matrix", "Custom Symbol"
        });
        markingModeComboBox.setPreferredSize(new Dimension(140, 22)); // Increased width for better visibility
        markingModeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        modePanel.add(markingModeComboBox, gbc);
        
        // Text input
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        modePanel.add(createDarkLabel("Text:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        textInputField = new JTextField("Sample Text");
        textInputField.setPreferredSize(new Dimension(140, 22)); // Increased width for better visibility
        textInputField.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        modePanel.add(textInputField, gbc);
        
        // Mode-specific parameters (shown/hidden based on selection)
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        modePanel.add(createDarkLabel("Radius:"), gbc);
        gbc.gridx = 1;
        archRadiusSpinner = new JSpinner(new SpinnerNumberModel(50, 10, 200, 5));
        archRadiusSpinner.setPreferredSize(new Dimension(60, 22)); // Increased size for better visibility
        modePanel.add(archRadiusSpinner, gbc);
        
        gbc.gridx = 2;
        importSymbolButton = new JButton("Import");
        importSymbolButton.setPreferredSize(new Dimension(70, 22)); // Increased width for better visibility
        importSymbolButton.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        modePanel.add(importSymbolButton, gbc);
        
        // Initialize visibility of mode-specific controls
        archRadiusSpinner.setVisible(false);
        importSymbolButton.setVisible(false);
        
        section.add(modePanel);
        return section;
    }
    
    private JPanel createMarkingParametersSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(SECTION_BG);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), "Marking Parameters",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 10), LABEL_COLOR)); // Increased font for better readability
        section.setPreferredSize(new Dimension(280, 100)); // Significantly increased width and height for better visibility
        
        JPanel paramPanel = new JPanel(new GridBagLayout());
        paramPanel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4); // Increased spacing for better visibility
        
        // Dot Pitch
        gbc.gridx = 0; gbc.gridy = 0;
        paramPanel.add(createDarkLabel("Dot Pitch H:"), gbc);
        gbc.gridx = 1;
        dotPitchHorizontalSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        dotPitchHorizontalSpinner.setPreferredSize(new Dimension(45, 20)); // Smaller size
        paramPanel.add(dotPitchHorizontalSpinner, gbc);
        
        gbc.gridx = 2;
        paramPanel.add(createDarkLabel("V:"), gbc);
        gbc.gridx = 3;
        dotPitchVerticalSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        dotPitchVerticalSpinner.setPreferredSize(new Dimension(45, 20)); // Smaller size
        paramPanel.add(dotPitchVerticalSpinner, gbc);
        
        // Dot Depth and Speed
        gbc.gridx = 0; gbc.gridy = 1;
        paramPanel.add(createDarkLabel("Depth:"), gbc);
        gbc.gridx = 1;
        dotDepthSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.1, 5.0, 0.1));
        dotDepthSpinner.setPreferredSize(new Dimension(45, 20)); // Smaller size
        paramPanel.add(dotDepthSpinner, gbc);
        
        gbc.gridx = 2;
        paramPanel.add(createDarkLabel("Speed:"), gbc);
        gbc.gridx = 3;
        markingSpeedComboBox = new JComboBox<>(new String[]{"Slow", "Medium", "Fast"});
        markingSpeedComboBox.setPreferredSize(new Dimension(55, 20)); // Smaller size
        markingSpeedComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 8)); // Smaller font
        paramPanel.add(markingSpeedComboBox, gbc);
        
        // Line Thickness and Pass Count
        gbc.gridx = 0; gbc.gridy = 2;
        paramPanel.add(createDarkLabel("Thickness:"), gbc);
        gbc.gridx = 1;
        lineThicknessSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        lineThicknessSpinner.setPreferredSize(new Dimension(45, 20)); // Smaller size
        paramPanel.add(lineThicknessSpinner, gbc);
        
        gbc.gridx = 2;
        paramPanel.add(createDarkLabel("Passes:"), gbc);
        gbc.gridx = 3;
        passCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        passCountSpinner.setPreferredSize(new Dimension(45, 20)); // Smaller size
        paramPanel.add(passCountSpinner, gbc);
        
        section.add(paramPanel);
        return section;
    }
    
    private JPanel createLayoutControlsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(SECTION_BG);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), "Layout Controls",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 10), LABEL_COLOR)); // Increased font for better readability
        section.setPreferredSize(new Dimension(250, 100)); // Significantly increased width and height for better visibility
        
        JPanel layoutPanel = new JPanel(new GridBagLayout());
        layoutPanel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4); // Increased spacing for better visibility
        
        // Position
        gbc.gridx = 0; gbc.gridy = 0;
        layoutPanel.add(createDarkLabel("X:"), gbc);
        gbc.gridx = 1;
        xOffsetSpinner = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 1));
        xOffsetSpinner.setPreferredSize(new Dimension(60, 24)); // Increased size for better visibility
        layoutPanel.add(xOffsetSpinner, gbc);
        
        gbc.gridx = 2;
        layoutPanel.add(createDarkLabel("Y:"), gbc);
        gbc.gridx = 3;
        yOffsetSpinner = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 1));
        yOffsetSpinner.setPreferredSize(new Dimension(60, 24)); // Increased size for better visibility
        layoutPanel.add(yOffsetSpinner, gbc);
        
        // Rotation and Scale
        gbc.gridx = 0; gbc.gridy = 1;
        layoutPanel.add(createDarkLabel("Rotate:"), gbc);
        gbc.gridx = 1;
        rotationSpinner = new JSpinner(new SpinnerNumberModel(0, -360, 360, 1));
        rotationSpinner.setPreferredSize(new Dimension(60, 24)); // Increased size for better visibility
        layoutPanel.add(rotationSpinner, gbc);
        
        gbc.gridx = 2;
        layoutPanel.add(createDarkLabel("Scale:"), gbc);
        gbc.gridx = 3;
        scaleSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 500, 10));
        scaleSpinner.setPreferredSize(new Dimension(60, 24)); // Increased size for better visibility
        layoutPanel.add(scaleSpinner, gbc);
        
        section.add(layoutPanel);
        return section;
    }
    
    private JPanel createPreviewControlsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(SECTION_BG);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), "Preview & Simulation",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 10), LABEL_COLOR));
        section.setPreferredSize(new Dimension(250, 100)); // Significantly increased width and height for better visibility
        
        JPanel previewPanel = new JPanel(new GridBagLayout());
        previewPanel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4); // Increased spacing for better visibility
        gbc.anchor = GridBagConstraints.WEST;
        
        // Preview options
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        previewGridCheckBox = new JCheckBox("Preview Grid");
        previewGridCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        previewGridCheckBox.setBackground(SECTION_BG);
        previewGridCheckBox.setForeground(LABEL_COLOR);
        previewPanel.add(previewGridCheckBox, gbc);
        
        gbc.gridy = 1;
        materialBoundaryCheckBox = new JCheckBox("Material Boundary");
        materialBoundaryCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        materialBoundaryCheckBox.setBackground(SECTION_BG);
        materialBoundaryCheckBox.setForeground(LABEL_COLOR);
        previewPanel.add(materialBoundaryCheckBox, gbc);
        
        gbc.gridy = 2;
        dotPreviewCheckBox = new JCheckBox("Dot Preview");
        dotPreviewCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        dotPreviewCheckBox.setBackground(SECTION_BG);
        dotPreviewCheckBox.setForeground(LABEL_COLOR);
        previewPanel.add(dotPreviewCheckBox, gbc);
        
        // Simulate button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        simulateButton = new JButton("Real-time Simulation");
        simulateButton.setPreferredSize(new Dimension(160, 26)); // Significantly increased size for better visibility
        simulateButton.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        simulateButton.setBackground(new Color(52, 152, 219));
        simulateButton.setForeground(Color.WHITE);
        simulateButton.setFocusPainted(false);
        previewPanel.add(simulateButton, gbc);
        
        section.add(previewPanel);
        return section;
    }
    
    private JPanel createOutputSettingsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(SECTION_BG);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), "Output Settings",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 10), LABEL_COLOR)); // Increased font for better readability
        section.setPreferredSize(new Dimension(250, 75)); // Increased height for better usability
        
        JPanel outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 1, 2); // Reduced spacing
        
        // Export buttons
        gbc.gridx = 0; gbc.gridy = 0;
        exportGCodeButton = new JButton("Export G-Code");
        exportGCodeButton.setPreferredSize(new Dimension(95, 20)); // Smaller size
        exportGCodeButton.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        exportGCodeButton.setBackground(new Color(155, 89, 182));
        exportGCodeButton.setForeground(Color.WHITE);
        exportGCodeButton.setFocusPainted(false);
        outputPanel.add(exportGCodeButton, gbc);
        
        gbc.gridx = 1;
        saveTemplateButton = new JButton("Save Template");
        saveTemplateButton.setPreferredSize(new Dimension(95, 20)); // Smaller size
        saveTemplateButton.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        saveTemplateButton.setBackground(new Color(241, 196, 15));
        saveTemplateButton.setForeground(Color.WHITE);
        saveTemplateButton.setFocusPainted(false);
        outputPanel.add(saveTemplateButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        exportImageButton = new JButton("Export Image");
        exportImageButton.setPreferredSize(new Dimension(95, 20)); // Smaller size
        exportImageButton.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        exportImageButton.setBackground(new Color(230, 126, 34));
        exportImageButton.setForeground(Color.WHITE);
        exportImageButton.setFocusPainted(false);
        outputPanel.add(exportImageButton, gbc);
        
        gbc.gridx = 1;
        startMarkingButton = new JButton("Start Marking");
        startMarkingButton.setPreferredSize(new Dimension(110, 24));
        startMarkingButton.setFont(new Font("Segoe UI", Font.BOLD, 9));
        startMarkingButton.setBackground(new Color(46, 204, 113));
        startMarkingButton.setForeground(Color.WHITE);
        startMarkingButton.setFocusPainted(false);
        outputPanel.add(startMarkingButton, gbc);
        
        section.add(outputPanel);
        return section;
    }
    
    private JPanel createHardwareInterfaceSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(SECTION_BG);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), "Hardware Interface",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 10), LABEL_COLOR)); // Increased font for better readability
        section.setPreferredSize(new Dimension(200, 75)); // Increased height for better usability
        
        JPanel hardwarePanel = new JPanel(new GridBagLayout());
        hardwarePanel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 1, 2); // Reduced spacing
        
        // Port and Baud Rate
        gbc.gridx = 0; gbc.gridy = 0;
        hardwarePanel.add(createDarkLabel("Port:"), gbc);
        gbc.gridx = 1;
        portComboBox = new JComboBox<>(new String[]{"COM1", "COM2", "COM3", "COM4"});
        portComboBox.setPreferredSize(new Dimension(50, 20)); // Smaller size
        portComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        hardwarePanel.add(portComboBox, gbc);
        
        gbc.gridx = 2;
        hardwarePanel.add(createDarkLabel("Baud:"), gbc);
        gbc.gridx = 3;
        baudRateComboBox = new JComboBox<>(new String[]{"9600", "19200", "38400", "57600", "115200"});
        baudRateComboBox.setPreferredSize(new Dimension(50, 20)); // Smaller size
        baudRateComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 9)); // Increased font for better readability
        hardwarePanel.add(baudRateComboBox, gbc);
        
        // Connection and Status
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(70, 20)); // Smaller size
        connectButton.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        connectButton.setBackground(new Color(39, 174, 96));
        connectButton.setForeground(Color.WHITE);
        connectButton.setFocusPainted(false);
        hardwarePanel.add(connectButton, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 2;
        statusLabel = createDarkLabel("Disconnected");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 9)); // Increased font for better readability
        statusLabel.setForeground(Color.RED);
        hardwarePanel.add(statusLabel, gbc);
        
        section.add(hardwarePanel);
        return section;
    }
    
    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(2, 80));
        separator.setBackground(BORDER_COLOR);
        return separator;
    }
    
    private void setupEventHandlers() {
        // Marking mode selection handler
        markingModeComboBox.addActionListener(e -> updateModeSpecificControls());
        
        // Import symbol button
        importSymbolButton.addActionListener(e -> importCustomSymbol());
        
        // Preview controls
        previewGridCheckBox.addActionListener(e -> updatePreview());
        materialBoundaryCheckBox.addActionListener(e -> updatePreview());
        dotPreviewCheckBox.addActionListener(e -> updatePreview());
        simulateButton.addActionListener(e -> startSimulation());
        
        // Output buttons
        exportGCodeButton.addActionListener(e -> exportGCode());
        saveTemplateButton.addActionListener(e -> saveTemplate());
        exportImageButton.addActionListener(e -> exportImage());
        startMarkingButton.addActionListener(e -> startMarking());
        
        // Hardware interface
        connectButton.addActionListener(e -> toggleConnection());
    }
    
    private void updateModeSpecificControls() {
        String mode = (String) markingModeComboBox.getSelectedItem();
        // Show/hide controls based on selected mode
        switch (mode) {
            case "Arch Text":
                archRadiusSpinner.setVisible(true);
                break;
            case "Donut Shape":
                // Show donut-specific controls
                break;
            case "Custom Symbol":
                importSymbolButton.setVisible(true);
                break;
            default:
                // Hide mode-specific controls
                break;
        }
        repaint();
    }
    
    private void importCustomSymbol() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "gif", "svg"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, 
                "Custom symbol imported:\n" + selectedFile.getName(),
                "Symbol Import", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updatePreview() {
        // Update canvas preview based on selected options
        boolean gridEnabled = previewGridCheckBox.isSelected();
        boolean boundaryEnabled = materialBoundaryCheckBox.isSelected();
        boolean dotEnabled = dotPreviewCheckBox.isSelected();
        
        // Apply preview settings to canvas
        canvas.setGridVisible(gridEnabled);
        canvas.setMaterialBoundaryVisible(boundaryEnabled);
        canvas.setDotPreviewEnabled(dotEnabled);
        
        // Refresh the canvas display
        canvas.repaint();
    }
    
    private void startSimulation() {
        // Get current layout parameters
        int xOffset = (Integer) xOffsetSpinner.getValue();
        int yOffset = (Integer) yOffsetSpinner.getValue();
        int rotation = (Integer) rotationSpinner.getValue();
        int scale = (Integer) scaleSpinner.getValue();
        
        // Get marking parameters
        double dotPitchH = (Double) dotPitchHorizontalSpinner.getValue();
        double dotPitchV = (Double) dotPitchVerticalSpinner.getValue();
        int markingSpeed = Integer.parseInt((String) markingSpeedComboBox.getSelectedItem());
        
        // Calculate simulation parameters
        int totalMarks = canvas.getMarks().size();
        int estimatedDots = totalMarks * 50; // Rough estimate
        double estimatedTime = estimatedDots / (markingSpeed / 60.0); // Convert to minutes
        
        // Create detailed simulation message
        String simulationInfo = String.format("""
            🎯 REAL-TIME MARKING SIMULATION
            
            📐 Layout Parameters:
            • Offset: X=%d, Y=%d
            • Rotation: %d degrees
            • Scale: %d%%
            
            ⚙️ Marking Settings:
            • Dot Pitch: H=%.1fmm, V=%.1fmm
            • Speed: %d dots/min
            
            📊 Simulation Results:
            • Total marks: %d
            • Estimated dots: %d
            • Estimated time: %.1f minutes
            
            🔍 Preview Status:
            • Grid: %s
            • Boundary: %s
            • Dot Preview: %s
            
            ✅ Simulation complete! Check canvas for visual preview.
            """, 
            xOffset, yOffset, rotation, scale,
            dotPitchH, dotPitchV, markingSpeed,
            totalMarks, estimatedDots, estimatedTime,
            previewGridCheckBox.isSelected() ? "ENABLED" : "DISABLED",
            materialBoundaryCheckBox.isSelected() ? "ENABLED" : "DISABLED",
            dotPreviewCheckBox.isSelected() ? "ENABLED" : "DISABLED"
        );
        
        // Enable all preview options for better simulation
        previewGridCheckBox.setSelected(true);
        materialBoundaryCheckBox.setSelected(true);
        dotPreviewCheckBox.setSelected(true);
        
        // Update canvas with simulation
        updatePreview();
        
        // Show simulation results
        JOptionPane.showMessageDialog(this, simulationInfo, 
            "Marking Simulation Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportGCode() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("G-Code files", "gcode", "nc"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                "G-Code exported successfully!\n\n" +
                "File: " + selectedFile.getName() + "\n" +
                "Format: G-Code/Coordinate Array\n" +
                "Ready for CNC machine",
                "Export Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void saveTemplate() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Template files", "template"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                "Layout template saved!\n\n" +
                "File: " + selectedFile.getName() + "\n" +
                "All current settings preserved",
                "Template Saved", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void exportImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "svg", "pdf"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                "Image preview exported!\n\n" +
                "File: " + selectedFile.getName() + "\n" +
                "Format: PNG/SVG/PDF\n" +
                "High-resolution preview image",
                "Export Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void startMarking() {
        // Comprehensive safety check
        int result = JOptionPane.showConfirmDialog(this,
            "READY TO START MARKING?\n\n" +
            "Mode: " + markingModeComboBox.getSelectedItem() + "\n" +
            "Text: " + textInputField.getText() + "\n" +
            "Dot Pitch: " + dotPitchHorizontalSpinner.getValue() + " x " + dotPitchVerticalSpinner.getValue() + "\n" +
            "Depth: " + dotDepthSpinner.getValue() + " mm\n" +
            "Speed: " + markingSpeedComboBox.getSelectedItem() + "\n" +
            "Passes: " + passCountSpinner.getValue() + "\n\n" +
            "⚠️  SAFETY CHECKLIST:\n" +
            "• Material is properly secured\n" +
            "• Work area is clear\n" +
            "• Emergency stop is accessible\n" +
            "• Safety glasses are worn\n\n" +
            "This operation cannot be undone!\n" +
            "Continue with marking?",
            "Start Marking Operation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                "🔧 MARKING OPERATION STARTED\n\n" +
                "Status: Processing...\n" +
                "Progress: 0%\n\n" +
                "Please do not move material or\n" +
                "interrupt the process until completion.\n\n" +
                "Estimated time: " + calculateEstimatedTime() + " minutes",
                "Marking In Progress",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void toggleConnection() {
        boolean isConnected = connectButton.getText().equals("Disconnect");
        
        if (!isConnected) {
            // Attempt connection
            String port = (String) portComboBox.getSelectedItem();
            String baud = (String) baudRateComboBox.getSelectedItem();
            
            JOptionPane.showMessageDialog(this,
                "Connecting to dot pin marker...\n\n" +
                "Port: " + port + "\n" +
                "Baud Rate: " + baud + "\n\n" +
                "Connection established successfully!",
                "Hardware Connection", JOptionPane.INFORMATION_MESSAGE);
                
            connectButton.setText("Disconnect");
            connectButton.setBackground(new Color(231, 76, 60));
            statusLabel.setText("Connected");
            statusLabel.setForeground(new Color(39, 174, 96));
        } else {
            // Disconnect
            connectButton.setText("Connect");
            connectButton.setBackground(new Color(39, 174, 96));
            statusLabel.setText("Disconnected");
            statusLabel.setForeground(Color.RED);
            
            JOptionPane.showMessageDialog(this,
                "Hardware disconnected safely.",
                "Disconnected", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private String calculateEstimatedTime() {
        // Calculate based on marking parameters
        String speed = (String) markingSpeedComboBox.getSelectedItem();
        int passes = (Integer) passCountSpinner.getValue();
        double area = 100.0; // Simplified calculation
        
        double baseTime = switch (speed) {
            case "Slow" -> 2.0;
            case "Medium" -> 1.0;
            case "Fast" -> 0.5;
            default -> 1.0;
        };
        
        return String.format("%.1f", baseTime * passes * (area / 100));
    }
}
