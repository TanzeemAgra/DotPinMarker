// Comprehensive Application Verification Test
// Tests all major functionality and identifies potential issues

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ApplicationVerificationTest {
    
    // Test configuration
    private static final boolean VERBOSE_OUTPUT = true;
    private static final boolean SAVE_TEST_IMAGES = false;
    
    // Test results tracking
    private static List<String> passedTests = new ArrayList<>();
    private static List<String> failedTests = new ArrayList<>();
    private static List<String> warnings = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("=== DOT PIN MARKER APPLICATION VERIFICATION TEST ===");
        System.out.println("Testing all major components and functionality...\n");
        
        try {
            // Test 1: Mark Creation and Basic Functionality
            testMarkCreation();
            
            // Test 2: Text Mark Enhancements
            testTextMarkEnhancements();
            
            // Test 3: Graph Mark Enhancements  
            testGraphMarkEnhancements();
            
            // Test 4: Drawing Canvas Functionality
            testDrawingCanvasFunctionality();
            
            // Test 5: Mark Interaction (Drag & Drop)
            testMarkInteraction();
            
            // Test 6: Error Handling and Edge Cases
            testErrorHandling();
            
            // Test 7: Memory and Performance
            testMemoryAndPerformance();
            
            // Test 8: Integration Tests
            testIntegration();
            
        } catch (Exception e) {
            failedTests.add("CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Print comprehensive test results
        printTestResults();
    }
    
    private static void testMarkCreation() {
        System.out.println(">>> Testing Mark Creation...");
        
        try {
            // Test TextMark creation
            TextMark textMark = new TextMark(50, 50, "Test Text");
            testAssert("TextMark creation", textMark != null && textMark.getText().equals("Test Text"));
            
            // Test GraphMark creation with different types
            GraphMark techFrame = new GraphMark(100, 100, GraphMark.GraphType.TECHNICAL_FRAME);
            testAssert("GraphMark TECHNICAL_FRAME creation", techFrame != null && techFrame.getGraphType() == GraphMark.GraphType.TECHNICAL_FRAME);
            
            GraphMark gridPattern = new GraphMark(150, 150, GraphMark.GraphType.GRID_PATTERN);
            testAssert("GraphMark GRID_PATTERN creation", gridPattern != null && gridPattern.getGraphType() == GraphMark.GraphType.GRID_PATTERN);
            
            // Test LineMark creation
            LineMark lineMark = new LineMark(200, 200);
            testAssert("LineMark creation", lineMark != null);
            
            // Test RectangleMark creation
            RectangleMark rectMark = new RectangleMark(250, 250);
            testAssert("RectangleMark creation", rectMark != null);
            
            // Test ArcLettersMark creation
            ArcLettersMark arcMark = new ArcLettersMark(300, 300, "ABCDE");
            testAssert("ArcLettersMark creation", arcMark != null);
            
            passedTests.add("Mark Creation Tests");
            
        } catch (Exception e) {
            failedTests.add("Mark Creation: " + e.getMessage());
        }
    }
    
    private static void testTextMarkEnhancements() {
        System.out.println(">>> Testing TextMark Enhancements...");
        
        try {
            TextMark textMark = new TextMark(100, 100, "Test Text");
            
            // Test width and spacing configuration (soft coding)
            textMark.setCharacterWidth(1.5);
            testAssert("TextMark character width setting", textMark.getCharacterWidth() == 1.5);
            
            textMark.setLineSpacing(1.2);
            testAssert("TextMark line spacing setting", textMark.getLineSpacing() == 1.2);
            
            // Test dynamic updates
            textMark.updateContent("New Text");
            testAssert("TextMark content update", textMark.getText().equals("New Text"));
            
            // Test font configuration
            Font newFont = new Font("Arial", Font.BOLD, 14);
            textMark.setFont(newFont);
            testAssert("TextMark font configuration", textMark.getFont().getName().equals("Arial"));
            
            passedTests.add("TextMark Enhancement Tests");
            
        } catch (Exception e) {
            failedTests.add("TextMark Enhancements: " + e.getMessage());
        }
    }
    
    private static void testGraphMarkEnhancements() {
        System.out.println(">>> Testing GraphMark Enhancements...");
        
        try {
            // Test all graph types
            GraphMark.GraphType[] types = GraphMark.getAvailableGraphTypes();
            testAssert("GraphMark available types count", types.length == 4);
            
            for (GraphMark.GraphType type : types) {
                GraphMark mark = new GraphMark(100, 100, type);
                String description = GraphMark.getGraphTypeDescription(type);
                testAssert("GraphMark type " + type + " description", description != null && !description.isEmpty());
                
                // Test configuration
                mark.setGridSpacing(20);
                testAssert("GraphMark grid spacing", mark.getGridSpacing() >= 5); // Should enforce minimum
                
                mark.setScaleInterval(15);
                testAssert("GraphMark scale interval", mark.getScaleInterval() >= 5); // Should enforce minimum
                
                mark.setBorderColor(Color.BLUE);
                testAssert("GraphMark border color", mark.getBorderColor().equals(Color.BLUE));
            }
            
            // Test dynamic type switching
            GraphMark dynamicMark = new GraphMark(200, 200, GraphMark.GraphType.TECHNICAL_FRAME);
            dynamicMark.setGraphType(GraphMark.GraphType.GRID_PATTERN);
            testAssert("GraphMark dynamic type change", dynamicMark.getGraphType() == GraphMark.GraphType.GRID_PATTERN);
            
            passedTests.add("GraphMark Enhancement Tests");
            
        } catch (Exception e) {
            failedTests.add("GraphMark Enhancements: " + e.getMessage());
        }
    }
    
    private static void testDrawingCanvasFunctionality() {
        System.out.println(">>> Testing Drawing Canvas Functionality...");
        
        try {
            // Create a mock graphics context for testing
            BufferedImage testImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = testImage.createGraphics();
            
            // Test mark rendering
            TextMark textMark = new TextMark(100, 100, "Test");
            textMark.draw(g2d, false);
            textMark.draw(g2d, true); // Test selection rendering
            
            GraphMark graphMark = new GraphMark(200, 200, GraphMark.GraphType.GRID_PATTERN);
            graphMark.width = 150;
            graphMark.height = 100;
            graphMark.draw(g2d, false);
            graphMark.draw(g2d, true); // Test selection rendering
            
            g2d.dispose();
            
            passedTests.add("Drawing Canvas Functionality Tests");
            
        } catch (Exception e) {
            failedTests.add("Drawing Canvas Functionality: " + e.getMessage());
        }
    }
    
    private static void testMarkInteraction() {
        System.out.println(">>> Testing Mark Interaction (Drag & Drop)...");
        
        try {
            TextMark textMark = new TextMark(100, 100, "Draggable");
            
            // Test hit detection
            boolean hit = textMark.contains(110, 110);
            testAssert("Mark hit detection", hit);
            
            // Test drag capability
            textMark.setCanDrag(true);
            testAssert("Mark drag capability setting", textMark.canDrag());
            
            // Test drag operation
            textMark.startDrag(100, 100);
            textMark.dragTo(150, 150);
            testAssert("Mark drag operation", textMark.x == 150 && textMark.y == 150);
            textMark.stopDrag();
            
            // Test resize capability
            textMark.setCanResize(true);
            testAssert("Mark resize capability setting", textMark.canResize());
            
            passedTests.add("Mark Interaction Tests");
            
        } catch (Exception e) {
            failedTests.add("Mark Interaction: " + e.getMessage());
        }
    }
    
    private static void testErrorHandling() {
        System.out.println(">>> Testing Error Handling and Edge Cases...");
        
        try {
            // Test null content handling
            TextMark nullContentMark = new TextMark(100, 100, null);
            testAssert("Null content handling", nullContentMark.getText() != null);
            
            // Test empty content handling
            TextMark emptyContentMark = new TextMark(100, 100, "");
            testAssert("Empty content handling", emptyContentMark.getText() != null);
            
            // Test invalid dimensions
            GraphMark invalidMark = new GraphMark(100, 100, GraphMark.GraphType.GRID_PATTERN);
            invalidMark.setGridSpacing(1); // Too small, should be corrected
            testAssert("Invalid grid spacing correction", invalidMark.getGridSpacing() >= 5);
            
            invalidMark.setScaleInterval(2); // Too small, should be corrected
            testAssert("Invalid scale interval correction", invalidMark.getScaleInterval() >= 5);
            
            passedTests.add("Error Handling Tests");
            
        } catch (Exception e) {
            failedTests.add("Error Handling: " + e.getMessage());
        }
    }
    
    private static void testMemoryAndPerformance() {
        System.out.println(">>> Testing Memory and Performance...");
        
        try {
            long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            
            // Create many marks to test memory usage
            List<Mark> testMarks = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                testMarks.add(new TextMark(i * 10, i * 10, "Test " + i));
                testMarks.add(new GraphMark(i * 10 + 5, i * 10 + 5, GraphMark.GraphType.TECHNICAL_FRAME));
            }
            
            long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long memoryUsed = endMemory - startMemory;
            
            // Test rendering performance
            BufferedImage perfTestImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = perfTestImage.createGraphics();
            
            long startTime = System.currentTimeMillis();
            for (Mark mark : testMarks) {
                mark.draw(g2d, false);
            }
            long endTime = System.currentTimeMillis();
            long renderTime = endTime - startTime;
            
            g2d.dispose();
            
            testAssert("Memory usage reasonable", memoryUsed < 50 * 1024 * 1024); // Less than 50MB
            testAssert("Rendering performance acceptable", renderTime < 1000); // Less than 1 second
            
            if (VERBOSE_OUTPUT) {
                System.out.println("  Memory used: " + (memoryUsed / 1024) + " KB");
                System.out.println("  Render time: " + renderTime + " ms");
            }
            
            passedTests.add("Memory and Performance Tests");
            
        } catch (Exception e) {
            failedTests.add("Memory and Performance: " + e.getMessage());
        }
    }
    
    private static void testIntegration() {
        System.out.println(">>> Testing Integration...");
        
        try {
            // Test mark integration with canvas operations
            List<Mark> marks = new ArrayList<>();
            
            // Add various mark types
            marks.add(new TextMark(100, 100, "Integration Test"));
            marks.add(new GraphMark(200, 200, GraphMark.GraphType.GRID_PATTERN));
            marks.add(new LineMark(300, 300));
            marks.add(new RectangleMark(400, 400));
            
            // Test rendering all marks together
            BufferedImage integrationImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = integrationImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 800, 600);
            
            for (Mark mark : marks) {
                mark.draw(g2d, false);
            }
            
            g2d.dispose();
            
            // Test mark selection and interaction
            Mark selectedMark = marks.get(0);
            selectedMark.setCanDrag(true);
            selectedMark.setCanResize(true);
            
            testAssert("Integration mark configuration", selectedMark.canDrag() && selectedMark.canResize());
            
            passedTests.add("Integration Tests");
            
        } catch (Exception e) {
            failedTests.add("Integration: " + e.getMessage());
        }
    }
    
    private static void testAssert(String testName, boolean condition) {
        if (condition) {
            if (VERBOSE_OUTPUT) {
                System.out.println("  ✓ " + testName);
            }
        } else {
            failedTests.add(testName);
            System.err.println("  ✗ " + testName + " FAILED");
        }
    }
    
    private static void printTestResults() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("APPLICATION VERIFICATION TEST RESULTS");
        System.out.println("=".repeat(60));
        
        System.out.println("\n✅ PASSED TESTS (" + passedTests.size() + "):");
        for (String test : passedTests) {
            System.out.println("  ✓ " + test);
        }
        
        if (!failedTests.isEmpty()) {
            System.out.println("\n❌ FAILED TESTS (" + failedTests.size() + "):");
            for (String test : failedTests) {
                System.out.println("  ✗ " + test);
            }
        }
        
        if (!warnings.isEmpty()) {
            System.out.println("\n⚠️ WARNINGS (" + warnings.size() + "):");
            for (String warning : warnings) {
                System.out.println("  ⚠ " + warning);
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        
        int totalTests = passedTests.size() + failedTests.size();
        double successRate = totalTests > 0 ? (double) passedTests.size() / totalTests * 100 : 0;
        
        System.out.println("SUMMARY:");
        System.out.println("  Total Tests: " + totalTests);
        System.out.println("  Passed: " + passedTests.size());
        System.out.println("  Failed: " + failedTests.size());
        System.out.println("  Success Rate: " + String.format("%.1f%%", successRate));
        
        if (failedTests.isEmpty()) {
            System.out.println("\n🎉 ALL TESTS PASSED! Application is functioning correctly.");
        } else {
            System.out.println("\n⚠️ Some tests failed. Review the issues above for necessary fixes.");
        }
        
        System.out.println("=".repeat(60));
    }
}
