import java.awt.*;
import javax.swing.*;

public class NeedleAimPanel extends JPanel {
    private final DrawingCanvas canvas;
    
    // State variables
    private boolean aimEnabled = true;
    private String currentPosition = "Center";
    private int moveStep = 1;
    
    // Needle aim controls
    private final JSpinner moveStepSpinner;
    private final JCheckBox disableAimCheckBox;

    public NeedleAimPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout());

        // Initialize controls with consistent styling
        moveStepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        disableAimCheckBox = new JCheckBox("Disable Aim");

        // Apply consistent styling
        applyConsistentStyling();

        // Create main toolbar panel
        JPanel mainToolbarPanel = new JPanel();
        mainToolbarPanel.setLayout(new BoxLayout(mainToolbarPanel, BoxLayout.Y_AXIS));
        mainToolbarPanel.setBackground(getBackground());
        mainToolbarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // === NEEDLE AIM SECTION ===
        JPanel needleAimSection = createToolbarSection("Needle Aim Control", createNeedleAimControls());

        // Add section to main toolbar
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topRow.setBackground(getBackground());
        topRow.add(needleAimSection);
        
        mainToolbarPanel.add(topRow);
        
        add(mainToolbarPanel, BorderLayout.NORTH);
    }

    private void applyConsistentStyling() {
        // Move step spinner
        moveStepSpinner.setPreferredSize(new Dimension(80, 24));
        ((JSpinner.DefaultEditor) moveStepSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        // Disable aim checkbox
        disableAimCheckBox.setBackground(getBackground());
        disableAimCheckBox.setFocusPainted(false);
    }

    private JPanel createNeedleAimControls() {
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

        // Row 1: Move Step and Disable Aim
        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(new JLabel("Move Step:"), gbc);
        gbc.gridx = 1;
        gridPanel.add(moveStepSpinner, gbc);

        gbc.gridx = 2;
        gridPanel.add(disableAimCheckBox, gbc);

        // Row 2: Position controls - Top row
        gbc.gridx = 0; gbc.gridy = 1;
        JButton topLeftButton = createPositionButton("Top Left", "Position needle aim to top left corner");
        topLeftButton.addActionListener(e -> setNeedlePosition("Top Left"));
        gridPanel.add(topLeftButton, gbc);

        gbc.gridx = 1;
        // Empty space for alignment
        gridPanel.add(Box.createHorizontalStrut(80), gbc);

        gbc.gridx = 2;
        JButton topRightButton = createPositionButton("Top Right", "Position needle aim to top right corner");
        topRightButton.addActionListener(e -> setNeedlePosition("Top Right"));
        gridPanel.add(topRightButton, gbc);

        // Row 3: Position controls - Middle row (Center)
        gbc.gridx = 0; gbc.gridy = 2;
        // Empty space
        gridPanel.add(Box.createHorizontalStrut(80), gbc);

        gbc.gridx = 1;
        JButton centerButton = createPositionButton("Center", "Position needle aim to center");
        centerButton.addActionListener(e -> setNeedlePosition("Center"));
        gridPanel.add(centerButton, gbc);

        gbc.gridx = 2;
        // Empty space
        gridPanel.add(Box.createHorizontalStrut(80), gbc);

        // Row 4: Position controls - Bottom row
        gbc.gridx = 0; gbc.gridy = 3;
        JButton bottomLeftButton = createPositionButton("Bottom Left", "Position needle aim to bottom left corner");
        bottomLeftButton.addActionListener(e -> setNeedlePosition("Bottom Left"));
        gridPanel.add(bottomLeftButton, gbc);

        gbc.gridx = 1;
        // Empty space for alignment
        gridPanel.add(Box.createHorizontalStrut(80), gbc);

        gbc.gridx = 2;
        JButton bottomRightButton = createPositionButton("Bottom Right", "Position needle aim to bottom right corner");
        bottomRightButton.addActionListener(e -> setNeedlePosition("Bottom Right"));
        gridPanel.add(bottomRightButton, gbc);

        panel.add(gridPanel);
        return panel;
    }

    private JButton createPositionButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(80, 24));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setBackground(new Color(250, 250, 250));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(230, 240, 250));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(250, 250, 250));
            }
        });
        
        return button;
    }

    private JPanel createToolbarSection(String title, JPanel content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(getBackground());
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(),
                title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font(Font.SANS_SERIF, Font.BOLD, 11),
                Color.DARK_GRAY
            ),
            BorderFactory.createEmptyBorder(2, 4, 4, 4)
        ));
        
        section.add(content, BorderLayout.CENTER);
        return section;
    }

    // === ACTION HANDLERS ===

    private void setNeedlePosition(String position) {
        currentPosition = position;
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

    public void addActionListeners() {
        // Move step spinner listener
        moveStepSpinner.addChangeListener(e -> {
            moveStep = (Integer) moveStepSpinner.getValue();
            onMoveStepChanged();
        });

        // Disable aim checkbox listener
        disableAimCheckBox.addActionListener(e -> {
            aimEnabled = !disableAimCheckBox.isSelected();
            onAimStatusChanged();
        });
    }

    private void onMoveStepChanged() {
        String message = String.format("Move step changed to: %d units", moveStep);
        System.out.println("NeedleAim: " + message);
        // TODO: Apply move step changes to needle positioning system
    }

    private void onAimStatusChanged() {
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

    // === GETTERS FOR STATE ===

    public boolean isAimEnabled() {
        return aimEnabled;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public int getMoveStep() {
        return moveStep;
    }
}
