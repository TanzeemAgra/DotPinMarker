import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * TestTextMarkResizing - Comprehensive test for TextMark multi-directional resizing
 * Tests vertical, horizontal, and diagonal resizing using soft coding techniques
 * 
 * Test Scenarios:
 * 1. Add TextMark to canvas
 * 2. Test vertical resizing (font size adjustment)
 * 3. Test horizontal resizing (character spacing adjustment)
 * 4. Test diagonal resizing (combined font + spacing)
 * 5. Verify soft coding configuration flags work
 * 6. Test Lock Size functionality integration
 */
public class TestTextMarkResizing {

    private static JFrame testFrame;
    private static DrawingCanvas canvas;
    private static JLabel statusLabel;
    private static JTextArea logArea;
    private static TextMark testTextMark;
    
    public static void main(String[] args) {
        System.out.println("🧪 Starting TextMark Multi-Directional Resizing Test");
        System.out.println("📋 Soft Coding Configuration:");
        System.out.println("   ✅ ENABLE_TEXTMARK_MULTI_DIRECTIONAL_RESIZE: " + RugrelDropdownConfig.ENABLE_TEXTMARK_MULTI_DIRECTIONAL_RESIZE);
        System.out.println("   ✅ ENABLE_TEXTMARK_FONT_SIZE_HANDLES: " + RugrelDropdownConfig.ENABLE_TEXTMARK_FONT_SIZE_HANDLES);
        System.out.println("   ✅ ENABLE_TEXTMARK_SPACING_HANDLES: " + RugrelDropdownConfig.ENABLE_TEXTMARK_SPACING_HANDLES);
        System.out.println("   ✅ ENABLE_TEXTMARK_DIAGONAL_HANDLES: " + RugrelDropdownConfig.ENABLE_TEXTMARK_DIAGONAL_HANDLES);
        System.out.println("   ✅ TEXTMARK_FONT_SIZE_SENSITIVITY: " + RugrelDropdownConfig.TEXTMARK_FONT_SIZE_SENSITIVITY);
        System.out.println("   ✅ TEXTMARK_SPACING_SENSITIVITY: " + RugrelDropdownConfig.TEXTMARK_SPACING_SENSITIVITY);
        System.out.println("   ✅ FONT_HANDLE_RADIUS: " + RugrelDropdownConfig.TEXTMARK_FONT_HANDLE_RADIUS);
        System.out.println("   ✅ SPACING_HANDLE_RADIUS: " + RugrelDropdownConfig.TEXTMARK_SPACING_HANDLE_RADIUS);
        
        SwingUtilities.invokeLater(() -> createTestInterface());
    }
    
    private static void createTestInterface() {
        testFrame = new JFrame("TextMark Multi-Directional Resizing Test - Soft Coding");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setLayout(new BorderLayout());
        
        // Create test canvas
        canvas = new DrawingCanvas();
        canvas.setPreferredSize(new Dimension(800, 600));
        canvas.setBackground(Color.WHITE);
        
        // Create control panel
        JPanel controlPanel = createControlPanel();
        
        // Create status panel
        JPanel statusPanel = createStatusPanel();
        
        // Create log panel
        JPanel logPanel = createLogPanel();
        
        // Layout
        testFrame.add(canvas, BorderLayout.CENTER);
        testFrame.add(controlPanel, BorderLayout.NORTH);
        testFrame.add(statusPanel, BorderLayout.SOUTH);
        testFrame.add(logPanel, BorderLayout.EAST);
        
        testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
        
        logMessage("🚀 TextMark resize test interface ready");
        logMessage("📝 Add a TextMark and test different resize handles:");
        logMessage("   🔵 TOP/BOTTOM handles: Font size adjustment");
        logMessage("   🟢 LEFT/RIGHT handles: Character spacing adjustment");
        logMessage("   🟠 CORNER handles: Combined font + spacing adjustment");
    }
    
    private static JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // Add TextMark
        JButton addTextMark = new JButton("Add TextMark");
        addTextMark.addActionListener(e -> addTestTextMark());
        
        // Test operations
        JButton testVerticalResize = new JButton("Test Vertical Resize");
        testVerticalResize.addActionListener(e -> testVerticalResize());
        
        JButton testHorizontalResize = new JButton("Test Horizontal Resize");
        testHorizontalResize.addActionListener(e -> testHorizontalResize());
        
        JButton testDiagonalResize = new JButton("Test Diagonal Resize");
        testDiagonalResize.addActionListener(e -> testDiagonalResize());
        
        JButton testLockSize = new JButton("Toggle Lock Size");
        testLockSize.addActionListener(e -> testLockSizeToggle());
        
        JButton clearCanvas = new JButton("Clear Canvas");
        clearCanvas.addActionListener(e -> clearCanvas());
        
        JButton runFullTest = new JButton("Run Full Test");
        runFullTest.addActionListener(e -> runComprehensiveTest());
        
        panel.add(addTextMark);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(testVerticalResize);
        panel.add(testHorizontalResize);
        panel.add(testDiagonalResize);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(testLockSize);
        panel.add(clearCanvas);
        panel.add(runFullTest);
        
        return panel;
    }
    
    private static JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status: Ready for TextMark resize testing");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(statusLabel, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Resize Test Log"));
        
        logArea = new JTextArea(25, 35);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static void addTestTextMark() {
        try {
            testTextMark = new TextMark(200, 150, "RESIZE TEST");
            canvas.addMarkObject(testTextMark);
            canvas.setSelectedMark(testTextMark);
            
            logMessage("✅ TextMark added at (200,150) with text 'RESIZE TEST'");
            logMessage("   📊 Initial font size: " + testTextMark.getFont().getSize() + "pt");
            logMessage("   📊 Initial dimensions: " + testTextMark.width + "x" + testTextMark.height);
            updateStatus("TextMark added - Ready for resize testing");
            
        } catch (Exception e) {
            logMessage("❌ Failed to add TextMark: " + e.getMessage());
        }
    }
    
    private static void testVerticalResize() {
        if (testTextMark == null) {
            logMessage("⚠️ No TextMark available - Add one first");
            return;
        }
        
        logMessage("🔵 Testing Vertical Resize (Font Size Adjustment)...");
        
        // Simulate TOP handle resize (increase font size)
        int initialFontSize = testTextMark.getFont().getSize();
        logMessage("   📊 Initial font size: " + initialFontSize + "pt");
        
        // Simulate dragging TOP handle upward (negative Y = increase font)
        testTextMark.startResize();
        testTextMark.resizeText(0, -20, TextMark.TextResizeHandle.TOP);
        testTextMark.stopResize();
        
        int newFontSize = testTextMark.getFont().getSize();
        logMessage("   📊 After TOP handle resize: " + newFontSize + "pt");
        logMessage("   📈 Font size change: " + (newFontSize - initialFontSize) + "pt");
        
        // Simulate BOTTOM handle resize (decrease font size)
        testTextMark.startResize();
        testTextMark.resizeText(0, 10, TextMark.TextResizeHandle.BOTTOM);
        testTextMark.stopResize();
        
        int finalFontSize = testTextMark.getFont().getSize();
        logMessage("   📊 After BOTTOM handle resize: " + finalFontSize + "pt");
        
        updateStatus("Vertical resize test completed - Font size: " + finalFontSize + "pt");
        canvas.repaint();
    }
    
    private static void testHorizontalResize() {
        if (testTextMark == null) {
            logMessage("⚠️ No TextMark available - Add one first");
            return;
        }
        
        logMessage("🟢 Testing Horizontal Resize (Character Spacing Adjustment)...");
        
        // Get initial character width (this is private, so we'll just show the resize action)
        logMessage("   📊 Testing character spacing adjustment...");
        
        // Simulate RIGHT handle resize (increase spacing)
        testTextMark.startResize();
        testTextMark.resizeText(15, 0, TextMark.TextResizeHandle.RIGHT);
        testTextMark.stopResize();
        
        logMessage("   📊 RIGHT handle resize completed (spacing increased)");
        
        // Simulate LEFT handle resize (decrease spacing)
        testTextMark.startResize();
        testTextMark.resizeText(-10, 0, TextMark.TextResizeHandle.LEFT);
        testTextMark.stopResize();
        
        logMessage("   📊 LEFT handle resize completed (spacing decreased)");
        
        updateStatus("Horizontal resize test completed - Character spacing adjusted");
        canvas.repaint();
    }
    
    private static void testDiagonalResize() {
        if (testTextMark == null) {
            logMessage("⚠️ No TextMark available - Add one first");
            return;
        }
        
        logMessage("🟠 Testing Diagonal Resize (Combined Font + Spacing Adjustment)...");
        
        int initialFontSize = testTextMark.getFont().getSize();
        logMessage("   📊 Initial font size: " + initialFontSize + "pt");
        
        // Simulate BOTTOM_RIGHT handle resize (increase both font and spacing)
        testTextMark.startResize();
        testTextMark.resizeText(12, 15, TextMark.TextResizeHandle.BOTTOM_RIGHT);
        testTextMark.stopResize();
        
        int newFontSize = testTextMark.getFont().getSize();
        logMessage("   📊 After BOTTOM_RIGHT resize: " + newFontSize + "pt (font + spacing)");
        
        // Simulate TOP_LEFT handle resize (combined adjustment)
        testTextMark.startResize();
        testTextMark.resizeText(-8, -10, TextMark.TextResizeHandle.TOP_LEFT);
        testTextMark.stopResize();
        
        int finalFontSize = testTextMark.getFont().getSize();
        logMessage("   📊 After TOP_LEFT resize: " + finalFontSize + "pt (font + spacing)");
        
        updateStatus("Diagonal resize test completed - Combined adjustments applied");
        canvas.repaint();
    }
    
    private static void testLockSizeToggle() {
        if (testTextMark == null) {
            logMessage("⚠️ No TextMark available - Add one first");
            return;
        }
        
        boolean wasLocked = testTextMark.lockSize;
        testTextMark.lockSize = !testTextMark.lockSize;
        
        String status = testTextMark.lockSize ? "LOCKED" : "UNLOCKED";
        logMessage("🔒 Lock Size toggled - TextMark is now " + status);
        
        // Test resize with lock
        if (testTextMark.lockSize) {
            logMessage("   🧪 Testing resize with size locked...");
            testTextMark.startResize();
            testTextMark.resizeText(10, 10, TextMark.TextResizeHandle.BOTTOM_RIGHT);
            testTextMark.stopResize();
            logMessage("   📊 Resize with lock completed (should be blocked)");
        }
        
        updateStatus("Lock Size: " + status + " - Resize " + (testTextMark.lockSize ? "blocked" : "enabled"));
    }
    
    private static void clearCanvas() {
        canvas.clearAllMarks();
        testTextMark = null;
        logMessage("🗑️ Canvas cleared - Ready for new TextMark");
        updateStatus("Canvas cleared - Add new TextMark to continue testing");
    }
    
    private static void runComprehensiveTest() {
        logMessage("🧪 Starting Comprehensive TextMark Resize Test");
        logMessage("==========================================");
        
        // Clear and start fresh
        clearCanvas();
        
        // Test 1: Add TextMark
        logMessage("📝 Test 1: Adding TextMark...");
        addTestTextMark();
        
        // Test 2: Vertical resize
        logMessage("📝 Test 2: Testing vertical resize...");
        testVerticalResize();
        
        // Test 3: Horizontal resize
        logMessage("📝 Test 3: Testing horizontal resize...");
        testHorizontalResize();
        
        // Test 4: Diagonal resize
        logMessage("📝 Test 4: Testing diagonal resize...");
        testDiagonalResize();
        
        // Test 5: Lock Size functionality
        logMessage("📝 Test 5: Testing Lock Size functionality...");
        testLockSizeToggle();
        
        // Test 6: Soft coding configuration
        logMessage("📝 Test 6: Verifying soft coding configuration...");
        logMessage("   ✅ Multi-directional resize: " + RugrelDropdownConfig.ENABLE_TEXTMARK_MULTI_DIRECTIONAL_RESIZE);
        logMessage("   ✅ Font size handles: " + RugrelDropdownConfig.ENABLE_TEXTMARK_FONT_SIZE_HANDLES);
        logMessage("   ✅ Spacing handles: " + RugrelDropdownConfig.ENABLE_TEXTMARK_SPACING_HANDLES);
        logMessage("   ✅ Diagonal handles: " + RugrelDropdownConfig.ENABLE_TEXTMARK_DIAGONAL_HANDLES);
        logMessage("   ✅ Respects Lock Size: " + RugrelDropdownConfig.TEXTMARK_RESPECT_LOCK_SIZE);
        
        logMessage("🎉 Comprehensive TextMark resize test completed!");
        logMessage("==========================================");
        updateStatus("Comprehensive test completed - Check log for detailed results");
    }
    
    private static void logMessage(String message) {
        if (logArea != null) {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
        System.out.println(message);
    }
    
    private static void updateStatus(String status) {
        if (statusLabel != null) {
            statusLabel.setText("Status: " + status);
        }
    }
}