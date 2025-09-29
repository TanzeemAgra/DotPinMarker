import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Individual Coder Type Implementations
 * 
 * This file contains all the concrete implementations of the CoderType interface.
 * Each coder type has its own specific logic and configuration options.
 * 
 * Implemented Coder Types:
 * - NoCodeCoder: Static text input
 * - SerialNumberCoder: Auto-incrementing numbers with configuration
 * - VINCoder: VIN-compliant 17-character codes
 * - DateTimeCoder: System date/time in various formats
 * - RandomNumberCoder: Random numeric codes with configurable range
 */

// ==================== NO CODE CODER ====================

/**
 * No Code Coder - User enters static text
 */
class NoCodeCoder extends ThorX6CoderTypeSystem.BaseCoderType {
    private String userText = "";
    private JTextField textField;
    
    public NoCodeCoder() {
        super("No Code");
    }
    
    @Override
    public String generateCode() {
        return userText;
    }
    
    @Override
    public String getPreview() {
        return userText.isEmpty() ? "[Enter text...]" : userText;
    }
    
    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    
    @Override
    public JPanel getConfigurationPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
            
            textField = new JTextField(userText, 20);
            textField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { updateText(); }
                @Override
                public void removeUpdate(DocumentEvent e) { updateText(); }
                @Override
                public void changedUpdate(DocumentEvent e) { updateText(); }
                
                private void updateText() {
                    userText = textField.getText();
                    notifyConfigurationChanged();
                }
            });
            
            JPanel inputPanel = createLabeledComponent("Text", textField);
            configPanel.add(createTitledSection("Static Text Configuration", inputPanel));
        }
        return configPanel;
    }
    
    @Override
    public Map<String, Object> getConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put("userText", userText);
        return config;
    }
    
    @Override
    public void setConfiguration(Map<String, Object> config) {
        if (config.containsKey("userText")) {
            userText = (String) config.get("userText");
            if (textField != null) {
                textField.setText(userText);
            }
        }
    }
}

// ==================== SERIAL NUMBER CODER ====================

/**
 * Serial Number Coder - Auto-incrementing numbers with full configuration
 */
class SerialNumberCoder extends ThorX6CoderTypeSystem.BaseCoderType {
    private int startValue = 1000;
    private int stepValue = 1;
    private int maxValue = 99999;
    private int currentValue;
    private int repeatCount = 1;
    private int currentRepeat = 0;
    private boolean resetOnMax = true;
    
    // GUI Components
    private JSpinner startSpinner;
    private JSpinner stepSpinner;
    private JSpinner maxSpinner;
    private JSpinner repeatSpinner;
    private JCheckBox resetCheckBox;
    
    public SerialNumberCoder() {
        super("Serial Number");
        currentValue = startValue;
    }
    
    @Override
    public String generateCode() {
        String code = String.format("%05d", currentValue);
        
        currentRepeat++;
        if (currentRepeat >= repeatCount) {
            currentRepeat = 0;
            currentValue += stepValue;
            
            if (currentValue > maxValue) {
                if (resetOnMax) {
                    currentValue = startValue;
                } else {
                    currentValue = maxValue; // Stay at max
                }
            }
        }
        
        return code;
    }
    
    @Override
    public String getPreview() {
        return String.format("%05d (Next: %05d)", currentValue, 
                           currentValue + (currentRepeat >= repeatCount - 1 ? stepValue : 0));
    }
    
    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    
    @Override
    public JPanel getConfigurationPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
            
            // Create spinners
            startSpinner = new JSpinner(new SpinnerNumberModel(startValue, 0, 999999, 1));
            stepSpinner = new JSpinner(new SpinnerNumberModel(stepValue, 1, 1000, 1));
            maxSpinner = new JSpinner(new SpinnerNumberModel(maxValue, 1, 999999, 1));
            repeatSpinner = new JSpinner(new SpinnerNumberModel(repeatCount, 1, 100, 1));
            resetCheckBox = new JCheckBox("Reset to start when max reached", resetOnMax);
            
            // Add change listeners
            ChangeListener updateListener = e -> {
                startValue = (Integer) startSpinner.getValue();
                stepValue = (Integer) stepSpinner.getValue();
                maxValue = (Integer) maxSpinner.getValue();
                repeatCount = (Integer) repeatSpinner.getValue();
                resetOnMax = resetCheckBox.isSelected();
                
                // Reset current value if start changed
                if (e.getSource() == startSpinner) {
                    currentValue = startValue;
                    currentRepeat = 0;
                }
                
                notifyConfigurationChanged();
            };
            
            startSpinner.addChangeListener(updateListener);
            stepSpinner.addChangeListener(updateListener);
            maxSpinner.addChangeListener(updateListener);
            repeatSpinner.addChangeListener(updateListener);
            resetCheckBox.addActionListener(e -> {
                resetOnMax = resetCheckBox.isSelected();
                notifyConfigurationChanged();
            });
            
            // Layout
            JPanel rangePanel = createLabeledComponent("Start Value", startSpinner);
            rangePanel.add(createLabeledComponent("Step", stepSpinner));
            rangePanel.add(createLabeledComponent("Max Value", maxSpinner));
            
            JPanel behaviorPanel = createLabeledComponent("Repeat Count", repeatSpinner);
            behaviorPanel.add(resetCheckBox);
            
            // Reset button
            JButton resetButton = new JButton("Reset Counter");
            resetButton.addActionListener(e -> {
                currentValue = startValue;
                currentRepeat = 0;
                notifyConfigurationChanged();
            });
            
            configPanel.add(createTitledSection("Range Configuration", rangePanel));
            configPanel.add(createTitledSection("Behavior Configuration", behaviorPanel));
            configPanel.add(createTitledSection("Reset", resetButton));
        }
        return configPanel;
    }
    
    @Override
    public void reset() {
        currentValue = startValue;
        currentRepeat = 0;
    }
    
    @Override
    public Map<String, Object> getConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put("startValue", startValue);
        config.put("stepValue", stepValue);
        config.put("maxValue", maxValue);
        config.put("repeatCount", repeatCount);
        config.put("resetOnMax", resetOnMax);
        config.put("currentValue", currentValue);
        config.put("currentRepeat", currentRepeat);
        return config;
    }
    
    @Override
    public void setConfiguration(Map<String, Object> config) {
        if (config.containsKey("startValue")) startValue = (Integer) config.get("startValue");
        if (config.containsKey("stepValue")) stepValue = (Integer) config.get("stepValue");
        if (config.containsKey("maxValue")) maxValue = (Integer) config.get("maxValue");
        if (config.containsKey("repeatCount")) repeatCount = (Integer) config.get("repeatCount");
        if (config.containsKey("resetOnMax")) resetOnMax = (Boolean) config.get("resetOnMax");
        if (config.containsKey("currentValue")) currentValue = (Integer) config.get("currentValue");
        if (config.containsKey("currentRepeat")) currentRepeat = (Integer) config.get("currentRepeat");
        
        // Update GUI if it exists
        if (startSpinner != null) {
            startSpinner.setValue(startValue);
            stepSpinner.setValue(stepValue);
            maxSpinner.setValue(maxValue);
            repeatSpinner.setValue(repeatCount);
            resetCheckBox.setSelected(resetOnMax);
        }
    }
}

// ==================== VIN CODER ====================

/**
 * VIN Coder - Generates VIN-compliant 17-character codes
 */
class VINCoder extends ThorX6CoderTypeSystem.BaseCoderType {
    private static final String VIN_CHARS = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789"; // No I, O, Q
    private static final String VIN_NUMBERS = "0123456789";
    private Random random = new Random();
    
    // VIN Configuration
    private String wmiPrefix = "1HG"; // World Manufacturer Identifier (first 3 chars)
    private boolean useSequential = false;
    private int sequentialCounter = 100000;
    
    private JTextField wmiField;
    private JCheckBox sequentialCheckBox;
    private JSpinner counterSpinner;
    
    public VINCoder() {
        super("VIN");
    }
    
    @Override
    public String generateCode() {
        StringBuilder vin = new StringBuilder();
        
        // WMI (positions 1-3)
        vin.append(wmiPrefix.length() >= 3 ? wmiPrefix.substring(0, 3) : String.format("%-3s", wmiPrefix).replace(' ', 'A'));
        
        // VDS (positions 4-8) - Vehicle Descriptor Section
        for (int i = 0; i < 5; i++) {
            vin.append(VIN_CHARS.charAt(random.nextInt(VIN_CHARS.length())));
        }
        
        // Check digit (position 9) - simplified
        vin.append(VIN_NUMBERS.charAt(random.nextInt(VIN_NUMBERS.length())));
        
        // Model year (position 10)
        vin.append(VIN_CHARS.charAt(random.nextInt(VIN_CHARS.length())));
        
        // Plant code (position 11)
        vin.append(VIN_CHARS.charAt(random.nextInt(VIN_CHARS.length())));
        
        // Sequential number (positions 12-17)
        if (useSequential) {
            vin.append(String.format("%06d", sequentialCounter++));
        } else {
            for (int i = 0; i < 6; i++) {
                vin.append(VIN_NUMBERS.charAt(random.nextInt(VIN_NUMBERS.length())));
            }
        }
        
        return vin.toString();
    }
    
    @Override
    public String getPreview() {
        return generateCode() + " (Sample VIN)";
    }
    
    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    
    @Override
    public JPanel getConfigurationPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
            
            wmiField = new JTextField(wmiPrefix, 10);
            wmiField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { updateWMI(); }
                @Override
                public void removeUpdate(DocumentEvent e) { updateWMI(); }
                @Override
                public void changedUpdate(DocumentEvent e) { updateWMI(); }
                
                private void updateWMI() {
                    wmiPrefix = wmiField.getText().toUpperCase();
                    notifyConfigurationChanged();
                }
            });
            
            sequentialCheckBox = new JCheckBox("Use Sequential Numbers", useSequential);
            counterSpinner = new JSpinner(new SpinnerNumberModel(sequentialCounter, 100000, 999999, 1));
            
            sequentialCheckBox.addActionListener(e -> {
                useSequential = sequentialCheckBox.isSelected();
                counterSpinner.setEnabled(useSequential);
                notifyConfigurationChanged();
            });
            
            counterSpinner.addChangeListener(e -> {
                sequentialCounter = (Integer) counterSpinner.getValue();
                notifyConfigurationChanged();
            });
            
            counterSpinner.setEnabled(useSequential);
            
            JPanel wmiPanel = createLabeledComponent("WMI Prefix (3 chars)", wmiField);
            JPanel seqPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            seqPanel.add(sequentialCheckBox);
            seqPanel.add(new JLabel("Counter:"));
            seqPanel.add(counterSpinner);
            
            configPanel.add(createTitledSection("VIN Configuration", wmiPanel, seqPanel));
            
            // Info panel
            JTextArea infoArea = new JTextArea("VIN Format: WMI(3) + VDS(5) + Check(1) + Year(1) + Plant(1) + Serial(6)\nExcludes: I, O, Q characters");
            infoArea.setEditable(false);
            infoArea.setBackground(configPanel.getBackground());
            configPanel.add(createTitledSection("VIN Information", infoArea));
        }
        return configPanel;
    }
    
    @Override
    public Map<String, Object> getConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put("wmiPrefix", wmiPrefix);
        config.put("useSequential", useSequential);
        config.put("sequentialCounter", sequentialCounter);
        return config;
    }
    
    @Override
    public void setConfiguration(Map<String, Object> config) {
        if (config.containsKey("wmiPrefix")) wmiPrefix = (String) config.get("wmiPrefix");
        if (config.containsKey("useSequential")) useSequential = (Boolean) config.get("useSequential");
        if (config.containsKey("sequentialCounter")) sequentialCounter = (Integer) config.get("sequentialCounter");
        
        if (wmiField != null) {
            wmiField.setText(wmiPrefix);
            sequentialCheckBox.setSelected(useSequential);
            counterSpinner.setValue(sequentialCounter);
            counterSpinner.setEnabled(useSequential);
        }
    }
}

// ==================== DATE/TIME CODER ====================

/**
 * Date/Time Coder - System date/time in various formats
 */
class DateTimeCoder extends ThorX6CoderTypeSystem.BaseCoderType {
    private String[] formats = {"YYMMDD", "YYYYMMDD", "Julian Date", "HHMM", "HHMMSS", "YYYY-MM-DD HH:MM"};
    private int selectedFormat = 0;
    
    private JComboBox<String> formatComboBox;
    
    public DateTimeCoder() {
        super("Date/Time");
    }
    
    @Override
    public String generateCode() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        
        switch (selectedFormat) {
            case 0: // YYMMDD
                return new SimpleDateFormat("yyMMdd").format(now);
            case 1: // YYYYMMDD
                return new SimpleDateFormat("yyyyMMdd").format(now);
            case 2: // Julian Date
                int julian = cal.get(Calendar.DAY_OF_YEAR);
                return String.format("%02d%03d", cal.get(Calendar.YEAR) % 100, julian);
            case 3: // HHMM
                return new SimpleDateFormat("HHmm").format(now);
            case 4: // HHMMSS
                return new SimpleDateFormat("HHmmss").format(now);
            case 5: // Full format
                return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now);
            default:
                return new SimpleDateFormat("yyMMdd").format(now);
        }
    }
    
    @Override
    public String getPreview() {
        return generateCode() + " (Live " + formats[selectedFormat] + ")";
    }
    
    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    
    @Override
    public JPanel getConfigurationPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
            
            formatComboBox = new JComboBox<>(formats);
            formatComboBox.setSelectedIndex(selectedFormat);
            formatComboBox.addActionListener(e -> {
                selectedFormat = formatComboBox.getSelectedIndex();
                notifyConfigurationChanged();
            });
            
            JPanel formatPanel = createLabeledComponent("Date/Time Format", formatComboBox);
            configPanel.add(createTitledSection("Format Configuration", formatPanel));
            
            // Preview examples
            JTextArea exampleArea = new JTextArea();
            exampleArea.setEditable(false);
            exampleArea.setBackground(configPanel.getBackground());
            updateExamples(exampleArea);
            
            configPanel.add(createTitledSection("Format Examples", exampleArea));
        }
        return configPanel;
    }
    
    private void updateExamples(JTextArea area) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        
        StringBuilder examples = new StringBuilder();
        examples.append("YYMMDD: ").append(new SimpleDateFormat("yyMMdd").format(now)).append("\n");
        examples.append("YYYYMMDD: ").append(new SimpleDateFormat("yyyyMMdd").format(now)).append("\n");
        examples.append("Julian Date: ").append(String.format("%02d%03d", cal.get(Calendar.YEAR) % 100, cal.get(Calendar.DAY_OF_YEAR))).append("\n");
        examples.append("HHMM: ").append(new SimpleDateFormat("HHmm").format(now)).append("\n");
        examples.append("HHMMSS: ").append(new SimpleDateFormat("HHmmss").format(now)).append("\n");
        examples.append("Full: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now));
        
        area.setText(examples.toString());
    }
    
    @Override
    public Map<String, Object> getConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put("selectedFormat", selectedFormat);
        return config;
    }
    
    @Override
    public void setConfiguration(Map<String, Object> config) {
        if (config.containsKey("selectedFormat")) {
            selectedFormat = (Integer) config.get("selectedFormat");
            if (formatComboBox != null) {
                formatComboBox.setSelectedIndex(selectedFormat);
            }
        }
    }
}

// ==================== RANDOM NUMBER CODER ====================

/**
 * Random Number Coder - Generates random numeric codes with configurable range
 */
class RandomNumberCoder extends ThorX6CoderTypeSystem.BaseCoderType {
    private int minValue = 1000;
    private int maxValue = 9999;
    private int length = 4;
    private boolean fixedLength = true;
    private Random random = new Random();
    
    private JSpinner minSpinner;
    private JSpinner maxSpinner;
    private JSpinner lengthSpinner;
    private JCheckBox fixedLengthCheckBox;
    private JRadioButton rangeMode;
    private JRadioButton lengthMode;
    
    public RandomNumberCoder() {
        super("Random Number");
    }
    
    @Override
    public String generateCode() {
        if (fixedLength) {
            // Generate random number with fixed length
            int min = (int) Math.pow(10, length - 1);
            int max = (int) Math.pow(10, length) - 1;
            int randomNum = random.nextInt(max - min + 1) + min;
            return String.format("%0" + length + "d", randomNum);
        } else {
            // Generate random number in range
            int randomNum = random.nextInt(maxValue - minValue + 1) + minValue;
            return String.valueOf(randomNum);
        }
    }
    
    @Override
    public String getPreview() {
        if (fixedLength) {
            return generateCode() + " (" + length + " digits)";
        } else {
            return generateCode() + " (" + minValue + "-" + maxValue + ")";
        }
    }
    
    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    
    @Override
    public JPanel getConfigurationPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
            
            // Mode selection
            rangeMode = new JRadioButton("Range Mode (Min-Max)", !fixedLength);
            lengthMode = new JRadioButton("Fixed Length Mode", fixedLength);
            ButtonGroup modeGroup = new ButtonGroup();
            modeGroup.add(rangeMode);
            modeGroup.add(lengthMode);
            
            // Controls
            minSpinner = new JSpinner(new SpinnerNumberModel(minValue, 0, 999999, 1));
            maxSpinner = new JSpinner(new SpinnerNumberModel(maxValue, 1, 999999, 1));
            lengthSpinner = new JSpinner(new SpinnerNumberModel(length, 1, 10, 1));
            
            // Listeners
            ActionListener modeListener = e -> {
                fixedLength = lengthMode.isSelected();
                updateControlStates();
                notifyConfigurationChanged();
            };
            
            rangeMode.addActionListener(modeListener);
            lengthMode.addActionListener(modeListener);
            
            ChangeListener valueListener = e -> {
                minValue = (Integer) minSpinner.getValue();
                maxValue = (Integer) maxSpinner.getValue();
                length = (Integer) lengthSpinner.getValue();
                notifyConfigurationChanged();
            };
            
            minSpinner.addChangeListener(valueListener);
            maxSpinner.addChangeListener(valueListener);
            lengthSpinner.addChangeListener(valueListener);
            
            // Layout
            JPanel modePanel = new JPanel();
            modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
            modePanel.add(rangeMode);
            modePanel.add(lengthMode);
            
            JPanel rangePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rangePanel.add(new JLabel("Min:"));
            rangePanel.add(minSpinner);
            rangePanel.add(new JLabel("Max:"));
            rangePanel.add(maxSpinner);
            
            JPanel lengthPanel = createLabeledComponent("Length", lengthSpinner);
            
            configPanel.add(createTitledSection("Generation Mode", modePanel));
            configPanel.add(createTitledSection("Range Configuration", rangePanel));
            configPanel.add(createTitledSection("Length Configuration", lengthPanel));
            
            updateControlStates();
        }
        return configPanel;
    }
    
    private void updateControlStates() {
        if (minSpinner != null) {
            minSpinner.setEnabled(!fixedLength);
            maxSpinner.setEnabled(!fixedLength);
            lengthSpinner.setEnabled(fixedLength);
        }
    }
    
    @Override
    public Map<String, Object> getConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put("minValue", minValue);
        config.put("maxValue", maxValue);
        config.put("length", length);
        config.put("fixedLength", fixedLength);
        return config;
    }
    
    @Override
    public void setConfiguration(Map<String, Object> config) {
        if (config.containsKey("minValue")) minValue = (Integer) config.get("minValue");
        if (config.containsKey("maxValue")) maxValue = (Integer) config.get("maxValue");
        if (config.containsKey("length")) length = (Integer) config.get("length");
        if (config.containsKey("fixedLength")) fixedLength = (Boolean) config.get("fixedLength");
        
        if (minSpinner != null) {
            minSpinner.setValue(minValue);
            maxSpinner.setValue(maxValue);
            lengthSpinner.setValue(length);
            rangeMode.setSelected(!fixedLength);
            lengthMode.setSelected(fixedLength);
            updateControlStates();
        }
    }
}