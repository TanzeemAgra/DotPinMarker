import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.geom.*;

/**
 * IconGenerator - Creates professional custom icons for Mark Types
 * Inspired by Text.PNG reference for consistent design language
 */
public class IconGenerator {
    private static final int ICON_SIZE = 32;
    private static final Color ICON_COLOR = new Color(60, 60, 60);
    private static final Color ACCENT_COLOR = new Color(0, 120, 215);
    private static final BasicStroke THICK_STROKE = new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke THIN_STROKE = new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    
    public static void main(String[] args) {
        try {
            System.out.println("🎨 Generating custom mark type icons...");
            
            // Create images directory if it doesn't exist
            File imagesDir = new File("images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            
            // Generate all mark type icons
            generateTextIcon();
            generateLineIcon();
            generateCircleIcon();
            generateRectangleIcon();
            generateArcIcon();
            generateDotMatrixIcon();
            generateBarcodeIcon();
            generateGraphIcon();
            generateFarziIcon();
            generateBowTextIcon();
            
            System.out.println("✅ All icons generated successfully in images/ folder!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static BufferedImage createIconBase() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        
        // Enable anti-aliasing for smooth graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Clear background (transparent)
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
        g2.setComposite(AlphaComposite.SrcOver);
        
        return image;
    }
    
    private static void saveIcon(BufferedImage image, String filename) throws Exception {
        File outputFile = new File("images/" + filename);
        ImageIO.write(image, "PNG", outputFile);
        System.out.println("  📁 Created: " + filename);
    }
    
    // Text Icon - Inspired by Text.PNG reference
    private static void generateTextIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw stylized "T" with text formatting elements
        g2.setColor(ICON_COLOR);
        g2.setStroke(THICK_STROKE);
        
        // Main "T" shape
        g2.drawLine(8, 8, 24, 8);   // Top horizontal line
        g2.drawLine(16, 8, 16, 24); // Vertical line
        
        // Add formatting indicators (underline, bold marks)
        g2.setStroke(THIN_STROKE);
        g2.setColor(ACCENT_COLOR);
        g2.drawLine(12, 26, 20, 26); // Underline indicator
        
        // Small "A" to indicate text
        Font font = new Font("Arial", Font.BOLD, 8);
        g2.setFont(font);
        g2.setColor(ICON_COLOR);
        g2.drawString("A", 20, 22);
        
        g2.dispose();
        saveIcon(image, "text.png");
    }
    
    // Line Icon - Clean straight line
    private static void generateLineIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        g2.setStroke(THICK_STROKE);
        g2.drawLine(6, 16, 26, 16);
        
        // Add small handles at ends
        g2.fillOval(4, 14, 4, 4);
        g2.fillOval(24, 14, 4, 4);
        
        g2.dispose();
        saveIcon(image, "line.png");
    }
    
    // Circle Icon - Perfect circle
    private static void generateCircleIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        g2.setStroke(THICK_STROKE);
        g2.drawOval(8, 8, 16, 16);
        
        g2.dispose();
        saveIcon(image, "circle.png");
    }
    
    // Rectangle Icon - Clean rectangle
    private static void generateRectangleIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        g2.setStroke(THICK_STROKE);
        g2.drawRect(7, 10, 18, 12);
        
        g2.dispose();
        saveIcon(image, "rectangle.png");
    }
    
    // Arc Icon - Curved arc
    private static void generateArcIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        g2.setStroke(THICK_STROKE);
        g2.drawArc(8, 8, 16, 16, 45, 180);
        
        g2.dispose();
        saveIcon(image, "arc.png");
    }
    
    // Dot Matrix Icon - Grid of dots
    private static void generateDotMatrixIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        
        // Create 4x4 dot matrix
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = 8 + i * 5;
                int y = 8 + j * 5;
                g2.fillOval(x, y, 3, 3);
            }
        }
        
        g2.dispose();
        saveIcon(image, "dot_matrix.png");
    }
    
    // Barcode Icon - Vertical lines of varying thickness
    private static void generateBarcodeIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        
        // Create barcode pattern
        int[] widths = {2, 1, 3, 1, 2, 1, 3, 2};
        int x = 6;
        for (int width : widths) {
            g2.fillRect(x, 8, width, 16);
            x += width + 1;
        }
        
        g2.dispose();
        saveIcon(image, "barcode.png");
    }
    
    // Graph Icon - Simple bar chart
    private static void generateGraphIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        
        // Draw axes
        g2.setStroke(THIN_STROKE);
        g2.drawLine(8, 24, 24, 24); // X-axis
        g2.drawLine(8, 8, 8, 24);   // Y-axis
        
        // Draw bars
        g2.setColor(ACCENT_COLOR);
        g2.fillRect(10, 18, 3, 6);  // Bar 1
        g2.fillRect(14, 14, 3, 10); // Bar 2
        g2.fillRect(18, 20, 3, 4);  // Bar 3
        g2.fillRect(22, 16, 3, 8);  // Bar 4
        
        g2.dispose();
        saveIcon(image, "graph.png");
    }
    
    // Farzi Icon - Sparkle/star pattern
    private static void generateFarziIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ACCENT_COLOR);
        g2.setStroke(THICK_STROKE);
        
        // Draw star/sparkle pattern
        g2.drawLine(16, 6, 16, 26);   // Vertical
        g2.drawLine(6, 16, 26, 16);   // Horizontal
        g2.drawLine(10, 10, 22, 22);  // Diagonal 1
        g2.drawLine(22, 10, 10, 22);  // Diagonal 2
        
        // Add center dot
        g2.setColor(ICON_COLOR);
        g2.fillOval(14, 14, 4, 4);
        
        g2.dispose();
        saveIcon(image, "farzi.png");
    }
    
    // Bow Text Icon - Curved text path
    private static void generateBowTextIcon() throws Exception {
        BufferedImage image = createIconBase();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(ICON_COLOR);
        g2.setStroke(THICK_STROKE);
        
        // Draw curved path
        QuadCurve2D curve = new QuadCurve2D.Float(6, 20, 16, 8, 26, 20);
        g2.draw(curve);
        
        // Add small text indicators along the curve
        g2.setColor(ACCENT_COLOR);
        Font font = new Font("Arial", Font.BOLD, 6);
        g2.setFont(font);
        g2.drawString("A", 8, 18);
        g2.drawString("B", 15, 12);
        g2.drawString("C", 22, 18);
        
        g2.dispose();
        saveIcon(image, "bow_text.png");
    }
}