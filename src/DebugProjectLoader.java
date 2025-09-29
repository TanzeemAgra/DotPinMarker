import java.io.*;
import java.util.zip.*;

/**
 * Debug tool to test project file loading
 */
public class DebugProjectLoader {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java DebugProjectLoader <project_file_path>");
            return;
        }
        
        String filePath = args[0];
        File projectFile = new File(filePath);
        
        if (!projectFile.exists()) {
            System.err.println("❌ File not found: " + filePath);
            return;
        }
        
        System.out.println("🔍 Testing project file: " + filePath);
        System.out.println("📏 File size: " + projectFile.length() + " bytes");
        
        // Test file header
        testFileHeader(projectFile);
        
        // Test compressed loading
        testCompressedLoading(projectFile);
    }
    
    private static void testFileHeader(File projectFile) {
        System.out.println("\n=== FILE HEADER ANALYSIS ===");
        try (FileInputStream fis = new FileInputStream(projectFile)) {
            byte[] header = new byte[4];
            int bytesRead = fis.read(header);
            
            if (bytesRead >= 2) {
                boolean isGZIP = (header[0] == (byte)0x1F && header[1] == (byte)0x8B);
                System.out.printf("📋 Header bytes: %02X %02X %02X %02X%n", 
                                header[0], header[1], header[2], header[3]);
                System.out.println("🗜️ GZIP format: " + (isGZIP ? "✅ Yes" : "❌ No"));
            }
        } catch (Exception e) {
            System.err.println("❌ Error reading header: " + e.getMessage());
        }
    }
    
    private static void testCompressedLoading(File projectFile) {
        System.out.println("\n=== COMPRESSED LOADING TEST ===");
        try (FileInputStream fis = new FileInputStream(projectFile);
             GZIPInputStream gzis = new GZIPInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(gzis)) {
            
            System.out.println("✅ GZIP stream opened successfully");
            System.out.println("✅ ObjectInputStream created successfully");
            
            Object obj = ois.readObject();
            System.out.println("✅ Object read successfully: " + obj.getClass().getSimpleName());
            
            if (obj.toString() != null) {
                System.out.println("📋 Object content preview: " + obj.toString().substring(0, Math.min(100, obj.toString().length())));
            }
            
        } catch (Exception e) {
            System.err.println("❌ Compressed loading failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}