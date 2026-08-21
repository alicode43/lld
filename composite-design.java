import java.util.ArrayList;
import java.util.List;

// ============================================================
// 1. COMPONENT INTERFACE
// ============================================================
interface FileSystemItem {
    void ls(int indent);
    void openAll(int indent);
    int getSize();
    FileSystemItem cd(String target);
    String getName();
    boolean isFolder();
}

// ============================================================
// 2. LEAF COMPONENT (File)
// ============================================================
class File implements FileSystemItem {
    private final String name;
    private final int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void ls(int indent) {
        printIndent(indent);
        System.out.println(name);
    }

    @Override
    public void openAll(int indent) {
        printIndent(indent);
        System.out.println(name);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public FileSystemItem cd(String target) {
        // Cannot change directory into a file (Leaf node)
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

    private void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }
}

// ============================================================
// 3. COMPOSITE COMPONENT (Folder)
// ============================================================
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
            printIndent(indent);
            if (child.isFolder()) {
                System.out.println("+" + child.getName());
            } else {
                System.out.println(child.getName());
            }
        }
    }

    @Override
    public void openAll(int indent) {
        printIndent(indent);
        System.out.println("+" + name);
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
    public FileSystemItem cd(String target) {
        for (FileSystemItem child : children) {
            if (child.isFolder() && child.getName().equals(target)) {
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

    private void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }
}

// ============================================================
// 4. CLIENT / DRIVER CODE
// ============================================================
public class Main {
    public static void main(String[] args) {
        // Create root directory
        Folder root = new Folder("root");
        root.add(new File("file1.txt", 1));
        root.add(new File("file2.txt", 1));

        // Create docs directory with files
        Folder docs = new Folder("docs");
        docs.add(new File("resume.pdf", 1));
        docs.add(new File("notes.txt", 1));
        root.add(docs);

        // Create images directory with file
        Folder images = new Folder("images");
        images.add(new File("photo.jpg", 1));
        root.add(images);

        // 1. Test openAll() - Expands the entire hierarchy
        System.out.println("--- 1. openAll() Output ---");
        root.openAll(0);

        // 2. Test ls() on root
        System.out.println("\n--- 2. ls() on root ---");
        root.ls(0);

        // 3. Test ls() on docs
        System.out.println("\n--- 3. ls() on docs ---");
        docs.ls(0);

        // 4. Test cd() command
        System.out.println("\n--- 4. cd('docs') followed by ls() ---");
        FileSystemItem cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("Could not cd into docs");
        }

        // 5. Test getSize() - Recursive calculation across composite tree
        System.out.println("\n--- 5. Total Size of Root ---");
        System.out.println("Total Root Size: " + root.getSize() + " KB");
    }
}
