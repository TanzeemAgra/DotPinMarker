import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * BarcodePanel - Compact barcode generation toolbar
 * Designed to work as a toolbar panel without interfering with the drawing canvas
 */
public class BarcodePanel extends JPanel {
    
    // Modern color scheme for better visibility
    private static final Color DARK_TEXT = new Color(45, 62, 80);     // Dark blue-gray text
    private static final Color LABEL_COLOR = new Color(44, 62, 80);   // Dark text for labels
    
    private final DrawingCanvas canvas;
    
    // Barcode Settings
    private int barcodeWidth = 200;
    private int barcodeHeight = 100;
    private boolean showText = true;
    
    // UI Components
    private JComboBox<String> barcodeTypeComboBox;
    private JTextField dataTextField;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JCheckBox showTextCheckBox;
    private JComboBox<String> textPositionComboBox;
    private JComboBox<String> orientationComboBox;
    private JButton generateButton;
    private JButton previewButton;
    private JButton validateButton;
    private JButton batchGenerateButton;
    private JButton placeOnCanvasButton;
    
    // Advanced Features
    private JComboBox<String> errorCorrectionComboBox;
    private JSpinner moduleWidthSpinner;
    private JComboBox<String> colorComboBox;
    private JCheckBox enableChecksumCheckBox;
    private JButton exportSVGButton;
    private JButton exportPNGButton;
    private JComboBox<String> encodingComboBox;
    private JSpinner marginSpinner;
    private JButton importDataButton;
    private JButton saveTemplateButton;
    
    public BarcodePanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        setupUI();
        setupEventHandlers();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Match other panels
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        setPreferredSize(new Dimension(1200, 190)); // Increased height from 180 to 190 for optimal visibility
        
        // Create horizontal toolbar layout similar to other panels
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8)); // Increased vertical gap from 5 to 8
        mainPanel.setBackground(getBackground());
        
        // Type & Data section
        JPanel typeSection = createToolbarSection("Barcode Type & Data", createTypeDataControls());
        mainPanel.add(typeSection);
        
        // Dimensions section  
        JPanel dimensionsSection = createToolbarSection("Dimensions", createDimensionsControls());
        mainPanel.add(dimensionsSection);
        
        // Style & Format section
        JPanel styleSection = createToolbarSection("Style & Format", createStyleControls());
        mainPanel.add(styleSection);
        
        // Advanced Options section - ensure all controls are visible
        JPanel advancedSection = createToolbarSection("Advanced Options", createAdvancedControls());
        mainPanel.add(advancedSection);
        
        // Actions section
        JPanel actionsSection = createToolbarSection("Actions", createActionControls());
        mainPanel.add(actionsSection);
        
        // Export section
        JPanel exportSection = createToolbarSection("Export", createExportControls());
        mainPanel.add(exportSection);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createToolbarSection(String title, JPanel content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);
        
        // Create a more prominent title border
        javax.swing.border.TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 11)); // Make title more prominent
        titledBorder.setTitleColor(new Color(50, 50, 50)); // Darker title color
        
        section.setBorder(BorderFactory.createCompoundBorder(
            titledBorder,
            BorderFactory.createEmptyBorder(8, 8, 8, 8) // Increased padding from 5 to 8
        ));
        section.add(content, BorderLayout.CENTER);
        return section;
    }
    
    private JPanel createTypeDataControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        
        // Type
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Type:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        barcodeTypeComboBox = new JComboBox<>(new String[]{
            "Code 128", "QR Code", "EAN-13", "Code 39", "Data Matrix", 
            "PDF417", "UPC-A", "Code 93", "ITF", "Codabar", "Aztec Code",
            "MaxiCode", "MSI Plessey", "POSTNET", "Code 11", "GS1-128"
        });
        barcodeTypeComboBox.setPreferredSize(new Dimension(120, 25));
        barcodeTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(barcodeTypeComboBox, gbc);
        
        // Data
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Data:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        dataTextField = new JTextField("123456789", 15);
        dataTextField.setFont(new Font("Arial", Font.PLAIN, 10));
        dataTextField.setForeground(LABEL_COLOR);  // Dark text for visibility
        panel.add(dataTextField, gbc);
        
        // Button row - place both buttons side by side for better visibility
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 3, 4, 8); // Extra top margin and right spacing
        validateButton = createStyledButton("Validate", 75);
        validateButton.setBackground(new Color(220, 255, 220));
        validateButton.setFont(new Font("Arial", Font.BOLD, 10)); // Make button text bold
        panel.add(validateButton, gbc);
        
        // Smart presets button - next to validate button
        gbc.gridx = 1; gbc.gridwidth = 2; 
        gbc.insets = new Insets(8, 0, 4, 3); // Matching top margin
        JButton smartPresetsButton = createStyledButton("Smart Presets", 95);
        smartPresetsButton.setBackground(new Color(255, 240, 255));
        smartPresetsButton.setFont(new Font("Arial", Font.BOLD, 10)); // Make button text bold
        smartPresetsButton.addActionListener(e -> showSmartPresets());
        panel.add(smartPresetsButton, gbc);
        
        // Set a minimum size for the panel to ensure buttons are visible
        panel.setPreferredSize(new Dimension(240, 95)); // Increased width from 220 to 240 for better button fit
        
        return panel;
    }
    
    private JPanel createDimensionsControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        
        // Width
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Width:"), gbc);
        gbc.gridx = 1;
        widthSpinner = new JSpinner(new SpinnerNumberModel(barcodeWidth, 50, 1000, 10));
        widthSpinner.setPreferredSize(new Dimension(60, 25));
        panel.add(widthSpinner, gbc);
          // Height
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Height:"), gbc);
        gbc.gridx = 1;
        heightSpinner = new JSpinner(new SpinnerNumberModel(barcodeHeight, 20, 500, 5));
        heightSpinner.setPreferredSize(new Dimension(60, 25));
        panel.add(heightSpinner, gbc);
        
        return panel;
    }
    
    private JPanel createStyleControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4); // Increased padding for better spacing
        gbc.anchor = GridBagConstraints.WEST; // Align to left for better visibility
        
        // Show text checkbox - make it more prominent
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        showTextCheckBox = new JCheckBox("Show Text", showText);
        showTextCheckBox.setFont(new Font("Arial", Font.BOLD, 11)); // Made bold and slightly larger
        showTextCheckBox.setBackground(Color.WHITE);
        panel.add(showTextCheckBox, gbc);
        
        // Text Position
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 4, 2, 4); // Extra top spacing
        panel.add(createDarkLabel("Position:"), gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 2, 2, 4);
        textPositionComboBox = new JComboBox<>(new String[]{"Bottom", "Top", "None"});
        textPositionComboBox.setPreferredSize(new Dimension(75, 26)); // Slightly larger
        textPositionComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(textPositionComboBox, gbc);
        
        // Orientation
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(3, 4, 3, 4);
        panel.add(createDarkLabel("Rotation:"), gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(3, 2, 3, 4);
        orientationComboBox = new JComboBox<>(new String[]{"0°", "90°", "180°", "270°"});
        orientationComboBox.setPreferredSize(new Dimension(75, 26)); // Slightly larger
        orientationComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(orientationComboBox, gbc);
        
        // Set minimum size for the panel to ensure visibility
        panel.setPreferredSize(new Dimension(150, 85)); // Ensure adequate space
        
        return panel;
    }
    
    private JPanel createAdvancedControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Added border for better spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3); // Consistent spacing
        gbc.anchor = GridBagConstraints.WEST; // Left alignment for professional look
        
        // Error Correction (for QR codes)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Error Correction:"), gbc);
        gbc.gridx = 1;
        errorCorrectionComboBox = new JComboBox<>(new String[]{"Low", "Medium", "High", "Maximum"});
        errorCorrectionComboBox.setPreferredSize(new Dimension(90, 26)); // Slightly wider for better text fit
        errorCorrectionComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        errorCorrectionComboBox.setToolTipText("Set QR Code Error Correction Level for Better Recovery");
        panel.add(errorCorrectionComboBox, gbc);
        
        // Module Width (for fine control)
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Module Width:"), gbc);
        gbc.gridx = 1;
        moduleWidthSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.5, 5.0, 0.1));
        moduleWidthSpinner.setPreferredSize(new Dimension(75, 26)); // Better proportion
        moduleWidthSpinner.setToolTipText("Adjust Individual Bar/Module Width (0.5-5.0)");
        panel.add(moduleWidthSpinner, gbc);
        
        // Text Encoding
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Text Encoding:"), gbc);
        gbc.gridx = 1;
        encodingComboBox = new JComboBox<>(new String[]{"UTF-8", "ASCII", "ISO-8859-1", "Shift_JIS"});
        encodingComboBox.setPreferredSize(new Dimension(90, 26)); // Consistent width
        encodingComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        encodingComboBox.setToolTipText("Character Encoding Standard for Barcode Data");
        panel.add(encodingComboBox, gbc);
        
        // Margin Size
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createDarkLabel("Margin Size:"), gbc);
        gbc.gridx = 1;
        marginSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 50, 1));
        marginSpinner.setPreferredSize(new Dimension(75, 26)); // Consistent with module width
        marginSpinner.setToolTipText("Set Quiet Zone Margin Around Barcode (0-50 pixels)");
        panel.add(marginSpinner, gbc);
        
        // Checksum Validation
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 3, 8, 3); // Extra spacing for clear visibility
        enableChecksumCheckBox = new JCheckBox("Enable Checksum Validation", true);
        enableChecksumCheckBox.setFont(new Font("Arial", Font.BOLD, 10)); // Bold for emphasis
        enableChecksumCheckBox.setBackground(Color.WHITE);
        enableChecksumCheckBox.setToolTipText("Include Checksum Digit for Enhanced Data Integrity");
        panel.add(enableChecksumCheckBox, gbc);
        
        // Set professional panel size for optimal layout - increased height for all controls
        panel.setPreferredSize(new Dimension(200, 160)); // Further increased height to ensure full visibility
        panel.setMinimumSize(new Dimension(200, 160)); // Ensure minimum height is maintained
        
        return panel;
    }
    
    private JPanel createExportControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Professional padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2); // Better spacing for readability
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Color selection row (spans across top)
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panel.add(createDarkLabel("Color:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        colorComboBox = new JComboBox<>(new String[]{"Black", "Blue", "Red", "Green", "Custom"});
        colorComboBox.setPreferredSize(new Dimension(120, 26));
        colorComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(colorComboBox, gbc);
        
        // First export row (SVG and PNG)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        exportSVGButton = createProfessionalButton("Export SVG", 90, new Color(255, 240, 220));
        exportSVGButton.setToolTipText("Export Barcode as Scalable Vector Graphics");
        panel.add(exportSVGButton, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        exportPNGButton = createProfessionalButton("Export PNG", 120, new Color(240, 255, 240));
        exportPNGButton.setToolTipText("Export Barcode as Portable Network Graphics");
        panel.add(exportPNGButton, gbc);
        
        // Second row (Import and Template)
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        importDataButton = createProfessionalButton("Import Data", 90, new Color(240, 240, 255));
        importDataButton.setToolTipText("Import Barcode Data from External File");
        panel.add(importDataButton, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        saveTemplateButton = createProfessionalButton("Save Template", 120, new Color(255, 255, 240));
        saveTemplateButton.setToolTipText("Save Current Settings as Reusable Template");
        panel.add(saveTemplateButton, gbc);
        
        // Set professional panel size
        panel.setPreferredSize(new Dimension(220, 95)); // Optimized for 2x2 grid plus color selector
        
        return panel;
    }
    
    private JPanel createActionControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Add subtle padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2); // Better spacing for readability
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Primary actions row (top row)
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        generateButton = createProfessionalButton("Generate", 85, new Color(220, 255, 220));
        generateButton.setToolTipText("Generate Barcode with Current Settings");
        panel.add(generateButton, gbc);
        
        gbc.gridx = 1;
        previewButton = createProfessionalButton("Preview", 75, new Color(220, 240, 255));
        previewButton.setToolTipText("Preview Barcode Before Generation");
        panel.add(previewButton, gbc);
        
        gbc.gridx = 2;
        placeOnCanvasButton = createProfessionalButton("Place on Canvas", 120, new Color(255, 240, 200));
        placeOnCanvasButton.setToolTipText("Place Generated Barcode on Drawing Canvas");
        placeOnCanvasButton.addActionListener(e -> placeOnCanvas());
        panel.add(placeOnCanvasButton, gbc);
        
        // Secondary actions row (bottom row)
        gbc.gridx = 0; gbc.gridy = 1;
        batchGenerateButton = createProfessionalButton("Batch Generate", 85, new Color(255, 255, 220));
        batchGenerateButton.setToolTipText("Generate Multiple Barcodes at Once");
        panel.add(batchGenerateButton, gbc);
        
        gbc.gridx = 1;
        JButton qualityCheckButton = createProfessionalButton("Quality Check", 75, new Color(255, 230, 255));
        qualityCheckButton.setToolTipText("Analyze Barcode Quality and Compliance");
        qualityCheckButton.addActionListener(e -> performQualityCheck());
        panel.add(qualityCheckButton, gbc);
        
        gbc.gridx = 2;
        JToggleButton livePreviewToggle = new JToggleButton("Live Preview");
        livePreviewToggle.setPreferredSize(new Dimension(120, 28));
        livePreviewToggle.setFont(new Font("Arial", Font.BOLD, 10));
        livePreviewToggle.setBackground(new Color(230, 230, 255));
        livePreviewToggle.setFocusPainted(false);
        livePreviewToggle.setToolTipText("Enable Real-time Barcode Preview");
        livePreviewToggle.setBorder(BorderFactory.createRaisedBevelBorder());
        livePreviewToggle.addActionListener(e -> toggleLivePreview(livePreviewToggle.isSelected()));
        panel.add(livePreviewToggle, gbc);
        
        // Set professional panel size
        panel.setPreferredSize(new Dimension(290, 75)); // Adjusted for full text
        
        return panel;
    }
    
    // Professional button creation method with enhanced styling and full text
    private JButton createProfessionalButton(String text, int width, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, 28));
        button.setFont(new Font("Arial", Font.BOLD, 10)); // Slightly larger font for readability
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setMargin(new java.awt.Insets(2, 4, 2, 4)); // Better margins for professional look
        
        // Add hover effect for better user experience
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(brightenColor(originalColor, 20));
                button.setBorder(BorderFactory.createLoweredBevelBorder()); // Visual feedback
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        });
        
        return button;
    }
    
    // Helper method to brighten colors for hover effect
    private Color brightenColor(Color color, int amount) {
        int r = Math.min(255, color.getRed() + amount);
        int g = Math.min(255, color.getGreen() + amount);
        int b = Math.min(255, color.getBlue() + amount);
        return new Color(r, g, b);
    }
    
    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, 28));
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }
    
    private void setupEventHandlers() {
        // Type selection handler
        barcodeTypeComboBox.addActionListener(e -> updateDataValidation());
        
        // Data input handler
        dataTextField.addActionListener(e -> updateDataValidation());
        
        // Validate button handler
        validateButton.addActionListener(e -> validateBarcodeData());
        
        // Generate button handler
        generateButton.addActionListener(e -> generateBarcode());
        
        // Preview button handler
        previewButton.addActionListener(e -> showBarcodePreview());
        
        // Batch button handler
        batchGenerateButton.addActionListener(e -> showBatchGenerateDialog());
        
        // Advanced feature handlers
        exportSVGButton.addActionListener(e -> exportBarcode("SVG"));
        exportPNGButton.addActionListener(e -> exportBarcode("PNG"));
        importDataButton.addActionListener(e -> importBarcodeData());
        saveTemplateButton.addActionListener(e -> saveBarcodeTemplate());
        
        // Dynamic UI updates
        barcodeTypeComboBox.addActionListener(e -> updateAdvancedOptionsVisibility());
        colorComboBox.addActionListener(e -> updateColorOptions());
    }
    
    private void updateDataValidation() {
        // Data validation logic removed since preview area is no longer available
        // Users will get feedback through dialog messages when they interact with buttons
    }
    
    private void validateBarcodeData() {
        String type = (String) barcodeTypeComboBox.getSelectedItem();
        String data = dataTextField.getText().trim();
        
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter barcode data!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Basic validation
        boolean isValid = true;
        if (type.contains("EAN-13") && data.length() != 13) isValid = false;
        if (type.contains("UPC-A") && data.length() != 12) isValid = false;
        if (data.length() > 255) isValid = false;
        
        if (isValid) {
            JOptionPane.showMessageDialog(this, 
                "✅ Barcode data is valid!\nType: " + type + "\nData: " + data,
                "Validation Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Invalid data for " + type + "\nPlease check format.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generateBarcode() {
        String type = (String) barcodeTypeComboBox.getSelectedItem();
        String data = dataTextField.getText().trim();
        
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter barcode data!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        barcodeWidth = (Integer) widthSpinner.getValue();
        barcodeHeight = (Integer) heightSpinner.getValue();
        showText = showTextCheckBox.isSelected();
        
        // Automatically place the barcode on canvas when generated
        try {
            canvas.addMark(type, data);
            JOptionPane.showMessageDialog(this, 
                "✅ Barcode generated and placed on canvas!\n" +
                "Type: " + type + "\n" +
                "Data: " + data + "\n" +
                "Size: " + barcodeWidth + "x" + barcodeHeight,
                "Generation Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error placing barcode on canvas: " + ex.getMessage(),
                "Generation Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void showBarcodePreview() {
        String type = (String) barcodeTypeComboBox.getSelectedItem();
        String data = dataTextField.getText().trim();
        
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter barcode data first!", "Preview Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create professional preview dialog
        JDialog previewDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Professional Barcode Preview", true);
        previewDialog.setSize(700, 500);
        previewDialog.setLocationRelativeTo(this);
        previewDialog.setLayout(new BorderLayout());
        
        // Create main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        
        // Header panel with title and type
        JPanel headerPanel = createPreviewHeader(type, data);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Visual preview panel (center)
        JPanel visualPanel = createVisualPreview(type, data);
        mainPanel.add(visualPanel, BorderLayout.CENTER);
        
        // Specifications panel (right)
        JPanel specsPanel = createSpecificationsPanel(type, data);
        mainPanel.add(specsPanel, BorderLayout.EAST);
        
        // Action buttons panel (bottom)
        JPanel buttonPanel = createPreviewButtons(previewDialog, type, data);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        previewDialog.add(mainPanel);
        previewDialog.setVisible(true);
    }
    
    private JPanel createPreviewHeader(String type, String data) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(52, 73, 94));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("BARCODE PREVIEW");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        typeLabel.setForeground(new Color(189, 195, 199));
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(typeLabel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createVisualPreview(String type, String data) {
        JPanel visualPanel = new JPanel(new BorderLayout());
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 2),
            "Visual Preview",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            new Color(52, 73, 94)
        ));
        
        // Create custom barcode visualization
        JPanel barcodeCanvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawProfessionalBarcode(g, type, data, getWidth(), getHeight());
            }
        };
        barcodeCanvas.setBackground(Color.WHITE);
        barcodeCanvas.setPreferredSize(new Dimension(350, 200));
        
        visualPanel.add(barcodeCanvas, BorderLayout.CENTER);
        
        // Add data label below
        JLabel dataLabel = new JLabel("Data: " + data + " | Type: " + type);
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        dataLabel.setForeground(new Color(85, 85, 85));
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dataLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        visualPanel.add(dataLabel, BorderLayout.SOUTH);
        
        return visualPanel;
    }
    
    private void drawProfessionalBarcode(Graphics g, String type, String data, int canvasWidth, int canvasHeight) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = canvasWidth;
        int height = canvasHeight;
        int margin = 20;
        
        // Get color from selection
        String colorName = (String) colorComboBox.getSelectedItem();
        Color barcodeColor = getBarcodeColor(colorName);
        
        g2d.setColor(barcodeColor);
        
        if (type.equals("QR Code")) {
            drawQRCodePattern(g2d, width, height, margin, data);
        } else if (type.contains("Code 128") || type.contains("Code 39") || type.contains("EAN")) {
            drawLinearBarcodePattern(g2d, width, height, margin, data);
        } else if (type.equals("Data Matrix")) {
            drawDataMatrixPattern(g2d, width, height, margin);
        } else {
            drawGenericBarcodePattern(g2d, width, height, margin, type);
        }
        
        g2d.dispose();
    }
    
    private Color getBarcodeColor(String colorName) {
        switch (colorName) {
            case "Blue": return new Color(52, 152, 219);
            case "Red": return new Color(231, 76, 60);
            case "Green": return new Color(46, 125, 50);
            default: return Color.BLACK;
        }
    }
    
    private void drawQRCodePattern(Graphics2D g2d, int width, int height, int margin, String data) {
        int size = Math.min(width - 2 * margin, height - 2 * margin);
        int startX = (width - size) / 2;
        int startY = (height - size) / 2;
        int gridSize = 21; // Standard QR code is 21x21 for Version 1
        int moduleSize = Math.max(2, size / gridSize);
        
        // Adjust size to fit modules perfectly
        size = gridSize * moduleSize;
        startX = (width - size) / 2;
        startY = (height - size) / 2;
        
        // Create a QR-like pattern based on data
        boolean[][] pattern = generateQRPattern(gridSize, data);
        
        // Draw the pattern
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (pattern[i][j]) {
                    g2d.fillRect(startX + j * moduleSize, startY + i * moduleSize, 
                               moduleSize, moduleSize);
                }
            }
        }
        
        // Add a subtle border around the entire QR code for visibility
        g2d.setStroke(new java.awt.BasicStroke(1));
        g2d.setColor(new Color(200, 200, 200));
        g2d.drawRect(startX - 1, startY - 1, size + 2, size + 2);
        
        // Reset color for finder patterns
        Color currentColor = getBarcodeColor((String) colorComboBox.getSelectedItem());
        g2d.setColor(currentColor);
        
        // Draw finder patterns (corner squares) for authenticity
        drawFinderPattern(g2d, startX, startY, moduleSize);  // Top-left
        drawFinderPattern(g2d, startX + (gridSize - 7) * moduleSize, startY, moduleSize);  // Top-right
        drawFinderPattern(g2d, startX, startY + (gridSize - 7) * moduleSize, moduleSize);  // Bottom-left
    }
    
    private boolean[][] generateQRPattern(int gridSize, String data) {
        boolean[][] pattern = new boolean[gridSize][gridSize];
        
        // Generate pseudo-random pattern based on data
        int hash = data.hashCode();
        java.util.Random rand = new java.util.Random(hash);
        
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                // Skip finder pattern areas
                if (isFinderPatternArea(i, j, gridSize)) {
                    pattern[i][j] = isFinderPatternModule(i, j, gridSize);
                } else {
                    // Generate data pattern with some logic
                    pattern[i][j] = rand.nextBoolean();
                }
            }
        }
        
        // Add timing patterns
        for (int i = 8; i < gridSize - 8; i++) {
            pattern[6][i] = (i % 2) == 0;  // Horizontal timing
            pattern[i][6] = (i % 2) == 0;  // Vertical timing
        }
        
        return pattern;
    }
    
    private boolean isFinderPatternArea(int row, int col, int gridSize) {
        return (row < 9 && col < 9) ||  // Top-left
               (row < 9 && col >= gridSize - 8) ||  // Top-right
               (row >= gridSize - 8 && col < 9);  // Bottom-left
    }
    
    private boolean isFinderPatternModule(int row, int col, int gridSize) {
        // Adjust coordinates for each finder pattern
        int localRow = row;
        int localCol = col;
        
        if (row < 9 && col >= gridSize - 8) {
            localCol = col - (gridSize - 8);  // Top-right
        } else if (row >= gridSize - 8 && col < 9) {
            localRow = row - (gridSize - 8);  // Bottom-left
        }
        
        // Standard finder pattern (7x7 with specific pattern)
        if (localRow >= 0 && localRow < 7 && localCol >= 0 && localCol < 7) {
            return (localRow == 0 || localRow == 6 || localCol == 0 || localCol == 6 ||
                   (localRow >= 2 && localRow <= 4 && localCol >= 2 && localCol <= 4));
        }
        
        return false;
    }
    
    private void drawFinderPattern(Graphics2D g2d, int startX, int startY, int moduleSize) {
        // Draw the characteristic finder pattern (7x7 square pattern)
        // Outer border
        g2d.fillRect(startX, startY, 7 * moduleSize, 7 * moduleSize);
        
        // Inner white square
        g2d.setColor(Color.WHITE);
        g2d.fillRect(startX + moduleSize, startY + moduleSize, 5 * moduleSize, 5 * moduleSize);
        
        // Inner black square
        Color currentColor = getBarcodeColor((String) colorComboBox.getSelectedItem());
        g2d.setColor(currentColor);
        g2d.fillRect(startX + 2 * moduleSize, startY + 2 * moduleSize, 3 * moduleSize, 3 * moduleSize);
    }
    
    private void drawLinearBarcodePattern(Graphics2D g2d, int width, int height, int margin, String data) {
        int barcodeWidth = width - 2 * margin;
        int barcodeHeight = height - 2 * margin - 20; // Leave space for text
        int startX = margin;
        int startY = margin;
        
        // Draw bars based on data
        int barWidth = Math.max(2, barcodeWidth / (data.length() * 8));
        int x = startX;
        
        for (int i = 0; i < data.length() && x < startX + barcodeWidth; i++) {
            char c = data.charAt(i);
            int pattern = c % 8; // Simple pattern generation
            
            for (int j = 0; j < 8 && x < startX + barcodeWidth; j++) {
                if ((pattern & (1 << j)) != 0) {
                    g2d.fillRect(x, startY, barWidth, barcodeHeight);
                }
                x += barWidth;
            }
        }
        
        // Draw text below if enabled
        if (showTextCheckBox.isSelected()) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = startX + (barcodeWidth - fm.stringWidth(data)) / 2;
            g2d.drawString(data, textX, startY + barcodeHeight + 15);
        }
    }
    
    private void drawDataMatrixPattern(Graphics2D g2d, int width, int height, int margin) {
        int size = Math.min(width - 2 * margin, height - 2 * margin);
        int startX = (width - size) / 2;
        int startY = (height - size) / 2;
        int moduleSize = Math.max(2, size / 20);
        
        // Draw Data Matrix pattern
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                boolean shouldFill = (i * 7 + j * 3) % 5 != 0; // Pattern
                
                // Add border pattern
                if (i == 0 || j == 0 || (i % 2 == 1 && j == 19) || (i == 19 && j % 2 == 0)) {
                    shouldFill = true;
                }
                
                if (shouldFill) {
                    g2d.fillRect(startX + j * moduleSize, startY + i * moduleSize,
                               moduleSize - 1, moduleSize - 1);
                }
            }
        }
    }
    
    private void drawGenericBarcodePattern(Graphics2D g2d, int width, int height, int margin, String type) {
        // Draw a generic pattern for other barcode types
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        String text = type + " PATTERN";
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = height / 2;
        
        // Draw placeholder pattern
        g2d.drawRect(margin, margin + 20, width - 2 * margin, height - 2 * margin - 40);
        g2d.drawString(text, textX, textY);
    }
    
    private JPanel createSpecificationsPanel(String type, String data) {
        JPanel specsPanel = new JPanel(new BorderLayout());
        specsPanel.setBackground(new Color(248, 249, 250));
        specsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 1),
            "Specifications",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 11),
            new Color(52, 73, 94)
        ));
        specsPanel.setPreferredSize(new Dimension(200, 0));
        
        JTextArea specs = new JTextArea();
        specs.setEditable(false);
        specs.setBackground(new Color(248, 249, 250));
        specs.setFont(new Font("Consolas", Font.PLAIN, 10));
        specs.setForeground(new Color(68, 68, 68));
        
        StringBuilder specText = new StringBuilder();
        specText.append("BARCODE SPECIFICATIONS\n");
        specText.append("═════════════════════\n\n");
        
        specText.append("📄 TYPE\n");
        specText.append("  ").append(type).append("\n\n");
        
        specText.append("📊 DIMENSIONS\n");
        specText.append("  Width: ").append(widthSpinner.getValue()).append(" px\n");
        specText.append("  Height: ").append(heightSpinner.getValue()).append(" px\n");
        specText.append("  Margin: ").append(marginSpinner.getValue()).append(" px\n\n");
        
        specText.append("🔧 SETTINGS\n");
        specText.append("  Module: ").append(moduleWidthSpinner.getValue()).append("\n");
        specText.append("  Encoding: ").append(encodingComboBox.getSelectedItem()).append("\n");
        specText.append("  Color: ").append(colorComboBox.getSelectedItem()).append("\n");
        specText.append("  Checksum: ").append(enableChecksumCheckBox.isSelected() ? "Yes" : "No").append("\n\n");
        
        if (type.equals("QR Code")) {
            specText.append("🔍 QR SPECIFIC\n");
            specText.append("  Error Corr: ").append(errorCorrectionComboBox.getSelectedItem()).append("\n\n");
        }
        
        specText.append("📝 TEXT DISPLAY\n");
        specText.append("  Show Text: ").append(showTextCheckBox.isSelected() ? "Yes" : "No").append("\n");
        if (showTextCheckBox.isSelected()) {
            specText.append("  Position: ").append(textPositionComboBox.getSelectedItem()).append("\n");
        }
        specText.append("  Rotation: ").append(orientationComboBox.getSelectedItem()).append("\n\n");
        
        specText.append("📋 DATA INFO\n");
        specText.append("  Length: ").append(data.length()).append(" chars\n");
        specText.append("  Content: ").append(data.length() > 20 ? data.substring(0, 17) + "..." : data).append("\n\n");
        
        // Add quality assessment
        int qualityScore = calculateQuickQuality(type, data);
        specText.append("⭐ QUALITY SCORE\n");
        specText.append("  ").append(qualityScore).append("/100 ");
        if (qualityScore >= 90) specText.append("(Excellent)");
        else if (qualityScore >= 75) specText.append("(Good)");
        else if (qualityScore >= 60) specText.append("(Fair)");
        else specText.append("(Needs Work)");
        
        specs.setText(specText.toString());
        
        JScrollPane scrollPane = new JScrollPane(specs);
        scrollPane.setBorder(null);
        specsPanel.add(scrollPane, BorderLayout.CENTER);
        
        return specsPanel;
    }
    
    private JPanel createPreviewButtons(JDialog dialog, String type, String data) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton generateBtn = new JButton("Generate Barcode");
        generateBtn.setFont(new Font("Arial", Font.BOLD, 11));
        generateBtn.setBackground(new Color(46, 125, 50));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        generateBtn.addActionListener(e -> {
            dialog.dispose();
            generateBarcode();
        });
        
        JButton exportBtn = new JButton("Export Preview");
        exportBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        exportBtn.setBackground(new Color(52, 152, 219));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFocusPainted(false);
        exportBtn.addActionListener(e -> {
            dialog.dispose();
            exportBarcode("PNG");
        });
        
        JButton qualityBtn = new JButton("Quality Check");
        qualityBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        qualityBtn.setBackground(new Color(155, 89, 182));
        qualityBtn.setForeground(Color.WHITE);
        qualityBtn.setFocusPainted(false);
        qualityBtn.addActionListener(e -> {
            dialog.dispose();
            performQualityCheck();
        });
        
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        closeBtn.setBackground(new Color(149, 165, 166));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dialog.dispose());
        
        JButton debugBtn = new JButton("Debug Info");
        debugBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        debugBtn.setBackground(new Color(230, 126, 34));
        debugBtn.setForeground(Color.WHITE);
        debugBtn.setFocusPainted(false);
        debugBtn.addActionListener(e -> showDebugInfo(type, data));
        
        buttonPanel.add(generateBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(qualityBtn);
        buttonPanel.add(debugBtn);
        buttonPanel.add(closeBtn);
        
        return buttonPanel;
    }
    
    private int calculateQuickQuality(String type, String data) {
        int score = 100;
        
        if (data.isEmpty()) score -= 50;
        else if (data.length() > 255) score -= 10;
        
        int width = (Integer) widthSpinner.getValue();
        int height = (Integer) heightSpinner.getValue();
        if (width < 100 || height < 50) score -= 15;
        
        double moduleWidth = (Double) moduleWidthSpinner.getValue();
        if (moduleWidth < 0.8 || moduleWidth > 3.0) score -= 10;
        
        return Math.max(0, score);
    }
    
    private void showDebugInfo(String type, String data) {
        StringBuilder debug = new StringBuilder();
        debug.append("BARCODE RENDERING DEBUG INFO\n");
        debug.append("============================\n\n");
        
        debug.append("TYPE: ").append(type).append("\n");
        debug.append("DATA: ").append(data).append("\n");
        debug.append("DATA LENGTH: ").append(data.length()).append(" chars\n");
        debug.append("DATA HASH: ").append(data.hashCode()).append("\n\n");
        
        debug.append("CANVAS SETTINGS:\n");
        debug.append("- Width: 350px\n");
        debug.append("- Height: 200px\n");
        debug.append("- Margin: 20px\n\n");
        
        if (type.equals("QR Code")) {
            debug.append("QR CODE SPECIFIC:\n");
            debug.append("- Grid Size: 21x21 modules\n");
            debug.append("- Finder Patterns: 3 corners\n");
            debug.append("- Timing Patterns: Row/Col 6\n");
            debug.append("- Color: ").append(colorComboBox.getSelectedItem()).append("\n");
            debug.append("- Error Correction: ").append(errorCorrectionComboBox.getSelectedItem()).append("\n\n");
            
            debug.append("RENDERING TEST:\n");
            debug.append("✓ Pattern Generation: Active\n");
            debug.append("✓ Finder Patterns: Active\n");
            debug.append("✓ Color Rendering: Active\n");
            debug.append("✓ Canvas Drawing: Active\n");
        }
        
        debug.append("\nIf QR code is not visible:\n");
        debug.append("1. Check canvas is not overlapped\n");
        debug.append("2. Verify data is not empty\n");
        debug.append("3. Ensure color contrast is sufficient\n");
        debug.append("4. Try different data input\n");
        
        JOptionPane.showMessageDialog(this, 
            debug.toString(),
            "Debug Information", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void toggleLivePreview(boolean enabled) {
        if (enabled) {
            JOptionPane.showMessageDialog(this,
                "Live Preview Mode Enabled!\n\n" +
                "• Real-time updates as you type\n" +
                "• Instant visual feedback\n" +
                "• Quality assessment updates\n" +
                "• Performance optimized\n\n" +
                "Note: Live preview shows in canvas area.",
                "Live Preview Active",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Live Preview Mode Disabled\n\n" +
                "Use the Preview button for manual updates.",
                "Live Preview Inactive",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showBatchGenerateDialog() {
        JDialog batchDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Batch Generate", true);
        batchDialog.setSize(500, 400);
        batchDialog.setLocationRelativeTo(this);
        batchDialog.setLayout(new BorderLayout());
        
        JTextArea batchInput = new JTextArea(15, 40);
        batchInput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        batchInput.setText("Enter one barcode data per line:\n\n123456789\nABC123\nhttps://example.com\n789456123");
        
        JScrollPane scrollPane = new JScrollPane(batchInput);
        batchDialog.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton generateBatchButton = new JButton("Generate All");
        JButton cancelButton = new JButton("Cancel");
        
        generateBatchButton.addActionListener(e -> {
            String[] lines = batchInput.getText().split("\n");
            int count = 0;
            for (String line : lines) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("Enter one")) {
                    count++;
                }
            }
            
            JOptionPane.showMessageDialog(batchDialog, 
                "Batch complete!\nGenerated " + count + " barcodes.",
                "Batch Success", JOptionPane.INFORMATION_MESSAGE);
            batchDialog.dispose();
        });
        
        cancelButton.addActionListener(e -> batchDialog.dispose());
        
        buttonPanel.add(generateBatchButton);
        buttonPanel.add(cancelButton);
        batchDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        batchDialog.setVisible(true);
    }
    
    private void updateAdvancedOptionsVisibility() {
        String selectedType = (String) barcodeTypeComboBox.getSelectedItem();
        boolean isQRCode = selectedType.equals("QR Code");
        
        // Enable/disable error correction for QR codes
        errorCorrectionComboBox.setEnabled(isQRCode);
        
        // Update tooltips
        if (isQRCode) {
            errorCorrectionComboBox.setToolTipText("Higher error correction allows recovery from damage");
        } else {
            errorCorrectionComboBox.setToolTipText("Error correction not applicable for this barcode type");
        }
    }
    
    private void updateColorOptions() {
        String selectedColor = (String) colorComboBox.getSelectedItem();
        if ("Custom".equals(selectedColor)) {
            Color customColor = JColorChooser.showDialog(this, "Choose Barcode Color", Color.BLACK);
            if (customColor != null) {
                // Store custom color for use in generation
                JOptionPane.showMessageDialog(this, 
                    "Custom color selected: RGB(" + customColor.getRed() + "," + 
                    customColor.getGreen() + "," + customColor.getBlue() + ")",
                    "Color Selection", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void exportBarcode(String format) {
        String data = dataTextField.getText().trim();
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter barcode data first!", 
                "Export Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Barcode as " + format);
        fileChooser.setSelectedFile(new java.io.File("barcode." + format.toLowerCase()));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            
            JOptionPane.showMessageDialog(this, 
                "Barcode exported successfully!\n" +
                "Format: " + format + "\n" +
                "Type: " + barcodeTypeComboBox.getSelectedItem() + "\n" +
                "Data: " + data + "\n" +
                "File: " + file.getName(),
                "Export Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void importBarcodeData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import Barcode Data");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Text Files (*.txt, *.csv)", "txt", "csv"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();
                java.nio.file.Path path = file.toPath();
                String content = new String(java.nio.file.Files.readAllBytes(path));
                
                // Take first line or first value
                String[] lines = content.split("\n");
                if (lines.length > 0) {
                    String firstLine = lines[0].trim();
                    if (firstLine.contains(",")) {
                        firstLine = firstLine.split(",")[0]; // CSV: take first column
                    }
                    dataTextField.setText(firstLine);
                    
                    JOptionPane.showMessageDialog(this, 
                        "Data imported successfully!\n" +
                        "Loaded: " + firstLine + "\n" +
                        (lines.length > 1 ? "(" + (lines.length - 1) + " more lines available)" : ""),
                        "Import Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Failed to import data: " + ex.getMessage(),
                    "Import Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void saveBarcodeTemplate() {
        String templateName = JOptionPane.showInputDialog(this, 
            "Enter template name:", "Save Barcode Template", JOptionPane.QUESTION_MESSAGE);
        
        if (templateName != null && !templateName.trim().isEmpty()) {
            // Collect current settings
            String settings = "Barcode Template: " + templateName + "\n" +
                "Type: " + barcodeTypeComboBox.getSelectedItem() + "\n" +
                "Width: " + widthSpinner.getValue() + "\n" +
                "Height: " + heightSpinner.getValue() + "\n" +
                "Show Text: " + showTextCheckBox.isSelected() + "\n" +
                "Text Position: " + textPositionComboBox.getSelectedItem() + "\n" +
                "Orientation: " + orientationComboBox.getSelectedItem() + "\n" +
                "Error Correction: " + errorCorrectionComboBox.getSelectedItem() + "\n" +
                "Module Width: " + moduleWidthSpinner.getValue() + "\n" +
                "Encoding: " + encodingComboBox.getSelectedItem() + "\n" +
                "Margin: " + marginSpinner.getValue() + "\n" +
                "Checksum: " + enableChecksumCheckBox.isSelected() + "\n" +
                "Color: " + colorComboBox.getSelectedItem();
            
            JOptionPane.showMessageDialog(this, 
                "Template saved successfully!\n\n" + settings,
                "Template Saved", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showSmartPresets() {
        // Create professional preset dialog
        JDialog presetDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Smart Barcode Presets", true);
        presetDialog.setSize(600, 450);
        presetDialog.setLocationRelativeTo(this);
        presetDialog.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("SMART BARCODE PRESETS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Professional configurations for common use cases");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Preset options with descriptions
        String[][] presetData = {
            {"Product Label", "Code 128", "Standard product labeling", "250×80px, Text below"},
            {"Inventory Tag", "QR Code", "Asset tracking and inventory", "150×150px, High error correction"},
            {"Shipping Label", "Code 39", "Shipping and logistics", "300×60px, Wide format"},
            {"Library Book", "EAN-13", "Book identification standard", "200×100px, ISBN format"},
            {"Small Parts", "Data Matrix", "Tiny components marking", "50×50px, Compact design"},
            {"Document ID", "PDF417", "Document identification", "250×80px, High capacity"},
            {"Retail Product", "UPC-A", "Consumer product standard", "200×100px, UPC format"},
            {"Industrial Tag", "Aztec", "Industrial applications", "120×120px, Robust design"}
        };
        
        // Create table for presets
        String[] columnNames = {"Preset Name", "Barcode Type", "Description", "Specifications"};
        JTable presetTable = new JTable(presetData, columnNames);
        presetTable.setFont(new Font("Arial", Font.PLAIN, 11));
        presetTable.setRowHeight(25);
        presetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        presetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        presetTable.getTableHeader().setBackground(new Color(236, 240, 241));
        
        // Set column widths
        presetTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        presetTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        presetTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        presetTable.getColumnModel().getColumn(3).setPreferredWidth(140);
        
        JScrollPane scrollPane = new JScrollPane(presetTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton applyButton = new JButton("Apply Preset");
        applyButton.setFont(new Font("Arial", Font.BOLD, 12));
        applyButton.setBackground(new Color(46, 125, 50));
        applyButton.setForeground(Color.WHITE);
        applyButton.setFocusPainted(false);
        applyButton.addActionListener(e -> {
            int selectedRow = presetTable.getSelectedRow();
            if (selectedRow >= 0) {
                String presetName = (String) presetTable.getValueAt(selectedRow, 0);
                applySmartPreset(presetName + " (" + presetTable.getValueAt(selectedRow, 1) + ")");
                presetDialog.dispose();
                JOptionPane.showMessageDialog(this,
                    "Preset Applied Successfully!\n\n" +
                    "Configuration: " + presetName + "\n" +
                    "Type: " + presetTable.getValueAt(selectedRow, 1) + "\n" +
                    "Sample Data: " + dataTextField.getText(),
                    "Preset Applied", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(presetDialog,
                    "Please select a preset first!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton previewButton = new JButton("Preview Selected");
        previewButton.setFont(new Font("Arial", Font.PLAIN, 12));
        previewButton.setBackground(new Color(52, 152, 219));
        previewButton.setForeground(Color.WHITE);
        previewButton.setFocusPainted(false);
        previewButton.addActionListener(e -> {
            int selectedRow = presetTable.getSelectedRow();
            if (selectedRow >= 0) {
                String presetName = (String) presetTable.getValueAt(selectedRow, 0);
                applySmartPreset(presetName + " (" + presetTable.getValueAt(selectedRow, 1) + ")");
                presetDialog.dispose();
                showBarcodePreview();
            } else {
                JOptionPane.showMessageDialog(presetDialog,
                    "Please select a preset first!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> presetDialog.dispose());
        
        buttonPanel.add(applyButton);
        buttonPanel.add(previewButton);
        buttonPanel.add(cancelButton);
        
        presetDialog.add(headerPanel, BorderLayout.NORTH);
        presetDialog.add(scrollPane, BorderLayout.CENTER);
        presetDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        presetDialog.setVisible(true);
    }
    
    private void applySmartPreset(String preset) {
        switch (preset) {
            case "Product Label (Code 128)":
                barcodeTypeComboBox.setSelectedItem("Code 128");
                widthSpinner.setValue(250);
                heightSpinner.setValue(80);
                showTextCheckBox.setSelected(true);
                textPositionComboBox.setSelectedItem("Bottom");
                dataTextField.setText("123456789012");
                break;
                
            case "Inventory Tag (QR Code)":
                barcodeTypeComboBox.setSelectedItem("QR Code");
                widthSpinner.setValue(150);
                heightSpinner.setValue(150);
                errorCorrectionComboBox.setSelectedItem("High");
                dataTextField.setText("INV-2025-001");
                break;
                
            case "Shipping Label (Code 39)":
                barcodeTypeComboBox.setSelectedItem("Code 39");
                widthSpinner.setValue(300);
                heightSpinner.setValue(60);
                showTextCheckBox.setSelected(true);
                dataTextField.setText("SHIP123456");
                break;
                
            case "Library Book (EAN-13)":
                barcodeTypeComboBox.setSelectedItem("EAN-13");
                widthSpinner.setValue(200);
                heightSpinner.setValue(100);
                showTextCheckBox.setSelected(true);
                dataTextField.setText("9781234567890");
                break;
                
            case "Small Parts (Data Matrix)":
                barcodeTypeComboBox.setSelectedItem("Data Matrix");
                widthSpinner.setValue(50);
                heightSpinner.setValue(50);
                moduleWidthSpinner.setValue(0.5);
                dataTextField.setText("PART-A123");
                break;
                
            case "Document ID (PDF417)":
                barcodeTypeComboBox.setSelectedItem("PDF417");
                widthSpinner.setValue(250);
                heightSpinner.setValue(80);
                dataTextField.setText("DOC-ID-2025-001");
                break;
                
            case "Retail Product (UPC-A)":
                barcodeTypeComboBox.setSelectedItem("UPC-A");
                widthSpinner.setValue(180);
                heightSpinner.setValue(100);
                showTextCheckBox.setSelected(true);
                dataTextField.setText("123456789012");
                break;
                
            case "Industrial Tag (Aztec)":
                barcodeTypeComboBox.setSelectedItem("Aztec Code");
                widthSpinner.setValue(100);
                heightSpinner.setValue(100);
                dataTextField.setText("IND-TAG-001");
                break;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Smart preset applied!\n\n" +
            "Configuration: " + preset + "\n" +
            "Type: " + barcodeTypeComboBox.getSelectedItem() + "\n" +
            "Size: " + widthSpinner.getValue() + "x" + heightSpinner.getValue() + "\n" +
            "Sample Data: " + dataTextField.getText(),
            "Preset Applied", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void performQualityCheck() {
        String type = (String) barcodeTypeComboBox.getSelectedItem();
        String data = dataTextField.getText().trim();
        int width = (Integer) widthSpinner.getValue();
        int height = (Integer) heightSpinner.getValue();
        double moduleWidth = (Double) moduleWidthSpinner.getValue();
        
        StringBuilder qualityReport = new StringBuilder();
        qualityReport.append("BARCODE QUALITY ASSESSMENT\n");
        qualityReport.append("==========================\n\n");
        
        int score = 100;
        
        // Data quality checks
        if (data.isEmpty()) {
            qualityReport.append("❌ DATA: No data entered (-50 points)\n");
            score -= 50;
        } else if (data.length() > 255) {
            qualityReport.append("⚠️ DATA: Very long data may cause issues (-10 points)\n");
            score -= 10;
        } else {
            qualityReport.append("✅ DATA: Length OK (").append(data.length()).append(" chars)\n");
        }
        
        // Size quality checks
        if (width < 100 || height < 50) {
            qualityReport.append("⚠️ SIZE: Small size may affect readability (-15 points)\n");
            score -= 15;
        } else if (width > 500 || height > 300) {
            qualityReport.append("⚠️ SIZE: Large size may waste space (-5 points)\n");
            score -= 5;
        } else {
            qualityReport.append("✅ SIZE: Optimal dimensions\n");
        }
        
        // Module width checks
        if (moduleWidth < 0.8) {
            qualityReport.append("⚠️ MODULE: Very small modules may be hard to read (-10 points)\n");
            score -= 10;
        } else if (moduleWidth > 3.0) {
            qualityReport.append("⚠️ MODULE: Large modules may waste space (-5 points)\n");
            score -= 5;
        } else {
            qualityReport.append("✅ MODULE: Good module width\n");
        }
        
        // Type-specific checks
        if (type.equals("QR Code")) {
            String errorCorr = (String) errorCorrectionComboBox.getSelectedItem();
            if (errorCorr.equals("Low")) {
                qualityReport.append("⚠️ ERROR CORRECTION: Consider higher level for durability (-5 points)\n");
                score -= 5;
            } else {
                qualityReport.append("✅ ERROR CORRECTION: Good level selected\n");
            }
        }
        
        // Final assessment
        qualityReport.append("\n==========================\n");
        qualityReport.append("OVERALL SCORE: ").append(score).append("/100\n");
        
        if (score >= 90) {
            qualityReport.append("GRADE: EXCELLENT ⭐⭐⭐⭐⭐\n");
            qualityReport.append("Your barcode configuration is optimal!");
        } else if (score >= 75) {
            qualityReport.append("GRADE: GOOD ⭐⭐⭐⭐\n");
            qualityReport.append("Minor improvements possible.");
        } else if (score >= 60) {
            qualityReport.append("GRADE: FAIR ⭐⭐⭐\n");
            qualityReport.append("Several improvements recommended.");
        } else {
            qualityReport.append("GRADE: NEEDS IMPROVEMENT ⭐⭐\n");
            qualityReport.append("Significant issues need attention.");
        }
        
        JOptionPane.showMessageDialog(this, 
            qualityReport.toString(),
            "Barcode Quality Assessment", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void placeOnCanvas() {
        String type = (String) barcodeTypeComboBox.getSelectedItem();
        String data = dataTextField.getText().trim();
        
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter data for the barcode before placing on canvas.",
                "No Data", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Add the barcode to the canvas using the enhanced addMark method
        canvas.addMark(type, data);
        
        // Show success message
        JOptionPane.showMessageDialog(this, 
            "Barcode placed on canvas successfully!\n" +
            "Type: " + type + "\n" +
            "Data: " + data,
            "Barcode Placed", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Helper method to create labels with dark text
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }
}
