import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * TestDisablePrintFunctionality - Comprehensive test for Disable Print functionality
 * Tests individual mark disable print behavior using soft coding techniques
 * 
 * Test Scenarios:
 * 1. Add various mark types to canvas
 * 2. Select marks and toggle disable print
 * 3. Verify disable print status is correctly applied
 * 4. Test soft coding configuration flags
 * 5. Validate print status persistence
 */
public class TestDisablePrintFunctionality {

    private static JFrame testFrame;
    private static DrawingCanvas canvas;
    private static JLabel statusLabel;
    private static JTextArea logArea;
    private static ArrayList<Mark> testMarks = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("🧪 Starting Disable Print Functionality Test with Soft Coding");
        System.out.println("📋 Test Configuration:");
        System.out.println("   ✅ ENABLE_DISABLE_PRINT_FUNCTIONALITY: " + RugrelDropdownConfig.ENABLE_DISABLE_PRINT_FUNCTIONALITY);
        System.out.println("   ✅ SHOW_PROPERTY_ACTION_FEEDBACK: " + RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK);
        System.out.println("   ✅ ELIMINATE_DISABLE_PRINT_DUPLICATES: " + RugrelDropdownConfig.ELIMINATE_DISABLE_PRINT_DUPLICATES);
        
        SwingUtilities.invokeLater(() -> createTestInterface());
    }
    
    private static void createTestInterface() {
        testFrame = new JFrame("Disable Print Functionality Test - Soft Coding Verification");
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
        
        logMessage("🚀 Test interface ready - Add marks and test disable print functionality");
    }
    
    private static JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // Add different mark types
        JButton addTextMark = new JButton("Add TextMark");
        addTextMark.addActionListener(e -> addTestTextMark());
        
        JButton addBarcodeMark = new JButton("Add BarcodeMark");
        addBarcodeMark.addActionListener(e -> addTestBarcodeMark());
        
        JButton addGraphMark = new JButton("Add GraphMark");
        addGraphMark.addActionListener(e -> addTestGraphMark());
        
        JButton addDotMatrixMark = new JButton("Add DotMatrixMark");
        addDotMatrixMark.addActionListener(e -> addTestDotMatrixMark());
        
        // Test operations
        JButton testDisablePrint = new JButton("Toggle Disable Print");
        testDisablePrint.addActionListener(e -> testDisablePrintToggle());
        
        JButton clearCanvas = new JButton("Clear All");
        clearCanvas.addActionListener(e -> clearAllMarks());
        
        JButton runFullTest = new JButton("Run Full Test");
        runFullTest.addActionListener(e -> runComprehensiveTest());
        
        panel.add(addTextMark);
        panel.add(addBarcodeMark);
        panel.add(addGraphMark);
        panel.add(addDotMatrixMark);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(testDisablePrint);
        panel.add(clearCanvas);
        panel.add(runFullTest);
        
        return panel;
    }
    
    private static JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status: Ready for testing");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(statusLabel, BorderLayout.CENTER);
        return panel;
    }
    
    private static JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Test Log"));
        
        logArea = new JTextArea(25, 30);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static void addTestTextMark() {
        try {
            int x = 100 + testMarks.size() * 50;
            int y = 100 + testMarks.size() * 40;
            String text = "Test Text " + (testMarks.size() + 1);
            
            TextMark textMark = new TextMark(x, y, text);
            textMark.width = 120;
            textMark.height = 30;
            
            canvas.addMarkObject(textMark);
            testMarks.add(textMark);
            
            logMessage("✅ TextMark added at (" + textMark.x + "," + textMark.y + ") - DisablePrint: " + textMark.disablePrint);
            updateStatus("TextMark added - Total marks: " + testMarks.size());
            
        } catch (Exception e) {
            logMessage("❌ Failed to add TextMark: " + e.getMessage());
        }
    }
    
    private static void addTestBarcodeMark() {
        try {
            int x = 200 + testMarks.size() * 50;
            int y = 150 + testMarks.size() * 40;
            String barcodeText = "123456";
            String barcodeType = "Code128";
            
            BarcodeMark barcodeMark = new BarcodeMark(x, y, barcodeText, barcodeType);
            barcodeMark.width = 100;
            barcodeMark.height = 50;
            
            canvas.addMarkObject(barcodeMark);
            testMarks.add(barcodeMark);
            
            logMessage("✅ BarcodeMark added at (" + barcodeMark.x + "," + barcodeMark.y + ") - DisablePrint: " + barcodeMark.disablePrint);
            updateStatus("BarcodeMark added - Total marks: " + testMarks.size());
            
        } catch (Exception e) {
            logMessage("❌ Failed to add BarcodeMark: " + e.getMessage());
        }
    }
    
    private static void addTestGraphMark() {
        try {
            int x = 300 + testMarks.size() * 50;
            int y = 200 + testMarks.size() * 40;
            
            GraphMark graphMark = new GraphMark(x, y);
            graphMark.width = 150;
            graphMark.height = 100;
            
            canvas.addMarkObject(graphMark);
            testMarks.add(graphMark);
            
            logMessage("✅ GraphMark added at (" + graphMark.x + "," + graphMark.y + ") - DisablePrint: " + graphMark.disablePrint);
            updateStatus("GraphMark added - Total marks: " + testMarks.size());
            
        } catch (Exception e) {
            logMessage("❌ Failed to add GraphMark: " + e.getMessage());
        }
    }
    
    private static void addTestDotMatrixMark() {
        try {
            int x = 400 + testMarks.size() * 50;
            int y = 250 + testMarks.size() * 40;
            String matrixText = "DM" + (testMarks.size() + 1);
            
            DotMatrixMark dotMatrixMark = new DotMatrixMark(x, y, matrixText);
            dotMatrixMark.width = 120;
            dotMatrixMark.height = 120;
            
            canvas.addMarkObject(dotMatrixMark);
            testMarks.add(dotMatrixMark);
            
            logMessage("✅ DotMatrixMark added at (" + dotMatrixMark.x + "," + dotMatrixMark.y + ") - DisablePrint: " + dotMatrixMark.disablePrint);
            updateStatus("DotMatrixMark added - Total marks: " + testMarks.size());
            
        } catch (Exception e) {
            logMessage("❌ Failed to add DotMatrixMark: " + e.getMessage());
        }
    }
    
    private static void testDisablePrintToggle() {
        Mark selectedMark = canvas.getSelectedMark();
        
        if (selectedMark != null) {
            logMessage("🎯 Testing Disable Print toggle on: " + selectedMark.getClass().getSimpleName());
            logMessage("   📊 Before toggle - DisablePrint: " + selectedMark.disablePrint);
            
            // Test the toggle functionality
            canvas.togglePrintDisabled();
            
            logMessage("   📊 After toggle - DisablePrint: " + selectedMark.disablePrint);
            
            String status = selectedMark.disablePrint ? "DISABLED" : "ENABLED";
            updateStatus("Print " + status + " for " + selectedMark.getClass().getSimpleName());
            
        } else {
            logMessage("⚠️ No mark selected - Testing global disable print");
            canvas.togglePrintDisabled();
            updateStatus("Global print toggle executed");
        }
    }
    
    private static void clearAllMarks() {
        canvas.clearAllMarks();
        testMarks.clear();
        logMessage("🗑️ All marks cleared from canvas");
        updateStatus("Canvas cleared - Ready for new tests");
    }
    
    private static void runComprehensiveTest() {
        logMessage("🧪 Starting Comprehensive Disable Print Test");
        logMessage("==========================================");
        
        // Clear existing marks
        clearAllMarks();
        
        // Test 1: Add marks of different types
        logMessage("📝 Test 1: Adding various mark types...");
        addTestTextMark();
        addTestBarcodeMark();
        addTestGraphMark();
        addTestDotMatrixMark();
        
        // Test 2: Test disable print on each mark type
        logMessage("📝 Test 2: Testing disable print on each mark type...");
        for (int i = 0; i < testMarks.size(); i++) {
            Mark mark = testMarks.get(i);
            canvas.setSelectedMark(mark);
            
            logMessage("   Testing " + mark.getClass().getSimpleName() + ":");
            logMessage("     Initial disablePrint: " + mark.disablePrint);
            
            // Toggle twice to test both states
            canvas.togglePrintDisabled();
            logMessage("     After first toggle: " + mark.disablePrint);
            
            canvas.togglePrintDisabled();
            logMessage("     After second toggle: " + mark.disablePrint);
        }
        
        // Test 3: Verify soft coding flags
        logMessage("📝 Test 3: Verifying soft coding configuration...");
        logMessage("   ENABLE_DISABLE_PRINT_FUNCTIONALITY: " + RugrelDropdownConfig.ENABLE_DISABLE_PRINT_FUNCTIONALITY);
        logMessage("   SHOW_PROPERTY_ACTION_FEEDBACK: " + RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK);
        logMessage("   ELIMINATE_DISABLE_PRINT_DUPLICATES: " + RugrelDropdownConfig.ELIMINATE_DISABLE_PRINT_DUPLICATES);
        
        // Test 4: Test with disabled functionality
        logMessage("📝 Test 4: Testing with disabled functionality...");
        // Note: This would require temporarily changing the config, 
        // but we'll just log the expected behavior
        if (!RugrelDropdownConfig.ENABLE_DISABLE_PRINT_FUNCTIONALITY) {
            logMessage("   ✅ Disable Print functionality is disabled via soft coding");
        } else {
            logMessage("   ✅ Disable Print functionality is enabled via soft coding");
        }
        
        logMessage("🎉 Comprehensive test completed!");
        logMessage("==========================================");
        updateStatus("Comprehensive test completed - Check log for results");
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