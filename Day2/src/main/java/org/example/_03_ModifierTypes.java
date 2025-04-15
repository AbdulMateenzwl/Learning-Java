package org.example;

// 📘 This class demonstrates Access Control Modifiers
class AccessExample {
    public String publicVar = "Public Variable";          // Accessible everywhere
    protected String protectedVar = "Protected Variable"; // Accessible in same package or subclass
    String defaultVar = "Default Variable";               // Package-private (default)
    private String privateVar = "Private Variable";       // Only accessible within the class

    public void showAll() {
        System.out.println("Inside AccessExample:");
        System.out.println(publicVar);
        System.out.println(protectedVar);
        System.out.println(defaultVar);
        System.out.println(privateVar);
    }
}

// Subclass in same package to show access
class SubExample extends AccessExample {
    public void showInherited() {
        System.out.println("Inside SubExample:");
        System.out.println(publicVar);
        System.out.println(protectedVar);
        System.out.println(defaultVar);
        // System.out.println(privateVar); ❌ Not accessible
    }
}

public class _03_ModifierTypes {
    public static void main(String[] args) {
        AccessExample obj = new AccessExample();
        System.out.println("Accessing from main:");

        // ✅ Accessible
        System.out.println(obj.publicVar);      // always accessible
        System.out.println(obj.protectedVar);   // (same package) or subclass
        System.out.println(obj.defaultVar);     // (same package)
        // System.out.println(obj.privateVar);  // Not accessible

        obj.showAll();

        SubExample sub = new SubExample();
        sub.showInherited();
    }
}
