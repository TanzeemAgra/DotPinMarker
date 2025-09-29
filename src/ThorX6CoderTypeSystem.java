import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * ThorX6 Coder Type System - Modular Implementation
 * 
 * This module implements a comprehensive coder type system that mimics ThorX6 software functionality.
 * It provides various coder types (No Code, Serial Number, VIN, Date/Time, Random Number) with
 * real-time preview and configuration capabilities.
 * 
 * Design Principles:
 * - Modular OOP design for easy extensibility
 * - Non-intrusive integration with existing codebase
 * - Real-time preview functionality
 * - Configuration persistence
 * - ThorX6-compatible behavior
 * 
 * @author DotPin Marking Software
 * @version 1.0
 */
public class ThorX6CoderTypeSystem {
    
    // ==================== CORE INTERFACES ====================
    
    /**
     * Core interface that all coder types must implement.
     * This ensures consistent behavior and easy extensibility.
     */
    public interface CoderType {
        /**
         * Generate the actual code based on current configuration
         * @return Generated code string
         */
        String generateCode();
        
        /**
         * Get the display name of this coder type
         * @return Human-readable name for dropdowns/UI
         */
        String getTypeName();
        
        /**
         * Get a preview of what the next generated code will look like
         * @return Preview string for real-time display
         */
        String getPreview();
        
        /**
         * Check if this coder type requires configuration
         * @return true if configuration panel should be shown
         */
        boolean requiresConfiguration();
        
        /**
         * Get the configuration panel for this coder type
         * @return JPanel with configuration controls, or null if no config needed
         */
        JPanel getConfigurationPanel();
        
        /**
         * Reset the coder to its initial state (for Serial Numbers, counters, etc.)
         */
        void reset();
        
        /**
         * Get configuration as a map for persistence
         * @return Configuration parameters
         */
        Map<String, Object> getConfiguration();
        
        /**
         * Set configuration from a map (for loading saved settings)
         * @param config Configuration parameters
         */
        void setConfiguration(Map<String, Object> config);
    }
    
    // ==================== ABSTRACT BASE CLASS ====================
    
    /**
     * Abstract base class providing common functionality for all coder types.
     * This reduces code duplication and ensures consistent behavior.
     */
    public static abstract class BaseCoderType implements CoderType {
        protected String typeName;
        protected JPanel configPanel;
        protected boolean configurationChanged = false;
        
        public BaseCoderType(String typeName) {
            this.typeName = typeName;
        }
        
        @Override
        public String getTypeName() {
            return typeName;
        }
        
        @Override
        public void reset() {
            // Default implementation - override if needed
        }
        
        @Override
        public Map<String, Object> getConfiguration() {
            return new HashMap<>(); // Default empty config
        }
        
        @Override
        public void setConfiguration(Map<String, Object> config) {
            // Default implementation - override if needed
        }
        
        /**
         * Notify that configuration has changed (triggers preview update)
         */
        protected void notifyConfigurationChanged() {
            configurationChanged = true;
            // This will be used by the GUI to update previews
        }
        
        /**
         * Create a labeled component for configuration panels
         */
        protected JPanel createLabeledComponent(String label, JComponent component) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.add(new JLabel(label + ":"));
            panel.add(component);
            return panel;
        }
        
        /**
         * Create a titled border for configuration sections
         */
        protected JPanel createTitledSection(String title, JComponent... components) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createTitledBorder(title));
            
            for (JComponent component : components) {
                panel.add(component);
            }
            
            return panel;
        }
    }
    
    // ==================== CODER TYPE REGISTRY ====================
    
    /**
     * Registry for all available coder types.
     * This makes it easy to add new coder types in the future.
     */
    public static class CoderTypeRegistry {
        private static final Map<String, Class<? extends CoderType>> registeredTypes = new HashMap<>();
        private static final Map<String, CoderType> instances = new HashMap<>();
        
        static {
            // Register all built-in coder types
            registerCoderType("No Code", NoCodeCoder.class);
            registerCoderType("Serial Number", SerialNumberCoder.class);
            registerCoderType("VIN", VINCoder.class);
            registerCoderType("Date/Time", DateTimeCoder.class);
            registerCoderType("Random Number", RandomNumberCoder.class);
        }
        
        /**
         * Register a new coder type (for future extensibility)
         */
        public static void registerCoderType(String name, Class<? extends CoderType> coderClass) {
            registeredTypes.put(name, coderClass);
        }
        
        /**
         * Get all available coder type names
         */
        public static String[] getAvailableTypes() {
            return registeredTypes.keySet().toArray(new String[0]);
        }
        
        /**
         * Get a coder type instance by name
         */
        public static CoderType getCoderType(String typeName) {
            if (!instances.containsKey(typeName)) {
                try {
                    Class<? extends CoderType> coderClass = registeredTypes.get(typeName);
                    if (coderClass != null) {
                        instances.put(typeName, coderClass.getDeclaredConstructor().newInstance());
                    }
                } catch (Exception e) {
                    System.err.println("Error creating coder type: " + typeName + " - " + e.getMessage());
                    return null;
                }
            }
            return instances.get(typeName);
        }
    }
    
    // ==================== CODER TYPE MANAGER ====================
    
    /**
     * Manager class that handles the overall coder type system.
     * This provides a clean interface to the rest of the application.
     */
    public static class CoderTypeManager {
        private CoderType currentCoderType;
        private String currentTypeName;
        private java.util.List<CoderTypeChangeListener> listeners = new java.util.ArrayList<>();
        
        public interface CoderTypeChangeListener {
            void onCoderTypeChanged(CoderType newType, String typeName);
            void onPreviewUpdated(String preview);
        }
        
        public CoderTypeManager() {
            // Default to "No Code"
            setCoderType("No Code");
        }
        
        /**
         * Set the current coder type
         */
        public void setCoderType(String typeName) {
            this.currentTypeName = typeName;
            this.currentCoderType = CoderTypeRegistry.getCoderType(typeName);
            
            // Notify listeners
            for (CoderTypeChangeListener listener : listeners) {
                listener.onCoderTypeChanged(currentCoderType, typeName);
                if (currentCoderType != null) {
                    listener.onPreviewUpdated(currentCoderType.getPreview());
                }
            }
        }
        
        /**
         * Generate code using current coder type
         */
        public String generateCode() {
            if (currentCoderType != null) {
                return currentCoderType.generateCode();
            }
            return "";
        }
        
        /**
         * Get current preview
         */
        public String getCurrentPreview() {
            if (currentCoderType != null) {
                return currentCoderType.getPreview();
            }
            return "";
        }
        
        /**
         * Get current coder type
         */
        public CoderType getCurrentCoderType() {
            return currentCoderType;
        }
        
        /**
         * Get current type name
         */
        public String getCurrentTypeName() {
            return currentTypeName;
        }
        
        /**
         * Add a change listener
         */
        public void addChangeListener(CoderTypeChangeListener listener) {
            listeners.add(listener);
        }
        
        /**
         * Remove a change listener
         */
        public void removeChangeListener(CoderTypeChangeListener listener) {
            listeners.remove(listener);
        }
        
        /**
         * Update preview (call this when configuration changes)
         */
        public void updatePreview() {
            if (currentCoderType != null) {
                for (CoderTypeChangeListener listener : listeners) {
                    listener.onPreviewUpdated(currentCoderType.getPreview());
                }
            }
        }
    }
}