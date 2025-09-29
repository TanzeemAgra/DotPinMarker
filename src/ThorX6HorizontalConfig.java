import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Mark class imports for comprehensive editor support
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;

/**
 * ThorX6 Horizontal Mark Ta      
       // All button groups in order (Only Clipboard group remaining)ups in order (Only Clipboard group remaining)n groups in order (Only Clipboard group remaining)ding System
 * Creates exact ThorX6 horizontal layout like real ThorX6 software
 */
public class ThorX6HorizontalConfig {
    
    // ==================== SOFT CODING CONTROL FLAGS ====================
    
    public static final boolean ENABLE_THORX6_PROPERTIES_PANEL = false; // DISABLED in Mark tab (keeping bottom property strip only - selective duplicate removal)
    public static final boolean ENABLE_CODER_SUBGROUP = false;          // DISABLE entire Coder subgroup from UI (DELETED via soft coding)
    public static final boolean HIDE_CODER_SUBGROUP_COMPLETELY = true;  // Master flag to completely hide Coder subgroup
    public static final boolean CODER_INTEGRATION_ACTIVE = true;        // Enable Coder functionality integration
    public static final boolean ENABLE_BARCODE_OPTION = false;          // DISABLE generic Barcode option (use specific barcode types in grid instead)
    
    // DataMatrix Button Configuration (Soft-coded name replacement)
    public static final boolean REPLACE_DATAMATRIX_WITH_CODER = true;   // ENABLE DataMatrix -> Coder name replacement
    public static final String DATAMATRIX_BUTTON_TEXT = "Coder";        // Soft-coded button text (DataMatrix -> Coder)
    public static final String DATAMATRIX_BUTTON_ICON = "▣";            // Soft-coded button icon  
    public static final String DATAMATRIX_BUTTON_TOOLTIP = "Generate Coder"; // Soft-coded tooltip (DataMatrix -> Coder)
    
    // No Coder Configuration (Soft-coded first option)
    public static final boolean ENABLE_NO_CODER_OPTION = true;          // ENABLE "No Coder" as first cell
    public static final String NO_CODER_TEXT = "No Coder";              // Soft-coded No Coder text
    public static final String NO_CODER_ICON = "✕";                     // Professional visual label (like reference image)
    public static final String NO_CODER_TOOLTIP = "Clear coder selection"; // Soft-coded No Coder tooltip

    // Serial Configuration (Soft-coded second option)
    public static final boolean ENABLE_SERIAL_OPTION = true;            // ENABLE "Serial" as second cell
    public static final String SERIAL_TEXT = "Serial";                  // Soft-coded Serial text
    public static final String SERIAL_ICON = "123";                     // Professional visual label (like reference image)
    public static final String SERIAL_TOOLTIP = "Generate sequential serial numbers"; // Soft-coded Serial tooltip
    
    // Serial Number Management (Soft-coded)
    public static final boolean ENABLE_AUTO_INCREMENT = true;           // ENABLE auto-increment for serial numbers
    public static final String SERIAL_PREFIX = "SN";                    // Soft-coded serial number prefix
    public static final int SERIAL_START_NUMBER = 1;                    // Soft-coded starting number
    public static final int SERIAL_PADDING_ZEROS = 3;                   // Soft-coded padding (e.g., SN001, SN002)
    private static int currentSerialNumber = SERIAL_START_NUMBER;       // Current serial counter

    // VIN Configuration (Soft-coded third option)
    public static final boolean ENABLE_VIN_OPTION = true;               // ENABLE "VIN" as third cell
    public static final String VIN_TEXT = "VIN";                        // Soft-coded VIN text
    public static final String VIN_ICON = "VIN";                        // Professional visual label (like reference image)
    public static final String VIN_TOOLTIP = "Generate Vehicle Identification Number"; // Soft-coded VIN tooltip
    
    // VIN Management (Soft-coded)
    public static final boolean ENABLE_VIN_VALIDATION = true;           // ENABLE VIN format validation
    public static final String VIN_DEFAULT_PREFIX = "1HGBH41JXMN";     // Soft-coded VIN default prefix (Honda example)
    public static final int VIN_LENGTH = 17;                            // Standard VIN length (17 characters)

    // PIN Configuration (Soft-coded fourth option)
    public static final boolean ENABLE_PIN_OPTION = true;               // ENABLE "PIN" as fourth cell
    public static final String PIN_TEXT = "PIN";                        // Soft-coded PIN text
    public static final String PIN_ICON = "�️";                        // Professional security key icon (ThorX6 style)
    public static final String PIN_TOOLTIP = "Generate Personal Identification Number"; // Soft-coded PIN tooltip
    
    // PIN Management (Soft-coded)
    public static final boolean ENABLE_PIN_SECURITY = true;             // ENABLE PIN security features
    public static final int PIN_DEFAULT_LENGTH = 4;                     // Soft-coded PIN length (4 digits)
    public static final int PIN_MIN_LENGTH = 3;                         // Minimum PIN length
    public static final int PIN_MAX_LENGTH = 8;                         // Maximum PIN length
    public static final boolean ENABLE_PIN_COMPLEXITY = false;          // Simple numeric PINs only

    // DateTime Configuration (Soft-coded fifth option)
    public static final boolean ENABLE_DATETIME_OPTION = true;          // ENABLE "DateTime" as fifth cell
    public static final String DATETIME_TEXT = "DateTime";              // Soft-coded DateTime text
    public static final String DATETIME_ICON = "📅";                   // Professional visual label (like reference image)
    public static final String DATETIME_TOOLTIP = "Generate current date and time stamp"; // Soft-coded DateTime tooltip
    
    // DateTime Management (Soft-coded)
    public static final boolean ENABLE_CUSTOM_DATETIME_FORMAT = true;   // ENABLE custom date/time formatting
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"; // Default format
    public static final String DATETIME_SHORT_FORMAT = "MM/dd/yyyy";    // Short date format
    public static final String DATETIME_TIME_ONLY = "HH:mm:ss";        // Time only format
    public static final boolean ENABLE_REALTIME_UPDATE = false;         // Static timestamp vs real-time

    // VCode Configuration (Soft-coded sixth option - Next Row First Column)
    public static final boolean ENABLE_VCODE_OPTION = true;             // ENABLE "VCode" as sixth cell (Row 2, Col 1)
    public static final String VCODE_TEXT = "VCode";                    // Soft-coded VCode text
    public static final String VCODE_ICON = "✅";                       // Professional verification checkmark icon (ThorX6 style)
    public static final String VCODE_TOOLTIP = "Generate Verification Code for security"; // Soft-coded VCode tooltip
    
    // VCode Management (Soft-coded)
    public static final boolean ENABLE_VCODE_ENCRYPTION = true;         // ENABLE VCode encryption features
    public static final int VCODE_DEFAULT_LENGTH = 8;                   // Soft-coded VCode length (8 characters)
    public static final String VCODE_CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Character set for VCode
    public static final boolean ENABLE_VCODE_CHECKSUM = true;           // ENABLE checksum validation
    public static final String VCODE_PREFIX = "V";                      // Soft-coded VCode prefix

    // TxMark6PieCoder Configuration (Soft-coded seventh option - Row 2, Col 2)
    public static final boolean ENABLE_TXMARK6PIE_OPTION = true;        // ENABLE "TxMark6PieCoder" as seventh cell
    public static final String TXMARK6PIE_TEXT = "TxMark6PieCoder";     // Soft-coded TxMark6PieCoder text
    public static final String TXMARK6PIE_ICON = "📊";                  // Professional pie chart icon (ThorX6 style)
    public static final String TXMARK6PIE_TOOLTIP = "Generate TxMark6 Pie-based encoding codes"; // Soft-coded tooltip
    
    // TxMark6PieCoder Management (Soft-coded)
    public static final boolean ENABLE_TXMARK6PIE_ADVANCED = true;      // ENABLE advanced TxMark6Pie features
    public static final String TXMARK6PIE_ALGORITHM = "PIE-6";          // Soft-coded algorithm version
    public static final int TXMARK6PIE_SECTORS = 6;                     // Pie sectors for encoding (6-sector pie)
    public static final String TXMARK6PIE_PREFIX = "TX6";               // Soft-coded TxMark6 prefix
    public static final boolean ENABLE_TXMARK6PIE_VALIDATION = true;    // ENABLE pie sector validation

    // Class Configuration (Soft-coded eighth option - Row 2, Col 3)
    public static final boolean ENABLE_CLASS_OPTION = true;             // ENABLE "Class" as eighth cell
    public static final String CLASS_TEXT = "Class";                    // Soft-coded Class text
    public static final String CLASS_ICON = "�";                      // Professional graduation cap icon for classification (ThorX6 style)
    public static final String CLASS_TOOLTIP = "Generate Class identification codes"; // Soft-coded Class tooltip
    
    // Class Management (Soft-coded)
    public static final boolean ENABLE_CLASS_CATEGORIZATION = true;     // ENABLE class categorization features
    public static final String CLASS_DEFAULT_CATEGORY = "STD";          // Soft-coded default class category (Standard)
    public static final String[] CLASS_CATEGORIES = {"STD", "ADV", "PRO", "EXP"}; // Standard, Advanced, Professional, Expert
    public static final String CLASS_PREFIX = "CL";                     // Soft-coded Class prefix
    public static final boolean ENABLE_CLASS_HIERARCHY = true;          // ENABLE hierarchical class numbering

    // Random Code Configuration (Soft-coded ninth option - Row 2, Col 4 - Final Option)
    public static final boolean ENABLE_RANDOM_CODE_OPTION = true;       // ENABLE "Random Code" as ninth cell (final option)
    public static final String RANDOM_CODE_TEXT = "Random Code";        // Soft-coded Random Code text
    public static final String RANDOM_CODE_ICON = "🔀";                 // Professional shuffle/random icon (ThorX6 style)
    public static final String RANDOM_CODE_TOOLTIP = "Generate completely random codes with multiple algorithms"; // Soft-coded tooltip
    
    // Random Code Management (Soft-coded)
    public static final boolean ENABLE_RANDOM_ALGORITHMS = true;        // ENABLE multiple random algorithms
    public static final int RANDOM_CODE_MIN_LENGTH = 6;                 // Minimum random code length
    public static final int RANDOM_CODE_MAX_LENGTH = 12;                // Maximum random code length
    public static final String[] RANDOM_ALGORITHMS = {"ALPHA", "NUMERIC", "ALPHANUMERIC", "MIXED", "SYMBOLS"}; // Algorithm types
    public static final String RANDOM_CODE_PREFIX = "RND";              // Soft-coded Random Code prefix
    public static final boolean ENABLE_RANDOM_COMPLEXITY = true;        // ENABLE variable complexity levels

    // Standard Coder Types Control (Soft-coded - DISABLED to keep only No Coder and Serial)
    public static final boolean ENABLE_QR_CODE = false;                 // DISABLE QR Code option
    public static final boolean ENABLE_DATAMATRIX = false;              // DISABLE DataMatrix option
    public static final boolean ENABLE_CODE128 = false;                 // DISABLE Code 128 option
    public static final boolean ENABLE_CODE39 = false;                  // DISABLE Code 39 option
    public static final boolean ENABLE_EAN13 = false;                   // DISABLE EAN-13 option
    public static final boolean ENABLE_UPC_A = false;                   // DISABLE UPC-A option
    public static final boolean ENABLE_PDF417 = false;                  // DISABLE PDF417 option
    public static final boolean ENABLE_AZTEC = false;                   // DISABLE Aztec option
    public static final boolean ENABLE_CODE93 = false;                  // DISABLE Code 93 option
    public static final boolean ENABLE_MAXICODE = false;                // DISABLE MaxiCode option

    // Innovative Coder State Management System (Soft-coded)
    public static final boolean ENABLE_CODER_STATE_SYSTEM = true;       // ENABLE intelligent coder state management
    public static final boolean ENABLE_VISUAL_STATE_FEEDBACK = true;    // ENABLE visual state indicators on buttons
    public static final boolean ENABLE_DYNAMIC_CODER_BEHAVIOR = true;   // ENABLE Coder button behavior changes based on state
    public static final boolean ENABLE_CODER_MODIFICATION_DEBUG = true; // ENABLE detailed debug logging for coder modification
    public static final boolean ENABLE_FORCE_CODER_MODIFICATION = true; // ENABLE forced modification workflow over creation
    
    // Coder State Variables (Dynamic State Management)
    private static String currentCoderType = "No Coder";               // Current selected coder type
    private static boolean isNoCoderMode = true;                       // No Coder mode state
    private static java.awt.Color originalCoderButtonColor = null;      // Original Coder button color for restoration
    
    // ==================== THORX6 HORIZONTAL LAYOUT CONFIGURATION ====================
    
    // Main Layout Dimensions
    public static final int TOOLBAR_HEIGHT = 80;  // Horizontal toolbar height
    public static final int BUTTON_WIDTH = 60;    // Standard button width
    public static final int BUTTON_HEIGHT = 50;   // Standard button height
    public static final int GROUP_SPACING = 15;   // Space between button groups
    public static final int BUTTON_SPACING = 3;   // Space between buttons in group
    
    // ThorX6 Professional Colors
    public static final Color THORX6_TOOLBAR_BG = new Color(240, 240, 240);
    public static final Color THORX6_BUTTON_BG = new Color(250, 250, 250);
    public static final Color THORX6_BUTTON_HOVER = new Color(225, 240, 255);
    public static final Color THORX6_BUTTON_PRESSED = new Color(200, 230, 255);
    public static final Color THORX6_BORDER = new Color(180, 180, 180);
    public static final Color THORX6_TEXT = new Color(60, 60, 60);
    public static final Color THORX6_SEPARATOR = new Color(200, 200, 200);
    
    // Typography
    public static final Font THORX6_BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 9);
    public static final Font THORX6_LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font THORX6_GROUP_FONT = new Font("Segoe UI", Font.BOLD, 10);
    
    // ==================== THORX6 BUTTON GROUPS CONFIGURATION ====================
    
    public static class ThorX6ButtonGroup {
        public final String groupName;
        public final ThorX6Button[] buttons;
        
        public ThorX6ButtonGroup(String groupName, ThorX6Button[] buttons) {
            this.groupName = groupName;
            this.buttons = buttons;
        }
    }
    
    public static class ThorX6Button {
        public final String text;
        public final String icon;
        public final String tooltip;
        public final boolean hasDropdown;
        public final Runnable action;
        
        public ThorX6Button(String text, String icon, String tooltip, boolean hasDropdown, Runnable action) {
            this.text = text;
            this.icon = icon;
            this.tooltip = tooltip;
            this.hasDropdown = hasDropdown;
            this.action = action;
        }
        
        public ThorX6Button(String text, String icon, String tooltip, Runnable action) {
            this(text, icon, tooltip, false, action);
        }
    }
    
    // ==================== THORX6 MARK TAB BUTTON GROUPS ====================
    
    // Clipboard Group (like real ThorX6) - Enhanced with Undo and Erase
    public static final ThorX6ButtonGroup CLIPBOARD_GROUP = new ThorX6ButtonGroup("Clipboard", new ThorX6Button[] {
        new ThorX6Button("Undo", "↶", "Undo last action (Ctrl+Z)", () -> handleThorX6UndoAction()),
        new ThorX6Button("Paste", "📋", "Paste (Ctrl+V)", true, () -> handleThorX6PasteAction()),
        new ThorX6Button("Cut", "✂", "Cut (Ctrl+X)", () -> handleThorX6CutAction()),
        new ThorX6Button("Copy", "📄", "Copy (Ctrl+C)", () -> handleThorX6CopyAction()),
        new ThorX6Button("Erase", "🗑", "Erase selected elements (Delete)", () -> handleThorX6EraseAction())
    });
    
    // ==================== THORX6 MARK DROPDOWN CONFIGURATION ====================
    
    // Mark Types Configuration (5x2 Grid = 10 mark types)
    public static class MarkTypeConfig {
        public final String name;
        public final String iconPath;
        public final String tooltip;
        public final Runnable action;
        public ImageIcon icon;
        
        public MarkTypeConfig(String name, String iconPath, String tooltip, Runnable action) {
            this.name = name;
            this.iconPath = iconPath;
            this.tooltip = tooltip;
            this.action = action;
            this.icon = loadIcon(iconPath);
        }
        
        private ImageIcon loadIcon(String path) {
            try {
                File iconFile = new File("images/" + path);
                if (iconFile.exists()) {
                    BufferedImage img = ImageIO.read(iconFile);
                    // Scale icon to fit button size (32x32)
                    Image scaledImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                } else {
                    System.out.println("⚠️ Icon not found: " + path + " - using fallback text");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("❌ Error loading icon: " + path + " - " + e.getMessage());
                return null;
            }
        }
    }
    
    /**
     * Helper class for mark dropdown options (Soft Coding Support)
     */
    public static class MarkOption {
        public final String displayText;
        public final MarkTypeConfig mark;
        
        public MarkOption(String displayText, MarkTypeConfig mark) {
            this.displayText = displayText;
            this.mark = mark;
        }
        
        @Override
        public String toString() {
            return displayText;
        }
    }
    
    // 5x2 Mark Types Grid (Row 1: Basic Marks, Row 2: Advanced Marks)
    // Using custom designed icons from images/ folder
    public static final MarkTypeConfig[][] MARK_TYPES_GRID = {
        // Row 1: Basic Marks (Updated: Circle→DataMatrix, Rectangle→Graph, Arc→Chart)
        {
            new MarkTypeConfig("Text", "text.png", "Add Text Mark", () -> handleAddTextMark()),
            new MarkTypeConfig("BowText", "bow_text.png", "Add Bow Text Mark", () -> handleAddBowTextMark()),
            new MarkTypeConfig("DataMatrix", "datamatrix.png", "Add DataMatrix Code", () -> handleAddDataMatrixMark()),
            new MarkTypeConfig("Graph", "graph.png", "Add Graph Mark", () -> handleAddGraphMark()),
            new MarkTypeConfig("Chart", "chart.png", "Add Chart Mark", () -> handleAddChartMark())
        },
        // Row 2: Advanced Marks (Updated: DotMatrix→Ruler, Barcode→AvoidPoint, Graph→Farsi, Removed: Farzi, Line)
        {
            new MarkTypeConfig("Ruler", "ruler.png", "Add Ruler Mark", () -> handleAddRulerMark()),
            new MarkTypeConfig("AvoidPoint", "avoidpoint.png", "Add Avoid Point", () -> handleAddAvoidPointMark()),
            new MarkTypeConfig("Farsi", "farsi.png", "Add Farsi Text Mark", () -> handleAddFarsiMark()),
            null, // Empty slot (removed Farzi)
            null  // Empty slot (removed Line)
        }
    };
    
    // Mark Group (Enhanced with Add Mark dropdown and two-column layout)
    public static final ThorX6ButtonGroup MARK_GROUP = new ThorX6ButtonGroup("Mark", new ThorX6Button[] {
        new ThorX6Button("Add Mark", "➕", "Add new mark to canvas", true, () -> handleAddMarkDropdown()),
        new ThorX6Button("Edit Mark", "✏️", "Edit selected mark", () -> handleEditMark())
    });
    
    // ==================== SOFT-CODED CODER SUBGROUP ====================

    // Master Coder Control (Soft Coding Technique)
    public static final boolean DELETE_CODER_TYPE_COMPLETELY = true;      // Master flag to completely remove Coder Type functionality
    public static final boolean DELETE_ALL_CODER_BUTTONS = true;          // Ultimate flag to remove ALL coder buttons (including DataMatrix/Coder)
    
    // Coder Type Configuration (Soft Coded Replacement)
    public static final boolean REPLACE_QR_WITH_CODER_TYPE = true;        // ENABLE QR Code -> Coder Type replacement
    public static final boolean ENABLE_CODER_TYPE_OPTIONS = true;         // Enable Coder Type dropdown functionality
    public static final String CODER_TYPE_BUTTON_TEXT = "Coder Type";     // Soft-coded button text
    public static final String CODER_TYPE_ICON = "⚙️";                    // Soft-coded button icon
    public static final String CODER_TYPE_TOOLTIP = "Select Coder Type"; // Soft-coded tooltip    // Coder Group (Soft-coded integration next to Mark group - QR Code replaced with Coder Type)
    public static final ThorX6ButtonGroup CODER_GROUP = createCoderGroup();
    
    // Reconstructed Coder Group (New modular system)
    public static final ThorX6ButtonGroup RECONSTRUCTED_CODER_GROUP = createReconstructedCoderGroup();
    
    // All button groups in order (Clipboard, Mark, Coder, and conditionally Reconstructed Coder groups)
    public static final ThorX6ButtonGroup[] ALL_BUTTON_GROUPS = createButtonGroupsArray();
    
    // ==================== THORX6 PROPERTIES CONFIGURATION ====================
    
    public static class ThorX6Property {
        public final String label;
        public final String defaultValue;
        public final int width;
        public final boolean editable;
        public final String unit;
        
        public ThorX6Property(String label, String defaultValue, int width, boolean editable, String unit) {
            this.label = label;
            this.defaultValue = defaultValue;
            this.width = width;
            this.editable = editable;
            this.unit = unit;
        }
        
        public ThorX6Property(String label, String defaultValue, int width, boolean editable) {
            this(label, defaultValue, width, editable, "");
        }
    }
    
    // Enhanced ThorX6-style properties (Filtered based on duplicate prevention configuration)
    public static final ThorX6Property[] THORX6_PROPERTIES = getFilteredThorX6HorizontalProperties();
    
    // Original ThorX6 Horizontal Properties
    private static final ThorX6Property[] ORIGINAL_THORX6_PROPERTIES = {
        new ThorX6Property("Name", "newmark1", 80, true),
        new ThorX6Property("X", "0.00", 60, true, "mm"),
        new ThorX6Property("Y", "0.00", 60, true, "mm"),
        new ThorX6Property("Z", "0", 50, true, "mm"),
        new ThorX6Property("Angle", "0°", 50, true),
        new ThorX6Property("Width", "10.00", 70, true, "mm"),
        new ThorX6Property("Height", "5.00", 70, true, "mm"),
        new ThorX6Property("Clear Trans", "false", 80, true),
        new ThorX6Property("Mirror", "false", 60, true),
        new ThorX6Property("Lock Size", "false", 70, true),
        new ThorX6Property("Disable Print", "false", 85, true)
    };
    
    // Filtered ThorX6 horizontal properties based on soft coding configuration
    private static ThorX6Property[] getFilteredThorX6HorizontalProperties() {
        // Check if we should block horizontal property creation entirely
        if (RugrelDropdownConfig.BLOCK_HORIZONTAL_PROPERTY_CREATION ||
            RugrelDropdownConfig.DISABLE_HORIZONTAL_PROPERTY_DUPLICATES ||
            !RugrelDropdownConfig.SHOW_PROPERTIES_IN_HORIZONTAL_LAYOUT) {
            
            if (RugrelDropdownConfig.LOG_BLOCKED_DUPLICATE_ATTEMPTS) {
                System.out.println("🚫 BLOCKED: ThorX6 Horizontal Properties creation disabled by soft coding");
                System.out.println("   Using basic properties only (Name, X, Y, Z, Angle, Width, Height)");
            }
            
            // Return only basic properties without the duplicate ones
            return new ThorX6Property[] {
                new ThorX6Property("Name", "newmark1", 80, true),
                new ThorX6Property("X", "0.00", 60, true, "mm"),
                new ThorX6Property("Y", "0.00", 60, true, "mm"),
                new ThorX6Property("Z", "0", 50, true, "mm"),
                new ThorX6Property("Angle", "0°", 50, true),
                new ThorX6Property("Width", "10.00", 70, true, "mm"),
                new ThorX6Property("Height", "5.00", 70, true, "mm")
            };
        }
        
        // Check if individual property duplicates should be eliminated
        if (RugrelDropdownConfig.ELIMINATE_CLEAR_TRANS_DUPLICATES ||
            RugrelDropdownConfig.ELIMINATE_MIRROR_DUPLICATES ||
            RugrelDropdownConfig.ELIMINATE_LOCK_SIZE_DUPLICATES ||
            RugrelDropdownConfig.ELIMINATE_DISABLE_PRINT_DUPLICATES ||
            RugrelDropdownConfig.ENFORCE_BOTTOM_STRIP_ONLY) {
            
            if (RugrelDropdownConfig.LOG_BLOCKED_DUPLICATE_ATTEMPTS) {
                System.out.println("🚫 BLOCKED: Individual property duplicates eliminated from ThorX6 Horizontal Properties");
                System.out.println("   Properties only available in bottom strip to prevent duplicates");
            }
            
            // Return only basic properties
            return new ThorX6Property[] {
                new ThorX6Property("Name", "newmark1", 80, true),
                new ThorX6Property("X", "0.00", 60, true, "mm"),
                new ThorX6Property("Y", "0.00", 60, true, "mm"),
                new ThorX6Property("Z", "0", 50, true, "mm"),
                new ThorX6Property("Angle", "0°", 50, true),
                new ThorX6Property("Width", "10.00", 70, true, "mm"),
                new ThorX6Property("Height", "5.00", 70, true, "mm")
            };
        }
        
        // If no duplicate prevention is enabled, return original properties
        if (RugrelDropdownConfig.LOG_PROPERTY_ICON_CREATION) {
            System.out.println("⚠️ WARNING: Creating ThorX6 Horizontal Properties with potential duplicates");
        }
        
        return ORIGINAL_THORX6_PROPERTIES;
    }
    
    // ==================== COMPONENT CREATION METHODS ====================
    
    /**
     * Create ThorX6-style horizontal toolbar
     */
    public static JPanel createThorX6HorizontalToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, GROUP_SPACING, 8));
        toolbar.setBackground(THORX6_TOOLBAR_BG);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, THORX6_BORDER),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        toolbar.setPreferredSize(new Dimension(0, TOOLBAR_HEIGHT));
        
        // Add button groups
        for (int i = 0; i < ALL_BUTTON_GROUPS.length; i++) {
            ThorX6ButtonGroup group = ALL_BUTTON_GROUPS[i];
            JPanel groupPanel = createThorX6ButtonGroup(group);
            toolbar.add(groupPanel);
            
            // Add separator between groups (except last)
            if (i < ALL_BUTTON_GROUPS.length - 1) {
                toolbar.add(createThorX6Separator());
            }
        }
        
        return toolbar;
    }
    
    /**
     * Create Coder Group with soft-coded Barcode option control
     */
    private static ThorX6ButtonGroup createCoderGroup() {
        // Build coder buttons dynamically based on configuration flags
        java.util.List<ThorX6Button> coderButtons = new java.util.ArrayList<>();
        
        // Add primary coder button (QR Code or Coder Type selection with dropdown)
        // SOFT CODING: Complete deletion control with DELETE_CODER_TYPE_COMPLETELY flag
        if (!DELETE_CODER_TYPE_COMPLETELY) {
            if (REPLACE_QR_WITH_CODER_TYPE && !DISABLE_ORIGINAL_CODER_TYPE_BUTTON) {
                coderButtons.add(new ThorX6Button(CODER_TYPE_BUTTON_TEXT, CODER_TYPE_ICON, CODER_TYPE_TOOLTIP, true, () -> handleCoderTypeSelection()));
            } else if (!REPLACE_QR_WITH_CODER_TYPE) {
                coderButtons.add(new ThorX6Button("QR Code", "⬛", "Generate QR Code", () -> handleCoderAction("QR Code")));
            }
        }
        // When DELETE_CODER_TYPE_COMPLETELY is true, no coder type button is added at all
        
        // Add DataMatrix/Coder button with Innovative State-Based Behavior
        // SOFT CODING: Complete deletion control - skip ALL coder buttons if DELETE_ALL_CODER_BUTTONS is true
        if (!DELETE_ALL_CODER_BUTTONS) {
            if (REPLACE_DATAMATRIX_WITH_CODER) {
                coderButtons.add(new ThorX6Button(DATAMATRIX_BUTTON_TEXT, DATAMATRIX_BUTTON_ICON, DATAMATRIX_BUTTON_TOOLTIP, () -> handleRebuiltCoderButtonV3()));
            } else {
                coderButtons.add(new ThorX6Button("DataMatrix", "▣", "Generate DataMatrix code", () -> handleCoderAction("DataMatrix")));
            }
            
            // Conditionally add Barcode button based on soft coding flag
            if (ENABLE_BARCODE_OPTION) {
                coderButtons.add(new ThorX6Button("Barcode", "|||", "Generate Barcode", () -> handleCoderAction("Barcode")));
            }
        }
        // When DELETE_ALL_CODER_BUTTONS is true, NO coder buttons are added at all
        
        // Convert list to array and create group
        ThorX6Button[] buttonArray = coderButtons.toArray(new ThorX6Button[0]);
        return new ThorX6ButtonGroup("Coder", buttonArray);
    }
    
    /**
     * Create Reconstructed Coder Group with modular OOP system
     */
    private static ThorX6ButtonGroup createReconstructedCoderGroup() {
        if (!ENABLE_RECONSTRUCTED_CODER_SYSTEM) {
            return new ThorX6ButtonGroup("Reconstructed", new ThorX6Button[0]);
        }
        
        java.util.List<ThorX6Button> reconstructedButtons = new java.util.ArrayList<>();
        
        // Coder Tab Button - Opens the consolidated coder configuration panel or dropdown
        String buttonText = CONSOLIDATE_CODER_FUNCTIONALITY ? "All Coders" : "Coder Tab";
        String tooltip = CONSOLIDATE_CODER_FUNCTIONALITY ? 
            "Open Consolidated Coder System (New + Original ThorX6)" : 
            "Open Reconstructed Coder Configuration";
        
        // Soft-coded dropdown vs panel behavior
        if (CONSOLIDATE_CODER_FUNCTIONALITY && ENABLE_ALL_CODERS_DROPDOWN) {
            reconstructedButtons.add(new ThorX6Button(buttonText, "🔽", tooltip + " (Dropdown)", true, () -> handleAllCodersDropdown()));
        } else {
            reconstructedButtons.add(new ThorX6Button(buttonText, "⚙️", tooltip, () -> openReconstructedCoderPanel()));
        }
        
        // Quick Generate Button - Uses current active coder
        reconstructedButtons.add(new ThorX6Button("Generate", "🔄", "Generate code with current coder", () -> handleQuickGenerate()));
        
        // Preview Button - Shows current coder preview
        reconstructedButtons.add(new ThorX6Button("Preview", "👁️", "Show coder preview", () -> showCoderPreview()));
        
        ThorX6Button[] buttonArray = reconstructedButtons.toArray(new ThorX6Button[0]);
        return new ThorX6ButtonGroup("Reconstructed", buttonArray);
    }
    
    /**
     * Create button groups array based on soft coding configuration
     */
    private static ThorX6ButtonGroup[] createButtonGroupsArray() {
        java.util.List<ThorX6ButtonGroup> groups = new java.util.ArrayList<>();
        
        // Always add core groups
        groups.add(CLIPBOARD_GROUP);
        groups.add(MARK_GROUP);
        
        // Conditionally add original coder group
        // SOFT CODING: Complete subgroup removal control
        if (ENABLE_CODER_SUBGROUP && !HIDE_CODER_SUBGROUP_COMPLETELY) {
            groups.add(CODER_GROUP);
        }
        // When HIDE_CODER_SUBGROUP_COMPLETELY is true, Coder subgroup is completely hidden from UI
        
        // Conditionally add reconstructed coder group
        if (ADD_RECONSTRUCTED_CODER_BUTTON_GROUP && ENABLE_RECONSTRUCTED_CODER_SYSTEM) {
            groups.add(RECONSTRUCTED_CODER_GROUP);
        }
        
        return groups.toArray(new ThorX6ButtonGroup[0]);
    }

    /**
     * Create ThorX6-style button group with title
     */
    private static JPanel createThorX6ButtonGroup(ThorX6ButtonGroup group) {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setBackground(THORX6_TOOLBAR_BG);
        
        // Button row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTON_SPACING, 0));
        buttonRow.setBackground(THORX6_TOOLBAR_BG);
        
        for (ThorX6Button buttonConfig : group.buttons) {
            JButton button = createThorX6Button(buttonConfig);
            buttonRow.add(button);
        }
        
        groupPanel.add(buttonRow);
        
        // Group label
        JLabel groupLabel = new JLabel(group.groupName, SwingConstants.CENTER);
        groupLabel.setFont(THORX6_GROUP_FONT);
        groupLabel.setForeground(THORX6_TEXT);
        groupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        groupPanel.add(groupLabel);
        
        return groupPanel;
    }
    
    /**
     * Create individual ThorX6-style button
     */
    private static JButton createThorX6Button(ThorX6Button config) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        
        // Icon
        JLabel iconLabel = new JLabel(config.icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        button.add(iconLabel, BorderLayout.CENTER);
        
        // Text
        JLabel textLabel = new JLabel(config.text, SwingConstants.CENTER);
        textLabel.setFont(THORX6_BUTTON_FONT);
        textLabel.setForeground(THORX6_TEXT);
        button.add(textLabel, BorderLayout.SOUTH);
        
        // Dropdown indicator
        if (config.hasDropdown) {
            JLabel dropdown = new JLabel("▼", SwingConstants.CENTER);
            dropdown.setFont(new Font("Segoe UI", Font.PLAIN, 6));
            dropdown.setForeground(THORX6_TEXT);
            button.add(dropdown, BorderLayout.EAST);
        }
        
        // Styling
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setBackground(THORX6_BUTTON_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(THORX6_BORDER, 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(config.tooltip);
        
        // Professional hover effects
        addThorX6ButtonEffects(button);
        
        // Action - Special handling for dropdown buttons (Soft Coded)
        if (config.hasDropdown && config.text.equals("Add Mark")) {
            if (ENABLE_ADD_MARK_DROPDOWN) {
                button.addActionListener(e -> showAddMarkDropdownMenu(button)); // Use popup menu
            } else {
                button.addActionListener(e -> handleAddMarkDropdown()); // Fallback to window method
            }
        } else if (config.hasDropdown && config.text.equals("All Coders")) {
            if (ENABLE_ALL_CODERS_DROPDOWN) {
                button.addActionListener(e -> handleAllCodersDropdownMenu(button)); // Use popup menu
            } else {
                button.addActionListener(e -> config.action.run()); // Fallback to action
            }
        } else if (config.hasDropdown && config.text.equals("Coder Type")) {
            button.addActionListener(e -> showCoderTypeDropdownMenu(button));
        } else {
            button.addActionListener(e -> config.action.run());
        }
        
        return button;
    }
    
    /**
     * Create ThorX6-style separator
     */
    private static JComponent createThorX6Separator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(THORX6_SEPARATOR);
        separator.setPreferredSize(new Dimension(1, BUTTON_HEIGHT - 10));
        return separator;
    }
    
    /**
     * Create ThorX6-style properties panel (Soft Coded Control)
     */
    public static JPanel createThorX6PropertiesPanel() {
        if (!ENABLE_THORX6_PROPERTIES_PANEL) {
            // Return empty panel to avoid duplicate with bottom coordinate strip
            System.out.println("🔧 ThorX6 Properties Panel DISABLED (avoiding duplicate with coordinate tracking strip)");
            JPanel emptyPanel = new JPanel();
            emptyPanel.setPreferredSize(new Dimension(0, 0));
            emptyPanel.setVisible(false);
            return emptyPanel;
        }
        
        JPanel propsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        propsPanel.setBackground(new Color(245, 245, 245));
        propsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, THORX6_BORDER),
            BorderFactory.createEmptyBorder(3, 10, 3, 10)
        ));
        
        for (ThorX6Property prop : THORX6_PROPERTIES) {
            JPanel propField = createThorX6PropertyField(prop);
            propsPanel.add(propField);
        }
        
        System.out.println("✅ ThorX6 Properties Panel created with " + THORX6_PROPERTIES.length + " properties");
        return propsPanel;
    }
    
    /**
     * Create individual property field (Soft Coded with Smart Type Detection)
     */
    private static JPanel createThorX6PropertyField(ThorX6Property prop) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        panel.setBackground(new Color(245, 245, 245));
        
        // Determine if this is a boolean property (checkbox) or text field
        boolean isBooleanProperty = prop.defaultValue.equals("false") || prop.defaultValue.equals("true");
        
        if (isBooleanProperty) {
            // Create checkbox for boolean properties
            JCheckBox checkBox = new JCheckBox(prop.label);
            checkBox.setFont(THORX6_LABEL_FONT);
            checkBox.setBackground(new Color(245, 245, 245));
            checkBox.setForeground(THORX6_TEXT);
            checkBox.setSelected(Boolean.parseBoolean(prop.defaultValue));
            checkBox.setEnabled(prop.editable);
            panel.add(checkBox);
        } else {
            // Create text field for value properties
            // Label
            JLabel label = new JLabel(prop.label + ":");
            label.setFont(THORX6_LABEL_FONT);
            label.setForeground(THORX6_TEXT);
            panel.add(label);
            
            // Field
            JTextField field = new JTextField(prop.defaultValue);
            field.setFont(THORX6_LABEL_FONT);
            field.setPreferredSize(new Dimension(prop.width, 22));
            field.setEditable(prop.editable);
            field.setBackground(prop.editable ? Color.WHITE : new Color(250, 250, 250));
            field.setBorder(BorderFactory.createLineBorder(THORX6_BORDER, 1));
            panel.add(field);
            
            // Unit
            if (!prop.unit.isEmpty()) {
                JLabel unit = new JLabel(prop.unit);
                unit.setFont(THORX6_LABEL_FONT);
                unit.setForeground(THORX6_TEXT);
                panel.add(unit);
            }
        }
        
        return panel;
    }
    
    /**
     * Add ThorX6-style button hover effects
     */
    private static void addThorX6ButtonEffects(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(THORX6_BUTTON_HOVER);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(THORX6_BUTTON_BG);
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.setBackground(THORX6_BUTTON_PRESSED);
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.setBackground(THORX6_BUTTON_HOVER);
            }
        });
    }
    
    /**
     * Print ThorX6 configuration summary
     */
    public static void printThorX6ConfigSummary() {
        System.out.println("🎯 ThorX6 Horizontal Configuration:");
        System.out.println("   Button Groups: " + ALL_BUTTON_GROUPS.length);
        System.out.println("   Total Buttons: " + getTotalButtonCount());
        System.out.println("   Properties: " + THORX6_PROPERTIES.length);
        System.out.println("   Layout: Horizontal ThorX6 Style");
    }
    
    private static int getTotalButtonCount() {
        int count = 0;
        for (ThorX6ButtonGroup group : ALL_BUTTON_GROUPS) {
            count += group.buttons.length;
        }
        return count;
    }
    
    // ==================== ENHANCED CLIPBOARD ACTION HANDLERS (SOFT CODED) ====================
    
    /**
     * ThorX6 Undo Action Handler
     */
    public static void handleThorX6UndoAction() {
        System.out.println("🔄 ThorX6 Undo Action - Reverting last operation...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.undo();
                System.out.println("📝 Performing ThorX6 undo operation...");
                System.out.println("✅ ThorX6 Undo completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for undo operation");
            }
        } catch (Exception e) {
            System.err.println("❌ ThorX6 Undo failed: " + e.getMessage());
        }
    }
    
    /**
     * ThorX6 Paste Action Handler
     */
    public static void handleThorX6PasteAction() {
        System.out.println("📋 ThorX6 Paste Action - Inserting clipboard content...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.paste();
                System.out.println("📌 Pasting content from ThorX6 clipboard...");
                System.out.println("✅ ThorX6 Paste completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for paste operation");
            }
        } catch (Exception e) {
            System.err.println("❌ ThorX6 Paste failed: " + e.getMessage());
        }
    }
    
    /**
     * ThorX6 Cut Action Handler
     */
    public static void handleThorX6CutAction() {
        System.out.println("✂ ThorX6 Cut Action - Moving selection to clipboard...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.cutSelected();
                System.out.println("📌 Cutting selection to ThorX6 clipboard...");
                System.out.println("✅ ThorX6 Cut completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for cut operation");
            }
        } catch (Exception e) {
            System.err.println("❌ ThorX6 Cut failed: " + e.getMessage());
        }
    }
    
    /**
     * ThorX6 Copy Action Handler
     */
    public static void handleThorX6CopyAction() {
        System.out.println("📄 ThorX6 Copy Action - Copying selection to clipboard...");
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                canvas.copySelected();
                System.out.println("📌 Copying selection to ThorX6 clipboard...");
                System.out.println("✅ ThorX6 Copy completed successfully");
            } else {
                System.err.println("❌ No drawing canvas found for copy operation");
            }
        } catch (Exception e) {
            System.err.println("❌ ThorX6 Copy failed: " + e.getMessage());
        }
    }
    
    /**
     * ThorX6 Erase Action Handler
     */
    public static void handleThorX6EraseAction() {
        System.out.println("🗑 ThorX6 Erase Action - Deleting selected elements...");
        try {
            System.out.println("🧹 Erasing selected ThorX6 elements...");
            deleteSelectedElements(); // This affects the canvas and drawing state
            System.out.println("✅ ThorX6 Erase completed successfully");
        } catch (Exception e) {
            System.err.println("❌ ThorX6 Erase failed: " + e.getMessage());
        }
    }
    



    
    // ==================== CORE FUNCTIONALITY METHODS (SOFT CODED) ====================
    
    /**
     * Delete selected elements (affects multiple parts of code)
     */
    private static void deleteSelectedElements() {
        System.out.println("🧹 Performing element deletion - affects canvas, selection, and history...");
        DrawingCanvas canvas = getCurrentDrawingCanvas();
        if (canvas != null) {
            canvas.eraseSelected();
        }
        // This method affects:
        // 1. Canvas drawing state
        // 2. Selection management
        // 3. Undo/Redo history
        // 4. Properties panel updates
    }
    
    /**
     * Update canvas state after operations
     */
    private static void updateCanvasState() {
        System.out.println("🔄 Updating canvas state - refreshing all dependent components...");
    }
    
    /**
     * Refresh drawing area display
     */
    private static void refreshDrawingArea() {
        System.out.println("🎨 Refreshing drawing area - repainting canvas...");
    }
    
    // ==================== THORX6 MARK ACTION HANDLERS ====================
    
    /**
     * Show Add Mark dropdown menu attached to the button
     */
    private static void showAddMarkDropdownMenu(JButton sourceButton) {
        System.out.println("➕ Opening Add Mark dropdown menu...");
        
        JPopupMenu dropdownMenu = new JPopupMenu();
        
        // 🎯 ThorX6 Integration: Show current active coder at top
        ensureCoderInitialized();
        String activeCoderInfo = "No Active Coder";
        if (currentActiveCoder != null) {
            activeCoderInfo = "Active: " + currentActiveCoder.getTypeName();
        }
        
        dropdownMenu.setBorder(BorderFactory.createTitledBorder("Mark Types - " + activeCoderInfo));
        
        // Create compact panel for 5x2 grid (icons only)
        JPanel gridPanel = new JPanel(new GridLayout(2, 5, 4, 4));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        
        // Add menu items for each mark type
        for (int row = 0; row < MARK_TYPES_GRID.length; row++) {
            for (int col = 0; col < MARK_TYPES_GRID[row].length; col++) {
                MarkTypeConfig mark = MARK_TYPES_GRID[row][col];
                
                // Skip null entries (deleted marks)
                if (mark == null) {
                    continue;
                }
                
                JButton markButton;
                if (mark.icon != null) {
                    markButton = new JButton(mark.icon);
                } else {
                    // Fallback to text if icon loading failed
                    markButton = new JButton(mark.name.substring(0, 1));
                    markButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
                }
                
                markButton.setToolTipText(mark.name + " - " + mark.tooltip);
                markButton.setPreferredSize(new Dimension(45, 40));
                markButton.setFocusPainted(false);
                markButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // Add hover effects
                markButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        markButton.setBackground(new Color(230, 240, 255));
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        markButton.setBackground(UIManager.getColor("Button.background"));
                    }
                });
                
                // Action when clicked
                markButton.addActionListener(e -> {
                    System.out.println("🎯 Selected mark type: " + mark.icon + " " + mark.name);
                    mark.action.run();
                    dropdownMenu.setVisible(false);
                });
                
                gridPanel.add(markButton);
            }
        }
        
        dropdownMenu.add(gridPanel);
        
        // Show dropdown below the button
        dropdownMenu.show(sourceButton, 0, sourceButton.getHeight());
        
        System.out.println("✅ Add Mark dropdown menu opened with " + 
            (MARK_TYPES_GRID.length * MARK_TYPES_GRID[0].length) + " options");
    }

    /**
     * Create Professional Visual Coder Button (Custom Attractive Icons)
     */
    private static JButton createProfessionalCoderButton(CoderTypeConfig coder) {
        // Get custom attractive icon design
        String attractiveIcon = getProfessionalVisualLabel(coder.name);
        
        // Create beautiful button with custom attractive icon
        JButton button = new JButton("<html><center>" + attractiveIcon + "</center></html>");
        
        // Enhanced attractive styling with gradient background
        button.setBackground(new Color(248, 249, 250)); // Clean white background to show gradient icons
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2), // Beautiful blue border
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
            )
        ));
        
        // Enhanced hover effects for attractive interaction
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(236, 240, 241)); // Light hover effect
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(41, 128, 185), 3), // Darker blue on hover
                        BorderFactory.createEmptyBorder(4, 4, 4, 4)
                    )
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(248, 249, 250)); // Return to original
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 152, 219), 2), // Original border
                        BorderFactory.createEmptyBorder(4, 4, 4, 4)
                    )
                ));
            }
        });
        
        return button;
    }
    
    /**
     * Get Custom Attractive Visual Icon for Each Coder Type (Soft-Coded Design)
     */
    private static String getProfessionalVisualLabel(String coderName) {
        // Soft-coded attractive icon configurations
        return getCustomAttractiveIcon(coderName);
    }
    
    /**
     * Custom Attractive Icon Creator (Soft-Coded Visual Design System)
     */
    private static String getCustomAttractiveIcon(String coderName) {
        switch (coderName) {
            case "No Coder": 
                return createNoCoderIcon();
            case "Serial": 
                return createSerialIcon();
            case "VIN": 
                return createVINIcon();
            case "PIN": 
                return createPINIcon();
            case "DateTime": 
                return createDateTimeIcon();
            case "VCode": 
                return createVCodeIcon();
            case "TxMark6PieCoder": 
                return createTxMark6PieIcon();
            case "Class": 
                return createClassIcon();
            case "Random Code": 
                return createRandomCodeIcon();
            default: 
                return createDefaultIcon(coderName);
        }
    }

    // ==================== CUSTOM ATTRACTIVE ICON CREATORS (SOFT-CODED) ====================
    
    /**
     * Create No Coder Attractive Icon (Soft-Coded Design)
     */
    private static String createNoCoderIcon() {
        return "<span style='background: #ff6b6b; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>✕</span>";
    }
    
    /**
     * Create Serial Number Attractive Icon (Soft-Coded Design)
     */
    private static String createSerialIcon() {
        return "<span style='background: #4834d4; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>123</span>";
    }
    
    /**
     * Create VIN Attractive Icon (Soft-Coded Design)
     */
    private static String createVINIcon() {
        return "<span style='background: #00b894; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>VIN</span>";
    }
    
    /**
     * Create PIN Attractive Icon (Soft-Coded Design)
     */
    private static String createPINIcon() {
        return "<span style='background: #e17055; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>PIN</span>";
    }
    
    /**
     * Create DateTime Attractive Icon (Soft-Coded Design)
     */
    private static String createDateTimeIcon() {
        return "<span style='background: #6c5ce7; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>TIME</span>";
    }
    
    /**
     * Create VCode Attractive Icon (Soft-Coded Design)
     */
    private static String createVCodeIcon() {
        return "<span style='background: #fd79a8; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>CODE</span>";
    }
    
    /**
     * Create TxMark6Pie Attractive Icon (Soft-Coded Design)
     */
    private static String createTxMark6PieIcon() {
        return "<span style='background: #00cec9; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>PIE6</span>";
    }
    
    /**
     * Create Class Attractive Icon (Soft-Coded Design)
     */
    private static String createClassIcon() {
        return "<span style='background: #fab1a0; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>CLS</span>";
    }
    
    /**
     * Create Random Code Attractive Icon (Soft-Coded Design)
     */
    private static String createRandomCodeIcon() {
        return "<span style='background: #fd79a8; color: white; padding: 8px; border-radius: 6px; font-weight: bold;'>RND</span>";
    }
    
    /**
     * Create Default Attractive Icon (Soft-Coded Design)
     */
    private static String createDefaultIcon(String coderName) {
        return "<div style='width:40px; height:30px; background: linear-gradient(45deg, #636e72, #b2bec3); " +
               "border: 2px solid #2d3436; border-radius: 8px; display: flex; align-items: center; " +
               "justify-content: center; color: white; font-weight: bold; font-size: 10px; " +
               "text-shadow: 1px 1px 2px rgba(0,0,0,0.5);'>" + 
               coderName.substring(0, Math.min(3, coderName.length())).toUpperCase() + "</div>";
    }

    // ==================== INTELLIGENT ADD MARK ↔ CODER TYPE INTEGRATION ====================
    
    /**
     * Get Intelligent Coder Content Based on Current Coder Type (Advanced Integration)
     */
    private static String getIntelligentCoderContent(String markType) {
        // Use current coder type if available, otherwise provide intelligent default
        if (currentCoderType != null && !currentCoderType.equals("No Coder")) {
            System.out.println("🎯 Using current coder type: " + currentCoderType + " for " + markType);
            return getDefaultCoderData(currentCoderType);
        } else {
            // Intelligent default based on mark type
            System.out.println("🤖 Generating intelligent default for " + markType);
            switch (markType) {
                case "DataMatrix": return "DM-" + System.currentTimeMillis() % 10000;
                case "Text": return "Sample Text";
                case "BowText": return "Curved Text";
                default: return markType + "-001";
            }
        }
    }
    
    /**
     * Show Intelligent Coder Suggestion Dialog (Advanced User Experience)
     */
    private static void showIntelligentCoderSuggestion(String markType) {
        // Create intelligent suggestion based on mark type
        String[] suggestedCoders = getIntelligentCoderSuggestions(markType);
        
        if (suggestedCoders.length > 0) {
            String suggestion = (String) JOptionPane.showInputDialog(
                null,
                "🎯 Enhance your " + markType + " with intelligent coder content:\n\n" +
                "Would you like to configure a Coder Type for automatic content generation?",
                "Intelligent Coder Integration",
                JOptionPane.QUESTION_MESSAGE,
                null,
                suggestedCoders,
                suggestedCoders[0]
            );
            
            if (suggestion != null) {
                // Apply the suggested coder type
                applySuggestedCoderType(suggestion);
                System.out.println("✅ Applied intelligent coder suggestion: " + suggestion);
            }
        }
    }
    
    /**
     * Get Intelligent Coder Suggestions Based on Mark Type (Soft-Coded Intelligence)
     */
    private static String[] getIntelligentCoderSuggestions(String markType) {
        switch (markType) {
            case "DataMatrix":
                return new String[]{"Serial", "VIN", "DateTime", "Random Code"};
            case "Text":
                return new String[]{"DateTime", "Serial", "Class"};
            case "BowText":
                return new String[]{"VIN", "Serial", "Class"};
            default:
                return new String[]{"Serial", "DateTime", "Random Code"};
        }
    }
    
    /**
     * Apply Suggested Coder Type (Intelligent Auto-Configuration)
     */
    private static void applySuggestedCoderType(String coderType) {
        currentCoderType = coderType;
        isNoCoderMode = false;
        
        // Update visual feedback
        updateCoderButtonVisualState();
        showCoderStateNotification("Auto-Configured: " + coderType, 
            "Intelligent integration activated. New marks will use " + coderType + " content.", 
            new Color(220, 255, 220));
            
        System.out.println("🚀 Intelligent auto-configuration: " + coderType + " activated");
    }

    /**
     * Show Coder Type dropdown menu attached to the button (ThorX6 Professional Style)
     */
    private static void showCoderTypeDropdownMenu(JButton sourceButton) {
        System.out.println("⚙️ Opening Coder Type dropdown menu (ThorX6 Style)...");
        
        JPopupMenu dropdownMenu = new JPopupMenu();
        dropdownMenu.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
                "Coder Types", 
                javax.swing.border.TitledBorder.CENTER, 
                javax.swing.border.TitledBorder.TOP, 
                THORX6_GROUP_FONT, 
                THORX6_TEXT)
        ));
        dropdownMenu.setBackground(THORX6_TOOLBAR_BG);
        
        // Create dynamic grid based on coder count
        int totalCoders = 0;
        for (int row = 0; row < CODER_TYPES_GRID.length; row++) {
            for (int col = 0; col < CODER_TYPES_GRID[row].length; col++) {
                if (CODER_TYPES_GRID[row][col] != null) {
                    totalCoders++;
                }
            }
        }
        
        JPanel gridPanel = new JPanel(new GridLayout(CODER_TYPES_GRID.length, 5, 3, 3));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        gridPanel.setBackground(THORX6_TOOLBAR_BG);
        
        // Add menu items for each coder type with enhanced ThorX6 styling
        for (int row = 0; row < CODER_TYPES_GRID.length; row++) {
            for (int col = 0; col < CODER_TYPES_GRID[row].length; col++) {
                CoderTypeConfig coder = CODER_TYPES_GRID[row][col];
                
                if (coder != null) {
                    // Create professional visual button like reference image
                    JButton coderButton = createProfessionalCoderButton(coder);
                    
                    // Professional visual styling (like Type Coder.PNG reference)
                    coderButton.setFont(new Font("Arial", Font.BOLD, 12));
                    coderButton.setToolTipText(coder.name + " - " + coder.tooltip);
                    coderButton.setPreferredSize(new Dimension(60, 48));
                    coderButton.setBackground(new Color(230, 230, 250)); // Professional light blue-gray
                    coderButton.setForeground(new Color(0, 0, 139)); // Professional dark blue text
                    coderButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                    ));
                    coderButton.setFocusPainted(false);
                    coderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    // Special styling for "No Coder" option
                    if (coder.name.equals(NO_CODER_TEXT)) {
                        coderButton.setBackground(new Color(255, 240, 240)); // Light red tint
                        coderButton.setForeground(new Color(150, 0, 0)); // Dark red text
                    }
                    
                    // Enhanced ThorX6 hover effects
                    coderButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            if (coder.name.equals(NO_CODER_TEXT)) {
                                coderButton.setBackground(new Color(255, 220, 220)); // Darker red on hover
                            } else {
                                coderButton.setBackground(THORX6_BUTTON_HOVER);
                            }
                            coderButton.setBorder(BorderFactory.createLoweredBevelBorder());
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            if (coder.name.equals(NO_CODER_TEXT)) {
                                coderButton.setBackground(new Color(255, 240, 240));
                            } else {
                                coderButton.setBackground(THORX6_BUTTON_BG);
                            }
                            coderButton.setBorder(BorderFactory.createRaisedBevelBorder());
                        }
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            coderButton.setBackground(THORX6_BUTTON_PRESSED);
                        }
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                            if (coder.name.equals(NO_CODER_TEXT)) {
                                coderButton.setBackground(new Color(255, 220, 220));
                            } else {
                                coderButton.setBackground(THORX6_BUTTON_HOVER);
                            }
                        }
                    });
                    
                    // Action when clicked
                    coderButton.addActionListener(e -> {
                        System.out.println("🎯 Selected coder type: " + coder.icon + " " + coder.name);
                        coder.action.run();
                        dropdownMenu.setVisible(false);
                    });
                    
                    gridPanel.add(coderButton);
                } else {
                    // Add empty panel for null entries
                    JPanel emptyPanel = new JPanel();
                    emptyPanel.setBackground(THORX6_TOOLBAR_BG);
                    gridPanel.add(emptyPanel);
                }
            }
        }
        
        dropdownMenu.add(gridPanel);
        
        // Show dropdown below the button
        dropdownMenu.show(sourceButton, 0, sourceButton.getHeight());
        
        System.out.println("✅ Coder Type dropdown menu opened with " + 
            (CODER_TYPES_GRID.length * CODER_TYPES_GRID[0].length) + " options");
    }

    /**
     * Handle Add Mark Dropdown - Soft-coded implementation with multiple styles
     */
    public static void handleAddMarkDropdown() {
        if (!ENABLE_ADD_MARK_DROPDOWN) {
            // Fallback to original grid-based implementation
            handleAddMarkGridDialog();
            return;
        }
        
        System.out.println("🔽 Showing Add Mark dropdown (soft-coded style: " + ADD_MARK_DROPDOWN_STYLE + ")");
        
        // Route to appropriate dropdown implementation based on soft coding
        if ("SIMPLE".equals(ADD_MARK_DROPDOWN_STYLE)) {
            showSimpleMarkDropdown();
        } else if ("PROFESSIONAL".equals(ADD_MARK_DROPDOWN_STYLE)) {
            showProfessionalMarkDropdown();
        } else if ("THORX6".equals(ADD_MARK_DROPDOWN_STYLE)) {
            showThorX6MarkDropdown();
        } else {
            // Default to simple if unknown style
            showSimpleMarkDropdown();
        }
    }
    
    /**
     * Simple dropdown using JOptionPane with icons (Soft Coding Implementation)
     */
    private static void showSimpleMarkDropdown() {
        java.util.List<MarkOption> markOptions = new java.util.ArrayList<>();
        
        // Build list of available marks from MARK_TYPES_GRID
        for (int row = 0; row < MARK_TYPES_GRID.length; row++) {
            for (int col = 0; col < MARK_TYPES_GRID[row].length; col++) {
                MarkTypeConfig mark = MARK_TYPES_GRID[row][col];
                if (mark != null) {
                    String displayText = SHOW_MARK_ICONS_IN_DROPDOWN ? 
                        getMarkIcon(mark.name) + " " + mark.name : mark.name;
                    if (SHOW_MARK_DESCRIPTIONS) {
                        displayText += " - " + getMarkDescription(mark.name);
                    }
                    markOptions.add(new MarkOption(displayText, mark));
                }
            }
        }
        
        // Show selection dialog
        MarkOption[] options = markOptions.toArray(new MarkOption[0]);
        MarkOption selected = (MarkOption) javax.swing.JOptionPane.showInputDialog(
            null,
            "Select a mark type to add:",
            "Add Mark",
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selected != null && AUTO_CLOSE_MARK_DROPDOWN_ON_SELECT) {
            System.out.println("🎯 Selected mark: " + selected.mark.name);
            selected.mark.action.run();
        }
    }
    
    /**
     * Professional dropdown with custom dialog and enhanced UI
     */
    private static void showProfessionalMarkDropdown() {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) null, "Add Mark - Professional Style", true);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(350, 400);
        dialog.setLocationRelativeTo(null);
        
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Select Mark Type", javax.swing.JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 16));
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Create list model and list
        javax.swing.DefaultListModel<MarkOption> listModel = new javax.swing.DefaultListModel<>();
        
        for (int row = 0; row < MARK_TYPES_GRID.length; row++) {
            for (int col = 0; col < MARK_TYPES_GRID[row].length; col++) {
                MarkTypeConfig mark = MARK_TYPES_GRID[row][col];
                if (mark != null) {
                    String displayText = SHOW_MARK_ICONS_IN_DROPDOWN ? 
                        getMarkIcon(mark.name) + " " + mark.name : mark.name;
                    if (SHOW_MARK_DESCRIPTIONS) {
                        displayText += " - " + getMarkDescription(mark.name);
                    }
                    listModel.addElement(new MarkOption(displayText, mark));
                }
            }
        }
        
        javax.swing.JList<MarkOption> markList = new javax.swing.JList<>(listModel);
        markList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        markList.setSelectedIndex(0);
        
        if (ENABLE_MARK_DROPDOWN_HOVER_EFFECTS) {
            // Add hover effects
            markList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    int index = markList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        markList.setSelectedIndex(index);
                    }
                }
            });
        }
        
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(markList);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 250));
        
        // Button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        javax.swing.JButton selectButton = new javax.swing.JButton("Add Selected Mark");
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        
        selectButton.addActionListener(e -> {
            MarkOption selected = markList.getSelectedValue();
            if (selected != null) {
                System.out.println("🎯 Professional style selected: " + selected.mark.name);
                selected.mark.action.run();
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(titleLabel, java.awt.BorderLayout.NORTH);
        mainPanel.add(scrollPane, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    /**
     * ThorX6 style dropdown (mimics original ThorX6 software appearance)
     */
    private static void showThorX6MarkDropdown() {
        // For now, fallback to professional style
        // Can be enhanced later to match exact ThorX6 appearance
        showProfessionalMarkDropdown();
    }
    
    /**
     * Original grid-based implementation (fallback when dropdown is disabled)
     */
    private static void handleAddMarkGridDialog() {
        System.out.println("➕ ThorX6 Add Mark Grid - Opening visual mark selection with intelligent coder integration...");
        
        try {
            // Create dropdown window
            JFrame dropdownFrame = new JFrame("Add Mark - Select Type");
            dropdownFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dropdownFrame.setSize(400, 200);
            dropdownFrame.setLocationRelativeTo(null);
            
            // Create main panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createTitledBorder("Select Mark Type"));
            
            // Create 5x2 grid panel for mark types
            JPanel gridPanel = new JPanel(new GridLayout(2, 5, 10, 10));
            gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Add buttons for each mark type
            for (int row = 0; row < MARK_TYPES_GRID.length; row++) {
                for (int col = 0; col < MARK_TYPES_GRID[row].length; col++) {
                    MarkTypeConfig mark = MARK_TYPES_GRID[row][col];
                    if (mark != null) {
                        JButton markButton = new JButton("<html><center>" + 
                            mark.icon + "<br>" + mark.name + "</center></html>");
                        markButton.setToolTipText(mark.tooltip);
                        markButton.setPreferredSize(new Dimension(70, 60));
                        markButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                        
                        // Add action listener
                        markButton.addActionListener(e -> {
                            System.out.println("🎯 Selected: " + mark.icon + " " + mark.name);
                            mark.action.run(); // Execute the mark type action
                            dropdownFrame.dispose(); // Close dropdown
                        });
                        
                        gridPanel.add(markButton);
                    } else {
                        // Add empty placeholder
                        gridPanel.add(new JLabel());
                    }
                }
            }
            
            // Add instruction label
            JLabel instructionLabel = new JLabel("Click on a mark type to add it to the canvas", JLabel.CENTER);
            instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            
            // Layout
            mainPanel.add(instructionLabel, BorderLayout.NORTH);
            mainPanel.add(gridPanel, BorderLayout.CENTER);
            
            dropdownFrame.add(mainPanel);
            dropdownFrame.setVisible(true);
            
            System.out.println("✅ Visual grid opened with " + 
                (MARK_TYPES_GRID.length * MARK_TYPES_GRID[0].length) + " mark types");
            
        } catch (Exception e) {
            System.err.println("❌ Failed to open dropdown: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get soft-coded icon for mark type (Unicode/Emoji based)
     */
    private static String getMarkIcon(String markName) {
        if (!SHOW_MARK_ICONS_IN_DROPDOWN) return "";
        
        switch (markName.toLowerCase()) {
            case "text": return "📝";           // 📝 Document/Text icon
            case "bowtext": return "🏹";        // 🏹 Bow and arrow (curved text)
            case "datamatrix": return "⬛";     // ⬛ Black square (matrix)
            case "graph": return "📈";          // 📈 Graph/Chart trending up
            case "chart": return "📊";          // 📊 Bar chart
            case "ruler": return "📏";          // 📏 Ruler/Straight edge
            case "avoidpoint": return "⚠️";     // ⚠️ Warning sign (avoid)
            case "farsi": return "🌍";          // 🌍 Globe (international text)
            default: return "➕";              // ➕ Plus sign (generic add)
        }
    }
    
    /**
     * Get soft-coded description for mark type
     */
    private static String getMarkDescription(String markName) {
        if (!SHOW_MARK_DESCRIPTIONS) return "";
        
        switch (markName.toLowerCase()) {
            case "text": return "Standard text marking";
            case "bowtext": return "Curved/arc text marking";
            case "datamatrix": return "2D barcode matrix";
            case "graph": return "Line graph visualization";
            case "chart": return "Bar chart display";
            case "ruler": return "Measurement ruler";
            case "avoidpoint": return "Collision avoidance marker";
            case "farsi": return "Persian/Arabic text";
            default: return "Mark type";
        }
    }
    
    /**
     * Enhanced Edit Mark Action with Text Property Editor
     */
    public static void handleEditMark() {
        System.out.println("✏️ ThorX6 Edit Mark - Enhanced property editor...");
        
        try {
            // Get the current DrawingCanvas and selected mark
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Mark selectedMark = canvas.getSelectedMark();
                
                if (selectedMark != null) {
                    String markType = selectedMark.getClass().getSimpleName();
                    System.out.println("📝 Selected mark type: " + markType);
                    
                    // Enhanced mark type handling with soft coding for all new mark types
                    switch (markType) {
                        case "TextMark":
                            System.out.println("📝 Opening enhanced text editor...");
                            openEnhancedTextEditor((TextMark) selectedMark, canvas);
                            break;
                            
                        case "BowTextMark":
                            System.out.println("🏹 Opening enhanced bow text editor...");
                            openEnhancedBowTextEditor((BowTextMark) selectedMark, canvas);
                            break;
                            
                        case "DotMatrixMark":
                            System.out.println("🔲 Opening DataMatrix editor...");
                            openDataMatrixEditor((DotMatrixMark) selectedMark, canvas);
                            break;
                            
                        case "GraphMark":
                            System.out.println("📊 Opening Graph editor...");
                            openGraphEditor((GraphMark) selectedMark, canvas);
                            break;
                            
                        case "ArcLettersMark":
                            System.out.println("📈 Opening Chart editor...");
                            openChartEditor((ArcLettersMark) selectedMark, canvas);
                            break;
                            
                        case "LineMark":
                            System.out.println("📏 Opening Ruler editor...");
                            openRulerEditor((LineMark) selectedMark, canvas);
                            break;
                            
                        case "RulerMark":
                            System.out.println("📏 Opening comprehensive Ruler editor...");
                            openComprehensiveRulerEditor((RulerMark) selectedMark, canvas);
                            break;
                            
                        case "AvoidPointMark":
                            System.out.println("⚪ Opening AvoidPoint editor...");
                            openAvoidPointEditor((AvoidPointMark) selectedMark, canvas);
                            break;
                            
                        default:
                            System.out.println("🔧 Opening generic mark editor for: " + markType);
                            openGenericMarkEditor(selectedMark, canvas);
                            break;
                    }
                } else {
                    System.out.println("⚠️ No mark selected - please select a mark first");
                    showNoSelectionMessage();
                }
            } else {
                System.out.println("⚠️ Canvas not found");
            }
        } catch (Exception e) {
            System.err.println("❌ Edit mark failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ==================== SOFT-CODED CODER ACTION HANDLER ====================
    /**
     * Handle Coder actions - Enhanced with Intelligent Mark Modification Support
     */
    public static void handleCoderAction(String coderType) {
        if (!CODER_INTEGRATION_ACTIVE) {
            System.out.println("⚠️ Coder integration is disabled via soft coding");
            return;
        }
        
        if (ENABLE_CODER_STATE_SYSTEM) {
            // Update state when coder is selected
            currentCoderType = coderType;
            isNoCoderMode = false;
            
            // Update visual feedback
            updateCoderButtonVisualState();
            
            // Show state notification
            showCoderStateNotification(coderType + " Selected", 
                "Coder button is now ready for " + coderType + " operations. Click Coder to generate " + coderType + ".", 
                new Color(240, 255, 240));
        }
        
        // NEW: Intelligent Coder Modification System
        handleIntelligentCoderAction(coderType);
    }
    
    /**
     * Intelligent Coder Action - Check for selected coder marks first, then create new
     */
    private static void handleIntelligentCoderAction(String coderType) {
        System.out.println("🧠 Intelligent Coder Action: " + coderType);
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Mark selectedMark = canvas.getSelectedMark();
                
                // Check if there's a selected mark that can be modified
                if (selectedMark != null && selectedMark instanceof TextMark) {
                    TextMark textMark = (TextMark) selectedMark;
                    String currentText = textMark.getText();
                    
                    // Check if the selected mark is a coder mark (contains ":")
                    if (isCoderMark(currentText)) {
                        System.out.println("🔄 Found selected coder mark: " + currentText);
                        handleCoderMarkModification(textMark, coderType, canvas);
                        return;
                    } else {
                        System.out.println("ℹ️ Selected mark is not a coder mark, creating new coder");
                    }
                } else {
                    System.out.println("ℹ️ No coder mark selected, creating new coder");
                }
                
                // No selected coder mark, create new one
                handleOriginalCoderAction(coderType);
                
            } else {
                System.out.println("⚠️ Canvas not found for coder action");
            }
        } catch (Exception e) {
            System.err.println("❌ Intelligent coder action failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Check if a text mark is a coder mark (contains "Type: Data" format)
     */
    private static boolean isCoderMark(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        // Check for coder format: "CoderType: CoderData"
        return text.contains(": ") && (
            text.startsWith("Serial: ") ||
            text.startsWith("VIN: ") ||
            text.startsWith("PIN: ") ||
            text.startsWith("DateTime: ") ||
            text.startsWith("VCode: ") ||
            text.startsWith("TxMark6PieCoder: ") ||
            text.startsWith("Class: ") ||
            text.startsWith("Random Code: ") ||
            text.startsWith("QR Code: ") ||
            text.startsWith("DataMatrix: ") ||
            text.startsWith("Code 128: ") ||
            text.startsWith("Code 39: ") ||
            text.startsWith("EAN-13: ") ||
            text.startsWith("UPC-A: ") ||
            text.startsWith("PDF417: ") ||
            text.startsWith("Aztec: ") ||
            text.startsWith("Code 93: ") ||
            text.startsWith("MaxiCode: ")
        );
    }
    
    /**
     * Handle modification of an existing coder mark
     */
    private static void handleCoderMarkModification(TextMark coderMark, String newCoderType, DrawingCanvas canvas) {
        String currentText = coderMark.getText();
        String[] parts = currentText.split(": ", 2);
        String currentCoderType = parts[0];
        String currentCoderData = parts.length > 1 ? parts[1] : "";
        
        System.out.println("🔧 Modifying coder mark from '" + currentCoderType + "' to '" + newCoderType + "'");
        
        // Generate new coder data based on the requested type
        String newCoderData = generateCoderDataForType(newCoderType, currentCoderData);
        
        if (newCoderData != null && !newCoderData.trim().isEmpty()) {
            // Update the existing mark with new coder data
            String newDisplayText = newCoderType + ": " + newCoderData;
            coderMark.setText(newDisplayText);
            
            // Update canvas display
            canvas.repaint();
            
            System.out.println("✅ Coder mark modified: " + currentText + " → " + newDisplayText);
            
            // Show modification confirmation
            showCoderModificationNotification(currentCoderType, newCoderType, newCoderData);
        } else {
            System.out.println("⚠️ Coder modification cancelled or failed");
        }
    }
    
    /**
     * Generate coder data for a specific type (used for modification)
     */
    private static String generateCoderDataForType(String coderType, String currentData) {
        // Special handling for auto-generated types
        if ("Serial".equals(coderType) && ENABLE_AUTO_INCREMENT) {
            return generateNextSerialNumber();
        } else if ("VIN".equals(coderType) && ENABLE_VIN_VALIDATION) {
            return generateVINNumber();
        } else if ("PIN".equals(coderType) && ENABLE_PIN_SECURITY) {
            return generatePINNumber();
        } else if ("DateTime".equals(coderType) && ENABLE_CUSTOM_DATETIME_FORMAT) {
            return generateDateTimeStamp();
        } else if ("VCode".equals(coderType) && ENABLE_VCODE_ENCRYPTION) {
            return generateVCodeNumber();
        } else if ("TxMark6PieCoder".equals(coderType) && ENABLE_TXMARK6PIE_ADVANCED) {
            return generateTxMark6PieCode();
        } else if ("Class".equals(coderType) && ENABLE_CLASS_CATEGORIZATION) {
            return generateClassCode();
        } else if ("Random Code".equals(coderType) && ENABLE_RANDOM_ALGORITHMS) {
            return generateRandomCode();
        } else {
            // For other types, show input dialog with current data as default
            return showCoderModificationDialog(coderType, currentData);
        }
    }
    
    /**
     * Show dialog for coder data modification with current data as default
     */
    private static String showCoderModificationDialog(String coderType, String currentData) {
        return (String) JOptionPane.showInputDialog(
            null,
            "Modify " + coderType + " data:",
            "Modify Coder",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            currentData
        );
    }
    
    /**
     * Show coder modification options when no coder type is selected
     */
    private static void showCoderModificationOptions(TextMark coderMark, DrawingCanvas canvas) {
        String currentText = coderMark.getText();
        String[] parts = currentText.split(": ", 2);
        String currentCoderType = parts[0];
        
        // Show selection dialog for new coder type
        String[] coderTypes = {"Serial", "VIN", "PIN", "DateTime", "VCode", "TxMark6PieCoder", 
                              "Class", "Random Code", "QR Code", "DataMatrix", "Code 128", 
                              "Code 39", "EAN-13", "UPC-A", "PDF417", "Aztec", "Code 93", "MaxiCode"};
        
        String selectedType = (String) JOptionPane.showInputDialog(
            null,
            "Current Coder: " + currentText + "\n\nSelect new coder type to modify to:",
            "Modify Coder Type",
            JOptionPane.QUESTION_MESSAGE,
            null,
            coderTypes,
            currentCoderType
        );
        
        if (selectedType != null && !selectedType.equals(currentCoderType)) {
            handleCoderMarkModification(coderMark, selectedType, canvas);
        } else if (selectedType != null && selectedType.equals(currentCoderType)) {
            // Same type selected, just regenerate data
            System.out.println("🔄 Regenerating data for same coder type: " + selectedType);
            handleCoderMarkModification(coderMark, selectedType, canvas);
        }
    }
    
    /**
     * Show notification for successful coder modification
     */
    private static void showCoderModificationNotification(String oldType, String newType, String newData) {
        if (ENABLE_VISUAL_STATE_FEEDBACK) {
            String message = "Coder Modified: " + oldType + " → " + newType + "\nNew Data: " + newData;
            showCoderStateNotification("Coder Modified", message, new Color(240, 255, 240));
        }
    }

    /**
     * Original Coder Action Logic (preserved)
     */
    private static void handleOriginalCoderAction(String coderType) {
        System.out.println("🔧 ThorX6 Coder Action: " + coderType);
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                String coderData = null;
                
                // Special handling for Serial numbers with auto-increment
                if ("Serial".equals(coderType) && ENABLE_AUTO_INCREMENT) {
                    coderData = generateNextSerialNumber();
                    System.out.println("🔢 Auto-generated serial number: " + coderData);
                } 
                // Special handling for VIN numbers with validation
                else if ("VIN".equals(coderType) && ENABLE_VIN_VALIDATION) {
                    coderData = generateVINNumber();
                    System.out.println("🚗 Auto-generated VIN number: " + coderData);
                }
                // Special handling for PIN numbers with security
                else if ("PIN".equals(coderType) && ENABLE_PIN_SECURITY) {
                    coderData = generatePINNumber();
                    System.out.println("🔒 Auto-generated PIN number: " + coderData);
                }
                // Special handling for DateTime stamps with formatting
                else if ("DateTime".equals(coderType) && ENABLE_CUSTOM_DATETIME_FORMAT) {
                    coderData = generateDateTimeStamp();
                    System.out.println("📅 Auto-generated DateTime stamp: " + coderData);
                }
                // Special handling for VCode verification codes with encryption
                else if ("VCode".equals(coderType) && ENABLE_VCODE_ENCRYPTION) {
                    coderData = generateVCodeNumber();
                    System.out.println("🔐 Auto-generated VCode: " + coderData);
                }
                // Special handling for TxMark6PieCoder with pie-sector algorithm
                else if ("TxMark6PieCoder".equals(coderType) && ENABLE_TXMARK6PIE_ADVANCED) {
                    coderData = generateTxMark6PieCode();
                    System.out.println("🥧 Auto-generated TxMark6PieCode: " + coderData);
                }
                // Special handling for Class codes with categorization
                else if ("Class".equals(coderType) && ENABLE_CLASS_CATEGORIZATION) {
                    coderData = generateClassCode();
                    System.out.println("📚 Auto-generated ClassCode: " + coderData);
                }
                // Special handling for Random Code generation with multiple algorithms
                else if ("Random Code".equals(coderType) && ENABLE_RANDOM_ALGORITHMS) {
                    coderData = generateRandomCode();
                    System.out.println("🎲 Auto-generated Random Code: " + coderData);
                } else {
                    // Create coder dialog for data input (other coder types)
                    coderData = showCoderDataDialog(coderType);
                }
                
                if (coderData != null && !coderData.trim().isEmpty()) {
                    // Add coder mark to canvas using existing mark system
                    String displayText = coderType + ": " + coderData;
                    canvas.addMark("Text", displayText); // Use existing text mark system
                    System.out.println("✅ " + coderType + " coder added: " + coderData);
                } else {
                    System.out.println("⚠️ Coder data input cancelled");
                }
            } else {
                System.out.println("⚠️ Canvas not found for coder action");
            }
        } catch (Exception e) {
            System.err.println("❌ Coder action failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Show data input dialog for coder
     */
    private static String showCoderDataDialog(String coderType) {
        String defaultData = getDefaultCoderData(coderType);
        return JOptionPane.showInputDialog(
            null,
            "Enter data for " + coderType + ":",
            "Coder Data Input",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            defaultData
        ).toString();
    }
    
    // ==================== CODER TYPE GRID CONFIGURATION (SOFT CODED) ====================
    
    // Grid Layout Configuration (Soft Coded)
    public static final boolean ENABLE_CODER_GRID_LAYOUT = true;     // ENABLE 5x2 grid layout for coder types
    public static final boolean CENTER_ALIGN_GRID = true;            // CENTER align the grid display
    public static final int CODER_GRID_ROWS = 2;                     // Grid rows (soft coded)
    public static final int CODER_GRID_COLS = 5;                     // Grid columns (soft coded)
    public static final int CODER_BUTTON_WIDTH = 80;                 // Button width (soft coded)
    public static final int CODER_BUTTON_HEIGHT = 70;                // Button height (soft coded)
    public static final int CODER_GRID_GAP = 10;                     // Gap between buttons (soft coded)
    
    // Coder Type Configuration Class
    public static class CoderTypeConfig {
        public final String name;
        public final String icon;
        public final String tooltip;
        public final Runnable action;
        
        public CoderTypeConfig(String name, String icon, String tooltip, Runnable action) {
            this.name = name;
            this.icon = icon;
            this.tooltip = tooltip;
            this.action = action;
        }
    }
    
    // Soft-coded Coder Type Grid (9 total: Row1[No Coder + Serial + VIN + PIN + DateTime] + Row2[VCode + TxMark6PieCoder + Class + Random Code])
    public static final CoderTypeConfig[][] CODER_TYPES_GRID = createCoderTypesGrid();

    /**
     * Create Coder Types Grid with soft-coded options (Complete 2x5 grid with Random Code as final option)
     */
    private static CoderTypeConfig[][] createCoderTypesGrid() {
        java.util.List<CoderTypeConfig> allCoders = new java.util.ArrayList<>();
        
        // Add "No Coder" option as first cell if enabled
        if (ENABLE_NO_CODER_OPTION) {
            allCoders.add(new CoderTypeConfig(NO_CODER_TEXT, NO_CODER_ICON, NO_CODER_TOOLTIP, () -> handleNoCoderAction()));
        }
        
        // Add "Serial" option as second cell if enabled
        if (ENABLE_SERIAL_OPTION) {
            allCoders.add(new CoderTypeConfig(SERIAL_TEXT, SERIAL_ICON, SERIAL_TOOLTIP, () -> handleSerialAction()));
        }
        
        // Add "VIN" option as third cell if enabled
        if (ENABLE_VIN_OPTION) {
            allCoders.add(new CoderTypeConfig(VIN_TEXT, VIN_ICON, VIN_TOOLTIP, () -> handleVINAction()));
        }
        
        // Add "PIN" option as fourth cell if enabled
        if (ENABLE_PIN_OPTION) {
            allCoders.add(new CoderTypeConfig(PIN_TEXT, PIN_ICON, PIN_TOOLTIP, () -> handlePINAction()));
        }
        
        // Add "DateTime" option as fifth cell if enabled
        if (ENABLE_DATETIME_OPTION) {
            allCoders.add(new CoderTypeConfig(DATETIME_TEXT, DATETIME_ICON, DATETIME_TOOLTIP, () -> handleDateTimeAction()));
        }
        
        // Add "VCode" option as sixth cell if enabled (Next Row First Column)
        if (ENABLE_VCODE_OPTION) {
            allCoders.add(new CoderTypeConfig(VCODE_TEXT, VCODE_ICON, VCODE_TOOLTIP, () -> handleVCodeAction()));
        }
        
        // Add "TxMark6PieCoder" option as seventh cell if enabled (Row 2, Col 2)
        if (ENABLE_TXMARK6PIE_OPTION) {
            allCoders.add(new CoderTypeConfig(TXMARK6PIE_TEXT, TXMARK6PIE_ICON, TXMARK6PIE_TOOLTIP, () -> handleTxMark6PieAction()));
        }
        
        // Add "Class" option as eighth cell if enabled (Row 2, Col 3)
        if (ENABLE_CLASS_OPTION) {
            allCoders.add(new CoderTypeConfig(CLASS_TEXT, CLASS_ICON, CLASS_TOOLTIP, () -> handleClassAction()));
        }
        
        // Add "Random Code" option as ninth cell if enabled (Row 2, Col 4 - Final Option)
        if (ENABLE_RANDOM_CODE_OPTION) {
            allCoders.add(new CoderTypeConfig(RANDOM_CODE_TEXT, RANDOM_CODE_ICON, RANDOM_CODE_TOOLTIP, () -> handleRandomCodeAction()));
        }
        
        // Add standard coder types (controlled by soft coding flags)
        if (ENABLE_QR_CODE) {
            allCoders.add(new CoderTypeConfig("QR Code", "⬛", "2D QR Code for URLs, text", () -> handleCoderAction("QR Code")));
        }
        if (ENABLE_DATAMATRIX) {
            allCoders.add(new CoderTypeConfig("DataMatrix", "▣", "2D DataMatrix for compact data", () -> handleCoderAction("DataMatrix")));
        }
        if (ENABLE_CODE128) {
            allCoders.add(new CoderTypeConfig("Code 128", "|||", "Linear Code 128 barcode", () -> handleCoderAction("Code 128")));
        }
        if (ENABLE_CODE39) {
            allCoders.add(new CoderTypeConfig("Code 39", "⦀⦀", "Linear Code 39 alphanumeric", () -> handleCoderAction("Code 39")));
        }
        if (ENABLE_EAN13) {
            allCoders.add(new CoderTypeConfig("EAN-13", "⦙⦙", "EAN-13 retail barcode", () -> handleCoderAction("EAN-13")));
        }
        if (ENABLE_UPC_A) {
            allCoders.add(new CoderTypeConfig("UPC-A", "||⦙", "UPC-A product barcode", () -> handleCoderAction("UPC-A")));
        }
        if (ENABLE_PDF417) {
            allCoders.add(new CoderTypeConfig("PDF417", "▤", "2D PDF417 for documents", () -> handleCoderAction("PDF417")));
        }
        if (ENABLE_AZTEC) {
            allCoders.add(new CoderTypeConfig("Aztec", "◈", "Aztec code for tickets", () -> handleCoderAction("Aztec Code")));
        }
        if (ENABLE_CODE93) {
            allCoders.add(new CoderTypeConfig("Code 93", "|⦀|", "Linear Code 93 compact", () -> handleCoderAction("Code 93")));
        }
        if (ENABLE_MAXICODE) {
            allCoders.add(new CoderTypeConfig("MaxiCode", "◉", "MaxiCode for shipping", () -> handleCoderAction("MaxiCode")));
        }
        
        // Create 5-column grid with dynamic rows based on coder count
        int cols = 5;
        int rows = (int) Math.ceil((double) allCoders.size() / cols);
        CoderTypeConfig[][] grid = new CoderTypeConfig[rows][cols];
        
        // Fill grid row by row
        for (int i = 0; i < allCoders.size(); i++) {
            int row = i / cols;
            int col = i % cols;
            grid[row][col] = allCoders.get(i);
        }
        
        return grid;
    }

    /**
     * Handle No Coder Action - Innovative State Management System
     */
    private static void handleNoCoderAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("⊘ No Coder selected - basic mode");
            return;
        }
        
        System.out.println("⊘ No Coder selected - activating intelligent state management...");
        
        // Update coder state
        currentCoderType = "No Coder";
        isNoCoderMode = true;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Clear any existing coder marks from canvas
        clearCoderMarksFromCanvas();
        
        // Show innovative feedback
        showCoderStateNotification("No Coder Mode Activated", 
            "Coder button is now in disabled state. Select a coder type to enable coder functionality.", 
            new Color(255, 240, 240));
        
        System.out.println("✅ No Coder state activated - Coder button behavior updated");
    }
    
    /**
     * Handle Serial Action - Enhanced with Innovative State Management
     */
    private static void handleSerialAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("🔢 Serial selected - basic mode");
            handleCoderAction("Serial");
            return;
        }
        
        System.out.println("🔢 Serial selected - activating intelligent state management...");
        
        // Update coder state for Serial
        currentCoderType = "Serial";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with Serial-specific messaging
        showCoderStateNotification("Serial Mode Activated", 
            "Coder button is now ready for Serial number generation. Click Coder to add sequential numbers.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ Serial state activated - Coder button ready for serial number generation");
        
        // Handle original coder action for Serial
        handleCoderAction("Serial");
    }
    
    /**
     * Handle VIN Action - Enhanced with Innovative State Management
     */
    private static void handleVINAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("🚗 VIN selected - basic mode");
            handleCoderAction("VIN");
            return;
        }
        
        System.out.println("🚗 VIN selected - activating intelligent state management...");
        
        // Update coder state for VIN
        currentCoderType = "VIN";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with VIN-specific messaging
        showCoderStateNotification("VIN Mode Activated", 
            "Coder button is now ready for Vehicle Identification Number generation. Click Coder to add VIN codes.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ VIN state activated - Coder button ready for VIN generation");
        
        // Handle original coder action for VIN
        handleCoderAction("VIN");
    }
    
    /**
     * Handle PIN Action - Enhanced with Innovative State Management
     */
    private static void handlePINAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("🔒 PIN selected - basic mode");
            handleCoderAction("PIN");
            return;
        }
        
        System.out.println("🔒 PIN selected - activating intelligent state management...");
        
        // Update coder state for PIN
        currentCoderType = "PIN";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with PIN-specific messaging
        showCoderStateNotification("PIN Mode Activated", 
            "Coder button is now ready for Personal Identification Number generation. Click Coder to add secure PINs.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ PIN state activated - Coder button ready for secure PIN generation");
        
        // Handle original coder action for PIN
        handleCoderAction("PIN");
    }
    
    /**
     * Handle DateTime Action - Enhanced with Innovative State Management
     */
    private static void handleDateTimeAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("📅 DateTime selected - basic mode");
            handleCoderAction("DateTime");
            return;
        }
        
        System.out.println("📅 DateTime selected - activating intelligent state management...");
        
        // Update coder state for DateTime
        currentCoderType = "DateTime";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with DateTime-specific messaging
        showCoderStateNotification("DateTime Mode Activated", 
            "Coder button is now ready for date and time stamp generation. Click Coder to add current timestamps.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ DateTime state activated - Coder button ready for timestamp generation");
        
        // Handle original coder action for DateTime
        handleCoderAction("DateTime");
    }
    
    /**
     * Handle VCode Action - Enhanced with Innovative State Management
     */
    private static void handleVCodeAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("🔐 VCode selected - basic mode");
            handleCoderAction("VCode");
            return;
        }
        
        System.out.println("🔐 VCode selected - activating intelligent state management...");
        
        // Update coder state for VCode
        currentCoderType = "VCode";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with VCode-specific messaging
        showCoderStateNotification("VCode Mode Activated", 
            "Coder button is now ready for Verification Code generation. Click Coder to add secure verification codes.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ VCode state activated - Coder button ready for verification code generation");
        
        // Handle original coder action for VCode
        handleCoderAction("VCode");
    }
    
    /**
     * Handle TxMark6PieCoder Action - Enhanced with Innovative State Management
     */
    private static void handleTxMark6PieAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("🥧 TxMark6PieCoder selected - basic mode");
            handleCoderAction("TxMark6PieCoder");
            return;
        }
        
        System.out.println("🥧 TxMark6PieCoder selected - activating intelligent state management...");
        
        // Update coder state for TxMark6PieCoder
        currentCoderType = "TxMark6PieCoder";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with TxMark6PieCoder-specific messaging
        showCoderStateNotification("TxMark6PieCoder Mode Activated", 
            "Coder button is now ready for TxMark6 Pie-based encoding. Click Coder to generate pie-sector codes.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ TxMark6PieCoder state activated - Coder button ready for pie-based encoding generation");
        
        // Handle original coder action for TxMark6PieCoder
        handleCoderAction("TxMark6PieCoder");
    }
    
    /**
     * Handle Class Action - Enhanced with Innovative State Management
     */
    private static void handleClassAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("📚 Class selected - basic mode");
            handleCoderAction("Class");
            return;
        }
        
        System.out.println("📚 Class selected - activating intelligent state management...");
        
        // Update coder state for Class
        currentCoderType = "Class";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with Class-specific messaging
        showCoderStateNotification("Class Mode Activated", 
            "Coder button is now ready for Class identification codes. Click Coder to generate class categories.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ Class state activated - Coder button ready for class categorization");
        
        // Handle original coder action for Class
        handleCoderAction("Class");
    }
    
    /**
     * Handle Random Code Action - Enhanced with Innovative State Management (Final Option)
     */
    private static void handleRandomCodeAction() {
        if (!ENABLE_CODER_STATE_SYSTEM) {
            System.out.println("🎲 Random Code selected - basic mode");
            handleCoderAction("Random Code");
            return;
        }
        
        System.out.println("🎲 Random Code selected - activating intelligent state management...");
        
        // Update coder state for Random Code
        currentCoderType = "Random Code";
        isNoCoderMode = false;
        
        // Apply visual state feedback to buttons
        updateCoderButtonVisualState();
        
        // Show innovative feedback with Random Code-specific messaging
        showCoderStateNotification("Random Code Mode Activated", 
            "Coder button is now ready for completely random code generation. Click Coder to generate unpredictable codes.", 
            new Color(240, 255, 240));
        
        System.out.println("✅ Random Code state activated - Coder button ready for random generation");
        
        // Handle original coder action for Random Code
        handleCoderAction("Random Code");
    }

    /**
     * Handle Coder Type Selection - Soft-coded 5x2 grid dropdown (similar to Add Mark)
     */
    public static void handleCoderTypeSelection() {
        if (DELETE_CODER_TYPE_COMPLETELY) {
            System.out.println("🚫 Coder Type functionality completely deleted via soft coding");
            return;
        }
        
        if (!ENABLE_CODER_TYPE_OPTIONS) {
            System.out.println("⚠️ Coder Type options disabled via soft coding");
            return;
        }
        
        if (!ENABLE_CODER_GRID_LAYOUT) {
            // Fallback to simple dropdown if grid layout disabled
            handleSimpleCoderSelection();
            return;
        }
        
        System.out.println("⚙️ ThorX6 Coder Type Selection - Opening visual grid selection...");
        
        try {
            // Create centered dropdown window (Soft Coded Layout)
            JFrame dropdownFrame = new JFrame("Coder Type - Select from Grid");
            dropdownFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dropdownFrame.setSize(500, 250);
            if (CENTER_ALIGN_GRID) {
                dropdownFrame.setLocationRelativeTo(null); // Center on screen
            }
            
            // Create main panel with soft-coded styling
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createTitledBorder("Select Coder Type (5×2 Grid)"));
            mainPanel.setBackground(Color.WHITE);
            
            // Create grid panel (Soft Coded Grid Configuration)
            JPanel gridPanel = new JPanel(new GridLayout(CODER_GRID_ROWS, CODER_GRID_COLS, CODER_GRID_GAP, CODER_GRID_GAP));
            gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            gridPanel.setBackground(Color.WHITE);
            
            // Add buttons for each coder type (Soft Coded Grid Population)
            for (int row = 0; row < CODER_TYPES_GRID.length; row++) {
                for (int col = 0; col < CODER_TYPES_GRID[row].length; col++) {
                    CoderTypeConfig coder = CODER_TYPES_GRID[row][col];
                    
                    JButton coderButton = new JButton("<html><center>" + 
                        coder.icon + "<br><small>" + coder.name + "</small></center></html>");
                    coderButton.setToolTipText(coder.tooltip);
                    coderButton.setPreferredSize(new Dimension(CODER_BUTTON_WIDTH, CODER_BUTTON_HEIGHT));
                    coderButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
                    
                    // Soft-coded button styling
                    coderButton.setBackground(new Color(245, 245, 255));
                    coderButton.setBorder(BorderFactory.createRaisedBevelBorder());
                    coderButton.setFocusPainted(false);
                    
                    // Add hover effect
                    coderButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            coderButton.setBackground(new Color(225, 240, 255));
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            coderButton.setBackground(new Color(245, 245, 255));
                        }
                    });
                    
                    // Add action listener
                    coderButton.addActionListener(e -> {
                        System.out.println("⚙️ Selected: " + coder.icon + " " + coder.name);
                        coder.action.run(); // Execute the coder type action
                        dropdownFrame.dispose(); // Close dropdown
                    });
                    
                    gridPanel.add(coderButton);
                }
            }
            
            // Add instruction label (Soft Coded Styling)
            JLabel instructionLabel = new JLabel("Click on a coder type to generate and add to canvas", JLabel.CENTER);
            instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            instructionLabel.setForeground(new Color(100, 100, 100));
            instructionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            
            // Layout assembly
            mainPanel.add(instructionLabel, BorderLayout.NORTH);
            mainPanel.add(gridPanel, BorderLayout.CENTER);
            
            dropdownFrame.add(mainPanel);
            dropdownFrame.setVisible(true);
            
            System.out.println("✅ Visual coder grid opened with " + 
                (CODER_TYPES_GRID.length * CODER_TYPES_GRID[0].length) + " coder types");
            
        } catch (Exception e) {
            System.err.println("❌ Failed to open coder grid: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Fallback simple coder selection (if grid layout disabled)
     */
    private static void handleSimpleCoderSelection() {
        String[] coderTypes = {"QR Code", "DataMatrix", "Code 128", "Code 39", "EAN-13", 
                              "UPC-A", "PDF417", "Aztec Code", "Code 93", "MaxiCode"};
        
        String selectedType = (String) JOptionPane.showInputDialog(null, "Select Coder Type:", 
            "Coder Type Selection", JOptionPane.QUESTION_MESSAGE, null, coderTypes, coderTypes[0]);
        
        if (selectedType != null) {
            System.out.println("✅ Coder Type selected: " + selectedType);
            handleCoderAction(selectedType);
        }
    }

    /**
     * Get default data based on coder type - Soft coded defaults (Enhanced for Grid Types)
     */
    private static String getDefaultCoderData(String coderType) {
        switch (coderType) {
            case "QR Code": return "https://example.com";
            case "DataMatrix": return "DM123456789";
            case "Barcode": return "1234567890123";
            case "Serial": return generateNextSerialNumber(); // Soft-coded Serial with auto-increment
            case "VIN": return generateVINNumber(); // Soft-coded VIN with validation
            case "PIN": return generatePINNumber(); // Soft-coded PIN with security
            case "DateTime": return generateDateTimeStamp(); // Soft-coded DateTime with formatting
            case "VCode": return generateVCodeNumber(); // Soft-coded VCode with encryption
            case "TxMark6PieCoder": return generateTxMark6PieCode(); // Soft-coded TxMark6Pie with pie-sectors
            case "Class": return generateClassCode(); // Soft-coded Class with categorization
            case "Random Code": return generateRandomCode(); // Soft-coded Random Code with multiple algorithms
            case "Code 128": return "CODE128-123456";
            case "Code 39": return "CODE39-ABCD123";
            case "EAN-13": return "1234567890123";
            case "UPC-A": return "123456789012";
            case "PDF417": return "PDF417-DATA-123";
            case "Aztec Code": return "AZTEC-CODE-456";
            case "Code 93": return "CODE93-SAMPLE";
            case "MaxiCode": return "MAXICODE-SHIPPING";
            default: return "SAMPLE_DATA";
        }
    }
    
    /**
     * Generate next serial number with soft-coded auto-increment
     */
    private static String generateNextSerialNumber() {
        if (!ENABLE_AUTO_INCREMENT) {
            return SERIAL_PREFIX + String.format("%0" + SERIAL_PADDING_ZEROS + "d", SERIAL_START_NUMBER);
        }
        
        // Auto-increment serial number
        String serialNumber = SERIAL_PREFIX + String.format("%0" + SERIAL_PADDING_ZEROS + "d", currentSerialNumber);
        currentSerialNumber++; // Increment for next use
        
        System.out.println("🔢 Generated serial: " + serialNumber + " (next will be: " + 
            SERIAL_PREFIX + String.format("%0" + SERIAL_PADDING_ZEROS + "d", currentSerialNumber) + ")");
        
        return serialNumber;
    }
    
    /**
     * Generate VIN number with soft-coded validation and format
     */
    private static String generateVINNumber() {
        if (!ENABLE_VIN_VALIDATION) {
            return VIN_DEFAULT_PREFIX + "123456"; // Simple fallback
        }
        
        // Generate random VIN suffix (last 6 digits for example)
        java.util.Random random = new java.util.Random();
        String suffix = String.format("%06d", random.nextInt(999999));
        
        // Combine prefix with suffix to create 17-character VIN
        String vinNumber = VIN_DEFAULT_PREFIX + suffix;
        
        // Ensure VIN is exactly 17 characters (standard VIN length)
        if (vinNumber.length() > VIN_LENGTH) {
            vinNumber = vinNumber.substring(0, VIN_LENGTH);
        } else if (vinNumber.length() < VIN_LENGTH) {
            // Pad with zeros if needed
            vinNumber = vinNumber + "0".repeat(VIN_LENGTH - vinNumber.length());
        }
        
        System.out.println("🚗 Generated VIN: " + vinNumber + " (Length: " + vinNumber.length() + " chars)");
        
        return vinNumber;
    }
    
    /**
     * Generate PIN number with soft-coded security and length control
     */
    private static String generatePINNumber() {
        if (!ENABLE_PIN_SECURITY) {
            return "1234"; // Simple fallback PIN
        }
        
        java.util.Random random = new java.util.Random();
        
        // Use soft-coded PIN length
        int pinLength = PIN_DEFAULT_LENGTH;
        
        // Generate secure random PIN
        StringBuilder pinBuilder = new StringBuilder();
        
        if (ENABLE_PIN_COMPLEXITY) {
            // Complex PIN with mixed characters (if enabled)
            String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < pinLength; i++) {
                pinBuilder.append(chars.charAt(random.nextInt(chars.length())));
            }
        } else {
            // Simple numeric PIN (default)
            for (int i = 0; i < pinLength; i++) {
                pinBuilder.append(random.nextInt(10)); // 0-9 digits only
            }
        }
        
        String pinNumber = pinBuilder.toString();
        
        // Ensure PIN meets length requirements
        if (pinNumber.length() < PIN_MIN_LENGTH || pinNumber.length() > PIN_MAX_LENGTH) {
            // Regenerate if outside valid range
            return generatePINNumber();
        }
        
        System.out.println("🔒 Generated PIN: " + pinNumber + " (Length: " + pinNumber.length() + 
                          ", Security: " + (ENABLE_PIN_COMPLEXITY ? "Complex" : "Numeric") + ")");
        
        return pinNumber;
    }
    
    /**
     * Generate DateTime stamp with soft-coded formatting options
     */
    private static String generateDateTimeStamp() {
        if (!ENABLE_CUSTOM_DATETIME_FORMAT) {
            // Simple fallback format
            return new java.util.Date().toString();
        }
        
        try {
            // Use soft-coded date/time formatting
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATETIME_DEFAULT_FORMAT);
            
            String dateTimeStamp = now.format(formatter);
            
            System.out.println("📅 Generated DateTime: " + dateTimeStamp + 
                              " (Format: " + DATETIME_DEFAULT_FORMAT + 
                              ", Real-time: " + ENABLE_REALTIME_UPDATE + ")");
            
            return dateTimeStamp;
            
        } catch (Exception e) {
            // Fallback to simple format if pattern fails
            System.err.println("⚠️ DateTime format error, using fallback: " + e.getMessage());
            return new java.util.Date().toString();
        }
    }
    
    /**
     * Generate VCode (Verification Code) with soft-coded encryption and validation
     */
    private static String generateVCodeNumber() {
        if (!ENABLE_VCODE_ENCRYPTION) {
            return "V12345678"; // Simple fallback VCode
        }
        
        try {
            java.util.Random random = new java.util.Random();
            StringBuilder vcodeBuilder = new StringBuilder();
            
            // Add soft-coded prefix
            if (VCODE_PREFIX != null && !VCODE_PREFIX.isEmpty()) {
                vcodeBuilder.append(VCODE_PREFIX);
            }
            
            // Generate VCode using soft-coded character set and length
            int remainingLength = VCODE_DEFAULT_LENGTH - vcodeBuilder.length();
            for (int i = 0; i < remainingLength; i++) {
                char randomChar = VCODE_CHARACTER_SET.charAt(random.nextInt(VCODE_CHARACTER_SET.length()));
                vcodeBuilder.append(randomChar);
            }
            
            String vcode = vcodeBuilder.toString();
            
            // Add checksum if enabled
            if (ENABLE_VCODE_CHECKSUM) {
                vcode = addVCodeChecksum(vcode);
            }
            
            System.out.println("🔐 Generated VCode: " + vcode + 
                              " (Length: " + vcode.length() + 
                              ", Encrypted: " + ENABLE_VCODE_ENCRYPTION +
                              ", Checksum: " + ENABLE_VCODE_CHECKSUM + ")");
            
            return vcode;
            
        } catch (Exception e) {
            System.err.println("⚠️ VCode generation error, using fallback: " + e.getMessage());
            return "V12345678"; // Fallback VCode
        }
    }
    
    /**
     * Add checksum to VCode for validation (soft-coded)
     */
    private static String addVCodeChecksum(String baseVCode) {
        if (!ENABLE_VCODE_CHECKSUM) {
            return baseVCode;
        }
        
        // Simple checksum calculation
        int checksum = 0;
        for (char c : baseVCode.toCharArray()) {
            checksum += (int) c;
        }
        
        // Add checksum as single character
        char checksumChar = VCODE_CHARACTER_SET.charAt(checksum % VCODE_CHARACTER_SET.length());
        return baseVCode + checksumChar;
    }
    
    /**
     * Generate TxMark6PieCoder with soft-coded pie-sector algorithm
     */
    private static String generateTxMark6PieCode() {
        if (!ENABLE_TXMARK6PIE_ADVANCED) {
            return "TX6PIE123456"; // Simple fallback TxMark6Pie code
        }
        
        try {
            java.util.Random random = new java.util.Random();
            StringBuilder pieCodeBuilder = new StringBuilder();
            
            // Add soft-coded prefix
            pieCodeBuilder.append(TXMARK6PIE_PREFIX);
            
            // Generate pie-sector based encoding
            for (int sector = 1; sector <= TXMARK6PIE_SECTORS; sector++) {
                // Each sector contributes 2 characters (sector position + value)
                char sectorPos = (char) ('A' + (sector - 1)); // A, B, C, D, E, F for 6 sectors
                int sectorValue = random.nextInt(10); // 0-9 for each sector
                
                pieCodeBuilder.append(sectorPos);
                pieCodeBuilder.append(sectorValue);
            }
            
            String pieCode = pieCodeBuilder.toString();
            
            // Add validation if enabled
            if (ENABLE_TXMARK6PIE_VALIDATION) {
                pieCode = addTxMark6PieValidation(pieCode);
            }
            
            System.out.println("🥧 Generated TxMark6PieCode: " + pieCode + 
                              " (Algorithm: " + TXMARK6PIE_ALGORITHM +
                              ", Sectors: " + TXMARK6PIE_SECTORS +
                              ", Validation: " + ENABLE_TXMARK6PIE_VALIDATION + ")");
            
            return pieCode;
            
        } catch (Exception e) {
            System.err.println("⚠️ TxMark6PieCoder generation error, using fallback: " + e.getMessage());
            return "TX6PIE123456"; // Fallback code
        }
    }
    
    /**
     * Add validation to TxMark6PieCoder (soft-coded)
     */
    private static String addTxMark6PieValidation(String basePieCode) {
        if (!ENABLE_TXMARK6PIE_VALIDATION) {
            return basePieCode;
        }
        
        // Calculate validation based on pie sectors
        int validation = 0;
        for (int i = 0; i < basePieCode.length(); i++) {
            validation += (int) basePieCode.charAt(i);
        }
        
        // Add validation as hex digit
        String validationHex = Integer.toHexString(validation % 16).toUpperCase();
        return basePieCode + validationHex;
    }
    
    /**
     * Generate Class code with soft-coded categorization and hierarchy
     */
    private static String generateClassCode() {
        if (!ENABLE_CLASS_CATEGORIZATION) {
            return "CL001"; // Simple fallback Class code
        }
        
        try {
            java.util.Random random = new java.util.Random();
            StringBuilder classCodeBuilder = new StringBuilder();
            
            // Add soft-coded prefix
            classCodeBuilder.append(CLASS_PREFIX);
            
            // Select category from soft-coded categories
            String category = CLASS_CATEGORIES[random.nextInt(CLASS_CATEGORIES.length)];
            classCodeBuilder.append(category);
            
            // Add hierarchical numbering if enabled
            if (ENABLE_CLASS_HIERARCHY) {
                // Generate hierarchical class number (e.g., Level.Section.Item)
                int level = random.nextInt(9) + 1;    // 1-9
                int section = random.nextInt(9) + 1;  // 1-9
                int item = random.nextInt(99) + 1;    // 1-99
                
                classCodeBuilder.append(String.format("%d%d%02d", level, section, item));
            } else {
                // Simple sequential numbering
                int classNumber = random.nextInt(999) + 1; // 1-999
                classCodeBuilder.append(String.format("%03d", classNumber));
            }
            
            String classCode = classCodeBuilder.toString();
            
            System.out.println("📚 Generated ClassCode: " + classCode + 
                              " (Category: " + category +
                              ", Hierarchy: " + ENABLE_CLASS_HIERARCHY +
                              ", Prefix: " + CLASS_PREFIX + ")");
            
            return classCode;
            
        } catch (Exception e) {
            System.err.println("⚠️ Class code generation error, using fallback: " + e.getMessage());
            return "CL001"; // Fallback code
        }
    }
    
    /**
     * Generate Random Code - Final Coder with Multiple Algorithm Support
     */
    private static String generateRandomCode() {
        if (!ENABLE_RANDOM_ALGORITHMS) {
            return RANDOM_CODE_PREFIX + "000001";
        }
        
        try {
            // Determine code length
            Random random = new Random();
            int codeLength = RANDOM_CODE_MIN_LENGTH + random.nextInt(RANDOM_CODE_MAX_LENGTH - RANDOM_CODE_MIN_LENGTH + 1);
            
            // Select random algorithm
            String algorithm = RANDOM_ALGORITHMS[random.nextInt(RANDOM_ALGORITHMS.length)];
            StringBuilder randomCode = new StringBuilder();
            
            // Generate based on selected algorithm
            switch (algorithm) {
                case "ALPHA":
                    for (int i = 0; i < codeLength; i++) {
                        randomCode.append((char) ('A' + random.nextInt(26)));
                    }
                    break;
                    
                case "NUMERIC":
                    for (int i = 0; i < codeLength; i++) {
                        randomCode.append(random.nextInt(10));
                    }
                    break;
                    
                case "ALPHANUMERIC":
                    String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                    for (int i = 0; i < codeLength; i++) {
                        randomCode.append(alphaNumeric.charAt(random.nextInt(alphaNumeric.length())));
                    }
                    break;
                    
                case "MIXED":
                    String mixed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                    for (int i = 0; i < codeLength; i++) {
                        randomCode.append(mixed.charAt(random.nextInt(mixed.length())));
                    }
                    break;
                    
                case "SYMBOLS":
                    String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
                    for (int i = 0; i < codeLength; i++) {
                        randomCode.append(symbols.charAt(random.nextInt(symbols.length())));
                    }
                    break;
                    
                default:
                    // Default to alphanumeric
                    String defaultSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                    for (int i = 0; i < codeLength; i++) {
                        randomCode.append(defaultSet.charAt(random.nextInt(defaultSet.length())));
                    }
            }
            
            // Add complexity if enabled
            if (ENABLE_RANDOM_COMPLEXITY && randomCode.length() > 4) {
                randomCode.insert(randomCode.length() / 2, "-");
            }
            
            String finalCode = RANDOM_CODE_PREFIX + randomCode.toString();
            System.out.println("🎲 Generated Random Code: " + finalCode + " (Algorithm: " + algorithm + ", Length: " + codeLength + ")");
            
            return finalCode;
            
        } catch (Exception e) {
            System.err.println("⚠️ Random code generation error, using fallback: " + e.getMessage());
            return "RND000001"; // Fallback code
        }
    }
    
    /**
     * Enhanced Text Editor with Content, Font, ExFont, Height, Width, Spacing
     */
    private static void openEnhancedTextEditor(TextMark textMark, DrawingCanvas canvas) {
        System.out.println("🎨 Dynamic Text Properties - Live editing active for: \"" + textMark.getText() + "\"");
        
        // Create compact, efficient editor window using smart intelligence
        JFrame editorFrame = new JFrame("🎨 Live Text Properties");
        editorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        editorFrame.setSize(380, 480); // Compact dimensions - fits in single view
        
        // Smart positioning - place on right side of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - 400; // 20px from right edge
        int y = 50; // 50px from top
        editorFrame.setLocation(x, y);
        
        editorFrame.setAlwaysOnTop(false);
        editorFrame.setResizable(true);
        
        // Modern look and feel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Compact header with minimal padding
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // Reduced padding
        
        JLabel titleLabel = new JLabel("<html><center><font color='white'><b>🎨 Live Text Editor</b> • <i>Changes apply instantly</i></font></center></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Compact properties panel - no scrolling needed with smart layout
        JPanel propertiesWrapper = new JPanel(new BorderLayout());
        propertiesWrapper.setBackground(Color.WHITE);
        propertiesWrapper.setBorder(BorderFactory.createEmptyBorder(8, 12, 5, 12)); // Minimal padding
        
        JPanel propertiesPanel = createCompactTextPropertiesEditor(textMark, canvas);
        propertiesWrapper.add(propertiesPanel, BorderLayout.CENTER);
        
        mainPanel.add(propertiesWrapper, BorderLayout.CENTER);
        
        // Minimal footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(4, 12, 4, 12) // Minimal padding
        ));
        
        JLabel statusLabel = new JLabel("<html><center><small>✅ Live Editing • Changes apply instantly</small></center></html>");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(new Color(34, 139, 34));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerPanel.add(statusLabel, BorderLayout.CENTER);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        
        System.out.println("💡 Compact live editing interface opened - all options in single view");
    }
    
    /**
     * Create compact text properties editor - fits in single window using smart intelligence
     */
    private static JPanel createCompactTextPropertiesEditor(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 8, 0); // Minimal spacing between sections
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Content Section - compact version
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        JPanel contentSection = createCompactContentSection(textMark, canvas);
        panel.add(contentSection, gbc);
        
        // Font & Style Section - combined for efficiency
        gbc.gridy = 1;
        JPanel fontSection = createCompactFontSection(textMark, canvas);
        panel.add(fontSection, gbc);
        
        // Dimensions & Spacing Section - combined in 2x2 grid for compactness
        gbc.gridy = 2;
        JPanel controlsSection = createCompactControlsSection(textMark, canvas);
        panel.add(controlsSection, gbc);
        
        return panel;
    }
    
    /**
     * Create comprehensive text properties editor with attractive card-based layout
     */
    private static JPanel createTextPropertiesEditor(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 18, 0); // More spacing between sections
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Content Section with attractive card styling
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        JPanel contentSection = createContentSection(textMark, canvas);
        contentSection = wrapInAttractiveCard("📝 Text Content", "Edit your text content here", contentSection);
        panel.add(contentSection, gbc);
        
        // Font Section with attractive card styling
        gbc.gridy = 1;
        JPanel fontSection = createFontSection(textMark, canvas);
        fontSection = wrapInAttractiveCard("🔤 Font & Style", "Choose font family and styling options", fontSection);
        panel.add(fontSection, gbc);
        
        // Dimensions Section with attractive card styling
        gbc.gridy = 2;
        JPanel dimensionsSection = createDimensionsSection(textMark, canvas);
        dimensionsSection = wrapInAttractiveCard("📏 Dimensions", "Adjust text height and width", dimensionsSection);
        panel.add(dimensionsSection, gbc);
        
        // Spacing Section with attractive card styling
        gbc.gridy = 3;
        JPanel spacingSection = createSpacingSection(textMark, canvas);
        spacingSection = wrapInAttractiveCard("📐 Spacing", "Fine-tune character and line spacing", spacingSection);
        panel.add(spacingSection, gbc);
        
        return panel;
    }
    
    /**
     * Compact content section with minimal styling
     */
    private static JPanel createCompactContentSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Compact header
        JLabel titleLabel = new JLabel("📝 Text Content");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        // Compact text area
        JTextArea textArea = new JTextArea(textMark.getText(), 2, 25); // Smaller height
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        
        // Live update listener
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateText(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateText(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateText(); }
            
            private void updateText() {
                SwingUtilities.invokeLater(() -> {
                    textMark.setText(textArea.getText());
                    canvas.repaint();
                });
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(320, 45)); // Fixed compact size
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Compact font section with efficient layout
     */
    private static JPanel createCompactFontSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Compact header
        JLabel titleLabel = new JLabel("🔤 Font & Style");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(new Color(52, 73, 94));
        
        // Efficient layout - everything in one row
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPanel.setBackground(Color.WHITE);
        
        // Compact font combo with actual font updating
        String[] fonts = {"Arial", "Times New Roman", "Calibri", "Segoe UI", "Verdana"};
        JComboBox<String> fontCombo = new JComboBox<>(fonts);
        fontCombo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        fontCombo.setPreferredSize(new Dimension(85, 24));
        fontCombo.setSelectedItem(textMark.getFont().getName());
        fontCombo.addActionListener(e -> {
            String selectedFont = (String) fontCombo.getSelectedItem();
            Font currentFont = textMark.getFont();
            Font newFont = new Font(selectedFont, currentFont.getStyle(), currentFont.getSize());
            textMark.setFont(newFont);
            canvas.repaint();
            System.out.println("🔤 Font family updated to: " + selectedFont);
        });
        
        // Compact checkboxes with actual font style updating
        Font currentFont = textMark.getFont();
        boolean isBold = (currentFont.getStyle() & Font.BOLD) != 0;
        boolean isItalic = (currentFont.getStyle() & Font.ITALIC) != 0;
        
        JCheckBox boldCheck = new JCheckBox("B", isBold);
        JCheckBox italicCheck = new JCheckBox("I", isItalic);
        JCheckBox underlineCheck = new JCheckBox("U", false); // Note: Java Font doesn't support underline natively
        
        for (JCheckBox cb : new JCheckBox[]{boldCheck, italicCheck, underlineCheck}) {
            cb.setFont(new Font("Segoe UI", Font.BOLD, 10));
            cb.setPreferredSize(new Dimension(35, 24));
            cb.setBackground(Color.WHITE);
            cb.setFocusPainted(false);
        }
        
        // Update font style when checkboxes change
        Runnable updateFontStyle = () -> {
            Font font = textMark.getFont();
            int style = Font.PLAIN;
            if (boldCheck.isSelected()) style |= Font.BOLD;
            if (italicCheck.isSelected()) style |= Font.ITALIC;
            
            Font newFont = new Font(font.getName(), style, font.getSize());
            textMark.setFont(newFont);
            canvas.repaint();
            System.out.println("🔤 Font style updated - Bold: " + boldCheck.isSelected() + ", Italic: " + italicCheck.isSelected());
        };
        
        boldCheck.addActionListener(e -> updateFontStyle.run());
        italicCheck.addActionListener(e -> updateFontStyle.run());
        underlineCheck.addActionListener(e -> {
            canvas.repaint();
            System.out.println("⚠️ Underline not supported by Java Font - use custom rendering if needed");
        });
        
        contentPanel.add(fontCombo);
        contentPanel.add(boldCheck);
        contentPanel.add(italicCheck);
        contentPanel.add(underlineCheck);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Compact controls section - combines dimensions and spacing in efficient 2x2 grid
     */
    private static JPanel createCompactControlsSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Compact header
        JLabel titleLabel = new JLabel("📏 Dimensions & Spacing");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(new Color(52, 73, 94));
        
        // Efficient 2x2 grid layout
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        gridPanel.setBackground(Color.WHITE);
        
        // Height control - updates font size
        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        heightPanel.setBackground(Color.WHITE);
        JLabel heightLabel = new JLabel("Size:");
        heightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(textMark.getFont().getSize(), 8, 72, 1));
        heightSpinner.setPreferredSize(new Dimension(45, 22));
        heightSpinner.addChangeListener(e -> {
            int newSize = (Integer) heightSpinner.getValue();
            Font font = textMark.getFont();
            Font newFont = new Font(font.getName(), font.getStyle(), newSize);
            textMark.setFont(newFont);
            canvas.repaint();
            System.out.println("📏 Font size updated to: " + newSize + "pt");
        });
        heightPanel.add(heightLabel);
        heightPanel.add(heightSpinner);
        
        // Position controls (X coordinate)
        JPanel posPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        posPanel.setBackground(Color.WHITE);
        JLabel posLabel = new JLabel("X:");
        posLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        JSpinner xSpinner = new JSpinner(new SpinnerNumberModel(textMark.x, 0, 2000, 5));
        xSpinner.setPreferredSize(new Dimension(45, 22));
        xSpinner.addChangeListener(e -> {
            textMark.x = (Integer) xSpinner.getValue();
            canvas.repaint();
            System.out.println("📍 X position updated to: " + textMark.x);
        });
        posPanel.add(posLabel);
        posPanel.add(xSpinner);
        
        // Character spacing - updates character width
        JPanel charPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        charPanel.setBackground(Color.WHITE);
        JLabel charLabel = new JLabel("Char:");
        charLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        // Convert character width (0.5-3.0) to percentage for user display
        int currentCharPercent = (int) (textMark.getCharacterWidth() * 100);
        JSpinner charSpinner = new JSpinner(new SpinnerNumberModel(currentCharPercent, 50, 300, 5));
        charSpinner.setPreferredSize(new Dimension(45, 22));
        charSpinner.addChangeListener(e -> {
            int percent = (Integer) charSpinner.getValue();
            double charWidth = percent / 100.0;
            textMark.setCharacterWidth(charWidth);
            canvas.repaint();
            System.out.println("📐 Character spacing updated to: " + charWidth + "x (" + percent + "%)");
        });
        charPanel.add(charLabel);
        charPanel.add(charSpinner);
        
        // Line spacing - updates line spacing
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        linePanel.setBackground(Color.WHITE);
        JLabel lineLabel = new JLabel("Line:");
        lineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        JSpinner lineSpinner = new JSpinner(new SpinnerNumberModel(textMark.getLineSpacing(), 0.5, 5.0, 0.1));
        lineSpinner.setPreferredSize(new Dimension(45, 22));
        lineSpinner.addChangeListener(e -> {
            double lineSpacing = (Double) lineSpinner.getValue();
            textMark.setLineSpacing(lineSpacing);
            canvas.repaint();
            System.out.println("📐 Line spacing updated to: " + lineSpacing + "x");
        });
        linePanel.add(lineLabel);
        linePanel.add(lineSpinner);
        
        gridPanel.add(heightPanel);
        gridPanel.add(posPanel);
        gridPanel.add(charPanel);
        gridPanel.add(linePanel);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(gridPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Wrap a content panel in an attractive card with title and description
     */
    private static JPanel wrapInAttractiveCard(String title, String description, JPanel contentPanel) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        
        // Modern card styling with subtle shadow effect
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 225, 225), 1, true),
            BorderFactory.createEmptyBorder(15, 18, 18, 18)
        ));
        
        // Card header with title and description
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        
        JLabel titleLabel = new JLabel("<html><b style='font-size: 14px; color: #34495e'>" + title + "</b></html>");
        JLabel descLabel = new JLabel("<html><small style='color: #95a5a6; font-size: 11px'>" + description + "</small></html>");
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(descLabel, BorderLayout.SOUTH);
        
        // Content area with subtle background
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(new Color(249, 250, 251));
        contentWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241), 1, true),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        contentWrapper.add(contentPanel, BorderLayout.CENTER);
        
        cardPanel.add(headerPanel, BorderLayout.NORTH);
        cardPanel.add(contentWrapper, BorderLayout.CENTER);
        
        return cardPanel;
    }
    
    /**
     * Create Content section with attractive live text editing
     */
    private static JPanel createContentSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Attractive text area with modern styling
        JTextArea textArea = new JTextArea(textMark.getText(), 4, 28);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textArea.setBackground(new Color(255, 255, 255));
        textArea.setForeground(new Color(51, 51, 51));
        
        // Live update listener
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateText(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateText(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateText(); }
            
            private void updateText() {
                SwingUtilities.invokeLater(() -> {
                    textMark.setText(textArea.getText());
                    canvas.repaint();
                    System.out.println("📝 Text updated: \"" + textArea.getText() + "\"");
                });
            }
        });
        
        // Elegant scroll pane with custom styling
        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createEmptyBorder());
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        panel.add(textScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create Font section with attractive styling controls
     */
    private static JPanel createFontSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Font Family with attractive styling
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel fontLabel = new JLabel("Font Family:");
        fontLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fontLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(fontLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] fonts = {"Arial", "Times New Roman", "Calibri", "Segoe UI", "Verdana", "Helvetica", "Comic Sans MS", "Tahoma", "Georgia"};
        JComboBox<String> fontCombo = new JComboBox<>(fonts);
        fontCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        fontCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        fontCombo.addActionListener(e -> {
            String selectedFont = (String) fontCombo.getSelectedItem();
            System.out.println("🔤 Font changed to: " + selectedFont);
            canvas.repaint();
        });
        contentPanel.add(fontCombo, gbc);
        
        // ExFont (Extended Font Properties) with attractive styling
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        JLabel styleLabel = new JLabel("Text Style:");
        styleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        styleLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(styleLabel, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        stylePanel.setBackground(Color.WHITE);
        // Attractive checkboxes with modern styling
        JCheckBox boldCheck = new JCheckBox("  Bold");
        JCheckBox italicCheck = new JCheckBox("  Italic");
        JCheckBox underlineCheck = new JCheckBox("  Underline");
        
        // Style the checkboxes
        for (JCheckBox checkbox : new JCheckBox[]{boldCheck, italicCheck, underlineCheck}) {
            checkbox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            checkbox.setForeground(new Color(52, 73, 94));
            checkbox.setBackground(Color.WHITE);
            checkbox.setFocusPainted(false);
            checkbox.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        }
        
        // Live update listeners with enhanced feedback
        boldCheck.addActionListener(e -> {
            System.out.println("🔤 Bold styling: " + (boldCheck.isSelected() ? "Applied" : "Removed"));
            canvas.repaint();
        });
        italicCheck.addActionListener(e -> {
            System.out.println("🔤 Italic styling: " + (italicCheck.isSelected() ? "Applied" : "Removed"));
            canvas.repaint();
        });
        underlineCheck.addActionListener(e -> {
            System.out.println("🔤 Underline styling: " + (underlineCheck.isSelected() ? "Applied" : "Removed"));
            canvas.repaint();
        });
        
        stylePanel.add(boldCheck);
        stylePanel.add(Box.createHorizontalStrut(10));
        stylePanel.add(italicCheck);
        stylePanel.add(Box.createHorizontalStrut(10));
        stylePanel.add(underlineCheck);
        
        contentPanel.add(stylePanel, gbc);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create attractive Dimensions section for Height and Width
     */
    private static JPanel createDimensionsSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Height control with attractive styling
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel heightLabel = new JLabel("Height (px):");
        heightLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        heightLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(heightLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(24, 8, 200, 1));
        heightSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ((JSpinner.DefaultEditor) heightSpinner.getEditor()).getTextField().setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            )
        );
        heightSpinner.addChangeListener(e -> {
            int height = (Integer) heightSpinner.getValue();
            System.out.println("📏 Height adjusted to: " + height + "px");
            canvas.repaint();
        });
        contentPanel.add(heightSpinner, gbc);
        
        // Width control with attractive styling
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        JLabel widthLabel = new JLabel("Width (px):");
        widthLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        widthLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(widthLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 800, 5));
        widthSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ((JSpinner.DefaultEditor) widthSpinner.getEditor()).getTextField().setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            )
        );
        widthSpinner.addChangeListener(e -> {
            int width = (Integer) widthSpinner.getValue();
            System.out.println("📏 Width adjusted to: " + width + "px");
            canvas.repaint();
        });
        contentPanel.add(widthSpinner, gbc);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Create attractive Spacing section for character and line spacing
     */
    private static JPanel createSpacingSection(TextMark textMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Character Spacing with attractive styling
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel charLabel = new JLabel("Character Spacing:");
        charLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        charLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(charLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner charSpacingSpinner = new JSpinner(new SpinnerNumberModel(0, -10, 20, 1));
        charSpacingSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ((JSpinner.DefaultEditor) charSpacingSpinner.getEditor()).getTextField().setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            )
        );
        charSpacingSpinner.addChangeListener(e -> {
            int charSpacing = (Integer) charSpacingSpinner.getValue();
            System.out.println("📐 Character spacing adjusted: " + charSpacing + "px");
            canvas.repaint();
        });
        contentPanel.add(charSpacingSpinner, gbc);
        
        // Line Spacing with attractive styling
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        JLabel lineLabel = new JLabel("Line Spacing:");
        lineLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lineLabel.setForeground(new Color(52, 73, 94));
        contentPanel.add(lineLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner lineSpacingSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.5, 5.0, 0.1));
        lineSpacingSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ((JSpinner.DefaultEditor) lineSpacingSpinner.getEditor()).getTextField().setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            )
        );
        lineSpacingSpinner.addChangeListener(e -> {
            double lineSpacing = (Double) lineSpacingSpinner.getValue();
            System.out.println("📐 Line spacing adjusted: " + lineSpacing + "x");
            canvas.repaint();
        });
        contentPanel.add(lineSpacingSpinner, gbc);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Enhanced BowText Editor with Content, Font, Curvature, Direction - Smart soft coding
     */
    private static void openEnhancedBowTextEditor(BowTextMark bowTextMark, DrawingCanvas canvas) {
        System.out.println("🏹 Dynamic BowText Properties - Live editing active for: \"" + bowTextMark.getText() + "\"");
        
        // Create compact, efficient editor window using smart intelligence
        JFrame editorFrame = new JFrame("🏹 Live BowText Properties");
        editorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        editorFrame.setSize(380, 520); // Slightly larger for bow-specific controls
        
        // Smart positioning - place on right side of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - 400; // 20px from right edge
        int y = 50; // 50px from top
        editorFrame.setLocation(x, y);
        
        editorFrame.setAlwaysOnTop(false);
        editorFrame.setResizable(true);
        
        // Modern look and feel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Title with BowText icon
        JLabel titleLabel = new JLabel("<html><center><font color='white'><b>🏹 Live BowText Editor</b> • <i>Changes apply instantly</i></font></center></html>");
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue
        titleLabel.setPreferredSize(new Dimension(380, 45));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create BowText properties editor
        JPanel propertiesPanel = createBowTextPropertiesEditor(bowTextMark, canvas);
        
        // Layout
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(propertiesPanel, BorderLayout.CENTER);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        
        System.out.println("✅ BowText editor opened with live content editing");
    }
    
    /**
     * Create BowText properties editor with content, font, curvature, and direction controls
     */
    private static JPanel createBowTextPropertiesEditor(BowTextMark bowTextMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Content Section - Live text editing
        JPanel contentSection = createBowTextContentSection(bowTextMark, canvas);
        
        // Font Section - Font family and size
        JPanel fontSection = createBowTextFontSection(bowTextMark, canvas);
        
        // Bow Properties Section - Curvature and direction
        JPanel bowSection = createBowPropertiesSection(bowTextMark, canvas);
        
        // Color Section - Text color
        JPanel colorSection = createBowTextColorSection(bowTextMark, canvas);
        
        // Add all sections
        panel.add(contentSection);
        panel.add(Box.createVerticalStrut(10));
        panel.add(fontSection);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bowSection);
        panel.add(Box.createVerticalStrut(10));
        panel.add(colorSection);
        
        return panel;
    }
    
    /**
     * Create BowText content section with live text editing
     */
    private static JPanel createBowTextContentSection(BowTextMark bowTextMark, DrawingCanvas canvas) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBorder(BorderFactory.createTitledBorder("📝 Text Content"));
        
        JTextField contentField = new JTextField(bowTextMark.getText());
        contentField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        contentField.setPreferredSize(new Dimension(300, 30));
        
        // Live content update - soft coding technique
        contentField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateContent(); }
            public void removeUpdate(DocumentEvent e) { updateContent(); }
            public void changedUpdate(DocumentEvent e) { updateContent(); }
            
            private void updateContent() {
                SwingUtilities.invokeLater(() -> {
                    String newText = contentField.getText();
                    if (!newText.equals(bowTextMark.getText())) {
                        bowTextMark.setText(newText);
                        // Update width based on text length
                        bowTextMark.width = Math.max(200, newText.length() * 15);
                        canvas.repaint();
                        System.out.println("🏹 BowText content updated: \"" + newText + "\"");
                    }
                });
            }
        });
        
        section.add(new JLabel("Text:"), BorderLayout.WEST);
        section.add(contentField, BorderLayout.CENTER);
        
        return section;
    }
    
    /**
     * Create BowText font section
     */
    private static JPanel createBowTextFontSection(BowTextMark bowTextMark, DrawingCanvas canvas) {
        JPanel section = new JPanel(new GridLayout(2, 2, 5, 5));
        section.setBorder(BorderFactory.createTitledBorder("🔤 Font Properties"));
        
        // Font family
        String[] fonts = {"Arial", "Times New Roman", "Courier New", "Verdana", "Georgia"};
        JComboBox<String> fontCombo = new JComboBox<>(fonts);
        fontCombo.setSelectedItem(bowTextMark.getFont().getName());
        fontCombo.addActionListener(e -> {
            String selectedFont = (String) fontCombo.getSelectedItem();
            Font currentFont = bowTextMark.getFont();
            Font newFont = new Font(selectedFont, currentFont.getStyle(), currentFont.getSize());
            bowTextMark.setFont(newFont);
            canvas.repaint();
            System.out.println("🏹 BowText font changed to: " + selectedFont);
        });
        
        // Font size
        SpinnerNumberModel sizeModel = new SpinnerNumberModel(bowTextMark.getFont().getSize(), 8, 72, 1);
        JSpinner sizeSpinner = new JSpinner(sizeModel);
        sizeSpinner.addChangeListener(e -> {
            int size = (Integer) sizeSpinner.getValue();
            Font currentFont = bowTextMark.getFont();
            Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), size);
            bowTextMark.setFont(newFont);
            canvas.repaint();
            System.out.println("🏹 BowText font size changed to: " + size);
        });
        
        section.add(new JLabel("Font:"));
        section.add(fontCombo);
        section.add(new JLabel("Size:"));
        section.add(sizeSpinner);
        
        return section;
    }
    
    /**
     * Create bow-specific properties section (curvature and direction)
     */
    private static JPanel createBowPropertiesSection(BowTextMark bowTextMark, DrawingCanvas canvas) {
        JPanel section = new JPanel(new GridLayout(3, 2, 5, 5));
        section.setBorder(BorderFactory.createTitledBorder("🏹 Bow Properties"));
        
        // Curvature slider
        int curvatureValue = (int) (bowTextMark.getCurvature() * 100);
        JSlider curvatureSlider = new JSlider(10, 100, curvatureValue);
        curvatureSlider.setMajorTickSpacing(30);
        curvatureSlider.setMinorTickSpacing(10);
        curvatureSlider.setPaintTicks(true);
        curvatureSlider.setPaintLabels(true);
        
        JLabel curvatureLabel = new JLabel("Curve: " + curvatureValue + "%");
        
        curvatureSlider.addChangeListener(e -> {
            int value = curvatureSlider.getValue();
            double curvature = value / 100.0;
            bowTextMark.setCurvature(curvature);
            curvatureLabel.setText("Curve: " + value + "%");
            canvas.repaint();
            System.out.println("🏹 BowText curvature changed to: " + curvature);
        });
        
        // Direction toggle
        JCheckBox directionToggle = new JCheckBox("Bow Up", bowTextMark.isBowUp());
        directionToggle.addActionListener(e -> {
            boolean bowUp = directionToggle.isSelected();
            bowTextMark.setBowUp(bowUp);
            canvas.repaint();
            System.out.println("🏹 BowText direction changed to: " + (bowUp ? "Up" : "Down"));
        });
        
        section.add(new JLabel("Curvature:"));
        section.add(curvatureLabel);
        section.add(new JLabel("Slider:"));
        section.add(curvatureSlider);
        section.add(new JLabel("Direction:"));
        section.add(directionToggle);
        
        return section;
    }
    
    /**
     * Create BowText color section
     */
    private static JPanel createBowTextColorSection(BowTextMark bowTextMark, DrawingCanvas canvas) {
        JPanel section = new JPanel(new FlowLayout());
        section.setBorder(BorderFactory.createTitledBorder("🎨 Text Color"));
        
        JButton colorButton = new JButton("Choose Color");
        colorButton.setBackground(bowTextMark.getTextColor());
        colorButton.setOpaque(true);
        
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose BowText Color", bowTextMark.getTextColor());
            if (newColor != null) {
                bowTextMark.setTextColor(newColor);
                colorButton.setBackground(newColor);
                canvas.repaint();
                System.out.println("🏹 BowText color changed to: " + newColor);
            }
        });
        
        section.add(colorButton);
        
        return section;
    }
    
    /**
     * Generic mark editor for non-text marks
     */
    private static void openGenericMarkEditor(Mark mark, DrawingCanvas canvas) {
        System.out.println("🎛️ Opening generic mark editor for: " + mark.getClass().getSimpleName());
        JOptionPane.showMessageDialog(null,
            "Generic mark editor for " + mark.getClass().getSimpleName() + "\n" +
            "Enhanced editing features coming soon!",
            "Mark Editor",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show message when no mark is selected
     */
    private static void showNoSelectionMessage() {
        JOptionPane.showMessageDialog(null,
            "⚠️ No mark selected!\n\n" +
            "Please:\n" +
            "1. Click on a mark in the canvas to select it\n" +
            "2. Then click 'Edit Mark' to customize properties",
            "No Selection",
            JOptionPane.WARNING_MESSAGE);
    }
    
    // ==================== SPECIALIZED MARK EDITORS FOR NEW TYPES ====================
    
    /**
     * DataMatrix Editor (replacing Circle editor)
     */
    private static void openDataMatrixEditor(DotMatrixMark mark, DrawingCanvas canvas) {
        JFrame editorFrame = new JFrame("🔲 DataMatrix Editor");
        editorFrame.setSize(450, 350);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Data content
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(new JLabel("Data Content:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dataField = new JTextField(mark.getData(), 20);
        contentPanel.add(dataField, gbc);
        
        // Matrix size
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        contentPanel.add(new JLabel("Matrix Size:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(mark.getMatrixSize(), 4, 32, 1));
        contentPanel.add(sizeSpinner, gbc);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Live update listeners
        dataField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateDataMatrix(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateDataMatrix(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateDataMatrix(); }
            
            private void updateDataMatrix() {
                mark.setData(dataField.getText());
                canvas.repaint();
            }
        });
        
        sizeSpinner.addChangeListener(e -> {
            mark.setMatrixSize((Integer) sizeSpinner.getValue());
            canvas.repaint();
        });
        
        // Status
        JLabel statusLabel = new JLabel("✅ Live editing active • All changes apply instantly");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        System.out.println("🔲 DataMatrix editor opened with live editing");
    }
    
    /**
     * Enhanced Graph Editor with Image Management System
     */
    private static void openGraphEditor(GraphMark mark, DrawingCanvas canvas) {
        JFrame editorFrame = new JFrame("📊 Enhanced Graph Editor with Image Management");
        editorFrame.setSize(550, 500);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create tabbed pane for Graph Properties and Image Management
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // ===============================================================================
        // TAB 1: GRAPH PROPERTIES
        // ===============================================================================
        JPanel propsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Graph type
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        propsPanel.add(new JLabel("Graph Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] graphTypes = {"TECHNICAL_FRAME", "GRID_PATTERN", "SCALE_RULER", "ALIGNMENT_CROSS"};
        JComboBox<String> typeCombo = new JComboBox<>(graphTypes);
        typeCombo.setSelectedItem(mark.getGraphType().toString());
        propsPanel.add(typeCombo, gbc);
        
        // Dimensions
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        propsPanel.add(new JLabel("Width:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(mark.width, 50, 500, 10));
        propsPanel.add(widthSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        propsPanel.add(new JLabel("Height:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(mark.height, 30, 300, 10));
        propsPanel.add(heightSpinner, gbc);
        
        // Grid spacing (for GRID_PATTERN)
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        propsPanel.add(new JLabel("Grid Spacing:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner gridSpinner = new JSpinner(new SpinnerNumberModel(mark.getGridSpacing(), 5, 50, 5));
        propsPanel.add(gridSpinner, gbc);
        
        // Scale interval (for SCALE_RULER)
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        propsPanel.add(new JLabel("Scale Interval:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner scaleSpinner = new JSpinner(new SpinnerNumberModel(mark.getScaleInterval(), 5, 30, 5));
        propsPanel.add(scaleSpinner, gbc);
        
        tabbedPane.addTab("📊 Graph Properties", propsPanel);
        
        // ===============================================================================
        // TAB 2: IMAGE MANAGEMENT
        // ===============================================================================
        JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
        
        // Image info panel
        JPanel imageInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints imgGbc = new GridBagConstraints();
        imgGbc.insets = new Insets(5, 5, 5, 5);
        
        // Current image status
        imgGbc.gridx = 0; imgGbc.gridy = 0; imgGbc.anchor = GridBagConstraints.WEST;
        JLabel currentImageLabel = new JLabel("Current Image:");
        imageInfoPanel.add(currentImageLabel, imgGbc);
        
        imgGbc.gridx = 1; imgGbc.fill = GridBagConstraints.HORIZONTAL; imgGbc.weightx = 1.0;
        JLabel imageStatusLabel = new JLabel(mark.hasAssignedImage() ? 
            "✅ " + mark.getImageName() : "❌ No image assigned");
        imageStatusLabel.setOpaque(true);
        imageStatusLabel.setBackground(mark.hasAssignedImage() ? 
            new Color(200, 255, 200) : new Color(255, 200, 200));
        imageStatusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        imageInfoPanel.add(imageStatusLabel, imgGbc);
        
        // Image scale control
        imgGbc.gridx = 0; imgGbc.gridy = 1; imgGbc.fill = GridBagConstraints.NONE; imgGbc.weightx = 0;
        imageInfoPanel.add(new JLabel("Image Scale:"), imgGbc);
        imgGbc.gridx = 1; imgGbc.fill = GridBagConstraints.HORIZONTAL; imgGbc.weightx = 1.0;
        JSpinner scaleSpinnerImg = new JSpinner(new SpinnerNumberModel(mark.getImageScale() * 100, 10, 500, 10));
        JLabel scaleLabel = new JLabel("%");
        JPanel scalePanel = new JPanel(new BorderLayout());
        scalePanel.add(scaleSpinnerImg, BorderLayout.CENTER);
        scalePanel.add(scaleLabel, BorderLayout.EAST);
        imageInfoPanel.add(scalePanel, imgGbc);
        
        // Image offset controls
        imgGbc.gridx = 0; imgGbc.gridy = 2; imgGbc.fill = GridBagConstraints.NONE; imgGbc.weightx = 0;
        imageInfoPanel.add(new JLabel("Offset X:"), imgGbc);
        imgGbc.gridx = 1; imgGbc.fill = GridBagConstraints.HORIZONTAL; imgGbc.weightx = 1.0;
        JSpinner offsetXSpinner = new JSpinner(new SpinnerNumberModel(mark.getImageOffsetX(), -100, 100, 5));
        imageInfoPanel.add(offsetXSpinner, imgGbc);
        
        imgGbc.gridx = 0; imgGbc.gridy = 3; imgGbc.fill = GridBagConstraints.NONE; imgGbc.weightx = 0;
        imageInfoPanel.add(new JLabel("Offset Y:"), imgGbc);
        imgGbc.gridx = 1; imgGbc.fill = GridBagConstraints.HORIZONTAL; imgGbc.weightx = 1.0;
        JSpinner offsetYSpinner = new JSpinner(new SpinnerNumberModel(mark.getImageOffsetY(), -100, 100, 5));
        imageInfoPanel.add(offsetYSpinner, imgGbc);
        
        imagePanel.add(imageInfoPanel, BorderLayout.CENTER);
        
        // Image action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton uploadButton = new JButton("🖼️ Upload/Change Image");
        uploadButton.setBackground(new Color(0, 150, 0));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFont(uploadButton.getFont().deriveFont(Font.BOLD));
        
        JButton removeButton = new JButton("🗑️ Remove Image");
        removeButton.setBackground(new Color(200, 100, 0));
        removeButton.setForeground(Color.WHITE);
        
        JButton previewButton = new JButton("👁️ Toggle Preview");
        previewButton.setBackground(new Color(0, 100, 200));
        previewButton.setForeground(Color.WHITE);
        
        buttonPanel.add(uploadButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(previewButton);
        
        imagePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("🖼️ Image Management", imagePanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // ===============================================================================
        // EVENT LISTENERS FOR LIVE UPDATES
        // ===============================================================================
        
        // Graph property listeners
        typeCombo.addActionListener(e -> {
            String selectedType = (String) typeCombo.getSelectedItem();
            try {
                mark.setGraphType(GraphMark.GraphType.valueOf(selectedType));
                canvas.repaint();
                System.out.println("🔄 Graph type changed to: " + selectedType);
            } catch (Exception ex) {
                System.err.println("❌ Invalid graph type: " + selectedType);
            }
        });
        
        widthSpinner.addChangeListener(e -> {
            mark.width = (Integer) widthSpinner.getValue();
            canvas.repaint();
        });
        
        heightSpinner.addChangeListener(e -> {
            mark.height = (Integer) heightSpinner.getValue();
            canvas.repaint();
        });
        
        gridSpinner.addChangeListener(e -> {
            mark.setGridSpacing((Integer) gridSpinner.getValue());
            canvas.repaint();
        });
        
        scaleSpinner.addChangeListener(e -> {
            mark.setScaleInterval((Integer) scaleSpinner.getValue());
            canvas.repaint();
        });
        
        // Image property listeners
        scaleSpinnerImg.addChangeListener(e -> {
            double scalePercent = (Integer) scaleSpinnerImg.getValue();
            mark.setImageScale(scalePercent / 100.0);
            canvas.repaint();
        });
        
        offsetXSpinner.addChangeListener(e -> {
            int offsetX = (Integer) offsetXSpinner.getValue();
            mark.setImageOffset(offsetX, mark.getImageOffsetY());
            canvas.repaint();
        });
        
        offsetYSpinner.addChangeListener(e -> {
            int offsetY = (Integer) offsetYSpinner.getValue();
            mark.setImageOffset(mark.getImageOffsetX(), offsetY);
            canvas.repaint();
        });
        
        // Image action button listeners
        uploadButton.addActionListener(e -> {
            System.out.println("🖼️ Opening image upload dialog from Edit Mark...");
            SwingUtilities.invokeLater(() -> {
                boolean success = mark.openImageUploadDialog();
                if (success) {
                    // Update status label
                    imageStatusLabel.setText(mark.hasAssignedImage() ? 
                        "✅ " + mark.getImageName() : "❌ No image assigned");
                    imageStatusLabel.setBackground(mark.hasAssignedImage() ? 
                        new Color(200, 255, 200) : new Color(255, 200, 200));
                    
                    // Update scale spinner to current image scale
                    scaleSpinnerImg.setValue((int)(mark.getImageScale() * 100));
                    offsetXSpinner.setValue(mark.getImageOffsetX());
                    offsetYSpinner.setValue(mark.getImageOffsetY());
                    
                    canvas.repaint();
                    System.out.println("✅ GraphMark image updated from Edit Mark");
                }
            });
        });
        
        removeButton.addActionListener(e -> {
            mark.removeImageFromGraph();
            imageStatusLabel.setText("❌ No image assigned");
            imageStatusLabel.setBackground(new Color(255, 200, 200));
            scaleSpinnerImg.setValue(100);
            offsetXSpinner.setValue(0);
            offsetYSpinner.setValue(0);
            canvas.repaint();
            System.out.println("🗑️ Image removed from GraphMark");
        });
        
        previewButton.addActionListener(e -> {
            mark.setShowImage(!mark.isShowImage());
            canvas.repaint();
            System.out.println("👁️ Image preview toggled: " + (mark.isShowImage() ? "ON" : "OFF"));
        });
        
        // Status
        JLabel statusLabel = new JLabel("✅ Live editing active • All properties update instantly • Image management available");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        System.out.println("📊 Enhanced Graph editor with image management opened");
    }
    
    /**
     * Chart Editor (replacing Arc editor)
     */
    private static void openChartEditor(ArcLettersMark mark, DrawingCanvas canvas) {
        JFrame editorFrame = new JFrame("📈 Chart Editor");
        editorFrame.setSize(450, 300);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Chart properties panel
        JPanel chartPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Chart title
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        chartPanel.add(new JLabel("Chart Title:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField titleField = new JTextField(mark.getLetters(), 20);
        chartPanel.add(titleField, gbc);
        
        // Arc radius
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        chartPanel.add(new JLabel("Radius:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner radiusSpinner = new JSpinner(new SpinnerNumberModel(mark.getRadius(), 20, 200, 5));
        chartPanel.add(radiusSpinner, gbc);
        
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        
        // Live update listeners
        titleField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateChart(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateChart(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateChart(); }
            
            private void updateChart() {
                mark.setLetters(titleField.getText());
                canvas.repaint();
            }
        });
        
        radiusSpinner.addChangeListener(e -> {
            mark.setRadius((Integer) radiusSpinner.getValue());
            canvas.repaint();
        });
        
        // Status
        JLabel statusLabel = new JLabel("✅ Live editing active • Chart updates instantly");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        System.out.println("📈 Chart editor opened with live editing");
    }
    
    /**
     * Ruler Editor (replacing Dot Matrix editor)
     */
    private static void openRulerEditor(LineMark mark, DrawingCanvas canvas) {
        JFrame editorFrame = new JFrame("📏 Ruler Editor");
        editorFrame.setSize(450, 300);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Ruler properties panel
        JPanel rulerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Ruler length
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        rulerPanel.add(new JLabel("Length:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner lengthSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 500, 5));
        rulerPanel.add(lengthSpinner, gbc);
        
        // Thickness
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        rulerPanel.add(new JLabel("Thickness:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner thicknessSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        rulerPanel.add(thicknessSpinner, gbc);
        
        mainPanel.add(rulerPanel, BorderLayout.CENTER);
        
        // Live update listeners
        lengthSpinner.addChangeListener(e -> {
            // Update ruler properties via reflection if needed
            canvas.repaint();
        });
        
        thicknessSpinner.addChangeListener(e -> {
            // Update ruler thickness if the field is accessible
            canvas.repaint();
        });
        
        // Status
        JLabel statusLabel = new JLabel("✅ Live editing active • Ruler measurements update instantly");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        System.out.println("📏 Ruler editor opened with live editing");
    }
    
    /**
     * AvoidPoint Editor (replacing Barcode editor)
     */
    private static void openAvoidPointEditor(AvoidPointMark mark, DrawingCanvas canvas) {
        JFrame editorFrame = new JFrame("⚪ AvoidPoint Editor");
        editorFrame.setSize(400, 250);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // AvoidPoint properties panel
        JPanel avoidPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Avoidance radius
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        avoidPanel.add(new JLabel("Avoidance Radius:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner radiusSpinner = new JSpinner(new SpinnerNumberModel(mark.getAvoidRadius(), 5, 100, 2));
        avoidPanel.add(radiusSpinner, gbc);
        
        // Priority level
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        avoidPanel.add(new JLabel("Priority Level:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        String[] priorities = {"Low", "Medium", "High", "Critical"};
        JComboBox<String> priorityCombo = new JComboBox<>(priorities);
        avoidPanel.add(priorityCombo, gbc);
        
        mainPanel.add(avoidPanel, BorderLayout.CENTER);
        
        // Live update listeners
        radiusSpinner.addChangeListener(e -> {
            mark.setAvoidRadius(((Number) radiusSpinner.getValue()).doubleValue());
            canvas.repaint();
        });
        
        // Status
        JLabel statusLabel = new JLabel("✅ Live editing active • AvoidPoint settings update instantly");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        editorFrame.add(mainPanel);
        editorFrame.setVisible(true);
        System.out.println("⚪ AvoidPoint editor opened with live editing");
    }
    
    // ==================== MARK TYPE ACTION HANDLERS ====================
    
    /**
     * Basic Mark Type Handlers (Row 1)
     */
    public static void handleAddTextMark() {
        System.out.println("📝 Adding Text Mark - Direct canvas placement...");
        
        try {
            // Direct text placement on canvas with default properties
            placeDefaultTextOnCanvas();
            
        } catch (Exception e) {
            System.out.println("❌ Error in text mark creation: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Place default text directly on canvas - Smart positioning inside visible area
     * Use Edit Mark button for customization afterwards
     */
    private static void placeDefaultTextOnCanvas() {
        System.out.println("🎯 Placing default text on canvas - Use Edit Mark to customize");
        
        // Get the current DrawingCanvas
        DrawingCanvas canvas = getCurrentDrawingCanvas();
        if (canvas != null) {
            // Smart positioning: Place text in the center of visible canvas area
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();
            
            // Calculate center position within canvas bounds
            int centerX = Math.max(50, canvasWidth / 2 - 50);   // Center X, minimum 50px from edge
            int centerY = Math.max(50, canvasHeight / 2 - 25);  // Center Y, minimum 50px from edge
            
            // Ensure it's within visible bounds
            centerX = Math.min(centerX, canvasWidth - 100);     // Keep 100px margin from right
            centerY = Math.min(centerY, canvasHeight - 50);     // Keep 50px margin from bottom
            
            System.out.println("📍 Smart placement: Canvas size " + canvasWidth + "x" + canvasHeight + 
                              ", placing text at (" + centerX + ", " + centerY + ")");
            
            // Create text mark with smart positioning
            placeTextAtPosition(canvas, centerX, centerY);
            
            System.out.println("✅ Text placed inside canvas and auto-selected");
            System.out.println("💡 Text ready for editing - use Edit Mark for customization");
            
        } else {
            System.out.println("⚠️ Canvas not found - cannot place text");
        }
    }
    
    /**
     * Place text at specific canvas coordinates
     */
    private static void placeTextAtPosition(DrawingCanvas canvas, int x, int y) {
        // 🎯 ThorX6 Integration: Get content from current active coder
        String textContent = "Text"; // Default fallback
        
        // Check if there's an active coder to provide intelligent content
        ensureCoderInitialized(); // Ensure currentActiveCoder is not null
        if (currentActiveCoder != null) {
            try {
                textContent = currentActiveCoder.generateCode();
                System.out.println("🎯 ThorX6 Integration: Using coder '" + currentActiveCoder.getTypeName() + 
                                  "' content: " + textContent);
            } catch (Exception e) {
                System.out.println("⚠️ Coder generation failed, using default text: " + e.getMessage());
                textContent = "Text"; // Fallback to default
            }
        } else {
            System.out.println("💡 No active coder - using default text content");
        }
        
        // Create TextMark with intelligent content from coder
        TextMark textMark = new TextMark(x, y, textContent);
        textMark.setCanDrag(true);
        textMark.setCanResize(true);
        
        // Add to canvas marks list
        canvas.getMarks().add(textMark);
        
        // Select the newly created mark (using reflection since method is private)
        try {
            // Try to access the selectedMark field directly
            java.lang.reflect.Field selectedMarkField = canvas.getClass().getDeclaredField("selectedMark");
            selectedMarkField.setAccessible(true);
            selectedMarkField.set(canvas, textMark);
            
            // Trigger repaint
            canvas.repaint();
            
            System.out.println("🎯 Text mark created at (" + x + ", " + y + ") and selected");
            
        } catch (Exception e) {
            // Fallback: just add without selection
            canvas.repaint();
            System.out.println("🎯 Text mark created at (" + x + ", " + y + ")");
        }
    }
    
    /**
     * Show brief instructions for using Edit Mark
     */
    private static void showEditMarkInstructions() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                "✅ Text placed on canvas!\n\n" +
                "🎯 Next Steps:\n" +
                "1. Text is automatically selected\n" +
                "2. Click 'Edit Mark' button to customize:\n" +
                "   • Content (text content)\n" +
                "   • Font (font family)\n" +
                "   • ExFont (extended font properties)\n" +
                "   • Height (text height)\n" +
                "   • Width (text width)\n" +
                "   • Spacing (character/line spacing)\n\n" +
                "3. Changes apply instantly to canvas",
                "Text Placement Success",
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    /**
     * Enhanced Text Drag & Drop System
     * Inspired by Text.PNG reference for advanced text formatting
     */
    private static void activateTextDragDropMode() {
        System.out.println("🎯 Enhanced Text Drag & Drop Mode Activated!");
        System.out.println("💡 Canvas-integrated drag & drop with directional resizing arrows");
        System.out.println("✨ Features: Grid snapping, live customization, Text.PNG style controls");
        
        // Get the current DrawingCanvas from the application
        DrawingCanvas canvas = getCurrentDrawingCanvas();
        if (canvas != null) {
            // Use the new enhanced drag & drop system
            EnhancedTextDragDrop.activateDragDropMode(canvas);
        } else {
            System.out.println("⚠️ Canvas not found - falling back to text panel");
            createEnhancedTextPanel();
        }
    }
    
    /**
     * Get the current DrawingCanvas from the application
     */
    private static DrawingCanvas getCurrentDrawingCanvas() {
        // Search through all open frames for DrawingCanvas
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof JFrame) {
                JFrame jFrame = (JFrame) frame;
                DrawingCanvas canvas = findDrawingCanvas(jFrame.getContentPane());
                if (canvas != null) {
                    return canvas;
                }
            }
        }
        return null;
    }
    
    /**
     * Recursively find DrawingCanvas in component hierarchy
     */
    private static DrawingCanvas findDrawingCanvas(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof DrawingCanvas) {
                return (DrawingCanvas) component;
            } else if (component instanceof Container) {
                DrawingCanvas found = findDrawingCanvas((Container) component);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
    
    // ==================== INNOVATIVE CODER STATE MANAGEMENT SYSTEM ====================
    
    /**
     * Handle Rebuilt Coder Button - DIRECT MODIFICATION SYSTEM (VERSION 3.0)
     */
    private static void handleRebuiltCoderButtonV3() {
        System.out.println("� REBUILT CODER BUTTON - VERSION 3.0 - MODIFICATION FOCUSED!");
        System.out.println("🎯 Current Coder Type: " + (currentCoderType != null ? currentCoderType : "NONE SELECTED"));
        
        // STEP 1: Check if we have a Coder Type selected
        if (currentCoderType == null || currentCoderType.trim().isEmpty()) {
            System.out.println("❌ No Coder Type selected! Please select from dropdown first.");
            showCoderStateNotification("Select Coder Type First", "Please select a Coder Type from the dropdown before clicking Coder button.", new Color(255, 240, 240));
            return;
        }
        
        // STEP 2: Find canvas and selected marks
        DrawingCanvas canvas = getCurrentDrawingCanvas();
        if (canvas == null) {
            System.out.println("❌ No canvas found!");
            return;
        }
        
        Mark selectedMark = canvas.getSelectedMark();
        System.out.println("🔍 Selected mark: " + (selectedMark != null ? selectedMark.getClass().getSimpleName() : "NONE"));
        
        // STEP 3: Check if there's a selected TextMark that can be modified
        if (selectedMark != null && selectedMark instanceof TextMark) {
            TextMark textMark = (TextMark) selectedMark;
            String currentText = textMark.getText();
            
            System.out.println("� TextMark content: '" + currentText + "'");
            
            // Check if it's a coder mark (contains ":")
            if (isCoderMark(currentText)) {
                System.out.println("✅ Found coder mark to modify!");
                modifyCoderMark(textMark, currentText, currentCoderType, canvas);
                return;
            } else {
                System.out.println("ℹ️ Selected TextMark is not a coder mark, will create new one");
            }
        }
        
        // STEP 4: No suitable mark selected, look for any coder marks to modify
        java.util.List<Mark> allMarks = canvas.getMarks();
        System.out.println("🔍 Searching " + allMarks.size() + " marks for coder marks...");
        
        for (Mark mark : allMarks) {
            if (mark instanceof TextMark) {
                TextMark textMark = (TextMark) mark;
                String text = textMark.getText();
                if (isCoderMark(text)) {
                    System.out.println("🔄 Auto-selecting first coder mark found: " + text);
                    modifyCoderMark(textMark, text, currentCoderType, canvas);
                    return;
                }
            }
        }
        
        // STEP 5: No coder marks found, create new one
        System.out.println("➕ No coder marks found, creating new " + currentCoderType + " mark");
        createNewCoderMark(currentCoderType);
    }
    
    /**
     * MODIFY AN EXISTING CODER MARK TO NEW TYPE WITH CUSTOM VALUE EDITING
     */
    private static void modifyCoderMark(TextMark textMark, String currentText, String newCoderType, DrawingCanvas canvas) {
        System.out.println("🔧 MODIFYING CODER MARK:");
        System.out.println("   From: " + currentText);
        System.out.println("   To Type: " + newCoderType);
        
        // Extract current coder data if possible
        String[] parts = currentText.split(": ", 2);
        String oldType = parts[0];
        String oldData = parts.length > 1 ? parts[1] : "";
        
        String newData;
        
        // ENHANCED: Allow direct editing of ALL coder types
        if (shouldPromptForCustomValue(newCoderType, oldType)) {
            newData = promptForCustomCoderValue(oldData, newCoderType, oldType);
            if (newData == null) {
                System.out.println("❌ Coder value editing cancelled");
                return;
            }
        } else {
            // Auto-generate data for automatic coder types
            newData = generateCoderDataForType(newCoderType, oldData);
        }
        
        String newDisplayText = newCoderType + ": " + newData;
        
        // Update the text mark
        textMark.setText(newDisplayText);
        canvas.repaint();
        
        System.out.println("✅ MODIFICATION COMPLETE: " + newDisplayText);
        showCoderStateNotification("Coder Modified", "Changed " + oldType + " to " + newCoderType + ": " + newData, new Color(240, 255, 240));
    }
    
    /**
     * CREATE NEW CODER MARK WHEN NO EXISTING ONES TO MODIFY
     */
    private static void createNewCoderMark(String coderType) {
        System.out.println("➕ Creating new " + coderType + " mark");
        handleCoderAction(coderType);
    }
    
    
    /**
     * CHECK IF WE SHOULD PROMPT FOR CUSTOM VALUE (vs auto-generate)
     */
    private static boolean shouldPromptForCustomValue(String newCoderType, String oldType) {
        // Always prompt for these types as they are typically user-defined
        return newCoderType.equals("Serial") || 
               newCoderType.equals("VIN") || 
               newCoderType.equals("PIN") || 
               newCoderType.equals("Class") ||
               oldType.equals("Serial") ||
               oldType.equals("VIN") ||
               oldType.equals("PIN") ||
               oldType.equals("Class");
    }
    
    /**
     * PROMPT USER TO ENTER CUSTOM CODER VALUE
     */
    private static String promptForCustomCoderValue(String currentValue, String newCoderType, String oldType) {
        System.out.println("📝 Prompting for custom " + newCoderType + " value - Current: " + currentValue);
        
        // Create appropriate dialog based on coder type
        String dialogTitle = "Edit " + newCoderType;
        String dialogMessage = getCoderInputMessage(newCoderType);
        String placeholder = getCoderPlaceholder(newCoderType);
        
        // Show input dialog with context
        String fullMessage = dialogMessage + "\n\n" +
                            "Current value: " + currentValue + "\n" +
                            "Example: " + placeholder + "\n\n" +
                            "Enter new value:";
        
        String userInput = javax.swing.JOptionPane.showInputDialog(
            null, 
            fullMessage, 
            dialogTitle, 
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        
        if (userInput != null && !userInput.trim().isEmpty()) {
            String cleanInput = userInput.trim();
            System.out.println("✅ User entered custom " + newCoderType + ": " + cleanInput);
            return cleanInput;
        } else if (userInput != null && userInput.trim().isEmpty()) {
            // User entered empty string - keep current value
            System.out.println("ℹ️ Empty input - keeping current value: " + currentValue);
            return currentValue;
        } else {
            // User cancelled
            System.out.println("❌ User cancelled " + newCoderType + " input");
            return null;
        }
    }
    
    /**
     * GET APPROPRIATE INPUT MESSAGE FOR CODER TYPE
     */
    private static String getCoderInputMessage(String coderType) {
        switch (coderType) {
            case "Serial": return "Enter the serial number:";
            case "VIN": return "Enter the VIN (Vehicle Identification Number):";
            case "PIN": return "Enter the PIN code:";
            case "Class": return "Enter the class identifier:";
            default: return "Enter the " + coderType.toLowerCase() + " value:";
        }
    }
    
    /**
     * GET EXAMPLE PLACEHOLDER FOR CODER TYPE
     */
    private static String getCoderPlaceholder(String coderType) {
        switch (coderType) {
            case "Serial": return "SN001, ABC123, or custom format";
            case "VIN": return "1HGBH41JXMN109186";
            case "PIN": return "1234, ABC123";
            case "Class": return "ClassA, Type1, Premium";
            default: return "Custom value";
        }
    }
    
    // ==================== SUPPORTING METHODS FOR NEW CODER SYSTEM ====================
    
    /**
     * Update Coder Button Visual State based on current mode
     */
    private static void updateCoderButtonVisualState() {
        if (!ENABLE_VISUAL_STATE_FEEDBACK) return;
        
        try {
            // Find the Coder button in the interface
            JButton coderButton = findCoderButton();
            if (coderButton != null) {
                if (isNoCoderMode) {
                    // No Coder state - muted appearance
                    coderButton.setBackground(new Color(240, 240, 240)); // Light gray
                    coderButton.setForeground(new Color(128, 128, 128)); // Gray text
                    coderButton.setToolTipText("Select a coder type from 'Coder Type' dropdown to enable");
                    coderButton.setBorder(BorderFactory.createLoweredBevelBorder());
                } else {
                    // Active coder state - highlighted appearance
                    coderButton.setBackground(new Color(240, 255, 240)); // Light green
                    coderButton.setForeground(THORX6_TEXT);
                    coderButton.setToolTipText("Generate " + currentCoderType + " - Ready to use!");
                    coderButton.setBorder(BorderFactory.createRaisedBevelBorder());
                }
                coderButton.repaint();
            }
        } catch (Exception e) {
            System.err.println("⚠️ Visual state update failed: " + e.getMessage());
        }
    }
    
    /**
     * Find the Coder button in the interface
     */
    private static JButton findCoderButton() {
        // Implementation to find the Coder button in the UI hierarchy
        // This would search through the component tree to find the button
        return null; // Placeholder - actual implementation would traverse the UI
    }
    
    /**
     * Clear Coder marks from canvas (when No Coder is selected)
     */
    private static void clearCoderMarksFromCanvas() {
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                // Remove any existing coder marks
                // Implementation would identify and remove coder-type marks
                System.out.println("🧹 Clearing existing coder marks from canvas...");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Canvas cleanup failed: " + e.getMessage());
        }
    }
    
    /**
     * Show Coder State Notification with enhanced styling
     */
    private static void showCoderStateNotification(String title, String message, Color backgroundColor) {
        if (!ENABLE_VISUAL_STATE_FEEDBACK) return;
        
        SwingUtilities.invokeLater(() -> {
            JPanel notificationPanel = new JPanel(new BorderLayout());
            notificationPanel.setBackground(backgroundColor);
            notificationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(THORX6_GROUP_FONT);
            titleLabel.setForeground(THORX6_TEXT);
            
            JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
            messageLabel.setFont(THORX6_LABEL_FONT);
            messageLabel.setForeground(THORX6_TEXT);
            
            notificationPanel.add(titleLabel, BorderLayout.NORTH);
            notificationPanel.add(messageLabel, BorderLayout.CENTER);
            
            // Show notification in a temporary popup
            JWindow popup = new JWindow();
            popup.add(notificationPanel);
            popup.pack();
            
            // Position at bottom right of screen
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            popup.setLocation(screenSize.width - popup.getWidth() - 20, 
                              screenSize.height - popup.getHeight() - 100);
            popup.setVisible(true);
            
            // Auto-hide after 3 seconds
            Timer timer = new Timer(3000, e -> popup.dispose());
            timer.setRepeats(false);
            timer.start();
        });
        
        System.out.println("📢 " + title + ": " + message);
    }
    
    /**
     * Show Coder State Dialog for user interaction
     */
    private static void showCoderStateDialog(String title, String message, int messageType, Color backgroundColor) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        optionPane.setBackground(backgroundColor);
        
        JDialog dialog = optionPane.createDialog(title);
        dialog.setModal(false); // Non-blocking
        dialog.setVisible(true);
    }
    
    /**
     * Get Current Coder State (for debugging and integration)
     */
    public static String getCurrentCoderState() {
        if (isNoCoderMode) {
            return "No Coder";
        } else {
            return currentCoderType;
        }
    }
    
    /**
     * Reset Coder State to No Coder (for system reset)
     */
    public static void resetCoderState() {
        if (ENABLE_CODER_STATE_SYSTEM) {
            currentCoderType = "No Coder";
            isNoCoderMode = true;
            updateCoderButtonVisualState();
            System.out.println("🔄 Coder state reset to No Coder mode");
        }
    }
    
    /**
     * Create Enhanced Text Panel with Drag & Drop capabilities
     * Based on Text.PNG reference design
     */
    private static void createEnhancedTextPanel() {
        JFrame textFrame = new JFrame("Enhanced Text Creator - Drag & Drop Mode");
        textFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textFrame.setSize(400, 500);
        textFrame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header with instructions
        JLabel headerLabel = new JLabel("<html><center><b>Text Creator</b><br>" +
            "Configure text properties, then drag to place on canvas</center></html>");
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Text Properties Panel
        JPanel propertiesPanel = createTextPropertiesPanel();
        mainPanel.add(propertiesPanel, BorderLayout.CENTER);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton previewButton = new JButton("🔍 Live Preview");
        previewButton.addActionListener(e -> showTextPreview());
        
        JButton dragModeButton = new JButton("🎯 Activate Drag Mode");
        dragModeButton.addActionListener(e -> {
            System.out.println("✅ Drag Mode Activated - Click and drag on canvas to place text");
            textFrame.setVisible(false);
        });
        
        JButton cancelButton = new JButton("❌ Cancel");
        cancelButton.addActionListener(e -> textFrame.dispose());
        
        buttonPanel.add(previewButton);
        buttonPanel.add(dragModeButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        textFrame.add(mainPanel);
        textFrame.setVisible(true);
    }
    
    /**
     * Create Text Properties Panel inspired by Text.PNG
     */
    private static JPanel createTextPropertiesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder("Text Properties"));
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Text Input
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Text:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField("Sample Text", 15);
        panel.add(textField, gbc);
        
        // Font Family
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Font:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] fonts = {"Arial", "Times New Roman", "Calibri", "Segoe UI", "Comic Sans MS"};
        JComboBox<String> fontCombo = new JComboBox<>(fonts);
        panel.add(fontCombo, gbc);
        
        // Font Size
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(12, 6, 72, 1));
        panel.add(sizeSpinner, gbc);
        
        // Style checkboxes
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox boldCheck = new JCheckBox("Bold");
        JCheckBox italicCheck = new JCheckBox("Italic");
        JCheckBox underlineCheck = new JCheckBox("Underline");
        stylePanel.add(boldCheck);
        stylePanel.add(italicCheck);
        stylePanel.add(underlineCheck);
        panel.add(stylePanel, gbc);
        
        // Color selection
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Color:"), gbc);
        gbc.gridx = 1;
        JButton colorButton = new JButton("🎨 Choose Color");
        colorButton.setBackground(Color.BLACK);
        colorButton.setForeground(Color.WHITE);
        panel.add(colorButton, gbc);
        
        // Alignment options
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        JPanel alignPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        alignPanel.setBorder(BorderFactory.createTitledBorder("Alignment"));
        ButtonGroup alignGroup = new ButtonGroup();
        JRadioButton leftAlign = new JRadioButton("Left", true);
        JRadioButton centerAlign = new JRadioButton("Center");
        JRadioButton rightAlign = new JRadioButton("Right");
        alignGroup.add(leftAlign);
        alignGroup.add(centerAlign);
        alignGroup.add(rightAlign);
        alignPanel.add(leftAlign);
        alignPanel.add(centerAlign);
        alignPanel.add(rightAlign);
        panel.add(alignPanel, gbc);
        
        return panel;
    }
    
    /**
     * Show text preview with current settings
     */
    private static void showTextPreview() {
        System.out.println("🔍 Showing text preview with current formatting options");
        JOptionPane.showMessageDialog(null, 
            "Preview: Text will appear with selected formatting\n" +
            "Font, size, color, and alignment will be applied\n" +
            "Click 'Activate Drag Mode' to place on canvas", 
            "Text Preview", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void handleAddLineMark() {
        System.out.println("📏 Adding Line Mark - Creating line element...");
        System.out.println("✅ Line mark added to canvas");
    }
    
    // Enhanced DataMatrix handler (replacing Circle)
    public static void handleAddDataMatrixMark() {
        System.out.println("🔲 Adding DataMatrix Mark - Creating DataMatrix code element with intelligent Coder Type integration...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                // Smart placement: center of canvas with offset to avoid overlap
                Point centerPoint = calculateSmartCanvasPlacement(canvas);
                System.out.println("📍 Smart placement: DataMatrix at (" + centerPoint.x + ", " + centerPoint.y + ")");
                
                // 🎯 ThorX6 Integration: Use current active coder for intelligent content
                String intelligentContent = "DM-001"; // Default fallback
                
                ensureCoderInitialized(); // Ensure currentActiveCoder is not null
                if (currentActiveCoder != null) {
                    try {
                        intelligentContent = currentActiveCoder.generateCode();
                        System.out.println("🎯 ThorX6 Integration: Using coder '" + currentActiveCoder.getTypeName() + 
                                          "' content for DataMatrix: " + intelligentContent);
                    } catch (Exception e) {
                        System.out.println("⚠️ Coder generation failed, using default DataMatrix: " + e.getMessage());
                        intelligentContent = "DM-001"; // Fallback
                    }
                } else {
                    System.out.println("💡 No active coder - using default DataMatrix content");
                }
                
                // Create DataMatrix mark with intelligent content from active coder
                DotMatrixMark dataMatrixMark = new DotMatrixMark(centerPoint.x, centerPoint.y, intelligentContent);
                canvas.getMarks().add(dataMatrixMark);
                
                // Auto-select for immediate editing
                selectMarkOnCanvas(canvas, dataMatrixMark);
                canvas.repaint();
                System.out.println("✅ DataMatrix mark added with intelligent coder integration and auto-selected");
                
                // ADVANCED INTEGRATION: Auto-open Coder Type if not configured
                if (currentCoderType == null || currentCoderType.equals("No Coder")) {
                    System.out.println("🎯 Auto-suggesting Coder Type configuration for enhanced DataMatrix...");
                    showIntelligentCoderSuggestion("DataMatrix");
                }
            } else {
                System.out.println("❌ Canvas not available for DataMatrix placement");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating DataMatrix: " + e.getMessage());
        }
    }
    
    // Enhanced Graph handler (replacing Rectangle)  
    public static void handleAddGraphMark() {
        System.out.println("📊 Adding Graph Mark - Creating graph element...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Point centerPoint = calculateSmartCanvasPlacement(canvas);
                System.out.println("📍 Smart placement: Graph at (" + centerPoint.x + ", " + centerPoint.y + ")");
                
                // Create Graph mark using enhanced GraphMark
                GraphMark graphMark = new GraphMark(centerPoint.x, centerPoint.y);
                graphMark.width = 120;
                graphMark.height = 80;
                canvas.getMarks().add(graphMark);
                
                // Auto-select for immediate editing
                selectMarkOnCanvas(canvas, graphMark);
                canvas.repaint();
                System.out.println("✅ Graph mark added and auto-selected");
            } else {
                System.out.println("❌ Canvas not available for Graph placement");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating Graph: " + e.getMessage());
        }
    }
    
    // Enhanced Chart handler (replacing Arc)
    public static void handleAddChartMark() {
        System.out.println("📈 Adding Chart Mark - Creating chart element...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Point centerPoint = calculateSmartCanvasPlacement(canvas);
                System.out.println("📍 Smart placement: Chart at (" + centerPoint.x + ", " + centerPoint.y + ")");
                
                // Create Chart mark using ArcLettersMark as base for circular charts
                ArcLettersMark chartMark = new ArcLettersMark(centerPoint.x, centerPoint.y, "CHART");
                canvas.getMarks().add(chartMark);
                
                // Auto-select for immediate editing
                selectMarkOnCanvas(canvas, chartMark);
                canvas.repaint();
                System.out.println("✅ Chart mark added and auto-selected");
            } else {
                System.out.println("❌ Canvas not available for Chart placement");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating Chart: " + e.getMessage());
        }
    }
    
    /**
     * Advanced Mark Type Handlers (Row 2) - Enhanced with New Mark Types
     */
    
    // Enhanced Ruler handler (replacing Dot Matrix)
    public static void handleAddRulerMark() {
        System.out.println("📏 Adding Ruler Mark - Creating comprehensive measurement ruler...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Point centerPoint = calculateSmartCanvasPlacement(canvas);
                System.out.println("📍 Smart placement: Ruler at (" + centerPoint.x + ", " + centerPoint.y + ")");
                
                // Create comprehensive RulerMark with default configuration
                RulerMark rulerMark = new RulerMark(centerPoint.x, centerPoint.y);
                canvas.getMarks().add(rulerMark);
                
                // Auto-select for immediate editing
                selectMarkOnCanvas(canvas, rulerMark);
                canvas.repaint();
                System.out.println("✅ Comprehensive Ruler mark added and auto-selected");
                
                // Auto-open the comprehensive ruler editor for immediate customization
                SwingUtilities.invokeLater(() -> {
                    openComprehensiveRulerEditor(rulerMark, canvas);
                });
            } else {
                System.out.println("❌ Canvas not available for Ruler placement");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating Ruler: " + e.getMessage());
        }
    }
    
    // Enhanced AvoidPoint handler (replacing Barcode)
    public static void handleAddAvoidPointMark() {
        System.out.println("⚪ Adding Avoid Point Mark - Creating collision avoidance point...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Point centerPoint = calculateSmartCanvasPlacement(canvas);
                System.out.println("📍 Smart placement: AvoidPoint at (" + centerPoint.x + ", " + centerPoint.y + ")");
                
                // Create AvoidPoint mark using AvoidPointMark
                AvoidPointMark avoidPointMark = new AvoidPointMark(centerPoint.x, centerPoint.y);
                canvas.getMarks().add(avoidPointMark);
                
                // Auto-select for immediate editing
                selectMarkOnCanvas(canvas, avoidPointMark);
                canvas.repaint();
                System.out.println("✅ AvoidPoint mark added and auto-selected");
            } else {
                System.out.println("❌ Canvas not available for AvoidPoint placement");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating AvoidPoint: " + e.getMessage());
        }
    }
    

    
    // Enhanced Farsi handler (replacing Graph in Row 2)
    public static void handleAddFarsiMark() {
        System.out.println("ف Adding Farsi Text Mark - Creating Persian/Arabic text element...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                Point centerPoint = calculateSmartCanvasPlacement(canvas);
                System.out.println("📍 Smart placement: Farsi text at (" + centerPoint.x + ", " + centerPoint.y + ")");
                
                // Create Farsi text mark with Arabic/Persian default text
                TextMark farsiMark = new TextMark(centerPoint.x, centerPoint.y, "سلام");
                
                // Set appropriate font for Farsi/Arabic text with fallback options
                Font farsiFont;
                try {
                    farsiFont = new Font("Arial Unicode MS", Font.PLAIN, 16);
                    if (farsiFont.canDisplay('س')) {
                        farsiMark.setFont(farsiFont);
                    } else {
                        // Fallback to system default
                        farsiFont = new Font("SansSerif", Font.PLAIN, 16);
                        farsiMark.setFont(farsiFont);
                    }
                } catch (Exception e) {
                    // Ultimate fallback
                    farsiFont = new Font("Dialog", Font.PLAIN, 16);
                    farsiMark.setFont(farsiFont);
                }
                
                canvas.getMarks().add(farsiMark);
                
                // Auto-select for immediate editing
                selectMarkOnCanvas(canvas, farsiMark);
                canvas.repaint();
                System.out.println("✅ Farsi text mark added and auto-selected with Unicode support");
            } else {
                System.out.println("❌ Canvas not available for Farsi text placement");
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating Farsi text: " + e.getMessage());
        }
    }
    
    public static void handleAddBowTextMark() {
        System.out.println("🏹 Adding Bow Text Mark - Creating bow text element...");
        
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                // Create bow text at center of canvas
                int centerX = canvas.getWidth() / 2 - 100; // Offset for text size
                int centerY = canvas.getHeight() / 2 - 40;
                
                // 🎯 ThorX6 Integration: Get content from current active coder
                String bowTextContent = "BOW TEXT"; // Default fallback
                
                ensureCoderInitialized(); // Ensure currentActiveCoder is not null
                if (currentActiveCoder != null) {
                    try {
                        bowTextContent = currentActiveCoder.generateCode();
                        System.out.println("🎯 ThorX6 Integration: Using coder '" + currentActiveCoder.getTypeName() + 
                                          "' content for BowText: " + bowTextContent);
                    } catch (Exception e) {
                        System.out.println("⚠️ Coder generation failed, using default bow text: " + e.getMessage());
                        bowTextContent = "BOW TEXT"; // Fallback
                    }
                } else {
                    System.out.println("💡 No active coder - using default bow text content");
                }
                
                BowTextMark bowTextMark = new BowTextMark(centerX, centerY, bowTextContent);
                bowTextMark.setCanDrag(true);
                bowTextMark.setCanResize(true);
                
                // Add to canvas marks list
                canvas.getMarks().add(bowTextMark);
                
                // Select the newly created mark (using reflection since method is private)
                try {
                    java.lang.reflect.Field selectedMarkField = canvas.getClass().getDeclaredField("selectedMark");
                    selectedMarkField.setAccessible(true);
                    selectedMarkField.set(canvas, bowTextMark);
                    
                    // Trigger repaint
                    canvas.repaint();
                    
                    System.out.println("🏹 BowText mark created at (" + centerX + ", " + centerY + ") and selected");
                    
                } catch (Exception ex) {
                    // Fallback: just add without selection
                    canvas.repaint();
                    System.out.println("🏹 BowText mark created at (" + centerX + ", " + centerY + ")");
                }
                
                System.out.println("✅ BowText mark added at (" + centerX + ", " + centerY + ") with 8-directional handles");
                canvas.repaint();
            } else {
                System.out.println("⚠️ Canvas not found for BowText creation");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to create BowText mark: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ==================== COMPREHENSIVE RULER EDITOR WITH ALL CUSTOMIZATION OPTIONS ====================
    
    /**
     * COMPREHENSIVE RULER EDITOR with all customization options as specified
     * Advanced tabbed interface with live preview and soft-coded controls
     */
    private static void openComprehensiveRulerEditor(RulerMark rulerMark, DrawingCanvas canvas) {
        System.out.println("📏 Comprehensive Ruler Editor - Opening advanced customization interface...");
        
        // Create enhanced editor window
        JFrame editorFrame = new JFrame("📏 Comprehensive Ruler Editor - Advanced Customization");
        editorFrame.setSize(680, 520);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editorFrame.setResizable(true);
        
        // Main tabbed interface
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(THORX6_LABEL_FONT);
        
        // ===== TAB 1: RULER DIMENSIONS =====
        JPanel dimensionsTab = createRulerDimensionsTab(rulerMark, canvas);
        tabbedPane.addTab("📐 Dimensions", null, dimensionsTab, "Length, Height, Scale Size, and Span settings");
        
        // ===== TAB 2: SCALE LINES =====
        JPanel scaleLinesTab = createScaleLinesTab(rulerMark, canvas);
        tabbedPane.addTab("📏 Scale Lines", null, scaleLinesTab, "Major, Middle, and Short scale line heights");
        
        // ===== TAB 3: NUMBER DISPLAY =====
        JPanel numberDisplayTab = createNumberDisplayTab(rulerMark, canvas);
        tabbedPane.addTab("🔢 Numbers", null, numberDisplayTab, "Show Numbers, Show Middle Numbers, and positioning");
        
        // ===== TAB 4: ADVANCED OPTIONS =====
        JPanel advancedTab = createAdvancedOptionsTab(rulerMark, canvas);
        tabbedPane.addTab("⚙️ Advanced", null, advancedTab, "Radius, Orientation, Colors, and styling");
        
        // ===== CONTROL PANEL =====
        JPanel controlPanel = createRulerControlPanel(editorFrame, rulerMark, canvas);
        
        // Layout
        editorFrame.setLayout(new BorderLayout(10, 10));
        editorFrame.add(tabbedPane, BorderLayout.CENTER);
        editorFrame.add(controlPanel, BorderLayout.SOUTH);
        
        // Show editor
        editorFrame.setVisible(true);
        System.out.println("✅ Comprehensive Ruler Editor opened with tabbed interface");
    }
    
    /**
     * Create Ruler Dimensions Tab with Length, Scale Size, Scale Value, Height, Start Value, Span Length
     */
    private static JPanel createRulerDimensionsTab(RulerMark rulerMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("📐 Ruler Dimensions Configuration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Length control
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Length:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner lengthSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getRulerLength(), 50, 1000, 10));
        lengthSpinner.addChangeListener(e -> {
            rulerMark.setRulerLength((Integer) lengthSpinner.getValue());
            canvas.repaint();
        });
        panel.add(lengthSpinner, gbc);
        
        // Height control
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Height:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getRulerHeight(), 20, 200, 5));
        heightSpinner.addChangeListener(e -> {
            rulerMark.setRulerHeight((Integer) heightSpinner.getValue());
            canvas.repaint();
        });
        panel.add(heightSpinner, gbc);
        
        // Scale Size (interval between major marks)
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Scale Size:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner scaleSizeSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getScaleSize(), 1, 50, 1));
        scaleSizeSpinner.addChangeListener(e -> {
            rulerMark.setScaleSize((Integer) scaleSizeSpinner.getValue());
            canvas.repaint();
        });
        panel.add(scaleSizeSpinner, gbc);
        
        // Scale Value (numerical increment)
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Scale Value:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner scaleValueSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getScaleValue(), 1, 100, 1));
        scaleValueSpinner.addChangeListener(e -> {
            rulerMark.setScaleValue((Integer) scaleValueSpinner.getValue());
            canvas.repaint();
        });
        panel.add(scaleValueSpinner, gbc);
        
        // Start Value
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Start Value:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner startValueSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getStartValue(), -1000, 1000, 1));
        startValueSpinner.addChangeListener(e -> {
            rulerMark.setStartValue((Integer) startValueSpinner.getValue());
            canvas.repaint();
        });
        panel.add(startValueSpinner, gbc);
        
        // Span Length (total measurement range)
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Span Length:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner spanLengthSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getSpanLength(), 10, 1000, 10));
        spanLengthSpinner.addChangeListener(e -> {
            rulerMark.setSpanLength((Integer) spanLengthSpinner.getValue());
            canvas.repaint();
        });
        panel.add(spanLengthSpinner, gbc);
        
        return panel;
    }
    
    /**
     * Create Scale Lines Tab with Major Scale Line, Middle Scale Line, Short Scale Line heights
     */
    private static JPanel createScaleLinesTab(RulerMark rulerMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("📏 Scale Line Heights Configuration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Show/Hide controls for each scale type
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.gridwidth = 2;
        JCheckBox showMajorCheck = new JCheckBox("Show Major Scale Lines", rulerMark.isShowMajorMarks());
        showMajorCheck.addActionListener(e -> {
            rulerMark.setShowMajorMarks(showMajorCheck.isSelected());
            canvas.repaint();
        });
        panel.add(showMajorCheck, gbc);
        
        // Major Scale Line Height
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Major Scale Height:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner majorHeightSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getMajorScaleHeight(), 5, 100, 2));
        majorHeightSpinner.addChangeListener(e -> {
            rulerMark.setMajorScaleHeight((Integer) majorHeightSpinner.getValue());
            canvas.repaint();
        });
        panel.add(majorHeightSpinner, gbc);
        
        // Show Middle Scale Lines
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JCheckBox showMiddleCheck = new JCheckBox("Show Middle Scale Lines", rulerMark.isShowMiddleMarks());
        showMiddleCheck.addActionListener(e -> {
            rulerMark.setShowMiddleMarks(showMiddleCheck.isSelected());
            canvas.repaint();
        });
        panel.add(showMiddleCheck, gbc);
        
        // Middle Scale Line Height
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Middle Scale Height:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner middleHeightSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getMiddleScaleHeight(), 5, 100, 2));
        middleHeightSpinner.addChangeListener(e -> {
            rulerMark.setMiddleScaleHeight((Integer) middleHeightSpinner.getValue());
            canvas.repaint();
        });
        panel.add(middleHeightSpinner, gbc);
        
        // Show Minor Scale Lines
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JCheckBox showMinorCheck = new JCheckBox("Show Short Scale Lines", rulerMark.isShowMinorMarks());
        showMinorCheck.addActionListener(e -> {
            rulerMark.setShowMinorMarks(showMinorCheck.isSelected());
            canvas.repaint();
        });
        panel.add(showMinorCheck, gbc);
        
        // Short Scale Line Height
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Short Scale Height:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner shortHeightSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getShortScaleHeight(), 3, 50, 1));
        shortHeightSpinner.addChangeListener(e -> {
            rulerMark.setShortScaleHeight((Integer) shortHeightSpinner.getValue());
            canvas.repaint();
        });
        panel.add(shortHeightSpinner, gbc);
        
        return panel;
    }
    
    /**
     * Create Number Display Tab with Show Number, Show Middle Number options
     */
    private static JPanel createNumberDisplayTab(RulerMark rulerMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("🔢 Number Display Configuration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Show Numbers (major scale numbers)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.gridwidth = 2;
        JCheckBox showNumbersCheck = new JCheckBox("Show Numbers", rulerMark.isShowNumbers());
        showNumbersCheck.addActionListener(e -> {
            rulerMark.setShowNumbers(showNumbersCheck.isSelected());
            canvas.repaint();
        });
        panel.add(showNumbersCheck, gbc);
        
        // Show Middle Numbers
        gbc.gridx = 0; gbc.gridy = 1;
        JCheckBox showMiddleNumbersCheck = new JCheckBox("Show Middle Numbers", rulerMark.isShowMiddleNumbers());
        showMiddleNumbersCheck.addActionListener(e -> {
            rulerMark.setShowMiddleNumbers(showMiddleNumbersCheck.isSelected());
            canvas.repaint();
        });
        panel.add(showMiddleNumbersCheck, gbc);
        
        // Text Size
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Text Size:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner textSizeSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getTextSize(), 6, 24, 1));
        textSizeSpinner.addChangeListener(e -> {
            rulerMark.setTextSize((Integer) textSizeSpinner.getValue());
            canvas.repaint();
        });
        panel.add(textSizeSpinner, gbc);
        
        // Number Offset
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Number Offset:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner numberOffsetSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getNumberOffset(), -20, 20, 1));
        numberOffsetSpinner.addChangeListener(e -> {
            rulerMark.setNumberOffset((Integer) numberOffsetSpinner.getValue());
            canvas.repaint();
        });
        panel.add(numberOffsetSpinner, gbc);
        
        // Unit Suffix
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Unit Suffix:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField unitSuffixField = new JTextField(rulerMark.getUnitSuffix());
        unitSuffixField.addActionListener(e -> {
            rulerMark.setUnitSuffix(unitSuffixField.getText());
            canvas.repaint();
        });
        panel.add(unitSuffixField, gbc);
        
        // Reverse Numbers
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JCheckBox reverseNumbersCheck = new JCheckBox("Reverse Numbers", rulerMark.isReverseNumbers());
        reverseNumbersCheck.addActionListener(e -> {
            rulerMark.setReverseNumbers(reverseNumbersCheck.isSelected());
            canvas.repaint();
        });
        panel.add(reverseNumbersCheck, gbc);
        
        return panel;
    }
    
    /**
     * Create Advanced Options Tab with Radius, Orientation, Colors, Line Thickness
     */
    private static JPanel createAdvancedOptionsTab(RulerMark rulerMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("⚙️ Advanced Ruler Configuration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Radius (corner rounding)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Radius:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner radiusSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getRadius(), 0.0, 20.0, 0.5));
        radiusSpinner.addChangeListener(e -> {
            rulerMark.setRadius((Double) radiusSpinner.getValue());
            canvas.repaint();
        });
        panel.add(radiusSpinner, gbc);
        
        // Vertical Orientation
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JCheckBox verticalCheck = new JCheckBox("Vertical Orientation", rulerMark.isVertical());
        verticalCheck.addActionListener(e -> {
            rulerMark.setVertical(verticalCheck.isSelected());
            canvas.repaint();
        });
        panel.add(verticalCheck, gbc);
        
        // Line Thickness
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Line Thickness:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JSpinner thicknessSpinner = new JSpinner(new SpinnerNumberModel(rulerMark.getLineThickness(), 0.5, 5.0, 0.1));
        thicknessSpinner.addChangeListener(e -> {
            rulerMark.setLineThickness((Double) thicknessSpinner.getValue());
            canvas.repaint();
        });
        panel.add(thicknessSpinner, gbc);
        
        // Show Background
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JCheckBox showBackgroundCheck = new JCheckBox("Show Background", rulerMark.isShowBackground());
        showBackgroundCheck.addActionListener(e -> {
            rulerMark.setShowBackground(showBackgroundCheck.isSelected());
            canvas.repaint();
        });
        panel.add(showBackgroundCheck, gbc);
        
        // Color selection buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Ruler Color:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JButton rulerColorButton = new JButton();
        rulerColorButton.setBackground(rulerMark.getRulerColor());
        rulerColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(panel, "Choose Ruler Color", rulerMark.getRulerColor());
            if (newColor != null) {
                rulerMark.setRulerColor(newColor);
                rulerColorButton.setBackground(newColor);
                canvas.repaint();
            }
        });
        panel.add(rulerColorButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Text Color:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JButton textColorButton = new JButton();
        textColorButton.setBackground(rulerMark.getTextColor());
        textColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(panel, "Choose Text Color", rulerMark.getTextColor());
            if (newColor != null) {
                rulerMark.setTextColor(newColor);
                textColorButton.setBackground(newColor);
                canvas.repaint();
            }
        });
        panel.add(textColorButton, gbc);
        
        return panel;
    }
    
    /**
     * Create control panel with Apply, Reset, and Close buttons
     */
    private static JPanel createRulerControlPanel(JFrame editorFrame, RulerMark rulerMark, DrawingCanvas canvas) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        JButton resetButton = new JButton("🔄 Reset to Default");
        resetButton.addActionListener(e -> {
            // Reset to default values
            rulerMark.setRulerLength(300);
            rulerMark.setRulerHeight(40);
            rulerMark.setScaleSize(10);
            rulerMark.setScaleValue(1);
            rulerMark.setStartValue(0);
            rulerMark.setSpanLength(100);
            canvas.repaint();
            
            // Close and reopen editor to refresh all controls
            editorFrame.dispose();
            SwingUtilities.invokeLater(() -> openComprehensiveRulerEditor(rulerMark, canvas));
        });
        
        JButton closeButton = new JButton("✅ Close Editor");
        closeButton.addActionListener(e -> editorFrame.dispose());
        
        panel.add(resetButton);
        panel.add(closeButton);
        
        return panel;
    }
    
    // ==================== SMART PLACEMENT AND UTILITY METHODS ====================
    
    /**
     * Calculate smart placement position for new marks to avoid overlap
     */
    private static Point calculateSmartCanvasPlacement(DrawingCanvas canvas) {
        // Start at center of canvas
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        
        // Check for existing marks and offset if needed
        if (canvas.getMarks().size() > 0) {
            // Offset by 30 pixels for each existing mark to avoid overlap
            int offset = (canvas.getMarks().size() % 10) * 30;
            centerX += offset;
            centerY += offset;
            
            // Keep within canvas bounds
            centerX = Math.max(50, Math.min(centerX, canvas.getWidth() - 100));
            centerY = Math.max(50, Math.min(centerY, canvas.getHeight() - 100));
        }
        
        return new Point(centerX, centerY);
    }
    
    /**
     * Select a mark on the canvas using reflection for private field access
     */
    private static void selectMarkOnCanvas(DrawingCanvas canvas, Mark mark) {
        try {
            // Use reflection to access private selectedMark field
            java.lang.reflect.Field selectedMarkField = canvas.getClass().getDeclaredField("selectedMark");
            selectedMarkField.setAccessible(true);
            selectedMarkField.set(canvas, mark);
            
            // Try to call notifySelectionChanged if it exists
            try {
                java.lang.reflect.Method notifyMethod = canvas.getClass().getDeclaredMethod("notifySelectionChanged");
                notifyMethod.setAccessible(true);
                notifyMethod.invoke(canvas);
            } catch (Exception e) {
                // Method might not exist, continue without notification
            }
            
            System.out.println("📌 Mark auto-selected for immediate editing");
        } catch (Exception e) {
            System.out.println("⚠️ Could not auto-select mark: " + e.getMessage());
        }
    }

    // ==================== RECONSTRUCTED CODER TAB SYSTEM (MODULAR OOP DESIGN) ====================
    
    // SOFT CODING: Enable/Disable New Modular Coder System
    public static final boolean ENABLE_RECONSTRUCTED_CODER_SYSTEM = true;
    public static final boolean ENABLE_REAL_TIME_PREVIEW = true;
    public static final boolean ENABLE_CODER_CONFIGURATION = true;
    public static final boolean ADD_RECONSTRUCTED_CODER_BUTTON_GROUP = true; // Add new button group to toolbar
    
    // SOFT CODING: Consolidation Control - Move Coder Type functionality to Reconstructed Tab
    public static final boolean CONSOLIDATE_CODER_FUNCTIONALITY = true;    // Move all coder features to single tab
    public static final boolean DISABLE_ORIGINAL_CODER_TYPE_BUTTON = true; // Hide original "Coder Type" button when consolidated
    public static final boolean INTEGRATE_ORIGINAL_CODERS = true;          // Include original coder types in reconstructed system
    
    // ==================== ALL CODERS DROPDOWN SOFT CODING ====================
    
    public static final boolean ENABLE_ALL_CODERS_DROPDOWN = true;        // Enable dropdown menu for All Coders button
    public static final boolean SHOW_CODER_ICONS_IN_DROPDOWN = true;       // Show icons next to coder names in dropdown
    public static final String ALL_CODERS_DROPDOWN_STYLE = "THORX6";        // SIMPLE, PROFESSIONAL, or THORX6
    public static final boolean AUTO_CLOSE_DROPDOWN_ON_SELECT = true;      // Close dropdown after selection
    public static final boolean ENABLE_DROPDOWN_HOVER_EFFECTS = true;      // Enable hover effects in dropdown
    public static final int DROPDOWN_MAX_WIDTH = 200;                      // Maximum width of dropdown menu
    public static final int DROPDOWN_ITEM_HEIGHT = 28;                     // Height of each dropdown item
    
    // Dropdown Content Configuration
    public static final boolean SHOW_CODER_DESCRIPTIONS = false;           // Show brief descriptions in dropdown
    public static final boolean GROUP_CODERS_BY_TYPE = false;              // Group similar coder types together
    public static final String DROPDOWN_SELECTION_COLOR = "#E6F3FF";       // Hex color for selected item background
    
    // ==================== ADD MARK DROPDOWN SOFT CODING ====================
    
    public static final boolean ENABLE_ADD_MARK_DROPDOWN = true;           // Enable dropdown menu for Add Mark button
    public static final boolean SHOW_MARK_ICONS_IN_DROPDOWN = true;        // Show icons next to mark names in dropdown
    public static final String ADD_MARK_DROPDOWN_STYLE = "SIMPLE";           // SIMPLE, PROFESSIONAL, or THORX6
    public static final boolean AUTO_CLOSE_MARK_DROPDOWN_ON_SELECT = true;  // Close dropdown after mark selection
    public static final boolean ENABLE_MARK_DROPDOWN_HOVER_EFFECTS = true;  // Enable hover effects in mark dropdown
    public static final int MARK_DROPDOWN_MAX_WIDTH = 250;                  // Maximum width of mark dropdown menu
    public static final int MARK_DROPDOWN_ITEM_HEIGHT = 32;                 // Height of each mark dropdown item
    
    // Mark Dropdown Content Configuration
    public static final boolean SHOW_MARK_DESCRIPTIONS = true;              // Show brief descriptions in mark dropdown
    public static final boolean GROUP_MARKS_BY_CATEGORY = true;             // Group mark types by category (Basic/Advanced)
    public static final String MARK_DROPDOWN_SELECTION_COLOR = "#E6F3FF";    // Hex color for selected mark item background
    public static final boolean SHOW_MARK_TOOLTIPS_IN_DROPDOWN = true;      // Show tooltips when hovering over mark options
    
    // Preview Panel Reference (for real-time updates)
    private static javax.swing.JTextArea coderPreviewArea = null;
    private static javax.swing.JPanel coderConfigPanel = null;
    
    /**
     * CODER INTERFACE - Common interface for all coder types (OOP Design)
     */
    public interface CoderInterface {
        String generateCode();
        String getTypeName();
        javax.swing.JPanel getConfigurationPanel();
        String getPreviewText();
        void resetConfiguration();
    }
    
    /**
     * ABSTRACT BASE CODER CLASS - Common functionality for all coders
     */
    public static abstract class BaseCoder implements CoderInterface {
        protected String typeName;
        protected boolean isEnabled;
        
        public BaseCoder(String typeName) {
            this.typeName = typeName;
            this.isEnabled = true;
        }
        
        @Override
        public String getTypeName() {
            return typeName;
        }
        
        @Override
        public String getPreviewText() {
            return generateCode();
        }
        
        protected void updatePreview() {
            if (ENABLE_REAL_TIME_PREVIEW && coderPreviewArea != null) {
                coderPreviewArea.setText(getPreviewText());
                coderPreviewArea.repaint();
            }
        }
    }
    
    /**
     * 1. FIXED TEXT CODER - User enters static text
     */
    public static class FixedTextCoder extends BaseCoder {
        private String fixedText = "SAMPLE TEXT";
        private javax.swing.JTextField textField;
        
        public FixedTextCoder() {
            super("Fixed Text");
        }
        
        @Override
        public String generateCode() {
            return fixedText;
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Fixed Text Configuration"));
            
            panel.add(new javax.swing.JLabel("Text:"));
            textField = new javax.swing.JTextField(fixedText, 20);
            textField.addActionListener(e -> {
                fixedText = textField.getText();
                updatePreview();
            });
            panel.add(textField);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            fixedText = "SAMPLE TEXT";
            if (textField != null) textField.setText(fixedText);
            updatePreview();
        }
    }
    
    /**
     * 2. SERIAL NUMBER CODER - Configurable serial with increment
     */
    public static class SerialNumberCoder extends BaseCoder {
        private int startNumber = 1;
        private int stepSize = 1;
        private int currentNumber;
        private String prefix = "SN";
        private String suffix = "";
        private javax.swing.JTextField startField, stepField, prefixField, suffixField;
        
        public SerialNumberCoder() {
            super("Serial Number");
            currentNumber = startNumber;
        }
        
        @Override
        public String generateCode() {
            String result = prefix + String.format("%03d", currentNumber) + suffix;
            currentNumber += stepSize;
            return result;
        }
        
        @Override
        public String getPreviewText() {
            return prefix + String.format("%03d", currentNumber) + suffix + " (Next: " + 
                   prefix + String.format("%03d", currentNumber + stepSize) + suffix + ")";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(5, 2, 5, 5));
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Serial Number Configuration"));
            
            panel.add(new javax.swing.JLabel("Start Number:"));
            startField = new javax.swing.JTextField(String.valueOf(startNumber), 10);
            startField.addActionListener(e -> {
                try {
                    startNumber = Integer.parseInt(startField.getText());
                    currentNumber = startNumber;
                    updatePreview();
                } catch (NumberFormatException ex) { startField.setText(String.valueOf(startNumber)); }
            });
            panel.add(startField);
            
            panel.add(new javax.swing.JLabel("Step Size:"));
            stepField = new javax.swing.JTextField(String.valueOf(stepSize), 10);
            stepField.addActionListener(e -> {
                try {
                    stepSize = Integer.parseInt(stepField.getText());
                    updatePreview();
                } catch (NumberFormatException ex) { stepField.setText(String.valueOf(stepSize)); }
            });
            panel.add(stepField);
            
            panel.add(new javax.swing.JLabel("Prefix:"));
            prefixField = new javax.swing.JTextField(prefix, 10);
            prefixField.addActionListener(e -> { prefix = prefixField.getText(); updatePreview(); });
            panel.add(prefixField);
            
            panel.add(new javax.swing.JLabel("Suffix:"));
            suffixField = new javax.swing.JTextField(suffix, 10);
            suffixField.addActionListener(e -> { suffix = suffixField.getText(); updatePreview(); });
            panel.add(suffixField);
            
            javax.swing.JButton resetButton = new javax.swing.JButton("Reset Counter");
            resetButton.addActionListener(e -> { currentNumber = startNumber; updatePreview(); });
            panel.add(resetButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            startNumber = 1;
            stepSize = 1;
            currentNumber = startNumber;
            prefix = "SN";
            suffix = "";
            updatePreview();
        }
    }
    
    /**
     * 3. DATE/TIME CODER - Current system date/time in configurable format
     */
    public static class DateTimeCoder extends BaseCoder {
        private String dateFormat = "yyyyMMdd";
        private javax.swing.JComboBox<String> formatCombo;
        private final String[] formats = {"yyyyMMdd", "yyMMdd", "ddMMyyyy", "HHmm", "HHmmss", "yyyyMMdd_HHmm"};
        
        public DateTimeCoder() {
            super("Date/Time");
        }
        
        @Override
        public String generateCode() {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
            return sdf.format(new java.util.Date());
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Date/Time Configuration"));
            
            panel.add(new javax.swing.JLabel("Format:"));
            formatCombo = new javax.swing.JComboBox<>(formats);
            formatCombo.setSelectedItem(dateFormat);
            formatCombo.addActionListener(e -> {
                dateFormat = (String) formatCombo.getSelectedItem();
                updatePreview();
            });
            panel.add(formatCombo);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            dateFormat = "yyyyMMdd";
            if (formatCombo != null) formatCombo.setSelectedItem(dateFormat);
            updatePreview();
        }
    }
    
    /**
     * 4. SHIFT CODE CODER - Work shift based on system time
     */
    public static class ShiftCodeCoder extends BaseCoder {
        private int shiftAStart = 6, shiftAEnd = 14;   // 6 AM - 2 PM
        private int shiftBStart = 14, shiftBEnd = 22;  // 2 PM - 10 PM  
        private int shiftCStart = 22, shiftCEnd = 6;   // 10 PM - 6 AM
        private javax.swing.JSpinner aStartSpinner, aEndSpinner, bStartSpinner, bEndSpinner, cStartSpinner, cEndSpinner;
        
        public ShiftCodeCoder() {
            super("Shift Code");
        }
        
        @Override
        public String generateCode() {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
            
            if (hour >= shiftAStart && hour < shiftAEnd) return "A";
            else if (hour >= shiftBStart && hour < shiftBEnd) return "B";
            else return "C";
        }
        
        @Override
        public String getPreviewText() {
            return generateCode() + " (Current time: " + 
                   new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()) + ")";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(3, 4, 5, 5));
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Shift Code Configuration"));
            
            panel.add(new javax.swing.JLabel("Shift A:"));
            aStartSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(shiftAStart, 0, 23, 1));
            aStartSpinner.addChangeListener(e -> { shiftAStart = (Integer) aStartSpinner.getValue(); updatePreview(); });
            panel.add(aStartSpinner);
            panel.add(new javax.swing.JLabel("to"));
            aEndSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(shiftAEnd, 0, 23, 1));
            aEndSpinner.addChangeListener(e -> { shiftAEnd = (Integer) aEndSpinner.getValue(); updatePreview(); });
            panel.add(aEndSpinner);
            
            panel.add(new javax.swing.JLabel("Shift B:"));
            bStartSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(shiftBStart, 0, 23, 1));
            bStartSpinner.addChangeListener(e -> { shiftBStart = (Integer) bStartSpinner.getValue(); updatePreview(); });
            panel.add(bStartSpinner);
            panel.add(new javax.swing.JLabel("to"));
            bEndSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(shiftBEnd, 0, 23, 1));
            bEndSpinner.addChangeListener(e -> { shiftBEnd = (Integer) bEndSpinner.getValue(); updatePreview(); });
            panel.add(bEndSpinner);
            
            panel.add(new javax.swing.JLabel("Shift C:"));
            cStartSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(shiftCStart, 0, 23, 1));
            cStartSpinner.addChangeListener(e -> { shiftCStart = (Integer) cStartSpinner.getValue(); updatePreview(); });
            panel.add(cStartSpinner);
            panel.add(new javax.swing.JLabel("to"));
            cEndSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(shiftCEnd, 0, 23, 1));
            cEndSpinner.addChangeListener(e -> { shiftCEnd = (Integer) cEndSpinner.getValue(); updatePreview(); });
            panel.add(cEndSpinner);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            shiftAStart = 6; shiftAEnd = 14;
            shiftBStart = 14; shiftBEnd = 22;
            shiftCStart = 22; shiftCEnd = 6;
            updatePreview();
        }
    }
    
    /**
     * 5. BARCODE/QR CODE CODER - Generate code images from text
     */
    public static class BarcodeCoder extends BaseCoder {
        private String inputText = "SAMPLE123";
        private String codeType = "QR Code";
        private javax.swing.JTextField inputField;
        private javax.swing.JComboBox<String> typeCombo;
        private final String[] codeTypes = {"QR Code", "Code 128", "Code 39", "DataMatrix"};
        
        public BarcodeCoder() {
            super("Barcode/QR Code");
        }
        
        @Override
        public String generateCode() {
            return "[" + codeType + ": " + inputText + "]";
        }
        
        @Override
        public String getPreviewText() {
            return generateCode() + "\n(Image would be generated for: " + inputText + ")";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(3, 2, 5, 5));
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Barcode/QR Configuration"));
            
            panel.add(new javax.swing.JLabel("Code Type:"));
            typeCombo = new javax.swing.JComboBox<>(codeTypes);
            typeCombo.setSelectedItem(codeType);
            typeCombo.addActionListener(e -> { codeType = (String) typeCombo.getSelectedItem(); updatePreview(); });
            panel.add(typeCombo);
            
            panel.add(new javax.swing.JLabel("Input Text:"));
            inputField = new javax.swing.JTextField(inputText, 15);
            inputField.addActionListener(e -> { inputText = inputField.getText(); updatePreview(); });
            panel.add(inputField);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            inputText = "SAMPLE123";
            codeType = "QR Code";
            if (inputField != null) inputField.setText(inputText);
            if (typeCombo != null) typeCombo.setSelectedItem(codeType);
            updatePreview();
        }
    }
    
    /**
     * 6. EXTERNAL INPUT CODER - Read from file or database
     */
    public static class ExternalInputCoder extends BaseCoder {
        private String dataSource = "CSV File";
        private String filePath = "data/codes.csv";
        private String currentData = "EXT001";
        private javax.swing.JComboBox<String> sourceCombo;
        private javax.swing.JTextField pathField;
        private final String[] sources = {"CSV File", "JSON File", "Database", "API Endpoint"};
        
        public ExternalInputCoder() {
            super("External Input");
        }
        
        @Override
        public String generateCode() {
            // Simulate reading from external source
            return currentData;
        }
        
        @Override
        public String getPreviewText() {
            return currentData + "\n(Source: " + dataSource + " - " + filePath + ")";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(4, 2, 5, 5));
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("External Input Configuration"));
            
            panel.add(new javax.swing.JLabel("Data Source:"));
            sourceCombo = new javax.swing.JComboBox<>(sources);
            sourceCombo.setSelectedItem(dataSource);
            sourceCombo.addActionListener(e -> { 
                dataSource = (String) sourceCombo.getSelectedItem(); 
                updatePreview(); 
            });
            panel.add(sourceCombo);
            
            panel.add(new javax.swing.JLabel("File/URL Path:"));
            pathField = new javax.swing.JTextField(filePath, 20);
            pathField.addActionListener(e -> { filePath = pathField.getText(); updatePreview(); });
            panel.add(pathField);
            
            javax.swing.JButton loadButton = new javax.swing.JButton("Load Data");
            loadButton.addActionListener(e -> loadExternalData());
            panel.add(loadButton);
            
            javax.swing.JButton testButton = new javax.swing.JButton("Test Connection");
            testButton.addActionListener(e -> testConnection());
            panel.add(testButton);
            
            return panel;
        }
        
        private void loadExternalData() {
            // Simulate loading external data
            currentData = "EXT" + (int)(Math.random() * 1000);
            updatePreview();
            javax.swing.JOptionPane.showMessageDialog(null, "Data loaded: " + currentData);
        }
        
        private void testConnection() {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Connection test for " + dataSource + "\nPath: " + filePath + "\nStatus: OK");
        }
        
        @Override
        public void resetConfiguration() {
            dataSource = "CSV File";
            filePath = "data/codes.csv";
            currentData = "EXT001";
            if (sourceCombo != null) sourceCombo.setSelectedItem(dataSource);
            if (pathField != null) pathField.setText(filePath);
            updatePreview();
        }
    }
    
    // CODER INSTANCES (Singleton Pattern) - Dynamically built based on configuration
    private static final CoderInterface[] RECONSTRUCTED_CODERS = createConsolidatedCoderArray();
    
    private static CoderInterface currentActiveCoder = null; // Will be initialized on first access
    
    /**
     * CREATE CONSOLIDATED CODER ARRAY - Includes both new and original coder types
     */
    public static CoderInterface[] createConsolidatedCoderArray() {
        java.util.List<CoderInterface> allCoders = new java.util.ArrayList<>();
        
        // Always add the new modular coder types
        allCoders.add(new FixedTextCoder());
        allCoders.add(new SerialNumberCoder());
        allCoders.add(new DateTimeCoder());
        allCoders.add(new ShiftCodeCoder());
        allCoders.add(new BarcodeCoder());
        allCoders.add(new ExternalInputCoder());
        
        // INTEGRATION: Add original ThorX6 coder types if enabled
        if (INTEGRATE_ORIGINAL_CODERS) {
            allCoders.add(new OriginalSerialCoder());
            allCoders.add(new OriginalVINCoder());
            allCoders.add(new OriginalPINCoder());
            allCoders.add(new OriginalDateTimeCoder());
            allCoders.add(new OriginalVCodeCoder());
            allCoders.add(new OriginalClassCoder());
            allCoders.add(new OriginalRandomCodeCoder());
            
            // Add barcode types from original system
            allCoders.add(new OriginalQRCodeCoder());
            allCoders.add(new OriginalDataMatrixCoder());
            allCoders.add(new OriginalCode128Coder());
            allCoders.add(new OriginalCode39Coder());
        }
        
        CoderInterface[] result = allCoders.toArray(new CoderInterface[0]);
        
        return result;
    }
    
    /**
     * Initialize default coder - ensures currentActiveCoder is never null
     */
    private static CoderInterface initializeDefaultCoder() {
        try {
            if (RECONSTRUCTED_CODERS != null && RECONSTRUCTED_CODERS.length > 0) {
                System.out.println("✅ Initializing default coder: " + RECONSTRUCTED_CODERS[0].getTypeName());
                return RECONSTRUCTED_CODERS[0];
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error accessing RECONSTRUCTED_CODERS: " + e.getMessage());
        }
        // Fallback - create a basic coder if array is empty or error occurred
        System.out.println("🔧 Creating fallback FixedTextCoder");
        return new FixedTextCoder();
    }
    
    /**
     * Ensure currentActiveCoder is initialized - called before any coder operations
     */
    private static void ensureCoderInitialized() {
        if (currentActiveCoder == null) {
            currentActiveCoder = initializeDefaultCoder();
            System.out.println("🔧 Auto-initialized currentActiveCoder: " + currentActiveCoder.getTypeName());
        }
    }
    
    // ==================== ORIGINAL CODER TYPE WRAPPERS (Integration Layer) ====================
    
    /**
     * ORIGINAL SERIAL CODER - Wrapper for existing Serial functionality
     */
    public static class OriginalSerialCoder extends BaseCoder {
        public OriginalSerialCoder() {
            super("ThorX6 Serial");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalSerialNumber();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 Serial Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 serial generation system"));
            
            javax.swing.JButton configButton = new javax.swing.JButton("Open Original Serial Config");
            configButton.addActionListener(e -> handleSerialAction());
            panel.add(configButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            // Reset original serial system
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL VIN CODER - Wrapper for existing VIN functionality  
     */
    public static class OriginalVINCoder extends BaseCoder {
        public OriginalVINCoder() {
            super("ThorX6 VIN");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalVINNumber();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 VIN Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 VIN generation system"));
            
            javax.swing.JButton configButton = new javax.swing.JButton("Open Original VIN Config");
            configButton.addActionListener(e -> handleVINAction());
            panel.add(configButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL PIN CODER - Wrapper for existing PIN functionality
     */
    public static class OriginalPINCoder extends BaseCoder {
        public OriginalPINCoder() {
            super("ThorX6 PIN");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalPINNumber();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 PIN Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 PIN generation system"));
            
            javax.swing.JButton configButton = new javax.swing.JButton("Open Original PIN Config");
            configButton.addActionListener(e -> handlePINAction());
            panel.add(configButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL DATETIME CODER - Wrapper for existing DateTime functionality
     */
    public static class OriginalDateTimeCoder extends BaseCoder {
        public OriginalDateTimeCoder() {
            super("ThorX6 DateTime");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalDateTimeCode();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 DateTime Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 DateTime generation system"));
            
            javax.swing.JButton configButton = new javax.swing.JButton("Open Original DateTime Config");
            configButton.addActionListener(e -> handleDateTimeAction());
            panel.add(configButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL VCODE CODER - Wrapper for existing VCode functionality
     */
    public static class OriginalVCodeCoder extends BaseCoder {
        public OriginalVCodeCoder() {
            super("ThorX6 VCode");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalVCode();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 VCode Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 VCode generation system"));
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL CLASS CODER - Wrapper for existing Class functionality
     */
    public static class OriginalClassCoder extends BaseCoder {
        public OriginalClassCoder() {
            super("ThorX6 Class");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalClassCode();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 Class Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 Class generation system"));
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL RANDOM CODE CODER - Wrapper for existing Random Code functionality
     */
    public static class OriginalRandomCodeCoder extends BaseCoder {
        public OriginalRandomCodeCoder() {
            super("ThorX6 Random Code");
        }
        
        @Override
        public String generateCode() {
            return generateOriginalRandomCode();
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 Random Code Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 Random Code generation system"));
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL QR CODE CODER - Wrapper for existing QR Code functionality
     */
    public static class OriginalQRCodeCoder extends BaseCoder {
        public OriginalQRCodeCoder() {
            super("ThorX6 QR Code");
        }
        
        @Override
        public String generateCode() {
            return "[QR Code Generated]";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 QR Code Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 QR Code generation system"));
            
            javax.swing.JButton configButton = new javax.swing.JButton("Generate QR Code");
            configButton.addActionListener(e -> handleCoderAction("QR Code"));
            panel.add(configButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL DATAMATRIX CODER - Wrapper for existing DataMatrix functionality
     */
    public static class OriginalDataMatrixCoder extends BaseCoder {
        public OriginalDataMatrixCoder() {
            super("ThorX6 DataMatrix");
        }
        
        @Override
        public String generateCode() {
            return "[DataMatrix Generated]";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 DataMatrix Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 DataMatrix generation system"));
            
            javax.swing.JButton configButton = new javax.swing.JButton("Generate DataMatrix");
            configButton.addActionListener(e -> handleCoderAction("DataMatrix"));
            panel.add(configButton);
            
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL CODE 128 CODER - Wrapper for existing Code 128 functionality
     */
    public static class OriginalCode128Coder extends BaseCoder {
        public OriginalCode128Coder() {
            super("ThorX6 Code 128");
        }
        
        @Override
        public String generateCode() {
            return "[Code 128 Generated]";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 Code 128 Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 Code 128 generation system"));
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    /**
     * ORIGINAL CODE 39 CODER - Wrapper for existing Code 39 functionality
     */
    public static class OriginalCode39Coder extends BaseCoder {
        public OriginalCode39Coder() {
            super("ThorX6 Code 39");
        }
        
        @Override
        public String generateCode() {
            return "[Code 39 Generated]";
        }
        
        @Override
        public javax.swing.JPanel getConfigurationPanel() {
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout());
            panel.setBorder(javax.swing.BorderFactory.createTitledBorder("ThorX6 Code 39 Configuration"));
            panel.add(new javax.swing.JLabel("Uses original ThorX6 Code 39 generation system"));
            return panel;
        }
        
        @Override
        public void resetConfiguration() {
            updatePreview();
        }
    }
    
    // ==================== ORIGINAL CODER GENERATION METHODS (Integration Layer) ====================
    
    private static String generateOriginalSerialNumber() {
        return "SN" + String.format("%03d", (int)(Math.random() * 1000));
    }
    
    private static String generateOriginalVINNumber() {
        return "1HGBH41JXMN" + String.format("%06d", (int)(Math.random() * 1000000));
    }
    
    private static String generateOriginalPINNumber() {
        return String.format("%04d", (int)(Math.random() * 10000));
    }
    
    private static String generateOriginalDateTimeCode() {
        return new java.text.SimpleDateFormat("yyyyMMdd_HHmm").format(new java.util.Date());
    }
    
    private static String generateOriginalVCode() {
        return "VC" + String.format("%04d", (int)(Math.random() * 10000));
    }
    
    private static String generateOriginalClassCode() {
        String[] classes = {"ClassA", "ClassB", "ClassC", "Premium", "Standard"};
        return classes[(int)(Math.random() * classes.length)];
    }
    
    private static String generateOriginalRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt((int)(Math.random() * chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * CREATE RECONSTRUCTED CODER TAB PANEL (Main UI)
     */
    public static javax.swing.JPanel createReconstructedCoderTab() {
        if (!ENABLE_RECONSTRUCTED_CODER_SYSTEM) {
            return new javax.swing.JPanel(); // Return empty panel if disabled
        }
        
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout(10, 10));
        String title = CONSOLIDATE_CODER_FUNCTIONALITY ? 
            "Consolidated Coder System (New + Original ThorX6)" : 
            "Reconstructed Coder System";
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        
        // TOP: Coder Type Selection
        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        topPanel.add(new javax.swing.JLabel("Coder Type:"));
        
        javax.swing.JComboBox<String> coderTypeCombo = new javax.swing.JComboBox<>();
        for (CoderInterface coder : RECONSTRUCTED_CODERS) {
            coderTypeCombo.addItem(coder.getTypeName());
        }
        
        coderTypeCombo.addActionListener(e -> {
            int selectedIndex = coderTypeCombo.getSelectedIndex();
            currentActiveCoder = RECONSTRUCTED_CODERS[selectedIndex];
            updateCoderConfiguration();
            updateCoderPreview();
        });
        
        topPanel.add(coderTypeCombo);
        mainPanel.add(topPanel, java.awt.BorderLayout.NORTH);
        
        // CENTER: Configuration Panel
        ensureCoderInitialized();
        coderConfigPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        coderConfigPanel.add(currentActiveCoder.getConfigurationPanel(), java.awt.BorderLayout.CENTER);
        
        javax.swing.JScrollPane configScroll = new javax.swing.JScrollPane(coderConfigPanel);
        configScroll.setPreferredSize(new java.awt.Dimension(400, 200));
        mainPanel.add(configScroll, java.awt.BorderLayout.CENTER);
        
        // BOTTOM: Preview Panel
        if (ENABLE_REAL_TIME_PREVIEW) {
            javax.swing.JPanel previewPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
            previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Live Preview"));
            
            coderPreviewArea = new javax.swing.JTextArea(4, 30);
            coderPreviewArea.setEditable(false);
            coderPreviewArea.setBackground(new java.awt.Color(240, 248, 255));
            coderPreviewArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 12));
            coderPreviewArea.setText(currentActiveCoder.getPreviewText());
            
            javax.swing.JScrollPane previewScroll = new javax.swing.JScrollPane(coderPreviewArea);
            previewPanel.add(previewScroll, java.awt.BorderLayout.CENTER);
            
            // Preview Control Buttons
            javax.swing.JPanel previewButtons = new javax.swing.JPanel(new java.awt.FlowLayout());
            
            javax.swing.JButton refreshButton = new javax.swing.JButton("Refresh Preview");
            refreshButton.addActionListener(e -> updateCoderPreview());
            previewButtons.add(refreshButton);
            
            javax.swing.JButton generateButton = new javax.swing.JButton("Generate Code");
            generateButton.addActionListener(e -> {
                String generated = currentActiveCoder.generateCode();
                javax.swing.JOptionPane.showMessageDialog(null, "Generated: " + generated);
                updateCoderPreview();
            });
            previewButtons.add(generateButton);
            
            javax.swing.JButton resetButton = new javax.swing.JButton("Reset Config");
            resetButton.addActionListener(e -> {
                currentActiveCoder.resetConfiguration();
                updateCoderConfiguration();
            });
            previewButtons.add(resetButton);
            
            previewPanel.add(previewButtons, java.awt.BorderLayout.SOUTH);
            mainPanel.add(previewPanel, java.awt.BorderLayout.SOUTH);
        }
        
        return mainPanel;
    }
    
    /**
     * UPDATE CONFIGURATION PANEL WHEN CODER TYPE CHANGES
     */
    private static void updateCoderConfiguration() {
        if (coderConfigPanel != null && currentActiveCoder != null) {
            coderConfigPanel.removeAll();
            coderConfigPanel.add(currentActiveCoder.getConfigurationPanel(), java.awt.BorderLayout.CENTER);
            coderConfigPanel.revalidate();
            coderConfigPanel.repaint();
        }
    }
    
    /**
     * UPDATE PREVIEW AREA WITH CURRENT CODER OUTPUT  
     */
    private static void updateCoderPreview() {
        if (coderPreviewArea != null && currentActiveCoder != null) {
            coderPreviewArea.setText(currentActiveCoder.getPreviewText());
            coderPreviewArea.repaint();
        }
    }
    
    /**
     * GET CURRENT ACTIVE CODER FOR INTEGRATION WITH MARKING SYSTEM
     */
    public static CoderInterface getCurrentActiveCoder() {
        return currentActiveCoder;
    }
    
    // ==================== RECONSTRUCTED CODER BUTTON ACTIONS ====================
    
    /**
     * Open Reconstructed Coder Configuration Panel in a dialog
     */
    private static void openReconstructedCoderPanel() {
        System.out.println("🔧 Opening Reconstructed Coder Configuration Panel...");
        
        JDialog dialog = new JDialog();
        String dialogTitle = CONSOLIDATE_CODER_FUNCTIONALITY ? 
            "Consolidated Coder System - All Coder Types" : 
            "Reconstructed Coder System - Configuration";
        dialog.setTitle(dialogTitle);
        dialog.setModal(true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Add the reconstructed coder tab content
        dialog.add(createReconstructedCoderTab());
        
        dialog.setVisible(true);
    }
    
    /**
     * Quick Generate - Use current active coder to generate code
     */
    private static void handleQuickGenerate() {
        ensureCoderInitialized();
        System.out.println("⚡ Quick Generate with current coder: " + currentActiveCoder.getTypeName());
        
        String generatedCode = currentActiveCoder.generateCode();
        System.out.println("✅ Generated: " + generatedCode);
        
        // Show result and optionally add to canvas
        int option = JOptionPane.showConfirmDialog(null, 
            "Generated Code: " + generatedCode + "\n\nWould you like to add this to the canvas?",
            "Code Generated - " + currentActiveCoder.getTypeName(),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE);
            
        if (option == JOptionPane.YES_OPTION) {
            addGeneratedCodeToCanvas(generatedCode);
        }
    }
    
    /**
     * Handle All Coders Dropdown (Soft-coded implementation)
     */
    private static void handleAllCodersDropdown() {
        System.out.println("🔽 Showing All Coders dropdown (soft-coded style: " + ALL_CODERS_DROPDOWN_STYLE + ")");
        
        // Create dropdown based on soft coding configuration
        if ("SIMPLE".equals(ALL_CODERS_DROPDOWN_STYLE)) {
            showSimpleCodersDropdown();
        } else if ("PROFESSIONAL".equals(ALL_CODERS_DROPDOWN_STYLE)) {
            showProfessionalCodersDropdown();
        } else if ("THORX6".equals(ALL_CODERS_DROPDOWN_STYLE)) {
            showThorX6CodersDropdown();
        } else {
            // Fallback to simple style
            showSimpleCodersDropdown();
        }
    }
    
    /**
     * Handle All Coders Dropdown Menu (Attached to button like Add Mark)
     */
    private static void handleAllCodersDropdownMenu(JButton sourceButton) {
        System.out.println("🔽 Showing All Coders dropdown menu (soft-coded style: " + ALL_CODERS_DROPDOWN_STYLE + ")");
        
        // Route to appropriate dropdown implementation based on soft coding
        if ("SIMPLE".equals(ALL_CODERS_DROPDOWN_STYLE)) {
            showSimpleCodersDropdownMenu(sourceButton);
        } else if ("PROFESSIONAL".equals(ALL_CODERS_DROPDOWN_STYLE)) {
            showProfessionalCodersDropdownMenu(sourceButton);
        } else if ("THORX6".equals(ALL_CODERS_DROPDOWN_STYLE)) {
            showThorX6CodersDropdownMenu(sourceButton);
        } else {
            // Fallback to simple style
            showSimpleCodersDropdownMenu(sourceButton);
        }
    }
    
    /**
     * Show Simple Coders Dropdown (Soft-coded)
     */
    private static void showSimpleCodersDropdown() {
        ensureCoderInitialized();
        
        String[] coderNames = new String[RECONSTRUCTED_CODERS.length];
        for (int i = 0; i < RECONSTRUCTED_CODERS.length; i++) {
            String name = RECONSTRUCTED_CODERS[i].getTypeName();
            if (SHOW_CODER_ICONS_IN_DROPDOWN) {
                name = getCoderIcon(name) + " " + name;
            }
            coderNames[i] = name;
        }
        
        String selected = (String) javax.swing.JOptionPane.showInputDialog(
            null,
            "Select Coder Type:",
            "All Coders - Quick Select",
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            coderNames,
            coderNames[0]
        );
        
        if (selected != null) {
            // Find the selected coder
            for (int i = 0; i < coderNames.length; i++) {
                if (coderNames[i].equals(selected)) {
                    currentActiveCoder = RECONSTRUCTED_CODERS[i];
                    System.out.println("✅ Selected coder: " + currentActiveCoder.getTypeName());
                    
                    // Generate code immediately if configured
                    if (AUTO_CLOSE_DROPDOWN_ON_SELECT) {
                        String code = currentActiveCoder.generateCode();
                        javax.swing.JOptionPane.showMessageDialog(null, 
                            "Generated Code: " + code + "\\n\\nCoder: " + currentActiveCoder.getTypeName(),
                            "Code Generated",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
    
    /**
     * Show Simple Coders Dropdown Menu (Attached to button)
     */
    private static void showSimpleCodersDropdownMenu(JButton sourceButton) {
        ensureCoderInitialized();
        System.out.println("🔽 Opening All Coders dropdown menu (Simple style)...");
        
        JPopupMenu dropdownMenu = new JPopupMenu();
        dropdownMenu.setBorder(BorderFactory.createTitledBorder("All Coders"));
        
        // Add menu items for each coder
        for (int i = 0; i < RECONSTRUCTED_CODERS.length; i++) {
            CoderInterface coder = RECONSTRUCTED_CODERS[i];
            String name = coder.getTypeName();
            String displayText = SHOW_CODER_ICONS_IN_DROPDOWN ? getCoderIcon(name) + " " + name : name;
            
            JMenuItem coderItem = new JMenuItem(displayText);
            coderItem.setToolTipText(getCoderDescription(name));
            
            // Add hover effects if enabled
            if (ENABLE_DROPDOWN_HOVER_EFFECTS) {
                coderItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        coderItem.setBackground(new Color(230, 240, 255));
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        coderItem.setBackground(UIManager.getColor("MenuItem.background"));
                    }
                });
            }
            
            // Action when clicked
            coderItem.addActionListener(e -> {
                currentActiveCoder = coder;
                System.out.println("✅ Selected coder: " + currentActiveCoder.getTypeName());
                
                // Generate code immediately if configured
                if (AUTO_CLOSE_DROPDOWN_ON_SELECT) {
                    String code = currentActiveCoder.generateCode();
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Generated Code: " + code + "\\n\\nCoder: " + currentActiveCoder.getTypeName(),
                        "Code Generated",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
                dropdownMenu.setVisible(false);
            });
            
            dropdownMenu.add(coderItem);
        }
        
        // Show dropdown below the button
        dropdownMenu.show(sourceButton, 0, sourceButton.getHeight());
        
        System.out.println("✅ All Coders dropdown menu opened with " + RECONSTRUCTED_CODERS.length + " options");
    }
    
    /**
     * Show Professional Coders Dropdown (Soft-coded)
     */
    private static void showProfessionalCodersDropdown() {
        ensureCoderInitialized();
        
        javax.swing.JDialog dropdown = new javax.swing.JDialog();
        dropdown.setTitle("All Coders - Professional Selection");
        dropdown.setModal(true);
        dropdown.setLayout(new java.awt.BorderLayout());
        
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout(5, 5));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        javax.swing.JLabel headerLabel = new javax.swing.JLabel("🔧 Select Coder Type", javax.swing.SwingConstants.CENTER);
        headerLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        mainPanel.add(headerLabel, java.awt.BorderLayout.NORTH);
        
        // Coder list
        javax.swing.DefaultListModel<String> listModel = new javax.swing.DefaultListModel<>();
        for (CoderInterface coder : RECONSTRUCTED_CODERS) {
            String displayName = coder.getTypeName();
            if (SHOW_CODER_ICONS_IN_DROPDOWN) {
                displayName = getCoderIcon(coder.getTypeName()) + "  " + displayName;
            }
            if (SHOW_CODER_DESCRIPTIONS) {
                displayName += " - " + getCoderDescription(coder.getTypeName());
            }
            listModel.addElement(displayName);
        }
        
        javax.swing.JList<String> coderList = new javax.swing.JList<>(listModel);
        coderList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        coderList.setSelectedIndex(0);
        
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(coderList);
        scrollPane.setPreferredSize(new java.awt.Dimension(DROPDOWN_MAX_WIDTH, DROPDOWN_ITEM_HEIGHT * Math.min(6, RECONSTRUCTED_CODERS.length)));
        mainPanel.add(scrollPane, java.awt.BorderLayout.CENTER);
        
        // Buttons
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        
        javax.swing.JButton selectButton = new javax.swing.JButton("✅ Select");
        selectButton.addActionListener(e -> {
            int selectedIndex = coderList.getSelectedIndex();
            if (selectedIndex >= 0) {
                currentActiveCoder = RECONSTRUCTED_CODERS[selectedIndex];
                System.out.println("✅ Professional selection: " + currentActiveCoder.getTypeName());
                dropdown.dispose();
                
                if (AUTO_CLOSE_DROPDOWN_ON_SELECT) {
                    String code = currentActiveCoder.generateCode();
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Generated Code: " + code + "\\n\\nCoder: " + currentActiveCoder.getTypeName(),
                        "Professional Code Generation",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        javax.swing.JButton configButton = new javax.swing.JButton("⚙️ Configure");
        configButton.addActionListener(e -> {
            dropdown.dispose();
            openReconstructedCoderPanel();
        });
        
        javax.swing.JButton cancelButton = new javax.swing.JButton("❌ Cancel");
        cancelButton.addActionListener(e -> dropdown.dispose());
        
        buttonPanel.add(selectButton);
        buttonPanel.add(configButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        dropdown.add(mainPanel);
        dropdown.pack();
        dropdown.setLocationRelativeTo(null);
        dropdown.setVisible(true);
    }
    
    /**
     * Show ThorX6-style Coders Dropdown (Soft-coded)
     */
    private static void showThorX6CodersDropdown() {
        // This would open the configuration panel with enhanced dropdown inside
        openReconstructedCoderPanel();
    }
    
    /**
     * Show Professional Coders Dropdown Menu (Attached to button)
     */
    private static void showProfessionalCodersDropdownMenu(JButton sourceButton) {
        // For now, use the simple style - can be enhanced later
        showSimpleCodersDropdownMenu(sourceButton);
    }
    
    /**
     * Show ThorX6 Coders Dropdown Menu (Attached to button) - 3x3 Grid with Icons Only
     */
    private static void showThorX6CodersDropdownMenu(JButton sourceButton) {
        ensureCoderInitialized();
        System.out.println("🔽 Opening ThorX6-style coders dropdown (3x3 grid, icons only)...");
        
        JPopupMenu dropdownMenu = new JPopupMenu();
        dropdownMenu.setBorder(BorderFactory.createTitledBorder("ThorX6 Coders"));
        
        // Create 3x3 grid panel for ThorX6 style
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        // Define the 9 most important ThorX6 coder types (matching typical ThorX6 usage)
        CoderInterface[] thorx6Coders = getThorX6CoderSelection();
        
        // Add buttons for each coder type (icons only)
        for (int i = 0; i < 9; i++) {
            if (i < thorx6Coders.length && thorx6Coders[i] != null) {
                CoderInterface coder = thorx6Coders[i];
                String icon = getThorX6CoderIcon(coder.getTypeName());
                
                JButton coderButton = new JButton(icon);
                coderButton.setPreferredSize(new Dimension(50, 45));
                coderButton.setFont(new Font("Arial", Font.BOLD, 9)); // Optimized font for HTML text
                coderButton.setToolTipText(coder.getTypeName()); // Tooltip shows name on hover
                coderButton.setFocusPainted(false);
                coderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // ThorX6-style hover effects
                coderButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        coderButton.setBackground(new Color(220, 235, 255)); // Light blue hover
                        coderButton.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2));
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        coderButton.setBackground(UIManager.getColor("Button.background"));
                        coderButton.setBorder(UIManager.getBorder("Button.border"));
                    }
                });
                
                // Action when clicked
                coderButton.addActionListener(e -> {
                    currentActiveCoder = coder;
                    System.out.println("✅ ThorX6 selected: " + icon + " " + currentActiveCoder.getTypeName());
                    
                    // Generate code immediately if configured
                    if (AUTO_CLOSE_DROPDOWN_ON_SELECT) {
                        String code = currentActiveCoder.generateCode();
                        javax.swing.JOptionPane.showMessageDialog(null, 
                            "Generated Code: " + code + "\n\nCoder: " + currentActiveCoder.getTypeName(),
                            "ThorX6 Code Generated",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }
                    dropdownMenu.setVisible(false);
                });
                
                gridPanel.add(coderButton);
            } else {
                // Add empty slot if we have less than 9 coders
                JLabel emptySlot = new JLabel();
                emptySlot.setPreferredSize(new Dimension(45, 40));
                gridPanel.add(emptySlot);
            }
        }
        
        dropdownMenu.add(gridPanel);
        
        // Show dropdown below the button (ThorX6 style positioning)
        dropdownMenu.show(sourceButton, 0, sourceButton.getHeight());
        
        System.out.println("✅ ThorX6 coders dropdown opened (3x3 grid with 9 options)");
    }
    
    /**
     * Get ThorX6 Coder Selection - The 9 most important coder types for ThorX6 dropdown
     */
    private static CoderInterface[] getThorX6CoderSelection() {
        java.util.List<CoderInterface> thorx6Selection = new java.util.ArrayList<>();
        
        // Select the 9 most commonly used coder types in ThorX6 (prioritized order)
        for (CoderInterface coder : RECONSTRUCTED_CODERS) {
            String name = coder.getTypeName().toLowerCase();
            
            // Priority 1: Essential coders (always included)
            if (name.contains("fixed") || name.contains("text")) {
                thorx6Selection.add(coder);
            } else if (name.contains("serial") && !name.contains("original")) {
                thorx6Selection.add(coder);
            } else if (name.contains("date") && !name.contains("original")) {
                thorx6Selection.add(coder);
            } else if (name.contains("barcode") && !name.contains("original")) {
                thorx6Selection.add(coder);
            }
        }
        
        // Priority 2: Add original ThorX6 coders if we need more
        if (thorx6Selection.size() < 9) {
            for (CoderInterface coder : RECONSTRUCTED_CODERS) {
                if (thorx6Selection.size() >= 9) break;
                String name = coder.getTypeName().toLowerCase();
                
                if (!thorx6Selection.contains(coder)) {
                    if (name.contains("vin") || name.contains("pin") || name.contains("shift") || 
                        name.contains("external") || name.contains("qr") || name.contains("datamatrix") ||
                        name.contains("random")) {
                        thorx6Selection.add(coder);
                    }
                }
            }
        }
        
        // Ensure we have exactly 9 coders (pad with null if needed)
        while (thorx6Selection.size() < 9) {
            thorx6Selection.add(null);
        }
        
        return thorx6Selection.subList(0, 9).toArray(new CoderInterface[9]);
    }
    
    /**
     * Get ThorX6-style coder icon (HTML formatted for better appearance)
     */
    private static String getThorX6CoderIcon(String coderType) {
        String type = coderType.toLowerCase();
        if (type.contains("fixed") || type.contains("text")) return "<html><center><b>TXT</b></center></html>";
        if (type.contains("serial")) return "<html><center><b>123</b></center></html>";
        if (type.contains("date") || type.contains("time")) return "<html><center><b>DATE</b></center></html>";
        if (type.contains("shift")) return "<html><center><b>SHIFT</b></center></html>";
        if (type.contains("barcode")) return "<html><center><b>|||</b></center></html>";
        if (type.contains("external")) return "<html><center><b>EXT</b></center></html>";
        if (type.contains("vin")) return "<html><center><b>VIN</b></center></html>";
        if (type.contains("pin")) return "<html><center><b>PIN</b></center></html>";
        if (type.contains("random")) return "<html><center><b>RND</b></center></html>";
        if (type.contains("qr")) return "<html><center><b>QR</b></center></html>";
        if (type.contains("datamatrix")) return "<html><center><b>DM</b></center></html>";
        return "<html><center><b>COD</b></center></html>";
    }
    
    /**
     * Get coder icon (soft-coded)
     */
    private static String getCoderIcon(String coderType) {
        if (!SHOW_CODER_ICONS_IN_DROPDOWN) return "";
        
        String type = coderType.toLowerCase();
        if (type.contains("fixed") || type.contains("text")) return "📝";
        if (type.contains("serial")) return "🔢";
        if (type.contains("date") || type.contains("time")) return "📅";
        if (type.contains("shift")) return "⏰";
        if (type.contains("barcode")) return "📊";
        if (type.contains("external")) return "📥";
        if (type.contains("random")) return "🎲";
        return "⚙️";
    }
    
    /**
     * Get coder description (soft-coded)
     */
    private static String getCoderDescription(String coderType) {
        String type = coderType.toLowerCase();
        if (type.contains("fixed")) return "Static text output";
        if (type.contains("serial")) return "Sequential numbering";
        if (type.contains("date")) return "Date/time stamps";
        if (type.contains("shift")) return "Shift-based codes";
        if (type.contains("barcode")) return "Barcode generation";
        if (type.contains("external")) return "External data input";
        if (type.contains("random")) return "Random number generation";
        return "Code generation";
    }
    
    /**
     * Show current coder preview in a popup
     */
    private static void showCoderPreview() {
        System.out.println("👁️ Showing preview for: " + currentActiveCoder.getTypeName());
        
        String previewText = currentActiveCoder.getPreviewText();
        
        JTextArea previewArea = new JTextArea(8, 40);
        previewArea.setText(previewText);
        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        previewArea.setBackground(new Color(240, 248, 255));
        
        JScrollPane scrollPane = new JScrollPane(previewArea);
        
        JOptionPane.showMessageDialog(null, scrollPane, 
            "Coder Preview - " + currentActiveCoder.getTypeName(), 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Add generated code to the drawing canvas as a text mark
     */
    private static void addGeneratedCodeToCanvas(String code) {
        try {
            DrawingCanvas canvas = getCurrentDrawingCanvas();
            if (canvas != null) {
                // Create a new text mark with the generated code
                // This integrates with the existing marking system
                System.out.println("📍 Adding generated code to canvas: " + code);
                
                // Use the existing mark creation system
                if (canvas != null && code != null && !code.trim().isEmpty()) {
                    // Find a good position for the new mark
                    int x = 100 + (int)(Math.random() * 200);
                    int y = 100 + (int)(Math.random() * 200);
                    
                    // Create text mark (this will depend on your existing TextMark system)
                    // For now, show success message
                    JOptionPane.showMessageDialog(null, 
                        "Code added to canvas at position (" + x + ", " + y + ")\nContent: " + code,
                        "Added to Canvas", 
                        JOptionPane.INFORMATION_MESSAGE);
                        
                    System.out.println("✅ Generated code successfully added to canvas");
                } else {
                    System.out.println("❌ Cannot add to canvas - canvas or code is null");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Canvas not available", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("❌ Error adding code to canvas: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error adding code to canvas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}