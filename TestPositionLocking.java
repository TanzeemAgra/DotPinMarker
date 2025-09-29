import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Test class for Position Locking System
 * 
 * Tests:
 * 1. Automatic position locking after placement
 * 2. Top-left corner position fixation
 * 3. Prevention of drag operations on locked objects
 * 4. Visual feedback for locked position objects
 * 5. Soft coding configuration compliance
 */
public class TestPositionLocking {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Position Locking System Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        
        // Create test panel
        JPanel testPanel = new JPanel() {
            private TextMark unlockedMark = createUnlockedTextMark();
            private TextMark lockedMark = createLockedTextMark();
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint test objects
                unlockedMark.draw(g2, true);  // Show as selected to see handles
                lockedMark.draw(g2, true);    // Show as selected to see lock indicators
                
                // Draw test instructions
                drawTestInstructions(g2);
            }
            
            private void drawTestInstructions(Graphics2D g) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("Position Locking System Test", 10, 25);
                
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                String[] instructions = {
                    "",
                    "🔓 UNLOCKED Object (Left): Can be dragged freely",
                    "🔒 LOCKED Object (Right): Position is fixed - cannot be moved",
                    "",
                    "Configuration Status:",
                    "- ENABLE_POSITION_LOCKING: " + RugrelDropdownConfig.ENABLE_POSITION_LOCKING,
                    "- AUTO_LOCK_ON_PLACEMENT: " + RugrelDropdownConfig.AUTO_LOCK_ON_PLACEMENT,
                    "- LOCK_TOP_LEFT_CORNER: " + RugrelDropdownConfig.LOCK_TOP_LEFT_CORNER,
                    "- PREVENT_DRAG_WHEN_LOCKED: " + RugrelDropdownConfig.PREVENT_DRAG_WHEN_LOCKED,
                    "- SHOW_POSITION_LOCK_INDICATOR: " + RugrelDropdownConfig.SHOW_POSITION_LOCK_INDICATOR,
                    "",
                    "Try dragging both objects to see the difference!"
                };
                
                for (int i = 0; i < instructions.length; i++) {
                    g.drawString(instructions[i], 10, 50 + (i * 18));
                }
                
                // Draw status information
                g.setColor(new Color(0, 0, 150));
                g.setFont(new Font("Arial", Font.BOLD, 11));
                g.drawString("Unlocked Status: " + unlockedMark.getPositionLockInfo(), 10, 300);
                g.drawString("Locked Status: " + lockedMark.getPositionLockInfo(), 10, 320);
            }
        };
        
        // Add mouse listeners for testing drag operations
        testPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Test drag start on both objects
                System.out.println("🖱️ Mouse pressed at (" + e.getX() + ", " + e.getY() + ")");
                testPanel.repaint();
            }
        });
        
        testPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Simulate drag operations
                testPanel.repaint();
            }
        });
        
        // Create control panel
        JPanel controlPanel = createControlPanel(testPanel);
        
        frame.add(testPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Print initial test status
        printTestStatus();
    }
    
    private static TextMark createUnlockedTextMark() {
        TextMark mark = new TextMark(150, 150, "UNLOCKED");
        // This object remains unlocked for comparison
        return mark;
    }
    
    private static TextMark createLockedTextMark() {
        TextMark mark = new TextMark(450, 150, "LOCKED");
        // Lock this object's position
        mark.lockPosition("TEST_LOCK");
        return mark;
    }
    
    private static JPanel createControlPanel(JPanel testPanel) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        // Test buttons
        JButton testAutoLock = new JButton("Test Auto-Lock");
        testAutoLock.addActionListener(e -> {
            System.out.println("🧪 Testing auto-lock functionality...");
            TextMark testMark = new TextMark(300, 300, "AUTO-TEST");
            testMark.autoLockOnPlacement();
            System.out.println("Auto-lock result: " + testMark.getPositionLockInfo());
            testPanel.repaint();
        });
        
        JButton testMovementCheck = new JButton("Test Movement Check");
        testMovementCheck.addActionListener(e -> {
            System.out.println("🧪 Testing movement permissions...");
            TextMark locked = createLockedTextMark();
            TextMark unlocked = createUnlockedTextMark();
            
            System.out.println("Locked object movement allowed: " + locked.isMovementAllowed());
            System.out.println("Unlocked object movement allowed: " + unlocked.isMovementAllowed());
            System.out.println("Locked object can drag: " + locked.canDrag());
            System.out.println("Unlocked object can drag: " + unlocked.canDrag());
        });
        
        JButton testUnlock = new JButton("Test Unlock Attempt");
        testUnlock.addActionListener(e -> {
            System.out.println("🧪 Testing unlock attempt...");
            TextMark locked = createLockedTextMark();
            boolean unlockResult = locked.attemptUnlockPosition();
            System.out.println("Unlock attempt result: " + unlockResult);
            System.out.println("New status: " + locked.getPositionLockInfo());
        });
        
        controlPanel.add(testAutoLock);
        controlPanel.add(testMovementCheck);
        controlPanel.add(testUnlock);
        
        return controlPanel;
    }
    
    private static void printTestStatus() {
        System.out.println("===========================================");
        System.out.println("🧪 Position Locking System Test Started");
        System.out.println("===========================================");
        System.out.println("Configuration Status:");
        System.out.println("- ENABLE_POSITION_LOCKING: " + RugrelDropdownConfig.ENABLE_POSITION_LOCKING);
        System.out.println("- AUTO_LOCK_ON_PLACEMENT: " + RugrelDropdownConfig.AUTO_LOCK_ON_PLACEMENT);
        System.out.println("- LOCK_TOP_LEFT_CORNER: " + RugrelDropdownConfig.LOCK_TOP_LEFT_CORNER);
        System.out.println("- PREVENT_DRAG_WHEN_LOCKED: " + RugrelDropdownConfig.PREVENT_DRAG_WHEN_LOCKED);
        System.out.println("- PREVENT_RESIZE_WHEN_LOCKED: " + RugrelDropdownConfig.PREVENT_RESIZE_WHEN_LOCKED);
        System.out.println("- SHOW_POSITION_LOCK_INDICATOR: " + RugrelDropdownConfig.SHOW_POSITION_LOCK_INDICATOR);
        System.out.println("- USE_LOCK_ICON_OVERLAY: " + RugrelDropdownConfig.USE_LOCK_ICON_OVERLAY);
        System.out.println("- HIGHLIGHT_LOCKED_POSITION: " + RugrelDropdownConfig.HIGHLIGHT_LOCKED_POSITION);
        System.out.println("- ENABLE_MANUAL_POSITION_UNLOCK: " + RugrelDropdownConfig.ENABLE_MANUAL_POSITION_UNLOCK);
        System.out.println("===========================================");
        
        // Test core functionality
        testPositionLockingAPI();
    }
    
    private static void testPositionLockingAPI() {
        System.out.println("🔧 Testing Position Locking API...");
        
        // Test 1: Basic position locking
        TextMark testMark = new TextMark(100, 200, "API-TEST");
        System.out.println("Initial lock status: " + testMark.isPositionLocked());
        
        testMark.lockPosition("API_TEST");
        System.out.println("After lock: " + testMark.isPositionLocked());
        System.out.println("Lock info: " + testMark.getPositionLockInfo());
        
        // Test 2: Movement permission checking
        System.out.println("Movement allowed: " + testMark.isMovementAllowed());
        System.out.println("Can drag: " + testMark.canDrag());
        
        // Test 3: Auto-lock functionality
        TextMark autoTestMark = new TextMark(300, 400, "AUTO-TEST");
        autoTestMark.autoLockOnPlacement();
        System.out.println("Auto-lock result: " + autoTestMark.getPositionLockInfo());
        
        // Test 4: Unlock attempt
        boolean unlockResult = testMark.attemptUnlockPosition();
        System.out.println("Unlock attempt: " + unlockResult);
        System.out.println("Final status: " + testMark.getPositionLockInfo());
        
        System.out.println("✅ API Test Complete");
    }
}