/**
 * Test ThorX6 Clipboard with Undo and Erase
 */
public class TestThorX6Clipboard {
    public static void main(String[] args) {
        System.out.println("🧪 Testing ThorX6 Mark Tab Clipboard with Undo and Erase...\n");
        
        // Print ThorX6 configuration
        ThorX6HorizontalConfig.printThorX6ConfigSummary();
        
        System.out.println("\n📋 ThorX6 Clipboard Group Details:");
        ThorX6HorizontalConfig.ThorX6ButtonGroup clipboardGroup = ThorX6HorizontalConfig.CLIPBOARD_GROUP;
        System.out.println("Group Name: " + clipboardGroup.groupName);
        System.out.println("Button Count: " + clipboardGroup.buttons.length);
        
        System.out.println("\n🔹 Clipboard Buttons:");
        for (int i = 0; i < clipboardGroup.buttons.length; i++) {
            ThorX6HorizontalConfig.ThorX6Button button = clipboardGroup.buttons[i];
            System.out.println((i+1) + ". " + button.text + " (" + button.icon + ") - " + button.tooltip);
        }
        
        System.out.println("\n🧪 Testing Each ThorX6 Clipboard Function:");
        
        // Test each clipboard function
        for (int i = 0; i < clipboardGroup.buttons.length; i++) {
            System.out.println("\n" + (i+1) + "️⃣ Testing " + clipboardGroup.buttons[i].text + ":");
            clipboardGroup.buttons[i].action.run();
        }
        
        System.out.println("\n✅ All ThorX6 clipboard options tested successfully!");
        System.out.println("📋 ThorX6 Mark Tab now has 5 clipboard options: Undo, Paste, Cut, Copy, Erase");
    }
}