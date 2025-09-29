import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ThorX6 Coder Type GUI - Main User Interface
 * 
 * This class provides the main GUI interface for the Coder Type system.
 * It includes:
 * - Dropdown for coder type selection
 * - Configuration panels for each coder type
 * - Real-time preview box with auto-update
 * - Generate button for manual code generation
 * - Integration hooks for the main application
 * 
 * The GUI is designed to be modular and non-intrusive to existing code.
 */
public class ThorX6CoderTypeGUI extends JPanel {
    
    // Core components
    private ThorX6CoderTypeSystem.CoderTypeManager coderManager;
    private JComboBox<String> coderTypeDropdown;
    private JPanel configurationPanel;
    private JPanel currentConfigPanel;
    private JTextArea previewArea;
    private JButton generateButton;
    private JButton resetButton;
    private JLabel statusLabel;
    
    // Real-time preview update
    private Timer previewUpdateTimer;
    private boolean autoPreviewEnabled = true;
    
    // GUI styling
    private static final Color PREVIEW_BACKGROUND = new Color(240, 248, 255);
    private static final Color CONFIG_BACKGROUND = new Color(248, 248, 248);
    private static final Font PREVIEW_FONT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    private static final Font STATUS_FONT = new Font(Font.SANS_SERIF, Font.ITALIC, 11);
    
    /**
     * Constructor - Initialize the complete GUI
     */
    public ThorX6CoderTypeGUI() {
        initializeComponents();
        setupLayout();
        connectEventHandlers();
        startPreviewUpdater();
        
        // Initialize with default coder type
        coderManager.setCoderType("No Code");
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Core manager
        coderManager = new ThorX6CoderTypeSystem.CoderTypeManager();
        
        // Dropdown for coder type selection
        coderTypeDropdown = new JComboBox<>(ThorX6CoderTypeSystem.CoderTypeRegistry.getAvailableTypes());
        coderTypeDropdown.setPreferredSize(new Dimension(200, 25));
        coderTypeDropdown.setToolTipText("Select the type of code to generate");
        
        // Configuration panel container
        configurationPanel = new JPanel(new BorderLayout());
        configurationPanel.setBackground(CONFIG_BACKGROUND);
        configurationPanel.setPreferredSize(new Dimension(400, 200));
        configurationPanel.setBorder(createTitledBorder("Configuration"));
        
        // Preview area
        previewArea = new JTextArea(3, 30);
        previewArea.setEditable(false);
        previewArea.setFont(PREVIEW_FONT);
        previewArea.setBackground(PREVIEW_BACKGROUND);
        previewArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        previewArea.setToolTipText("Real-time preview of generated codes");
        
        // Buttons
        generateButton = new JButton("Generate Code");
        generateButton.setPreferredSize(new Dimension(120, 30));
        generateButton.setToolTipText("Generate and return the code");
        
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(80, 30));
        resetButton.setToolTipText("Reset the current coder to initial state");
        
        // Status label
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(Color.GRAY);
    }
    
    /**
     * Setup the overall layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel - Coder type selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Coder Type:"));
        topPanel.add(coderTypeDropdown);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(generateButton);
        topPanel.add(resetButton);
        
        // Center panel - Configuration
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(configurationPanel, BorderLayout.CENTER);
        
        // Preview panel
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(createTitledBorder("Real-time Preview"));
        
        JScrollPane previewScroll = new JScrollPane(previewArea);
        previewScroll.setPreferredSize(new Dimension(400, 80));
        previewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        previewScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        previewPanel.add(previewScroll, BorderLayout.CENTER);
        
        // Auto-update control
        JPanel previewControlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JCheckBox autoUpdateCheckBox = new JCheckBox("Auto-update preview", autoPreviewEnabled);
        autoUpdateCheckBox.addActionListener(e -> {
            autoPreviewEnabled = autoUpdateCheckBox.isSelected();
            if (autoPreviewEnabled) {
                startPreviewUpdater();
            } else {
                stopPreviewUpdater();
            }
        });
        previewControlPanel.add(autoUpdateCheckBox);
        previewPanel.add(previewControlPanel, BorderLayout.SOUTH);
        
        // Bottom panel - Status
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(statusLabel);
        
        // Main layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(previewPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Connect all event handlers
     */
    private void connectEventHandlers() {
        // Coder type dropdown
        coderTypeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) coderTypeDropdown.getSelectedItem();
                if (selectedType != null) {
                    coderManager.setCoderType(selectedType);
                    updateStatus("Switched to " + selectedType);
                }
            }
        });
        
        // Generate button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String generatedCode = coderManager.generateCode();
                updateStatus("Generated: " + generatedCode);
                
                // Flash the preview to show generation
                flashPreview();
                
                // Trigger any external listeners (for integration)
                fireCodeGenerated(generatedCode);
            }
        });
        
        // Reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThorX6CoderTypeSystem.CoderType currentCoder = coderManager.getCurrentCoderType();
                if (currentCoder != null) {
                    currentCoder.reset();
                    coderManager.updatePreview();
                    updateStatus("Reset " + coderManager.getCurrentTypeName());
                }
            }
        });
        
        // Coder manager listeners
        coderManager.addChangeListener(new ThorX6CoderTypeSystem.CoderTypeManager.CoderTypeChangeListener() {
            @Override
            public void onCoderTypeChanged(ThorX6CoderTypeSystem.CoderType newType, String typeName) {
                updateConfigurationPanel(newType);
                resetButton.setEnabled(newType != null);
            }
            
            @Override
            public void onPreviewUpdated(String preview) {
                updatePreviewArea(preview);
            }
        });
    }
    
    /**
     * Update the configuration panel for the selected coder type
     */
    private void updateConfigurationPanel(ThorX6CoderTypeSystem.CoderType coderType) {
        // Remove current configuration panel
        if (currentConfigPanel != null) {
            configurationPanel.remove(currentConfigPanel);
        }
        
        if (coderType != null && coderType.requiresConfiguration()) {
            currentConfigPanel = coderType.getConfigurationPanel();
            if (currentConfigPanel != null) {
                configurationPanel.add(currentConfigPanel, BorderLayout.CENTER);
                
                // Add configuration change listener to update preview
                addConfigurationChangeListener(currentConfigPanel);
            }
        } else {
            // Show "No configuration required" message
            JLabel noConfigLabel = new JLabel("No configuration required for this coder type.");
            noConfigLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noConfigLabel.setForeground(Color.GRAY);
            currentConfigPanel = new JPanel(new BorderLayout());
            currentConfigPanel.add(noConfigLabel, BorderLayout.CENTER);
            configurationPanel.add(currentConfigPanel, BorderLayout.CENTER);
        }
        
        configurationPanel.revalidate();
        configurationPanel.repaint();
    }
    
    /**
     * Add listeners to configuration components to update preview
     */
    private void addConfigurationChangeListener(JPanel panel) {
        addChangeListenersRecursively(panel);
    }
    
    /**
     * Recursively add change listeners to all components
     */
    private void addChangeListenersRecursively(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    @Override
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { delayedPreviewUpdate(); }
                    @Override
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { delayedPreviewUpdate(); }
                    @Override
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { delayedPreviewUpdate(); }
                });
            } else if (component instanceof JSpinner) {
                JSpinner spinner = (JSpinner) component;
                spinner.addChangeListener(e -> delayedPreviewUpdate());
            } else if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                checkBox.addActionListener(e -> delayedPreviewUpdate());
            } else if (component instanceof JComboBox) {
                JComboBox<?> comboBox = (JComboBox<?>) component;
                comboBox.addActionListener(e -> delayedPreviewUpdate());
            } else if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                radioButton.addActionListener(e -> delayedPreviewUpdate());
            } else if (component instanceof Container) {
                addChangeListenersRecursively((Container) component);
            }
        }
    }
    
    /**
     * Delayed preview update to avoid too frequent updates
     */
    private Timer delayedUpdateTimer;
    private void delayedPreviewUpdate() {
        if (delayedUpdateTimer != null) {
            delayedUpdateTimer.cancel();
        }
        delayedUpdateTimer = new Timer();
        delayedUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> coderManager.updatePreview());
            }
        }, 300); // 300ms delay
    }
    
    /**
     * Update the preview area
     */
    private void updatePreviewArea(String preview) {
        SwingUtilities.invokeLater(() -> {
            previewArea.setText("Preview: " + preview + "\n\nNext codes:\n");
            
            // Show next few codes for demonstration
            ThorX6CoderTypeSystem.CoderType currentCoder = coderManager.getCurrentCoderType();
            if (currentCoder != null) {
                for (int i = 0; i < 3; i++) {
                    previewArea.append((i + 1) + ". " + currentCoder.getPreview() + "\n");
                }
            }
        });
    }
    
    /**
     * Flash the preview area to indicate generation
     */
    private void flashPreview() {
        Color originalBg = previewArea.getBackground();
        previewArea.setBackground(Color.YELLOW);
        
        Timer flashTimer = new Timer();
        flashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> previewArea.setBackground(originalBg));
            }
        }, 200);
    }
    
    /**
     * Start the preview updater for real-time updates
     */
    private void startPreviewUpdater() {
        if (previewUpdateTimer != null) {
            previewUpdateTimer.cancel();
        }
        
        if (autoPreviewEnabled) {
            previewUpdateTimer = new Timer();
            previewUpdateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    coderManager.updatePreview();
                }
            }, 1000, 2000); // Update every 2 seconds
        }
    }
    
    /**
     * Stop the preview updater
     */
    private void stopPreviewUpdater() {
        if (previewUpdateTimer != null) {
            previewUpdateTimer.cancel();
            previewUpdateTimer = null;
        }
    }
    
    /**
     * Update status message
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
        
        // Clear status after delay
        Timer statusTimer = new Timer();
        statusTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> statusLabel.setText("Ready"));
            }
        }, 3000);
    }
    
    /**
     * Create titled border with consistent styling
     */
    private Border createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), title
        );
        border.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        return border;
    }
    
    // ==================== INTEGRATION HOOKS ====================
    
    /**
     * Interface for external integration
     */
    public interface CodeGenerationListener {
        void onCodeGenerated(String code, String coderType);
    }
    
    private java.util.List<CodeGenerationListener> codeGenerationListeners = new java.util.ArrayList<>();
    
    /**
     * Add listener for code generation events
     */
    public void addCodeGenerationListener(CodeGenerationListener listener) {
        codeGenerationListeners.add(listener);
    }
    
    /**
     * Remove code generation listener
     */
    public void removeCodeGenerationListener(CodeGenerationListener listener) {
        codeGenerationListeners.remove(listener);
    }
    
    /**
     * Fire code generated event
     */
    private void fireCodeGenerated(String code) {
        String coderType = coderManager.getCurrentTypeName();
        for (CodeGenerationListener listener : codeGenerationListeners) {
            try {
                listener.onCodeGenerated(code, coderType);
            } catch (Exception e) {
                System.err.println("Error in code generation listener: " + e.getMessage());
            }
        }
    }
    
    // ==================== PUBLIC API ====================
    
    /**
     * Get the current coder manager (for external access)
     */
    public ThorX6CoderTypeSystem.CoderTypeManager getCoderManager() {
        return coderManager;
    }
    
    /**
     * Generate code programmatically
     */
    public String generateCode() {
        return coderManager.generateCode();
    }
    
    /**
     * Set coder type programmatically
     */
    public void setCoderType(String typeName) {
        coderTypeDropdown.setSelectedItem(typeName);
        coderManager.setCoderType(typeName);
    }
    
    /**
     * Get current preview
     */
    public String getCurrentPreview() {
        return coderManager.getCurrentPreview();
    }
    
    /**
     * Enable/disable auto preview
     */
    public void setAutoPreviewEnabled(boolean enabled) {
        autoPreviewEnabled = enabled;
        if (enabled) {
            startPreviewUpdater();
        } else {
            stopPreviewUpdater();
        }
    }
    
    /**
     * Cleanup resources when component is disposed
     */
    public void dispose() {
        stopPreviewUpdater();
        if (delayedUpdateTimer != null) {
            delayedUpdateTimer.cancel();
        }
    }
    
    // ==================== STANDALONE TESTING ====================
    
    /**
     * Create a standalone test window
     */
    public static JFrame createTestWindow() {
        JFrame frame = new JFrame("ThorX6 Coder Type System - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ThorX6CoderTypeGUI coderGUI = new ThorX6CoderTypeGUI();
        
        // Add test listener
        coderGUI.addCodeGenerationListener(new CodeGenerationListener() {
            @Override
            public void onCodeGenerated(String code, String coderType) {
                System.out.println("Generated Code: " + code + " (Type: " + coderType + ")");
            }
        });
        
        frame.add(coderGUI);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        return frame;
    }
    
    /**
     * Main method for standalone testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = createTestWindow();
            testFrame.setVisible(true);
        });
    }
}