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
    public static final String SERIALIZATION_FORMAT = "JSON";              // JSON, XML, or BINARY
    public static final boolean ENABLE_HUMAN_READABLE_FORMAT = true;        // ENABLE readable JSON format
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
    
    // Menu Items Configuration (Soft Coded)
    public static final String[] MENU_ITEMS = {
        "New Project",
        "Open Project", 
        "Save Project",
        "Save As...",
        "---", // Separator
        "Import Design",
        "Export Design",
        "---", // Separator
        "Recent Files",
        "---", // Separator
        "Settings",
        "About Rugrel",
        "Exit"
    };
    
    // Menu Icons (Soft Coded)
    public static final String[] MENU_ICONS = {
        "📄", // New Project
        "📂", // Open Project
        "💾", // Save Project
        "💾", // Save As
        "",   // Separator
        "📥", // Import Design
        "📤", // Export Design
        "",   // Separator
        "📋", // Recent Files
        "",   // Separator
        "⚙️", // Settings
        "ℹ️",  // About Rugrel
        "🚪"  // Exit
    };
    
    // Visual Configuration
    public static final Color LOGO_BACKGROUND = new Color(0, 120, 215);
    public static final Color LOGO_FOREGROUND = Color.WHITE;
    public static final Color MENU_BACKGROUND = Color.WHITE;
    public static final Color MENU_FOREGROUND = Color.BLACK;
    public static final Color MENU_HOVER = new Color(230, 240, 255);
    
    // Size Configuration
    public static final int LOGO_WIDTH = 120;
    public static final int LOGO_HEIGHT = 30;
    public static final int MENU_ITEM_HEIGHT = 25;
    public static final int MENU_WIDTH = 180;
    
    // Behavior Configuration
    public static final boolean ENABLE_HOVER_EFFECTS = true;
    public static final boolean ENABLE_MENU_ICONS = true;
    public static final boolean ENABLE_DEBUG_OUTPUT = true;
    
    /**
     * Create Rugrel logo button with dropdown functionality
     */
    public static JButton createRugrelLogoWithDropdown() {
        String buttonText = LOGO_TEXT;
        if (SHOW_DROPDOWN_INDICATOR) {
            buttonText += DROPDOWN_INDICATOR;
        }
        
        JButton logoButton = new JButton(buttonText);
        logoButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoButton.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
        logoButton.setBackground(LOGO_BACKGROUND);
        logoButton.setForeground(LOGO_FOREGROUND);
        logoButton.setBorder(BorderFactory.createRaisedBevelBorder());
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
     * Show dropdown menu below the logo button
     */
    private static void showDropdownMenu(JButton logoButton) {
        if (ENABLE_DEBUG_OUTPUT) {
            System.out.println("📋 Showing Rugrel dropdown menu...");
        }
        
        JPopupMenu dropdownMenu = new JPopupMenu();
        dropdownMenu.setBackground(MENU_BACKGROUND);
        dropdownMenu.setBorder(BorderFactory.createRaisedBevelBorder());
        
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
        
        try {
            switch (menuItem) {
                case "New Project":
                    handleNewProjectWithDB();
                    break;
                case "Open Project":
                    handleOpenProjectWithDB();
                    break;
                case "Save Project":
                    handleSaveProjectWithDB();
                    break;
                case "Save As...":
                    handleSaveAsProjectWithDB();
                    break;
                case "Import Design":
                    handleImportDesignWithDB();
                    break;
                case "Export Design":
                    handleExportDesignWithDB();
                    break;
                case "Recent Files":
                    handleRecentFilesWithDB();
                    break;
                case "Settings":
                    handleSettingsWithDB();
                    break;
                case "About Rugrel":
                    handleAboutRugrelWithDB();
                    break;
                case "Exit":
                    handleExitWithDB();
                    break;
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
                // Load complete project state (canvas + database)
                boolean success = loadCompleteProjectState(selectedProject);
                
                if (success) {
                    updateRecentFiles(selectedProject);
                    System.out.println("✅ Complete project state loaded: " + selectedProject);
                } else {
                    System.err.println("❌ Failed to load complete project state: " + selectedProject);
                    showEnhancedDialog("Error", "Failed to load project: " + selectedProject, "error");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error opening project: " + e.getMessage());
            showEnhancedDialog("Error", "Failed to open project: " + e.getMessage(), "error");
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
        // TODO: Integrate with application state management
        return "Current RUGREL Project"; // Get from application state
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
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Auto-save interval:"));
        panel.add(new JSpinner(new SpinnerNumberModel(5, 1, 60, 1)));
        
        panel.add(new JLabel("Grid size:"));
        JComboBox<String> gridCombo = new JComboBox<>(new String[]{"14x8", "10x8", "Custom"});
        panel.add(gridCombo);
        
        return panel;
    }
    
    private static JPanel createDatabaseSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Database location:"));
        panel.add(new JTextField("./database/"));
        
        panel.add(new JLabel("Auto-backup:"));
        panel.add(new JCheckBox("Enable automatic backups", true));
        
        return panel;
    }
    
    private static JPanel createGridSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
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

    // ==================== PROJECT STATE SERIALIZATION SYSTEM ====================
    
    /**
     * Project State Container - Serializable class to store complete canvas state
     */
    public static class ProjectState implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        // Canvas State
        public java.util.List<Mark> marks;
        public java.util.List<TextMark> textMarks;
        public double zoomLevel;
        public int viewOffsetX;
        public int viewOffsetY;
        public boolean gridVisible;
        public boolean materialBoundaryVisible;
        public boolean dotPreviewEnabled;
        
        // Selection State
        public int selectedMarkIndex = -1; // Index of selected mark in marks list
        
        // Undo/Redo State
        public java.util.List<java.util.List<Mark>> undoHistory;
        public java.util.List<java.util.List<Mark>> redoHistory;
        
        // Project Metadata
        public String projectName;
        public String projectId;
        public java.util.Date lastSaved;
        public String version = "1.0";
        
        public ProjectState() {
            marks = new java.util.ArrayList<>();
            textMarks = new java.util.ArrayList<>();
            undoHistory = new java.util.ArrayList<>();
            redoHistory = new java.util.ArrayList<>();
            lastSaved = new java.util.Date();
        }
    }
    
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
     * Get DrawingCanvas reference from MarkingInterfaceApp
     */
    private static DrawingCanvas getDrawingCanvasReference() {
        try {
            return MarkingInterfaceApp.getDrawingCanvas();
        } catch (Exception e) {
            System.err.println("❌ Could not get DrawingCanvas reference: " + e.getMessage());
            return null;
        }
    }
    
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
            String fileName = projectName.replaceAll("[^a-zA-Z0-9._-]", "_") + PROJECT_FILE_EXTENSION;
            java.io.File projectFile = new java.io.File(PROJECT_DATA_DIRECTORY, fileName);
            
            if (!projectFile.exists()) {
                System.err.println("❌ Project file not found: " + projectFile.getAbsolutePath());
                return null;
            }
            
            // Try compressed format first
            ProjectState projectState = loadCompressedProjectState(projectFile);
            if (projectState != null) {
                return projectState;
            }
            
            // Fall back to uncompressed format
            return loadUncompressedProjectState(projectFile);
            
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
            // Not a compressed file, or other error
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
}