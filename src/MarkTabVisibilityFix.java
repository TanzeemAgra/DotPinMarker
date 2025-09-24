import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Mark Tab Visibility Fix
 * Ensures the ThorX6 Mark tab content is properly visible
 */
public class MarkTabVisibilityFix {
    
    /**
     * Force refresh and ensure Mark tab content visibility
     */
    public static void ensureMarkTabVisibility() {
        SwingUtilities.invokeLater(() -> {
            // Force a refresh of the tab content system
            TabManagementConfig.switchToTab("Mark");
            
            // Add a small delay and refresh again to ensure visibility
            Timer refreshTimer = new Timer(100, e -> {
                TabManagementConfig.switchToTab("Mark");
                System.out.println("🔧 Mark tab visibility refresh completed");
            });
            refreshTimer.setRepeats(false);
            refreshTimer.start();
        });
    }
    
    /**
     * Test Mark tab visibility by creating a simple window
     */
    public static void testMarkTabDirectly(DrawingCanvas drawingCanvas) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("ThorX6 Mark Tab Test - Direct View");
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            testFrame.setSize(1200, 800);
            testFrame.setLocationRelativeTo(null);
            
            // Create the ThorX6 Mark tab directly
            ThorX6HorizontalMarkTab markTab = new ThorX6HorizontalMarkTab(drawingCanvas);
            testFrame.add(markTab, BorderLayout.CENTER);
            
            // Add close handler
            testFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("🔧 Mark tab test window closed");
                }
            });
            
            testFrame.setVisible(true);
            System.out.println("🔧 ThorX6 Mark tab test window opened - you should see all ThorX6 options!");
        });
    }
    
    /**
     * Debug the content area state
     */
    public static void debugContentAreaState() {
        System.out.println("\n🔧 === CONTENT AREA DEBUG ===");
        TabManagementConfig.printTabDebugInfo();
        
        // Force Mark tab selection
        System.out.println("🔧 Forcing Mark tab selection...");
        TabManagementConfig.switchToTab("Mark");
        
        System.out.println("🔧 === DEBUG COMPLETE ===\n");
    }
}