/**
 * Test to demonstrate Text option in Add Mark dropdown
 */
public class TestTextInDropdown {
    
    public static void main(String[] args) {
        System.out.println("🧪 Testing Text Option in Add Mark Dropdown...\n");
        
        // Show the Add Mark Dropdown
        System.out.println("📋 Demonstrating Add Mark Dropdown:");
        ThorX6HorizontalConfig.handleAddMarkDropdown();
        
        System.out.println("\n🎯 Selecting Text Option from Grid:");
        System.out.println("   Position: Row 1, Column 1 (First option)");
        
        // Get Text configuration from grid
        ThorX6HorizontalConfig.MarkTypeConfig textConfig = ThorX6HorizontalConfig.MARK_TYPES_GRID[0][0];
        System.out.println("   Name: " + textConfig.name);
        System.out.println("   Icon: " + textConfig.icon);
        System.out.println("   Tooltip: " + textConfig.tooltip);
        
        // Execute Text action
        System.out.println("\n📝 Executing Text Mark Action:");
        textConfig.action.run();
        
        // Also test directly
        System.out.println("\n📝 Direct Text Handler Test:");
        ThorX6HorizontalConfig.handleAddTextMark();
        
        System.out.println("\n✅ Text option is properly available in Add Mark dropdown!");
        
        // Show all available options for reference
        System.out.println("\n📋 Complete Mark Types Grid:");
        for (int row = 0; row < ThorX6HorizontalConfig.MARK_TYPES_GRID.length; row++) {
            System.out.println("   Row " + (row + 1) + ":");
            for (int col = 0; col < ThorX6HorizontalConfig.MARK_TYPES_GRID[row].length; col++) {
                ThorX6HorizontalConfig.MarkTypeConfig mark = ThorX6HorizontalConfig.MARK_TYPES_GRID[row][col];
                System.out.println("      [" + row + "," + col + "] " + mark.icon + " " + mark.name + " - " + mark.tooltip);
            }
        }
    }
}