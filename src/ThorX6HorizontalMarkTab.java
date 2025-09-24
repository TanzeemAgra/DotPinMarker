import javax.swing.*;
import java.awt.*;

/**
 * ThorX6 Horizontal Mark Tab Panel
 * Exact copy of ThorX6 software with horizontal layout like Typeset tab
 */
public class ThorX6HorizontalMarkTab extends JPanel {
    
    private DrawingCanvas drawingCanvas;
    
    public ThorX6HorizontalMarkTab(DrawingCanvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        System.out.println("🎯 Creating ThorX6 Horizontal Mark Tab (exact ThorX6 copy)...");
        ThorX6HorizontalConfig.printThorX6ConfigSummary();
        
        // Create ThorX6-style horizontal layout
        createThorX6HorizontalInterface();
        
        System.out.println("✅ ThorX6 Horizontal Mark Tab created successfully!");
    }
    
    /**
     * Create complete ThorX6 horizontal interface matching real ThorX6 software
     */
    private void createThorX6HorizontalInterface() {
        // Top: ThorX6 horizontal toolbar (like Typeset tab reference)
        JPanel thorX6Toolbar = ThorX6HorizontalConfig.createThorX6HorizontalToolbar();
        add(thorX6Toolbar, BorderLayout.NORTH);
        
        // Center: Canvas area with ThorX6 styling
        JPanel canvasArea = createThorX6CanvasArea();
        add(canvasArea, BorderLayout.CENTER);
        
        // Bottom: Properties panel (soft-coded selective removal - keep bottom strip, remove upper duplicate)
        if (!PropertyPanelControlConfig.shouldDisableMarkTabProperties()) {
            JPanel propertiesPanel = ThorX6HorizontalConfig.createThorX6PropertiesPanel();
            add(propertiesPanel, BorderLayout.SOUTH);
        } else {
            // Upper Mark tab properties disabled - bottom property strip will remain active
            if (PropertyPanelControlConfig.ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🗑️ Mark Tab Properties Panel skipped - keeping bottom property strip only");
            }
        }
        
        System.out.println("🎯 ThorX6 horizontal interface layout complete");
    }
    
    /**
     * Create ThorX6-style canvas area
     */
    private JPanel createThorX6CanvasArea() {
        JPanel canvasArea = new JPanel(new BorderLayout());
        canvasArea.setBackground(Color.WHITE);
        canvasArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThorX6HorizontalConfig.THORX6_BORDER, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Add drawing canvas
        if (drawingCanvas != null) {
            JScrollPane scrollPane = new JScrollPane(drawingCanvas);
            scrollPane.setBackground(Color.WHITE);
            scrollPane.setBorder(null);
            canvasArea.add(scrollPane, BorderLayout.CENTER);
        } else {
            // Placeholder if no canvas
            JLabel placeholder = new JLabel("ThorX6 Drawing Canvas", SwingConstants.CENTER);
            placeholder.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            placeholder.setForeground(ThorX6HorizontalConfig.THORX6_TEXT);
            canvasArea.add(placeholder, BorderLayout.CENTER);
        }
        
        return canvasArea;
    }
    
    /**
     * Get the drawing canvas
     */
    public DrawingCanvas getDrawingCanvas() {
        return drawingCanvas;
    }
    
    /**
     * Set the drawing canvas
     */
    public void setDrawingCanvas(DrawingCanvas canvas) {
        this.drawingCanvas = canvas;
    }
}