import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class IconGenerator {
    private static final int ICON_SIZE = 24;
    private static final Color PRIMARY_COLOR = new Color(44, 123, 229); // Professional blue
    private static final Color SECONDARY_COLOR = new Color(76, 76, 76); // Dark gray
    private static final Color ACCENT_COLOR = new Color(255, 193, 7); // Amber accent
    
    public static void main(String[] args) {
        try {
            File imagesDir = new File("images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            
            System.out.println("🎨 Generating professional icons...");
            
            // Generate all mark type icons
            generateDataMatrixIcon();
            generateGraphIcon();
            generateChartIcon();
            generateRulerIcon();
            generateAvoidPointIcon();
            generateFarsiIcon();
            
            System.out.println("✅ All professional icons generated successfully!");
            System.out.println("📁 Icons saved in: " + imagesDir.getAbsolutePath());
            
        } catch (Exception e) {
            System.err.println("❌ Error generating icons: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void generateDataMatrixIcon() throws Exception {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        setupGraphics(g2d);
        
        // Create DataMatrix pattern - square grid with finder patterns
        g2d.setColor(PRIMARY_COLOR);
        
        // Main border
        g2d.drawRect(2, 2, ICON_SIZE-5, ICON_SIZE-5);
        
        // DataMatrix dot pattern
        for (int x = 4; x < ICON_SIZE-3; x += 3) {
            for (int y = 4; y < ICON_SIZE-3; y += 3) {
                if ((x + y) % 6 == 0) {
                    g2d.fillRect(x, y, 2, 2);
                }
            }
        }
        
        // Finder patterns (L-shapes in corners)
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine(2, 2, 2, 8);
        g2d.drawLine(2, 2, 8, 2);
        g2d.drawLine(ICON_SIZE-3, ICON_SIZE-9, ICON_SIZE-3, ICON_SIZE-3);
        g2d.drawLine(ICON_SIZE-9, ICON_SIZE-3, ICON_SIZE-3, ICON_SIZE-3);
        
        g2d.dispose();
        ImageIO.write(img, "PNG", new File("images/datamatrix.png"));
        System.out.println("✓ DataMatrix icon created");
    }
    
    private static void generateGraphIcon() throws Exception {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        setupGraphics(g2d);
        
        g2d.setColor(PRIMARY_COLOR);
        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Draw coordinate axes
        g2d.drawLine(3, ICON_SIZE-3, ICON_SIZE-3, ICON_SIZE-3); // X-axis
        g2d.drawLine(3, ICON_SIZE-3, 3, 3); // Y-axis
        
        // Draw graph line with points
        int[] xPoints = {6, 9, 12, 15, 18};
        int[] yPoints = {18, 10, 14, 7, 9};
        
        g2d.setColor(ACCENT_COLOR);
        g2d.setStroke(new BasicStroke(2f));
        
        // Connect points with lines
        for (int i = 0; i < xPoints.length - 1; i++) {
            g2d.drawLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
        }
        
        // Draw data points
        g2d.setColor(PRIMARY_COLOR);
        for (int i = 0; i < xPoints.length; i++) {
            g2d.fillOval(xPoints[i]-2, yPoints[i]-2, 4, 4);
        }
        
        g2d.dispose();
        ImageIO.write(img, "PNG", new File("images/graph.png"));
        System.out.println("✓ Graph icon created");
    }
    
    private static void generateChartIcon() throws Exception {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        setupGraphics(g2d);
        
        // Create pie chart
        int centerX = ICON_SIZE / 2;
        int centerY = ICON_SIZE / 2;
        int radius = 8;
        
        // Pie slices with different colors
        g2d.setColor(PRIMARY_COLOR);
        g2d.fillArc(centerX-radius, centerY-radius, radius*2, radius*2, 0, 120);
        
        g2d.setColor(ACCENT_COLOR);
        g2d.fillArc(centerX-radius, centerY-radius, radius*2, radius*2, 120, 140);
        
        g2d.setColor(SECONDARY_COLOR);
        g2d.fillArc(centerX-radius, centerY-radius, radius*2, radius*2, 260, 100);
        
        // Add border
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawOval(centerX-radius, centerY-radius, radius*2, radius*2);
        
        g2d.dispose();
        ImageIO.write(img, "PNG", new File("images/chart.png"));
        System.out.println("✓ Chart icon created");
    }
    
    private static void generateRulerIcon() throws Exception {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        setupGraphics(g2d);
        
        g2d.setColor(PRIMARY_COLOR);
        g2d.setStroke(new BasicStroke(2f));
        
        // Main ruler line (diagonal)
        g2d.drawLine(3, ICON_SIZE-3, ICON_SIZE-3, 3);
        
        // Measurement marks
        g2d.setStroke(new BasicStroke(1f));
        for (int i = 0; i < 6; i++) {
            int x = 3 + (i * 3);
            int y = ICON_SIZE - 3 - (i * 3);
            
            if (i % 2 == 0) {
                // Long marks
                g2d.drawLine(x-2, y+2, x+2, y-2);
            } else {
                // Short marks
                g2d.drawLine(x-1, y+1, x+1, y-1);
            }
        }
        
        // Add measurement numbers
        g2d.setFont(new Font("Arial", Font.BOLD, 6));
        g2d.setColor(SECONDARY_COLOR);
        g2d.drawString("0", 1, ICON_SIZE-1);
        g2d.drawString("10", ICON_SIZE-12, 8);
        
        g2d.dispose();
        ImageIO.write(img, "PNG", new File("images/ruler.png"));
        System.out.println("✓ Ruler icon created");
    }
    
    private static void generateAvoidPointIcon() throws Exception {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        setupGraphics(g2d);
        
        // Create warning/avoid symbol
        int centerX = ICON_SIZE / 2;
        int centerY = ICON_SIZE / 2;
        
        // Draw warning triangle
        g2d.setColor(new Color(255, 87, 51)); // Warning red-orange
        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        int[] xTriangle = {centerX, centerX-7, centerX+7};
        int[] yTriangle = {4, ICON_SIZE-4, ICON_SIZE-4};
        g2d.fillPolygon(xTriangle, yTriangle, 3);
        
        // Add exclamation mark
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(centerX, 8, centerX, 14);
        g2d.fillOval(centerX-1, 16, 2, 2);
        
        // Add "avoid" arrows pointing away
        g2d.setColor(SECONDARY_COLOR);
        g2d.setStroke(new BasicStroke(1f));
        
        // Left arrow
        g2d.drawLine(4, centerY, 1, centerY);
        g2d.drawLine(1, centerY, 3, centerY-2);
        g2d.drawLine(1, centerY, 3, centerY+2);
        
        // Right arrow
        g2d.drawLine(ICON_SIZE-4, centerY, ICON_SIZE-1, centerY);
        g2d.drawLine(ICON_SIZE-1, centerY, ICON_SIZE-3, centerY-2);
        g2d.drawLine(ICON_SIZE-1, centerY, ICON_SIZE-3, centerY+2);
        
        g2d.dispose();
        ImageIO.write(img, "PNG", new File("images/avoidpoint.png"));
        System.out.println("✓ AvoidPoint icon created");
    }
    
    private static void generateFarsiIcon() throws Exception {
        BufferedImage img = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        setupGraphics(g2d);
        
        g2d.setColor(PRIMARY_COLOR);
        g2d.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        
        // Draw Farsi/Persian text symbol
        // Using Persian letter "ف" (Feh) which represents Farsi
        String farsiChar = "ف";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (ICON_SIZE - fm.stringWidth(farsiChar)) / 2;
        int y = (ICON_SIZE + fm.getAscent()) / 2 - 2;
        
        g2d.drawString(farsiChar, x, y);
        
        // Add decorative border for text context
        g2d.setColor(SECONDARY_COLOR);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawRoundRect(2, 2, ICON_SIZE-5, ICON_SIZE-5, 4, 4);
        
        // Add small text indicator lines
        g2d.setColor(ACCENT_COLOR);
        g2d.drawLine(4, ICON_SIZE-5, 8, ICON_SIZE-5);
        g2d.drawLine(ICON_SIZE-8, 4, ICON_SIZE-4, 4);
        
        g2d.dispose();
        ImageIO.write(img, "PNG", new File("images/farsi.png"));
        System.out.println("✓ Farsi icon created");
    }
    
    private static void setupGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
}