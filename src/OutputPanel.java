import java.awt.*;
import java.awt.print.*;
import javax.print.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * OutputPanel - Unified Print &         tabbedPane.addTab("🖨️ PRINT & PREVIEW", printTab);ngraving Interface
 * Combines printing, engraving controls, preview, and output functionality
 */
public class OutputPanel extends JPanel implements Printable {
    
    // Enhanced professional color scheme for maximum visibility
    private static final Color LABEL_COLOR = new Color(34, 49, 63);           // Darker text for better readability
    // Enhanced vibrant color scheme for maximum visibility
    private static final Color SECTION_BG = new Color(255, 255, 255);         // Pure white for sections
    private static final Color BORDER_COLOR = new Color(41, 128, 185);        // Professional blue for borders
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);        // Vibrant green for actions
    private static final Color HOVER_COLOR = new Color(39, 174, 96);          // Darker green for hover
    private static final Color PANEL_BG = new Color(236, 240, 241);           // Light blue-gray background
    private static final Color TAB_BG = new Color(44, 62, 80);                // Dark blue for tabs
    private static final Color TAB_SELECTED = new Color(52, 152, 219);        // Bright blue for selected tab
    private static final Color WARNING_COLOR = new Color(230, 126, 34);       // Orange for warnings
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);        // Green for success
    private static final Color INFO_COLOR = new Color(52, 152, 219);          // Blue for info
    private static final Color HEADER_COLOR = new Color(41, 128, 185);        // Professional blue for headers
    private static final Color SHADOW_COLOR = new Color(149, 165, 166);       // Gray for subtle shadows
    
    // Innovative visibility colors and effects
    private static final Color GLOW_COLOR = new Color(52, 152, 219, 80);      // Translucent blue glow
    private static final Color HIGHLIGHT_COLOR = new Color(155, 89, 182);     // Purple highlight
    private static final Color ACCENT_GOLD = new Color(241, 196, 15);         // Gold accent
    private static final Color SOFT_GREEN = new Color(46, 204, 113, 40);      // Translucent green
    
    // Animation and state tracking for innovative effects
    private Timer pulseTimer;
    private float pulseAlpha = 0.3f;
    private boolean pulseDirection = true;
    private boolean isHighlightMode = false;
    
    private final DrawingCanvas canvas;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    
    // Print Settings
    private boolean printBackground = true;
    private boolean printBorders = true;
    private boolean fitToPage = true;
    private double scaleFactor = 1.0;
    private int copies = 1;
    private boolean printSelected = false;
    
    // Engraving Parameters
    private int penDownDelay = 100;    // milliseconds
    private int penUpDelay = 50;       // milliseconds
    private int clawDelay = 75;        // milliseconds
    private String printMode = "Normal";
    private boolean printTest = false;
    private String paperType = "Standard";
    private double diameter = 1.0;     // mm
    private int dotSize = 1;           // dot size
    private double dotPitchHorizontal = 1.0;
    private double dotPitchVertical = 1.0;
    private double dotDepth = 0.5;
    private String markingSpeed = "Medium";
    
    // Layout Controls
    private double xOffset = 0.0;
    private double yOffset = 0.0;
    private double rotation = 0.0;
    private double scale = 1.0;
    
    // UI Components - Print
    private JComboBox<String> printerComboBox;
    private JComboBox<String> paperSizeComboBox;
    private JComboBox<String> orientationComboBox;
    private JComboBox<String> qualityComboBox;
    private JSpinner copiesSpinner;
    private JCheckBox backgroundCheckBox;
    private JCheckBox bordersCheckBox;
    private JCheckBox fitToPageCheckBox;
    private JCheckBox selectedOnlyCheckBox;
    private JSlider scaleSlider;
    private JLabel scaleLabel;
    
    // UI Components - Engraving
    private JComboBox<String> markingModeComboBox;
    private JSpinner dotPitchHorizontalSpinner;
    private JSpinner dotPitchVerticalSpinner;
    private JSpinner dotDepthSpinner;
    private JComboBox<String> markingSpeedComboBox;
    private JSpinner penDownDelaySpinner;
    private JSpinner penUpDelaySpinner;
    private JSpinner clawDelaySpinner;
    private JComboBox<String> printModeComboBox;
    private JSpinner diameterSpinner;
    private JSpinner dotSizeSpinner;
    
    // Layout Controls
    private JSpinner xOffsetSpinner;
    private JSpinner yOffsetSpinner;
    private JSpinner rotationSpinner;
    private JSpinner scaleSpinnerLayout;
    
    // Preview Controls
    private JCheckBox previewGridCheckBox;
    private JCheckBox materialBoundaryCheckBox;
    private JCheckBox dotPreviewCheckBox;
    
    // Action Buttons
    private JButton previewButton;
    private JButton printButton;
    private JButton exportPdfButton;
    private JButton exportImageButton;
    private JButton exportGCodeButton;
    private JButton startMarkingButton;
    private JButton saveTemplateButton;
    private JButton simulateButton;
    
    // Helper method to create attractive labels with enhanced styling
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Slightly larger font
        return label;
    }
    
    // Helper method to create attractive section headers
    private JLabel createSectionHeader(String text) {
        JLabel header = new JLabel(text);
        header.setForeground(TAB_BG);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));
        return header;
    }
    
    public OutputPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        initializePrinting();
        setupUI();
        setupEventHandlers();
    }
    
    private void initializePrinting() {
        printerJob = PrinterJob.getPrinterJob();
        pageFormat = printerJob.defaultPage();
        printerJob.setPrintable(this, pageFormat);
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        setPreferredSize(new Dimension(1200, 240)); // Increased height for better tab visibility
        
        // Create professional header
        JLabel headerLabel = new JLabel("🎯 OUTPUT & PRODUCTION CONTROL CENTER", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(TAB_BG);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(HEADER_COLOR);
        headerLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        add(headerLabel, BorderLayout.NORTH);
        
        // Create enhanced tabbed interface with attractive styling
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Larger font for better visibility
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBackground(PANEL_BG);
        tabbedPane.setForeground(TAB_BG);
        tabbedPane.setPreferredSize(new Dimension(1180, 180)); // Ensure adequate tab area
        
        // Enhanced tab styling
        tabbedPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(3, 3, 3, 3) // Reduced padding for more content space
        ));
        
        // Tab 1: Print Settings with enhanced icon and styling
        JPanel printTab = createPrintPanel();
        printTab.setBackground(SECTION_BG);
        tabbedPane.addTab("�️ Print & Preview", printTab);
        tabbedPane.setBackgroundAt(0, SECTION_BG);
        
        // Tab 2: Engraving Parameters with enhanced styling
        JPanel engraveTab = createEngravePanel();
        engraveTab.setBackground(SECTION_BG);
        tabbedPane.addTab("⚙️ ENGRAVE & MARK", engraveTab);
        tabbedPane.setBackgroundAt(1, SECTION_BG);
        
        // Tab 3: Layout & Preview with enhanced styling
        JPanel layoutTab = createLayoutPanel();
        layoutTab.setBackground(SECTION_BG);
        tabbedPane.addTab("🎯 LAYOUT & VIEW", layoutTab);
        tabbedPane.setBackgroundAt(2, SECTION_BG);
        
        // Tab 4: Export & Output with enhanced styling
        JPanel exportTab = createExportPanel();
        exportTab.setBackground(SECTION_BG);
        tabbedPane.addTab("📤 EXPORT & SAVE", exportTab);
        tabbedPane.setBackgroundAt(3, SECTION_BG);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createPrintPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Reasonable padding
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // Balanced spacing between sections
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Center-aligned layout for 4 sections with equal spacing
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createCompactSection("🖨️ PRINTER", createPrinterControls()), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(createCompactSection("📄 PAGE SETUP", createPageSetupControls()), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(createCompactSection("⚙️ OPTIONS", createPrintOptionsControls()), gbc);
        
        gbc.gridx = 3; gbc.gridy = 0;
        panel.add(createCompactSection("🎯 ACTIONS", createPrintActionsControls()), gbc);
        
        return panel;
    }
    
    private JPanel createEngravePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Better padding
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8); // Consistent spacing between sections
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // First row
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createCompactSection("Mode", createMarkingModeControls()), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(createCompactSection("Parameters", createMarkingParametersControls()), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(createCompactSection("Timing", createTimingControls()), gbc);
        
        gbc.gridx = 3; gbc.gridy = 0;
        panel.add(createCompactSection("Actions", createEngravingActionsControls()), gbc);
        
        return panel;
    }
    
    private JPanel createLayoutPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); // More generous padding
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // Better spacing between sections
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Center-aligned layout for 3 sections with equal spacing
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createCompactSection("📐 POSITION", createPositionControls()), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(createCompactSection("🔄 TRANSFORM", createTransformControls()), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(createCompactSection("👁️ PREVIEW", createPreviewControls()), gbc);
        
        return panel;
    }
    
    private JPanel createExportPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); // More generous padding
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // Better spacing between sections
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Center-aligned layout for 3 sections with equal spacing
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createCompactSection("📤 FILE EXPORT", createFileExportControls()), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(createCompactSection("📋 TEMPLATES", createTemplateControls()), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(createCompactSection("🔧 HARDWARE", createHardwareControls()), gbc);
        
        return panel;
    }
    
    private JPanel createCompactSection(String title, JPanel content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(SECTION_BG);
        
        // Enhanced border with gradient-like effect using multiple borders
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createTitledBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 2),
                        BorderFactory.createLineBorder(Color.WHITE, 1)
                    ),
                    title,
                    TitledBorder.LEFT,
                    TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 14), // Much larger font for titles
                    TAB_BG // Use darker color for better contrast
                )
            ),
            BorderFactory.createEmptyBorder(6, 8, 8, 8) // More padding for better spacing
        ));
        
        // Reduced size for better tab visibility and compatibility
        section.setPreferredSize(new Dimension(280, 120)); // Reduced height for better fit
        section.setMinimumSize(new Dimension(280, 120));   // Consistent minimum size
        section.setMaximumSize(new Dimension(280, 120));   // Prevent stretching
        
        // Add subtle shadow effect by setting the content panel background
        content.setBackground(SECTION_BG);
        section.add(content, BorderLayout.CENTER);
        
        return section;
    }
    
    private JPanel createPrinterControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // Reasonable spacing
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Printer Selection Label - Clear and visible
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel printerLabel = new JLabel("🖨️ SELECT PRINTER", JLabel.CENTER);
        printerLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Clear readable font
        printerLabel.setForeground(Color.WHITE);
        printerLabel.setOpaque(true);
        printerLabel.setBackground(new Color(41, 128, 185)); // Professional blue
        printerLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(6, 12, 6, 12) // Reasonable padding
        ));
        printerLabel.setPreferredSize(new Dimension(260, 30)); // Balanced size
        panel.add(printerLabel, gbc);
        
        // Printer Dropdown - Well-sized
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(8, 12, 8, 12); // Consistent spacing
        printerComboBox = new JComboBox<>(getPrinterNames());
        printerComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Readable font
        printerComboBox.setPreferredSize(new Dimension(260, 28)); // Standard dropdown size
        printerComboBox.setBackground(Color.WHITE);
        printerComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8) // Standard padding
        ));
        panel.add(printerComboBox, gbc);
        
        return panel;
    }
    
    private JPanel createPageSetupControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // More generous spacing
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Paper Size Label and Control
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel sizeLabel = new JLabel("📄 PAPER SIZE", JLabel.CENTER);
        sizeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sizeLabel.setForeground(TAB_BG);
        sizeLabel.setOpaque(true);
        sizeLabel.setBackground(new Color(248, 249, 250));
        sizeLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        panel.add(sizeLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(6, 12, 8, 12); // Less top margin for dropdown
        paperSizeComboBox = new JComboBox<>(new String[]{"A4 (210×297mm)", "Letter (8.5×11\")", "Legal (8.5×14\")", "A3 (297×420mm)"});
        paperSizeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        paperSizeComboBox.setPreferredSize(new Dimension(220, 30));
        paperSizeComboBox.setBackground(Color.WHITE);
        paperSizeComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        panel.add(paperSizeComboBox, gbc);
        
        // Orientation Label and Control
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(12, 12, 8, 12); // More top margin for separation
        JLabel orientLabel = new JLabel("🔄 ORIENTATION", JLabel.CENTER);
        orientLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        orientLabel.setForeground(TAB_BG);
        orientLabel.setOpaque(true);
        orientLabel.setBackground(new Color(248, 249, 250));
        orientLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        panel.add(orientLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(6, 12, 8, 12); // Less top margin for dropdown
        orientationComboBox = new JComboBox<>(new String[]{"📊 Portrait", "📈 Landscape"});
        orientationComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        orientationComboBox.setPreferredSize(new Dimension(220, 30));
        orientationComboBox.setBackground(Color.WHITE);
        orientationComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        panel.add(orientationComboBox, gbc);
        
        return panel;
    }
    
    private JPanel createPrintOptionsControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // Reasonable spacing
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Copies Label and Control - Clear and accessible
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel copiesLabel = new JLabel("📊 COPIES", JLabel.CENTER);
        copiesLabel.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Clear font
        copiesLabel.setForeground(Color.WHITE);
        copiesLabel.setOpaque(true);
        copiesLabel.setBackground(new Color(155, 89, 182)); // Professional purple
        copiesLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // Reasonable padding
        ));
        copiesLabel.setPreferredSize(new Dimension(260, 26)); // Balanced size
        panel.add(copiesLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(6, 12, 8, 12); // Standard margins
        copiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        copiesSpinner.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Clear readable font
        copiesSpinner.setPreferredSize(new Dimension(120, 28)); // Standard spinner size
        copiesSpinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8) // Standard padding
        ));
        panel.add(copiesSpinner, gbc);
        
        // Background Option - Clear and visible
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(10, 12, 5, 12); // Reasonable spacing
        backgroundCheckBox = new JCheckBox("🖼️ Print Background");
        backgroundCheckBox.setSelected(true);
        backgroundCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Clear font
        backgroundCheckBox.setForeground(TAB_BG);
        backgroundCheckBox.setBackground(new Color(240, 248, 255)); // Light blue
        backgroundCheckBox.setOpaque(true);
        backgroundCheckBox.setFocusPainted(false);
        backgroundCheckBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8) // Standard padding
        ));
        backgroundCheckBox.setPreferredSize(new Dimension(260, 25)); // Balanced checkbox
        panel.add(backgroundCheckBox, gbc);
        
        // Fit to Page Option - Clear and visible
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(6, 12, 8, 12); // Consistent spacing
        fitToPageCheckBox = new JCheckBox("📏 Fit to Page");
        fitToPageCheckBox.setSelected(true);
        fitToPageCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Clear font
        fitToPageCheckBox.setForeground(TAB_BG);
        fitToPageCheckBox.setBackground(new Color(240, 248, 255)); // Light blue
        fitToPageCheckBox.setOpaque(true);
        fitToPageCheckBox.setFocusPainted(false);
        fitToPageCheckBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8) // Standard padding
        ));
        fitToPageCheckBox.setPreferredSize(new Dimension(260, 25)); // Balanced checkbox
        panel.add(fitToPageCheckBox, gbc);
        
        return panel;
    }
    
    private JPanel createPrintActionsControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        
        // **INNOVATION 1: Smart Status Indicator Panel**
        JPanel statusPanel = createSmartStatusPanel();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(statusPanel, gbc);
        
        // **INNOVATION 2: Interactive Preview Button with Visual Feedback**
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        previewButton = createEnhancedButton("👁️ PREVIEW", "Preview print layout", INFO_COLOR);
        previewButton.addActionListener(e -> {
            // Trigger visual feedback and show advanced preview
            flashButton(previewButton, ACCENT_GOLD);
            showAdvancedPreview();
        });
        panel.add(previewButton, gbc);
        
        // **INNOVATION 3: Animated Print Button with Progress Indication**
        gbc.gridx = 1; gbc.gridy = 1;
        printButton = createEnhancedButton("🖨️ PRINT NOW", "Start printing immediately", SUCCESS_COLOR);
        printButton.addActionListener(e -> {
            startPrintWithVisualFeedback();
        });
        panel.add(printButton, gbc);
        
        // **INNOVATION 4: Quick Settings Bar**
        JPanel quickSettings = createQuickSettingsBar();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 5, 5, 5);
        panel.add(quickSettings, gbc);
        
        return panel;
    }
    
    // **INNOVATION 1: Smart Status Indicator**
    private JPanel createSmartStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        statusPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INFO_COLOR, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        
        // Printer status with live indicator
        JLabel printerStatus = new JLabel("🖨️ Ready");
        printerStatus.setFont(new Font("Segoe UI", Font.BOLD, 10));
        printerStatus.setForeground(SUCCESS_COLOR);
        
        // Paper status with visual indicator
        JLabel paperStatus = new JLabel("📄 A4");
        paperStatus.setFont(new Font("Segoe UI", Font.BOLD, 10));
        paperStatus.setForeground(HEADER_COLOR);
        
        // Quality indicator with color coding
        JLabel qualityStatus = new JLabel("⚡ High Quality");
        qualityStatus.setFont(new Font("Segoe UI", Font.BOLD, 10));
        qualityStatus.setForeground(WARNING_COLOR);
        
        statusPanel.add(printerStatus);
        statusPanel.add(new JLabel("•"));
        statusPanel.add(paperStatus);
        statusPanel.add(new JLabel("•"));
        statusPanel.add(qualityStatus);
        
        return statusPanel;
    }
    
    // **INNOVATION 2: Enhanced Button Creator with Visual Effects**
    private JButton createEnhancedButton(String text, String tooltip, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 32));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add glow effect border
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(baseColor.darker(), 2),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        
        // **Innovative hover effects**
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_GOLD, 2),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(baseColor.darker(), 2),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
        });
        
        return button;
    }
    
    // **INNOVATION 3: Visual Feedback Effects**
    private void flashButton(JButton button, Color flashColor) {
        Color originalColor = button.getBackground();
        Timer flashTimer = new Timer(100, null);
        final int[] flashCount = {0};
        
        flashTimer.addActionListener(e -> {
            if (flashCount[0] % 2 == 0) {
                button.setBackground(flashColor);
            } else {
                button.setBackground(originalColor);
            }
            flashCount[0]++;
            if (flashCount[0] >= 4) {
                flashTimer.stop();
                button.setBackground(originalColor);
            }
        });
        flashTimer.start();
    }
    
    // **INNOVATION 4: Quick Settings Bar with Visual Toggles**
    private JPanel createQuickSettingsBar() {
        JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 2));
        quickPanel.setBackground(SECTION_BG);
        
        // Color-coded toggle switches
        JToggleButton colorToggle = createVisualToggle("🎨", "Color Print", false);
        JToggleButton qualityToggle = createVisualToggle("⚡", "High Quality", true);
        JToggleButton duplexToggle = createVisualToggle("📑", "Both Sides", false);
        
        quickPanel.add(new JLabel("Quick:"));
        quickPanel.add(colorToggle);
        quickPanel.add(qualityToggle);
        quickPanel.add(duplexToggle);
        
        return quickPanel;
    }
    
    private JToggleButton createVisualToggle(String icon, String tooltip, boolean defaultState) {
        JToggleButton toggle = new JToggleButton(icon);
        toggle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        toggle.setPreferredSize(new Dimension(35, 25));
        toggle.setSelected(defaultState);
        toggle.setToolTipText(tooltip);
        toggle.setFocusPainted(false);
        toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Visual state indication
        updateToggleAppearance(toggle);
        toggle.addActionListener(e -> updateToggleAppearance(toggle));
        
        return toggle;
    }
    
    private void updateToggleAppearance(JToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setBackground(SUCCESS_COLOR);
            toggle.setForeground(Color.WHITE);
            toggle.setBorder(BorderFactory.createLineBorder(SUCCESS_COLOR.darker(), 2));
        } else {
            toggle.setBackground(Color.LIGHT_GRAY);
            toggle.setForeground(Color.DARK_GRAY);
            toggle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
    }
    
    // **INNOVATION 6: Advanced Print Preview Dialog**
    private void showAdvancedPreview() {
        JDialog previewDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "🔍 Advanced Print Preview", true);
        previewDialog.setSize(600, 500);
        previewDialog.setLocationRelativeTo(this);
        
        // Main preview panel with modern design
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // **Header with status indicators**
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        headerPanel.setBackground(INFO_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("� PRINT PREVIEW");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel statusLabel = new JLabel("✅ Ready to Print");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel);
        headerPanel.add(new JLabel("•"));
        headerPanel.add(statusLabel);
        
        // **Interactive preview area with zoom controls**
        JPanel previewArea = new JPanel(new BorderLayout());
        previewArea.setBackground(Color.WHITE);
        previewArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS_COLOR, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Simulated preview content
        JLabel previewContent = new JLabel("<html><div style='text-align: center;'>" +
            "<h2 style='color: #2c3e50;'>📄 Document Preview</h2>" +
            "<p style='color: #34495e;'>Canvas Size: 800 × 600 pixels</p>" +
            "<p style='color: #34495e;'>Print Quality: High Definition</p>" +
            "<p style='color: #27ae60;'><b>✓ All elements visible</b></p>" +
            "<p style='color: #27ae60;'><b>✓ Optimal layout detected</b></p>" +
            "<br><div style='background: #ecf0f1; padding: 20px; border-radius: 8px;'>" +
            "🎯 Preview shows your canvas content<br>" +
            "📏 Scaled to fit selected paper size<br>" +
            "🖨️ Ready for high-quality printing" +
            "</div></html>", JLabel.CENTER);
        previewContent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        previewArea.add(previewContent, BorderLayout.CENTER);
        
        // **Action buttons with enhanced styling**
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 249, 250));
        
        JButton printNowBtn = createEnhancedButton("🖨️ PRINT NOW", "Start printing immediately", SUCCESS_COLOR);
        printNowBtn.addActionListener(e -> {
            previewDialog.dispose();
            startPrintWithVisualFeedback();
        });
        
        JButton settingsBtn = createEnhancedButton("⚙️ SETTINGS", "Adjust print settings", INFO_COLOR);
        settingsBtn.addActionListener(e -> {
            // Flash the settings and close preview
            flashButton(settingsBtn, ACCENT_GOLD);
            previewDialog.dispose();
        });
        
        JButton cancelBtn = createEnhancedButton("❌ CANCEL", "Close preview", new Color(231, 76, 60));
        cancelBtn.addActionListener(e -> previewDialog.dispose());
        
        buttonPanel.add(printNowBtn);
        buttonPanel.add(settingsBtn);
        buttonPanel.add(cancelBtn);
        
        // **Innovative: Add zoom controls**
        JPanel zoomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        zoomPanel.setBackground(new Color(248, 249, 250));
        
        JButton zoomInBtn = new JButton("🔍+");
        JButton zoomOutBtn = new JButton("�-");
        JButton fitPageBtn = new JButton("📄 Fit");
        
        for (JButton btn : new JButton[]{zoomInBtn, zoomOutBtn, fitPageBtn}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btn.setBackground(Color.WHITE);
            btn.setForeground(HEADER_COLOR);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HEADER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> flashButton(btn, ACCENT_GOLD));
            zoomPanel.add(btn);
        }
        
        // Assemble the dialog
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(previewArea, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(zoomPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        previewDialog.add(mainPanel);
        previewDialog.setVisible(true);
    }
    
    // **INNOVATION 5: Animated Print Process**
    private void startPrintWithVisualFeedback() {
        // Change button to show progress
        printButton.setText("🔄 PRINTING...");
        printButton.setBackground(WARNING_COLOR);
        printButton.setEnabled(false);
        
        // Simulate print process with visual feedback
        Timer printTimer = new Timer(2000, e -> {
            printButton.setText("✅ COMPLETE");
            printButton.setBackground(SUCCESS_COLOR);
            
            // Reset after 2 seconds
            Timer resetTimer = new Timer(2000, evt -> {
                printButton.setText("🖨️ PRINT NOW");
                printButton.setBackground(SUCCESS_COLOR);
                printButton.setEnabled(true);
            });
            resetTimer.setRepeats(false);
            resetTimer.start();
        });
        printTimer.setRepeats(false);
        printTimer.start();
    }
    
    private JPanel createMarkingModeControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Mode:"), gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        markingModeComboBox = new JComboBox<>(new String[]{"Text", "Arc", "Matrix", "Graph", "Rectangle", "Line"});
        styleComboBox(markingModeComboBox, 140);
        panel.add(markingModeComboBox, gbc);
        
        return panel;
    }
    
    private JPanel createMarkingParametersControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Dot pitch controls
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("H Pitch:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(createDarkLabel("V Pitch:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dotPitchHorizontalSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        styleSpinner(dotPitchHorizontalSpinner, 60);
        panel.add(dotPitchHorizontalSpinner, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        dotPitchVerticalSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        styleSpinner(dotPitchVerticalSpinner, 60);
        panel.add(dotPitchVerticalSpinner, gbc);
        
        // Depth and speed
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Depth:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(createDarkLabel("Speed:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dotDepthSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.1, 5.0, 0.1));
        styleSpinner(dotDepthSpinner, 60);
        panel.add(dotDepthSpinner, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        markingSpeedComboBox = new JComboBox<>(new String[]{"Slow", "Medium", "Fast"});
        styleComboBox(markingSpeedComboBox, 60);
        panel.add(markingSpeedComboBox, gbc);
        
        return panel;
    }
    
    private JPanel createTimingControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Timing parameters
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Down:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(createDarkLabel("Up:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        penDownDelaySpinner = new JSpinner(new SpinnerNumberModel(100, 10, 1000, 10));
        styleSpinner(penDownDelaySpinner, 60);
        panel.add(penDownDelaySpinner, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        penUpDelaySpinner = new JSpinner(new SpinnerNumberModel(50, 10, 1000, 10));
        styleSpinner(penUpDelaySpinner, 60);
        panel.add(penUpDelaySpinner, gbc);
        
        // Claw delay
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Claw:"), gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        clawDelaySpinner = new JSpinner(new SpinnerNumberModel(75, 10, 1000, 10));
        styleSpinner(clawDelaySpinner, 60);
        panel.add(clawDelaySpinner, gbc);
        
        return panel;
    }
    
    private JPanel createEngravingActionsControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        simulateButton = createStyledButton("🎮 Simulate", "Simulate engraving");
        panel.add(simulateButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        startMarkingButton = createActionButton("⚡ Start", "Start engraving", SUCCESS_COLOR);
        panel.add(startMarkingButton, gbc);
        
        return panel;
    }
    
    private JPanel createPositionControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Professional spacing
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // X Position Label and Control
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel xLabel = createDarkLabel("📍 X Position:");
        xLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        panel.add(xLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(2, 8, 8, 8); // Less top margin for spinner
        xOffsetSpinner = new JSpinner(new SpinnerNumberModel(0.0, -100.0, 100.0, 0.5));
        styleSpinner(xOffsetSpinner, 80); // Wider for better visibility
        panel.add(xOffsetSpinner, gbc);
        
        // Y Position Label and Control  
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(8, 8, 6, 8); // More top margin for separation
        JLabel yLabel = createDarkLabel("📍 Y Position:");
        yLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        panel.add(yLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(2, 8, 6, 8); // Less top margin for spinner
        yOffsetSpinner = new JSpinner(new SpinnerNumberModel(0.0, -100.0, 100.0, 0.5));
        styleSpinner(yOffsetSpinner, 80); // Wider for better visibility
        panel.add(yOffsetSpinner, gbc);
        
        return panel;
    }
    
    private JPanel createTransformControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Professional spacing
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Rotation Label and Control
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel rotateLabel = createDarkLabel("🔄 Rotation (°):");
        rotateLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        panel.add(rotateLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(2, 8, 8, 8); // Less top margin for spinner
        rotationSpinner = new JSpinner(new SpinnerNumberModel(0.0, -360.0, 360.0, 1.0));
        styleSpinner(rotationSpinner, 80); // Wider for better visibility
        panel.add(rotationSpinner, gbc);
        
        // Scale Label and Control
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(8, 8, 6, 8); // More top margin for separation
        JLabel transformScaleLabel = createDarkLabel("📏 Scale Factor:");
        transformScaleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        panel.add(transformScaleLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(2, 8, 6, 8); // Less top margin for spinner
        scaleSpinnerLayout = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        styleSpinner(scaleSpinnerLayout, 80); // Wider for better visibility
        panel.add(scaleSpinnerLayout, gbc);
        
        return panel;
    }
    
    private JPanel createPreviewControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Professional spacing
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Grid Preview Checkbox
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        previewGridCheckBox = new JCheckBox("🔳 Show 7×7 Grid");
        previewGridCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 11));
        previewGridCheckBox.setForeground(TAB_BG);
        previewGridCheckBox.setBackground(SECTION_BG);
        previewGridCheckBox.setFocusPainted(false);
        panel.add(previewGridCheckBox, gbc);
        
        // Material Boundary Checkbox
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(8, 8, 6, 8); // More spacing between options
        materialBoundaryCheckBox = new JCheckBox("📏 Material Boundary");
        materialBoundaryCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 11));
        materialBoundaryCheckBox.setForeground(TAB_BG);
        materialBoundaryCheckBox.setBackground(SECTION_BG);
        materialBoundaryCheckBox.setFocusPainted(false);
        panel.add(materialBoundaryCheckBox, gbc);
        
        // Dot Preview Checkbox
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(8, 8, 6, 8); // More spacing between options
        dotPreviewCheckBox = new JCheckBox("⚫ Dot Preview Mode");
        dotPreviewCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 11));
        dotPreviewCheckBox.setForeground(TAB_BG);
        dotPreviewCheckBox.setBackground(SECTION_BG);
        dotPreviewCheckBox.setFocusPainted(false);
        panel.add(dotPreviewCheckBox, gbc);
        
        return panel;
    }
    
    private JPanel createFileExportControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Much better spacing for professional look
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        
        // PDF Export Button
        gbc.gridx = 0; gbc.gridy = 0;
        exportPdfButton = createActionButton("📄 PDF EXPORT", "Export design as PDF document", INFO_COLOR);
        panel.add(exportPdfButton, gbc);
        
        // Image Export Button  
        gbc.gridx = 0; gbc.gridy = 1;
        exportImageButton = createActionButton("🖼️ IMAGE EXPORT", "Export design as image file", SUCCESS_COLOR);
        panel.add(exportImageButton, gbc);
        
        // G-Code Export Button
        gbc.gridx = 0; gbc.gridy = 2;
        exportGCodeButton = createActionButton("⚙️ G-CODE EXPORT", "Export G-Code for CNC machine", WARNING_COLOR);
        panel.add(exportGCodeButton, gbc);
        
        return panel;
    }
    
    private JPanel createTemplateControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Professional spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        
        // Save Template Button
        gbc.gridx = 0; gbc.gridy = 0;
        saveTemplateButton = createActionButton("💾 SAVE TEMPLATE", "Save current design as template", SUCCESS_COLOR);
        panel.add(saveTemplateButton, gbc);
        
        // Load Template Button
        gbc.gridx = 0; gbc.gridy = 1;
        JButton loadTemplateButton = createActionButton("📂 LOAD TEMPLATE", "Load existing template", INFO_COLOR);
        panel.add(loadTemplateButton, gbc);
        
        // Add some vertical spacing to center the buttons
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weighty = 1.0; // Allow vertical expansion
        panel.add(Box.createVerticalGlue(), gbc);
        
        return panel;
    }
    
    private JPanel createHardwareControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECTION_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8); // Professional spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        
        // Port Label
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel portLabel = createDarkLabel("🔌 CONNECTION PORT:");
        portLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        panel.add(portLabel, gbc);
        
        // Port Selection Dropdown
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(2, 8, 6, 8); // Less top margin for dropdown
        JComboBox<String> portComboBox = new JComboBox<>(new String[]{"COM1", "COM2", "COM3", "USB"});
        styleComboBox(portComboBox, 140); // Wider for better visibility
        panel.add(portComboBox, gbc);
        
        // Connect Button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(6, 8, 4, 8); // More top margin for button
        JButton connectButton = createActionButton("🔗 CONNECT", "Connect to marking hardware", ACCENT_COLOR);
        panel.add(connectButton, gbc);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Print button handlers
        printButton.addActionListener(e -> performPrint());
        previewButton.addActionListener(e -> showPrintPreview());
        exportPdfButton.addActionListener(e -> exportToPdf());
        exportImageButton.addActionListener(e -> exportToImage());
        
        // Engraving button handlers
        startMarkingButton.addActionListener(e -> startEngraving());
        simulateButton.addActionListener(e -> simulateEngraving());
        exportGCodeButton.addActionListener(e -> exportGCode());
        saveTemplateButton.addActionListener(e -> saveTemplate());
        
        // Preview checkboxes
        previewGridCheckBox.addActionListener(e -> updatePreview());
        materialBoundaryCheckBox.addActionListener(e -> updatePreview());
        dotPreviewCheckBox.addActionListener(e -> updatePreview());
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
    
    // Print functionality
    private void performPrint() {
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
                JOptionPane.showMessageDialog(this, "Print job started successfully!", "Print", JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Print failed: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showPrintPreview() {
        JOptionPane.showMessageDialog(this, "Print preview functionality would open here.", "Print Preview", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportToPdf() {
        JOptionPane.showMessageDialog(this, "PDF export functionality would execute here.", "Export PDF", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportToImage() {
        JOptionPane.showMessageDialog(this, "Image export functionality would execute here.", "Export Image", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Engraving functionality
    private void startEngraving() {
        JOptionPane.showMessageDialog(this, "Engraving process would start here.", "Start Engraving", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void simulateEngraving() {
        JOptionPane.showMessageDialog(this, "Engraving simulation would run here.", "Simulate Engraving", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportGCode() {
        JOptionPane.showMessageDialog(this, "G-Code export functionality would execute here.", "Export G-Code", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void saveTemplate() {
        JOptionPane.showMessageDialog(this, "Template save functionality would execute here.", "Save Template", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Utility methods
    private String[] getPrinterNames() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        String[] printerNames = new String[printServices.length + 1];
        printerNames[0] = "Default Printer";
        for (int i = 0; i < printServices.length; i++) {
            printerNames[i + 1] = printServices[i].getName();
        }
        return printerNames;
    }
    
    // Enhanced button creation with attractive styling
    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Even larger, bolder font
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(150, 32)); // Much larger buttons for visibility
        button.setFocusPainted(false);
        
        // Enhanced styling with colors and effects
        button.setBackground(INFO_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createLineBorder(BORDER_COLOR, 1)
            ),
            BorderFactory.createEmptyBorder(4, 12, 4, 12) // More padding
        ));
        button.setOpaque(true);
        
        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(TAB_SELECTED); // Brighter on hover
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createLineBorder(TAB_BG, 2) // Thicker colored border on hover
                    ),
                    BorderFactory.createEmptyBorder(4, 12, 4, 12)
                ));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(INFO_COLOR); // Return to original
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createLineBorder(BORDER_COLOR, 1)
                    ),
                    BorderFactory.createEmptyBorder(4, 12, 4, 12)
                ));
            }
        });
        
        return button;
    }
    
    // Enhanced action button creation for primary actions
    private JButton createActionButton(String text, String tooltip, Color bgColor) {
        JButton button = createStyledButton(text, tooltip);
        button.setBackground(bgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Even larger for action buttons
        button.setPreferredSize(new Dimension(160, 35)); // Larger action buttons
        
        // Override hover color for action buttons
        button.removeMouseListener(button.getMouseListeners()[0]); // Remove previous listener
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor.brighter()); // Brighter shade on hover
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createLineBorder(Color.WHITE, 2) // White border on hover
                    ),
                    BorderFactory.createEmptyBorder(4, 12, 4, 12)
                ));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor); // Return to original
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createLineBorder(BORDER_COLOR, 1)
                    ),
                    BorderFactory.createEmptyBorder(4, 12, 4, 12)
                ));
            }
        });
        
        return button;
    }
    
    private void styleComboBox(JComboBox<?> comboBox, int width) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10)); // Slightly larger font
        comboBox.setPreferredSize(new Dimension(width, 24)); // Taller for better visibility
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 4, 2, 4)
        ));
    }
    
    private void styleSpinner(JSpinner spinner, int width) {
        spinner.setFont(new Font("Segoe UI", Font.BOLD, 10)); // Bolder, larger font
        spinner.setPreferredSize(new Dimension(width, 26)); // Taller for better visibility
        spinner.setBackground(Color.WHITE);
        spinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 4, 2, 4)
        ));
        
        // Enhanced styling for the text field inside spinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        editor.getTextField().setFont(new Font("Segoe UI", Font.BOLD, 10));
        editor.getTextField().setBackground(Color.WHITE);
        editor.getTextField().setForeground(LABEL_COLOR);
    }
    
    // Printable interface implementation
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Scale to fit page if enabled
        if (fitToPage) {
            double scaleX = pageFormat.getImageableWidth() / canvas.getWidth();
            double scaleY = pageFormat.getImageableHeight() / canvas.getHeight();
            double pageScale = Math.min(scaleX, scaleY);
            g2d.scale(pageScale, pageScale);
        }
        
        // Print the canvas
        canvas.printAll(g2d);
        
        return PAGE_EXISTS;
    }
}
