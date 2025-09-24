import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Smart Project Structure Manager - Soft-Coded Project Organization
 * Automatically manages separation between source files and compiled files
 */
public class ProjectStructureManager {
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // Project Structure Control Flags
    private static final boolean ENABLE_AUTOMATIC_SEPARATION = true;
    private static final boolean ENABLE_BUILD_INTEGRATION = true;
    private static final boolean ENABLE_STRUCTURE_VALIDATION = true;
    private static final boolean ENABLE_BACKUP_BEFORE_MOVE = true;
    private static final boolean ENABLE_VERBOSE_LOGGING = true;
    
    // Directory Configuration
    private static final String SOURCE_DIRECTORY = "src";
    private static final String BUILD_DIRECTORY = "build";
    private static final String BACKUP_DIRECTORY = "backup";
    
    // File Type Configuration
    private static final String[] SOURCE_EXTENSIONS = {".java", ".properties", ".txt", ".md"};
    private static final String[] COMPILED_EXTENSIONS = {".class"};
    private static final String[] EXCLUDED_FILES = {"package-info.java", "module-info.java"};
    
    // Logging Configuration
    private static final String LOG_PREFIX_INFO = "📁 [ProjectStructure]";
    private static final String LOG_PREFIX_SUCCESS = "✅ [Success]";
    private static final String LOG_PREFIX_WARNING = "⚠️ [Warning]";
    private static final String LOG_PREFIX_ERROR = "❌ [Error]";
    
    // ==================== MAIN STRUCTURE MANAGEMENT METHODS ====================
    
    /**
     * Main method to execute smart project structure management
     */
    public static void main(String[] args) {
        log(LOG_PREFIX_INFO, "Starting Smart Project Structure Management");
        
        if (ENABLE_AUTOMATIC_SEPARATION) {
            executeAutomaticSeparation();
        }
        
        if (ENABLE_STRUCTURE_VALIDATION) {
            validateProjectStructure();
        }
        
        log(LOG_PREFIX_SUCCESS, "Project Structure Management Complete!");
    }
    
    /**
     * Execute automatic file separation using soft-coded rules
     */
    public static void executeAutomaticSeparation() {
        log(LOG_PREFIX_INFO, "Executing Automatic File Separation...");
        
        try {
            // Create build directory if it doesn't exist
            createBuildDirectoryStructure();
            
            // Move compiled files to build directory
            int movedFiles = moveCompiledFilesToBuild();
            
            // Verify source directory only contains source files
            validateSourceDirectory();
            
            log(LOG_PREFIX_SUCCESS, String.format("Moved %d compiled files to build directory", movedFiles));
            
        } catch (Exception e) {
            log(LOG_PREFIX_ERROR, "Failed to execute automatic separation: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create build directory structure with soft-coded configuration
     */
    private static void createBuildDirectoryStructure() throws IOException {
        Path buildPath = Paths.get(BUILD_DIRECTORY);
        
        if (!Files.exists(buildPath)) {
            Files.createDirectories(buildPath);
            log(LOG_PREFIX_INFO, "Created build directory: " + BUILD_DIRECTORY);
        }
        
        if (ENABLE_BACKUP_BEFORE_MOVE) {
            Path backupPath = Paths.get(BACKUP_DIRECTORY);
            if (!Files.exists(backupPath)) {
                Files.createDirectories(backupPath);
                log(LOG_PREFIX_INFO, "Created backup directory: " + BACKUP_DIRECTORY);
            }
        }
    }
    
    /**
     * Move compiled files from src to build directory using soft-coded rules
     */
    private static int moveCompiledFilesToBuild() throws IOException {
        Path sourcePath = Paths.get(SOURCE_DIRECTORY);
        Path buildPath = Paths.get(BUILD_DIRECTORY);
        
        if (!Files.exists(sourcePath)) {
            log(LOG_PREFIX_WARNING, "Source directory not found: " + SOURCE_DIRECTORY);
            return 0;
        }
        
        List<Path> filesToMove = new ArrayList<>();
        
        // Find all compiled files in source directory
        try (Stream<Path> paths = Files.walk(sourcePath)) {
            filesToMove = paths
                .filter(Files::isRegularFile)
                .filter(ProjectStructureManager::isCompiledFile)
                .collect(Collectors.toList());
        }
        
        log(LOG_PREFIX_INFO, String.format("Found %d compiled files to move", filesToMove.size()));
        
        // Move each compiled file
        int movedCount = 0;
        for (Path file : filesToMove) {
            try {
                moveFileToTarget(file, buildPath);
                movedCount++;
                
                if (ENABLE_VERBOSE_LOGGING) {
                    log(LOG_PREFIX_INFO, "Moved: " + file.getFileName());
                }
            } catch (IOException e) {
                log(LOG_PREFIX_WARNING, "Failed to move file: " + file.getFileName() + " - " + e.getMessage());
            }
        }
        
        return movedCount;
    }
    
    /**
     * Check if file is a compiled file based on soft-coded extensions
     */
    private static boolean isCompiledFile(Path file) {
        String fileName = file.getFileName().toString().toLowerCase();
        
        for (String extension : COMPILED_EXTENSIONS) {
            if (fileName.endsWith(extension.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Move individual file to target directory with error handling
     */
    private static void moveFileToTarget(Path sourceFile, Path targetDirectory) throws IOException {
        Path targetFile = targetDirectory.resolve(sourceFile.getFileName());
        
        // Create backup if enabled
        if (ENABLE_BACKUP_BEFORE_MOVE && Files.exists(targetFile)) {
            Path backupFile = Paths.get(BACKUP_DIRECTORY, sourceFile.getFileName().toString() + ".backup");
            Files.copy(targetFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
        }
        
        // Move file
        Files.move(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Validate source directory contains only source files
     */
    private static void validateSourceDirectory() throws IOException {
        Path sourcePath = Paths.get(SOURCE_DIRECTORY);
        
        try (Stream<Path> paths = Files.walk(sourcePath)) {
            List<Path> compiledFiles = paths
                .filter(Files::isRegularFile)
                .filter(file -> isCompiledFileStatic(file))
                .collect(Collectors.toList());
            
            if (compiledFiles.isEmpty()) {
                log(LOG_PREFIX_SUCCESS, "Source directory is clean - contains only source files");
            } else {
                log(LOG_PREFIX_WARNING, String.format("Found %d compiled files still in source directory", compiledFiles.size()));
                for (Path file : compiledFiles) {
                    log(LOG_PREFIX_WARNING, "Remaining compiled file: " + file.getFileName());
                }
            }
        }
    }
    
    /**
     * Static version of compiled file check
     */
    private static boolean isCompiledFileStatic(Path file) {
        String fileName = file.getFileName().toString().toLowerCase();
        
        for (String extension : COMPILED_EXTENSIONS) {
            if (fileName.endsWith(extension.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Validate entire project structure
     */
    public static void validateProjectStructure() {
        log(LOG_PREFIX_INFO, "Validating Project Structure...");
        
        try {
            ProjectStructureReport report = generateStructureReport();
            displayStructureReport(report);
            
        } catch (Exception e) {
            log(LOG_PREFIX_ERROR, "Failed to validate project structure: " + e.getMessage());
        }
    }
    
    /**
     * Generate comprehensive project structure report
     */
    private static ProjectStructureReport generateStructureReport() throws IOException {
        ProjectStructureReport report = new ProjectStructureReport();
        
        // Analyze source directory
        if (Files.exists(Paths.get(SOURCE_DIRECTORY))) {
            analyzeDirectory(SOURCE_DIRECTORY, report.sourceStats);
        }
        
        // Analyze build directory
        if (Files.exists(Paths.get(BUILD_DIRECTORY))) {
            analyzeDirectory(BUILD_DIRECTORY, report.buildStats);
        }
        
        return report;
    }
    
    /**
     * Analyze directory contents and populate statistics
     */
    private static void analyzeDirectory(String directory, DirectoryStats stats) throws IOException {
        Path dirPath = Paths.get(directory);
        
        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(Files::isRegularFile)
                 .forEach(file -> categorizeFile(file, stats));
        }
    }
    
    /**
     * Categorize file type and update statistics
     */
    private static void categorizeFile(Path file, DirectoryStats stats) {
        String fileName = file.getFileName().toString().toLowerCase();
        
        if (fileName.endsWith(".java")) {
            stats.javaFiles++;
        } else if (fileName.endsWith(".class")) {
            stats.classFiles++;
        } else if (fileName.endsWith(".properties")) {
            stats.propertiesFiles++;
        } else {
            stats.otherFiles++;
        }
        
        stats.totalFiles++;
    }
    
    /**
     * Display comprehensive structure report
     */
    private static void displayStructureReport(ProjectStructureReport report) {
        log(LOG_PREFIX_INFO, "=== PROJECT STRUCTURE REPORT ===");
        
        log(LOG_PREFIX_INFO, String.format("📁 SOURCE DIRECTORY (%s):", SOURCE_DIRECTORY));
        displayDirectoryStats(report.sourceStats);
        
        log(LOG_PREFIX_INFO, String.format("📁 BUILD DIRECTORY (%s):", BUILD_DIRECTORY));
        displayDirectoryStats(report.buildStats);
        
        log(LOG_PREFIX_INFO, "=== STRUCTURE HEALTH CHECK ===");
        validateStructureHealth(report);
    }
    
    /**
     * Display statistics for a directory
     */
    private static void displayDirectoryStats(DirectoryStats stats) {
        log(LOG_PREFIX_INFO, String.format("   Java files: %d", stats.javaFiles));
        log(LOG_PREFIX_INFO, String.format("   Class files: %d", stats.classFiles));
        log(LOG_PREFIX_INFO, String.format("   Properties files: %d", stats.propertiesFiles));
        log(LOG_PREFIX_INFO, String.format("   Other files: %d", stats.otherFiles));
        log(LOG_PREFIX_INFO, String.format("   Total files: %d", stats.totalFiles));
    }
    
    /**
     * Validate structure health and provide recommendations
     */
    private static void validateStructureHealth(ProjectStructureReport report) {
        boolean isHealthy = true;
        
        // Check if source directory has compiled files
        if (report.sourceStats.classFiles > 0) {
            log(LOG_PREFIX_WARNING, String.format("Found %d .class files in source directory - should be 0", report.sourceStats.classFiles));
            isHealthy = false;
        }
        
        // Check if build directory has source files
        if (report.buildStats.javaFiles > 0) {
            log(LOG_PREFIX_WARNING, String.format("Found %d .java files in build directory - unusual", report.buildStats.javaFiles));
        }
        
        // Check if build directory exists and has compiled files
        if (report.buildStats.totalFiles == 0) {
            log(LOG_PREFIX_WARNING, "Build directory is empty - run compilation first");
            isHealthy = false;
        }
        
        if (isHealthy) {
            log(LOG_PREFIX_SUCCESS, "🎯 PROJECT STRUCTURE: EXCELLENT!");
            log(LOG_PREFIX_SUCCESS, "✅ Clean separation between source and compiled files");
        } else {
            log(LOG_PREFIX_WARNING, "🔧 PROJECT STRUCTURE: NEEDS ATTENTION");
            log(LOG_PREFIX_INFO, "💡 Run: java ProjectStructureManager to auto-fix issues");
        }
    }
    
    // ==================== INTEGRATION WITH BUILD PROCESS ====================
    
    /**
     * Execute structure management as part of build process
     */
    public static void executeAsPartOfBuild() {
        if (ENABLE_BUILD_INTEGRATION) {
            log(LOG_PREFIX_INFO, "Executing structure management as part of build process...");
            executeAutomaticSeparation();
        }
    }
    
    /**
     * Clean structure before compilation
     */
    public static void cleanStructureBeforeCompilation() {
        if (ENABLE_AUTOMATIC_SEPARATION) {
            log(LOG_PREFIX_INFO, "Cleaning project structure before compilation...");
            executeAutomaticSeparation();
        }
    }
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Soft-coded logging method
     */
    private static void log(String prefix, String message) {
        if (ENABLE_VERBOSE_LOGGING) {
            System.out.println(prefix + " " + message);
        }
    }
    
    // ==================== DATA STRUCTURES ====================
    
    /**
     * Directory statistics container
     */
    private static class DirectoryStats {
        int javaFiles = 0;
        int classFiles = 0;
        int propertiesFiles = 0;
        int otherFiles = 0;
        int totalFiles = 0;
    }
    
    /**
     * Project structure report container
     */
    private static class ProjectStructureReport {
        DirectoryStats sourceStats = new DirectoryStats();
        DirectoryStats buildStats = new DirectoryStats();
    }
}