import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

public class NeedleAimPanelAdvanced extends JPanel {
    private final DrawingCanvas canvas;
    
    // Enhanced state variables
    private boolean aimEnabled = true;
    private String currentPosition = "Center";
    private int moveStep = 1;
    private double precision = 0.1;
    private boolean autoAdjust = false;
    
    // Enhanced UI components
    private final JSpinner moveStepSpinner;
    private final JSpinner precisionSpinner;
    private final JToggleButton aimToggleButton;
    private final JToggleButton autoAdjustButton;
    private final JLabel statusLabel;
    private final JProgressBar precisionIndicator;
    private JButton activePositionButton;

    // Enhanced color scheme
    private static final Color PRIMARY_COLOR = new Color(45, 125, 210);
    private static final Color SECONDARY_COLOR = new Color(240, 245, 250);
    private static final Color ACCENT_COLOR = new Color(255, 193, 7);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color DARK_TEXT = new Color(33, 37, 41);
    private static final Color LIGHT_TEXT = new Color(108, 117, 125);

    public NeedleAimPanelAdvanced(DrawingCanvas canvas) {
        this.canvas = canvas;
        setBackground(SECONDARY_COLOR);
        setLayout(new BorderLayout());

        // Initialize enhanced controls
        moveStepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        precisionSpinner = new JSpinner(new SpinnerNumberModel(0.1, 0.01, 1.0, 0.01));
        aimToggleButton = new JToggleButton("◯ AIM OFF", true);
        autoAdjustButton = new JToggleButton("AUTO", false);
        statusLabel = new JLabel("Ready");
        precisionIndicator = new JProgressBar(0, 100);

        // Apply enhanced styling
        applyAdvancedStyling();
        
        // Create the main panel with modern design
        add(createAdvancedMainPanel(), BorderLayout.CENTER);
        add(createAdvancedStatusBar(), BorderLayout.SOUTH);
    }

    private void applyAdvancedStyling() {
        // Move step spinner with modern styling
        moveStepSpinner.setPreferredSize(new Dimension(80, 32));
        styleSpinner(moveStepSpinner);
        
        // Precision spinner with modern styling
        precisionSpinner.setPreferredSize(new Dimension(80, 32));
        styleSpinner(precisionSpinner);
        
        // Aim toggle button with enhanced styling
        aimToggleButton.setPreferredSize(new Dimension(120, 35));
        styleToggleButton(aimToggleButton, SUCCESS_COLOR);
        aimToggleButton.addActionListener(e -> toggleAim());
        
        // Auto adjust button
        autoAdjustButton.setPreferredSize(new Dimension(80, 28));
        styleToggleButton(autoAdjustButton, ACCENT_COLOR);
        autoAdjustButton.addActionListener(e -> toggleAutoAdjust());
        
        // Status label styling
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        statusLabel.setForeground(DARK_TEXT);
        
        // Precision indicator styling
        precisionIndicator.setPreferredSize(new Dimension(100, 8));
        precisionIndicator.setValue(50);
        precisionIndicator.setStringPainted(false);
        precisionIndicator.setBackground(Color.LIGHT_GRAY);
        precisionIndicator.setForeground(SUCCESS_COLOR);
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setBorder(createRoundedBorder(PRIMARY_COLOR, 1));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        editor.getTextField().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        editor.getTextField().setBackground(Color.WHITE);
    }

    private void styleToggleButton(JToggleButton button, Color activeColor) {
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorder(createRoundedBorder(activeColor, 2));
        button.setBackground(Color.WHITE);
        button.setForeground(activeColor);
        
        // Add state change styling
        button.addItemListener(e -> {
            if (button.isSelected()) {
                button.setBackground(activeColor);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(activeColor);
            }
        });
    }

    private JPanel createAdvancedMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header section with title and main controls
        mainPanel.add(createHeaderSection());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Position control grid (enhanced 3x3 layout)
        mainPanel.add(createAdvancedPositionGrid());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Advanced controls section
        mainPanel.add(createAdvancedControlsSection());
        
        return mainPanel;
    }

    private JPanel createHeaderSection() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SECONDARY_COLOR);
        header.setBorder(createRoundedBorder(PRIMARY_COLOR, 2));
        header.setPreferredSize(new Dimension(0, 60));

        // Title
        JLabel titleLabel = new JLabel("◈ NEEDLE AIM CONTROL", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        // Main toggle button
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        togglePanel.setBackground(SECONDARY_COLOR);
        togglePanel.add(aimToggleButton);
        
        header.add(titleLabel, BorderLayout.CENTER);
        header.add(togglePanel, BorderLayout.EAST);
        
        return header;
    }

    private JPanel createAdvancedPositionGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 8, 8));
        gridPanel.setBackground(SECONDARY_COLOR);
        gridPanel.setBorder(BorderFactory.createCompoundBorder(
            createRoundedBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Position button array
        String[][] positions = {
            {"↖ TL", "↑ TOP", "↗ TR"},
            {"← LEFT", "● CENTER", "→ RIGHT"},
            {"↙ BL", "↓ BOTTOM", "↘ BR"}
        };

        String[][] positionNames = {
            {"Top Left", "Top", "Top Right"},
            {"Left", "Center", "Right"},
            {"Bottom Left", "Bottom", "Bottom Right"}
        };

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton posButton = createAdvancedPositionButton(
                    positions[row][col], 
                    positionNames[row][col]
                );
                
                // Set center as active initially
                if (row == 1 && col == 1) {
                    activePositionButton = posButton;
                    posButton.setBackground(PRIMARY_COLOR);
                    posButton.setForeground(Color.WHITE);
                }
                
                gridPanel.add(posButton);
            }
        }

        return gridPanel;
    }

    private JButton createAdvancedPositionButton(String text, String position) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(70, 50));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorder(createRoundedBorder(PRIMARY_COLOR, 1));
        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY_COLOR);
        
        // Enhanced hover and click effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button != activePositionButton && button.isEnabled()) {
                    button.setBackground(new Color(230, 240, 250));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != activePositionButton) {
                    button.setBackground(Color.WHITE);
                }
            }
        });
        
        button.addActionListener(e -> setAdvancedNeedlePosition(position, button));
        return button;
    }

    private JPanel createAdvancedControlsSection() {
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setBackground(SECONDARY_COLOR);
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(
            createRoundedBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1: Move Step
        gbc.gridx = 0; gbc.gridy = 0;
        controlsPanel.add(createStyledLabel("Move Step:"), gbc);
        gbc.gridx = 1;
        controlsPanel.add(moveStepSpinner, gbc);
        gbc.gridx = 2;
        controlsPanel.add(createStyledLabel("units"), gbc);

        // Row 2: Precision
        gbc.gridx = 0; gbc.gridy = 1;
        controlsPanel.add(createStyledLabel("Precision:"), gbc);
        gbc.gridx = 1;
        controlsPanel.add(precisionSpinner, gbc);
        gbc.gridx = 2;
        controlsPanel.add(autoAdjustButton, gbc);

        // Row 3: Precision indicator
        gbc.gridx = 0; gbc.gridy = 2;
        controlsPanel.add(createStyledLabel("Accuracy:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(precisionIndicator, gbc);

        return controlsPanel;
    }

    private JPanel createAdvancedStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(PRIMARY_COLOR);
        statusBar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        statusBar.setPreferredSize(new Dimension(0, 35));

        statusLabel.setForeground(Color.WHITE);
        statusLabel.setText("● Position: " + currentPosition + " | Step: " + moveStep + " | Status: Ready");

        JLabel versionLabel = new JLabel("v2.0 Advanced", JLabel.RIGHT);
        versionLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
        versionLabel.setForeground(new Color(255, 255, 255, 180));

        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(versionLabel, BorderLayout.EAST);

        return statusBar;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label.setForeground(DARK_TEXT);
        return label;
    }

    private AbstractBorder createRoundedBorder(Color color, int thickness) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(thickness));
                g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, 8, 8));
                g2.dispose();
            }
            
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(thickness + 2, thickness + 2, thickness + 2, thickness + 2);
            }
        };
    }

    // === ENHANCED ACTION HANDLERS ===

    private void toggleAim() {
        aimEnabled = aimToggleButton.isSelected();
        updateAimToggleButton();
        updateStatus();
        
        // Visual feedback
        showAdvancedNotification(
            aimEnabled ? "Needle Aim Activated" : "Needle Aim Deactivated",
            aimEnabled ? SUCCESS_COLOR : DANGER_COLOR
        );
    }

    private void toggleAutoAdjust() {
        autoAdjust = autoAdjustButton.isSelected();
        updatePrecisionIndicator();
        updateStatus();
    }

    private void setAdvancedNeedlePosition(String position, JButton button) {
        if (!aimEnabled) {
            showAdvancedNotification("Enable Needle Aim First!", DANGER_COLOR);
            return;
        }

        // Update active button styling
        if (activePositionButton != null) {
            activePositionButton.setBackground(Color.WHITE);
            activePositionButton.setForeground(PRIMARY_COLOR);
        }
        
        activePositionButton = button;
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        
        currentPosition = position;
        updateStatus();
        updatePrecisionIndicator();
        
        // Enhanced feedback
        showAdvancedNotification("Position: " + position, SUCCESS_COLOR);
        canvas.repaint();
    }

    private void updateAimToggleButton() {
        if (aimEnabled) {
            aimToggleButton.setText("● AIM ON");
            aimToggleButton.setBackground(SUCCESS_COLOR);
            aimToggleButton.setForeground(Color.WHITE);
        } else {
            aimToggleButton.setText("◯ AIM OFF");
            aimToggleButton.setBackground(Color.WHITE);
            aimToggleButton.setForeground(SUCCESS_COLOR);
        }
    }

    private void updateStatus() {
        String status = aimEnabled ? "Active" : "Inactive";
        String autoStatus = autoAdjust ? " | Auto-Adjust: ON" : "";
        statusLabel.setText("● Position: " + currentPosition + " | Step: " + moveStep + " | Status: " + status + autoStatus);
    }

    private void updatePrecisionIndicator() {
        int value = (int)((precision * 100) + (autoAdjust ? 20 : 0));
        precisionIndicator.setValue(Math.min(value, 100));
        precisionIndicator.setForeground(value > 80 ? SUCCESS_COLOR : value > 50 ? ACCENT_COLOR : DANGER_COLOR);
    }

    private void showAdvancedNotification(String message, Color color) {
        // Create a temporary notification label
        JLabel notification = new JLabel(message, JLabel.CENTER);
        notification.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        notification.setForeground(Color.WHITE);
        notification.setBackground(color);
        notification.setOpaque(true);
        notification.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Add notification to a dialog for better visibility
        JDialog notificationDialog = new JDialog();
        notificationDialog.setUndecorated(true);
        notificationDialog.add(notification);
        notificationDialog.pack();
        notificationDialog.setLocationRelativeTo(this);
        notificationDialog.setVisible(true);

        // Auto-hide after 2 seconds
        Timer timer = new Timer(2000, e -> notificationDialog.dispose());
        timer.setRepeats(false);
        timer.start();
    }

    public void addAdvancedActionListeners() {
        // Move step spinner listener
        moveStepSpinner.addChangeListener(e -> {
            moveStep = (Integer) moveStepSpinner.getValue();
            updateStatus();
        });

        // Precision spinner listener
        precisionSpinner.addChangeListener(e -> {
            precision = (Double) precisionSpinner.getValue();
            updatePrecisionIndicator();
        });
    }

    // === PUBLIC GETTERS ===

    public boolean isAimEnabled() {
        return aimEnabled;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public int getMoveStep() {
        return moveStep;
    }

    public double getPrecision() {
        return precision;
    }

    public boolean isAutoAdjustEnabled() {
        return autoAdjust;
    }
}
