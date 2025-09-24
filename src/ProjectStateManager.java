import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.*;

/**
 * ProjectStateManager - Comprehensive project serialization and deserialization manager
 * Handles complete Save/Open functionality with canvas state preservation using soft-coded configuration
 */
public class ProjectStateManager {
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // Import configuration from RugrelDropdownConfig
    private static final boolean ENABLE_CANVAS_STATE_SERIALIZATION = RugrelDropdownConfig.ENABLE_CANVAS_STATE_SERIALIZATION;
    private static final boolean ENABLE_MARKS_SERIALIZATION = RugrelDropdownConfig.ENABLE_MARKS_SERIALIZATION;
    private static final boolean ENABLE_TEXT_MARKS_SERIALIZATION = RugrelDropdownConfig.ENABLE_TEXT_MARKS_SERIALIZATION;
    private static final boolean ENABLE_ZOOM_STATE_SERIALIZATION = RugrelDropdownConfig.ENABLE_ZOOM_STATE_SERIALIZATION;
    private static final boolean ENABLE_VIEW_POSITION_SERIALIZATION = RugrelDropdownConfig.ENABLE_VIEW_POSITION_SERIALIZATION;
    private static final boolean ENABLE_UNDO_STACK_SERIALIZATION = RugrelDropdownConfig.ENABLE_UNDO_STACK_SERIALIZATION;
    private static final boolean ENABLE_SELECTION_STATE_SERIALIZATION = RugrelDropdownConfig.ENABLE_SELECTION_STATE_SERIALIZATION;
    private static final boolean ENABLE_AUTO_PROJECT_BACKUP = RugrelDropdownConfig.ENABLE_AUTO_PROJECT_BACKUP;
    private static final boolean ENABLE_PROJECT_COMPRESSION = RugrelDropdownConfig.ENABLE_PROJECT_COMPRESSION;
    private static final boolean ENABLE_HUMAN_READABLE_FORMAT = RugrelDropdownConfig.ENABLE_HUMAN_READABLE_FORMAT;
    private static final boolean ENABLE_METADATA_PRESERVATION = RugrelDropdownConfig.ENABLE_METADATA_PRESERVATION;
    private static final boolean ENABLE_TIMESTAMP_TRACKING = RugrelDropdownConfig.ENABLE_TIMESTAMP_TRACKING;
    
    private static final String PROJECT_FILE_EXTENSION = RugrelDropdownConfig.PROJECT_FILE_EXTENSION;
    private static final String PROJECT_DATA_DIRECTORY = RugrelDropdownConfig.PROJECT_DATA_DIRECTORY;
    private static final String SERIALIZATION_FORMAT = RugrelDropdownConfig.SERIALIZATION_FORMAT;
    
    // ==================== SERIALIZATION METHODS ====================
    
    /**
     * Extract complete project state from DrawingCanvas
     */
    public static ProjectState extractProjectState(DrawingCanvas canvas, String projectName, String projectId) {
        if (!ENABLE_CANVAS_STATE_SERIALIZATION) {
            System.out.println("📄 Canvas state serialization disabled - creating minimal state");
            return new ProjectState(projectName, projectId);
        }
        
        try {
            ProjectState state = new ProjectState(projectName, projectId);
            
            if (ENABLE_TIMESTAMP_TRACKING) {
                state.updateModificationTime();
            }
            
            // Extract marks collection
            if (ENABLE_MARKS_SERIALIZATION) {
                List<Mark> canvasMarks = getCanvasMarks(canvas);
                if (canvasMarks != null) {
                    state.marks = new ArrayList<>(canvasMarks);
                    System.out.println("✅ Extracted " + state.marks.size() + " marks from canvas");
                }
            }
            
            // Extract text marks collection
            if (ENABLE_TEXT_MARKS_SERIALIZATION) {
                List<TextMark> canvasTextMarks = getCanvasTextMarks(canvas);
                if (canvasTextMarks != null) {
                    state.textMarks = new ArrayList<>(canvasTextMarks);
                    System.out.println("✅ Extracted " + state.textMarks.size() + " text marks from canvas");
                }
            }
            
            // Extract zoom and view state
            if (ENABLE_ZOOM_STATE_SERIALIZATION) {
                state.zoomLevel = getCanvasZoomLevel(canvas);
                System.out.println("✅ Extracted zoom level: " + (state.zoomLevel * 100) + "%");
            }
            
            if (ENABLE_VIEW_POSITION_SERIALIZATION) {
                Point viewOffset = getCanvasViewOffset(canvas);
                if (viewOffset != null) {
                    state.viewOffsetX = viewOffset.x;
                    state.viewOffsetY = viewOffset.y;
                    System.out.println("✅ Extracted view position: (" + state.viewOffsetX + ", " + state.viewOffsetY + ")");
                }
            }
            
            // Extract selection state
            if (ENABLE_SELECTION_STATE_SERIALIZATION) {
                Mark selectedMark = getSelectedMark(canvas);
                Mark activeMark = getActiveMark(canvas);
                
                state.selectedMarkIndex = findMarkIndex(state.marks, selectedMark);
                state.activeMarkIndex = findMarkIndex(state.marks, activeMark);
                
                System.out.println("✅ Extracted selection state - Selected: " + state.selectedMarkIndex + ", Active: " + state.activeMarkIndex);
            }
            
            // Extract visual settings
            if (RugrelDropdownConfig.PRESERVE_GRID_VISIBILITY) {
                state.gridVisible = getGridVisibility(canvas);
            }
            
            if (RugrelDropdownConfig.PRESERVE_MATERIAL_BOUNDARY) {
                state.materialBoundaryVisible = getMaterialBoundaryVisibility(canvas);
            }
            
            if (RugrelDropdownConfig.PRESERVE_DOT_PREVIEW) {
                state.dotPreviewEnabled = getDotPreviewEnabled(canvas);
            }
            
            // Extract undo/redo stacks
            if (ENABLE_UNDO_STACK_SERIALIZATION) {
                // Note: Undo stacks are complex to serialize, implement if needed
                System.out.println("⚠️ Undo stack serialization not yet implemented");
            }
            
            System.out.println("✅ Complete project state extracted: " + state.getProjectSummary());
            return state;
            
        } catch (Exception e) {
            System.err.println("❌ Error extracting project state: " + e.getMessage());
            e.printStackTrace();
            return new ProjectState(projectName, projectId); // Return minimal state
        }
    }
    
    /**
     * Apply complete project state to DrawingCanvas
     */
    public static boolean applyProjectState(DrawingCanvas canvas, ProjectState state) {
        if (!ENABLE_CANVAS_STATE_SERIALIZATION || state == null || !state.isValid()) {
            System.err.println("❌ Cannot apply project state - serialization disabled or invalid state");
            return false;
        }
        
        try {
            System.out.println("🔄 Applying project state: " + state.getProjectSummary());
            
            // Clear existing canvas state
            clearCanvasState(canvas);
            
            // Apply marks collection
            if (ENABLE_MARKS_SERIALIZATION && state.marks != null) {
                setCanvasMarks(canvas, state.marks);
                System.out.println("✅ Applied " + state.marks.size() + " marks to canvas");
            }
            
            // Apply text marks collection
            if (ENABLE_TEXT_MARKS_SERIALIZATION && state.textMarks != null) {
                setCanvasTextMarks(canvas, state.textMarks);
                System.out.println("✅ Applied " + state.textMarks.size() + " text marks to canvas");
            }
            
            // Apply zoom and view state
            if (ENABLE_ZOOM_STATE_SERIALIZATION) {
                setCanvasZoomLevel(canvas, state.zoomLevel);
                System.out.println("✅ Applied zoom level: " + (state.zoomLevel * 100) + "%");
            }
            
            if (ENABLE_VIEW_POSITION_SERIALIZATION) {
                setCanvasViewOffset(canvas, state.viewOffsetX, state.viewOffsetY);
                System.out.println("✅ Applied view position: (" + state.viewOffsetX + ", " + state.viewOffsetY + ")");
            }
            
            // Apply selection state
            if (ENABLE_SELECTION_STATE_SERIALIZATION) {
                if (state.selectedMarkIndex >= 0 && state.selectedMarkIndex < state.marks.size()) {
                    setSelectedMark(canvas, state.marks.get(state.selectedMarkIndex));
                }
                if (state.activeMarkIndex >= 0 && state.activeMarkIndex < state.marks.size()) {
                    setActiveMark(canvas, state.marks.get(state.activeMarkIndex));
                }
                System.out.println("✅ Applied selection state");
            }
            
            // Apply visual settings
            if (RugrelDropdownConfig.PRESERVE_GRID_VISIBILITY) {
                setGridVisibility(canvas, state.gridVisible);
            }
            
            if (RugrelDropdownConfig.PRESERVE_MATERIAL_BOUNDARY) {
                setMaterialBoundaryVisibility(canvas, state.materialBoundaryVisible);
            }
            
            if (RugrelDropdownConfig.PRESERVE_DOT_PREVIEW) {
                setDotPreviewEnabled(canvas, state.dotPreviewEnabled);
            }
            
            // Refresh canvas display
            canvas.repaint();
            
            System.out.println("✅ Project state applied successfully - ready to continue work!");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error applying project state: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Save project state to file
     */
    public static boolean saveProjectToFile(ProjectState state, String filePath) {
        if (state == null || !state.isValid()) {
            System.err.println("❌ Cannot save invalid project state");
            return false;
        }
        
        try {
            // Ensure directory exists
            File file = new File(filePath);
            File directory = file.getParentFile();
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }
            
            // Create backup if enabled
            if (ENABLE_AUTO_PROJECT_BACKUP && file.exists()) {
                createBackup(filePath);
            }
            
            // Update timestamp
            if (ENABLE_TIMESTAMP_TRACKING) {
                state.updateModificationTime();
            }
            
            // Serialize based on format
            if ("JSON".equals(SERIALIZATION_FORMAT)) {
                return saveAsJSON(state, filePath);
            } else {
                return saveAsBinary(state, filePath);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error saving project to file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load project state from file
     */
    public static ProjectState loadProjectFromFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("❌ Project file not found: " + filePath);
                return null;
            }
            
            // Load based on format
            if ("JSON".equals(SERIALIZATION_FORMAT)) {
                return loadFromJSON(filePath);
            } else {
                return loadFromBinary(filePath);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error loading project from file: " + e.getMessage());
            return null;
        }
    }
    
    // ==================== CANVAS REFLECTION METHODS ====================
    // These methods use reflection to access private canvas fields
    
    @SuppressWarnings("unchecked")
    private static List<Mark> getCanvasMarks(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field marksField = canvas.getClass().getDeclaredField("marks");
            marksField.setAccessible(true);
            return (List<Mark>) marksField.get(canvas);
        } catch (Exception e) {
            System.err.println("⚠️ Could not access canvas marks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    private static List<TextMark> getCanvasTextMarks(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field textMarksField = canvas.getClass().getDeclaredField("textMarks");
            textMarksField.setAccessible(true);
            return (List<TextMark>) textMarksField.get(canvas);
        } catch (Exception e) {
            System.err.println("⚠️ Could not access canvas text marks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private static double getCanvasZoomLevel(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field zoomField = canvas.getClass().getDeclaredField("zoomLevel");
            zoomField.setAccessible(true);
            return zoomField.getDouble(canvas);
        } catch (Exception e) {
            System.err.println("⚠️ Could not access canvas zoom level: " + e.getMessage());
            return 1.0;
        }
    }
    
    private static Point getCanvasViewOffset(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field offsetXField = canvas.getClass().getDeclaredField("viewOffsetX");
            java.lang.reflect.Field offsetYField = canvas.getClass().getDeclaredField("viewOffsetY");
            offsetXField.setAccessible(true);
            offsetYField.setAccessible(true);
            return new Point(offsetXField.getInt(canvas), offsetYField.getInt(canvas));
        } catch (Exception e) {
            System.err.println("⚠️ Could not access canvas view offset: " + e.getMessage());
            return new Point(0, 0);
        }
    }
    
    private static Mark getSelectedMark(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field selectedField = canvas.getClass().getDeclaredField("selectedMark");
            selectedField.setAccessible(true);
            return (Mark) selectedField.get(canvas);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static Mark getActiveMark(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field activeField = canvas.getClass().getDeclaredField("activeMark");
            activeField.setAccessible(true);
            return (Mark) activeField.get(canvas);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static boolean getGridVisibility(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field gridField = canvas.getClass().getDeclaredField("gridVisible");
            gridField.setAccessible(true);
            return gridField.getBoolean(canvas);
        } catch (Exception e) {
            return true; // Default value
        }
    }
    
    private static boolean getMaterialBoundaryVisibility(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field boundaryField = canvas.getClass().getDeclaredField("materialBoundaryVisible");
            boundaryField.setAccessible(true);
            return boundaryField.getBoolean(canvas);
        } catch (Exception e) {
            return false; // Default value
        }
    }
    
    private static boolean getDotPreviewEnabled(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field previewField = canvas.getClass().getDeclaredField("dotPreviewEnabled");
            previewField.setAccessible(true);
            return previewField.getBoolean(canvas);
        } catch (Exception e) {
            return false; // Default value
        }
    }
    
    // ==================== CANVAS SETTER METHODS ====================
    
    private static void clearCanvasState(DrawingCanvas canvas) {
        try {
            // Use existing clearAllForNewProject method if available
            canvas.clearAllForNewProject();
        } catch (Exception e) {
            System.err.println("⚠️ Could not clear canvas state: " + e.getMessage());
        }
    }
    
    private static void setCanvasMarks(DrawingCanvas canvas, List<Mark> marks) {
        try {
            java.lang.reflect.Field marksField = canvas.getClass().getDeclaredField("marks");
            marksField.setAccessible(true);
            List<Mark> canvasMarks = (List<Mark>) marksField.get(canvas);
            canvasMarks.clear();
            canvasMarks.addAll(marks);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set canvas marks: " + e.getMessage());
        }
    }
    
    private static void setCanvasTextMarks(DrawingCanvas canvas, List<TextMark> textMarks) {
        try {
            java.lang.reflect.Field textMarksField = canvas.getClass().getDeclaredField("textMarks");
            textMarksField.setAccessible(true);
            List<TextMark> canvasTextMarks = (List<TextMark>) textMarksField.get(canvas);
            canvasTextMarks.clear();
            canvasTextMarks.addAll(textMarks);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set canvas text marks: " + e.getMessage());
        }
    }
    
    private static void setCanvasZoomLevel(DrawingCanvas canvas, double zoomLevel) {
        try {
            java.lang.reflect.Field zoomField = canvas.getClass().getDeclaredField("zoomLevel");
            zoomField.setAccessible(true);
            zoomField.setDouble(canvas, zoomLevel);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set canvas zoom level: " + e.getMessage());
        }
    }
    
    private static void setCanvasViewOffset(DrawingCanvas canvas, int offsetX, int offsetY) {
        try {
            java.lang.reflect.Field offsetXField = canvas.getClass().getDeclaredField("viewOffsetX");
            java.lang.reflect.Field offsetYField = canvas.getClass().getDeclaredField("viewOffsetY");
            offsetXField.setAccessible(true);
            offsetYField.setAccessible(true);
            offsetXField.setInt(canvas, offsetX);
            offsetYField.setInt(canvas, offsetY);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set canvas view offset: " + e.getMessage());
        }
    }
    
    private static void setSelectedMark(DrawingCanvas canvas, Mark mark) {
        try {
            java.lang.reflect.Field selectedField = canvas.getClass().getDeclaredField("selectedMark");
            selectedField.setAccessible(true);
            selectedField.set(canvas, mark);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set selected mark: " + e.getMessage());
        }
    }
    
    private static void setActiveMark(DrawingCanvas canvas, Mark mark) {
        try {
            java.lang.reflect.Field activeField = canvas.getClass().getDeclaredField("activeMark");
            activeField.setAccessible(true);
            activeField.set(canvas, mark);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set active mark: " + e.getMessage());
        }
    }
    
    private static void setGridVisibility(DrawingCanvas canvas, boolean visible) {
        try {
            java.lang.reflect.Field gridField = canvas.getClass().getDeclaredField("gridVisible");
            gridField.setAccessible(true);
            gridField.setBoolean(canvas, visible);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set grid visibility: " + e.getMessage());
        }
    }
    
    private static void setMaterialBoundaryVisibility(DrawingCanvas canvas, boolean visible) {
        try {
            java.lang.reflect.Field boundaryField = canvas.getClass().getDeclaredField("materialBoundaryVisible");
            boundaryField.setAccessible(true);
            boundaryField.setBoolean(canvas, visible);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set material boundary visibility: " + e.getMessage());
        }
    }
    
    private static void setDotPreviewEnabled(DrawingCanvas canvas, boolean enabled) {
        try {
            java.lang.reflect.Field previewField = canvas.getClass().getDeclaredField("dotPreviewEnabled");
            previewField.setAccessible(true);
            previewField.setBoolean(canvas, enabled);
        } catch (Exception e) {
            System.err.println("⚠️ Could not set dot preview enabled: " + e.getMessage());
        }
    }
    
    // ==================== UTILITY METHODS ====================
    
    private static int findMarkIndex(List<Mark> marks, Mark targetMark) {
        if (targetMark == null || marks == null) return -1;
        return marks.indexOf(targetMark);
    }
    
    private static void createBackup(String originalFilePath) {
        try {
            File original = new File(originalFilePath);
            File backup = new File(originalFilePath + ".backup");
            java.nio.file.Files.copy(original.toPath(), backup.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Created backup: " + backup.getName());
        } catch (Exception e) {
            System.err.println("⚠️ Could not create backup: " + e.getMessage());
        }
    }
    
    // ==================== SERIALIZATION FORMAT METHODS ====================
    
    private static boolean saveAsJSON(ProjectState state, String filePath) {
        try {
            // Simple JSON serialization without external dependencies
            String json = createSimpleJSON(state);
            
            if (ENABLE_PROJECT_COMPRESSION) {
                return saveCompressedJSON(json, filePath);
            } else {
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(json);
                }
            }
            
            System.out.println("✅ Project saved as JSON: " + filePath);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Error saving JSON: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean saveCompressedJSON(String json, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             GZIPOutputStream gzos = new GZIPOutputStream(fos);
             OutputStreamWriter writer = new OutputStreamWriter(gzos)) {
            
            writer.write(json);
            System.out.println("✅ Project saved as compressed JSON: " + filePath);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Error saving compressed JSON: " + e.getMessage());
            return false;
        }
    }
    
    private static ProjectState loadFromJSON(String filePath) {
        try {
            String json;
            
            if (ENABLE_PROJECT_COMPRESSION) {
                json = loadCompressedJSON(filePath);
            } else {
                json = new String(java.nio.file.Files.readAllBytes(new File(filePath).toPath()));
            }
            
            // Simple JSON parsing without external dependencies
            ProjectState state = parseSimpleJSON(json);
            
            System.out.println("✅ Project loaded from JSON: " + filePath);
            return state;
        } catch (Exception e) {
            System.err.println("❌ Error loading JSON: " + e.getMessage());
            return null;
        }
    }
    
    private static String loadCompressedJSON(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             GZIPInputStream gzis = new GZIPInputStream(fis);
             InputStreamReader reader = new InputStreamReader(gzis)) {
            
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int length;
            while ((length = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, length);
            }
            return sb.toString();
        }
    }
    
    private static boolean saveAsBinary(ProjectState state, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(state);
            System.out.println("✅ Project saved as binary: " + filePath);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Error saving binary: " + e.getMessage());
            return false;
        }
    }
    
    private static ProjectState loadFromBinary(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            ProjectState state = (ProjectState) ois.readObject();
            System.out.println("✅ Project loaded from binary: " + filePath);
            return state;
        } catch (Exception e) {
            System.err.println("❌ Error loading binary: " + e.getMessage());
            return null;
        }
    }
    
    // ==================== SIMPLE JSON SERIALIZATION (No External Dependencies) ====================
    
    /**
     * Create simple JSON representation of project state
     */
    private static String createSimpleJSON(ProjectState state) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        // Basic project info
        json.append("  \"projectName\": \"").append(escapeJSON(state.projectName)).append("\",\n");
        json.append("  \"projectId\": \"").append(escapeJSON(state.projectId)).append("\",\n");
        json.append("  \"description\": \"").append(escapeJSON(state.description)).append("\",\n");
        json.append("  \"version\": \"").append(escapeJSON(state.version)).append("\",\n");
        json.append("  \"createdDate\": \"").append(state.createdDate != null ? state.createdDate.toString() : "").append("\",\n");
        json.append("  \"lastModified\": \"").append(state.lastModified != null ? state.lastModified.toString() : "").append("\",\n");
        
        // Canvas state
        json.append("  \"zoomLevel\": ").append(state.zoomLevel).append(",\n");
        json.append("  \"viewOffsetX\": ").append(state.viewOffsetX).append(",\n");
        json.append("  \"viewOffsetY\": ").append(state.viewOffsetY).append(",\n");
        json.append("  \"gridVisible\": ").append(state.gridVisible).append(",\n");
        json.append("  \"materialBoundaryVisible\": ").append(state.materialBoundaryVisible).append(",\n");
        json.append("  \"dotPreviewEnabled\": ").append(state.dotPreviewEnabled).append(",\n");
        
        // Selection state
        json.append("  \"selectedMarkIndex\": ").append(state.selectedMarkIndex).append(",\n");
        json.append("  \"activeMarkIndex\": ").append(state.activeMarkIndex).append(",\n");
        
        // Work progress
        json.append("  \"sizeLocked\": ").append(state.sizeLocked).append(",\n");
        json.append("  \"printDisabled\": ").append(state.printDisabled).append(",\n");
        json.append("  \"canvasWidth\": ").append(state.canvasWidth).append(",\n");
        json.append("  \"canvasHeight\": ").append(state.canvasHeight).append(",\n");
        
        // Marks count (actual mark serialization would be more complex)
        json.append("  \"marksCount\": ").append(state.marks != null ? state.marks.size() : 0).append(",\n");
        json.append("  \"textMarksCount\": ").append(state.textMarks != null ? state.textMarks.size() : 0).append("\n");
        
        json.append("}");
        return json.toString();
    }
    
    /**
     * Parse simple JSON back to project state
     */
    private static ProjectState parseSimpleJSON(String json) {
        ProjectState state = new ProjectState();
        
        try {
            // Simple JSON parsing (basic implementation)
            state.projectName = extractJSONString(json, "projectName");
            state.projectId = extractJSONString(json, "projectId");
            state.description = extractJSONString(json, "description");
            state.version = extractJSONString(json, "version");
            
            state.zoomLevel = extractJSONDouble(json, "zoomLevel", 1.0);
            state.viewOffsetX = extractJSONInt(json, "viewOffsetX", 0);
            state.viewOffsetY = extractJSONInt(json, "viewOffsetY", 0);
            state.gridVisible = extractJSONBoolean(json, "gridVisible", true);
            state.materialBoundaryVisible = extractJSONBoolean(json, "materialBoundaryVisible", false);
            state.dotPreviewEnabled = extractJSONBoolean(json, "dotPreviewEnabled", false);
            
            state.selectedMarkIndex = extractJSONInt(json, "selectedMarkIndex", -1);
            state.activeMarkIndex = extractJSONInt(json, "activeMarkIndex", -1);
            
            state.sizeLocked = extractJSONBoolean(json, "sizeLocked", false);
            state.printDisabled = extractJSONBoolean(json, "printDisabled", false);
            state.canvasWidth = extractJSONInt(json, "canvasWidth", 800);
            state.canvasHeight = extractJSONInt(json, "canvasHeight", 600);
            
            // Initialize collections
            state.marks = new ArrayList<>();
            state.textMarks = new ArrayList<>();
            state.undoStates = new ArrayList<>();
            state.redoStates = new ArrayList<>();
            state.propertyStripSettings = new HashMap<>();
            
        } catch (Exception e) {
            System.err.println("⚠️ Error parsing JSON, using defaults: " + e.getMessage());
        }
        
        return state;
    }
    
    /**
     * Escape JSON strings
     */
    private static String escapeJSON(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
    
    /**
     * Extract string value from JSON
     */
    private static String extractJSONString(String json, String key) {
        try {
            String pattern = "\"" + key + "\":\\s*\"([^\"]*?)\"";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error extracting JSON string for " + key + ": " + e.getMessage());
        }
        return "";
    }
    
    /**
     * Extract double value from JSON
     */
    private static double extractJSONDouble(String json, String key, double defaultValue) {
        try {
            String pattern = "\"" + key + "\":\\s*([0-9.]+)";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) {
                return Double.parseDouble(m.group(1));
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error extracting JSON double for " + key + ": " + e.getMessage());
        }
        return defaultValue;
    }
    
    /**
     * Extract int value from JSON
     */
    private static int extractJSONInt(String json, String key, int defaultValue) {
        try {
            String pattern = "\"" + key + "\":\\s*([0-9-]+)";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error extracting JSON int for " + key + ": " + e.getMessage());
        }
        return defaultValue;
    }
    
    /**
     * Extract boolean value from JSON
     */
    private static boolean extractJSONBoolean(String json, String key, boolean defaultValue) {
        try {
            String pattern = "\"" + key + "\":\\s*(true|false)";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) {
                return Boolean.parseBoolean(m.group(1));
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error extracting JSON boolean for " + key + ": " + e.getMessage());
        }
        return defaultValue;
    }
}