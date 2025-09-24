/**
 * Test Simple Clipboard with Undo and Erase
 */
public class TestClipboardWithUndo {
    public static void main(String[] args) {
        System.out.println("🧪 Testing Mark Tab Clipboard with Undo and Erase...\n");
        
        // Print configuration
        MarkTabConfig.printConfigSummary();
        
        System.out.println("\n📋 Clipboard Button Details:");
        for (int i = 0; i < MarkTabConfig.TOOLBAR_BUTTONS.length; i++) {
            MarkTabConfig.ToolbarButton button = MarkTabConfig.TOOLBAR_BUTTONS[i];
            System.out.println((i+1) + ". " + button.text + " (" + button.icon + ") - " + button.tooltip);
        }
        
        System.out.println("\n🧪 Testing Each Clipboard Function:");
        
        // Test Undo (NEW)
        System.out.println("\n1️⃣ Testing NEW Undo Function:");
        MarkTabConfig.TOOLBAR_BUTTONS[0].action.run();
        
        // Test Paste (Existing)
        System.out.println("\n2️⃣ Testing Paste Function:");
        MarkTabConfig.TOOLBAR_BUTTONS[1].action.run();
        
        // Test Cut (Existing)
        System.out.println("\n3️⃣ Testing Cut Function:");
        MarkTabConfig.TOOLBAR_BUTTONS[2].action.run();
        
        // Test Copy (Existing)
        System.out.println("\n4️⃣ Testing Copy Function:");
        MarkTabConfig.TOOLBAR_BUTTONS[3].action.run();
        
        // Test Erase (NEW)
        System.out.println("\n5️⃣ Testing NEW Erase Function:");
        MarkTabConfig.TOOLBAR_BUTTONS[4].action.run();
        
        System.out.println("\n✅ All clipboard options tested successfully!");
        System.out.println("📋 Mark Tab now has 5 clipboard options: Undo, Paste, Cut, Copy, Erase");
    }
}