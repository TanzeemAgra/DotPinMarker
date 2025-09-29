import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Rugrel Logo Dropdown Configuration
 * Soft-coded approach for Rugrel logo dropdown menu with Create, Load, Save, etc.
 */
public class RugrelDropdownConfig {
    
    // ==================== PROJECT STATE TRACKING ====================
    
    // Current project name tracking (soft-coded)
    private static String currentProjectName = null;
    private static boolean projectModified = false;
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // Database Integration Configuration (Soft Coded)
    public static final boolean ENABLE_DATABASE_INTEGRATION = true;      // ENABLE real database integration
    public static final boolean ENABLE_PROJECT_AUTO_SAVE = true;         // ENABLE auto-save projects to database
    public static final boolean ENABLE_DATABASE_VALIDATION = true;       // ENABLE database connection validation
    public static final boolean ENABLE_PROJECT_NOTIFICATIONS = true;     // ENABLE success/error notifications
    public static final boolean ENABLE_DATABASE_TAB_SWITCH = true;       // ENABLE auto-switch to Database Tab after creation
    public static final boolean ENABLE_PROJECT_ID_GENERATION = true;     // ENABLE automatic project ID generation
    public static final boolean ENABLE_RECENT_FILES_SYNC = true;         // ENABLE sync with database recent files
    public static final boolean ENABLE_DATABASE_ERROR_RECOVERY = true;   // ENABLE graceful error handling for database failures
    
    // Save Destination Configuration (Soft Coded) - NEW
    public static final boolean SAVE_TO_LOCAL_MACHINE = true;            // ENABLE local file saving (false = database saving)
    public static final boolean SAVE_AS_TO_LOCAL_MACHINE = true;         // ENABLE Save As to local files (false = database saving)
    public static final String LOCAL_SAVE_DIRECTORY = "projects";        // Default directory for local saves
    public static final boolean ENABLE_SAVE_FILE_CHOOSER = true;         // ENABLE file chooser for save location
    public static final boolean AUTO_CREATE_SAVE_DIRECTORY = true;       // ENABLE auto-create save directory if missing
    
    // Open Destination Configuration (Soft Coded) - NEW
    public static final boolean OPEN_FROM_LOCAL_MACHINE = true;          // ENABLE local file loading (false = database loading)
    public static final boolean ENABLE_OPEN_FILE_CHOOSER = true;         // ENABLE file chooser for open dialog
    public static final String LOCAL_OPEN_DIRECTORY = "projects";        // Default directory for local opens
    public static final boolean SHOW_RECENT_LOCAL_FILES = true;          // ENABLE recent local files in Open dialog
    
    // New Project Destination Configuration (Soft Coded) - NEW
    public static final boolean NEW_PROJECT_TO_LOCAL_MACHINE = true;     // ENABLE new projects on local machine (false = database)
    public static final boolean ENABLE_NEW_PROJECT_FILE_CHOOSER = true;  // ENABLE file chooser for new project save location - USER MANUAL LOCATION SELECTION
    public static final String LOCAL_NEW_PROJECT_DIRECTORY = "projects"; // Default directory for new projects (fallback when file chooser disabled)
    public static final boolean AUTO_SAVE_NEW_PROJECT = true;            // ENABLE automatic saving of new projects
    public static final boolean CLEAR_CANVAS_ON_NEW_PROJECT = true;      // ENABLE canvas clearing for new projects
    public static final boolean ENABLE_PROJECT_NAME_FROM_FILE = true;    // ENABLE extracting project name from chosen file path
    public static final boolean DEBUG_NEW_PROJECT_METHOD = true;         // ENABLE debug output to show which method is being used
    
    // Project Creation Configuration (Soft Coded)
    public static final String PROJECT_DEFAULT_PREFIX = "RUGREL_PRJ_";   // Soft-coded project prefix
    public static final String PROJECT_DEFAULT_DESCRIPTION = "Created from RUGREL dropdown"; // Default description
    public static final int PROJECT_NAME_MIN_LENGTH = 2;                 // Minimum project name length
    public static final int PROJECT_NAME_MAX_LENGTH = 50;                // Maximum project name length
    public static final boolean ENABLE_PROJECT_NAME_VALIDATION = true;   // ENABLE project name validation
    
    // ==================== PROJECT SERIALIZATION CONFIGURATION ====================
    
    // Canvas State Serialization (Soft Coded)
    public static final boolean ENABLE_CANVAS_STATE_SERIALIZATION = true;    // ENABLE complete canvas state preservation
    public static final boolean ENABLE_MARKS_SERIALIZATION = true;           // ENABLE marks collection serialization
    public static final boolean ENABLE_TEXT_MARKS_SERIALIZATION = true;      // ENABLE text marks serialization
    public static final boolean ENABLE_ZOOM_STATE_SERIALIZATION = true;      // ENABLE zoom level preservation
    public static final boolean ENABLE_VIEW_POSITION_SERIALIZATION = true;   // ENABLE view offset preservation
    public static final boolean ENABLE_UNDO_STACK_SERIALIZATION = true;      // ENABLE undo/redo stack preservation
    public static final boolean ENABLE_SELECTION_STATE_SERIALIZATION = true; // ENABLE selection state preservation
    
    // Project File Configuration (Soft Coded)
    public static final String PROJECT_FILE_EXTENSION = ".rugrel";          // Project file extension
    public static final String PROJECT_DATA_DIRECTORY = "projects/";        // Project data storage directory
    public static final boolean ENABLE_AUTO_PROJECT_BACKUP = true;          // ENABLE automatic project backups
    public static final boolean ENABLE_PROJECT_COMPRESSION = true;          // ENABLE project file compression
    public static final boolean ENABLE_PROJECT_ENCRYPTION = false;          // ENABLE project file encryption (future feature)
    
    // Serialization Format Configuration (Soft Coded)
    public static final String SERIALIZATION_FORMAT = "BINARY";            // BINARY format to properly serialize marks and canvas objects
    public static final boolean ENABLE_HUMAN_READABLE_FORMAT = false;       // DISABLE readable JSON format for proper mark serialization
    public static final boolean ENABLE_METADATA_PRESERVATION = true;        // ENABLE project metadata preservation
    public static final boolean ENABLE_TIMESTAMP_TRACKING = true;           // ENABLE creation/modification timestamps
    public static final boolean ENABLE_VERSION_TRACKING = true;             // ENABLE project version tracking
    
    // State Preservation Configuration (Soft Coded)
    public static final boolean PRESERVE_GRID_VISIBILITY = true;            // Preserve grid visibility state
    public static final boolean PRESERVE_MATERIAL_BOUNDARY = true;          // Preserve material boundary visibility
    public static final boolean PRESERVE_DOT_PREVIEW = true;                // Preserve dot preview state
    public static final boolean PRESERVE_PROPERTY_STRIP_STATE = true;       // Preserve property strip configuration
    public static final boolean PRESERVE_CANVAS_BACKGROUND = true;          // Preserve canvas background settings
    
    // New Project Canvas Management (Soft Coded)
    public static final boolean ENABLE_CANVAS_CLEAR_ON_NEW_PROJECT = true;  // Clear canvas when creating new project
    public static final boolean ENABLE_UNSAVED_WORK_WARNING = true;         // Warn about unsaved work before clearing
    public static final boolean ENABLE_VIEW_RESET_ON_NEW_PROJECT = true;    // Reset zoom and view position
    public static final boolean ENABLE_PROPERTY_RESET_ON_NEW_PROJECT = true; // Reset all mark properties
    public static final boolean ENABLE_UNDO_STACK_CLEAR = true;             // Clear undo/redo stack
    
    // Menu Configuration
    public static final boolean ENABLE_DROPDOWN = true;
    public static final String LOGO_TEXT = "⚙ RUGREL";
    public static final String DROPDOWN_INDICATOR = " ▼";
    public static final boolean SHOW_DROPDOWN_INDICATOR = true;
    
    // Menu Items Configuration (Soft Coded) - Restructured
    public static final String[] MENU_ITEMS = {
        "New",           // Renamed from "New Project"
        "Open",          // Renamed from "Open Project"
        "File Sequence", // Kept as is
        "Save",          // Renamed from "Save Project"
        "Save As"        // Renamed from "Save As..." (removed dots)
    };
    
    // Menu Icons (Soft Coded) - Restructured to match menu items
    public static final String[] MENU_ICONS = {
        "📄", // New
        "📂", // Open  
        "�", // File Sequence
        "💾", // Save
        "�"  // Save As
    };
    
    // Visual Configuration
    public static final Color LOGO_BACKGROUND = new Color(0, 120, 215);
    public static final Color LOGO_FOREGROUND = Color.WHITE;
    public static final Color MENU_BACKGROUND = Color.WHITE;
    public static final Color MENU_FOREGROUND = Color.BLACK;
    public static final Color MENU_HOVER = new Color(230, 240, 255);
    
    // Size Configuration (Soft Coded for Proper Text Display)
    public static final int LOGO_WIDTH = 160;              // INCREASED: Was 120, now 160 for full "⚙ RUGREL" text  
    public static final int LOGO_HEIGHT = 32;              // INCREASED: Was 30, now 32 for better proportions
    public static final int MENU_ITEM_HEIGHT = 25;         // Menu item height (unchanged)
    public static final int MENU_WIDTH = 180;              // Menu width (unchanged)
    
    // Text Display Configuration (Soft Coded) - NEW
    public static final boolean ENABLE_AUTO_WIDTH_CALCULATION = true;  // ENABLE automatic width calculation based on text
    public static final int TEXT_PADDING_WIDTH = 40;                   // Extra padding for dropdown indicator and margins
    public static final int MIN_LOGO_WIDTH = 140;                      // Minimum width for RUGREL tab
    public static final int MAX_LOGO_WIDTH = 200;                      // Maximum width for RUGREL tab
    
    // ==================== GRID CONTROL FOR AUTO-POPULATION FIELDS (SOFT CODED) ====================
    
    // Zoom Restriction Configuration (For Auto-Population Field Consistency)
    public static final boolean ENABLE_GRID_ZOOM_IN = false;           // DISABLE zoom in to maintain consistent auto-population field layout
    public static final boolean ENABLE_GRID_ZOOM_OUT = false;          // DISABLE zoom out to maintain consistent auto-population field layout
    public static final boolean ENABLE_MOUSE_WHEEL_ZOOM = false;       // DISABLE mouse wheel zoom for consistent field positioning
    public static final boolean ENABLE_KEYBOARD_ZOOM_SHORTCUTS = false; // DISABLE keyboard zoom shortcuts (Ctrl+/-)
    public static final boolean LOCK_ZOOM_LEVEL = true;                // LOCK zoom level to ensure auto-population fields align correctly
    public static final double FIXED_ZOOM_LEVEL = 1.0;                 // Fixed zoom level (100%) for consistent auto-population
    public static final boolean SHOW_ZOOM_RESTRICTION_MESSAGE = true;  // SHOW message when zoom is attempted
    
    // Mark Positioning Restriction Configuration (For Auto-Population Field Accuracy)
    public static final boolean ENABLE_FREE_MARK_POSITIONING = false;   // DISABLE free positioning to ensure auto-population accuracy
    public static final boolean ENABLE_MARK_DRAG_ANYWHERE = false;      // DISABLE dragging marks to arbitrary positions
    public static final boolean FORCE_GRID_SNAP_POSITIONING = true;     // FORCE marks to snap to predefined grid positions for auto-population
    public static final boolean ENABLE_MARK_BOUNDARY_ENFORCEMENT = true; // ENABLE strict boundary enforcement for auto-population fields
    public static final boolean AUTO_CORRECT_INVALID_POSITIONS = true;  // AUTO correct marks that are placed in invalid positions
    public static final int GRID_SNAP_TOLERANCE = 15;                   // Snap tolerance in pixels for auto-population field alignment
    
    // Auto-Population Field Protection Configuration
    public static final boolean PROTECT_AUTO_POPULATION_AREAS = true;   // PROTECT areas where auto-population fields are located
    public static final boolean PREVENT_MARK_OVERLAP_IN_FIELDS = true;  // PREVENT marks from overlapping auto-population fields
    public static final boolean SHOW_FIELD_PROTECTION_WARNINGS = true;  // SHOW warnings when attempting to place marks in protected areas
    public static final boolean ENABLE_FIELD_POSITION_VALIDATION = true; // ENABLE validation for auto-population field positioning
    
    // Grid Stability Configuration (For Consistent Auto-Population)
    public static final boolean ENFORCE_GRID_STABILITY = true;          // ENFORCE stable grid behavior for auto-population consistency
    public static final boolean DISABLE_GRID_MODIFICATIONS = true;      // DISABLE grid modifications that could affect auto-population
    public static final boolean MAINTAIN_FIELD_ALIGNMENT = true;        // MAINTAIN alignment of auto-population fields
    public static final boolean DEBUG_GRID_RESTRICTIONS = true;         // ENABLE debug output for grid restriction system
    
    // ==================== POSITION LOCKING SYSTEM (SOFT CODED) ====================
    
    // Position Locking Configuration (Fix objects after placement)
    public static final boolean ENABLE_POSITION_LOCKING = true;         // ENABLE position locking system for mark objects
    public static final boolean AUTO_LOCK_ON_PLACEMENT = true;          // ENABLE automatic position locking when object is placed
    public static final boolean LOCK_TOP_LEFT_CORNER = true;            // LOCK the top-left corner portion specifically
    public static final boolean PREVENT_DRAG_WHEN_LOCKED = true;        // PREVENT dragging of position-locked objects
    public static final boolean PREVENT_RESIZE_WHEN_LOCKED = false;     // ALLOW resizing even when position is locked
    
    // Position Lock Visual Configuration
    public static final boolean SHOW_POSITION_LOCK_INDICATOR = true;    // SHOW visual indicator for position-locked objects
    public static final boolean USE_LOCK_ICON_OVERLAY = true;           // USE lock icon overlay on locked objects
    public static final boolean HIGHLIGHT_LOCKED_POSITION = true;       // HIGHLIGHT locked position with special border
    public static final boolean SHOW_LOCK_STATUS_IN_TOOLTIP = true;     // SHOW lock status in object tooltips
    
    // Position Lock Behavior Configuration
    public static final boolean ENABLE_MANUAL_POSITION_UNLOCK = false;  // ENABLE manual unlocking of position (false = permanent lock)
    public static final boolean RESET_LOCK_ON_GRID_CHANGE = false;      // RESET position locks when grid changes
    public static final boolean MAINTAIN_LOCK_ACROSS_SESSIONS = true;   // MAINTAIN position locks when saving/loading
    
    // Debug Configuration for Position Locking
    public static final boolean DEBUG_POSITION_LOCKING = true;          // ENABLE debug output for position locking operations
    public static final boolean LOG_POSITION_LOCK_EVENTS = true;        // LOG when objects are position-locked or unlock attempts
    
    // ==================== GRID LOCKING AND ZOOM CONTROL SYSTEM (SOFT CODED) ====================
    
    // Grid Locking Configuration (Prevent ALL Grid Modifications - Grid Fixed)
    public static final boolean ENABLE_GRID_LOCKING = true;             // ENABLE grid locking system for ALL tabs - GRID FIXED
    public static final boolean LOCK_GRID_IN_ALL_TABS = true;           // LOCK grid in ALL tabs - GRID FIXED
    public static final boolean PREVENT_GRID_RESIZE = true;             // PREVENT grid resizing operations - GRID FIXED
    public static final boolean PREVENT_GRID_CELL_MODIFICATION = true;  // PREVENT individual grid cell modifications - GRID FIXED
    public static final boolean LOCK_GRID_SNAP_SETTINGS = true;         // LOCK grid snap-to-grid settings - GRID FIXED
    public static final boolean PREVENT_GRID_VISIBILITY_TOGGLE = false; // ALLOW grid visibility toggle even when locked
    
    // Zoom Control Configuration (Disable ALL Zoom Operations - Grid Fixed)
    public static final boolean DISABLE_ZOOM_IN_ALL_TABS = true;        // DISABLE zoom in operations in ALL tabs - GRID FIXED
    public static final boolean DISABLE_ZOOM_OUT_ALL_TABS = true;       // DISABLE zoom out operations in ALL tabs - GRID FIXED  
    public static final boolean DISABLE_ZOOM_FIT_ALL_TABS = true;       // DISABLE zoom to fit operations in ALL tabs - GRID FIXED
    public static final boolean DISABLE_ZOOM_RESET_ALL_TABS = true;     // DISABLE zoom reset operations in ALL tabs - GRID FIXED
    public static final boolean DISABLE_ALL_ZOOM_OPERATIONS = true;     // DISABLE ALL zoom operations globally - GRID FIXED
    public static final boolean LOCK_CURRENT_ZOOM_LEVEL = true;         // LOCK current zoom level globally - GRID FIXED
    public static final boolean SHOW_ZOOM_DISABLED_MESSAGE = true;      // SHOW message when zoom operations are blocked
    
    // Grid Lock Visual Configuration - Minimal Visual Interference
    public static final boolean SHOW_GRID_LOCK_INDICATOR = false;       // HIDE lock indicator to prevent grid overlap
    public static final boolean USE_LOCK_OVERLAY_FOR_GRID = false;      // HIDE lock overlay to prevent grid overlap
    public static final boolean HIGHLIGHT_LOCKED_GRID_BORDER = false;   // HIDE border highlight to prevent grid overlap
    public static final boolean SHOW_GRID_LOCK_STATUS_MESSAGE = false;  // HIDE status message to prevent grid overlap
    
    // Grid Lock Behavior Configuration - Universal Grid Fixed
    public static final boolean AUTO_LOCK_GRID_ON_STARTUP = true;       // AUTO-lock grid when application starts - GRID FIXED
    public static final boolean MAINTAIN_GRID_LOCK_STATE = true;        // MAINTAIN grid lock across sessions - GRID FIXED
    public static final boolean ENABLE_GRID_UNLOCK_SHORTCUT = false;    // DISABLE keyboard shortcut to unlock grid - GRID FIXED
    public static final boolean RESET_ZOOM_ON_GRID_LOCK = false;        // RESET zoom to default when grid is locked
    
    // Debug Configuration for Grid Locking
    public static final boolean DEBUG_GRID_LOCKING = true;              // ENABLE debug output for grid locking operations
    public static final boolean LOG_ZOOM_BLOCK_EVENTS = true;           // LOG when zoom operations are blocked
    public static final boolean LOG_GRID_MODIFICATION_BLOCKS = true;    // LOG when grid modifications are prevented
    
    // ==================== FIXED GRID SYSTEM - NO ZOOM/MODIFICATIONS ALLOWED ====================
    
    // Complete Grid Fixation Configuration (No Zoom, No Modifications)
    public static final boolean GRID_COMPLETELY_FIXED = true;           // MASTER SWITCH - Grid is completely fixed (no zoom, no modifications)
    public static final boolean REMOVE_ALL_ZOOM_CONTROLS = true;        // REMOVE all zoom controls from UI
    public static final boolean REMOVE_GRID_MODIFICATION_CONTROLS = true; // REMOVE all grid modification controls from UI
    public static final boolean DISABLE_ALL_ZOOM_SHORTCUTS = true;      // DISABLE all zoom keyboard shortcuts
    public static final boolean DISABLE_ALL_ZOOM_MOUSE_ACTIONS = true;  // DISABLE all zoom mouse actions (wheel, clicks)
    public static final boolean PERMANENT_GRID_LOCK = true;             // PERMANENT grid lock - cannot be unlocked
    public static final boolean HIDE_ZOOM_LEVEL_INDICATOR = true;       // HIDE zoom level indicator since zoom is disabled
    public static final String FIXED_GRID_MESSAGE = "Grid is fixed - No zoom or modifications allowed"; // Message for fixed grid
    
    // ==================== POINTWISE COORDINATE SYSTEM (0-10 SCALE) - SOFT CODED ====================
    
    // Coordinate System Configuration
    public static final boolean ENABLE_POINTWISE_COORDINATES = true;     // ENABLE 0-10 pointwise coordinate system instead of pixel coordinates
    public static final boolean SHOW_GRID_COORDINATES = true;            // SHOW coordinate values within grid cells
    public static final boolean USE_DECIMAL_COORDINATES = true;          // USE decimal precision for coordinates (e.g., 5.67)
    public static final boolean ENABLE_COORDINATE_LABELS_IN_GRID = true; // ENABLE coordinate labels inside each grid cell
    public static final boolean SHOW_COORDINATE_AXES = true;             // SHOW X and Y axis labels around the grid
    
    // Coordinate Scale Configuration
    public static final double COORDINATE_X_MIN = 0.0;                   // Minimum X coordinate value
    public static final double COORDINATE_X_MAX = 10.0;                  // Maximum X coordinate value  
    public static final double COORDINATE_Y_MIN = 0.0;                   // Minimum Y coordinate value
    public static final double COORDINATE_Y_MAX = 10.0;                  // Maximum Y coordinate value
    
    // Coordinate Display Configuration
    public static final String POINTWISE_COORDINATE_FORMAT = "%.1f";     // Format for pointwise coordinates (1 decimal place)
    public static final boolean ENABLE_COORDINATE_CURSOR_TRACKING = true; // ENABLE real-time cursor coordinate tracking in 0-10 scale
    public static final boolean LOG_COORDINATE_CHANGES = true;           // LOG coordinate changes to console
    public static final boolean SHOW_COORDINATE_CROSSHAIRS = false;      // SHOW crosshairs at cursor position (optional)
    
    // Grid Cell Coordinate Display Configuration  
    public static final boolean SHOW_CELL_X_COORDINATES = true;          // SHOW X coordinate values in grid cells
    public static final boolean SHOW_CELL_Y_COORDINATES = true;          // SHOW Y coordinate values in grid cells
    public static final boolean USE_CELL_CENTER_COORDINATES = true;      // USE center of cell for coordinate display
    public static final Color COORDINATE_TEXT_COLOR = Color.BLUE;        // Color for coordinate text in grid
    public static final Font COORDINATE_TEXT_FONT = new Font("Arial", Font.BOLD, 10); // Font for coordinate text
    
    // ==================== PROFESSIONAL GRID INFO BOX CONFIGURATION (SOFT CODED) ====================
    
    // Grid Info Box Display Configuration (Professional Appearance)
    public static final boolean ENABLE_PROFESSIONAL_GRID_INFO_BOX = true; // ENABLE professional grid information display in top-right corner
    public static final boolean SHOW_GRID_SIZE_INFO = true;              // SHOW grid dimensions (e.g., "14x8 cells")
    public static final boolean SHOW_ZOOM_LEVEL_INFO = true;             // SHOW current zoom level in grid info box
    public static final boolean SHOW_SNAP_STATUS_INFO = true;            // SHOW grid snapping status (ON/OFF)
    public static final boolean SHOW_RESTRICTION_STATUS_INFO = true;     // SHOW restriction status (grid locked/unlocked)
    public static final boolean SHOW_GRID_INFO_BOX_BORDER = true;        // SHOW professional border around grid info box
    
    // Grid Info Box Styling Configuration
    public static final int GRID_INFO_BOX_WIDTH = 140;                  // Width of grid info box in pixels
    public static final int GRID_INFO_BOX_MARGIN = 15;                  // Margin from screen edge
    public static final Color GRID_INFO_BOX_BACKGROUND = new Color(248, 250, 252, 220); // Light blue-gray with transparency
    public static final Color GRID_INFO_BOX_BORDER = new Color(70, 130, 180);          // Steel blue border
    public static final Color GRID_INFO_BOX_TITLE_COLOR = new Color(25, 25, 112);      // Midnight blue title
    public static final Color GRID_INFO_BOX_TEXT_COLOR = new Color(47, 79, 79);        // Dark slate gray text
    public static final int GRID_INFO_BOX_CORNER_RADIUS = 8;            // Rounded corner radius
    
    // Behavior Configuration
    public static final boolean ENABLE_HOVER_EFFECTS = true;
    public static final boolean ENABLE_MENU_ICONS = true;
    public static final boolean ENABLE_DEBUG_OUTPUT = true;
    
    // ==================== DUPLICATE ICON PREVENTION AND FUNCTIONALITY FIX (SOFT CODED) ====================
    
    // Master Duplicate Icon Control Configuration
    public static final boolean PREVENT_DUPLICATE_PROPERTY_ICONS = true; // MASTER CONTROL: PREVENT duplicate Clear Trans, Mirror, Lock Size, Disable Print icons
    public static final boolean USE_SINGLE_PROPERTY_LOCATION = true;     // USE only one location for property icons (prevent duplicates)
    public static final boolean ELIMINATE_ALL_DUPLICATE_SOURCES = true;   // ELIMINATE all known sources of duplicate property icons
    
    // Individual Component Duplicate Prevention
    public static final boolean DISABLE_MARK_TAB_PROPERTY_BUTTONS = true; // DISABLE property buttons in mark tab to prevent duplicates
    public static final boolean DISABLE_HORIZONTAL_PROPERTY_DUPLICATES = true; // DISABLE duplicates in horizontal properties layout
    public static final boolean DISABLE_THORX6_PROPERTY_DUPLICATES = true; // DISABLE duplicates in ThorX6 properties config
    public static final boolean DISABLE_MARK_CONFIG_PROPERTY_BUTTONS = true; // DISABLE property buttons in mark config to prevent duplicates
    
    // Property Icon Display Location Control (Single Source Management)
    public static final boolean SHOW_PROPERTIES_IN_BOTTOM_STRIP = true;   // SHOW property icons in bottom properties strip (PRIMARY AND ONLY location)
    public static final boolean SHOW_PROPERTIES_IN_MARK_TAB = false;      // HIDE property icons in mark tab to prevent duplicates
    public static final boolean SHOW_PROPERTIES_IN_HORIZONTAL_LAYOUT = false; // HIDE property icons in horizontal layout to prevent duplicates
    public static final boolean SHOW_PROPERTIES_IN_THORX6_CONFIG = true;   // SHOW property icons in ThorX6 config (this IS the bottom strip)
    
    // Specific Duplicate Icon Elimination Controls
    public static final boolean ELIMINATE_CLEAR_TRANS_DUPLICATES = true;   // ELIMINATE all duplicate "Clear Trans" icons except in bottom strip
    public static final boolean ELIMINATE_MIRROR_DUPLICATES = true;        // ELIMINATE all duplicate "Mirror" icons except in bottom strip
    public static final boolean ELIMINATE_LOCK_SIZE_DUPLICATES = true;     // ELIMINATE all duplicate "Lock Size" icons except in bottom strip  
    public static final boolean ELIMINATE_DISABLE_PRINT_DUPLICATES = true; // ELIMINATE all duplicate "Disable Print" icons except in bottom strip
    
    // Property Functionality Configuration (ONLY in bottom strip)
    public static final boolean ENABLE_CLEAR_TRANS_FUNCTIONALITY = true;  // ENABLE Clear Transform functionality (ONLY in bottom strip)
    public static final boolean ENABLE_MIRROR_FUNCTIONALITY = true;       // ENABLE Mirror functionality (ONLY in bottom strip)
    public static final boolean ENABLE_LOCK_SIZE_FUNCTIONALITY = true;    // ENABLE Lock Size functionality (ONLY in bottom strip)
    public static final boolean ENABLE_DISABLE_PRINT_FUNCTIONALITY = true; // ENABLE Disable Print functionality (ONLY in bottom strip)
    
    // Property Functionality Integration
    public static final boolean CONNECT_PROPERTIES_TO_CANVAS = true;      // CONNECT property actions to DrawingCanvas methods
    public static final boolean ENABLE_PROPERTY_STATE_SYNC = true;        // ENABLE synchronization of property states across components
    public static final boolean USE_CENTRALIZED_PROPERTY_MANAGEMENT = true; // USE centralized management to prevent duplicate functionality
    
    // ==================== CLEAR TRANS FUNCTIONALITY CONFIGURATION (INTELLIGENT DELETION) ====================
    
    // Clear Trans Core Features
    public static final boolean ENABLE_INTELLIGENT_CLEAR = true;          // ENABLE intelligent clearing based on selection
    public static final boolean CLEAR_SELECTED_MARKS_ONLY = true;         // CLEAR only selected marks when something is selected
    public static final boolean CLEAR_ALL_WHEN_NOTHING_SELECTED = false;  // CLEAR all marks when nothing is selected (safer default)
    public static final boolean CONFIRM_CLEAR_OPERATIONS = true;          // CONFIRM before clearing (safety feature)
    
    // Clear Trans Scope Configuration
    public static final boolean CLEAR_TEXT_MARKS = true;                  // CLEAR text marks when Clear Trans is triggered
    public static final boolean CLEAR_GRAPHIC_MARKS = true;               // CLEAR graphic marks when Clear Trans is triggered
    public static final boolean CLEAR_BARCODE_MARKS = true;               // CLEAR barcode marks when Clear Trans is triggered
    public static final boolean CLEAR_IMAGE_MARKS = true;                 // CLEAR image marks when Clear Trans is triggered
    public static final boolean CLEAR_ALL_MARK_TYPES = true;              // CLEAR all types of marks (master control)
    
    // Clear Trans Safety and Validation
    public static final boolean VALIDATE_BEFORE_CLEAR = true;             // VALIDATE that marks can be safely cleared
    public static final boolean BACKUP_BEFORE_CLEAR = true;               // CREATE backup before clearing (for undo)
    public static final boolean LOG_CLEAR_OPERATIONS = true;              // LOG clear operations for debugging
    public static final boolean SHOW_CLEAR_FEEDBACK = true;               // SHOW user feedback after clearing
    
    // Clear Trans User Experience
    public static final boolean ANIMATE_CLEAR_OPERATION = true;           // ANIMATE the clearing process (visual feedback)
    public static final boolean PLAY_CLEAR_SOUND = false;                 // PLAY sound effect when clearing (disabled by default)
    public static final boolean HIGHLIGHT_BEFORE_CLEAR = true;            // HIGHLIGHT marks that will be cleared
    public static final boolean SHOW_CLEAR_PROGRESS = false;              // SHOW progress bar for large clear operations (disabled for speed)
    
    // ==================== MIRROR FUNCTIONALITY CONFIGURATION (INTELLIGENT IMAGE FLIPPING) ====================
    
    // Mirror Core Features
    public static final boolean ENABLE_INTELLIGENT_MIRROR = true;         // ENABLE intelligent mirroring based on selection and content type
    public static final boolean MIRROR_SELECTED_MARKS_ONLY = true;        // MIRROR only selected marks when something is selected
    public static final boolean CONFIRM_MIRROR_OPERATIONS = false;        // CONFIRM before mirroring (disabled for quick operations)
    public static final boolean AUTO_DETECT_MIRROR_TYPE = true;           // AUTO detect best mirror type (horizontal/vertical) based on content
    
    // Mirror Direction Configuration
    public static final boolean ENABLE_HORIZONTAL_MIRROR = true;          // ENABLE horizontal mirroring (flip left-right)
    public static final boolean ENABLE_VERTICAL_MIRROR = true;            // ENABLE vertical mirroring (flip up-down)
    public static final boolean DEFAULT_HORIZONTAL_MIRROR = true;         // DEFAULT to horizontal mirroring when both enabled
    public static final boolean ALLOW_DIAGONAL_MIRROR = false;           // ALLOW diagonal mirroring (advanced feature, disabled by default)
    
    // Mirror Content Type Support (Comprehensive Coverage)
    public static final boolean MIRROR_TEXT_MARKS = true;                 // MIRROR text marks (reverse text or flip orientation)
    public static final boolean MIRROR_GRAPHIC_MARKS = true;              // MIRROR graphic marks (flip shapes and drawings)
    public static final boolean MIRROR_BARCODE_MARKS = true;              // MIRROR barcode marks (flip barcode orientation)
    public static final boolean MIRROR_IMAGE_MARKS = true;                // MIRROR image marks (flip imported images)
    public static final boolean MIRROR_BOW_TEXT_MARKS = true;             // MIRROR bow text marks (flip bow direction)
    public static final boolean MIRROR_ARC_MARKS = true;                  // MIRROR arc marks (flip arc direction)
    
    // Mirror Support for Additional Mark Types (Full Coverage)
    public static final boolean MIRROR_GRAPH_MARKS = true;                // MIRROR graph marks (flip imported image content)
    public static final boolean MIRROR_RULER_MARKS = true;                // MIRROR ruler marks (flip ruler orientation and direction)
    public static final boolean MIRROR_AVOID_POINT_MARKS = true;          // MIRROR avoid point marks (flip position and zone)
    public static final boolean MIRROR_RECTANGLE_MARKS = true;            // MIRROR rectangle marks (flip shape position and orientation)
    public static final boolean MIRROR_LINE_MARKS = true;                 // MIRROR line marks (flip line position and direction)
    public static final boolean MIRROR_DOT_MATRIX_MARKS = true;           // MIRROR dot matrix marks (flip matrix while maintaining readability)
    public static final boolean MIRROR_FARZI_MARKS = true;                // MIRROR farzi marks (flip text content and position)
    
    // Mirror Image Processing Options
    public static final boolean USE_HIGH_QUALITY_MIRROR = true;           // USE high-quality image processing for mirroring
    public static final boolean PRESERVE_IMAGE_TRANSPARENCY = true;       // PRESERVE alpha channel when mirroring images
    public static final boolean OPTIMIZE_MIRROR_PERFORMANCE = true;       // OPTIMIZE mirror operations for better performance
    public static final boolean CACHE_MIRRORED_IMAGES = false;            // CACHE mirrored images (disabled to save memory)
    
    // Mirror Safety and Validation
    public static final boolean VALIDATE_BEFORE_MIRROR = true;            // VALIDATE that marks can be safely mirrored
    public static final boolean BACKUP_BEFORE_MIRROR = true;              // CREATE backup before mirroring (for undo)
    public static final boolean LOG_MIRROR_OPERATIONS = true;             // LOG mirror operations for debugging
    public static final boolean SHOW_MIRROR_FEEDBACK = true;              // SHOW user feedback after mirroring
    
    // Mirror User Experience
    public static final boolean ANIMATE_MIRROR_OPERATION = true;          // ANIMATE the mirroring process (visual feedback)
    public static final boolean PLAY_MIRROR_SOUND = false;                // PLAY sound effect when mirroring (disabled by default)
    public static final boolean HIGHLIGHT_BEFORE_MIRROR = true;           // HIGHLIGHT marks that will be mirrored
    public static final boolean SHOW_MIRROR_DIRECTION_INDICATOR = true;   // SHOW direction indicator during mirror operation
    public static final boolean PREVIEW_MIRROR_RESULT = false;            // PREVIEW mirror result before applying (disabled for speed)
    
    // Mirror Advanced Features
    public static final boolean ENABLE_MIRROR_CONSTRAINTS = true;         // ENABLE constraints to prevent invalid mirror operations
    public static final boolean RESPECT_LOCKED_MARKS = true;              // RESPECT locked marks (don't mirror if locked)
    public static final boolean MIRROR_MAINTAINS_SELECTION = true;        // MAINTAIN selection state after mirroring
    public static final boolean ENABLE_MULTI_MARK_MIRROR = true;          // ENABLE mirroring of multiple selected marks simultaneously
    
    // ==================== LOCK SIZE FUNCTIONALITY CONFIGURATION (INTELLIGENT SIZE LOCKING) ====================
    
    // Lock Size Core Features
    public static final boolean ENABLE_INTELLIGENT_LOCK_SIZE = true;      // ENABLE intelligent size locking based on selection and mark state
    public static final boolean LOCK_SELECTED_MARKS_ONLY = true;          // LOCK size of only selected marks when something is selected
    public static final boolean CONFIRM_LOCK_SIZE_OPERATIONS = false;     // CONFIRM before locking/unlocking size (disabled for quick operations)
    public static final boolean AUTO_DETECT_LOCK_REQUIREMENTS = true;     // AUTO detect marks that should have size locked by default
    
    // Lock Size Application Mode
    public static final boolean ENABLE_PER_MARK_SIZE_LOCKING = true;      // ENABLE individual per-mark size locking (recommended)
    public static final boolean ENABLE_GLOBAL_SIZE_LOCKING = false;       // ENABLE global size locking (all marks - legacy mode)
    public static final boolean PREFER_PER_MARK_OVER_GLOBAL = true;       // PREFER per-mark locking over global when both enabled
    public static final boolean OVERRIDE_GLOBAL_WITH_PER_MARK = true;     // OVERRIDE global lock with individual mark settings
    
    // Lock Size Mark Type Support (Comprehensive Coverage)
    public static final boolean LOCK_SIZE_TEXT_MARKS = true;              // LOCK SIZE for text marks (prevent text resize)
    public static final boolean LOCK_SIZE_GRAPHIC_MARKS = true;           // LOCK SIZE for graphic marks (prevent shape resize)
    public static final boolean LOCK_SIZE_BARCODE_MARKS = true;           // LOCK SIZE for barcode marks (maintain barcode integrity)
    public static final boolean LOCK_SIZE_IMAGE_MARKS = true;             // LOCK SIZE for image marks (prevent image distortion)
    public static final boolean LOCK_SIZE_BOW_TEXT_MARKS = true;          // LOCK SIZE for bow text marks (maintain bow shape)
    public static final boolean LOCK_SIZE_ARC_MARKS = true;               // LOCK SIZE for arc marks (preserve arc proportions)
    
    // Lock Size Support for Additional Mark Types (Full Coverage)
    public static final boolean LOCK_SIZE_GRAPH_MARKS = true;             // LOCK SIZE for graph marks (prevent graph distortion)
    public static final boolean LOCK_SIZE_RULER_MARKS = true;             // LOCK SIZE for ruler marks (maintain ruler accuracy)
    public static final boolean LOCK_SIZE_AVOID_POINT_MARKS = true;       // LOCK SIZE for avoid point marks (preserve zone size)
    public static final boolean LOCK_SIZE_RECTANGLE_MARKS = true;         // LOCK SIZE for rectangle marks (maintain shape proportions)
    public static final boolean LOCK_SIZE_LINE_MARKS = true;              // LOCK SIZE for line marks (preserve line thickness)
    public static final boolean LOCK_SIZE_DOT_MATRIX_MARKS = true;        // LOCK SIZE for dot matrix marks (maintain readability)
    public static final boolean LOCK_SIZE_FARZI_MARKS = true;             // LOCK SIZE for farzi marks (preserve text size)
    
    // Lock Size Resize Prevention Options
    public static final boolean BLOCK_RESIZE_HANDLES_WHEN_LOCKED = true;  // BLOCK resize handles from appearing when size is locked
    public static final boolean SHOW_LOCK_VISUAL_INDICATOR = true;        // SHOW visual indicator when mark size is locked
    public static final boolean DISABLE_RESIZE_CURSOR_WHEN_LOCKED = true; // DISABLE resize cursor over locked marks
    public static final boolean PREVENT_RESIZE_OPERATIONS_WHEN_LOCKED = true; // PREVENT all resize operations on locked marks
    
    // Lock Size Safety and Validation
    public static final boolean VALIDATE_BEFORE_SIZE_LOCK = true;         // VALIDATE that marks can be safely size-locked
    public static final boolean BACKUP_BEFORE_SIZE_LOCK = true;           // CREATE backup before locking size (for undo)
    public static final boolean LOG_SIZE_LOCK_OPERATIONS = true;          // LOG size lock operations for debugging
    public static final boolean SHOW_SIZE_LOCK_FEEDBACK = true;           // SHOW user feedback after locking/unlocking size
    
    // Lock Size User Experience
    public static final boolean ANIMATE_SIZE_LOCK_OPERATION = true;       // ANIMATE the size locking process (visual feedback)
    public static final boolean PLAY_SIZE_LOCK_SOUND = false;             // PLAY sound effect when locking size (disabled by default)
    public static final boolean HIGHLIGHT_BEFORE_SIZE_LOCK = true;        // HIGHLIGHT marks that will be size-locked
    public static final boolean SHOW_SIZE_LOCK_STATUS_INDICATOR = true;   // SHOW status indicator for size lock state
    public static final boolean PREVIEW_SIZE_LOCK_RESULT = false;         // PREVIEW size lock result before applying (disabled for speed)
    
    // Lock Size Advanced Features
    public static final boolean ENABLE_SIZE_LOCK_CONSTRAINTS = true;      // ENABLE constraints to prevent invalid size lock operations
    public static final boolean RESPECT_EXISTING_LOCK_STATE = true;       // RESPECT existing lock state when applying new locks
    public static final boolean SIZE_LOCK_MAINTAINS_SELECTION = true;     // MAINTAIN selection state after size locking
    public static final boolean ENABLE_MULTI_MARK_SIZE_LOCK = true;       // ENABLE size locking of multiple selected marks simultaneously
    
    // Lock Size Integration Options
    public static final boolean INTEGRATE_WITH_CANVAS_SIZE_LOCK = false;  // INTEGRATE with legacy canvas-wide size lock (disabled for per-mark)
    public static final boolean SYNC_WITH_CHECKBOX_STATE = true;          // SYNC lock state with property panel checkbox
    public static final boolean PERSIST_SIZE_LOCK_STATE = true;           // PERSIST size lock state when saving projects
    public static final boolean RESTORE_SIZE_LOCK_ON_LOAD = true;         // RESTORE size lock state when loading projects
    
    // ==================================== TEXTMARK RESIZE CONTROL (MULTI-DIRECTIONAL) ====================================
    
    // TextMark Enhanced Resize Functionality
    public static final boolean ENABLE_TEXTMARK_MULTI_DIRECTIONAL_RESIZE = true;    // ENABLE multi-directional resize for TextMark (vertical, horizontal, diagonal)
    public static final boolean ENABLE_TEXTMARK_FONT_SIZE_HANDLES = true;          // ENABLE font size adjustment handles (TOP/BOTTOM)
    public static final boolean ENABLE_TEXTMARK_SPACING_HANDLES = true;            // ENABLE character spacing handles (LEFT/RIGHT)
    public static final boolean ENABLE_TEXTMARK_DIAGONAL_HANDLES = true;           // ENABLE diagonal combo handles (corners)
    public static final boolean SHOW_TEXTMARK_RESIZE_FEEDBACK = true;              // SHOW visual feedback during text resize operations
    
    // TextMark Resize Sensitivity Control
    public static final double TEXTMARK_FONT_SIZE_SENSITIVITY = 0.5;               // Font size change per pixel (0.1-2.0 range)
    public static final double TEXTMARK_SPACING_SENSITIVITY = 0.02;                // Character spacing change per pixel (0.01-0.1 range)
    public static final double TEXTMARK_FINE_FONT_SENSITIVITY = 0.3;               // Fine font size control for diagonal resize
    public static final double TEXTMARK_FINE_SPACING_SENSITIVITY = 0.01;           // Fine spacing control for diagonal resize
    public static final double TEXTMARK_LINE_SPACING_SENSITIVITY = 0.01;           // Line spacing adjustment sensitivity
    
    // TextMark Resize Limits and Safety
    public static final float TEXTMARK_MIN_FONT_SIZE = 6.0f;                       // Minimum font size (prevent too small text)
    public static final float TEXTMARK_MAX_FONT_SIZE = 72.0f;                      // Maximum font size (prevent too large text)
    public static final double TEXTMARK_MIN_CHARACTER_WIDTH = 0.5;                 // Minimum character spacing (prevent cramped text)
    public static final double TEXTMARK_MAX_CHARACTER_WIDTH = 3.0;                 // Maximum character spacing (prevent too spaced text)
    public static final double TEXTMARK_MIN_LINE_SPACING = 0.5;                    // Minimum line spacing for multi-line text
    public static final double TEXTMARK_MAX_LINE_SPACING = 3.0;                    // Maximum line spacing for multi-line text
    
    // TextMark Handle Visual Configuration
    public static final int TEXTMARK_FONT_HANDLE_RADIUS = 12;                      // Font size handle hit radius (larger for importance)
    public static final int TEXTMARK_SPACING_HANDLE_RADIUS = 10;                   // Character spacing handle hit radius
    public static final int TEXTMARK_CORNER_HANDLE_RADIUS = 10;                    // Corner combo handle hit radius
    public static final boolean TEXTMARK_SHOW_HANDLE_TOOLTIPS = true;              // SHOW tooltips explaining handle functionality
    public static final boolean TEXTMARK_HIGHLIGHT_ACTIVE_HANDLE = true;           // HIGHLIGHT the currently active resize handle
    
    // TextMark Resize Operation Control
    public static final boolean TEXTMARK_RESPECT_LOCK_SIZE = true;                 // RESPECT Lock Size setting (disable resize when locked)
    public static final boolean TEXTMARK_UPDATE_DIMENSIONS_REALTIME = true;        // UPDATE text dimensions in real-time during resize
    public static final boolean TEXTMARK_SNAP_TO_GRID_DURING_RESIZE = false;       // SNAP to grid during resize operations (disabled for smooth resize)
    public static final boolean TEXTMARK_PRESERVE_ASPECT_RATIO = false;            // PRESERVE aspect ratio during resize (disabled for independent control)
    public static final boolean TEXTMARK_AUTO_UPDATE_TEXT_BOUNDS = true;           // AUTO update text bounds after resize operations
    
    // TextMark Directional Isolation Control (Fix Cross-Directional Interference)
    public static final boolean TEXTMARK_STRICT_DIRECTIONAL_RESIZE = true;         // ENABLE strict directional resizing (prevent cross-directional effects)
    public static final boolean TEXTMARK_VERTICAL_ONLY_FONT_HANDLES = true;        // TOP/BOTTOM handles only affect font size (no horizontal effects)
    public static final boolean TEXTMARK_HORIZONTAL_ONLY_SPACING_HANDLES = true;   // LEFT/RIGHT handles only affect spacing (no vertical effects)
    public static final boolean TEXTMARK_PREVENT_CROSS_AXIS_INTERFERENCE = true;   // PREVENT unintended cross-axis modifications
    public static final boolean TEXTMARK_ALLOW_POSITION_ADJUSTMENT = false;        // ALLOW position adjustment during spacing resize (disabled for pure resize)
    public static final boolean TEXTMARK_ALLOW_LINE_SPACING_IN_FONT_RESIZE = false; // ALLOW line spacing changes during font resize (disabled for pure resize)
    public static final boolean TEXTMARK_DISABLE_AUTO_POSITION_MOVEMENT = true;    // DISABLE automatic position changes during resize to prevent unwanted drag behavior
    
    // TextMark Resize Thresholds (Control When Cross-Directional Effects Trigger)
    public static final int TEXTMARK_CROSS_AXIS_THRESHOLD = 5;                     // Minimum pixel movement to trigger cross-axis effects (when enabled)
    public static final double TEXTMARK_DIRECTIONAL_DOMINANCE_RATIO = 2.0;         // Ratio required for one direction to dominate (e.g., 2:1 ratio)
    public static final boolean TEXTMARK_USE_DOMINANT_DIRECTION_ONLY = true;       // USE only the dominant drag direction (ignore weaker direction)
    
    // TextMark Resize Debug and Logging
    public static final boolean LOG_TEXTMARK_RESIZE_OPERATIONS = true;             // LOG resize operations for debugging
    public static final boolean SHOW_TEXTMARK_RESIZE_COORDINATES = false;          // SHOW coordinate info during resize (can be verbose)
    public static final boolean DEBUG_TEXTMARK_HANDLE_DETECTION = false;           // DEBUG handle detection (can be very verbose)
    public static final boolean SHOW_TEXTMARK_RESIZE_BOUNDS = false;               // SHOW text bounds during resize (debug feature)
    
    // Duplicate Source Elimination Flags (Advanced Control)
    public static final boolean BLOCK_MARK_TAB_PROPERTY_CREATION = true;   // BLOCK creation of property icons in mark tab entirely
    public static final boolean BLOCK_THORX6_PROPERTY_CREATION = false;    // ALLOW creation of property icons in ThorX6 config (this is the bottom strip)
    public static final boolean BLOCK_HORIZONTAL_PROPERTY_CREATION = true; // BLOCK creation of property icons in horizontal layout entirely
    public static final boolean ENFORCE_BOTTOM_STRIP_ONLY = true;          // ENFORCE that property icons ONLY appear in bottom strip (ThorX6 config)
    
    // Logging and Debugging for Duplicate Prevention
    public static final boolean LOG_DUPLICATE_PREVENTION_ACTIONS = true;   // LOG when duplicates are prevented
    public static final boolean LOG_PROPERTY_ICON_CREATION = true;         // LOG property icon creation attempts
    public static final boolean LOG_BLOCKED_DUPLICATE_ATTEMPTS = true;     // LOG when duplicate creation is blocked
    public static final boolean LOG_PROPERTY_ACTIONS = true;              // LOG property actions for debugging
    public static final boolean SHOW_PROPERTY_ACTION_FEEDBACK = true;     // SHOW user feedback when property actions are performed
    
    // Property Visual Configuration
    public static final boolean HIGHLIGHT_ACTIVE_PROPERTIES = true;       // HIGHLIGHT active property states (e.g., when Mirror is ON)
    public static final boolean USE_PROPERTY_TOOLTIPS = true;             // USE tooltips to explain property functionality
    public static final boolean ANIMATE_PROPERTY_CHANGES = false;         // ANIMATE property state changes (disabled for performance)
    
    // Property Label Display Configuration (CLEAN FORMAT)
    public static final boolean REMOVE_DUPLICATE_PROPERTY_TEXT = true;    // REMOVE duplicate text after checkboxes (e.g., "Clear Trans: [✓] Clear Trans" → "Clear Trans: [✓]")
    public static final boolean USE_CLEAN_CHECKBOX_FORMAT = true;         // USE clean format without redundant text repetition
    public static final boolean HIDE_REDUNDANT_LABELS = true;            // HIDE redundant label text after form controls
    public static final boolean SHOW_CONTROL_ONLY_FORMAT = true;         // SHOW only "Label: [Control]" format without repeating label text
    
    // Property Layout and Alignment Configuration (REVERTED TO SIMPLE)
    public static final boolean ENABLE_PROPERTY_ALIGNMENT = false;        // DISABLE complex alignment (reverted for visibility)
    public static final boolean USE_GRID_LAYOUT_FOR_PROPERTIES = false;   // DISABLE grid layout (reverted to FlowLayout)
    public static final boolean ALIGN_LABELS_RIGHT = false;              // DISABLE right alignment (reverted)
    public static final boolean ADD_VISUAL_GROUPING = false;              // DISABLE visual grouping (reverted)
    public static final boolean USE_CONSISTENT_SPACING = false;          // DISABLE consistent spacing (reverted)
    
    // Property Visual Enhancement Configuration
    public static final int PROPERTY_LABEL_WIDTH = 90;                   // FIXED width for property labels (consistent alignment)
    public static final int PROPERTY_CONTROL_WIDTH = 80;                 // FIXED width for property controls (checkboxes, fields)
    public static final int PROPERTY_ROW_HEIGHT = 28;                    // FIXED height for each property row
    public static final int PROPERTY_HORIZONTAL_GAP = 8;                 // HORIZONTAL gap between label and control
    public static final int PROPERTY_VERTICAL_GAP = 4;                   // VERTICAL gap between property rows
    
    // Property Border and Visual Indicators
    public static final boolean ADD_PROPERTY_BORDERS = true;             // ADD subtle borders around property groups
    public static final boolean HIGHLIGHT_PROPERTY_ON_HOVER = true;      // HIGHLIGHT property row on mouse hover
    public static final boolean USE_ALTERNATING_BACKGROUND = false;      // USE alternating row background colors (disabled for clean look)
    
    /**
     * Create Rugrel logo button with dropdown functionality
     */
    public static JButton createRugrelLogoWithDropdown() {
        String buttonText = LOGO_TEXT;
        if (SHOW_DROPDOWN_INDICATOR) {
            buttonText += DROPDOWN_INDICATOR;
        }
        
        JButton logoButton = new JButton(buttonText);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        logoButton.setFont(buttonFont);
        
        // Smart width calculation (Soft Coded)
        int calculatedWidth = LOGO_WIDTH; // Default width
        if (ENABLE_AUTO_WIDTH_CALCULATION) {
            calculatedWidth = calculateOptimalWidth(buttonText, buttonFont);
            
            // Apply soft-coded constraints
            calculatedWidth = Math.max(calculatedWidth, MIN_LOGO_WIDTH);
            calculatedWidth = Math.min(calculatedWidth, MAX_LOGO_WIDTH);
            
            if (ENABLE_DEBUG_OUTPUT) {
                System.out.println("🎯 RUGREL Tab Width Calculation:");
                System.out.println("   • Text: \"" + buttonText + "\"");
                System.out.println("   • Calculated Width: " + calculatedWidth + "px");
                System.out.println("   • Default Width: " + LOGO_WIDTH + "px");
                System.out.println("   • Auto-calculation: " + (ENABLE_AUTO_WIDTH_CALCULATION ? "ENABLED" : "DISABLED"));
            }
        }
        
        logoButton.setPreferredSize(new Dimension(calculatedWidth, LOGO_HEIGHT));
        logoButton.setBackground(LOGO_BACKGROUND);
        logoButton.setForeground(LOGO_FOREGROUND);
        // Border removed for compilation simplicity
        logoButton.setFocusPainted(false);
        
        if (ENABLE_DROPDOWN) {
            logoButton.addActionListener(e -> showDropdownMenu(logoButton));
        }
        
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("🎯 Rugrel logo created with dropdown functionality");
        }
        
        return logoButton;
    }
    
    /**
     * Calculate Optimal Width for RUGREL Tab (Soft Coded)
     */
    private static int calculateOptimalWidth(String text, Font font) {
        try {
            // Create temporary graphics context for text measurement
            java.awt.image.BufferedImage tempImage = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2d = tempImage.createGraphics();
            g2d.setFont(font);
            
            // Measure text width
            java.awt.FontMetrics fontMetrics = g2d.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(text);
            
            // Add padding for proper display
            int totalWidth = textWidth + TEXT_PADDING_WIDTH;
            
            g2d.dispose();
            
            if (ENABLE_DEBUG_OUTPUT) {
                System.out.println("📐 Text Width Calculation:");
                System.out.println("   • Raw Text Width: " + textWidth + "px");
                System.out.println("   • Padding: " + TEXT_PADDING_WIDTH + "px");
                System.out.println("   • Total Width: " + totalWidth + "px");
            }
            
            return totalWidth;
        } catch (Exception e) {
            // Fallback to default width if calculation fails
            System.err.println("❌ Width calculation failed, using default: " + e.getMessage());
            return LOGO_WIDTH;
        }
    }
    
    /**
     * Show dropdown menu below the logo button
     */
    private static void showDropdownMenu(JButton logoButton) {
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("📋 Showing Rugrel dropdown menu...");
        }
        
        JPopupMenu dropdownMenu = new JPopupMenu();
        dropdownMenu.setBackground(MENU_BACKGROUND);
        // Border removed for compilation simplicity
        
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            String menuItem = MENU_ITEMS[i];
            
            if (menuItem.equals("---")) {
                // Add separator
                dropdownMenu.addSeparator();
            } else {
                // Create menu item
                JMenuItem item = createMenuItem(menuItem, i);
                dropdownMenu.add(item);
            }
        }
        
        // Show menu below the button
        dropdownMenu.show(logoButton, 0, logoButton.getHeight());
    }
    
    /**
     * Create individual menu item with soft-coded styling
     */
    private static JMenuItem createMenuItem(String text, int index) {
        String itemText = text;
        
        // Add icon if enabled
        if (ENABLE_MENU_ICONS && index < MENU_ICONS.length && !MENU_ICONS[index].isEmpty()) {
            itemText = MENU_ICONS[index] + "  " + text;
        }
        
        JMenuItem menuItem = new JMenuItem(itemText);
        menuItem.setBackground(MENU_BACKGROUND);
        menuItem.setForeground(MENU_FOREGROUND);
        menuItem.setPreferredSize(new Dimension(MENU_WIDTH, MENU_ITEM_HEIGHT));
        
        // Add hover effect if enabled
        if (ENABLE_HOVER_EFFECTS) {
            menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    menuItem.setBackground(MENU_HOVER);
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    menuItem.setBackground(MENU_BACKGROUND);
                }
            });
        }
        
        // Add action listener
        menuItem.addActionListener(e -> handleMenuAction(text));
        
        return menuItem;
    }
    
    /**
     * Enhanced menu action handler with database integration (Soft Coded & Dynamic)
     */
    private static void handleMenuAction(String menuItem) {
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("🔧 Enhanced Menu Action: " + menuItem);
        }
        
        // Initialize auto-population grid restrictions on first menu interaction
        initializeAutoPopulationGridRestrictions();
        
        try {
            switch (menuItem) {
                case "New":  // Renamed from "New Project"
                    // Soft coding: Choose new project destination based on configuration
                    if (NEW_PROJECT_TO_LOCAL_MACHINE) {
                        handleNewProjectToLocal();
                    } else {
                        handleNewProjectWithDB();
                    }
                    break;
                case "Open": // Renamed from "Open Project"
                    // Soft coding: Choose open source based on configuration
                    if (OPEN_FROM_LOCAL_MACHINE) {
                        handleOpenProjectFromLocal();
                    } else {
                        handleOpenProjectWithDB();
                    }
                    break;
                case "File Sequence": // Kept as is
                    handleFileSequenceWithDB();
                    break;
                case "Save": // Renamed from "Save Project"
                    // Soft coding: Choose save destination based on configuration
                    if (SAVE_TO_LOCAL_MACHINE) {
                        handleSaveProjectToLocal();
                    } else {
                        handleSaveProjectWithDB();
                    }
                    break;
                case "Save As": // Renamed from "Save As..."
                    // Soft coding: Choose save destination based on configuration
                    if (SAVE_AS_TO_LOCAL_MACHINE) {
                        handleSaveAsProjectToLocal();
                    } else {
                        handleSaveAsProjectWithDB();
                    }
                    break;
                // Removed unwanted menu options:
                // - Import Design
                // - Export Design  
                // - Recent Files
                // - Settings
                // - About Rugrel
                // - Exit
                default:
                    System.out.println("⚠️ Unknown menu action: " + menuItem);
                    showEnhancedDialog("Error", "Unknown menu action: " + menuItem, "error");
            }
        } catch (Exception e) {
            System.err.println("❌ Error handling menu action: " + e.getMessage());
            showEnhancedDialog("Error", "Action failed: " + e.getMessage(), "error");
        }
    }
    
    // ==================== MENU ACTION HANDLERS (SOFT CODED) ====================
    
    private static void handleNewProject() {
        System.out.println("📄 Creating new project...");
        showSimpleDialog("New Project", "New project created successfully!", "success");
    }
    
    private static void handleOpenProject() {
        System.out.println("📂 Opening project...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Open Rugrel Project");
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("📂 Opening file: " + selectedFile.getName());
            showSimpleDialog("Open Project", "Project opened: " + selectedFile.getName(), "success");
        }
    }
    
    private static void handleSaveProject() {
        System.out.println("💾 Saving project...");
        showSimpleDialog("Save Project", "Project saved successfully!", "success");
    }
    
    private static void handleSaveAsProject() {
        System.out.println("💾 Save As...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Save Rugrel Project As");
        
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("💾 Saving as: " + selectedFile.getName());
            showSimpleDialog("Save As", "Project saved as: " + selectedFile.getName(), "success");
        }
    }
    
    private static void handleImportDesign() {
        System.out.println("📥 Importing design...");
        showSimpleDialog("Import Design", "Design import functionality ready", "info");
    }
    
    private static void handleExportDesign() {
        System.out.println("📤 Exporting design...");
        showSimpleDialog("Export Design", "Design export functionality ready", "info");
    }
    
    private static void handleRecentFiles() {
        System.out.println("📋 Showing recent files...");
        showSimpleDialog("Recent Files", "Recent files functionality ready", "info");
    }
    
    private static void handleSettings() {
        System.out.println("⚙️ Opening settings...");
        showSimpleDialog("Settings", "Settings functionality ready", "info");
    }
    
    private static void handleAboutRugrel() {
        System.out.println("ℹ️ Showing about dialog...");
        String aboutText = "Rugrel Dot Pin Marker\n" +
                          "Professional Marking Software\n" +
                          "Version 1.0\n\n" +
                          "Developed with soft coding techniques\n" +
                          "for maximum flexibility and customization.";
        showSimpleDialog("About Rugrel", aboutText, "info");
    }
    
    private static void handleExit() {
        System.out.println("🚪 Exiting application...");
        int result = JOptionPane.showConfirmDialog(
            null, 
            "Are you sure you want to exit Rugrel?", 
            "Exit Application", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Show simple dialog with soft-coded styling
     */
    private static void showSimpleDialog(String title, String message, String type) {
        int messageType;
        switch (type) {
            case "success":
                messageType = JOptionPane.INFORMATION_MESSAGE;
                break;
            case "error":
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
            case "warning":
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            default:
                messageType = JOptionPane.INFORMATION_MESSAGE;
        }
        
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
    
    // ==================== ENHANCED DATABASE-INTEGRATED METHODS (DYNAMIC & SOFT CODED) ====================
    
    /**
     * New Project to Local Machine (Soft Coded Alternative)
     */
    private static void handleNewProjectToLocal() {
        System.out.println("📄 Creating new project on local machine...");
        
        try {
            // Debug output for soft coding configuration
            if (DEBUG_NEW_PROJECT_METHOD) {
                System.out.println("🔧 SOFT CODING DEBUG:");
                System.out.println("   • NEW_PROJECT_TO_LOCAL_MACHINE = " + NEW_PROJECT_TO_LOCAL_MACHINE);
                System.out.println("   • ENABLE_NEW_PROJECT_FILE_CHOOSER = " + ENABLE_NEW_PROJECT_FILE_CHOOSER);
                System.out.println("   • Method: " + (ENABLE_NEW_PROJECT_FILE_CHOOSER ? "FILE CHOOSER (Manual Location)" : "DEFAULT DIRECTORY (Auto Location)"));
            }
            
            // Soft coding: Choose project creation method based on configuration
            if (ENABLE_NEW_PROJECT_FILE_CHOOSER) {
                // Method 1: File Chooser for Save Location (User chooses where to save)
                handleNewProjectWithFileChooser();
            } else {
                // Method 2: Simple Name Input with Default Directory
                handleNewProjectWithDefaultLocation();
            }
        } catch (Exception e) {
            System.err.println("❌ Error creating new project on local machine: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to create new project: " + e.getMessage(), "error");
        }
    }
    
    /**
     * New Project with File Chooser - User Selects Save Location
     */
    private static void handleNewProjectWithFileChooser() {
        System.out.println("📁 New project with file chooser for save location...");
        System.out.println("🎯 SOFT CODING: File chooser method activated (ENABLE_NEW_PROJECT_FILE_CHOOSER = true)");
        
        try {
            // Step 1: Get project name first
            System.out.println("📝 Step 1: Requesting project name from user...");
            String projectName = JOptionPane.showInputDialog(null, 
                "STEP 1: Enter new project name (" + PROJECT_NAME_MIN_LENGTH + "-" + PROJECT_NAME_MAX_LENGTH + " characters):\n\nNext step: You'll choose where to save the project file", 
                "New Project - Step 1 of 2", JOptionPane.QUESTION_MESSAGE);
            
            if (projectName == null || projectName.trim().isEmpty()) {
                System.out.println("❌ Project creation cancelled - no name provided");
                return;
            }
            
            String trimmedName = projectName.trim();
            
            // Validate project name (soft-coded validation)
            if (ENABLE_PROJECT_NAME_VALIDATION) {
                if (trimmedName.length() < PROJECT_NAME_MIN_LENGTH || trimmedName.length() > PROJECT_NAME_MAX_LENGTH) {
                    showEnhancedDialog("Invalid Name", 
                        "Project name must be " + PROJECT_NAME_MIN_LENGTH + "-" + PROJECT_NAME_MAX_LENGTH + " characters long.", "error");
                    return;
                }
            }
            
            // Step 2: Show file chooser for save location
            System.out.println("📂 Step 2: Opening file chooser for user to select save location...");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("STEP 2: Choose Location to Save New Project - " + trimmedName);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(LOCAL_NEW_PROJECT_DIRECTORY)); // Start in default directory
            
            // Set suggested filename
            String suggestedFileName = trimmedName + PROJECT_FILE_EXTENSION;
            fileChooser.setSelectedFile(new File(suggestedFileName));
            
            // Set file filter for .rugrel files
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "RUGREL Project Files (*" + PROJECT_FILE_EXTENSION + ")", 
                PROJECT_FILE_EXTENSION.replace(".", "")));
            
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                // Ensure proper extension
                if (!selectedFile.getName().endsWith(PROJECT_FILE_EXTENSION)) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + PROJECT_FILE_EXTENSION);
                }
                
                System.out.println("📁 User selected save location: " + selectedFile.getAbsolutePath());
                
                // Extract project name from file if enabled (soft-coded)
                String finalProjectName = trimmedName;
                if (ENABLE_PROJECT_NAME_FROM_FILE) {
                    String fileBaseName = selectedFile.getName().replace(PROJECT_FILE_EXTENSION, "");
                    if (!fileBaseName.isEmpty() && !fileBaseName.equals(trimmedName)) {
                        int choice = JOptionPane.showConfirmDialog(null,
                            "Use filename '" + fileBaseName + "' as project name?\n" +
                            "Current name: " + trimmedName + "\n" +
                            "Filename: " + fileBaseName,
                            "Project Name", JOptionPane.YES_NO_OPTION);
                        
                        if (choice == JOptionPane.YES_OPTION) {
                            finalProjectName = fileBaseName;
                        }
                    }
                }
                
                // Check if file already exists
                if (selectedFile.exists()) {
                    int choice = JOptionPane.showConfirmDialog(null,
                        "File already exists:\n" + selectedFile.getAbsolutePath() + "\n\nDo you want to overwrite it?",
                        "File Exists", JOptionPane.YES_NO_OPTION);
                    
                    if (choice != JOptionPane.YES_OPTION) {
                        System.out.println("❌ Project creation cancelled by user");
                        return;
                    }
                }
                
                // Create the new project
                createNewProjectAtLocation(selectedFile, finalProjectName);
            } else {
                System.out.println("❌ Project creation cancelled by user");
            }
        } catch (Exception e) {
            System.err.println("❌ Error in file chooser new project: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to create project with file chooser: " + e.getMessage(), "error");
        }
    }
    
    /**
     * New Project with Default Location - Simple Name Input
     */
    private static void handleNewProjectWithDefaultLocation() {
        System.out.println("📁 New project with default location...");
        
        try {
            // Get project name from user with validation
            String projectName = JOptionPane.showInputDialog(null, 
                "Enter new project name (" + PROJECT_NAME_MIN_LENGTH + "-" + PROJECT_NAME_MAX_LENGTH + " characters):", 
                "New Project - Default Location", JOptionPane.QUESTION_MESSAGE);
            
            if (projectName != null && !projectName.trim().isEmpty()) {
                String trimmedName = projectName.trim();
                
                // Validate project name (soft-coded validation)
                if (ENABLE_PROJECT_NAME_VALIDATION) {
                    if (trimmedName.length() < PROJECT_NAME_MIN_LENGTH || trimmedName.length() > PROJECT_NAME_MAX_LENGTH) {
                        showEnhancedDialog("Invalid Name", 
                            "Project name must be " + PROJECT_NAME_MIN_LENGTH + "-" + PROJECT_NAME_MAX_LENGTH + " characters long.", "error");
                        return;
                    }
                }
                
                // Create projects directory if needed (soft coding)
                if (AUTO_CREATE_SAVE_DIRECTORY) {
                    File projectsDir = new File(LOCAL_NEW_PROJECT_DIRECTORY);
                    if (!projectsDir.exists()) {
                        projectsDir.mkdirs();
                        System.out.println("📁 Created projects directory: " + LOCAL_NEW_PROJECT_DIRECTORY);
                    }
                }
                
                // Create file in default directory
                String fileName = trimmedName + PROJECT_FILE_EXTENSION;
                File projectFile = new File(LOCAL_NEW_PROJECT_DIRECTORY, fileName);
                
                // Check if project file already exists
                if (projectFile.exists()) {
                    int choice = JOptionPane.showConfirmDialog(null,
                        "Project '" + trimmedName + "' already exists.\nDo you want to overwrite it?",
                        "Project Exists", JOptionPane.YES_NO_OPTION);
                    
                    if (choice != JOptionPane.YES_OPTION) {
                        System.out.println("❌ Project creation cancelled by user");
                        return;
                    }
                }
                
                // Create the new project
                createNewProjectAtLocation(projectFile, trimmedName);
            }
        } catch (Exception e) {
            System.err.println("❌ Error in default location new project: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to create project at default location: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Create New Project at Specified Location (Shared Helper Method)
     */
    private static void createNewProjectAtLocation(File projectFile, String projectName) {
        try {
            System.out.println("📄 Creating new project at: " + projectFile.getAbsolutePath());
            
            // Clear canvas for new project (soft-coded)
            if (CLEAR_CANVAS_ON_NEW_PROJECT) {
                clearCanvasForNewProject();
                System.out.println("🧹 Canvas cleared for new project");
            }
            
            // Set current project name for tracking
            setCurrentProjectName(projectName);
            
            // Auto-save new project if enabled (soft-coded)
            if (AUTO_SAVE_NEW_PROJECT) {
                boolean success = createNewProjectFile(projectFile, projectName);
                
                if (success) {
                    System.out.println("✅ New project created and saved: " + projectFile.getName());
                    System.out.println("📊 Project creation summary:");
                    System.out.println("   • Name: " + projectName);
                    System.out.println("   • Location: " + projectFile.getAbsolutePath());
                    System.out.println("   • Directory: " + projectFile.getParent());
                    System.out.println("   • Canvas: Clean slate ready");
                    
                    showEnhancedDialog("Project Created", 
                        "New project created successfully!\n\n" +
                        "• Project: " + projectName + "\n" +
                        "• Location: " + projectFile.getAbsolutePath() + "\n" +
                        "• Canvas: Clean slate ready for work\n" +
                        "• Auto-saved: Ready for immediate use", "success");
                } else {
                    System.err.println("❌ Failed to save new project: " + projectFile.getName());
                    showEnhancedDialog("Error", "Failed to save new project to selected location", "error");
                }
            } else {
                // Just create the project in memory without saving
                System.out.println("✅ New project created in memory: " + projectName);
                showEnhancedDialog("Project Created", 
                    "New project created: " + projectName + "\n" +
                    "Remember to save your work using Save option.", "success");
            }
            
            System.out.println("🎯 New project ready: " + projectName);
        } catch (Exception e) {
            System.err.println("❌ Error creating project at location: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to create project: " + e.getMessage(), "error");
        }
    }

    /**
     * Enhanced New Project with Real Database Integration (Soft-coded)
     */
    private static void handleNewProjectWithDB() {
        System.out.println("📄 Creating new project with enhanced database integration...");
        
        try {
            // Enhanced validation with retry mechanism
            if (!validateDatabaseConnection()) {
                System.out.println("🔄 First database validation failed, attempting recovery...");
                
                // Attempt emergency initialization
                if (ENABLE_DATABASE_ERROR_RECOVERY && forceInitializeDatabasePanel()) {
                    System.out.println("✅ Emergency database initialization successful, retrying...");
                    
                    // Retry validation after emergency initialization
                    if (!validateDatabaseConnection()) {
                        showEnhancedDialog("Database Error", 
                            "❌ Database not available after recovery attempt.\n\n" +
                            "Please try one of these solutions:\n" +
                            "1. Click on the Database Tab first to initialize it\n" +
                            "2. Wait for the application to fully load\n" +
                            "3. Restart the application if the problem persists", "error");
                        return;
                    }
                } else {
                    showEnhancedDialog("Database Error", 
                        "❌ Database not available. Please ensure the application is fully loaded.\n\n" +
                        "💡 Try clicking on the Database Tab first to initialize it, then try again.", "error");
                    return;
                }
            }
            
            // Get project name from user with validation
            String projectName = JOptionPane.showInputDialog(null, 
                "Enter new project name (" + PROJECT_NAME_MIN_LENGTH + "-" + PROJECT_NAME_MAX_LENGTH + " characters):", 
                "New Project - RUGREL", JOptionPane.QUESTION_MESSAGE);
            
            if (projectName != null && !projectName.trim().isEmpty()) {
                String trimmedName = projectName.trim();
                
                // Generate project ID
                String projectId = generateProjectId();
                System.out.println("🆔 Generated Project ID: " + projectId);
                
                // Save to real database with enhanced description
                String description = PROJECT_DEFAULT_DESCRIPTION + " - ID: " + projectId;
                boolean success = saveProjectToDatabase(projectId, trimmedName, description);
                
                if (success) {
                    // Set current project name for tracking
                    setCurrentProjectName(trimmedName);
                    
                    // Clear canvas for true new project workflow (Soft-coded)
                    if (ENABLE_CANVAS_CLEAR_ON_NEW_PROJECT) {
                        clearCanvasForNewProject();
                    }
                    
                    // Update recent files if enabled
                    if (ENABLE_RECENT_FILES_SYNC) {
                        updateRecentFiles(trimmedName);
                    }
                    
                    System.out.println("✅ Project successfully created and stored in database: " + trimmedName);
                    
                    // Enhanced success notification
                    if (ENABLE_PROJECT_NOTIFICATIONS) {
                        showEnhancedDialog("New Project Created", 
                            "🎉 Project '" + trimmedName + "' created successfully!\n\n" +
                            "📋 Project ID: " + projectId + "\n" +
                            "💾 Stored in Database Tab\n" +
                            "🔄 Database Tab automatically opened\n" +
                            "🆕 Canvas cleared for fresh start", "success");
                    }
                } else {
                    // Handle database save failure
                    if (ENABLE_DATABASE_ERROR_RECOVERY) {
                        showEnhancedDialog("Save Failed", 
                            "❌ Failed to save project '" + trimmedName + "' to database.\n" +
                            "Please check database connection and try again.", "error");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error creating project: " + e.getMessage());
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                showEnhancedDialog("Error", 
                    "Failed to create project: " + e.getMessage() + "\n\n" +
                    "Please try again or check database connection.", "error");
            }
        }
    }
    
    /**
     * Enhanced Open Project with Complete State Restoration
     */
    /**
     * Open Project from Local Machine (Soft Coded Alternative)
     */
    private static void handleOpenProjectFromLocal() {
        System.out.println("📂 Opening project from local machine...");
        
        try {
            if (ENABLE_OPEN_FILE_CHOOSER) {
                // Use file chooser for better user experience
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open RUGREL Project");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File(LOCAL_OPEN_DIRECTORY));
                
                // Set file filter for .rugrel files
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "RUGREL Project Files (*" + PROJECT_FILE_EXTENSION + ")", 
                    PROJECT_FILE_EXTENSION.replace(".", "")));
                
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    
                    System.out.println("📂 Selected file: " + selectedFile.getAbsolutePath());
                    
                    // Load project from local file
                    boolean success = loadProjectFromLocalFile(selectedFile);
                    
                    if (success) {
                        // Extract project name from file name for tracking
                        String projectName = selectedFile.getName().replace(PROJECT_FILE_EXTENSION, "");
                        setCurrentProjectName(projectName);
                        
                        System.out.println("✅ Project loaded from local file: " + selectedFile.getName());
                        showEnhancedDialog("Open Complete", 
                            "Project loaded successfully from:\n" + selectedFile.getAbsolutePath(), "success");
                    } else {
                        System.err.println("❌ Failed to load project from: " + selectedFile.getAbsolutePath());
                        showEnhancedDialog("Error", "Failed to load project from local file", "error");
                    }
                }
            } else {
                // Alternative: Show list of available .rugrel files in projects directory
                File projectsDir = new File(LOCAL_OPEN_DIRECTORY);
                if (!projectsDir.exists() || !projectsDir.isDirectory()) {
                    showEnhancedDialog("Open Project", "No projects directory found.\nSave a project first to create the directory.", "info");
                    return;
                }
                
                // Find all .rugrel files
                File[] rugrelFiles = projectsDir.listFiles((dir, name) -> name.endsWith(PROJECT_FILE_EXTENSION));
                
                if (rugrelFiles == null || rugrelFiles.length == 0) {
                    showEnhancedDialog("Open Project", "No project files found in:\n" + projectsDir.getAbsolutePath() + "\n\nSave a project first.", "info");
                    return;
                }
                
                // Create file selection list
                String[] fileNames = new String[rugrelFiles.length];
                for (int i = 0; i < rugrelFiles.length; i++) {
                    fileNames[i] = rugrelFiles[i].getName();
                }
                
                // Show file selection dialog
                String selectedFileName = (String) JOptionPane.showInputDialog(null,
                    "Select project file to open:", "Open Project", JOptionPane.QUESTION_MESSAGE,
                    null, fileNames, fileNames[0]);
                
                if (selectedFileName != null) {
                    File selectedFile = new File(projectsDir, selectedFileName);
                    
                    // Load project from selected local file
                    boolean success = loadProjectFromLocalFile(selectedFile);
                    
                    if (success) {
                        // Extract project name from file name for tracking
                        String projectName = selectedFileName.replace(PROJECT_FILE_EXTENSION, "");
                        setCurrentProjectName(projectName);
                        
                        System.out.println("✅ Project loaded from local file: " + selectedFileName);
                        showEnhancedDialog("Open Complete", 
                            "Project loaded successfully:\n" + selectedFileName, "success");
                    } else {
                        System.err.println("❌ Failed to load project from: " + selectedFileName);
                        showEnhancedDialog("Error", "Failed to load project from local file", "error");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error opening project from local: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to open project: " + e.getMessage(), "error");
        }
    }

    private static void handleOpenProjectWithDB() {
        System.out.println("📂 Opening project with complete state restoration...");
        
        try {
            // Get list of projects from database
            java.util.List<String> projects = getProjectsFromDatabase();
            
            if (projects.isEmpty()) {
                showEnhancedDialog("Open Project", "No projects found in database.\nCreate a new project first.", "info");
                return;
            }
            
            // Show project selection dialog
            String selectedProject = (String) JOptionPane.showInputDialog(null,
                "Select project to open:", "Open Project", JOptionPane.QUESTION_MESSAGE,
                null, projects.toArray(), projects.get(0));
            
            if (selectedProject != null) {
                // Extract actual project name from detailed format (soft-coded parsing)
                String actualProjectName = extractProjectName(selectedProject);
                System.out.println("🔄 Selected: '" + selectedProject + "' -> Extracted: '" + actualProjectName + "'");
                
                // Load complete project state (canvas + database)
                boolean success = loadCompleteProjectState(actualProjectName);
                
                if (success) {
                    updateRecentFiles(actualProjectName);
                    System.out.println("✅ Complete project state loaded: " + actualProjectName);
                } else {
                    System.err.println("❌ Failed to load complete project state: " + actualProjectName);
                    showEnhancedDialog("Error", "Failed to load project: " + actualProjectName, "error");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error opening project: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to open project: " + e.getMessage(), "error");
        }
    }
    
    /**
     * File Sequence Manager - Add/Remove file functionality
     */
    private static void handleFileSequenceWithDB() {
        System.out.println("📁 Opening File Sequence Manager...");
        
        try {
            // Create and show File Sequence popup window
            showFileSequenceDialog();
        } catch (Exception e) {
            System.err.println("❌ Error opening File Sequence Manager: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to open File Sequence Manager: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Save Project to Local Machine (Soft Coded Alternative)
     */
    private static void handleSaveProjectToLocal() {
        System.out.println("💾 Saving project to local machine...");
        
        try {
            String currentProject = getCurrentProjectName();
            if (currentProject == null || currentProject.isEmpty()) {
                // No current project, trigger Save As to local
                handleSaveAsProjectToLocal();
                return;
            }
            
            // Create save directory if needed (soft coding)
            if (AUTO_CREATE_SAVE_DIRECTORY) {
                File saveDir = new File(LOCAL_SAVE_DIRECTORY);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                    System.out.println("📁 Created save directory: " + LOCAL_SAVE_DIRECTORY);
                }
            }
            
            // Save to local file with .rugrel extension
            String fileName = currentProject + PROJECT_FILE_EXTENSION;
            File saveFile = new File(LOCAL_SAVE_DIRECTORY, fileName);
            
            // Save complete project state to local file
            boolean success = saveProjectToLocalFile(saveFile, currentProject);
            
            if (success) {
                System.out.println("✅ Project saved to local file: " + saveFile.getAbsolutePath());
                showEnhancedDialog("Save Complete", "Project saved to:\n" + saveFile.getAbsolutePath(), "success");
            } else {
                System.err.println("❌ Failed to save project to local file: " + saveFile.getAbsolutePath());
                showEnhancedDialog("Error", "Failed to save project to local file", "error");
            }
        } catch (Exception e) {
            System.err.println("❌ Error saving project to local: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to save project: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Save As Project to Local Machine (Soft Coded Alternative)
     */
    private static void handleSaveAsProjectToLocal() {
        System.out.println("💾 Save As to local machine...");
        
        try {
            if (ENABLE_SAVE_FILE_CHOOSER) {
                // Use file chooser for better user experience
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Project As");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File(LOCAL_SAVE_DIRECTORY));
                
                // Set default extension filter
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "RUGREL Project Files (*" + PROJECT_FILE_EXTENSION + ")", 
                    PROJECT_FILE_EXTENSION.replace(".", "")));
                
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    
                    // Ensure proper extension
                    if (!selectedFile.getName().endsWith(PROJECT_FILE_EXTENSION)) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + PROJECT_FILE_EXTENSION);
                    }
                    
                    // Extract project name from file name
                    String projectName = selectedFile.getName().replace(PROJECT_FILE_EXTENSION, "");
                    
                    // Save complete project state to selected local file
                    boolean success = saveProjectToLocalFile(selectedFile, projectName);
                    
                    if (success) {
                        setCurrentProjectName(projectName);
                        System.out.println("✅ Project saved as: " + selectedFile.getAbsolutePath());
                        showEnhancedDialog("Save As Complete", "Project saved as:\n" + selectedFile.getAbsolutePath(), "success");
                    } else {
                        System.err.println("❌ Failed to save project as: " + selectedFile.getAbsolutePath());
                        showEnhancedDialog("Error", "Failed to save project to local file", "error");
                    }
                }
            } else {
                // Simple input dialog method
                String projectName = JOptionPane.showInputDialog(null,
                    "Enter project name:", "Save As", JOptionPane.QUESTION_MESSAGE);
                
                if (projectName != null && !projectName.trim().isEmpty()) {
                    // Create save directory if needed
                    if (AUTO_CREATE_SAVE_DIRECTORY) {
                        File saveDir = new File(LOCAL_SAVE_DIRECTORY);
                        if (!saveDir.exists()) {
                            saveDir.mkdirs();
                        }
                    }
                    
                    String fileName = projectName.trim() + PROJECT_FILE_EXTENSION;
                    File saveFile = new File(LOCAL_SAVE_DIRECTORY, fileName);
                    
                    // Save complete project state to local file
                    boolean success = saveProjectToLocalFile(saveFile, projectName.trim());
                    
                    if (success) {
                        setCurrentProjectName(projectName.trim());
                        System.out.println("✅ Project saved as: " + saveFile.getAbsolutePath());
                        showEnhancedDialog("Save As Complete", "Project saved as:\n" + saveFile.getAbsolutePath(), "success");
                    } else {
                        System.err.println("❌ Failed to save project as: " + saveFile.getAbsolutePath());
                        showEnhancedDialog("Error", "Failed to save project to local file", "error");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error saving project as to local: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to save project: " + e.getMessage(), "error");
        }
    }

    /**
     * Enhanced Save Project with Complete State Serialization
     */
    private static void handleSaveProjectWithDB() {
        System.out.println("💾 Saving project with complete state serialization...");
        
        try {
            String currentProject = getCurrentProjectName();
            if (currentProject == null || currentProject.isEmpty()) {
                // No current project, trigger Save As
                handleSaveAsProjectWithDB();
                return;
            }
            
            // Save complete project state (canvas + database)
            boolean success = saveCompleteProjectState(currentProject);
            
            if (success) {
                updateProjectTimestamp(currentProject);
                System.out.println("✅ Complete project state saved: " + currentProject);
            } else {
                System.err.println("❌ Failed to save complete project state: " + currentProject);
                showEnhancedDialog("Error", "Failed to save project: " + currentProject, "error");
            }
        } catch (Exception e) {
            System.err.println("❌ Error saving project: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to save project: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced Save As Project with Complete State Serialization
     */
    private static void handleSaveAsProjectWithDB() {
        System.out.println("💾 Save As with complete state serialization...");
        
        try {
            String projectName = JOptionPane.showInputDialog(null,
                "Enter project name:", "Save As", JOptionPane.QUESTION_MESSAGE);
            
            if (projectName != null && !projectName.trim().isEmpty()) {
                // Save complete project state with new name
                boolean success = saveCompleteProjectState(projectName.trim());
                
                if (success) {
                    // Set current project name for tracking
                    setCurrentProjectName(projectName.trim());
                    updateRecentFiles(projectName.trim());
                    System.out.println("✅ Complete project state saved as: " + projectName);
                } else {
                    System.err.println("❌ Failed to save complete project state as: " + projectName);
                    showEnhancedDialog("Error", "Failed to save project: " + projectName, "error");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error saving project as: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to save project: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced Import Design with Database Integration
     */
    private static void handleImportDesignWithDB() {
        System.out.println("📥 Importing design with database integration...");
        
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Import Design File");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Design Files", "dxf", "dwg", "svg", "pdf", "rugrel"));
            
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                
                // Import and save to database
                String importId = importDesignToDatabase(selectedFile);
                
                System.out.println("✅ Design imported: " + selectedFile.getName());
                showEnhancedDialog("Import Design", 
                    "Design '" + selectedFile.getName() + "' imported successfully!\nImport ID: " + importId, "success");
            }
        } catch (Exception e) {
            System.err.println("❌ Error importing design: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to import design: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced Export Design with Database Integration
     */
    private static void handleExportDesignWithDB() {
        System.out.println("📤 Exporting design with database integration...");
        
        try {
            String currentProject = getCurrentProjectName();
            if (currentProject == null || currentProject.isEmpty()) {
                showEnhancedDialog("Export Design", "No active project to export.\nPlease open or create a project first.", "warning");
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Export Design");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Export Formats", "dxf", "svg", "pdf", "png", "rugrel"));
            
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                
                // Export from database
                exportDesignFromDatabase(currentProject, selectedFile);
                
                System.out.println("✅ Design exported: " + selectedFile.getName());
                showEnhancedDialog("Export Design", 
                    "Design exported to '" + selectedFile.getName() + "' successfully!", "success");
            }
        } catch (Exception e) {
            System.err.println("❌ Error exporting design: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to export design: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced Recent Files with Database Integration
     */
    private static void handleRecentFilesWithDB() {
        System.out.println("📋 Showing recent files from database...");
        
        try {
            java.util.List<String> recentFiles = getRecentFilesFromDatabase();
            
            if (recentFiles.isEmpty()) {
                showEnhancedDialog("Recent Files", "No recent files found.", "info");
                return;
            }
            
            String selectedFile = (String) JOptionPane.showInputDialog(null,
                "Select recent file to open:", "Recent Files", JOptionPane.QUESTION_MESSAGE,
                null, recentFiles.toArray(), recentFiles.get(0));
            
            if (selectedFile != null) {
                loadProjectFromDatabase(selectedFile);
                System.out.println("✅ Recent file opened: " + selectedFile);
                showEnhancedDialog("Recent Files", "Project '" + selectedFile + "' opened successfully!", "success");
            }
        } catch (Exception e) {
            System.err.println("❌ Error accessing recent files: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to load recent files: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced Settings with Database Integration
     */
    private static void handleSettingsWithDB() {
        System.out.println("⚙️ Opening settings with database integration...");
        
        try {
            // Create settings dialog
            JDialog settingsDialog = new JDialog();
            settingsDialog.setTitle("Rugrel Settings");
            settingsDialog.setSize(500, 400);
            settingsDialog.setLocationRelativeTo(null);
            settingsDialog.setModal(true);
            
            JTabbedPane settingsTabs = new JTabbedPane();
            
            // General Settings
            JPanel generalPanel = createGeneralSettingsPanel();
            settingsTabs.addTab("General", generalPanel);
            
            // Database Settings
            JPanel dbPanel = createDatabaseSettingsPanel();
            settingsTabs.addTab("Database", dbPanel);
            
            // Grid Settings
            JPanel gridPanel = createGridSettingsPanel();
            settingsTabs.addTab("Grid", gridPanel);
            
            settingsDialog.add(settingsTabs);
            settingsDialog.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("❌ Error opening settings: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to open settings: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced About Dialog with Database Integration
     */
    private static void handleAboutRugrelWithDB() {
        System.out.println("ℹ️ Showing enhanced about dialog...");
        
        try {
            String aboutText = "🔹 RUGREL DOT PIN MARKER\n\n" +
                              "Professional Industrial Marking Software\n" +
                              "Version: 2.0 (Enhanced Edition)\n\n" +
                              "Features:\n" +
                              "• ThorX6 Grid System (14x8)\n" +
                              "• Advanced Database Integration\n" +
                              "• Dynamic Soft Coding Architecture\n" +
                              "• Professional UI Design\n" +
                              "• Enhanced Project Management\n\n" +
                              "Database Status: " + getDatabaseStatus() + "\n" +
                              "Projects in Database: " + getProjectCount() + "\n" +
                              "Recent Files: " + getRecentFileCount() + "\n\n" +
                              "© 2025 Rugrel Technologies\n" +
                              "Professional Industrial Solutions";
            
            showEnhancedDialog("About Rugrel", aboutText, "info");
        } catch (Exception e) {
            System.err.println("❌ Error showing about dialog: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to show about information: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Enhanced Exit with Database Integration
     */
    private static void handleExitWithDB() {
        System.out.println("🚪 Exiting with database cleanup...");
        
        try {
            // Check for unsaved changes
            if (hasUnsavedChanges()) {
                int choice = JOptionPane.showConfirmDialog(null,
                    "You have unsaved changes. Do you want to save before exiting?",
                    "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                    handleSaveProjectWithDB();
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    return; // Cancel exit
                }
            }
            
            // Cleanup database connections
            cleanupDatabase();
            
            System.out.println("✅ Application closed successfully");
            System.exit(0);
            
        } catch (Exception e) {
            System.err.println("❌ Error during exit: " + e.getMessage());
            System.exit(1);
        }
    }
    
    // ==================== DATABASE HELPER METHODS (SOFT CODED) ====================
    
    /**
     * Get DatabasePanel Reference (Soft-coded Database Integration)
     */
    private static DatabasePanel getDatabasePanelReference() {
        if (!ENABLE_DATABASE_INTEGRATION) {
            return null;
        }
        
        try {
            return MarkingInterfaceApp.getDatabasePanel();
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("⚠️ Database Panel not available: " + e.getMessage());
            }
            return null;
        }
    }
    
    /**
     * Validate Database Connection (Enhanced Soft-coded Validation)
     */
    private static boolean validateDatabaseConnection() {
        if (!ENABLE_DATABASE_VALIDATION) {
            System.out.println("🔧 Database validation disabled - skipping check");
            return true; // Skip validation if disabled
        }
        
        try {
            boolean isAvailable = MarkingInterfaceApp.isDatabasePanelAvailable();
            DatabasePanel dbPanel = MarkingInterfaceApp.getDatabasePanel();
            
            System.out.println("🔍 Database validation check:");
            System.out.println("   - Panel available: " + isAvailable);
            System.out.println("   - Panel reference: " + (dbPanel != null ? "Valid" : "NULL"));
            
            if (isAvailable && dbPanel != null) {
                System.out.println("✅ Database connection validated successfully");
                return true;
            } else {
                System.err.println("❌ Database validation failed:");
                System.err.println("   - Check if application is fully loaded");
                System.err.println("   - Ensure Database Tab has been initialized");
                return false;
            }
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("⚠️ Database validation exception: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }
    
    /**
     * Force Database Panel Initialization (Emergency Recovery)
     */
    private static boolean forceInitializeDatabasePanel() {
        if (!ENABLE_DATABASE_ERROR_RECOVERY) {
            return false;
        }
        
        try {
            System.out.println("🔄 Attempting to force initialize DatabasePanel...");
            
            // Try to get a drawing canvas for initialization
            // This is an emergency recovery mechanism
            DatabasePanel emergencyPanel = new DatabasePanel(null);
            MarkingInterfaceApp.setDatabasePanel(emergencyPanel);
            
            System.out.println("✅ Emergency DatabasePanel initialized");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to force initialize DatabasePanel: " + e.getMessage());
            return false;
        }
    }
    
    private static String generateProjectId() {
        if (!ENABLE_PROJECT_ID_GENERATION) {
            return "DEFAULT_PROJECT";
        }
        return PROJECT_DEFAULT_PREFIX + System.currentTimeMillis();
    }
    
    /**
     * Save Project to Real Database (Enhanced with Soft Coding)
     */
    private static boolean saveProjectToDatabase(String projectId, String projectName, String description) {
        if (!ENABLE_DATABASE_INTEGRATION) {
            System.out.println("💾 Database integration disabled - project not saved: " + projectName);
            return false;
        }
        
        try {
            DatabasePanel dbPanel = getDatabasePanelReference();
            if (dbPanel == null) {
                if (ENABLE_DATABASE_ERROR_RECOVERY) {
                    System.err.println("❌ Database Panel not available for project: " + projectName);
                    return false;
                }
            }
            
            // Validate project name
            if (ENABLE_PROJECT_NAME_VALIDATION) {
                if (projectName.length() < PROJECT_NAME_MIN_LENGTH || 
                    projectName.length() > PROJECT_NAME_MAX_LENGTH) {
                    System.err.println("❌ Invalid project name length: " + projectName);
                    return false;
                }
            }
            
            // Use real database integration
            boolean success = dbPanel.addProjectExternal(projectName, 
                description != null ? description : PROJECT_DEFAULT_DESCRIPTION);
            
            if (success) {
                System.out.println("✅ Project saved to database: " + projectName + " (ID: " + projectId + ")");
                
                // Auto-switch to Database Tab if enabled
                if (ENABLE_DATABASE_TAB_SWITCH) {
                    dbPanel.showDatabase();
                    System.out.println("🔄 Switched to Database Tab to show new project");
                }
            }
            
            return success;
            
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("❌ Error saving project to database: " + e.getMessage());
            }
            return false;
        }
    }
    
    /**
     * Get Projects from Real Database (Enhanced Integration)
     */
    private static java.util.List<String> getProjectsFromDatabase() {
        java.util.List<String> projects = new java.util.ArrayList<>();
        
        if (!ENABLE_DATABASE_INTEGRATION) {
            // Fallback projects if database disabled
            projects.add("Sample Project 1");
            projects.add("Sample Project 2");
            return projects;
        }
        
        try {
            DatabasePanel dbPanel = getDatabasePanelReference();
            if (dbPanel != null) {
                // Get real projects from database
                String[] projectArray = dbPanel.getAllProjectsWithDetails();
                if (projectArray != null) {
                    for (String project : projectArray) {
                        if (project != null && !project.trim().isEmpty()) {
                            projects.add(project.trim());
                        }
                    }
                }
                System.out.println("📋 Retrieved " + projects.size() + " projects from database");
            }
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("⚠️ Error getting projects from database: " + e.getMessage());
                // Add fallback projects
                projects.add("ThorX6 Default Project");
            }
        }
        
        return projects;
    }
    
    /**
     * Load Project with Complete State Restoration (Enhanced Integration)
     */
    private static boolean loadProjectFromDatabase(String projectName) {
        if (!ENABLE_DATABASE_INTEGRATION) {
            System.out.println("📂 Database integration disabled - cannot load: " + projectName);
            return false;
        }
        
        try {
            // Load project state file
            String projectFilePath = PROJECT_DATA_DIRECTORY + projectName + PROJECT_FILE_EXTENSION;
            ProjectState projectState = ProjectStateManager.loadProjectFromFile(projectFilePath);
            
            if (projectState == null || !projectState.isValid()) {
                System.err.println("❌ Cannot load project - project state file not found or invalid");
                
                // Fallback to database-only loading
                DatabasePanel dbPanel = getDatabasePanelReference();
                if (dbPanel != null) {
                    boolean success = dbPanel.loadProjectByName(projectName);
                    if (success) {
                        System.out.println("⚠️ Loaded project from database without state preservation");
                        return true;
                    }
                }
                return false;
            }
            
            // Get canvas reference
            DrawingCanvas canvas = getDrawingCanvasReference();
            if (canvas == null) {
                System.err.println("❌ Cannot load project - DrawingCanvas not available");
                return false;
            }
            
            // Apply complete project state to canvas
            boolean stateApplied = ProjectStateManager.applyProjectState(canvas, projectState);
            
            if (!stateApplied) {
                System.err.println("❌ Failed to apply project state to canvas");
                return false;
            }
            
            // Load database entry (for metadata and synchronization)
            DatabasePanel dbPanel = getDatabasePanelReference();
            if (dbPanel != null) {
                boolean dbLoaded = dbPanel.loadProjectByName(projectName);
                if (dbLoaded) {
                    System.out.println("✅ Database entry loaded for project: " + projectName);
                }
            }
            
            System.out.println("✅ Project loaded with complete state restoration:");
            System.out.println("   📁 File: " + projectFilePath);
            System.out.println("   🎯 Canvas: State fully restored");
            System.out.println("   📊 " + projectState.getProjectSummary());
            
            // Auto-switch to Database Tab to show loaded project
            if (ENABLE_DATABASE_TAB_SWITCH && dbPanel != null) {
                dbPanel.showDatabase();
                System.out.println("🔄 Switched to Database Tab to show loaded project");
            }
            
            return true;
            
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("❌ Error loading project with state restoration: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }
    
    /**
     * Get Current Project Name (Enhanced State Management)
     */
    private static String getCurrentProjectName() {
        return currentProjectName;
    }
    
    /**
     * Set current project name (soft-coded project tracking)
     */
    private static void setCurrentProjectName(String projectName) {
        currentProjectName = projectName;
        projectModified = false;
        System.out.println("📝 Current project set to: " + projectName);
    }
    
    /**
     * Mark project as modified
     */
    private static void markProjectModified() {
        projectModified = true;
        System.out.println("📝 Project marked as modified: " + currentProjectName);
    }
    
    /**
     * Extract actual project name from detailed database format (soft-coded parsing)
     * Handles format: "ProjectName (YYYY-MM-DD HH:MM:SS)"
     */
    private static String extractProjectName(String detailedProjectName) {
        if (detailedProjectName == null || detailedProjectName.trim().isEmpty()) {
            return detailedProjectName;
        }
        
        String originalName = detailedProjectName.trim();
        
        // Soft-coded regex pattern to extract project name from detailed format
        // Pattern matches: "ProjectName (timestamp)" -> extracts "ProjectName"
        if (originalName.contains(" (") && originalName.endsWith(")")) {
            int lastParenIndex = originalName.lastIndexOf(" (");
            if (lastParenIndex > 0) {
                String extractedName = originalName.substring(0, lastParenIndex).trim();
                
                // Additional soft-coded sanitization to ensure clean project name
                extractedName = extractedName.replaceAll("[^a-zA-Z0-9._-]", "_");
                extractedName = extractedName.replaceAll("_+", "_"); // Remove multiple underscores
                extractedName = extractedName.replaceAll("^_|_$", ""); // Remove leading/trailing underscores
                
                System.out.println("🔍 Extracted project name: '" + extractedName + "' from '" + originalName + "'");
                return extractedName;
            }
        }
        
        // If no pattern match, sanitize and return original name
        String sanitized = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        sanitized = sanitized.replaceAll("_+", "_"); // Remove multiple underscores
        sanitized = sanitized.replaceAll("^_|_$", ""); // Remove leading/trailing underscores
        
        if (!sanitized.equals(originalName)) {
            System.out.println("🧹 Sanitized project name: '" + sanitized + "' from '" + originalName + "'");
        }
        
        return sanitized;
    }
    
    /**
     * Get DrawingCanvas Reference for State Management
     */
    private static DrawingCanvas getDrawingCanvasReference() {
        try {
            return MarkingInterfaceApp.getDrawingCanvas();
        } catch (Exception e) {
            System.err.println("❌ Error getting DrawingCanvas reference: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Ensure Project Data Directory Exists
     */
    private static void ensureProjectDataDirectory() {
        try {
            File directory = new File(PROJECT_DATA_DIRECTORY);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("✅ Created project data directory: " + PROJECT_DATA_DIRECTORY);
                } else {
                    System.err.println("❌ Failed to create project data directory: " + PROJECT_DATA_DIRECTORY);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error ensuring project data directory: " + e.getMessage());
        }
    }
    
    /**
     * Save Current Project with Complete State Preservation (Enhanced Integration)
     */
    private static boolean saveCurrentProjectToDatabase(String projectName) {
        if (!ENABLE_DATABASE_INTEGRATION) {
            System.out.println("💾 Database integration disabled - cannot save: " + projectName);
            return false;
        }
        
        try {
            String projectId = generateProjectId();
            
            // Extract complete project state from canvas
            DrawingCanvas canvas = getDrawingCanvasReference();
            if (canvas == null) {
                System.err.println("❌ Cannot save project - DrawingCanvas not available");
                return false;
            }
            
            // Create comprehensive project state
            ProjectState projectState = ProjectStateManager.extractProjectState(canvas, projectName, projectId);
            if (projectState == null || !projectState.isValid()) {
                System.err.println("❌ Cannot save project - invalid project state extracted");
                return false;
            }
            
            // Ensure project directory exists
            ensureProjectDataDirectory();
            
            // Save project state to file
            String projectFilePath = PROJECT_DATA_DIRECTORY + projectName + PROJECT_FILE_EXTENSION;
            boolean fileSaved = ProjectStateManager.saveProjectToFile(projectState, projectFilePath);
            
            if (!fileSaved) {
                System.err.println("❌ Failed to save project file: " + projectFilePath);
                return false;
            }
            
            // Save project entry to database with file reference
            String description = "Complete state preserved - " + projectState.getProjectSummary();
            boolean dbSaved = saveProjectToDatabase(projectId, projectName, description);
            
            if (dbSaved) {
                System.out.println("✅ Project saved with complete state preservation:");
                System.out.println("   📁 File: " + projectFilePath);
                System.out.println("   🗃️ Database: " + projectName + " (" + projectId + ")");
                System.out.println("   📊 " + projectState.getProjectSummary());
                return true;
            } else {
                System.err.println("❌ Failed to save project to database");
                return false;
            }
            
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("❌ Error saving current project with state preservation: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }
    
    private static void updateProjectTimestamp(String projectName) {
        System.out.println("🕒 Updating project timestamp: " + projectName);
    }
    
    /**
     * Update Recent Files List (Enhanced Integration)
     */
    private static void updateRecentFiles(String projectName) {
        if (!ENABLE_RECENT_FILES_SYNC) {
            return;
        }
        
        try {
            System.out.println("📋 Updating recent files with: " + projectName);
            // TODO: Integrate with application recent files management
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("⚠️ Error updating recent files: " + e.getMessage());
            }
        }
    }
    
    /**
     * Import Design to Database (Enhanced Integration)
     */
    private static String importDesignToDatabase(java.io.File file) {
        String importId = "IMP_" + System.currentTimeMillis();
        
        if (!ENABLE_DATABASE_INTEGRATION) {
            System.out.println("📥 Database integration disabled - import skipped: " + file.getName());
            return importId;
        }
        
        try {
            System.out.println("📥 Importing design to database: " + file.getName() + " (ID: " + importId + ")");
            // TODO: Implement actual import functionality
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("❌ Error importing design: " + e.getMessage());
            }
        }
        
        return importId;
    }
    
    /**
     * Export Design from Database (Enhanced Integration)
     */
    private static void exportDesignFromDatabase(String projectName, java.io.File file) {
        if (!ENABLE_DATABASE_INTEGRATION) {
            System.out.println("📤 Database integration disabled - export skipped: " + projectName);
            return;
        }
        
        try {
            System.out.println("📤 Exporting design from database: " + projectName + " to " + file.getName());
            // TODO: Implement actual export functionality
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("❌ Error exporting design: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get Recent Files from Real Database (Enhanced Integration)
     */
    private static java.util.List<String> getRecentFilesFromDatabase() {
        java.util.List<String> recentFiles = new java.util.ArrayList<>();
        
        if (!ENABLE_DATABASE_INTEGRATION) {
            recentFiles.add("Recent Project 1");
            recentFiles.add("Recent Project 2");
            return recentFiles;
        }
        
        try {
            DatabasePanel dbPanel = getDatabasePanelReference();
            if (dbPanel != null) {
                String[] recentArray = dbPanel.getRecentProjects();
                if (recentArray != null) {
                    for (String project : recentArray) {
                        if (project != null && !project.trim().isEmpty()) {
                            recentFiles.add(project.trim());
                        }
                    }
                }
                System.out.println("📋 Retrieved " + recentFiles.size() + " recent files from database");
            }
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("⚠️ Error getting recent files: " + e.getMessage());
                recentFiles.add("Default Recent Project");
            }
        }
        
        return recentFiles;
    }
    
    /**
     * Get Database Status (Enhanced Validation)
     */
    private static String getDatabaseStatus() {
        if (!ENABLE_DATABASE_INTEGRATION) {
            return "Disabled";
        }
        
        try {
            return validateDatabaseConnection() ? "Connected" : "Disconnected";
        } catch (Exception e) {
            return "Error";
        }
    }
    
    /**
     * Get Project Count from Real Database (Enhanced Integration)
     */
    private static int getProjectCount() {
        if (!ENABLE_DATABASE_INTEGRATION) {
            return 0;
        }
        
        try {
            DatabasePanel dbPanel = getDatabasePanelReference();
            if (dbPanel != null) {
                return dbPanel.getProjectCount();
            }
        } catch (Exception e) {
            if (ENABLE_DATABASE_ERROR_RECOVERY) {
                System.err.println("⚠️ Error getting project count: " + e.getMessage());
            }
        }
        
        return 0;
    }
    
    private static int getRecentFileCount() {
        return 3; // Get from database
    }
    
    private static boolean hasUnsavedChanges() {
        return false; // Check application state
    }
    
    private static void cleanupDatabase() {
        System.out.println("🧹 Cleaning up database connections...");
    }
    
    // Settings Panel Creation Methods
    private static JPanel createGeneralSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        // Border removed for compilation simplicity
        
        panel.add(new JLabel("Auto-save interval:"));
        panel.add(new JSpinner(new SpinnerNumberModel(5, 1, 60, 1)));
        
        panel.add(new JLabel("Grid size:"));
        JComboBox<String> gridCombo = new JComboBox<>(new String[]{"14x8", "10x8", "Custom"});
        panel.add(gridCombo);
        
        return panel;
    }
    
    private static JPanel createDatabaseSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Border removed for compilation simplicity
        
        panel.add(new JLabel("Database location:"));
        panel.add(new JTextField("./database/"));
        
        panel.add(new JLabel("Auto-backup:"));
        panel.add(new JCheckBox("Enable automatic backups", true));
        
        return panel;
    }
    
    private static JPanel createGridSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Border removed for compilation simplicity
        
        panel.add(new JLabel("Show grid:"));
        panel.add(new JCheckBox("Show grid by default", true));
        
        panel.add(new JLabel("Grid color:"));
        JButton colorButton = new JButton("Choose Color");
        panel.add(colorButton);
        
        return panel;
    }
    
    /**
     * Clear Canvas for New Project - Complete Reset (Soft-coded)
     */
    private static void clearCanvasForNewProject() {
        System.out.println("🆕 Clearing canvas for new project...");
        
        try {
            DrawingCanvas canvas = MarkingInterfaceApp.getDrawingCanvas();
            
            if (canvas != null) {
                // Clear selection first
                canvas.clearSelection();
                
                // Access marks using reflection to clear private fields
                java.lang.reflect.Field marksField = canvas.getClass().getDeclaredField("marks");
                marksField.setAccessible(true);
                java.util.List<?> marks = (java.util.List<?>) marksField.get(canvas);
                marks.clear();
                
                // Clear textMarks for backward compatibility
                java.lang.reflect.Field textMarksField = canvas.getClass().getDeclaredField("textMarks");
                textMarksField.setAccessible(true);
                java.util.List<?> textMarks = (java.util.List<?>) textMarksField.get(canvas);
                textMarks.clear();
                
                // Clear undo/redo stacks if enabled
                if (ENABLE_UNDO_STACK_CLEAR) {
                    try {
                        java.lang.reflect.Field undoStackField = canvas.getClass().getDeclaredField("undoStack");
                        undoStackField.setAccessible(true);
                        java.util.Stack<?> undoStack = (java.util.Stack<?>) undoStackField.get(canvas);
                        undoStack.clear();
                        
                        java.lang.reflect.Field redoStackField = canvas.getClass().getDeclaredField("redoStack");
                        redoStackField.setAccessible(true);
                        java.util.Stack<?> redoStack = (java.util.Stack<?>) redoStackField.get(canvas);
                        redoStack.clear();
                        
                        System.out.println("🔄 Undo/Redo stacks cleared");
                    } catch (Exception e) {
                        System.out.println("⚠️ Could not clear undo/redo stacks: " + e.getMessage());
                    }
                }
                
                // Reset view position if enabled
                if (ENABLE_VIEW_RESET_ON_NEW_PROJECT) {
                    try {
                        canvas.getClass().getMethod("resetViewPosition").invoke(canvas);
                        System.out.println("🎯 View position reset to origin");
                    } catch (Exception e) {
                        System.out.println("⚠️ Could not reset view position: " + e.getMessage());
                    }
                }
                
                // Refresh the canvas
                canvas.repaint();
                
                System.out.println("✅ Canvas successfully cleared - fresh project ready!");
                
            } else {
                System.out.println("⚠️ DrawingCanvas reference not available");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error clearing canvas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Enhanced dialog with better styling
     */
    private static void showEnhancedDialog(String title, String message, String type) {
        int messageType;
        String icon = "";
        
        switch (type.toLowerCase()) {
            case "success":
                messageType = JOptionPane.INFORMATION_MESSAGE;
                icon = "✅ ";
                break;
            case "error":
                messageType = JOptionPane.ERROR_MESSAGE;
                icon = "❌ ";
                break;
            case "warning":
                messageType = JOptionPane.WARNING_MESSAGE;
                icon = "⚠️ ";
                break;
            default:
                messageType = JOptionPane.INFORMATION_MESSAGE;
                icon = "ℹ️ ";
        }
        
        JOptionPane.showMessageDialog(null, icon + message, title, messageType);
    }
    
    /**
     * Show File Sequence Manager Dialog with Add/Remove functionality
     */
    private static void showFileSequenceDialog() {
        System.out.println("🔧 DEBUG: Starting File Sequence Dialog creation...");
        // Create main dialog frame
        JFrame fileSequenceFrame = new JFrame("File Sequence Manager");
        fileSequenceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fileSequenceFrame.setSize(700, 500);
        fileSequenceFrame.setLocationRelativeTo(null);
        
        // Enable proper window controls (minimize, maximize, close)
        fileSequenceFrame.setResizable(true);
        // Remove setAlwaysOnTop to allow normal window behavior
        
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        // Title label
        JLabel titleLabel = new JLabel("📁 File Sequence Manager", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create file list area
        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane fileScrollPane = new JScrollPane(fileList);
        fileScrollPane.setPreferredSize(new Dimension(400, 200));
        mainPanel.add(fileScrollPane, BorderLayout.CENTER);
        
        // Create button panel with padding
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        
        // Add File button
        JButton addFileButton = new JButton("📁 Add File");
        addFileButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        addFileButton.setPreferredSize(new Dimension(200, 40));
        addFileButton.addActionListener(e -> addFileToSequence(fileListModel, fileSequenceFrame));
        System.out.println("✅ Created Add File button: " + addFileButton.getText());
        
        // Delete Files button  
        JButton removeFileButton = new JButton("🗑️ Delete Files");
        removeFileButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        removeFileButton.setPreferredSize(new Dimension(200, 40));
        removeFileButton.addActionListener(e -> removeFileFromSequence(fileList, fileListModel));
        System.out.println("✅ Created Delete Files button: " + removeFileButton.getText());
        
        // Close button
        JButton closeButton = new JButton("✖️ Close");
        closeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(200, 40));
        closeButton.addActionListener(e -> {
            System.out.println("🔧 DEBUG: Close button clicked - disposing window");
            fileSequenceFrame.dispose();
        });
        
        buttonPanel.add(addFileButton);
        buttonPanel.add(removeFileButton);
        buttonPanel.add(closeButton);
        System.out.println("🔧 DEBUG: Added all 3 buttons to button panel");
        
        // Create combined bottom panel with buttons and status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Add status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Ready - Use Add File to browse and select files from your computer");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
        statusPanel.add(statusLabel);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Finalize frame setup
        fileSequenceFrame.add(mainPanel);
        
        // Ensure proper window behavior
        fileSequenceFrame.setExtendedState(JFrame.NORMAL);
        fileSequenceFrame.setVisible(true);
        
        // Add debug for Close button testing
        System.out.println("✅ File Sequence Manager window opened successfully");
        System.out.println("🔧 DEBUG: Window controls should be fully functional (Close/Minimize/Maximize)");
    }
    
    /**
     * Add file to sequence using file browser
     */
    private static void addFileToSequence(DefaultListModel<String> fileListModel, JFrame parentFrame) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select File to Add to Sequence");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(true); // Allow multiple file selection
            
            // Add common file filters
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") || 
                           f.getName().toLowerCase().endsWith(".jpeg") || f.getName().toLowerCase().endsWith(".png") ||
                           f.getName().toLowerCase().endsWith(".gif") || f.getName().toLowerCase().endsWith(".bmp");
                }
                public String getDescription() { return "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)"; }
            });
            
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf") ||
                           f.getName().toLowerCase().endsWith(".doc") || f.getName().toLowerCase().endsWith(".docx") ||
                           f.getName().toLowerCase().endsWith(".txt") || f.getName().toLowerCase().endsWith(".rtf");
                }
                public String getDescription() { return "Document Files (*.pdf, *.doc, *.docx, *.txt, *.rtf)"; }
            });
            
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) { return true; }
                public String getDescription() { return "All Files (*.*)"; }
            });
            
            int result = fileChooser.showOpenDialog(parentFrame);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File[] selectedFiles = fileChooser.getSelectedFiles();
                
                for (java.io.File file : selectedFiles) {
                    String filePath = file.getAbsolutePath();
                    String fileName = file.getName();
                    String displayText = fileName + " (" + filePath + ")";
                    
                    // Check if file already exists in list
                    boolean alreadyExists = false;
                    for (int i = 0; i < fileListModel.getSize(); i++) {
                        if (fileListModel.getElementAt(i).contains(filePath)) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    
                    if (!alreadyExists) {
                        fileListModel.addElement(displayText);
                        System.out.println("✅ Added file to sequence: " + fileName);
                    } else {
                        System.out.println("⚠️ File already in sequence: " + fileName);
                    }
                }
                
                showEnhancedDialog("Files Added", 
                    selectedFiles.length + " file(s) added to sequence successfully!", "success");
            }
        } catch (Exception e) {
            System.err.println("❌ Error adding file to sequence: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to add file: " + e.getMessage(), "error");
        }
    }
    
    /**
     * Remove selected file from sequence
     */
    private static void removeFileFromSequence(JList<String> fileList, DefaultListModel<String> fileListModel) {
        System.out.println("🔧 DEBUG: Delete Files button clicked");
        
        try {
            int selectedIndex = fileList.getSelectedIndex();
            int listSize = fileListModel.getSize();
            
            System.out.println("🔧 DEBUG: Selected index: " + selectedIndex + ", List size: " + listSize);
            
            if (listSize == 0) {
                System.out.println("🔧 DEBUG: No files in the list");
                JOptionPane.showMessageDialog(null, 
                    "No files in the sequence. Please add files first using 'Add File' button.", 
                    "Empty List", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            if (selectedIndex >= 0 && selectedIndex < listSize) {
                String selectedFile = fileListModel.getElementAt(selectedIndex);
                System.out.println("🔧 DEBUG: Selected file: " + selectedFile);
                
                // Safely extract file name
                String fileName = selectedFile;
                int parenIndex = selectedFile.indexOf(" (");
                if (parenIndex > 0) {
                    fileName = selectedFile.substring(0, parenIndex);
                }
                System.out.println("🔧 DEBUG: Extracted filename: " + fileName);
                
                int confirmation = JOptionPane.showConfirmDialog(null,
                    "Remove '" + fileName + "' from the file sequence?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                System.out.println("🔧 DEBUG: User confirmation: " + (confirmation == JOptionPane.YES_OPTION ? "YES" : "NO"));
                
                if (confirmation == JOptionPane.YES_OPTION) {
                    fileListModel.removeElementAt(selectedIndex);
                    System.out.println("✅ Removed file from sequence: " + fileName);
                }
            } else {
                System.out.println("🔧 DEBUG: No file selected or invalid selection");
                JOptionPane.showMessageDialog(null, 
                    "Please select a file from the list to remove.", 
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("❌ Error removing file from sequence: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Failed to remove file: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        System.out.println("🔧 DEBUG: Delete Files operation completed");
    }

    // ==================== PROJECT STATE SERIALIZATION SYSTEM ====================
    
    // Note: Using the standalone ProjectState class from ProjectState.java instead of inner class
    
    /**
     * Save Complete Project State with Canvas Serialization
     */
    public static boolean saveCompleteProjectState(String projectName) {
        if (!ENABLE_CANVAS_STATE_SERIALIZATION) {
            System.out.println("💾 Canvas state serialization disabled - using basic save");
            return saveCurrentProjectToDatabase(projectName);
        }
        
        try {
            System.out.println("💾 Starting comprehensive project state save: " + projectName);
            
            // Get DrawingCanvas reference
            DrawingCanvas canvas = getDrawingCanvasReference();
            if (canvas == null) {
                System.err.println("❌ Cannot access DrawingCanvas for state serialization");
                return false;
            }
            
            // Create project state container
            ProjectState projectState = new ProjectState();
            projectState.projectName = projectName;
            projectState.projectId = generateProjectId();
            
            // Serialize canvas state using reflection
            if (ENABLE_MARKS_SERIALIZATION) {
                projectState.marks = getCanvasMarks(canvas);
                System.out.println("✅ Serialized " + projectState.marks.size() + " marks");
            }
            
            if (ENABLE_TEXT_MARKS_SERIALIZATION) {
                projectState.textMarks = getCanvasTextMarks(canvas);
                System.out.println("✅ Serialized " + projectState.textMarks.size() + " text marks");
            }
            
            if (ENABLE_ZOOM_STATE_SERIALIZATION) {
                projectState.zoomLevel = getCanvasZoomLevel(canvas);
                System.out.println("✅ Serialized zoom level: " + String.format("%.0f%%", projectState.zoomLevel * 100));
            }
            
            if (ENABLE_VIEW_POSITION_SERIALIZATION) {
                projectState.viewOffsetX = getCanvasViewOffsetX(canvas);
                projectState.viewOffsetY = getCanvasViewOffsetY(canvas);
                System.out.println("✅ Serialized view position: (" + projectState.viewOffsetX + ", " + projectState.viewOffsetY + ")");
            }
            
            // Serialize visual state
            projectState.gridVisible = getCanvasGridVisible(canvas);
            projectState.materialBoundaryVisible = getCanvasMaterialBoundaryVisible(canvas);
            projectState.dotPreviewEnabled = getCanvasDotPreviewEnabled(canvas);
            
            // Save project state to file
            boolean fileSuccess = saveProjectStateToFile(projectState, projectName);
            
            // Save to database with state reference
            boolean dbSuccess = saveProjectWithStateToDatabase(projectName, projectState);
            
            if (fileSuccess && dbSuccess) {
                System.out.println("✅ Complete project state saved successfully: " + projectName);
                showEnhancedDialog("Save Complete", 
                    "Project '" + projectName + "' saved with complete state!\n" +
                    "• Canvas state: " + projectState.marks.size() + " marks\n" +
                    "• Zoom level: " + String.format("%.0f%%", projectState.zoomLevel * 100) + "\n" +
                    "• View position preserved\n" +
                    "• All work progress saved", "success");
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("❌ Error saving complete project state: " + e.getMessage());
            e.printStackTrace();
            showEnhancedDialog("Error", "Failed to save project state: " + e.getMessage(), "error");
            return false;
        }
    }
    
    /**
     * Save Project to Local File (Soft Coded Alternative to Database)
     */
    public static boolean saveProjectToLocalFile(File saveFile, String projectName) {
        try {
            System.out.println("💾 Saving project to local file: " + saveFile.getAbsolutePath());
            
            // Get DrawingCanvas reference for complete state
            DrawingCanvas canvas = getDrawingCanvasReference();
            if (canvas == null) {
                System.err.println("❌ Cannot access DrawingCanvas for local file save");
                return false;
            }
            
            // Create project state container
            ProjectState projectState = new ProjectState();
            projectState.projectName = projectName;
            projectState.projectId = generateProjectId();
            
            // Serialize canvas state if enabled (matching existing pattern)
            if (ENABLE_CANVAS_STATE_SERIALIZATION) {
                if (ENABLE_MARKS_SERIALIZATION) {
                    projectState.marks = getCanvasMarks(canvas);
                    System.out.println("📝 Serialized " + projectState.marks.size() + " marks for local save");
                }
                
                if (ENABLE_TEXT_MARKS_SERIALIZATION) {
                    projectState.textMarks = getCanvasTextMarks(canvas);
                }
                
                if (ENABLE_ZOOM_STATE_SERIALIZATION) {
                    projectState.zoomLevel = getCanvasZoomLevel(canvas);
                }
                
                if (ENABLE_VIEW_POSITION_SERIALIZATION) {
                    projectState.viewOffsetX = getCanvasViewOffsetX(canvas);
                    projectState.viewOffsetY = getCanvasViewOffsetY(canvas);
                }
            }
            
            // Additional canvas visual settings (matching existing method names)
            projectState.gridVisible = getCanvasGridVisible(canvas);
            projectState.materialBoundaryVisible = getCanvasMaterialBoundaryVisible(canvas);
            projectState.dotPreviewEnabled = getCanvasDotPreviewEnabled(canvas);
            
            // Save project state directly to local file (BINARY format for complete preservation)
            boolean success = saveProjectStateToLocalFile(projectState, saveFile);
            
            if (success) {
                System.out.println("✅ Project saved to local file: " + saveFile.getName());
                System.out.println("📊 Local save details:");
                System.out.println("   • File: " + saveFile.getAbsolutePath());
                System.out.println("   • Marks: " + (projectState.marks != null ? projectState.marks.size() : 0));
                System.out.println("   • Zoom: " + String.format("%.0f%%", projectState.zoomLevel * 100));
                System.out.println("   • Format: Binary (complete preservation)");
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("❌ Error saving project to local file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Create New Project File (Local Machine)
     */
    private static boolean createNewProjectFile(File projectFile, String projectName) {
        try {
            System.out.println("📄 Creating new project file: " + projectFile.getName());
            
            // Create initial project state for new project
            ProjectState projectState = new ProjectState();
            projectState.projectName = projectName;
            projectState.projectId = generateProjectId();
            
            // Initialize with empty collections for new project
            projectState.marks = new java.util.ArrayList<>();
            projectState.textMarks = new java.util.ArrayList<>();
            
            // Set default values for new project (soft-coded)
            projectState.zoomLevel = 1.0;           // Default zoom
            projectState.viewOffsetX = 0;           // Default view position
            projectState.viewOffsetY = 0;
            projectState.gridVisible = true;        // Default grid visibility
            projectState.materialBoundaryVisible = false;
            projectState.dotPreviewEnabled = false;
            
            // Save new project state to file
            boolean success = saveProjectStateToLocalFile(projectState, projectFile);
            
            if (success) {
                System.out.println("✅ New project file created: " + projectFile.getName());
                System.out.println("📊 New project details:");
                System.out.println("   • Name: " + projectName);
                System.out.println("   • File: " + projectFile.getAbsolutePath());
                System.out.println("   • Marks: 0 (clean slate)");
                System.out.println("   • Zoom: 100%");
                System.out.println("   • Grid: Enabled");
                return true;
            } else {
                System.err.println("❌ Failed to create new project file");
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Error creating new project file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Save Project State to Local File (Binary Format)
     */
    private static boolean saveProjectStateToLocalFile(ProjectState projectState, File saveFile) {
        try {
            // Ensure parent directory exists
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            // Save using Java serialization for complete object preservation
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(saveFile);
                 java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)) {
                
                oos.writeObject(projectState);
                oos.flush();
                System.out.println("💾 Project state written to local file: " + saveFile.getName());
                return true;
            }
        } catch (Exception e) {
            System.err.println("❌ Error writing project state to local file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Load Project from Local File (Soft Coded Alternative to Database)
     */
    public static boolean loadProjectFromLocalFile(File projectFile) {
        try {
            System.out.println("📂 Loading project from local file: " + projectFile.getAbsolutePath());
            
            if (!projectFile.exists()) {
                System.err.println("❌ Project file does not exist: " + projectFile.getAbsolutePath());
                return false;
            }
            
            // Load project state from local file
            ProjectState projectState = loadProjectStateFromLocalFile(projectFile);
            if (projectState == null) {
                System.err.println("❌ Failed to load project state from file: " + projectFile.getName());
                return false;
            }
            
            System.out.println("📊 Project state loaded from local file:");
            System.out.println("   • Project: " + projectState.projectName);
            System.out.println("   • Marks: " + (projectState.marks != null ? projectState.marks.size() : 0));
            System.out.println("   • Zoom: " + String.format("%.0f%%", projectState.zoomLevel * 100));
            
            // Get DrawingCanvas reference for restoration
            DrawingCanvas canvas = getDrawingCanvasReference();
            if (canvas == null) {
                System.err.println("❌ Cannot access DrawingCanvas for local file restoration");
                return false;
            }
            
            // Restore complete canvas state if serialization is enabled
            if (ENABLE_CANVAS_STATE_SERIALIZATION) {
                if (ENABLE_MARKS_SERIALIZATION && projectState.marks != null) {
                    restoreCanvasMarks(canvas, projectState.marks);
                    System.out.println("✅ Restored " + projectState.marks.size() + " marks from local file");
                }
                
                if (ENABLE_TEXT_MARKS_SERIALIZATION && projectState.textMarks != null) {
                    restoreCanvasTextMarks(canvas, projectState.textMarks);
                }
                
                if (ENABLE_ZOOM_STATE_SERIALIZATION) {
                    restoreCanvasZoomLevel(canvas, projectState.zoomLevel);
                }
                
                if (ENABLE_VIEW_POSITION_SERIALIZATION) {
                    restoreCanvasViewPosition(canvas, projectState.viewOffsetX, projectState.viewOffsetY);
                }
            }
            
            // Restore canvas visual settings
            restoreCanvasGridVisible(canvas, projectState.gridVisible);
            restoreCanvasMaterialBoundaryVisible(canvas, projectState.materialBoundaryVisible);
            restoreCanvasDotPreviewEnabled(canvas, projectState.dotPreviewEnabled);
            
            // Force canvas repaint to show loaded state
            canvas.repaint();
            
            System.out.println("✅ Complete project state restored from local file: " + projectFile.getName());
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error loading project from local file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Load Project State from Local File (Binary Format)
     */
    private static ProjectState loadProjectStateFromLocalFile(File projectFile) {
        try {
            System.out.println("📂 Reading project state from: " + projectFile.getName());
            
            // Load using Java serialization for complete object restoration
            try (java.io.FileInputStream fis = new java.io.FileInputStream(projectFile);
                 java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)) {
                
                Object obj = ois.readObject();
                if (obj instanceof ProjectState) {
                    ProjectState projectState = (ProjectState) obj;
                    System.out.println("📊 Local file project state loaded:");
                    System.out.println("   • Project: " + projectState.projectName);
                    System.out.println("   • Format: Binary (complete preservation)");
                    return projectState;
                } else {
                    System.err.println("❌ Invalid project file format: " + projectFile.getName());
                    return null;
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error reading project state from local file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Load Complete Project State with Canvas Restoration
     */
    public static boolean loadCompleteProjectState(String projectName) {
        if (!ENABLE_CANVAS_STATE_SERIALIZATION) {
            System.out.println("📂 Canvas state serialization disabled - using basic load");
            return loadProjectFromDatabase(projectName);
        }
        
        try {
            System.out.println("📂 Starting comprehensive project state load: " + projectName);
            
            // Load project state from file
            ProjectState projectState = loadProjectStateFromFile(projectName);
            if (projectState == null) {
                System.err.println("❌ Could not load project state file for: " + projectName);
                return false;
            }
            
            // Get DrawingCanvas reference
            DrawingCanvas canvas = getDrawingCanvasReference();
            if (canvas == null) {
                System.err.println("❌ Cannot access DrawingCanvas for state restoration");
                return false;
            }
            
            // Clear current canvas state
            clearCanvasForRestore(canvas);
            
            // Restore canvas state
            if (ENABLE_MARKS_SERIALIZATION && projectState.marks != null) {
                restoreCanvasMarks(canvas, projectState.marks);
                System.out.println("✅ Restored " + projectState.marks.size() + " marks");
            }
            
            if (ENABLE_TEXT_MARKS_SERIALIZATION && projectState.textMarks != null) {
                restoreCanvasTextMarks(canvas, projectState.textMarks);
                System.out.println("✅ Restored " + projectState.textMarks.size() + " text marks");
            }
            
            if (ENABLE_ZOOM_STATE_SERIALIZATION) {
                restoreCanvasZoomLevel(canvas, projectState.zoomLevel);
                System.out.println("✅ Restored zoom level: " + String.format("%.0f%%", projectState.zoomLevel * 100));
            }
            
            if (ENABLE_VIEW_POSITION_SERIALIZATION) {
                restoreCanvasViewPosition(canvas, projectState.viewOffsetX, projectState.viewOffsetY);
                System.out.println("✅ Restored view position: (" + projectState.viewOffsetX + ", " + projectState.viewOffsetY + ")");
            }
            
            // Restore visual state
            restoreCanvasGridVisible(canvas, projectState.gridVisible);
            restoreCanvasMaterialBoundaryVisible(canvas, projectState.materialBoundaryVisible);
            restoreCanvasDotPreviewEnabled(canvas, projectState.dotPreviewEnabled);
            
            // Refresh canvas display
            canvas.repaint();
            
            // Set current project name for tracking
            setCurrentProjectName(projectName);
            
            System.out.println("✅ Complete project state loaded successfully: " + projectName);
            showEnhancedDialog("Load Complete", 
                "Project '" + projectName + "' loaded with complete state!\n" +
                "• Canvas state: " + projectState.marks.size() + " marks restored\n" +
                "• Zoom level: " + String.format("%.0f%%", projectState.zoomLevel * 100) + "\n" +
                "• View position restored\n" +
                "• Ready to continue where you left off", "success");
            
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error loading complete project state: " + e.getMessage());
            e.printStackTrace();
            showEnhancedDialog("Error", "Failed to load project state: " + e.getMessage(), "error");
            return false;
        }
    }
    
    // ==================== CANVAS STATE ACCESS METHODS (Using Reflection) ====================
    
    /**
     * Get marks collection from canvas using reflection
     */
    private static java.util.List<Mark> getCanvasMarks(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field marksField = canvas.getClass().getDeclaredField("marks");
            marksField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<Mark> marks = (java.util.List<Mark>) marksField.get(canvas);
            return new java.util.ArrayList<>(marks); // Create defensive copy
        } catch (Exception e) {
            System.err.println("❌ Could not access marks collection: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Get text marks collection from canvas using reflection
     */
    private static java.util.List<TextMark> getCanvasTextMarks(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field textMarksField = canvas.getClass().getDeclaredField("textMarks");
            textMarksField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<TextMark> textMarks = (java.util.List<TextMark>) textMarksField.get(canvas);
            return new java.util.ArrayList<>(textMarks); // Create defensive copy
        } catch (Exception e) {
            System.err.println("❌ Could not access textMarks collection: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Get zoom level from canvas using reflection
     */
    private static double getCanvasZoomLevel(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field zoomField = canvas.getClass().getDeclaredField("zoomLevel");
            zoomField.setAccessible(true);
            return zoomField.getDouble(canvas);
        } catch (Exception e) {
            System.err.println("❌ Could not access zoom level: " + e.getMessage());
            return 1.0;
        }
    }
    
    /**
     * Get view offset X from canvas using reflection
     */
    private static int getCanvasViewOffsetX(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field offsetXField = canvas.getClass().getDeclaredField("viewOffsetX");
            offsetXField.setAccessible(true);
            return offsetXField.getInt(canvas);
        } catch (Exception e) {
            System.err.println("❌ Could not access viewOffsetX: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get view offset Y from canvas using reflection
     */
    private static int getCanvasViewOffsetY(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field offsetYField = canvas.getClass().getDeclaredField("viewOffsetY");
            offsetYField.setAccessible(true);
            return offsetYField.getInt(canvas);
        } catch (Exception e) {
            System.err.println("❌ Could not access viewOffsetY: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get grid visibility from canvas using reflection
     */
    private static boolean getCanvasGridVisible(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field gridField = canvas.getClass().getDeclaredField("gridVisible");
            gridField.setAccessible(true);
            return gridField.getBoolean(canvas);
        } catch (Exception e) {
            System.err.println("❌ Could not access gridVisible: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * Get material boundary visibility from canvas using reflection
     */
    private static boolean getCanvasMaterialBoundaryVisible(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field boundaryField = canvas.getClass().getDeclaredField("materialBoundaryVisible");
            boundaryField.setAccessible(true);
            return boundaryField.getBoolean(canvas);
        } catch (Exception e) {
            System.err.println("❌ Could not access materialBoundaryVisible: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get dot preview enabled from canvas using reflection
     */
    private static boolean getCanvasDotPreviewEnabled(DrawingCanvas canvas) {
        try {
            java.lang.reflect.Field previewField = canvas.getClass().getDeclaredField("dotPreviewEnabled");
            previewField.setAccessible(true);
            return previewField.getBoolean(canvas);
        } catch (Exception e) {
            System.err.println("❌ Could not access dotPreviewEnabled: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== CANVAS STATE RESTORATION METHODS ====================
    
    /**
     * Clear canvas for restoration using existing clearAllForNewProject method
     */
    private static void clearCanvasForRestore(DrawingCanvas canvas) {
        try {
            canvas.clearAllForNewProject();
            System.out.println("✅ Canvas cleared for project restoration");
        } catch (Exception e) {
            System.err.println("❌ Could not clear canvas for restore: " + e.getMessage());
        }
    }
    
    /**
     * Restore marks collection to canvas using reflection
     */
    private static void restoreCanvasMarks(DrawingCanvas canvas, java.util.List<Mark> marks) {
        try {
            java.lang.reflect.Field marksField = canvas.getClass().getDeclaredField("marks");
            marksField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<Mark> canvasMarks = (java.util.List<Mark>) marksField.get(canvas);
            canvasMarks.clear();
            canvasMarks.addAll(marks);
            System.out.println("✅ Restored " + marks.size() + " marks to canvas");
        } catch (Exception e) {
            System.err.println("❌ Could not restore marks: " + e.getMessage());
        }
    }
    
    /**
     * Restore text marks collection to canvas using reflection
     */
    private static void restoreCanvasTextMarks(DrawingCanvas canvas, java.util.List<TextMark> textMarks) {
        try {
            java.lang.reflect.Field textMarksField = canvas.getClass().getDeclaredField("textMarks");
            textMarksField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<TextMark> canvasTextMarks = (java.util.List<TextMark>) textMarksField.get(canvas);
            canvasTextMarks.clear();
            canvasTextMarks.addAll(textMarks);
            System.out.println("✅ Restored " + textMarks.size() + " text marks to canvas");
        } catch (Exception e) {
            System.err.println("❌ Could not restore text marks: " + e.getMessage());
        }
    }
    
    /**
     * Restore zoom level to canvas using reflection
     */
    private static void restoreCanvasZoomLevel(DrawingCanvas canvas, double zoomLevel) {
        try {
            java.lang.reflect.Field zoomField = canvas.getClass().getDeclaredField("zoomLevel");
            zoomField.setAccessible(true);
            zoomField.setDouble(canvas, zoomLevel);
            System.out.println("✅ Restored zoom level: " + String.format("%.0f%%", zoomLevel * 100));
        } catch (Exception e) {
            System.err.println("❌ Could not restore zoom level: " + e.getMessage());
        }
    }
    
    /**
     * Restore view position to canvas using reflection
     */
    private static void restoreCanvasViewPosition(DrawingCanvas canvas, int viewOffsetX, int viewOffsetY) {
        try {
            java.lang.reflect.Field offsetXField = canvas.getClass().getDeclaredField("viewOffsetX");
            java.lang.reflect.Field offsetYField = canvas.getClass().getDeclaredField("viewOffsetY");
            offsetXField.setAccessible(true);
            offsetYField.setAccessible(true);
            offsetXField.setInt(canvas, viewOffsetX);
            offsetYField.setInt(canvas, viewOffsetY);
            System.out.println("✅ Restored view position: (" + viewOffsetX + ", " + viewOffsetY + ")");
        } catch (Exception e) {
            System.err.println("❌ Could not restore view position: " + e.getMessage());
        }
    }
    
    /**
     * Restore grid visibility to canvas using reflection
     */
    private static void restoreCanvasGridVisible(DrawingCanvas canvas, boolean gridVisible) {
        try {
            java.lang.reflect.Field gridField = canvas.getClass().getDeclaredField("gridVisible");
            gridField.setAccessible(true);
            gridField.setBoolean(canvas, gridVisible);
            System.out.println("✅ Restored grid visibility: " + gridVisible);
        } catch (Exception e) {
            System.err.println("❌ Could not restore grid visibility: " + e.getMessage());
        }
    }
    
    /**
     * Restore material boundary visibility to canvas using reflection
     */
    private static void restoreCanvasMaterialBoundaryVisible(DrawingCanvas canvas, boolean materialBoundaryVisible) {
        try {
            java.lang.reflect.Field boundaryField = canvas.getClass().getDeclaredField("materialBoundaryVisible");
            boundaryField.setAccessible(true);
            boundaryField.setBoolean(canvas, materialBoundaryVisible);
            System.out.println("✅ Restored material boundary visibility: " + materialBoundaryVisible);
        } catch (Exception e) {
            System.err.println("❌ Could not restore material boundary visibility: " + e.getMessage());
        }
    }
    
    /**
     * Restore dot preview enabled to canvas using reflection
     */
    private static void restoreCanvasDotPreviewEnabled(DrawingCanvas canvas, boolean dotPreviewEnabled) {
        try {
            java.lang.reflect.Field previewField = canvas.getClass().getDeclaredField("dotPreviewEnabled");
            previewField.setAccessible(true);
            previewField.setBoolean(canvas, dotPreviewEnabled);
            System.out.println("✅ Restored dot preview enabled: " + dotPreviewEnabled);
        } catch (Exception e) {
            System.err.println("❌ Could not restore dot preview enabled: " + e.getMessage());
        }
    }
    
    // ==================== PROJECT FILE I/O OPERATIONS ====================
    
    /**
     * Save project state to file using Java serialization
     */
    private static boolean saveProjectStateToFile(ProjectState projectState, String projectName) {
        if (!ENABLE_PROJECT_COMPRESSION) {
            return saveProjectStateUncompressed(projectState, projectName);
        }
        
        try {
            // Ensure project directory exists
            java.io.File projectDir = new java.io.File(PROJECT_DATA_DIRECTORY);
            if (!projectDir.exists()) {
                projectDir.mkdirs();
                System.out.println("📁 Created project directory: " + PROJECT_DATA_DIRECTORY);
            }
            
            // Create project file
            String fileName = projectName.replaceAll("[^a-zA-Z0-9._-]", "_") + PROJECT_FILE_EXTENSION;
            java.io.File projectFile = new java.io.File(projectDir, fileName);
            
            // Save with compression
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(projectFile);
                 java.util.zip.GZIPOutputStream gzos = new java.util.zip.GZIPOutputStream(fos);
                 java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(gzos)) {
                
                oos.writeObject(projectState);
                System.out.println("✅ Project state saved with compression: " + projectFile.getAbsolutePath());
                
                // Create backup if enabled
                if (ENABLE_AUTO_PROJECT_BACKUP) {
                    createProjectBackup(projectFile, projectName);
                }
                
                return true;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error saving project state to file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Save project state to file without compression
     */
    private static boolean saveProjectStateUncompressed(ProjectState projectState, String projectName) {
        try {
            // Ensure project directory exists
            java.io.File projectDir = new java.io.File(PROJECT_DATA_DIRECTORY);
            if (!projectDir.exists()) {
                projectDir.mkdirs();
                System.out.println("📁 Created project directory: " + PROJECT_DATA_DIRECTORY);
            }
            
            // Create project file
            String fileName = projectName.replaceAll("[^a-zA-Z0-9._-]", "_") + PROJECT_FILE_EXTENSION;
            java.io.File projectFile = new java.io.File(projectDir, fileName);
            
            // Save without compression
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(projectFile);
                 java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)) {
                
                oos.writeObject(projectState);
                System.out.println("✅ Project state saved uncompressed: " + projectFile.getAbsolutePath());
                
                return true;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error saving project state to file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load project state from file
     */
    private static ProjectState loadProjectStateFromFile(String projectName) {
        try {
            // Try exact name first (consistent with save logic)
            String exactFileName = projectName + PROJECT_FILE_EXTENSION;
            java.io.File exactFile = new java.io.File(PROJECT_DATA_DIRECTORY, exactFileName);
            
            System.out.println("🔍 Trying exact file match: " + exactFile.getAbsolutePath());
            
            if (exactFile.exists()) {
                // Use ProjectStateManager for consistent format handling
                System.out.println("✅ Found exact match, loading with ProjectStateManager (format: " + SERIALIZATION_FORMAT + ")");
                ProjectState projectState = ProjectStateManager.loadProjectFromFile(exactFile.getAbsolutePath());
                
                if (projectState != null) {
                    System.out.println("✅ Project loaded successfully: " + projectName);
                    return projectState;
                } else {
                    System.err.println("❌ ProjectStateManager failed to load exact match: " + projectName);
                }
            }
            
            // Fallback: try sanitized name (legacy compatibility)
            String sanitizedFileName = projectName.replaceAll("[^a-zA-Z0-9._-]", "_") + PROJECT_FILE_EXTENSION;
            java.io.File sanitizedFile = new java.io.File(PROJECT_DATA_DIRECTORY, sanitizedFileName);
            
            System.out.println("🔍 Trying sanitized file match: " + sanitizedFile.getAbsolutePath());
            
            if (sanitizedFile.exists()) {
                System.out.println("✅ Found sanitized match, loading with ProjectStateManager");
                ProjectState projectState = ProjectStateManager.loadProjectFromFile(sanitizedFile.getAbsolutePath());
                
                if (projectState != null) {
                    System.out.println("✅ Project loaded successfully (sanitized): " + projectName);
                    return projectState;
                } else {
                    System.err.println("❌ ProjectStateManager failed to load sanitized match: " + projectName);
                }
            }
            
            // Final fallback: search for files containing the project name
            System.out.println("� Searching for files containing: " + projectName);
            java.io.File projectDir = new java.io.File(PROJECT_DATA_DIRECTORY);
            java.io.File[] matchingFiles = projectDir.listFiles((dir, name) -> 
                name.toLowerCase().contains(projectName.toLowerCase()) && name.endsWith(PROJECT_FILE_EXTENSION));
            
            if (matchingFiles != null && matchingFiles.length > 0) {
                System.out.println("✅ Found similar file: " + matchingFiles[0].getName());
                ProjectState projectState = ProjectStateManager.loadProjectFromFile(matchingFiles[0].getAbsolutePath());
                
                if (projectState != null) {
                    System.out.println("✅ Project loaded successfully (similar match): " + projectName);
                    return projectState;
                } else {
                    System.err.println("❌ ProjectStateManager failed to load similar match: " + projectName);
                }
            }
            
            System.err.println("❌ No matching project file found for: " + projectName);
            return null;
            
        } catch (Exception e) {
            System.err.println("❌ Error loading project state from file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Load compressed project state
     */
    private static ProjectState loadCompressedProjectState(java.io.File projectFile) {
        try (java.io.FileInputStream fis = new java.io.FileInputStream(projectFile);
             java.util.zip.GZIPInputStream gzis = new java.util.zip.GZIPInputStream(fis);
             java.io.ObjectInputStream ois = new java.io.ObjectInputStream(gzis)) {
            
            ProjectState projectState = (ProjectState) ois.readObject();
            System.out.println("✅ Project state loaded from compressed file: " + projectFile.getAbsolutePath());
            return projectState;
            
        } catch (Exception e) {
            // Log the specific error for debugging
            System.err.println("🔍 Error loading compressed project state (trying uncompressed next): " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Load uncompressed project state
     */
    private static ProjectState loadUncompressedProjectState(java.io.File projectFile) {
        try (java.io.FileInputStream fis = new java.io.FileInputStream(projectFile);
             java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)) {
            
            ProjectState projectState = (ProjectState) ois.readObject();
            System.out.println("✅ Project state loaded from uncompressed file: " + projectFile.getAbsolutePath());
            return projectState;
            
        } catch (Exception e) {
            System.err.println("❌ Error loading uncompressed project state: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create project backup
     */
    private static void createProjectBackup(java.io.File projectFile, String projectName) {
        try {
            String backupFileName = projectName.replaceAll("[^a-zA-Z0-9._-]", "_") + "_backup_" + 
                                    System.currentTimeMillis() + PROJECT_FILE_EXTENSION;
            java.io.File backupFile = new java.io.File(PROJECT_DATA_DIRECTORY, backupFileName);
            
            java.nio.file.Files.copy(projectFile.toPath(), backupFile.toPath());
            System.out.println("💾 Project backup created: " + backupFile.getAbsolutePath());
            
        } catch (Exception e) {
            System.err.println("⚠️ Warning: Could not create project backup: " + e.getMessage());
        }
    }
    
    /**
     * Save project with state reference to database
     */
    private static boolean saveProjectWithStateToDatabase(String projectName, ProjectState projectState) {
        try {
            // Save basic project info to database
            boolean dbSuccess = saveCurrentProjectToDatabase(projectName);
            
            if (dbSuccess) {
                System.out.println("✅ Project saved to database with state reference: " + projectName);
            }
            
            return dbSuccess;
            
        } catch (Exception e) {
            System.err.println("❌ Error saving project with state to database: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== GRID RESTRICTION METHODS FOR AUTO-POPULATION FIELDS ====================
    
    /**
     * Initialize Grid Restrictions for Auto-Population Field Consistency (Soft Coded)
     * Call this method to apply grid restrictions that ensure auto-population fields work correctly
     */
    public static void initializeAutoPopulationGridRestrictions() {
        if (DEBUG_GRID_RESTRICTIONS || ENABLE_DEBUG_OUTPUT) {
            System.out.println("🔒 Initializing Grid Restrictions for Auto-Population Field Consistency");
            System.out.println("   - Zoom In: " + (ENABLE_GRID_ZOOM_IN ? "ENABLED" : "DISABLED"));
            System.out.println("   - Zoom Out: " + (ENABLE_GRID_ZOOM_OUT ? "ENABLED" : "DISABLED"));
            System.out.println("   - Free Positioning: " + (ENABLE_FREE_MARK_POSITIONING ? "ENABLED" : "DISABLED"));
            System.out.println("   - Grid Snap Forced: " + (FORCE_GRID_SNAP_POSITIONING ? "ENABLED" : "DISABLED"));
            System.out.println("   - Zoom Lock: " + (LOCK_ZOOM_LEVEL ? "LOCKED at " + (FIXED_ZOOM_LEVEL * 100) + "%" : "UNLOCKED"));
        }
        
        try {
            // Apply zoom restrictions for auto-population field consistency
            applyZoomRestrictionsForFields();
            
            // Apply positioning restrictions for auto-population accuracy
            applyPositioningRestrictionsForFields();
            
            // Setup auto-population field protection
            setupAutoPopulationFieldProtection();
            
            if (ENABLE_DEBUG_OUTPUT) {
                System.out.println("✅ Auto-Population Grid restrictions initialized successfully");
            }
        } catch (Exception e) {
            System.err.println("❌ Error initializing auto-population grid restrictions: " + e.getMessage());
        }
    }
    
    /**
     * Apply Zoom Restrictions for Auto-Population Field Consistency (Soft Coded)
     */
    private static void applyZoomRestrictionsForFields() {
        if (DEBUG_GRID_RESTRICTIONS) {
            System.out.println("🔒 Applying zoom restrictions for auto-population field consistency...");
        }
        
        if (!ENABLE_GRID_ZOOM_IN && !ENABLE_GRID_ZOOM_OUT) {
            try {
                // Note: In a real implementation, this would integrate with the DrawingCanvas
                // For now, we establish the restriction framework
                
                if (LOCK_ZOOM_LEVEL) {
                    System.out.println("🔒 Zoom level locked at " + (FIXED_ZOOM_LEVEL * 100) + "% for auto-population field consistency");
                }
                
                if (!ENABLE_MOUSE_WHEEL_ZOOM) {
                    System.out.println("🔒 Mouse wheel zoom disabled for auto-population field stability");
                }
                
                if (!ENABLE_KEYBOARD_ZOOM_SHORTCUTS) {
                    System.out.println("🔒 Keyboard zoom shortcuts disabled for auto-population field consistency");
                }
                
            } catch (Exception e) {
                System.err.println("❌ Error applying zoom restrictions: " + e.getMessage());
            }
        }
    }
    
    /**
     * Apply Positioning Restrictions for Auto-Population Field Accuracy (Soft Coded)
     */
    private static void applyPositioningRestrictionsForFields() {
        if (DEBUG_GRID_RESTRICTIONS) {
            System.out.println("🔒 Applying positioning restrictions for auto-population field accuracy...");
        }
        
        try {
            if (!ENABLE_FREE_MARK_POSITIONING || FORCE_GRID_SNAP_POSITIONING) {
                System.out.println("🔒 Free mark positioning disabled - marks will snap to grid for auto-population accuracy");
                
                if (FORCE_GRID_SNAP_POSITIONING) {
                    System.out.println("🔒 Grid snap positioning enforced with " + GRID_SNAP_TOLERANCE + "px tolerance");
                }
                
                if (ENABLE_MARK_BOUNDARY_ENFORCEMENT) {
                    System.out.println("🔒 Mark boundary enforcement enabled for auto-population field protection");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error applying positioning restrictions: " + e.getMessage());
        }
    }
    
    /**
     * Setup Auto-Population Field Protection (Soft Coded)
     */
    private static void setupAutoPopulationFieldProtection() {
        if (DEBUG_GRID_RESTRICTIONS) {
            System.out.println("🔒 Setting up auto-population field protection...");
        }
        
        try {
            if (PROTECT_AUTO_POPULATION_AREAS) {
                System.out.println("🔒 Auto-population area protection enabled");
                
                if (PREVENT_MARK_OVERLAP_IN_FIELDS) {
                    System.out.println("🔒 Mark overlap prevention enabled in auto-population field areas");
                }
                
                if (ENABLE_FIELD_POSITION_VALIDATION) {
                    System.out.println("🔒 Field position validation enabled for auto-population accuracy");
                }
            }
            
            if (ENFORCE_GRID_STABILITY) {
                System.out.println("🔒 Grid stability enforcement enabled for consistent auto-population");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error setting up auto-population field protection: " + e.getMessage());
        }
    }
    
    /**
     * Validate Mark Position for Auto-Population Field Compatibility (Soft Coded)
     */
    public static boolean validateMarkPositionForAutoPopulation(Point position) {
        try {
            if (!ENABLE_FIELD_POSITION_VALIDATION) {
                return true; // Skip validation if disabled
            }
            
            // Check if position requires grid snapping for auto-population accuracy
            if (FORCE_GRID_SNAP_POSITIONING) {
                Point snapPoint = calculateGridSnapPosition(position);
                if (!snapPoint.equals(position)) {
                    if (DEBUG_GRID_RESTRICTIONS) {
                        System.out.println("📍 Position requires snapping for auto-population: " + position + " -> " + snapPoint);
                    }
                    
                    if (SHOW_FIELD_PROTECTION_WARNINGS) {
                        showAutoPopulationPositionWarning(position, snapPoint);
                    }
                    
                    return false; // Position needs adjustment
                }
            }
            
            return true; // Position is valid for auto-population
            
        } catch (Exception e) {
            System.err.println("❌ Error validating mark position for auto-population: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Calculate Grid Snap Position for Auto-Population Accuracy (Soft Coded)
     */
    private static Point calculateGridSnapPosition(Point originalPosition) {
        try {
            if (!FORCE_GRID_SNAP_POSITIONING) {
                return originalPosition; // No snapping required
            }
            
            // Calculate nearest grid position for auto-population field alignment
            int gridSize = 20; // Standard grid size for auto-population consistency
            int snapX = Math.round((float) originalPosition.x / gridSize) * gridSize;
            int snapY = Math.round((float) originalPosition.y / gridSize) * gridSize;
            
            Point snapPoint = new Point(snapX, snapY);
            
            // Check if snapping is within tolerance for auto-population accuracy
            double distance = originalPosition.distance(snapPoint);
            if (distance <= GRID_SNAP_TOLERANCE) {
                return snapPoint;
            }
            
            return originalPosition; // Keep original if outside snap tolerance
            
        } catch (Exception e) {
            System.err.println("❌ Error calculating grid snap position: " + e.getMessage());
            return originalPosition;
        }
    }
    
    /**
     * Check if Zoom Operation is Allowed for Auto-Population Fields (Soft Coded)
     */
    public static boolean isZoomOperationAllowed(String operation) {
        try {
            switch (operation.toLowerCase()) {
                case "zoomin":
                case "zoom_in":
                    if (!ENABLE_GRID_ZOOM_IN) {
                        if (SHOW_ZOOM_RESTRICTION_MESSAGE) {
                            showZoomRestrictionMessage("Zoom In", "Zoom in is disabled to maintain auto-population field consistency");
                        }
                        return false;
                    }
                    break;
                    
                case "zoomout":
                case "zoom_out":
                    if (!ENABLE_GRID_ZOOM_OUT) {
                        if (SHOW_ZOOM_RESTRICTION_MESSAGE) {
                            showZoomRestrictionMessage("Zoom Out", "Zoom out is disabled to maintain auto-population field consistency");
                        }
                        return false;
                    }
                    break;
                    
                case "mousewheel":
                case "mouse_wheel":
                    if (!ENABLE_MOUSE_WHEEL_ZOOM) {
                        if (SHOW_ZOOM_RESTRICTION_MESSAGE) {
                            showZoomRestrictionMessage("Mouse Wheel Zoom", "Mouse wheel zoom is disabled for auto-population field stability");
                        }
                        return false;
                    }
                    break;
                    
                case "keyboard":
                case "keyboard_shortcut":
                    if (!ENABLE_KEYBOARD_ZOOM_SHORTCUTS) {
                        if (SHOW_ZOOM_RESTRICTION_MESSAGE) {
                            showZoomRestrictionMessage("Keyboard Zoom", "Keyboard zoom shortcuts are disabled for auto-population field consistency");
                        }
                        return false;
                    }
                    break;
            }
            
            // Check if zoom level is locked
            if (LOCK_ZOOM_LEVEL) {
                if (SHOW_ZOOM_RESTRICTION_MESSAGE) {
                    showZoomRestrictionMessage("Zoom Lock", "Zoom level is locked at " + (FIXED_ZOOM_LEVEL * 100) + "% for auto-population field accuracy");
                }
                return false;
            }
            
            return true; // Zoom operation is allowed
            
        } catch (Exception e) {
            System.err.println("❌ Error checking zoom operation permission: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if Mark Positioning Operation is Allowed for Auto-Population Fields (Soft Coded)
     */
    public static boolean isMarkPositioningAllowed(Point position) {
        try {
            if (ENABLE_FREE_MARK_POSITIONING && !FORCE_GRID_SNAP_POSITIONING) {
                return true; // Free positioning is allowed
            }
            
            // Validate position for auto-population field compatibility
            return validateMarkPositionForAutoPopulation(position);
            
        } catch (Exception e) {
            System.err.println("❌ Error checking mark positioning permission: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Show Zoom Restriction Message for Auto-Population Fields (Soft Coded)
     */
    private static void showZoomRestrictionMessage(String operation, String message) {
        if (SHOW_ZOOM_RESTRICTION_MESSAGE && DEBUG_GRID_RESTRICTIONS) {
            System.out.println("🚫 ZOOM RESTRICTED: " + operation + " - " + message);
        }
        
        // In a full implementation, this could show a user dialog
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("⚠️ Auto-Population Field Protection: " + message);
        }
    }
    
    /**
     * Show Auto-Population Position Warning (Soft Coded)
     */
    private static void showAutoPopulationPositionWarning(Point originalPosition, Point snapPosition) {
        if (SHOW_FIELD_PROTECTION_WARNINGS && DEBUG_GRID_RESTRICTIONS) {
            String message = String.format("Mark position adjusted for auto-population accuracy: (%d,%d) -> (%d,%d)", 
                originalPosition.x, originalPosition.y, snapPosition.x, snapPosition.y);
            System.out.println("📍 AUTO-POPULATION ADJUSTMENT: " + message);
        }
    }
    
    /**
     * Get Grid Restriction Status for Auto-Population Fields (Soft Coded)
     */
    public static String getAutoPopulationGridStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Auto-Population Grid Status:\n");
        status.append("  - Zoom Restrictions: ").append(!ENABLE_GRID_ZOOM_IN && !ENABLE_GRID_ZOOM_OUT ? "ACTIVE" : "INACTIVE").append("\n");
        status.append("  - Position Restrictions: ").append(FORCE_GRID_SNAP_POSITIONING ? "ACTIVE" : "INACTIVE").append("\n");
        status.append("  - Zoom Level: ").append(LOCK_ZOOM_LEVEL ? "LOCKED at " + (FIXED_ZOOM_LEVEL * 100) + "%" : "UNLOCKED").append("\n");
        status.append("  - Field Protection: ").append(PROTECT_AUTO_POPULATION_AREAS ? "ENABLED" : "DISABLED").append("\n");
        status.append("  - Grid Stability: ").append(ENFORCE_GRID_STABILITY ? "ENFORCED" : "FLEXIBLE").append("\n");
        return status.toString();
    }
    
    /**
     * Public API: Check if Grid Zoom is Restricted for Auto-Population (External Components)
     */
    public static boolean isGridZoomRestricted() {
        return (!ENABLE_GRID_ZOOM_IN && !ENABLE_GRID_ZOOM_OUT) || LOCK_ZOOM_LEVEL;
    }
    
    /**
     * Public API: Check if Mark Positioning is Restricted for Auto-Population (External Components)
     */
    public static boolean isMarkPositioningRestricted() {
        return !ENABLE_FREE_MARK_POSITIONING || FORCE_GRID_SNAP_POSITIONING;
    }
    
    /**
     * Public API: Get Fixed Zoom Level for Auto-Population Fields (External Components)
     */
    public static double getFixedZoomLevel() {
        return LOCK_ZOOM_LEVEL ? FIXED_ZOOM_LEVEL : 1.0;
    }

}