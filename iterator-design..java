import java.util.*;

// ==========================================
// 1. CORE INTERFACES (Iterator & Iterable)
// ==========================================
interface CustomIterator<T> {
    boolean hasNext();
    T next();
}

interface CustomIterable<T> {
    CustomIterator<T> getIterator();
}

// ==========================================
// 2. LINKED LIST IMPLEMENTATION
// ==========================================
class ListNode implements CustomIterable<Integer> {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
        this.next = null;
    }

    @Override
    public CustomIterator<Integer> getIterator() {
        return new LinkedListIterator(this);
    }
}

class LinkedListIterator implements CustomIterator<Integer> {
    private ListNode current;

    public LinkedListIterator(ListNode head) {
        this.current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in LinkedList.");
        }
        int val = current.val;
        current = current.next;
        return val;
    }
}

// ==========================================
// 3. BINARY TREE (IN-ORDER ITERATOR)
// ==========================================
class TreeNode implements CustomIterable<Integer> {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    @Override
    public CustomIterator<Integer> getIterator() {
        return new BinaryTreeInOrderIterator(this);
    }
}

class BinaryTreeInOrderIterator implements CustomIterator<Integer> {
    private final Stack<TreeNode> stack = new Stack<>();

    public BinaryTreeInOrderIterator(TreeNode root) {
        pushLeft(root);
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more nodes in Binary Tree.");
        }
        TreeNode node = stack.pop();
        if (node.right != null) {
            pushLeft(node.right);
        }
        return node.val;
    }
}

// ==========================================
// 4. MUSIC PLAYLIST EXAMPLE
// ==========================================
class Song {
    private final String title;
    private final String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "'" + title + "' by " + artist;
    }
}

class Playlist implements CustomIterable<Song> {
    private final List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public CustomIterator<Song> getIterator() {
        return new PlaylistIterator(this.songs);
    }
}

class PlaylistIterator implements CustomIterator<Song> {
    private final List<Song> songs;
    private int currentIndex = 0;

    public PlaylistIterator(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < songs.size();
    }

    @Override
    public Song next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more songs in playlist.");
        }
        return songs.get(currentIndex++);
    }
}

// ==========================================
// 5. DRIVER CODE (CLIENT)
// ==========================================
public class Main {
    public static void main(String[] args) {

        // --- 1. Testing LinkedList Traversal ---
        System.out.println("=== 1. LinkedList Traversal ===");
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);

        CustomIterator<Integer> listIt = head.getIterator();
        while (listIt.hasNext()) {
            System.out.print(listIt.next() + " ");
        }
        System.out.println("\n");

        // --- 2. Testing Binary Tree In-Order Traversal ---
        System.out.println("=== 2. Binary Tree In-Order Traversal ===");
        /*
               2
              / \
             1   3
        */
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        CustomIterator<Integer> treeIt = root.getIterator();
        while (treeIt.hasNext()) {
            System.out.print(treeIt.next() + " ");
        }
        System.out.println("\n");

        // --- 3. Testing Playlist Traversal ---
        System.out.println("=== 3. Music Playlist Traversal ===");
        Playlist playlist = new Playlist();
        playlist.addSong(new Song("Admiring You", "Karan Aujla"));
        playlist.addSong(new Song("Husn", "Anuv Jain"));

        CustomIterator<Song> playlistIt = playlist.getIterator();
        while (playlistIt.hasNext()) {
            System.out.println(playlistIt.next());
        }
    }
}
