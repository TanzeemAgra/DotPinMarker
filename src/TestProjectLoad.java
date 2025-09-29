/**
 * Test the fixed ProjectStateManager loading
 */
public class TestProjectLoad {
    
    public static void main(String[] args) {
        System.out.println("🧪 Testing ProjectStateManager.loadProjectFromFile()...");
        
        String filePath = "projects\\Saved_project.rugrel";
        
        try {
            ProjectState state = ProjectStateManager.loadProjectFromFile(filePath);
            
            if (state != null) {
                System.out.println("✅ SUCCESS: Loaded project '" + state.projectName + "'");
                System.out.println("   Project ID: " + state.projectId);
                System.out.println("   Description: " + state.description);
                System.out.println("   Marks: " + (state.marks != null ? state.marks.size() : 0));
                System.out.println("   Zoom: " + state.zoomLevel);
            } else {
                System.out.println("❌ FAILED: ProjectStateManager returned null");
            }
            
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}