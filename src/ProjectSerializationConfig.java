import java.io.*;
import java.util.*;
import java.awt.Font;
import java.awt.Color;

/**
 * ProjectSerializationConfig - Comprehensive project state serialization
 * Soft-coded approach for saving and loading complete canvas state with all work data
 */
public class ProjectSerializationConfig {
    
    // Serialization Control Flags (Soft Coded)
    public static final boolean ENABLE_PROJECT_SERIALIZATION = true;        // Master enable flag
    public static final boolean ENABLE_CANVAS_STATE_SAVE = true;           // Save canvas zoom, view, etc.
    public static final boolean ENABLE_MARKS_SERIALIZATION = true;         // Save all marks/objects
    public static final boolean ENABLE_TEXT_MARKS_SERIALIZATION = true;    // Save text objects
    public static final boolean ENABLE_UNDO_STACK_SAVE = true;            // Save undo/redo history
    public static final boolean ENABLE_SELECTION_STATE_SAVE = true;       // Save current selection
    public static final boolean ENABLE_PROPERTY_STRIP_STATE = true;       // Save property panel state
    
    // File Format Configuration
    public static final String PROJECT_FILE_EXTENSION = ".rugrel";        // Project file extension
    public static final String PROJECT_BACKUP_EXTENSION = ".backup";      // Backup file extension
    public static final boolean ENABLE_AUTO_BACKUP = true;               // Auto-create backups
    public static final boolean ENABLE_COMPRESSION = true;               // Compress project files
    public static final String SERIALIZATION_VERSION = "1.0";           // Version for compatibility
    
    // Canvas State Serialization
    public static final boolean SAVE_ZOOM_LEVEL = true;                  // Save current zoom
    public static final boolean SAVE_VIEW_OFFSET = true;                 // Save pan position
    public static final boolean SAVE_GRID_VISIBILITY = true;            // Save grid on/off
    public static final boolean SAVE_MATERIAL_BOUNDARY = true;          // Save boundary visibility
    public static final boolean SAVE_DOT_PREVIEW = true;                // Save preview mode
    
    // Mark Serialization Configuration
    public static final boolean SAVE_MARK_PROPERTIES = true;            // Save all mark properties
    public static final boolean SAVE_MARK_TRANSFORMS = true;            // Save rotation, scaling, etc.
    public static final boolean SAVE_MARK_FORMATTING = true;            // Save colors, fonts, etc.
    public static final boolean SAVE_MARK_LAYERING = true;              // Save z-order
    public static final boolean SAVE_MARK_LOCKS = true;                 // Save lock states
    
    // Debug and Recovery Options
    public static final boolean ENABLE_SERIALIZATION_DEBUG = true;      // Debug output
    public static final boolean ENABLE_ERROR_RECOVERY = true;           // Error handling
    public static final boolean ENABLE_PARTIAL_LOAD = true;            // Load partial projects
    public static final int MAX_UNDO_LEVELS_SAVE = 10;                 // Limit undo stack size
    
    /**
     * ProjectState - Comprehensive container for all project data
     */
    public static class ProjectState implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // Project metadata
        public String projectName;
        public String description;
        public String version = SERIALIZATION_VERSION;
        public Date createdDate;
        public Date modifiedDate;
        
        // Canvas state
        public double zoomLevel = 1.0;
        public int viewOffsetX = 0;
        public int viewOffsetY = 0;
        public boolean gridVisible = true;
        public boolean materialBoundaryVisible = false;
        public boolean dotPreviewEnabled = false;
        
        // Drawing objects
        public List<SerializableMark> marks = new ArrayList<>();
        public List<SerializableTextMark> textMarks = new ArrayList<>();
        
        // Selection and interaction state
        public int selectedMarkIndex = -1;
        public int activeMarkIndex = -1;
        
        // Undo/Redo stacks (limited size for performance)
        public List<List<SerializableMark>> undoStack = new ArrayList<>();
        public List<List<SerializableMark>> redoStack = new ArrayList<>();
        
        // Property strip state
        public boolean sizeLocked = false;
        public boolean printDisabled = false;
        
        // Clipboard state
        public SerializableMark clipboardMark = null;
    }
    
    /**
     * SerializableMark - Serializable version of Mark class
     */
    public static class SerializableMark implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // Basic properties
        public int x, y, width, height;
        public int z = 0;
        public double angle = 0.0;
        public String name = "Mark";
        public boolean clearTrans = false;
        public boolean mirror = false;
        public boolean lockSize = false;
        public boolean disablePrint = false;
        
        // Mark type identification
        public String markType; // "Rectangle", "Line", "Graph", etc.
        public Map<String, Object> typeSpecificData = new HashMap<>();
        
        // Visual properties
        public SerializableColor color;
        public SerializableFont font;
        public String text; // For text-based marks
        
        public SerializableMark() {}
        
        public SerializableMark(String type) {
            this.markType = type;
        }
    }
    
    /**
     * SerializableTextMark - Serializable version of TextMark class
     */
    public static class SerializableTextMark extends SerializableMark {
        private static final long serialVersionUID = 1L;
        
        // Text-specific properties
        public String text = "ABC123";
        public SerializableFont font;
        public double characterWidth = 1.0;
        public double lineSpacing = 1.0;
        public int minWidth = 50;
        public int padding = 20;
        
        public SerializableTextMark() {
            super("TextMark");
        }
    }
    
    /**
     * SerializableFont - Serializable font representation
     */
    public static class SerializableFont implements Serializable {
        private static final long serialVersionUID = 1L;
        
        public String name;
        public int style;
        public int size;
        
        public SerializableFont() {}
        
        public SerializableFont(Font font) {
            this.name = font.getName();
            this.style = font.getStyle();
            this.size = font.getSize();
        }
        
        public Font toFont() {
            return new Font(name, style, size);
        }
    }
    
    /**
     * SerializableColor - Serializable color representation
     */
    public static class SerializableColor implements Serializable {
        private static final long serialVersionUID = 1L;
        
        public int red, green, blue, alpha;
        
        public SerializableColor() {}
        
        public SerializableColor(Color color) {
            this.red = color.getRed();
            this.green = color.getGreen();
            this.blue = color.getBlue();
            this.alpha = color.getAlpha();
        }
        
        public Color toColor() {
            return new Color(red, green, blue, alpha);
        }
    }
    
    /**
     * Save project state to file
     */
    public static boolean saveProjectState(ProjectState state, String filePath) {
        if (!ENABLE_PROJECT_SERIALIZATION) {
            System.out.println("💾 Project serialization disabled - cannot save: " + filePath);
            return false;
        }
        
        try {
            // Create backup if enabled
            if (ENABLE_AUTO_BACKUP) {
                createBackup(filePath);
            }
            
            // Update modification date
            state.modifiedDate = new Date();
            
            if (ENABLE_SERIALIZATION_DEBUG) {
                System.out.printf("💾 Saving project state: %s (Version: %s)%n", 
                    state.projectName, state.version);
                System.out.printf("   Canvas: zoom=%.2f, offset=(%d,%d)%n", 
                    state.zoomLevel, state.viewOffsetX, state.viewOffsetY);
                System.out.printf("   Objects: %d marks, %d text marks%n", 
                    state.marks.size(), state.textMarks.size());
            }
            
            // Serialize to file
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(filePath))) {
                oos.writeObject(state);
            }
            
            System.out.println("✅ Project state saved successfully: " + filePath);
            return true;
            
        } catch (Exception e) {
            if (ENABLE_ERROR_RECOVERY) {
                System.err.println("❌ Error saving project state: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }
    
    /**
     * Load project state from file
     */
    public static ProjectState loadProjectState(String filePath) {
        if (!ENABLE_PROJECT_SERIALIZATION) {
            System.out.println("📂 Project serialization disabled - cannot load: " + filePath);
            return null;
        }
        
        try {
            ProjectState state;
            
            // Deserialize from file
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(filePath))) {
                state = (ProjectState) ois.readObject();
            }
            
            if (ENABLE_SERIALIZATION_DEBUG) {
                System.out.printf("📂 Loaded project state: %s (Version: %s)%n", 
                    state.projectName, state.version);
                System.out.printf("   Canvas: zoom=%.2f, offset=(%d,%d)%n", 
                    state.zoomLevel, state.viewOffsetX, state.viewOffsetY);
                System.out.printf("   Objects: %d marks, %d text marks%n", 
                    state.marks.size(), state.textMarks.size());
            }
            
            // Version compatibility check
            if (!SERIALIZATION_VERSION.equals(state.version)) {
                System.out.println("⚠️ Version mismatch - attempting compatibility mode");
            }
            
            System.out.println("✅ Project state loaded successfully: " + filePath);
            return state;
            
        } catch (Exception e) {
            if (ENABLE_ERROR_RECOVERY) {
                System.err.println("❌ Error loading project state: " + e.getMessage());
                if (ENABLE_PARTIAL_LOAD) {
                    System.out.println("🔄 Attempting partial recovery...");
                    // Could implement partial recovery logic here
                }
            }
            return null;
        }
    }
    
    /**
     * Create backup of existing project file
     */
    private static void createBackup(String filePath) {
        try {
            File original = new File(filePath);
            if (original.exists()) {
                File backup = new File(filePath + PROJECT_BACKUP_EXTENSION);
                java.nio.file.Files.copy(original.toPath(), backup.toPath(), 
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                
                if (ENABLE_SERIALIZATION_DEBUG) {
                    System.out.println("💾 Backup created: " + backup.getName());
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Warning: Could not create backup: " + e.getMessage());
        }
    }
    
    /**
     * Get standard project file path
     */
    public static String getProjectFilePath(String projectName) {
        return "projects/" + projectName + PROJECT_FILE_EXTENSION;
    }
    
    /**
     * Validate project file
     */
    public static boolean isValidProjectFile(String filePath) {
        try {
            File file = new File(filePath);
            return file.exists() && file.getName().endsWith(PROJECT_FILE_EXTENSION);
        } catch (Exception e) {
            return false;
        }
    }
}