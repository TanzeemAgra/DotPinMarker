import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestDropdownInteractive {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create main test window
            JFrame testFrame = new JFrame("Mark Type Dropdown Test");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(600, 400);
            testFrame.setLayout(new BorderLayout());
            
            // Create test panel
            JPanel testPanel = new JPanel();
            testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
            testPanel.setBorder(BorderFactory.createTitledBorder("Mark Type Replacement Test"));
            
            // Add test results display
            JTextArea resultArea = new JTextArea(15, 50);
            resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);
            
            // Test button
            JButton testButton = new JButton("Test Mark Types Grid");
            testButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StringBuilder results = new StringBuilder();
                    results.append("=== MARK TYPES GRID TEST ===\n\n");
                    
                    try {
                        // Access the ThorX6HorizontalConfig class
                        Class<?> configClass = Class.forName("ThorX6HorizontalConfig");
                        java.lang.reflect.Field gridField = configClass.getDeclaredField("MARK_TYPES_GRID");
                        gridField.setAccessible(true);
                        
                        // Get the grid
                        Object[][] grid = (Object[][]) gridField.get(null);
                        
                        results.append("Grid dimensions: ").append(grid.length).append(" rows x ").append(grid[0].length).append(" columns\n\n");
                        
                        // Test each position
                        for (int row = 0; row < grid.length; row++) {
                            for (int col = 0; col < grid[row].length; col++) {
                                Object mark = grid[row][col];
                                results.append("Position [").append(row).append("][").append(col).append("]: ");
                                
                                if (mark == null) {
                                    results.append("NULL (deleted mark)\n");
                                } else {
                                    // Get mark details using reflection
                                    Class<?> markClass = mark.getClass();
                                    java.lang.reflect.Field nameField = markClass.getDeclaredField("name");
                                    nameField.setAccessible(true);
                                    String name = (String) nameField.get(mark);
                                    
                                    java.lang.reflect.Field tooltipField = markClass.getDeclaredField("tooltip");
                                    tooltipField.setAccessible(true);
                                    String tooltip = (String) tooltipField.get(mark);
                                    
                                    results.append("✓ ").append(name).append(" (").append(tooltip).append(")\n");
                                }
                            }
                        }
                        
                        // Check specific replacements
                        results.append("\n=== REPLACEMENT VERIFICATION ===\n");
                        String[] expectedMarks = {"DataMatrix", "Graph", "Chart", "Ruler", "AvoidPoint", "Farsi"};
                        int foundCount = 0;
                        
                        for (int row = 0; row < grid.length; row++) {
                            for (int col = 0; col < grid[row].length; col++) {
                                Object mark = grid[row][col];
                                if (mark != null) {
                                    Class<?> markClass = mark.getClass();
                                    java.lang.reflect.Field nameField = markClass.getDeclaredField("name");
                                    nameField.setAccessible(true);
                                    String name = (String) nameField.get(mark);
                                    
                                    for (String expected : expectedMarks) {
                                        if (name.equals(expected)) {
                                            results.append("✓ Found: ").append(expected).append("\n");
                                            foundCount++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        
                        results.append("\nReplacement Summary: ").append(foundCount).append("/").append(expectedMarks.length).append(" new marks found\n");
                        
                        // Check deleted marks
                        results.append("\n=== DELETION VERIFICATION ===\n");
                        String[] deletedMarks = {"Farzi", "Line"};
                        boolean foundDeleted = false;
                        
                        for (int row = 0; row < grid.length; row++) {
                            for (int col = 0; col < grid[row].length; col++) {
                                Object mark = grid[row][col];
                                if (mark != null) {
                                    Class<?> markClass = mark.getClass();
                                    java.lang.reflect.Field nameField = markClass.getDeclaredField("name");
                                    nameField.setAccessible(true);
                                    String name = (String) nameField.get(mark);
                                    
                                    for (String deleted : deletedMarks) {
                                        if (name.equals(deleted)) {
                                            results.append("❌ ERROR: Found deleted mark: ").append(deleted).append("\n");
                                            foundDeleted = true;
                                        }
                                    }
                                }
                            }
                        }
                        
                        if (!foundDeleted) {
                            results.append("✓ Confirmed: Farzi and Line marks successfully deleted\n");
                        }
                        
                        results.append("\n=== TEST COMPLETE ===\n");
                        if (foundCount == expectedMarks.length && !foundDeleted) {
                            results.append("🎉 ALL TESTS PASSED! Mark replacement successful.\n");
                        } else {
                            results.append("⚠️ Some issues found. Review results above.\n");
                        }
                        
                    } catch (Exception ex) {
                        results.append("ERROR: ").append(ex.getMessage()).append("\n");
                        ex.printStackTrace();
                    }
                    
                    resultArea.setText(results.toString());
                }
            });
            
            // Layout components
            testPanel.add(Box.createVerticalStrut(10));
            testPanel.add(testButton);
            testPanel.add(Box.createVerticalStrut(10));
            testPanel.add(scrollPane);
            
            testFrame.add(testPanel, BorderLayout.CENTER);
            
            // Add instructions
            JLabel instructions = new JLabel("<html><b>Instructions:</b> Click 'Test Mark Types Grid' to verify all mark replacements are working correctly.</html>");
            instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            testFrame.add(instructions, BorderLayout.NORTH);
            
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
            
            System.out.println("✓ Mark Type Dropdown Test window opened!");
            System.out.println("✓ Click 'Test Mark Types Grid' to run verification tests");
        });
    }
}