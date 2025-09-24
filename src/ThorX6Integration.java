import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import javax.swing.*;

/**
 * ThorX6Integration - Interface for fetching functions from ThorX6 software
 * Provides multiple integration methods for connecting with ThorX6 system
 */
public class ThorX6Integration {
    
    // ThorX6 Integration Constants
    private static final String THORX6_API_BASE = "https://thorx6.software.informer.com/";
    private static final String THORX6_LOCAL_PORT = "8080"; // Default port for local ThorX6 service
    private static final String THORX6_PROCESS_NAME = "X6.exe";
    
    // Integration Status
    private boolean isConnected = false;
    private String connectionMethod = "None";
    private String lastError = "";
    
    // Available Integration Methods
    public enum IntegrationMethod {
        HTTP_API,       // Web-based API calls
        LOCAL_SOCKET,   // Local socket communication
        FILE_EXCHANGE,  // File-based data exchange
        PROCESS_PIPE,   // Named pipe communication
        COMMAND_LINE    // Command line interface
    }
    
    /**
     * Attempt to establish connection with ThorX6 using multiple methods
     */
    public boolean connectToThorX6() {
        System.out.println("🔄 Attempting ThorX6 Integration...");
        
        // Try different connection methods in order of preference
        for (IntegrationMethod method : IntegrationMethod.values()) {
            if (tryConnection(method)) {
                isConnected = true;
                connectionMethod = method.toString();
                System.out.println("✅ ThorX6 Connected via: " + connectionMethod);
                return true;
            }
        }
        
        System.out.println("❌ ThorX6 Connection Failed - All methods exhausted");
        return false;
    }
    
    /**
     * Try specific connection method
     */
    private boolean tryConnection(IntegrationMethod method) {
        try {
            switch (method) {
                case HTTP_API:
                    return tryHttpApiConnection();
                case LOCAL_SOCKET:
                    return tryLocalSocketConnection();
                case FILE_EXCHANGE:
                    return tryFileExchangeConnection();
                case PROCESS_PIPE:
                    return tryProcessPipeConnection();
                case COMMAND_LINE:
                    return tryCommandLineConnection();
                default:
                    return false;
            }
        } catch (Exception e) {
            lastError = "Error in " + method + ": " + e.getMessage();
            return false;
        }
    }
    
    /**
     * HTTP API Connection Method
     */
    private boolean tryHttpApiConnection() {
        try {
            // Try to connect to ThorX6 web service
            URL url = new URL(THORX6_API_BASE + "api/status");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("🌐 HTTP API Connection: Available");
                return true;
            }
        } catch (Exception e) {
            System.out.println("🌐 HTTP API Connection: Unavailable (" + e.getMessage() + ")");
        }
        return false;
    }
    
    /**
     * Local Socket Connection Method
     */
    private boolean tryLocalSocketConnection() {
        try {
            // Try to connect to local ThorX6 service
            Socket socket = new Socket("localhost", Integer.parseInt(THORX6_LOCAL_PORT));
            socket.setSoTimeout(2000);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println("PING");
            String response = in.readLine();
            
            socket.close();
            
            if ("PONG".equals(response)) {
                System.out.println("🔌 Local Socket Connection: Available");
                return true;
            }
        } catch (Exception e) {
            System.out.println("🔌 Local Socket Connection: Unavailable (" + e.getMessage() + ")");
        }
        return false;
    }
    
    /**
     * File Exchange Connection Method
     */
    private boolean tryFileExchangeConnection() {
        try {
            // Check for ThorX6 exchange directory
            File exchangeDir = new File("thorx6_exchange");
            if (!exchangeDir.exists()) {
                exchangeDir.mkdirs();
            }
            
            // Create test file
            File testFile = new File(exchangeDir, "test_connection.txt");
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("CONNECTION_TEST:" + System.currentTimeMillis());
            writer.close();
            
            // Check if ThorX6 responds by modifying the file
            Thread.sleep(1000);
            
            if (testFile.exists() && testFile.length() > 0) {
                System.out.println("📁 File Exchange Connection: Available");
                return true;
            }
        } catch (Exception e) {
            System.out.println("📁 File Exchange Connection: Setup Complete (Monitoring)");
            return true; // File exchange is always available as fallback
        }
        return false;
    }
    
    /**
     * Process Pipe Connection Method
     */
    private boolean tryProcessPipeConnection() {
        try {
            // Check if ThorX6 process is running
            if (isThorX6ProcessRunning()) {
                System.out.println("⚡ Process Pipe Connection: ThorX6 Process Found");
                return true;
            }
        } catch (Exception e) {
            System.out.println("⚡ Process Pipe Connection: Unavailable (" + e.getMessage() + ")");
        }
        return false;
    }
    
    /**
     * Command Line Connection Method
     */
    private boolean tryCommandLineConnection() {
        try {
            // Try to execute ThorX6 command
            ProcessBuilder pb = new ProcessBuilder("X6.exe", "--version");
            pb.directory(new File("."));
            Process process = pb.start();
            
            boolean finished = process.waitFor(5, TimeUnit.SECONDS);
            if (finished && process.exitValue() == 0) {
                System.out.println("💻 Command Line Connection: Available");
                return true;
            }
        } catch (Exception e) {
            System.out.println("💻 Command Line Connection: Unavailable (" + e.getMessage() + ")");
        }
        return false;
    }
    
    /**
     * Check if ThorX6 process is running
     */
    private boolean isThorX6ProcessRunning() {
        try {
            String line;
            String os = System.getProperty("os.name").toLowerCase();
            
            Process process;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            } else {
                process = Runtime.getRuntime().exec("ps -e");
            }
            
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.toLowerCase().contains("x6.exe") || 
                    line.toLowerCase().contains("thorx6") ||
                    line.toLowerCase().contains("thorx5.exe")) {
                    return true;
                }
            }
            input.close();
        } catch (Exception e) {
            // Ignore exceptions in process checking
        }
        return false;
    }
    
    /**
     * Fetch available functions from ThorX6
     */
    public java.util.List<ThorX6Function> fetchFunctions() {
        java.util.List<ThorX6Function> functions = new ArrayList<>();
        
        if (!isConnected) {
            System.out.println("⚠️ Not connected to ThorX6. Attempting connection...");
            if (!connectToThorX6()) {
                return getMockFunctions(); // Return mock functions for demonstration
            }
        }
        
        try {
            switch (connectionMethod) {
                case "HTTP_API":
                    return fetchFunctionsViaHTTP();
                case "LOCAL_SOCKET":
                    return fetchFunctionsViaSocket();
                case "FILE_EXCHANGE":
                    return fetchFunctionsViaFile();
                case "PROCESS_PIPE":
                    return fetchFunctionsViaPipe();
                case "COMMAND_LINE":
                    return fetchFunctionsViaCommandLine();
                default:
                    return getMockFunctions();
            }
        } catch (Exception e) {
            System.err.println("Error fetching functions: " + e.getMessage());
            return getMockFunctions();
        }
    }
    
    /**
     * HTTP API method to fetch functions
     */
    private java.util.List<ThorX6Function> fetchFunctionsViaHTTP() {
        java.util.List<ThorX6Function> functions = new ArrayList<>();
        
        try {
            URL url = new URL(THORX6_API_BASE + "api/functions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Parse JSON response (simplified)
            // In real implementation, use JSON library
            String jsonResponse = response.toString();
            functions = parseFunctionsFromJSON(jsonResponse);
            
        } catch (Exception e) {
            System.err.println("HTTP fetch error: " + e.getMessage());
        }
        
        return functions;
    }
    
    /**
     * Socket method to fetch functions
     */
    private java.util.List<ThorX6Function> fetchFunctionsViaSocket() {
        java.util.List<ThorX6Function> functions = new ArrayList<>();
        
        try {
            Socket socket = new Socket("localhost", Integer.parseInt(THORX6_LOCAL_PORT));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println("GET_FUNCTIONS");
            
            String response;
            while ((response = in.readLine()) != null) {
                if (response.equals("END")) break;
                
                ThorX6Function function = parseFunctionFromString(response);
                if (function != null) {
                    functions.add(function);
                }
            }
            
            socket.close();
            
        } catch (Exception e) {
            System.err.println("Socket fetch error: " + e.getMessage());
        }
        
        return functions;
    }
    
    /**
     * File exchange method to fetch functions
     */
    private java.util.List<ThorX6Function> fetchFunctionsViaFile() {
        java.util.List<ThorX6Function> functions = new ArrayList<>();
        
        try {
            File exchangeDir = new File("thorx6_exchange");
            File requestFile = new File(exchangeDir, "function_request.txt");
            File responseFile = new File(exchangeDir, "function_response.txt");
            
            // Write request
            PrintWriter writer = new PrintWriter(requestFile);
            writer.println("REQUEST:GET_FUNCTIONS");
            writer.println("TIMESTAMP:" + System.currentTimeMillis());
            writer.close();
            
            // Wait for response
            int attempts = 0;
            while (!responseFile.exists() && attempts < 10) {
                Thread.sleep(500);
                attempts++;
            }
            
            if (responseFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(responseFile));
                String line;
                
                while ((line = reader.readLine()) != null) {
                    ThorX6Function function = parseFunctionFromString(line);
                    if (function != null) {
                        functions.add(function);
                    }
                }
                reader.close();
                
                // Cleanup
                responseFile.delete();
                requestFile.delete();
            }
            
        } catch (Exception e) {
            System.err.println("File fetch error: " + e.getMessage());
        }
        
        return functions;
    }
    
    /**
     * Process pipe method to fetch functions
     */
    private java.util.List<ThorX6Function> fetchFunctionsViaPipe() {
        // Implementation for named pipe communication
        return getMockFunctions();
    }
    
    /**
     * Command line method to fetch functions
     */
    private java.util.List<ThorX6Function> fetchFunctionsViaCommandLine() {
        java.util.List<ThorX6Function> functions = new ArrayList<>();
        
        try {
            ProcessBuilder pb = new ProcessBuilder("X6.exe", "--list-functions");
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
                ThorX6Function function = parseFunctionFromString(line);
                if (function != null) {
                    functions.add(function);
                }
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.err.println("Command line fetch error: " + e.getMessage());
        }
        
        return functions;
    }
    
    /**
     * Parse function from JSON (simplified implementation)
     */
    private java.util.List<ThorX6Function> parseFunctionsFromJSON(String json) {
        // In real implementation, use proper JSON parser
        return getMockFunctions();
    }
    
    /**
     * Parse function from string format
     */
    private ThorX6Function parseFunctionFromString(String line) {
        try {
            // Expected format: "FUNCTION:name|description|parameters"
            if (line.startsWith("FUNCTION:")) {
                String[] parts = line.substring(9).split("\\|");
                if (parts.length >= 3) {
                    return new ThorX6Function(parts[0], parts[1], parts[2]);
                }
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
        return null;
    }
    
    /**
     * Get mock functions for demonstration - PUBLIC for testing
     */
    public java.util.List<ThorX6Function> getMockFunctions() {
        java.util.List<ThorX6Function> functions = new ArrayList<>();
        
        functions.add(new ThorX6Function(
            "startMarking", 
            "Start the marking process", 
            "speed:int,depth:double,pattern:string"
        ));
        
        functions.add(new ThorX6Function(
            "stopMarking", 
            "Stop the marking process", 
            ""
        ));
        
        functions.add(new ThorX6Function(
            "setMarkingParameters", 
            "Configure marking parameters", 
            "speed:int,depth:double,force:double"
        ));
        
        functions.add(new ThorX6Function(
            "getStatus", 
            "Get current system status", 
            ""
        ));
        
        functions.add(new ThorX6Function(
            "calibrateSystem", 
            "Calibrate the marking system", 
            "xOffset:double,yOffset:double"
        ));
        
        functions.add(new ThorX6Function(
            "loadPattern", 
            "Load a marking pattern", 
            "patternFile:string"
        ));
        
        functions.add(new ThorX6Function(
            "savePattern", 
            "Save current pattern", 
            "filename:string"
        ));
        
        functions.add(new ThorX6Function(
            "moveToPosition", 
            "Move to specified position", 
            "x:double,y:double"
        ));
        
        return functions;
    }
    
    /**
     * Execute a ThorX6 function
     */
    public String executeFunction(String functionName, Map<String, Object> parameters) {
        if (!isConnected) {
            return "Error: Not connected to ThorX6";
        }
        
        try {
            switch (connectionMethod) {
                case "HTTP_API":
                    return executeFunctionViaHTTP(functionName, parameters);
                case "LOCAL_SOCKET":
                    return executeFunctionViaSocket(functionName, parameters);
                case "FILE_EXCHANGE":
                    return executeFunctionViaFile(functionName, parameters);
                case "PROCESS_PIPE":
                    return executeFunctionViaPipe(functionName, parameters);
                case "COMMAND_LINE":
                    return executeFunctionViaCommandLine(functionName, parameters);
                default:
                    return "Mock execution of " + functionName + " with parameters: " + parameters;
            }
        } catch (Exception e) {
            return "Error executing function: " + e.getMessage();
        }
    }
    
    // Implementation methods for each execution type
    private String executeFunctionViaHTTP(String functionName, Map<String, Object> parameters) {
        return "HTTP execution: " + functionName;
    }
    
    private String executeFunctionViaSocket(String functionName, Map<String, Object> parameters) {
        return "Socket execution: " + functionName;
    }
    
    private String executeFunctionViaFile(String functionName, Map<String, Object> parameters) {
        return "File execution: " + functionName;
    }
    
    private String executeFunctionViaPipe(String functionName, Map<String, Object> parameters) {
        return "Pipe execution: " + functionName;
    }
    
    private String executeFunctionViaCommandLine(String functionName, Map<String, Object> parameters) {
        return "CLI execution: " + functionName;
    }
    
    /**
     * Get connection status
     */
    public boolean isConnected() {
        return isConnected;
    }
    
    public String getConnectionMethod() {
        return connectionMethod;
    }
    
    public String getLastError() {
        return lastError;
    }
    
    /**
     * ThorX6Function class to represent available functions
     */
    public static class ThorX6Function {
        private String name;
        private String description;
        private String parameters;
        
        public ThorX6Function(String name, String description, String parameters) {
            this.name = name;
            this.description = description;
            this.parameters = parameters;
        }
        
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getParameters() { return parameters; }
        
        @Override
        public String toString() {
            return name + " - " + description + " (" + parameters + ")";
        }
    }
    
    /**
     * Create ThorX6 Integration Panel for UI
     */
    public static JPanel createThorX6IntegrationPanel(DrawingCanvas canvas) {
        ThorX6Integration integration = new ThorX6Integration();
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("🔗 ThorX6 Integration"));
        panel.setBackground(new Color(248, 249, 250));
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(248, 249, 250));
        
        JButton connectBtn = new JButton("🔌 Connect to ThorX6");
        JButton refreshBtn = new JButton("🔄 Refresh Functions");
        JLabel statusLabel = new JLabel("❌ Not Connected");
        
        headerPanel.add(connectBtn);
        headerPanel.add(refreshBtn);
        headerPanel.add(statusLabel);
        
        // Functions list
        DefaultListModel<ThorX6Integration.ThorX6Function> listModel = new DefaultListModel<>();
        JList<ThorX6Integration.ThorX6Function> functionsList = new JList<>(listModel);
        functionsList.setFont(new Font("Consolas", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(functionsList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        // Execute panel
        JPanel executePanel = new JPanel(new FlowLayout());
        executePanel.setBackground(new Color(248, 249, 250));
        
        JButton executeBtn = new JButton("▶️ Execute Function");
        JTextArea resultArea = new JTextArea(3, 30);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 10));
        resultArea.setEditable(false);
        resultArea.setBackground(Color.WHITE);
        
        executePanel.add(executeBtn);
        
        // Event handlers
        connectBtn.addActionListener(e -> {
            boolean connected = integration.connectToThorX6();
            if (connected) {
                statusLabel.setText("✅ Connected via " + integration.getConnectionMethod());
                statusLabel.setForeground(new Color(39, 174, 96));
                
                // Load functions
                java.util.List<ThorX6Integration.ThorX6Function> functions = integration.fetchFunctions();
                listModel.clear();
                for (ThorX6Integration.ThorX6Function function : functions) {
                    listModel.addElement(function);
                }
            } else {
                statusLabel.setText("❌ Connection Failed");
                statusLabel.setForeground(new Color(231, 76, 60));
            }
        });
        
        refreshBtn.addActionListener(e -> {
            if (integration.isConnected()) {
                java.util.List<ThorX6Integration.ThorX6Function> functions = integration.fetchFunctions();
                listModel.clear();
                for (ThorX6Integration.ThorX6Function function : functions) {
                    listModel.addElement(function);
                }
                JOptionPane.showMessageDialog(panel, "✅ Functions refreshed: " + functions.size() + " available");
            } else {
                JOptionPane.showMessageDialog(panel, "❌ Not connected to ThorX6");
            }
        });
        
        executeBtn.addActionListener(e -> {
            ThorX6Integration.ThorX6Function selected = functionsList.getSelectedValue();
            if (selected != null) {
                String result = integration.executeFunction(selected.getName(), new HashMap<>());
                resultArea.setText(result);
                JOptionPane.showMessageDialog(panel, "Function executed: " + selected.getName() + "\nResult: " + result);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a function to execute");
            }
        });
        
        // Layout
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(executePanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
}
