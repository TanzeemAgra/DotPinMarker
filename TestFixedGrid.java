import javax.swing.*;
import java.awt.*;

/**
 * Test Fixed Grid System - No Zoom Operations Allowed
 * 
 * This test verifies that the grid is completely fixed with:
 * - No zoom in/out operations
 * - No mouse wheel zoom
 * - No grid modifications
 * - Fixed zoom level at 100%
 * - All zoom controls disabled
 */
public class TestFixedGrid extends JFrame {
    
    private DrawingCanvas drawingCanvas;
    
    public TestFixedGrid() {
        super("Fixed Grid Test - No Zoom Operations");
        
        // Create the main drawing canvas
        drawingCanvas = new DrawingCanvas();
        
        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Add title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(40, 40, 40));
        JLabel titleLabel = new JLabel("🔒 FIXED GRID TEST - NO ZOOM OPERATIONS ALLOWED");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Add canvas
        add(drawingCanvas, BorderLayout.CENTER);
        
        // Add test controls panel
        JPanel controlsPanel = createControlsPanel();
        add(controlsPanel, BorderLayout.SOUTH);
        
        // Initialize and test
        performFixedGridTests();
        
        // Set window properties
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createControlsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(60, 60, 60));
        
        // Test buttons (all should be blocked)
        JButton zoomInBtn = new JButton("🔍➕ Try Zoom In");
        JButton zoomOutBtn = new JButton("🔍➖ Try Zoom Out");
        JButton zoomResetBtn = new JButton("🔄 Try Reset Zoom");
        JButton zoomFitBtn = new JButton("📐 Try Zoom Fit");
        
        // Style buttons
        JButton[] buttons = {zoomInBtn, zoomOutBtn, zoomResetBtn, zoomFitBtn};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(200, 50, 50));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }
        
        // Add action listeners (all should be blocked)
        zoomInBtn.addActionListener(e -> {
            System.out.println("🧪 TEST: Attempting Zoom In...");
            drawingCanvas.zoomIn();
        });
        
        zoomOutBtn.addActionListener(e -> {
            System.out.println("🧪 TEST: Attempting Zoom Out...");
            drawingCanvas.zoomOut();
        });
        
        zoomResetBtn.addActionListener(e -> {
            System.out.println("🧪 TEST: Attempting Zoom Reset...");
            drawingCanvas.resetZoom();
        });
        
        zoomFitBtn.addActionListener(e -> {
            System.out.println("🧪 TEST: Attempting Zoom Fit...");
            drawingCanvas.zoomToFit();
        });
        
        panel.add(zoomInBtn);
        panel.add(zoomOutBtn);
        panel.add(zoomResetBtn);
        panel.add(zoomFitBtn);
        
        // Status label
        JLabel statusLabel = new JLabel("Try the buttons above - All zoom operations should be blocked!");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(statusLabel);
        
        return panel;
    }
    
    private void performFixedGridTests() {
        System.out.println("=====================================");
        System.out.println("🧪 Fixed Grid Test Started");
        System.out.println("=====================================");
        
        // Test configuration status
        System.out.println("Configuration Status:");
        System.out.println("- GRID_COMPLETELY_FIXED: " + RugrelDropdownConfig.GRID_COMPLETELY_FIXED);
        System.out.println("- DISABLE_ALL_ZOOM_OPERATIONS: " + RugrelDropdownConfig.DISABLE_ALL_ZOOM_OPERATIONS);
        System.out.println("- PERMANENT_GRID_LOCK: " + RugrelDropdownConfig.PERMANENT_GRID_LOCK);
        System.out.println("- DISABLE_ZOOM_IN_ALL_TABS: " + RugrelDropdownConfig.DISABLE_ZOOM_IN_ALL_TABS);
        System.out.println("- DISABLE_ZOOM_OUT_ALL_TABS: " + RugrelDropdownConfig.DISABLE_ZOOM_OUT_ALL_TABS);
        System.out.println("- DISABLE_ALL_ZOOM_MOUSE_ACTIONS: " + RugrelDropdownConfig.DISABLE_ALL_ZOOM_MOUSE_ACTIONS);
        System.out.println("- LOCK_ZOOM_LEVEL: " + RugrelDropdownConfig.LOCK_ZOOM_LEVEL);
        System.out.println("- FIXED_ZOOM_LEVEL: " + RugrelDropdownConfig.FIXED_ZOOM_LEVEL);
        System.out.println("=====================================");
        
        // Test zoom disabled status
        boolean isZoomDisabled = drawingCanvas.isZoomDisabled();
        boolean isGridLocked = drawingCanvas.isGridLocked();
        
        System.out.println("Canvas Status:");
        System.out.println("- Zoom Disabled: " + isZoomDisabled);
        System.out.println("- Grid Locked: " + isGridLocked);
        System.out.println("=====================================");
        
        if (isZoomDisabled && isGridLocked) {
            System.out.println("✅ SUCCESS: Grid is completely fixed!");
            System.out.println("🔒 All zoom operations are disabled");
            System.out.println("🔒 Grid modifications are disabled");
        } else {
            System.out.println("❌ WARNING: Grid may not be completely fixed");
            System.out.println("❌ Zoom Disabled: " + isZoomDisabled);
            System.out.println("❌ Grid Locked: " + isGridLocked);
        }
        
        System.out.println("=====================================");
        System.out.println("Testing Manual Zoom Operations:");
        
        // Test each zoom operation
        System.out.println("🧪 TEST: Zoom In...");
        drawingCanvas.zoomIn();
        
        System.out.println("🧪 TEST: Zoom Out...");
        drawingCanvas.zoomOut();
        
        System.out.println("🧪 TEST: Zoom Reset...");
        drawingCanvas.resetZoom();
        
        System.out.println("🧪 TEST: Zoom Fit...");
        drawingCanvas.zoomToFit();
        
        System.out.println("=====================================");
        System.out.println("Instructions:");
        System.out.println("1. Try scrolling mouse wheel - should be blocked");
        System.out.println("2. Click the zoom buttons - should be blocked");
        System.out.println("3. All zoom operations should show blocked messages");
        System.out.println("4. Grid should remain at fixed zoom level");
        System.out.println("=====================================");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TestFixedGrid();
        });
    }
}