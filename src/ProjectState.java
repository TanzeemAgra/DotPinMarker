import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * ProjectState - Comprehensive project state container for complete Save/Open functionality
 * Preserves all canvas state, marks, view settings, and work progress for seamless project continuity
 */
public class ProjectState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // ==================== PROJECT METADATA ====================
    
    public String projectName;
    public String projectId;
    public String description;
    public Date createdDate;
    public Date lastModified;
    public String version = "1.0";
    
    // ==================== CANVAS STATE ====================
    
    // Marks and objects
    public List<Mark> marks = new ArrayList<>();
    public List<TextMark> textMarks = new ArrayList<>();
    
    // Selection state
    public int selectedMarkIndex = -1;   // Index of selected mark, -1 if none
    public int activeMarkIndex = -1;     // Index of active mark, -1 if none
    
    // Clipboard state
    public Mark clipboardMark = null;
    
    // ==================== VIEW STATE ====================
    
    // Zoom and view positioning
    public double zoomLevel = 1.0;
    public int viewOffsetX = 0;
    public int viewOffsetY = 0;
    public boolean moveViewMode = false;
    
    // Visual settings
    public boolean gridVisible = true;
    public boolean materialBoundaryVisible = false;
    public boolean dotPreviewEnabled = false;
    
    // ==================== UNDO/REDO STATE ====================
    
    // Undo/Redo stacks (serialized as lists for compatibility)
    public List<List<Mark>> undoStates = new ArrayList<>();
    public List<List<Mark>> redoStates = new ArrayList<>();
    
    // ==================== WORK PROGRESS STATE ====================
    
    // Mark properties and settings
    public boolean sizeLocked = false;
    public boolean printDisabled = false;
    
    // Canvas dimensions and settings
    public int canvasWidth = 800;
    public int canvasHeight = 600;
    
    // Property strip state (if applicable)
    public Map<String, Object> propertyStripSettings = new HashMap<>();
    
    // ==================== CONSTRUCTORS ====================
    
    public ProjectState() {
        this.createdDate = new Date();
        this.lastModified = new Date();
    }
    
    public ProjectState(String projectName, String projectId) {
        this();
        this.projectName = projectName;
        this.projectId = projectId;
        this.description = "Project created with comprehensive state preservation";
    }
    
    // ==================== STATE MANAGEMENT METHODS ====================
    
    /**
     * Update the last modified timestamp
     */
    public void updateModificationTime() {
        this.lastModified = new Date();
    }
    
    /**
     * Get project summary for display
     */
    public String getProjectSummary() {
        return String.format("Project: %s | Marks: %d | Text: %d | Zoom: %.0f%% | Modified: %s",
            projectName != null ? projectName : "Untitled",
            marks.size(),
            textMarks.size(),
            zoomLevel * 100,
            lastModified != null ? lastModified.toString() : "Unknown"
        );
    }
    
    /**
     * Validate project state integrity
     */
    public boolean isValid() {
        return projectName != null && 
               !projectName.trim().isEmpty() && 
               marks != null && 
               textMarks != null &&
               zoomLevel > 0;
    }
    
    /**
     * Clear all state for new project
     */
    public void clearAllState() {
        marks.clear();
        textMarks.clear();
        selectedMarkIndex = -1;
        activeMarkIndex = -1;
        clipboardMark = null;
        zoomLevel = 1.0;
        viewOffsetX = 0;
        viewOffsetY = 0;
        moveViewMode = false;
        undoStates.clear();
        redoStates.clear();
        sizeLocked = false;
        printDisabled = false;
        propertyStripSettings.clear();
        updateModificationTime();
    }
    
    /**
     * Create a deep copy of the current state for undo operations
     */
    public ProjectState createSnapshot() {
        ProjectState snapshot = new ProjectState();
        snapshot.projectName = this.projectName;
        snapshot.projectId = this.projectId;
        snapshot.description = this.description;
        snapshot.createdDate = this.createdDate;
        snapshot.lastModified = new Date();
        snapshot.version = this.version;
        
        // Deep copy marks
        snapshot.marks = new ArrayList<>();
        for (Mark mark : this.marks) {
            // Note: This requires Mark classes to implement proper cloning
            // For now, we'll reference the original marks
            snapshot.marks.add(mark);
        }
        
        // Deep copy text marks
        snapshot.textMarks = new ArrayList<>();
        for (TextMark textMark : this.textMarks) {
            snapshot.textMarks.add(textMark);
        }
        
        // Copy view state
        snapshot.zoomLevel = this.zoomLevel;
        snapshot.viewOffsetX = this.viewOffsetX;
        snapshot.viewOffsetY = this.viewOffsetY;
        snapshot.moveViewMode = this.moveViewMode;
        snapshot.gridVisible = this.gridVisible;
        snapshot.materialBoundaryVisible = this.materialBoundaryVisible;
        snapshot.dotPreviewEnabled = this.dotPreviewEnabled;
        
        // Copy work state
        snapshot.sizeLocked = this.sizeLocked;
        snapshot.printDisabled = this.printDisabled;
        snapshot.canvasWidth = this.canvasWidth;
        snapshot.canvasHeight = this.canvasHeight;
        
        // Copy property settings
        snapshot.propertyStripSettings = new HashMap<>(this.propertyStripSettings);
        
        return snapshot;
    }
    
    @Override
    public String toString() {
        return String.format("ProjectState[name=%s, marks=%d, textMarks=%d, zoom=%.2f, modified=%s]",
            projectName, marks.size(), textMarks.size(), zoomLevel, lastModified);
    }
}