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
        this.inbox = new LinkedBlockingQueue<String>();
    }

    public void recieiveMessage(String m){
        inbox.add(m);
    }

    // define position of the track x and y and pass to the train

    // able to send the message


}
