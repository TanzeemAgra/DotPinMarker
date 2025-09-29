import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Test Pointwise Coordinate System (0-10 Scale)
 * 
 * This test verifies the new coordinate system that:
 * - Uses 0-10 scale instead of pixel coordinates starting at 200
 * - Shows pointwise values within grid cells
 * - Tracks cursor coordinates in real-time using 0-10 scale
 * - Records X,Y values using soft coding technique
 */
public class TestPointwiseCoordinates extends JFrame {
    
    private DrawingCanvas drawingCanvas;
    private JLabel coordLabel;
    private JLabel gridInfoLabel;
    
    public TestPointwiseCoordinates() {
        super("Pointwise Coordinate System Test (0-10 Scale)");
        
        // Create the main drawing canvas
        drawingCanvas = new DrawingCanvas();
        
        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Add title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(40, 40, 40));
        JLabel titleLabel = new JLabel("🎯 POINTWISE COORDINATE SYSTEM TEST (0-10 SCALE)");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Add canvas
        add(drawingCanvas, BorderLayout.CENTER);
        
        // Add coordinate display panel
        JPanel coordPanel = createCoordinatePanel();
        add(coordPanel, BorderLayout.SOUTH);
        
        // Add mouse tracking to canvas
        addMouseTracking();
        
        // Perform tests
        performCoordinateTests();
        
        // Set window properties
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createCoordinatePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(60, 60, 60));
        
        // Real-time coordinate display
        coordLabel = new JLabel("Move mouse over canvas to see pointwise coordinates (0-10 scale)");
        coordLabel.setForeground(Color.WHITE);
        coordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Grid info display
        gridInfoLabel = new JLabel("Grid cells should show pointwise coordinate values");
        gridInfoLabel.setForeground(Color.CYAN);
        gridInfoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        
        panel.add(coordLabel);
        panel.add(new JLabel("  |  "));
        JLabel separator = new JLabel("  |  ");
        separator.setForeground(Color.WHITE);
        panel.add(separator);
        panel.add(gridInfoLabel);
        
        return panel;
    }
    
    private void addMouseTracking() {
        drawingCanvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Get pixel coordinates
                int pixelX = e.getX();
                int pixelY = e.getY();
                
                // Convert to pointwise coordinates using DrawingCanvas method
                double[] pointwise = drawingCanvas.pixelToPointwise(pixelX, pixelY);
                double pointwiseX = pointwise[0];
                double pointwiseY = pointwise[1];
                
                // Update display
                String coordText = String.format("Pixel: (%d,%d) → Pointwise: (%.1f,%.1f)", 
                                                pixelX, pixelY, pointwiseX, pointwiseY);
                coordLabel.setText(coordText);
                
                // Get grid cell information
                int[] gridCell = drawingCanvas.getGridCellFromPixel(pixelX, pixelY);
                String gridText = String.format("Grid Cell: [%d,%d]", gridCell[0], gridCell[1]);
                gridInfoLabel.setText(gridText);
                
                // Log coordinates occasionally
                if (RugrelDropdownConfig.LOG_COORDINATE_CHANGES && Math.random() < 0.01) {
                    System.out.println("🎯 Real-time Coordinates: Pixel(" + pixelX + "," + pixelY + ") → " +
                                     "Pointwise(" + String.format("%.1f", pointwiseX) + "," + 
                                     String.format("%.1f", pointwiseY) + ") → " +
                                     "Cell[" + gridCell[0] + "," + gridCell[1] + "]");
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e); // Same behavior for dragging
            }
        });
    }
    
    private void performCoordinateTests() {
        System.out.println("=====================================");
        System.out.println("🎯 Pointwise Coordinate System Test Started");
        System.out.println("=====================================");
        
        // Test configuration status
        System.out.println("Configuration Status:");
        System.out.println("- ENABLE_POINTWISE_COORDINATES: " + RugrelDropdownConfig.ENABLE_POINTWISE_COORDINATES);
        System.out.println("- SHOW_GRID_COORDINATES: " + RugrelDropdownConfig.SHOW_GRID_COORDINATES);
        System.out.println("- ENABLE_COORDINATE_LABELS_IN_GRID: " + RugrelDropdownConfig.ENABLE_COORDINATE_LABELS_IN_GRID);
        System.out.println("- COORDINATE_X_MIN: " + RugrelDropdownConfig.COORDINATE_X_MIN);
        System.out.println("- COORDINATE_X_MAX: " + RugrelDropdownConfig.COORDINATE_X_MAX);
        System.out.println("- COORDINATE_Y_MIN: " + RugrelDropdownConfig.COORDINATE_Y_MIN);
        System.out.println("- COORDINATE_Y_MAX: " + RugrelDropdownConfig.COORDINATE_Y_MAX);
        System.out.println("- POINTWISE_COORDINATE_FORMAT: " + RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT);
        System.out.println("=====================================");
        
        // Test coordinate transformation
        System.out.println("Testing Coordinate Transformations:");
        
        // Test corner coordinates
        testCoordinateTransformation(100, 100, "Top-Left Area");
        testCoordinateTransformation(500, 300, "Center Area");  
        testCoordinateTransformation(800, 500, "Bottom-Right Area");
        
        System.out.println("=====================================");
        System.out.println("Expected Results:");
        System.out.println("1. Grid cells should show coordinate values (0.0-10.0 range)");
        System.out.println("2. Mouse movement should display real-time pointwise coordinates");
        System.out.println("3. Console should show coordinate conversions");
        System.out.println("4. Property strip should show 0-10 scale coordinates");
        System.out.println("5. Coordinates should start from 0 instead of ~200");
        System.out.println("=====================================");
    }
    
    private void testCoordinateTransformation(int pixelX, int pixelY, String location) {
        double[] pointwise = drawingCanvas.pixelToPointwise(pixelX, pixelY);
        int[] backToPixel = drawingCanvas.pointwiseToPixel(pointwise[0], pointwise[1]);
        int[] gridCell = drawingCanvas.getGridCellFromPixel(pixelX, pixelY);
        
        System.out.printf("🧪 %s: Pixel(%d,%d) → Pointwise(%.1f,%.1f) → BackPixel(%d,%d) → Cell[%d,%d]%n",
                         location, pixelX, pixelY, pointwise[0], pointwise[1], 
                         backToPixel[0], backToPixel[1], gridCell[0], gridCell[1]);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TestPointwiseCoordinates();
        });
    }
}