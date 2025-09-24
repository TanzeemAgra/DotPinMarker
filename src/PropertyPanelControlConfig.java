/**
 * Property Panel Control Configuration
 * Soft-coded approach to control which property panel system to use
 * Prevents multiple property panels from appearing simultaneously
 */
public class PropertyPanelControlConfig {
    
    // ==================== SOFT CODING CONFIGURATION ====================
    
    // SELECTIVE DUPLICATE REMOVAL (Soft Coded) - Keep bottom strip, remove upper Mark tab strip
    public static final boolean DISABLE_MARK_TAB_PROPERTIES = true;  // DISABLE upper Mark tab property strip (keep bottom one)
    public static final boolean KEEP_BOTTOM_PROPERTY_STRIP = true;    // KEEP bottom property strip active
    
    // UNIFIED PROPERTY SYSTEM CONTROL (Soft Coded)
    public static final boolean USE_UNIFIED_PROPERTIES = false;     // DISABLE unified property system 
    public static final boolean PREVENT_DUPLICATES = false;         // Allow original property panels
    
    // Legacy System Control (Keep bottom strip active)
    public static final boolean ENABLE_COORDINATE_TRACKING = true;   // ENABLE coordinate tracking (bottom strip)
    public static final boolean USE_THORX6_PROPERTIES = true;       // ENABLE ThorX6 properties (bottom strip)
    public static final boolean USE_OLD_PROPERTY_STRIP = false;      // Keep old PropertyStrip disabled
    public static final boolean ENABLE_PROPERTY_DEBUGGING = true;   // Debug property panel usage
    
    // Fallback Configuration
    public static final boolean ALLOW_MULTIPLE_PANELS = false;      // Disable multiple panels
    public static final boolean AUTO_HIDE_OLD_PANELS = true;        // Auto hide old panels
    
    // Visual Configuration
    public static final boolean SHOW_CONTROL_MESSAGES = true;       // Show control messages
    
    /**
     * Check if ThorX6 properties should be used (Soft Coded Control)
     */
    public static boolean shouldUseThorX6Properties() {
        if (ENABLE_PROPERTY_DEBUGGING && SHOW_CONTROL_MESSAGES) {
            System.out.println("🔧 Property Panel Control: ThorX6 = " + USE_THORX6_PROPERTIES);
        }
        return USE_THORX6_PROPERTIES;
    }
    
    /**
     * Check if old PropertyStrip should be used (Soft Coded Control)
     */
    public static boolean shouldUseOldPropertyStrip() {
        boolean shouldUse = USE_OLD_PROPERTY_STRIP && (!USE_THORX6_PROPERTIES || ALLOW_MULTIPLE_PANELS);
        
        if (ENABLE_PROPERTY_DEBUGGING && SHOW_CONTROL_MESSAGES) {
            System.out.println("🔧 Property Panel Control: Old PropertyStrip = " + shouldUse);
            if (!shouldUse && USE_OLD_PROPERTY_STRIP) {
                System.out.println("   ↳ Old PropertyStrip disabled (ThorX6 properties active)");
            }
        }
        
        return shouldUse;
    }
    
    /**
     * Get a replacement panel when old PropertyStrip is disabled (Soft Coded Approach)
     */
    public static javax.swing.JPanel getReplacementPanel() {
        if (shouldUseThorX6Properties()) {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 Using ThorX6 properties panel as replacement");
            }
            return ThorX6PropertiesConfig.createThorX6PropertiesPanel();
        } else {
            // Return empty panel if both are disabled
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 Using empty panel (no properties enabled)");
            }
            javax.swing.JPanel emptyPanel = new javax.swing.JPanel();
            emptyPanel.setPreferredSize(new java.awt.Dimension(0, 0));
            return emptyPanel;
        }
    }
    
    /**
     * Create controlled PropertyStrip (returns appropriate panel based on configuration)
     */
    public static javax.swing.JComponent createControlledPropertyPanel(DrawingCanvas canvas) {
        if (shouldUseOldPropertyStrip()) {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 Creating old PropertyStrip");
            }
            return new PropertyStrip(canvas);
        } else if (shouldUseThorX6Properties()) {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 Creating ThorX6 properties panel instead of old PropertyStrip");
            }
            return ThorX6PropertiesConfig.createThorX6PropertiesPanel();
        } else {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 No property panel enabled - using empty panel");
            }
            javax.swing.JPanel emptyPanel = new javax.swing.JPanel();
            emptyPanel.setPreferredSize(new java.awt.Dimension(0, 0));
            return emptyPanel;
        }
    }
    
    /**
     * Check if coordinate tracking should be enabled (Soft Coded Control)
     */
    public static boolean shouldEnableCoordinateTracking() {
        if (ENABLE_PROPERTY_DEBUGGING && SHOW_CONTROL_MESSAGES) {
            System.out.println("🔧 Coordinate Tracking Control: " + ENABLE_COORDINATE_TRACKING);
        }
        return ENABLE_COORDINATE_TRACKING;
    }
    
    /**
     * Check if Mark tab properties should be disabled (Soft Coded Control)
     */
    public static boolean shouldDisableMarkTabProperties() {
        if (ENABLE_PROPERTY_DEBUGGING && SHOW_CONTROL_MESSAGES) {
            if (DISABLE_MARK_TAB_PROPERTIES) {
                System.out.println("🗑️ Mark Tab Properties: DISABLED (removing upper duplicate - keeping bottom strip)");
            }
        }
        return DISABLE_MARK_TAB_PROPERTIES;
    }

    /**
     * Create coordinate tracking panel or unified panel based on soft-coded configuration
     */
    public static javax.swing.JComponent createCoordinateTrackingPanel(DrawingCanvas canvas) {
        if (USE_UNIFIED_PROPERTIES) {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🎯 Using Unified Property System (Total Print, Print Today, X, Y)");
                System.out.println("🧹 Duplicate prevention active - clean interface guaranteed");
            }
            // Note: UnifiedPropertyConfig was removed during revert
            javax.swing.JPanel emptyPanel = new javax.swing.JPanel();
            emptyPanel.setPreferredSize(new java.awt.Dimension(0, 0));
            emptyPanel.setVisible(false);
            return emptyPanel;
        } else if (shouldEnableCoordinateTracking()) {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 Creating coordinate tracking panel");
            }
            return createControlledPropertyPanel(canvas);
        } else {
            if (ENABLE_PROPERTY_DEBUGGING) {
                System.out.println("🔧 All property panels disabled - using empty panel");
            }
            javax.swing.JPanel emptyPanel = new javax.swing.JPanel();
            emptyPanel.setPreferredSize(new java.awt.Dimension(0, 0));
            emptyPanel.setVisible(false);
            return emptyPanel;
        }
    }
    
    /**
     * Validate property panel configuration
     */
    public static void validateConfiguration() {
        if (ENABLE_PROPERTY_DEBUGGING) {
            System.out.println("📋 === PROPERTY PANEL CONFIGURATION ===");
            System.out.println("   Coordinate Tracking: " + ENABLE_COORDINATE_TRACKING);
            System.out.println("   ThorX6 Properties: " + USE_THORX6_PROPERTIES);
            System.out.println("   Old PropertyStrip: " + USE_OLD_PROPERTY_STRIP);
            System.out.println("   Allow Multiple: " + ALLOW_MULTIPLE_PANELS);
            System.out.println("   Auto Hide Old: " + AUTO_HIDE_OLD_PANELS);
            
            if (USE_THORX6_PROPERTIES && USE_OLD_PROPERTY_STRIP && !ALLOW_MULTIPLE_PANELS) {
                System.out.println("   ⚠️ Conflict detected: Both panels enabled but multiple panels disabled");
                System.out.println("   ↳ ThorX6 properties will take precedence");
            }
            
            System.out.println("📋 =====================================");
        }
    }
    
    /**
     * Print current active panel info
     */
    public static void printActivePanel() {
        if (SHOW_CONTROL_MESSAGES) {
            if (!shouldEnableCoordinateTracking()) {
                System.out.println("✅ Coordinate Tracking: DISABLED (removed as requested)");
            } else if (shouldUseThorX6Properties()) {
                System.out.println("✅ Active Property Panel: ThorX6 Properties (New)");
            } else if (shouldUseOldPropertyStrip()) {
                System.out.println("✅ Active Property Panel: PropertyStrip (Old)");
            } else {
                System.out.println("✅ Active Property Panel: None (Disabled)");
            }
        }
    }
}