import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Professional ThorX6-Style Mark Tab Configuration
 * Soft-coded system for creating the exact Mark tab interface shown in Tab_Design.PNG
 */
public class MarkTabConfig {
    
    // ==================== THORX6 MARK TAB STYLING ====================
    
    // Layout Configuration
    public static final int TOOLBAR_WIDTH = 85;
    public static final int CODER_PANEL_WIDTH = 120;
    public static final int PROPERTIES_HEIGHT = 45;
    public static final int BUTTON_SIZE = 32;
    public static final int SPACING = 4;
    
    // Colors (matching ThorX6 Professional theme)
    public static final Color TOOLBAR_BACKGROUND = new Color(240, 240, 240);
    public static final Color CONTENT_BACKGROUND = Color.WHITE;
    public static final Color BUTTON_BACKGROUND = new Color(245, 245, 245);
    public static final Color BUTTON_HOVER = new Color(225, 240, 255);
    public static final Color BUTTON_PRESSED = new Color(200, 230, 255);
    public static final Color BORDER_COLOR = new Color(200, 200, 200);
    public static final Color TEXT_COLOR = new Color(60, 60, 60);
    
    // Typography
    public static final Font TOOLBAR_FONT = new Font("Segoe UI", Font.PLAIN, 9);
    public static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font PROPERTIES_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    // ==================== TOOLBAR BUTTON CONFIGURATION (SOFT CODED) ====================
    
    public static class ToolbarButton {
        public final String text;
        public final String icon;
        public final String tooltip;
        public final Runnable action;
        
        public ToolbarButton(String text, String icon, String tooltip, Runnable action) {
            this.text = text;
            this.icon = icon;
            this.tooltip = tooltip;
            this.action = action;
        }
    }
    
    // Enhanced clipboard buttons with Undo and Erase added (Soft Coded)
    public static final ToolbarButton[] TOOLBAR_BUTTONS = {
        new ToolbarButton("Undo", "↶", "Undo last action (Ctrl+Z)", () -> handleUndoAction()),
        new ToolbarButton("Paste", "📋", "Paste from clipboard (Ctrl+V)", () -> handlePasteAction()),
        new ToolbarButton("Cut", "✂", "Cut selection (Ctrl+X)", () -> handleCutAction()),
        new ToolbarButton("Copy", "📄", "Copy selection (Ctrl+C)", () -> handleCopyAction()),
        new ToolbarButton("Erase", "🗑", "Erase selected elements (Delete)", () -> handleEraseAction())
    };
    
    // ==================== CONTENT PANEL CONFIGURATION ====================
    
    public static class ContentConfig {
        public static final String DEFAULT_TEXT = "ABC123";
        public static final Color TEXT_BACKGROUND = Color.WHITE;
        public static final Color TEXT_BORDER = new Color(180, 180, 180);
        public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 24);
        public static final int TEXT_AREA_HEIGHT = 150;
    }
    
    // ==================== CODER PANEL CONFIGURATION ====================
    
    public static class CoderConfig {
        public static final String[] CODER_OPTIONS = {"No Coder", "QR Code", "Barcode", "DataMatrix"};
        public static final String DEFAULT_SELECTION = "No Coder";
        public static final Color DROPDOWN_BACKGROUND = Color.WHITE;
        public static final Font DROPDOWN_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    }
    
    // ==================== PROPERTIES BAR CONFIGURATION ====================
    
    public static class PropertyField {
        public final String label;
        public final String defaultValue;
        public final int width;
        public final boolean editable;
        
        public PropertyField(String label, String defaultValue, int width, boolean editable) {
            this.label = label;
            this.defaultValue = defaultValue;
            this.width = width;
            this.editable = editable;
        }
    }
    
    public static final PropertyField[] PROPERTY_FIELDS = {
        new PropertyField("Name", "newmark1", 80, true),
        new PropertyField("X", "47.75", 60, true),
        new PropertyField("Y", "47.25", 60, true),
        new PropertyField("Z", "0", 40, true),
        new PropertyField("Angle", "0", 50, true),
        new PropertyField("Width", "38.9594", 70, false),
        new PropertyField("Height", "7.0097", 70, false)
    };
    
    // ==================== ACTION BUTTONS CONFIGURATION ====================
    
    public static class ActionButton {
        public final String text;
        public final boolean isCheckbox;
        public final boolean defaultState;
        public final Runnable action;
        
        public ActionButton(String text, boolean isCheckbox, boolean defaultState, Runnable action) {
            this.text = text;
            this.isCheckbox = isCheckbox;
            this.defaultState = defaultState;
            this.action = action;
        }
    }
    
    public static final ActionButton[] ACTION_BUTTONS = {
        new ActionButton("Clear Trans", true, false, () -> System.out.println("🔄 Clear Transform")),
        new ActionButton("Mirror", true, false, () -> System.out.println("🪞 Mirror action")),
        new ActionButton("Lock Size", true, false, () -> System.out.println("🔒 Lock Size")),
        new ActionButton("Disable Print", true, false, () -> System.out.println("🚫 Disable Print"))
    };
    
    // ==================== CLIPBOARD ACTION HANDLERS (SOFT CODED) ====================
    
    /**
     * Undo Action Handler
     */
    public static void handleUndoAction() {
        System.out.println("🔄 Undo Action - Reverting last operation...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.undo();
                System.out.println("✅ Undo completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for undo operation");
            }
        } catch (Exception e) {
            System.err.println("❌ Undo failed: " + e.getMessage());
        }
    }
    
    /**
     * Paste Action Handler
     */
    public static void handlePasteAction() {
        System.out.println("📋 Paste Action - Inserting clipboard content...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.paste();
                System.out.println("✅ Paste completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for paste operation");
            }
        } catch (Exception e) {
            System.err.println("❌ Paste failed: " + e.getMessage());
        }
    }
    
    /**
     * Cut Action Handler
     */
    public static void handleCutAction() {
        System.out.println("✂ Cut Action - Moving selection to clipboard...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.cutSelected();
                System.out.println("✅ Cut completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for cut operation");
            }
        } catch (Exception e) {
            System.err.println("❌ Cut failed: " + e.getMessage());
        }
    }
    
    /**
     * Copy Action Handler
     */
    public static void handleCopyAction() {
        System.out.println("📄 Copy Action - Copying selection to clipboard...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.copySelected();
                System.out.println("✅ Copy completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for copy operation");
            }
        } catch (Exception e) {
            System.err.println("❌ Copy failed: " + e.getMessage());
        }
    }
    
    /**
     * Erase Action Handler  
     */
    public static void handleEraseAction() {
        System.out.println("🗑 Erase Action - Deleting selected elements...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.eraseSelected();
                System.out.println("✅ Erase completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for erase operation");
            }
        } catch (Exception e) {
            System.err.println("❌ Erase failed: " + e.getMessage());
        }
    }
    
    // ==================== COMPONENT CREATION METHODS ====================
    
    /**
     * Create professional toolbar button matching ThorX6 style
     */
    public static JButton createToolbarButton(ToolbarButton config) {
        JButton button = new JButton();
        
        // Create button layout with icon and text
        button.setLayout(new BorderLayout());
        
        // Icon label
        JLabel iconLabel = new JLabel(config.icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        button.add(iconLabel, BorderLayout.CENTER);
        
        // Text label
        JLabel textLabel = new JLabel(config.text, SwingConstants.CENTER);
        textLabel.setFont(TOOLBAR_FONT);
        textLabel.setForeground(TEXT_COLOR);
        button.add(textLabel, BorderLayout.SOUTH);
        
        // Professional styling
        button.setPreferredSize(new Dimension(BUTTON_SIZE + 20, BUTTON_SIZE + 15));
        button.setBackground(BUTTON_BACKGROUND);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Tooltip
        button.setToolTipText(config.tooltip);
        
        // Professional hover effects
        addButtonHoverEffects(button);
        
        // Action
        button.addActionListener(e -> config.action.run());
        
        return button;
    }
    
    /**
     * Create property field with label and input
     */
    public static JPanel createPropertyField(PropertyField config) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        panel.setBackground(TOOLBAR_BACKGROUND);
        
        // Label
        JLabel label = new JLabel(config.label + ":");
        label.setFont(PROPERTIES_FONT);
        label.setForeground(TEXT_COLOR);
        panel.add(label);
        
        // Input field
        JTextField field = new JTextField(config.defaultValue);
        field.setFont(PROPERTIES_FONT);
        field.setPreferredSize(new Dimension(config.width, 20));
        field.setEditable(config.editable);
        field.setBackground(config.editable ? Color.WHITE : new Color(250, 250, 250));
        field.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        panel.add(field);
        
        return panel;
    }
    
    /**
     * Create action button (checkbox or regular button)
     */
    public static JComponent createActionButton(ActionButton config) {
        if (config.isCheckbox) {
            JCheckBox checkbox = new JCheckBox(config.text, config.defaultState);
            checkbox.setFont(PROPERTIES_FONT);
            checkbox.setBackground(TOOLBAR_BACKGROUND);
            checkbox.setForeground(TEXT_COLOR);
            checkbox.addActionListener(e -> config.action.run());
            return checkbox;
        } else {
            JButton button = new JButton(config.text);
            button.setFont(PROPERTIES_FONT);
            button.setBackground(BUTTON_BACKGROUND);
            button.setForeground(TEXT_COLOR);
            button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            button.addActionListener(e -> config.action.run());
            addButtonHoverEffects(button);
            return button;
        }
    }
    
    // ==================== CLIPBOARD OPERATION IMPLEMENTATIONS ====================
    
    /**
     * Undo last operation implementation
     */
    private static void undoLastOperation() {
        // Implementation for undo functionality
        System.out.println("🔄 Performing undo operation...");
    }
    
    /**
     * Paste from clipboard implementation
     */
    private static void pasteFromClipboard() {
        // Implementation for paste functionality
        System.out.println("📋 Pasting content from clipboard...");
    }
    
    /**
     * Cut selection to clipboard implementation
     */
    private static void cutSelectionToClipboard() {
        // Implementation for cut functionality
        System.out.println("✂ Cutting selection to clipboard...");
    }
    
    /**
     * Copy selection to clipboard implementation
     */
    private static void copySelectionToClipboard() {
        // Implementation for copy functionality
        System.out.println("📄 Copying selection to clipboard...");
    }
    
    /**
     * Erase selected elements implementation
     */
    private static void eraseSelectedElements() {
        // Implementation for erase functionality
        System.out.println("🗑 Erasing selected elements...");
    }
    
    /**
     * Add professional hover effects to buttons
     */
    private static void addButtonHoverEffects(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_HOVER);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_BACKGROUND);
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_PRESSED);
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_HOVER);
            }
        });
    }
    
    /**
     * Get the current DrawingCanvas instance
     */
    private static DrawingCanvas getCurrentDrawingCanvas() {
        // Search through all open frames for DrawingCanvas
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof JFrame) {
                JFrame jFrame = (JFrame) frame;
                DrawingCanvas canvas = findDrawingCanvas(jFrame.getContentPane());
                if (canvas != null) {
                    return canvas;
                }
            }
        }
        return null;
    }
    
    /**
     * Recursively find DrawingCanvas in component hierarchy
     */
    private static DrawingCanvas findDrawingCanvas(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof DrawingCanvas) {
                return (DrawingCanvas) component;
            } else if (component instanceof Container) {
                DrawingCanvas found = findDrawingCanvas((Container) component);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /**
     * Setup keyboard shortcuts for clipboard operations
     */
    public static void setupKeyboardShortcuts(JComponent component) {
        // Get input and action maps
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
        
        // Ctrl+C for Copy
        inputMap.put(KeyStroke.getKeyStroke("ctrl C"), "copy");
        actionMap.put("copy", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleCopyAction();
            }
        });
        
        // Ctrl+V for Paste
        inputMap.put(KeyStroke.getKeyStroke("ctrl V"), "paste");
        actionMap.put("paste", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handlePasteAction();
            }
        });
        
        // Ctrl+X for Cut
        inputMap.put(KeyStroke.getKeyStroke("ctrl X"), "cut");
        actionMap.put("cut", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleCutAction();
            }
        });
        
        // Ctrl+Z for Undo
        inputMap.put(KeyStroke.getKeyStroke("ctrl Z"), "undo");
        actionMap.put("undo", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleUndoAction();
            }
        });
        
        // Delete key for Erase
        inputMap.put(KeyStroke.getKeyStroke("DELETE"), "erase");
        actionMap.put("erase", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleEraseAction();
            }
        });
        
        System.out.println("⌨️ Keyboard shortcuts setup: Ctrl+C, Ctrl+V, Ctrl+X, Ctrl+Z, Delete");
    }

    /**
     * Print configuration summary
     */
    public static void printConfigSummary() {
        System.out.println("🎨 ThorX6 Mark Tab Configuration:");
        System.out.println("   Clipboard Buttons: " + TOOLBAR_BUTTONS.length + " (Added: Undo, Erase)");
        System.out.println("   Property Fields: " + PROPERTY_FIELDS.length);
        System.out.println("   Action Controls: " + ACTION_BUTTONS.length);
        System.out.println("   Layout: Professional ThorX6 style");
        System.out.println("   📋 Clipboard Options: Undo, Paste, Cut, Copy, Erase");
        System.out.println("   ⌨️ Keyboard Shortcuts: Ctrl+C/V/X/Z, Delete");
    }
}