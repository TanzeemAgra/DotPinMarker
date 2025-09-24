import java.awt.*;
import java.io.Serializable;

public abstract class Mark implements Serializable {
    private static final long serialVersionUID = 1L; // For project state serialization
    public int x, y, width, height;
    public int z = 0;                    // Z-depth for layering
    public double angle = 0.0;           // Rotation angle in degrees
    public String name = "Mark";         // Object name
    public boolean clearTrans = false;   // Clear transparency
    public boolean mirror = false;       // Mirror effect
    public boolean lockSize = false;     // Lock size (prevent resizing)
    public boolean disablePrint = false; // Disable printing
    
    protected boolean resizing = false;
    protected boolean dragging = false;
    protected int dragOffsetX, dragOffsetY;
    protected static final int HANDLE_SIZE = 10;
    
    // Soft coding: Configurable interaction parameters
    protected static final int CLICK_TOLERANCE = 5;     // Pixels tolerance for click detection
    protected static final int MIN_DRAG_DISTANCE = 3;   // Minimum distance to start dragging
    protected static final int MIN_WIDTH = 20;          // Minimum mark width
    protected static final int MIN_HEIGHT = 15;         // Minimum mark height
    protected static final int RESIZE_BORDER = 8;       // Resize handle border thickness
    
    // Soft coding: Enhanced interaction state
    private boolean canDrag = true;
    private boolean canResize = true;
    private int lastMouseX = 0, lastMouseY = 0;
    private boolean dragStarted = false;
    
    public Mark(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 40;
    }
    
    public abstract void draw(Graphics2D g, boolean isSelected);
    
    // Soft coding: Enhanced hit detection with tolerance
    public boolean contains(int px, int py) {
        return px >= (x - CLICK_TOLERANCE) && px <= (x + width + CLICK_TOLERANCE) && 
               py >= (y - CLICK_TOLERANCE) && py <= (y + height + CLICK_TOLERANCE);
    }
    
    // Soft coding: Enhanced resize handle detection
    public boolean overResizeHandle(int px, int py) {
        int handleX = x + width - HANDLE_SIZE;
        int handleY = y + height - HANDLE_SIZE;
        return px >= (handleX - RESIZE_BORDER) && px <= (x + width + RESIZE_BORDER) &&
               py >= (handleY - RESIZE_BORDER) && py <= (y + height + RESIZE_BORDER);
    }
    
    // Soft coding: Enhanced drag initiation with distance threshold
    public boolean shouldStartDrag(int mx, int my) {
        if (!canDrag) return false;
        
        int deltaX = Math.abs(mx - lastMouseX);
        int deltaY = Math.abs(my - lastMouseY);
        return (deltaX > MIN_DRAG_DISTANCE || deltaY > MIN_DRAG_DISTANCE);
    }
    
    // Soft coding: Improved drag start with validation
    public void startDrag(int mx, int my) {
        if (!canDrag) return;
        
        dragging = true;
        dragStarted = false;
        dragOffsetX = mx - x;
        dragOffsetY = my - y;
        lastMouseX = mx;
        lastMouseY = my;
    }
    
    // Soft coding: Enhanced drag with smooth movement and boundary checks
    public void dragTo(int mx, int my) {
        if (!dragging || !canDrag) return;
        
        // Only start actual dragging after minimum distance threshold
        if (!dragStarted && shouldStartDrag(mx, my)) {
            dragStarted = true;
        }
        
        if (dragStarted) {
            int newX = mx - dragOffsetX;
            int newY = my - dragOffsetY;
            
            // Soft coding: Boundary validation (keep marks within reasonable bounds)
            newX = Math.max(-width/2, newX);  // Allow some negative coordinates
            newY = Math.max(-height/2, newY);
            
            x = newX;
            y = newY;
            lastMouseX = mx;
            lastMouseY = my;
        }
    }
    
    public void stopDrag() {
        dragging = false;
        dragStarted = false;
    }
    
    // Soft coding: Enhanced resize start with validation
    public void startResize() {
        if (!canResize) return;
        resizing = true;
    }
    
    // Soft coding: Enhanced resize with minimum size constraints
    public void resizeTo(int mx, int my) {
        if (!resizing || !canResize) return;
        
        int newWidth = Math.max(MIN_WIDTH, mx - x);
        int newHeight = Math.max(MIN_HEIGHT, my - y);
        
        width = newWidth;
        height = newHeight;
    }
    
    public void stopResize() {
        resizing = false;
    }
    
    // Soft coding: Configuration methods for dynamic behavior
    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }
    
    public void setCanResize(boolean canResize) {
        this.canResize = canResize;
    }
    
    public boolean getCanDrag() {
        return canDrag;
    }

    public boolean getCanResize() {
        return canResize;
    }
    
    // Soft coding: Convenience methods for API consistency
    public boolean canDrag() {
        return getCanDrag();
    }
    
    public boolean canResize() {
        return getCanResize();
    }    public boolean isDragging() {
        return dragging;
    }
    
    public boolean isResizing() {
        return resizing;
    }
}
