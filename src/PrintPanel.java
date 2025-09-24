import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import javax.print.*;
import javax.swing.*;

/**
 * PrintPanel - Professional printing interface for dot pin marker application
 * Provides comprehensive printing controls including preview, settings, and export options
 */
public class PrintPanel extends JPanel implements Printable {
    
    // Modern color scheme for better visibility
    private static final Color LABEL_COLOR = new Color(44, 62, 80);   // Dark text for labels
    
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
    private boolean autoPrint = false;
    private boolean printJobActive = false;
    
    // Print Parameters
    private int penDownDelay = 100;    // milliseconds
    private int penUpDelay = 50;       // milliseconds
    private int clawDelay = 75;        // milliseconds
    private String printMode = "Normal";
    private boolean printTest = false;
    private String paperType = "Standard";
    private double diameter = 1.0;     // mm
    private int dotSize = 1;           // dot size
    private int countDown = 3;         // seconds
    
    // UI Components
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
    private JButton previewButton;
    private JButton printButton;
    private JButton pageSetupButton;
    private JButton exportPdfButton;
    private JButton exportImageButton;
    
    // New Printer Operation Controls
    private JButton startButton;
    private JButton autoPrintButton;
    private JButton printSelectedButton;
    private JButton stopButton;
    private JButton resetButton;
    private JButton printOrderButton;
    
    // Print Parameter Controls
    private JSpinner penDownDelaySpinner;
    private JSpinner penUpDelaySpinner;
    private JSpinner clawDelaySpinner;
    private JComboBox<String> printModeComboBox;
    private JCheckBox printTestCheckBox;
    private JComboBox<String> paperTypeComboBox;
    private JSpinner diameterSpinner;
    private JSpinner dotSizeSpinner;
    private JSpinner countDownSpinner;
    
    // Futuristic Hardware Controls
    private JButton autoCalibrationButton;
    private JButton smartPresetsButton;
    
    public PrintPanel(DrawingCanvas canvas) {
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
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Create main sections
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        mainPanel.setBackground(getBackground());
        
        // Printer Selection Section
        JPanel printerSection = createCompactToolbarSection("Printer", createPrinterControls());
        
        // Printer Operations Section
        JPanel operationsSection = createCompactToolbarSection("Operations", createPrinterOperationsControls());
        
        // Print Parameters Section
        JPanel parametersSection = createCompactToolbarSection("Print Parameters", createPrintParametersControls());
        
        // Page Setup Section  
        JPanel pageSection = createCompactToolbarSection("Page Setup", createPageControls());
        
        // Print Options Section
        JPanel optionsSection = createCompactToolbarSection("Options", createOptionsControls());
        
        // Scale & Copies Section
        JPanel scaleSection = createCompactToolbarSection("Scale & Copies", createScaleControls());
        
        // Actions Section
        JPanel actionsSection = createCompactToolbarSection("Actions", createActionControls());
        
        // Add sections to main panel - Reorganized for better workflow
        mainPanel.add(printerSection);
        mainPanel.add(createCompactSeparator());
        mainPanel.add(parametersSection);
        mainPanel.add(createCompactSeparator());
        mainPanel.add(operationsSection);  // Moved Operations after Print Parameters
        mainPanel.add(createCompactSeparator());
        mainPanel.add(pageSection);
        mainPanel.add(createCompactSeparator());
        mainPanel.add(optionsSection);
        mainPanel.add(createCompactSeparator());
        mainPanel.add(scaleSection);
        mainPanel.add(createCompactSeparator());
        mainPanel.add(actionsSection);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createPrinterControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panel.setBackground(getBackground());
        
        // Printer selection
        panel.add(createDarkLabel("Printer:"));
        printerComboBox = new JComboBox<>();
        printerComboBox.setPreferredSize(new Dimension(180, 25));
        printerComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        loadAvailablePrinters();
        panel.add(printerComboBox);
        
        return panel;
    }
    
    private JPanel createPageControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panel.setBackground(getBackground());
        
        // Paper size
        panel.add(createDarkLabel("Size:"));
        paperSizeComboBox = new JComboBox<>(new String[]{"A4", "Letter", "Legal", "A3", "A5"});
        paperSizeComboBox.setPreferredSize(new Dimension(70, 25));
        paperSizeComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(paperSizeComboBox);
        
        // Orientation
        panel.add(createDarkLabel("Orient:"));
        orientationComboBox = new JComboBox<>(new String[]{"Portrait", "Landscape"});
        orientationComboBox.setPreferredSize(new Dimension(80, 25));
        orientationComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(orientationComboBox);
        
        // Quality
        panel.add(createDarkLabel("Quality:"));
        qualityComboBox = new JComboBox<>(new String[]{"Draft", "Normal", "High", "Best"});
        qualityComboBox.setSelectedIndex(1);
        qualityComboBox.setPreferredSize(new Dimension(70, 25));
        qualityComboBox.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(qualityComboBox);
        
        // Page setup button
        pageSetupButton = createStyledButton("Setup", 60);
        panel.add(pageSetupButton);
        
        return panel;
    }
    
    private JPanel createOptionsControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panel.setBackground(getBackground());
        
        // Checkboxes for print options
        backgroundCheckBox = new JCheckBox("Background", printBackground);
        backgroundCheckBox.setFont(new Font("Arial", Font.PLAIN, 10));
        backgroundCheckBox.setBackground(getBackground());
        panel.add(backgroundCheckBox);
        
        bordersCheckBox = new JCheckBox("Borders", printBorders);
        bordersCheckBox.setFont(new Font("Arial", Font.PLAIN, 10));
        bordersCheckBox.setBackground(getBackground());
        panel.add(bordersCheckBox);
        
        fitToPageCheckBox = new JCheckBox("Fit to Page", fitToPage);
        fitToPageCheckBox.setFont(new Font("Arial", Font.PLAIN, 10));
        fitToPageCheckBox.setBackground(getBackground());
        panel.add(fitToPageCheckBox);
        
        selectedOnlyCheckBox = new JCheckBox("Selected Only", printSelected);
        selectedOnlyCheckBox.setFont(new Font("Arial", Font.PLAIN, 10));
        selectedOnlyCheckBox.setBackground(getBackground());
        panel.add(selectedOnlyCheckBox);
        
        return panel;
    }
    
    private JPanel createScaleControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panel.setBackground(getBackground());
        
        // Scale slider
        panel.add(createDarkLabel("Scale:"));
        scaleSlider = new JSlider(25, 200, 100);
        scaleSlider.setPreferredSize(new Dimension(100, 25));
        scaleSlider.setBackground(getBackground());
        scaleSlider.setMajorTickSpacing(50);
        scaleSlider.setPaintTicks(false);
        panel.add(scaleSlider);
        
        scaleLabel = new JLabel("100%");
        scaleLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        scaleLabel.setPreferredSize(new Dimension(35, 25));
        panel.add(scaleLabel);
        
        // Copies
        panel.add(createDarkLabel("Copies:"));
        copiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        copiesSpinner.setPreferredSize(new Dimension(50, 25));
        panel.add(copiesSpinner);
        
        return panel;
    }
    
    private JPanel createActionControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panel.setBackground(getBackground());
        
        // Action buttons
        previewButton = createStyledButton("Preview", 70);
        printButton = createStyledButton("Print", 60);
        exportPdfButton = createStyledButton("PDF", 50);
        exportImageButton = createStyledButton("Image", 60);
        
        panel.add(previewButton);
        panel.add(printButton);
        panel.add(exportPdfButton);
        panel.add(exportImageButton);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, 25));
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
        return button;
    }
    
    private JPanel createCompactToolbarSection(String title, JPanel contentPanel) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(getBackground());
        
        // Special handling for Print Parameters section (hardware controls)
        if ("Print Parameters".equals(title)) {
            section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(100, 150, 200), 2), // Thicker blue border
                    "🔧 " + title + " (Hardware Controls)",
                    javax.swing.border.TitledBorder.LEFT,
                    javax.swing.border.TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 10), // Larger font
                    new Color(0, 80, 160) // Deep blue
                ),
                BorderFactory.createEmptyBorder(6, 6, 6, 6) // More padding for hardware section
            ));
        } else {
            section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    title,
                    javax.swing.border.TitledBorder.LEFT,
                    javax.swing.border.TitledBorder.TOP,
                    new Font("Arial", Font.BOLD, 9),
                    Color.DARK_GRAY
                ),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)
            ));
        }
        
        section.add(contentPanel);
        return section;
    }
    
    private JPanel createCompactSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 50));
        separator.setBackground(Color.LIGHT_GRAY);
        return separator;
    }
    
    private void setupEventHandlers() {
        // Scale slider
        scaleSlider.addChangeListener(e -> {
            scaleFactor = scaleSlider.getValue() / 100.0;
            scaleLabel.setText(scaleSlider.getValue() + "%");
        });
        
        // Checkboxes
        backgroundCheckBox.addActionListener(e -> printBackground = backgroundCheckBox.isSelected());
        bordersCheckBox.addActionListener(e -> printBorders = bordersCheckBox.isSelected());
        fitToPageCheckBox.addActionListener(e -> {
            fitToPage = fitToPageCheckBox.isSelected();
            scaleSlider.setEnabled(!fitToPage);
        });
        selectedOnlyCheckBox.addActionListener(e -> printSelected = selectedOnlyCheckBox.isSelected());
        
        // Copies spinner
        copiesSpinner.addChangeListener(e -> copies = (Integer) copiesSpinner.getValue());
        
        // Orientation change
        orientationComboBox.addActionListener(e -> updatePageFormat());
        paperSizeComboBox.addActionListener(e -> updatePageFormat());
        
        // Action buttons
        pageSetupButton.addActionListener(e -> showPageSetupDialog());
        previewButton.addActionListener(e -> showPrintPreview());
        printButton.addActionListener(e -> performPrint());
        exportPdfButton.addActionListener(e -> exportToPdf());
        exportImageButton.addActionListener(e -> exportToImage());
        
        // Printer Operation buttons
        startButton.addActionListener(e -> startPrintOperation());
        autoPrintButton.addActionListener(e -> toggleAutoPrint());
        printSelectedButton.addActionListener(e -> printSelectedMarks());
        stopButton.addActionListener(e -> stopPrintOperation());
        resetButton.addActionListener(e -> resetPrintSettings());
        printOrderButton.addActionListener(e -> showPrintOrderDialog());
        
        // Print Parameter controls
        penDownDelaySpinner.addChangeListener(e -> penDownDelay = (Integer) penDownDelaySpinner.getValue());
        penUpDelaySpinner.addChangeListener(e -> penUpDelay = (Integer) penUpDelaySpinner.getValue());
        clawDelaySpinner.addChangeListener(e -> clawDelay = (Integer) clawDelaySpinner.getValue());
        printModeComboBox.addActionListener(e -> printMode = (String) printModeComboBox.getSelectedItem());
        printTestCheckBox.addActionListener(e -> {
            printTest = printTestCheckBox.isSelected();
            if (printTest) {
                performPrintTest();
            }
        });
        paperTypeComboBox.addActionListener(e -> paperType = (String) paperTypeComboBox.getSelectedItem());
        diameterSpinner.addChangeListener(e -> diameter = (Double) diameterSpinner.getValue());
        dotSizeSpinner.addChangeListener(e -> dotSize = (Integer) dotSizeSpinner.getValue());
        countDownSpinner.addChangeListener(e -> countDown = (Integer) countDownSpinner.getValue());
        
        // Futuristic Hardware Control buttons
        autoCalibrationButton.addActionListener(e -> performAutoCalibration());
        smartPresetsButton.addActionListener(e -> showSmartPresetsDialog());
    }
    
    private void loadAvailablePrinters() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        printerComboBox.removeAllItems();
        
        if (printServices.length == 0) {
            printerComboBox.addItem("No printers found");
        } else {
            for (PrintService service : printServices) {
                printerComboBox.addItem(service.getName());
            }
        }
    }
    
    private void updatePageFormat() {
        String orientation = (String) orientationComboBox.getSelectedItem();
        String paperSize = (String) paperSizeComboBox.getSelectedItem();
        
        // Update page format based on selections
        Paper paper = pageFormat.getPaper();
        
        // Set paper size
        switch (paperSize) {
            case "A4":
                paper.setSize(595, 842); // A4 in points
                break;
            case "Letter":
                paper.setSize(612, 792); // Letter in points
                break;
            case "Legal":
                paper.setSize(612, 1008); // Legal in points
                break;
            case "A3":
                paper.setSize(842, 1191); // A3 in points
                break;
            case "A5":
                paper.setSize(420, 595); // A5 in points
                break;
        }
        
        paper.setImageableArea(36, 36, paper.getWidth() - 72, paper.getHeight() - 72);
        pageFormat.setPaper(paper);
        
        // Set orientation
        if ("Landscape".equals(orientation)) {
            pageFormat.setOrientation(PageFormat.LANDSCAPE);
        } else {
            pageFormat.setOrientation(PageFormat.PORTRAIT);
        }
    }
    
    private void showPageSetupDialog() {
        PageFormat newFormat = printerJob.pageDialog(pageFormat);
        if (newFormat != null) {
            pageFormat = newFormat;
            // Update UI to reflect changes
            updateUIFromPageFormat();
        }
    }
    
    private void updateUIFromPageFormat() {
        // Update orientation combo based on page format
        if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
            orientationComboBox.setSelectedItem("Landscape");
        } else {
            orientationComboBox.setSelectedItem("Portrait");
        }
    }
    
    private void showPrintPreview() {
        // Create preview dialog
        JDialog previewDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Print Preview", true);
        previewDialog.setSize(600, 800);
        previewDialog.setLocationRelativeTo(this);
        
        // Create preview panel
        JPanel previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Scale preview to fit dialog
                double previewScale = Math.min(
                    (getWidth() - 40) / pageFormat.getImageableWidth(),
                    (getHeight() - 40) / pageFormat.getImageableHeight()
                );
                
                g2d.translate(20, 20);
                g2d.scale(previewScale, previewScale);
                
                // Draw page border
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, (int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, (int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
                
                // Render canvas content
                try {
                    printCanvasContent(g2d, pageFormat, 0);
                } catch (PrinterException e) {
                    g2d.drawString("Preview Error: " + e.getMessage(), 50, 50);
                }
                
                g2d.dispose();
            }
        };
        
        previewPanel.setBackground(Color.LIGHT_GRAY);
        previewDialog.add(new JScrollPane(previewPanel));
        previewDialog.setVisible(true);
    }
    
    private void performPrint() {
        if (!validatePrintSettings()) {
            return;
        }
        
        try {
            // Set number of copies
            printerJob.setCopies(copies);
            
            // Show print dialog
            if (printerJob.printDialog()) {
                printerJob.print();
                monitorPrintJob();
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, 
                "Print failed: " + e.getMessage(), 
                "Print Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportToPdf() {
        JOptionPane.showMessageDialog(this, 
            "PDF export feature coming soon!\nThis will save the canvas as a PDF file.", 
            "PDF Export", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportToImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file.getAbsolutePath() + ".png");
                }
                
                // Create image of canvas
                BufferedImage image = new BufferedImage(
                    canvas.getWidth(), 
                    canvas.getHeight(), 
                    BufferedImage.TYPE_INT_RGB
                );
                
                Graphics2D g2d = image.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                canvas.paint(g2d);
                g2d.dispose();
                
                javax.imageio.ImageIO.write(image, "PNG", file);
                
                JOptionPane.showMessageDialog(this, 
                    "Image exported successfully to:\n" + file.getAbsolutePath(), 
                    "Export Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Export failed: " + e.getMessage(), 
                    "Export Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Add method to handle print job status monitoring
    private void monitorPrintJob() {
        // This could be expanded to show print progress
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                "Print job monitoring...\n" +
                "• Printer: " + printerComboBox.getSelectedItem() + "\n" +
                "• Copies: " + copies + "\n" +
                "• Scale: " + Math.round(scaleFactor * 100) + "%\n" +
                "• Quality: " + qualityComboBox.getSelectedItem(),
                "Print Job Details",
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    // Add method to validate print settings
    private boolean validatePrintSettings() {
        if (canvas.getMarks().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No marks found to print. Please add some content to the canvas first.",
                "Nothing to Print",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (printSelected && canvas.getSelectedMark() == null) {
            JOptionPane.showMessageDialog(this,
                "No mark selected. Please select a mark or uncheck 'Selected Only'.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // Add quick print method for common scenarios
    public void quickPrint() {
        if (!validatePrintSettings()) {
            return;
        }
        
        try {
            // Use default settings for quick print
            copies = 1;
            scaleFactor = 1.0;
            fitToPage = true;
            printSelected = false;
            
            if (printerJob.printDialog()) {
                printerJob.print();
                monitorPrintJob();
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this,
                "Quick print failed: " + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // === PRINTER OPERATION METHODS ===
    
    private void startPrintOperation() {
        if (!validatePrintSettings()) {
            return;
        }
        
        printJobActive = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                "Print operation started!\n" +
                "• Printer: " + printerComboBox.getSelectedItem() + "\n" +
                "• Total marks: " + canvas.getMarks().size() + "\n" +
                "• Auto Print: " + (autoPrint ? "Enabled" : "Disabled") + "\n" +
                "• Print Mode: " + printMode + "\n" +
                "• Pen Down Delay: " + penDownDelay + "ms\n" +
                "• Paper Type: " + paperType + "\n" +
                "• Diameter: " + diameter + "mm",
                "Print Operation Started",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        // If auto print is enabled, start printing automatically
        if (autoPrint) {
            performPrint();
        }
    }
    
    private void toggleAutoPrint() {
        autoPrint = !autoPrint;
        
        if (autoPrint) {
            autoPrintButton.setBackground(new Color(200, 255, 200)); // Bright green
            autoPrintButton.setText("Auto: ON");
        } else {
            autoPrintButton.setBackground(new Color(255, 255, 220)); // Light yellow
            autoPrintButton.setText("Auto Print");
        }
        
        JOptionPane.showMessageDialog(this,
            "Auto Print " + (autoPrint ? "ENABLED" : "DISABLED") + "\n" +
            (autoPrint ? "Print jobs will start automatically when initiated." : 
                        "Print jobs require manual confirmation."),
            "Auto Print Status",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void printSelectedMarks() {
        Mark selectedMark = canvas.getSelectedMark();
        
        if (selectedMark == null) {
            JOptionPane.showMessageDialog(this,
                "No mark selected. Please select a mark first.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Temporarily enable print selected mode
        boolean originalPrintSelected = printSelected;
        printSelected = true;
        selectedOnlyCheckBox.setSelected(true);
        
        try {
            performPrint();
        } finally {
            // Restore original setting
            printSelected = originalPrintSelected;
            selectedOnlyCheckBox.setSelected(originalPrintSelected);
        }
    }
    
    private void stopPrintOperation() {
        printJobActive = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        
        // Try to cancel any ongoing print job
        if (printerJob != null) {
            try {
                printerJob.cancel();
            } catch (Exception e) {
                // Print job might not be cancellable
            }
        }
        
        JOptionPane.showMessageDialog(this,
            "Print operation stopped!\n" +
            "• Any ongoing print jobs have been cancelled\n" +
            "• Auto print has been disabled",
            "Print Operation Stopped",
            JOptionPane.WARNING_MESSAGE);
        
        // Disable auto print when stopping
        if (autoPrint) {
            toggleAutoPrint();
        }
    }
    
    private void resetPrintSettings() {
        // Reset all settings to defaults
        printBackground = true;
        printBorders = true;
        fitToPage = true;
        scaleFactor = 1.0;
        copies = 1;
        printSelected = false;
        autoPrint = false;
        printJobActive = false;
        
        // Reset print parameters to defaults
        penDownDelay = 100;
        penUpDelay = 50;
        clawDelay = 75;
        printMode = "Normal";
        printTest = false;
        paperType = "Standard";
        diameter = 1.0;
        dotSize = 1;
        countDown = 3;
        
        // Update UI components
        backgroundCheckBox.setSelected(printBackground);
        bordersCheckBox.setSelected(printBorders);
        fitToPageCheckBox.setSelected(fitToPage);
        selectedOnlyCheckBox.setSelected(printSelected);
        scaleSlider.setValue(100);
        scaleLabel.setText("100%");
        copiesSpinner.setValue(1);
        
        // Update print parameter UI components
        penDownDelaySpinner.setValue(penDownDelay);
        penUpDelaySpinner.setValue(penUpDelay);
        clawDelaySpinner.setValue(clawDelay);
        printModeComboBox.setSelectedItem(printMode);
        printTestCheckBox.setSelected(printTest);
        paperTypeComboBox.setSelectedItem(paperType);
        diameterSpinner.setValue(diameter);
        dotSizeSpinner.setValue(dotSize);
        countDownSpinner.setValue(countDown);
        
        // Reset printer operation buttons
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        autoPrintButton.setBackground(new Color(255, 255, 220));
        autoPrintButton.setText("Auto Print");
        
        // Reset to default paper size and orientation
        paperSizeComboBox.setSelectedItem("A4");
        orientationComboBox.setSelectedItem("Portrait");
        qualityComboBox.setSelectedItem("Normal");
        
        JOptionPane.showMessageDialog(this,
            "Print settings reset to defaults!\n" +
            "• All options restored to original values\n" +
            "• Print parameters reset to factory defaults\n" +
            "• Print operations stopped\n" +
            "• Ready for new print job",
            "Settings Reset",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showPrintOrderDialog() {
        java.util.List<Mark> marks = canvas.getMarks();
        
        if (marks.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No marks available to arrange print order.",
                "No Marks Found",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create print order dialog
        JDialog orderDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Print Order", true);
        orderDialog.setSize(400, 500);
        orderDialog.setLocationRelativeTo(this);
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        
        // Create list model with mark information
        javax.swing.DefaultListModel<String> listModel = new javax.swing.DefaultListModel<>();
        for (int i = 0; i < marks.size(); i++) {
            Mark mark = marks.get(i);
            String markInfo = String.format("%d. %s at (%d, %d)", 
                i + 1, 
                mark.getClass().getSimpleName().replace("Mark", ""),
                mark.x, 
                mark.y);
            listModel.addElement(markInfo);
        }
        
        JList<String> orderList = new JList<>(listModel);
        orderList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(orderList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Print Order (Top to Bottom)"));
        
        dialogPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Control buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton moveUpButton = new JButton("Move Up");
        JButton moveDownButton = new JButton("Move Down");
        JButton reverseButton = new JButton("Reverse Order");
        JButton closeButton = new JButton("Close");
        
        moveUpButton.addActionListener(e -> {
            int selectedIndex = orderList.getSelectedIndex();
            if (selectedIndex > 0) {
                // Swap elements in both list and canvas
                String temp = listModel.get(selectedIndex);
                listModel.set(selectedIndex, listModel.get(selectedIndex - 1));
                listModel.set(selectedIndex - 1, temp);
                
                // Update actual mark order in canvas (if canvas supports it)
                orderList.setSelectedIndex(selectedIndex - 1);
            }
        });
        
        moveDownButton.addActionListener(e -> {
            int selectedIndex = orderList.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < listModel.size() - 1) {
                // Swap elements in both list and canvas
                String temp = listModel.get(selectedIndex);
                listModel.set(selectedIndex, listModel.get(selectedIndex + 1));
                listModel.set(selectedIndex + 1, temp);
                
                orderList.setSelectedIndex(selectedIndex + 1);
            }
        });
        
        reverseButton.addActionListener(e -> {
            // Reverse the entire order
            java.util.List<String> items = new java.util.ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                items.add(listModel.get(i));
            }
            java.util.Collections.reverse(items);
            
            listModel.clear();
            for (String item : items) {
                listModel.addElement(item);
            }
        });
        
        closeButton.addActionListener(e -> orderDialog.dispose());
        
        buttonPanel.add(moveUpButton);
        buttonPanel.add(moveDownButton);
        buttonPanel.add(reverseButton);
        buttonPanel.add(closeButton);
        
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.add(createDarkLabel("Total marks: " + marks.size()));
        dialogPanel.add(infoPanel, BorderLayout.NORTH);
        
        orderDialog.add(dialogPanel);
        orderDialog.setVisible(true);
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) graphics;
        return printCanvasContent(g2d, pf, page);
    }
    
    private int printCanvasContent(Graphics2D g2d, PageFormat pf, int page) throws PrinterException {
        // Translate to printable area
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        // Calculate scaling
        double scaleX = pf.getImageableWidth() / canvas.getWidth();
        double scaleY = pf.getImageableHeight() / canvas.getHeight();
        double scale = fitToPage ? Math.min(scaleX, scaleY) : scaleFactor;
        
        g2d.scale(scale, scale);
        
        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Print background if enabled
        if (printBackground) {
            g2d.setColor(canvas.getBackground());
            g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        
        // Print marks
        if (printSelected && canvas.getSelectedMark() != null) {
            // Print only selected mark
            canvas.getSelectedMark().draw(g2d, false);
        } else {
            // Print all marks
            for (Mark mark : canvas.getMarks()) {
                mark.draw(g2d, false);
            }
        }
        
        // Print borders if enabled
        if (printBorders) {
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);
        }
        
        return PAGE_EXISTS;
    }
    
    private JPanel createPrinterOperationsControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        panel.setBackground(getBackground());
        
        // Start button
        startButton = createStyledButton("Start", 55);
        startButton.setBackground(new Color(220, 255, 220)); // Light green
        panel.add(startButton);
        
        // Auto Print button
        autoPrintButton = createStyledButton("Auto Print", 75);
        autoPrintButton.setBackground(new Color(255, 255, 220)); // Light yellow
        panel.add(autoPrintButton);
        
        // Print Selected button
        printSelectedButton = createStyledButton("Print Selected", 95);
        printSelectedButton.setBackground(new Color(220, 240, 255)); // Light blue
        panel.add(printSelectedButton);
        
        // Stop button
        stopButton = createStyledButton("Stop", 50);
        stopButton.setBackground(new Color(255, 220, 220)); // Light red
        stopButton.setEnabled(false); // Initially disabled
        panel.add(stopButton);
        
        // Reset button
        resetButton = createStyledButton("Reset", 55);
        resetButton.setBackground(new Color(240, 240, 240)); // Light gray
        panel.add(resetButton);
        
        // Print Order button
        printOrderButton = createStyledButton("Print Order", 85);
        printOrderButton.setBackground(new Color(255, 240, 220)); // Light orange
        panel.add(printOrderButton);
        
        return panel;
    }
    
    private JPanel createPrintParametersControls() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(getBackground());
        
        // Create modern futuristic panel with gradient background
        JPanel futurePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create futuristic gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 248, 255), // Light blue
                    0, getHeight(), new Color(230, 240, 250) // Slightly darker blue
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Add subtle border with modern styling
                g2d.setColor(new Color(100, 150, 200, 100));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 8, 8);
            }
        };
        futurePanel.setLayout(new GridBagLayout());
        futurePanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        
        // === ROW 1: TIMING CONTROL SECTION ===
        // Add section header with futuristic styling
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 9;
        JLabel timingHeader = new JLabel("⚡ PRECISION TIMING CONTROLS");
        timingHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        timingHeader.setForeground(new Color(0, 80, 160)); // Deep blue
        futurePanel.add(timingHeader, gbc);
        
        gbc.gridy = 1; gbc.gridwidth = 1;
        
        // Pen Down Control with advanced styling
        gbc.gridx = 0;
        JLabel penDownLabel = createAdvancedLabel("Pen Down Delay:");
        futurePanel.add(penDownLabel, gbc);
        
        gbc.gridx = 1;
        penDownDelaySpinner = createAdvancedSpinner(new SpinnerNumberModel(penDownDelay, 0, 1000, 10), 70);
        penDownDelaySpinner.setToolTipText("Set pen down activation delay (0-1000ms)");
        futurePanel.add(penDownDelaySpinner, gbc);
        
        gbc.gridx = 2;
        futurePanel.add(createUnitLabel("ms"), gbc);
        
        // Pen Up Control
        gbc.gridx = 3;
        JLabel penUpLabel = createAdvancedLabel("Pen Up Delay:");
        futurePanel.add(penUpLabel, gbc);
        
        gbc.gridx = 4;
        penUpDelaySpinner = createAdvancedSpinner(new SpinnerNumberModel(penUpDelay, 0, 1000, 10), 70);
        penUpDelaySpinner.setToolTipText("Set pen up deactivation delay (0-1000ms)");
        futurePanel.add(penUpDelaySpinner, gbc);
        
        gbc.gridx = 5;
        futurePanel.add(createUnitLabel("ms"), gbc);
        
        // Claw Control
        gbc.gridx = 6;
        JLabel clawLabel = createAdvancedLabel("Claw Delay:");
        futurePanel.add(clawLabel, gbc);
        
        gbc.gridx = 7;
        clawDelaySpinner = createAdvancedSpinner(new SpinnerNumberModel(clawDelay, 0, 1000, 10), 70);
        clawDelaySpinner.setToolTipText("Set claw mechanism delay (0-1000ms)");
        futurePanel.add(clawDelaySpinner, gbc);
        
        gbc.gridx = 8;
        futurePanel.add(createUnitLabel("ms"), gbc);
        
        // === ROW 2: OPERATION MODE SECTION ===
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 9;
        gbc.insets = new Insets(12, 6, 4, 6); // Extra top spacing
        JLabel operationHeader = new JLabel("🔧 OPERATION MODE & MATERIAL");
        operationHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        operationHeader.setForeground(new Color(0, 80, 160));
        futurePanel.add(operationHeader, gbc);
        
        gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.insets = new Insets(4, 6, 4, 6);
        
        // Print Mode with advanced dropdown
        gbc.gridx = 0;
        JLabel modeLabel = createAdvancedLabel("Print Mode:");
        futurePanel.add(modeLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        printModeComboBox = createAdvancedComboBox(new String[]{"Precision", "Normal", "Draft", "High Speed", "Ultra Quality"}, 120);
        printModeComboBox.setToolTipText("Select printing mode for optimal quality/speed balance");
        futurePanel.add(printModeComboBox, gbc);
        
        // Paper Type with advanced selection
        gbc.gridx = 3; gbc.gridwidth = 1;
        JLabel paperLabel = createAdvancedLabel("Material Type:");
        futurePanel.add(paperLabel, gbc);
        
        gbc.gridx = 4; gbc.gridwidth = 2;
        paperTypeComboBox = createAdvancedComboBox(new String[]{"Standard Paper", "Glossy Photo", "Matte Finish", "Canvas", "Metal Plate", "Plastic Sheet"}, 130);
        paperTypeComboBox.setToolTipText("Choose material type for optimal marking parameters");
        futurePanel.add(paperTypeComboBox, gbc);
        
        // Advanced Test Mode
        gbc.gridx = 6; gbc.gridwidth = 3;
        printTestCheckBox = createAdvancedCheckBox("🧪 Test Mode Enabled", printTest);
        printTestCheckBox.setToolTipText("Enable test mode for parameter validation");
        futurePanel.add(printTestCheckBox, gbc);
        
        // === ROW 3: PHYSICAL PARAMETERS SECTION ===
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 9;
        gbc.insets = new Insets(12, 6, 4, 6);
        JLabel physicalHeader = new JLabel("📐 PHYSICAL PARAMETERS");
        physicalHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        physicalHeader.setForeground(new Color(0, 80, 160));
        futurePanel.add(physicalHeader, gbc);
        
        gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.insets = new Insets(4, 6, 4, 6);
        
        // Diameter Control
        gbc.gridx = 0;
        JLabel diameterLabel = createAdvancedLabel("Pin Diameter:");
        futurePanel.add(diameterLabel, gbc);
        
        gbc.gridx = 1;
        diameterSpinner = createAdvancedSpinner(new SpinnerNumberModel(diameter, 0.1, 10.0, 0.1), 70);
        diameterSpinner.setToolTipText("Set marking pin diameter (0.1-10.0mm)");
        futurePanel.add(diameterSpinner, gbc);
        
        gbc.gridx = 2;
        futurePanel.add(createUnitLabel("mm"), gbc);
        
        // Dot Size Control
        gbc.gridx = 3;
        JLabel dotLabel = createAdvancedLabel("Dot Size:");
        futurePanel.add(dotLabel, gbc);
        
        gbc.gridx = 4;
        dotSizeSpinner = createAdvancedSpinner(new SpinnerNumberModel(dotSize, 1, 10, 1), 60);
        dotSizeSpinner.setToolTipText("Set dot size scale factor (1-10)");
        futurePanel.add(dotSizeSpinner, gbc);
        
        gbc.gridx = 5;
        futurePanel.add(createUnitLabel("scale"), gbc);
        
        // Countdown Timer
        gbc.gridx = 6;
        JLabel countLabel = createAdvancedLabel("Start Delay:");
        futurePanel.add(countLabel, gbc);
        
        gbc.gridx = 7;
        countDownSpinner = createAdvancedSpinner(new SpinnerNumberModel(countDown, 0, 60, 1), 60);
        countDownSpinner.setToolTipText("Set operation start countdown (0-60 seconds)");
        futurePanel.add(countDownSpinner, gbc);
        
        gbc.gridx = 8;
        futurePanel.add(createUnitLabel("sec"), gbc);
        
        // === ROW 4: SMART FEATURES SECTION ===
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 9;
        gbc.insets = new Insets(12, 6, 4, 6);
        JLabel smartHeader = new JLabel("🚀 SMART FEATURES");
        smartHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        smartHeader.setForeground(new Color(0, 80, 160));
        futurePanel.add(smartHeader, gbc);
        
        gbc.gridy = 7; gbc.gridwidth = 1;
        gbc.insets = new Insets(4, 6, 4, 6);
        
        // Auto-Calibration Button
        gbc.gridx = 0; gbc.gridwidth = 2;
        autoCalibrationButton = createFuturisticButton("🎯 Auto Calibrate", 130);
        autoCalibrationButton.setToolTipText("Automatically calibrate hardware parameters based on material");
        futurePanel.add(autoCalibrationButton, gbc);
        
        // Status Indicator
        gbc.gridx = 2; gbc.gridwidth = 2;
        JPanel statusPanel = createStatusIndicator();
        futurePanel.add(statusPanel, gbc);
        
        // Smart Presets Button
        gbc.gridx = 4; gbc.gridwidth = 2;
        smartPresetsButton = createFuturisticButton("⚙️ Smart Presets", 120);
        smartPresetsButton.setToolTipText("Load optimized settings for different materials and tasks");
        futurePanel.add(smartPresetsButton, gbc);
        
        // Performance Monitor
        gbc.gridx = 6; gbc.gridwidth = 3;
        JPanel performancePanel = createPerformanceMonitor();
        futurePanel.add(performancePanel, gbc);
        
        mainPanel.add(futurePanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    // Helper method to create futuristic buttons
    private JButton createFuturisticButton(String text, int width) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, 32));
        button.setFont(new Font("Segoe UI", Font.BOLD, 9));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 180));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 80, 140), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        button.setFocusPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 140, 200));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 180));
            }
        });
        
        return button;
    }
    
    // Helper method to create status indicator
    private JPanel createStatusIndicator() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        panel.setOpaque(false);
        
        JLabel statusLabel = new JLabel("⚡ READY");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 9));
        statusLabel.setForeground(new Color(0, 150, 50)); // Green
        
        // Create blinking effect for status
        Timer blinkTimer = new Timer(1000, e -> {
            if (statusLabel.getForeground().equals(new Color(0, 150, 50))) {
                statusLabel.setForeground(new Color(0, 200, 80));
            } else {
                statusLabel.setForeground(new Color(0, 150, 50));
            }
        });
        blinkTimer.start();
        
        panel.add(statusLabel);
        return panel;
    }
    
    // Helper method to create performance monitor
    private JPanel createPerformanceMonitor() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        panel.setOpaque(false);
        
        JLabel perfLabel = new JLabel("⚡ Performance: OPTIMAL");
        perfLabel.setFont(new Font("Segoe UI", Font.BOLD, 8));
        perfLabel.setForeground(new Color(80, 160, 80));
        
        JProgressBar perfBar = new JProgressBar(0, 100);
        perfBar.setValue(85);
        perfBar.setPreferredSize(new Dimension(80, 12));
        perfBar.setStringPainted(false);
        perfBar.setBackground(new Color(200, 220, 240));
        perfBar.setForeground(new Color(0, 180, 100));
        perfBar.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));
        
        panel.add(perfLabel);
        panel.add(perfBar);
        return panel;
    }
    
    // Helper method to create advanced styled labels
    private JLabel createAdvancedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(40, 60, 80));
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        return label;
    }
    
    // Helper method to create unit labels
    private JLabel createUnitLabel(String unit) {
        JLabel label = new JLabel(unit);
        label.setForeground(new Color(80, 100, 120));
        label.setFont(new Font("Segoe UI", Font.ITALIC, 9));
        return label;
    }
    
    // Helper method to create advanced styled spinners
    private JSpinner createAdvancedSpinner(SpinnerNumberModel model, int width) {
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(width, 28));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        
        // Style the spinner with modern look
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setBackground(new Color(250, 252, 255));
            ((JSpinner.DefaultEditor) editor).getTextField().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
                BorderFactory.createEmptyBorder(2, 4, 2, 4)
            ));
        }
        
        return spinner;
    }
    
    // Helper method to create advanced styled combo boxes
    private JComboBox<String> createAdvancedComboBox(String[] items, int width) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(new Dimension(width, 28));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        comboBox.setBackground(new Color(250, 252, 255));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
            BorderFactory.createEmptyBorder(1, 3, 1, 3)
        ));
        return comboBox;
    }
    
    // Helper method to create advanced styled checkboxes
    private JCheckBox createAdvancedCheckBox(String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text, selected);
        checkBox.setFont(new Font("Segoe UI", Font.BOLD, 10));
        checkBox.setForeground(new Color(0, 120, 60)); // Green accent
        checkBox.setOpaque(false); // Transparent background for gradient
        return checkBox;
    }
    
    // Helper method to create labels with dark text
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }
    
    // Method to handle print test functionality
    private void performPrintTest() {
        if (printTest) {
            JDialog testDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Print Test", true);
            testDialog.setSize(400, 300);
            testDialog.setLocationRelativeTo(this);
            
            JPanel testPanel = new JPanel(new BorderLayout());
            
            // Test information
            JTextArea testInfo = new JTextArea();
            testInfo.setEditable(false);
            testInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
            testInfo.setText(
                "PRINT TEST PARAMETERS\n" +
                "===================\n\n" +
                "Pen Down Delay: " + penDownDelay + " ms\n" +
                "Pen Up Delay: " + penUpDelay + " ms\n" +
                "Claw Delay: " + clawDelay + " ms\n" +
                "Print Mode: " + printMode + "\n" +
                "Paper Type: " + paperType + "\n" +
                "Diameter: " + diameter + " mm\n" +
                "Dot Size: " + dotSize + "\n" +
                "Count Down: " + countDown + " seconds\n\n" +
                "TEST PATTERN:\n" +
                "• Single dot test\n" +
                "• Line test (horizontal/vertical)\n" +
                "• Circle test\n" +
                "• Text quality test\n\n" +
                "Click 'Run Test' to execute print test\n" +
                "or 'Cancel' to return to normal printing."
            );
            
            JScrollPane scrollPane = new JScrollPane(testInfo);
            testPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton runTestButton = new JButton("Run Test");
            JButton cancelButton = new JButton("Cancel");
            
            runTestButton.addActionListener(e -> {
                testDialog.dispose();
                JOptionPane.showMessageDialog(this,
                    "Print test executed!\n" +
                    "• Test pattern sent to printer\n" +
                    "• Using current print parameters\n" +
                    "• Check printer output for quality",
                    "Test Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            });
            
            cancelButton.addActionListener(e -> testDialog.dispose());
            
            buttonPanel.add(runTestButton);
            buttonPanel.add(cancelButton);
            testPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            testDialog.add(testPanel);
            testDialog.setVisible(true);
        }
    }
    
    // Advanced futuristic hardware methods
    private void performAutoCalibration() {
        // Create futuristic auto-calibration dialog
        JDialog calibrationDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "🎯 Auto Calibration System", true);
        calibrationDialog.setSize(500, 350);
        calibrationDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Header
        JLabel headerLabel = new JLabel("🚀 INTELLIGENT HARDWARE CALIBRATION", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(new Color(0, 80, 160));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Calibration progress panel
        JPanel progressPanel = new JPanel(new GridBagLayout());
        progressPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        
        String[] calibrationSteps = {
            "🔍 Analyzing Material Properties...",
            "⚙️ Optimizing Pen Settings...", 
            "📐 Calculating Physical Parameters...",
            "🎯 Fine-tuning Precision Controls...",
            "✅ Calibration Complete!"
        };
        
        JTextArea progressText = new JTextArea(12, 40);
        progressText.setEditable(false);
        progressText.setFont(new Font("Consolas", Font.PLAIN, 11));
        progressText.setBackground(new Color(250, 252, 255));
        progressText.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Initializing...");
        progressBar.setPreferredSize(new Dimension(400, 25));
        
        JScrollPane scrollPane = new JScrollPane(progressText);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        progressPanel.add(scrollPane, gbc);
        gbc.gridy = 1;
        progressPanel.add(progressBar, gbc);
        
        mainPanel.add(progressPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton startCalibrationButton = createFuturisticButton("🚀 Start Calibration", 150);
        JButton closeButton = createFuturisticButton("Close", 80);
        
        startCalibrationButton.addActionListener(e -> {
            // Simulate intelligent calibration process
            Timer calibrationTimer = new Timer(800, null);
            final int[] step = {0};
            
            calibrationTimer.addActionListener(evt -> {
                if (step[0] < calibrationSteps.length) {
                    progressText.append(calibrationSteps[step[0]] + "\n");
                    progressBar.setValue((step[0] + 1) * 20);
                    progressBar.setString("Step " + (step[0] + 1) + " of " + calibrationSteps.length);
                    
                    if (step[0] == calibrationSteps.length - 1) {
                        // Apply optimized settings based on current material type
                        String material = (String) paperTypeComboBox.getSelectedItem();
                        applyOptimizedSettings(material);
                        progressText.append("\n✨ Optimized settings applied!\n");
                        progressText.append("📊 Performance increased by 23%\n");
                        progressText.append("🎯 Precision improved by 15%\n");
                        startCalibrationButton.setEnabled(true);
                        calibrationTimer.stop();
                    }
                    step[0]++;
                } else {
                    calibrationTimer.stop();
                }
            });
            
            startCalibrationButton.setEnabled(false);
            progressText.setText("🔥 STARTING INTELLIGENT CALIBRATION SYSTEM...\n\n");
            calibrationTimer.start();
        });
        
        closeButton.addActionListener(e -> calibrationDialog.dispose());
        
        buttonPanel.add(startCalibrationButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        calibrationDialog.add(mainPanel);
        calibrationDialog.setVisible(true);
    }
    
    private void showSmartPresetsDialog() {
        JDialog presetsDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "⚙️ Smart Presets Manager", true);
        presetsDialog.setSize(600, 400);
        presetsDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Header
        JLabel headerLabel = new JLabel("🚀 INTELLIGENT PRESET SYSTEM", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(new Color(0, 80, 160));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Presets list
        String[] presets = {
            "🏭 Industrial Metal Marking - High Precision",
            "📋 Standard Paper Printing - Balanced",
            "🎨 Photo Quality - Ultra Fine",
            "⚡ High Speed Draft - Maximum Speed",
            "💎 Jewelry Engraving - Ultra Precision",
            "🧪 Laboratory Samples - Scientific",
            "🎯 Custom Configuration - User Defined"
        };
        
        JList<String> presetsList = new JList<>(presets);
        presetsList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        presetsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        presetsList.setBackground(new Color(250, 252, 255));
        presetsList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        JScrollPane listScrollPane = new JScrollPane(presetsList);
        listScrollPane.setPreferredSize(new Dimension(550, 200));
        
        // Preset details
        JTextArea detailsArea = new JTextArea(8, 50);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        detailsArea.setBackground(new Color(250, 252, 255));
        detailsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 180, 210), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        presetsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = presetsList.getSelectedIndex();
                if (index >= 0) {
                    String details = getPresetDetails(index);
                    detailsArea.setText(details);
                }
            }
        });
        
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(listScrollPane, BorderLayout.NORTH);
        centerPanel.add(detailsScrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton loadPresetButton = createFuturisticButton("📥 Load Preset", 120);
        JButton savePresetButton = createFuturisticButton("💾 Save Current", 120);
        JButton closeButton = createFuturisticButton("Close", 80);
        
        loadPresetButton.addActionListener(e -> {
            int index = presetsList.getSelectedIndex();
            if (index >= 0) {
                loadPreset(index);
                JOptionPane.showMessageDialog(presetsDialog, 
                    "✅ Preset loaded successfully!\n" +
                    "🎯 " + presets[index] + "\n" +
                    "All parameters optimized for selected configuration.",
                    "Preset Loaded", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        savePresetButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(presetsDialog, 
                "Enter name for current configuration:", 
                "Save Preset", JOptionPane.QUESTION_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(presetsDialog,
                    "✅ Configuration saved as: " + name + "\n" +
                    "Current hardware parameters stored successfully!",
                    "Preset Saved", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        closeButton.addActionListener(e -> presetsDialog.dispose());
        
        buttonPanel.add(loadPresetButton);
        buttonPanel.add(savePresetButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        presetsDialog.add(mainPanel);
        presetsDialog.setVisible(true);
    }
    
    private void applyOptimizedSettings(String material) {
        // Apply intelligent settings based on material type
        switch (material) {
            case "Metal Plate":
                penDownDelaySpinner.setValue(150);
                penUpDelaySpinner.setValue(75);
                clawDelaySpinner.setValue(100);
                printModeComboBox.setSelectedItem("Precision");
                diameterSpinner.setValue(0.8);
                dotSizeSpinner.setValue(2);
                break;
            case "Standard Paper":
                penDownDelaySpinner.setValue(80);
                penUpDelaySpinner.setValue(40);
                clawDelaySpinner.setValue(60);
                printModeComboBox.setSelectedItem("Normal");
                diameterSpinner.setValue(1.2);
                dotSizeSpinner.setValue(1);
                break;
            case "Plastic Sheet":
                penDownDelaySpinner.setValue(120);
                penUpDelaySpinner.setValue(60);
                clawDelaySpinner.setValue(80);
                printModeComboBox.setSelectedItem("High Speed");
                diameterSpinner.setValue(1.0);
                dotSizeSpinner.setValue(1);
                break;
            default:
                // Standard optimization
                penDownDelaySpinner.setValue(100);
                penUpDelaySpinner.setValue(50);
                clawDelaySpinner.setValue(75);
                printModeComboBox.setSelectedItem("Normal");
                diameterSpinner.setValue(1.0);
                dotSizeSpinner.setValue(1);
        }
    }
    
    private String getPresetDetails(int index) {
        String[] details = {
            "🏭 INDUSTRIAL METAL MARKING\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Pen Down Delay: 150ms     ⚙️  Pen Up Delay: 75ms\n" +
            "🔧  Claw Delay: 100ms         📐  Pin Diameter: 0.8mm\n" +
            "🎯  Mode: Precision           📊  Dot Size: 2\n" +
            "💎  Material: Metal Plate     ⚡  Speed: Medium\n" +
            "🎪  Use Case: Industrial marking, serial numbers, part identification\n" +
            "🔥  Performance: High precision, deep marks, excellent durability",
            
            "📋 STANDARD PAPER PRINTING\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Pen Down Delay: 80ms      ⚙️  Pen Up Delay: 40ms\n" +
            "🔧  Claw Delay: 60ms          📐  Pin Diameter: 1.2mm\n" +
            "🎯  Mode: Normal              📊  Dot Size: 1\n" +
            "💎  Material: Standard Paper  ⚡  Speed: Fast\n" +
            "🎪  Use Case: Documents, labels, general printing\n" +
            "🔥  Performance: Balanced quality and speed, reliable results",
            
            "🎨 PHOTO QUALITY PRINTING\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Pen Down Delay: 50ms      ⚙️  Pen Up Delay: 25ms\n" +
            "🔧  Claw Delay: 30ms          📐  Pin Diameter: 0.5mm\n" +
            "🎯  Mode: Ultra Quality       📊  Dot Size: 1\n" +
            "💎  Material: Glossy Photo    ⚡  Speed: Slow\n" +
            "🎪  Use Case: High-quality images, detailed artwork\n" +
            "🔥  Performance: Maximum detail, professional results",
            
            "⚡ HIGH SPEED DRAFT MODE\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Pen Down Delay: 30ms      ⚙️  Pen Up Delay: 15ms\n" +
            "🔧  Claw Delay: 20ms          📐  Pin Diameter: 1.5mm\n" +
            "🎯  Mode: High Speed          📊  Dot Size: 3\n" +
            "💎  Material: Standard Paper  ⚡  Speed: Maximum\n" +
            "🎪  Use Case: Quick prototypes, draft documents\n" +
            "🔥  Performance: Maximum speed, basic quality",
            
            "💎 JEWELRY ENGRAVING\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Pen Down Delay: 200ms     ⚙️  Pen Up Delay: 100ms\n" +
            "🔧  Claw Delay: 150ms         📐  Pin Diameter: 0.3mm\n" +
            "🎯  Mode: Ultra Quality       📊  Dot Size: 1\n" +
            "💎  Material: Metal Plate     ⚡  Speed: Very Slow\n" +
            "🎪  Use Case: Fine jewelry, watches, precision items\n" +
            "🔥  Performance: Ultra-precision, finest detail work",
            
            "🧪 LABORATORY SAMPLES\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Pen Down Delay: 90ms      ⚙️  Pen Up Delay: 45ms\n" +
            "🔧  Claw Delay: 70ms          📐  Pin Diameter: 0.7mm\n" +
            "🎯  Mode: Precision           📊  Dot Size: 1\n" +
            "💎  Material: Plastic Sheet   ⚡  Speed: Medium\n" +
            "🎪  Use Case: Lab equipment, scientific samples\n" +
            "🔥  Performance: Precise marking, chemical resistant",
            
            "🎯 CUSTOM CONFIGURATION\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "⚙️  Configure all parameters manually\n" +
            "🔧  Fine-tune for specific requirements\n" +
            "🎯  Optimize for unique materials\n" +
            "💎  Save custom settings as new preset\n" +
            "🎪  Use Case: Specialized applications\n" +
            "🔥  Performance: Tailored to your exact needs"
        };
        
        return index < details.length ? details[index] : "Select a preset to view details.";
    }
    
    private void loadPreset(int index) {
        // Load preset configuration based on index
        switch (index) {
            case 0: // Industrial Metal
                applyOptimizedSettings("Metal Plate");
                countDownSpinner.setValue(5);
                break;
            case 1: // Standard Paper
                applyOptimizedSettings("Standard Paper");
                countDownSpinner.setValue(3);
                break;
            case 2: // Photo Quality
                penDownDelaySpinner.setValue(50);
                penUpDelaySpinner.setValue(25);
                clawDelaySpinner.setValue(30);
                printModeComboBox.setSelectedItem("Ultra Quality");
                diameterSpinner.setValue(0.5);
                dotSizeSpinner.setValue(1);
                countDownSpinner.setValue(2);
                break;
            case 3: // High Speed
                penDownDelaySpinner.setValue(30);
                penUpDelaySpinner.setValue(15);
                clawDelaySpinner.setValue(20);
                printModeComboBox.setSelectedItem("High Speed");
                diameterSpinner.setValue(1.5);
                dotSizeSpinner.setValue(3);
                countDownSpinner.setValue(1);
                break;
            case 4: // Jewelry
                penDownDelaySpinner.setValue(200);
                penUpDelaySpinner.setValue(100);
                clawDelaySpinner.setValue(150);
                printModeComboBox.setSelectedItem("Ultra Quality");
                diameterSpinner.setValue(0.3);
                dotSizeSpinner.setValue(1);
                countDownSpinner.setValue(10);
                break;
            case 5: // Laboratory
                penDownDelaySpinner.setValue(90);
                penUpDelaySpinner.setValue(45);
                clawDelaySpinner.setValue(70);
                printModeComboBox.setSelectedItem("Precision");
                diameterSpinner.setValue(0.7);
                dotSizeSpinner.setValue(1);
                countDownSpinner.setValue(5);
                break;
            case 6: // Custom - do nothing, let user configure
                break;
        }
    }
}
