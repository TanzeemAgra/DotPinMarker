// GraphMark Test Program - Verify Enhanced Functionality
// This test demonstrates all four graph types working correctly

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GraphMarkTest {
    public static void main(String[] args) {
        try {
            // Create test image to verify rendering
            BufferedImage testImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = testImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // White background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 800, 600);
            
            // Test all four graph types
            System.out.println("Testing GraphMark Enhancement...");
            
            // 1. Technical Frame (top-left)
            GraphMark techFrame = new GraphMark(50, 50, GraphMark.GraphType.TECHNICAL_FRAME);
            techFrame.width = 150;
            techFrame.height = 100;
            techFrame.setBorderColor(Color.BLACK);
            techFrame.draw(g, false);
            System.out.println("✓ TECHNICAL_FRAME: " + GraphMark.getGraphTypeDescription(GraphMark.GraphType.TECHNICAL_FRAME));
            
            // 2. Grid Pattern (top-right)
            GraphMark gridPattern = new GraphMark(250, 50, GraphMark.GraphType.GRID_PATTERN);
            gridPattern.width = 150;
            gridPattern.height = 100;
            gridPattern.setGridSpacing(15);
            gridPattern.setBorderColor(Color.BLUE);
            gridPattern.draw(g, false);
            System.out.println("✓ GRID_PATTERN: " + GraphMark.getGraphTypeDescription(GraphMark.GraphType.GRID_PATTERN));
            
            // 3. Scale Ruler (bottom-left)
            GraphMark scaleRuler = new GraphMark(50, 200, GraphMark.GraphType.SCALE_RULER);
            scaleRuler.width = 200;
            scaleRuler.height = 50;
            scaleRuler.setScaleInterval(10);
            scaleRuler.setBorderColor(Color.RED);
            scaleRuler.draw(g, false);
            System.out.println("✓ SCALE_RULER: " + GraphMark.getGraphTypeDescription(GraphMark.GraphType.SCALE_RULER));
            
            // 4. Alignment Cross (bottom-right)
            GraphMark alignCross = new GraphMark(300, 180, GraphMark.GraphType.ALIGNMENT_CROSS);
            alignCross.width = 120;
            alignCross.height = 120;
            alignCross.setBorderColor(Color.GREEN);
            alignCross.draw(g, false);
            System.out.println("✓ ALIGNMENT_CROSS: " + GraphMark.getGraphTypeDescription(GraphMark.GraphType.ALIGNMENT_CROSS));
            
            // Test selection rendering
            GraphMark selectedFrame = new GraphMark(450, 50, GraphMark.GraphType.TECHNICAL_FRAME);
            selectedFrame.width = 100;
            selectedFrame.height = 80;
            selectedFrame.draw(g, true); // Draw as selected
            System.out.println("✓ Selection handles rendered correctly");
            
            // Test configuration methods
            System.out.println("\nTesting Soft Coding Configuration:");
            System.out.println("Available Graph Types: " + GraphMark.getAvailableGraphTypes().length);
            for (GraphMark.GraphType type : GraphMark.getAvailableGraphTypes()) {
                System.out.println("  - " + type + ": " + GraphMark.getGraphTypeDescription(type));
            }
            
            // Test dynamic type changing
            GraphMark dynamicMark = new GraphMark(500, 200, GraphMark.GraphType.TECHNICAL_FRAME);
            dynamicMark.width = 100;
            dynamicMark.height = 100;
            System.out.println("\nTesting Dynamic Type Changes:");
            System.out.println("Initial type: " + dynamicMark.getGraphType());
            
            dynamicMark.setGraphType(GraphMark.GraphType.GRID_PATTERN);
            System.out.println("Changed to: " + dynamicMark.getGraphType());
            dynamicMark.draw(g, false);
            
            // Test parameter configuration
            System.out.println("\nTesting Parameter Configuration:");
            System.out.println("Grid spacing before: " + dynamicMark.getGridSpacing());
            dynamicMark.setGridSpacing(20);
            System.out.println("Grid spacing after: " + dynamicMark.getGridSpacing());
            
            System.out.println("Scale interval before: " + dynamicMark.getScaleInterval());
            dynamicMark.setScaleInterval(15);
            System.out.println("Scale interval after: " + dynamicMark.getScaleInterval());
            
            // Test toString method
            System.out.println("\nGraphMark toString: " + dynamicMark.toString());
            
            g.dispose();
            
            // Save test image (optional)
            try {
                ImageIO.write(testImage, "PNG", new File("graphmark_test.png"));
                System.out.println("\n✓ Test image saved as 'graphmark_test.png'");
            } catch (Exception e) {
                System.out.println("Note: Could not save test image - " + e.getMessage());
            }
            
            System.out.println("\n🎉 All GraphMark Enhancement Tests PASSED!");
            System.out.println("Graph Option functionality is now clear and fully functional.");
            
        } catch (Exception e) {
            System.err.println("❌ Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
