import java.io.*;
import java.util.zip.*;

/**
 * Advanced debug tool to analyze project file content
 */
public class AdvancedProjectDebug {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java AdvancedProjectDebug <project_file_path>");
            return;
        }
        
        String filePath = args[0];
        File projectFile = new File(filePath);
        
        System.out.println("🔍 Advanced analysis of: " + filePath);
        
        // Decompress and show raw content
        decompressAndShowContent(projectFile);
    }
    
    private static void decompressAndShowContent(File projectFile) {
        System.out.println("\n=== DECOMPRESSED CONTENT ANALYSIS ===");
        
        try (FileInputStream fis = new FileInputStream(projectFile);
             GZIPInputStream gzis = new GZIPInputStream(fis)) {
            
            byte[] buffer = new byte[1024];
            StringBuilder content = new StringBuilder();
            int bytesRead;
            
            while ((bytesRead = gzis.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead, "UTF-8"));
            }
            
            String decompressed = content.toString();
            System.out.println("📏 Decompressed size: " + decompressed.length() + " chars");
            System.out.println("🔤 Content type detection:");
            
            if (decompressed.startsWith("{") || decompressed.startsWith("[")) {
                System.out.println("   📋 Appears to be JSON format");
            } else if (decompressed.startsWith("aced0005")) {
                System.out.println("   📋 Appears to be Java serialized object (hex)");
            } else if (decompressed.getBytes()[0] == (byte)0xAC && decompressed.getBytes()[1] == (byte)0xED) {
                System.out.println("   📋 Appears to be Java serialized object (binary)");
            } else {
                System.out.println("   📋 Unknown format");
            }
            
            System.out.println("\n📋 First 200 characters of decompressed content:");
            System.out.println("----------------------------------------");
            System.out.println(decompressed.substring(0, Math.min(200, decompressed.length())));
            System.out.println("----------------------------------------");
            
            if (decompressed.length() > 200) {
                System.out.println("\n📋 Last 100 characters:");
                System.out.println("----------------------------------------");
                System.out.println(decompressed.substring(Math.max(0, decompressed.length() - 100)));
                System.out.println("----------------------------------------");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error decompressing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}