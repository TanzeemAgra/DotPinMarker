import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * GraphMark: Enhanced Technical Drawing and Image Management for Dot Pin Marking
 * Creates geometric reference grids, technical drawing frames, alignment guides, and manages custom images
 * Optimized for precision CNC marking applications with integrated image upload and management
 */
public class GraphMark extends Mark {
    private Color borderColor = Color.BLACK;
    
    // Soft coding: Image Management System Configuration
    private static final String IMAGES_FOLDER_NAME = "uploaded_images";
    private static final String[] SUPPORTED_IMAGE_FORMATS = {"jpg", "jpeg", "png", "bmp", "gif"};
    private static final long MAX_IMAGE_SIZE_MB = 10; // 10MB limit
    private static final int MAX_IMAGE_DIMENSION = 2048; // Max width/height in pixels
    
    // Image properties
    private BufferedImage assignedImage = null;
    private String imageName = "";
    private String imageFilePath = "";
    private boolean showImage = true;
    private double imageScale = 1.0; // Scale factor for image display
    private int imageOffsetX = 0;    // Offset for image positioning
    private int imageOffsetY = 0;
    
    // Soft coding: Configurable graph types and properties
    public enum GraphType { 
        TECHNICAL_FRAME,    // Rectangle with diagonals (current)
        GRID_PATTERN,       // Grid lines for measurements
        SCALE_RULER,        // Scale markings for precision
        ALIGNMENT_CROSS     // Cross-hair for alignment
    }
    
    private GraphType graphType = GraphType.TECHNICAL_FRAME;
    private boolean showGrid = false;
    private boolean showScale = false;
    private int gridSpacing = 10;      // Spacing between grid lines
    private int scaleInterval = 5;     // Interval for scale markings
    
    // Simple resize handle types
    private enum ResizeType { NONE, CORNER, HORIZONTAL, VERTICAL }
    private ResizeType activeResize = ResizeType.NONE;
    
    public GraphMark(int x, int y) {
        super(x, y);
        this.width = 120;
        this.height = 80;
    }
    
    // Soft coding: Constructor with configurable type
    public GraphMark(int x, int y, GraphType type) {
        super(x, y);
        this.width = 120;
        this.height = 80;
        this.graphType = type;
        
        // Auto-configure based on type
        switch (type) {
            case GRID_PATTERN:
                this.showGrid = true;
                this.width = 150;
                this.height = 100;
                break;
            case SCALE_RULER:
                this.showScale = true;
                this.width = 200;
                this.height = 50;
                break;
            case ALIGNMENT_CROSS:
                this.width = 100;
                this.height = 100;
                break;
            default:
                // TECHNICAL_FRAME - keep defaults
                break;
        }
    }
    
    @Override
    public void draw(Graphics2D g, boolean isSelected) {
        // Set rendering hints for better image quality
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        // Draw assigned image first (as background)
        if (hasAssignedImage()) {
            drawAssignedImage(g);
        }
        
        // Set stroke for bold lines
        BasicStroke boldStroke = new BasicStroke(2.0f);
        g.setStroke(boldStroke);
        
        // Set color - green if selected, black otherwise
        if (isSelected) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(borderColor);
        }
        
        // Soft coding: Draw based on graph type (as overlay)
        switch (graphType) {
            case TECHNICAL_FRAME:
                drawTechnicalFrame(g);
                break;
            case GRID_PATTERN:
                drawGridPattern(g);
                break;
            case SCALE_RULER:
                drawScaleRuler(g);
                break;
            case ALIGNMENT_CROSS:
                drawAlignmentCross(g);
                break;
        }
        
        // Draw image info overlay if image is assigned
        if (hasAssignedImage() && !imageName.isEmpty()) {
            drawImageInfoOverlay(g);
        }
        
        // Draw resize handles if selected
        if (isSelected) {
            drawResizeHandles(g);
        }
    }
    
    // Soft coding: Technical frame drawing (original functionality)
    private void drawTechnicalFrame(Graphics2D g) {
        // Draw rectangular frame with sharp corners (no fill, only border)
        g.drawRect(x, y, width, height);
        
        // Draw diagonal lines connecting opposite corners
        // Diagonal 1: top-left to bottom-right
        g.drawLine(x, y, x + width, y + height);
        
        // Diagonal 2: bottom-left to top-right  
        g.drawLine(x, y + height, x + width, y);
    }
    
    // Soft coding: Grid pattern for precise measurements
    private void drawGridPattern(Graphics2D g) {
        // Draw outer frame
        g.drawRect(x, y, width, height);
        
        // Draw grid lines
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(new Color(128, 128, 128)); // Gray for grid lines
        
        // Vertical grid lines
        for (int i = gridSpacing; i < width; i += gridSpacing) {
            g.drawLine(x + i, y, x + i, y + height);
        }
        
        // Horizontal grid lines
        for (int i = gridSpacing; i < height; i += gridSpacing) {
            g.drawLine(x, y + i, x + width, y + i);
        }
        
        // Restore border stroke and color
        g.setStroke(new BasicStroke(2.0f));
        g.setColor(borderColor);
        g.drawRect(x, y, width, height);
    }
    
    // Soft coding: Scale ruler for precision marking
    private void drawScaleRuler(Graphics2D g) {
        // Draw main ruler line
        g.drawLine(x, y + height/2, x + width, y + height/2);
        
        // Draw scale markings
        g.setStroke(new BasicStroke(1.0f));
        Font oldFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 8));
        
        for (int i = 0; i <= width; i += scaleInterval) {
            int markHeight = (i % (scaleInterval * 2) == 0) ? 8 : 4; // Larger marks every 10
            g.drawLine(x + i, y + height/2 - markHeight/2, x + i, y + height/2 + markHeight/2);
            
            // Draw numbers for major markings
            if (i % (scaleInterval * 4) == 0 && i > 0) {
                String label = String.valueOf(i);
                FontMetrics fm = g.getFontMetrics();
                int labelWidth = fm.stringWidth(label);
                g.drawString(label, x + i - labelWidth/2, y + height/2 - 10);
            }
        }
        
        g.setFont(oldFont);
        g.setStroke(new BasicStroke(2.0f));
    }
    
    // Soft coding: Alignment cross for positioning
    private void drawAlignmentCross(Graphics2D g) {
        int centerX = x + width/2;
        int centerY = y + height/2;
        
        // Draw cross lines
        g.drawLine(x, centerY, x + width, centerY);           // Horizontal line
        g.drawLine(centerX, y, centerX, y + height);          // Vertical line
        
        // Draw center circle
        g.setStroke(new BasicStroke(1.0f));
        g.drawOval(centerX - 5, centerY - 5, 10, 10);
        
        // Draw corner markers
        int markerSize = 10;
        g.drawLine(x, y, x + markerSize, y);                  // Top-left
        g.drawLine(x, y, x, y + markerSize);
        g.drawLine(x + width - markerSize, y, x + width, y);  // Top-right
        g.drawLine(x + width, y, x + width, y + markerSize);
        g.drawLine(x, y + height - markerSize, x, y + height); // Bottom-left
        g.drawLine(x, y + height, x + markerSize, y + height);
        g.drawLine(x + width, y + height - markerSize, x + width, y + height); // Bottom-right
        g.drawLine(x + width - markerSize, y + height, x + width, y + height);
        
        g.setStroke(new BasicStroke(2.0f));
    }
    
    // Soft coding: Draw resize handles
    private void drawResizeHandles(Graphics2D g) {
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(1.0f));
        
        // Corner handle (both directions)
        g.fillRect(x + width - HANDLE_SIZE, y + height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE);
        
        // Right edge handle (horizontal only)
        g.fillRect(x + width - HANDLE_SIZE/2, y + height/2 - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
        
        // Bottom edge handle (vertical only)
        g.fillRect(x + width/2 - HANDLE_SIZE/2, y + height - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
    }
    
    // Enhanced resize handle detection
    @Override
    public boolean overResizeHandle(int px, int py) {
        return getResizeType(px, py) != ResizeType.NONE;
    }
    
    // Determine which type of resize handle was clicked
    private ResizeType getResizeType(int px, int py) {
        // Corner handle (bottom-right)
        if (px >= x + width - HANDLE_SIZE && px <= x + width &&
            py >= y + height - HANDLE_SIZE && py <= y + height) {
            return ResizeType.CORNER;
        }
        
        // Right edge handle (horizontal resize)
        if (px >= x + width - HANDLE_SIZE/2 && px <= x + width + HANDLE_SIZE/2 &&
            py >= y + height/2 - HANDLE_SIZE && py <= y + height/2 + HANDLE_SIZE) {
            return ResizeType.HORIZONTAL;
        }
        
        // Bottom edge handle (vertical resize)
        if (px >= x + width/2 - HANDLE_SIZE && px <= x + width/2 + HANDLE_SIZE &&
            py >= y + height - HANDLE_SIZE/2 && py <= y + height + HANDLE_SIZE/2) {
            return ResizeType.VERTICAL;
        }
        
        return ResizeType.NONE;
    }
    
    // Start resize and remember which type
    @Override
    public void startResize() {
        // Check lock size flag before allowing resize
        if (lockSize && RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("GraphMark startResize blocked: Size is locked");
            }
            return;
        }
        
        resizing = true;
        // activeResize will be set in startResize(px, py)
    }
    
    // Start resize with position to determine type
    public void startResize(int px, int py) {
        activeResize = getResizeType(px, py);
        resizing = true;
    }
    
    // Enhanced resize based on handle type
    @Override
    public void resizeTo(int mx, int my) {
        // Check lock size flag before allowing resize
        if (lockSize && RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY) {
            if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                System.out.println("GraphMark resizeTo blocked: Size is locked");
            }
            return;
        }
        
        if (resizing) {
            switch (activeResize) {
                case CORNER:
                    // Both width and height
                    width = Math.max(50, mx - x);
                    height = Math.max(30, my - y);
                    break;
                case HORIZONTAL:
                    // Width only
                    width = Math.max(50, mx - x);
                    break;
                case VERTICAL:
                    // Height only
                    height = Math.max(30, my - y);
                    break;
                default:
                    // Fallback to standard resize
                    width = Math.max(50, mx - x);
                    height = Math.max(30, my - y);
                    break;
            }
        }
    }
    
    @Override
    public void stopResize() {
        resizing = false;
        activeResize = ResizeType.NONE;
    }
    
    public void setBorderColor(Color color) {
        this.borderColor = color;
    }
    
    public Color getBorderColor() {
        return this.borderColor;
    }
    
    // Additional soft coding: Getters and setters for configuration
    public GraphType getGraphType() {
        return graphType;
    }
    
    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }
    
    public boolean isShowGrid() {
        return showGrid;
    }
    
    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }
    
    public boolean isShowScale() {
        return showScale;
    }
    
    public void setShowScale(boolean showScale) {
        this.showScale = showScale;
    }
    
    public int getGridSpacing() {
        return gridSpacing;
    }
    
    public void setGridSpacing(int gridSpacing) {
        this.gridSpacing = Math.max(5, gridSpacing); // Minimum spacing
    }
    
    public int getScaleInterval() {
        return scaleInterval;
    }
    
    public void setScaleInterval(int scaleInterval) {
        this.scaleInterval = Math.max(5, scaleInterval); // Minimum interval
    }
    
    // Soft coding: Get available graph types for UI display
    public static GraphType[] getAvailableGraphTypes() {
        return GraphType.values();
    }
    
    // Soft coding: Get description of each graph type
    public static String getGraphTypeDescription(GraphType type) {
        switch (type) {
            case TECHNICAL_FRAME:
                return "Technical frame with diagonals for precise marking";
            case GRID_PATTERN:
                return "Grid pattern for detailed measurements";
            case SCALE_RULER:
                return "Scale ruler for precision marking";
            case ALIGNMENT_CROSS:
                return "Alignment cross for positioning";
            default:
                return "Unknown graph type";
        }
    }
    
    // ===============================================================================
    // SOFT-CODED IMAGE MANAGEMENT SYSTEM
    // ===============================================================================
    
    /**
     * Opens image upload dialog and assigns image to GraphMark
     * Includes name assignment, file browser, and preview functionality
     */
    public boolean openImageUploadDialog() {
        System.out.println("🖼️ Opening GraphMark Image Upload Dialog...");
        
        // Create image upload dialog with custom layout
        JDialog uploadDialog = new JDialog((Frame) null, "GraphMark Image Upload", true);
        uploadDialog.setLayout(new BorderLayout());
        uploadDialog.setSize(500, 400);
        uploadDialog.setLocationRelativeTo(null);
        
        // Top panel: Image name assignment
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setBorder(BorderFactory.createTitledBorder("Image Name Assignment"));
        JTextField nameField = new JTextField(imageName.isEmpty() ? "Graph_Image_" + System.currentTimeMillis() : imageName, 20);
        namePanel.add(new JLabel("Image Name:"));
        namePanel.add(nameField);
        
        // Center panel: File browser and preview
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // File selection panel
        JPanel filePanel = new JPanel(new FlowLayout());
        filePanel.setBorder(BorderFactory.createTitledBorder("Image File Selection"));
        JTextField filePathField = new JTextField(imageFilePath, 25);
        filePathField.setEditable(false);
        JButton browseButton = new JButton("Browse Images...");
        filePanel.add(filePathField);
        filePanel.add(browseButton);
        
        // Image preview panel
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createTitledBorder("Image Preview"));
        JLabel previewLabel = new JLabel("No image selected", SwingConstants.CENTER);
        previewLabel.setPreferredSize(new Dimension(200, 150));
        previewLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        previewPanel.add(previewLabel, BorderLayout.CENTER);
        
        centerPanel.add(filePanel, BorderLayout.NORTH);
        centerPanel.add(previewPanel, BorderLayout.CENTER);
        
        // Bottom panel: Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton assignButton = new JButton("Assign Image");
        JButton removeButton = new JButton("Remove Image");
        JButton cancelButton = new JButton("Cancel");
        
        // Style buttons
        assignButton.setBackground(new Color(0, 150, 0));
        assignButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(200, 100, 0));
        removeButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(150, 150, 150));
        
        buttonPanel.add(assignButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);
        
        uploadDialog.add(namePanel, BorderLayout.NORTH);
        uploadDialog.add(centerPanel, BorderLayout.CENTER);
        uploadDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // File browser functionality
        final File[] selectedFile = {null};
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = createImageFileChooser();
            int result = fileChooser.showOpenDialog(uploadDialog);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile[0].getAbsolutePath());
                
                // Update preview
                try {
                    BufferedImage previewImg = ImageIO.read(selectedFile[0]);
                    if (previewImg != null) {
                        // Scale image for preview
                        ImageIcon icon = new ImageIcon(scaleImageForPreview(previewImg, 180, 130));
                        previewLabel.setIcon(icon);
                        previewLabel.setText("");
                        
                        // Auto-generate name if empty
                        if (nameField.getText().equals("")) {
                            String fileName = selectedFile[0].getName();
                            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                            nameField.setText("Graph_" + baseName);
                        }
                    }
                } catch (IOException ex) {
                    previewLabel.setText("Preview Error");
                    System.err.println("Image preview error: " + ex.getMessage());
                }
            }
        });
        
        // Button actions
        final boolean[] success = {false};
        
        assignButton.addActionListener(e -> {
            if (selectedFile[0] != null && !nameField.getText().trim().isEmpty()) {
                try {
                    success[0] = assignImageToGraph(selectedFile[0], nameField.getText().trim());
                    if (success[0]) {
                        uploadDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(uploadDialog, 
                            "Failed to assign image. Please check file format and size.",
                            "Assignment Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(uploadDialog,
                        "Error: " + ex.getMessage(),
                        "Assignment Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(uploadDialog,
                    "Please select an image file and provide a name.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        removeButton.addActionListener(e -> {
            removeImageFromGraph();
            success[0] = true;
            uploadDialog.dispose();
        });
        
        cancelButton.addActionListener(e -> uploadDialog.dispose());
        
        // Show current image if exists
        if (assignedImage != null) {
            ImageIcon currentIcon = new ImageIcon(scaleImageForPreview(assignedImage, 180, 130));
            previewLabel.setIcon(currentIcon);
            previewLabel.setText("");
        }
        
        uploadDialog.setVisible(true);
        return success[0];
    }
    
    /**
     * Creates a file chooser configured for image selection
     */
    private JFileChooser createImageFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image for GraphMark");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // Add file filters for supported formats
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Image Files (" + String.join(", ", SUPPORTED_IMAGE_FORMATS) + ")", 
            SUPPORTED_IMAGE_FORMATS);
        fileChooser.setFileFilter(imageFilter);
        
        // Set initial directory to images folder
        Path imagesFolder = getImagesFolder();
        if (Files.exists(imagesFolder)) {
            fileChooser.setCurrentDirectory(imagesFolder.toFile());
        }
        
        return fileChooser;
    }
    
    /**
     * Assigns an image file to this GraphMark with validation and storage management
     */
    private boolean assignImageToGraph(File imageFile, String name) {
        try {
            System.out.println("🖼️ Assigning image: " + imageFile.getName() + " with name: " + name);
            
            // Validate file
            if (!validateImageFile(imageFile)) {
                return false;
            }
            
            // Load and validate image
            BufferedImage image = ImageIO.read(imageFile);
            if (image == null) {
                System.err.println("❌ Failed to load image: " + imageFile.getName());
                return false;
            }
            
            // Create images directory if needed
            Path imagesFolder = createImagesFolderIfNeeded();
            if (imagesFolder == null) {
                return false;
            }
            
            // Generate unique filename
            String fileExtension = getFileExtension(imageFile.getName());
            String safeFileName = sanitizeFileName(name) + "_" + System.currentTimeMillis() + "." + fileExtension;
            Path targetPath = imagesFolder.resolve(safeFileName);
            
            // Copy file to managed location
            Files.copy(imageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            // Assign to GraphMark
            this.assignedImage = image;
            this.imageName = name;
            this.imageFilePath = targetPath.toString();
            this.showImage = true;
            this.imageScale = calculateOptimalScale(image);
            
            System.out.println("✅ Image assigned successfully: " + safeFileName);
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error assigning image: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Removes the assigned image from this GraphMark
     */
    public void removeImageFromGraph() {
        System.out.println("🗑️ Removing image from GraphMark: " + imageName);
        this.assignedImage = null;
        this.imageName = "";
        this.imageFilePath = "";
        this.showImage = false;
        this.imageScale = 1.0;
        this.imageOffsetX = 0;
        this.imageOffsetY = 0;
    }
    
    /**
     * Soft coding: Get or create the images folder path
     */
    private Path getImagesFolder() {
        return Paths.get(System.getProperty("user.dir"), IMAGES_FOLDER_NAME);
    }
    
    /**
     * Creates the images directory structure if it doesn't exist
     */
    private Path createImagesFolderIfNeeded() {
        try {
            Path imagesFolder = getImagesFolder();
            if (!Files.exists(imagesFolder)) {
                Files.createDirectories(imagesFolder);
                System.out.println("📁 Created images folder: " + imagesFolder.toString());
            }
            return imagesFolder;
        } catch (Exception e) {
            System.err.println("❌ Failed to create images folder: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Validates image file format and size
     */
    private boolean validateImageFile(File file) {
        // Check file extension
        String extension = getFileExtension(file.getName()).toLowerCase();
        boolean validFormat = false;
        for (String format : SUPPORTED_IMAGE_FORMATS) {
            if (format.equals(extension)) {
                validFormat = true;
                break;
            }
        }
        
        if (!validFormat) {
            System.err.println("❌ Unsupported image format: " + extension);
            return false;
        }
        
        // Check file size
        long fileSizeMB = file.length() / (1024 * 1024);
        if (fileSizeMB > MAX_IMAGE_SIZE_MB) {
            System.err.println("❌ Image too large: " + fileSizeMB + "MB (max: " + MAX_IMAGE_SIZE_MB + "MB)");
            return false;
        }
        
        return true;
    }
    
    /**
     * Utility method to get file extension
     */
    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot > 0) ? fileName.substring(lastDot + 1) : "";
    }
    
    /**
     * Sanitizes filename to remove invalid characters
     */
    private String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
    
    /**
     * Scales image for preview display
     */
    private BufferedImage scaleImageForPreview(BufferedImage original, int maxWidth, int maxHeight) {
        double scaleX = (double) maxWidth / original.getWidth();
        double scaleY = (double) maxHeight / original.getHeight();
        double scale = Math.min(scaleX, scaleY);
        
        int newWidth = (int) (original.getWidth() * scale);
        int newHeight = (int) (original.getHeight() * scale);
        
        BufferedImage scaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaled.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        
        return scaled;
    }
    
    /**
     * Calculates optimal scale for image display within GraphMark bounds
     */
    private double calculateOptimalScale(BufferedImage image) {
        double scaleX = (double) (width - 20) / image.getWidth();  // Leave 10px margin on each side
        double scaleY = (double) (height - 20) / image.getHeight();
        return Math.min(Math.min(scaleX, scaleY), 1.0); // Don't scale up beyond original size
    }
    
    /**
     * Draws the assigned image within the GraphMark bounds with proper scaling and positioning
     */
    private void drawAssignedImage(Graphics2D g) {
        if (assignedImage == null) return;
        
        // Calculate scaled dimensions
        int scaledWidth = (int) (assignedImage.getWidth() * imageScale);
        int scaledHeight = (int) (assignedImage.getHeight() * imageScale);
        
        // Center image within GraphMark bounds with offset
        int imageX = x + (width - scaledWidth) / 2 + imageOffsetX;
        int imageY = y + (height - scaledHeight) / 2 + imageOffsetY;
        
        // Ensure image stays within GraphMark bounds
        imageX = Math.max(x, Math.min(imageX, x + width - scaledWidth));
        imageY = Math.max(y, Math.min(imageY, y + height - scaledHeight));
        
        // Save original composite for transparency
        Composite originalComposite = g.getComposite();
        
        // Apply slight transparency to make overlay elements visible
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        
        // Draw image with high quality scaling
        g.drawImage(assignedImage, imageX, imageY, scaledWidth, scaledHeight, null);
        
        // Restore original composite
        g.setComposite(originalComposite);
        
        System.out.println("🖼️ Drawing image '" + imageName + "' at (" + imageX + "," + imageY + 
                          ") size: " + scaledWidth + "x" + scaledHeight + " scale: " + imageScale);
    }
    
    /**
     * Draws image information overlay showing name and controls
     */
    private void drawImageInfoOverlay(Graphics2D g) {
        // Save original settings
        Color originalColor = g.getColor();
        Font originalFont = g.getFont();
        Stroke originalStroke = g.getStroke();
        
        // Set up overlay styling
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.setStroke(new BasicStroke(1.0f));
        
        // Draw info background
        String infoText = "📷 " + imageName + " (" + String.format("%.1f%%", imageScale * 100) + ")";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(infoText);
        int textHeight = fm.getHeight();
        
        int infoX = x + 5;
        int infoY = y + 5;
        
        // Semi-transparent background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(infoX - 3, infoY - textHeight + 3, textWidth + 6, textHeight + 2, 5, 5);
        
        // White text
        g.setColor(Color.WHITE);
        g.drawString(infoText, infoX, infoY);
        
        // Image control indicators (small corner markers)
        g.setColor(new Color(255, 255, 0, 150)); // Yellow with transparency
        
        // Scale control indicator (bottom-right corner)
        int scaleX = x + width - 15;
        int scaleY = y + height - 15;
        g.fillRect(scaleX, scaleY, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("S", scaleX + 2, scaleY + 8);
        
        // Position control indicator (top-right corner)  
        int posX = x + width - 15;
        int posY = y + 5;
        g.setColor(new Color(0, 255, 255, 150)); // Cyan with transparency
        g.fillRect(posX, posY, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString("P", posX + 2, posY + 8);
        
        // Restore original settings
        g.setColor(originalColor);
        g.setFont(originalFont);
        g.setStroke(originalStroke);
    }
    
    // ===============================================================================
    // IMAGE PROPERTY GETTERS AND SETTERS
    // ===============================================================================
    
    public boolean hasAssignedImage() {
        return assignedImage != null && showImage;
    }
    
    public String getImageName() {
        return imageName;
    }
    
    public String getImageFilePath() {
        return imageFilePath;
    }
    
    public void setShowImage(boolean show) {
        this.showImage = show;
    }
    
    public boolean isShowImage() {
        return showImage;
    }
    
    public void setImageScale(double scale) {
        this.imageScale = Math.max(0.1, Math.min(5.0, scale)); // Limit scale between 0.1x and 5x
    }
    
    public double getImageScale() {
        return imageScale;
    }
    
    public void setImageOffset(int offsetX, int offsetY) {
        this.imageOffsetX = offsetX;
        this.imageOffsetY = offsetY;
    }
    
    public int getImageOffsetX() {
        return imageOffsetX;
    }
    
    public int getImageOffsetY() {
        return imageOffsetY;
    }
    
    @Override
    public String toString() {
        return "GraphMark{" +
                "type=" + graphType +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", gridSpacing=" + gridSpacing +
                ", scaleInterval=" + scaleInterval +
                ", hasImage=" + hasAssignedImage() +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
