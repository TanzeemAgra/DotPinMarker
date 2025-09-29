/**
 * Test to verify that vertical dragging no longer causes automatic horizontal movement
 * and horizontal dragging no longer causes automatic vertical movement.
 * 
 * Focus: Cross-directional movement isolation
 */
import java.awt.*;

public class TestCrossDirectionalMovement {
    public static void main(String[] args) {
        System.out.println("🧪 TESTING: Cross-Directional Movement Isolation");
        System.out.println("=".repeat(60));
        
        // Create a TextMark for testing
        TextMark textMark = new TextMark(100, 100, "Test Cross Movement");
        
        // Test 1: Pure Vertical Drag (Should NOT cause horizontal position changes)
        System.out.println("\n📏 TEST 1: Pure Vertical Drag (TOP handle)");
        System.out.println("   Initial position: (" + textMark.x + ", " + textMark.y + ")");
        int initialX = textMark.x;
        int initialY = textMark.y;
        
        // Simulate vertical resize drag (TOP handle - should only affect font size)
        textMark.startResize(textMark.x + textMark.width/2, textMark.y, ResizeHandle.TOP);
        textMark.continueResize(textMark.x + textMark.width/2, textMark.y - 20);  // Pure vertical movement
        
        System.out.println("   After vertical drag: (" + textMark.x + ", " + textMark.y + ")");
        System.out.println("   X position change: " + (textMark.x - initialX) + " (should be 0)");
        System.out.println("   Y position change: " + (textMark.y - initialY) + " (should be 0 - position locked)");
        
        if (textMark.x == initialX) {
            System.out.println("   ✅ PASS: No unwanted horizontal movement during vertical drag");
        } else {
            System.out.println("   ❌ FAIL: Unwanted horizontal movement detected: " + (textMark.x - initialX) + "px");
        }
        
        // Test 2: Pure Horizontal Drag (Should NOT cause vertical position changes)  
        System.out.println("\n📏 TEST 2: Pure Horizontal Drag (RIGHT handle)");
        initialX = textMark.x;
        initialY = textMark.y;
        System.out.println("   Initial position: (" + initialX + ", " + initialY + ")");
        
        // Simulate horizontal resize drag (RIGHT handle - should only affect character spacing)
        textMark.startResize(textMark.x + textMark.width, textMark.y + textMark.height/2, ResizeHandle.RIGHT);
        textMark.continueResize(textMark.x + textMark.width + 30, textMark.y + textMark.height/2);  // Pure horizontal movement
        
        System.out.println("   After horizontal drag: (" + textMark.x + ", " + textMark.y + ")");
        System.out.println("   X position change: " + (textMark.x - initialX) + " (should be 0)");
        System.out.println("   Y position change: " + (textMark.y - initialY) + " (should be 0)");
        
        if (textMark.y == initialY) {
            System.out.println("   ✅ PASS: No unwanted vertical movement during horizontal drag");
        } else {
            System.out.println("   ❌ FAIL: Unwanted vertical movement detected: " + (textMark.y - initialY) + "px");
        }
        
        // Test 3: LEFT Handle Horizontal Drag (Most problematic case)
        System.out.println("\n📏 TEST 3: LEFT Handle Horizontal Drag (Previously Problematic)");
        initialX = textMark.x;
        initialY = textMark.y;
        System.out.println("   Initial position: (" + initialX + ", " + initialY + ")");
        
        // Simulate LEFT handle drag - this was causing automatic vertical position adjustments
        textMark.startResize(textMark.x, textMark.y + textMark.height/2, ResizeHandle.LEFT);
        textMark.continueResize(textMark.x - 25, textMark.y + textMark.height/2);  // Pure horizontal movement
        
        System.out.println("   After LEFT handle drag: (" + textMark.x + ", " + textMark.y + ")");
        System.out.println("   X position change: " + (textMark.x - initialX) + " (should be 0)");
        System.out.println("   Y position change: " + (textMark.y - initialY) + " (should be 0 - NO AUTO ADJUSTMENT)");
        
        if (textMark.y == initialY) {
            System.out.println("   ✅ PASS: No automatic vertical position adjustment during LEFT handle drag");
        } else {
            System.out.println("   ❌ FAIL: Unwanted vertical position adjustment: " + (textMark.y - initialY) + "px");
        }
        
        // Test 4: Mixed Directional Drag (Diagonal - should be controlled)
        System.out.println("\n📏 TEST 4: Mixed Directional Drag (BOTTOM_RIGHT diagonal)");
        initialX = textMark.x;
        initialY = textMark.y;
        System.out.println("   Initial position: (" + initialX + ", " + initialY + ")");
        
        // Simulate diagonal drag - should use directional dominance
        textMark.startResize(textMark.x + textMark.width, textMark.y + textMark.height, ResizeHandle.BOTTOM_RIGHT);
        textMark.continueResize(textMark.x + textMark.width + 15, textMark.y + textMark.height + 10);  // Mixed movement
        
        System.out.println("   After diagonal drag: (" + textMark.x + ", " + textMark.y + ")");
        System.out.println("   X position change: " + (textMark.x - initialX) + " (should be 0)");
        System.out.println("   Y position change: " + (textMark.y - initialY) + " (should be 0)");
        
        if (textMark.x == initialX && textMark.y == initialY) {
            System.out.println("   ✅ PASS: Position remains stable during diagonal resize");
        } else {
            System.out.println("   ❌ FAIL: Position drift during diagonal resize - X:" + (textMark.x - initialX) + "px, Y:" + (textMark.y - initialY) + "px");
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 CROSS-DIRECTIONAL MOVEMENT ISOLATION TEST COMPLETE");
        System.out.println("✨ Expected Result: All position changes should be 0 (no unwanted movement)");
    }
}