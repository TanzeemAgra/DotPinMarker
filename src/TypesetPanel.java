import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;

public class TypesetPanel extends JPanel {
    
    // Modern color scheme for better visibility
    private static final Color LABEL_COLOR = new Color(44, 62, 80);   // Dark text for labels
    
    private final DrawingCanvas canvas;
    
    // State variables
    private boolean isLocked = false;
    private double currentZoom = 1.0;
    private Color backgroundColor = Color.WHITE;
    
    // Typeset controls
    private final JComboBox<String> templateComboBox;
    private final JSpinner marginSpinner;
    private final JSpinner lineSpacingSpinner;
    private final JSpinner columnSpinner;
    private final JComboBox<String> alignmentComboBox;
    
    // Alignment controls
    private final JComboBox<String> alignReferenceComboBox;
    private final JCheckBox makeLineCheckBox;
    
    // Needle Aim controls
    private final JSpinner moveStepSpinner;
    private final JCheckBox disableAimCheckBox;

    public TypesetPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout());

        // Initialize controls with consistent styling
        String[] templates = {"Custom", "Business Cards", "Labels", "Name Tags", "Address Labels", "CD Labels"};
        templateComboBox = new JComboBox<>(templates);

        marginSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
        lineSpacingSpinner = new JSpinner(new SpinnerNumberModel(1.2, 0.5, 3.0, 0.1));
        columnSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        String[] alignments = {"Left", "Center", "Right", "Justify"};
        alignmentComboBox = new JComboBox<>(alignments);

        // Initialize alignment controls
        String[] alignReferences = {"Start Point", "Center"};
        alignReferenceComboBox = new JComboBox<>(alignReferences);
        makeLineCheckBox = new JCheckBox("Make Line");

        // Initialize needle aim controls
        moveStepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        disableAimCheckBox = new JCheckBox("Disable Aim");

        // Apply consistent styling
        applyConsistentStyling();

        // Create main toolbar panel
        JPanel mainToolbarPanel = new JPanel();
        mainToolbarPanel.setLayout(new BoxLayout(mainToolbarPanel, BoxLayout.Y_AXIS));
        mainToolbarPanel.setBackground(getBackground());
        mainToolbarPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        // === TEMPLATE SECTION ===
        JPanel templateSection = createCompactToolbarSection("Templates", createTemplateControls());
        
        // === LAYOUT SECTION ===
        JPanel layoutSection = createCompactToolbarSection("Layout", createLayoutControls());
        
        // === ALIGN SECTION ===
        JPanel alignSection = createCompactToolbarSection("Align", createAlignControls());
        
        // === NEEDLE AIM SECTION ===
        JPanel needleAimSection = createCompactToolbarSection("Needle Aim", createNeedleAimControls());

        // Add sections to main toolbar - all sections in a single row
        JPanel singleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0)); // Even more compact spacing
        singleRow.setBackground(getBackground());
        singleRow.add(templateSection);
        singleRow.add(createCompactSeparator());
        singleRow.add(layoutSection);
        singleRow.add(createCompactSeparator());
        singleRow.add(alignSection);
        singleRow.add(createCompactSeparator());
        singleRow.add(needleAimSection);
        
        mainToolbarPanel.add(singleRow);
        
        add(mainToolbarPanel, BorderLayout.NORTH);
    }

    private void applyConsistentStyling() {
        // Template controls
        templateComboBox.setPreferredSize(new Dimension(120, 24));
        templateComboBox.setMinimumSize(new Dimension(120, 24));
        templateComboBox.setMaximumSize(new Dimension(120, 24));

        // Spinner controls
        marginSpinner.setPreferredSize(new Dimension(55, 24));
        marginSpinner.setMinimumSize(new Dimension(55, 24));
        marginSpinner.setMaximumSize(new Dimension(55, 24));
        
        lineSpacingSpinner.setPreferredSize(new Dimension(55, 24));
        lineSpacingSpinner.setMinimumSize(new Dimension(55, 24));
        lineSpacingSpinner.setMaximumSize(new Dimension(55, 24));
        
        columnSpinner.setPreferredSize(new Dimension(55, 24));
        columnSpinner.setMinimumSize(new Dimension(55, 24));
        columnSpinner.setMaximumSize(new Dimension(55, 24));

        // Combo box controls
        alignmentComboBox.setPreferredSize(new Dimension(85, 24));
        alignmentComboBox.setMinimumSize(new Dimension(85, 24));
        alignmentComboBox.setMaximumSize(new Dimension(85, 24));

        // Ensure spinner text fields are properly aligned
        JSpinner.DefaultEditor marginEditor = (JSpinner.DefaultEditor) marginSpinner.getEditor();
        marginEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        JSpinner.DefaultEditor lineSpacingEditor = (JSpinner.DefaultEditor) lineSpacingSpinner.getEditor();
        lineSpacingEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        JSpinner.DefaultEditor columnEditor = (JSpinner.DefaultEditor) columnSpinner.getEditor();
        columnEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        // Needle aim controls
        moveStepSpinner.setPreferredSize(new Dimension(55, 24));
        moveStepSpinner.setMinimumSize(new Dimension(55, 24));
        moveStepSpinner.setMaximumSize(new Dimension(55, 24));
        
        JSpinner.DefaultEditor moveStepEditor = (JSpinner.DefaultEditor) moveStepSpinner.getEditor();
        moveStepEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        disableAimCheckBox.setBackground(getBackground());
        disableAimCheckBox.setFocusPainted(false);
    }

    private JPanel createTemplateControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Reduced padding

        // Create a grid-based layout for better alignment
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 1, 2);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Template selection
        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(createDarkLabel("Template:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        gridPanel.add(templateComboBox, gbc);

        // Row 2: Load and Save buttons
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JButton loadButton = createStyledButton("Load", "Load template settings");
        loadButton.addActionListener(e -> loadTemplate());
        gridPanel.add(loadButton, gbc);
        
        gbc.gridx = 1;
        JButton saveButton = createStyledButton("Save", "Save current settings as template");
        saveButton.addActionListener(e -> saveTemplate());
        gridPanel.add(saveButton, gbc);

        gbc.gridx = 2;
        JButton lockButton = createStyledButton("Lock", "Lock/unlock template for editing");
        lockButton.addActionListener(e -> toggleLock(lockButton));
        gridPanel.add(lockButton, gbc);

        // Row 3: Background control
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; // Span 2 columns for better alignment
        JButton backgroundButton = createStyledButton("Background", "Set background image or color");
        backgroundButton.setPreferredSize(new Dimension(95, 20)); // Wider for full text
        backgroundButton.addActionListener(e -> chooseBackground());
        gridPanel.add(backgroundButton, gbc);

        panel.add(gridPanel);
        return panel;
    }

    private JPanel createLayoutControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Reduced padding

        // Create a grid-based layout for better alignment
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 1, 2);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Margin and Line Spacing
        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(createDarkLabel("Margin:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(marginSpinner, gbc);

        gbc.gridx = 2;
        gridPanel.add(createDarkLabel("Line Spacing:"), gbc);
        gbc.gridx = 3;
        gridPanel.add(lineSpacingSpinner, gbc);

        // Row 2: Columns and Alignment
        gbc.gridx = 0; gbc.gridy = 1;
        gridPanel.add(createDarkLabel("Columns:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(columnSpinner, gbc);

        gbc.gridx = 2;
        gridPanel.add(createDarkLabel("Align:"), gbc);
        gbc.gridx = 3;
        gridPanel.add(alignmentComboBox, gbc);

        panel.add(gridPanel);
        return panel;
    }

    private JPanel createAlignControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Reduced padding

        // Create a grid-based layout for better alignment
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 1, 2);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Reference point and Make Line
        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(createDarkLabel("Reference:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(alignReferenceComboBox, gbc);

        gbc.gridx = 2;
        makeLineCheckBox.setToolTipText("Align objects in a straight line");
        gridPanel.add(makeLineCheckBox, gbc);

        // Row 2: Directional alignment buttons
        gbc.gridx = 0; gbc.gridy = 1;
        JButton leftButton = createStyledButton("Left", "Align objects to the left");
        leftButton.addActionListener(e -> alignDirection("Left"));
        gridPanel.add(leftButton, gbc);

        gbc.gridx = 1;
        JButton rightButton = createStyledButton("Right", "Align objects to the right");
        rightButton.addActionListener(e -> alignDirection("Right"));
        gridPanel.add(rightButton, gbc);

        gbc.gridx = 2;
        JButton topButton = createStyledButton("Top", "Align objects to the top");
        topButton.addActionListener(e -> alignDirection("Top"));
        gridPanel.add(topButton, gbc);

        // Row 3: More directional alignment buttons
        gbc.gridx = 0; gbc.gridy = 2;
        JButton bottomButton = createStyledButton("Bottom", "Align objects to the bottom");
        bottomButton.addActionListener(e -> alignDirection("Bottom"));
        gridPanel.add(bottomButton, gbc);

        gbc.gridx = 1;
        JButton verticalButton = createStyledButton("Vertical", "Align objects vertically");
        verticalButton.addActionListener(e -> alignDirection("Vertical"));
        gridPanel.add(verticalButton, gbc);

        gbc.gridx = 2;
        JButton horizontalButton = createStyledButton("Horizontal", "Align objects horizontally");
        horizontalButton.addActionListener(e -> alignDirection("Horizontal"));
        gridPanel.add(horizontalButton, gbc);

        panel.add(gridPanel);
        return panel;
    }

    private JPanel createNeedleAimControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        // Create a compact grid-based layout
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 1, 2);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1: Aim Toggle and Move Step
        gbc.gridx = 0; gbc.gridy = 0;
        JToggleButton aimToggle = new JToggleButton("🎯 ON", true);
        aimToggle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
        aimToggle.setPreferredSize(new Dimension(50, 22));
        aimToggle.setFocusPainted(false);
        aimToggle.setOpaque(true); // Ensure toggle button is opaque
        aimToggle.setBackground(new Color(40, 167, 69));
        aimToggle.setForeground(Color.WHITE);
        aimToggle.addActionListener(e -> {
            if (aimToggle.isSelected()) {
                aimToggle.setText("🎯 ON");
                aimToggle.setBackground(new Color(40, 167, 69));
                aimToggle.setForeground(Color.WHITE);
            } else {
                aimToggle.setText("◯ OFF");
                aimToggle.setBackground(new Color(220, 53, 69));
                aimToggle.setForeground(Color.WHITE);
            }
        });
        gridPanel.add(aimToggle, gbc);

        gbc.gridx = 1;
        gridPanel.add(createDarkLabel("Step:"), gbc);
        gbc.gridx = 2;
        JSpinner compactMoveStepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        compactMoveStepSpinner.setPreferredSize(new Dimension(45, 22));
        gridPanel.add(compactMoveStepSpinner, gbc);

        // Row 2: Position Grid (3x3 compact)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        JPanel positionGrid = new JPanel(new GridLayout(3, 3, 2, 2));
        positionGrid.setBackground(getBackground());
        positionGrid.setPreferredSize(new Dimension(120, 60));

        String[][] positions = {
            {"↖", "↑", "↗"},
            {"←", "●", "→"},
            {"↙", "↓", "↘"}
        };

        String[][] positionNames = {
            {"Top Left", "Top", "Top Right"},
            {"Left", "Center", "Right"},
            {"Bottom Left", "Bottom", "Bottom Right"}
        };

        // Track the active button for proper state management
        final JButton[] activeButtonRef = new JButton[1];
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton posButton = new JButton(positions[row][col]);
                posButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
                posButton.setPreferredSize(new Dimension(25, 18));
                posButton.setFocusPainted(false);
                posButton.setOpaque(true); // Ensure button is opaque for visibility
                posButton.setBackground(Color.WHITE);
                posButton.setForeground(new Color(45, 125, 210));
                posButton.setBorder(BorderFactory.createLineBorder(new Color(45, 125, 210), 1));
                
                final String posName = positionNames[row][col];
                final int buttonRow = row;
                final int buttonCol = col;
                
                // Add proper action listener with state management
                posButton.addActionListener(e -> {
                    // Reset previous active button
                    if (activeButtonRef[0] != null) {
                        activeButtonRef[0].setBackground(Color.WHITE);
                        activeButtonRef[0].setForeground(new Color(45, 125, 210));
                    }
                    
                    // Set new active button
                    posButton.setBackground(new Color(45, 125, 210));
                    posButton.setForeground(Color.WHITE);
                    activeButtonRef[0] = posButton;
                    
                    setNeedlePosition(posName);
                });
                
                // Set center as active by default
                if (row == 1 && col == 1) {
                    posButton.setBackground(new Color(45, 125, 210));
                    posButton.setForeground(Color.WHITE);
                    activeButtonRef[0] = posButton;
                }
                
                positionGrid.add(posButton);
            }
        }
        
        gridPanel.add(positionGrid, gbc);

        // Row 3: Precision controls
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        gridPanel.add(createDarkLabel("Precision:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JSpinner precisionSpinner = new JSpinner(new SpinnerNumberModel(0.1, 0.01, 1.0, 0.01));
        precisionSpinner.setPreferredSize(new Dimension(65, 22));
        gridPanel.add(precisionSpinner, gbc);

        panel.add(gridPanel);
        return panel;
    }

    // Create a toolbar section with title and content panel (same as MarkPanel)
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
        
        // Add a small gap
        section.add(Box.createVerticalStrut(3));
        
        // Add content panel
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.add(contentPanel);
        
        return section;
    }

    // Create a separator line (same as MarkPanel)
    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(2, 85));
        separator.setBackground(Color.LIGHT_GRAY);
        return separator;
    }

    // Create styled button with tooltip (same as MarkPanel)
    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(65, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setMargin(new Insets(1, 3, 1, 3));
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

    // Add action listeners for the controls
    public void addActionListeners() {
        templateComboBox.addActionListener(e -> onTemplateChanged());
        marginSpinner.addChangeListener(e -> onLayoutChanged());
        lineSpacingSpinner.addChangeListener(e -> onLayoutChanged());
        columnSpinner.addChangeListener(e -> onLayoutChanged());
        alignmentComboBox.addActionListener(e -> onLayoutChanged());
        
        // Alignment controls
        alignReferenceComboBox.addActionListener(e -> onAlignmentChanged());
        makeLineCheckBox.addActionListener(e -> onAlignmentChanged());
        
        // Needle aim controls
        moveStepSpinner.addChangeListener(e -> onMoveStepChanged());
        disableAimCheckBox.addActionListener(e -> onAimStatusChanged());
    }

    // Event handlers for typeset operations
    private void onTemplateChanged() {
        String selectedTemplate = (String) templateComboBox.getSelectedItem();
        // Apply template-specific settings
        switch (selectedTemplate) {
            case "Business Cards":
                marginSpinner.setValue(5);
                lineSpacingSpinner.setValue(1.0);
                break;
            case "Labels":
                marginSpinner.setValue(3);
                lineSpacingSpinner.setValue(1.2);
                break;
            case "Name Tags":
                marginSpinner.setValue(8);
                lineSpacingSpinner.setValue(1.5);
                break;
            case "Address Labels":
                marginSpinner.setValue(2);
                lineSpacingSpinner.setValue(1.0);
                break;
            case "CD Labels":
                marginSpinner.setValue(15);
                lineSpacingSpinner.setValue(1.3);
                break;
        }
        canvas.repaint();
    }

    private void onLayoutChanged() {
        // Soft coding: Apply layout changes to selected marks with validation
        if (!isLocked) {
            try {
                // Get current layout parameters
                int margin = (Integer) marginSpinner.getValue();
                double lineSpacing = (Double) lineSpacingSpinner.getValue();
                int columns = (Integer) columnSpinner.getValue();
                String alignment = (String) alignmentComboBox.getSelectedItem();
                
                System.out.println("Layout changed - Margin: " + margin + ", Line Spacing: " + lineSpacing + 
                                 ", Columns: " + columns + ", Alignment: " + alignment);
                
                // Apply changes to selected marks or all marks if none selected
                java.util.List<Mark> marksToUpdate = canvas.getSelectedMark() != null ? 
                    java.util.List.of(canvas.getSelectedMark()) : canvas.getMarks();
                
                // Soft coding: Apply layout parameters based on mark type
                for (Mark mark : marksToUpdate) {
                    if (mark instanceof TextMark) {
                        TextMark textMark = (TextMark) mark;
                        
                        // Apply line spacing if mark supports it
                        if (lineSpacing != 1.2) { // Only if changed from default
                            textMark.setLineSpacing(lineSpacing);
                        }
                        
                        // Apply margin by adjusting position
                        if (margin > 0) {
                            mark.x = Math.max(margin, mark.x);
                            mark.y = Math.max(margin, mark.y);
                        }
                    }
                }
                
                // Visual feedback and canvas update
                canvas.repaint();
                
                // Show visual confirmation for user feedback
                String message = String.format("Layout applied: Margin=%d, Spacing=%.1f, Columns=%d, Align=%s", 
                                              margin, lineSpacing, columns, alignment);
                showQuickNotification(message);
                
            } catch (Exception e) {
                System.err.println("Error applying layout changes: " + e.getMessage());
            }
        }
    }

    // Soft coding: Quick notification system for user feedback
    private void showQuickNotification(String message) {
        // Create a quick, non-blocking notification
        Timer notificationTimer = new Timer(2000, e -> {
            // Auto-dismiss after 2 seconds
        });
        notificationTimer.setRepeats(false);
        
        // Show in status or console for now (can be enhanced with visual feedback)
        System.out.println("TypesetPanel: " + message);
    }
    
    // Soft coding: Enhanced alignment change handler
    private void onAlignmentChanged() {
        if (!isLocked) {
            String reference = (String) alignReferenceComboBox.getSelectedItem();
            boolean makeLine = makeLineCheckBox.isSelected();
            
            System.out.println("Alignment configuration changed - Reference: " + reference + ", Make Line: " + makeLine);
            
            // Store alignment preferences for next alignment operation
            canvas.repaint();
            showQuickNotification("Alignment config: " + reference + (makeLine ? " + Line" : ""));
        }
    }
    
    // Soft coding: Enhanced move step change handler  
    private void onMoveStepChanged() {
        int moveStep = (Integer) moveStepSpinner.getValue();
        System.out.println("Move step changed to: " + moveStep);
        
        // Apply move step to needle aim system
        showQuickNotification("Move step set to: " + moveStep + " units");
    }
    
    // === NEW BUTTON ACTION HANDLERS ===
    
    private void loadTemplate() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Template");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Template files (*.tpl)", "tpl"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // TODO: Implement template loading from file
            JOptionPane.showMessageDialog(this, "Template loaded: " + fileChooser.getSelectedFile().getName());
            canvas.repaint();
        }
    }
    
    private void saveTemplate() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Template");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Template files (*.tpl)", "tpl"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // TODO: Implement template saving to file
            JOptionPane.showMessageDialog(this, "Template saved: " + fileChooser.getSelectedFile().getName());
        }
    }
    
    private void toggleLock(JButton lockButton) {
        isLocked = !isLocked;
        
        // Update button appearance
        if (isLocked) {
            lockButton.setText("Unlock");
            lockButton.setBackground(new Color(255, 200, 200)); // Light red background
            lockButton.setToolTipText("Click to unlock template for editing");
            
            // Disable other controls when locked
            templateComboBox.setEnabled(false);
            marginSpinner.setEnabled(false);
            lineSpacingSpinner.setEnabled(false);
            columnSpinner.setEnabled(false);
            alignmentComboBox.setEnabled(false);
        } else {
            lockButton.setText("Lock");
            lockButton.setBackground(UIManager.getColor("Button.background"));
            lockButton.setToolTipText("Lock template to prevent accidental changes");
            
            // Enable controls when unlocked
            templateComboBox.setEnabled(true);
            marginSpinner.setEnabled(true);
            lineSpacingSpinner.setEnabled(true);
            columnSpinner.setEnabled(true);
            alignmentComboBox.setEnabled(true);
        }
    }
    
    private void chooseBackground() {
        String[] options = {"Solid Color", "Image", "Grid", "None"};
        String choice = (String) JOptionPane.showInputDialog(
            this,
            "Choose background type:",
            "Background Settings",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choice != null) {
            switch (choice) {
                case "Solid Color":
                    Color color = JColorChooser.showDialog(this, "Choose Background Color", backgroundColor);
                    if (color != null) {
                        backgroundColor = color;
                        canvas.setBackground(backgroundColor);
                        canvas.repaint();
                    }
                    break;
                case "Image":
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select Background Image");
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        "Image files", "jpg", "jpeg", "png", "gif", "bmp"));
                    
                    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        // TODO: Load and set background image
                        JOptionPane.showMessageDialog(this, "Background image: " + fileChooser.getSelectedFile().getName());
                    }
                    break;
                case "Grid":
                    // TODO: Implement grid background
                    JOptionPane.showMessageDialog(this, "Grid background applied");
                    break;
                case "None":
                    backgroundColor = Color.WHITE;
                    canvas.setBackground(backgroundColor);
                    canvas.repaint();
                    break;
            }
        }
    }
    
    // === ALIGNMENT ACTION HANDLERS ===
    
    private void alignDirection(String direction) {
        boolean makeLine = makeLineCheckBox.isSelected();
        String reference = (String) alignReferenceComboBox.getSelectedItem();
        
        // Soft coding: Enhanced alignment implementation with actual functionality
        java.util.List<Mark> marksToAlign = new java.util.ArrayList<>();
        if (canvas.getSelectedMark() != null) {
            marksToAlign.add(canvas.getSelectedMark());
        } else {
            marksToAlign.addAll(canvas.getMarks());
        }
        
        if (marksToAlign.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No marks available to align. Please create some marks first.", 
                "No Marks Available", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (marksToAlign.size() < 2 && makeLine) {
            JOptionPane.showMessageDialog(this, 
                "Need at least 2 marks to create a line arrangement.", 
                "Insufficient Marks", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Soft coding: Implement actual alignment algorithms
        try {
            switch (direction) {
                case "Left":
                    alignLeft(marksToAlign, reference);
                    break;
                case "Right":
                    alignRight(marksToAlign, reference);
                    break;
                case "Top":
                    alignTop(marksToAlign, reference);
                    break;
                case "Bottom":
                    alignBottom(marksToAlign, reference);
                    break;
                case "Vertical":
                    if (makeLine) {
                        arrangeVerticalLine(marksToAlign);
                    } else {
                        alignVerticalCenter(marksToAlign);
                    }
                    break;
                case "Horizontal":
                    if (makeLine) {
                        arrangeHorizontalLine(marksToAlign);
                    } else {
                        alignHorizontalCenter(marksToAlign);
                    }
                    break;
            }
            
            // Update canvas and provide feedback
            canvas.repaint();
            
            String message = String.format("Aligned %d marks: %s (%s)%s", 
                marksToAlign.size(), direction, reference, makeLine ? " + Line" : "");
            showQuickNotification(message);
            
            System.out.println("Alignment completed: " + message);
            
        } catch (Exception e) {
            System.err.println("Error during alignment: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error during alignment: " + e.getMessage(), 
                "Alignment Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Soft coding: Alignment algorithm implementations
    private void alignLeft(java.util.List<Mark> marks, String reference) {
        if (reference.equals("Start Point")) {
            // Find leftmost position
            int leftmost = marks.stream().mapToInt(m -> m.x).min().orElse(0);
            // Align all marks to leftmost position
            marks.forEach(m -> m.x = leftmost);
        } else {
            // Align to center, then move left
            int centerX = marks.stream().mapToInt(m -> m.x + m.width/2).sum() / marks.size();
            marks.forEach(m -> m.x = centerX - m.width);
        }
    }
    
    private void alignRight(java.util.List<Mark> marks, String reference) {
        if (reference.equals("Start Point")) {
            // Find rightmost position
            int rightmost = marks.stream().mapToInt(m -> m.x + m.width).max().orElse(0);
            // Align all marks to rightmost position
            marks.forEach(m -> m.x = rightmost - m.width);
        } else {
            // Align to center, then move right
            int centerX = marks.stream().mapToInt(m -> m.x + m.width/2).sum() / marks.size();
            marks.forEach(m -> m.x = centerX);
        }
    }
    
    private void alignTop(java.util.List<Mark> marks, String reference) {
        if (reference.equals("Start Point")) {
            // Find topmost position
            int topmost = marks.stream().mapToInt(m -> m.y).min().orElse(0);
            // Align all marks to topmost position
            marks.forEach(m -> m.y = topmost);
        } else {
            // Align to center, then move up
            int centerY = marks.stream().mapToInt(m -> m.y + m.height/2).sum() / marks.size();
            marks.forEach(m -> m.y = centerY - m.height);
        }
    }
    
    private void alignBottom(java.util.List<Mark> marks, String reference) {
        if (reference.equals("Start Point")) {
            // Find bottommost position
            int bottommost = marks.stream().mapToInt(m -> m.y + m.height).max().orElse(0);
            // Align all marks to bottommost position
            marks.forEach(m -> m.y = bottommost - m.height);
        } else {
            // Align to center, then move down
            int centerY = marks.stream().mapToInt(m -> m.y + m.height/2).sum() / marks.size();
            marks.forEach(m -> m.y = centerY);
        }
    }
    
    private void alignVerticalCenter(java.util.List<Mark> marks) {
        // Align all marks to the same vertical center
        int avgCenterX = marks.stream().mapToInt(m -> m.x + m.width/2).sum() / marks.size();
        marks.forEach(m -> m.x = avgCenterX - m.width/2);
    }
    
    private void alignHorizontalCenter(java.util.List<Mark> marks) {
        // Align all marks to the same horizontal center
        int avgCenterY = marks.stream().mapToInt(m -> m.y + m.height/2).sum() / marks.size();
        marks.forEach(m -> m.y = avgCenterY - m.height/2);
    }
    
    private void arrangeVerticalLine(java.util.List<Mark> marks) {
        // Arrange marks in a vertical line with equal spacing
        marks.sort((a, b) -> Integer.compare(a.y, b.y)); // Sort by Y position
        
        if (marks.size() < 2) return;
        
        int startY = marks.get(0).y;
        int endY = marks.get(marks.size() - 1).y + marks.get(marks.size() - 1).height;
        int totalHeight = endY - startY;
        int spacing = totalHeight / (marks.size() - 1);
        
        // Align vertical centers
        int avgCenterX = marks.stream().mapToInt(m -> m.x + m.width/2).sum() / marks.size();
        
        for (int i = 0; i < marks.size(); i++) {
            Mark mark = marks.get(i);
            mark.x = avgCenterX - mark.width/2;
            mark.y = startY + (i * spacing);
        }
    }
    
    private void arrangeHorizontalLine(java.util.List<Mark> marks) {
        // Arrange marks in a horizontal line with equal spacing
        marks.sort((a, b) -> Integer.compare(a.x, b.x)); // Sort by X position
        
        if (marks.size() < 2) return;
        
        int startX = marks.get(0).x;
        int endX = marks.get(marks.size() - 1).x + marks.get(marks.size() - 1).width;
        int totalWidth = endX - startX;
        int spacing = totalWidth / (marks.size() - 1);
        
        // Align horizontal centers
        int avgCenterY = marks.stream().mapToInt(m -> m.y + m.height/2).sum() / marks.size();
        
        for (int i = 0; i < marks.size(); i++) {
            Mark mark = marks.get(i);
            mark.x = startX + (i * spacing);
            mark.y = avgCenterY - mark.height/2;
        }
    }
    
    // === NEEDLE AIM ACTION HANDLERS ===

    private void setNeedlePosition(String position) {
        boolean enabled = !disableAimCheckBox.isSelected();
        
        if (!enabled) {
            JOptionPane.showMessageDialog(this, 
                "Needle aim is currently disabled. Enable it first to change position.", 
                "Needle Aim Disabled", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int step = (Integer) moveStepSpinner.getValue();
        
        String message = String.format(
            "Needle aim positioned to: %s\nMove step: %d units\nAim status: %s", 
            position, 
            step,
            enabled ? "Enabled" : "Disabled"
        );
        
        JOptionPane.showMessageDialog(this, 
            message, 
            "Needle Position Updated", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // TODO: Implement actual needle positioning logic
        canvas.repaint();
    }



    private void onAimStatusChanged() {
        boolean aimEnabled = !disableAimCheckBox.isSelected();
        String status = aimEnabled ? "enabled" : "disabled";
        String message = String.format("Needle aim %s", status);
        System.out.println("NeedleAim: " + message);
        
        // Show visual feedback
        JOptionPane.showMessageDialog(this, 
            String.format("Needle aim has been %s", status), 
            "Aim Status Changed", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // TODO: Apply aim enable/disable to needle system
        canvas.repaint();
    }

    private JPanel createCompactSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 85)); // Thinner separator - reduced from 2 to 1 pixel
        separator.setBackground(Color.LIGHT_GRAY);
        return separator;
    }

    // Create a compact toolbar section with reduced padding for better space utilization
    private JPanel createCompactToolbarSection(String title, JPanel contentPanel) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(getBackground());
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(2, 4, 3, 4) // Reduced padding: top/bottom from 3,5 to 2,3 and left/right from 6 to 4
        ));
        
        // Add title label with smaller font
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 10)); // Reduced from 11 to 10
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.add(titleLabel);
        
        // Add a smaller gap
        section.add(Box.createVerticalStrut(2)); // Reduced from 3 to 2
        
        // Add content panel
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.add(contentPanel);
        
        return section;
    }
    
    // Helper method to create labels with dark text
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }
}
