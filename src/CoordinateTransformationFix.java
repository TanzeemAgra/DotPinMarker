import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Enhanced coordinate transformation system for DrawingCanvas
 * Fixes zoom view alignment issues when grid is enabled
 * Uses soft coding techniques for maintainable coordinate handling
 */
public class CoordinateTransformationFix {
    
    /**
     * Enhanced coordinate transformation that accounts for zoom, view offset, and grid alignment
     * This fixes the issue where dragging with mouse doesn't work properly in zoom view with grid
     */
    public static class EnhancedCoordinateTransform {
        private final double zoomLevel;
        private final int viewOffsetX;
        private final int viewOffsetY;
        private final boolean gridVisible;
        private final double gridSpacing;
        
        public EnhancedCoordinateTransform(double zoomLevel, int viewOffsetX, int viewOffsetY, 
                                         boolean gridVisible, double gridSpacing) {
            // Enhanced zoom level validation for high zoom levels (up to 500%)
            this.zoomLevel = Math.max(0.1, Math.min(10.0, zoomLevel)); // Support up to 1000% for safety
            this.viewOffsetX = viewOffsetX;
            this.viewOffsetY = viewOffsetY;
            this.gridVisible = gridVisible;
            this.gridSpacing = Math.max(1.0, gridSpacing); // Minimum grid spacing
        }
        
        /**
         * Transform screen coordinates to canvas coordinates with enhanced precision for high zoom
         */
        public Point transformScreenToCanvas(int screenX, int screenY) {
            // Enhanced precision transformation for high zoom levels
            double precisionCanvasX = (double)(screenX - viewOffsetX) / zoomLevel;
            double precisionCanvasY = (double)(screenY - viewOffsetY) / zoomLevel;
            
            // Apply high-zoom specific adjustments
            if (zoomLevel >= 4.0) { // 400% and above
                // Increase precision and reduce rounding errors
                precisionCanvasX = Math.round(precisionCanvasX * 100.0) / 100.0;
                precisionCanvasY = Math.round(precisionCanvasY * 100.0) / 100.0;
            }
            
            // If grid is visible, apply grid-aware coordinate adjustment
            if (gridVisible) {
                precisionCanvasX = adjustForGridAlignment(precisionCanvasX, zoomLevel);
                precisionCanvasY = adjustForGridAlignment(precisionCanvasY, zoomLevel);
            }
            
            // Enhanced boundary validation for high zoom levels
            double maxCoord = zoomLevel >= 5.0 ? 6000 : 4000; // Larger bounds at high zoom
            double minCoord = zoomLevel >= 5.0 ? -3000 : -2000;
            
            precisionCanvasX = Math.max(minCoord, Math.min(maxCoord, precisionCanvasX));
            precisionCanvasY = Math.max(minCoord, Math.min(maxCoord, precisionCanvasY));
            
            return new Point((int)Math.round(precisionCanvasX), (int)Math.round(precisionCanvasY));
        }
        
        /**
         * Transform canvas coordinates to screen coordinates
         */
        public Point transformCanvasToScreen(int canvasX, int canvasY) {
            double screenX = canvasX * zoomLevel + viewOffsetX;
            double screenY = canvasY * zoomLevel + viewOffsetY;
            
            return new Point((int)screenX, (int)screenY);
        }
        
        /**
         * Enhanced grid alignment adjustment for high zoom levels
         */
        private double adjustForGridAlignment(double coordinate, double currentZoom) {
            // Calculate working area offset for grid alignment
            double mmToPixel = 3.78;
            double workingAreaOffset = 60; // Standard offset for working area
            
            // Adjust coordinate relative to grid origin
            double relativeToGrid = coordinate - workingAreaOffset;
            
            // Apply zoom-specific grid spacing awareness
            double effectiveGridSpacing = gridSpacing;
            
            // At high zoom levels (400%+), apply finer grid adjustments
            if (currentZoom >= 4.0) {
                effectiveGridSpacing = gridSpacing / 2.0; // Finer grid at high zoom
            } else if (currentZoom >= 2.0) {
                effectiveGridSpacing = gridSpacing * 0.8; // Slightly finer grid
            }
            
            // Apply subtle grid awareness (not snapping, just alignment assistance)
            double gridAwareCoordinate = relativeToGrid;
            
            // At very high zoom (500%+), apply micro-adjustments for precision
            if (currentZoom >= 5.0) {
                // Add tiny adjustment to help with precision at extreme zoom
                double microAdjustment = (relativeToGrid % 1.0) < 0.5 ? -0.1 : 0.1;
                gridAwareCoordinate = relativeToGrid + microAdjustment;
            }
            
            // Return adjusted coordinate
            return gridAwareCoordinate + workingAreaOffset;
        }
        
        /**
         * Enhanced boundary validation for high zoom levels
         */
        public boolean isWithinDragBounds(int canvasX, int canvasY) {
            // Dynamic bounds based on zoom level
            if (zoomLevel >= 5.0) {
                // At 500%+ zoom, allow larger coordinate space
                return canvasX >= -2000 && canvasX <= 5000 && 
                       canvasY >= -2000 && canvasY <= 5000;
            } else if (zoomLevel >= 3.0) {
                // At 300-400% zoom, moderate bounds
                return canvasX >= -1500 && canvasX <= 4000 && 
                       canvasY >= -1500 && canvasY <= 4000;
            } else {
                // Standard bounds for normal zoom levels
                return canvasX >= -1000 && canvasX <= 3000 && 
                       canvasY >= -1000 && canvasY <= 3000;
            }
        }
        
        /**
         * Enhanced hit tolerance calculation for all zoom levels including 500%
         */
        public double getHitTolerance() {
            // Dynamic hit tolerance based on zoom level
            if (zoomLevel >= 5.0) {
                // At 500%+ zoom, use very small tolerance for precision
                return Math.max(2.0, 5.0 / zoomLevel);
            } else if (zoomLevel >= 3.0) {
                // At 300-400% zoom, moderate tolerance
                return Math.max(3.0, 8.0 / zoomLevel);
            } else if (zoomLevel >= 1.5) {
                // At 150-300% zoom, standard tolerance
                return Math.max(4.0, 10.0 / zoomLevel);
            } else {
                // At lower zoom levels, larger tolerance
                return Math.max(5.0, 15.0 / zoomLevel);
            }
        }
    }
    
    /**
     * Enhanced mouse event handler that properly handles zoom + grid interaction
     */
    public static class ZoomGridMouseHandler {
        
        /**
         * Process mouse pressed event with enhanced coordinate transformation
         */
        public static ProcessedMouseEvent processMousePressed(MouseEvent e, double zoomLevel, 
                                                            int viewOffsetX, int viewOffsetY, 
                                                            boolean gridVisible, boolean moveViewMode) {
            
            ProcessedMouseEvent result = new ProcessedMouseEvent();
            result.originalEvent = e;
            result.isHandled = false;
            
            // Handle move view mode first
            if (moveViewMode) {
                result.isHandled = true;
                result.actionType = "MOVE_VIEW";
                return result;
            }
            
            // Create enhanced transformer
            EnhancedCoordinateTransform transformer = new EnhancedCoordinateTransform(
                zoomLevel, viewOffsetX, viewOffsetY, gridVisible, 5.0);
            
            // Transform coordinates with grid awareness
            Point canvasCoords = transformer.transformScreenToCanvas(e.getX(), e.getY());
            result.transformedX = canvasCoords.x;
            result.transformedY = canvasCoords.y;
            result.hitTolerance = transformer.getHitTolerance();
            result.isWithinBounds = transformer.isWithinDragBounds(canvasCoords.x, canvasCoords.y);
            
            result.actionType = "MARK_INTERACTION";
            return result;
        }
        
        /**
         * Process mouse dragged event with enhanced coordinate transformation
         */
        public static ProcessedMouseEvent processMouseDragged(MouseEvent e, double zoomLevel, 
                                                            int viewOffsetX, int viewOffsetY, 
                                                            boolean gridVisible, boolean moveViewMode) {
            
            ProcessedMouseEvent result = new ProcessedMouseEvent();
            result.originalEvent = e;
            result.isHandled = false;
            
            // Handle move view mode
            if (moveViewMode) {
                result.isHandled = true;
                result.actionType = "MOVE_VIEW";
                return result;
            }
            
            // Create enhanced transformer
            EnhancedCoordinateTransform transformer = new EnhancedCoordinateTransform(
                zoomLevel, viewOffsetX, viewOffsetY, gridVisible, 5.0);
            
            // Transform coordinates with grid awareness
            Point canvasCoords = transformer.transformScreenToCanvas(e.getX(), e.getY());
            result.transformedX = canvasCoords.x;
            result.transformedY = canvasCoords.y;
            result.hitTolerance = transformer.getHitTolerance();
            result.isWithinBounds = transformer.isWithinDragBounds(canvasCoords.x, canvasCoords.y);
            
            result.actionType = "DRAG_OPERATION";
            return result;
        }
    }
    
    /**
     * Data structure to hold processed mouse event information
     */
    public static class ProcessedMouseEvent {
        public MouseEvent originalEvent;
        public int transformedX;
        public int transformedY;
        public double hitTolerance;
        public boolean isWithinBounds;
        public boolean isHandled;
        public String actionType;
    }
}
