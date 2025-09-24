import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MarkPanel extends JPanel implements DrawingCanvas.SelectionListener {

    // Modern color scheme
    private static final Color PANEL_BG = new Color(236, 240, 241);      // Light gray background
    private static final Color SECTION_BG = new Color(255, 255, 255);    // White sections
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);   // Blue accent
    private static final Color BUTTON_COLOR = new Color(46, 204, 113);   // Green buttons
    private static final Color HOVER_COLOR = new Color(39, 174, 96);     // Darker green hover
    private static final Color TEXT_COLOR = new Color(44, 62, 80);       // Dark text
    private static final Color BORDER_COLOR = new Color(189, 195, 199);  // Light border
    
    // Consistent modern colors across app
    private static final Color DARK_BLUE = new Color(45, 62, 80);        // Main dark color
    private static final Color ACCENT_BLUE = new Color(52, 152, 219);    // Accent blue
    private static final Color LIGHT_GRAY = new Color(236, 240, 241);    // Light background
    private static final Font MODERN_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    private final DrawingCanvas canvas;
    private final JTextField contentField;
    private final JSpinner heightSpinner;
    private final JSpinner widthSpinner;
    private final JSpinner spacingSpinner;
    private final JComboBox<String> fontComboBox;
    private final JComboBox<String> exFontComboBox;
    
    // View & Navigation state variables
    private double currentZoom = 1.0;
    private boolean moveViewMode = false;

    public MarkPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);

        // Initialize all components first
        contentField = new JTextField("Text", 10);
        contentField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contentField.setForeground(DARK_BLUE);  // Dark text for visibility
        contentField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.setSelectedItem("Arial");
        fontComboBox.addActionListener(e -> updateSelectedMarkFont());
        styleComboBox(fontComboBox);

        heightSpinner = new JSpinner(new SpinnerNumberModel(12, 1, 100, 1));
        heightSpinner.addChangeListener(e -> updateSelectedMarkFont());

        widthSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 500, 10));
        widthSpinner.addChangeListener(e -> updateSelectedMarkFont());

        spacingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
        spacingSpinner.addChangeListener(e -> updateSelectedMarkFont());

        // Apply modern styling to all spinners
        styleSpinner(heightSpinner, 55);
        styleSpinner(widthSpinner, 65);
        styleSpinner(spacingSpinner, 55);

        String[] exFonts = {"Standard", "Bold", "Italic", "Bold Italic"};
        exFontComboBox = new JComboBox<>(exFonts);
        exFontComboBox.addActionListener(e -> updateSelectedMarkFont());
        styleComboBox(exFontComboBox);
        spacingSpinner.setMaximumSize(new Dimension(55, 24));
        
        // Ensure spinner text fields are properly aligned
        JSpinner.DefaultEditor heightEditor = (JSpinner.DefaultEditor) heightSpinner.getEditor();
        heightEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        JSpinner.DefaultEditor widthEditor = (JSpinner.DefaultEditor) widthSpinner.getEditor();
        widthEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        JSpinner.DefaultEditor spacingEditor = (JSpinner.DefaultEditor) spacingSpinner.getEditor();
        spacingEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);

        // Add action listeners to content field
        contentField.addActionListener(e -> updateSelectedMarkText());
        contentField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                updateSelectedMarkText();
            }
        });

        // === CREATE MAIN TOOLBAR PANEL ===
        JPanel mainToolbarPanel = new JPanel();
        mainToolbarPanel.setLayout(new BoxLayout(mainToolbarPanel, BoxLayout.Y_AXIS));
        mainToolbarPanel.setBackground(getBackground());
        mainToolbarPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // === CLIPBOARD SECTION ===
        JPanel clipboardSection = createToolbarSection("Clipboard", createClipboardButtons());
        
        // === MARKS SECTION ===
        JPanel marksSection = createToolbarSection("Marks", createMarksButtons());
        
        // === TEXT FORMATTING SECTION ===
        JPanel textSection = createToolbarSection("Text Formatting", createTextFormattingControls());
        
        // === VIEW & NAVIGATION SECTION ===
        JPanel viewSection = createToolbarSection("View & Navigation", createViewControls());

        // Add sections to main toolbar
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topRow.setBackground(getBackground());
        topRow.add(clipboardSection);
        topRow.add(createSeparator());
        topRow.add(marksSection);
        topRow.add(createSeparator());
        topRow.add(textSection);
        topRow.add(createSeparator());
        topRow.add(viewSection);
        
        mainToolbarPanel.add(topRow);
        
        add(mainToolbarPanel, BorderLayout.NORTH);

        // Set this panel as the selection listener
        canvas.setSelectionListener(this);
    }

    // Create a toolbar section with title and buttons
    private JPanel createToolbarSection(String title, JPanel contentPanel) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(getBackground());
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(3, 6, 5, 6)
        ));
        
        // Add title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.add(titleLabel);
        section.add(Box.createVerticalStrut(3));
        
        // Add content
        contentPanel.setBackground(getBackground());
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.add(contentPanel);
        
        return section;
    }
    
    // Create clipboard buttons section
    private JPanel createClipboardButtons() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 3, 3));  // Increased spacing
        panel.setBackground(getBackground());
        
        // First row
        panel.add(createOfficeButton("↶ Undo", "Undo", true));
        panel.add(createOfficeButton("↷ Redo", "Redo", true));
        panel.add(createOfficeButton("✂ Cut", "Cut", true));
        
        // Second row
        panel.add(createOfficeButton("📄 Copy", "Copy", true));
        panel.add(createOfficeButton("📋 Paste", "Paste", true));
        panel.add(createOfficeButton("🧽 Erase", "Erase", true));
        
        return panel;
    }
    
    // Create marks buttons section
    private JPanel createMarksButtons() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 3, 3));  // Increased spacing
        panel.setBackground(getBackground());
        
        // First row
        panel.add(createMarkButton("🅰 Text", "Text"));
        panel.add(createMarkButton("🔄 Arc", "Arc Letters"));
        panel.add(createMarkButton("🔳 Matrix", "Dot Matrix"));
        panel.add(createMarkButton("� Graph", "Graph"));
        
        // Second row
        panel.add(createMarkButton("⬜ Rect", "Rectangle"));
        panel.add(createMarkButton("📏 Line", "Line"));
        panel.add(createMarkButton("⚫ Dot Text", "Farzi"));
        panel.add(createMarkButton("🎯 Avoid", "Avoid Point"));
        
        return panel;
    }
    
    // Create text formatting controls section
    private JPanel createTextFormattingControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create a grid-based layout for better alignment
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure consistent filling
        
        // Row 1: Content and Font
        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(createDarkLabel("Content:"), gbc);
        gbc.gridx = 1;
        contentField.setPreferredSize(new Dimension(85, 24));
        gridPanel.add(contentField, gbc);
        
        gbc.gridx = 2;
        gridPanel.add(createDarkLabel("Font:"), gbc);
        gbc.gridx = 3;
        fontComboBox.setPreferredSize(new Dimension(105, 24));
        gridPanel.add(fontComboBox, gbc);
        
        // Row 2: Size and Style  
        gbc.gridx = 0; gbc.gridy = 1;
        gridPanel.add(createDarkLabel("Size:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(heightSpinner, gbc);
        
        gbc.gridx = 2;
        gridPanel.add(createDarkLabel("Style:"), gbc);
        gbc.gridx = 3;
        exFontComboBox.setPreferredSize(new Dimension(85, 24));
        gridPanel.add(exFontComboBox, gbc);
        
        // Row 3: Width and Spacing
        gbc.gridx = 0; gbc.gridy = 2;
        gridPanel.add(createDarkLabel("Width:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(widthSpinner, gbc);
        
        gbc.gridx = 2;
        gridPanel.add(createDarkLabel("Spacing:"), gbc);
        gbc.gridx = 3;
        // Ensure the spinner is properly aligned
        gbc.anchor = GridBagConstraints.WEST;
        gridPanel.add(spacingSpinner, gbc);
        
        panel.add(gridPanel);
        
        return panel;
    }
    
    // Create a separator line
    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(2, 85));  // Adjusted height for new layout
        separator.setBackground(Color.LIGHT_GRAY);
        separator.setBorder(BorderFactory.createLoweredBevelBorder());
        return separator;
    }
    
    // Create Office-style buttons
    private JButton createOfficeButton(String text, String tooltip, boolean enabled) {
        // Use HTML for better text formatting
        String buttonText = "<html><center>" + text + "</center></html>";
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(55, 40));  // Increased size for better visibility
        button.setToolTipText(tooltip);
        button.setEnabled(enabled);
        button.setFont(MODERN_FONT.deriveFont(Font.BOLD, 10f));  // Use modern font
        button.setBackground(LIGHT_GRAY);
        button.setForeground(DARK_BLUE);
        button.setBorder(new LineBorder(ACCENT_BLUE, 1, true));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setMargin(new Insets(2, 2, 2, 2));  // Small margins
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add modern hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(ACCENT_BLUE);
                    button.setForeground(Color.WHITE);  // White text on blue background is fine
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(LIGHT_GRAY);
                button.setForeground(DARK_BLUE);
            }
        });
        
        // Add action listeners for clipboard operations
        if (tooltip.equals("Undo")) {
            button.addActionListener(e -> canvas.undo());
        } else if (tooltip.equals("Redo")) {
            button.addActionListener(e -> canvas.redo());
        } else if (tooltip.equals("Cut")) {
            button.addActionListener(e -> canvas.cutSelected());
        } else if (tooltip.equals("Copy")) {
            button.addActionListener(e -> canvas.copySelected());
        } else if (tooltip.equals("Paste")) {
            button.addActionListener(e -> canvas.paste());
        } else if (tooltip.equals("Erase")) {
            button.addActionListener(e -> canvas.eraseSelected());
        }
        
        return button;
    }
    
    // Create mark type buttons with modern styling
    private JButton createMarkButton(String text, String markType) {
        // Use HTML for better text formatting
        String buttonText = "<html><center>" + text + "</center></html>";
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(75, 45));
        button.setToolTipText(markType);
        button.setFont(new Font("Segoe UI", Font.BOLD, 10));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);  // White text on green background is readable
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add modern hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        // Add action listener to create marks
        button.addActionListener(e -> {
            String content = contentField.getText();
            
            // Set default content for specific mark types if content field is empty
            if (markType.equals("Avoid Point") && (content == null || content.trim().isEmpty())) {
                content = "Concentric Circles ⌀100/64";
            }
            
            int height = (Integer) heightSpinner.getValue();
            int width = (Integer) widthSpinner.getValue();
            String font = (String) fontComboBox.getSelectedItem();
            
            if (markType.equals("Arc Letters")) {
                canvas.addMark("ArcLetters", content, height, width, font);
            } else if (markType.equals("Text")) {
                canvas.addMark("Text", content, height, width, font);
            } else if (markType.equals("Rectangle")) {
                canvas.addMark("Rectangle", content, height, width, font);
            } else if (markType.equals("Line")) {
                canvas.addMark("Line", content, height, width, font);
            } else {
                canvas.addMark(markType, content, height, width, font);
            }
        });
        
        return button;
    }

    // Method to update the text of the currently selected mark
    private void updateSelectedMarkText() {
        String newText = contentField.getText().trim();
        if (!newText.isEmpty()) {
            // Get the currently selected mark from canvas and update its text
            Mark selectedMark = canvas.getSelectedMark();
            if (selectedMark instanceof ArcLettersMark) {
                ArcLettersMark arcMark = (ArcLettersMark) selectedMark;
                arcMark.setLetters(newText);
                canvas.repaint();
            } else if (selectedMark instanceof DotMatrixMark) {
                DotMatrixMark dotMatrix = (DotMatrixMark) selectedMark;
                dotMatrix.setData(newText);
                canvas.repaint();
            } else if (selectedMark instanceof FarziMark) {
                FarziMark farziMark = (FarziMark) selectedMark;
                farziMark.setText(newText);
                canvas.repaint();
            } else if (selectedMark instanceof TextMark) {
                TextMark textMark = (TextMark) selectedMark;
                textMark.setText(newText);
                canvas.repaint();
            } else if (selectedMark instanceof BowTextMark) {
                BowTextMark bowMark = (BowTextMark) selectedMark;
                bowMark.setText(newText);
                canvas.repaint();
            } else if (selectedMark instanceof AvoidPointMark) {
                // AvoidPointMark doesn't have text content, but we can use the field for notes
                // For now, just repaint to acknowledge the selection
                canvas.repaint();
            }
            // Could add support for other text-based marks here in the future
        }
    }
    
    // Method to update the font of the currently selected mark
    private void updateSelectedMarkFont() {
        Mark selectedMark = canvas.getSelectedMark();
        if (selectedMark instanceof ArcLettersMark) {
            ArcLettersMark arcMark = (ArcLettersMark) selectedMark;
            
            String fontName = (String) fontComboBox.getSelectedItem();
            int fontSize = (Integer) heightSpinner.getValue();
            String fontStyle = (String) exFontComboBox.getSelectedItem();
            
            // Convert font style string to Font constants
            int style = Font.PLAIN;
            if ("Bold".equals(fontStyle)) {
                style = Font.BOLD;
            } else if ("Italic".equals(fontStyle)) {
                style = Font.ITALIC;
            } else if ("Bold Italic".equals(fontStyle)) {
                style = Font.BOLD | Font.ITALIC;
            }
            
            Font newFont = new Font(fontName, style, fontSize);
            arcMark.setFont(newFont);
            canvas.repaint();
        } else if (selectedMark instanceof DotMatrixMark) {
            DotMatrixMark dotMatrix = (DotMatrixMark) selectedMark;
            
            // Map formatting controls to DotMatrix properties
            int dotDiameter = (Integer) heightSpinner.getValue(); // Height → Dot Diameter
            int dotPitch = Math.max(dotDiameter + 1, (Integer) widthSpinner.getValue() / 10); // Width → Dot Pitch
            int matrixSize = Math.max(10, (Integer) spacingSpinner.getValue() * 2); // Spacing → Matrix Size
            String fontStyle = (String) exFontComboBox.getSelectedItem();
            
            // Update DotMatrix properties
            dotMatrix.setDotDiameter(dotDiameter);
            dotMatrix.setDotPitch(dotPitch);
            dotMatrix.setMatrixSize(matrixSize);
            
            // Map font style to display options
            dotMatrix.setShowGrid("Bold".equals(fontStyle) || "Bold Italic".equals(fontStyle));
            dotMatrix.setShowBorder("Italic".equals(fontStyle) || "Bold Italic".equals(fontStyle));
            
            canvas.repaint();
        } else if (selectedMark instanceof FarziMark) {
            FarziMark farziMark = (FarziMark) selectedMark;
            
            // Map formatting controls to Farzi properties
            double charHeight = (Integer) heightSpinner.getValue() * 2.0; // Height → Character Height
            double charWidth = (Integer) widthSpinner.getValue() / 3.0; // Width → Character Width 
            double charSpacing = (Integer) spacingSpinner.getValue(); // Spacing → Character Spacing
            String fontStyle = (String) exFontComboBox.getSelectedItem();
            
            // Update Farzi properties
            farziMark.setCharHeight(charHeight);
            farziMark.setCharWidth(charWidth);
            farziMark.setCharSpacing(charSpacing);
            
            // Map font style to script options  
            farziMark.setShowGrid("Bold".equals(fontStyle) || "Bold Italic".equals(fontStyle));
            if ("Italic".equals(fontStyle) || "Bold Italic".equals(fontStyle)) {
                farziMark.setScriptSlant(0.3); // More italic slant
                farziMark.setStrokeWidth(2.0); // Thicker stroke for emphasis
            } else {
                farziMark.setScriptSlant(0.15); // Gentle script slant
                farziMark.setStrokeWidth(1.5); // Normal stroke width
            }
            
            canvas.repaint();
        } else if (selectedMark instanceof AvoidPointMark) {
            AvoidPointMark avoidMark = (AvoidPointMark) selectedMark;
            
            // Map formatting controls to intelligent AvoidPoint properties
            double designRadius = (Integer) heightSpinner.getValue() * 2.0; // Height → Design Radius
            double avoidRadius = Math.max(8, designRadius * 0.6); // Width → Avoid Radius (proportional)
            double designBuffer = (Integer) spacingSpinner.getValue(); // Spacing → Design Buffer
            String fontStyle = (String) exFontComboBox.getSelectedItem();
            
            // Update intelligent AvoidPoint properties
            avoidMark.setDesignRadius(designRadius);
            avoidMark.setAvoidRadius(avoidRadius);
            avoidMark.setDesignBuffer(designBuffer);
            
            // Map font style to display options
            avoidMark.setShowDots("Bold".equals(fontStyle) || "Bold Italic".equals(fontStyle));
            if ("Italic".equals(fontStyle) || "Bold Italic".equals(fontStyle)) {
                avoidMark.setDotSpacing(1.5); // Tighter dot spacing for "italic"
            } else {
                avoidMark.setDotSpacing(2.0); // Normal dot spacing
            }
            
            canvas.repaint();
        } else if (selectedMark instanceof TextMark) {
            TextMark textMark = (TextMark) selectedMark;
            
            String fontName = (String) fontComboBox.getSelectedItem();
            int fontSize = (Integer) heightSpinner.getValue();
            String fontStyle = (String) exFontComboBox.getSelectedItem();
            
            // Convert font style string to Font constants
            int style = Font.PLAIN;
            if ("Bold".equals(fontStyle)) {
                style = Font.BOLD;
            } else if ("Italic".equals(fontStyle)) {
                style = Font.ITALIC;
            } else if ("Bold Italic".equals(fontStyle)) {
                style = Font.BOLD | Font.ITALIC;
            }
            
            // Soft coding: Apply configurable width and spacing for TextMark
            Font newFont = new Font(fontName, style, fontSize);
            textMark.setFont(newFont);
            
            // Apply width (character width) and spacing (line spacing) controls
            int width = (Integer) widthSpinner.getValue();
            int spacing = (Integer) spacingSpinner.getValue();
            
            // Map width spinner (10-500) to character width (0.5-3.0)
            double characterWidth = 0.5 + (width - 10) * 2.5 / 490.0;
            textMark.setCharacterWidth(characterWidth);
            
            // Map spacing spinner (1-50) to line spacing (0.5-3.0)
            double lineSpacing = 0.5 + (spacing - 1) * 2.5 / 49.0;
            textMark.setLineSpacing(lineSpacing);
            
            canvas.repaint();
        } else if (selectedMark instanceof BowTextMark) {
            BowTextMark bowMark = (BowTextMark) selectedMark;
            
            String fontName = (String) fontComboBox.getSelectedItem();
            int fontSize = (Integer) heightSpinner.getValue();
            String fontStyle = (String) exFontComboBox.getSelectedItem();
            
            // Convert font style string to Font constants
            int style = Font.PLAIN;
            if ("Bold".equals(fontStyle)) {
                style = Font.BOLD;
            } else if ("Italic".equals(fontStyle)) {
                style = Font.ITALIC;
            } else if ("Bold Italic".equals(fontStyle)) {
                style = Font.BOLD | Font.ITALIC;
            }
            
            Font newFont = new Font(fontName, style, fontSize);
            bowMark.setFont(newFont);
            canvas.repaint();
        }
    }
    
    // Method to update the content field when a mark is selected
    public void updateContentField(Mark selectedMark) {
        if (selectedMark instanceof ArcLettersMark) {
            ArcLettersMark arcMark = (ArcLettersMark) selectedMark;
            contentField.setText(arcMark.getLetters());
            
            // Update font controls to match the selected mark's font
            Font currentFont = arcMark.getFont();
            fontComboBox.setSelectedItem(currentFont.getName());
            heightSpinner.setValue(currentFont.getSize());
            
            // Update font style based on current font
            int style = currentFont.getStyle();
            if ((style & Font.BOLD) != 0 && (style & Font.ITALIC) != 0) {
                exFontComboBox.setSelectedItem("Bold Italic");
            } else if ((style & Font.BOLD) != 0) {
                exFontComboBox.setSelectedItem("Bold");
            } else if ((style & Font.ITALIC) != 0) {
                exFontComboBox.setSelectedItem("Italic");
            } else {
                exFontComboBox.setSelectedItem("Standard");
            }
        } else if (selectedMark instanceof DotMatrixMark) {
            DotMatrixMark dotMatrix = (DotMatrixMark) selectedMark;
            
            // Update content field with DotMatrix data
            contentField.setText(dotMatrix.getData());
            
            // Map DotMatrix properties to formatting controls
            heightSpinner.setValue(dotMatrix.getDotDiameter()); // Dot Diameter → Height
            widthSpinner.setValue(dotMatrix.getDotPitch() * 10); // Dot Pitch → Width (scaled)
            spacingSpinner.setValue(dotMatrix.getMatrixSize() / 2); // Matrix Size → Spacing (scaled)
            
            // Set font style based on display options
            boolean showGrid = dotMatrix.isShowGrid();
            boolean showBorder = dotMatrix.isShowBorder();
            
            if (showGrid && showBorder) {
                exFontComboBox.setSelectedItem("Bold Italic");
            } else if (showGrid) {
                exFontComboBox.setSelectedItem("Bold");
            } else if (showBorder) {
                exFontComboBox.setSelectedItem("Italic");
            } else {
                exFontComboBox.setSelectedItem("Standard");
            }
            
            // Set a representative font for DotMatrix
            fontComboBox.setSelectedItem("Courier New"); // Monospace font appropriate for matrix data
        } else if (selectedMark instanceof FarziMark) {
            FarziMark farziMark = (FarziMark) selectedMark;
            
            // Update content field with Farzi text
            contentField.setText(farziMark.getText());
            
            // Map Farzi properties to formatting controls
            heightSpinner.setValue((int) (farziMark.getCharHeight() / 2.0)); // Character Height → Height (scaled)
            widthSpinner.setValue((int) (farziMark.getCharWidth() * 3.0)); // Character Width → Width (scaled)
            spacingSpinner.setValue((int) farziMark.getCharSpacing()); // Character Spacing → Spacing
            
            // Set font style based on script options
            boolean showGrid = farziMark.isShowGrid();
            boolean heavyScript = farziMark.getStrokeWidth() > 1.8;
            boolean italicSlant = farziMark.getScriptSlant() > 0.25;
            
            if (showGrid && (heavyScript || italicSlant)) {
                exFontComboBox.setSelectedItem("Bold Italic");
            } else if (showGrid) {
                exFontComboBox.setSelectedItem("Bold");
            } else if (heavyScript || italicSlant) {
                exFontComboBox.setSelectedItem("Italic");
            } else {
                exFontComboBox.setSelectedItem("Standard");
            }
            
            // Set a representative font for Farzi
            fontComboBox.setSelectedItem("Arial"); // Clean font for vector text
        } else if (selectedMark instanceof AvoidPointMark) {
            AvoidPointMark avoidMark = (AvoidPointMark) selectedMark;
            
            // Update content field with intelligent AvoidPoint info (FINAL OUTPUT ONLY)
            double avoidDiameter = avoidMark.getAvoidRadius() * 2;
            double designDiameter = avoidMark.getDesignRadius() * 2;
            contentField.setText(String.format("AvoidPoint ⌀%.0f (Design: ⌀%.0f)", avoidDiameter, designDiameter));
            
            // Map intelligent AvoidPoint properties to formatting controls
            heightSpinner.setValue((int) (avoidMark.getDesignRadius() / 2.0)); // Design Radius → Height (scaled)
            widthSpinner.setValue((int) (avoidMark.getAvoidRadius() / 2.0)); // Avoid Radius → Width (scaled)
            spacingSpinner.setValue((int) avoidMark.getDesignBuffer()); // Design Buffer → Spacing
            
            // Set font style based on display options
            boolean showDots = avoidMark.isShowDots();
            boolean tightSpacing = avoidMark.getDotSpacing() < 2.0;
            
            if (showDots && tightSpacing) {
                exFontComboBox.setSelectedItem("Bold Italic");
            } else if (showDots) {
                exFontComboBox.setSelectedItem("Bold");
            } else if (tightSpacing) {
                exFontComboBox.setSelectedItem("Italic");
            } else {
                exFontComboBox.setSelectedItem("Standard");
            }
            
            // Set a representative font for AvoidPoint
            fontComboBox.setSelectedItem("Arial"); // Standard font
        } else if (selectedMark instanceof TextMark) {
            TextMark textMark = (TextMark) selectedMark;
            
            // Update content field with text content
            contentField.setText(textMark.getText());
            
            // Update font controls to match the selected mark's font
            Font currentFont = textMark.getFont();
            fontComboBox.setSelectedItem(currentFont.getName());
            heightSpinner.setValue(currentFont.getSize());
            
            // Soft coding: Map TextMark properties to width and spacing controls
            double characterWidth = textMark.getCharacterWidth();
            double lineSpacing = textMark.getLineSpacing();
            
            // Map character width (0.5-3.0) to width spinner (10-500)
            int widthValue = (int) (10 + (characterWidth - 0.5) * 490.0 / 2.5);
            widthSpinner.setValue(Math.max(10, Math.min(500, widthValue)));
            
            // Map line spacing (0.5-3.0) to spacing spinner (1-50)
            int spacingValue = (int) (1 + (lineSpacing - 0.5) * 49.0 / 2.5);
            spacingSpinner.setValue(Math.max(1, Math.min(50, spacingValue)));
            
            // Update font style based on current font
            int style = currentFont.getStyle();
            if ((style & Font.BOLD) != 0 && (style & Font.ITALIC) != 0) {
                exFontComboBox.setSelectedItem("Bold Italic");
            } else if ((style & Font.BOLD) != 0) {
                exFontComboBox.setSelectedItem("Bold");
            } else if ((style & Font.ITALIC) != 0) {
                exFontComboBox.setSelectedItem("Italic");
            } else {
                exFontComboBox.setSelectedItem("Standard");
            }
        } else if (selectedMark instanceof BowTextMark) {
            BowTextMark bowMark = (BowTextMark) selectedMark;
            
            // Update content field with bow text content
            contentField.setText(bowMark.getText());
            
            // Map BowTextMark properties to formatting controls
            Font currentFont = bowMark.getFont();
            fontComboBox.setSelectedItem(currentFont.getName());
            heightSpinner.setValue(currentFont.getSize());
            
            // Update font style based on current font
            int style = currentFont.getStyle();
            if ((style & Font.BOLD) != 0 && (style & Font.ITALIC) != 0) {
                exFontComboBox.setSelectedItem("Bold Italic");
            } else if ((style & Font.BOLD) != 0) {
                exFontComboBox.setSelectedItem("Bold");
            } else if ((style & Font.ITALIC) != 0) {
                exFontComboBox.setSelectedItem("Italic");
            } else {
                exFontComboBox.setSelectedItem("Standard");
            }
        }
        // Could add support for other text-based marks here
    }
    
    // Implementation of SelectionListener
    @Override
    public void onSelectionChanged(Mark selectedMark) {
        updateContentField(selectedMark);
    }
    
    // === VIEW & NAVIGATION CONTROLS ===
    
    // Create styled button with tooltip (same as TypesetPanel)
    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(95, 20)); // Increased width for optimal text display
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setMargin(new Insets(1, 2, 1, 2)); // Reduced side margins for better text fit
        button.setFocusPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(220, 230, 240));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(UIManager.getColor("Button.background"));
            }
        });
        
        return button;
    }
    
    private JPanel createViewControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create a grid-based layout for better alignment
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 3, 2, 3);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Zoom controls
        gbc.gridx = 0; gbc.gridy = 0;
        JButton zoomInButton = createStyledButton("Zoom In", "Zoom in to view details");
        zoomInButton.addActionListener(e -> zoomIn());
        gridPanel.add(zoomInButton, gbc);

        gbc.gridx = 1;
        JButton zoomOutButton = createStyledButton("Zoom Out", "Zoom out for overview");
        zoomOutButton.addActionListener(e -> zoomOut());
        gridPanel.add(zoomOutButton, gbc);

        // Row 2: Zoom functions
        gbc.gridx = 0; gbc.gridy = 1;
        JButton zoomSelectedButton = createStyledButton("Zoom Selected", "Zoom to fit selected objects");
        zoomSelectedButton.addActionListener(e -> zoomToSelected());
        gridPanel.add(zoomSelectedButton, gbc);

        gbc.gridx = 1;
        JButton zoomViewButton = createStyledButton("Zoom View", "Zoom to fit all objects in view");
        zoomViewButton.addActionListener(e -> zoomToFitAll());
        gridPanel.add(zoomViewButton, gbc);

        // Row 3: Navigation and refresh
        gbc.gridx = 0; gbc.gridy = 2;
        JButton moveViewButton = createStyledButton("Move View", "Pan/move the view around");
        moveViewButton.addActionListener(e -> toggleMoveView(moveViewButton));
        gridPanel.add(moveViewButton, gbc);

        gbc.gridx = 1;
        JButton refreshButton = createStyledButton("Refresh", "Refresh the entire canvas");
        refreshButton.addActionListener(e -> refreshCanvas());
        gridPanel.add(refreshButton, gbc);

        // Row 4: Grid toggle
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // Span both columns
        JButton gridToggleButton = createStyledButton("� Measurement Grid", "Toggle high-precision millimeter measurement grid");
        gridToggleButton.addActionListener(e -> toggleGrid(gridToggleButton));
        gridPanel.add(gridToggleButton, gbc);
        
        // Row 5: Snap to Grid
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2; // Span both columns
        JButton snapToGridButton = createStyledButton("📐 Snap to Grid", "Snap selected object to grid points");
        snapToGridButton.addActionListener(e -> {
            if (canvas.getSelectedMark() != null) {
                canvas.snapSelectedToGrid();
            } else {
                // Soft coding: Non-blocking selection warning
                showNonBlockingZoomStatus("Please select an object first!", true);
            }
        });
        gridPanel.add(snapToGridButton, gbc);
        
        // Reset gridwidth for future components
        gbc.gridwidth = 1;

        panel.add(gridPanel);
        return panel;
    }
    
    // === VIEW & NAVIGATION ACTION HANDLERS ===
    
    private void zoomIn() {
        if (currentZoom < 5.0) { // Maximum zoom 500%
            double previousZoom = currentZoom;
            currentZoom *= 1.25; // Increase by 25%
            
            // Ensure we don't exceed exactly 500%
            if (currentZoom > 5.0) {
                currentZoom = 5.0;
            }
            
            applyZoom();
            
            // Soft coding: Non-intrusive zoom feedback without popup dialogs
            String statusMessage;
            if (currentZoom >= 5.0) {
                statusMessage = String.format("Zoom: %.0f%% (Maximum - High Precision Mode)", currentZoom * 100);
            } else if (currentZoom >= 4.0) {
                statusMessage = String.format("Zoom: %.0f%% (Detail Mode)", currentZoom * 100);
            } else {
                statusMessage = String.format("Zoom: %.0f%%", currentZoom * 100);
            }
            
            // Soft coding: Show status message without blocking popup
            showNonBlockingZoomStatus(statusMessage, false);
            
            // Log zoom change for debugging
            System.out.printf("✅ Zoom In: %.1f%% -> %.1f%%\n", previousZoom * 100, currentZoom * 100);
            
        } else {
            // Soft coding: Non-blocking maximum zoom notification
            showNonBlockingZoomStatus("Maximum zoom (500%) reached - Use precise movements", true);
            System.out.println("⚠️ Maximum zoom level reached");
        }
    }
    
    private void zoomOut() {
        if (currentZoom > 0.25) { // Minimum zoom 25%
            double previousZoom = currentZoom;
            currentZoom *= 0.8; // Decrease by 20%
            applyZoom();
            
            // Soft coding: Non-intrusive zoom feedback without popup dialogs
            String statusMessage = String.format("Zoom: %.0f%%", currentZoom * 100);
            showNonBlockingZoomStatus(statusMessage, false);
            
            // Log zoom change for debugging
            System.out.printf("✅ Zoom Out: %.1f%% -> %.1f%%\n", previousZoom * 100, currentZoom * 100);
            
        } else {
            // Soft coding: Non-blocking minimum zoom notification
            showNonBlockingZoomStatus("Minimum zoom (25%) reached", true);
            System.out.println("⚠️ Minimum zoom level reached");
        }
    }
    
    private void applyZoom() {
        // Enhanced zoom application with high-zoom support
        System.out.printf("🔍 Applying zoom level: %.1f%%\n", currentZoom * 100);
        
        // Validate zoom level before applying
        currentZoom = Math.max(0.25, Math.min(5.0, currentZoom));
        
        // Apply zoom transformation to canvas with enhanced error handling
        try {
            canvas.setZoomLevel(currentZoom);
            canvas.repaint();
            
            // Special handling for high zoom levels
            if (currentZoom >= 5.0) {
                System.out.println("✅ High precision mode activated at 500% zoom");
                // Force a second repaint to ensure proper rendering at high zoom
                SwingUtilities.invokeLater(() -> canvas.repaint());
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error applying zoom: " + e.getMessage());
            // Fallback to safe zoom level
            currentZoom = 1.0;
            canvas.setZoomLevel(currentZoom);
            canvas.repaint();
        }
    }
    
    private void zoomToSelected() {
        // Check if any objects are selected
        if (canvas.getSelectedMark() != null) {
            Mark selectedMark = canvas.getSelectedMark();
            // Calculate appropriate zoom level based on mark size
            double markWidth = selectedMark.width;
            double markHeight = selectedMark.height;
            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();
            
            // Calculate zoom to fit mark with some padding
            double zoomX = canvasWidth * 0.8 / markWidth; // 80% of canvas width
            double zoomY = canvasHeight * 0.8 / markHeight; // 80% of canvas height
            currentZoom = Math.min(zoomX, zoomY);
            currentZoom = Math.max(0.25, Math.min(5.0, currentZoom)); // Clamp between 25% and 500%
            
            applyZoom();
            // Soft coding: Non-blocking zoom to selected notification
            showNonBlockingZoomStatus(String.format("Zoomed to selected mark at %.0f%%", currentZoom * 100), false);
        } else {
            // Soft coding: Non-blocking no selection notification
            showNonBlockingZoomStatus("No object selected to zoom to", true);
        }
    }
    
    private void zoomToFitAll() {
        // Check if there are any marks on the canvas
        if (!canvas.getMarks().isEmpty()) {
            // Calculate bounding box of all marks
            int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
            
            for (Mark mark : canvas.getMarks()) {
                minX = Math.min(minX, mark.x);
                minY = Math.min(minY, mark.y);
                maxX = Math.max(maxX, mark.x + mark.width);
                maxY = Math.max(maxY, mark.y + mark.height);
            }
            
            // Calculate zoom to fit all marks with padding
            double contentWidth = maxX - minX;
            double contentHeight = maxY - minY;
            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();
            
            if (contentWidth > 0 && contentHeight > 0) {
                double zoomX = canvasWidth * 0.9 / contentWidth; // 90% of canvas width
                double zoomY = canvasHeight * 0.9 / contentHeight; // 90% of canvas height
                currentZoom = Math.min(zoomX, zoomY);
                currentZoom = Math.max(0.25, Math.min(5.0, currentZoom)); // Clamp between 25% and 500%
                
                applyZoom();
                // Soft coding: Non-blocking zoom to fit notification
                showNonBlockingZoomStatus(String.format("Zoomed to fit all objects at %.0f%%", currentZoom * 100), false);
            } else {
                currentZoom = 1.0;
                applyZoom();
                // Soft coding: Non-blocking zoom reset notification
                showNonBlockingZoomStatus("Reset zoom to 100%", false);
            }
        } else {
            // Soft coding: Non-blocking no objects notification
            showNonBlockingZoomStatus("No objects on canvas to fit", true);
        }
    }
    
    private void toggleMoveView(JButton moveViewButton) {
        moveViewMode = !moveViewMode;
        if (moveViewMode) {
            moveViewButton.setText("Exit Move");
            moveViewButton.setBackground(Color.ORANGE);
            canvas.setMoveViewMode(true);
            // Soft coding: Non-blocking move view status notification
            showNonBlockingZoomStatus("Move view mode enabled - Click and drag to pan canvas", false);
        } else {
            moveViewButton.setText("Move View");
            moveViewButton.setBackground(UIManager.getColor("Button.background"));
            canvas.setMoveViewMode(false);
            // Soft coding: Non-blocking move view status notification
            showNonBlockingZoomStatus("Move view mode disabled", false);
        }
    }
    
    private void refreshCanvas() {
        // Reset zoom and view position
        currentZoom = 1.0;
        moveViewMode = false;
        canvas.setZoomLevel(currentZoom);
        canvas.setMoveViewMode(false);
        canvas.resetViewPosition();
        canvas.repaint();
        // Soft coding: Non-blocking refresh status notification
        showNonBlockingRefreshStatus("Canvas refreshed - Zoom reset to 100%");
    }
    
    // Modern styling methods
    private void styleSpinner(JSpinner spinner, int width) {
        spinner.setPreferredSize(new Dimension(width, 26));
        spinner.setMinimumSize(new Dimension(width, 26));
        spinner.setMaximumSize(new Dimension(width, 26));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        spinner.setBorder(new LineBorder(BORDER_COLOR, 1));
        
        // Style the text field inside spinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    }
    
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        comboBox.setBackground(SECTION_BG);
        comboBox.setBorder(new LineBorder(BORDER_COLOR, 1));
        comboBox.setPreferredSize(new Dimension(120, 26));
    }
    
    private void toggleGrid(JButton gridToggleButton) {
        boolean currentGridState = canvas.isGridVisible();
        boolean newGridState = !currentGridState;
        
        canvas.setGridVisible(newGridState);
        canvas.repaint();
        
        // Update button appearance and show non-blocking feedback
        if (newGridState) {
            gridToggleButton.setText("🔳 Precision Grid ✓");
            gridToggleButton.setBackground(ACCENT_BLUE);
            gridToggleButton.setForeground(Color.WHITE);
            // Soft coding: Non-blocking grid status notification
            showNonBlockingGridStatus("High-precision millimeter grid enabled - Working area: 200×150mm");
        } else {
            gridToggleButton.setText("🔳 Precision Grid");
            gridToggleButton.setBackground(UIManager.getColor("Button.background"));
            gridToggleButton.setForeground(Color.BLACK);
            // Soft coding: Non-blocking grid status notification
            showNonBlockingGridStatus("Precision grid disabled");
        }
    }
    
    // === SOFT CODING: NON-BLOCKING STATUS NOTIFICATION SYSTEM ===
    
    /**
     * Modern non-blocking status notification system using soft coding techniques
     * Eliminates annoying popup dialogs for smooth user experience
     */
    private void showNonBlockingZoomStatus(String message, boolean isWarning) {
        // Soft coding: Use console output for immediate feedback
        if (isWarning) {
            System.out.println("⚠️ " + message);
        } else {
            System.out.println("🔍 " + message);
        }
        
        // Soft coding: Update canvas tooltip temporarily for visual feedback
        canvas.setToolTipText(message);
        
        // Soft coding: Auto-clear tooltip after delay to prevent clutter
        Timer tooltipClearTimer = new Timer(2000, e -> canvas.setToolTipText(null));
        tooltipClearTimer.setRepeats(false);
        tooltipClearTimer.start();
        
        // Soft coding: Optional status bar update if available
        updateStatusBarIfAvailable(message, isWarning);
    }
    
    /**
     * Non-blocking grid status notification 
     */
    private void showNonBlockingGridStatus(String message) {
        System.out.println("🔳 " + message);
        canvas.setToolTipText(message);
        
        Timer tooltipClearTimer = new Timer(2000, e -> canvas.setToolTipText(null));
        tooltipClearTimer.setRepeats(false);
        tooltipClearTimer.start();
    }
    
    /**
     * Non-blocking refresh status notification
     */
    private void showNonBlockingRefreshStatus(String message) {
        System.out.println("🔄 " + message);
        canvas.setToolTipText(message);
        
        Timer tooltipClearTimer = new Timer(2000, e -> canvas.setToolTipText(null));
        tooltipClearTimer.setRepeats(false);
        tooltipClearTimer.start();
    }
    
    /**
     * Soft coding: Update status bar if available in parent component
     */
    private void updateStatusBarIfAvailable(String message, boolean isWarning) {
        try {
            // Soft coding: Try to find and update status bar component
            Container parent = getParent();
            while (parent != null) {
                if (parent instanceof JFrame) {
                    JFrame frame = (JFrame) parent;
                    // Look for status bar component
                    Component[] components = frame.getContentPane().getComponents();
                    for (Component comp : components) {
                        if (comp instanceof JLabel && comp.getName() != null && 
                            comp.getName().equals("statusBar")) {
                            JLabel statusBar = (JLabel) comp;
                            statusBar.setText(message);
                            statusBar.setForeground(isWarning ? Color.RED : Color.BLACK);
                            
                            // Auto-clear after delay
                            Timer clearTimer = new Timer(3000, e -> {
                                statusBar.setText("Ready");
                                statusBar.setForeground(Color.BLACK);
                            });
                            clearTimer.setRepeats(false);
                            clearTimer.start();
                            break;
                        }
                    }
                    break;
                }
                parent = parent.getParent();
            }
        } catch (Exception e) {
            // Soft coding: Graceful degradation if status bar not available
            System.out.println("Status bar not available, using console output only");
        }
    }
    
    // Helper method to create labels with dark text
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(DARK_BLUE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }
}
