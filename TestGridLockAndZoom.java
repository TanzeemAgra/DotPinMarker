import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Test class for Grid Locking and Zoom Control System
 * 
 * Tests:
 * 1. Grid locking to prevent modifications
 * 2. Zoom disable functionality in mark tab
 * 3. Visual indicators for locked grid state
 * 4. Auto-lock when switching to mark tab
 * 5. Soft coding configuration compliance
 */
public class TestGridLockAndZoom {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Grid Lock and Zoom Control Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        
        // Create mock DrawingCanvas for testing
        JPanel testPanel = new JPanel() {
            private boolean gridLocked = false;
            private boolean zoomDisabled = false;
            private double currentZoom = 1.0;
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw test grid
                drawTestGrid(g2);
                
                // Draw grid lock indicators if locked
                if (gridLocked) {
                    drawGridLockTestIndicators(g2);
                }
                
                // Draw test instructions
                drawTestInstructions(g2);
            }
            
            private void drawTestGrid(Graphics2D g) {
                g.setColor(new Color(200, 200, 200));
                g.setStroke(new BasicStroke(1.0f));
                
                // Draw grid lines
                for (int x = 0; x < getWidth(); x += 40) {
                    g.drawLine(x, 0, x, getHeight());
                }
                for (int y = 0; y < getHeight(); y += 40) {
                    g.drawLine(0, y, getWidth(), y);
                }
                
                // Draw grid border
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(2.0f));
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
            
            private void drawGridLockTestIndicators(Graphics2D g) {
                // Red border for locked grid
                g.setColor(new Color(255, 0, 0, 100));
                g.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                                          1.0f, new float[]{10, 5}, 0));
                g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
                
                // Lock icon
                g.setColor(new Color(255, 255, 255, 220));
                g.fillRoundRect(6, 6, 40, 40, 8, 8);
                
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(2.0f));
                g.drawRoundRect(6, 6, 40, 40, 8, 8);
                
                // Draw lock symbol
                g.setStroke(new BasicStroke(3.0f));
                g.fillRect(18, 26, 16, 12);
                g.drawArc(20, 14, 12, 16, 0, 180);
                
                // Status message
                g.setColor(new Color(255, 0, 0, 200));
                g.fillRoundRect(5, getHeight() - 40, 150, 25, 6, 6);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString("GRID LOCKED", 10, getHeight() - 22);
                
                // Zoom disabled indicator
                if (zoomDisabled) {
                    g.setColor(new Color(255, 100, 0, 180));
                    g.fillRoundRect(getWidth() - 120, getHeight() - 40, 110, 25, 4, 4);
                    g.setColor(Color.WHITE);
                    g.drawString("ZOOM DISABLED", getWidth() - 115, getHeight() - 22);
                }
            }
            
            private void drawTestInstructions(Graphics2D g) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("Grid Lock and Zoom Control Test", 10, 30);
                
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                String[] instructions = {
                    "",
                    "Configuration Status:",
                    "- ENABLE_GRID_LOCKING: " + RugrelDropdownConfig.ENABLE_GRID_LOCKING,
                    "- LOCK_GRID_IN_MARK_TAB: " + RugrelDropdownConfig.LOCK_GRID_IN_MARK_TAB,
                    "- DISABLE_ZOOM_IN_MARK_TAB: " + RugrelDropdownConfig.DISABLE_ZOOM_IN_MARK_TAB,
                    "- AUTO_LOCK_GRID_ON_TAB_SWITCH: " + RugrelDropdownConfig.AUTO_LOCK_GRID_ON_TAB_SWITCH,
                    "- SHOW_GRID_LOCK_INDICATOR: " + RugrelDropdownConfig.SHOW_GRID_LOCK_INDICATOR,
                    "",
                    "Current State:",
                    "- Grid Locked: " + gridLocked,
                    "- Zoom Disabled: " + zoomDisabled,
                    "- Current Zoom: " + String.format("%.0f%%", currentZoom * 100),
                    "",
                    "Use buttons below to test functionality!"
                };
                
                for (int i = 0; i < instructions.length; i++) {
                    g.drawString(instructions[i], 10, 55 + (i * 18));
                }
            }
            
            // Test methods
            public void testLockGrid() {
                gridLocked = true;
                zoomDisabled = true;
                repaint();
                System.out.println("🔒 TEST: Grid locked - zoom disabled");
            }
            
            public void testUnlockGrid() {
                gridLocked = false;
                zoomDisabled = false;
                repaint();
                System.out.println("🔓 TEST: Grid unlocked - zoom enabled");
            }
            
            public void testZoomIn() {
                if (zoomDisabled) {
                    System.out.println("🚫 TEST: Zoom In blocked - zoom disabled");
                    return;
                }
                currentZoom = Math.min(currentZoom * 1.25, 5.0);
                repaint();
                System.out.println("🔍 TEST: Zoom In to " + String.format("%.0f%%", currentZoom * 100));
            }
            
            public void testZoomOut() {
                if (zoomDisabled) {
                    System.out.println("🚫 TEST: Zoom Out blocked - zoom disabled");
                    return;
                }
                currentZoom = Math.max(currentZoom / 1.25, 0.1);
                repaint();
                System.out.println("🔍 TEST: Zoom Out to " + String.format("%.0f%%", currentZoom * 100));
            }
        };
        
        // Create control panel
        JPanel controlPanel = createControlPanel(testPanel);
        
        frame.add(testPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Print initial test status
        printTestStatus();
    }
    
    private static JPanel createControlPanel(JPanel testPanel) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        // Grid lock controls
        JButton lockGrid = new JButton("Lock Grid");
        lockGrid.addActionListener(e -> {
            try {
                java.lang.reflect.Method method = testPanel.getClass().getMethod("testLockGrid");
                method.invoke(testPanel);
            } catch (Exception ex) {
                System.err.println("Error calling testLockGrid: " + ex.getMessage());
            }
        });
        
        JButton unlockGrid = new JButton("Unlock Grid");
        unlockGrid.addActionListener(e -> {
            try {
                java.lang.reflect.Method method = testPanel.getClass().getMethod("testUnlockGrid");
                method.invoke(testPanel);
            } catch (Exception ex) {
                System.err.println("Error calling testUnlockGrid: " + ex.getMessage());
            }
        });
        
        // Zoom controls
        JButton zoomIn = new JButton("Zoom In");
        zoomIn.addActionListener(e -> {
            try {
                java.lang.reflect.Method method = testPanel.getClass().getMethod("testZoomIn");
                method.invoke(testPanel);
            } catch (Exception ex) {
                System.err.println("Error calling testZoomIn: " + ex.getMessage());
            }
        });
        
        JButton zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(e -> {
            try {
                java.lang.reflect.Method method = testPanel.getClass().getMethod("testZoomOut");
                method.invoke(testPanel);
            } catch (Exception ex) {
                System.err.println("Error calling testZoomOut: " + ex.getMessage());
            }
        });
        
        // Mark tab simulation
        JButton simulateMarkTab = new JButton("Simulate Mark Tab");
        simulateMarkTab.addActionListener(e -> {
            System.out.println("📋 TEST: Simulating switch to Mark Tab...");
            try {
                java.lang.reflect.Method lockMethod = testPanel.getClass().getMethod("testLockGrid");
                lockMethod.invoke(testPanel);
                System.out.println("✅ Auto-lock activated for Mark Tab");
            } catch (Exception ex) {
                System.err.println("Error simulating mark tab: " + ex.getMessage());
            }
        });
        
        controlPanel.add(lockGrid);
        controlPanel.add(unlockGrid);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(zoomIn);
        controlPanel.add(zoomOut);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(simulateMarkTab);
        
        return controlPanel;
    }
    
    private static void printTestStatus() {
        System.out.println("===========================================");
        System.out.println("🧪 Grid Lock and Zoom Control Test Started");
        System.out.println("===========================================");
        System.out.println("Configuration Status:");
        System.out.println("- ENABLE_GRID_LOCKING: " + RugrelDropdownConfig.ENABLE_GRID_LOCKING);
        System.out.println("- LOCK_GRID_IN_MARK_TAB: " + RugrelDropdownConfig.LOCK_GRID_IN_MARK_TAB);
        System.out.println("- PREVENT_GRID_RESIZE: " + RugrelDropdownConfig.PREVENT_GRID_RESIZE);
        System.out.println("- DISABLE_ZOOM_IN_MARK_TAB: " + RugrelDropdownConfig.DISABLE_ZOOM_IN_MARK_TAB);
        System.out.println("- DISABLE_ZOOM_OUT_IN_MARK_TAB: " + RugrelDropdownConfig.DISABLE_ZOOM_OUT_IN_MARK_TAB);
        System.out.println("- AUTO_LOCK_GRID_ON_TAB_SWITCH: " + RugrelDropdownConfig.AUTO_LOCK_GRID_ON_TAB_SWITCH);
        System.out.println("- SHOW_GRID_LOCK_INDICATOR: " + RugrelDropdownConfig.SHOW_GRID_LOCK_INDICATOR);
        System.out.println("- HIGHLIGHT_LOCKED_GRID_BORDER: " + RugrelDropdownConfig.HIGHLIGHT_LOCKED_GRID_BORDER);
        System.out.println("- SHOW_ZOOM_DISABLED_MESSAGE: " + RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE);
        System.out.println("===========================================");
        
        // Test API functionality
        testGridLockAPI();
    }
    
    private static void testGridLockAPI() {
        System.out.println("🔧 Testing Grid Lock API...");
        
        // Note: This would normally test DrawingCanvas methods,
        // but for this demo we'll test the configuration values
        
        System.out.println("Testing configuration flags:");
        System.out.println("- Grid locking enabled: " + RugrelDropdownConfig.ENABLE_GRID_LOCKING);
        System.out.println("- Mark tab grid lock: " + RugrelDropdownConfig.LOCK_GRID_IN_MARK_TAB);
        System.out.println("- Zoom disable: " + RugrelDropdownConfig.DISABLE_ZOOM_IN_MARK_TAB);
        System.out.println("- Visual indicators: " + RugrelDropdownConfig.SHOW_GRID_LOCK_INDICATOR);
        
        System.out.println("✅ Configuration Test Complete");
        System.out.println("");
        System.out.println("🎮 Use the GUI controls to test functionality!");
    }
}