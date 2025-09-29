import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.*;

public class DrawingCanvas extends JPanel {

    // Modern color scheme for better visibility
    private static final Color LABEL_COLOR = new Color(44, 62, 80);   // Dark text for labels
    
    // ===============================================================================
    // SOFT CODING CONFIGURATION - COPY/PASTE SYSTEM
    // ===============================================================================
    
    // Copy/Paste format preservation configuration (soft-coded)
    private static final boolean ENABLE_COPY_PASTE_LOGGING = true;          // Enable detailed copy/paste logs
    private static final boolean ENABLE_FORMAT_PRESERVATION = true;         // Enable comprehensive format copying
    private static final boolean ENABLE_RULER_COPY_SUPPORT = true;          // Enable RulerMark copy support
    private static final boolean AUTO_SELECT_PASTED_OBJECTS = true;         // Auto-select pasted objects
    private static final int PASTE_OFFSET_X = 20;                          // X offset for pasted objects
    private static final int PASTE_OFFSET_Y = 20;                          // Y offset for pasted objects
    
    private final List<Mark> marks = new ArrayList<>();
    private final List<TextMark> textMarks = new ArrayList<>(); // Keep for backward compatibility
    private Mark activeMark = null;
    private Mark selectedMark = null;
    
    // Property strip integration
    private PropertyStrip propertyStrip = null;
    
    // Clipboard and undo functionality
    private final Stack<List<Mark>> undoStack = new Stack<>();
    private final Stack<List<Mark>> redoStack = new Stack<>();
    private Mark clipboardMark = null;
    
    // Mark properties
    private boolean sizeLocked = false;
    private boolean printDisabled = false;
    
    // Zoom and view functionality
    private double zoomLevel = 1.0;
    private boolean moveViewMode = false;
    private int viewOffsetX = 0;
    private int viewOffsetY = 0;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    
    // Preview and visualization settings
    private boolean gridVisible = true; // Enable ThorX6 grid by default
    private boolean materialBoundaryVisible = false;
    private boolean gridInfoVisible = true; // Show professional grid info box by default
    private boolean dotPreviewEnabled = false;
    
    // ==================== GRID LOCKING AND ZOOM CONTROL SYSTEM ====================
    
    // Grid locking state (prevents grid modifications)
    private boolean gridLocked = false;                         // Main grid lock state
    private double lockedZoomLevel = 1.0;                      // Locked zoom level
    private boolean zoomDisabled = false;                      // Zoom operations disabled state
    private String gridLockReason = "MARK_TAB_PROTECTION";     // Reason for grid lock
    private long gridLockTime = 0;                             // Time when grid was locked

    // Helper method to create labels with dark text
    private JLabel createDarkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LABEL_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }

    public DrawingCanvas() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, 280)); // Reduced height from 400 to 280 to make room for EngravedPanel features
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));

MouseAdapter mouseHandler = new MouseAdapter() {
    private ArcLettersMark.ControlHandle activeHandle = ArcLettersMark.ControlHandle.NONE;
    private DotMatrixControlHandle activeDotMatrixHandle = DotMatrixControlHandle.NONE;
    private TextMark.TextResizeHandle activeTextHandle = TextMark.TextResizeHandle.NONE;
    private BowTextMark.BowTextResizeHandle activeBowTextHandle = BowTextMark.BowTextResizeHandle.NONE;
    private RulerMark.RulerControlType activeRulerControl = null;
    private int dragStartX = 0; // Store initial X position for size control
    private int dragStartY = 0; // Store initial Y position for size control
    
    @Override
    public void mousePressed(MouseEvent e) {
        // Store initial mouse position for both selection and panning
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        
        // ===== INTELLIGENT PANNING ACTIVATION =====
        // Activate panning mode for intuitive navigation
        boolean shouldEnablePanning = 
            e.getButton() == MouseEvent.BUTTON2 || // Middle mouse button
            e.getButton() == MouseEvent.BUTTON3 || // Right mouse button
            (e.getButton() == MouseEvent.BUTTON1 && e.isControlDown()) || // Ctrl + Left click
            (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()); // Shift + Left click for pan mode
            
        if (shouldEnablePanning) {
            setMoveViewMode(true);
            System.out.printf("🖱️ Smart Panning Activated - Button: %d, Ctrl: %s, Shift: %s, Zoom: %.0f%%%n", 
                             e.getButton(), e.isControlDown(), e.isShiftDown(), zoomLevel * 100);
            return;
        }
        
        // If already in move view mode, handle panning
        if (moveViewMode) {
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
            return;
        }
        
        // Soft coding: Enhanced coordinate transformation with zoom and grid awareness
        CoordinateTransformationFix.ProcessedMouseEvent processedEvent = 
            CoordinateTransformationFix.ZoomGridMouseHandler.processMousePressed(
                e, zoomLevel, viewOffsetX, viewOffsetY, gridVisible, moveViewMode);
        
        if (processedEvent.isHandled) {
            return;
        }
        
        int transformedX = processedEvent.transformedX;
        int transformedY = processedEvent.transformedY;
        
        // Soft coding: Clear previous selection state
        updateSelectedMarkWithNotification(null);
        activeHandle = ArcLettersMark.ControlHandle.NONE;
        activeDotMatrixHandle = DotMatrixControlHandle.NONE;
        activeTextHandle = TextMark.TextResizeHandle.NONE;
        activeBowTextHandle = BowTextMark.BowTextResizeHandle.NONE;
        activeRulerControl = null;
        dragStartX = transformedX;
        dragStartY = transformedY;
        
        // Soft coding: Enhanced mark selection with priority handling
        Mark clickedMark = null;
        boolean isResizeClick = false;
        boolean isSpecialControl = false;
        
        // First pass: Check for special controls and resize handles
        for (Mark mark : marks) {
            // Special handling for ArcLettersMark controls
            if (mark instanceof ArcLettersMark) {
                ArcLettersMark arcMark = (ArcLettersMark) mark;
                activeHandle = arcMark.getControlHandle(transformedX, transformedY);
                
                if (activeHandle != ArcLettersMark.ControlHandle.NONE) {
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    isSpecialControl = true;
                    notifySelectionChanged();
                    repaint();
                    return;
                }
            }
            
            // Special handling for DotMatrixMark controls
            if (mark instanceof DotMatrixMark) {
                DotMatrixMark dotMatrix = (DotMatrixMark) mark;
                activeDotMatrixHandle = getDotMatrixControlHandle(dotMatrix, transformedX, transformedY);
                
                if (activeDotMatrixHandle == DotMatrixControlHandle.RESIZE) {
                    // Handle resize using standard resize mechanism
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    mark.startResize();
                    isSpecialControl = true;
                    notifySelectionChanged();
                    repaint();
                    return;
                } else if (activeDotMatrixHandle != DotMatrixControlHandle.NONE) {
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    isSpecialControl = true;
                    notifySelectionChanged();
                    repaint();
                    return;
                }
            }
            
            // Special handling for TextMark dynamic resize handles
            if (mark instanceof TextMark) {
                TextMark textMark = (TextMark) mark;
                activeTextHandle = textMark.getTextResizeHandle(transformedX, transformedY);
                
                if (activeTextHandle != TextMark.TextResizeHandle.NONE) {
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    textMark.startDynamicResize(transformedX, transformedY, activeTextHandle);
                    dragStartX = transformedX;
                    dragStartY = transformedY;
                    isSpecialControl = true;
                    notifySelectionChanged();
                    repaint();
                    return;
                }
            }
            
            // Special handling for BowTextMark control handles (user-friendly design)
            if (mark instanceof BowTextMark) {
                BowTextMark bowTextMark = (BowTextMark) mark;
                activeBowTextHandle = bowTextMark.getBowTextResizeHandle(transformedX, transformedY);
                
                if (activeBowTextHandle != BowTextMark.BowTextResizeHandle.NONE) {
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    dragStartX = transformedX;
                    dragStartY = transformedY;
                    isSpecialControl = true;
                    notifySelectionChanged();
                    repaint();
                    return;
                }
            }
            
            // Special handling for RulerMark intelligent control handles (comprehensive drag system)
            if (mark instanceof RulerMark) {
                RulerMark rulerMark = (RulerMark) mark;
                activeRulerControl = rulerMark.detectControlHandle(transformedX, transformedY);
                
                if (activeRulerControl != null) {
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    rulerMark.startDrag(transformedX, transformedY, activeRulerControl);
                    dragStartX = transformedX;
                    dragStartY = transformedY;
                    isSpecialControl = true;
                    System.out.println("🎯 RulerMark control activated: " + activeRulerControl);
                    notifySelectionChanged();
                    repaint();
                    return;
                }
            }
            
            // Check for resize handles (higher priority than normal clicks)
            if (mark.overResizeHandle(transformedX, transformedY)) {
                clickedMark = mark;
                isResizeClick = true;
                break; // Exit loop early for resize
            }
        }
        
        // Second pass: Handle resize clicks
        if (isResizeClick && clickedMark != null) {
            // Soft coding: Canvas-level Lock Size validation
            if (RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY && 
                RugrelDropdownConfig.ENABLE_INTELLIGENT_LOCK_SIZE && 
                RugrelDropdownConfig.ENABLE_PER_MARK_SIZE_LOCKING) {
                
                // Double-check that this mark can be resized
                if (clickedMark.lockSize && RugrelDropdownConfig.PREVENT_RESIZE_OPERATIONS_WHEN_LOCKED) {
                    if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                        String markName = clickedMark.name != null ? clickedMark.name : "Mark";
                        JOptionPane.showMessageDialog(DrawingCanvas.this, 
                            markName + " size is locked. Cannot resize.", 
                            "Lock Size", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                    return; // Block resize operation
                }
            }
            
            activeMark = clickedMark;
            // Set the clicked mark as selected and update property strip
            updateSelectedMarkWithNotification(clickedMark);
            clickedMark.startResize(); // This will also check lockSize internally
            notifySelectionChanged();
            repaint();
            return;
        }
        
        // Third pass: Handle normal mark selection and dragging
        for (Mark mark : marks) {
            // Special handling for GraphMark
            if (mark instanceof GraphMark) {
                GraphMark graphMark = (GraphMark) mark;
                if (graphMark.contains(transformedX, transformedY)) {
                    activeMark = mark;
                    updateSelectedMark(mark);  // Update PropertyStrip properly
                    mark.startDrag(transformedX, transformedY);
                    notifySelectionChanged();
                    repaint();
                    return;
                }
            }
            
            // Soft coding: Enhanced mark detection with improved hit testing
            if (mark.contains(transformedX, transformedY)) {
                activeMark = mark;
                updateSelectedMark(mark);  // Update PropertyStrip properly
                mark.startDrag(transformedX, transformedY);
                notifySelectionChanged();
                repaint();
                return;
            }
        }
        notifySelectionChanged(); // Notify even when selection is cleared
        repaint(); // Refresh to clear selection highlights
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // ===== INTELLIGENT PANNING DEACTIVATION =====
        // Automatically deactivate panning when appropriate mouse button is released
        if (moveViewMode) {
            boolean shouldDeactivatePanning = 
                e.getButton() == MouseEvent.BUTTON2 || // Middle mouse button released
                e.getButton() == MouseEvent.BUTTON3 || // Right mouse button released  
                e.getButton() == MouseEvent.BUTTON1;   // Any left click released (Ctrl/Shift may be up)
                
            if (shouldDeactivatePanning) {
                setMoveViewMode(false);
                System.out.printf("🖱️ Smart Panning Deactivated - Button: %d released, Zoom: %.0f%%%n", 
                                 e.getButton(), zoomLevel * 100);
                return;
            }
        }
        
        // Soft coding: Enhanced mouse release with proper cleanup
        if (activeMark != null) {
            try {
                activeMark.stopDrag();
                activeMark.stopResize();
            } catch (Exception ex) {
                System.err.println("Error during mouse release: " + ex.getMessage());
            } finally {
                // Always clean up state regardless of errors
                // Special cleanup for RulerMark drag operations
                if (activeMark instanceof RulerMark && activeRulerControl != null) {
                    ((RulerMark) activeMark).endDrag();
                }
                
                activeMark = null;
                activeHandle = ArcLettersMark.ControlHandle.NONE;
                activeDotMatrixHandle = DotMatrixControlHandle.NONE;
                activeTextHandle = TextMark.TextResizeHandle.NONE;
                activeBowTextHandle = BowTextMark.BowTextResizeHandle.NONE;
                activeRulerControl = null;
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Use same coordinate transformation as mousePressed for consistency
        CoordinateTransformationFix.ProcessedMouseEvent processedEvent = 
            CoordinateTransformationFix.ZoomGridMouseHandler.processMousePressed(
                e, zoomLevel, viewOffsetX, viewOffsetY, gridVisible, moveViewMode);
        
        if (processedEvent.isHandled) {
            return;
        }
        
        int transformedX = processedEvent.transformedX;
        int transformedY = processedEvent.transformedY;
        
        // ===============================================================================
        // CURSOR COORDINATE TRACKING - POINTWISE COORDINATES (0-10 SCALE)
        // ===============================================================================
        if (propertyStrip != null) {
            if (RugrelDropdownConfig.ENABLE_POINTWISE_COORDINATES && RugrelDropdownConfig.ENABLE_COORDINATE_CURSOR_TRACKING) {
                // Convert pixel coordinates to pointwise coordinates (0-10 scale)
                double[] pointwise = pixelToPointwise(transformedX, transformedY);
                double pointwiseX = pointwise[0];
                double pointwiseY = pointwise[1];
                
                // Update property strip with pointwise coordinates
                propertyStrip.updateCursorCoordinates(pointwiseX, pointwiseY);
                
                // Log coordinate changes if enabled
                if (RugrelDropdownConfig.LOG_COORDINATE_CHANGES && Math.random() < 0.005) { // Log 0.5% of updates to avoid spam
                    System.out.println("📍 Pointwise Coordinates: X=" + 
                                     String.format(RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT, pointwiseX) + 
                                     ", Y=" + String.format(RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT, pointwiseY));
                }
            } else {
                // Use original pixel coordinates if pointwise system is disabled
                propertyStrip.updateCursorCoordinates(transformedX, transformedY);
            }
        }
        
        // Check if mouse is over any control handles and change cursor accordingly
        boolean cursorChanged = false;
        
        for (Mark mark : marks) {
            if (mark == selectedMark && mark instanceof ArcLettersMark) {
                ArcLettersMark arcMark = (ArcLettersMark) mark;
                // Use transformed coordinates for accurate handle detection
                ArcLettersMark.ControlHandle handle = arcMark.getControlHandle(transformedX, transformedY);
                
                switch (handle) {
                    case GREEN_BOX:
                        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        cursorChanged = true;
                        break;
                    case ROTATION:
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        cursorChanged = true;
                        break;
                    case START_ANGLE:
                    case END_ANGLE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                        cursorChanged = true;
                        break;
                    case RADIUS:
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case TEXT_SIZE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case RESIZE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                }
                
                if (cursorChanged) break;
            }
            
            // Handle DotMatrix cursor changes
            if (mark == selectedMark && mark instanceof DotMatrixMark) {
                DotMatrixMark dotMatrix = (DotMatrixMark) mark;
                DotMatrixControlHandle handle = getDotMatrixControlHandle(dotMatrix, transformedX, transformedY);
                
                switch (handle) {
                    case DOT_PITCH:
                    case DOT_DIAMETER:
                    case MATRIX_SIZE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case RESIZE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                }
                
                if (cursorChanged) break;
            }
            
            // Handle TextMark cursor changes for dynamic resize handles
            if (mark == selectedMark && mark instanceof TextMark) {
                TextMark textMark = (TextMark) mark;
                TextMark.TextResizeHandle handle = textMark.getTextResizeHandle(transformedX, transformedY);
                
                switch (handle) {
                    case TOP:
                    case BOTTOM:
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case LEFT:
                    case RIGHT:
                        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case TOP_LEFT:
                    case BOTTOM_RIGHT:
                        setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case TOP_RIGHT:
                    case BOTTOM_LEFT:
                        setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                }
                
                if (cursorChanged) break;
            }
            
            // Handle BowTextMark cursor changes for user-friendly control handles
            if (mark == selectedMark && mark instanceof BowTextMark) {
                BowTextMark bowTextMark = (BowTextMark) mark;
                BowTextMark.BowTextResizeHandle handle = bowTextMark.getBowTextResizeHandle(transformedX, transformedY);
                
                switch (handle) {
                    case TOP:
                    case BOTTOM:
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case LEFT:
                    case RIGHT:
                        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case TOP_LEFT:
                    case BOTTOM_RIGHT:
                        setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                    case TOP_RIGHT:
                    case BOTTOM_LEFT:
                        setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        cursorChanged = true;
                        break;
                }
                
                if (cursorChanged) break;
            }
            
            // Handle RulerMark cursor changes for intelligent control handles
            if (mark == selectedMark && mark instanceof RulerMark) {
                RulerMark rulerMark = (RulerMark) mark;
                RulerMark.RulerControlType control = rulerMark.detectControlHandle(transformedX, transformedY);
                
                if (control != null) {
                    switch (control) {
                        case POSITION:
                            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                            cursorChanged = true;
                            break;
                        case LENGTH:
                            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                            cursorChanged = true;
                            break;
                        case HEIGHT:
                            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                            cursorChanged = true;
                            break;
                        case SCALE_SIZE:
                            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                            cursorChanged = true;
                            break;
                        case NUMBER_OFFSET:
                            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                            cursorChanged = true;
                            break;
                    }
                    
                    if (cursorChanged) break;
                }
            }
        }
        
        // Reset cursor if not over any control handle
        if (!cursorChanged) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // ===== INTELLIGENT VIEW PANNING =====
        if (moveViewMode) {
            int deltaX = e.getX() - lastMouseX;
            int deltaY = e.getY() - lastMouseY;
            
            // Apply smart panning with momentum and boundaries
            int newViewOffsetX = viewOffsetX + deltaX;
            int newViewOffsetY = viewOffsetY + deltaY;
            
            // Calculate smart boundaries based on zoom level and canvas size
            int canvasWidth = getWidth();
            int canvasHeight = getHeight();
            int maxOffsetX = (int)(canvasWidth * zoomLevel * 0.5);
            int maxOffsetY = (int)(canvasHeight * zoomLevel * 0.5);
            int minOffsetX = -maxOffsetX;
            int minOffsetY = -maxOffsetY;
            
            // Apply intelligent boundary constraints
            if (zoomLevel > 1.0) {
                // When zoomed in, allow more generous panning
                newViewOffsetX = Math.max(minOffsetX, Math.min(maxOffsetX, newViewOffsetX));
                newViewOffsetY = Math.max(minOffsetY, Math.min(maxOffsetY, newViewOffsetY));
            } else {
                // When zoomed out, limit panning to prevent excessive movement
                int limitedRangeX = (int)(canvasWidth * 0.3);
                int limitedRangeY = (int)(canvasHeight * 0.3);
                newViewOffsetX = Math.max(-limitedRangeX, Math.min(limitedRangeX, newViewOffsetX));
                newViewOffsetY = Math.max(-limitedRangeY, Math.min(limitedRangeY, newViewOffsetY));
            }
            
            // Update view offsets with smart constraints
            viewOffsetX = newViewOffsetX;
            viewOffsetY = newViewOffsetY;
            
            // Update mouse tracking
            lastMouseX = e.getX();
            lastMouseY = e.getY();
            
            // Provide real-time feedback for large movements
            if (Math.abs(deltaX) > 5 || Math.abs(deltaY) > 5) {
                System.out.printf("🔄 Panning: Offset(%d,%d) Delta(%+d,%+d) Zoom:%.0f%%\r", 
                                 viewOffsetX, viewOffsetY, deltaX, deltaY, zoomLevel * 100);
            }
            
            // Smooth repaint for fluid panning
            repaint();
            return;
        }
        
        // Soft coding: Enhanced drag handling with zoom and grid aware coordinate transformation
        if (activeMark != null) {
            // Enhanced coordinate transformation with zoom and grid awareness
            CoordinateTransformationFix.ProcessedMouseEvent processedEvent = 
                CoordinateTransformationFix.ZoomGridMouseHandler.processMouseDragged(
                    e, zoomLevel, viewOffsetX, viewOffsetY, gridVisible, moveViewMode);
            
            if (processedEvent.isHandled) {
                return;
            }
            
            int transformedX = processedEvent.transformedX;
            int transformedY = processedEvent.transformedY;
            
            // Soft coding: Boundary validation using enhanced bounds checking
            if (!processedEvent.isWithinBounds) {
                System.out.println("⚠️ Drag operation outside valid bounds, constraining coordinates");
                transformedX = Math.max(-1000, Math.min(2000, transformedX));
                transformedY = Math.max(-1000, Math.min(2000, transformedY));
            }
            
            // Handle ArcLettersMark controls
            if (activeMark instanceof ArcLettersMark && activeHandle != ArcLettersMark.ControlHandle.NONE) {
                ArcLettersMark arcMark = (ArcLettersMark) activeMark;
                
                switch (activeHandle) {
                    case ROTATION:
                        arcMark.rotate360(transformedX, transformedY);
                        break;
                    case START_ANGLE:
                        arcMark.adjustStartAngle(transformedX, transformedY);
                        break;
                    case END_ANGLE:
                        arcMark.adjustEndAngle(transformedX, transformedY);
                        break;
                    case RADIUS:
                        arcMark.adjustRadius(transformedX, transformedY);
                        break;
                    case TEXT_SIZE:
                        arcMark.adjustTextSize(transformedX, transformedY, dragStartX, dragStartY);
                        break;
                    case GREEN_BOX:
                        arcMark.adjustSizeByGreenBox(transformedX, transformedY, dragStartX, dragStartY);
                        break;
                    case RESIZE:
                        arcMark.resizeTo(transformedX, transformedY);
                        break;
                }
            }
            // Handle DotMatrixMark controls
            else if (activeMark instanceof DotMatrixMark && activeDotMatrixHandle != DotMatrixControlHandle.NONE) {
                DotMatrixMark dotMatrix = (DotMatrixMark) activeMark;
                int deltaY = transformedY - dragStartY;
                
                switch (activeDotMatrixHandle) {
                    case DOT_PITCH:
                        int newPitch = dotMatrix.getDotPitch() + deltaY / 5;
                        dotMatrix.setDotPitch(newPitch);
                        dragStartY = transformedY; // Update for continuous dragging
                        break;
                    case DOT_DIAMETER:
                        int newDiameter = dotMatrix.getDotDiameter() + deltaY / 5;
                        dotMatrix.setDotDiameter(newDiameter);
                        dragStartY = transformedY;
                        break;
                    case MATRIX_SIZE:
                        // Cycle through standard matrix sizes
                        if (Math.abs(deltaY) > 15) { // Threshold for size change
                            int currentSize = dotMatrix.getMatrixSize();
                            int newSize = deltaY > 0 ? currentSize + 2 : currentSize - 2;
                            newSize = Math.max(10, Math.min(24, newSize)); // Limit range
                            dotMatrix.setMatrixSize(newSize);
                            dragStartY = transformedY;
                        }
                        break;
                    case RESIZE:
                        // This case is now handled by the standard resize mechanism
                        dotMatrix.resizeTo(transformedX, transformedY);
                        break;
                }
            }
            // Handle TextMark enhanced multi-directional control handle dragging
            else if (activeMark instanceof TextMark && activeTextHandle != TextMark.TextResizeHandle.NONE) {
                TextMark textMark = (TextMark) activeMark;
                int deltaX = transformedX - dragStartX;
                int deltaY = transformedY - dragStartY;
                textMark.resizeText(deltaX, deltaY, activeTextHandle);
                dragStartX = transformedX;
                dragStartY = transformedY;
                // Update PropertyStrip with new coordinates during resize
                if (propertyStrip != null) {
                    propertyStrip.setSelectedMark(textMark);
                }
            }
            // Handle BowTextMark user-friendly control handle dragging
            else if (activeMark instanceof BowTextMark && activeBowTextHandle != BowTextMark.BowTextResizeHandle.NONE) {
                BowTextMark bowTextMark = (BowTextMark) activeMark;
                int deltaX = transformedX - dragStartX;
                int deltaY = transformedY - dragStartY;
                bowTextMark.resizeBowText(deltaX, deltaY, activeBowTextHandle);
                dragStartX = transformedX;
                dragStartY = transformedY;
                // Update PropertyStrip with new coordinates during resize
                if (propertyStrip != null) {
                    propertyStrip.setSelectedMark(bowTextMark);
                }
            }
            // Handle RulerMark intelligent control handle dragging (comprehensive system)
            else if (activeMark instanceof RulerMark && activeRulerControl != null) {
                RulerMark rulerMark = (RulerMark) activeMark;
                rulerMark.processDrag(transformedX, transformedY);
                // Update PropertyStrip with new properties during drag
                if (propertyStrip != null) {
                    propertyStrip.setSelectedMark(rulerMark);
                }
            }
            // Handle GraphMark resize
            else if (activeMark instanceof GraphMark && activeMark.isResizing()) {
                GraphMark graphMark = (GraphMark) activeMark;
                graphMark.resizeTo(transformedX, transformedY);
            } 
            // Soft coding: Enhanced standard drag/resize handling with validation
            else {
                try {
                    // Standard drag/resize handling with error prevention
                    if (activeMark.isDragging() && activeMark.getCanDrag()) {
                        activeMark.dragTo(transformedX, transformedY);
                        // Update PropertyStrip during drag operations
                        if (propertyStrip != null) {
                            propertyStrip.setSelectedMark(activeMark);
                        }
                    } else if (activeMark.isResizing() && activeMark.getCanResize()) {
                        activeMark.resizeTo(transformedX, transformedY);
                        // Update PropertyStrip during resize operations  
                        if (propertyStrip != null) {
                            propertyStrip.setSelectedMark(activeMark);
                        }
                    }
                } catch (Exception ex) {
                    // Soft coding: Error recovery - stop operations if something goes wrong
                    System.err.println("Error during drag/resize operation: " + ex.getMessage());
                    if (activeMark != null) {
                        activeMark.stopDrag();
                        activeMark.stopResize();
                    }
                }
            }
            repaint();
        }
    }
};

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        
        // Add intelligent mouse wheel zoom functionality
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseWheelZoom(e);
            }
        });
        
        // Initialize fixed grid state if configured
        initializeFixedGrid();
    }

    /**
     * Intelligent mouse wheel zoom implementation with smart center point calculation
     * Features:
     * - Zoom toward mouse cursor position (natural zoom behavior)
     * - Adaptive zoom steps (finer control at high zoom levels)
     * - Zoom range limiting (0.1x to 10x)
     * - Grid-aware zoom increments
     * - Visual feedback with zoom level indicator
     * - Smooth coordinate transformation
     */
    private void handleMouseWheelZoom(MouseWheelEvent e) {
        // Check if zoom is disabled (Grid Fixed - No Zoom Allowed)
        if (isZoomDisabled() || RugrelDropdownConfig.DISABLE_ALL_ZOOM_MOUSE_ACTIONS || 
            !RugrelDropdownConfig.ENABLE_MOUSE_WHEEL_ZOOM || RugrelDropdownConfig.GRID_COMPLETELY_FIXED) {
            if (RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE) {
                System.out.println("🚫 Mouse wheel zoom blocked - Grid is fixed, no zoom operations allowed");
            }
            return; // Block all mouse wheel zoom operations
        }
        
        // Get mouse position for zoom center calculation
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        // Calculate zoom delta with intelligent step sizing
        double zoomFactor;
        int wheelRotation = e.getWheelRotation();
        
        // Adaptive zoom steps - finer control at higher zoom levels
        if (zoomLevel >= 5.0) {
            zoomFactor = wheelRotation < 0 ? 1.1 : 0.9; // Fine steps at high zoom
        } else if (zoomLevel >= 2.0) {
            zoomFactor = wheelRotation < 0 ? 1.15 : 0.85; // Medium steps
        } else {
            zoomFactor = wheelRotation < 0 ? 1.2 : 0.8; // Coarse steps at low zoom
        }
        
        // Calculate new zoom level with bounds checking
        double newZoomLevel = zoomLevel * zoomFactor;
        
        // Enforce zoom limits (0.1x to 10x range)
        if (newZoomLevel < 0.1) {
            newZoomLevel = 0.1;
        } else if (newZoomLevel > 10.0) {
            newZoomLevel = 10.0;
        }
        
        // Only proceed if zoom level actually changes
        if (Math.abs(newZoomLevel - zoomLevel) < 0.001) {
            return;
        }
        
        // Calculate zoom center point in world coordinates
        // This ensures zoom happens toward the mouse cursor
        double worldX = (mouseX - viewOffsetX) / zoomLevel;
        double worldY = (mouseY - viewOffsetY) / zoomLevel;
        
        // Update zoom level
        double oldZoomLevel = zoomLevel;
        zoomLevel = newZoomLevel;
        
        // Adjust view offsets to maintain zoom center point
        viewOffsetX = (int)(mouseX - worldX * zoomLevel);
        viewOffsetY = (int)(mouseY - worldY * zoomLevel);
        
        // Smart grid snapping for common zoom levels
        if (Math.abs(zoomLevel - 1.0) < 0.05) {
            zoomLevel = 1.0; // Snap to 100%
        } else if (Math.abs(zoomLevel - 2.0) < 0.1) {
            zoomLevel = 2.0; // Snap to 200%
        } else if (Math.abs(zoomLevel - 0.5) < 0.05) {
            zoomLevel = 0.5; // Snap to 50%
        }
        
        // Visual feedback
        String zoomPercent = String.format("%.0f", zoomLevel * 100);
        String zoomDirection = zoomLevel > oldZoomLevel ? "↗" : "↙";
        System.out.printf("🔍 Smart Zoom: %s%% %s (Mouse: %d,%d) Grid: %s%n", 
                         zoomPercent, zoomDirection, mouseX, mouseY, 
                         gridVisible ? "Visible" : "Hidden");
        
        // Update display
        revalidate();
        repaint();
        
        // Update property strip if available (refresh selected mark display)
        if (propertyStrip != null && selectedMark != null) {
            propertyStrip.setSelectedMark(selectedMark);
        }
    }

    // Helper method to identify all barcode types
    private boolean isBarcodeType(String type) {
        if (type == null) return false;
        
        // List of all supported barcode types
        String[] barcodeTypes = {
            "Code 128", "QR Code", "EAN-13", "Code 39", "Data Matrix", 
            "PDF417", "UPC-A", "Code 93", "ITF", "Codabar", "Aztec Code",
            "MaxiCode", "MSI Plessey", "POSTNET", "Code 11", "GS1-128",
            "EAN-8", "UPC-E", "Pharma Code", "MSI"
        };
        
        // Check if the type matches any of the known barcode types
        for (String barcodeType : barcodeTypes) {
            if (type.equals(barcodeType)) {
                return true;
            }
        }
        
        // Also check for partial matches for legacy support
        return type.contains("Barcode") || type.contains("Code") || 
               type.contains("QR") || type.contains("Matrix") || 
               type.contains("PDF") || type.contains("UPC") || 
               type.contains("EAN") || type.contains("ITF");
    }

    public void addMark(String type) {
        addMark(type, "ABC123");
    }
    
    public void addMark(String type, String content) {
        addMark(type, content, 12, 100, "Arial");
    }
    
    // Direct Mark object addition for testing purposes (soft coding enabled)
    public void addMarkObject(Mark mark) {
        if (mark == null) {
            if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
                System.out.println("❌ Cannot add null mark to canvas");
            }
            return;
        }
        
        marks.add(mark);
        
        // Handle specific mark type registrations
        if (mark instanceof TextMark) {
            textMarks.add((TextMark) mark);
        }
        
        if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
            System.out.println("✅ " + mark.getClass().getSimpleName() + " added to canvas at (" + mark.x + "," + mark.y + ")");
        }
        
        repaint();
    }
    
    public void addMark(String type, String content, int height, int width, String font) {
        saveStateForUndo(); // Save current state before adding
        
        // Soft coding: Enhanced mark creation with logging and validation
        Mark newMark = null;
        
        try {
            System.out.println("Creating mark: " + type + " with content: " + content);
            
            if (type.equals("Text") || type.contains("🅰 Text")) {
                newMark = new TextMark(100, 100, content);
                marks.add(newMark);
                textMarks.add((TextMark) newMark); // Keep backward compatibility
                System.out.println("TextMark created successfully");
            } else if (type.equals("BowText") || type.contains("Bow Text")) {
                newMark = new BowTextMark(100, 100, content);
                marks.add(newMark);
                System.out.println("BowTextMark created successfully");
            } else if (type.equals("ArcLetters") || type.contains("Arc Letters")) {
                String letters = (content != null && !content.trim().isEmpty()) ? content.trim() : "ABCDE";
                newMark = new ArcLettersMark(100, 100, letters);
                marks.add(newMark);
                System.out.println("ArcLettersMark created successfully");
            } else if (type.contains("Rectangle")) {
                newMark = new RectangleMark(100, 100);
                marks.add(newMark);
                System.out.println("RectangleMark created successfully");
            } else if (type.contains("Line")) {
                newMark = new LineMark(100, 100);
                marks.add(newMark);
                System.out.println("LineMark created successfully");
            } else if (type.contains("Dot Matrix")) {
                String data = (content != null && !content.trim().isEmpty()) ? content.trim() : "MFR123456";
                newMark = new DotMatrixMark(100, 100, data);
                marks.add(newMark);
                System.out.println("DotMatrixMark created successfully");
            } else if (type.contains("Graph")) {
                newMark = new GraphMark(100, 100);
                marks.add(newMark);
                System.out.println("GraphMark created successfully");
                
                // Auto-activate image upload dialog when GraphMark is created
                GraphMark graphMark = (GraphMark) newMark;
                SwingUtilities.invokeLater(() -> {
                    System.out.println("🚀 Auto-opening image upload dialog for new GraphMark...");
                    graphMark.openImageUploadDialog();
                    repaint(); // Refresh display after image assignment
                });
            } else if (type.contains("Farzi")) {
                String text = (content != null && !content.trim().isEmpty()) ? content.trim() : "FARZI123";
                newMark = new FarziMark(100, 100, text);
                marks.add(newMark);
                System.out.println("FarziMark created successfully");
            } else if (type.contains("Avoid Point")) {
                newMark = new AvoidPointMark(100, 100);
                marks.add(newMark);
                System.out.println("AvoidPointMark created successfully");
            } else if (isBarcodeType(type)) {
                String data = (content != null && !content.trim().isEmpty()) ? content.trim() : "BARCODE123";
                BarcodeMark barcodeMark = new BarcodeMark(100, 100, type, data);
                marks.add(barcodeMark);
                System.out.println("BarcodeMark created successfully: " + type);
                newMark = barcodeMark;
            } else if (type.contains("Path")) {
                // TODO: Implement path marks
                JOptionPane.showMessageDialog(this, "Path marks coming soon!", "Feature Not Implemented", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Mark type '" + type + "' not implemented yet!", "Feature Not Implemented", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Soft coding: Configure new mark for optimal interaction
            if (newMark != null) {
                newMark.setCanDrag(true);
                newMark.setCanResize(true);
                System.out.println("Mark configured for drag and resize. Total marks: " + marks.size());
                
                // Auto-select the newly created mark for immediate interaction
                selectedMark = newMark;
                notifySelectionChanged();
            }
            
        } catch (Exception ex) {
            System.err.println("Error creating mark: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating mark: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create(); // Create a copy for transformations
        
        // Apply zoom and view offset transformations
        g2d.translate(viewOffsetX, viewOffsetY);
        g2d.scale(zoomLevel, zoomLevel);
        
        // Enable antialiasing for better quality at different zoom levels
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw grid if enabled
        if (gridVisible) {
            drawPreviewGrid(g2d);
        }
        
        // Draw material boundary if enabled
        if (materialBoundaryVisible) {
            drawMaterialBoundary(g2d);
        }
        
        // Draw all marks
        for (Mark mark : marks) {
            mark.draw(g2d, mark == selectedMark);
        }
        
        // Draw dot preview if enabled
        if (dotPreviewEnabled) {
            drawDotPreview(g2d);
        }
        
        g2d.dispose(); // Clean up the graphics context
        
        // Draw professional grid info box in top-right corner
        if (gridInfoVisible && gridVisible && RugrelDropdownConfig.ENABLE_PROFESSIONAL_GRID_INFO_BOX) {
            drawGridInfoBox(g);
        }
        
        // Draw grid lock visual indicators
        if (isGridLocked() && RugrelDropdownConfig.SHOW_GRID_LOCK_INDICATOR) {
            drawGridLockIndicators(g);
        }
        
        // Enhanced zoom level indicator with high-zoom status information
        if (zoomLevel != 1.0) {
            g.setColor(zoomLevel >= 5.0 ? Color.RED : zoomLevel >= 3.0 ? Color.ORANGE : Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            
            // Enhanced zoom display with precision info for high zoom levels
            String zoomText;
            if (zoomLevel >= 5.0) {
                zoomText = String.format("Zoom: %.1f%% (High Precision Mode)", zoomLevel * 100);
            } else if (zoomLevel >= 3.0) {
                zoomText = String.format("Zoom: %.1f%% (Detail Mode)", zoomLevel * 100);
            } else {
                zoomText = String.format("Zoom: %.0f%%", zoomLevel * 100);
            }
            
            g.drawString(zoomText, 10, getHeight() - 10);
            
            // Add performance tip for very high zoom levels
            if (zoomLevel >= 5.0) {
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString("Tip: Use precise mouse movements at 500% zoom", 10, getHeight() - 25);
            }
        }
    }

    // Clipboard and Undo Operations
    private List<Mark> copyAllMarks(List<Mark> originalMarks) {
        List<Mark> copies = new ArrayList<>();
        for (Mark mark : originalMarks) {
            // Create a deep copy of each mark with COMPLETE format preservation
            if (mark instanceof TextMark) {
                TextMark textMark = (TextMark) mark;
                TextMark copy = new TextMark(textMark.x, textMark.y, textMark.getText());
                copy.width = textMark.width;
                copy.height = textMark.height;
                
                // ENHANCED: Copy ALL text formatting properties (soft-coded approach)
                copy.setFont(textMark.getFont());
                copy.setCharacterWidth(textMark.getCharacterWidth());
                copy.setLineSpacing(textMark.getLineSpacing());
                
                if (ENABLE_COPY_PASTE_LOGGING) {
                    System.out.println("📝 TextMark copied with format: font=" + textMark.getFont().getName() + 
                                     " size=" + textMark.getFont().getSize() + 
                                     " charWidth=" + textMark.getCharacterWidth() + 
                                     " lineSpacing=" + textMark.getLineSpacing());
                }
                copies.add(copy);
            } else if (mark instanceof BowTextMark) {
                BowTextMark bowMark = (BowTextMark) mark;
                BowTextMark copy = new BowTextMark(bowMark.x, bowMark.y, bowMark.getText());
                copy.width = bowMark.width;
                copy.height = bowMark.height;
                copy.setCurvature(bowMark.getCurvature());
                copy.setBowUp(bowMark.isBowUp());
                copies.add(copy);
            } else if (mark instanceof ArcLettersMark) {
                ArcLettersMark arcMark = (ArcLettersMark) mark;
                ArcLettersMark copy = new ArcLettersMark(arcMark.x, arcMark.y, arcMark.getLetters());
                copy.width = arcMark.width;
                copy.height = arcMark.height;
                copy.setStartAngle(arcMark.getStartAngle());
                copy.setArcAngle(arcMark.getArcAngle());
                copy.setRadius(arcMark.getRadius());
                copy.setShowArc(arcMark.isShowArc());
                if (arcMark.getFont() != null) {
                    copy.setFont(arcMark.getFont());
                }
                copies.add(copy);
            } else if (mark instanceof RulerMark) {
                // ENHANCED: RulerMark complete format preservation (was previously missing!)
                RulerMark rulerMark = (RulerMark) mark;
                RulerMark copy = new RulerMark(rulerMark.x, rulerMark.y);
                copy.width = rulerMark.width;
                copy.height = rulerMark.height;
                
                // Copy ALL ruler formatting properties using soft-coded getters
                copy.setRulerLength(rulerMark.getRulerLength());
                copy.setRulerHeight(rulerMark.getRulerHeight());
                copy.setScaleSize(rulerMark.getScaleSize());
                copy.setScaleValue(rulerMark.getScaleValue());
                copy.setStartValue(rulerMark.getStartValue());
                copy.setRadius(rulerMark.getRadius());
                copy.setSpanLength(rulerMark.getSpanLength());
                
                // Copy text formatting properties
                copy.setFontFamily(rulerMark.getFontFamily());
                copy.setFontSize(rulerMark.getFontSize());
                copy.setFontStyle(rulerMark.getFontStyle());
                copy.setCharacterWidth(rulerMark.getCharacterWidth());
                copy.setLineSpacing(rulerMark.getLineSpacing());
                copy.setTextOffsetX(rulerMark.getTextOffsetX());
                copy.setTextOffsetY(rulerMark.getTextOffsetY());
                copy.setHorizontalAlignment(rulerMark.getHorizontalAlignment());
                copy.setVerticalAlignment(rulerMark.getVerticalAlignment());
                copy.setTextColor(rulerMark.getTextColor());
                
                // Copy display options
                copy.setShowNumbers(rulerMark.isShowNumbers());
                copy.setShowMiddleNumbers(rulerMark.isShowMiddleNumbers());
                copy.setShowMinorMarks(rulerMark.isShowMinorMarks());
                copy.setShowMiddleMarks(rulerMark.isShowMiddleMarks());
                copy.setShowMajorMarks(rulerMark.isShowMajorMarks());
                
                if (ENABLE_COPY_PASTE_LOGGING) {
                    System.out.println("📏 RulerMark copied with complete format: font=" + rulerMark.getFontFamily() + 
                                     " size=" + rulerMark.getFontSize() + 
                                     " charWidth=" + rulerMark.getCharacterWidth() + 
                                     " alignment=" + rulerMark.getHorizontalAlignment());
                }
                copies.add(copy);
            } else if (mark instanceof GraphMark) {
                GraphMark graphMark = (GraphMark) mark;
                GraphMark copy = new GraphMark(graphMark.x, graphMark.y);
                copy.width = graphMark.width;
                copy.height = graphMark.height;
                copy.setBorderColor(graphMark.getBorderColor());
                copies.add(copy);
            } else if (mark instanceof RectangleMark) {
                RectangleMark rectMark = (RectangleMark) mark;
                RectangleMark copy = new RectangleMark(rectMark.x, rectMark.y);
                copy.width = rectMark.width;
                copy.height = rectMark.height;
                copies.add(copy);
            } else if (mark instanceof LineMark) {
                LineMark lineMark = (LineMark) mark;
                LineMark copy = new LineMark(lineMark.x, lineMark.y);
                copy.width = lineMark.width;
                copy.height = lineMark.height;
                copies.add(copy);
            } else if (mark instanceof FarziMark) {
                FarziMark farziMark = (FarziMark) mark;
                FarziMark copy = new FarziMark(farziMark.x, farziMark.y, farziMark.getText());
                copy.width = farziMark.width;
                copy.height = farziMark.height;
                copy.setCharHeight(farziMark.getCharHeight());
                copy.setCharWidth(farziMark.getCharWidth());
                copy.setCharSpacing(farziMark.getCharSpacing());
                copy.setShowGrid(farziMark.isShowGrid());
                copy.setStrokeWidth(farziMark.getStrokeWidth());
                copy.setScriptSlant(farziMark.getScriptSlant());
                copies.add(copy);
            } else if (mark instanceof DotMatrixMark) {
                DotMatrixMark dotMatrix = (DotMatrixMark) mark;
                DotMatrixMark copy = new DotMatrixMark(dotMatrix.x, dotMatrix.y, dotMatrix.getData());
                copy.width = dotMatrix.width;
                copy.height = dotMatrix.height;
                copy.setDotDiameter(dotMatrix.getDotDiameter());
                copy.setDotPitch(dotMatrix.getDotPitch());
                copy.setMatrixSize(dotMatrix.getMatrixSize());
                copy.setShowGrid(dotMatrix.isShowGrid());
                copy.setShowBorder(dotMatrix.isShowBorder());
                copies.add(copy);
            } else if (mark instanceof AvoidPointMark) {
                AvoidPointMark avoidMark = (AvoidPointMark) mark;
                AvoidPointMark copy = new AvoidPointMark(avoidMark.x, avoidMark.y);
                copy.width = avoidMark.width;
                copy.height = avoidMark.height;
                copy.setAvoidRadius(avoidMark.getAvoidRadius());
                copy.setDesignRadius(avoidMark.getDesignRadius());
                copy.setDesignBuffer(avoidMark.getDesignBuffer());
                copy.setShowDots(avoidMark.isShowDots());
                copy.setDotSpacing(avoidMark.getDotSpacing());
                copies.add(copy);
            }
            // Add other mark types as needed
        }
        return copies;
    }
    
    private void saveStateForUndo() {
        List<Mark> currentState = copyAllMarks(marks);
        undoStack.push(currentState);
        
        // Clear redo stack when a new action is performed
        redoStack.clear();
        
        // Limit undo stack size to prevent memory issues
        if (undoStack.size() > 20) {
            undoStack.remove(0);
        }
    }
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            // Save current state to redo stack before undoing
            List<Mark> currentState = copyAllMarks(marks);
            redoStack.push(currentState);
            
            // Apply the undo - restore previous state
            marks.clear();
            textMarks.clear();
            List<Mark> previousState = undoStack.pop();
            marks.addAll(previousState);
            
            // Update textMarks for backward compatibility
            for (Mark mark : marks) {
                if (mark instanceof TextMark) {
                    textMarks.add((TextMark) mark);
                }
            }
            selectedMark = null;
            notifySelectionChanged();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to undo!", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            // Save current state to undo stack before redoing
            List<Mark> currentState = copyAllMarks(marks);
            undoStack.push(currentState);
            
            // Apply the redo - restore next state
            marks.clear();
            textMarks.clear();
            List<Mark> redoState = redoStack.pop();
            marks.addAll(redoState);
            
            // Update textMarks for backward compatibility
            for (Mark mark : marks) {
                if (mark instanceof TextMark) {
                    textMarks.add((TextMark) mark);
                }
            }
            selectedMark = null;
            notifySelectionChanged();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to redo!", "Redo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void cutSelected() {
        if (selectedMark != null) {
            saveStateForUndo();
            
            // Create a deep copy for clipboard
            clipboardMark = createMarkCopy(selectedMark);
            
            // Remove from lists
            if (selectedMark instanceof TextMark) {
                textMarks.remove((TextMark) selectedMark);
            }
            marks.remove(selectedMark);
            selectedMark = null;
            notifySelectionChanged();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No mark selected to cut!", "Cut", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void copySelected() {
        if (selectedMark != null) {
            // Create a deep copy for clipboard
            clipboardMark = createMarkCopy(selectedMark);
            JOptionPane.showMessageDialog(this, "Mark copied to clipboard!", "Copy", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No mark selected to copy!", "Copy", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void paste() {
        if (clipboardMark != null) {
            saveStateForUndo();
            
            // Create a new copy with offset position
            Mark newMark = createMarkCopy(clipboardMark);
            newMark.x += 20; // Offset to avoid overlap
            newMark.y += 20;
            
            marks.add(newMark);
            if (newMark instanceof TextMark) {
                textMarks.add((TextMark) newMark);
            }
            selectedMark = newMark;
            notifySelectionChanged();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Clipboard is empty!", "Paste", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private Mark createMarkCopy(Mark original) {
        if (original instanceof TextMark) {
            TextMark textMark = (TextMark) original;
            TextMark copy = new TextMark(textMark.x, textMark.y, textMark.getText());
            copy.width = textMark.width;
            copy.height = textMark.height;
            
            // ENHANCED: Copy ALL text formatting properties for format preservation
            copy.setFont(textMark.getFont());
            copy.setCharacterWidth(textMark.getCharacterWidth());
            copy.setLineSpacing(textMark.getLineSpacing());
            
            if (ENABLE_COPY_PASTE_LOGGING) {
                System.out.println("🔄 createMarkCopy - TextMark format preserved: font=" + textMark.getFont().getName() + 
                                 " size=" + textMark.getFont().getSize() + 
                                 " charWidth=" + textMark.getCharacterWidth() + 
                                 " lineSpacing=" + textMark.getLineSpacing());
            }
            return copy;
        } else if (original instanceof BowTextMark) {
            BowTextMark bowMark = (BowTextMark) original;
            BowTextMark copy = new BowTextMark(bowMark.x, bowMark.y, bowMark.getText());
            copy.width = bowMark.width;
            copy.height = bowMark.height;
            copy.setCurvature(bowMark.getCurvature());
            copy.setBowUp(bowMark.isBowUp());
            return copy;
        } else if (original instanceof ArcLettersMark) {
            ArcLettersMark arcMark = (ArcLettersMark) original;
            ArcLettersMark copy = new ArcLettersMark(arcMark.x, arcMark.y, arcMark.getLetters());
            copy.width = arcMark.width;
            copy.height = arcMark.height;
            copy.setStartAngle(arcMark.getStartAngle());
            copy.setArcAngle(arcMark.getArcAngle());
            copy.setRadius(arcMark.getRadius());
            copy.setShowArc(arcMark.isShowArc());
            if (arcMark.getFont() != null) {
                copy.setFont(arcMark.getFont());
            }
            return copy;
        } else if (original instanceof GraphMark) {
            GraphMark graphMark = (GraphMark) original;
            GraphMark copy = new GraphMark(graphMark.x, graphMark.y);
            copy.width = graphMark.width;
            copy.height = graphMark.height;
            copy.setBorderColor(graphMark.getBorderColor());
            return copy;
        } else if (original instanceof RectangleMark) {
            RectangleMark rectMark = (RectangleMark) original;
            RectangleMark copy = new RectangleMark(rectMark.x, rectMark.y);
            copy.width = rectMark.width;
            copy.height = rectMark.height;
            return copy;
        } else if (original instanceof LineMark) {
            LineMark lineMark = (LineMark) original;
            LineMark copy = new LineMark(lineMark.x, lineMark.y);
            copy.width = lineMark.width;
            copy.height = lineMark.height;
            return copy;
        } else if (original instanceof FarziMark) {
            FarziMark farziMark = (FarziMark) original;
            FarziMark copy = new FarziMark(farziMark.x, farziMark.y, farziMark.getText());
            copy.width = farziMark.width;
            copy.height = farziMark.height;
            copy.setCharHeight(farziMark.getCharHeight());
            copy.setCharWidth(farziMark.getCharWidth());
            copy.setCharSpacing(farziMark.getCharSpacing());
            copy.setShowGrid(farziMark.isShowGrid());
            copy.setStrokeWidth(farziMark.getStrokeWidth());
            copy.setScriptSlant(farziMark.getScriptSlant());
            return copy;
        } else if (original instanceof RulerMark) {
            // ENHANCED: RulerMark complete format preservation in createMarkCopy (was missing!)
            RulerMark rulerMark = (RulerMark) original;
            RulerMark copy = new RulerMark(rulerMark.x, rulerMark.y);
            copy.width = rulerMark.width;
            copy.height = rulerMark.height;
            
            // Copy ALL ruler formatting properties
            copy.setRulerLength(rulerMark.getRulerLength());
            copy.setRulerHeight(rulerMark.getRulerHeight());
            copy.setScaleSize(rulerMark.getScaleSize());
            copy.setScaleValue(rulerMark.getScaleValue());
            copy.setStartValue(rulerMark.getStartValue());
            copy.setRadius(rulerMark.getRadius());
            copy.setSpanLength(rulerMark.getSpanLength());
            
            // Copy text formatting properties
            copy.setFontFamily(rulerMark.getFontFamily());
            copy.setFontSize(rulerMark.getFontSize());
            copy.setFontStyle(rulerMark.getFontStyle());
            copy.setCharacterWidth(rulerMark.getCharacterWidth());
            copy.setLineSpacing(rulerMark.getLineSpacing());
            copy.setTextOffsetX(rulerMark.getTextOffsetX());
            copy.setTextOffsetY(rulerMark.getTextOffsetY());
            copy.setHorizontalAlignment(rulerMark.getHorizontalAlignment());
            copy.setVerticalAlignment(rulerMark.getVerticalAlignment());
            copy.setTextColor(rulerMark.getTextColor());
            
            // Copy display options
            copy.setShowNumbers(rulerMark.isShowNumbers());
            copy.setShowMiddleNumbers(rulerMark.isShowMiddleNumbers());
            copy.setShowMinorMarks(rulerMark.isShowMinorMarks());
            copy.setShowMiddleMarks(rulerMark.isShowMiddleMarks());
            copy.setShowMajorMarks(rulerMark.isShowMajorMarks());
            
            if (ENABLE_COPY_PASTE_LOGGING) {
                System.out.println("🔄 createMarkCopy - RulerMark format preserved: font=" + rulerMark.getFontFamily() + 
                                 " size=" + rulerMark.getFontSize() + 
                                 " charWidth=" + rulerMark.getCharacterWidth());
            }
            return copy;
        } else if (original instanceof DotMatrixMark) {
            DotMatrixMark dotMatrix = (DotMatrixMark) original;
            DotMatrixMark copy = new DotMatrixMark(dotMatrix.x, dotMatrix.y, dotMatrix.getData());
            copy.width = dotMatrix.width;
            copy.height = dotMatrix.height;
            copy.setDotDiameter(dotMatrix.getDotDiameter());
            copy.setDotPitch(dotMatrix.getDotPitch());
            copy.setMatrixSize(dotMatrix.getMatrixSize());
            copy.setShowGrid(dotMatrix.isShowGrid());
            copy.setShowBorder(dotMatrix.isShowBorder());
            return copy;
        } else if (original instanceof AvoidPointMark) {
            AvoidPointMark avoidMark = (AvoidPointMark) original;
            AvoidPointMark copy = new AvoidPointMark(avoidMark.x, avoidMark.y);
            copy.width = avoidMark.width;
            copy.height = avoidMark.height;
            copy.setAvoidRadius(avoidMark.getAvoidRadius());
            copy.setDesignRadius(avoidMark.getDesignRadius());
            copy.setDesignBuffer(avoidMark.getDesignBuffer());
            copy.setShowDots(avoidMark.isShowDots());
            copy.setDotSpacing(avoidMark.getDotSpacing());
            return copy;
        }
        return null; // Should not happen
    }
    
    public void eraseSelected() {
        if (selectedMark != null) {
            saveStateForUndo();
            if (selectedMark instanceof TextMark) {
                textMarks.remove((TextMark) selectedMark);
            }
            marks.remove(selectedMark);
            selectedMark = null;
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No mark selected to erase!", "Erase", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void clearTransformation() {
        if (selectedMark != null) {
            saveStateForUndo();
            selectedMark.x = 100;
            selectedMark.y = 100;
            selectedMark.width = 140;
            selectedMark.height = 40;
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No mark selected!", "Clear Transform", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void clearAllForNewProject() {
        saveStateForUndo();
        marks.clear();
        textMarks.clear();
        selectedMark = null;
        activeMark = null;
        clipboardMark = null;
        notifySelectionChanged();
        repaint();
        System.out.println("🆕 Canvas cleared for new project - All marks removed");
    }
    
    // Clear all marks for testing purposes (soft coding enabled)
    public void clearAllMarks() {
        if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
            System.out.println("🗑️ Clearing all marks from canvas (" + marks.size() + " marks)");
        }
        
        marks.clear();
        textMarks.clear();
        selectedMark = null;
        activeMark = null;
        
        if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
            System.out.println("✅ Canvas cleared successfully");
        }
        
        repaint();
    }
    
    // ==================== INTELLIGENT CLEAR TRANS FUNCTIONALITY (SOFT CODED) ====================
    
    /**
     * Intelligent Clear Trans functionality that clears/deletes selected marking content
     * Uses soft coding configuration for flexible behavior
     */
    public void clearTransSelected() {
        if (!RugrelDropdownConfig.ENABLE_CLEAR_TRANS_FUNCTIONALITY) {
            if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
                System.out.println("🚫 Clear Trans functionality is disabled via soft coding");
            }
            return;
        }
        
        if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
            System.out.println("🧹 Clear Trans initiated - Analyzing selection...");
        }
        
        // Intelligent clearing based on selection state
        if (selectedMark != null) {
            clearSelectedMarks();
        } else {
            handleNothingSelectedCase();
        }
    }
    
    /**
     * Clear selected marks with safety checks and validation
     */
    private void clearSelectedMarks() {
        if (!RugrelDropdownConfig.CLEAR_SELECTED_MARKS_ONLY) {
            if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
                System.out.println("⚠️ Clear selected marks is disabled via soft coding");
            }
            return;
        }
        
        // Validation before clearing
        if (RugrelDropdownConfig.VALIDATE_BEFORE_CLEAR) {
            if (!validateMarkForClearing(selectedMark)) {
                if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
                    System.out.println("❌ Mark validation failed - cannot clear");
                }
                return;
            }
        }
        
        // Confirmation if enabled
        if (RugrelDropdownConfig.CONFIRM_CLEAR_OPERATIONS) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear the selected marking content?\nThis action cannot be undone.",
                "Clear Transform - Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (result != JOptionPane.YES_OPTION) {
                if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
                    System.out.println("🚫 Clear operation cancelled by user");
                }
                return;
            }
        }
        
        // Backup for undo if enabled
        if (RugrelDropdownConfig.BACKUP_BEFORE_CLEAR) {
            saveStateForUndo();
        }
        
        // Highlight before clear if enabled
        if (RugrelDropdownConfig.HIGHLIGHT_BEFORE_CLEAR) {
            highlightMarkForClearing(selectedMark);
        }
        
        // Perform the actual clearing
        performIntelligentClear();
        
        // Show feedback
        if (RugrelDropdownConfig.SHOW_CLEAR_FEEDBACK) {
            showClearSuccessMessage();
        }
        
        if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
            System.out.println("✅ Clear Trans completed successfully - Selected mark cleared");
        }
    }
    
    /**
     * Handle the case when nothing is selected
     */
    private void handleNothingSelectedCase() {
        if (RugrelDropdownConfig.CLEAR_ALL_WHEN_NOTHING_SELECTED) {
            // Clear all marks (dangerous operation)
            if (RugrelDropdownConfig.CONFIRM_CLEAR_OPERATIONS) {
                int result = JOptionPane.showConfirmDialog(
                    this,
                    "No mark is selected. Clear ALL marking content?\nThis will delete everything on the canvas!",
                    "Clear Transform - Clear All",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (result == JOptionPane.YES_OPTION) {
                    clearAllForNewProject();
                    if (RugrelDropdownConfig.SHOW_CLEAR_FEEDBACK) {
                        JOptionPane.showMessageDialog(this, "All marking content has been cleared.", "Clear Transform", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {
            // Safe behavior - just show a helpful message
            String message = "Clear Trans: No marking content selected.\n\n" +
                           "To use Clear Transform:\n" +
                           "1. Select a text or graphic mark on the drawing board\n" +
                           "2. Click Clear Trans to delete the selected content\n\n" +
                           "This will permanently remove the selected marking content.";
                           
            JOptionPane.showMessageDialog(this, message, "Clear Transform - Help", JOptionPane.INFORMATION_MESSAGE);
            
            if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
                System.out.println("ℹ️ Clear Trans help shown - no selection found");
            }
        }
    }
    
    /**
     * Validate that a mark can be safely cleared
     */
    private boolean validateMarkForClearing(Mark mark) {
        if (mark == null) return false;
        
        // Check mark type permissions
        if (mark instanceof TextMark && !RugrelDropdownConfig.CLEAR_TEXT_MARKS) return false;
        if (mark instanceof BarcodeMark && !RugrelDropdownConfig.CLEAR_BARCODE_MARKS) return false;
        // Add more mark type checks as needed
        
        // General permission check
        return RugrelDropdownConfig.CLEAR_ALL_MARK_TYPES;
    }
    
    /**
     * Highlight mark that will be cleared (visual feedback)
     */
    private void highlightMarkForClearing(Mark mark) {
        if (mark == null) return;
        
        // Flash the mark to show what will be cleared
        Graphics2D g2d = (Graphics2D) getGraphics();
        if (g2d != null) {
            g2d.setColor(new Color(255, 0, 0, 100)); // Red highlight
            g2d.fillRect(mark.x - 2, mark.y - 2, mark.width + 4, mark.height + 4);
            g2d.dispose();
            
            // Brief pause to show highlight
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Perform the actual intelligent clearing operation
     */
    private void performIntelligentClear() {
        if (selectedMark == null) return;
        
        String markType = selectedMark.getClass().getSimpleName();
        
        if (RugrelDropdownConfig.LOG_CLEAR_OPERATIONS) {
            System.out.println("🗑️ Clearing mark: " + markType + " at (" + selectedMark.x + "," + selectedMark.y + ")");
        }
        
        // Use existing erase functionality with animation if enabled
        if (RugrelDropdownConfig.ANIMATE_CLEAR_OPERATION) {
            animateClearOperation(selectedMark);
        }
        
        // Remove from appropriate collections based on mark type
        if (selectedMark instanceof TextMark) {
            textMarks.remove((TextMark) selectedMark);
        }
        marks.remove(selectedMark);
        
        // Clear selection
        selectedMark = null;
        
        // Refresh display
        repaint();
        notifySelectionChanged();
    }
    
    /**
     * Animate the clear operation for better user feedback
     */
    private void animateClearOperation(Mark mark) {
        if (mark == null) return;
        
        // Simple fade-out animation
        Timer fadeTimer = new Timer(50, null);
        final int[] alpha = {255};
        
        fadeTimer.addActionListener(e -> {
            alpha[0] -= 25;
            if (alpha[0] <= 0) {
                fadeTimer.stop();
            } else {
                // Redraw with fading mark
                repaint();
            }
        });
        
        fadeTimer.start();
        
        // Wait for animation to complete
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Show success message after clearing
     */
    private void showClearSuccessMessage() {
        String message = "Clear Transform completed successfully!\n" +
                        "The selected marking content has been removed from the drawing board.";
        
        JOptionPane.showMessageDialog(this, message, "Clear Transform", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ==================== END CLEAR TRANS FUNCTIONALITY ====================
    
    // ==================== INTELLIGENT MIRROR FUNCTIONALITY (SOFT CODED) ====================
    
    /**
     * Intelligent Mirror functionality that flips selected images/marks in opposite direction
     * Supports horizontal and vertical mirroring based on content type using soft coding configuration
     */
    public void mirrorSelected() {
        // Check if Mirror functionality is enabled via soft coding
        if (!RugrelDropdownConfig.ENABLE_INTELLIGENT_MIRROR) {
            if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
                System.out.println("🚫 Mirror functionality is disabled via soft coding");
            }
            return;
        }
        
        if (selectedMark != null) {
            if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
                System.out.println("🪞 Mirror initiated - Analyzing selected content...");
            }
            
            // Validate before mirroring if enabled
            if (RugrelDropdownConfig.VALIDATE_BEFORE_MIRROR) {
                if (!validateMarkForMirroring(selectedMark)) {
                    return;
                }
            }
            
            // Check if mark is locked and respect lock status
            if (RugrelDropdownConfig.RESPECT_LOCKED_MARKS && isMarkLocked(selectedMark)) {
                if (RugrelDropdownConfig.SHOW_MIRROR_FEEDBACK) {
                    JOptionPane.showMessageDialog(this, 
                        "Cannot mirror locked content.\nUnlock the mark first to enable mirroring.", 
                        "Mirror - Content Locked", 
                        JOptionPane.WARNING_MESSAGE);
                }
                return;
            }
            
            // Create backup for undo if enabled
            if (RugrelDropdownConfig.BACKUP_BEFORE_MIRROR) {
                saveStateForUndo();
            }
            
            // Perform intelligent mirroring based on mark type
            performIntelligentMirror(selectedMark);
            
            // Maintain selection if configured
            if (RugrelDropdownConfig.MIRROR_MAINTAINS_SELECTION) {
                // Selection is automatically maintained as selectedMark remains the same object
            }
            
            // Show feedback if enabled
            if (RugrelDropdownConfig.SHOW_MIRROR_FEEDBACK) {
                showMirrorCompleteFeedback(selectedMark);
            }
            
            repaint();
        } else {
            handleNoSelectionForMirror();
        }
    }
    
    /**
     * Validate that a mark can be safely mirrored
     */
    private boolean validateMarkForMirroring(Mark mark) {
        if (mark == null) {
            return false;
        }
        
        // Check if mark type is supported for mirroring (Comprehensive Coverage)
        boolean isSupported = false;
        
        if (mark instanceof TextMark && RugrelDropdownConfig.MIRROR_TEXT_MARKS) {
            isSupported = true;
        } else if (mark instanceof BowTextMark && RugrelDropdownConfig.MIRROR_BOW_TEXT_MARKS) {
            isSupported = true;
        } else if (mark instanceof BarcodeMark && RugrelDropdownConfig.MIRROR_BARCODE_MARKS) {
            isSupported = true;
        } else if (mark instanceof ArcLettersMark && RugrelDropdownConfig.MIRROR_ARC_MARKS) {
            isSupported = true;
        } else if (mark instanceof GraphMark && RugrelDropdownConfig.MIRROR_GRAPH_MARKS) {
            isSupported = true;
        } else if (mark instanceof RulerMark && RugrelDropdownConfig.MIRROR_RULER_MARKS) {
            isSupported = true;
        } else if (mark instanceof AvoidPointMark && RugrelDropdownConfig.MIRROR_AVOID_POINT_MARKS) {
            isSupported = true;
        } else if (mark instanceof RectangleMark && RugrelDropdownConfig.MIRROR_RECTANGLE_MARKS) {
            isSupported = true;
        } else if (mark instanceof LineMark && RugrelDropdownConfig.MIRROR_LINE_MARKS) {
            isSupported = true;
        } else if (mark instanceof DotMatrixMark && RugrelDropdownConfig.MIRROR_DOT_MATRIX_MARKS) {
            isSupported = true;
        } else if (mark instanceof FarziMark && RugrelDropdownConfig.MIRROR_FARZI_MARKS) {
            isSupported = true;
        } else if (RugrelDropdownConfig.MIRROR_GRAPHIC_MARKS) {
            // Fallback support for any other graphic marks
            isSupported = true;
        }
        
        if (!isSupported) {
            if (RugrelDropdownConfig.SHOW_MIRROR_FEEDBACK) {
                JOptionPane.showMessageDialog(this, 
                    "Mirror operation is not supported for this type of content:\n" + 
                    mark.getClass().getSimpleName() + 
                    "\n\nSupported types: Text, Bow Text, Arc Text, Barcodes, Images/Graphics,\n" +
                    "Rulers, Avoid Points, Rectangles, Lines, Dot Matrix, Farzi Text", 
                    "Mirror - Unsupported Content", 
                    JOptionPane.WARNING_MESSAGE);
            }
            return false;
        }
        
        if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
            System.out.println("✅ Mirror validation passed for: " + mark.getClass().getSimpleName());
        }
        
        return true;
    }
    
    /**
     * Check if a mark is locked (placeholder - implement based on your lock system)
     */
    private boolean isMarkLocked(Mark mark) {
        // This would check your specific lock implementation
        // For now, return false (no locks)
        return false;
    }
    
    /**
     * Perform intelligent mirroring based on mark type and configuration
     */
    private void performIntelligentMirror(Mark mark) {
        if (RugrelDropdownConfig.HIGHLIGHT_BEFORE_MIRROR) {
            // Visual feedback could be added here
        }
        
        if (RugrelDropdownConfig.ANIMATE_MIRROR_OPERATION) {
            // Animation could be added here
        }
        
        // Determine mirror direction based on configuration and auto-detection
        boolean useHorizontalMirror = determineMirrorDirection(mark);
        
        if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
            String direction = useHorizontalMirror ? "horizontal" : "vertical";
            System.out.println("🔄 Mirroring " + mark.getClass().getSimpleName() + " in " + direction + " direction...");
        }
        
        // Apply mirroring based on mark type (Comprehensive Support)
        if (mark instanceof TextMark && RugrelDropdownConfig.MIRROR_TEXT_MARKS) {
            mirrorTextMark((TextMark) mark, useHorizontalMirror);
        } else if (mark instanceof BowTextMark && RugrelDropdownConfig.MIRROR_BOW_TEXT_MARKS) {
            mirrorBowTextMark((BowTextMark) mark, useHorizontalMirror);
        } else if (mark instanceof BarcodeMark && RugrelDropdownConfig.MIRROR_BARCODE_MARKS) {
            mirrorBarcodeMark((BarcodeMark) mark, useHorizontalMirror);
        } else if (mark instanceof ArcLettersMark && RugrelDropdownConfig.MIRROR_ARC_MARKS) {
            mirrorArcMark((ArcLettersMark) mark, useHorizontalMirror);
        } else if (mark instanceof GraphMark && RugrelDropdownConfig.MIRROR_IMAGE_MARKS) {
            mirrorGraphMark((GraphMark) mark, useHorizontalMirror);
        } else if (mark instanceof RulerMark && RugrelDropdownConfig.MIRROR_GRAPHIC_MARKS) {
            mirrorRulerMark((RulerMark) mark, useHorizontalMirror);
        } else if (mark instanceof AvoidPointMark && RugrelDropdownConfig.MIRROR_GRAPHIC_MARKS) {
            mirrorAvoidPointMark((AvoidPointMark) mark, useHorizontalMirror);
        } else if (mark instanceof RectangleMark && RugrelDropdownConfig.MIRROR_GRAPHIC_MARKS) {
            mirrorRectangleMark((RectangleMark) mark, useHorizontalMirror);
        } else if (mark instanceof LineMark && RugrelDropdownConfig.MIRROR_GRAPHIC_MARKS) {
            mirrorLineMark((LineMark) mark, useHorizontalMirror);
        } else if (mark instanceof DotMatrixMark && RugrelDropdownConfig.MIRROR_BARCODE_MARKS) {
            mirrorDotMatrixMark((DotMatrixMark) mark, useHorizontalMirror);
        } else if (mark instanceof FarziMark && RugrelDropdownConfig.MIRROR_TEXT_MARKS) {
            mirrorFarziMark((FarziMark) mark, useHorizontalMirror);
        } else if (RugrelDropdownConfig.MIRROR_GRAPHIC_MARKS) {
            mirrorGenericMark(mark, useHorizontalMirror);
        }
        
        if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
            System.out.println("✅ Mirror completed successfully - Content flipped in opposite direction");
        }
    }
    
    /**
     * Determine optimal mirror direction based on mark type and configuration
     */
    private boolean determineMirrorDirection(Mark mark) {
        if (RugrelDropdownConfig.AUTO_DETECT_MIRROR_TYPE) {
            // Auto-detect based on content type and dimensions
            if (mark instanceof TextMark || mark instanceof BowTextMark) {
                // Text typically looks better with horizontal mirroring
                return true;
            } else if (mark instanceof BarcodeMark) {
                // Barcodes usually need horizontal mirroring to remain readable
                return true;
            } else {
                // For graphics, use aspect ratio to determine best direction
                if (mark.width > mark.height) {
                    return false; // Vertical mirror for wide objects
                } else {
                    return true;  // Horizontal mirror for tall objects
                }
            }
        }
        
        // Use configuration default
        return RugrelDropdownConfig.DEFAULT_HORIZONTAL_MIRROR;
    }
    
    /**
     * Mirror text mark - reverse text content or flip position
     */
    private void mirrorTextMark(TextMark textMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: reverse text content
            String text = textMark.getText();
            StringBuilder mirrored = new StringBuilder(text);
            textMark.setText(mirrored.reverse().toString());
        } else {
            // Vertical mirror: flip Y position relative to center
            double centerY = textMark.y + textMark.height / 2.0;
            textMark.y = (int) (centerY - (textMark.y - centerY) - textMark.height);
        }
    }
    
    /**
     * Mirror bow text mark - flip bow direction and/or position
     */
    private void mirrorBowTextMark(BowTextMark bowMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip bow direction
            bowMark.setBowUp(!bowMark.isBowUp());
            
            // Also reverse text content if configured
            String text = bowMark.getText();
            StringBuilder mirrored = new StringBuilder(text);
            bowMark.setText(mirrored.reverse().toString());
        } else {
            // Vertical mirror: flip bow direction and position
            bowMark.setBowUp(!bowMark.isBowUp());
            double centerY = bowMark.y + bowMark.height / 2.0;
            bowMark.y = (int) (centerY - (bowMark.y - centerY) - bowMark.height);
        }
    }
    
    /**
     * Mirror barcode mark - flip orientation while maintaining readability
     */
    private void mirrorBarcodeMark(BarcodeMark barcodeMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position relative to center
            double centerX = barcodeMark.x + barcodeMark.width / 2.0;
            barcodeMark.x = (int) (centerX - (barcodeMark.x - centerX) - barcodeMark.width);
        } else {
            // Vertical mirror: flip Y position relative to center
            double centerY = barcodeMark.y + barcodeMark.height / 2.0;
            barcodeMark.y = (int) (centerY - (barcodeMark.y - centerY) - barcodeMark.height);
        }
        
        // Set mirror flag for rendering
        barcodeMark.mirror = !barcodeMark.mirror;
    }
    
    /**
     * Mirror arc mark - flip arc direction and curvature
     */
    private void mirrorArcMark(ArcLettersMark arcMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position and reverse letters
            double centerX = arcMark.x + arcMark.width / 2.0;
            arcMark.x = (int) (centerX - (arcMark.x - centerX) - arcMark.width);
            
            // Reverse letters content using reflection to access private field
            try {
                java.lang.reflect.Field lettersField = ArcLettersMark.class.getDeclaredField("letters");
                lettersField.setAccessible(true);
                String letters = (String) lettersField.get(arcMark);
                StringBuilder mirrored = new StringBuilder(letters);
                lettersField.set(arcMark, mirrored.reverse().toString());
            } catch (Exception e) {
                // Fallback: just flip position without reversing text
                if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
                    System.out.println("⚠️ Could not reverse arc letters, proceeding with position flip only");
                }
            }
        } else {
            // Vertical mirror: flip Y position
            double centerY = arcMark.y + arcMark.height / 2.0;
            arcMark.y = (int) (centerY - (arcMark.y - centerY) - arcMark.height);
        }
        
        // Set mirror flag for rendering
        arcMark.mirror = !arcMark.mirror;
    }
    
    /**
     * Mirror generic mark - flip position and set mirror flag
     */
    private void mirrorGenericMark(Mark mark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position relative to center
            double centerX = mark.x + mark.width / 2.0;
            mark.x = (int) (centerX - (mark.x - centerX) - mark.width);
        } else {
            // Vertical mirror: flip Y position relative to center
            double centerY = mark.y + mark.height / 2.0;
            mark.y = (int) (centerY - (mark.y - centerY) - mark.height);
        }
        
        // Set mirror flag for rendering (if supported by the mark type)
        mark.mirror = !mark.mirror;
    }
    
    /**
     * Mirror graph mark - flip image content and position
     */
    private void mirrorGraphMark(GraphMark graphMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position relative to center
            double centerX = graphMark.x + graphMark.width / 2.0;
            graphMark.x = (int) (centerX - (graphMark.x - centerX) - graphMark.width);
        } else {
            // Vertical mirror: flip Y position relative to center
            double centerY = graphMark.y + graphMark.height / 2.0;
            graphMark.y = (int) (centerY - (graphMark.y - centerY) - graphMark.height);
        }
        
        // Set mirror flag for image rendering
        graphMark.mirror = !graphMark.mirror;
    }
    
    /**
     * Mirror ruler mark - flip ruler orientation and position
     */
    private void mirrorRulerMark(RulerMark rulerMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position and ruler direction
            double centerX = rulerMark.x + rulerMark.width / 2.0;
            rulerMark.x = (int) (centerX - (rulerMark.x - centerX) - rulerMark.width);
        } else {
            // Vertical mirror: flip Y position and orientation
            double centerY = rulerMark.y + rulerMark.height / 2.0;
            rulerMark.y = (int) (centerY - (rulerMark.y - centerY) - rulerMark.height);
        }
        
        // Set mirror flag for rendering
        rulerMark.mirror = !rulerMark.mirror;
    }
    
    /**
     * Mirror avoid point mark - flip position and zone
     */
    private void mirrorAvoidPointMark(AvoidPointMark avoidMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position
            double centerX = avoidMark.x + avoidMark.width / 2.0;
            avoidMark.x = (int) (centerX - (avoidMark.x - centerX) - avoidMark.width);
        } else {
            // Vertical mirror: flip Y position
            double centerY = avoidMark.y + avoidMark.height / 2.0;
            avoidMark.y = (int) (centerY - (avoidMark.y - centerY) - avoidMark.height);
        }
        
        // Set mirror flag for rendering
        avoidMark.mirror = !avoidMark.mirror;
    }
    
    /**
     * Mirror rectangle mark - flip shape position and orientation
     */
    private void mirrorRectangleMark(RectangleMark rectangleMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position
            double centerX = rectangleMark.x + rectangleMark.width / 2.0;
            rectangleMark.x = (int) (centerX - (rectangleMark.x - centerX) - rectangleMark.width);
        } else {
            // Vertical mirror: flip Y position
            double centerY = rectangleMark.y + rectangleMark.height / 2.0;
            rectangleMark.y = (int) (centerY - (rectangleMark.y - centerY) - rectangleMark.height);
        }
        
        // Set mirror flag for rendering
        rectangleMark.mirror = !rectangleMark.mirror;
    }
    
    /**
     * Mirror line mark - flip line position and direction
     */
    private void mirrorLineMark(LineMark lineMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position and line direction
            double centerX = lineMark.x + lineMark.width / 2.0;
            lineMark.x = (int) (centerX - (lineMark.x - centerX) - lineMark.width);
        } else {
            // Vertical mirror: flip Y position and orientation
            double centerY = lineMark.y + lineMark.height / 2.0;
            lineMark.y = (int) (centerY - (lineMark.y - centerY) - lineMark.height);
        }
        
        // Set mirror flag for rendering
        lineMark.mirror = !lineMark.mirror;
    }
    
    /**
     * Mirror dot matrix mark - flip matrix position while maintaining readability
     */
    private void mirrorDotMatrixMark(DotMatrixMark dotMatrixMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position
            double centerX = dotMatrixMark.x + dotMatrixMark.width / 2.0;
            dotMatrixMark.x = (int) (centerX - (dotMatrixMark.x - centerX) - dotMatrixMark.width);
        } else {
            // Vertical mirror: flip Y position
            double centerY = dotMatrixMark.y + dotMatrixMark.height / 2.0;
            dotMatrixMark.y = (int) (centerY - (dotMatrixMark.y - centerY) - dotMatrixMark.height);
        }
        
        // Set mirror flag for rendering
        dotMatrixMark.mirror = !dotMatrixMark.mirror;
    }
    
    /**
     * Mirror farzi mark - flip text content and position
     */
    private void mirrorFarziMark(FarziMark farziMark, boolean horizontal) {
        if (horizontal) {
            // Horizontal mirror: flip X position and reverse text if possible
            double centerX = farziMark.x + farziMark.width / 2.0;
            farziMark.x = (int) (centerX - (farziMark.x - centerX) - farziMark.width);
            
            // Attempt to reverse text content using reflection
            try {
                java.lang.reflect.Field textField = FarziMark.class.getDeclaredField("text");
                textField.setAccessible(true);
                String text = (String) textField.get(farziMark);
                if (text != null) {
                    StringBuilder mirrored = new StringBuilder(text);
                    textField.set(farziMark, mirrored.reverse().toString());
                }
            } catch (Exception e) {
                // Fallback: just flip position without reversing text
                if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
                    System.out.println("⚠️ Could not reverse farzi text, proceeding with position flip only");
                }
            }
        } else {
            // Vertical mirror: flip Y position
            double centerY = farziMark.y + farziMark.height / 2.0;
            farziMark.y = (int) (centerY - (farziMark.y - centerY) - farziMark.height);
        }
        
        // Set mirror flag for rendering
        farziMark.mirror = !farziMark.mirror;
    }
    
    /**
     * Handle case when no mark is selected for mirroring
     */
    private void handleNoSelectionForMirror() {
        if (RugrelDropdownConfig.LOG_MIRROR_OPERATIONS) {
            System.out.println("ℹ️ Mirror help shown - no selection found");
        }
        
        String message = "Mirror: No content selected.\n\n" +
                        "To use Mirror functionality:\n" +
                        "1. Select any marking content (text, image, shape, barcode, etc.)\n" +
                        "2. Click Mirror to flip the selected content in opposite direction\n\n" +
                        "Supported content: Text, Bow Text, Arc Text, Barcodes, Images/Graphics,\n" +
                        "Rulers, Avoid Points, Rectangles, Lines, Dot Matrix, Farzi Text, and more!";
        
        JOptionPane.showMessageDialog(this, message, "Mirror - Help", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show completion feedback for mirror operation
     */
    private void showMirrorCompleteFeedback(Mark mark) {
        String message = "Mirror completed successfully!\n" +
                        "Content type: " + mark.getClass().getSimpleName().replace("Mark", "") + "\n" +
                        "The selected content has been flipped in the opposite direction.";
        
        JOptionPane.showMessageDialog(this, message, "Mirror", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ==================== END MIRROR FUNCTIONALITY ====================
    
    public void toggleSizeLock() {
        // Soft coding: Enhanced Lock Size with per-mark intelligence
        if (RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY && 
            RugrelDropdownConfig.ENABLE_INTELLIGENT_LOCK_SIZE) {
            
            if (RugrelDropdownConfig.ENABLE_PER_MARK_SIZE_LOCKING) {
                // Per-mark Lock Size mode (recommended)
                if (RugrelDropdownConfig.LOCK_SELECTED_MARKS_ONLY && selectedMark != null) {
                    // Toggle lock for the selected mark only
                    boolean newLockState = !selectedMark.lockSize;
                    selectedMark.lockSize = newLockState;
                    
                    if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                        String markName = selectedMark.name != null ? selectedMark.name : "Mark";
                        String message = newLockState ? 
                            markName + " size locked!" : 
                            markName + " size unlocked!";
                        JOptionPane.showMessageDialog(DrawingCanvas.this, message, "Lock Size", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    if (RugrelDropdownConfig.SIZE_LOCK_MAINTAINS_SELECTION) {
                        repaint(); // Refresh visual indicators
                    }
                    return;
                } 
                else if (RugrelDropdownConfig.LOCK_SELECTED_MARKS_ONLY && selectedMark == null && marks.size() > 0) {
                    // No mark selected but marks exist - provide helpful feedback
                    if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                        JOptionPane.showMessageDialog(DrawingCanvas.this, 
                            "Please select a mark first to lock/unlock its size.\n" +
                            "Click on a mark to select it, then try Lock Size again.", 
                            "Lock Size", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    return;
                }
                else if (RugrelDropdownConfig.ENABLE_MULTI_MARK_SIZE_LOCK && marks.size() > 0) {
                    // Toggle lock for all marks when no specific selection
                    boolean anyLocked = marks.stream().anyMatch(mark -> mark.lockSize);
                    boolean newLockState = !anyLocked; // If any locked, unlock all; if none locked, lock all
                    
                    int affectedCount = 0;
                    for (Mark mark : marks) {
                        if (shouldLockSizeForMarkType(mark)) {
                            mark.lockSize = newLockState;
                            affectedCount++;
                        }
                    }
                    
                    if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK && affectedCount > 0) {
                        String message = newLockState ? 
                            "Size locked for " + affectedCount + " marks!" : 
                            "Size unlocked for " + affectedCount + " marks!";
                        JOptionPane.showMessageDialog(DrawingCanvas.this, message, "Lock Size", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    repaint(); // Refresh visual indicators
                    return;
                }
                else if (marks.size() == 0) {
                    // No marks exist - provide feedback
                    if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                        JOptionPane.showMessageDialog(DrawingCanvas.this, 
                            "No marks found to lock/unlock size.\n" +
                            "Add some marks first using the toolbar buttons.", 
                            "Lock Size", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    return;
                }
            }
            
            // Fall back to legacy global mode if per-mark is disabled
            if (RugrelDropdownConfig.ENABLE_GLOBAL_SIZE_LOCKING) {
                sizeLocked = !sizeLocked;
                String message = sizeLocked ? "Global size lock enabled!" : "Global size lock disabled!";
                if (RugrelDropdownConfig.SHOW_SIZE_LOCK_FEEDBACK) {
                    JOptionPane.showMessageDialog(DrawingCanvas.this, message, "Lock Size", JOptionPane.INFORMATION_MESSAGE);
                }
                return;
            }
        }
        
        // Legacy behavior (fallback)
        sizeLocked = !sizeLocked;
        String message = sizeLocked ? "Size lock enabled!" : "Size lock disabled!";
        JOptionPane.showMessageDialog(DrawingCanvas.this, message, "Size Lock", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void togglePrintDisabled() {
        // Enhanced Disable Print functionality for individual marks using soft coding
        if (!RugrelDropdownConfig.ENABLE_DISABLE_PRINT_FUNCTIONALITY) {
            if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
                System.out.println("🚫 Disable Print functionality is disabled via soft coding");
            }
            return;
        }
        
        if (selectedMark != null) {
            // Toggle disable print for selected mark
            selectedMark.disablePrint = !selectedMark.disablePrint;
            
            String markType = selectedMark.getClass().getSimpleName();
            String status = selectedMark.disablePrint ? "DISABLED" : "ENABLED";
            String message = markType + " print " + status.toLowerCase() + "!";
            
            if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
                System.out.println("🖨️ " + markType + " Print Status: " + status);
            }
            
            JOptionPane.showMessageDialog(this, message, "Print Toggle", JOptionPane.INFORMATION_MESSAGE);
            repaint(); // Refresh display to show changes
            
        } else {
            // Fallback to global toggle if no mark selected
            printDisabled = !printDisabled;
            String message = printDisabled ? "Global print disabled!" : "Global print enabled!";
            
            if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
                System.out.println("🖨️ Global Print Status: " + (printDisabled ? "DISABLED" : "ENABLED"));
            }
            
            JOptionPane.showMessageDialog(this, message, "Print Toggle", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Getter methods for current settings
    public Mark getSelectedMark() {
        return selectedMark;
    }
    
    // Setter method for testing purposes (soft coding enabled)
    public void setSelectedMark(Mark mark) {
        if (RugrelDropdownConfig.SHOW_PROPERTY_ACTION_FEEDBACK) {
            System.out.println("🎯 Selected mark changed to: " + (mark != null ? mark.getClass().getSimpleName() : "null"));
        }
        
        selectedMark = mark;
        
        // Update property strip if available
        if (propertyStrip != null && mark != null) {
            propertyStrip.setSelectedMark(mark);
        }
        
        repaint(); // Refresh display to show selection
    }
    
    public boolean isSizeLocked() {
        return sizeLocked;
    }
    
    // Soft coding: Helper method to check if mark type supports Lock Size
    private boolean shouldLockSizeForMarkType(Mark mark) {
        if (!RugrelDropdownConfig.ENABLE_LOCK_SIZE_FUNCTIONALITY) {
            return false;
        }
        
        // Check mark type against soft coding configuration
        if (mark instanceof TextMark && RugrelDropdownConfig.LOCK_SIZE_TEXT_MARKS) return true;
        if (mark instanceof BarcodeMark && RugrelDropdownConfig.LOCK_SIZE_BARCODE_MARKS) return true;
        if (mark instanceof GraphMark && RugrelDropdownConfig.LOCK_SIZE_IMAGE_MARKS) return true;
        if (mark instanceof BowTextMark && RugrelDropdownConfig.LOCK_SIZE_BOW_TEXT_MARKS) return true;
        if (mark instanceof ArcLettersMark && RugrelDropdownConfig.LOCK_SIZE_ARC_MARKS) return true;
        if (mark instanceof RulerMark && RugrelDropdownConfig.LOCK_SIZE_RULER_MARKS) return true;
        if (mark instanceof AvoidPointMark && RugrelDropdownConfig.LOCK_SIZE_AVOID_POINT_MARKS) return true;
        if (mark instanceof RectangleMark && RugrelDropdownConfig.LOCK_SIZE_RECTANGLE_MARKS) return true;
        if (mark instanceof LineMark && RugrelDropdownConfig.LOCK_SIZE_LINE_MARKS) return true;
        if (mark instanceof DotMatrixMark && RugrelDropdownConfig.LOCK_SIZE_DOT_MATRIX_MARKS) return true;
        if (mark instanceof FarziMark && RugrelDropdownConfig.LOCK_SIZE_FARZI_MARKS) return true;
        
        // Default: allow Lock Size for any other mark types (extensible)
        return true;
    }
    
    public boolean isPrintDisabled() {
        return printDisabled;
    }
    
    public java.util.List<Mark> getMarks() {
        return marks;
    }
    
    // Zoom and view control methods (with grid locking protection)
    public void setZoomLevel(double zoom) {
        if (isZoomDisabled()) {
            if (RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE) {
                System.out.println("🚫 Zoom operation blocked - Grid is fixed, no zoom operations allowed");
            }
            if (RugrelDropdownConfig.LOG_ZOOM_BLOCK_EVENTS) {
                System.out.println("🚫 Zoom blocked: Attempted zoom to " + String.format("%.0f%%", zoom * 100));
            }
            return;  // Block zoom operation
        }
        
        this.zoomLevel = zoom;
        repaint();
        
        if (RugrelDropdownConfig.DEBUG_GRID_LOCKING) {
            System.out.println("🔍 Zoom level set to: " + String.format("%.0f%%", zoom * 100));
        }
    }
    
    public double getZoomLevel() {
        return zoomLevel;
    }
    
    public void setMoveViewMode(boolean moveViewMode) {
        this.moveViewMode = moveViewMode;
        if (moveViewMode) {
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public void resetViewPosition() {
        viewOffsetX = 0;
        viewOffsetY = 0;
        System.out.println("🎯 View Reset: Centered at origin, Zoom: " + String.format("%.0f%%", zoomLevel * 100));
        repaint();
    }
    
    // ==================== GRID LOCKING AND ZOOM CONTROL METHODS ====================
    
    /**
     * Lock the grid to prevent modifications
     */
    public void lockGrid(String reason) {
        if (!RugrelDropdownConfig.ENABLE_GRID_LOCKING) return;
        
        this.gridLocked = true;
        this.gridLockReason = (reason != null) ? reason : "MANUAL";
        this.gridLockTime = System.currentTimeMillis();
        
        if (RugrelDropdownConfig.DEBUG_GRID_LOCKING) {
            System.out.println("🔒 Grid locked - Reason: " + gridLockReason);
        }
    }
    
    /**
     * Disable zoom operations (Grid Fixed - Universal Zoom Disable)
     */
    public void disableZoom(String reason) {
        if (!RugrelDropdownConfig.DISABLE_ALL_ZOOM_OPERATIONS && !RugrelDropdownConfig.GRID_COMPLETELY_FIXED) return;
        
        this.zoomDisabled = true;
        this.lockedZoomLevel = this.zoomLevel;  // Remember current zoom level
        
        if (RugrelDropdownConfig.DEBUG_GRID_LOCKING) {
            System.out.println("🚫 Zoom disabled at level: " + String.format("%.0f%%", lockedZoomLevel * 100) + " - Reason: " + reason + " (Grid Fixed)");
        }
    }
    
    /**
     * Auto-lock grid and disable zoom (Grid Fixed - Universal Application)
     */
    public void autoLockForMarkTab() {
        if (RugrelDropdownConfig.AUTO_LOCK_GRID_ON_STARTUP || RugrelDropdownConfig.GRID_COMPLETELY_FIXED) {
            lockGrid("GRID_FIXED_AUTO_LOCK");
        }
        
        if (RugrelDropdownConfig.DISABLE_ALL_ZOOM_OPERATIONS || RugrelDropdownConfig.GRID_COMPLETELY_FIXED) {
            disableZoom("GRID_FIXED_ZOOM_DISABLE");
        }
        
        if (RugrelDropdownConfig.RESET_ZOOM_ON_GRID_LOCK) {
            this.zoomLevel = 1.0;  // Reset to default zoom
            repaint();
        }
    }
    
    /**
     * Initialize fixed grid state on application startup
     */
    public void initializeFixedGrid() {
        if (RugrelDropdownConfig.GRID_COMPLETELY_FIXED) {
            lockGrid("APPLICATION_STARTUP_GRID_FIXED");
            disableZoom("APPLICATION_STARTUP_GRID_FIXED");
            this.zoomLevel = RugrelDropdownConfig.FIXED_ZOOM_LEVEL;  // Set to fixed zoom level
            
            if (RugrelDropdownConfig.DEBUG_GRID_LOCKING) {
                System.out.println("🔒 Grid completely fixed on startup - No zoom or modifications allowed");
                System.out.println("🔍 Zoom locked at: " + String.format("%.0f%%", this.zoomLevel * 100));
            }
            
            repaint();
        }
    }
    
    /**
     * Check if grid is currently locked
     */
    public boolean isGridLocked() {
        return RugrelDropdownConfig.ENABLE_GRID_LOCKING && gridLocked;
    }
    
    /**
     * Check if zoom operations are disabled (Grid Fixed - All Zoom Disabled)
     */
    public boolean isZoomDisabled() {
        return RugrelDropdownConfig.GRID_COMPLETELY_FIXED || 
               RugrelDropdownConfig.DISABLE_ALL_ZOOM_OPERATIONS ||
               RugrelDropdownConfig.PERMANENT_GRID_LOCK ||
               (RugrelDropdownConfig.DISABLE_ZOOM_IN_ALL_TABS && 
                RugrelDropdownConfig.DISABLE_ZOOM_OUT_ALL_TABS);
    }
    
    /**
     * Attempt zoom in (with grid lock protection)
     */
    public void zoomIn() {
        if (isZoomDisabled()) {
            if (RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE) {
                System.out.println("🚫 Zoom In blocked - Grid is fixed, no zoom operations allowed");
            }
            return;
        }
        
        double newZoom = Math.min(zoomLevel * 1.25, 5.0);  // Max 500% zoom
        setZoomLevel(newZoom);
    }
    
    /**
     * Attempt zoom out (with grid lock protection)
     */
    public void zoomOut() {
        if (isZoomDisabled()) {
            if (RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE) {
                System.out.println("🚫 Zoom Out blocked - Grid is fixed, no zoom operations allowed");
            }
            return;
        }
        
        double newZoom = Math.max(zoomLevel / 1.25, 0.1);  // Min 10% zoom
        setZoomLevel(newZoom);
    }
    
    /**
     * Attempt zoom to fit (with grid lock protection)
     */
    public void zoomToFit() {
        if (RugrelDropdownConfig.DISABLE_ZOOM_FIT_ALL_TABS || isZoomDisabled()) {
            if (RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE) {
                System.out.println("🚫 Zoom to Fit blocked - Grid is fixed, no zoom operations allowed");
            }
            return;
        }
        
        setZoomLevel(1.0);  // Reset to 100%
    }
    
    /**
     * Attempt zoom reset (with grid lock protection)
     */
    public void resetZoom() {
        if (RugrelDropdownConfig.DISABLE_ZOOM_RESET_ALL_TABS || isZoomDisabled()) {
            if (RugrelDropdownConfig.SHOW_ZOOM_DISABLED_MESSAGE) {
                System.out.println("🚫 Zoom Reset blocked - Grid is fixed, no zoom operations allowed");
            }
            return;
        }
        
        setZoomLevel(1.0);  // Reset to 100%
    }
    
    /**
     * Get grid lock status information
     */
    public String getGridLockInfo() {
        if (!isGridLocked()) {
            return "Grid: Unlocked";
        }
        
        return String.format("Grid: Locked [%s] - Zoom: %s", 
                           gridLockReason, 
                           isZoomDisabled() ? "DISABLED" : "ENABLED");
    }
    
    /**
     * Unlock grid (if permitted)
     */
    public boolean attemptUnlockGrid() {
        if (!RugrelDropdownConfig.ENABLE_GRID_UNLOCK_SHORTCUT) {
            if (RugrelDropdownConfig.DEBUG_GRID_LOCKING) {
                System.out.println("🚫 Grid unlock blocked - unlock shortcut disabled");
            }
            return false;
        }
        
        this.gridLocked = false;
        this.zoomDisabled = false;
        this.gridLockReason = null;
        
        if (RugrelDropdownConfig.DEBUG_GRID_LOCKING) {
            System.out.println("🔓 Grid unlocked - zoom re-enabled");
        }
        
        return true;
    }
    
    /**
     * Intelligent panning control methods with smart navigation
     */
    public void panToCenter() {
        viewOffsetX = 0;
        viewOffsetY = 0;
        System.out.println("📍 Pan to Center: View centered, Zoom: " + String.format("%.0f%%", zoomLevel * 100));
        repaint();
    }
    
    public void smartPanBy(int deltaX, int deltaY) {
        // Apply smart panning with the same constraints as mouse drag
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        
        int newViewOffsetX = viewOffsetX + deltaX;
        int newViewOffsetY = viewOffsetY + deltaY;
        
        // Apply intelligent boundary constraints
        if (zoomLevel > 1.0) {
            int maxOffsetX = (int)(canvasWidth * zoomLevel * 0.5);
            int maxOffsetY = (int)(canvasHeight * zoomLevel * 0.5);
            newViewOffsetX = Math.max(-maxOffsetX, Math.min(maxOffsetX, newViewOffsetX));
            newViewOffsetY = Math.max(-maxOffsetY, Math.min(maxOffsetY, newViewOffsetY));
        }
        
        viewOffsetX = newViewOffsetX;
        viewOffsetY = newViewOffsetY;
        
        System.out.printf("⬅️➡️ Smart Pan: (%+d,%+d) → Offset(%d,%d) Zoom:%.0f%%\n", 
                         deltaX, deltaY, viewOffsetX, viewOffsetY, zoomLevel * 100);
        repaint();
    }
    
    public void getPanningInstructions() {
        System.out.println("🖱️ SMART PANNING CONTROLS:");
        System.out.println("   • Middle Mouse Button + Drag = Pan");
        System.out.println("   • Right Mouse Button + Drag = Pan"); 
        System.out.println("   • Ctrl + Left Click + Drag = Pan");
        System.out.println("   • Shift + Left Click + Drag = Pan");
        System.out.println("   • Mouse Wheel = Zoom In/Out");
        System.out.println("   • Zoom Range: 10% - 1000%");
        System.out.println("   • Smart boundaries prevent excessive panning");
    }
    
    // Preview and visualization control methods
    public void setGridVisible(boolean visible) {
        this.gridVisible = visible;
        repaint();
    }
    
    public boolean isGridVisible() {
        return gridVisible;
    }
    
    public void setMaterialBoundaryVisible(boolean visible) {
        this.materialBoundaryVisible = visible;
        repaint();
    }
    
    public boolean isMaterialBoundaryVisible() {
        return materialBoundaryVisible;
    }
    
    public void setGridInfoVisible(boolean visible) {
        this.gridInfoVisible = visible;
        repaint();
    }
    
    public boolean isGridInfoVisible() {
        return gridInfoVisible;
    }
    
    public void setDotPreviewEnabled(boolean enabled) {
        this.dotPreviewEnabled = enabled;
        repaint();
    }
    
    public boolean isDotPreviewEnabled() {
        return dotPreviewEnabled;
    }
    
    public void mouseClicked(MouseEvent e) {
        // Handle double-click for text editing
        if (e.getClickCount() == 2 && selectedMark instanceof ArcLettersMark) {
            ArcLettersMark arcMark = (ArcLettersMark) selectedMark;
            editArcLettersText(arcMark);
        }
        // Handle double-click for DotMatrix data editing
        else if (e.getClickCount() == 2 && selectedMark instanceof DotMatrixMark) {
            DotMatrixMark dotMatrix = (DotMatrixMark) selectedMark;
            editDotMatrixData(dotMatrix);
        }
        // Handle double-click for GraphMark image upload/management
        else if (e.getClickCount() == 2 && selectedMark instanceof GraphMark) {
            GraphMark graphMark = (GraphMark) selectedMark;
            System.out.println("🖼️ Double-click detected on GraphMark - Opening image upload dialog...");
            boolean changed = graphMark.openImageUploadDialog();
            if (changed) {
                repaint(); // Refresh display after image changes
                System.out.println("✅ GraphMark image updated successfully");
            }
        }
    }
    
    // Method to edit Arc Letters text
    private void editArcLettersText(ArcLettersMark arcMark) {
        String currentText = arcMark.getLetters();
        String newText = (String) JOptionPane.showInputDialog(
            this,
            "Edit Arc Letters Text:",
            "Text Editor",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            currentText
        );
        
        if (newText != null && !newText.trim().isEmpty()) {
            saveStateForUndo(); // Save state before making changes
            arcMark.setLetters(newText.trim());
            notifySelectionChanged(); // Update the content field
            repaint();
        }
    }
    
    // Method to edit DotMatrix data
    private void editDotMatrixData(DotMatrixMark dotMatrix) {
        // Create a dialog with multiple input fields
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Data input field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createDarkLabel("Data:"), gbc);
        gbc.gridx = 1;
        JTextField dataField = new JTextField(dotMatrix.getData(), 15);
        panel.add(dataField, gbc);
        
        // Dot pitch slider
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDarkLabel("Dot Pitch:"), gbc);
        gbc.gridx = 1;
        JSlider pitchSlider = new JSlider(4, 20, dotMatrix.getDotPitch());
        pitchSlider.setMajorTickSpacing(4);
        pitchSlider.setPaintTicks(true);
        pitchSlider.setPaintLabels(true);
        panel.add(pitchSlider, gbc);
        
        // Dot diameter slider
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDarkLabel("Dot Diameter:"), gbc);
        gbc.gridx = 1;
        JSlider diameterSlider = new JSlider(2, 15, dotMatrix.getDotDiameter());
        diameterSlider.setMajorTickSpacing(3);
        diameterSlider.setPaintTicks(true);
        diameterSlider.setPaintLabels(true);
        panel.add(diameterSlider, gbc);
        
        // Matrix size dropdown
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createDarkLabel("Matrix Size:"), gbc);
        gbc.gridx = 1;
        Integer[] sizes = {10, 12, 14, 16, 18, 20, 22, 24};
        JComboBox<Integer> sizeCombo = new JComboBox<>(sizes);
        sizeCombo.setSelectedItem(dotMatrix.getMatrixSize());
        panel.add(sizeCombo, gbc);
        
        // Grid display checkbox
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(createDarkLabel("Show Grid:"), gbc);
        gbc.gridx = 1;
        JCheckBox gridCheckbox = new JCheckBox("", dotMatrix.isShowGrid());
        panel.add(gridCheckbox, gbc);
        
        // Border display checkbox
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(createDarkLabel("Show Border:"), gbc);
        gbc.gridx = 1;
        JCheckBox borderCheckbox = new JCheckBox("", dotMatrix.isShowBorder());
        panel.add(borderCheckbox, gbc);
        
        // Export path button
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton exportButton = new JButton("Export Dot Path");
        exportButton.addActionListener(e -> {
            String pathConfig = dotMatrix.exportDotPathConfig();
            JTextArea textArea = new JTextArea(pathConfig);
            textArea.setRows(15);
            textArea.setColumns(50);
            textArea.setEditable(false);
            textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(this, scrollPane, "Dot Path Configuration", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(exportButton, gbc);
        
        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Edit Data Matrix",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            saveStateForUndo(); // Save state before making changes
            
            // Apply changes
            String newData = dataField.getText().trim();
            if (!newData.isEmpty()) {
                dotMatrix.setData(newData);
            }
            
            dotMatrix.setDotPitch(pitchSlider.getValue());
            dotMatrix.setDotDiameter(diameterSlider.getValue());
            dotMatrix.setMatrixSize((Integer) sizeCombo.getSelectedItem());
            dotMatrix.setShowGrid(gridCheckbox.isSelected());
            dotMatrix.setShowBorder(borderCheckbox.isSelected());
            
            // Notify selection listener and repaint
            notifySelectionChanged();
            repaint();
        }
    }

    // Interface for selection change notifications
    public interface SelectionListener {
        void onSelectionChanged(Mark selectedMark);
    }
    
    private SelectionListener selectionListener;
    
    // Set the selection listener
    public void setSelectionListener(SelectionListener listener) {
        this.selectionListener = listener;
    }
    
    // Notify selection change
    private void notifySelectionChanged() {
        if (selectionListener != null) {
            selectionListener.onSelectionChanged(selectedMark);
        }
        
        // Update PropertyStrip with object coordinate information
        if (propertyStrip != null) {
            propertyStrip.setSelectedMark(selectedMark);
        }
        
        // COORDINATE TRACKING FIX: Update ThorX6 properties panel coordinates
        try {
            ThorX6PropertiesConfig.updateSelectedMarkCoordinates(selectedMark);
        } catch (Exception e) {
            System.err.println("❌ Error updating ThorX6 coordinates: " + e.getMessage());
        }
    }
    
    // Control handle detection for DotMatrix
    public enum DotMatrixControlHandle {
        NONE, RESIZE, DOT_PITCH, DOT_DIAMETER, MATRIX_SIZE
    }
    
    private static final int HANDLE_SIZE = 10; // Define HANDLE_SIZE constant
    
    // Method to check which DotMatrix control handle is being clicked
    public DotMatrixControlHandle getDotMatrixControlHandle(DotMatrixMark dotMatrix, int px, int py) {
        // Check resize handle
        if (px >= dotMatrix.x + dotMatrix.width - HANDLE_SIZE && px <= dotMatrix.x + dotMatrix.width &&
            py >= dotMatrix.y + dotMatrix.height - HANDLE_SIZE && py <= dotMatrix.y + dotMatrix.height) {
            return DotMatrixControlHandle.RESIZE;
        }
        
        // Check configuration handles (positioned to the right of the matrix)
        int handleX = dotMatrix.x + dotMatrix.width + 5;
        int handleRadius = 4;
        
        // Dot pitch control
        if (Math.sqrt((px - handleX) * (px - handleX) + (py - (dotMatrix.y + 10)) * (py - (dotMatrix.y + 10))) <= handleRadius) {
            return DotMatrixControlHandle.DOT_PITCH;
        }
        
        // Dot diameter control
        if (Math.sqrt((px - handleX) * (px - handleX) + (py - (dotMatrix.y + 25)) * (py - (dotMatrix.y + 25))) <= handleRadius) {
            return DotMatrixControlHandle.DOT_DIAMETER;
        }
        
        // Matrix size control
        if (Math.sqrt((px - handleX) * (px - handleX) + (py - (dotMatrix.y + 40)) * (py - (dotMatrix.y + 40))) <= handleRadius) {
            return DotMatrixControlHandle.MATRIX_SIZE;
        }
        
        return DotMatrixControlHandle.NONE;
    }
    
    // ===============================================================================
    // SIMPLE 14x8 CELL GRID SYSTEM - EXACTLY WHAT USER WANTS
    // ===============================================================================
    
    // Soft-coded 14x8 cell grid configuration
    private static final int GRID_COLUMNS = 14;               // Exactly 14 columns
    private static final int GRID_ROWS = 8;                   // Exactly 8 rows
    private static final Color CELL_GRID_COLOR = new Color(150, 150, 150, 120); // Clear grid lines
    private static final float CELL_LINE_WIDTH = 1.5f;        // Visible line thickness
    private static final boolean SHOW_CELL_LABELS = false;    // Optional cell labeling (A1, B2, etc.)
    
    /**
     * Simple 14x8 Cell Grid - Exactly What User Requested
     * Divides canvas into exactly 14 columns x 8 rows of cells
     */
    private void drawPreviewGrid(Graphics2D g2d) {
        // Save original graphics state
        Stroke originalStroke = g2d.getStroke();
        Color originalColor = g2d.getColor();
        
        // Get canvas dimensions
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        
        // ============================================================================
        // DYNAMIC WORKING AREA CALCULATION (like previous design)
        // ============================================================================
        
        // Define working area dimensions (similar to previous industrial grid)
        double mmToPixel = 3.78; // Standard conversion
        int workingAreaMM_X = 200; // 200mm working area width
        int workingAreaMM_Y = 150; // 150mm working area height
        
        // Calculate working area in pixels
        int workingAreaWidth = (int)(workingAreaMM_X * mmToPixel);
        int workingAreaHeight = (int)(workingAreaMM_Y * mmToPixel);
        
        // Center the working area on canvas with margins
        int offsetX = Math.max(60, (canvasWidth - workingAreaWidth) / 2);
        int offsetY = Math.max(40, (canvasHeight - workingAreaHeight) / 2);
        
        // Adjust working area if canvas is too small
        if (workingAreaWidth > canvasWidth - 120) {
            workingAreaWidth = canvasWidth - 120;
        }
        if (workingAreaHeight > canvasHeight - 80) {
            workingAreaHeight = canvasHeight - 80;
        }
        
        // ============================================================================
        // 14x8 CELL GRID WITHIN WORKING AREA
        // ============================================================================
        
        // Calculate cell dimensions within the working area
        double cellWidth = (double) workingAreaWidth / GRID_COLUMNS;   // Width of each cell
        double cellHeight = (double) workingAreaHeight / GRID_ROWS;    // Height of each cell
        
        // Set grid line properties
        g2d.setColor(CELL_GRID_COLOR);
        g2d.setStroke(new BasicStroke(CELL_LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); // Crisp lines
        
        // Draw working area boundary
        g2d.setColor(new Color(100, 150, 255, 150)); // Blue boundary like previous design
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawRect(offsetX, offsetY, workingAreaWidth, workingAreaHeight);
        
        // Draw 14x8 cell grid lines within working area
        g2d.setColor(CELL_GRID_COLOR);
        g2d.setStroke(new BasicStroke(CELL_LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Draw vertical cell lines (14 columns)
        for (int col = 0; col <= GRID_COLUMNS; col++) {
            int x = offsetX + (int) (col * cellWidth);
            g2d.drawLine(x, offsetY, x, offsetY + workingAreaHeight);
        }
        
        // Draw horizontal cell lines (8 rows)  
        for (int row = 0; row <= GRID_ROWS; row++) {
            int y = offsetY + (int) (row * cellHeight);
            g2d.drawLine(offsetX, y, offsetX + workingAreaWidth, y);
        }
        
        // Optional: Draw cell labels if enabled (A1, A2, B1, B2, etc.)
        if (SHOW_CELL_LABELS) {
            drawCellLabels(g2d, offsetX, offsetY, cellWidth, cellHeight);
        }
        
        // Restore original graphics state
        g2d.setStroke(originalStroke);
        g2d.setColor(originalColor);
        
        // Debug info (can be enabled for testing)
        if (false) { // Set to true for debugging
            System.out.println("� 14x8 Cell Grid: CellW=" + String.format("%.1f", cellWidth) + 
                             "px, CellH=" + String.format("%.1f", cellHeight) + "px");
        }
    }
    
    /**
     * Draw cell labels for 14x8 grid (optional - A1, A2, B1, B2, etc.)
     */
    private void drawCellLabels(Graphics2D g2d, int offsetX, int offsetY, double cellWidth, double cellHeight) {
        // Check if coordinate system is enabled via soft coding
        if (!RugrelDropdownConfig.ENABLE_COORDINATE_LABELS_IN_GRID && !RugrelDropdownConfig.SHOW_GRID_COORDINATES) {
            return; // Skip drawing if disabled
        }
        
        g2d.setColor(RugrelDropdownConfig.COORDINATE_TEXT_COLOR);
        g2d.setFont(RugrelDropdownConfig.COORDINATE_TEXT_FONT);
        FontMetrics fm = g2d.getFontMetrics();
        
        // Draw pointwise coordinates for each cell
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLUMNS; col++) {
                // Calculate center position of cell within working area
                int centerX = offsetX + (int) ((col + 0.5) * cellWidth);
                int centerY = offsetY + (int) ((row + 0.5) * cellHeight);
                
                // Convert cell center to pointwise coordinates
                double[] pointwise = pixelToPointwise(centerX, centerY);
                double pointwiseX = pointwise[0];
                double pointwiseY = pointwise[1];
                
                String coordinateLabel = "";
                
                // Show coordinates based on configuration
                if (RugrelDropdownConfig.SHOW_CELL_X_COORDINATES && RugrelDropdownConfig.SHOW_CELL_Y_COORDINATES) {
                    // Show both X and Y coordinates
                    coordinateLabel = String.format(RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT, pointwiseX) + 
                                    "," + String.format(RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT, pointwiseY);
                } else if (RugrelDropdownConfig.SHOW_CELL_X_COORDINATES) {
                    // Show only X coordinate
                    coordinateLabel = "X:" + String.format(RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT, pointwiseX);
                } else if (RugrelDropdownConfig.SHOW_CELL_Y_COORDINATES) {
                    // Show only Y coordinate  
                    coordinateLabel = "Y:" + String.format(RugrelDropdownConfig.POINTWISE_COORDINATE_FORMAT, pointwiseY);
                } else {
                    // Fallback to traditional cell labels if no coordinate display is enabled
                    char rowLetter = (char) ('A' + row);
                    int colNumber = col + 1;
                    coordinateLabel = rowLetter + String.valueOf(colNumber);
                }
                
                // Draw coordinate label centered in cell
                int textWidth = fm.stringWidth(coordinateLabel);
                int textHeight = fm.getAscent();
                g2d.drawString(coordinateLabel, centerX - textWidth/2, centerY + textHeight/2 - 2);
            }
        }
    }
    
    // ===============================================================================
    // 14x8 CELL GRID CONFIGURATION METHODS  
    // ===============================================================================
    
    /**
     * Get cell dimensions for 14x8 grid within working area
     */
    public Dimension getCellDimensions() {
        Rectangle workArea = getWorkingArea();
        double cellWidth = (double) workArea.width / GRID_COLUMNS;
        double cellHeight = (double) workArea.height / GRID_ROWS;
        return new Dimension((int) cellWidth, (int) cellHeight);
    }
    
    /**
     * Get which cell contains a given point
     */
    public Point getCellAt(int x, int y) {
        Rectangle workArea = getWorkingArea();
        
        // Check if point is within working area
        if (x < workArea.x || x >= workArea.x + workArea.width ||
            y < workArea.y || y >= workArea.y + workArea.height) {
            return new Point(-1, -1); // Outside working area
        }
        
        // Calculate relative position within working area
        int relativeX = x - workArea.x;
        int relativeY = y - workArea.y;
        
        Dimension cellSize = getCellDimensions();
        int col = relativeX / cellSize.width;
        int row = relativeY / cellSize.height;
        
        // Clamp to valid range
        col = Math.max(0, Math.min(GRID_COLUMNS - 1, col));
        row = Math.max(0, Math.min(GRID_ROWS - 1, row));
        
        return new Point(col, row);
    }
    
    /**
     * Get cell label (A1, B2, etc.)
     */
    public String getCellLabel(int col, int row) {
        if (col >= 0 && col < GRID_COLUMNS && row >= 0 && row < GRID_ROWS) {
            char rowLetter = (char) ('A' + row);
            int colNumber = col + 1;
            return rowLetter + String.valueOf(colNumber);
        }
        return "Invalid";
    }
    
    /**
     * Get grid info
     */
    public String getGridInfo() {
        return GRID_COLUMNS + "x" + GRID_ROWS + " cells";
    }
    
    // ==================== POINTWISE COORDINATE SYSTEM (0-10 SCALE) ====================
    
    /**
     * Convert pixel coordinates to pointwise coordinates (0-10 scale)
     */
    public double[] pixelToPointwise(int pixelX, int pixelY) {
        if (!RugrelDropdownConfig.ENABLE_POINTWISE_COORDINATES) {
            return new double[]{pixelX, pixelY}; // Return original coordinates if disabled
        }
        
        // Get current working area and grid parameters
        Rectangle workArea = getWorkingArea();
        
        // Calculate relative position within working area
        double relativeX = (double)(pixelX - workArea.x) / workArea.width;
        double relativeY = (double)(pixelY - workArea.y) / workArea.height;
        
        // Clamp to valid range [0, 1]
        relativeX = Math.max(0.0, Math.min(1.0, relativeX));
        relativeY = Math.max(0.0, Math.min(1.0, relativeY));
        
        // Convert to pointwise scale
        double pointwiseX = RugrelDropdownConfig.COORDINATE_X_MIN + 
                          relativeX * (RugrelDropdownConfig.COORDINATE_X_MAX - RugrelDropdownConfig.COORDINATE_X_MIN);
        double pointwiseY = RugrelDropdownConfig.COORDINATE_Y_MIN + 
                          relativeY * (RugrelDropdownConfig.COORDINATE_Y_MAX - RugrelDropdownConfig.COORDINATE_Y_MIN);
        
        return new double[]{pointwiseX, pointwiseY};
    }
    
    /**
     * Convert pointwise coordinates (0-10 scale) to pixel coordinates
     */
    public int[] pointwiseToPixel(double pointwiseX, double pointwiseY) {
        if (!RugrelDropdownConfig.ENABLE_POINTWISE_COORDINATES) {
            return new int[]{(int)pointwiseX, (int)pointwiseY}; // Return original coordinates if disabled
        }
        
        // Get current working area
        Rectangle workArea = getWorkingArea();
        
        // Calculate relative position in pointwise scale
        double relativeX = (pointwiseX - RugrelDropdownConfig.COORDINATE_X_MIN) / 
                          (RugrelDropdownConfig.COORDINATE_X_MAX - RugrelDropdownConfig.COORDINATE_X_MIN);
        double relativeY = (pointwiseY - RugrelDropdownConfig.COORDINATE_Y_MIN) / 
                          (RugrelDropdownConfig.COORDINATE_Y_MAX - RugrelDropdownConfig.COORDINATE_Y_MIN);
        
        // Convert to pixel coordinates
        int pixelX = workArea.x + (int)(relativeX * workArea.width);
        int pixelY = workArea.y + (int)(relativeY * workArea.height);
        
        return new int[]{pixelX, pixelY};
    }
    
    /**
     * Get working area rectangle for coordinate calculations
     */
    private Rectangle getWorkingArea() {
        // Same calculation as in drawPreviewGrid method
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        
        double mmToPixel = 3.78;
        int workingAreaMM_X = 200;
        int workingAreaMM_Y = 150;
        
        int workingAreaWidth = (int)(workingAreaMM_X * mmToPixel);
        int workingAreaHeight = (int)(workingAreaMM_Y * mmToPixel);
        
        int offsetX = Math.max(60, (canvasWidth - workingAreaWidth) / 2);
        int offsetY = Math.max(40, (canvasHeight - workingAreaHeight) / 2);
        
        if (workingAreaWidth > canvasWidth - 120) {
            workingAreaWidth = canvasWidth - 120;
        }
        if (workingAreaHeight > canvasHeight - 80) {
            workingAreaHeight = canvasHeight - 80;
        }
        
        return new Rectangle(offsetX, offsetY, workingAreaWidth, workingAreaHeight);
    }
    
    /**
     * Get grid cell coordinates for a given pixel position
     */
    public int[] getGridCellFromPixel(int pixelX, int pixelY) {
        Rectangle workArea = getWorkingArea();
        
        double cellWidth = (double) workArea.width / GRID_COLUMNS;
        double cellHeight = (double) workArea.height / GRID_ROWS;
        
        int col = (int) ((pixelX - workArea.x) / cellWidth);
        int row = (int) ((pixelY - workArea.y) / cellHeight);
        
        col = Math.max(0, Math.min(GRID_COLUMNS - 1, col));
        row = Math.max(0, Math.min(GRID_ROWS - 1, row));
        
        return new int[]{col, row};
    }
    
    /**
     * Draw professional grid information box in top-right corner
     */
    private void drawGridInfoBox(Graphics g) {
        // Professional styling using soft-coded configuration
        Font titleFont = new Font("Segoe UI", Font.BOLD, 11);
        Font infoFont = new Font("Segoe UI", Font.PLAIN, 10);
        Color borderColor = RugrelDropdownConfig.GRID_INFO_BOX_BORDER;
        Color bgColor = RugrelDropdownConfig.GRID_INFO_BOX_BACKGROUND;
        Color titleColor = RugrelDropdownConfig.GRID_INFO_BOX_TITLE_COLOR;
        Color textColor = RugrelDropdownConfig.GRID_INFO_BOX_TEXT_COLOR;
        
        // Calculate box dimensions and position
        FontMetrics titleFm = g.getFontMetrics(titleFont);
        FontMetrics infoFm = g.getFontMetrics(infoFont);
        
        String title = "Grid Info";
        String gridSize = RugrelDropdownConfig.SHOW_GRID_SIZE_INFO ? (GRID_COLUMNS + "x" + GRID_ROWS + " cells") : "";
        String zoomInfo = RugrelDropdownConfig.SHOW_ZOOM_LEVEL_INFO ? String.format("Zoom: %.0f%%", zoomLevel * 100) : "";
        String snapInfo = RugrelDropdownConfig.SHOW_SNAP_STATUS_INFO ? ("Snap: " + (ThorX6GridConfig.ENABLE_GRID_SNAPPING ? "ON" : "OFF")) : "";
        String restrictInfo = RugrelDropdownConfig.SHOW_RESTRICTION_STATUS_INFO ? ("Lock: " + (RugrelDropdownConfig.LOCK_ZOOM_LEVEL ? "ON" : "OFF")) : "";
        
        // Calculate maximum text width
        int maxWidth = titleFm.stringWidth(title);
        if (!gridSize.isEmpty()) maxWidth = Math.max(maxWidth, infoFm.stringWidth(gridSize));
        if (!zoomInfo.isEmpty()) maxWidth = Math.max(maxWidth, infoFm.stringWidth(zoomInfo));
        if (!snapInfo.isEmpty()) maxWidth = Math.max(maxWidth, infoFm.stringWidth(snapInfo));
        if (!restrictInfo.isEmpty()) maxWidth = Math.max(maxWidth, infoFm.stringWidth(restrictInfo));
        
        int boxWidth = Math.max(RugrelDropdownConfig.GRID_INFO_BOX_WIDTH, maxWidth + 20);  // Use configured width or text width + padding
        int lineCount = 1; // Title
        if (!gridSize.isEmpty()) lineCount++;
        if (!zoomInfo.isEmpty()) lineCount++;
        if (!snapInfo.isEmpty()) lineCount++;
        if (!restrictInfo.isEmpty()) lineCount++;
        
        int boxHeight = titleFm.getHeight() + (infoFm.getHeight() * (lineCount - 1)) + 18;  // Dynamic height based on enabled info
        
        // Position in top-right corner with configured margin
        int boxX = getWidth() - boxWidth - RugrelDropdownConfig.GRID_INFO_BOX_MARGIN;
        int boxY = RugrelDropdownConfig.GRID_INFO_BOX_MARGIN;
        
        // Draw box background with rounded corners
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background
        g2d.setColor(bgColor);
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, RugrelDropdownConfig.GRID_INFO_BOX_CORNER_RADIUS, RugrelDropdownConfig.GRID_INFO_BOX_CORNER_RADIUS);
        
        // Border (if enabled)
        if (RugrelDropdownConfig.SHOW_GRID_INFO_BOX_BORDER) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, RugrelDropdownConfig.GRID_INFO_BOX_CORNER_RADIUS, RugrelDropdownConfig.GRID_INFO_BOX_CORNER_RADIUS);
        }
        
        // Draw title
        g2d.setFont(titleFont);
        g2d.setColor(titleColor);
        int titleX = boxX + (boxWidth - titleFm.stringWidth(title)) / 2;
        int titleY = boxY + titleFm.getAscent() + 5;
        g2d.drawString(title, titleX, titleY);
        
        // Draw separator line
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(1.0f));
        int lineY = titleY + 3;
        g2d.drawLine(boxX + 5, lineY, boxX + boxWidth - 5, lineY);
        
        // Draw info lines (only enabled ones)
        g2d.setFont(infoFont);
        g2d.setColor(textColor);
        int infoX = boxX + 8;
        int currentY = lineY + infoFm.getAscent() + 5;
        
        if (!gridSize.isEmpty()) {
            g2d.drawString(gridSize, infoX, currentY);
            currentY += infoFm.getHeight();
        }
        
        if (!zoomInfo.isEmpty()) {
            g2d.drawString(zoomInfo, infoX, currentY);
            currentY += infoFm.getHeight();
        }
        
        if (!snapInfo.isEmpty()) {
            g2d.drawString(snapInfo, infoX, currentY);
            currentY += infoFm.getHeight();
        }
        
        if (!restrictInfo.isEmpty()) {
            g2d.drawString(restrictInfo, infoX, currentY);
        }
        
        g2d.dispose();
    }

    /**
     * Draw grid lock visual indicators
     */
    private void drawGridLockIndicators(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (RugrelDropdownConfig.HIGHLIGHT_LOCKED_GRID_BORDER) {
            // Draw red border around entire canvas to indicate locked state
            g2d.setColor(new Color(255, 0, 0, 100));  // Semi-transparent red
            g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                                        1.0f, new float[]{10, 5}, 0));
            g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
        }
        
        if (RugrelDropdownConfig.USE_LOCK_OVERLAY_FOR_GRID) {
            // Draw lock overlay in top-left corner
            int lockSize = 32;
            int lockX = 10;
            int lockY = 10;
            
            // Lock background
            g2d.setColor(new Color(255, 255, 255, 220));
            g2d.fillRoundRect(lockX - 4, lockY - 4, lockSize + 8, lockSize + 8, 8, 8);
            
            // Lock border
            g2d.setColor(new Color(255, 0, 0));
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawRoundRect(lockX - 4, lockY - 4, lockSize + 8, lockSize + 8, 8, 8);
            
            // Lock icon
            g2d.setColor(new Color(255, 0, 0));
            g2d.setStroke(new BasicStroke(3.0f));
            
            // Lock body (rectangle)
            g2d.fillRect(lockX + 8, lockY + 16, 16, 12);
            
            // Lock shackle (arc)
            g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawArc(lockX + 10, lockY + 4, 12, 16, 0, 180);
            
            // Lock status text
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.drawString("GRID", lockX + 5, lockY + lockSize + 15);
            g2d.drawString("LOCKED", lockX + 1, lockY + lockSize + 27);
        }
        
        if (RugrelDropdownConfig.SHOW_GRID_LOCK_STATUS_MESSAGE) {
            // Draw status message in bottom-left corner
            String lockMessage = getGridLockInfo();
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int msgWidth = fm.stringWidth(lockMessage);
            int msgHeight = fm.getHeight();
            
            int msgX = 10;
            int msgY = getHeight() - 50;
            
            // Message background
            g2d.setColor(new Color(255, 0, 0, 200));  // Red background
            g2d.fillRoundRect(msgX - 5, msgY - msgHeight + 2, msgWidth + 10, msgHeight + 4, 6, 6);
            
            // Message text
            g2d.setColor(Color.WHITE);
            g2d.drawString(lockMessage, msgX, msgY);
        }
        
        // Draw zoom disabled indicator
        if (isZoomDisabled()) {
            g2d.setColor(new Color(255, 100, 0, 180));  // Orange for zoom disabled
            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            String zoomMsg = "ZOOM DISABLED";
            FontMetrics fm = g2d.getFontMetrics();
            int zoomMsgX = getWidth() - fm.stringWidth(zoomMsg) - 15;
            int zoomMsgY = getHeight() - 30;
            
            // Zoom disabled background
            g2d.fillRoundRect(zoomMsgX - 5, zoomMsgY - fm.getHeight() + 2, 
                            fm.stringWidth(zoomMsg) + 10, fm.getHeight() + 4, 4, 4);
            
            // Zoom disabled text
            g2d.setColor(Color.WHITE);
            g2d.drawString(zoomMsg, zoomMsgX, zoomMsgY);
        }
        
        g2d.dispose();
    }

    private void drawMaterialBoundary(Graphics2D g2d) {
        g2d.setColor(new Color(255, 100, 100, 128)); // Semi-transparent red
        g2d.setStroke(new BasicStroke(3.0f));
        
        // Draw material boundary rectangle (10 pixel margin from edges)
        int margin = 10;
        int boundaryWidth = getWidth() - (2 * margin);
        int boundaryHeight = getHeight() - (2 * margin);
        
        g2d.drawRect(margin, margin, boundaryWidth, boundaryHeight);
        
        // Add corner markers
        int markerSize = 8;
        g2d.fillRect(margin - markerSize/2, margin - markerSize/2, markerSize, markerSize);
        g2d.fillRect(margin + boundaryWidth - markerSize/2, margin - markerSize/2, markerSize, markerSize);
        g2d.fillRect(margin - markerSize/2, margin + boundaryHeight - markerSize/2, markerSize, markerSize);
        g2d.fillRect(margin + boundaryWidth - markerSize/2, margin + boundaryHeight - markerSize/2, markerSize, markerSize);
        
        // Reset stroke
        g2d.setStroke(new BasicStroke());
    }
    
    private void drawDotPreview(Graphics2D g2d) {
        g2d.setColor(new Color(0, 150, 0, 180)); // Semi-transparent green
        
        // Draw dot pattern for all marks that would create dots
        for (Mark mark : marks) {
            if (mark instanceof TextMark) {
                drawTextDotPreview(g2d, (TextMark) mark);
            } else if (mark instanceof BowTextMark) {
                drawBowTextDotPreview(g2d, (BowTextMark) mark);
            } else if (mark instanceof GraphMark) {
                drawGraphDotPreview(g2d, (GraphMark) mark);
            }
            // Add more mark types as needed
        }
    }
    
    private void drawTextDotPreview(Graphics2D g2d, TextMark textMark) {
        // Simulate dot pattern for text marking
        int dotSize = 2;
        int dotSpacing = 4;
        
        // Draw dots in a pattern that represents the text
        for (int x = textMark.x; x < textMark.x + textMark.width; x += dotSpacing) {
            for (int y = textMark.y; y < textMark.y + textMark.height; y += dotSpacing) {
                g2d.fillOval(x, y, dotSize, dotSize);
            }
        }
    }
    
    private void drawBowTextDotPreview(Graphics2D g2d, BowTextMark bowMark) {
        // Simulate dot pattern for bow text marking
        int dotSize = 2;
        int dotSpacing = 4;
        
        // Draw dots in a curved pattern
        for (int x = bowMark.x; x < bowMark.x + bowMark.width; x += dotSpacing) {
            for (int y = bowMark.y; y < bowMark.y + bowMark.height; y += dotSpacing) {
                g2d.fillOval(x, y, dotSize, dotSize);
            }
        }
    }
    
    private void drawGraphDotPreview(Graphics2D g2d, GraphMark graphMark) {
        // Simulate dot pattern for graph marking
        int dotSize = 2;
        int dotSpacing = 6;
        
        // Draw dots along the graph frame and diagonals
        // Frame dots
        for (int x = graphMark.x; x <= graphMark.x + graphMark.width; x += dotSpacing) {
            g2d.fillOval(x, graphMark.y, dotSize, dotSize); // Top edge
            g2d.fillOval(x, graphMark.y + graphMark.height, dotSize, dotSize); // Bottom edge
        }
        for (int y = graphMark.y; y <= graphMark.y + graphMark.height; y += dotSpacing) {
            g2d.fillOval(graphMark.x, y, dotSize, dotSize); // Left edge
            g2d.fillOval(graphMark.x + graphMark.width, y, dotSize, dotSize); // Right edge
        }
        
        // Diagonal dots
        for (int i = 0; i < Math.min(graphMark.width, graphMark.height); i += dotSpacing) {
            g2d.fillOval(graphMark.x + i, graphMark.y + i, dotSize, dotSize); // Main diagonal
            g2d.fillOval(graphMark.x + graphMark.width - i, graphMark.y + i, dotSize, dotSize); // Anti-diagonal
        }
    }
    
    /**
     * 14x8 Cell Grid Snapping - Snap to Cell Corners
     * Snaps selected object to nearest cell intersection
     */
    public void snapSelectedToGrid() {
        if (selectedMark == null || !gridVisible) return;
        
        saveStateForUndo();
        
        // Get working area and cell dimensions
        Rectangle workArea = getWorkingArea();
        Dimension cellSize = getCellDimensions();
        double cellWidth = cellSize.width;
        double cellHeight = cellSize.height;
        
        // Get current mark position
        int currentX = selectedMark.x;
        int currentY = selectedMark.y;
        
        // Calculate position relative to working area
        int relativeX = currentX - workArea.x;
        int relativeY = currentY - workArea.y;
        
        // Calculate nearest cell intersection within working area
        int snappedRelativeX = (int) (Math.round(relativeX / cellWidth) * cellWidth);
        int snappedRelativeY = (int) (Math.round(relativeY / cellHeight) * cellHeight);
        
        // Convert back to absolute coordinates
        int snappedX = workArea.x + snappedRelativeX;
        int snappedY = workArea.y + snappedRelativeY;
        
        // Update mark position
        selectedMark.x = snappedX;
        selectedMark.y = snappedY;
        
        repaint();
        
        // Get cell info for feedback
        Point cell = getCellAt(snappedX, snappedY);
        String cellLabel = getCellLabel(cell.x, cell.y);
        
        // Feedback message
        String message = String.format("Object snapped to 14x8 grid! Position: (%d, %d) | Cell: %s", 
                                      snappedX, snappedY, cellLabel);
        System.out.println("✅ " + message);
        
        // Non-blocking notification using tooltip
        setToolTipText(message);
        Timer tooltipClearTimer = new Timer(2000, e -> setToolTipText(null));
        tooltipClearTimer.setRepeats(false);
        tooltipClearTimer.start();
    }

    // Method to get grid coordinates for a pixel position
    public String getGridCoordinates(int pixelX, int pixelY) {
        double mmToPixel = 3.78;
        
        // Calculate working area offset
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        int workingAreaWidth = (int)(200 * mmToPixel);
        int workingAreaHeight = (int)(150 * mmToPixel);
        int offsetX = Math.max(60, (canvasWidth - workingAreaWidth) / 2);
        int offsetY = Math.max(40, (canvasHeight - workingAreaHeight) / 2);
        
        // Convert pixel coordinates to millimeter coordinates (origin at bottom-left)
        double mmX = (pixelX - offsetX) / mmToPixel;
        double mmY = (offsetY + workingAreaHeight - pixelY) / mmToPixel;
        
        // Clamp to working area
        mmX = Math.max(0, Math.min(200, mmX));
        mmY = Math.max(0, Math.min(150, mmY));
        
        return String.format("(%.1f, %.1f) mm", mmX, mmY);
    }
    
    // =============================
    // PROPERTY STRIP INTEGRATION
    // =============================
    
    /**
     * Set the property strip that displays selected object properties
     */
    public void setPropertyStrip(PropertyStrip propertyStrip) {
        this.propertyStrip = propertyStrip;
    }
    
    /**
     * Update the selected mark and notify property strip
     */
    private void updateSelectedMark(Mark newSelection) {
        selectedMark = newSelection;
        
        // Update property strip if connected
        if (propertyStrip != null) {
            propertyStrip.setSelectedMark(selectedMark);
        }
        
        repaint();
    }
    
    /**
     * Clear selection and update property strip
     */
    public void clearSelection() {
        updateSelectedMarkWithNotification(null);
    }
    
    // ==================== THORX6 INTEGRATION METHODS ====================

    // Using existing SelectionListener interface
    private List<SelectionListener> selectionListeners = new ArrayList<>();
    private String currentTool = "SELECT";
    
    /**
     * Add selection listener for ThorX6MarkPanel integration
     */
    public void addSelectionListener(SelectionListener listener) {
        if (listener != null && !selectionListeners.contains(listener)) {
            selectionListeners.add(listener);
        }
    }
    
    /**
     * Remove selection listener
     */
    public void removeSelectionListener(SelectionListener listener) {
        selectionListeners.remove(listener);
    }
    
    /**
     * Notify selection listeners when selection changes
     */
    private void notifySelectionChanged(Mark selectedMark) {
        for (SelectionListener listener : selectionListeners) {
            try {
                listener.onSelectionChanged(selectedMark);
            } catch (Exception e) {
                System.err.println("Error notifying selection listener: " + e.getMessage());
            }
        }
    }
    
    /**
     * Set current tool for ThorX6MarkPanel
     */
    public void setCurrentTool(String tool) {
        this.currentTool = tool != null ? tool : "SELECT";
        setCursor(getToolCursor(this.currentTool));
        System.out.println("🔧 Canvas tool set to: " + this.currentTool);
    }
    
    /**
     * Get current tool
     */
    public String getCurrentTool() {
        return currentTool;
    }
    
    /**
     * Get appropriate cursor for tool
     */
    private Cursor getToolCursor(String tool) {
        switch (tool.toUpperCase()) {
            case "SELECT":
                return Cursor.getDefaultCursor();
            case "MOVE":
                return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
            case "TEXT":
                return Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
            case "LINE":
            case "ARC":
            case "RECTANGLE":
            case "CIRCLE":
                return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            default:
                return Cursor.getDefaultCursor();
        }
    }
    
    /**
     * Override updateSelectedMark to notify listeners
     */
    private void updateSelectedMarkWithNotification(Mark mark) {
        updateSelectedMark(mark);
        notifySelectionChanged(mark);
    }
}
