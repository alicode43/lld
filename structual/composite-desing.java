// The Composite Design Pattern

// The Composite Pattern is a structural design pattern that lets you compose objects into tree-like structures to represent part-whole hierarchies. It allows clients to treat individual objects (Leaf) and compositions of objects (Composite) uniformly through a single common interface (Component).

// Core Structure & Components
// Component (FileSystemItem): The common interface declaring shared operations for all nodes in the tree (ls, openAll, getSize, cd).

// Leaf (File): Terminal objects that have no children. They implement base behavior directly.

// Composite (Folder): Complex containers that can store children (Files or sub-Folders). They implement operations by delegating tasks recursively down to all their children.

// Client (Main): Interacts with all elements uniformly via the FileSystemItem interface without needing type checks or separate lists.

import java.util.ArrayList;
import java.util.List;

// Main execution entry point - Copy-paste and run directly
public class Main {
    public static void main(String[] args) {
        // 1. Create Root Directory
        Folder root = new Folder("root");
        root.add(new File("file1.txt", 1));
        root.add(new File("file2.txt", 1));

        // 2. Create 'docs' Subfolder
        Folder docs = new Folder("docs");
        docs.add(new File("resume.pdf", 1));
        docs.add(new File("notes.txt", 1));
        root.add(docs);

        // 3. Create 'images' Subfolder
        Folder images = new Folder("images");
        images.add(new File("photo.jpg", 1));
        root.add(images);

        // Demo 1: Recursive expansion of entire hierarchy
        System.out.println("=== 1. openAll() Output ===");
        root.openAll(0);

        // Demo 2: Listing direct contents only
        System.out.println("\n=== 2. ls() on root ===");
        root.ls(0);

        // Demo 3: Total aggregated size computation
        System.out.println("\n=== 3. Total Size of Root Folder ===");
        System.out.println("Total Size: " + root.getSize() + " KB");

        // Demo 4: Change directory and list contents
        System.out.println("\n=== 4. cd into 'docs' and ls() ===");
        FileSystemItem cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("Directory not found!");
        }
    }
}

// 1. Component Interface
interface FileSystemItem {
    void ls(int indent);
    void openAll(int indent);
    int getSize();
    FileSystemItem cd(String targetName);
    String getName();
    boolean isFolder();
}

// 2. Leaf Class (Cannot contain children)
class File implements FileSystemItem {
    private final String name;
    private final int size;

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
        // Operation not supported on leaf node
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

// 3. Composite Class (Contains children & delegates recursively)
class Folder implements FileSystemItem {
    private final String name;
    private final List<FileSystemItem> children;

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
        System.out.println(" ".repeat(indent) + "+ " + name);
        for (FileSystemItem child : children) {
            child.openAll(indent + 4);
        }
    }

    @Override
    public int getSize() {
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
