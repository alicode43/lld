// The Composite Pattern allows you to compose objects into tree structures to represent part-whole hierarchies. It enables clients to treat individual objects (Leaf) and compositions of objects (Composite) uniformly through a common interface (Component) [35:42].

// Component (FileSystemItem): Declares common operations for both simple and complex objects (ls, openAll, getSize, cd).

// Leaf (File): Represents the terminal nodes that have no children.

// Composite (Folder): Contains child components (File or sub-Folder) and delegates work recursively down the tree.




import java.util.ArrayList;
import java.util.List;

// 1. Component Interface
interface FileSystemItem {
    void ls(int indent);
    void openAll(int indent);
    int getSize();
    FileSystemItem cd(String targetName);
    String getName();
    boolean isFolder();
}

// 2. Leaf Class: File (cannot have child elements)
class File implements FileSystemItem {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void ls(int indent) {
        System.out.println(" ".repeat(indent) + name);
    }

    @Override
    public void openAll(int indent) {
        System.out.println(" ".repeat(indent) + name);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public FileSystemItem cd(String targetName) {
        // Changing directory into a file is not supported
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}

// 3. Composite Class: Folder (can hold Files or other Folders)
class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> children;

    public Folder(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void add(FileSystemItem item) {
        children.add(item);
    }

    @Override
    public void ls(int indent) {
        for (FileSystemItem child : children) {
            String prefix = child.isFolder() ? "+ " : "";
            System.out.println(" ".repeat(indent) + prefix + child.getName());
        }
    }

    @Override
    public void openAll(int indent) {
        // Print current folder name
        System.out.println(" ".repeat(indent) + "+ " + name);
        // Recursively trigger openAll on all children
        for (FileSystemItem child : children) {
            child.openAll(indent + 4);
        }
    }

    @Override
    public int getSize() {
        // Total size = sum of sizes of all child items (recursively computed)
        int totalSize = 0;
        for (FileSystemItem child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }

    @Override
    public FileSystemItem cd(String targetName) {
        for (FileSystemItem child : children) {
            if (child.isFolder() && child.getName().equalsIgnoreCase(targetName)) {
                return child;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return true;
    }
}

// 4. Client / Main Demo
public class Main {
    public static void main(String[] args) {
        // Create root directory
        Folder root = new Folder("root");

        // Add root files
        root.add(new File("file1.txt", 1));
        root.add(new File("file2.txt", 1));

        // Create 'docs' subfolder
        Folder docs = new Folder("docs");
        docs.add(new File("resume.pdf", 1));
        docs.add(new File("notes.txt", 1));
        root.add(docs);

        // Create 'images' subfolder
        Folder images = new Folder("images");
        images.add(new File("photo.jpg", 1));
        root.add(images);

        System.out.println("=== 1. openAll() Output ===");
        root.openAll(0);

        System.out.println("\n=== 2. ls() on root ===");
        root.ls(0);

        System.out.println("\n=== 3. Total Size of Root Folder ===");
        System.out.println("Total Size: " + root.getSize() + " KB");

        System.out.println("\n=== 4. cd into 'docs' and ls() ===");
        FileSystemItem cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("Directory not found!");
        }
    }
}
