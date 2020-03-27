package smarttrain;

import java.util.concurrent.LinkedBlockingQueue;

public class Track {

    private int name;
    private LinkedBlockingQueue<Message> inbox;
    private Track left;
    private Track right;

    public Track(int name, Track left, Track right){
        this.name = name;
        this.left = left;
        this.right = right;
        this.inbox = new LinkedBlockingQueue<Message>();
    }

    public void recieiveMessage(Message m){
        inbox.add(m);
    }

}
