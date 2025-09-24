import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager;

/**
 * Direct Mark Tab Test
 * Opens the ThorX6 Mark tab in a dedicated window for clear viewing
 */
public class DirectMarkTabTest {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 Starting Direct Mark Tab Test...");
            
            // Create a simple drawing canvas for testing
            DrawingCanvas testCanvas = new DrawingCanvas();
            
            // Create the test window
            JFrame testFrame = new JFrame("ThorX6 Mark Tab - Direct Test View");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(1400, 900);
            testFrame.setLocationRelativeTo(null);
            
            // Apply professional UI theme
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                // Fallback to default
            }
            
            // Create the ThorX6 Mark tab directly
            ThorX6HorizontalMarkTab markTab = new ThorX6HorizontalMarkTab(testCanvas);
            
            // Add some padding around the tab for better visibility
            JPanel containerPanel = new JPanel(new BorderLayout(10, 10));
            containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            containerPanel.setBackground(Color.WHITE);
            containerPanel.add(markTab, BorderLayout.CENTER);
            
            // Add a title panel at the top
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            titlePanel.setBackground(new Color(0, 120, 215)); // ThorX6 blue
            JLabel titleLabel = new JLabel("ThorX6 Mark Tab - All Options Should Be Visible");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titlePanel.add(titleLabel);
            containerPanel.add(titlePanel, BorderLayout.NORTH);
            
            testFrame.add(containerPanel);
            testFrame.setVisible(true);
            
            System.out.println("✅ Direct Mark Tab Test window opened!");
            System.out.println("📋 You should now see:");
            System.out.println("   • ThorX6 Horizontal Toolbar with 4 button groups");
            System.out.println("   • Clipboard: Paste, Cut, Copy");
            System.out.println("   • Font: Font Selection, Size, Bold, Italic"); 
            System.out.println("   • Tools: Text, Line, Rectangle, Circle");
            System.out.println("   • Actions: Undo, Redo, Delete");
            System.out.println("   • White canvas area in the center");
            System.out.println("   • Properties panel at the bottom");
            System.out.println("🎯 This is exactly what should appear in the Mark tab!");
        });
    }
}