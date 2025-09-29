import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Test class to verify the consolidated coder functionality.
 * This test demonstrates that both original ThorX6 coders and new modular coders
 * are available in a single consolidated interface.
 */
public class TestConsolidatedCoders extends JFrame {
    private JComboBox<ThorX6HorizontalConfig.CoderInterface> coderDropdown;
    private JTextArea outputArea;
    private JButton generateButton;
    
    public TestConsolidatedCoders() {
        setTitle("Test Consolidated Coder System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create consolidated coder array using soft coding
        ThorX6HorizontalConfig.CoderInterface[] coders = ThorX6HorizontalConfig.createConsolidatedCoderArray();
        
        // Create UI components
        coderDropdown = new JComboBox<>(coders);
        generateButton = new JButton("Generate Code");
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        // Add generate button listener
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThorX6HorizontalConfig.CoderInterface selectedCoder = (ThorX6HorizontalConfig.CoderInterface) coderDropdown.getSelectedItem();
                if (selectedCoder != null) {
                    String generatedCode = selectedCoder.generateCode();
                    outputArea.append("Coder: " + selectedCoder.getTypeName() + "\n");
                    outputArea.append("Generated: " + generatedCode + "\n");
                    outputArea.append("-----------------------------------\n");
                    outputArea.append("-----------------------------------\n");
                }
            }
        });
        
        // Layout
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Select Coder:"));
        topPanel.add(coderDropdown);
        topPanel.add(generateButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.add(new JLabel("Total Coders Available: " + coders.length));
        statusPanel.add(new JLabel(" | Consolidation: " + (ThorX6HorizontalConfig.CONSOLIDATE_CODER_FUNCTIONALITY ? "ENABLED" : "DISABLED")));
        add(statusPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestConsolidatedCoders().setVisible(true);
            }
        });
    }
}