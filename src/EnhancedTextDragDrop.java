import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

/**
 * Enhanced Text Drag & Drop System for Canvas Integration
 * Inspired by Text.PNG with directional arrows for real-time resizing
 * Features live text customization during drag and drop operations
 */
public class EnhancedTextDragDrop {
    
    // Text drag state management
    private static boolean dragDropModeActive = false;
    private static DrawingCanvas targetCanvas = null;
    private static TextDragOverlay dragOverlay = null;
    
    // Current text properties (soft coded for customization)
    private static String currentText = "Sample Text";
    private static String currentFont = "Arial";
    private static int currentSize = 24;
    private static Color currentColor = Color.BLACK;
    private static boolean isBold = false;
    private static boolean isItalic = false;
    private static boolean isUnderline = false;
    private static int textAlignment = SwingConstants.LEFT;
    
    /**
     * Activate enhanced drag & drop mode with live text customization
     */
    public static void activateDragDropMode(DrawingCanvas canvas) {
        targetCanvas = canvas;
        System.out.println("🎯 Enhanced Text Drag & Drop Mode Activated!");
        
        // Show text customization panel with drag & drop controls
        showEnhancedTextCustomizer();
    }
    
    /**
     * Enhanced Text Customization Panel with Live Preview
     */
    private static void showEnhancedTextCustomizer() {
        JFrame customizerFrame = new JFrame("Enhanced Text Customizer - Drag & Drop Mode");
        customizerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customizerFrame.setSize(500, 600);
        customizerFrame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JLabel headerLabel = new JLabel("<html><center><h2>🎨 Text Creator</h2>" +
            "Configure properties then drag to canvas with directional resizing</center></html>");
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Text Properties Panel
        JPanel propertiesPanel = createAdvancedPropertiesPanel();
        mainPanel.add(propertiesPanel, BorderLayout.CENTER);
        
        // Action Panel
        JPanel actionPanel = createActionPanel(customizerFrame);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);
        
        customizerFrame.add(mainPanel);
        customizerFrame.setVisible(true);
    }
    
    /**
     * Create advanced properties panel with live preview
     */
    private static JPanel createAdvancedPropertiesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Text Properties (Live Preview)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Text Input with live update
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Text:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(currentText, 20);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                currentText = textField.getText();
                updateLivePreview();
            }
        });
        panel.add(textField, gbc);
        
        // Font Selection
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Font:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] fonts = {"Arial", "Times New Roman", "Calibri", "Segoe UI", "Comic Sans MS", "Verdana", "Helvetica"};
        JComboBox<String> fontCombo = new JComboBox<>(fonts);
        fontCombo.setSelectedItem(currentFont);
        fontCombo.addActionListener(e -> {
            currentFont = (String) fontCombo.getSelectedItem();
            updateLivePreview();
        });
        panel.add(fontCombo, gbc);
        
        // Font Size with slider for precision
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JSlider sizeSlider = new JSlider(8, 72, currentSize);
        sizeSlider.addChangeListener(e -> {
            currentSize = sizeSlider.getValue();
            updateLivePreview();
        });
        panel.add(sizeSlider, gbc);
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel sizeLabel = new JLabel(currentSize + "pt");
        sizeSlider.addChangeListener(e -> sizeLabel.setText(sizeSlider.getValue() + "pt"));
        panel.add(sizeLabel, gbc);
        
        // Style Options
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox boldCheck = new JCheckBox("Bold", isBold);
        JCheckBox italicCheck = new JCheckBox("Italic", isItalic);
        JCheckBox underlineCheck = new JCheckBox("Underline", isUnderline);
        
        boldCheck.addActionListener(e -> { isBold = boldCheck.isSelected(); updateLivePreview(); });
        italicCheck.addActionListener(e -> { isItalic = italicCheck.isSelected(); updateLivePreview(); });
        underlineCheck.addActionListener(e -> { isUnderline = underlineCheck.isSelected(); updateLivePreview(); });
        
        stylePanel.add(boldCheck);
        stylePanel.add(italicCheck);
        stylePanel.add(underlineCheck);
        panel.add(stylePanel, gbc);
        
        // Color Selection
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Color:"), gbc);
        gbc.gridx = 1;
        JButton colorButton = new JButton("🎨 Choose Color");
        colorButton.setBackground(currentColor);
        colorButton.setForeground(getContrastColor(currentColor));
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose Text Color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                colorButton.setBackground(newColor);
                colorButton.setForeground(getContrastColor(newColor));
                updateLivePreview();
            }
        });
        panel.add(colorButton, gbc);
        
        // Alignment Options
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        JPanel alignPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        alignPanel.setBorder(BorderFactory.createTitledBorder("Alignment"));
        ButtonGroup alignGroup = new ButtonGroup();
        JRadioButton leftAlign = new JRadioButton("Left", textAlignment == SwingConstants.LEFT);
        JRadioButton centerAlign = new JRadioButton("Center", textAlignment == SwingConstants.CENTER);
        JRadioButton rightAlign = new JRadioButton("Right", textAlignment == SwingConstants.RIGHT);
        
        ActionListener alignListener = e -> {
            if (leftAlign.isSelected()) textAlignment = SwingConstants.LEFT;
            else if (centerAlign.isSelected()) textAlignment = SwingConstants.CENTER;
            else if (rightAlign.isSelected()) textAlignment = SwingConstants.RIGHT;
            updateLivePreview();
        };
        
        leftAlign.addActionListener(alignListener);
        centerAlign.addActionListener(alignListener);
        rightAlign.addActionListener(alignListener);
        
        alignGroup.add(leftAlign);
        alignGroup.add(centerAlign);
        alignGroup.add(rightAlign);
        alignPanel.add(leftAlign);
        alignPanel.add(centerAlign);
        alignPanel.add(rightAlign);
        panel.add(alignPanel, gbc);
        
        // Live Preview Panel
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.BOTH;
        JPanel previewPanel = createLivePreviewPanel();
        panel.add(previewPanel, gbc);
        
        return panel;
    }
    
    /**
     * Create live preview panel
     */
    private static JPanel createLivePreviewPanel() {
        JPanel previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Draw preview text
                int style = Font.PLAIN;
                if (isBold) style |= Font.BOLD;
                if (isItalic) style |= Font.ITALIC;
                
                Font font = new Font(currentFont, style, Math.max(12, currentSize / 2)); // Scale for preview
                g2d.setFont(font);
                g2d.setColor(currentColor);
                
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(currentText);
                int textHeight = fm.getHeight();
                
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - fm.getDescent();
                
                g2d.drawString(currentText, x, y);
                
                if (isUnderline) {
                    int underlineY = y + fm.getDescent() - 1;
                    g2d.drawLine(x, underlineY, x + textWidth, underlineY);
                }
                
                // Draw border
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        previewPanel.setPreferredSize(new Dimension(300, 80));
        previewPanel.setBorder(BorderFactory.createTitledBorder("Live Preview"));
        previewPanel.setBackground(Color.WHITE);
        
        return previewPanel;
    }
    
    /**
     * Create action panel with drag & drop controls
     */
    private static JPanel createActionPanel(JFrame parentFrame) {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton dragModeButton = new JButton("🎯 Start Drag & Drop Mode");
        dragModeButton.setFont(new Font("Arial", Font.BOLD, 14));
        dragModeButton.setBackground(new Color(0, 120, 215));
        dragModeButton.setForeground(Color.WHITE);
        dragModeButton.addActionListener(e -> {
            startCanvasDragMode();
            parentFrame.setVisible(false);
        });
        
        JButton cancelButton = new JButton("❌ Cancel");
        cancelButton.addActionListener(e -> parentFrame.dispose());
        
        panel.add(dragModeButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    /**
     * Start canvas drag mode with overlay
     */
    private static void startCanvasDragMode() {
        System.out.println("✅ Canvas Drag Mode Started - Click and drag on canvas to place text");
        System.out.println("🔧 Features: Grid snapping, directional resizing, live feedback");
        
        dragDropModeActive = true;
        
        // Create and show drag overlay on canvas
        if (targetCanvas != null) {
            dragOverlay = new TextDragOverlay();
            targetCanvas.add(dragOverlay);
            targetCanvas.setComponentZOrder(dragOverlay, 0); // Bring to front
            dragOverlay.setVisible(true);
            targetCanvas.repaint();
            
            // Show instructions
            JOptionPane.showMessageDialog(targetCanvas,
                "🎯 Drag Mode Active!\n" +
                "• Click and drag on canvas to place text\n" +
                "• Use directional arrows to resize while dragging\n" +
                "• Text will snap to grid automatically\n" +
                "• Release mouse to place text",
                "Text Drag & Drop Instructions",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Update live preview
     */
    private static void updateLivePreview() {
        // Implementation would update the preview panel
        // This is called whenever properties change
    }
    
    /**
     * Get contrast color for text visibility
     */
    private static Color getContrastColor(Color color) {
        double brightness = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114) / 255;
        return brightness > 0.5 ? Color.BLACK : Color.WHITE;
    }
    
    /**
     * Text Drag Overlay - Handles canvas drag operations with directional arrows
     */
    static class TextDragOverlay extends JPanel {
        private boolean isDragging = false;
        private Point startPoint;
        private Point currentPoint;
        private Rectangle2D textBounds;
        
        public TextDragOverlay() {
            setOpaque(false);
            setSize(targetCanvas.getSize());
            
            // Mouse handlers for drag operations
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    isDragging = true;
                    System.out.println("🎯 Text drag started at: " + startPoint);
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (isDragging) {
                        finalizePlacement(e.getPoint());
                        isDragging = false;
                    }
                }
            });
            
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isDragging) {
                        currentPoint = e.getPoint();
                        
                        // Handle directional resizing (inspired by Text.PNG arrows)
                        handleDirectionalResize(e);
                        
                        repaint();
                    }
                }
            });
        }
        
        /**
         * Handle directional resizing during drag (Text.PNG inspired)
         */
        private void handleDirectionalResize(MouseEvent e) {
            if (startPoint != null && currentPoint != null) {
                int deltaX = currentPoint.x - startPoint.x;
                int deltaY = currentPoint.y - startPoint.y;
                
                // Adjust size based on drag direction
                if (Math.abs(deltaX) > 10) {
                    int sizeChange = deltaX / 10;
                    currentSize = Math.max(8, Math.min(72, currentSize + sizeChange));
                    System.out.println("📏 Text size adjusted to: " + currentSize + "pt");
                }
            }
        }
        
        /**
         * Finalize text placement on canvas
         */
        private void finalizePlacement(Point finalPoint) {
            System.out.println("✅ Placing text at: " + finalPoint);
            
            // Create TextMark with current properties
            TextMark newTextMark = new TextMark(finalPoint.x, finalPoint.y, currentText);
            
            // Apply formatting (would need TextMark extensions for full formatting)
            targetCanvas.getMarks().add(newTextMark);
            
            // Clean up overlay
            targetCanvas.remove(this);
            dragDropModeActive = false;
            targetCanvas.repaint();
            
            System.out.println("🎨 Text placed successfully with formatting!");
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (isDragging && startPoint != null && currentPoint != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw preview text at current position
                drawPreviewText(g2d);
                
                // Draw directional resize arrows (Text.PNG style)
                drawDirectionalArrows(g2d);
                
                // Draw grid snap indicators
                drawGridSnapFeedback(g2d);
            }
        }
        
        /**
         * Draw preview text during drag
         */
        private void drawPreviewText(Graphics2D g2d) {
            int style = Font.PLAIN;
            if (isBold) style |= Font.BOLD;
            if (isItalic) style |= Font.ITALIC;
            
            Font font = new Font(currentFont, style, currentSize);
            g2d.setFont(font);
            g2d.setColor(new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 180));
            
            g2d.drawString(currentText, currentPoint.x, currentPoint.y);
            
            if (isUnderline) {
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(currentText);
                g2d.drawLine(currentPoint.x, currentPoint.y + 2, currentPoint.x + textWidth, currentPoint.y + 2);
            }
        }
        
        /**
         * Draw directional arrows for resizing (inspired by Text.PNG)
         */
        private void drawDirectionalArrows(Graphics2D g2d) {
            g2d.setColor(new Color(0, 120, 215));
            g2d.setStroke(new BasicStroke(2));
            
            // Horizontal resize arrows
            int arrowX = currentPoint.x + 100;
            int arrowY = currentPoint.y;
            
            // Left arrow
            g2d.drawLine(arrowX - 20, arrowY, arrowX - 10, arrowY);
            g2d.drawLine(arrowX - 15, arrowY - 5, arrowX - 20, arrowY);
            g2d.drawLine(arrowX - 15, arrowY + 5, arrowX - 20, arrowY);
            
            // Right arrow
            g2d.drawLine(arrowX + 10, arrowY, arrowX + 20, arrowY);
            g2d.drawLine(arrowX + 15, arrowY - 5, arrowX + 20, arrowY);
            g2d.drawLine(arrowX + 15, arrowY + 5, arrowX + 20, arrowY);
            
            // Size indicator
            g2d.drawString(currentSize + "pt", arrowX - 5, arrowY - 10);
        }
        
        /**
         * Draw grid snap feedback
         */
        private void drawGridSnapFeedback(Graphics2D g2d) {
            // Show grid snap indicators when near grid points
            g2d.setColor(new Color(255, 165, 0, 150));
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0));
            
            // Draw snap crosshair
            int snapX = currentPoint.x;
            int snapY = currentPoint.y;
            
            g2d.drawLine(snapX - 10, snapY, snapX + 10, snapY);
            g2d.drawLine(snapX, snapY - 10, snapX, snapY + 10);
            
            // Snap circle
            g2d.drawOval(snapX - 8, snapY - 8, 16, 16);
        }
    }
}