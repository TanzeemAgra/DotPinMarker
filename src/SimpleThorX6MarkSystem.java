/**
 * SimpleThorX6MarkSystem - Simplified implementation like real ThorX6 software
 * 
 * How Real ThorX6 Works:
 * 1. User clicks "Add Mark" button
 * 2. Simple dropdown shows mark types (Text, Barcode, etc.)
 * 3. User selects type
 * 4. User clicks on canvas
 * 5. Mark appears at click location
 * 6. Simple properties panel for selected mark
 * 
 * NO COMPLEX RESIZE SYSTEMS - Just simple, professional workflow
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimpleThorX6MarkSystem extends JPanel {
    
    // Integration with existing DrawingCanvas
    private DrawingCanvas drawingCanvas;
    
    // Simple mark types (like real ThorX6)
    public enum MarkType {
        TEXT("Text", "📝"),
        BARCODE("Barcode", "📊"), 
        LOGO("Logo", "🖼️"),
        DATE("Date/Time", "📅"),
        SERIAL("Serial Number", "🔢"),
        LINE("Line", "📏"),
        RECTANGLE("Rectangle", "⬜"),
        CIRCLE("Circle", "⭕");
        
        public final String name;
        public final String icon;
        
        MarkType(String name, String icon) {
            this.name = name;
            this.icon = icon;
        }
    }
    
    // Simple mark class (no complex resize handles)
    public static class SimpleMark {
        public MarkType type;
        public int x, y;
        public String content;
        public Font font;
        public Color color;
        public boolean selected;
        
        public SimpleMark(MarkType type, int x, int y, String content) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.content = content;
            this.font = new Font("Arial", Font.PLAIN, 14);
            this.color = Color.BLACK;
            this.selected = false;
        }
        
        public void draw(Graphics2D g) {
            g.setFont(font);
            g.setColor(color);
            
            if (selected) {
                g.setColor(Color.BLUE);
                g.drawRect(x-2, y-2, 100, 20);
                g.setColor(color);
            }
            
            switch (type) {
                case TEXT:
                    g.drawString(content, x, y);
                    break;
                case BARCODE:
                    // Simple barcode representation
                    for (int i = 0; i < 10; i++) {
                        if (i % 2 == 0) g.fillRect(x + i*3, y-10, 2, 15);
                    }
                    g.drawString(content, x, y+20);
                    break;
                case LOGO:
                    g.drawRect(x, y-10, 40, 20);
                    g.drawString("LOGO", x+5, y+5);
                    break;
                case DATE:
                    g.drawString(java.time.LocalDate.now().toString(), x, y);
                    break;
                case SERIAL:
                    g.drawString("SN:" + content, x, y);
                    break;
                case LINE:
                    g.drawLine(x, y, x+50, y);
                    break;
                case RECTANGLE:
                    g.drawRect(x, y-10, 50, 20);
                    break;
                case CIRCLE:
                    g.drawOval(x, y-10, 20, 20);
                    break;
            }
        }
        
        public boolean contains(int px, int py) {
            return px >= x-5 && px <= x+55 && py >= y-15 && py <= y+25;
        }
    }
    
    // Simple state management
    private List<SimpleMark> marks = new ArrayList<>();
    private SimpleMark selectedMark = null;
    private MarkType selectedMarkType = null;
    private boolean placementMode = false;
    
    // UI Components
    private JButton addMarkButton;
    private JPanel propertiesPanel;
    private JTextField contentField;
    private JSpinner fontSizeSpinner;
    
    public SimpleThorX6MarkSystem() {
        this(null);
    }
    
    public SimpleThorX6MarkSystem(DrawingCanvas canvas) {
        this.drawingCanvas = canvas;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeUI();
        setupCanvas();
    }
    
    private void initializeUI() {
        // Top toolbar (like real ThorX6)
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(new Color(240, 240, 240));
        toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Simple Add Mark button (main action)
        addMarkButton = new JButton("➕ Add Mark");
        addMarkButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addMarkButton.addActionListener(this::showAddMarkMenu);
        toolbar.add(addMarkButton);
        
        // Delete button
        JButton deleteButton = new JButton("🗑️ Delete");
        deleteButton.addActionListener(e -> deleteSelectedMark());
        toolbar.add(deleteButton);
        
        add(toolbar, BorderLayout.NORTH);
        
        // Right properties panel (simple)
        createPropertiesPanel();
        add(propertiesPanel, BorderLayout.EAST);
    }
    
    private void createPropertiesPanel() {
        propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
        propertiesPanel.setBackground(new Color(250, 250, 250));
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties"));
        propertiesPanel.setPreferredSize(new Dimension(200, 400));
        
        // Content field
        propertiesPanel.add(new JLabel("Content:"));
        contentField = new JTextField("Sample Text");
        contentField.addActionListener(e -> updateSelectedMark());
        propertiesPanel.add(contentField);
        
        propertiesPanel.add(Box.createVerticalStrut(10));
        
        // Font size
        propertiesPanel.add(new JLabel("Font Size:"));
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(14, 8, 72, 1));
        fontSizeSpinner.addChangeListener(e -> updateSelectedMark());
        propertiesPanel.add(fontSizeSpinner);
        
        propertiesPanel.add(Box.createVerticalGlue());
        
        updatePropertiesPanel();
    }
    
    private void setupCanvas() {
        // Mouse handling for mark placement and selection
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (placementMode && selectedMarkType != null) {
                    // Place new mark
                    String defaultContent = getDefaultContent(selectedMarkType);
                    SimpleMark newMark = new SimpleMark(selectedMarkType, e.getX(), e.getY(), defaultContent);
                    marks.add(newMark);
                    
                    // Also add to DrawingCanvas if available (integration)
                    if (drawingCanvas != null) {
                        String markContent = defaultContent;
                        addMarkToCanvas(selectedMarkType, markContent, e.getX(), e.getY());
                    }
                    
                    // Select the new mark
                    selectMark(newMark);
                    
                    // Exit placement mode
                    placementMode = false;
                    selectedMarkType = null;
                    addMarkButton.setText("➕ Add Mark");
                    setCursor(Cursor.getDefaultCursor());
                    
                    repaint();
                    System.out.println("✅ Mark placed: " + newMark.type.name + " at (" + e.getX() + "," + e.getY() + ")");
                } else {
                    // Select existing mark
                    SimpleMark clickedMark = findMarkAt(e.getX(), e.getY());
                    selectMark(clickedMark);
                    repaint();
                }
            }
        });
    }
    
    private void showAddMarkMenu(ActionEvent e) {
        System.out.println("📋 Opening Add Mark menu (simple ThorX6 style)...");
        
        JPopupMenu menu = new JPopupMenu("Add Mark");
        
        // Add menu items for each mark type (simple list)
        for (MarkType type : MarkType.values()) {
            JMenuItem item = new JMenuItem(type.icon + " " + type.name);
            item.addActionListener(evt -> selectMarkTypeForPlacement(type));
            menu.add(item);
        }
        
        menu.show(addMarkButton, 0, addMarkButton.getHeight());
    }
    
    private void selectMarkTypeForPlacement(MarkType type) {
        selectedMarkType = type;
        placementMode = true;
        addMarkButton.setText("Click to place " + type.name);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        System.out.println("🎯 Selected mark type: " + type.name + " - Click on canvas to place");
    }
    
    private String getDefaultContent(MarkType type) {
        switch (type) {
            case TEXT: return "Sample Text";
            case BARCODE: return "123456789";
            case LOGO: return "LOGO";
            case DATE: return "Date";
            case SERIAL: return "001";
            case LINE: return "";
            case RECTANGLE: return "";
            case CIRCLE: return "";
            default: return "Content";
        }
    }
    
    private SimpleMark findMarkAt(int x, int y) {
        // Find mark at click position (reverse order for top-most)
        for (int i = marks.size() - 1; i >= 0; i--) {
            if (marks.get(i).contains(x, y)) {
                return marks.get(i);
            }
        }
        return null;
    }
    
    private void selectMark(SimpleMark mark) {
        // Deselect all marks
        for (SimpleMark m : marks) {
            m.selected = false;
        }
        
        // Select the clicked mark
        selectedMark = mark;
        if (mark != null) {
            mark.selected = true;
            System.out.println("🔍 Selected mark: " + mark.type.name + " at (" + mark.x + "," + mark.y + ")");
        }
        
        updatePropertiesPanel();
    }
    
    private void updatePropertiesPanel() {
        boolean hasSelection = selectedMark != null;
        contentField.setEnabled(hasSelection);
        fontSizeSpinner.setEnabled(hasSelection);
        
        if (hasSelection) {
            contentField.setText(selectedMark.content);
            fontSizeSpinner.setValue(selectedMark.font.getSize());
        }
    }
    
    private void updateSelectedMark() {
        if (selectedMark != null) {
            selectedMark.content = contentField.getText();
            int fontSize = (Integer) fontSizeSpinner.getValue();
            selectedMark.font = new Font(selectedMark.font.getName(), selectedMark.font.getStyle(), fontSize);
            repaint();
            System.out.println("📝 Updated mark: " + selectedMark.type.name + " - " + selectedMark.content);
        }
    }
    
    private void deleteSelectedMark() {
        if (selectedMark != null) {
            marks.remove(selectedMark);
            System.out.println("🗑️ Deleted mark: " + selectedMark.type.name);
            selectedMark = null;
            updatePropertiesPanel();
            repaint();
        }
    }
    
    // Integration method to add marks to existing DrawingCanvas
    private void addMarkToCanvas(MarkType markType, String content, int x, int y) {
        if (drawingCanvas == null) return;
        
        try {
            switch (markType) {
                case TEXT:
                    drawingCanvas.addMark("Text", content, 20, 100, "Arial-PLAIN-14");
                    break;
                case BARCODE:
                    drawingCanvas.addMark("Barcode", content, 20, 100, "Arial-PLAIN-12");
                    break;
                case LOGO:
                    drawingCanvas.addMark("Logo", content, 40, 40, "Arial-PLAIN-12");
                    break;
                case DATE:
                    drawingCanvas.addMark("Text", java.time.LocalDate.now().toString(), 20, 100, "Arial-PLAIN-14");
                    break;
                case SERIAL:
                    drawingCanvas.addMark("Text", "SN:" + content, 20, 100, "Arial-PLAIN-14");
                    break;
                case LINE:
                    drawingCanvas.addMark("Line", "", 2, 50, "Arial-PLAIN-12");
                    break;
                case RECTANGLE:
                    drawingCanvas.addMark("Rectangle", "", 50, 30, "Arial-PLAIN-12");
                    break;
                case CIRCLE:
                    drawingCanvas.addMark("Circle", "", 30, 30, "Arial-PLAIN-12");
                    break;
            }
            System.out.println("🔗 Integrated mark with existing canvas: " + markType.name);
        } catch (Exception e) {
            System.out.println("⚠️ Canvas integration error: " + e.getMessage());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw grid (optional)
        g2d.setColor(new Color(240, 240, 240));
        for (int x = 0; x < getWidth(); x += 20) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += 20) {
            g2d.drawLine(0, y, getWidth(), y);
        }
        
        // Draw all marks
        for (SimpleMark mark : marks) {
            mark.draw(g2d);
        }
        
        // Placement mode indicator
        if (placementMode) {
            g2d.setColor(Color.BLUE);
            g2d.drawString("Click to place " + selectedMarkType.name, 10, getHeight() - 20);
        }
    }
    
    // Test the simplified system
    public static void main(String[] args) {
        System.out.println("🚀 Starting Simplified ThorX6 Mark System");
        System.out.println("✅ Simple workflow: Add Mark → Select Type → Click to Place → Edit Properties");
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simplified ThorX6 Mark System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SimpleThorX6MarkSystem());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}