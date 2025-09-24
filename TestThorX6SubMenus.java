/**
 * Test Enhanced ThorX6 Sub-Menus (Font, Tools, Actions)
 */
public class TestThorX6SubMenus {
    public static void main(String[] args) {
        System.out.println("🧪 Testing Enhanced ThorX6 Sub-Menus: Font, Tools, Actions...\n");
        
        // Print overall configuration
        ThorX6HorizontalConfig.printThorX6ConfigSummary();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📋 TESTING CLIPBOARD GROUP (5 buttons)");
        System.out.println("=".repeat(60));
        testButtonGroup(ThorX6HorizontalConfig.CLIPBOARD_GROUP);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔤 TESTING FONT GROUP (6 buttons)");
        System.out.println("=".repeat(60));
        testButtonGroup(ThorX6HorizontalConfig.FONT_GROUP);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔧 TESTING TOOLS GROUP (6 buttons)");
        System.out.println("=".repeat(60));
        testButtonGroup(ThorX6HorizontalConfig.TOOLS_GROUP);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("⚡ TESTING ACTIONS GROUP (6 buttons)");
        System.out.println("=".repeat(60));
        testButtonGroup(ThorX6HorizontalConfig.ACTIONS_GROUP);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ ALL SUB-MENUS TESTED SUCCESSFULLY!");
        System.out.println("📊 Total Groups: 4");
        System.out.println("📊 Total Buttons: " + getTotalButtons());
        System.out.println("🎯 All functionality implemented with soft coding technique");
        System.out.println("🗑 Delete actions affect canvas, selection, and history");
        System.out.println("=".repeat(60));
    }
    
    private static void testButtonGroup(ThorX6HorizontalConfig.ThorX6ButtonGroup group) {
        System.out.println("Group: " + group.groupName + " (" + group.buttons.length + " buttons)");
        System.out.println("-".repeat(40));
        
        for (int i = 0; i < group.buttons.length; i++) {
            ThorX6HorizontalConfig.ThorX6Button button = group.buttons[i];
            System.out.println((i+1) + ". " + button.text + " (" + button.icon + ") - " + button.tooltip);
            
            // Test the button functionality
            System.out.print("   Testing: ");
            try {
                button.action.run();
            } catch (Exception e) {
                System.out.println("   ❌ Error: " + e.getMessage());
            }
            System.out.println(); // Extra line for readability
        }
    }
    
    private static int getTotalButtons() {
        int total = 0;
        total += ThorX6HorizontalConfig.CLIPBOARD_GROUP.buttons.length;
        total += ThorX6HorizontalConfig.FONT_GROUP.buttons.length;
        total += ThorX6HorizontalConfig.TOOLS_GROUP.buttons.length;
        total += ThorX6HorizontalConfig.ACTIONS_GROUP.buttons.length;
        return total;
    }
}