import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoderPanel extends JPanel {
    private static final Color BUTTON_COLOR = new Color(40, 167, 69);
    private static final Color HOVER_COLOR = new Color(34, 139, 58);
    private static final Color BORDER_COLOR = new Color(28, 115, 48);
    
    private DrawingCanvas canvas;
    private DefaultTableModel tableModel;
    private JTable coderTable;
    private JTextField typeField;
    private JTextField coderField;
    
    public CoderPanel(DrawingCanvas canvas) {
        this.canvas = canvas;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Coder Management"));
        
        initializeComponents();
        loadCoderData();
    }
    
    private void initializeComponents() {
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Create top control panel
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Create table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Create action buttons panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        
        // Coder Type input
        panel.add(new JLabel("Coder Type:"));
        typeField = new JTextField(15);
        typeField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(typeField);
        
        // Coder input
        panel.add(new JLabel("Coder:"));
        coderField = new JTextField(20);
        coderField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(coderField);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Available Coders"));
        
        // Create table model
        String[] columnNames = {"Coder Type", "Coder"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Create table
        coderTable = new JTable(tableModel);
        coderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coderTable.setRowHeight(25);
        coderTable.getTableHeader().setBackground(BUTTON_COLOR);
        coderTable.getTableHeader().setForeground(Color.WHITE);
        coderTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Add table selection listener
        coderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = coderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    typeField.setText((String) tableModel.getValueAt(selectedRow, 0));
                    coderField.setText((String) tableModel.getValueAt(selectedRow, 1));
                }
            }
        });
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(coderTable);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE);
        
        // Add button
        JButton addButton = createActionButton("➕ Add", "Add new coder entry");
        addButton.addActionListener(e -> addCoder());
        panel.add(addButton);
        
        // Update button
        JButton updateButton = createActionButton("📝 Update", "Update selected coder");
        updateButton.addActionListener(e -> updateCoder());
        panel.add(updateButton);
        
        // Delete button
        JButton deleteButton = createActionButton("🗑️ Delete", "Delete selected coder");
        deleteButton.addActionListener(e -> deleteCoder());
        panel.add(deleteButton);
        
        // Use in Mark button
        JButton useButton = createActionButton("⚡ Use in Mark", "Add coder to canvas");
        useButton.addActionListener(e -> useCoderInMark());
        panel.add(useButton);
        
        // Export button
        JButton exportButton = createActionButton("📤 Export", "Export coder list");
        exportButton.addActionListener(e -> exportCoders());
        panel.add(exportButton);
        
        return panel;
    }
    
    private JButton createActionButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setToolTipText(tooltip);
        button.setFont(new Font("Segoe UI", Font.BOLD, 10));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    private void addCoder() {
        String type = typeField.getText().trim();
        String coder = coderField.getText().trim();
        
        if (!type.isEmpty() && !coder.isEmpty()) {
            tableModel.addRow(new Object[]{type, coder});
            clearFields();
            saveCoderData();
            JOptionPane.showMessageDialog(this, "Coder added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in both Coder Type and Coder fields.");
        }
    }
    
    private void updateCoder() {
        int selectedRow = coderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String type = typeField.getText().trim();
            String coder = coderField.getText().trim();
            
            if (!type.isEmpty() && !coder.isEmpty()) {
                tableModel.setValueAt(type, selectedRow, 0);
                tableModel.setValueAt(coder, selectedRow, 1);
                clearFields();
                saveCoderData();
                JOptionPane.showMessageDialog(this, "Coder updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both Coder Type and Coder fields.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a coder to update.");
        }
    }
    
    private void deleteCoder() {
        int selectedRow = coderTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this coder?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                clearFields();
                saveCoderData();
                JOptionPane.showMessageDialog(this, "Coder deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a coder to delete.");
        }
    }
    
    private void useCoderInMark() {
        String type = typeField.getText().trim();
        String coder = coderField.getText().trim();
        
        if (!type.isEmpty() && !coder.isEmpty()) {
            String markText = type + ": " + coder;
            canvas.addMark("Text", markText, 20, 150, "Arial");
            JOptionPane.showMessageDialog(this, "Coder added to canvas as text mark!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select or enter a coder to use.");
        }
    }
    
    private void exportCoders() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Coders");
        fileChooser.setSelectedFile(new File("coders_export.csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                writer.println("Coder Type,Coder");
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String type = (String) tableModel.getValueAt(i, 0);
                    String coder = (String) tableModel.getValueAt(i, 1);
                    writer.println(type + "," + coder);
                }
                JOptionPane.showMessageDialog(this, "Coders exported successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting coders: " + e.getMessage());
            }
        }
    }
    
    private void clearFields() {
        typeField.setText("");
        coderField.setText("");
        coderTable.clearSelection();
    }
    
    private void loadCoderData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database/coders.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    tableModel.addRow(new Object[]{parts[0].trim(), parts[1].trim()});
                }
            }
        } catch (IOException e) {
            // File doesn't exist or can't be read, start with empty table
            System.out.println("Could not load coder data: " + e.getMessage());
        }
    }
    
    private void saveCoderData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("database/coders.dat"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String type = (String) tableModel.getValueAt(i, 0);
                String coder = (String) tableModel.getValueAt(i, 1);
                writer.println(type + "," + coder);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving coder data: " + e.getMessage());
        }
    }
}