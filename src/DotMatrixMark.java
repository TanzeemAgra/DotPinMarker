import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

public class DotMatrixMark extends Mark {
    private String data = "MFR123456";
    private int dotPitch = 8; // Distance between dot centers in pixels
    private int dotDiameter = 6; // Diameter of each dot in pixels
    private boolean[][] matrix; // The actual data matrix pattern
    private int matrixSize = 10; // Default 10x10 matrix
    private Color dotColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;
    private boolean showGrid = true;
    private boolean showBorder = true;
    private double scale = 1.0;
    
    // Data Matrix encoding tables (simplified for demonstration)
    private static final Map<Character, Integer> ASCII_ENCODING = new HashMap<>();
    static {
        // Initialize basic ASCII encoding
        for (int i = 0; i < 128; i++) {
            ASCII_ENCODING.put((char) i, i + 1);
        }
    }
    
    // Standard Data Matrix sizes (ECC 200)
    private static final int[][] MATRIX_SIZES = {
        {10, 10}, {12, 12}, {14, 14}, {16, 16}, {18, 18}, {20, 20},
        {22, 22}, {24, 24}, {26, 26}, {32, 32}, {36, 36}, {40, 40},
        {44, 44}, {48, 48}, {52, 52}, {64, 64}, {72, 72}, {80, 80},
        {88, 88}, {96, 96}, {104, 104}, {120, 120}, {132, 132}, {144, 144}
    };
    
    public DotMatrixMark(int x, int y) {
        super(x, y);
        this.width = 120;
        this.height = 120;
        generateMatrix();
    }
    
    public DotMatrixMark(int x, int y, String data) {
        super(x, y);
        this.data = data.toUpperCase();
        this.width = 120;
        this.height = 120;
        generateMatrix();
    }
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Enable antialiasing for smooth dots
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw background
        if (showBorder) {
            g.setColor(backgroundColor);
            g.fillRect(x, y, width, height);
            g.setColor(Color.LIGHT_GRAY);
            g.setStroke(new BasicStroke(1));
            g.drawRect(x, y, width, height);
        }
        
        // Calculate dot positioning
        int matrixPixelSize = Math.min(width - 20, height - 20);
        int startX = x + (width - matrixPixelSize) / 2;
        int startY = y + (height - matrixPixelSize) / 2;
        
        // Draw grid if enabled
        if (showGrid) {
            drawGrid(g, startX, startY, matrixPixelSize);
        }
        
        // Draw the data matrix dots
        drawDataMatrix(g, startX, startY, matrixPixelSize);
        
        // Draw border pattern (characteristic of Data Matrix)
        drawDataMatrixBorder(g, startX, startY, matrixPixelSize);
        
        // Draw selection indicators if selected
        if (isSelected) {
            drawSelectionIndicators(g);
        }
        
        // Draw data label
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g.getFontMetrics();
        String label = "Data: " + data;
        int labelWidth = fm.stringWidth(label);
        g.drawString(label, x + (width - labelWidth) / 2, y + height + 15);
    }
    
    private void drawGrid(Graphics2D g, int startX, int startY, int matrixPixelSize) {
        g.setColor(new Color(200, 200, 200, 100));
        g.setStroke(new BasicStroke(0.5f));
        
        double cellSize = (double) matrixPixelSize / matrixSize;
        
        // Draw vertical lines
        for (int i = 0; i <= matrixSize; i++) {
            int lineX = startX + (int) (i * cellSize);
            g.drawLine(lineX, startY, lineX, startY + matrixPixelSize);
        }
        
        // Draw horizontal lines
        for (int i = 0; i <= matrixSize; i++) {
            int lineY = startY + (int) (i * cellSize);
            g.drawLine(startX, lineY, startX + matrixPixelSize, lineY);
        }
    }
    
    private void drawDataMatrix(Graphics2D g, int startX, int startY, int matrixPixelSize) {
        if (matrix == null) return;
        
        double cellSize = (double) matrixPixelSize / matrixSize;
        double dotRadius = (cellSize * dotDiameter / dotPitch) / 2.0;
        
        g.setColor(dotColor);
        
        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                if (matrix[row][col]) {
                    double dotCenterX = startX + (col + 0.5) * cellSize;
                    double dotCenterY = startY + (row + 0.5) * cellSize;
                    
                    Ellipse2D.Double dot = new Ellipse2D.Double(
                        dotCenterX - dotRadius,
                        dotCenterY - dotRadius,
                        dotRadius * 2,
                        dotRadius * 2
                    );
                    g.fill(dot);
                }
            }
        }
    }
    
    private void drawDataMatrixBorder(Graphics2D g, int startX, int startY, int matrixPixelSize) {
        // Data Matrix has a characteristic border pattern
        g.setColor(dotColor);
        g.setStroke(new BasicStroke(2));
        
        double cellSize = (double) matrixPixelSize / matrixSize;
        
        // Left and bottom solid borders
        g.drawLine(startX, startY, startX, startY + matrixPixelSize);
        g.drawLine(startX, startY + matrixPixelSize, startX + matrixPixelSize, startY + matrixPixelSize);
        
        // Top and right dashed borders (timing pattern)
        g.setStroke(new BasicStroke(1));
        for (int i = 0; i < matrixSize; i += 2) {
            // Top border
            int segmentX = startX + (int) (i * cellSize);
            int segmentEndX = startX + (int) ((i + 1) * cellSize);
            g.drawLine(segmentX, startY, segmentEndX, startY);
            
            // Right border
            int segmentY = startY + (int) (i * cellSize);
            int segmentEndY = startY + (int) ((i + 1) * cellSize);
            g.drawLine(startX + matrixPixelSize, segmentY, startX + matrixPixelSize, segmentEndY);
        }
    }
    
    private void drawSelectionIndicators(Graphics2D g) {
        // Draw bounding box
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(2));
        g.drawRect(x - 2, y - 2, width + 4, height + 4);
        
        // Draw resize handle
        g.setColor(Color.RED);
        g.fillRect(x + width - HANDLE_SIZE, y + height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE);
        
        // Draw configuration handles
        g.setColor(Color.GREEN);
        g.fillOval(x + width + 5, y + 10, 8, 8); // Dot pitch control
        g.fillOval(x + width + 5, y + 25, 8, 8); // Dot diameter control
        g.fillOval(x + width + 5, y + 40, 8, 8); // Matrix size control
    }
    
    private void generateMatrix() {
        // Determine optimal matrix size based on data length
        matrixSize = calculateOptimalSize(data.length());
        
        // Initialize matrix
        matrix = new boolean[matrixSize][matrixSize];
        
        // Generate Reed-Solomon error correction and fill matrix
        generateDataMatrixPattern();
    }
    
    private int calculateOptimalSize(int dataLength) {
        // Simple heuristic - in real implementation, this would consider
        // error correction capacity and ECC 200 standard
        if (dataLength <= 6) return 10;
        if (dataLength <= 10) return 12;
        if (dataLength <= 16) return 14;
        if (dataLength <= 24) return 16;
        if (dataLength <= 36) return 18;
        return 20;
    }
    
    private void generateDataMatrixPattern() {
        // Simplified Data Matrix generation - real implementation would use
        // proper Reed-Solomon error correction and ECC 200 standard
        
        // Clear matrix
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = false;
            }
        }
        
        // Create a simple pattern based on data
        int hash = data.hashCode();
        Random random = new Random(hash);
        
        // Fill data region (avoiding border areas)
        for (int row = 1; row < matrixSize - 1; row += 2) {
            for (int col = 1; col < matrixSize - 1; col += 2) {
                // Create pattern based on data characters and position
                int charIndex = ((row - 1) / 2 + (col - 1) / 2) % data.length();
                int charValue = data.charAt(charIndex);
                
                // Use bit pattern of character
                matrix[row][col] = (charValue & (1 << (row % 8))) != 0;
                
                // Add some controlled randomness for realistic look
                if (random.nextDouble() < 0.3) {
                    matrix[row][col] = !matrix[row][col];
                }
            }
        }
        
        // Add corner and alignment patterns
        addCornerPatterns();
        addTimingPatterns();
    }
    
    private void addCornerPatterns() {
        // Add characteristic corner patterns of Data Matrix
        if (matrixSize >= 10) {
            // Bottom-left corner pattern
            matrix[matrixSize - 1][0] = true;
            matrix[matrixSize - 2][0] = true;
            matrix[matrixSize - 2][1] = true;
            
            // Top-right corner detection
            matrix[0][matrixSize - 1] = true;
            matrix[1][matrixSize - 1] = true;
            matrix[1][matrixSize - 2] = true;
        }
    }
    
    private void addTimingPatterns() {
        // Add timing patterns on borders
        // Left border (solid)
        for (int i = 0; i < matrixSize; i++) {
            matrix[i][0] = true;
        }
        
        // Bottom border (solid)
        for (int i = 0; i < matrixSize; i++) {
            matrix[matrixSize - 1][i] = true;
        }
        
        // Top border (alternating)
        for (int i = 0; i < matrixSize; i += 2) {
            matrix[0][i] = true;
        }
        
        // Right border (alternating)
        for (int i = 1; i < matrixSize; i += 2) {
            matrix[i][matrixSize - 1] = true;
        }
    }
    
    // Getters and setters
    public String getData() { return data; }
    public void setData(String data) { 
        this.data = data.toUpperCase(); 
        generateMatrix();
    }
    
    public int getDotPitch() { return dotPitch; }
    public void setDotPitch(int dotPitch) { 
        this.dotPitch = Math.max(4, Math.min(20, dotPitch)); 
    }
    
    public int getDotDiameter() { return dotDiameter; }
    public void setDotDiameter(int dotDiameter) { 
        this.dotDiameter = Math.max(2, Math.min(dotPitch - 1, dotDiameter)); 
    }
    
    public int getMatrixSize() { return matrixSize; }
    public void setMatrixSize(int size) {
        // Validate against standard sizes
        for (int[] standardSize : MATRIX_SIZES) {
            if (standardSize[0] == size) {
                this.matrixSize = size;
                generateMatrix();
                break;
            }
        }
    }
    
    public boolean isShowGrid() { return showGrid; }
    public void setShowGrid(boolean showGrid) { this.showGrid = showGrid; }
    
    public boolean isShowBorder() { return showBorder; }
    public void setShowBorder(boolean showBorder) { this.showBorder = showBorder; }
    
    // Tool path generation for dot pin controllers
    public List<DotPosition> generateDotPath() {
        List<DotPosition> dotPath = new ArrayList<>();
        
        if (matrix == null) return dotPath;
        
        // Calculate actual dot positions
        int matrixPixelSize = Math.min(width - 20, height - 20);
        int startX = x + (width - matrixPixelSize) / 2;
        int startY = y + (height - matrixPixelSize) / 2;
        double cellSize = (double) matrixPixelSize / matrixSize;
        
        // Generate optimized path (row-by-row for now, could be optimized)
        for (int row = 0; row < matrixSize; row++) {
            // Alternate direction for efficiency (serpentine path)
            if (row % 2 == 0) {
                // Left to right
                for (int col = 0; col < matrixSize; col++) {
                    if (matrix[row][col]) {
                        double dotX = startX + (col + 0.5) * cellSize;
                        double dotY = startY + (row + 0.5) * cellSize;
                        dotPath.add(new DotPosition(dotX, dotY, dotDiameter));
                    }
                }
            } else {
                // Right to left
                for (int col = matrixSize - 1; col >= 0; col--) {
                    if (matrix[row][col]) {
                        double dotX = startX + (col + 0.5) * cellSize;
                        double dotY = startY + (row + 0.5) * cellSize;
                        dotPath.add(new DotPosition(dotX, dotY, dotDiameter));
                    }
                }
            }
        }
        
        return dotPath;
    }
    
    // Export configuration for dot pin controllers
    public String exportDotPathConfig() {
        List<DotPosition> dotPath = generateDotPath();
        StringBuilder config = new StringBuilder();
        
        config.append("# Data Matrix Configuration\n");
        config.append("# Data: ").append(data).append("\n");
        config.append("# Matrix Size: ").append(matrixSize).append("x").append(matrixSize).append("\n");
        config.append("# Dot Pitch: ").append(dotPitch).append("px\n");
        config.append("# Dot Diameter: ").append(dotDiameter).append("px\n");
        config.append("# Total Dots: ").append(dotPath.size()).append("\n\n");
        
        config.append("# Dot Positions (X, Y, Diameter)\n");
        for (int i = 0; i < dotPath.size(); i++) {
            DotPosition dot = dotPath.get(i);
            config.append(String.format("DOT %d: %.2f, %.2f, %.2f\n", 
                i + 1, dot.x, dot.y, dot.diameter));
        }
        
        return config.toString();
    }
    
    // Inner class for dot positions
    public static class DotPosition {
        public final double x, y, diameter;
        
        public DotPosition(double x, double y, double diameter) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
        }
        
        @Override
        public String toString() {
            return String.format("Dot(%.2f, %.2f, ⌀%.2f)", x, y, diameter);
        }
    }
    
    @Override
    public void resizeTo(int mx, int my) {
        // Check lock size flag before allowing resize
        if (lockSize && RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("DotMatrixMark resizeTo blocked: Size is locked");
            }
            return;
        }
        
        if (resizing) {
            // Maintain aspect ratio and minimum size for proper matrix display
            int newWidth = Math.max(80, mx - x);
            int newHeight = Math.max(80, my - y);
            
            // Keep it square for better matrix display
            int size = Math.min(newWidth, newHeight);
            width = size;
            height = size;
        }
    }
    
    @Override
    public boolean overResizeHandle(int px, int py) {
        // Ensure the resize handle is properly detected
        return px >= x + width - HANDLE_SIZE && px <= x + width &&
               py >= y + height - HANDLE_SIZE && py <= y + height;
    }
    
    // Handle size for resize detection
    private static final int HANDLE_SIZE = 10;
}
