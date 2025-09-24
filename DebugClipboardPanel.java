import javax.swing.*;
import java.awt.*;

/**
 * Debug Enhanced Clipboard Panel
 */
public class DebugClipboardPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("🔍 Debugging Enhanced Clipboard Panel...\n");
            
            // Print configuration
            MarkTabConfig.printConfigSummary();
            
            // Create test frame
            JFrame frame = new JFrame("Debug Clipboard Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            
            // Test 1: Direct clipboard panel
            System.out.println("\n🧪 Test 1: Creating enhanced clipboard panel directly...");
            JPanel clipboardPanel = MarkTabConfig.createEnhancedClipboardPanel();
            System.out.println("📏 Panel size: " + clipboardPanel.getPreferredSize());
            System.out.println("🎨 Panel background: " + clipboardPanel.getBackground());
            System.out.println("🧱 Panel components: " + clipboardPanel.getComponentCount());
            
            // Test 2: Simulate toolbar environment
            System.out.println("\n🧪 Test 2: Simulating toolbar environment...");
            JPanel toolbar = new JPanel();
            toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
            toolbar.setBackground(MarkTabConfig.TOOLBAR_BACKGROUND);
            toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, MarkTabConfig.BORDER_COLOR),
                BorderFactory.createEmptyBorder(8, 4, 8, 4)
            ));
            toolbar.setPreferredSize(new Dimension(MarkTabConfig.TOOLBAR_WIDTH, 0));
            
            // Add title
            JLabel title = new JLabel("Clipboard", SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI", Font.BOLD, 9));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            toolbar.add(title);
            toolbar.add(Box.createVerticalStrut(4));
            
            // Add clipboard panel
            clipboardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            toolbar.add(clipboardPanel);
            toolbar.add(Box.createVerticalGlue());
            
            // Test 3: Individual buttons
            System.out.println("\n🧪 Test 3: Testing individual buttons...");
            JPanel buttonTest = new JPanel(new FlowLayout());
            for (MarkTabConfig.ToolbarButton buttonConfig : MarkTabConfig.ENHANCED_CLIPBOARD_BUTTONS) {
                System.out.println("🔹 Button: " + buttonConfig.text + " (Column " + buttonConfig.column + ", Type: " + buttonConfig.type + ")");
                JButton button = MarkTabConfig.createToolbarButton(buttonConfig);
                buttonTest.add(button);
            }
            
            // Layout
            mainPanel.add(toolbar, BorderLayout.WEST);
            mainPanel.add(buttonTest, BorderLayout.CENTER);
            
            // Info panel
            JTextArea info = new JTextArea(
                "Debug Information:\\n\\n" +
                "Enhanced Clipboard: " + MarkTabConfig.ENABLE_TWO_COLUMN_CLIPBOARD + "\\n" +
                "Column 1 Width: " + MarkTabConfig.COLUMN_1_WIDTH + "\\n" +
                "Column 2 Width: " + MarkTabConfig.COLUMN_2_WIDTH + "\\n" +
                "Toolbar Width: " + MarkTabConfig.TOOLBAR_WIDTH + "\\n" +
                "Button Height: " + MarkTabConfig.CLIPBOARD_BUTTON_HEIGHT + "\\n\\n" +
                "Check console for detailed debugging information."
            );
            info.setEditable(false);
            info.setFont(new Font("Courier New", Font.PLAIN, 11));
            mainPanel.add(new JScrollPane(info), BorderLayout.SOUTH);
            
            frame.add(mainPanel);
            frame.setVisible(true);
            
            System.out.println("\\n✅ Debug window opened! Check the visual layout.");
        });
    }
}