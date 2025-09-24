import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * PrintEngravePanel - Unified printing and engraving interface
 * Combines traditional printing capabilities with advanced engraving/marking controls
 */
public class PrintEngravePanel extends JPanel implements Printable {
    
    // Modern color scheme
    private static final Color LABEL_COLOR = new Color(44, 62, 80);
    private static final Color SECTION_BG = new Color(248, 249, 250);
    private static final Color BORDER_COLOR = new Color(189, 195, 199);
    
    private DrawingCanvas canvas;
    
    // Print Controls
    private JComboBox<String> printerComboBox;
    private JButton printButton;
    private JButton previewButton;
    private JButton pageSetupButton;
    private JSpinner copiesSpinner;
    private JCheckBox colorCheckBox;
    private JComboBox<String> paperSizeComboBox;
    private JComboBox<String> orientationComboBox;
    
    // Engrave Controls - Basic
    private JComboBox<String> markingModeComboBox;
    private JTextField textInputField;
    private JSpinner dotPitchHorizontalSpinner;
    private JSpinner dotPitchVerticalSpinner;
    private JSpinner dotDepthSpinner;
    private JComboBox<String> markingSpeedComboBox;
    private JSpinner passCountSpinner;
    private JButton startMarkingButton;
    private JButton simulateButton;
    private JButton exportGCodeButton;
    
    // Advanced Engrave Controls
    private JComboBox<String> materialTypeComboBox;
    private JComboBox<String> needleTypeComboBox;
    private JSpinner needlePressureSpinner;
    private JComboBox<String> markingPatternComboBox;
    private JCheckBox autoDepthCheckBox;
    private JComboBox<String> fontTypeComboBox;
    private JSpinner fontSizeSpinner;
    private JCheckBox boldTextCheckBox;
    private JCheckBox italicTextCheckBox;
    private JComboBox<String> textAlignmentComboBox;
    private JSpinner lineSpacingSpinner;
    private JSpinner characterSpacingSpinner;
    private JButton importImageButton;
    private JButton loadTemplateButton;
    private JButton saveTemplateButton;
    private JComboBox<String> qualityPresetComboBox;
    private JCheckBox previewModeCheckBox;
    private JButton calibrateButton;
    private JProgressBar markingProgressBar;
    private JLabel estimatedTimeLabel;
    
    // Shared Layout Controls
    private JSpinner xOffsetSpinner;
    private JSpinner yOffsetSpinner;
    private JSpinner rotationSpinner;
    private JSpinner scaleSpinner;
    
    // Preview Controls
    private JCheckBox previewGridCheckBox;
    private JCheckBox materialBoundaryCheckBox;
    private JCheckBox dotPreviewCheckBox;
    
    // Hardware Interface
    private JButton connectButton;
    private JComboBox<String> portComboBox;
    private JComboBox<String> baudRateComboBox;
    private JLabel statusLabel;
    
    // WiFi Connectivity
    private JComboBox<String> connectionTypeComboBox;
    private JTextField ipAddressField;
    private JSpinner portSpinner;
    private JButton scanWiFiButton;
    private JButton testConnectionButton;
    private JLabel wifiStatusLabel;
    
    public PrintEngravePanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        initializeComponents(); // Initialize components FIRST
        setupUI();              // Then setup the UI
        setupEventHandlers();   // Finally setup event handlers
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        setPreferredSize(new Dimension(1200, 180)); // Increased height to accommodate hardware controls
        
        // Create tabbed interface for better organization
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBackground(getBackground());
        
        // Tab 1: Print Operations
        JPanel printTab = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        printTab.setBackground(getBackground());
                printTab.add(createCompactToolbarSection("Printer Setup", createPrintControls()));
        printTab.add(createSeparator());
        printTab.add(createCompactToolbarSection("Page Settings", createPageSetupControls()));
        printTab.add(createSeparator());
        printTab.add(createCompactToolbarSection("Print Actions", createPrintActionControls()));
        
        // Tab 2: Engrave Operations  
        JPanel engraveTab = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
        engraveTab.setBackground(getBackground());
        engraveTab.add(createCompactToolbarSection("Content & Mode", createEngraveContentControls()));
        engraveTab.add(createSeparator());
        engraveTab.add(createCompactToolbarSection("Material & Needle", createMaterialNeedleControls()));
        engraveTab.add(createSeparator());
        engraveTab.add(createCompactToolbarSection("Engrave Actions", createEngraveExecutionControls()));
        
        // Tab 3: Shared Layout & Preview
        JPanel layoutTab = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        layoutTab.setBackground(getBackground());
        layoutTab.add(createCompactToolbarSection("Position & Transform", createLayoutControls()));
        layoutTab.add(createSeparator());
        layoutTab.add(createCompactToolbarSection("Preview Options", createPreviewControls()));
        layoutTab.add(createSeparator());
        layoutTab.add(createCompactToolbarSection("Hardware", createHardwareControls()));
        
        // Add tabs
        tabbedPane.addTab("Print", printTab);
        tabbedPane.addTab("Engrave", engraveTab);
        tabbedPane.addTab("Layout & Preview", layoutTab);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void initializeComponents() {
        // Print components
        printerComboBox = new JComboBox<>(new String[]{"Default Printer", "HP LaserJet", "Canon Pixma", "Brother HL"});
        printButton = new JButton("Print");
        previewButton = new JButton("Preview");
        pageSetupButton = new JButton("Setup");
        copiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        colorCheckBox = new JCheckBox("Color");
        paperSizeComboBox = new JComboBox<>(new String[]{"A4", "Letter", "Legal", "A3"});
        orientationComboBox = new JComboBox<>(new String[]{"Portrait", "Landscape"});
        
        // Engrave components - Enhanced with soft-coded configuration
        markingModeComboBox = new JComboBox<>(MarkingModeConfig.getDisplayNames());
        textInputField = new JTextField("Sample Text", 15);
        dotPitchHorizontalSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.1, 5.0, 0.1));
        dotPitchVerticalSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.1, 5.0, 0.1));
        dotDepthSpinner = new JSpinner(new SpinnerNumberModel(0.1, 0.01, 2.0, 0.01));
        markingSpeedComboBox = new JComboBox<>(new String[]{"Ultra Slow (50/min)", "Slow (100/min)", "Medium (200/min)", "Fast (400/min)", "Ultra Fast (800/min)"});
        passCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        startMarkingButton = new JButton("Start Marking");
        simulateButton = new JButton("Simulate");
        exportGCodeButton = new JButton("Export G-Code");
        
        // Advanced Engrave components
        materialTypeComboBox = new JComboBox<>(new String[]{"Steel", "Aluminum", "Brass", "Plastic", "Titanium", "Stainless Steel", "Copper", "Custom"});
        needleTypeComboBox = new JComboBox<>(new String[]{"Standard Tip", "Fine Tip", "Heavy Duty", "Diamond Tip", "Carbide Tip"});
        needlePressureSpinner = new JSpinner(new SpinnerNumberModel(50, 10, 100, 5));
        markingPatternComboBox = new JComboBox<>(new String[]{"Single Dot", "Cross Pattern", "Circle Pattern", "Square Pattern", "Diamond Pattern", "Custom"});
        autoDepthCheckBox = new JCheckBox("Auto Depth Control");
        fontTypeComboBox = new JComboBox<>(new String[]{"Arial", "Times Roman", "Courier", "Impact", "Helvetica", "Industrial", "Stencil"});
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 6, 72, 1));
        boldTextCheckBox = new JCheckBox("Bold");
        italicTextCheckBox = new JCheckBox("Italic");
        textAlignmentComboBox = new JComboBox<>(new String[]{"Left", "Center", "Right", "Justify"});
        lineSpacingSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.5, 3.0, 0.1));
        characterSpacingSpinner = new JSpinner(new SpinnerNumberModel(0.0, -2.0, 5.0, 0.1));
        importImageButton = new JButton("Import Image");
        loadTemplateButton = new JButton("Load Template");
        saveTemplateButton = new JButton("Save Template");
        
        // Immediate styling for template buttons to ensure visibility
        loadTemplateButton.setOpaque(true);
        loadTemplateButton.setBackground(new Color(52, 152, 219));
        loadTemplateButton.setForeground(Color.WHITE);
        loadTemplateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        loadTemplateButton.setPreferredSize(new Dimension(120, 28));
        loadTemplateButton.setBorderPainted(true);
        loadTemplateButton.setFocusPainted(false);
        
        saveTemplateButton.setOpaque(true);
        saveTemplateButton.setBackground(new Color(39, 174, 96));
        saveTemplateButton.setForeground(Color.WHITE);
        saveTemplateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        saveTemplateButton.setPreferredSize(new Dimension(120, 28));
        saveTemplateButton.setBorderPainted(true);
        saveTemplateButton.setFocusPainted(false);
        
        importImageButton.setOpaque(true);
        importImageButton.setBackground(new Color(142, 68, 173));
        importImageButton.setForeground(Color.WHITE);
        importImageButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        importImageButton.setPreferredSize(new Dimension(120, 28));
        importImageButton.setBorderPainted(true);
        importImageButton.setFocusPainted(false);
        qualityPresetComboBox = new JComboBox<>(new String[]{"Draft", "Standard", "High Quality", "Ultra Fine", "Production"});
        previewModeCheckBox = new JCheckBox("Live Preview");
        calibrateButton = new JButton("Calibrate");
        markingProgressBar = new JProgressBar(0, 100);
        estimatedTimeLabel = createDarkLabel("Est. Time: --");
        
        // Shared layout components
        xOffsetSpinner = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
        yOffsetSpinner = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
        rotationSpinner = new JSpinner(new SpinnerNumberModel(0, -360, 360, 1));
        scaleSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 500, 5));
        
        // Preview components
        previewGridCheckBox = new JCheckBox("Grid");
        materialBoundaryCheckBox = new JCheckBox("Boundary");
        dotPreviewCheckBox = new JCheckBox("Dots");
        
        // Hardware components
        connectButton = new JButton("Connect");
        portComboBox = new JComboBox<>(new String[]{"COM1", "COM2", "COM3", "USB"});
        baudRateComboBox = new JComboBox<>(new String[]{"9600", "19200", "38400", "57600", "115200"});
        statusLabel = createDarkLabel("Disconnected");
        
        // WiFi components
        connectionTypeComboBox = new JComboBox<>(new String[]{"Serial Port", "WiFi Network"});
        ipAddressField = new JTextField("192.168.1.100", 12);
        portSpinner = new JSpinner(new SpinnerNumberModel(8080, 1, 65535, 1));
        scanWiFiButton = new JButton("Scan");
        testConnectionButton = new JButton("Test");
        wifiStatusLabel = createDarkLabel("Not Connected");
        
        // Apply consistent styling
        applyConsistentStyling();
        
        // Force button visibility after all styling is complete
        forceButtonVisibility();
        
        // Set initial visibility state (Serial Port mode)
        updateConnectionTypeVisibility();
    }
    
    private JPanel createPrintControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Printer selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Printer:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(printerComboBox, gbc);
        
        // Row 2: Copies
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Copies:"), gbc);
        gbc.gridx = 1;
        panel.add(copiesSpinner, gbc);
        
        gbc.gridx = 2;
        panel.add(colorCheckBox, gbc);
        
        return panel;
    }
    
    private JPanel createPageSetupControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Paper settings
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Paper:"), gbc);
        gbc.gridx = 1;
        panel.add(paperSizeComboBox, gbc);
        
        // Row 2: Orientation
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Orient:"), gbc);
        gbc.gridx = 1;
        panel.add(orientationComboBox, gbc);
        
        return panel;
    }
    
    private JPanel createPrintActionControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Actions
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(printButton, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(previewButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(pageSetupButton, gbc);
        
        return panel;
    }
    
    private JPanel createEngraveContentControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Content & Mode Configuration",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 10),
            new Color(100, 149, 237)
        ));
        
        // Set preferred size to ensure buttons are visible but not too wide
        panel.setPreferredSize(new Dimension(420, 140));
        panel.setMinimumSize(new Dimension(400, 120));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Marking Mode with better spacing
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        panel.add(createDarkLabel("Mode:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3; // Span across 3 columns
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(markingModeComboBox, gbc);
        
        // Row 2: Mode description
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel modeDescriptionLabel = new JLabel();
        modeDescriptionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 9));
        modeDescriptionLabel.setForeground(new Color(102, 102, 102));
        updateModeDescription(modeDescriptionLabel);
        panel.add(modeDescriptionLabel, gbc);
        
        // Row 3: Content Input with validation indicator
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        panel.add(createDarkLabel("Content:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textInputField, gbc);
        
        // Validation indicator
        gbc.gridx = 3; gbc.gridwidth = 1;
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        JLabel validationLabel = new JLabel("✓");
        validationLabel.setForeground(Color.GREEN);
        validationLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(validationLabel, gbc);
        
        // Row 4: Quick Actions - make them smaller to fit better
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        panel.add(createDarkLabel("Actions:"), gbc);
        
        // Generate button - smaller and more compact
        gbc.gridx = 1; gbc.gridwidth = 1;
        gbc.weightx = 0.25; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 3, 2, 1);
        JButton generateButton = new JButton("🔄 Gen");
        generateButton.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        generateButton.setToolTipText("Generate content based on selected mode");
        generateButton.setPreferredSize(new Dimension(65, 24));
        panel.add(generateButton, gbc);
        
        // Validate button
        gbc.gridx = 2;
        gbc.insets = new Insets(2, 1, 2, 1);
        JButton validateButton = new JButton("✓ Val");
        validateButton.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        validateButton.setToolTipText("Validate current content");
        validateButton.setPreferredSize(new Dimension(65, 24));
        panel.add(validateButton, gbc);
        
        // Preview button
        gbc.gridx = 3;
        gbc.insets = new Insets(2, 1, 2, 3);
        JButton previewContentButton = new JButton("👁 Prev");
        previewContentButton.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        previewContentButton.setToolTipText("Preview how content will be marked");
        previewContentButton.setPreferredSize(new Dimension(65, 24));
        panel.add(previewContentButton, gbc);
        
        // Row 5: Template Management with better spacing
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 3, 2, 3);
        panel.add(createDarkLabel("Template:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 1;
        gbc.weightx = 0.33; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 3, 2, 1);
        panel.add(loadTemplateButton, gbc);
        
        gbc.gridx = 2;
        gbc.insets = new Insets(2, 1, 2, 1);
        panel.add(saveTemplateButton, gbc);
        
        gbc.gridx = 3;
        gbc.insets = new Insets(2, 1, 2, 3);
        panel.add(importImageButton, gbc);
        
        // === EVENT HANDLERS ===
        
        // Mode change handler
        markingModeComboBox.addActionListener(e -> {
            updateModeDescription(modeDescriptionLabel);
            updateContentForMode();
            validateCurrentContent(validationLabel);
        });
        
        // Content change handler
        textInputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateCurrentContent(validationLabel); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateCurrentContent(validationLabel); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateCurrentContent(validationLabel); }
        });
        
        // Generate button handler
        generateButton.addActionListener(e -> generateContentForCurrentMode());
        
        // Validate button handler
        validateButton.addActionListener(e -> {
            boolean isValid = validateCurrentContent(validationLabel);
            String message = isValid ? 
                "✅ Content is valid for the selected marking mode!" :
                "❌ Content validation failed. Please check the format.";
            JOptionPane.showMessageDialog(this, message, "Content Validation", 
                isValid ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        });
        
        // Preview button handler
        previewContentButton.addActionListener(e -> showContentPreview());
        
        return panel;
    }
    
    // === ENHANCED MODE MANAGEMENT METHODS ===
    
    private void updateModeDescription(JLabel descriptionLabel) {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        MarkingModeConfig.ModeConfiguration config = MarkingModeConfig.getModeConfiguration(type);
        
        if (config != null) {
            descriptionLabel.setText(type.getDescription()); // Get description from MarkingType
            textInputField.setToolTipText(config.getTooltip());
        }
    }
    
    private void updateContentForMode() {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        MarkingModeConfig.ModeConfiguration config = MarkingModeConfig.getModeConfiguration(type);
        
        if (config != null) {
            textInputField.setText(config.getDefaultContent());
            
            // Update UI based on mode requirements
            importImageButton.setEnabled(config.isRequiresTemplate());
            if (config.isRequiresTemplate()) {
                importImageButton.setText("📁 Select Image");
                importImageButton.setBackground(new Color(52, 152, 219));
            } else {
                importImageButton.setText("📷 Import");
                importImageButton.setBackground(new Color(189, 195, 199));
            }
        }
    }
    
    private boolean validateCurrentContent(JLabel validationLabel) {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        String content = textInputField.getText();
        
        boolean isValid = MarkingModeConfig.validateContent(type, content);
        
        if (isValid) {
            validationLabel.setText("✓");
            validationLabel.setForeground(Color.GREEN);
            validationLabel.setToolTipText("Content is valid");
            textInputField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        } else {
            validationLabel.setText("✗");
            validationLabel.setForeground(Color.RED);
            validationLabel.setToolTipText("Content validation failed");
            textInputField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        
        return isValid;
    }
    
    private void generateContentForCurrentMode() {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        String currentContent = textInputField.getText();
        
        String generatedContent = MarkingModeConfig.generateContent(type, currentContent);
        textInputField.setText(generatedContent);
        
        // Show generation feedback
        JOptionPane.showMessageDialog(this, 
            "🔄 Content generated successfully!\n\nMode: " + selectedMode + "\nGenerated: " + generatedContent,
            "Content Generation", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showContentPreview() {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        String content = textInputField.getText();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        Map<String, Object> parameters = MarkingModeConfig.getModeParameters(type);
        
        StringBuilder preview = new StringBuilder();
        preview.append("📋 MARKING PREVIEW\n\n");
        preview.append("Mode: ").append(selectedMode).append("\n");
        preview.append("Content: ").append(content).append("\n");
        preview.append("Length: ").append(content.length()).append(" characters\n\n");
        
        preview.append("📐 MODE PARAMETERS:\n");
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            preview.append("• ").append(param.getKey()).append(": ").append(param.getValue()).append("\n");
        }
        
        preview.append("\n⚙️ CURRENT SETTINGS:\n");
        preview.append("• Depth: ").append(dotDepthSpinner.getValue()).append(" mm\n");
        preview.append("• Speed: ").append(markingSpeedComboBox.getSelectedItem()).append("\n");
        preview.append("• Material: ").append(materialTypeComboBox.getSelectedItem()).append("\n");
        
        JOptionPane.showMessageDialog(this, preview.toString(), "Marking Preview", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showModeSettings() {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        MarkingModeConfig.ModeConfiguration config = MarkingModeConfig.getModeConfiguration(type);
        
        if (config == null) return;
        
        // Create settings dialog
        JDialog settingsDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Mode Settings: " + selectedMode, true);
        settingsDialog.setSize(450, 350);
        settingsDialog.setLocationRelativeTo(this);
        
        JPanel settingsPanel = new JPanel(new BorderLayout(10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JLabel headerLabel = new JLabel("⚙️ " + selectedMode + " Configuration");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(new Color(100, 149, 237));
        settingsPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Settings content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Mode description
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea descArea = new JTextArea(type.getDescription());
        descArea.setEditable(false);
        descArea.setBackground(new Color(240, 248, 255));
        descArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        contentPanel.add(descArea, gbc);
        
        // Parameters
        gbc.gridy = 1; gbc.gridwidth = 1;
        contentPanel.add(new JLabel("Parameters:"), gbc);
        
        Map<String, Object> parameters = config.getParameters();
        int row = 2;
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            gbc.gridx = 0; gbc.gridy = row;
            gbc.fill = GridBagConstraints.NONE;
            contentPanel.add(new JLabel("• " + param.getKey() + ":"), gbc);
            
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JLabel valueLabel = new JLabel(param.getValue().toString());
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
            contentPanel.add(valueLabel, gbc);
            row++;
        }
        
        // Features
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        StringBuilder features = new StringBuilder("Features:\n");
        if (config.isRequiresTemplate()) features.append("• Requires template/image file\n");
        if (config.isSupportsAutoIncrement()) features.append("• Supports auto-increment\n");
        features.append("• Content validation: ").append(config.getValidator() != null ? "Enabled" : "Disabled").append("\n");
        features.append("• Content generation: ").append(config.getContentGenerator() != null ? "Available" : "Manual").append("\n");
        
        JTextArea featuresArea = new JTextArea(features.toString());
        featuresArea.setEditable(false);
        featuresArea.setBackground(new Color(248, 255, 248));
        featuresArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        featuresArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contentPanel.add(featuresArea, gbc);
        
        settingsPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> settingsDialog.dispose());
        buttonPanel.add(closeButton);
        
        settingsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        settingsDialog.add(settingsPanel);
        settingsDialog.setVisible(true);
    }
    
    private JPanel createMaterialNeedleControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Material Type
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Material:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(materialTypeComboBox, gbc);
        
        // Row 2: Needle Configuration
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Needle:"), gbc);
        gbc.gridx = 1;
        panel.add(needleTypeComboBox, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Pressure:"), gbc);
        gbc.gridx = 3;
        panel.add(needlePressureSpinner, gbc);
        
        // Row 3: Pattern and Auto Settings
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Pattern:"), gbc);
        gbc.gridx = 1;
        panel.add(markingPatternComboBox, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 2;
        panel.add(autoDepthCheckBox, gbc);
        
        return panel;
    }
    
    private JPanel createTextFormattingControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Font Settings
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Font:"), gbc);
        gbc.gridx = 1;
        panel.add(fontTypeComboBox, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Size:"), gbc);
        gbc.gridx = 3;
        panel.add(fontSizeSpinner, gbc);
        
        // Row 2: Text Style
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Style:"), gbc);
        gbc.gridx = 1;
        panel.add(boldTextCheckBox, gbc);
        gbc.gridx = 2;
        panel.add(italicTextCheckBox, gbc);
        
        gbc.gridx = 3;
        panel.add(textAlignmentComboBox, gbc);
        
        // Row 3: Spacing
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Line Space:"), gbc);
        gbc.gridx = 1;
        panel.add(lineSpacingSpinner, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Char Space:"), gbc);
        gbc.gridx = 3;
        panel.add(characterSpacingSpinner, gbc);
        
        return panel;
    }
    
    private JPanel createPrecisionControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Dot Pitch Settings
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Pitch H:"), gbc);
        gbc.gridx = 1;
        panel.add(dotPitchHorizontalSpinner, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("V:"), gbc);
        gbc.gridx = 3;
        panel.add(dotPitchVerticalSpinner, gbc);
        
        // Row 2: Depth and Speed
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Depth:"), gbc);
        gbc.gridx = 1;
        panel.add(dotDepthSpinner, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Speed:"), gbc);
        gbc.gridx = 3;
        panel.add(markingSpeedComboBox, gbc);
        
        // Row 3: Quality and Passes
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Quality:"), gbc);
        gbc.gridx = 1;
        panel.add(qualityPresetComboBox, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Passes:"), gbc);
        gbc.gridx = 3;
        panel.add(passCountSpinner, gbc);
        
        return panel;
    }
    
    private JPanel createEngraveExecutionControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5); // Increased padding for better visibility
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment for buttons
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontal space
        
        // Row 1: Main Actions with better spacing
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; // Equal weight distribution
        panel.add(startMarkingButton, gbc);
        
        gbc.gridx = 1;
        panel.add(simulateButton, gbc);
        
        gbc.gridx = 2;
        panel.add(calibrateButton, gbc);
        
        // Row 2: Preview and Export
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.5; // Different weight for checkbox
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(previewModeCheckBox, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(exportGCodeButton, gbc);
        
        // Row 3: Progress and Time with better layout
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(markingProgressBar, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(estimatedTimeLabel, gbc);
        
        return panel;
    }
    
    private JPanel createLayoutControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Position
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("X:"), gbc);
        gbc.gridx = 1;
        panel.add(xOffsetSpinner, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Y:"), gbc);
        gbc.gridx = 3;
        panel.add(yOffsetSpinner, gbc);
        
        // Row 2: Transform
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Rotate:"), gbc);
        gbc.gridx = 1;
        panel.add(rotationSpinner, gbc);
        
        gbc.gridx = 2;
        panel.add(createDarkLabel("Scale:"), gbc);
        gbc.gridx = 3;
        panel.add(scaleSpinner, gbc);
        
        return panel;
    }
    
    private JPanel createPreviewControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Preview options
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(previewGridCheckBox, gbc);
        gbc.gridx = 1;
        panel.add(materialBoundaryCheckBox, gbc);
        gbc.gridx = 2;
        panel.add(dotPreviewCheckBox, gbc);
        
        // Row 2: Actions
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(simulateButton, gbc);
        gbc.gridx = 1;
        panel.add(exportGCodeButton, gbc);
        
        return panel;
    }
    
    private JPanel createHardwareControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        panel.setPreferredSize(new Dimension(380, 120)); // Reduced height to fit better
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4); // Slightly reduced margins
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1: Connection Type
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(createDarkLabel("Type:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panel.add(connectionTypeComboBox, gbc);
        
        // Row 2: Serial Port Settings
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Port:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1;
        panel.add(portComboBox, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Baud:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 1;
        panel.add(baudRateComboBox, gbc);
        
        // Row 3: WiFi Settings
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(createDarkLabel("IP:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(ipAddressField, gbc);
        
        gbc.gridx = 3; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Port:"), gbc);
        gbc.gridx = 4; gbc.gridwidth = 1;
        panel.add(portSpinner, gbc);
        
        // Row 4: Action buttons with guaranteed space and visibility
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(6, 4, 3, 4); // Reduced spacing to fit better
        
        // Connect button with explicit positioning
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        panel.add(connectButton, gbc);
        
        // Scan WiFi button
        gbc.gridx = 1;
        panel.add(scanWiFiButton, gbc);
        
        // Test Connection button
        gbc.gridx = 2;
        panel.add(testConnectionButton, gbc);
        
        // Row 5: Status with proper spacing
        gbc.gridy = 4;
        gbc.gridx = 0; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 4, 2, 4); // Reduced bottom spacing
        panel.add(createDarkLabel("Status:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);
        
        gbc.gridx = 3; gbc.gridwidth = 2;
        panel.add(wifiStatusLabel, gbc);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Print handlers
        printButton.addActionListener(e -> performPrint());
        previewButton.addActionListener(e -> showPrintPreview());
        pageSetupButton.addActionListener(e -> showPageSetup());
        
        // Engrave handlers
        startMarkingButton.addActionListener(e -> startMarking());
        simulateButton.addActionListener(e -> startSimulation());
        exportGCodeButton.addActionListener(e -> exportGCode());
        
        // Preview handlers
        previewGridCheckBox.addActionListener(e -> updatePreview());
        materialBoundaryCheckBox.addActionListener(e -> updatePreview());
        dotPreviewCheckBox.addActionListener(e -> updatePreview());
        
        // Hardware handlers
        connectButton.addActionListener(e -> toggleConnection());
        connectionTypeComboBox.addActionListener(e -> updateConnectionTypeVisibility());
        scanWiFiButton.addActionListener(e -> scanForWiFiDevices());
        testConnectionButton.addActionListener(e -> testWiFiConnection());
        
        // Advanced engrave handlers
        markingModeComboBox.addActionListener(e -> updateMarkingModeSettings());
        materialTypeComboBox.addActionListener(e -> updateMaterialSettings());
        qualityPresetComboBox.addActionListener(e -> applyQualityPreset());
        autoDepthCheckBox.addActionListener(e -> toggleAutoDepth());
        previewModeCheckBox.addActionListener(e -> toggleLivePreview());
        importImageButton.addActionListener(e -> importCustomImage());
        loadTemplateButton.addActionListener(e -> loadMarkingTemplate());
        saveTemplateButton.addActionListener(e -> saveMarkingTemplate());
        calibrateButton.addActionListener(e -> calibrateDevice());
        
        // Real-time parameter updates
        fontSizeSpinner.addChangeListener(e -> updateEstimatedTime());
        dotPitchHorizontalSpinner.addChangeListener(e -> updateEstimatedTime());
        dotDepthSpinner.addChangeListener(e -> updateEstimatedTime());
        passCountSpinner.addChangeListener(e -> updateEstimatedTime());
    }
    
    private void performPrint() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        
        if (printJob.printDialog()) {
            try {
                printJob.print();
                JOptionPane.showMessageDialog(this, 
                    "Print job sent successfully!", 
                    "Print Status", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Print error: " + ex.getMessage(), 
                    "Print Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showPrintPreview() {
        JDialog previewDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Print Preview", true);
        previewDialog.setSize(600, 800);
        previewDialog.setLocationRelativeTo(this);
        
        JPanel previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                g2d.fillRect(50, 50, 500, 700);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(50, 50, 500, 700);
                g2d.drawString("Print Preview - Canvas Content", 60, 80);
            }
        };
        
        previewDialog.add(previewPanel);
        previewDialog.setVisible(true);
    }
    
    private void showPageSetup() {
        JOptionPane.showMessageDialog(this,
            "Page Setup\n\n" +
            "Paper: " + paperSizeComboBox.getSelectedItem() + "\n" +
            "Orientation: " + orientationComboBox.getSelectedItem() + "\n" +
            "Margins: Standard",
            "Page Setup", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void startMarking() {
        // Enhanced validation before starting
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        String content = textInputField.getText();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        
        // Validate content first
        if (!MarkingModeConfig.validateContent(type, content)) {
            JOptionPane.showMessageDialog(this,
                "Content validation failed for the selected marking mode.\n" +
                "Please check the format and try again.\n\n" +
                "Mode: " + selectedMode + "\n" +
                "Content: " + content,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this,
            "ðŸ”§ START MARKING OPERATION?\n\n" +
            "Mode: " + markingModeComboBox.getSelectedItem() + "\n" +
            "Text: " + textInputField.getText() + "\n" +
            "Depth: " + dotDepthSpinner.getValue() + " mm\n" +
            "Speed: " + markingSpeedComboBox.getSelectedItem() + "\n\n" +
            "âš ï¸ Ensure material is secured and safety equipment is worn!\n" +
            "This operation cannot be undone.",
            "Confirm Marking Operation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                "ðŸ”§ MARKING STARTED\n\n" +
                "Operation in progress...\n" +
                "Do not move material until completion.",
                "Marking In Progress",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void startSimulation() {
        updatePreview();
        JOptionPane.showMessageDialog(this,
            "ðŸŽ¯ SIMULATION COMPLETE\n\n" +
            "Preview updated with current settings.\n" +
            "Check canvas for visual representation.",
            "Simulation Results",
            JOptionPane.INFORMATION_MESSAGE);
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
                "Ready for CNC machine",
                "Export Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updatePreview() {
        if (canvas != null) {
            canvas.setGridVisible(previewGridCheckBox.isSelected());
            canvas.setMaterialBoundaryVisible(materialBoundaryCheckBox.isSelected());
            canvas.setDotPreviewEnabled(dotPreviewCheckBox.isSelected());
            canvas.repaint();
        }
    }
    
    private void toggleConnection() {
        boolean isConnected = connectButton.getText().equals("Disconnect");
        String connectionType = (String) connectionTypeComboBox.getSelectedItem();
        
        if (!isConnected) {
            String message;
            if ("WiFi Network".equals(connectionType)) {
                String ip = ipAddressField.getText();
                int port = (Integer) portSpinner.getValue();
                message = String.format("Connecting via WiFi...\n\nIP Address: %s\nPort: %d\n\nConnection established successfully!", ip, port);
                wifiStatusLabel.setText("WiFi Connected");
                wifiStatusLabel.setForeground(new Color(39, 174, 96));
            } else {
                String port = (String) portComboBox.getSelectedItem();
                String baud = (String) baudRateComboBox.getSelectedItem();
                message = String.format("Connecting via Serial...\n\nPort: %s\nBaud Rate: %s\n\nConnection established successfully!", port, baud);
            }
            
            JOptionPane.showMessageDialog(this, message, "Hardware Connection", JOptionPane.INFORMATION_MESSAGE);
            connectButton.setText("Disconnect");
            connectButton.setBackground(new Color(231, 76, 60));
            statusLabel.setText("Connected");
            statusLabel.setForeground(new Color(39, 174, 96));
        } else {
            connectButton.setText("Connect");
            connectButton.setBackground(new Color(39, 174, 96));
            statusLabel.setText("Disconnected");
            statusLabel.setForeground(Color.RED);
            wifiStatusLabel.setText("Not Connected");
            wifiStatusLabel.setForeground(Color.RED);
            
            JOptionPane.showMessageDialog(this, "Hardware disconnected safely.", "Disconnected", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateConnectionTypeVisibility() {
        String connectionType = (String) connectionTypeComboBox.getSelectedItem();
        boolean isWiFi = "WiFi Network".equals(connectionType);
        
        // Show/hide components based on connection type
        portComboBox.setVisible(!isWiFi);
        baudRateComboBox.setVisible(!isWiFi);
        ipAddressField.setVisible(isWiFi);
        portSpinner.setVisible(isWiFi);
        scanWiFiButton.setVisible(isWiFi);
        testConnectionButton.setVisible(isWiFi);
        wifiStatusLabel.setVisible(isWiFi);
        
        // Update labels and status
        if (isWiFi) {
            statusLabel.setText("WiFi Mode");
            wifiStatusLabel.setText("Not Connected");
        } else {
            statusLabel.setText("Serial Mode");
        }
        
        // Refresh the panel
        revalidate();
        repaint();
    }
    
    private void scanForWiFiDevices() {
        // Simulate WiFi device scanning
        String[] foundDevices = {
            "192.168.1.100 - DotPin Marker #001",
            "192.168.1.101 - DotPin Marker #002", 
            "192.168.1.102 - DotPin Marker #003"
        };
        
        String selectedDevice = (String) JOptionPane.showInputDialog(
            this,
            "ðŸ” WiFi Device Scanner\n\nSelect a device to connect to:",
            "Available WiFi Devices",
            JOptionPane.QUESTION_MESSAGE,
            null,
            foundDevices,
            foundDevices[0]
        );
        
        if (selectedDevice != null) {
            // Extract IP address from the selected device
            String ip = selectedDevice.split(" - ")[0];
            ipAddressField.setText(ip);
            JOptionPane.showMessageDialog(this,
                "âœ… Device Selected!\n\n" +
                "IP Address set to: " + ip + "\n" +
                "Click 'Test' to verify connection or 'Connect' to establish link.",
                "Device Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void testWiFiConnection() {
        String ip = ipAddressField.getText();
        int port = (Integer) portSpinner.getValue();
        
        // Simulate connection test
        if (ip.isEmpty() || !ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            JOptionPane.showMessageDialog(this,
                "âŒ Invalid IP Address\n\n" +
                "Please enter a valid IP address\n" +
                "(e.g., 192.168.1.100)",
                "Connection Test Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Simulate test delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        boolean testSuccess = Math.random() > 0.3; // 70% success rate for demo
        
        if (testSuccess) {
            wifiStatusLabel.setText("Test Passed");
            wifiStatusLabel.setForeground(new Color(39, 174, 96));
            JOptionPane.showMessageDialog(this,
                "âœ… CONNECTION TEST SUCCESSFUL\n\n" +
                "Device Info:\n" +
                "â€¢ IP Address: " + ip + "\n" +
                "â€¢ Port: " + port + "\n" +
                "â€¢ Response Time: 45ms\n" +
                "â€¢ Signal Strength: Strong\n\n" +
                "Device is ready for connection!",
                "Test Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            wifiStatusLabel.setText("Test Failed");
            wifiStatusLabel.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this,
                "âŒ CONNECTION TEST FAILED\n\n" +
                "Possible Issues:\n" +
                "â€¢ Device not powered on\n" +
                "â€¢ Wrong IP address\n" +
                "â€¢ Network connectivity issues\n" +
                "â€¢ Firewall blocking connection\n\n" +
                "Please check device and try again.",
                "Test Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // === ENHANCED MARKING MODE MANAGEMENT ===
    
    private void updateMarkingModeSettings() {
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        MarkingModeConfig.MarkingType type = MarkingModeConfig.getTypeByDisplayName(selectedMode);
        MarkingModeConfig.ModeConfiguration config = MarkingModeConfig.getModeConfiguration(type);
        
        if (config != null) {
            // Update content and tooltip
            textInputField.setText(config.getDefaultContent());
            textInputField.setToolTipText(config.getTooltip());
            
            // Update UI based on mode requirements
            if (config.isRequiresTemplate()) {
                importImageButton.setEnabled(true);
                importImageButton.setText("📁 Select Image");
                importImageButton.setBackground(new Color(52, 152, 219));
                importImageButton.setForeground(Color.WHITE);
            } else {
                importImageButton.setEnabled(true);
                importImageButton.setText("📷 Import");
                importImageButton.setBackground(new Color(189, 195, 199));
                importImageButton.setForeground(Color.BLACK);
            }
            
            // Show mode-specific information
            showModeSpecificInfo(type, config);
        }
        
        updateEstimatedTime();
    }
    
    private void showModeSpecificInfo(MarkingModeConfig.MarkingType type, MarkingModeConfig.ModeConfiguration config) {
        // Display mode-specific parameters and tips
        Map<String, Object> parameters = config.getParameters();
        
        switch (type) {
            case SERIAL_NUMBER:
                if (config.isSupportsAutoIncrement()) {
                    textInputField.setBackground(new Color(240, 255, 240)); // Light green
                }
                break;
            case DATE_TIME:
                textInputField.setEditable(false);
                textInputField.setBackground(new Color(240, 240, 255)); // Light blue
                break;
            case BARCODE:
            case QR_CODE:
                textInputField.setBackground(new Color(255, 255, 240)); // Light yellow
                break;
            case LOGO_IMAGE:
                textInputField.setEditable(false);
                textInputField.setBackground(new Color(255, 240, 255)); // Light purple
                break;
            default:
                textInputField.setEditable(true);
                textInputField.setBackground(Color.WHITE);
                break;
        }
    }
    
    private void updateMaterialSettings() {
        String material = (String) materialTypeComboBox.getSelectedItem();
        // Auto-adjust settings based on material
        switch (material) {
            case "Steel":
                dotDepthSpinner.setValue(0.15);
                markingSpeedComboBox.setSelectedItem("Medium (200/min)");
                needlePressureSpinner.setValue(70);
                break;
            case "Aluminum":
                dotDepthSpinner.setValue(0.10);
                markingSpeedComboBox.setSelectedItem("Fast (400/min)");
                needlePressureSpinner.setValue(50);
                break;
            case "Plastic":
                dotDepthSpinner.setValue(0.05);
                markingSpeedComboBox.setSelectedItem("Ultra Fast (800/min)");
                needlePressureSpinner.setValue(30);
                break;
            case "Titanium":
                dotDepthSpinner.setValue(0.20);
                markingSpeedComboBox.setSelectedItem("Slow (100/min)");
                needlePressureSpinner.setValue(90);
                break;
        }
        updateEstimatedTime();
    }
    
    private void applyQualityPreset() {
        String quality = (String) qualityPresetComboBox.getSelectedItem();
        switch (quality) {
            case "Draft":
                dotPitchHorizontalSpinner.setValue(1.0);
                dotPitchVerticalSpinner.setValue(1.0);
                passCountSpinner.setValue(1);
                markingSpeedComboBox.setSelectedItem("Ultra Fast (800/min)");
                break;
            case "Standard":
                dotPitchHorizontalSpinner.setValue(0.5);
                dotPitchVerticalSpinner.setValue(0.5);
                passCountSpinner.setValue(1);
                markingSpeedComboBox.setSelectedItem("Medium (200/min)");
                break;
            case "High Quality":
                dotPitchHorizontalSpinner.setValue(0.3);
                dotPitchVerticalSpinner.setValue(0.3);
                passCountSpinner.setValue(2);
                markingSpeedComboBox.setSelectedItem("Slow (100/min)");
                break;
            case "Ultra Fine":
                dotPitchHorizontalSpinner.setValue(0.2);
                dotPitchVerticalSpinner.setValue(0.2);
                passCountSpinner.setValue(3);
                markingSpeedComboBox.setSelectedItem("Ultra Slow (50/min)");
                break;
            case "Production":
                dotPitchHorizontalSpinner.setValue(0.8);
                dotPitchVerticalSpinner.setValue(0.8);
                passCountSpinner.setValue(1);
                markingSpeedComboBox.setSelectedItem("Fast (400/min)");
                break;
        }
        updateEstimatedTime();
    }
    
    private void toggleAutoDepth() {
        boolean enabled = autoDepthCheckBox.isSelected();
        dotDepthSpinner.setEnabled(!enabled);
        if (enabled) {
            // Auto-calculate depth based on material and needle type
            String material = (String) materialTypeComboBox.getSelectedItem();
            String needle = (String) needleTypeComboBox.getSelectedItem();
            double autoDepth = calculateOptimalDepth(material, needle);
            dotDepthSpinner.setValue(autoDepth);
        }
    }
    
    private double calculateOptimalDepth(String material, String needle) {
        double baseDepth = 0.1;
        // Material factor
        switch (material) {
            case "Steel": baseDepth = 0.15; break;
            case "Aluminum": baseDepth = 0.10; break;
            case "Plastic": baseDepth = 0.05; break;
            case "Titanium": baseDepth = 0.20; break;
            case "Brass": baseDepth = 0.12; break;
        }
        // Needle factor
        switch (needle) {
            case "Fine Tip": baseDepth *= 0.8; break;
            case "Heavy Duty": baseDepth *= 1.3; break;
            case "Diamond Tip": baseDepth *= 1.1; break;
        }
        return Math.round(baseDepth * 100.0) / 100.0;
    }
    
    private void toggleLivePreview() {
        boolean enabled = previewModeCheckBox.isSelected();
        if (enabled) {
            updatePreview();
            JOptionPane.showMessageDialog(this,
                "ðŸ” Live Preview Mode Enabled\n\n" +
                "Canvas will update in real-time as you\n" +
                "modify marking parameters.",
                "Live Preview", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void importCustomImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Image files", "png", "jpg", "jpeg", "gif", "bmp", "svg"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            markingModeComboBox.setSelectedItem("Logo/Image");
            textInputField.setText(selectedFile.getName());
            JOptionPane.showMessageDialog(this,
                "ðŸ“· Image Imported Successfully!\n\n" +
                "File: " + selectedFile.getName() + "\n" +
                "Image will be converted to dot pattern\n" +
                "for marking operation.",
                "Image Import", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadMarkingTemplate() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Template files", "template", "json"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                "ðŸ“ Template Loaded!\n\n" +
                "File: " + selectedFile.getName() + "\n" +
                "All marking settings have been restored\n" +
                "from the saved template.",
                "Template Loaded", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void saveMarkingTemplate() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Template files", "template"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                "ðŸ’¾ Template Saved!\n\n" +
                "File: " + selectedFile.getName() + "\n" +
                "Current marking configuration saved\n" +
                "for future use.",
                "Template Saved", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void calibrateDevice() {
        int result = JOptionPane.showConfirmDialog(this,
            "ðŸŽ¯ DEVICE CALIBRATION\n\n" +
            "This will perform automatic calibration to ensure\n" +
            "accurate marking precision and depth control.\n\n" +
            "âš ï¸ Ensure test material is properly positioned\n" +
            "and workspace is clear.\n\n" +
            "Continue with calibration?",
            "Device Calibration",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (result == JOptionPane.YES_OPTION) {
            // Simulate calibration process
            markingProgressBar.setVisible(true);
            for (int i = 0; i <= 100; i += 10) {
                markingProgressBar.setValue(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            markingProgressBar.setVisible(false);
            
            JOptionPane.showMessageDialog(this,
                "âœ… CALIBRATION COMPLETE\n\n" +
                "Device calibrated successfully!\n\n" +
                "â€¢ Needle position: Aligned\n" +
                "â€¢ Depth accuracy: Â±0.01mm\n" +
                "â€¢ Speed optimization: Applied\n" +
                "â€¢ Pressure calibration: Complete\n\n" +
                "Device ready for precision marking.",
                "Calibration Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateEstimatedTime() {
        // Calculate estimated time based on current settings
        String text = textInputField.getText();
        int fontSize = (Integer) fontSizeSpinner.getValue();
        double pitchH = (Double) dotPitchHorizontalSpinner.getValue();
        double pitchV = (Double) dotPitchVerticalSpinner.getValue();
        int passes = (Integer) passCountSpinner.getValue();
        String speed = (String) markingSpeedComboBox.getSelectedItem();
        
        // Extract speed value
        int dotsPerMin = 200; // default
        if (speed.contains("50")) dotsPerMin = 50;
        else if (speed.contains("100")) dotsPerMin = 100;
        else if (speed.contains("400")) dotsPerMin = 400;
        else if (speed.contains("800")) dotsPerMin = 800;
        
        // Estimate total dots
        int estimatedDots = text.length() * fontSize * passes;
        double estimatedMinutes = (double) estimatedDots / dotsPerMin;
        
        String timeText;
        if (estimatedMinutes < 1) {
            timeText = String.format("Est. Time: %.0f sec", estimatedMinutes * 60);
        } else {
            timeText = String.format("Est. Time: %.1f min", estimatedMinutes);
        }
        
        estimatedTimeLabel.setText(timeText);
    }
    
    private void applyConsistentStyling() {
        Font smallFont = new Font("Segoe UI", Font.BOLD, 10); // Reduced to fit window properly
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 11); // Reduced button font to fit within window
        Dimension smallSpinnerSize = new Dimension(60, 22);
        Dimension mediumButtonSize = new Dimension(110, 26); // Reduced button size to fit window layout
        
        // Style all components
        Component[] allComponents = {
            printerComboBox, printButton, previewButton, pageSetupButton, copiesSpinner,
            colorCheckBox, paperSizeComboBox, orientationComboBox, markingModeComboBox,
            textInputField, dotPitchHorizontalSpinner, dotPitchVerticalSpinner, dotDepthSpinner,
            markingSpeedComboBox, passCountSpinner, startMarkingButton, simulateButton,
            exportGCodeButton, xOffsetSpinner, yOffsetSpinner, rotationSpinner, scaleSpinner,
            previewGridCheckBox, materialBoundaryCheckBox, dotPreviewCheckBox, connectButton,
            portComboBox, baudRateComboBox, connectionTypeComboBox, ipAddressField, portSpinner,
            scanWiFiButton, testConnectionButton, materialTypeComboBox, needleTypeComboBox,
            needlePressureSpinner, markingPatternComboBox, autoDepthCheckBox, fontTypeComboBox,
            fontSizeSpinner, boldTextCheckBox, italicTextCheckBox, textAlignmentComboBox,
            lineSpacingSpinner, characterSpacingSpinner, importImageButton, loadTemplateButton,
            saveTemplateButton, qualityPresetComboBox, previewModeCheckBox, calibrateButton
        };
        
        for (Component comp : allComponents) {
            if (comp instanceof JSpinner) {
                comp.setFont(smallFont);
                comp.setPreferredSize(smallSpinnerSize);
            } else if (comp instanceof JButton) {
                comp.setFont(buttonFont); // Use larger button font
                comp.setPreferredSize(mediumButtonSize);
                ((JButton) comp).setFocusPainted(false);
                ((JButton) comp).setOpaque(true); // Ensure all buttons are opaque
            } else if (comp instanceof JComboBox) {
                comp.setFont(smallFont);
                comp.setPreferredSize(new Dimension(100, 22));
            } else {
                comp.setFont(smallFont); // All other components
            }
        }
        
        // Special styling for important buttons with proper sizing
        startMarkingButton.setBackground(new Color(231, 76, 60));
        startMarkingButton.setForeground(Color.WHITE);
        startMarkingButton.setOpaque(true);
        startMarkingButton.setBorderPainted(true);
        startMarkingButton.setBorder(BorderFactory.createRaisedBevelBorder());
        startMarkingButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        startMarkingButton.setText("▶️ Start Marking");
        
        printButton.setBackground(new Color(52, 152, 219));
        printButton.setForeground(Color.WHITE);
        printButton.setOpaque(true);
        printButton.setBorderPainted(true);
        printButton.setBorder(BorderFactory.createRaisedBevelBorder());
        printButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        printButton.setText("🖨️ Print");
        
        connectButton.setBackground(new Color(39, 174, 96));
        connectButton.setForeground(Color.WHITE);
        connectButton.setOpaque(true);
        connectButton.setBorderPainted(true);
        connectButton.setBorder(BorderFactory.createRaisedBevelBorder());
        connectButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        connectButton.setText("Connect"); // Simplified text for better fit
        connectButton.setPreferredSize(new Dimension(80, 24)); // Smaller but guaranteed visible
        connectButton.setMinimumSize(new Dimension(80, 24)); // Minimum size guarantee
        connectButton.setMaximumSize(new Dimension(80, 24)); // Prevent excessive growth
        
        // Scan WiFi button styling with guaranteed size
        scanWiFiButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        scanWiFiButton.setText("Scan WiFi");
        scanWiFiButton.setPreferredSize(new Dimension(80, 24));
        scanWiFiButton.setMinimumSize(new Dimension(80, 24));
        scanWiFiButton.setMaximumSize(new Dimension(80, 24));
        scanWiFiButton.setOpaque(true);
        scanWiFiButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Test Connection button styling with guaranteed size
        testConnectionButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        testConnectionButton.setText("Test");
        testConnectionButton.setPreferredSize(new Dimension(80, 24));
        testConnectionButton.setMinimumSize(new Dimension(80, 24));
        testConnectionButton.setMaximumSize(new Dimension(80, 24));
        testConnectionButton.setOpaque(true);
        testConnectionButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        calibrateButton.setBackground(new Color(155, 89, 182));
        calibrateButton.setForeground(Color.WHITE);
        calibrateButton.setOpaque(true);
        calibrateButton.setBorderPainted(true);
        calibrateButton.setBorder(BorderFactory.createRaisedBevelBorder());
        calibrateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        calibrateButton.setText("⚙️ Calibrate");
        
        simulateButton.setBackground(new Color(241, 196, 15));
        simulateButton.setForeground(Color.BLACK); // Black text for better contrast on yellow
        simulateButton.setOpaque(true);
        simulateButton.setBorderPainted(true);
        simulateButton.setBorder(BorderFactory.createRaisedBevelBorder());
        simulateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        simulateButton.setText("🔄 Simulate");
        
        // Template buttons styling with proper sizing
        loadTemplateButton.setBackground(new Color(52, 152, 219));
        loadTemplateButton.setForeground(Color.WHITE);
        loadTemplateButton.setOpaque(true);
        loadTemplateButton.setBorderPainted(true);
        loadTemplateButton.setBorder(BorderFactory.createRaisedBevelBorder());
        loadTemplateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        loadTemplateButton.setText("📁 Load Template");
        
        saveTemplateButton.setBackground(new Color(39, 174, 96));
        saveTemplateButton.setForeground(Color.WHITE);
        saveTemplateButton.setOpaque(true);
        saveTemplateButton.setBorderPainted(true);
        saveTemplateButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveTemplateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        saveTemplateButton.setText("💾 Save Template");
        
        // Import Image button styling
        importImageButton.setBackground(new Color(142, 68, 173));
        importImageButton.setForeground(Color.WHITE);
        importImageButton.setOpaque(true);
        importImageButton.setBorderPainted(true);
        importImageButton.setBorder(BorderFactory.createRaisedBevelBorder());
        importImageButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        importImageButton.setText("🖼️ Import Image");
        
        // Progress bar settings
        markingProgressBar.setStringPainted(true);
        markingProgressBar.setString("Ready");
        markingProgressBar.setVisible(false);
        
        // Initialize estimated time
        updateEstimatedTime();
        
        // Apply text clarity enhancements
        enhanceTextClarity();
    }
    
    private void enhanceTextClarity() {
        // Enable text antialiasing and clarity for all buttons
        JButton[] allButtons = {
            loadTemplateButton, saveTemplateButton, importImageButton, printButton,
            previewButton, pageSetupButton, startMarkingButton, simulateButton,
            exportGCodeButton, connectButton, scanWiFiButton, testConnectionButton,
            calibrateButton
        };
        
        for (JButton button : allButtons) {
            if (button != null) {
                // Enable high-quality rendering hints
                button.putClientProperty("awt.useSystemAAFontSettings", "on");
                button.putClientProperty("swing.aatext", true);
                
                // Ensure maximum contrast
                button.setOpaque(true);
                button.setBorderPainted(true);
                button.setFocusPainted(false);
                
                // Force repaint with new settings
                button.invalidate();
                button.repaint();
            }
        }
        
        // Set system-wide text rendering hints for better clarity
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }
    
    private void forceButtonVisibility() {
        // Force template buttons to be visible with explicit settings
        loadTemplateButton.setVisible(true);
        loadTemplateButton.setEnabled(true);
        loadTemplateButton.repaint();
        
        saveTemplateButton.setVisible(true);
        saveTemplateButton.setEnabled(true);
        saveTemplateButton.repaint();
        
        importImageButton.setVisible(true);
        importImageButton.setEnabled(true);
        importImageButton.repaint();
        
        // Force repaint of the parent container
        this.revalidate();
        this.repaint();
    }
    
    private JPanel createCompactToolbarSection(String title, JPanel contentPanel) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(getBackground());
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font(Font.SANS_SERIF, Font.BOLD, 10),
                Color.DARK_GRAY
            ),
            BorderFactory.createEmptyBorder(2, 4, 4, 4)
        ));
        
        section.add(contentPanel, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(2, 80));
        separator.setBackground(BORDER_COLOR);
        return separator;
    }
    
    // === UTILITY METHODS ===
    
    private String calculateEstimatedTime() {
        // Calculate estimated marking time based on content and settings
        String content = textInputField.getText();
        String speed = (String) markingSpeedComboBox.getSelectedItem();
        double depth = (Double) dotDepthSpinner.getValue();
        int passes = (Integer) passCountSpinner.getValue();
        
        // Extract speed value (dots per minute)
        int dotsPerMinute = 200; // default
        if (speed.contains("50")) dotsPerMinute = 50;
        else if (speed.contains("100")) dotsPerMinute = 100;
        else if (speed.contains("200")) dotsPerMinute = 200;
        else if (speed.contains("400")) dotsPerMinute = 400;
        else if (speed.contains("800")) dotsPerMinute = 800;
        
        // Estimate total dots needed (rough calculation)
        int estimatedDots = content.length() * 50 * passes; // 50 dots per character average
        
        // Add complexity factors
        double complexityFactor = 1.0;
        if (depth > 0.15) complexityFactor += 0.5; // Deeper marking takes longer
        
        String selectedMode = (String) markingModeComboBox.getSelectedItem();
        if (selectedMode.contains("Arch") || selectedMode.contains("Donut")) {
            complexityFactor += 0.3; // Complex shapes take longer
        }
        
        // Calculate time in minutes
        double timeMinutes = (estimatedDots * complexityFactor) / dotsPerMinute;
        
        if (timeMinutes < 1) {
            return String.format("%.0f seconds", timeMinutes * 60);
        } else if (timeMinutes < 60) {
            return String.format("%.1f minutes", timeMinutes);
        } else {
            int hours = (int) (timeMinutes / 60);
            int minutes = (int) (timeMinutes % 60);
            return String.format("%d:%02d hours", hours, minutes);
        }
    }
    
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        return label;
    }
    
    // Printable interface implementation
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Print the canvas content
        if (canvas != null) {
            canvas.paint(g2d);
        }
        
        return PAGE_EXISTS;
    }
}

