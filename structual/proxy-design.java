// The Proxy Design Pattern

// The Proxy Pattern is a structural design pattern that provides a surrogate, placeholder, or representative object to control access to another target object. The proxy intercepts requests from the client, performs access management, lazy initialization, or network communication, and then delegates the call to the real object.

// Core Structural Roles
// Subject Interface (ISubject): Defines the common interface for the Real Subject and the Proxy so the client can treat them interchangeably.

// Real Subject (RealSubject): The actual underlying service object that contains core business logic or expensive resources.

// Proxy (ProxySubject): Implements the Subject interface (is-a) and holds a reference to the Real Subject (has-a), managing when and how the Real Subject is accessed.

// Client: Communicates with the service exclusively through the Subject interface.

// Primary Types of Proxies
//   Proxy Type        Core Purpose                                                                        Typical Behavior   
//   Virtual Proxy     Controls access to expensive resources (Lazy Loading / Caching)                Postpones creating the heavy object until its method is actually called.
//   Protection Proxy  Controls access based on permissions, roles, or authentication                Checks user rights before delegating execution to the real object.
//   Remote Proxy      Represents an object located on a remote server or different address space    Handles serialization, network sockets, and communication protocols locally.


// Main entry point - Copy-paste and run directly
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("1. VIRTUAL PROXY DEMO (LAZY LOADING)");
        System.out.println("========================================");

        // Object reference created, but heavy disk loading has NOT occurred yet
        IImage image = new ImageProxy("high_res_photo.png");
        System.out.println("ImageProxy created. RealImage is not loaded yet.");

        System.out.println("\nFirst display call:");
        image.display(); // Triggers loading on demand

        System.out.println("\nSecond display call:");
        image.display(); // Uses already loaded instance without re-loading

        System.out.println("\n========================================");
        System.out.println("2. PROTECTION PROXY DEMO (ACCESS CONTROL)");
        System.out.println("========================================");

        User regularUser = new User("Bob", false);
        User premiumUser = new User("Alice", true);

        IDocumentReader docProxyForBob = new DocumentReaderProxy(regularUser);
        IDocumentReader docProxyForAlice = new DocumentReaderProxy(premiumUser);

        System.out.println("Regular User attempting to unlock PDF:");
        docProxyForBob.unlockPdf("finance_report.pdf", "secret123");

        System.out.println("\nPremium User attempting to unlock PDF:");
        docProxyForAlice.unlockPdf("finance_report.pdf", "secret123");
    }
}

// =============================================================
// SECTION 1: Virtual Proxy (Lazy Loading Example)
// =============================================================

// 1. Subject Interface
interface IImage {
    void display();
}

// 2. Real Subject (Resource-heavy initialization)
class RealImage implements IImage {
    private final String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk(); // Expensive operation simulated in constructor
    }

    private void loadFromDisk() {
        System.out.println("RealImage: Loading and decompressing " + fileName + " from disk...");
    }

    @Override
    public void display() {
        System.out.println("RealImage: Displaying " + fileName + " on screen.");
    }
}

// 3. Virtual Proxy (Implements IImage & wraps RealImage lazily)
class ImageProxy implements IImage {
    private final String fileName;
    private RealImage realImage; // Held as null until explicitly needed

    public ImageProxy(String fileName) {
        this.fileName = fileName;
        this.realImage = null;
    }

    @Override
    public void display() {
        // Lazy loading: create heavy object only when required
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}

// =============================================================
// SECTION 2: Protection Proxy (Access Control Example)
// =============================================================

class User {
    private final String name;
    private final boolean isPremium;

    public User(String name, boolean isPremium) {
        this.name = name;
        this.isPremium = isPremium;
    }

    public String getName() {
        return name;
    }

    public boolean isPremium() {
        return isPremium;
    }
}

// 1. Subject Interface
interface IDocumentReader {
    void unlockPdf(String filePath, String password);
}

// 2. Real Subject (Critical functionality)
class RealDocumentReader implements IDocumentReader {
    @Override
    public void unlockPdf(String filePath, String password) {
        System.out.println("RealDocumentReader: PDF [" + filePath + "] unlocked successfully with password [" + password + "].");
    }
}

// 3. Protection Proxy (Validates authorization before forwarding request)
class DocumentReaderProxy implements IDocumentReader {
    private final User user;
    private final RealDocumentReader realDocumentReader;

    public DocumentReaderProxy(User user) {
        this.user = user;
        this.realDocumentReader = new RealDocumentReader();
    }

    @Override
    public void unlockPdf(String filePath, String password) {
        // Validation check
        if (!user.isPremium()) {
            System.out.println("Access Denied: User '" + user.getName() + "' does not have a Premium subscription to unlock PDF documents.");
            return;
        }

        // Delegate execution once authorized
        realDocumentReader.unlockPdf(filePath, password);
    }
}
