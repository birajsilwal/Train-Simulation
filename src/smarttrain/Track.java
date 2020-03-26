package smarttrain;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Track {

    private int name;
    private LinkedBlockingQueue<String> inbox;
    private Track left;
    private Track right;

    public Track(int name, Track left, Track right){
        this.name = name;
        this.left = left;
        this.right = right;
        this.inbox = new LinkedBlockingQueue<String>();
    }

    public void recieiveMessage(String m){
        inbox.add(m);
    }

}
