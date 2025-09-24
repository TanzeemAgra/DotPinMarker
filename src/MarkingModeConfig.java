import java.util.*;
import java.util.function.Function;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MarkingModeConfig - Soft-coded configuration for marking modes and content generation
 * Provides flexible, extensible marking mode definitions with validation and content generation
 */
public class MarkingModeConfig {
    
    // === MARKING MODE DEFINITIONS ===
    
    public enum MarkingType {
        STANDARD_TEXT("Standard Text", "Simple text marking"),
        ARCH_TEXT("Arch Text", "Curved/arched text layout"),
        DONUT_SHAPE("Donut Shape", "Circular text arrangement"),
        CUSTOM_SYMBOL("Custom Symbol", "User-defined symbols"),
        SERIAL_NUMBER("Serial Number", "Auto-incrementing serial numbers"),
        DATE_TIME("Date/Time", "Current date and time stamps"),
        LOGO_IMAGE("Logo/Image", "Image-based marking"),
        BARCODE("Barcode", "Linear barcode generation"),
        QR_CODE("QR Code", "2D QR code generation"),
        BATCH_CODE("Batch Code", "Manufacturing batch codes"),
        PART_NUMBER("Part Number", "Component part numbers");
        
        private final String displayName;
        private final String description;
        
        MarkingType(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
    
    // === MARKING MODE CONFIGURATION ===
    
    public static class ModeConfiguration {
        private final MarkingType type;
        private final String defaultContent;
        private final String tooltip;
        private final Function<String, String> contentGenerator;
        private final Function<String, Boolean> validator;
        private final Map<String, Object> parameters;
        private final boolean requiresTemplate;
        private final boolean supportsAutoIncrement;
        
        public ModeConfiguration(MarkingType type, String defaultContent, String tooltip,
                               Function<String, String> contentGenerator,
                               Function<String, Boolean> validator,
                               Map<String, Object> parameters,
                               boolean requiresTemplate, boolean supportsAutoIncrement) {
            this.type = type;
            this.defaultContent = defaultContent;
            this.tooltip = tooltip;
            this.contentGenerator = contentGenerator;
            this.validator = validator;
            this.parameters = new HashMap<>(parameters);
            this.requiresTemplate = requiresTemplate;
            this.supportsAutoIncrement = supportsAutoIncrement;
        }
        
        // Getters
        public MarkingType getType() { return type; }
        public String getDefaultContent() { return defaultContent; }
        public String getTooltip() { return tooltip; }
        public Function<String, String> getContentGenerator() { return contentGenerator; }
        public Function<String, Boolean> getValidator() { return validator; }
        public Map<String, Object> getParameters() { return new HashMap<>(parameters); }
        public boolean isRequiresTemplate() { return requiresTemplate; }
        public boolean isSupportsAutoIncrement() { return supportsAutoIncrement; }
    }
    
    // === CONTENT GENERATORS ===
    
    private static final Map<MarkingType, ModeConfiguration> MODE_CONFIGS = new HashMap<>();
    
    static {
        initializeModeConfigurations();
    }
    
    private static void initializeModeConfigurations() {
        // Standard Text Mode
        MODE_CONFIGS.put(MarkingType.STANDARD_TEXT, new ModeConfiguration(
            MarkingType.STANDARD_TEXT,
            "Sample Text",
            "Enter text to be marked directly on the material",
            content -> content.trim().isEmpty() ? "DEFAULT" : content.trim(),
            content -> content != null && !content.trim().isEmpty() && content.length() <= 100,
            Map.of("maxLength", 100, "allowSpecialChars", true),
            false, false
        ));
        
        // Arch Text Mode
        MODE_CONFIGS.put(MarkingType.ARCH_TEXT, new ModeConfiguration(
            MarkingType.ARCH_TEXT,
            "CURVED TEXT",
            "Text arranged in an arc or curved path",
            content -> content.trim().toUpperCase(),
            content -> content != null && !content.trim().isEmpty() && content.length() <= 50,
            Map.of("maxLength", 50, "curvature", 45.0, "arcRadius", 25.0),
            false, false
        ));
        
        // Serial Number Mode
        MODE_CONFIGS.put(MarkingType.SERIAL_NUMBER, new ModeConfiguration(
            MarkingType.SERIAL_NUMBER,
            "SN-001",
            "Auto-incrementing serial numbers (SN-001, SN-002, etc.)",
            content -> generateSerialNumber(content),
            content -> validateSerialPattern(content),
            Map.of("prefix", "SN-", "startNumber", 1, "digits", 3, "increment", 1),
            false, true
        ));
        
        // Date/Time Mode
        MODE_CONFIGS.put(MarkingType.DATE_TIME, new ModeConfiguration(
            MarkingType.DATE_TIME,
            getCurrentDateTime(),
            "Current date and time (automatically generated)",
            content -> generateDateTime(content),
            content -> true, // Always valid
            Map.of("format", "yyyy-MM-dd HH:mm", "autoUpdate", true),
            false, false
        ));
        
        // Barcode Mode
        MODE_CONFIGS.put(MarkingType.BARCODE, new ModeConfiguration(
            MarkingType.BARCODE,
            "1234567890128",
            "Linear barcode data (Code 128, Code 39, EAN-13)",
            content -> validateAndFormatBarcode(content),
            content -> validateBarcodeContent(content),
            Map.of("type", "CODE128", "checkDigit", true, "humanReadable", true),
            false, false
        ));
        
        // QR Code Mode
        MODE_CONFIGS.put(MarkingType.QR_CODE, new ModeConfiguration(
            MarkingType.QR_CODE,
            "https://company.com/product/",
            "2D QR code data (URL, text, contact info)",
            content -> content.trim(),
            content -> content != null && content.length() <= 2000,
            Map.of("errorCorrection", "M", "size", "auto", "encoding", "UTF-8"),
            false, false
        ));
        
        // Logo/Image Mode
        MODE_CONFIGS.put(MarkingType.LOGO_IMAGE, new ModeConfiguration(
            MarkingType.LOGO_IMAGE,
            "[Select Image File]",
            "Image-based marking from file (PNG, JPG, SVG)",
            content -> content,
            content -> validateImagePath(content),
            Map.of("supportedFormats", Arrays.asList("png", "jpg", "jpeg", "svg"), "maxSize", "500x500"),
            true, false
        ));
        
        // Batch Code Mode
        MODE_CONFIGS.put(MarkingType.BATCH_CODE, new ModeConfiguration(
            MarkingType.BATCH_CODE,
            "BATCH-" + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()),
            "Manufacturing batch codes with date",
            content -> generateBatchCode(content),
            content -> validateBatchCode(content),
            Map.of("includeDate", true, "prefix", "BATCH-", "format", "yyyyMMdd"),
            false, true
        ));
        
        // Part Number Mode
        MODE_CONFIGS.put(MarkingType.PART_NUMBER, new ModeConfiguration(
            MarkingType.PART_NUMBER,
            "PN-12345-A",
            "Component part numbers with validation",
            content -> content.trim().toUpperCase(),
            content -> validatePartNumber(content),
            Map.of("pattern", "[A-Z]{2}-\\d{5}-[A-Z]", "allowRevision", true),
            false, false
        ));
    }
    
    // === CONTENT GENERATION METHODS ===
    
    private static String generateSerialNumber(String pattern) {
        // Parse pattern like "SN-001" and increment
        if (pattern.matches(".*-\\d+")) {
            String[] parts = pattern.split("-");
            if (parts.length >= 2) {
                try {
                    int number = Integer.parseInt(parts[parts.length - 1]);
                    number++;
                    parts[parts.length - 1] = String.format("%0" + parts[parts.length - 1].length() + "d", number);
                    return String.join("-", parts);
                } catch (NumberFormatException e) {
                    return pattern;
                }
            }
        }
        return pattern;
    }
    
    private static String getCurrentDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now());
    }
    
    private static String generateDateTime(String format) {
        try {
            DateTimeFormatter formatter = format.isEmpty() ? 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") :
                DateTimeFormatter.ofPattern(format);
            return formatter.format(LocalDateTime.now());
        } catch (Exception e) {
            return getCurrentDateTime();
        }
    }
    
    private static String validateAndFormatBarcode(String content) {
        // Remove non-alphanumeric characters and ensure proper format
        String cleaned = content.replaceAll("[^A-Za-z0-9]", "");
        return cleaned.length() > 13 ? cleaned.substring(0, 13) : cleaned;
    }
    
    private static String generateBatchCode(String input) {
        if (input.startsWith("BATCH-")) {
            return input;
        }
        return "BATCH-" + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()) + "-" + 
               String.format("%03d", (int)(Math.random() * 1000));
    }
    
    // === VALIDATION METHODS ===
    
    private static boolean validateSerialPattern(String content) {
        return content != null && content.matches(".*-\\d+") && content.length() <= 20;
    }
    
    private static boolean validateBarcodeContent(String content) {
        return content != null && content.matches("[A-Za-z0-9]+") && 
               content.length() >= 8 && content.length() <= 13;
    }
    
    private static boolean validateImagePath(String content) {
        return content != null && (content.toLowerCase().endsWith(".png") || 
               content.toLowerCase().endsWith(".jpg") || 
               content.toLowerCase().endsWith(".jpeg") || 
               content.toLowerCase().endsWith(".svg") ||
               content.equals("[Select Image File]"));
    }
    
    private static boolean validateBatchCode(String content) {
        return content != null && content.matches("BATCH-\\d{8}(-\\d{3})?") && content.length() <= 25;
    }
    
    private static boolean validatePartNumber(String content) {
        return content != null && content.matches("[A-Z]{2}-\\d{5}-[A-Z]") && content.length() <= 15;
    }
    
    // === PUBLIC API ===
    
    public static ModeConfiguration getModeConfiguration(MarkingType type) {
        return MODE_CONFIGS.get(type);
    }
    
    public static List<MarkingType> getAllMarkingTypes() {
        return Arrays.asList(MarkingType.values());
    }
    
    public static String[] getDisplayNames() {
        return Arrays.stream(MarkingType.values())
                     .map(MarkingType::getDisplayName)
                     .toArray(String[]::new);
    }
    
    public static MarkingType getTypeByDisplayName(String displayName) {
        return Arrays.stream(MarkingType.values())
                     .filter(type -> type.getDisplayName().equals(displayName))
                     .findFirst()
                     .orElse(MarkingType.STANDARD_TEXT);
    }
    
    public static String generateContent(MarkingType type, String currentContent) {
        ModeConfiguration config = getModeConfiguration(type);
        if (config != null && config.getContentGenerator() != null) {
            return config.getContentGenerator().apply(currentContent);
        }
        return currentContent;
    }
    
    public static boolean validateContent(MarkingType type, String content) {
        ModeConfiguration config = getModeConfiguration(type);
        if (config != null && config.getValidator() != null) {
            return config.getValidator().apply(content);
        }
        return true;
    }
    
    public static Map<String, Object> getModeParameters(MarkingType type) {
        ModeConfiguration config = getModeConfiguration(type);
        return config != null ? config.getParameters() : new HashMap<>();
    }
}
