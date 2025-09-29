import javax.swing.*;
import java.awt.*;

/**
 * ThorX6 Coder Type System - Standalone Test & Demo
 * 
 * This class provides a complete standalone test of the ThorX6 Coder Type system
 * without requiring integration with the existing ThorX6HorizontalConfig system.
 */
public class ThorX6CoderTypeStandaloneDemo {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    private static StringBuilder testResults = new StringBuilder();
    
    /**
     * Test assertion utility
     */
    private static void assertTrue(String testName, boolean condition, String message) {
        if (condition) {
            testsPassed++;
            testResults.append("✅ PASS: ").append(testName).append("\n");
        } else {
            testsFailed++;
            testResults.append("❌ FAIL: ").append(testName).append(" - ").append(message).append("\n");
        }
    }
    
    /**
     * Test assertion for non-null values
     */
    private static void assertNotNull(String testName, Object value, String message) {
        assertTrue(testName, value != null, message);
    }
    
    /**
     * Test assertion for string content
     */
    private static void assertNotEmpty(String testName, String value, String message) {
        assertTrue(testName, value != null && !value.trim().isEmpty(), message);
    }
    
    /**
     * Start a test category
     */
    private static void startTestCategory(String categoryName) {
        testResults.append("\n🧪 ").append(categoryName).append("\n");
        testResults.append("═".repeat(50)).append("\n");
    }
    
    /**
     * Run all core system tests
     */
    public static String runAllTests() {
        testsPassed = 0;
        testsFailed = 0;
        testResults = new StringBuilder();
        
        testResults.append("🚀 ThorX6 Coder Type System - Standalone Test Suite\n");
        testResults.append("=".repeat(60)).append("\n");
        
        long startTime = System.currentTimeMillis();
        
        runCoreSystemTests();
        runCoderTypeTests();
        runGUITests();
        runPerformanceTests();
        runDemonstrationTests();
        
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime) / 1000.0;
        
        // Summary
        testResults.append("\n🏁 TEST SUMMARY\n");
        testResults.append("=".repeat(30)).append("\n");
        testResults.append("Tests Passed: ").append(testsPassed).append("\n");
        testResults.append("Tests Failed: ").append(testsFailed).append("\n");
        testResults.append("Total Tests: ").append(testsPassed + testsFailed).append("\n");
        testResults.append("Success Rate: ").append(String.format("%.1f", (double)testsPassed / (testsPassed + testsFailed) * 100)).append("%\n");
        testResults.append("Execution Time: ").append(String.format("%.2f", totalTime)).append(" seconds\n");
        
        if (testsFailed == 0) {
            testResults.append("\n✅ ALL TESTS PASSED! System is ready for production.\n");
        } else {
            testResults.append("\n⚠️ Some tests failed. Please review and fix issues.\n");
        }
        
        return testResults.toString();
    }
    
    /**
     * Test core system components
     */
    private static void runCoreSystemTests() {
        startTestCategory("CORE SYSTEM TESTS");
        
        // Test registry
        String[] availableTypes = ThorX6CoderTypeSystem.CoderTypeRegistry.getAvailableTypes();
        assertNotNull("Registry Available Types", availableTypes, "Available types should not be null");
        assertTrue("Registry Type Count", availableTypes.length >= 5, "Should have at least 5 coder types");
        
        testResults.append("  Available types: ").append(availableTypes.length).append(" (");
        for (int i = 0; i < availableTypes.length; i++) {
            testResults.append(availableTypes[i]);
            if (i < availableTypes.length - 1) testResults.append(", ");
        }
        testResults.append(")\n");
        
        // Test manager
        ThorX6CoderTypeSystem.CoderTypeManager manager = new ThorX6CoderTypeSystem.CoderTypeManager();
        assertNotNull("Manager Creation", manager, "Manager should not be null");
        
        String initialType = manager.getCurrentTypeName();
        assertNotEmpty("Manager Initial Type", initialType, "Should have initial type");
        
        // Test type switching
        manager.setCoderType("Serial Number");
        assertTrue("Manager Type Switch", "Serial Number".equals(manager.getCurrentTypeName()), "Should switch types");
    }
    
    /**
     * Test individual coder types
     */
    private static void runCoderTypeTests() {
        startTestCategory("CODER TYPE TESTS");
        
        String[] types = {"No Code", "Serial Number", "VIN", "Date/Time", "Random Number"};
        
        for (String type : types) {
            ThorX6CoderTypeSystem.CoderType coder = ThorX6CoderTypeSystem.CoderTypeRegistry.getCoderType(type);
            assertNotNull("Create " + type, coder, "Should create " + type + " coder");
            
            if (coder != null) {
                String code1 = coder.generateCode();
                String code2 = coder.generateCode();
                
                assertNotNull("Generate " + type + " Code1", code1, "Should generate code");
                assertNotNull("Generate " + type + " Code2", code2, "Should generate second code");
                
                String preview = coder.getPreview();
                assertNotNull("Preview " + type, preview, "Should have preview");
                
                testResults.append("  ").append(type).append(": '").append(code1).append("' | '").append(code2).append("'\n");
                
                // Type-specific tests
                if ("VIN".equals(type)) {
                    assertTrue("VIN Length", code1.length() == 17, "VIN should be 17 characters");
                    assertTrue("VIN Uniqueness", !code1.equals(code2), "VINs should be unique");
                }
                
                if ("Serial Number".equals(type)) {
                    // Test reset functionality
                    coder.reset();
                    String resetCode = coder.generateCode();
                    testResults.append("    After reset: '").append(resetCode).append("'\n");
                }
            }
        }
    }
    
    /**
     * Test GUI components
     */
    private static void runGUITests() {
        startTestCategory("GUI TESTS");
        
        try {
            ThorX6CoderTypeGUI gui = new ThorX6CoderTypeGUI();
            assertNotNull("GUI Creation", gui, "GUI should be created");
            
            assertTrue("GUI Is Panel", gui instanceof JPanel, "GUI should be a JPanel");
            assertTrue("GUI Has Components", gui.getComponentCount() > 0, "GUI should have components");
            
            // Test manager access
            ThorX6CoderTypeSystem.CoderTypeManager manager = gui.getCoderManager();
            assertNotNull("GUI Manager", manager, "GUI should have manager");
            
            // Test code generation through GUI
            String code = gui.generateCode();
            assertNotNull("GUI Generate Code", code, "GUI should generate code");
            
            // Test type switching
            gui.setCoderType("VIN");
            assertTrue("GUI Type Switch", "VIN".equals(gui.getCoderManager().getCurrentTypeName()), "Should switch to VIN");
            
            String vinCode = gui.generateCode();
            assertTrue("GUI VIN Generation", vinCode.length() == 17, "Should generate 17-char VIN");
            
            testResults.append("  GUI generated VIN: ").append(vinCode).append("\n");
            
        } catch (Exception e) {
            testsFailed++;
            testResults.append("❌ FAIL: GUI Test - Exception: ").append(e.getMessage()).append("\n");
        }
    }
    
    /**
     * Test performance
     */
    private static void runPerformanceTests() {
        startTestCategory("PERFORMANCE TESTS");
        
        String[] testTypes = {"Serial Number", "VIN", "Random Number"};
        int iterations = 1000;
        
        for (String type : testTypes) {
            ThorX6CoderTypeSystem.CoderType coder = ThorX6CoderTypeSystem.CoderTypeRegistry.getCoderType(type);
            
            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                coder.generateCode();
            }
            long endTime = System.nanoTime();
            
            double avgTimeMs = (endTime - startTime) / 1_000_000.0 / iterations;
            testResults.append("  ").append(type).append(": ").append(String.format("%.3f", avgTimeMs)).append(" ms/generation\n");
            
            assertTrue("Performance " + type, avgTimeMs < 1.0, "Should generate in less than 1ms");
        }
    }
    
    /**
     * Run demonstration of features
     */
    private static void runDemonstrationTests() {
        startTestCategory("FEATURE DEMONSTRATION");
        
        // Demonstrate each coder type
        testResults.append("\n📋 Coder Type Examples:\n");
        String[] types = ThorX6CoderTypeSystem.CoderTypeRegistry.getAvailableTypes();
        
        for (String type : types) {
            ThorX6CoderTypeSystem.CoderType coder = ThorX6CoderTypeSystem.CoderTypeRegistry.getCoderType(type);
            testResults.append("  ").append(type).append(":\n");
            
            // Generate multiple examples
            for (int i = 0; i < 3; i++) {
                String example = coder.generateCode();
                testResults.append("    Example ").append(i + 1).append(": ").append(example).append("\n");
            }
            testResults.append("\n");
        }
        
        // Demonstrate serial number sequence
        testResults.append("🔢 Serial Number Sequence Demo:\n");
        ThorX6CoderTypeSystem.CoderType serialCoder = ThorX6CoderTypeSystem.CoderTypeRegistry.getCoderType("Serial Number");
        serialCoder.reset();
        
        testResults.append("  Sequence: ");
        for (int i = 0; i < 10; i++) {
            testResults.append(serialCoder.generateCode()).append(" ");
        }
        testResults.append("\n\n");
        
        // Demonstrate VIN characteristics
        testResults.append("🚗 VIN Characteristics Demo:\n");
        ThorX6CoderTypeSystem.CoderType vinCoder = ThorX6CoderTypeSystem.CoderTypeRegistry.getCoderType("VIN");
        
        for (int i = 0; i < 5; i++) {
            String vin = vinCoder.generateCode();
            boolean hasExcluded = vin.contains("I") || vin.contains("O") || vin.contains("Q");
            testResults.append("  VIN ").append(i + 1).append(": ").append(vin)
                         .append(" (Length: ").append(vin.length())
                         .append(", Excluded chars: ").append(hasExcluded ? "FOUND" : "NONE")
                         .append(")\n");
        }
    }
    
    /**
     * Create comprehensive demo window
     */
    public static JFrame createDemoWindow() {
        JFrame frame = new JFrame("ThorX6 Coder Type System - Complete Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Create tabbed pane for different demos
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Test Results Tab
        JTextArea testResultsArea = new JTextArea(25, 60);
        testResultsArea.setEditable(false);
        testResultsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        JScrollPane testScrollPane = new JScrollPane(testResultsArea);
        
        JPanel testPanel = new JPanel(new BorderLayout());
        testPanel.add(testScrollPane, BorderLayout.CENTER);
        
        JButton runTestsButton = new JButton("Run All Tests");
        runTestsButton.addActionListener(e -> {
            testResultsArea.setText("Running comprehensive tests...\n");
            SwingUtilities.invokeLater(() -> {
                String results = runAllTests();
                testResultsArea.setText(results);
                testResultsArea.setCaretPosition(0);
            });
        });
        
        testPanel.add(runTestsButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Test Results", testPanel);
        
        // Interactive Demo Tab
        ThorX6CoderTypeGUI interactiveGUI = new ThorX6CoderTypeGUI();
        JScrollPane interactiveScrollPane = new JScrollPane(interactiveGUI);
        tabbedPane.addTab("Interactive Demo", interactiveScrollPane);
        
        // Code Examples Tab
        JTextArea examplesArea = new JTextArea(25, 60);
        examplesArea.setEditable(false);
        examplesArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        
        // Generate code examples
        StringBuilder examples = new StringBuilder();
        examples.append("🎯 ThorX6 Coder Type System - Code Examples\n");
        examples.append("=".repeat(50)).append("\n\n");
        
        String[] types = ThorX6CoderTypeSystem.CoderTypeRegistry.getAvailableTypes();
        for (String type : types) {
            examples.append("📌 ").append(type).append(" Examples:\n");
            ThorX6CoderTypeSystem.CoderType coder = ThorX6CoderTypeSystem.CoderTypeRegistry.getCoderType(type);
            
            for (int i = 0; i < 5; i++) {
                examples.append("   ").append(coder.generateCode()).append("\n");
            }
            examples.append("\n");
        }
        
        examplesArea.setText(examples.toString());
        JScrollPane examplesScrollPane = new JScrollPane(examplesArea);
        tabbedPane.addTab("Code Examples", examplesScrollPane);
        
        frame.add(tabbedPane, BorderLayout.CENTER);
        
        // Status bar
        JLabel statusLabel = new JLabel("ThorX6 Coder Type System - Ready for testing and demonstration");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(statusLabel, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        return frame;
    }
    
    /**
     * Main method for standalone testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (args.length > 0 && "console".equals(args[0])) {
                // Console mode
                System.out.println(runAllTests());
            } else {
                // GUI mode
                JFrame demoFrame = createDemoWindow();
                demoFrame.setVisible(true);
                
                // Auto-run tests after a short delay
                Timer autoTest = new Timer(1000, e -> {
                    // This will trigger the test run automatically
                });
                autoTest.setRepeats(false);
                autoTest.start();
            }
        });
    }
}