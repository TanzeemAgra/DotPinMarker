import javax.swing.*;
import java.awt.*;

/**
 * Test to verify that Coder Type functionality has been completely deleted using soft coding.
 * This test checks that the button group no longer contains any Coder Type buttons.
 */
public class TestCoderTypeDeletion extends JFrame {
    
    public TestCoderTypeDeletion() {
        setTitle("Verify Coder Type Deletion - Soft Coding Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JTextArea outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        // Test soft coding flags
        outputArea.append("=== SOFT CODING VERIFICATION TEST ===\n\n");
        outputArea.append("DELETE_CODER_TYPE_COMPLETELY: " + ThorX6HorizontalConfig.DELETE_CODER_TYPE_COMPLETELY + "\n");
        outputArea.append("REPLACE_QR_WITH_CODER_TYPE: " + ThorX6HorizontalConfig.REPLACE_QR_WITH_CODER_TYPE + "\n");
        outputArea.append("DISABLE_ORIGINAL_CODER_TYPE_BUTTON: " + ThorX6HorizontalConfig.DISABLE_ORIGINAL_CODER_TYPE_BUTTON + "\n");
        outputArea.append("ENABLE_CODER_TYPE_OPTIONS: " + ThorX6HorizontalConfig.ENABLE_CODER_TYPE_OPTIONS + "\n\n");
        
        // Get the coder group and check its buttons
        ThorX6HorizontalConfig.ThorX6ButtonGroup coderGroup = ThorX6HorizontalConfig.CODER_GROUP;
        ThorX6HorizontalConfig.ThorX6Button[] buttons = coderGroup.buttons;
        
        outputArea.append("=== CODER GROUP ANALYSIS ===\n");
        outputArea.append("Total buttons in Coder Group: " + buttons.length + "\n\n");
        
        if (buttons.length == 0) {
            outputArea.append("✅ SUCCESS: No coder buttons found - Complete deletion achieved!\n\n");
        } else {
            outputArea.append("Button details:\n");
            for (int i = 0; i < buttons.length; i++) {
                outputArea.append("  " + (i+1) + ". " + buttons[i].text + " - " + buttons[i].tooltip + "\n");
            }
            
            // Check if any button contains "Coder Type" or "QR Code"
            boolean hasCoderType = false;
            boolean hasQRCode = false;
            for (ThorX6HorizontalConfig.ThorX6Button button : buttons) {
                if (button.text.contains("Coder Type")) {
                    hasCoderType = true;
                }
                if (button.text.contains("QR Code")) {
                    hasQRCode = true;
                }
            }
            
            outputArea.append("\n=== DELETION VERIFICATION ===\n");
            if (!hasCoderType && !hasQRCode) {
                outputArea.append("✅ SUCCESS: No Coder Type or QR Code buttons found!\n");
                outputArea.append("✅ Soft coding deletion is working correctly.\n");
            } else {
                if (hasCoderType) {
                    outputArea.append("❌ FAILURE: Coder Type button still exists!\n");
                }
                if (hasQRCode) {
                    outputArea.append("❌ FAILURE: QR Code button still exists!\n");
                }
            }
        }
        
        outputArea.append("\n=== TEST CONCLUSION ===\n");
        if (ThorX6HorizontalConfig.DELETE_CODER_TYPE_COMPLETELY) {
            outputArea.append("Master deletion flag is ENABLED - Coder Type should be completely removed.\n");
        } else {
            outputArea.append("Master deletion flag is DISABLED - Coder Type might still be present.\n");
        }
        
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton testHandlerButton = new JButton("Test Coder Type Handler");
        testHandlerButton.addActionListener(e -> {
            outputArea.append("\n=== HANDLER TEST ===\n");
            outputArea.append("Attempting to call handleCoderTypeSelection()...\n");
            try {
                ThorX6HorizontalConfig.handleCoderTypeSelection();
                outputArea.append("Handler executed - Check console for deletion message.\n");
            } catch (Exception ex) {
                outputArea.append("Handler error: " + ex.getMessage() + "\n");
            }
        });
        
        buttonPanel.add(testHandlerButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TestCoderTypeDeletion().setVisible(true));
    }
}