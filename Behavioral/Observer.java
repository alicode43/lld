import java.util.*;

// 1. Observer Interface
interface Subscriber {
    void update();
}

// 2. Observable Interface
interface Channel {
    void subscribe(Subscriber s);
    void unsubscribe(Subscriber s);
    void notifySubscribers();
}

// 3. Concrete Observable
class YouTubeChannel implements Channel {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String latestVideo;

    public void subscribe(Subscriber s) { 
        subscribers.add(s); 
    }

    public void unsubscribe(Subscriber s) { 
        subscribers.remove(s); 
    }

    public void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update();
        }
    }

    public void uploadVideo(String title) {
        this.latestVideo = title;
        System.out.println("Channel uploaded: " + title);
        notifySubscribers();
    }

    public String getLatestVideo() { 
        return latestVideo; 
    }
}

// 4. Concrete Observer
class User implements Subscriber {
    private String name;
    private YouTubeChannel channel;

    public User(String name, YouTubeChannel channel) {
        this.name = name;
        this.channel = channel;
    }

    public void update() {
        System.out.println("Hey " + name + ", new video: " + channel.getLatestVideo());
    }
}

// 5. Entry Point
public class Main {
    public static void main(String[] args) {
        YouTubeChannel techChannel = new YouTubeChannel();

        User alice = new User("Alice", techChannel);
        User bob = new User("Bob", techChannel);

        techChannel.subscribe(alice);
        techChannel.subscribe(bob);

        techChannel.uploadVideo("Design Patterns: Observer Explained");
    }
}
